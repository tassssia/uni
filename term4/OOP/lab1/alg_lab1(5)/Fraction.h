#pragma once

#include <iostream>

/// <summary>
/// Class of rational numbers
/// 
/// <param name="num">numerator</param>
/// <param name="denom">denominator</param>
/// </summary>
class fract {
	long long num;
	long long denom;

public:
	/// <summary>
	/// Prints fraction as "??? /???"
	/// </summary>
	void print() {
		std::cout.width(3);
		std::cout << this->num;
		std::cout << " /";
		std::cout.width(3);
		std::cout << this->denom;
	}
	/// <summary>
	/// Adds two fractions
	/// </summary>
	/// <param name="a">fraction to add to this</param>
	/// <returns>Sum of the given fractions</returns>
	fract sum(fract a) {
		fract res = fract(this->num * a.denom + this->denom * a.num, this->denom * a.denom);
		return res;
	}
	/// <summary>
	/// Substructs two fractions
	/// </summary>
	/// <param name="a">fraction to substruct from this</param>
	/// <returns>this fraction minus parameter</returns>
	fract diff(fract a) {
		fract res = fract(this->num * a.denom - this->denom * a.num, this->denom * a.denom);
		return res;
	}
	/// <summary>
	/// Multiplies two fractions
	/// </summary>
	/// <param name="a">fraction to multiply</param>
	/// <returns>product of the given fractions</returns>
	fract mult(fract a) {
		fract res = fract(this->num * a.num, this->denom * a.denom);
		return res;
	}
	/// <summary>
	/// Divides two fractions
	/// </summary>
	/// <param name="a">fraction to be a diveder</param>
	/// <returns>division of the given fractions (this / parametr)</returns>
	fract div(fract a) {
		return this->mult(fract(a.denom, a.num));
	}
	/// <summary>
	/// Check if the fractions are equal
	/// </summary>
	/// <param name="a">fraction to compare</param>
	/// <returns>true if the fractions are equal, false otherwise</returns>
	bool isEqual(fract a) {
		return (this->num == a.num && this->denom == a.denom);
	}
	/// <summary>
	/// Check if the fraction is equal to 0
	/// </summary>
	/// <returns>true if the fraction is, false otherwise</returns>
	bool isNull() {
		if (this->num == 0) return 1;
		else return 0;
	}

	/// <summary>
	/// Constructs a fraction with given numerator and denominator (0/1 by default)
	/// </summary>
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
