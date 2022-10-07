#pragma once
#include <cmath>
#include "float.h"
#include <iostream>

class Point;
class Line;
class Circle;


class Figure {
public:
	virtual Point* intersect(Line l) = 0;
	virtual Point* intersect(Circle c) = 0;
	virtual Figure* reflect(Line l) = 0;
	virtual Figure* invert(Circle c) = 0;
	virtual void output() = 0;
};

class Point :public Figure {
public:

	void output() override;

	Point* intersect(Line l) override;
	Point* intersect(Circle c) override;

	Point* reflect(Line l) override;
	Point* invert(Circle c) override;

	double x;
	double y;
	Point(Point* p) {
		this->x = p->x;
		this->y = p->y;
	}
	Point(double X = 0, double Y = 0) {
		x = X;
		y = Y;
	}
	Point(Point p1, Point p2) /*The middle of the segment P1P2*/ {
		x = (p1.x + p2.x) / 2;
		y = (p1.y + p2.y) / 2;
	}
};
class Line :public Figure {
public:
	void output() override;

	Point* intersect(Line l) override;
	Point* intersect(Circle c) override;

	Line* reflect(Line l) override;
	Figure* invert(Circle c) override;
	
	double a; double b; double c;
	Line(Line* l) {
		this->a = l->a;
		this->b = l->b;
		this->c = l->c;
	}
	Line(double xCoeff = 1, double yCoeff = 1, double free = 1) : Figure() {
		a = xCoeff;
		b = yCoeff;
		c = free;
	}
	Line(Point p1, Point p2) /*Via points P1 and P2*/ {
		a = p2.y - p1.y;
		b = p1.x - p2.x;
		c = p2.x * p1.y - p1.x * p2.y;
	}
	Line(Line l, Point p) /*Perpendicular to line l via point P*/ {
		a = l.a;
		b = -l.b;
		c = l.b * p.y - l.a * p.x;
	}
};
class Circle :public Figure {
public:
	void output() override;

	Point* intersect(Line l) override;
	Point* intersect(Circle c) override;

	Circle* reflect(Line l) override;
	Figure* invert(Circle c) override;

	Point center;
	double r;
	Circle(Circle* c) {
		this->center = Point(&(c->center));
		this->r = c->r;
	}
	Circle(Point c = Point(0, 0), double radius = 1.0) {
		center = c;
		r = radius;
	}
};
