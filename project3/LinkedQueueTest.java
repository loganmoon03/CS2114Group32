import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the LinkedQueue class.
 *
 * @author
 * @version 7 Nov 2026
 */
public class LinkedQueueTest
    extends TestCase
{

    // ~ Fields ................................................................

    private LinkedQueue<String> queue;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Sets up each test method.
     */
    public void setUp()
    {
        queue = new LinkedQueue<String>();
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Tests the constructor
     */
    public void testConstructor()
    {
        // TODO Normal: new queue is empty: isEmpty() is true and size() is 0
        // TODO Empty case: dequeue() on a new queue throws NoSuchElementException
    }


    // ----------------------------------------------------------
    /**
     * Tests enqueue()
     */
    public void testEnqueue()
    {
        // TODO Normal: item is added at the back, size goes up by one, peek() still returns the first item
    }


    // ----------------------------------------------------------
    /**
     * Tests enqueue() with null
     */
    public void testEnqueueNull()
    {
        // TODO Bad input: IllegalArgumentException is thrown and size is unchanged
    }


    // ----------------------------------------------------------
    /**
     * Tests dequeue()
     */
    public void testDequeue()
    {
        // TODO Normal: enqueue A, B, C returns A, then B, then C
        // TODO Boundary case: removing the last item empties the queue, and a new enqueue still works
    }


    // ----------------------------------------------------------
    /**
     * Tests dequeue() on an empty queue
     */
    public void testDequeueEmpty()
    {
        // TODO Empty case: NoSuchElementException is thrown
    }


    // ----------------------------------------------------------
    /**
     * Tests peek()
     */
    public void testPeek()
    {
        // TODO Normal: returns the front item without removing it; size is unchanged
        // TODO Empty case: empty queue throws NoSuchElementException
    }


    // ----------------------------------------------------------
    /**
     * Tests isEmpty()
     */
    public void testIsEmpty()
    {
        // TODO Normal: returns false after an item is added
        // TODO Boundary case: returns true again after every item is dequeued
    }


    // ----------------------------------------------------------
    /**
     * Tests size()
     */
    public void testSize()
    {
        // TODO Normal: returns the count after several enqueues and dequeues
        // TODO Empty case: returns 0 after a rejected enqueue(null)
    }


    // ----------------------------------------------------------
    /**
     * Tests toList()
     */
    public void testToList()
    {
        // TODO Normal: returns items in FIFO order; size is unchanged
        // TODO Empty case: empty queue returns an empty list; changing the list does not change the queue
    }
}
