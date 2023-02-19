#pragma once

#include <iostream>

class fract {
	long long num;
	long long denom;

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
		return res;
	}
	fract diff(fract a) {
		fract res = fract(this->num * a.denom - this->denom * a.num, this->denom * a.denom);
		return res;
	}
	fract mult(fract a) {
		fract res = fract(this->num * a.num, this->denom * a.denom);
		return res;
	}
	bool isEqual(fract a) {
		return (this->num == a.num && this->denom == a.denom);
	}

	fract(long long numerator = 0, long long denominator = 1) {
		if (denominator > 0) {
			num = numerator;
			denom = denominator;

			//simplification
			numerator = abs(numerator);
			while (numerator && denominator)
				numerator > denominator ? numerator = numerator % denominator : denominator = denominator % numerator;

			long long gcd = numerator + denominator;
			num = num / gcd;
			denom = denom / gcd;
		}
		else if (denominator < 0) {
			num = -1 * numerator;
			denom = abs(denominator);

			//simplification
			numerator = abs(numerator);
			denominator = abs(denominator);
			while (numerator && denominator)
				numerator > denominator ? numerator = numerator % denominator : denominator = denominator % numerator;

			long long gcd = numerator + denominator;
			num = num / gcd;
			denom = denom / gcd;
		}
	}
};
