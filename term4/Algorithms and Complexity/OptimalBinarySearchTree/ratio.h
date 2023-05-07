#pragma once

#include <iostream>

class ratio {
	int num;
	int denom;

public:
	void print() {
		std::cout.width(3);
		std::cout << this->num;
		std::cout << " /";
		std::cout.width(3);
		std::cout << this->denom;
	}
	ratio operator+(ratio a) {
		ratio res = ratio(this->num * a.denom + this->denom * a.num, this->denom * a.denom);
		return res;
	}
	ratio operator-(ratio a) {
		ratio res = ratio(this->num * a.denom - this->denom * a.num, this->denom * a.denom);
		return res;
	}
	ratio operator*(ratio a) {
		ratio res = ratio(this->num * a.num, this->denom * a.denom);
		return res;
	}
	ratio operator/(ratio a) {
		return *this * (ratio(a.denom, a.num));
	}
	bool operator==(ratio a) {
		return (this->num == a.num && this->denom == a.denom);
	}
	bool operator!=(ratio a) {
		return !(*this == a);
	}
	bool operator>(ratio a) {
		return (this->num * a.denom > a.num * this->denom);
	}
	bool operator<(ratio a) {
		return (this->num * a.denom < a.num * this->denom);
	}

	ratio(int numerator = 0, int denominator = 1) {
		if (denominator > 0) {
			num = numerator;
			denom = denominator;

			//simplification
			numerator = abs(numerator);
			while (numerator && denominator)
				numerator > denominator ? numerator = numerator % denominator : denominator = denominator % numerator;

			int gcd = numerator + denominator;
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

			int gcd = numerator + denominator;
			num = num / gcd;
			denom = denom / gcd;
		}
	}
};
