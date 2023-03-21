#pragma once

#include <vector>
#include "Fraction.h"

/// <summary>
/// Class of matrixes filled with rational numbers
/// 
/// <param name="rows">number of rows in matrix</param>
/// <param name="cols">number of columns in matrix</param>
/// <param name="content">the actual matrix</param>
/// </summary>
class RationalMatrix {
	int rows;
	int cols;
	std::vector<std::vector<fract>> content;

	// for Strassen's multiplication
	
	/// <summary>
	/// Extends matrix to have size 2^n*2^n (n is integer) for Strassen's multiplication with fractions 0/1.
	/// </summary>
	/// <param name="maxSize">final width and height of the matrix</param>
	void extend(int maxSize);
	/// <summary>
	/// Recursive multiplying part of Strassen's multiplication algorithm.
	/// </summary>
	/// <param name="b">matrix to multiply</param>
	/// <param name="ia">starting row index in the first matrix ("this")</param>
	/// <param name="ja">starting column index in the first matrix ("this")</param>
	/// <param name="ib">starting row index in the second matrix ("b")</param>
	/// <param name="jb">starting column index in the second matrix ("b")</param>
	/// <param name="size">number of rows and colums to be taken</param>
	/// <returns>product of the matrixes</returns>
	RationalMatrix multRec(RationalMatrix* b, int ia, int ja, int ib, int jb, int size);
	/// <summary>
	/// Shrinks matrix to its original size, oposite to extend(int maxSize).
	/// </summary>
	/// <param name="height">final height of the matrix</param>
	/// <param name="width">final width of the matrix</param>
	void shrink(int height, int width);

	// for minors method inversion

	/// <summary>
	/// Creates minor matrix.
	/// </summary>
	/// <param name="indRow">index of the row to be excluded</param>
	/// <param name="indCol">index of the column to be excluded</param>
	/// <returns>matrix without specified row and column</returns>
	RationalMatrix getMinor(int indRow, int indCol);
	/// <summary>
	/// Transposes matrix.
	/// </summary>
	/// <returns>transposed matrix</returns>
	RationalMatrix transposeMatrix();

	// for Gauss-Jordan elimination (inversion)

	/// <summary>
	/// Adds matrix filled with 0s and 1s on the main diagonal on the right of given matrix.
	/// </summary>
	/// <returns>(given m. | identity m.)</returns>
	RationalMatrix add_identity_matrix();
	/// <summary>
	/// Performs Gaussian-Jordan transformation.
	/// </summary>
	/// <returns>true if a matrix was transformed successfully, false otherwise</returns>
	bool gaussian_jordan();
	/// <summary>
	/// Removes the left half of the columns, which will contain an identity matrix.
	/// </summary>
	/// <returns>inverted matrix</returns>
	RationalMatrix remove_identity_matrix();
	/// <summary>
	/// Swaps rows with given indexes in the given matrix.
	/// </summary>
	/// <param name="row1">index of the first row to be swapped</param>
	/// <param name="row2">index of the second row to be swapped</param>
	void exchange(int row1, int row2);

	// for multiple linear regression

