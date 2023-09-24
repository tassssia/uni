def y(x, n, c)
  x ** (((n-c)**2+4.0*n*c)/(n*(n**2-c**2))) + (x/n+12.0)/(6.0-c*x)
end

def z(x, n, c)
  (1 - Math.cos(4*x)) / (Math.cos(2*x)**(-2) - 1) +
  (1 + Math.cos(4*x)) / (Math.sin(2*x)**(-2) - 1) +
  Math.tan(2*Math::PI/9 - x) ** (c/n)
end

def f(x, n, c)
  case x
  when 2...n
    y(x, n, c)
  when n...2*n
    z(x, n, c)
  else
    return nil
  end
end

def task1(n, c)
  step = (n-1.0) / (n+c)
  return if step.abs < 0.00001
  arg = 1.0
  res = []

  while arg <= n
    res << [arg, y(arg, n, c)]
    arg += step
  end

  return res
end

def task2(n, c)
  step = (Math::PI - Math::PI/n) / (1.5*n + c)
  return if step.abs < 0.00001
  arg = Math::PI/n
  res = []

  while arg <= n
    res << [arg, z(arg, n, c)]
    arg += step
  end

  return res
end

def task3(n, c)
  step = (c-2.0) / (2*n)
  return if step.abs < 0.00001
  arg = 2.0
  res = []

  while arg <= n
    res << [arg, z(arg, n, c)]
    arg += step
  end

  return res
end

puts "Enter N and c: "
n = gets.chomp.to_f
c = gets.chomp.to_f

puts "1."
task1(n, c).each { |point| puts "  y(#{point[0]}) = #{point[1]}"}

puts "=======================================\n2."
task2(n, c).each { |point| puts "  y(#{point[0]}) = #{point[1]}"}

puts "=======================================\n3."
task3(n, c).each { |point| puts "  y(#{point[0]}) = #{point[1]}"}