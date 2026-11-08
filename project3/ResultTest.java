import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the Result class.
 *
 * @author
 * @version 7 Nov 2026
 */
public class ResultTest
    extends TestCase
{

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Sets up each test method.
     */
    public void setUp()
    {
        // TODO
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Tests the constructor
     */
    public void testConstructor()
    {
        // TODO Normal: stores the success flag and the trimmed message
        // TODO Bad input: null or blank message throws IllegalArgumentException
    }


    // ----------------------------------------------------------
    /**
     * Tests isSuccess()
     */
    public void testIsSuccess()
    {
        // TODO Normal: returns true for a successful result
        // TODO Boundary case: returns false for a failed result
    }


    // ----------------------------------------------------------
    /**
     * Tests getMessage()
     */
    public void testGetMessage()
    {
        // TODO Normal: returns the message given
        // TODO Immutability check: a message with surrounding spaces is returned trimmed
    }
}