	/// <summary>
	/// Adds column of fractions with a specified value.
	/// </summary>
	/// <param name="position">index which column will be added after</param>
	/// <param name="val">value to be added</param>
	/// <returns>matrix with an added column or an empty matrix, if position was out of range</returns>
	RationalMatrix add_const_column(int position, fract val);
	/// <summary>
	/// Rewriting columns from one with a specified start index (included) to one with final index.
	/// </summary>
	/// <param name="start_col">index to start with</param>
	/// <param name="end_col">index of the first column which won't be rewrited</param>
	/// <returns>rewriten columns of the given matrix</returns>
	RationalMatrix extract_col(int start_col, int end_col);
	/// <summary>
	/// Calculats the residual sum of squares.
	/// </summary>
	/// <param name="x"></param>
	/// <param name="y"></param>
	/// <param name="coeff"></param>
	/// <returns>residual sum of squares</returns>
	fract rss(RationalMatrix x, RationalMatrix y, RationalMatrix coeff);
	/// <summary>
	/// Calculats the total sum of squares.
	/// </summary>
	/// <param name="x"></param>
	/// <param name="y"></param>
	/// <returns>total sum of squares</returns>
	fract tss(RationalMatrix x, RationalMatrix y);
	/// <summary>
	/// Calculates the coefficient of determination.
	/// </summary>
	/// <param name="x"></param>
	/// <param name="y"></param>
	/// <param name="coeff"></param>
	/// <returns>coefficient of determination</returns>
	fract coeff_of_determination(RationalMatrix x, RationalMatrix y, RationalMatrix coeff);

public:
	/// <summary>
	/// prints the whole matrix
	/// </summary>
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
	/// <summary>
	/// Adds two matrixes or patrs of them with set start positions and size.
	/// </summary>
	/// <param name="b">matrix to add</param>
	/// <param name="ia">starting row index in the first matrix ("this")</param>
	/// <param name="ja">starting column index in the first matrix ("this")</param>
	/// <param name="ib">starting row index in the second matrix ("b")</param>
	/// <param name="jb">starting column index in the second matrix ("b")</param>
	/// <param name="height">number of rows in the part of matrix which will be taken</param>
	/// <param name="width">number of colunms in the part of matrix which will be taken</param>
	/// <returns>sum of the two matrixes or an empty matrix, if sizes do not match</returns>
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
	/// <summary>
	/// Adds two matrixes.
	/// </summary>
	/// <param name="b">matrix to add</param>
	/// <returns>sum of the two matrixes or an empty matrix, if sizes do not match</returns>
	RationalMatrix sum(const RationalMatrix* b) {
		RationalMatrix res;
		if (this->cols != b->cols || this->rows != b->rows) return res;
		return this->sum(b, 0, 0, 0, 0, this->rows, this->cols);
	};

	// setting starting positions and final size manually
	/// <summary>
	/// Substracts two matrixes (this - b) or patrs of them with set start positions and size.
	/// </summary>
	/// <param name="b">matrix to substract</param>
	/// <param name="ia">starting row index in the first matrix ("this")</param>
	/// <param name="ja">starting column index in the first matrix ("this")</param>
	/// <param name="ib">starting row index in the second matrix ("b")</param>
	/// <param name="jb">starting column index in the second matrix ("b")</param>
	/// <param name="height">number of rows in the part of matrix which will be taken</param>
	/// <param name="width">number of colunms in the part of matrix which will be taken</param>
	/// <returns>this matrix minus parameter or an empty matrix, if sizes do not match</returns>
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
	/// <summary>
	/// Substracts two matrixes (this - b).
	/// </summary>
	/// <param name="b">matrix to substuct</param>
	/// <returns>this matrix minus parameter or an empty matrix, if sizes do not match</returns>
	RationalMatrix diff(const RationalMatrix* b) {
		RationalMatrix res;
		if (this->cols != b->cols || this->rows != b->rows) return res;
		return this->diff(b, 0, 0, 0, 0, this->rows, this->cols);
	}

	// setting starting positions and final size manually
	/// <summary>
	/// Multiplies two matrixes (this * b) or patrs of them with set start positions and size.
	/// </summary>
	/// <param name="b">matrix to multiply</param>
	/// <param name="ia">starting row index in the first matrix ("this")</param>
	/// <param name="ja">starting column index in the first matrix ("this")</param>
	/// <param name="ib">starting row index in the second matrix ("b")</param>
	/// <param name="jb">starting column index in the second matrix ("b")</param>
	/// <param name="heightRes">number of rows in resulting matrix</param>
	/// <param name="equalSide">numbers of columns in the first and rows in the second matrix</param>
	/// <param name="widthRes">number of columns in resulting matrix</param>
	/// <returns>this matrix multiplied on parameter or an empty matrix, if sizes do not match</returns>
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
	/// <summary>
	/// Multiplies two matrixes (this * b).
	/// </summary>
	/// <param name="b">matrix to multiply</param>
	/// <returns>this matrix multiplied on parameter or an empty matrix, if sizes do not match</returns>
	RationalMatrix mult(const RationalMatrix* b) {
		RationalMatrix res;
		if (this->cols != b->rows) return res;
		return this->mult(b, 0, 0, 0, 0, this->rows, this->cols, b->cols);
	}

