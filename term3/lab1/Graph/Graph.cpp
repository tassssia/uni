#include "Graph.h"
const double EPS = 0.0001;

template <typename T>
bool isEqual(T data1, T data2) {
	return data1 == data2;
}
template <> bool isEqual(Point data1, Point data2) {
	return (abs(data1.x - data2.x) < EPS && abs(data1.y - data2.y) < EPS);
}
template <> bool isEqual(Line data1, Line data2) {
	return (abs(data1.a / data2.a - data1.b / data2.b) < EPS && abs(data1.c / data2.c - data1.b / data2.b) < EPS);
}
template <> bool isEqual(Circle data1, Circle data2) {
	return (abs(data1.r - data2.r) < EPS && isEqual<Point>(data1.center, data2.center));
}
template <> bool isEqual(double data1, double data2) {
	return abs(data1 - data2) < EPS;
}

template <typename T>
void outputSingle(T data) {
	std::cout << " " << data << " ";
}
template <> void outputSingle(Point data) {
	data.output();
}
template <> void outputSingle(Line data) {
	data.output();
}
template <> void outputSingle(Circle data) {
	data.output();
}


template <typename vertT>
int GraphList<vertT>::getInd(vertT key) {
	for (int i = 0; i < this->n; i++)
		if (isEqual(this->vertices[i].data, key)) return i;
	return -1;
}
template <typename vertT, typename edgeT>
int  GraphMatrix<vertT, edgeT>::getInd(vertT key) {
	int n = this->vertices.size();
	for (int i = 0; i < n; i++)
		if (isEqual(this->vertices[i], key)) return i;
	return -1;
}


template <typename vertT>
void GraphList<vertT>::addVertex(vertT data) {
	node tmp;
	tmp.data = data;
	tmp.next = NULL;
	this->vertices.push_back(tmp);
	this->n++;
}
template<typename vertT, typename edgeT>
void GraphMatrix<vertT, edgeT>::addVertex(vertT data) {
	this->vertices.push_back(data);
	int n = this->edges.size();

	for (int i = 0; i < n; i++)
		this->edges[i].push_back(NULL);
	
	std::vector<edgeT*> tmp(n + 1, NULL);
	this->edges.push_back(tmp);
}

template<typename vertT>
void GraphList<vertT>::addEdge(vertT vert1, vertT vert2) {
	int ind1 = this->getInd(vert1);
	int ind2 = this->getInd(vert2);

	if (ind1 != -1 && ind2 != -1 && ind1 != ind2) {
		node* ptr = new node;
		ptr->data = vert1;
		ptr->next = this->vertices[ind2].next;
		this->vertices[ind2].next = ptr;

		ptr = new node;
		ptr->data = vert2;
		ptr->next = this->vertices[ind1].next;
		this->vertices[ind1].next = ptr;
	}
}
template<typename vertT, typename edgeT>
void GraphMatrix<vertT, edgeT>::addEdge(vertT vert1, vertT vert2, edgeT data) {
	int ind1 = this->getInd(vert1);
	int ind2 = this->getInd(vert2);
	if (ind1 != -1 && ind2 != -1) {

		edgeT* ptr = new edgeT;
		*ptr = data;
		this->edges[ind1][ind2] = ptr;
		ptr = new edgeT;
		*ptr = data;
		this->edges[ind2][ind1] = ptr;
	}
}

template <typename vertT>
void GraphList<vertT>::removeFromList(vertT key, int listInd) {
	node* ptr = this->vertices[listInd].next;
	node* prev = NULL;
	if (ptr && isEqual(ptr->data, key)) {
		this->vertices[listInd].next = ptr->next;
		delete ptr;
	}
	while (ptr && !isEqual(ptr->data, key))
	{
		prev = ptr;
		ptr = ptr->next;
	}
	if (ptr) {
		prev->next = ptr->next;
		delete ptr;
	}
}

template <typename vertT>
void GraphList<vertT>::removeVertex(vertT key) {
	int ind = this->getInd(key);
	if (ind != -1) {
		node* ptr = this->vertices[ind].next;
		node* prev = NULL;
		while (ptr)
		{
			prev = ptr;
			ptr = ptr->next;
			delete prev;
		}

		this->vertices.erase(this->vertices.begin() + ind);
		this->n--;
		for (int i = 0; i < this->n; i++) this->removeFromList(key, i);
	}
}
template<typename vertT, typename edgeT>
void GraphMatrix<vertT, edgeT>::removeVertex(vertT key) {
	int ind = this->getInd(key);
	this->edges.erase(this->edges.begin() + ind);
	for (int i = 0; i < this->edges.size(); i++)
		this->edges[i].erase(this->edges[i].begin() + ind);
}

template <typename vertT>
void GraphList<vertT>::removeEdge(vertT vert1, vertT vert2) {
	int ind = this->getInd(vert1);
	this->removeFromList(vert2, ind);
	ind = this->getInd(vert2);
	this->removeFromList(vert1, ind);
}
template<typename T, typename edgeT>
void GraphMatrix<T, edgeT>::removeEdge(T vert1, T vert2) {
	int ind1 = this->getInd(vert1);
	int ind2 = this->getInd(vert2);
	if (ind1 != -1 && ind2 != -1) {
		this->edges[ind1][ind2] = NULL;
		this->edges[ind2][ind1] = NULL;
	}
}

template <typename vertT>
bool GraphList<vertT>::isConnected() {
	for (int i = 0; i < this->n; i++) {
		if (!this->vertices[i].next) return 0;
	}

	return 1;
}
template<typename vertT, typename edgeT>
bool GraphMatrix<vertT, edgeT>::isConnected() {
	int n = this->edges.size();
	bool row;
	for (int i = 0; i < n - 1; i++)
	{
		row = 0;
		for (int j = i + 1; j < n; j++)
		{
			row = row || this->edges[i][j];
		}
		if (!row) return 0;
	}

	return 1;
}

