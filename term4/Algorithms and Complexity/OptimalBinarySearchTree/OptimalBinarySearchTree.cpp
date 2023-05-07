#include "OBST.h"

vector<ratio> generateKeys(int size);
vector<double> generateProbs(int size);

int main()
{
    int size;
    cout << "Number of elements: ";
    cin >> size;

    vector<ratio> sourceKeys = generateKeys(size);
    vector<double> sourceProbs = generateProbs(size);

    
    OBST tree = OBST(sourceKeys, sourceProbs);
    cout << "\n============================================================================\n\n";
    tree.printInOrder();
    cout << "\n============================================================================\n\n";
    tree.printStruct();
    cout << "\n============================================================================\n\n";

    char c;
    cout << "quit? [y/n] \n";
    ratio x; int num, denom;

    cin >> c;
    while (c == 'n')
    {
        cout << "Element to find (as {num}/{denom}): ";
        cin >> num >> c >> denom;
        x = ratio(num, denom);

        tree.find(x);

        cout << "\nquit? [y/n] \n";
        cin >> c;
    }

    return 0;
}

// in range of [-100, 100]
vector<ratio> generateKeys(int size) {
    vector<ratio> res = {};
    srand((unsigned)time(0));

    for (int i = 0; i < size; i++)
    {
        res.push_back(ratio((rand() % 201) - 100, (rand() % 101)));
        for (int j = 0; j < i; j++)
            if (res[i] == res[j]) {
                i--;
                res.pop_back();
                break;
            }
    }

    return res;
}

vector<double> generateProbs(int size) {
    vector<double> res = {};
    vector<int> tmp = {};
    int sum = 0;
    srand((unsigned)time(0) + 666666);

    for (int i = 0; i < size; i++)
    {
        tmp.push_back(rand() % 11);
        sum += tmp.back();
    }
    for (int i = 0; i < size; i++)
        res.push_back((double)tmp[i] / (double)sum);

    return res;
}