	/// <summary>
	/// Calculating the determinant of the martix.
	/// </summary>
	/// <returns>The determinant as a fraction</returns>
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

	/// <summary>
	/// Checks if matrixes are equal.
	/// </summary>
	/// <param name="b">matrix to compare</param>
	/// <returns>true if matrixes are equal, false otherwise</returns>
	bool isEqual(const RationalMatrix* b) {
		if (this->cols != b->cols || this->rows != b->rows) return 0;

		for (int i = 0; i < this->rows; i++)
			for (int j = 0; j < this->cols; j++)
				if (!this->content[i][j].isEqual(b->content[i][j])) return 0;

		return 1;
	}

	/// <summary>
	/// Multiplies two matrixes (this * b) using Strassen's method.
	/// </summary>
	/// <param name="b">matrix to multiply</param>
	/// <returns>this matrix multiplied on parameter or an empty matrix, if sizes do not match</returns>
	RationalMatrix StrassensMult(RationalMatrix* b);

	/// <summary>
	/// Inverts matrix using minors method
	/// </summary>
	/// <returns>the inverted matrix</returns>
	RationalMatrix minorInverse();

	/// <summary>
	/// Inverts matrix using Gauss-Jordan elimination
	/// </summary>
	/// <returns>the inverted matrix or the same matrix as given, if it was not a square matrix</returns>
	RationalMatrix GaussianJordanInverse();

	// multiple linear regression
	/// <summary>
	/// Builds linear regression according to given labels.
	/// </summary>
	/// <param name="y">matrix of labels for regression</param>
	/// <returns>matrix of coefficients of built regression</returns>
	RationalMatrix get_mlr_coeff(RationalMatrix y);
	/// <summary>
	/// Prints the regression mobel from this matrix of coefficients.
	/// </summary>
	void print_regression_model();

	/// <summary>
	/// Constructing a matrix as an odject from vector of vectors of fractions.
	/// </summary>
	/// <param name="matrix">the actual content of the matrix</param>
	RationalMatrix(std::vector<std::vector<fract>> matrix = { {} }) {
		content = matrix;
		rows = matrix.size();
		cols = matrix[0].size();
	}
	// matrix with random rational values from -5 to 5
	/// <summary>
	/// Constructing a matrix as an odject, filled with pseudo random rational numbers from -5 to 5, with fixed size.
	/// </summary>
	/// <param name="height">height of the generated matrix</param>
	/// <param name="width">width of the generated matrix</param>
	/// <param name="seed">a seed for generating a content of the matrix</param>
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

	/// <summary>
	/// Gets number of rows in the matrix.
	/// </summary>
	/// <returns>Numbers of rows in the matrix</returns>
	int get_rows()
	{
		return this->rows;
	}
	/// <summary>
	/// Gets number of columns in the matrix.
	/// </summary>
	/// <returns>Numbers of columns in the matrix</returns>
	int get_cols()
	{
		return this->cols;
	}
	/// <summary>
	/// Gets element of the matrix in specific coordinates.
	/// </summary>
	/// <param name="i">numder of the row</param>
	/// <param name="j">number of the column</param>
	/// <returns></returns>
	fract get(int i, int j)
	{
		if (i < this->rows && j < this->cols)
			return this->content[i][j];
	}
};
