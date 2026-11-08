import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// -------------------------------------------------------------------------
/**
 * Stores one team's name and its five player names.
 * A team cannot change after it is made.
 *
 * @author Dochan (Logan) Moon
 * @version 7 Nov 2026
 */
public class Team
{

    // ~ Fields ................................................................

    private static final int TEAM_SIZE = 5;

    private final String name;
    private final List<String> players;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a team. Names are trimmed.
     *
     * @param name the team name
     * @param players exactly five different player names
     * @throws IllegalArgumentException if the name or the roster is not
     *             valid
     */
    public Team(String name, List<String> players)
    {
        if (name == null || name.trim().isEmpty())
        {
            throw new IllegalArgumentException("Team name cannot be blank");
        }
        if (players == null || players.size() != TEAM_SIZE)
        {
            throw new IllegalArgumentException(
                "A team needs exactly " + TEAM_SIZE + " players");
        }

        List<String> cleanPlayers = new ArrayList<String>();
        List<String> lowerNames = new ArrayList<String>();

        for (String player : players)
        {
            if (player == null || player.trim().isEmpty())
            {
                throw new IllegalArgumentException(
                    "Player name cannot be blank");
            }

            // * "Sam" and " sam" count as the same player
            String lower = player.trim().toLowerCase();
            if (lowerNames.contains(lower))
            {
                throw new IllegalArgumentException(
                    "Player " + player.trim() + " is listed twice");
            }

            lowerNames.add(lower);
            cleanPlayers.add(player.trim());
        }

        this.name = name.trim();
        this.players = Collections.unmodifiableList(cleanPlayers);
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Gets the team name.
     *
     * @return the trimmed team name
     */
    public String getName()
    {
        return name;
    }


    // ----------------------------------------------------------
    /**
     * Gets the players. The list cannot be changed.
     *
     * @return the five player names
     */
    public List<String> getPlayers()
    {
        return players;
    }
}
