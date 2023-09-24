def multiply(vec_row, matrix)
  res = []
  vec_row.size.times do |i|
    t = 0
    vec_row.each_with_index do |el, j|
      t += el * matrix[j][i]
    end
    res << t
  end

  return res
end

n = 3
y = Array.new(n) { rand(-10..10) }
print "y: #{y}\n"
b = []
n.times{ b << Array.new(n) { rand(-10..10) } }
print "B:"
n.times{ |i| print "#{b[i]}\n  " }

print "\nThe result is #{multiply(y, b)}"