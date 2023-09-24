a = true
b = false
c = true
x = 50
y = 10
z = -5

expr_a = !(a || b) && (a && !b)
expr_b = (z != y).object_id <= (6 >= y).object_id && a || b && c && x >= 1.5 # "<=" with boolean values was defined as inversion (!a || b)
expr_c = (8-2*x <= z) && (x**2 >= y**2) || (z >= 1.5)
expr_d = x>0 && y<0 || z >= (x*y-(-y/x)) + (-z)/2
expr_e = !(a || b && !(c || (!a || b)))
expr_f = x**2+y**2>=1 && x>=0 && y>=0
expr_g = (a && (c && b != b || a) || c) && b

puts "1.a) #{expr_a}\n  b) #{expr_b}\n  c) #{expr_c}\n  d) #{expr_d}\n  e) #{expr_e}\n  e) #{expr_f}\n  g) #{expr_g}"

number = 4.5
logic = true

expr = !((Math::E**(number*number) - Math.sin(number)) < 3.14) && (logic || !logic)

puts "2. #{expr}"