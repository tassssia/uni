input = "10101010101010"

def to_dec(number, base)
  res = 0
  pow = number.length - 1

  number.each_char do |digit|
    res += digit.to_i * base**pow
    pow -= 1
  end

  return res
end

puts "#{input} -> #{to_dec(input, 2)}"