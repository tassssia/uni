def y_if(arg)
  x = arg.to_f
  if x > -4 && x <= 0
    ((x-2).abs / (x**2 * Math.cos(x))) ** (1.0/7)
  elsif x > 0 && x <= 12
    (Math.tan(x + Math::E**(-x)) / Math.sin(x)**2) ** (-2.0/7)
  else
    1 / (1 + x/(1 + x/(1+x)))
  end
end

def y_case(arg)
  x = arg.to_f
  case x
  when -Float::INFINITY..-4, 12..Float::INFINITY
    1 / (1 + x/(1 + x/(1+x)))
  when -4..0
    ((x-2).abs / (x**2 * Math.cos(x))) ** (1.0/7)
  when 0..12
    (Math.tan(x + Math::E**(-x)) / Math.sin(x)**2) ** (-2.0/7)
  end
end

test_values = [-5, -4, 0, 12, 12.5]
puts "'if' results: "
test_values.each { |arg| puts "y(#{arg}) = #{y_if(arg)}"}

puts "\n'case' results: "
test_values.each { |arg| puts "y(#{arg}) = #{y_case(arg)}"}

