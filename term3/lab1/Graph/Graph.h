#pragma once
#include "../Figures/Figures.h"
#include <iostream>
#include <vector>
#include <queue>

template <typename vertT> class GraphList;
template <typename vertT, typename edgeT> class GraphMatrix;

class UndirectedGraph {
protected:

	virtual bool isConnected() = 0;
	virtual bool isAcyclic() = 0;
	virtual void output() = 0;
};

template <typename vertT> class GraphList: public UndirectedGraph {
private:
	int n = 0;
	struct node {
		vertT data;
		node* next;
	};
	std::vector<node> vertices;

	int getInd(vertT key);
	void removeFromList(vertT key, int i);
	bool isAcyclicFromNode(bool visited[], node* curr, int prev);
public:
	void addVertex(vertT data);
	void addEdge(vertT vert1, vertT vert2);
	void removeVertex(vertT key);
	void removeEdge(vertT vert1, vertT vert2);
	
	bool isConnected() override;
	bool isAcyclic() override;

	int distance(vertT vert1, vertT vert2);

	void output() override;
};

template <typename vertT, typename edgeT> class GraphMatrix: public UndirectedGraph {
private:
	std::vector<vertT> vertices;
	std::vector<std::vector<edgeT*>> edges;

	int getInd(vertT key);
	bool isAcyclicFromNode(bool visited[], int curr, int prev);
public:
	void addVertex(vertT data);
	void addEdge(vertT vert1, vertT vert2, edgeT data);
	void removeVertex(vertT key);
	void removeEdge(vertT vert1, vertT vert2);

	bool isConnected() override;
	bool isAcyclic() override;

	std::vector<edgeT> distance(vertT vert1, vertT vert2);

	void output() override;
};
