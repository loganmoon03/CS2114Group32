import java.util.List;

// -------------------------------------------------------------------------
/**
 * Holds the rules of Got Next. It keeps the queue of waiting teams and the
 * current match, rejects bad registrations, starts a match with the first
 * two waiting teams, and finishes the current match.
 *
 * It performs no input or output. Every user action returns a Result, and a
 * rejected action leaves the waiting queue and current match unchanged.
 *
 * @author
 * @version 7 Nov 2026
 */
public class CourtManager
{

    // ~ Fields ................................................................

    private static final int TEAM_SIZE = 5;
    private static final int TEAMS_PER_MATCH = 2;

    private final LinkedQueue<Team> waitingTeams;
    private Match currentMatch; // null when the court is empty

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a manager with an empty waiting queue and an empty court.
     */
    public CourtManager()
    {
        waitingTeams = new LinkedQueue<Team>();
        currentMatch = null;
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Registers a team at the back of the waiting line.
     *
     * @param name the team name
     * @param players the five player names
     * @return a successful Result, or a failed Result if the team data is
     *         invalid or the name is already waiting or playing
     */
    public Result registerTeam(String name, List<String> players)
    {
        // TODO: build a Team (catch IllegalArgumentException), check for a
        // duplicate name among waiting and playing teams, then enqueue
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Starts a match with the first two waiting teams.
     *
     * @return a successful Result, or a failed Result if a match is already
     *         in progress or fewer than two teams are waiting
     */
    public Result startMatch()
    {
        // TODO: check court and queue size before dequeuing anything
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Finishes the current match. Neither team goes back into the queue.
     *
     * @return a successful Result, or a failed Result if no match is in
     *         progress
     */
    public Result finishMatch()
    {
        // TODO
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Gets a snapshot of the waiting teams in FIFO order. Changing the list
     * does not change the queue.
     *
     * @return the waiting teams, or an empty list
     */
    public List<Team> getWaitingTeams()
    {
        return waitingTeams.toList();
    }


    // ----------------------------------------------------------
    /**
     * Gets the match currently on the court.
     *
     * @return the current match, or null if the court is empty
     */
    public Match getCurrentMatch()
    {
        return currentMatch;
    }
}
