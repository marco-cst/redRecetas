package controller.tda.stack.stackArray;

import controller.tda.array.ArrayPositionException;

public class OperationStack<E> {
    private E[] elements;
    private int top;
    private int maxSize;

    @SuppressWarnings("unchecked")
    public OperationStack(int size) {
        this.maxSize = size;
        this.elements = (E[]) new Object[maxSize];
        this.top = -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == maxSize - 1;
    }

    public void push(E data) throws ArrayPositionException {
        if (isFull()) {
            throw new ArrayPositionException("La pila está llena");
        } else {
            elements[++top] = data;
        }
    }

    public E pop() throws ArrayPositionException {
        if (isEmpty()) {
            throw new ArrayPositionException("La pila está vacía");
        } else {
            return elements[top--];
        }
    }

    public int getSize() {
        return top + 1;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void reset() {
        top = -1;
    }

    public Integer getTop() {
        return top;
    }

    public E peek() throws ArrayPositionException {
        if (isEmpty()) {
            throw new ArrayPositionException("La pila está vacía");
        }
        return elements[top];
    }
}
