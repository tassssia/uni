clear
Y = dlmread('f9.txt',' ');
Y = Y(1 : end-1);

LEN = 500;
t = (0 : LEN-1)/100;
plot(t, Y);

YFourier = fft(Y);
plot(YFourier);

YFourAbs = abs(YFourier) * 2 / LEN;
t2 = (0 : LEN-1) * 1/5;
plot(YFourAbs);

YFourAbs = YFourAbs(1 : LEN/2);
t2 = t2(1 : LEN/2);
plot(t2, YFourAbs)
max = fix(localmax(YFourAbs, LEN/2 - 2)/5);
disp("Local maximum: " + max);

size = 6;
A = zeros(LEN, size);
b = zeros(LEN, 1);
for i = 1 : LEN
    A(i, 1) = t(i)^3;
    A(i, 2) = t(i)^2;
    A(i, 3) = t(i);
    A(i, 4) = sin(2*pi * max * t(i));
    A(i, 5) = cos(2*pi * max * t(i));
    A(i, 6) = 1;
    b(i, 1) = Y(i);
end
a = lsqr(A, b);

fRes = a(1)*t.^3 + a(2)*t.^2 + a(3)*t + a(4)*sin(2*pi * max * t) + a(5)*cos(2*pi * max * t) + a(6)*1;
plot(t, fRes);

disp("Coefficients: ");
disp(a);

function max = localmax(arr, n)
    res = 0;
    for i = 2 : n
        if arr(i) < arr(i+1) && arr(i+1) > arr(i+2)
            res = i + 1;
        end
    end
    max = res;
end