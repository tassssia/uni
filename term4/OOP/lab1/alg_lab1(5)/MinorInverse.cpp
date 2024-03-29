#include "RationalMatrix.h"
#include "cmath"

RationalMatrix RationalMatrix::getMinor( int indRow, int indCol)
{
    std::vector<std::vector<fract>> temp_matrix(this->rows-1,std::vector<fract>(this->cols - 1));

    int ki = 0;
    for (int i = 0; i < this->rows; i++){
        if(i != indRow){
            for (int j = 0, kj = 0; j < this->cols; j++){
                if (j != indCol){
                    temp_matrix[ki][kj] = this->content[i][j];
                    kj++;
                }

            }
            ki++;
        }
    }

    RationalMatrix res = RationalMatrix(temp_matrix);
    return res;
}

RationalMatrix RationalMatrix::transposeMatrix() {
    std::vector<std::vector<fract>> res(this->cols,std::vector<fract>(this->rows));

    for (int i = 0; i < this->rows; i++)
        for (int j = 0; j < this->cols; j++)
            res[j][i] = this->content[i][j];

    RationalMatrix mRes = RationalMatrix(res);
    return mRes;
}

RationalMatrix RationalMatrix::minorInverse(){
    RationalMatrix res;
    if (this->cols != this->rows) return res;
    fract det = this->get_matrix_det();

    if(det.isEqual(fract(0,1)) || det.isEqual(fract(INT_MAX, 1))){
        std::cout << "Error. Matrix det = 0" << std::endl;
        return res;
    }
    
    std::vector<std::vector<fract>> temp_matrix(this->rows, std::vector<fract>(this->cols)); // to store matrix of minors
    for(int i = 0; i < this->rows; i++)
    {
        for(int j = 0; j < this->cols; j++)
        {
            RationalMatrix matrix_minor = this->getMinor(i, j);
            temp_matrix[i][j] = fract((long long)pow(-1.0, i + j + 2), 1).mult(matrix_minor.get_matrix_det().div( det));
        }
    }
    res = RationalMatrix(temp_matrix).transposeMatrix();
    return res;
}