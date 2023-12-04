package task5;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;

class Node{
    final int key;
    final int level;
    final AtomicMarkableReference<Node>[] next;
    boolean deleted;


    @SuppressWarnings("unchecked")
    public Node(int key, int level, int maxLevel){
        this.key = key;
        this.level = level;
        this.next = (AtomicMarkableReference<Node>[])
                new AtomicMarkableReference<?>[maxLevel];
        this.deleted = false;
    }

    @Override
    public String toString(){
        return Integer.toString(key);
    }

    public int getKey() {
        return key;
    }
    public AtomicMarkableReference<Node>[] getNext() {
        return next;
    }
}

public class SkipList {
    final AtomicMarkableReference<Node> head;
    final AtomicInteger currLvl;
    final int maxLvl;
    final AtomicInteger size;
    SecureRandom random = new SecureRandom();

    public SkipList(int maxLevel){
        this.currLvl = new AtomicInteger(0);
        this.size = new AtomicInteger(0);
        this.maxLvl = maxLevel;

        Node headerNode = new Node(Integer.MAX_VALUE, 0, maxLevel);
        head = new AtomicMarkableReference<>(headerNode, true);

        for (int i = 0; i < maxLevel; i++){
            headerNode.next[i] = head;
        }
    }

    public synchronized boolean add(Integer toAdd){
        Node[] nodes = new Node[maxLvl];
        Node currNode = updateNodes(nodes, toAdd);
        int lvls = currLvl.get();

        if (currNode.key == toAdd) {
            return false;
        } else {
            int newLvl = randomLvl(maxLvl);

            if (newLvl >= lvls) {
                currLvl.incrementAndGet();
                lvls += 1;
                newLvl = lvls - 1;
                nodes[newLvl] = head.getReference();
            }

            Node newNode = new Node(toAdd, newLvl, maxLvl);
            AtomicMarkableReference<Node> atomicNewNode = new AtomicMarkableReference<>(newNode, true);
            for (int lvl = 0; lvl < lvls; lvl++) {

                if (nodes[lvl].deleted && lvl == 0) {
                    return add(toAdd);
                }

                if (nodes[lvl].deleted) {
                    return true;
                }

                if (lvl > 0) {
                    if (nodes[lvl - 1].next[lvl - 1].getReference().deleted) {
                        return true;
                    }
                }

                newNode.next[lvl] = nodes[lvl].next[lvl];
                nodes[lvl].next[lvl] = atomicNewNode;
            }

            size.incrementAndGet();

            return true;
        }
    }
    public boolean remove(Integer toRemove){
        Node[] nodes = new Node[maxLvl];
        Node currNode = updateNodes(nodes, toRemove);
        int lvls = currLvl.get();

        if (currNode.key == toRemove) {
            currNode.deleted = true;
            //for (int level = 0; level < lvls; level++) {
            for (int level = lvls - 1; level >= 0; level--) {
                nodes[level].next[level] = currNode.next[level];

                if (nodes[level].next[level] == null) {
                    nodes[level].next[level] = head;
                }
            }

            size.decrementAndGet();

            while (lvls > 1 && head.getReference().next[lvls] == head) {
                lvls = currLvl.decrementAndGet();
            }

            return true;
        }

        return false;
    }
    public boolean find(int value){
        AtomicMarkableReference<Node> currEl = this.head;
        AtomicMarkableReference<Node> prevEl = this.head;
        Node currNode = currEl.getReference();

        for (int level = currLvl.get() - 1; level >= 0; level--) {
            currEl = currNode.next[level];
            currNode = currEl.getReference();

            while (currNode.key < value) {
                if (level == currLvl.get() - 1 && currEl == this.head) {
                    return false;
                }

                prevEl = currEl;
                currEl = currNode.next[level];
                currNode = currEl.getReference();
            }
            currEl = prevEl;
            currNode = currEl.getReference();
        }
        currEl = currNode.next[0];
        currNode = currEl.getReference();

        return currNode.key == value;
    }

    private Node updateNodes(Node[] forUpdated, Integer value){
        AtomicMarkableReference<Node> currEl = this.head;
        AtomicMarkableReference<Node> prevEl;
        Node currNode = currEl.getReference();

        for (int lvl = currLvl.get() - 1; lvl >= 0; lvl--) {
            prevEl = currEl;
            currEl = currNode.next[lvl];
            currNode = currEl.getReference();

            while (currNode.key < value){
                if (currNode.deleted) {
                    eraseNode(prevEl.getReference(), lvl);
                    return updateNodes(forUpdated, value);
                }

                prevEl = currEl;
                currEl = currNode.next[lvl];
                currNode = currEl.getReference();
            }

            forUpdated[lvl] = prevEl.getReference();
            currEl = prevEl;
            currNode = currEl.getReference();
        }

        currEl = currNode.next[0];
        currNode = currEl.getReference();

        return currNode;
    }

    private void eraseNode(Node prevEl, int lvl){
        prevEl.next[lvl] = prevEl.next[lvl].getReference().next[lvl];
    }

    @Override
    public String toString() {
        AtomicMarkableReference<Node> curr = head;
        Node currNode = curr.getReference();
        curr = currNode.next[0];
        currNode = curr.getReference();

        StringBuilder out = new StringBuilder();
        out.append("[");
        while(curr != head){
            out.append(currNode.key);
            curr = currNode.next[0];

            currNode = curr.getReference();
            if(curr != head){
                out.append(" ");
            }
        }
        out.append("]");

        return out.toString();
    }

    public AtomicInteger getSize(){
        return size;
    }
    public AtomicMarkableReference<Node> getHead(){
        return head;
    }
    private int randomLvl(int maxLvl){;
        int lvl = 0;
        while (lvl < maxLvl - 1 && random.nextFloat() < 0.5f) {
            lvl += 1;
        }
        return lvl;
    }
}