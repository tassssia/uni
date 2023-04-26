#include <vector>
#include "hashTable.h"
using namespace std;
const int A = 3, B = 42, P = 101;

vector<double> generate(int size);

int main()
{
    int n, size;
    cout << "Number of elements: ";
    cin >> n;
    
    cout << "Number of rows: ";
    cin >> size;
    cout << "\n";

    vector<double> source = generate(n);
    hashTable table = hashTable(source, size, A, B, P);
    table.print();

    pair<int, int> pos = {-1, -1};
    double x;
    char c;
    cout << "quit? [y/n]";
    cin >> c;
    while (c == 'n')
    {
        cout << "Element to find: ";
        cin >> x;
        pos = table.find(x);
        if (pos.first > -1 && pos.second > -1)
            cout << "The element " << x << " position is (" << pos.first << ", " << pos.second << ")\n";
        else cout << "Element is not found.\n";

        cout << "quit? [y/n]";
        cin >> c;
    }

    return 0;
}

// from [-100, 100] with 0.01 accuracy
vector<double> generate(int size) {
    vector<double> res = {};
    srand((unsigned)time(0));
    for (int i = 0; i < size; i++)
    {
        res.push_back((double)(rand() % 20001) / 100 - 100);
        for (int j = 0; j < i; j++)
            if (abs(res[i] - res[j]) < 0.01) {
                i--;
                res.pop_back();
                break;
            }
    }
    return res;
}
