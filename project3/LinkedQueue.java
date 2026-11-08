import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 * A first-in, first-out queue built from singly linked nodes with head and
 * tail references.
 *
 * This class only owns storage: nodes, the head and tail references, and
 * the count. It knows nothing about teams or pickup games.
 *
 * Time complexity, where n is the number of items:
 *   enqueue  O(1)
 *   dequeue  O(1)
 *   peek     O(1)
 *   isEmpty  O(1)
 *   size     O(1)
 *   toList   O(n)
 *
 * @author
 * @version 7 Nov 2026
 *
 * @param <T> the type of items stored in the queue
 */
public class LinkedQueue<T>
{

    // ~ Fields ................................................................

    private Node<T> head; // front of the queue (next out), or null
    private Node<T> tail; // back of the queue (last in), or null
    private int size;     // number of items in the queue

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates an empty queue.
     */
    public LinkedQueue()
    {
        // TODO: size zero; head and tail null
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Adds an item at the back of the queue.
     *
     * @param item the item to add; must not be null
     * @throws IllegalArgumentException if item is null
     */
    public void enqueue(T item)
    {
        // TODO: reject null, link a new node behind tail, size++
    }


    // ----------------------------------------------------------
    /**
     * Removes and returns the item at the front of the queue.
     *
     * @return the front item
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue()
    {
        // TODO: move head forward, size--; set tail to null when emptied
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Returns the item at the front of the queue without removing it.
     *
     * @return the front item
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek()
    {
        // TODO
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Checks whether the queue has no items.
     *
     * @return true if size is zero
     */
    public boolean isEmpty()
    {
        // TODO
        return false;
    }


    // ----------------------------------------------------------
    /**
     * Gets the number of items in the queue.
     *
     * @return the item count, including zero
     */
    public int size()
    {
        // TODO
        return 0;
    }


    // ----------------------------------------------------------
    /**
     * Returns a new list of the items in FIFO order without removing them.
     * Changing the list does not change the queue.
     *
     * @return the items from head to tail, or an empty list
     */
    public List<T> toList()
    {
        // TODO: walk from head to tail and copy each item
        return new ArrayList<T>();
    }

    // ~ Private Classes .......................................................


    // ----------------------------------------------------------
    /**
     * One link in the queue. It holds one item and the next node.
     *
     * @param <E> the type of item stored in the node
     */
    private static class Node<E>
    {
        private final E data; // item stored in this node
        private Node<E> next; // link to the next node, or null

        /**
         * Creates a node with the given data and no next node.
         *
         * @param data the item to store
         */
        private Node(E data)
        {
            this.data = data;
            this.next = null;
        }
    }
}
