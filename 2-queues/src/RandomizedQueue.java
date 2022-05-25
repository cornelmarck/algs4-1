import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    //initial capacity of resizing array
    private static final int INIT_CAPACITY = 2;

    private Item[] q;
    private int numberOfItems = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (numberOfItems == 0);
    }

    // return the number of items on the deque
    public int size() {
        return numberOfItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (numberOfItems == q.length) {
            resize(2*q.length);
        }
        q[numberOfItems] = item;
        numberOfItems++;
    }

    // remove and return a random item
    public Item dequeue() {
        int randomInt = StdRandom.uniform(0, numberOfItems);
        Item it = q[randomInt];

        //Re-arrange the array
        q[randomInt] = q[numberOfItems-1];
        q[numberOfItems-1] = null;
        numberOfItems--;

        if (numberOfItems > 0 && numberOfItems == q.length/4) {
            resize(q.length/2);
        }
        return it;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int randomInt = StdRandom.uniform(0, numberOfItems);
        Item it = q[randomInt];
        return it;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {
        int[] idxOrder = StdRandom.permutation(numberOfItems);
        int i;

        public boolean hasNext() {
            return i <= numberOfItems-1;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = q[idxOrder[i]];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    //Shrink the array by half
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        int smallest = Math.min(capacity, q.length);
        for (int i=0; i< smallest; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();

        int n = 100;
        for (int i=0; i<n; i++) {q.enqueue(Integer.toString(i));}
        assert q.size()==n;
        for (int i=0; i<n; i++) {q.dequeue();}



    }

}