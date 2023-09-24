import math
EPS = 0.001

def f(x):
    return x**2 + math.sin(x) - 12*x - 0.25

def df(x):
    return 2*x + math.cos(x) - 12

class result:
    def __init__(self, value, iterations, error):
        self.x = value
        self.i = iterations
        self.eps = error

    def printResult(self):
        print(f"The root {self.x} (±{self.eps/2}) was found in {self.i} iterations")

def DichotomyMethod(left, right):
    i = 0
    fl = f(left)
    fr = f(right)
    if fl * fr > 0:
        print("Incorrect interval")
        return Result(float('inf'), i, EPS)

    middle = (left + right) / 2
    fm = f(middle)
    while right - left >= EPS:
        if abs(fl) < EPS:
            return Result(left, i, EPS)
        if abs(fr) < EPS:
            return Result(right, i, EPS)

        if fl * fm < 0:
            right = middle
            fr = fm
        else:
            left = middle
            fl = fm

        middle = (left + right) / 2
        fm = f(middle)
        i += 1

    return result(middle, i, EPS)

def getNext(current, alpha):
    return current - alpha * f(current)

def RelaxationMethod(current):
    i = 1
    alpha = 2 / df(current)

    nextVal = getNext(current, alpha)

    while abs(nextVal - current) >= EPS:
        current = nextVal
        nextVal = getNext(current, alpha)
        i += 1

    return result(nextVal, i, EPS)

def getDerExtremum(left, right):
    dMin = float('inf')
    dMax = float('-inf')

    x = left
    while x <= right:
        dVal = df(x)
        if dVal < dMin:
            dMin = dVal
        if dVal > dMax:
            dMax = dVal
        x += EPS

    return dMin, dMax

def OptRelaxationMethod(left, right):
    i = 1
    dMin, dMax = getDerExtremum(left, right)
    alpha = 2 / (dMin + dMax)

    current = (left + right) / 2
    nextVal = getNext(current, alpha)

    while abs(nextVal - current) >= EPS:
        current = nextVal
        nextVal = getNext(current, alpha)
        i += 1

    return result(nextVal, i, EPS)

def NewtonsMethod(current):
    i = 1
    nextVal = getNext(current, 1 / df(current))

    while abs(nextVal - current) >= EPS:
        current = nextVal
        nextVal = getNext(current, 1 / df(current))
        i += 1

    return result(nextVal, i, EPS)

a, b = map(float, input("Enter the sides of the interval: ").split())

x0 = (a + b) / 2

print("\nDichotomy method on [", a, "; ", b, "]: ")
DichotomyMethod(a, b).printResult()

print("\nRelaxation method around", x0, ": ")
RelaxationMethod(x0).printResult()

print("\nRelaxation method on [", a, "; ", b, "]: ")
OptRelaxationMethod(a, b).printResult()

print("\nNewton's method around", x0, ": ")
NewtonsMethod(x0).printResult()
