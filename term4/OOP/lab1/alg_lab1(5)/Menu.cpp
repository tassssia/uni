#include "Menu.h"
#include <vector>
#include <io.h>
#include <iostream>
using std::cout;
using std::cin;

RationalMatrix Menu::enterMatrix() {
	int c;
	RationalMatrix fromFile;
	cout << "choose the way of entering: \n"
		<< "1 - manualy\n"
		<< "2 - upload from a file\n"
		<< "3 - generate\n"
		<< "0 - quit\n";
	cin >> c;
	switch (c) {
	case 1:
		return this->enterManually();
	case 2:
		fromFile = this->upload();
		if (fromFile.isEqual(&RationalMatrix({}))) {
			return this->enterMatrix();
		}
		else return fromFile;
	case 3:
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
		fclose(fp);
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
	fclose(fp);
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

int Menu::saveMatrix(RationalMatrix* a) {
	char name[100];
	char c;
	cout << "enter file name: ";
	cin >> name;

	if (name == "") return 1;
	FILE* fp;
	fopen_s(&fp, name, "rb");
	bool unique = !((bool)fp);
	fclose(fp);

	if (!unique) {
		cout << "the name is not unique. rewrite previous? [y/n] ";
		cin >> c;
		if (c == 'y') {
			remove(name);
		}
		else return this->saveMatrix(a);
	}

	fopen_s(&fp, name, "wb");
	int m = a->get_cols(), n = a->get_rows();
	fract t;

	fwrite(&m, sizeof(int), 1, fp);
	fwrite(&n, sizeof(int), 1, fp);
	for (int i = 0; i < m; i++)
	{
		for (int j = 0; j < n; j++)
		{
			t = a->get(i, j);
			fwrite(&t, sizeof(fract), 1, fp);
		}
	}

	fclose(fp);
	return 0;
}

void Menu::printMenu() {
	int c = 4;
	while (c != 0)
	{
		system("cls");
		cout << "enter a rational matrix\n";
		RationalMatrix a = this->enterMatrix();
		RationalMatrix b;
		cout << "choose an action\n"
			<< "1 - calculate a determinant\n"
			<< "2 - calculate inverse\n"
			<< "3 - enter another one\n"
			<< "0 - quit";
		cin >> c;
		switch (c) {
		case 1:
			this->det(&a);
			break;
		case 2:
			this->inverse(&a);
			break;
		case 3:
			b = this->enterMatrix();
			cout << "choose an action\n"
				<< "1 - check for equality\n"
				<< "2 - calculate sum\n"
				<< "3 - calculate difference\n"
				<< "4 - multiply\n"
				<< "5 - build multiple linear regression\n"
				<< "0 - quit";
			cin >> c;
			switch (c) {
			case 1:
				this->equalityCheck(&a, &b);
				break;
			case 2:
				this->add(&a, &b);
				break;
			case 3:
				this->substruct(&a, &b);
				break;
			case 4:
				this->multiply(&a, &b);
				break;
			case 5:
				this->linearRegression(&a, &b);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}

	}
}

void Menu::det(RationalMatrix* a) {
	char c;
	a->get_matrix_det().print();
	cout << "enter any character to quit";
	cin >> c;
}
void Menu::inverse(RationalMatrix* a) {
	char c;
	RationalMatrix res;
	cout << "choose a way of inversion: \n"
		<< "1 - minors' method\n"
		<< "2 - Gaussian-Jordan Elimination";
	switch (c) {
	case '1': 
		res = a->minorInverse();
		res.print();
		
		cout << "save it? [y/n]\n";
		cin >> c;
		if (c == 'y') this->saveMatrix(&res);

		break;
	case '2':
		res = a->GaussianJordanInverse();
		res.print();

		cout << "save it? [y/n]\n";
		cin >> c;
		if (c == 'y') this->saveMatrix(&res);

		break;
	default:
		break;
	}
}

void Menu::equalityCheck(RationalMatrix* a, RationalMatrix* b) {
	bool res = a->isEqual(b);
	if (res) cout << "this matrixes are equal";
	else cout << "this matrixes are not equal";
	cout << "enter any character to quit";
	char c;
	cin >> c;
}
void Menu::add(RationalMatrix* a, RationalMatrix* b) {
	RationalMatrix res = a->sum(b);
	res.print();

	char c;
	cout << "save it? [y/n]\n";
	cin >> c;
	if (c == 'y') this->saveMatrix(&res);
}
void Menu::substruct(RationalMatrix* a, RationalMatrix* b) {
	RationalMatrix res = a->diff(b);
	res.print();

	char c;
	cout << "save it? [y/n]\n";
	cin >> c;
	if (c == 'y') this->saveMatrix(&res);

}
void Menu::multiply(RationalMatrix* a, RationalMatrix* b) {
	RationalMatrix res = a->StrassensMult(b);
	res.print();

	char c;
	cout << "save it? [y/n]\n";
	cin >> c;
	if (c == 'y') this->saveMatrix(&res);
}
void Menu::linearRegression(RationalMatrix* a, RationalMatrix* b) {
	RationalMatrix res = a->get_mlr_coeff(*b);
	res.print_regression_model();

	char c;
	cout << "save it? [y/n]\n";
	cin >> c;
	if (c == 'y') this->saveMatrix(&res);
}
