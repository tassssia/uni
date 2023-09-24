def f1(x)
  2 / (1 - 4*x)
end

def f2(x)
  Math.asin(Math.sqrt(x)) / Math.sqrt(x * (1 - x))
end

def rectangle(a, b, n = 10000)
  step = (b - a) / n
  x = a + step / 2
  res = 0

  n.times do
    res += yield(x) * step
    x += step
  end

  return res
end

def trapezoid(a, b, n = 10000)
  step = (b - a) / n
  x = a + step

  res = 0.5 * step * (yield(a) + yield(b))
  (n-1).times do
    res += step * yield(x)
    x += step
  end

  return res
end

a1 = 2.2
b1 = -1.2
puts "f1 on [#{a1}, #{b1}]:"
puts "Rectangle method: #{rectangle(a1, b1) { |x| f1(x) }}"
puts "Trapezoid method: #{trapezoid(a1, b1) { |x| f1(x) }}"

a2 = 0.2
b2 = 0.3
puts "f2 on [#{a2}, #{b2}]:"
puts "Rectangle method: #{rectangle(a2, b2) { |x| f2(x) }}"
puts "Trapezoid method: #{trapezoid(a2, b2) { |x| f2(x) }}"
