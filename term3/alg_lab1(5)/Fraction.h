#pragma once

#include <iostream>

class fract {
	int num;
	int denom;

	void simplify() {
		int a = abs(this->num);
		int b = this->num;
		while (a && b)
			a > b ? a = a % b : b = b % a;

		int gcd = a + b;
		this->num = this->num / gcd;
		this->denom = this->denom / gcd;
	}
public:
	void print() {
		std::cout.width(3);
		std::cout << this->num;
		std::cout << " /";
		std::cout.width(3);
		std::cout << this->denom;
	}
	fract sum(fract a) {
		fract res = fract(this->num * a.denom + this->denom * a.num, this->denom * a.denom);
		res.simplify();
		return res;
	}
	fract diff(fract a) {
		fract res = fract(this->num * a.denom - this->denom * a.num, this->denom * a.denom);
		res.simplify();
		return res;
	}
	fract mult(fract a) {
		fract res = fract(this->num * a.num, this->denom * a.denom);
		res.simplify();
		return res;
	}


	fract(int numerator = 0, int denominator = 1) {
		if (denominator > 0) {
			num = numerator;
			denom = denominator;
			this->simplify();
		}
		else if (denominator < 0) {
			num = -1 * numerator;
			denom = abs(denominator);
			this->simplify();
		}
	}
};