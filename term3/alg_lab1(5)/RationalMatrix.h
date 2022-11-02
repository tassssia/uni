#pragma once

#include <vector>
#include "Fraction.h"

class RationalMatrix {
	int rows;
	int cols;
	std::vector<std::vector<fract>> content;

	// for Strassen's multiplication
	void extend(int maxSize);
	RationalMatrix multMin(const RationalMatrix* b, int ia, int ja, int ib, int jb);
	RationalMatrix multRec(RationalMatrix* b, int ia, int ja, int ib, int jb, int size);
	void shrink(int height, int width);

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

	RationalMatrix mult(const RationalMatrix* b) {
		RationalMatrix res;
		if (this->cols != b->rows) return res;

		std::vector<fract> tmp = {};
		fract t;
		for (int i = 0; i < this->rows; i++)
		{
			for (int j = 0; j < b->cols; j++)
			{
				t = fract(0, 1);
				for (int k = 0; k < this->cols; k++)
					t = t.sum(this->content[i][k].mult(b->content[k][j]));
				tmp.push_back(t);
			}
			res.content.push_back(tmp);
			tmp = {};
		}

		res.content.erase(res.content.begin());
		res.rows = res.content.size();
		res.cols = res.content[0].size();

		return res;
	}

	bool isEqual(const RationalMatrix* b) {
		if (this->cols != b->cols || this->rows != b->rows) return 0;

		for (int i = 0; i < this->rows; i++)
			for (int j = 0; j < this->cols; j++)
				if (!this->content[i][j].isEqual(b->content[i][j])) return 0;

		return 1;
	}

	RationalMatrix StrassensMult(RationalMatrix* b);

	RationalMatrix(std::vector<std::vector<fract>> matrix = { {} }) {
		content = matrix;
		rows = matrix.size();
		cols = matrix[0].size();
	}
	// matrix with random rational values from -10 to 10
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
				rand() % 2 ? t = fract(rand() % 11, 1 + rand() % 10) : t = fract(-1 * (rand() % 11), 1 + rand() % 10);
				tmp.push_back(t);
			}
			content.push_back(tmp);
			tmp = {};
		}
	}
};