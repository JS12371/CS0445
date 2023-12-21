package ass2;

public class MyQueue<T> implements QueueInterface<T> {
    private MiniList<T> list = new MiniList<T>();

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enqueue(T data) {
        list.addLast(data);
    }

    public T dequeue() {
        return list.removeFirst();
    }

    public T front() {
        return list.getFirst();
    }

    public String toString() {
        return list.toString();
    }

    
}
