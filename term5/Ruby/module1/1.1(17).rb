def F(x, a, b, c)
  case
  when (x + 10) < 0 && b != 0
    res = a * x**2 - c * x + b
  when (x + 10) < 0 && b == 0
    res = (x - a) / (x - c)
  else
    res = -x / (a - c)
  end

  if (a.to_i | b.to_i) & -(a.to_i | c.to_i) != 0
    res
  else
    res.to_i
  end
end

puts "x_start:  "
x_start = gets.chomp.to_f
puts "x_end: "
x_end = gets.chomp.to_f
puts "dx: "
dx = gets.chomp.to_f

puts "a, b, c: "
a, b, c = gets.chomp.split.map(&:to_f)

res = []
x = x_start

while x <= x_end
  res << [x, F(x, a, b, c)]
  x += dx
end

res.each { |point| puts "F(#{point[0]}) = #{point[1]}"}
