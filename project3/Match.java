// -------------------------------------------------------------------------
/**
 * Keeps the two teams that are currently playing on the court. A Match
 * cannot change after it is created.
 *
 * @author
 * @version 7 Nov 2026
 */
public class Match
{

    // ~ Fields ................................................................

    private final Team homeTeam;
    private final Team awayTeam;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a match between two teams.
     *
     * @param homeTeam the first team taken from the queue
     * @param awayTeam the second team taken from the queue
     * @throws IllegalArgumentException if either team is null or their names
     *             match (ignoring case)
     */
    public Match(Team homeTeam, Team awayTeam)
    {
        // TODO: validate
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Gets the first team selected from the queue.
     *
     * @return the home team
     */
    public Team getHomeTeam()
    {
        return homeTeam;
    }


    // ----------------------------------------------------------
    /**
     * Gets the second team selected from the queue.
     *
     * @return the away team
     */
    public Team getAwayTeam()
    {
        return awayTeam;
    }
}
