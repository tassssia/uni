#pragma once
#include <iostream>
#include <vector>
#include "ratio.h"
using namespace std;

class binHeap {
	class node {
	public:
		ratio key;
        int degree;
		node* sibling;
		node* parent;
		node* child;

		node(ratio key, int degree = 0, node* sibling = NULL, node* parent = NULL, node* child = NULL) {
			this->key = key;
            this->degree = degree;
			this->sibling = sibling;
			this->parent = parent;
			this->child = child;
		}

		void print() {
			cout << " "; this->key.print(); cout << " ";
			if (this->child) this->child->print();
			if (this->sibling) this->sibling->print();
		}

		// [less].link([greater])
		void link(node *toLink) {
			if (this->key > toLink->key) return;
			toLink->parent = this;
			toLink->sibling = this->child;
			this->child = toLink;
			this->degree++;
		}

		node* find(ratio key) {
			node* res = NULL;
			if (this->key == key) return this;
			if (this->child) {
				res = this->child->find(key);
				if (res) return res;
			}
			if (this->sibling)
				res = this->sibling->find(key);

			return res;
		}
	};

	node* head;
	binHeap(node* head = new node(ratio(0, 1))) {
		this->head = head;
	}

	void merge(binHeap* first, binHeap* second) {
		if (!first->head) {
			head = second->head;
			return;
		}
		if (!second->head) {
			head = first->head;
			return;
		}

		node* ptr1 = first->head, * ptr2 = second->head;
		if (ptr1->degree > ptr2->degree) {
			head = ptr2;
			ptr2 = ptr2->sibling;
		}
		else {
			head = ptr1;
			ptr1 = ptr1->sibling;
		}

		node* ptr = head;
		while (ptr1 && ptr2)
		{
			if (ptr1->degree < ptr2->degree) {
				ptr->sibling = ptr1;
				ptr = ptr->sibling;
				ptr1 = ptr1->sibling;
			}
			else {
				ptr->sibling = ptr2;
				ptr = ptr->sibling;
				ptr2 = ptr2->sibling;
			}
		}
		while (ptr1)
		{
			ptr->sibling = ptr1;
			ptr = ptr->sibling;
			ptr1 = ptr1->sibling;
		}
		while (ptr2)
		{
			ptr->sibling = ptr2;
			ptr = ptr->sibling;
			ptr2 = ptr2->sibling;
		}
	}

	void decreaseKey(node* toDec, ratio value) {
		if (value > toDec->key) return;

		toDec->key = value;
		node* ptr = toDec;
		node* parent = toDec->parent;
		ratio forSwap;
		while (parent && (parent->key > ptr->key))
		{
			forSwap = parent->key;
			parent->key = ptr->key;
			ptr->key = forSwap;

			ptr = parent;
			parent = parent->parent;
		}
	}
	void extractMin() {
		if (!head) return;

		node* min = head, * ptr = head->sibling,
			* prev = head, * preMin = NULL;
		while (ptr)
		{
			if (ptr->key < min->key) { 
				min = ptr;
				preMin = prev;
			}
			prev = ptr;
			ptr = ptr->sibling;
		}

		if (preMin) preMin->sibling = min->sibling;
		else head = min->sibling;

		node* curr = min->child, * nextStep = NULL, * forSubHeap = NULL;
		while (curr)
		{
			curr->parent = NULL;
			nextStep = curr->sibling;

			curr->sibling = forSubHeap;
			forSubHeap = curr;
			curr = nextStep;
		}

		binHeap* subHeap = new binHeap(forSubHeap);
		binHeapUnion(this, subHeap);
	}

	node* find(ratio key) {
		node* ptr = head, * res = NULL;
		while (ptr)
		{
			if (!(key < ptr->key)) {
				res = ptr->find(key);
				if (res) return res;
			}
			ptr = ptr->sibling;
		}

		return res;
	}
public:
    binHeap(vector<ratio> keys) {
		int n = keys.size();
		this->head = new node(keys[0]);
		for (int i = 1; i < n; i++)
			this->insert(keys[i]);
    }

	void print() {
		node* ptr = head;
		while (ptr)
		{
			cout << "{ "; ptr->key.print(); cout << " } ";
			if (ptr->child) ptr->child->print();
			cout << "\n";

			ptr = ptr->sibling;
		}
	}

	void insert(ratio key) {
		binHeap* toIns = new binHeap();
		toIns->head->key = key;
		binHeapUnion(this, toIns);
	}

	bool isPresent(ratio key) {
		return (bool)this->find(key);
	}

	ratio minValue() {
		if (!head) return ratio(INT_MAX, 1);
		node* min = head, * ptr = head->sibling;
		while (ptr)
		{
			if (ptr->key < min->key) min = ptr;
			ptr = ptr->sibling;
		}

		return min->key;
	}

	void erase(ratio key) {
		node* toDel = this->find(key);
		if (!toDel) {
			cout << "The key was not found\n";
			return;
		};
		this->decreaseKey(toDel, ratio(-1000000, 1));
		this->extractMin();
	}

	void binHeapUnion(binHeap* first, binHeap* second) {
		merge(first, second);
		if (!head) return;

		node* prev = NULL, * curr = head, * next = curr->sibling;
		while (next) {
			if (curr->degree != next->degree ||
				(next->sibling && next->sibling->degree == curr->degree)) {
				prev = curr;
				curr = next;
			}
			else if (!(curr->key > next->key)) {
				curr->sibling = next->sibling;
				curr->link(next);
			}
			else {
				if (!prev) head = next;
				else prev->sibling = next;

				next->link(curr);
				curr = next;
			}
			next = curr->sibling;
		}
	}
};