def task1(x)
  res = 1
  for i in 1..10
    res +=  (-x)**i * (i + 1) / (i + 2.0)
  end
  return res
end

def task5(n)
  res = 0
  n.times { res = Math.sqrt(res + 2) }
  return res
end

puts "1. #{task1(2)}"
puts "5. Enter n: "
puts task5(gets.chomp.to_i)