template <typename vertT>
bool GraphList<vertT>::isAcyclic() {
	bool* visited = new bool[this->n];
	node* ptr;
	for (int i = 0; i < this->n; i++) {
		ptr = this->vertices[i].next;
		while (ptr)
		{
			for (int j = 0; j < this->n; j++) visited[j] = 0;
			visited[i] = 1;
			if (!this->isAcyclicFromNode(visited, ptr, i)) return 0;
			ptr = ptr->next;
		}
	}

	return 1;
}
template<typename vertT, typename edgeT>
bool GraphMatrix<vertT, edgeT>::isAcyclic() {
	int n = this->vertices.size();
	bool* visited = new bool[n];
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++)
		{
			if (this->edges[i][j]) {
				for (int k = 0; k < n; k++) visited[j] = 0;
				visited[i] = 1;
				if (!this->isAcyclicFromNode(visited, j, i)) return 0;
			}
		}
	}

	return 1;
}
template <typename vertT>
bool GraphList<vertT>::isAcyclicFromNode(bool visited[], node* curr, int prev)
{
	int currInd = this->getInd(curr->data);
	if (visited[currInd]) return 0;
	visited[currInd] = 1;

	curr = this->vertices[currInd].next;
	while (curr)
	{
		if (this->getInd(curr->data) != prev) 
			if (!(this->isAcyclicFromNode(visited, curr, currInd))) return 0;
		curr = curr->next;
	}

	visited[currInd] = 0;
	return 1;
}
template <typename vertT, typename edgeT>
bool GraphMatrix<vertT, edgeT>::isAcyclicFromNode(bool visited[], int curr, int prev)
{
	if (visited[curr]) return 0;
	visited[curr] = 1;

	for (int j = 0; j < this->vertices.size(); j++)
		if (j != prev && this->edges[curr][j] && !this->isAcyclicFromNode(visited, j, curr)) return 0;

	visited[curr] = 0;
	return 1;
}

template <typename vertT>
int GraphList<vertT>::distance(vertT vert1, vertT vert2) {
	int ind1 = this->getInd(vert1);
	int ind2 = this->getInd(vert2);

	if (ind1 == -1 || ind2 == -1) return INT_MIN; // one of the vertices is absent

	bool* visited = new bool[this->n];
	int curr, i, dist = 0;
	node* ptr;
	std::queue<int> q;

	q.push(ind1);
	visited[ind1] = 1;
	while (!q.empty()) {
		curr = q.front();
		q.pop();

		ptr = this->vertices[curr].next;
		while(ptr)
		{
			i = this->getInd(ptr->data);
			if (i == ind2) return dist + 1;
			if (!visited[i]) {
				q.push(i);
				visited[i] = 1;
			}
		}
		dist++;
	}

	return INT_MAX; // there is no path from between vert1 and vert2
}
template<typename T>
void add(T data, std::vector<T>* res) {
	res->push_back(data);
}
template<> void add(int data, std::vector<int>* res) {
	if (res->size()) (*res)[0] = (*res)[0] + data;
	else res->push_back(0);
}
template<> void add(double data, std::vector<double>* res) {
	if (res->size()) (*res)[0] = (*res)[0] + data;
	else res->push_back(0);
}
template<> void add(std::string data, std::vector<std::string>* res) {
	if (res->size()) (*res)[0] = (*res)[0] + data;
	else res->push_back("");
}
template<typename vertT, typename edgeT>
std::vector<edgeT> GraphMatrix<vertT, edgeT>::distance(vertT vert1, vertT vert2) {
	int ind1 = this->getInd(vert1);
	int ind2 = this->getInd(vert2);

	if (ind1 == -1 || ind2 == -1) return INT_MIN; // one of the vertices is absent

	std::vector<edgeT> dist;
	add(*(this->edges[0][0]), &dist);

	int	curr, prev = ind1, n = this->vertices.size();
	bool* visited = new bool[n];
	std::queue<int> q;

	q.push(ind1);
	visited[ind1] = 1;
	while (!q.empty()) {
		curr = q.front();
		q.pop();
		add(*(this->edges[prev][curr]), &dist);

		if (this->edges[curr][ind2]) {
			add(*(this->edges[curr][ind2]), &dist);
			return dist;
		}
		for (int i = 0; i < n; i++)
		{
			if (!visited[i] && this->edges[curr][i] /*!= NULL*/) {
				q.push(i);
				visited[i] = 1;
			}
		}
		prev = curr;
	}

	return INT_MAX; // there is no path from between vert1 and vert2
}

template <typename vertT>
void GraphList<vertT>::output() {
	node* ptr;
	for (int i = 0; i < this->n; i++)
	{
		outputSingle(this->vertices[i].data);
		std::cout << ": ";
		ptr = this->vertices[i].next;
		while (ptr) {
			outputSingle(ptr->data);
			ptr = ptr->next;
		}
		std::cout << std::endl;
	}
}
template<typename vertT, typename edgeT>
void GraphMatrix<vertT, edgeT>::output() {
	for (int i = 0; i < this->edges.size(); i++)
		for (int j = 0; j < this->edges.size(); j++)
			if (this->edges[i][j]) {
				outputSingle(this->vertices[i]);
				std::cout << "---";
				outputSingle(*(this->edges[i][j]));
				std::cout << "---";
				outputSingle(this->vertices[j]);
				std::cout << std::endl;
			}
}