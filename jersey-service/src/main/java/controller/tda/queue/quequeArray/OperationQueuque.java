package controller.tda.queue.quequeArray;

import controller.tda.array.ArrayPositionException;

public class OperationQueuque<E> {
    private E[] elements;
    private int front;
    private int rear;
    private int maxSize;
    private int currentSize;

    @SuppressWarnings("unchecked")
    public OperationQueuque(int size) {
        this.maxSize = size;
        this.elements = (E[]) new Object[maxSize];
        this.front = 0;
        this.rear = -1;
        this.currentSize = 0;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public boolean isFull() {
        return currentSize == maxSize;
    }

    public void queuque(E data) throws ArrayPositionException {
        if (isFull()) {
            throw new ArrayPositionException("La cola está llena");
        } else {
            rear = (rear + 1) % maxSize;
            elements[rear] = data;
            currentSize++;
        }
    }

    public E dequeuque() throws ArrayPositionException {
        if (isEmpty()) {
            throw new ArrayPositionException("La cola está vacía");
        } else {
            E data = elements[front];
            front = (front + 1) % maxSize;
            currentSize--;
            return data;
        }
    }

    public int getSize() {
        return currentSize;
    }

    public void reset() {
        front = 0;
        rear = -1;
        currentSize = 0;
    }
}
