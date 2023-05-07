#pragma once
#include "ratio.h"
#include <vector>
#include <stack>
using namespace std;

class OBST {
	class node {
	public:
		ratio key;
		double prob;
		node* left;
		node* right;

		node(ratio key, double prob, node* left = NULL, node* right = NULL) {
			this->key = key;
			this->prob = prob;
			this->left = left;
			this->right = right;
		}

		void print() {
			this->key.print();
			cout.precision(10);
			cout << " (p:" << fixed << this->prob << ") ";
		}
	};

	vector<vector<double>> costMatrix;
	vector<vector<int>> rootMatrix;
	node* root;

	void sort(vector<ratio>* keys, int left, int right, vector<double>* prob) {
		if (left >= right) return;

		int m = left + (right - left) / 2;

		sort(keys, left, m, prob);
		sort(keys, m + 1, right, prob);

		// merging
		int n1 = m - left + 1;
		int n2 = right - m;
		vector<ratio> kL(n1);
		vector<double> pL(n1);
		vector<ratio> kR(n2);
		vector<double> pR(n2);

		for (int i = 0; i < n1; i++)
		{
			kL[i] = (*keys)[left + i];
			pL[i] = (*prob)[left + i];
		}
		for (int i = 0; i < n2; i++)
		{
			kR[i] = (*keys)[m + 1 + i];
			pR[i] = (*prob)[m + 1 + i];
		}

		int i = 0, j = 0, k = left;
		while (i < n1 && j < n2)
		{
			if (kL[i] < kR[j]) {
				(*keys)[k] = kL[i];
				(*prob)[k++] = pL[i++];
			}
			else {
				(*keys)[k] = kR[j];
				(*prob)[k++] = pR[j++];
			}
		}
		while (i < n1)
		{
			(*keys)[k] = kL[i];
			(*prob)[k++] = pL[i++];
		}
		while (j < n2)
		{
			(*keys)[k] = kR[j];
			(*prob)[k++] = pR[j++];
		}
	}
	void calcMatrixes(vector<double> prob) {
		int n = prob.size();
		this->costMatrix = vector<vector<double>>(n + 1, vector<double>(n + 1, 0));
		this->rootMatrix = vector<vector<int>>(n, vector<int>(n, -1));
		for (int i = 0; i < n; i++)
		{
			this->costMatrix[i][i + 1] = prob[i];
			this->rootMatrix[i][i] = i;
		}

		int j;
		double curr;
		for (int d = 2; d < n + 1; d++)
		{
			for (int i = 0; i < n - d + 1; i++)
			{
				j = d + i;
				this->costMatrix[i][j] = DBL_MAX;
				for (int k = i; k < j; k++)
				{
					curr = this->costMatrix[i][k] + this->costMatrix[k + 1][j];
					if (curr < this->costMatrix[i][j]) {
						this->costMatrix[i][j] = curr;
						this->rootMatrix[i][j - 1] = k;
					}
				}
				for (int s = i; s < j; s++)
					this->costMatrix[i][j] += prob[s];
			}
		}
	}
	void buildTree(vector<ratio> keys, vector<double> prob) {
		int n = keys.size();
		this->root = new node(keys[this->rootMatrix[0][n - 1]], prob[this->rootMatrix[0][n - 1]]);

		stack<node*> roots;
		stack<int> left;
		stack<int> right;
		roots.push(this->root);
		left.push(0);
		right.push(n - 1);

		node* curr, * child;
		int i, j, k;
		while (!(roots.empty())) {
			curr = roots.top(); roots.pop();
			i = left.top(); left.pop();
			j = right.top(); right.pop();

			k = this->rootMatrix[i][j];
			if (k < j) {
				child = new node(keys[this->rootMatrix[k + 1][j]], prob[this->rootMatrix[k + 1][j]]);
				curr->right = child;

				roots.push(child);
				left.push(k + 1);
				right.push(j);
			}
			if (i < k) {
				child = new node(keys[this->rootMatrix[i][k - 1]], prob[this->rootMatrix[i][k - 1]]);
				curr->left = child;

				roots.push(child);
				left.push(i);
				right.push(k - 1);
			}
		}
	}

public: 
	OBST(vector<ratio> keys, vector<double> probabilities) {
		this->sort(&keys, 0, keys.size() - 1, &probabilities);

		this->calcMatrixes(probabilities);

		this->buildTree(keys, probabilities);
	}

	void printInOrder() {
		stack<node*> st;

		node* curr = this->root;
		while (curr || !st.empty()) {
			while (curr) {
				st.push(curr);
				curr = curr->left;
			}

			curr = st.top();
			st.pop();

			curr->print(); cout << '\n';
			curr = curr->right;
		}
	}
	void printStruct() {
		vector<node*> currLvl = { this->root };
		vector<node*> nextLvl;
		bool notAllNull = 1;
		while (notAllNull)
		{
			notAllNull = 0;
			for (int i = 0; i < currLvl.size(); i++)
			{
				if (currLvl[i]) { 
					currLvl[i]->print();
					nextLvl.push_back(currLvl[i]->left);
					nextLvl.push_back(currLvl[i]->right);
				}
				else {
					cout << " --- ";
					nextLvl.push_back(NULL);
					nextLvl.push_back(NULL);
				}
				
				if (nextLvl[nextLvl.size() - 1] || nextLvl[nextLvl.size() - 2]) notAllNull = 1;
			}
			currLvl = nextLvl;
			nextLvl = {};
			cout << '\n';
		}
	}

	bool isPresent(ratio key) {
		node* curr = this->root;

		while (curr)
		{
			if (key < curr->key) curr = curr->left;
			else if (key > curr->key) curr = curr->right;
			else return 1;
		}

		return 0;
	}
	void find(ratio key) {
		node* curr = this->root;
		int height = 0;

		while (curr)
		{
			height++;
			if (key < curr->key) curr = curr->left;
			else if (key > curr->key) curr = curr->right;
			else {
				cout << "The element"; curr->print(); cout << "is on the level " << height;
				return;
			}
		}

		cout << "Element is not found\n";
	}

	vector<ratio> readKeys() {
		vector<ratio> res;
		stack<node*> st;

		node* curr = this->root;
		while (curr || !st.empty()) 
		{
			while (curr) 
			{
				st.push(curr);
				curr = curr->left;
			}

			curr = st.top();
			st.pop();

			res.push_back(curr->key);
			curr = curr->right;
		}
		return res;
	}
	vector<double> readProbs() {
		vector<double> res;
		stack<node*> st;

		node* curr = this->root;
		while (curr || !st.empty()) 
		{
			while (curr) 
			{
				st.push(curr);
				curr = curr->left;
			}

			curr = st.top();
			st.pop();

			res.push_back(curr->prob);
			curr = curr->right;
		}
		return res;
	}
};