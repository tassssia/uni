import math

import numpy as np
import matplotlib.pyplot as plt

def f1(x):
    return 2 * x**2 - x + 1

def f2(x):
    return 2 * math.sin(3 * x)

def f3(x):
    return x**2 - 3 * math.sin(3 * x)

def get_points(f, start, end, num):
    x_vec = np.linspace(start, end, num)
    y_vec = [f(curr) for curr in x_vec]
    return x_vec, y_vec

def lagrange(x_vec, y_vec, x):
    result = 0.0
    n = len(x_vec)

    for i in range(n):
        term = y_vec[i]
        for j in range(n):
            if j != i:
                term *= (x - x_vec[j]) / (x_vec[i] - x_vec[j])
        result += term

    return result

def newton(x_vec, y_vec, x):
    n = len(x_vec)
    result = y_vec[0]

    div_diff = div_diff_table(x_vec, y_vec)
    for i in range(1, n):
        term = 1
        for j in range(i):
            term *= (x - x_vec[j])
        result += term * div_diff[i]
        #result += term * div_diff(x_vec[:i + 1], y_vec[:i + 1])

    return result
def div_diff(x_vec, y_vec):
    n = len(x_vec)
    if n == 1:
        return y_vec[0]
    else:
        return (div_diff(x_vec[1:], y_vec[1:]) - div_diff(x_vec[:-1], y_vec[:-1])) / (x_vec[-1] - x_vec[0])
def div_diff_table(x_vec, y_vec):
    n = len(x_vec)
    table = [y_vec.copy()]

    for i in range(1, n):
        row = []
        for j in range(n - i):
            diff = (table[i - 1][j + 1] - table[i - 1][j]) / (x_vec[j + i] - x_vec[j])
            row.append(diff)
        table.append(row)

    return [row[0] for row in table]

def cubic_spline(x_vec, y_vec, x_interpolation):
    n = len(x_vec)
    coeffs = get_spline_coefficients(x_vec, y_vec)

    y_interpolation = []
    for i in range(n - 1):
        indices = np.where((x_vec[i] <= x_interpolation) & (x_interpolation <= x_vec[i + 1]))[0]
        x_interval = x_interpolation[indices]

        y_interval = (
                coeffs[i][0]
                + coeffs[i][1] * (x_interval - x_vec[i])
                + coeffs[i][2] * (x_interval - x_vec[i])**2
                + coeffs[i][3] * (x_interval - x_vec[i])**3
        )
        y_interpolation.extend(y_interval)

    return np.array(y_interpolation)
def get_spline_coefficients(x_vec, y_vec):
    n = len(x_vec)
    h = np.diff(x_vec)
    b = np.zeros(n)
    u = np.zeros(n)
    v = np.zeros(n)

    for i in range(1, n - 1):
        b[i] = 6 * ((y_vec[i + 1] - y_vec[i]) / h[i] - (y_vec[i] - y_vec[i - 1]) / h[i - 1])

    for i in range(1, n - 1):
        t = h[i] / (h[i - 1] + h[i])
        u[i] = 2 * (t * u[i - 1] + 2)
        v[i] = t * (b[i] - v[i - 1])

    z = np.zeros(n)
    for i in range(n - 2, 0, -1):
        z[i] = (v[i] - h[i] * z[i + 1]) / u[i]

    coeffs = []
    for i in range(0, n - 1):
        a = y_vec[i]
        b = (y_vec[i + 1] - y_vec[i]) / h[i] - h[i] * (z[i + 1] + 2 * z[i]) / 6
        c = z[i] / 2
        d = (z[i + 1] - z[i]) / (6 * h[i])

        coeffs.append((a, b, c, d))

    return coeffs


def plot(x_vec, y_vec, function):
    x_for_plot = np.linspace(x_vec[0], x_vec[len(x_vec)-1], 1010)

    y_lagrange = [lagrange(x_vec, y_vec, x) for x in x_for_plot]
    y_newton = [newton(x_vec, y_vec, x) for x in x_for_plot]
    y_cubic_spline = cubic_spline(x_vec, y_vec, x_for_plot)
    y_exact = [function(x) for x in x_for_plot]

    plt.scatter(x_vec, y_vec, color="red")
    plt.plot(x_for_plot, y_exact, label="Exact Function")
    plt.plot(x_for_plot, y_lagrange, label="Lagrange's Polynomial", linestyle="dashed", dashes=(6, 1))
    plt.plot(x_for_plot, y_newton, label="Newton's Polynomial", linestyle="dashed", dashes=(4, 2))
    plt.plot(x_for_plot, y_cubic_spline, label="Cubic Splines", linestyle="dashed", dashes=(3, 3))

    plt.title("Interpolation")
    plt.xlabel("x")
    plt.ylabel(function.__name__ + "(x)")
    plt.legend()
    plt.grid(True)
    plt.show()


a = -2
b = 2
n = 8

x_vec, y_vec = get_points(f1, a, b, n)
plot(x_vec, y_vec, f1)

x_vec, y_vec = get_points(f2, a, b, n)
plot(x_vec, y_vec, f2)

x_vec, y_vec = get_points(f3, a, b, n)
plot(x_vec, y_vec, f3)
