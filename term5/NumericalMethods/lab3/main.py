import numpy as np

def read_adjacency_matrix(path):
    with open(path, 'r') as file:
        lines = file.readlines()
    matrix = []
    for line in lines:
        row = [float(val) for val in line.strip().split()]
        matrix.append(row)
    return np.array(matrix)

def power_iteration(matrix, max_iter = 1000, eps = 1e-6):
    n = len(matrix)
    x = np.ones(n) / n
    eigen = 0

    for iteration in range(max_iter):
        x1 = np.dot(matrix, x)
        x = x1 / np.linalg.norm(x1)
        eigen = x1[0] / x[0]
        
        if np.linalg.norm(x1 - x) < eps:
            return eigen, x


    return eigen, x


adjacency_matrix = read_adjacency_matrix("graph.txt")
transition_matrix = adjacency_matrix / np.sum(adjacency_matrix, axis=0)

print(transition_matrix, '\n')

eigen, ranks = power_iteration(transition_matrix)
print(eigen)
print(ranks)
