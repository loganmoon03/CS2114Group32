import java.util.List;

// -------------------------------------------------------------------------
/**
 * Stores one team's name and its five player names. A Team cannot change
 * after it is created.
 *
 * @author
 * @version 7 Nov 2026
 */
public class Team
{

    // ~ Fields ................................................................

    private final String name;
    private final List<String> players;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a team. Names are trimmed and the roster is stored as an
     * unmodifiable copy.
     *
     * @param name the team name; must not be null or blank
     * @param players exactly five non-blank, distinct player names
     * @throws IllegalArgumentException for a null/blank team name, a null
     *             roster, a roster size other than five, a null/blank player
     *             name, or a repeated name (ignoring case)
     */
    public Team(String name, List<String> players)
    {
        // TODO: validate, trim, and store an unmodifiable copy of players
        this.name = name;
        this.players = players;
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Gets the trimmed team name.
     *
     * @return the team name
     */
    public String getName()
    {
        return name;
    }


    // ----------------------------------------------------------
    /**
     * Gets the unmodifiable roster.
     *
     * @return the five player names in the order given
     */
    public List<String> getPlayers()
    {
        return players;
    }
}
