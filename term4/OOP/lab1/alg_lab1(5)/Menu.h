#pragma once
#include "RationalMatrix.h"

class Menu
{
	RationalMatrix enterMatrix();
	RationalMatrix enterManually();
	RationalMatrix upload();
	RationalMatrix generateRandom();

	int saveMatrix(RationalMatrix* a);

	void det(RationalMatrix* a);
	void inverse(RationalMatrix* a);

	void equalityCheck(RationalMatrix* a, RationalMatrix* b);
	void add(RationalMatrix* a, RationalMatrix* b);
	void substruct(RationalMatrix* a, RationalMatrix* b);
	void multiply(RationalMatrix* a, RationalMatrix* b);
	void linearRegression(RationalMatrix* a, RationalMatrix* b);

public:
	void printMenu();
};

