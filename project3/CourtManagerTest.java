import java.util.Arrays;
import java.util.List;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the CourtManager class without any view.
 *
 * @author
 * @version 7 Nov 2026
 */
public class CourtManagerTest
    extends TestCase
{

    // ~ Fields ................................................................

    private CourtManager manager;
    private List<String> players;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Sets up each test method.
     */
    public void setUp()
    {
        manager = new CourtManager();
        players = Arrays.asList("A", "B", "C", "D", "E");
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Tests the constructor
     */
    public void testConstructor()
    {
        assertTrue(manager.getWaitingTeams().isEmpty());
        assertNull(manager.getCurrentMatch());
        assertFalse(manager.startMatch().isSuccess());
        assertFalse(manager.finishMatch().isSuccess());
        assertTrue(manager.getWaitingTeams().isEmpty());
        assertNull(manager.getCurrentMatch());
    }


    // ----------------------------------------------------------
    /**
     * Tests registerTeam()
     */
    public void testRegisterTeam()
    {
        Result result = manager.registerTeam(" Lakers ", players);
        assertTrue(result.isSuccess());
        assertEquals("Team Lakers was added", result.getMessage());
        assertTrue(manager.registerTeam("Bulls", players).isSuccess());
        assertEquals(2, manager.getWaitingTeams().size());
        assertEquals("Lakers", manager.getWaitingTeams().get(0).getName());
        assertEquals(players, manager.getWaitingTeams().get(0).getPlayers());
        assertEquals("Bulls", manager.getWaitingTeams().get(1).getName());
        assertNull(manager.getCurrentMatch());
    }


    // ----------------------------------------------------------
    /**
     * Tests registerTeam() with bad team data
     */
    public void testRegisterTeamBadData()
    {
        manager.registerTeam("Lakers", players);
        manager.registerTeam("Bulls", players);
        manager.registerTeam("Celtics", players);
        manager.startMatch();
        assertRejectedRegistration(null, players);
        assertRejectedRegistration("   ", players);
        assertRejectedRegistration("New", null);
        assertRejectedRegistration("New", Arrays.asList("A", "B", "C", "D"));
        assertRejectedRegistration("New",
            Arrays.asList("A", "B", "C", "D", "E", "F"));
        assertRejectedRegistration("New",
            Arrays.asList("A", "B", "C", "D", null));
        assertRejectedRegistration("New",
            Arrays.asList("A", "B", "C", "D", "  "));
        assertRejectedRegistration("New",
            Arrays.asList("Sam", " sam ", "C", "D", "E"));
    }


    // ----------------------------------------------------------
    /**
     * Tests registerTeam() with a duplicate name
     */
    public void testRegisterTeamDuplicate()
    {
        manager.registerTeam("Lakers", players);
        manager.registerTeam("Bulls", players);
        assertRejectedRegistration(" lakers ", players);
        manager.startMatch();
        manager.registerTeam("Celtics", players);
        assertRejectedRegistration(" LAKERS ", players);
        assertRejectedRegistration(" bulls ", players);
        assertRejectedRegistration(" celtics ", players);
    }


    // ----------------------------------------------------------
    /**
     * Tests startMatch()
     */
    public void testStartMatch()
    {
        assertFalse(manager.startMatch().isSuccess());
        manager.registerTeam("Lakers", players);
        List<Team> before = manager.getWaitingTeams();
        assertFalse(manager.startMatch().isSuccess());
        assertEquals(before, manager.getWaitingTeams());
        assertNull(manager.getCurrentMatch());
        manager.registerTeam("Bulls", players);
        manager.registerTeam("Celtics", players);
        before = manager.getWaitingTeams();
        assertTrue(manager.startMatch().isSuccess());
        Match match = manager.getCurrentMatch();
        assertSame(before.get(0), match.getHomeTeam());
        assertSame(before.get(1), match.getAwayTeam());
        assertEquals(Arrays.asList(before.get(2)), manager.getWaitingTeams());
        // Even with enough waiting teams, an active match prevents a start.
        manager.registerTeam("Heat", players);
        before = manager.getWaitingTeams();
        assertFalse(manager.startMatch().isSuccess());
        assertSame(match, manager.getCurrentMatch());
        assertEquals(before, manager.getWaitingTeams());
    }


    // ----------------------------------------------------------
    /**
     * Tests finishMatch()
     */
    public void testFinishMatch()
    {
        manager.registerTeam("Lakers", players);
        List<Team> before = manager.getWaitingTeams();
        assertFalse(manager.finishMatch().isSuccess());
        assertEquals(before, manager.getWaitingTeams());
        assertNull(manager.getCurrentMatch());
        manager.registerTeam("Bulls", players);
        manager.registerTeam("Celtics", players);
        manager.startMatch();
        before = manager.getWaitingTeams();
        assertTrue(manager.finishMatch().isSuccess());
        assertNull(manager.getCurrentMatch());
        assertEquals(before, manager.getWaitingTeams());
        assertEquals(1, manager.getWaitingTeams().size());
        assertFalse(manager.finishMatch().isSuccess());
        assertEquals(before, manager.getWaitingTeams());
        // Finished teams may register again at the back.
        assertTrue(manager.registerTeam("Lakers", players).isSuccess());
        assertTrue(manager.registerTeam("Bulls", players).isSuccess());
        assertEquals("Celtics", manager.getWaitingTeams().get(0).getName());
        assertTrue(manager.startMatch().isSuccess());
        assertEquals("Celtics",
            manager.getCurrentMatch().getHomeTeam().getName());
    }


    // ----------------------------------------------------------
    /**
     * Tests getWaitingTeams()
     */
    public void testGetWaitingTeams()
    {
        List<Team> snapshot = manager.getWaitingTeams();
        assertTrue(snapshot.isEmpty());
        snapshot.add(new Team("Outside", players));
        assertTrue(manager.getWaitingTeams().isEmpty());
        manager.registerTeam("Lakers", players);
        manager.registerTeam("Bulls", players);
        snapshot = manager.getWaitingTeams();
        assertEquals("Lakers", snapshot.get(0).getName());
        assertEquals("Bulls", snapshot.get(1).getName());
        snapshot.clear();
        assertEquals(2, manager.getWaitingTeams().size());
        assertTrue(manager.startMatch().isSuccess());
        assertEquals("Lakers",
            manager.getCurrentMatch().getHomeTeam().getName());
        assertTrue(manager.getWaitingTeams().isEmpty());
    }


    // ----------------------------------------------------------
    /**
     * Tests getCurrentMatch()
     */
    public void testGetCurrentMatch()
    {
        assertNull(manager.getCurrentMatch());
        manager.registerTeam("Lakers", players);
        manager.registerTeam("Bulls", players);
        assertTrue(manager.startMatch().isSuccess());
        Match match = manager.getCurrentMatch();
        assertNotNull(match);
        assertSame(match, manager.getCurrentMatch());
        assertEquals("Lakers", match.getHomeTeam().getName());
        assertEquals("Bulls", match.getAwayTeam().getName());
        assertTrue(manager.finishMatch().isSuccess());
        assertNull(manager.getCurrentMatch());
    }


    // ----------------------------------------------------------
    /**
     * Checks that invalid registration reports failure and preserves state.
     *
     * @param name the proposed team name
     * @param roster the proposed players
     */
    private void assertRejectedRegistration(String name, List<String> roster)
    {
        List<Team> before = manager.getWaitingTeams();
        Match match = manager.getCurrentMatch();
        Result result = manager.registerTeam(name, roster);
        assertFalse(result.isSuccess());
        assertFalse(result.getMessage().trim().isEmpty());
        assertEquals(before, manager.getWaitingTeams());
        assertSame(match, manager.getCurrentMatch());
    }
}