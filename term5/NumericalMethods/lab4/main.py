import numpy as np

def f(x):
    return np.array([
        x[0]**2 - 2*x[0]*x[1] + 1,
        x[0]**2 + x[1]**2 - 2
    ])
def df(x):
    return np.array([
        [2*x[0] - 2*x[1],   -2*x[0]],
        [2*x[0],            2*x[1]]
    ])

def f_n_dim(x):
    n = len(x)
    res = np.full(n, np.sum(x**2) - n)
    for i in range(n):
        res[i] -= x[i]**2 - x[i]**3

    return res
def df_n_dim(x):
    n = len(x)
    res = np.tile(2*x, (n, 1))
    for i in range(n):
        res[i][i] *= 1.5*x[i]

    return res


def newton(f, df, x0, eps=0.001, max_iter=10000):
    x = x0.copy()

    for iter in range(max_iter):
        F = f(x)
        J = df(x)
        x += np.linalg.solve(J, -F)

        if np.linalg.norm(F) < eps:
            return x, iter

    return x, iter

def relaxation(f, df, x0, eps=0.001, max_iter=10000):
    x = x0.copy()

    for iter in range(max_iter):
        tau = 2 / np.linalg.norm(df(x), ord=np.inf) - eps
        x1 = x - tau * f(x)

        if np.linalg.norm(x1 - x) < eps:
            return x1, iter

        x = x1

    return x, iter


coeff = 0.8
x0 = [coeff, coeff]
x0_n = np.full(8, coeff)

result, iter = newton(f, df, x0)
print("Newton (",  iter, "):", result)

result, iter = newton(f_n_dim, df_n_dim, x0_n)
print("Newton (",  iter, "):", result)

result, iter = relaxation(f, df, x0)
print("Relaxation (",  iter, "):", result)

result, iter = relaxation(f_n_dim, df_n_dim, x0_n)
print("Relaxation (",  iter, "):", result)
