#pragma once
#include "RationalMatrix.h"

class Menu
{
	/// <summary>
	/// Prints ways of entering the matrix.
	/// </summary>
	/// <returns>entered matrix or an empty matrix, if there was an error</returns>
	RationalMatrix enterMatrix();
	/// <summary>
	/// Gets matrix, typed manually by a user.
	/// </summary>
	/// <returns>entered matrix or an empty matrix, if there was an error</returns>
	RationalMatrix enterManually();
	/// <summary>
	/// Uploads a matrix from a file.
	/// </summary>
	/// <returns>entered matrix or an empty matrix, if there was an error</returns>
	RationalMatrix upload();
	/// <summary>
	/// Constructing a matrix as an odject, filled with pseudo random rational numbers from -5 to 5, with fixed size.
	/// </summary>
	/// <returns>entered matrix</returns>
	RationalMatrix generateRandom();

	/// <summary>
	/// Saves matrix to a binary file.
	/// </summary>
	/// <param name="a">a matrix to be saved</param>
	/// <returns>1 if there have been an error, 0 otherwise</returns>
	int saveMatrix(RationalMatrix* a);

	/// <summary>
	/// Runs get_matrix_det() and prints a result.
	/// </summary>
	/// <param name="a">matrix to find a determinant</param>
	void det(RationalMatrix* a);
	/// <summary>
	/// Runs minorInverse() or GaussianJordanInverse() on users choice and prints a result.
	/// </summary>
	/// <param name="a">matrix to be inverted</param>
	void inverse(RationalMatrix* a);

	/// <summary>
	/// Runs isEqual(const RationalMatrix* b) and prints a result.
	/// </summary>
	/// <param name="a">matrix to ckeck for equality</param>
	/// <param name="b">matrix to ckeck for equality</param>
	void equalityCheck(RationalMatrix* a, RationalMatrix* b);
	/// <summary>
	/// Runs RationalMatrix sum(const RationalMatrix* b) and prints a result.
	/// </summary>
	/// <param name="a">matrix to add</param>
	/// <param name="b">matrix to add</param>
	void add(RationalMatrix* a, RationalMatrix* b);
	/// <summary>
	/// Runs RationalMatrix diff(const RationalMatrix* b) and prints a result.
	/// </summary>
	/// <param name="a">matrix to substrat from</param>
	/// <param name="b">matrix to substract</param>
	void substract(RationalMatrix* a, RationalMatrix* b);
	/// <summary>
	/// Runs RationalMatrix StrassensMult(RationalMatrix* b) and prints a result.
	/// </summary>
	/// <param name="a">the first matrix to multiply</param>
	/// <param name="b">the second matrix to multiply</param>
	void multiply(RationalMatrix* a, RationalMatrix* b);
	/// <summary>
	/// Runs get_mlr_coeff(RationalMatrix y) and prints a result.
	/// </summary>
	/// <param name="a"></param>
	/// <param name="b"></param>
	void linearRegression(RationalMatrix* a, RationalMatrix* b);

public:
	/// <summary>
	/// Prints menu and waits for user to choose an action.
	/// </summary>
	void printMenu();
};

