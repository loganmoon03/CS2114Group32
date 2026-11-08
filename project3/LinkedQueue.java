import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 * A first-in, first-out queue made of linked nodes.
 * It keeps a head (front) and a tail (back) reference.
 *
 * @author Dochan (Logan) Moon
 * @version 7 Nov 2026
 *
 * @param <T> the type of items in the queue
 */
public class LinkedQueue<T>
{

    // ~ Fields ................................................................

    private Node<T> head; // front of the queue, or null
    private Node<T> tail; // back of the queue, or null
    private int size;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates an empty queue.
     */
    public LinkedQueue()
    {
        head = null;
        tail = null;
        size = 0;
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Adds an item to the back of the queue.
     *
     * @param item the item to add
     * @throws IllegalArgumentException if item is null
     */
    public void enqueue(T item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Item cannot be null");
        }

        Node<T> newNode = new Node<T>(item);

        if (isEmpty())
        {
            head = newNode;
        }
        else
        {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }


    // ----------------------------------------------------------
    /**
     * Removes and returns the item at the front.
     *
     * @return the front item
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("Queue is empty");
        }

        T item = head.data;
        head = head.next;
        size--;

        // the queue is empty now, so the tail is gone too
        if (head == null)
        {
            tail = null;
        }
        return item;
    }


    // ----------------------------------------------------------
    /**
     * Gets the item at the front without removing it.
     *
     * @return the front item
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek()
    {
        if (isEmpty())
        {
            throw new NoSuchElementException("Queue is empty");
        }
        return head.data;
    }


    // ----------------------------------------------------------
    /**
     * Checks if the queue has no items.
     *
     * @return true if the queue is empty
     */
    public boolean isEmpty()
    {
        return size == 0;
    }


    // ----------------------------------------------------------
    /**
     * Gets the number of items in the queue.
     *
     * @return the number of items
     */
    public int size()
    {
        return size;
    }


    // ----------------------------------------------------------
    /**
     * Gets all items from front to back without removing them.
     * Changing the list does not change the queue.
     *
     * @return a new list of the items
     */
    public List<T> toList()
    {
        List<T> list = new ArrayList<T>();

        Node<T> current = head;
        while (current != null)
        {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    // ~ Private Classes .......................................................


    // ----------------------------------------------------------
    /**
     * One node in the queue. It holds one item and the next node.
     *
     * @param <E> the type of item in the node
     */
    private static class Node<E>
    {
        private final E data;
        private Node<E> next;

        /**
         * Creates a node with no next node.
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
