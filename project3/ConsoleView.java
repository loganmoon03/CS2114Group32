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
        System.out.println("1. Register team");
        System.out.println("2. Start match");
        System.out.println("3. Finish match");
        System.out.println("4. View status");
        System.out.println("5. Quit");
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
        System.out.print(prompt);
        if (scanner.hasNextLine())
        {
            return scanner.nextLine();
        }
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
        System.out.println(result.getMessage());
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
        System.out.println("Waiting teams:");
        if (waitingTeams.isEmpty())
        {
            System.out.println("No teams waiting");
        }
        for (int i = 0; i < waitingTeams.size(); i++)
        {
            System.out.println((i + 1) + ". " + waitingTeams.get(i).getName());
        }
        if (currentMatch == null)
        {
            System.out.println("No game in progress");
        }
        else
        {
            System.out.println("Current match: "
                + currentMatch.getHomeTeam().getName() + " vs "
                + currentMatch.getAwayTeam().getName());
        }
    }


    // ----------------------------------------------------------
    /**
     * Prints a plain message, such as an invalid menu choice.
     *
     * @param message the text to show
     */
    public void showMessage(String message)
    {
        System.out.println(message);
    }
}