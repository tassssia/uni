#include "RationalMatrix.h"
#include "Fraction.h"

RationalMatrix RationalMatrix::add_const_column(int position, fract val)
{
    if(this->content.size() < position || position < 0) 
        return{};

    std::vector<std::vector<fract>> temp = this->content;

    int index = 0;
    for(auto& row : temp)
        row.insert(row.begin() + position, val);

    return RationalMatrix(temp);
}

RationalMatrix RationalMatrix::extract_col(int start_col, int end_col)
{
    std::vector<std::vector<fract>> tmp(this->rows, std::vector<fract>(end_col - start_col + 1));

    int index ;
    for (int i = 0; i < this->rows; i++)
    {
        index = start_col;
        for (int j = 0; j < end_col - start_col + 1; j++)
            tmp[i][j] = this->content[i][index++];
    }

    return RationalMatrix(tmp);
}

// знаходження коефіцієнтів регресії
RationalMatrix RationalMatrix::get_mlr_coeff(RationalMatrix y)
{
    if (y.get_cols() != 1 || y.get_rows() != this->get_rows()) 
    {
        throw std::invalid_argument("Incorrect data");
    }
    RationalMatrix x = this->add_const_column(0, { 1,1 });
    RationalMatrix x_transposed = x.transposeMatrix();

    RationalMatrix normal_matrix_inversed = (x_transposed.StrassensMult(&x)).minorInverse();    // (X'*X)^(-1)
    RationalMatrix moment_matrix = x_transposed.StrassensMult(&y);                              // X'*Y

    return normal_matrix_inversed.StrassensMult(&moment_matrix);                                // (X'*X)^(-1) * (X'*Y)
}

void RationalMatrix::print_regression_model()
{
    std::cout << "y = (";
    this->content[0][0].print();
    std::cout << ") + ";
    for (int i = 1; i < this->rows; i++)
    {
        std::cout << "(";
        this->content[i][0].print();
        std::cout << ") * x" << i << " + ";
    }
}

// Residual sum of squares - Залишкова сума квадратів
fract RationalMatrix::rss(RationalMatrix x, RationalMatrix y, RationalMatrix coeff)
{
    fract rss(0, 1);
    for (int i = 0; i < y.rows; i++) 
    {
        fract curr_y(0, 1);
        curr_y = curr_y.sum(coeff.get(0, 0));
        for (int j = 0; j < x.cols; j++) 
            curr_y = curr_y.sum(x.get(i, j).mult(coeff.get(j+1, 0)));

        rss = rss.sum((y.get(i, 0).diff(curr_y)).mult(y.get(i, 0).diff(curr_y)));
    }

    return rss.mult(fract(1, y.get_rows()));
}

// Total sum of squares - загальна сума квадратів
fract RationalMatrix::tss(RationalMatrix x, RationalMatrix y)
{
    fract tss(0, 1);

    fract y_mean(0, 1);
    for (int i = 0; i < y.rows; i++)
        y_mean = y_mean.sum(y.get(i, 0));

    y_mean = y_mean.mult(fract(1, y.rows));

    for (int i = 0; i < y.rows; i++) 
        tss = tss.sum((y.get(i, 0).diff(y_mean)).mult(y.get(i, 0).diff(y_mean)));

    return tss.mult(fract(1, y.get_rows()));
}

// Coefficient of determination - коефіцієнт детермінації
fract RationalMatrix::coeff_of_determination(RationalMatrix x, RationalMatrix y, RationalMatrix coeff)
{
    fract rss = coeff.rss(x, y, coeff);
    fract tss = coeff.tss(x, y);
    return fract(1, 1).diff(rss.div(tss));
}


