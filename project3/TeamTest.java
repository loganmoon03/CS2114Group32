import java.util.Arrays;
import java.util.List;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the Team class.
 *
 * @author
 * @version 7 Nov 2026
 */
public class TeamTest
    extends TestCase
{

    // ~ Fields ................................................................

    private List<String> players;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Sets up each test method.
     */
    public void setUp()
    {
        players = Arrays.asList("A", "B", "C", "D", "E");
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Tests the constructor
     */
    public void testConstructor()
    {
        // TODO Normal: valid name and five distinct players creates a team; names are trimmed
    }


    // ----------------------------------------------------------
    /**
     * Tests the constructor with a bad team name
     */
    public void testConstructorBadName()
    {
        // TODO Bad input: null or blank team name throws IllegalArgumentException
    }


    // ----------------------------------------------------------
    /**
     * Tests the constructor with a bad roster
     */
    public void testConstructorBadRoster()
    {
        // TODO Bad input: null roster or four or six players throws IllegalArgumentException
        // TODO Bad input: blank player or repeated name ignoring case ("Sam", " sam") throws IllegalArgumentException
    }


    // ----------------------------------------------------------
    /**
     * Tests getName()
     */
    public void testGetName()
    {
        // TODO Normal: returns the trimmed name with capitalization preserved
        // TODO Immutability check: reassigning the caller's name variable does not change the stored name
    }


    // ----------------------------------------------------------
    /**
     * Tests getPlayers()
     */
    public void testGetPlayers()
    {
        // TODO Normal: returns the five players in the order given
        // TODO Immutability check: modifying the returned list throws UnsupportedOperationException; changing the original list does not change the team
    }
}
