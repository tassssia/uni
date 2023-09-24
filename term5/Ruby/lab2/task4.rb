input = 333

def to_bin(number)
  res = ""
  while number > 0
    res += (number % 2).to_s
    number /= 2
  end
  res.reverse!
end

puts "#{input} -> #{to_bin(input)}"