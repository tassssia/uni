import numpy as np
np.seterr(all='raise')

def get_matrix(size, min, max):
    A = np.random.uniform(min, max, size=(size, size))

    for i in range(size):
        row_sum = np.sum(np.abs(A[i, :])) - np.abs(A[i, i])
        if row_sum > np.abs(A[i, i]):
            A[i, i] = row_sum

    return A
def get_hilbert(size):
    H = np.zeros((size, size), dtype=float)

    for i in range(1, size + 1):
        for j in range(1, size + 1):
            H[i-1, j-1] = 1 / (i + j)

    return H

def lu(A):
    n = len(A)
    L = np.eye(n)
    U = A.copy()
    P = np.eye(n)

    for i in range(n):
        max_index = np.argmax(np.abs(U[i:, i])) + i

        if max_index != i:
            U[[i, max_index]] = U[[max_index, i]]
            P[[i, max_index]] = P[[max_index, i]]
            if i > 0:
                L[[i, max_index], :i] = L[[max_index, i], :i]

        for j in range(i + 1, n):
            factor = U[j, i] / U[i, i]
            L[j, i] = factor
            U[j, i:] -= factor * U[i, i:]

    return L, U, P
def gauss_lu(A, b):
    L, U, P = lu(A)

    b = np.dot(P, b)
    n = len(b)
    y = np.zeros(n)
    x = np.zeros(n)

    #Ly = Pb
    for i in range(n):
        y[i] = b[i] - np.dot(L[i, :i], y[:i])

    #Ux = y
    for i in range(n - 1, -1, -1):
        x[i] = (y[i] - np.dot(U[i, i+1:], x[i+1:])) / U[i, i]

    return x
def jacobi(A, b, x0, eps):
    n = len(b)
    x = x0.copy()
    iter = 0

    while True:
        x1 = np.zeros_like(x)

        for i in range(n):
            s1 = np.dot(A[i, :i], x[:i])
            s2 = np.dot(A[i, i+1:], x[i+1:])
            x1[i] = (b[i] - s1 - s2) / A[i, i]
        iter += 1

        if np.linalg.norm(x1 - x) < eps:
            break
        x = x1.copy()

    return x1, iter
def seidel(A, b, x0, eps):
    n = len(b)
    x = x0.copy()
    iter = 0

    while True:
        for i in range(n):
            s1 = np.dot(A[i, :i], x[:i])
            s2 = np.dot(A[i, i+1:], x[i+1:])
            x[i] = (b[i] - s1 - s2) / A[i, i]

        iter += 1
        if np.linalg.norm(np.dot(A, x) - b) < eps:
            break

    return x, iter

def check(A, b, x, eps=0.001):
    return np.linalg.norm(np.dot(A, x) - b) <= eps

EPS = 0.0001
size = 4
bound = 5

A = get_matrix(size, -bound, bound)
#A = get_hilbert(size)
b = np.random.uniform(-bound, bound, size)
x0 = np.zeros(len(b), dtype=float)

gx = gauss_lu(A, b)
print("Gauss:", gx, check(A, b, gx))
jx, ji = jacobi(A, b, x0, EPS)
print("Jacobi (", ji, "):", jx, check(A, b, jx))
sx, si = seidel(A, b, x0, EPS)
print("Seidel (", si, "):", sx, check(A, b, sx))
