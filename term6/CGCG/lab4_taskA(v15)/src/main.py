import matplotlib.pyplot as plt
from point import Point
from voronoi_diagram import VoronoiDiagram


def read_points(filename):
    points = []
    with open(filename, 'r') as file:
        for line in file:
            x, y = map(float, line.strip().split())
            points.append(Point(x, y))
    return points


def plot(points, segments):
    plt.figure(figsize=(10, 10))

    x_coords = [p.x for p in points]
    y_coords = [p.y for p in points]
    plt.scatter(x_coords, y_coords, color="red")
    for s in segments:
        plt.plot([s.start.x, s.end.x], [s.start.y, s.end.y], color="green")

    plt.xlim((0, 10))
    plt.ylim((0, 10))
    plt.show()


points = read_points("../data/points.txt")
vd = VoronoiDiagram(points)
vd.build()
segments = vd.res
plot(points, segments)
