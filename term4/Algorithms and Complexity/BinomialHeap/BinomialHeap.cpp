#include "binHeap.h"

vector<ratio> generateKeys(int size);

int main()
{
    int size;
    cout << "Number of elements: ";
    cin >> size;

    vector<ratio> sourceKeys = generateKeys(size);
    binHeap heap = binHeap(sourceKeys);
    cout << "\n============================================================================\n\n";
    heap.print();
    cout << "\n============================================================================\n\n";

    char c; 
    bool quit = 0;
    ratio x;
    int num, denom;
    while (!quit)
    {
        cout << "choose an action:\n1 - print\n2 - insert element\n3 - delete element\n4 - check presence\n5 - find minimum key value\n0 - quit\n";
        cin >> c;
        switch (c) {
        case '1':
            cout << "\n============================================================================\n\n";
            heap.print();
            cout << "\n============================================================================\n\n";
            break;
        case '2':
            cout << "Element to insert (as {num}/{denom}): ";
            cin >> num >> c >> denom;
            x = ratio(num, denom);
            heap.insert(x);
            break;
        case '3':
            cout << "Element to delete (as {num}/{denom}): ";
            cin >> num >> c >> denom;
            x = ratio(num, denom);
            heap.erase(x);
            break;
        case '4':
            cout << "Element to check (as {num}/{denom}): ";
            cin >> num >> c >> denom;
            x = ratio(num, denom);
            if (heap.isPresent(x)) cout << "It's present\n";
            else cout << "It's absent\n";
            break;
        case '5':
            cout << "Minimum is ";
            heap.minValue().print();
            cout << "\n";
            break;
        default:
            quit = 1;
            break;
        }
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
