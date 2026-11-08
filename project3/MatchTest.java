import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the Match class.
 *
 * @author
 * @version 7 Nov 2026
 */
public class MatchTest
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
        // TODO Normal: two different teams creates a match
        // TODO Bad input: null team or same name ignoring case throws IllegalArgumentException
    }


    // ----------------------------------------------------------
    /**
     * Tests getHomeTeam()
     */
    public void testGetHomeTeam()
    {
        // TODO Normal: returns the first team given
        // TODO Immutability check: returns the same team on every call
    }


    // ----------------------------------------------------------
    /**
     * Tests getAwayTeam()
     */
    public void testGetAwayTeam()
    {
        // TODO Normal: returns the second team given
        // TODO Immutability check: returns the same team on every call
    }
}
