import java.util.List;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Prints the menu, the waiting teams, the current match, and messages, and
 * reads raw text from the user.
 *
 * The view never parses or validates input and never changes any state. It
 * only knows what GotNextController hands it.
 *
 * @author
 * @version 7 Nov 2026
 */
public class ConsoleView
{

    // ~ Fields ................................................................

    private final Scanner scanner;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a view that reads from the given Scanner.
     *
     * @param scanner where the user's typing comes from
     */
    public ConsoleView(Scanner scanner)
    {
        this.scanner = scanner;
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Prints the main menu.
     */
    public void showMenu()
    {
        // TODO
    }


    // ----------------------------------------------------------
    /**
     * Prints a prompt and reads one line of raw text.
     *
     * @param prompt the text to show before reading
     * @return the line the user typed, or null at end of input
     */
    public String readLine(String prompt)
    {
        // TODO: print prompt; return null when scanner has no next line
        return null;
    }


    // ----------------------------------------------------------
    /**
     * Prints the outcome of an action.
     *
     * @param result the outcome to show
     */
    public void showResult(Result result)
    {
        // TODO: print result.getMessage()
    }


    // ----------------------------------------------------------
    /**
     * Prints the waiting line and the current match.
     *
     * @param waitingTeams the waiting teams in order, possibly empty
     * @param currentMatch the match on the court, or null
     */
    public void showState(List<Team> waitingTeams, Match currentMatch)
    {
        // TODO: "No teams waiting" / "No game in progress" for empty cases
    }


    // ----------------------------------------------------------
    /**
     * Prints a plain message, such as an invalid menu choice.
     *
     * @param message the text to show
     */
    public void showMessage(String message)
    {
        // TODO
    }
}
