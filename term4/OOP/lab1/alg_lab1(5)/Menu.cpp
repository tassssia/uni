#include "Menu.h"
#include <vector>
#include <io.h>
#include <iostream>
using std::cout;
using std::cin;

RationalMatrix Menu::enterMatrix() {
	char c;
	RationalMatrix fromFile;
	cout << "Choose the way of entering: \n"
		<< "1 - manualy\n"
		<< "2 - upload from a file\n"
		<< "3 - generate\n"
		<< "0 - quit\n";
	cin >> c;
	switch (c) {
	case '1':
		return this->enterManually();
	case '2':
		fromFile = this->upload();
		if (fromFile.isEqual(&RationalMatrix({}))) {
			return this->enterMatrix();
		}
		else return fromFile;
	case '3':
		return this->generateRandom();
	default:
		this->printMenu();
		return RationalMatrix({});
	}
}

RationalMatrix Menu::enterManually() {
	int m, n, num, denom;
	char c;
	std::vector<std::vector<fract>> matrix = {};
	cout << "height: "; cin >> m;
	cout << "width: "; cin >> n;
	cout << "\n";
	for (int i = 0; i < m; i++)
	{
		matrix.push_back({});
		for (int j = 0; j < n; j++)
		{
			cin >> num >> c >> denom;
			matrix[i].push_back(fract(num, denom));
		}
	}
	return RationalMatrix(matrix);
}

RationalMatrix Menu::upload() {
	char name[100];

	cout << "enter file name: ";
	cin >> name;
	FILE* fp;
	fopen_s(&fp, name, "rb");
	if (!fp) {
		cout << "File not found\n";
		return RationalMatrix({});
	}

	int m, n;
	fract t;
	fread(&m, sizeof(int), 1, fp);
	fread(&n, sizeof(int), 1, fp);

	std::vector<std::vector<fract>> matrix = {};
	for (int i = 0; i < m; i++)
	{
		matrix.push_back({});
		for (int j = 0; j < n; j++)
		{
			fread(&t, sizeof(fract), 1, fp);
			matrix[i].push_back(t);
		}
	}
	return RationalMatrix(matrix);
}

RationalMatrix Menu::generateRandom() {
	int m, n, seed;
	char c;
	cout << "height: "; cin >> m;
	cout << "width: "; cin >> n;
	cout << "seed: "; cin >> seed;
	cout << "\n";
	return RationalMatrix(m, n, seed);
}



