clear

A = imread("x1.bmp");
B = imread("y9.bmp");
imshow(A)
figure
imshow(B)

A = double(A);
B = double(B);
 
pseudoinverseG = greville(A);
check(pseudoinverseG,A);

operatorG = B * pseudoinverseG;
resG = uint8(operatorG * A);
 
figure
imshow(resG);

disp(pseudoinverseG);
resMult = A*pseudoinverseG;
disp(resMult);
disp("Error: " + norm(resMult - eye(size(resMult,1))));
 
function result = greville(A)
     curr_a = A(1,:);
     curr = zeros(size(curr_a));
     curr = transpose(curr);
      if (nnz(curr_a(1) ~= 0))
            curr = transpose(curr_a) / (curr_a * transpose(curr_a));
      end
       n = size(A,1);

     for i = 2:n
        curr_a = A(i,:); 
        z = eye(size(curr,1))-(curr*A(1:i-1,:));
        r = curr * transpose(curr);
        condition = (curr_a * z) * transpose(curr_a);
            if (nnz(condition) == 1)
                row_to_add = (z * transpose(curr_a)) / condition;
            else
                row_to_add = (r * transpose(curr_a)) / (1 + ((curr_a * r) * transpose(curr_a)));
            end
        curr = curr -( row_to_add * (curr_a * curr));
        curr = cat(2,curr, row_to_add);
     end
     result = curr;
end
 
function check(Aplus,A)
    assert(isequal(round(Aplus,4),round(Aplus * A * Aplus,4)));
    assert(isequal(round(A,4),round(A*Aplus*A,4)));
    assert(issymmetric(round(A*Aplus,4)));
    assert(issymmetric(round(Aplus*A,4)));
end
 
function n = norm(A)
    n = sum(sum(abs(A)));
end
