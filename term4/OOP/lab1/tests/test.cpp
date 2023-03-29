#include "pch.h"
#include "../alg_lab1(5)/RationalMatrix.h"

TEST(sum, correctSize) {
	RationalMatrix a = RationalMatrix({ {fract(3, 1), fract(9, 8), fract(10, 4)},
										{fract(5, 2), fract(-5, 1), fract(5, 2)},
										{fract(-10, 6), fract(5, 7), fract(8, 3)},
										{fract(-8, 1), fract(7, 9), fract(6, 5)} });
	RationalMatrix b = RationalMatrix({ {fract(7, 1), fract(3, 4), fract(2, 10)},
										{fract(8, 10), fract(-2, 3), fract(3, 10)},
										{fract(3, 6), fract(-5, 7), fract(10, 3)},
										{fract(-8, 7), fract(7, 9), fract(1, 5)} });
	RationalMatrix res = RationalMatrix({ {fract(10, 1), fract(15, 8), fract(27, 10)},
										  {fract(33, 10), fract(-17, 3), fract(14, 5)},
										  {fract(-7, 6), fract(0, 1), fract(6, 1)},
										  {fract(-64, 7), fract(14, 9), fract(7, 5)} });


	EXPECT_TRUE(a.sum(&b).isEqual(&res));
}

TEST(sum, wrongSize) {
	RationalMatrix a = RationalMatrix({ {fract(3, 1), fract(9, 8), fract(10, 4)},
										{fract(5, 2), fract(-5, 1), fract(5, 2)},
										{fract(-10, 6), fract(5, 7), fract(8, 3)},
										{fract(-8, 1), fract(7, 9), fract(6, 5)} });
	RationalMatrix b = RationalMatrix({ {fract(1, 10), fract(-3, 2)},
										{fract(-8, 5), fract(6, 10)} });
	RationalMatrix res;


	EXPECT_TRUE(a.sum(&b).isEqual(&res));
}

TEST(diff, correctSize) {
	RationalMatrix a = RationalMatrix({ {fract(3, 1), fract(9, 8), fract(10, 4)},
										{fract(5, 2), fract(-5, 1), fract(5, 2)},
										{fract(-10, 6), fract(5, 7), fract(8, 3)},
										{fract(-8, 1), fract(7, 9), fract(6, 5)} });
	RationalMatrix b = RationalMatrix({ {fract(7, 1), fract(3, 4), fract(2, 10)},
										{fract(8, 10), fract(-2, 3), fract(3, 10)},
										{fract(3, 6), fract(-5, 7), fract(10, 3)},
										{fract(-8, 7), fract(7, 9), fract(1, 5)} });
	RationalMatrix res = RationalMatrix({ {fract(-4, 1), fract(3, 8), fract(23, 10)},
										  {fract(17, 10), fract(-13, 3), fract(11, 5)},
										  {fract(-13, 6), fract(10, 7), fract(-2, 3)},
										  {fract(-48, 7), fract(0, 1), fract(1, 1)} });


	EXPECT_TRUE(a.diff(&b).isEqual(&res));
}

TEST(diff, wrongSize) {
	RationalMatrix a = RationalMatrix({ {fract(3, 1), fract(9, 8), fract(10, 4)},
										{fract(5, 2), fract(-5, 1), fract(5, 2)},
										{fract(-10, 6), fract(5, 7), fract(8, 3)},
										{fract(-8, 1), fract(7, 9), fract(6, 5)} });
	RationalMatrix b = RationalMatrix({ {fract(1, 10), fract(-3, 2)},
										{fract(-8, 5), fract(6, 10)} });
	RationalMatrix res;


	EXPECT_TRUE(a.diff(&b).isEqual(&res));
}

TEST(mult, correctSize) {
	RationalMatrix a = RationalMatrix({ {fract(3, 1), fract(9, 8), fract(10, 4)},
										{fract(5, 2), fract(-5, 1), fract(5, 2)},
										{fract(-10, 6), fract(5, 7), fract(8, 3)},
										{fract(-8, 1), fract(7, 9), fract(6, 5)} });
	RationalMatrix b = RationalMatrix({ {fract(1, 10), fract(-3, 2)},
										{fract(-8, 5), fract(6, 10)},
										{fract(5, 6), fract(5, 7)} });
	RationalMatrix res = RationalMatrix({ {fract(7, 12), fract(-571, 280)},
										  {fract(31, 3), fract(-139, 28)},
										  {fract(115, 126), fract(29, 6)},
										  {fract(-47, 45), fract(1399, 105)} });


	EXPECT_TRUE(a.mult(&b).isEqual(&res));
}

TEST(mult, wrongSize) {
	RationalMatrix a = RationalMatrix({ {fract(3, 1), fract(9, 8), fract(10, 4)},
										{fract(5, 2), fract(-5, 1), fract(5, 2)},
										{fract(-10, 6), fract(5, 7), fract(8, 3)},
										{fract(-8, 1), fract(7, 9), fract(6, 5)} });
	RationalMatrix b = RationalMatrix({ {fract(1, 10), fract(-3, 2)},
										{fract(-8, 5), fract(6, 10)} });
	RationalMatrix res;


	EXPECT_TRUE(a.mult(&b).isEqual(&res));
}

TEST(StrassensMult, correctSize) {
	RationalMatrix a = RationalMatrix(8, 10, (unsigned)time(0));
	RationalMatrix b = RationalMatrix(10, 4, (unsigned)time(0) + 666);
	RationalMatrix res = a.mult(&b);

	EXPECT_TRUE(a.StrassensMult(&b).isEqual(&res));
}

TEST(StrassensMult, wrongSize) {
	RationalMatrix a = RationalMatrix(10, 6, (unsigned)time(0));
	RationalMatrix b = RationalMatrix(10, 4, (unsigned)time(0) + 666);
	RationalMatrix res = a.mult(&b);

	EXPECT_TRUE(a.StrassensMult(&b).isEqual(&res));
}

