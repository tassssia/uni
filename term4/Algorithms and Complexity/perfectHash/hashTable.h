#pragma once
#include <iostream>
#include <vector>
using namespace std;
const double EPS = 0.01;

int h(double x, int a, int b, int p, int m) {
    return abs((((int)(a * x) + b) % p % m));
}

class hashTable {	
    int A, B, P;

    class row {
    public:
		int a, b, size;
		vector<double> data;

        void rowHash(int P) {
            vector<double> tmp = this->data;
            this->a = 1;
            this->data = {};
            this->data.insert(this->data.end(), this->size, DBL_MAX);
            
            int j;
            int n = tmp.size();
            for (int i = 0; i < n; i++)
            {
                j = h(tmp[i], this->a, this->b, P, this->size);

                if (this->data[j] < DBL_MAX) {
                    if ((int)(this->data[j]) == (int)(tmp[i])) {
                        this->a *= 10;
                    }
                    else {
                        this->b++;
                        if (this->b == P) {
                            this->b = 0;
                            this->a++;
                        }
                    }
                    this->data = {};
                    this->data.insert(this->data.end(), this->size, DBL_MAX);
                    i = -1;
                }
                else this->data[j] = tmp[i];
            }
        }

		row(int a, int b, int size) {
			this->a = a;
			this->b = b;
			this->size = size;
            this->data = {};
            this->data.insert(this->data.end(), size, DBL_MAX);
		}
	};

    void primaryHash(vector<double> source) {
        
        int n = source.size();
        int i;
        for (int j = 0; j < n; j++)
        {
            i = h(source[j], this->A, this->B, this->P, this->size);
            this->data[i].data.push_back(source[j]);
            this->data[i].size = ((int)sqrt(this->data[i].size) + 1) * ((int)sqrt(this->data[i].size) + 1);
        }
    }

public:
    vector<row> data;
    int size;

    hashTable(vector<double> source, int size, int a, int b, int p) {
        this->A = a;
        this->B = b;
        this->P = p;
        this->size = size;
        this->data = {};
        this->data.insert(this->data.end(), size, row(0, 0, 0));

        this->primaryHash(source);
        for (int i = 0; i < size; i++)
            if (this->data[i].size > 1) this->data[i].rowHash(p);
    }

    void print() {
        for (int i = 0; i < this->size; i++)
        {
            cout << i << " : ";
            if (this->data[i].size) {
                cout << "[" << this->data[i].size << ", " << this->data[i].a << ", " << this->data[i].b << "] ";

                for (int j = 0; j < this->data[i].size; j++)
                {
                    if (this->data[i].data[j] < DBL_MAX) cout << this->data[i].data[j] << " ";
                    else cout << "_ ";
                }
            }
            cout << "\n";
        }
    }

    pair<int, int> find(double key) {
        int i = h(key, this->A, this->B, this->P, this->size);
        if (this->data[i].size) {
            int j = h(key, this->data[i].a, this->data[i].b, this->P, this->data[i].size);
            if (this->data[i].data[j] < DBL_MAX && abs(this->data[i].data[j] - key) < EPS) return { i, j };
        }

        return { -1, -1 };
    }
};