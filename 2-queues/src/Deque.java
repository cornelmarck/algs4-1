import java.util.Iterator;
import java.util.NoSuchElementException;

//Use the linked list implementation due to performance requirements (Constant worst case time).
public class Deque<Item> implements Iterable<Item> {
    private Node front;
    private Node back;
    private int numberOfNodes; //Take implementation to guarantee constant size() function time.

    // construct an empty deque
    public Deque() {}

    private class Node {
        Item i;
        Node prev;
        Node next;

        public Node(Item item) {
            i = item;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (numberOfNodes==0);
    }

    // return the number of items on the deque
    public int size() {
        return numberOfNodes;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item==null) {
            throw new IllegalArgumentException();
        }

        Node x = new Node(item);
        if (isEmpty()) {
            back = x;
        }
        else {
            x.next = front;
            front.prev = x;
        }
        front = x;
        numberOfNodes++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item==null) {
            throw new IllegalArgumentException();
        }

        Node x = new Node(item);
        if (isEmpty()) {
            front = x;
        }
        else {
            x.prev = back;
            back.next = x;
        }
        back = x;
        numberOfNodes++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = front.i;
        if (numberOfNodes==1) {
            back = null;
            front = null;
        }
        else {
            front = front.next;
            front.prev = null;
        }
        numberOfNodes--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = back.i;
        if (numberOfNodes==1) {
            front = null;
            back = null;
        }
        else {
            back = back.prev;
            back.next = null;
        }
        numberOfNodes--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = front;

        public boolean hasNext() {
            return (current != null);
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.i;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        assert d.isEmpty();
        d.addFirst(4);
        assert d.size() == 1;
        assert d.front == d.back;

        d.removeFirst();
    }
}
