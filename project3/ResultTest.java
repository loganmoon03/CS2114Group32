import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the Result class without any view.
 *
 * @author Shalin Patel (shalinkpatel)
 * @version 9 Nov 2026
 */
public class ResultTest
    extends TestCase
{

    // ~ Fields ................................................................

    private Result success;
    private Result failure;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Sets up each test method.
     */
    public void setUp()
    {
        success = new Result(true, "Team Lakers was added");
        failure = new Result(false, "Not enough teams waiting");
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Tests the constructor with valid data
     */
    public void testConstructor()
    {
        assertTrue(success.isSuccess());
        assertEquals("Team Lakers was added", success.getMessage());
        assertFalse(failure.isSuccess());
        assertEquals("Not enough teams waiting", failure.getMessage());
    }


    // ----------------------------------------------------------
    /**
     * Tests the constructor with a bad message
     */
    public void testConstructorBadMessage()
    {
        assertBadResult(null);
        assertBadResult("");
        assertBadResult("   ");
    }


    // ----------------------------------------------------------
    /**
     * Tests isSuccess()
     */
    public void testIsSuccess()
    {
        assertTrue(success.isSuccess());
        assertFalse(failure.isSuccess());
    }


    // ----------------------------------------------------------
    /**
     * Tests getMessage()
     */
    public void testGetMessage()
    {
        assertEquals("Team Lakers was added", success.getMessage());
        Result padded = new Result(false, "  No match in progress  ");
        assertEquals("No match in progress", padded.getMessage());
    }


    // ----------------------------------------------------------
    /**
     * Checks that making a result with this message throws an
     * IllegalArgumentException.
     *
     * @param message the proposed message
     */
    private void assertBadResult(String message)
    {
        Exception thrown = null;
        try
        {
            new Result(true, message);
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IllegalArgumentException);
    }
}
