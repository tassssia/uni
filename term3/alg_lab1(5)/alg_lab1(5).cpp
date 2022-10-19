#include <iostream>
#include <vector>
using namespace std;

pair<int, int> typedef fract;
vector<vector<fract>> typedef matrix;

int GCD(int a, int b)
{
	while (a && b)
		a > b ? a = a % b : b = b % a;

	return (a + b);
}
fract simplify(fract a) {
	int gcd = GCD(abs(a.first), a.second);
	a.first = a.first / gcd;
	a.second = a.second / gcd;
	return a;
}
fract sum(fract a, fract b) {	
	return simplify( { a.first * b.second + a.second * b.first ,	a.second * b.second } );
}
fract diff(fract a, fract b) {
	return simplify( { a.first * b.second - a.second * b.first ,	a.second * b.second } );
}
fract mult(fract a, fract b) {
	return simplify( { a.first * b.first ,	a.second * b.second } );
}

matrix mSum(const matrix* a, int ia, int ja, const matrix* b, int ib, int jb, int size) {
	matrix res;
	vector<fract> tmp;

	for (int i = 0; i < size; i++)
	{
		for (int j = 0; j < size; j++)
		{
			tmp.push_back(sum((*a)[ia + i][ja + j], (*b)[ib + i][jb + j]));
		}
		res.push_back(tmp);
		tmp = {};
	}

	return res;
}
matrix mDiff(const matrix* a, int ia, int ja, const matrix* b, int ib, int jb, int size) {
	matrix res;
	vector<fract> tmp;

	for (int i = 0; i < size; i++)
	{
		for (int j = 0; j < size; j++)
		{
			tmp.push_back(diff((*a)[ia + i][ja + j], (*b)[ib + i][jb + j])); // diff of rational
		}
		res.push_back(tmp);
		tmp = {};
	}

	return res;
}
void mPrint(const matrix* a) {
	int m = (*a).size();
	int n = (*a)[0].size();

	for (int i = 0; i < m; i++)
	{
		for (int j = 0; j < n; j++){
			cout.width(3);
			cout << (*a)[i][j].first;
			cout << " /";
			cout.width(3);
			cout << (*a)[i][j].second << "   ";
		}
		cout << endl;
	}
}

// A[m*n], B[n*k] -> g = max(k, m, n)
void extend(matrix* a, int g) {
	int h = 1;
	while (h < g) h *= 2;

	int toRow = h - (*a)[0].size();
	int toColumn = h - (*a).size();
	if (toRow) {
		for (int i = 0; i < (*a).size(); i++)
			(*a)[i].insert((*a)[i].end(), toRow, { 0, 1 });
	}
	if (toColumn) {
		vector<fract> tmp(h, { 0, 1 });
		(*a).insert((*a).end(), toColumn, tmp);
	}
}
matrix multMin(const matrix* a, int ia, int ja, const matrix* b, int ib, int jb) {
	return { {sum(mult((*a)[ia][ja], (*b)[ib][jb]),		mult((*a)[ia][ja + 1], (*b)[ib + 1][jb])),
			  sum(mult((*a)[ia][ja], (*b)[ib][jb + 1]),		mult((*a)[ia][ja + 1], (*b)[ib + 1][jb + 1])) },
			 {sum(mult((*a)[ia + 1][ja], (*b)[ib][jb]),		mult((*a)[ia + 1][ja + 1], (*b)[ib + 1][jb])),
			  sum(mult((*a)[ia + 1][ja], (*b)[ib][jb + 1]),		mult((*a)[ia + 1][ja + 1], (*b)[ib + 1][jb + 1])) } };
}
matrix multRec(const matrix* a, int ia, int ja, const matrix* b, int ib, int jb, int size) {
	if (size == 2) return multMin(a, ia, ja, b, ib, jb);

	size /= 2;
	matrix s1 = mDiff(b, ib, jb + size, b, ib + size, jb + size, size);
	matrix s2 = mSum(a, ia, ja, a, ia, ja + size, size);
	matrix s3 = mSum(a, ia + size, ja, a, ia + size, ja + size, size);
	matrix s4 = mDiff(b, ib + size, jb, b, ib, jb, size);
	matrix s5 = mSum(a, ia, ja, a, ia + size, ja + size, size);
	matrix s6 = mSum(b, ib, jb, b, ib + size, jb + size, size);
	matrix s7 = mDiff(a, ia, ja + size, a, ia + size, ja + size, size);
	matrix s8 = mSum(b, ib + size, jb, b, ib + size, jb + size, size);
	matrix s9 = mDiff(a, ia, ja, a, ia + size, ja, size);
	matrix s10 = mSum(b, ib, jb, b, ib, jb + size, size);

	matrix p1 = multRec(a, ia, ja, &s1, 0, 0, size);
	matrix p2 = multRec(&s2, 0, 0, b, ib + size, jb + size, size);
	matrix p3 = multRec(&s3, 0, 0, b, ib, jb, size);
	matrix p4 = multRec(a, ia + size, ja + size, &s4, 0, 0, size);
	matrix p5 = multRec(&s5, 0, 0, &s6, 0, 0, size);
	matrix p6 = multRec(&s7, 0, 0, &s8, 0, 0, size);
	matrix p7 = multRec(&s9, 0, 0, &s10, 0, 0, size);

	matrix c11 = mSum(&mSum(&p5, 0, 0, &p4, 0, 0, size), 0, 0, &mDiff(&p6, 0, 0, &p2, 0, 0, size), 0, 0, size);
	matrix c12 = mSum(&p1, 0, 0, &p2, 0, 0, size);
	matrix c21 = mSum(&p3, 0, 0, &p4, 0, 0, size);
	matrix c22 = mDiff(&mSum(&p5, 0, 0, &p1, 0, 0, size), 0, 0, &mSum(&p3, 0, 0, &p7, 0, 0, size), 0, 0, size);

	for (int i = 0; i < size; i++)
	{
		c11[i].insert(c11[i].end(), c12[i].begin(), c12[i].end());
		c21[i].insert(c21[i].end(), c22[i].begin(), c22[i].end());
	}
	c11.insert(c11.end(), c21.begin(), c21.end());

	return c11;
}
void shrink(matrix* a, int row, int col) {
	a->resize(row);
	for (int i = a->size(); i >= row; i--)
		(*a)[i].resize(col);
}

matrix StrassensMult(matrix a, matrix b) {
	int rowA = a.size();
	int rowB = b.size();
	int colA = a[0].size();
	int colB = b[0].size();

	if (colA != rowB) return { {{}} };
	int forSize = max(rowA, max(rowB, colB));
	extend(&a, forSize);
	extend(&b, forSize);

	matrix res = multRec(&a, 0, 0, &b, 0, 0, a.size());
	shrink(&res, rowA, colB);

	return res;
}

int main()
{

	return 0;
}
