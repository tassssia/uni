package task6;

import java.util.concurrent.atomic.AtomicReference;

class Node<T> {
    T data;
    AtomicReference<Node<T>> next;

    public Node(T data) {
        this.data = data;
        this.next = new AtomicReference<>(null);
    }
}

public class NonBlockingQueue<T> {
    private AtomicReference<Node<T>> head;
    private AtomicReference<Node<T>> tail;

    public NonBlockingQueue() {
        Node<T> sentinel = new Node<>(null);

        this.head = new AtomicReference<>(sentinel);
        this.tail = new AtomicReference<>(sentinel);
    }

    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);

        while (true) {
            Node<T> currentTail = tail.get();
            Node<T> next = currentTail.next.get();

            if (currentTail == tail.get()) {

                if (next == null) {
                    if (currentTail.next.compareAndSet(null, newNode)) {
                        tail.compareAndSet(currentTail, newNode);
                        return;
                    }

                } else {
                    tail.compareAndSet(currentTail, next);
                }
            }
        }
    }

    public T dequeue() {
        while (true) {
            Node<T> currentHead = head.get();
            Node<T> currentTail = tail.get();
            Node<T> firstNode = currentHead.next.get();

            if (currentHead == head.get()) {

                if (currentHead == currentTail) {
                    if (firstNode == null) {
                        return null;
                    }
                    tail.compareAndSet(currentTail, firstNode);

                } else {
                    if (head.compareAndSet(currentHead, firstNode)) {
                        return firstNode.data;
                    }
                }
            }
        }
    }
}
