#pragma once
#include "RationalMatrix.h"

class Menu
{
	RationalMatrix enterMatrix();
	RationalMatrix enterManually();
	RationalMatrix upload();
	RationalMatrix generateRandom();

	int saveMatrix(RationalMatrix* a);
public:
	void printMenu();
	
	void det();
	void inverse();

	void equalityCheck();
	void add();
	void substruct();
	void multiply();
	void linearRegression();
};

