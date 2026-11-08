import java.util.List;

// -------------------------------------------------------------------------
/**
 * Holds the rules of Got Next. It keeps the waiting line and the current
 * match, adds teams, and starts and finishes matches.
 *
 * It does no input or output. Every action returns a Result, and a failed
 * action does not change anything.
 *
 * @author Dochan (Logan) Moon
 * @version 7 Nov 2026
 */
public class CourtManager
{

    // ~ Fields ................................................................

    private static final int TEAMS_PER_MATCH = 2;

    private final LinkedQueue<Team> waitingTeams;
    private Match currentMatch; // null when the court is empty

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates an empty waiting line and an empty court.
     */
    public CourtManager()
    {
        waitingTeams = new LinkedQueue<Team>();
        currentMatch = null;
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Adds a team to the back of the waiting line.
     *
     * @param name the team name
     * @param players the five player names
     * @return a Result that says if the team was added
     */
    public Result registerTeam(String name, List<String> players)
    {
        Team team;
        try
        {
            team = new Team(name, players);
        }
        catch (IllegalArgumentException e)
        {
            // * bad team data becomes a failed Result, not a crash
            return new Result(false, e.getMessage());
        }

        if (isTaken(team.getName()))
        {
            return new Result(false,
                "Team " + team.getName() + " is already waiting or playing");
        }

        waitingTeams.enqueue(team);
        return new Result(true, "Team " + team.getName() + " was added");
    }


    // ----------------------------------------------------------
    /**
     * Starts a match with the first two waiting teams.
     *
     * @return a Result that says if the match started
     */
    public Result startMatch()
    {
        // * check everything before taking teams out of the line
        if (currentMatch != null)
        {
            return new Result(false, "A match is already in progress");
        }
        if (waitingTeams.size() < TEAMS_PER_MATCH)
        {
            return new Result(false,
                "At least " + TEAMS_PER_MATCH + " teams must be waiting");
        }

        Team home = waitingTeams.dequeue();
        Team away = waitingTeams.dequeue();
        currentMatch = new Match(home, away);

        return new Result(true,
            home.getName() + " vs " + away.getName() + " started");
    }


    // ----------------------------------------------------------
    /**
     * Finishes the current match. Both teams leave the court and do not go
     * back into the line.
     *
     * @return a Result that says if the match finished
     */
    public Result finishMatch()
    {
        if (currentMatch == null)
        {
            return new Result(false, "No match is in progress");
        }

        String message = currentMatch.getHomeTeam().getName() + " vs "
            + currentMatch.getAwayTeam().getName() + " finished";
        currentMatch = null;

        return new Result(true, message);
    }


    // ----------------------------------------------------------
    /**
     * Gets a copy of the waiting teams from front to back.
     * Changing the list does not change the line.
     *
     * @return the waiting teams, or an empty list
     */
    public List<Team> getWaitingTeams()
    {
        return waitingTeams.toList();
    }


    // ----------------------------------------------------------
    /**
     * Gets the match on the court.
     *
     * @return the current match, or null if the court is empty
     */
    public Match getCurrentMatch()
    {
        return currentMatch;
    }

    // ~ Private Methods .......................................................


    // ----------------------------------------------------------
    /**
     * Checks if a team name is already waiting or playing.
     * Capital letters do not matter.
     *
     * @param name the team name to look for
     * @return true if the name is taken
     */
    private boolean isTaken(String name)
    {
        for (Team team : waitingTeams.toList())
        {
            if (team.getName().equalsIgnoreCase(name))
            {
                return true;
            }
        }

        if (currentMatch != null)
        {
            return currentMatch.getHomeTeam().getName().equalsIgnoreCase(name)
                || currentMatch.getAwayTeam().getName()
                    .equalsIgnoreCase(name);
        }
        return false;
    }
}
