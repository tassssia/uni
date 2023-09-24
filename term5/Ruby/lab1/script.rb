puts "Enter the values of t, x, z: "
t = gets.chomp.to_f
x = gets.chomp.to_f
z = gets.chomp.to_f

res = Math::E**(Math.tan(x)**2) +
  Math.sqrt((t-z).abs) / (Math.cos(Math::PI**2)**3 +
  Math::E**Math::PI * z**2)

puts res