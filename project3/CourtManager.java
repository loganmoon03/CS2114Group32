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
    private Team stayingTeam; // winner waiting on the court, or null

    // * a match and a staying team never exist at the same time

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates an empty waiting line and an empty court.
     */
    public CourtManager()
    {
        waitingTeams = new LinkedQueue<Team>();
        currentMatch = null;
        stayingTeam = null;
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
                "Team " + team.getName() + " is already waiting or on the court");
        }

        waitingTeams.enqueue(team);
        return new Result(true, "Team " + team.getName() + " was added");
    }


    // ----------------------------------------------------------
    /**
     * Starts a match. If a winner is staying, the winner plays the first
     * waiting team. Otherwise the first two waiting teams play.
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

        if (stayingTeam != null)
        {
            if (waitingTeams.isEmpty())
            {
                return new Result(false, "At least 1 team must be waiting");
            }
            Team home = stayingTeam;
            Team away = waitingTeams.dequeue();
            currentMatch = new Match(home, away);
            stayingTeam = null; // the winner is now in the match

            return new Result(true,
                home.getName() + " vs " + away.getName() + " started");
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
     * Finishes the current match with a winner. The winner can stay on the
     * court for the next match. The loser leaves and does not go back into
     * the line.
     *
     * @param winnerName the name of the team that won
     * @param winnerStays true if the winner stays for the next match
     * @return a Result that says if the match finished
     */
    public Result finishMatch(String winnerName, boolean winnerStays)
    {
        // * check everything before changing anything
        if (currentMatch == null)
        {
            return new Result(false, "No match is in progress");
        }
        if (winnerName == null || winnerName.trim().isEmpty())
        {
            return new Result(false, "Winner name cannot be blank");
        }

        String name = winnerName.trim();
        Team home = currentMatch.getHomeTeam();
        Team away = currentMatch.getAwayTeam();
        Team winner;
        if (home.getName().equalsIgnoreCase(name))
        {
            winner = home;
        }
        else if (away.getName().equalsIgnoreCase(name))
        {
            winner = away;
        }
        else
        {
            return new Result(false, name + " is not in the current match");
        }

        currentMatch = null;
        if (winnerStays)
        {
            stayingTeam = winner;
            return new Result(true, winner.getName() + " won and stays on");
        }
        stayingTeam = null;
        return new Result(true,
            winner.getName() + " won, both teams left");
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


    // ----------------------------------------------------------
    /**
     * Gets the winner that is staying for the next match.
     *
     * @return the staying team, or null if no winner is staying
     */
    public Team getStayingTeam()
    {
        return stayingTeam;
    }

    // ~ Private Methods .......................................................


    // ----------------------------------------------------------
    /**
     * Checks if a team name is already waiting, playing, or staying.
     * Capital letters do not matter.
     *
     * @param name the team name to look for
     * @return true if the name is taken
     */
    private boolean isTaken(String name)
    {
        /* equalsIgnoreCase() compares two strings without 
        *caring about uppercase or lowercase letters.
        */
        for (Team team : waitingTeams.toList())
        {
            if (team.getName().equalsIgnoreCase(name))
            {
                return true;
            }
        }

        if (stayingTeam != null
            && stayingTeam.getName().equalsIgnoreCase(name))
        {
            return true;
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
