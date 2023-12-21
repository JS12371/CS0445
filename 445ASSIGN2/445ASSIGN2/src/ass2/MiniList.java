package ass2;
import java.util.NoSuchElementException;

public class MiniList<E>{


private  Node<E> head;
private  Node<E> tail;

public MiniList(){
    this.head = null;
}

public boolean isEmpty(){
    return (this.head == null);
}

public void addFirst(E data){
    Node<E> newNode = new Node(data, this.head);
    this.head = newNode;
    if (this.tail == null){
        this.tail = this.head;
    }
}

public E getFirst(){
    if (this.head == null){
        throw new NoSuchElementException();
    }
    return this.head.data;
}

public E removeFirst(){
    if (this.head == null){
        throw new NoSuchElementException();
    }
    E data = this.head.data;
    this.head = this.head.next;
    if (this.head == null){
        this.tail = null;
    }
    return data;
}

public void addLast(E data) {
    Node<E> newNode = new Node(data, null);
    if (this.head == null) {
        this.head = newNode;
        this.tail = newNode;
    } 
    else {
        this.tail.next = newNode;
        this.tail = this.tail.next;
    }
    


}

public E removeLast() {
    if (this.head == null) {
        throw new NoSuchElementException();
    }
    Node<E> curr = this.head;
    Node<E> prev = null;
    while (curr.next != null) {
        prev = curr;
        curr = curr.next;
    }
    E data = curr.data;
    if (prev == null) {
        this.head = null;
    } else {
        prev.next = null;
    }
    return data;

}

public String toString(){
    String result = "";
    Node<E> curr = this.head;
    int i = 0;
    while (curr != null) {
        result += i++ + ": ";
        result += curr.data + "\n";
        curr = curr.next;
    }
    return result;

}

public void printList() {
    Node<E> curr = this.head;
    int i = 0;
    while (curr != null) {
        System.out.print(i++ + ": ");
        System.out.println(curr.data);
        curr = curr.next;
    }
}


public static void main(String[] args){


    MiniList<String> list = new MiniList<String>();
    list.addFirst("third");
    list.addFirst("second");
    list.addFirst("first");
    list.printList();
    System.out.println(list.isEmpty());

    // write a loop that removes every element of the list and prints the element it removes

    while (!list.isEmpty()) {
        System.out.println(list.removeFirst());
    }


    list.addFirst("third");
    list.addFirst("second");
    list.addFirst("first");

    list.printList();

    System.out.println(list.isEmpty());

    list.addLast("fourth");

    System.out.println("");
    list.printList();

    

}


}




class Node<E>{
    public E data;
    public Node<E> next;

    public Node (E data, Node<E> next){
        this.data = data;
        this.next = next;
    }

    public String toString(){
        return this.data.toString();
    }

}

