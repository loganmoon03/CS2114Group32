import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the LinkedQueue class without any view.
 *
 * @author Shalin Patel (shalinkpatel)
 * @version 9 Nov 2026
 */
public class LinkedQueueTest
    extends TestCase
{

    // ~ Fields ................................................................

    private LinkedQueue<String> queue;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Sets up each test method with an empty queue.
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
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertTrue(queue.toList().isEmpty());

        Exception thrown = null;
        try
        {
            queue.dequeue();
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof NoSuchElementException);
    }


    // ----------------------------------------------------------
    /**
     * Tests enqueue()
     */
    public void testEnqueue()
    {
        queue.enqueue("A");
        assertEquals(1, queue.size());
        assertEquals("A", queue.peek());

        queue.enqueue("B");
        assertEquals(2, queue.size());
        // the front item does not change when adding at the back
        assertEquals("A", queue.peek());
        assertEquals(Arrays.asList("A", "B"), queue.toList());
    }


    // ----------------------------------------------------------
    /**
     * Tests enqueue() with a null item
     */
    public void testEnqueueNull()
    {
        queue.enqueue("A");

        Exception thrown = null;
        try
        {
            queue.enqueue(null);
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IllegalArgumentException);
        assertEquals(1, queue.size());
        assertEquals(Arrays.asList("A"), queue.toList());
    }


    // ----------------------------------------------------------
    /**
     * Tests dequeue()
     */
    public void testDequeue()
    {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        assertEquals("A", queue.dequeue());
        assertEquals("B", queue.dequeue());
        assertEquals(1, queue.size());
        assertEquals("C", queue.dequeue());
        assertTrue(queue.isEmpty());

        // removing the last item resets the queue, so it still works
        queue.enqueue("D");
        assertEquals(1, queue.size());
        assertEquals("D", queue.peek());
        assertEquals(Arrays.asList("D"), queue.toList());
    }


    // ----------------------------------------------------------
    /**
     * Tests dequeue() on an empty queue
     */
    public void testDequeueEmpty()
    {
        queue.enqueue("A");
        queue.dequeue();

        Exception thrown = null;
        try
        {
            queue.dequeue();
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof NoSuchElementException);
        assertEquals(0, queue.size());
    }


    // ----------------------------------------------------------
    /**
     * Tests peek()
     */
    public void testPeek()
    {
        queue.enqueue("A");
        queue.enqueue("B");

        assertEquals("A", queue.peek());
        assertEquals("A", queue.peek());
        assertEquals(2, queue.size());
    }


    // ----------------------------------------------------------
    /**
     * Tests peek() on an empty queue
     */
    public void testPeekEmpty()
    {
        Exception thrown = null;
        try
        {
            queue.peek();
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof NoSuchElementException);
        assertTrue(queue.isEmpty());
    }


    // ----------------------------------------------------------
    /**
     * Tests isEmpty()
     */
    public void testIsEmpty()
    {
        assertTrue(queue.isEmpty());
        queue.enqueue("A");
        assertFalse(queue.isEmpty());
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }


    // ----------------------------------------------------------
    /**
     * Tests size()
     */
    public void testSize()
    {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.dequeue();
        assertEquals(2, queue.size());

        queue.dequeue();
        queue.dequeue();
        assertEquals(0, queue.size());

        // a rejected item does not change the size
        try
        {
            queue.enqueue(null);
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
        assertEquals(0, queue.size());
    }


    // ----------------------------------------------------------
    /**
     * Tests toList()
     */
    public void testToList()
    {
        assertTrue(queue.toList().isEmpty());

        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");

        List<String> list = queue.toList();
        assertEquals(Arrays.asList("A", "B", "C"), list);
        assertEquals(3, queue.size());

        // changing the returned list does not change the queue
        list.clear();
        assertEquals(3, queue.size());
        assertEquals("A", queue.peek());
        assertEquals(Arrays.asList("A", "B", "C"), queue.toList());
    }
}
