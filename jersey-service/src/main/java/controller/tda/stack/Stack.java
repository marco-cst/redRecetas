package controller.tda.stack;
import controller.tda.list.ListEmptyException;

public class Stack<E> {
    private OperationStack<E> operationStack;

    public Stack(Integer cant) {
        this.operationStack = new OperationStack<>(cant);
    }

    public void push(E element) throws ListEmptyException {
        operationStack.push(element);
    }

    public Integer getSize() {
        return operationStack.getSize();
    }

    public void clear() {
        operationStack.reset();
    }

    public Integer getTop() {
        return operationStack.getTop();
    }

   
    
}
