package ass2;

public class MyStack<T> implements StackInterface<T> {
    private MiniList<T> list = new MiniList<T>();

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void push(T data) {
        list.addFirst(data);
    }

    public T pop() {
        return list.removeFirst();
    }

    public T peek() {
        return list.getFirst();
    }

    public String toString() {
        return list.toString();
    }

    
}
