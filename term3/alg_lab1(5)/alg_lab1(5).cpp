#include "RationalMatrix.h"
#include <chrono>

int main()
{
	
	RationalMatrix a = RationalMatrix(64, 64, (unsigned)time(0));
	a.print();
	std::cout << std::endl;
	RationalMatrix b = RationalMatrix(64, 64, (unsigned)time(0) + 666);
	b.print();
	std::cout << std::endl;

	auto start = std::chrono::system_clock::now();
	RationalMatrix res1 = a.mult(&b);
	auto end = std::chrono::system_clock::now();
	std::chrono::duration<double> elapsed_seconds = end - start;

	res1.print();
	std::cout << "---elapsed time: " << elapsed_seconds.count() << " s" << std::endl << std::endl;

	start = std::chrono::system_clock::now();
	RationalMatrix res2 = a.StrassensMult(&b);
	end = std::chrono::system_clock::now();
	elapsed_seconds = end - start;

	res2.print();
	std::cout << "---elapsed time: " << elapsed_seconds.count() << " s" << std::endl << std::endl;

	std::cout << res1.isEqual(&res2) << std::endl;

	a.print();
	std::cout << std::endl;

	b.print();
	std::cout << std::endl;


	return 0;
}
