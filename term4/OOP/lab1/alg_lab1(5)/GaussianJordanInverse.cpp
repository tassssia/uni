#include "RationalMatrix.h"

RationalMatrix RationalMatrix::add_identity_matrix() {
	std::vector<std::vector<fract>> v;
	std::vector<fract> a = {};
	for (int i = 0; i < this->rows; i++) {
		for (int j = 0; j < 2 * this->cols; j++) {
			if (j < this->cols) a.push_back(this->content[i][j]);
			else {
				if (i == j - this->cols) a.push_back({ 1, 1 });
				else a.push_back({ 0, 1 });
			}
		}
		v.push_back(a);
		a.clear();
	}
	RationalMatrix res(v);
	return res;
}

bool RationalMatrix::gaussian_jordan() {
	for (int i = 0; i < this->rows; i++) {
		int n = i;
		while (this->content[n][i].isNull() && n < this->rows) n++;
		if (n == this->rows) return false;
		if (n != i) this->exchange(i, n);
		fract lead = this->content[i][i];
		for (int j = i; j < this->cols; j++)
		{
			fract curr = content[i][j].div(lead);
			content[i][j] = curr;
		}
		for (int m = 0; m < this->rows; m++) {
			if (m != i) {
				fract to_null = content[m][i];
				for (int k = i; k < this->cols; k++) {
					fract curr = content[m][k].diff(content[i][k].mult(to_null));
					content[m][k] = curr;
				}
			}
		}
	}
	return true;
}

RationalMatrix RationalMatrix::remove_identity_matrix() {
	std::vector<std::vector<fract>> v;
	std::vector<fract> a = {};
	for (int i = 0; i < this->rows; i++) {
		for (int j = this->cols / 2; j < this->cols; j++) {
			a.push_back(content[i][j]);
		}
		v.push_back(a);
		a.clear();
	}
	RationalMatrix res(v);
	return res;
}

void RationalMatrix::exchange(int row1, int row2) {
	for (int i = 0; i < this->cols; i++) {
		fract f1 = this->content[row1][i], f2 = this->content[row2][i];
		this->content[row1][i] = f2;
		this->content[row2][i] = f1;
	}
}

RationalMatrix RationalMatrix::GaussianJordanInverse() {
	RationalMatrix res;
	if (this->rows != this->cols || this->get_matrix_det().isNull()) {
		return res;
	}
	RationalMatrix curr = this->add_identity_matrix();
	bool success = curr.gaussian_jordan();
	if (success) res = curr.remove_identity_matrix();
	return res;
}