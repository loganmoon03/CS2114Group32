// -------------------------------------------------------------------------
/**
 * Keeps the two teams playing on the court.
 * A match cannot change after it is made.
 *
 * @author Dochan (Logan) Moon
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
     * @param homeTeam the first team from the queue
     * @param awayTeam the second team from the queue
     * @throws IllegalArgumentException if a team is null or both teams have
     *             the same name
     */
    public Match(Team homeTeam, Team awayTeam)
    {
        if (homeTeam == null || awayTeam == null)
        {
            throw new IllegalArgumentException("A match needs two teams");
        }
        if (homeTeam.getName().equalsIgnoreCase(awayTeam.getName()))
        {
            throw new IllegalArgumentException(
                "A team cannot play itself");
        }

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Gets the first team.
     *
     * @return the home team
     */
    public Team getHomeTeam()
    {
        return homeTeam;
    }


    // ----------------------------------------------------------
    /**
     * Gets the second team.
     *
     * @return the away team
     */
    public Team getAwayTeam()
    {
        return awayTeam;
    }
}
