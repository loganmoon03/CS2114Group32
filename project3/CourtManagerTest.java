import java.util.Arrays;
import java.util.List;
import student.TestCase;

// -------------------------------------------------------------------------
/**
 * Tests the CourtManager class without any view.
 *
 * @author Yabsira Habtamu (yabsirah)
 * @author Jonathan Davis (jonathandavis)
 * @version 17 Nov 2026
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
     * Tests finishMatch(String, boolean) when the winner stays on the court
     */
    public void testFinishMatchWinnerStays()
    {
        manager.registerTeam("Lakers", players);
        manager.registerTeam("Bulls", players);
        manager.registerTeam("Celtics", players);
        assertTrue(manager.startMatch().isSuccess());
        Team lakers = manager.getCurrentMatch().getHomeTeam();
        List<Team> before = manager.getWaitingTeams();

        // the winner's name matches with any capitalization and spacing
        Result result = manager.finishMatch(" lakers ", true);
        assertTrue(result.isSuccess());
        assertEquals("Lakers won and stays on", result.getMessage());
        assertNull(manager.getCurrentMatch());
        assertSame(lakers, manager.getStayingTeam());

        // the loser leaves and does not go back into the line
        assertEquals(before, manager.getWaitingTeams());
        assertEquals("Celtics", manager.getWaitingTeams().get(0).getName());
        assertTrue(manager.registerTeam("Bulls", players).isSuccess());
    }


    // ----------------------------------------------------------
    /**
     * Tests finishMatch(String, boolean) when the winner leaves the court
     */
    public void testFinishMatchWinnerLeaves()
    {
        manager.registerTeam("Lakers", players);
        manager.registerTeam("Bulls", players);
        manager.registerTeam("Celtics", players);
        assertTrue(manager.startMatch().isSuccess());
        List<Team> before = manager.getWaitingTeams();

        // the away team can win too
        Result result = manager.finishMatch("BULLS", false);
        assertTrue(result.isSuccess());
        assertEquals("Bulls won, both teams left", result.getMessage());
        assertNull(manager.getCurrentMatch());
        assertNull(manager.getStayingTeam());
        assertEquals(before, manager.getWaitingTeams());

        // both teams left the court, so both can register again
        assertTrue(manager.registerTeam("Lakers", players).isSuccess());
        assertTrue(manager.registerTeam("Bulls", players).isSuccess());
        assertEquals("Bulls", manager.getWaitingTeams().get(2).getName());
    }


    // ----------------------------------------------------------
    /**
     * Tests finishMatch(String, boolean) with bad input
     */
    public void testFinishMatchBadWinner()
    {
        // no match is in progress on a new court
        assertRejectedFinish("Lakers", true);
        assertRejectedFinish("Lakers", false);
        assertEquals("No match is in progress",
            manager.finishMatch("Lakers", true).getMessage());

        manager.registerTeam("Lakers", players);
        manager.registerTeam("Bulls", players);
        manager.registerTeam("Celtics", players);
        assertTrue(manager.startMatch().isSuccess());

        // blank winner names
        assertRejectedFinish(null, true);
        assertRejectedFinish("", true);
        assertRejectedFinish("   ", false);
        assertEquals("Winner name cannot be blank",
            manager.finishMatch("   ", true).getMessage());
        // Celtics is waiting, not playing
        assertRejectedFinish("Celtics", true);
        // Heat was never registered
        assertRejectedFinish("Heat", false);
        assertEquals("Heat is not in the current match",
            manager.finishMatch(" Heat ", true).getMessage());

        // no match is in progress while a winner is staying
        assertTrue(manager.finishMatch("Lakers", true).isSuccess());
        assertRejectedFinish("Lakers", true);
        assertRejectedFinish("Lakers", false);
    }


    // ----------------------------------------------------------
    /**
     * Tests startMatch() when a winner is staying on the court
     */
    public void testStartMatchWithStayingTeam()
    {
        manager.registerTeam("Lakers", players);
        manager.registerTeam("Bulls", players);
        assertTrue(manager.startMatch().isSuccess());
        assertTrue(manager.finishMatch("Lakers", true).isSuccess());
        Team lakers = manager.getStayingTeam();

        // zero teams waiting: the winner has no one to play
        Result result = manager.startMatch();
        assertFalse(result.isSuccess());
        assertEquals("At least 1 team must be waiting", result.getMessage());
        assertNull(manager.getCurrentMatch());
        assertSame(lakers, manager.getStayingTeam());
        assertTrue(manager.getWaitingTeams().isEmpty());

        // one team waiting is enough when a winner is staying
        manager.registerTeam("Celtics", players);
        manager.registerTeam("Heat", players);
        Team celtics = manager.getWaitingTeams().get(0);
        result = manager.startMatch();
        assertTrue(result.isSuccess());
        assertEquals("Lakers vs Celtics started", result.getMessage());
        Match match = manager.getCurrentMatch();
        assertSame(lakers, match.getHomeTeam());
        assertSame(celtics, match.getAwayTeam());
        assertNull(manager.getStayingTeam());
        // only one team left the line
        assertEquals(1, manager.getWaitingTeams().size());
        assertEquals("Heat", manager.getWaitingTeams().get(0).getName());

        // a match in progress still blocks a start
        List<Team> before = manager.getWaitingTeams();
        assertFalse(manager.startMatch().isSuccess());
        assertSame(match, manager.getCurrentMatch());
        assertNull(manager.getStayingTeam());
        assertEquals(before, manager.getWaitingTeams());

        // the winner can keep staying on
        assertTrue(manager.finishMatch("Lakers", true).isSuccess());
        assertTrue(manager.startMatch().isSuccess());
        assertSame(lakers, manager.getCurrentMatch().getHomeTeam());
        assertEquals("Heat",
            manager.getCurrentMatch().getAwayTeam().getName());
        assertTrue(manager.getWaitingTeams().isEmpty());
    }


    // ----------------------------------------------------------
    /**
     * Tests that the staying winner's name cannot be registered again
     */
    public void testRegisterStayingTeamName()
    {
        manager.registerTeam("Lakers", players);
        manager.registerTeam("Bulls", players);
        assertTrue(manager.startMatch().isSuccess());
        assertTrue(manager.finishMatch("Lakers", true).isSuccess());

        // the staying winner's name is taken in any capitalization
        assertRejectedRegistration("Lakers", players);
        assertRejectedRegistration(" lakers ", players);
        assertRejectedRegistration("LAKERS", players);
        Result result = manager.registerTeam("Lakers", players);
        assertEquals("Team Lakers is already waiting or on the court",
            result.getMessage());

        // other names still register while a winner is staying
        assertTrue(manager.registerTeam("Celtics", players).isSuccess());
        assertEquals(1, manager.getWaitingTeams().size());
    }


    // ----------------------------------------------------------
    /**
     * Tests getStayingTeam()
     */
    public void testGetStayingTeam()
    {
        assertNull(manager.getStayingTeam());
        manager.registerTeam("Lakers", players);
        manager.registerTeam("Bulls", players);
        assertTrue(manager.startMatch().isSuccess());
        // a match and a staying team never exist at the same time
        assertNull(manager.getStayingTeam());

        Team bulls = manager.getCurrentMatch().getAwayTeam();
        assertTrue(manager.finishMatch("Bulls", true).isSuccess());
        assertSame(bulls, manager.getStayingTeam());
        // same team on every call
        assertSame(manager.getStayingTeam(), manager.getStayingTeam());

        // the staying team moves into the next match
        manager.registerTeam("Celtics", players);
        assertTrue(manager.startMatch().isSuccess());
        assertNull(manager.getStayingTeam());

        // a winner who leaves is not held
        assertTrue(manager.finishMatch("Celtics", false).isSuccess());
        assertNull(manager.getStayingTeam());
    }


    // ----------------------------------------------------------
    /**
     * Tests that a staying winner who loses the next match does not stay
     */
    public void testStayingTeamLosesNextMatch()
    {
        manager.registerTeam("Lakers", players);
        manager.registerTeam("Bulls", players);
        assertTrue(manager.startMatch().isSuccess());
        assertTrue(manager.finishMatch("Lakers", true).isSuccess());

        // the old finishMatch() fails with no match and keeps the winner
        assertFalse(manager.finishMatch().isSuccess());
        assertEquals("Lakers", manager.getStayingTeam().getName());

        manager.registerTeam("Celtics", players);
        assertTrue(manager.startMatch().isSuccess());
        assertTrue(manager.finishMatch("Celtics", true).isSuccess());
        assertEquals("Celtics", manager.getStayingTeam().getName());
        // the old winner left, so its name is free again
        assertTrue(manager.registerTeam("Lakers", players).isSuccess());
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
        Team staying = manager.getStayingTeam();
        Result result = manager.registerTeam(name, roster);
        assertFalse(result.isSuccess());
        assertFalse(result.getMessage().trim().isEmpty());
        assertEquals(before, manager.getWaitingTeams());
        assertSame(match, manager.getCurrentMatch());
        assertSame(staying, manager.getStayingTeam());
    }


    // ----------------------------------------------------------
    /**
     * Checks that an invalid finish reports failure and preserves state.
     *
     * @param winnerName the proposed winner
     * @param winnerStays whether the winner would stay
     */
    private void assertRejectedFinish(String winnerName, boolean winnerStays)
    {
        List<Team> before = manager.getWaitingTeams();
        Match match = manager.getCurrentMatch();
        Team staying = manager.getStayingTeam();
        Result result = manager.finishMatch(winnerName, winnerStays);
        assertFalse(result.isSuccess());
        assertFalse(result.getMessage().trim().isEmpty());
        assertEquals(before, manager.getWaitingTeams());
        assertSame(match, manager.getCurrentMatch());
        assertSame(staying, manager.getStayingTeam());
    }
}