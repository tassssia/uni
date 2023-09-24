$x_range = 0.1..1
$n_range = 20..58
$eps = 0.001

def func(x, i)
  (Math.cos(2*i - 1) * x) / ((2*i - 1)**2)
end

def sum_n_first(f, x, n)
  res = 0
  n.times{ |i| res += f.call(x, i) }

  return res
end

def sum_eps(f, x, eps)
  res = 0
  i = 0
  to_add = f.call(x, i)

  while to_add.abs >= eps do
    res += to_add
    i += 1
    to_add = f.call(x, i)
  end

  return res
end

def sum(f, x, n)
  unless $x_range.include? x
    raise "The argument is out of the range #{$x_range}"
  end

  if $n_range.include? n
    return sum_n_first(f, x, n)
  else
    return sum_eps(f, x, $eps)
  end
end

puts "Enter x from #{$x_range}: "
x = gets.chomp.to_f
puts "Enter n: "
n = gets.chomp.to_i
puts "The result is: #{sum(method(:func), x, n)}"
