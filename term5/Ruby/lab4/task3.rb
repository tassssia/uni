def subtract(vector1, vector2)
  res = []
  vector1.each_with_index { |el, i| res << el - vector2[i] }
  return res
end

def mult_by_number(vector, number)
  res = []
  vector.each{ |el| res << el*number }
  return res
end

def gauss_method(coeffs, free_members_vector)
  coeffs.each_with_index{ |row, i| row << free_members_vector[i].to_f }
  n = coeffs.size

  0.upto(n-1) do |i|
    coeffs[i] = mult_by_number(coeffs[i], 1.0/coeffs[i][i])
    (i+1).upto(n-1) do |k|
      coeffs[k] = subtract(coeffs[k], mult_by_number(coeffs[i], coeffs[k][i]))
    end
  end

  (n-1).downto(1) do |i|
    (i-1).downto(0) do |k|
      coeffs[k] = subtract(coeffs[k], mult_by_number(coeffs[i], coeffs[k][i]))
    end
  end

  roots = []
  coeffs.each { |row| roots << row[n].round(5) }

  return roots
end

puts "Enter k: "
k = gets.chomp.to_i
n = rand(3..9)

b = (1..n).to_a
a = Array.new(n) { Array.new(n, k+2) }
a.each_with_index{ |row, j| row[j] = 2 }

n.times do |i|
  n.times { |j| print "#{a[i][j]} "}
  print "| #{b[i]}\n"
end

puts "============================"

res = gauss_method(a, b)
puts "The roots are: #{res}"