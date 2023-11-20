clear
clc

h = 0.2;
t0 = 0;
tk = 50;
eps = 1e-6;

data = dlmread('y9.txt');
[size_m, size_n] = size(data);
c = [0.14, 0.2, 0.2, 0.1]';
m = [9, 28, 18]';


I = inf;
step = 0;
while I > eps
  step = step + 1;

  y = data(:, 1);
  deltaY = zeros(6, 1);
  U = zeros(6, 3);
  integral_UU = zeros(3, 3);
  integral_Uy = zeros(3, 1);
  I = 0.0;
  A = countA(m, c);

  for i = 2:size_n
    U_next = RungeKutta_U(A, U, y, m, c, h);
    y_next = RungeKutta_y(A, y, h);
    deltaY_next = data(:, i) - y_next;
       
    integral_UU = integral_UU + 0.5*h*(U'*U + U_next'*U_next);
    integral_Uy = integral_Uy + 0.5*h*(U'*deltaY + U_next'*deltaY_next);

    I = I + 0.5*h*(deltaY'*deltaY + deltaY_next'*deltaY_next);

    U = U_next; 
    y = y_next; 
    deltaY = deltaY_next;
  end    
  
  delta = pinv(integral_UU)*integral_Uy;
  c(2) = c(2) + delta(1);
  c(4) = c(4) + delta(2);
  m(1) = m(1) + delta(3);
  
  disp(['[', num2str(step), ']: I = ', num2str(I)])
end

disp(' ')
disp('Result: ')
disp(['  c2 = ', num2str(c(2))])
disp(['  c4 = ', num2str(c(4))])
disp(['  m1 = ', num2str(m(1))])


function result = countA(m, c)
  A = zeros(6, 6);
  
  A(1, 2) = 1;
  
  A(2, 1) = -(c(1)+c(2))/m(1);
  A(2, 3) = c(2)/m(1);
  
  A(3, 4) = 1;
  
  A(4, 1) = c(2)/m(2);
  A(4, 3) = -(c(2)+c(3))/m(2);
  A(4, 5) = c(3)/m(2);
  
  A(5, 6) = 1;

  A(6, 3) = c(3)/m(3);
  A(6, 5) = -(c(3)+c(4))/m(3);
  
  result = A;
end

function result = RungeKutta_U (A, U, y, m, c, h)
  k1 = h*dU_dt(A, U, y, m, c);
  k2 = h*dU_dt(A, U + 0.5*k1, y, m, c);
  k3 = h*dU_dt(A, U + 0.5*k2, y, m, c);
  k4 = h*dU_dt(A, U + k3, y, m, c);

  result = U + (k1 + 2*k2 + 2*k3 + k4)/6;
end
function result = dU_dt (A, U, y, m, c)
  dAy_db = zeros(6, 3);
  dAy_db(2, 1) = (y(3)-y(1))/m(1);
  dAy_db(4, 1) = (y(1)-y(3))/m(2);
  dAy_db(6, 2) = -y(5)/m(3);
  dAy_db(2, 3) = (c(1)*y(1)+c(2)*y(1)-c(3)*y(3))/m(1)^2;

  result = A*U + dAy_db;
end

function result = RungeKutta_y (A, y, h)
  k1 = h*dy_dt(A, y);
  k2 = h*dy_dt(A, y + 0.5*k1);
  k3 = h*dy_dt(A, y + 0.5*k2);
  k4 = h*dy_dt(A, y + k3);

  result = y + (k1 + 2*k2 + 2*k3 + k4)/6;
end
function result = dy_dt (A, y)
  result = A*y;  
end
