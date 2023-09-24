def fact(x)
  res = 1.0
  for i in 2..x
    res *= i
  end
  return res
end

def series1(eps = 0.00001)
  res = 0.0
  n = 2
  to_add = (fact(n-1)/fact(n+1)) ** (n*(n+1))
  while to_add.abs >= eps
    res += to_add
    n += 1
    to_add = (fact(n-1)/fact(n+1)) ** (n*(n+1))
  end
  return res
end

# formula was taken from the task, but it seems it doesn't really calculate csch()
def series2(x, eps = 0.00001)
  res = 1.0/x
  n = 1
  to_add = 2 * (-1)**n * (2**(2*n)-1) * n**(6/7.0) * x**(2*n-1) / fact(2*n)
  while to_add.abs >= eps
    res += to_add
    n += 1
    to_add = 2 * (-1)**n * (2**(2*n)-1) * n**(6/7.0) * x**(2*n-1) / fact(2*n)
  end
  return res
end

def series3(eps = 0.00001)
  res = 0.0
  n = 1
  to_add = fact(3*n - 2) * fact(2*n - 1) / (fact(4*n) * 5**(2*n) * fact(2*n))
  while to_add.abs >= eps
    res += to_add
    n += 1
    to_add = fact(3*n - 2) * fact(2*n - 1) / (fact(4*n) * 5**(2*n) * fact(2*n))
  end
  return res
end

f1 = Proc.new{ |n| (fact(n-1)/fact(n+1)) ** (n*(n+1)) }
f2 = Proc.new{ |n, x| 2 * (-1)**n * (2**(2*n)-1) * n**(6/7.0) * x**(2*n-1) / fact(2*n) }
f3 = Proc.new{ |n| fact(3*n - 2) * fact(2*n - 1) / (fact(4*n) * 5**(2*n) * fact(2*n)) }
def series(func, initial_n = 1, eps = 0.00001, x = Float::INFINITY)
  res = 1.0/x
  n = initial_n
  to_add = func.call(n, x)
  while to_add.abs >= eps
    res += to_add
    n += 1
    to_add = func.call(n, x)
  end
  return res
end

puts series1
puts series2(2)
puts series3
puts "==============================="
puts series(f1, 2)
puts series(f2, 1, 0.00001, 2)
puts series(f3)