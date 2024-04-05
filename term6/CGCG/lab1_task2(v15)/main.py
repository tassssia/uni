
import math
from matplotlib import pyplot as plt
import bintrees

class Point:
    def __init__(self, x, y):
        self.x = float(x)
        self.y = float(y)
        self.income = 0
        self.outcome = 0

    def __repr__(self):
        return f"({self.x}; {self.y})"

class Edge:
    def __init__(self, start, end):
        self.start = start
        self.end = end
        self.weight = 0
        self.angle = math.atan2(end.y - start.y, end.x - start.x)

def form_vertices(vertices_file):
    vertices_coords = open(vertices_file).read().split()
    vertices = []
    for i in range(0, len(vertices_coords), 2):
        x = int(vertices_coords[i])
        y = int(vertices_coords[i + 1])
        vertices.append(Point(x, y))

    return sorted(vertices, key=lambda point: point.y)
"""
def regularize(vertices):
    reg_edges = []
    for i in range(1, len(vertices) - 1):
        if vertices[i].x != vertices[i - 1].x and vertices[i].x != vertices[i + 1].x:
            if (abs(vertices[i].x - vertices[i - 1].x) <
                    abs(vertices[i].x - vertices[i + 1].x)):
                closest = vertices[i - 1]
            else: closest = vertices[i + 1]

            reg_edges.append(Edge(vertices[i], closest))

    return reg_edges
"""
def inout_edges(vertices, edges):
    incoming = [[] for _ in vertices]
    outcoming = [[] for _ in vertices]
    for e in edges:
        start_id = vertices.index(e.start)
        end_id = vertices.index(e.end)
        outcoming[start_id].append(e)
        incoming[end_id].append(e)
        e.weight = 1
    return incoming, outcoming
def form_edges(edges_file, vertices):
    edges_read = open(edges_file).read().split()
    #edges = regularize(vertices)
    edges = []
    for i in range(0, len(edges_read), 2):
        start = vertices[int(edges_read[i])]
        end = vertices[int(edges_read[i + 1])]
        edges.append(Edge(start, end))
    return edges

def get_weight_sum(edges):
    return sum(e.weight for e in edges)
def update_v_inout(id, vertices, incoming, outcoming):
    vertices[id].income = get_weight_sum(incoming[id])
    vertices[id].outcome = get_weight_sum(outcoming[id])
def sort_edges(edges):
    return sorted(edges, key=lambda e: e.angle, reverse=True)
def is_imbalanced(vertices, incoming, outcoming):
    n = len(vertices)
    for i in range(n):
        update_v_inout(i, vertices, incoming, outcoming)
        if (i != 0 and i != n - 1
                and vertices[i].income != vertices[i].outcome):
            print(i)
            return True
    return False
def balancing(vertices, incoming, outcoming):
    n = len(vertices)
    # ascending
    for i in range(1, n - 2):
        update_v_inout(i, vertices, incoming, outcoming)
        outcoming[i] = sort_edges(outcoming[i])
        if vertices[i].income > vertices[i].outcome:
            outcoming[i][0].weight = vertices[i].income - vertices[i].outcome + 1
    # descending
    for i in range(n - 2, 1, -1):
        update_v_inout(i, vertices, incoming, outcoming)
        incoming[i] = sort_edges(incoming[i])
        if vertices[i].outcome > vertices[i].income:
            incoming[i][0].weight = vertices[i].outcome - vertices[i].income + incoming[i][0].weight
    if is_imbalanced(vertices, incoming, outcoming): print("Could NOT balance successfully")

def plot(vertices, edges, point):
    for i, v in enumerate(vertices):
        plt.plot(v.x, v.y, marker="o", markersize=5, color="black")
        plt.annotate(i, xy=(v.x, v.y), fontsize=14, color="purple", fontweight="bold")

    for e in edges:
        plt.plot([e.start.x, e.end.x], [e.start.y, e.end.y], color="black")
        plt.annotate(e.weight, xy=((e.end.x + e.start.x) / 2, (e.end.y + e.start.y) / 2), xytext=(10, -10), textcoords="offset points", color="green", fontweight="bold")

    plt.plot(point.x, point.y, marker="o", markersize=8, color="red")

    plt.show()

def leftest_free(edges):
    for e in edges:
        if e.weight > 0:
            return e
    return None
def single_chain(outcoming, vertices):
    chain = []
    curr_v_id = 0
    while curr_v_id < len(vertices) - 1:
        edge = leftest_free(outcoming[curr_v_id])
        edge.weight -= 1
        chain.append(edge)
        curr_v_id = vertices.index(edge.end)
    return chain
def form_chains(outcoming, vertices):
    chains = []
    for _ in range(get_weight_sum(outcoming[0])):
        chains.append(single_chain(outcoming, vertices))
    return chains
def print_chains(chains, vertices):
    print("")
    for i, c in enumerate(chains):
        print(f"C{i}: [{vertices.index(c[0].start)}", end="")
        for e in c:
            print(f", {vertices.index(e.end)}", end="")
        print("]")

def search(point, chains):
    bst = bintrees.AVLTree()
    for chain in chains:
        for edge in chain: bst.insert(edge.start.y, chain)
    for c_id in range(len(chains)):
        for e in chains[c_id]:
            if e.start.y <= point.y <= e.end.y:
                vec_start_to_point = Point(point.x - e.start.x, point.y - e.start.y)
                vec_start_to_end = Point(e.end.x - e.start.x, e.end.y - e.start.y)
                if (math.atan2(vec_start_to_point.y, vec_start_to_point.x) >= 
                    math.atan2(vec_start_to_end.y, vec_start_to_end.x)):
                    return f"{point} is between C{c_id - 1} and C{c_id}"
    return f"{point} is outside the graph"

point = Point(0, 0)
vertices = form_vertices("Data/vertices.txt")
edges = form_edges("Data/edges.txt", vertices)

incoming, outcoming = inout_edges(vertices, edges)
balancing(vertices, incoming, outcoming)
outcoming = [sort_edges(edges_from_v) for edges_from_v in outcoming]

plot(vertices, edges, point)

chains = form_chains(outcoming, vertices)
print_chains(chains, vertices)

print("\n"+search(point, chains))