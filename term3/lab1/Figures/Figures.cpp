#include "Figures.h"
const double EPS = 0.0001;

// some basic formulas
double distance(Point p, Line l) {
	return abs(l.a * p.x + l.b * p.y + l.c) / sqrt(l.a * l.a + l.b * l.b);
}
double distance(Point p1, Point p2) {
	return sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
}
bool isPerp(Line l1, Line l2) {
	return ((abs(l1.a) < EPS && abs(l2.a) >= EPS) || 
			(abs(l1.b) < EPS && abs(l2.b) >= EPS) ||
			(abs(l1.a) >= EPS && abs(l2.a) < EPS) ||
			(abs(l1.b) >= EPS && abs(l2.b) < EPS) ||
			 abs(l1.a * l2.b + l2.a * l1.b) < EPS);
}
bool isIntersected(Line l1, Line l2) {
	return !((abs(l1.a) < EPS && abs(l2.a) < EPS) || (abs(l1.b) < EPS && abs(l2.b) < EPS) || abs(l1.a * l2.b - l2.a * l1.b) < EPS);
}
bool isIntersected(Line l, Circle c) {
	return !(c.r < distance(c.center, l));
}
bool isIntersected(Circle c1, Circle c2) {
	double d = distance(c1.center, c2.center);
	return !(d < abs(c1.r - c2.r) || d > c1.r + c2.r);
}

void Point::output() {
	std::cout << " (" << this->x << " ; " << this->y << ") ";
}
void Line::output() {
	std::cout << " " << this->a << "x + " << this->b << "y + " << this->c << " = 0 ";
}
void Circle::output() {
	std::cout << " [";
	this->center.output();
	std::cout << ", " << this->r << "] ";
}

Point* Point::intersect(Line l) {
	if (abs(l.a * this->x + l.b * this->y + l.c) < EPS) { 
		Point* res = new Point;
		*res = Point(this);
		return res;
	}
	Point* res = new Point;
	*res = Point(DBL_MAX, DBL_MAX);
	return res;
}
Point* Point::intersect(Circle c) {
	if (abs((this->x - c.center.x) * (this->x - c.center.x) + (this->y - c.center.y) * (this->y - c.center.y) - c.r * c.r) < EPS) { 
		Point* res = new Point;
		*res = Point(this);
		return res;
	}
	Point* res = new Point;
	*res = Point(DBL_MAX, DBL_MAX);
	return res;
}
Point* Line::intersect(Line l) {
	// parallel lines case
	if (!(isIntersected(*this, l))) {
		Point* res = new Point;
		*res = Point(DBL_MAX, DBL_MAX);
		return res;
	}
	// 0 coefficients cases
	if (abs(l.a) < EPS || abs(l.b) < EPS) return l.intersect(*this);
	if (abs(this->a) < EPS) {
		if (abs(l.b) < EPS) {
			Point* res = new Point;
			*res = Point(-l.c / l.a, -this->c / this->b);
			return res;
		}
		Point* res = new Point;
		*res = Point((l.b * this->c - this->b * l.c) / (l.a * this->b), -this->c / this->b);
		return res;
	}
	if (abs(this->b) < EPS) {
		Point* res = new Point;
		*res = Point(-this->c / this->b, (l.a * this->c - this->a * l.c) / (this->a * l.b));
		return res;
	}
	// all coefficients are non-0
	Point* res = new Point;
	res->x = (l.b * this->c - this->b * l.c) / (l.a * this->b - this->a * l.b);
	res->y = -(this->c + this->a * res->x) / this->b;

	return res;
}
Point* Line::intersect(Circle c) {
	double d = distance(c.center, *this);
	Point* res = new Point[2];
	if (d > c.r) {
		res[0] = res[1] = Point(DBL_MAX, DBL_MAX);
		return res; // there's no intersection
	}
	// middle point of the segment between intersection points
	res[0] = res[1] = *Line(*this, c.center).intersect(*this);

	// line is parallel to y = 0 or x = 0
	if (abs(this->a) < EPS) {
		res[0].x -= sqrt(c.r * c.r - d * d);
		res[1].x += sqrt(c.r * c.r - d * d);
		return res;
	}
	if (abs(this->b) < EPS) {
		res[0].y -= sqrt(c.r * c.r - d * d);
		res[1].y += sqrt(c.r * c.r - d * d);
		return res;
	}

	// parallelly moving the circle's center projection
	double deltaY = sqrt(this->a * this->a * (c.r * c.r - d * d) / (this->a * this->a + this->b * this->b));
	res[0].y += deltaY;
	res[0].x -= this->b * deltaY / this->a;
	res[1].y -= deltaY;
	res[1].x += this->b * deltaY / this->a;
	return res;
}
Point* Circle::intersect(Line l) {
	return l.intersect(*this);
}
Point* Circle::intersect(Circle c) {
	Line l; // is Line via intersection points

	l.a = 2 * (c.center.x - this->center.x);
	l.b = 2 * (c.center.y - this->center.y);
	l.c = (this->center.x - c.center.x) * (this->center.x + c.center.x) + 
		  (this->center.y - c.center.y) * (this->center.y + c.center.y) + 
		  (c.r - this->r) * (c.r + this->r);
	return l.intersect(*this);
}

