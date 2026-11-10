import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the Team class without any view.
 *
 * @author Shalin Patel (shalinkpatel)
 * @version 9 Nov 2026
 */
public class TeamTest
    extends TestCase
{

    // ~ Fields ................................................................

    private List<String> players;
    private Team team;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Sets up each test method.
     */
    public void setUp()
    {
        players = Arrays.asList("A", "B", "C", "D", "E");
        team = new Team("Lakers", players);
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Tests the constructor with valid data
     */
    public void testConstructor()
    {
        Team trimmed = new Team(" Lakers ",
            Arrays.asList(" Ann ", "Bo", "Cy ", " Dee", "Eve"));
        assertEquals("Lakers", trimmed.getName());
        assertEquals(Arrays.asList("Ann", "Bo", "Cy", "Dee", "Eve"),
            trimmed.getPlayers());
    }


    // ----------------------------------------------------------
    /**
     * Tests the constructor with a bad team name
     */
    public void testConstructorBadName()
    {
        assertBadTeam(null, players);
        assertBadTeam("", players);
        assertBadTeam("   ", players);
    }


    // ----------------------------------------------------------
    /**
     * Tests the constructor with a bad roster size
     */
    public void testConstructorBadRosterSize()
    {
        assertBadTeam("Lakers", null);
        assertBadTeam("Lakers", Arrays.asList("A", "B", "C", "D"));
        assertBadTeam("Lakers", Arrays.asList("A", "B", "C", "D", "E", "F"));
    }


    // ----------------------------------------------------------
    /**
     * Tests the constructor with bad player names
     */
    public void testConstructorBadPlayers()
    {
        assertBadTeam("Lakers", Arrays.asList("A", "B", "C", "D", null));
        assertBadTeam("Lakers", Arrays.asList("A", "B", "C", "D", "  "));
        // "Sam" and " sam " count as the same player
        assertBadTeam("Lakers",
            Arrays.asList("Sam", " sam ", "C", "D", "E"));
    }


    // ----------------------------------------------------------
    /**
     * Tests getName()
     */
    public void testGetName()
    {
        assertEquals("Lakers", team.getName());
        Team mixedCase = new Team("  LA Lakers ", players);
        assertEquals("LA Lakers", mixedCase.getName());
    }


    // ----------------------------------------------------------
    /**
     * Tests getPlayers()
     */
    public void testGetPlayers()
    {
        assertEquals(players, team.getPlayers());
        assertEquals("A", team.getPlayers().get(0));
        assertEquals("E", team.getPlayers().get(4));
    }


    // ----------------------------------------------------------
    /**
     * Tests that the roster cannot be changed from outside
     */
    public void testGetPlayersCannotChange()
    {
        Exception thrown = null;
        try
        {
            team.getPlayers().add("F");
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof UnsupportedOperationException);
        assertEquals(5, team.getPlayers().size());

        // changing the original list does not change the team
        List<String> original = new ArrayList<String>(players);
        Team copyTeam = new Team("Bulls", original);
        original.set(0, "Z");
        assertEquals("A", copyTeam.getPlayers().get(0));
    }


    // ----------------------------------------------------------
    /**
     * Checks that making a team with this data throws an
     * IllegalArgumentException.
     *
     * @param name the proposed team name
     * @param roster the proposed players
     */
    private void assertBadTeam(String name, List<String> roster)
    {
        Exception thrown = null;
        try
        {
            new Team(name, roster);
        }
        catch (Exception e)
        {
            thrown = e;
        }
        assertNotNull(thrown);
        assertTrue(thrown instanceof IllegalArgumentException);
    }
}
