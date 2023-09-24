def func(a, b)
  c = []
  c << b.select{ |el| el % 2 == 0 }
  c[0] += b.select{ |el| el % 2 == 1 }
  c << a.select{ |el| el % 2 == 0 }
  c[1] += a.select{ |el| el % 2 == 1 }

  return c
end

a = Array.new(12) { rand(1..100) }
b = Array.new(12) { rand(1..100) }

c = func(a, b)
puts "a is #{a}"
puts "b is #{b}"
print "\nThe result is \n#{c[0]}\n#{c[1]}\n"