Point* Point::reflect(Line l) {
	// projection of this point on l is the middle of segment between point and its image
	Point mid = *Line(l, *this).intersect(l);
	Point* res = new Point;
	*res = Point(2 * mid.x - this->x, 2 * mid.y - this->y);
	return res;
}
Line* Line::reflect(Line l) {
	// perpendicular reflects to itself
	if (isPerp(*this, l)) {
		Line* res = new Line;
		*res = Line(this);
		return res;
	}
	// reflecting parallel line
	if (!(isIntersected(l, *this))) {
		double k = (abs(l.a) < EPS) ? (this->b / l.b) : (this->a / l.a);
		Line* res = new Line;
		*res = Line(this->a, this->b, 2 * k * l.c - this->c);
		return res;
	}

	// building image via image of point from this line and the intersection point
	Point fromThis;
	Point intersection = *(this->intersect(l));
	if (abs(this->a) < EPS) fromThis = Point(intersection.x + 1, intersection.y);
	else fromThis = Point(intersection.x - this->b / this->a, intersection.y + 1);	

	Line* res = new Line;
	*res = Line(intersection, *fromThis.reflect(l));
	return res;
}
Circle* Circle::reflect(Line l) {
	// reflecting circle's center, radius is invariant
	std::cout << this->r;
	Circle* res = new Circle;
	*res = Circle(*this->center.reflect(l), this->r);
	return res;
}

Point* Point::invert(Circle c) {
	Point* res;
	if (abs(this->x - c.center.x) < EPS && abs(this->y - c.center.y) < EPS) {
		res = new Point;
		*res = Point(c.center.x, c.center.y);
		return res;
	}
	res = new Point;
	*res = Point(c.center.x +
				c.r * c.r * (this->x - c.center.x) /
				((this->x - c.center.x) * (this->x - c.center.x) + (this->y - c.center.y) * (this->y - c.center.y)),

				c.center.y +
				c.r * c.r * (this->y - c.center.y) /
				((this->x - c.center.x) * (this->x - c.center.x) + (this->y - c.center.y) * (this->y - c.center.y)));
	return res;
}
Figure* Line::invert(Circle c) {
	// if the line contains center of c it inverts to itself
	if (abs(this->a * c.center.x + this->b * c.center.y + this->c) < EPS) {
		Line* res = new Line;
		*res = Line(this);
		return res;
	}

	// otherwise line => circle containing center of c
	Point centerProjectionImg = *Line(*this, c.center).intersect(*this)->invert(c);
	Circle* res = new Circle;
	*res = Circle(Point(c.center, centerProjectionImg), distance(c.center, centerProjectionImg) / 2);
	return res;
}
Figure* Circle::invert(Circle c) {
	Line centerLine;
	// if circles are concentric taking any line (for instance parallel to x = 0 via centers)
	if (abs(c.center.x - this->center.x) < EPS && abs(c.center.y - this->center.y) < EPS)
		centerLine = Line(1, 0, -c.center.x);
	else centerLine = Line(this->center, c.center);

	centerLine.output();
	Point* inters = new Point[2];
	inters = centerLine.intersect(*this);

	// if one of the intersection points is center of c circle => line
	if (abs(inters[0].x - c.center.x) < EPS && abs(inters[0].y - c.center.y) < EPS) {
		Line* res = new Line;
		*res = Line(centerLine, *inters[1].invert(c));
		return res;
	}
	if (abs(inters[1].x - c.center.x) < EPS && abs(inters[1].y - c.center.y) < EPS) {
		Line* res = new Line;
		*res = Line(centerLine, *inters[0].invert(c));
		return res;
	}

	Point intersImg1 = *inters[0].invert(c);
	Point intersImg2 = *inters[1].invert(c);
	Circle* res = new Circle;
	*res = Circle(Point(intersImg1, intersImg2), distance(intersImg1, intersImg2) / 2);
	return res;
}
