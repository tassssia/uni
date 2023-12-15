#pragma once
#include <iostream>
#include <random>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include <mpi.h>
#include <iostream>


static int maxVal = 3;
static int offset = 0;
static int precision = 1;
static int processNum = 0;
static int processRank = 0;
static MPI_Comm colComm;
static MPI_Comm rowComm;

namespace MatrixMult {
	namespace General {
		double* getRandom(unsigned int maxVal, int offset, unsigned int precision, unsigned int size) {
			unsigned int size2 = size * size;
			double* matrix = new double[size2];

			for (int i = 0; i < size2; i++) {
				matrix[i] = (rand() % (maxVal * precision) + offset * precision) / (double)precision;
			}

			return matrix;
		}

		double* getFilled(double filler, unsigned int size) {
			unsigned int size2 = size * size;
			double* matrix = new double[size2];

			for (int i = 0; i < size2; i++) {
				matrix[i] = filler;
			}

			return matrix;
		}

		void transpose(double* matrix, int size) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					double t = matrix[i * size + j];
					matrix[i * size + j] = matrix[j * size + i];
					matrix[j * size + i] = t;
				}
			}
		}

		void naiveMult(double* A, double* B, double* forRes, unsigned int size) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					for (int k = 0; k < size; k++) {
						forRes[i * size + j] += A[i * size + k] * B[k * size + j];
					}
				}
			}
		}
	}

	namespace StripDiagram {
		int coords;

		
		void initComms(int len) {
			MPI_Comm_split(MPI_COMM_WORLD, processRank / len, processRank, &rowComm);
			MPI_Comm_split(MPI_COMM_WORLD, processRank / len, processRank, &colComm);
		}
		void init(double*& A, double*& B, double*& C, double*& AStriped, double*& BStriped, double*& CStriped, int& size, int& len) {
			len = size / processNum;

			AStriped = new double[len * size];
			BStriped = new double[len * size];
			CStriped = new double[len * size];
			for (int i = 0; i < len * size; i++) {
				CStriped[i] = 0;
			}

			if (processRank == 0) {
				A = new double[size * size];
				B = new double[size * size];
				C = new double[size * size];

				A = General::getRandom(maxVal, offset, precision, size);
				B = General::getRandom(maxVal, offset, precision, size);
			}

		}

		void scatter(double* A, double* B, double* AStriped, double* BStriped, int size, int len) {
			if (processRank == 0) {
				General::transpose(B, size);
			}

			MPI_Scatter(&(A[size * len * coords]), len * size, MPI_DOUBLE, AStriped, len * size, MPI_DOUBLE, 0, rowComm);
			MPI_Scatter(&(B[size * len * coords]), len * size, MPI_DOUBLE, BStriped, len * size, MPI_DOUBLE, 0, rowComm);
		}
		
		void shift(double* BStrip, int len, int size) {
			MPI_Status status;

			int next = coords + 1;
			if (next == processNum) {
				next = 0;
			}

			int prev = coords - 1;
			if (prev == -1) {
				prev = processNum - 1;
			}

			MPI_Sendrecv_replace(BStrip, len * size, MPI_DOUBLE, next, 0, prev, 0, colComm, &status);
		}
		void mult(double* AStrip, double* BStrip, double* res, int size, int len, int iter) {
			int ind = len * iter;

			for (int i = 0; i < len; i++) {
				for (int j = 0; j < len; j++) {
					for (int k = 0; k < size; k++) {
						res[ind] += AStrip[i * size + k] * BStrip[j * size + k];
					}
					ind++;
				}
				ind += size - len;
			}
		}
		void calcStrip(double* AStrip, double* BStrip, double* CStrip, int len, int size) {
			int iter = coords;

			for (int i = 0; i < processNum; i++) {
				mult(AStrip, BStrip, CStrip, size, len, iter);
				iter++;

				if (iter == processNum) {
					iter = 0;
				}
				
				shift(BStrip, len, size);
			}
		}
		
		void getRes(double* C, double* CStriped, int len, int size) {
			MPI_Gather(CStriped, len * size, MPI_DOUBLE, C, len * size, MPI_DOUBLE, 0, rowComm);
		}
		void destruct(double* A, double* B, double* C, double* AStriped, double* BStriped, double* CStriped) {
			delete[] AStriped;
			delete[] BStriped;
			delete[] CStriped;

			if (processRank == 0) {
				delete[] A;
				delete[] B;
				delete[] C;
			}
		}

		void run(int argc, char* argv[], int dim) {
			double* A, * B, * C, * AStriped, * BStriped, * CStriped;
			int size, len;
			double start, end, takenTime;

			coords = processRank;
			size = dim;
			if (dim % processNum != 0) {
				if (processRank == 0) {
					std::cout << "\nInvalid processes number";
				}

				return;
			}

			init(A, B, C, AStriped, BStriped, CStriped, size, len);
			initComms(len);

			start = MPI_Wtime();
			scatter(A, B, AStriped, BStriped, size, len);
			calcStrip(AStriped, BStriped, CStriped, len, size);
			end = MPI_Wtime();

			getRes(C, CStriped, len, size);
			destruct(A, B, C, AStriped, BStriped, CStriped);

			takenTime = end - start;

			if (processRank == 0) {
				std::cout << "\n" << std::setw(15) << " Strip Diagram " << std::setw(8) << size << "  " << std::setw(13) << takenTime;
			}
		}

	}

	namespace Cannon {
		int grid[2];
		int gridSize;
		MPI_Comm gridComm;


		void initGridComms() {
			int dSize[2], period[2], subDim[2];

			dSize[0] = gridSize;
			dSize[1] = gridSize;
			period[0] = 0;
			period[1] = 0;
			
			MPI_Cart_create(MPI_COMM_WORLD, 2, dSize, period, 1, &gridComm);
			MPI_Cart_coords(gridComm, processRank, 2, grid);
			
			subDim[0] = 0;
			subDim[1] = 1;
			MPI_Cart_sub(gridComm, subDim, &rowComm);
			
			subDim[0] = 1;
			subDim[1] = 0;
			MPI_Cart_sub(gridComm, subDim, &colComm);
		}
		void init(double*& A, double*& B, double*& C, double*& ABlock, double*& BBlock, double*& CBlock, int& size, int& blockSize) {
			blockSize = size / gridSize;

			ABlock = new double[blockSize * blockSize];
			BBlock = new double[blockSize * blockSize];
			CBlock = new double[blockSize * blockSize];
			CBlock = General::getFilled(0, blockSize);

			if (processRank == 0) {
				A = new double[size * size];
				B = new double[size * size];
				C = new double[size * size];

				A = General::getRandom(maxVal, offset, precision, size);
				B = General::getRandom(maxVal, offset, precision, size);
			}
		}

		void scatterBlock(double* matrix, double* block, int row, int col, int size, int blockSize) {
			int start = col * blockSize * size + row * blockSize;
			int curr = start;

			for (int i = 0; i < blockSize; ++i, curr += size) {
				MPI_Scatter(&matrix[curr], blockSize, MPI_DOUBLE, &(block[i * blockSize]), blockSize, MPI_DOUBLE, 0, gridComm);
			}
		}
		void scatter(double* A, double* ABlock, double* B, double* BBlock, int size, int blockSize) {
			int N = grid[0];
			int M = grid[1];
			scatterBlock(A, ABlock, N, (N + M) % gridSize, size, blockSize);
			scatterBlock(B, BBlock, (N + M) % gridSize, M, size, blockSize);
		}
		
		void lShift(double* matrix, int size, int partSize) {
			int next = grid[1] + 1;
			if (grid[1] == gridSize - 1) {
				next = 0;
			}

			int prev = grid[1] - 1;
			if (grid[1] == 0) {
				prev = gridSize - 1;
			}

			MPI_Status status;
			MPI_Sendrecv_replace(matrix, partSize * partSize, MPI_DOUBLE, next, 0, prev, 0, rowComm, &status);
		}
		void rShift(double* matrix, int size, int partSize) {
			MPI_Status status;

			int next = grid[0] + 1;
			if (grid[0] == gridSize - 1) {
				next = 0;
			}

			int prev = grid[0] - 1;
			if (grid[0] == 0) {
				prev = gridSize - 1;
			}

			MPI_Sendrecv_replace(matrix, partSize * partSize, MPI_DOUBLE, next, 0, prev, 0, colComm, &status);
		}
		void initCalc(double* A, double* B, double* C, int size, int partSize) {
			for (int i = 0; i < gridSize; ++i) {
				General::naiveMult(A, B, C, partSize);

				lShift(A, size, partSize);
				rShift(B, size, partSize);
			}
		}

		void getRes(double* C, double* CBlock, int size, int partSize) {
			double* resRow = new double[size * partSize];
			for (int i = 0; i < partSize; i++) {
				MPI_Gather(&CBlock[i * partSize], partSize, MPI_DOUBLE, &resRow[i * size], partSize, MPI_DOUBLE, 0, rowComm);
			}

			if (grid[1] == 0) {
				MPI_Gather(resRow, partSize * size, MPI_DOUBLE, C, partSize * size, MPI_DOUBLE, 0, colComm);
			}

			delete[] resRow;
		}
		void destruct(double* A, double* B, double* C, double* ABlock, double* BBlock, double* CBlock) {
			if (processRank == 0) {
				delete[] A;
				delete[] B;
				delete[] C;
			}

			delete[] ABlock;
			delete[] BBlock;
			delete[] CBlock;
		}	

		void run(int argc, char* argv[], int dim) {
			double* A, * B, * C, * ABlock, * BBlock, * CBlock;
			int size, blockSize;
			double start, end, takenTime;

			size = dim;
			gridSize = sqrt((double)processNum);
			if (processNum != gridSize * gridSize) {
				if (processRank == 0) {
					std::cout << "\nInvalid processes number";
				}

				return;
			}

			initGridComms();
			init(A, B, C, ABlock, BBlock, CBlock, size, blockSize);

			scatter(A, ABlock, B, BBlock, size, blockSize);

			start = MPI_Wtime();
			initCalc(ABlock, BBlock, CBlock, size, blockSize);
			end = MPI_Wtime();

			getRes(C, CBlock, size, blockSize);

			destruct(A, B, C, ABlock, BBlock, CBlock);
			takenTime = end - start;

			if (processRank == 0) {
				std::cout << "\n" << std::setw(15) << " Cannon " << std::setw(8) << size << "  " << std::setw(13) << takenTime;
			}
		}
	}

	namespace Fox {
		int grid[2];
		int gridSize;
		MPI_Comm gridComm;

	
		void initGridComms() {
			int dim_size[2];
			int period[2];
			int sub_dimension[2];
			dim_size[0] = gridSize;
			dim_size[1] = gridSize;
			period[0] = 0;
			period[1] = 0;
			MPI_Cart_create(MPI_COMM_WORLD, 2, dim_size, period, 1, &gridComm);
			MPI_Cart_coords(gridComm, processRank, 2, grid);
			sub_dimension[0] = 0;
			sub_dimension[1] = 1;
			MPI_Cart_sub(gridComm, sub_dimension, &rowComm);
			sub_dimension[0] = 1;
			sub_dimension[1] = 0;
			MPI_Cart_sub(gridComm, sub_dimension, &colComm);
		}
		void init(double*& A, double*& B, double*& C, double*& ABlock, double*& BBlock, double*& CBlock, double*& ASubBlock, int& size, int& blockSize) {
			blockSize = size / gridSize;

			ABlock = new double[blockSize * blockSize];
			BBlock = new double[blockSize * blockSize];
			CBlock = new double[blockSize * blockSize];
			ASubBlock = new double[blockSize * blockSize];
			CBlock = General::getFilled(0, blockSize);

			if (processRank == 0) {
				A = new double[size * size];
				B = new double[size * size];
				C = new double[size * size];

				A = General::getRandom(maxVal, offset, precision, size);
				B = General::getRandom(maxVal, offset, precision, size);
			}
		}

		void scatterMatrix(double* matrix, double* matrixBlock, int size, int blockSize) {
			double* row = new double[blockSize * size];
			if (grid[1] == 0) {
				MPI_Scatter(matrix, blockSize * size, MPI_DOUBLE, row, blockSize * size, MPI_DOUBLE, 0, colComm);
			}

			for (int i = 0; i < blockSize; i++) {
				MPI_Scatter(&row[i * size], blockSize, MPI_DOUBLE, &(matrixBlock[i * blockSize]), blockSize, MPI_DOUBLE, 0, rowComm);
			}

			delete[] row;
		}
		void scatter(double* A, double* B, double* ABlock, double* BBlock, int size, int blockSize) {
			scatterMatrix(A, ABlock, size, blockSize);
			scatterMatrix(B, BBlock, size, blockSize);
		}
		
		void sendA(int i, double* A, double* ASubBlock, int blockSize) {
			int pivot = (grid[0] + i) % gridSize;
			
			if (grid[1] == pivot) {
				for (int i = 0; i < blockSize * blockSize; i++)
					A[i] = ASubBlock[i];
			}

			MPI_Bcast(A, blockSize * blockSize, MPI_DOUBLE, pivot, rowComm);
		}
		void sendB(double* B, int blockSize) {
			int next_process = grid[0] + 1;
			if (grid[0] == gridSize - 1) {
				next_process = 0;
			}

			int prev_process = grid[0] - 1;
			if (grid[0] == 0) { 
				prev_process = gridSize - 1;
			}

			MPI_Status mpi_status;
			MPI_Sendrecv_replace(B, blockSize * blockSize, MPI_DOUBLE, next_process, 0, prev_process, 0, colComm, &mpi_status);
		}
		void initCalc(double* A, double* ASubBlock, double* B, double* C, int blockSize) {
			for (int i = 0; i < gridSize; i++) {
				sendA(i, A, ASubBlock, blockSize);
				General::naiveMult(A, B, C, blockSize);
				sendB(B, blockSize);
			}
		}
		
		void getRes(double* C, double* CBlock, int size, int blockSize) {
			double* resRow = new double[size * blockSize];
			for (int i = 0; i < blockSize; i++) {
				MPI_Gather(&CBlock[i * blockSize], blockSize, MPI_DOUBLE, &resRow[i * size], blockSize, MPI_DOUBLE, 0, rowComm);
			}

			if (grid[1] == 0) {
				MPI_Gather(resRow, blockSize * size, MPI_DOUBLE, C, blockSize * size, MPI_DOUBLE, 0, colComm);
			}

			delete[] resRow;
		}
		void destruct(double* A, double* B, double* C, double* ABlock, double* BBlock, double* CBlock, double* ASubBlock = NULL) {
			if (processRank == 0) {
				delete[] A;
				delete[] B;
				delete[] C;
			}

			delete[] ABlock;
			delete[] BBlock;
			delete[] CBlock;

			if (!ASubBlock) {
				delete[] ASubBlock;
			}
		}

		void run(int argc, char* argv[], int dim) {
			double* A, * B, * C, * ABlock, * BBlock, * CBlock, * ASubBlock;
			int size, blockSize;
			double start, end, takenTime;

			size = dim;
			gridSize = sqrt((double)processNum);
			if (processNum != gridSize * gridSize) {
				if (processRank == 0) {
					std::cout << "\nInvalid processes number";
				}

				return;
			}

			initGridComms();
			init(A, B, C, ABlock, BBlock, CBlock, ASubBlock, size, blockSize);

			scatter(A, B, ASubBlock, BBlock, size, blockSize);

			start = MPI_Wtime();
			initCalc(ABlock, ASubBlock, BBlock, CBlock, blockSize);
			end = MPI_Wtime();

			getRes(C, CBlock, size, blockSize);
			destruct(A, B, C, ABlock, BBlock, CBlock, ASubBlock);

			takenTime = end - start;
			if (processRank == 0) {
				std::cout << "\n" << std::setw(15) << " Fox " << std::setw(8) << size << "  " << std::setw(13) << takenTime;
			}
		}
	}

	void testAlgorithm(int argc, char* argv[], int dim) {
		StripDiagram::run(argc, argv, dim);
		Cannon::run(argc, argv, dim);
		Fox::run(argc, argv, dim);
	}
};