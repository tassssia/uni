#pragma once
#include "RationalMatrix.h"

void RationalMatrix::extend(int newSize) {
	for (int i = 0; i < this->rows; i++)
		this->content[i].resize(newSize, fract(0, 1));
	this->cols = newSize;

	std::vector<fract> tmp (newSize, fract(0, 1));
	this->content.resize(newSize, tmp);
	this->rows = newSize;
}
RationalMatrix RationalMatrix::multMin(const RationalMatrix* b, int ia, int ja, int ib, int jb) {
	RationalMatrix res = RationalMatrix( {
		{(this->content[ia][ja].mult(b->content[ib][jb])).sum(this->content[ia][ja + 1].mult(b->content[ib + 1][jb])),
		 (this->content[ia][ja].mult(b->content[ib][jb + 1])).sum(this->content[ia][ja + 1].mult(b->content[ib + 1][jb + 1]))},
		{(this->content[ia + 1][ja].mult(b->content[ib][jb])).sum(this->content[ia + 1][ja + 1].mult(b->content[ib + 1][jb])),
		 (this->content[ia + 1][ja].mult(b->content[ib][jb + 1])).sum(this->content[ia + 1][ja + 1].mult(b->content[ib + 1][jb + 1]))} });
	return res;
}
RationalMatrix RationalMatrix::multRec(RationalMatrix* b, int ia, int ja, int ib, int jb, int size) {
	if (size == 2) return this->multMin(b, ia, ja, ib, jb);

	size /= 2;
	RationalMatrix s1 = b->diff(b, ib, jb + size, ib + size, jb + size, size, size);
	RationalMatrix s2 = this->sum(this, ia, ja, ia, ja + size, size, size);
	RationalMatrix s3 = this->sum(this, ia + size, ja, ia + size, ja + size, size, size);
	RationalMatrix s4 = b->diff(b, ib + size, jb, ib, jb, size, size);
	RationalMatrix s5 = this->sum(this, ia, ja, ia + size, ja + size, size, size);
	RationalMatrix s6 = b->sum(b, ib, jb, ib + size, jb + size, size, size);
	RationalMatrix s7 = this->diff(this, ia, ja + size, ia + size, ja + size, size, size);
	RationalMatrix s8 = b->sum(b, ib + size, jb, ib + size, jb + size, size, size);
	RationalMatrix s9 = this->diff(this, ia, ja, ia + size, ja, size, size);
	RationalMatrix s10 = b->sum(b, ib, jb, ib, jb + size, size, size);

	RationalMatrix p1 = this->multRec(&s1, ia, ja, 0, 0, size);
	RationalMatrix p2 = s2.multRec(b, 0, 0, ib + size, jb + size, size);
	RationalMatrix p3 = s3.multRec(b, 0, 0, ib, jb, size);
	RationalMatrix p4 = this->multRec(&s4, ia + size, ja + size, 0, 0, size);
	RationalMatrix p5 = s5.multRec(&s6, 0, 0, 0, 0, size);
	RationalMatrix p6 = s7.multRec(&s8, 0, 0, 0, 0, size);
	RationalMatrix p7 = s9.multRec(&s10, 0, 0, 0, 0, size);

	RationalMatrix c11 = (p5.sum(&p4)).sum(&(p6.diff(&p2)));
	RationalMatrix c12 = p1.sum(&p2);
	RationalMatrix c21 = p3.sum(&p4);
	RationalMatrix c22 = (p5.sum(&p1)).diff(&(p3.sum(&p7)));

	for (int i = 0; i < size; i++)
	{
		c11.content[i].insert(c11.content[i].end(), c12.content[i].begin(), c12.content[i].end());
		c21.content[i].insert(c21.content[i].end(), c22.content[i].begin(), c22.content[i].end());
	}
	c11.content.insert(c11.content.end(), c21.content.begin(), c21.content.end());
	c11.cols = 2 * size;
	c11.rows = 2 * size;
	return c11;
}
void RationalMatrix::shrink(int height, int width) {
	this->content.resize(height);
	this->rows = height;

	for (int i = 0; i < height; i++)
		this->content[i].resize(width);
	this->cols = width;
}

RationalMatrix RationalMatrix::StrassensMult(RationalMatrix b) {
	int rowA = this->rows;
	int rowB = b.rows;
	int colA = this->cols;
	int colB = b.cols;

	RationalMatrix res = RationalMatrix({ {} });
	if (colA != rowB) return res;
	
	int maxSize = std::max({rowA, rowB, colB});
	int newSize = 1;
	while (newSize < maxSize) newSize *= 2;

	this->extend(newSize);
	b.extend(newSize);

	RationalMatrix res = this->multRec(&b, 0, 0, 0, 0, newSize);
	this->shrink(rowA, colA);
	b.shrink(rowB, colB);
	res.shrink(rowA, colB);

	return res;
}