#include <iostream>
#include <iomanip>
#include <mpi.h>
#include <random>
#include <time.h>
#include "MatrixMult.h"


void preOutput(int size) {
	std::cout << "\n\nSize: " << size;
	std::cout << "\n" << std::setw(15) << "|  Algorithm  |" << std::setw(8) << "| Size |" << std::setw(16) << "|     Time     |";
	std::cout << "\n---------------------------------------";
}
void demo(int argc, char* argv[], int s1, int s2, int s3) {
	preOutput(s1);
	MatrixMult::testAlgorithm(argc, argv, s1);

	preOutput(s2);
	MatrixMult::testAlgorithm(argc, argv, s2);

	preOutput(s3);
	MatrixMult::testAlgorithm(argc, argv, s3);
}

int main(int argc, char* argv[])
{
	srand(time(0));
	setvbuf(stdout, 0, _IONBF, 0);
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &processRank);
	MPI_Comm_size(MPI_COMM_WORLD, &processNum);

	std::cout << "\n" << processNum << " processes\n";
	if (processNum == 1) {
		demo(argc, argv, 75, 300, 1200);
	}
	else if (processNum == 4) {
		demo(argc, argv, 72, 288, 1152);
	}
	else if (processNum == 9) {
		int s1 = 81, s2 = 324, s3 = 1296;
		demo(argc, argv, 81, 324, 1296);
	}
	else {
		std::cout << "\nInvalid number of processes";
	}

	MPI_Finalize();
	return 0;
}
