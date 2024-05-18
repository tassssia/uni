from math import sqrt, inf
from matplotlib import pyplot as plt


class Point:
    def __init__(self, x, y):
        self.x = x
        self.y = y

    def distance(self, other):
        return sqrt((self.x - other.x) ** 2 + (self.y - other.y) ** 2)

    def __repr__(self):
        return f"({self.x}; {self.y})"


def read_points(filename):
    coords = open(filename).read().split()
    points = []
    for i in range(0, len(coords), 2):
        x = float(coords[i])
        y = float(coords[i + 1])
        points.append(Point(x, y))
    return points


def nearest_pair_naive(points):
    d = inf
    p1 = Point(None, None)
    p2 = Point(None, None)
    for i in range(len(points)):
        for j in range(len(points[i + 1:])):
            if d > points[i].distance(points[i + j + 1]):
                d = points[i].distance(points[i + j + 1])
                p1 = points[i]
                p2 = points[i + j + 1]

    return (p1, p2), d


def get_interval(part, mid_x, dist):
    interval = []
    for p in part:
        if abs(p.x - mid_x) <= dist:
            interval.append(p)
    interval = sorted(interval, key=lambda p: p.y)

    return interval


def nearest_pair(points):
    if len(points) == 1:
        return (None, None), inf
    if len(points) == 2:
        return (points[0], points[1]), points[0].distance(points[1])

    points = sorted(points, key=lambda p: p.x)
    mid_id = (len(points) - 1) // 2
    mid = points[mid_id]

    left, right = points[:mid_id], points[mid_id:]
    res = min([nearest_pair(left), nearest_pair(right)], key=lambda x: x[1])
    dist = res[1]

    interval_left = get_interval(left, mid.x, dist)
    interval_right = get_interval(right, mid.x, dist)

    start = 0
    for p in interval_left:
        i = start
        while (i < len(interval_right) - 1 and
               (p.y - interval_right[i].y) >= dist):
            i += 1

        start = i
        while (i < len(interval_right) and
               abs(p.y - interval_right[i].y) <= dist):
            d_i = p.distance(interval_right[i])
            if d_i < dist:
                res = ((p, interval_right[i]), d_i)
            i += 1

    return res


def demo(points):
    (p1, p2), dist = nearest_pair(points)

    plt.scatter([p.x for p in points], [p.y for p in points], color="red")
    plt.plot([p1.x, p2.x], [p1.y, p2.y], color="red")
    plt.gca().set_aspect("equal")
    plt.show()

    return (p1, p2), dist


points = read_points("points.txt")

(p1, p2), dist = demo(points)
print(f"{p1}, {p2}: {dist}")

(p1_1, p2_1), dist_1 = nearest_pair_naive(points)
print(f"Naive: {p1_1}, {p2_1}: {dist_1}")
