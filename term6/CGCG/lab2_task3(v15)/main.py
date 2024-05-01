import matplotlib.pyplot as plt
from point import Point
from region import Region
from twoDTree import TwoDTree


def read_points(filename):
    coords = open(filename).read().split()
    points = []
    for i in range(0, len(coords), 2):
        x = float(coords[i])
        y = float(coords[i + 1])
        points.append(Point(x, y))
    return points
def read_region(filename):
    info = open(filename).read().split()
    return Region(Point(float(info[0]), float(info[1])), float(info[2]), float(info[3]))
def plot_input(points, region):
    for p in points:
        plt.scatter(p.x, p.y, color="green")
    region.plot()
def show_res(res_points):
    print(f"There are {len(res_points)} points in region:")
    for p in res_points:
        print(f"({p.x}, {p.y})")
        plt.scatter(p.x, p.y, color="red")


points = read_points("data/points.txt")
region = read_region("data/region.txt")
plot_input(points, region)

tree = TwoDTree(points)
res = tree.search(region)
show_res(res)
plt.show()
