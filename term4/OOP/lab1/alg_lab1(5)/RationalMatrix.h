#pragma once

#include <vector>
#include "Fraction.h"

class RationalMatrix {
	int rows;
	int cols;
	std::vector<std::vector<fract>> content;

	// for Strassen's multiplication
	void extend(int maxSize);
	RationalMatrix multRec(RationalMatrix* b, int ia, int ja, int ib, int jb, int size);
	void shrink(int height, int width);

	// for minors method inversion
	RationalMatrix getMinor(int indRow, int indCol);
	RationalMatrix transposeMatrix();

	// for Gauss-Jordan elimination (inversion)
	RationalMatrix add_identity_matrix();
	bool gaussian_jordan();
	RationalMatrix remove_identity_matrix();
	void exchange(int row1, int row2);

	// for multiple linear regression
	RationalMatrix add_const_column(int position, fract val);
	RationalMatrix extract_col(int start_col, int end_col);
	fract rss(RationalMatrix x, RationalMatrix y, RationalMatrix coeff);
	fract tss(RationalMatrix x, RationalMatrix y);
	fract coeff_of_determination(RationalMatrix x, RationalMatrix y, RationalMatrix coeff);

public:
	void print() {
		for (int i = 0; i < this->rows; i++)
		{
			for (int j = 0; j < this->cols; j++) {
				this->content[i][j].print();
				std::cout << "   ";
			}
			std::cout << std::endl;
		}
	}

	// setting starting positions and final size manually
	RationalMatrix sum(const RationalMatrix* b, int ia, int ja, int ib, int jb, int height, int width) {
		RationalMatrix res;
		if (height + ia > this->rows || height + ib > b->rows || width + ja > this->cols || width + jb > b->cols) return res;

		std::vector<fract> tmp = {};
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
				tmp.push_back((this->content)[ia + i][ja + j].sum((b->content)[ib + i][jb + j]));
			res.content.push_back(tmp);
			tmp = {};
		}

		res.content.erase(res.content.begin());
		res.rows = height;
		res.cols = width;

		return res;
	}
	// taking the whole matrix
	RationalMatrix sum(const RationalMatrix* b) {
		RationalMatrix res;
		if (this->cols != b->cols || this->rows != b->rows) return res;
		return this->sum(b, 0, 0, 0, 0, this->rows, this->cols);
	};

	// setting starting positions and final size manually
	RationalMatrix diff(const RationalMatrix* b, int ia, int ja, int ib, int jb, int height, int width) {
		RationalMatrix res;
		if (height + ia > this->rows || height + ib > b->rows || width + ja > this->cols || width + jb > b->cols) return res;

		std::vector<fract> tmp = {};
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
				tmp.push_back((this->content)[ia + i][ja + j].diff((b->content)[ib + i][jb + j]));
			res.content.push_back(tmp);
			tmp = {};
		}

		res.content.erase(res.content.begin());
		res.rows = height;
		res.cols = width;

		return res;
	}
	// taking the whole matrix
	RationalMatrix diff(const RationalMatrix* b) {
		RationalMatrix res;
		if (this->cols != b->cols || this->rows != b->rows) return res;
		return this->diff(b, 0, 0, 0, 0, this->rows, this->cols);
	}

	// setting starting positions and final size manually
	RationalMatrix mult(const RationalMatrix* b, int ia, int ja, int ib, int jb, int heightRes, int equalSide, int widthRes) {
		RationalMatrix res;
		if (heightRes + ia > this->rows || equalSide + ib > b->rows || equalSide + ja > this->cols || widthRes + jb > b->cols) return res;

		std::vector<fract> tmp = {};
		fract t;
		for (int i = 0; i < heightRes; i++)
		{
			for (int j = 0; j < widthRes; j++)
			{
				t = fract(0, 1);
				for (int k = 0; k < equalSide; k++)
					t = t.sum(this->content[ia + i][ja + k].mult(b->content[ib + k][jb + j]));
				tmp.push_back(t);
			}
			res.content.push_back(tmp);
			tmp = {};
		}

		res.content.erase(res.content.begin());
		res.rows = heightRes;
		res.cols = widthRes;

		return res;
	}
	// taking the whole matrix
	RationalMatrix mult(const RationalMatrix* b) {
		RationalMatrix res;
		if (this->cols != b->rows) return res;
		return this->mult(b, 0, 0, 0, 0, this->rows, this->cols, b->cols);
	}

	fract get_matrix_det()
	{
		fract temp;   // temporary variable for storing det
		fract k(1, 1);      // power of minor

		if (this->rows != this->cols) {
			std::cout << "Matrix is not square" << std::endl;
			return fract(INT_MAX, 1);
		}
		if (this->rows < 1) {
			std::cout << "Matrix size is incorrect" << std::endl;
			return fract(INT_MAX, 1);
		}

		if (this->rows == 1)
			temp = this->content[0][0];
		else if (this->rows == 2)
			temp = (this->content[0][0].mult(this->content[1][1])).diff(this->content[1][0].mult(this->content[0][1]));
		else {
			for (int i = 0; i < this->rows; i++) {
				RationalMatrix temp_matrix = this->getMinor(0, i);
				temp = temp.sum(k.mult(this->content[0][i].mult(temp_matrix.get_matrix_det())));

				k = k.mult(fract(-1, 1));
			}
		}
		return temp;
	}

	bool isEqual(const RationalMatrix* b) {
		if (this->cols != b->cols || this->rows != b->rows) return 0;

		for (int i = 0; i < this->rows; i++)
			for (int j = 0; j < this->cols; j++)
				if (!this->content[i][j].isEqual(b->content[i][j])) return 0;

		return 1;
	}


	RationalMatrix StrassensMult(RationalMatrix* b);

	RationalMatrix minorInverse();

	RationalMatrix GaussianJordanInverse();

	// multiple linear regression
	RationalMatrix get_mlr_coeff(RationalMatrix y);
	void print_regression_model();


	RationalMatrix(std::vector<std::vector<fract>> matrix = { {} }) {
		content = matrix;
		rows = matrix.size();
		cols = matrix[0].size();
	}
	// matrix with random rational values from -5 to 5
	RationalMatrix(int height, int width, unsigned seed) {
		rows = height;
		cols = width;
		content = { {} };

		srand(seed);
		fract t;
		std::vector<fract> tmp = {};
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				t = fract(rand() % 11 - 5, rand() % 2 + 1);
				tmp.push_back(t);
			}
			content.push_back(tmp);
			tmp = {};
		}
		content.erase(content.begin());
	}

	int get_rows()
	{
		return this->rows;
	}
	int get_cols()
	{
		return this->cols;
	}
	fract get(int i, int j)
	{
		if (i < this->rows && j < this->cols)
			return this->content[i][j];
	}
};
