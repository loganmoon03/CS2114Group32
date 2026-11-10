import java.util.Arrays;
import java.util.List;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the Match class without any view.
 *
 * @author Shalin Patel (shalinkpatel)
 * @version 9 Nov 2026
 */
public class MatchTest
    extends TestCase
{

    // ~ Fields ................................................................

    private Team lakers;
    private Team bulls;
    private Match match;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Sets up each test method.
     */
    public void setUp()
    {
        List<String> players = Arrays.asList("A", "B", "C", "D", "E");
        lakers = new Team("Lakers", players);
        bulls = new Team("Bulls", players);
        match = new Match(lakers, bulls);
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Tests the constructor with two different teams
     */
    public void testConstructor()
    {
        assertSame(lakers, match.getHomeTeam());
        assertSame(bulls, match.getAwayTeam());
    }


    // ----------------------------------------------------------
    /**
     * Tests the constructor with null teams
     */
    public void testConstructorNullTeam()
    {
        assertBadMatch(null, bulls);
        assertBadMatch(lakers, null);
        assertBadMatch(null, null);
    }


    // ----------------------------------------------------------
    /**
     * Tests the constructor with two teams that have the same name
     */
    public void testConstructorSameName()
    {
        Team otherLakers = new Team("LAKERS",
            Arrays.asList("F", "G", "H", "I", "J"));
        assertBadMatch(lakers, otherLakers);
        assertBadMatch(lakers, lakers);
    }


    // ----------------------------------------------------------
    /**
     * Tests getHomeTeam()
     */
    public void testGetHomeTeam()
    {
        assertSame(lakers, match.getHomeTeam());
        // same team on every call; there is no setter to change it
        assertSame(match.getHomeTeam(), match.getHomeTeam());
        assertNotSame(bulls, match.getHomeTeam());
    }


    // ----------------------------------------------------------
    /**
     * Tests getAwayTeam()
     */
    public void testGetAwayTeam()
    {
        assertSame(bulls, match.getAwayTeam());
        // same team on every call; there is no setter to change it
        assertSame(match.getAwayTeam(), match.getAwayTeam());
        assertNotSame(lakers, match.getAwayTeam());
    }


    // ----------------------------------------------------------
    /**
     * Checks that making a match with these teams throws an
     * IllegalArgumentException.
     *
     * @param home the proposed home team
     * @param away the proposed away team
     */
    private void assertBadMatch(Team home, Team away)
    {
        Exception thrown = null;
        try
        {
            new Match(home, away);
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IllegalArgumentException);
    }
}
