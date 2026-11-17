import java.util.ArrayList;
import java.util.List;

// -------------------------------------------------------------------------
/**
 * Runs the menu loop, turns user input into CourtManager calls, and passes
 * the results to ConsoleView.
 *
 * The controller checks menu syntax only. All game rules live in
 * CourtManager.
 *
 * @author
 * @version 7 Nov 2026
 */
public class GotNextController
{

    // ~ Fields ................................................................

    private final CourtManager manager;
    private final ConsoleView view;
    private boolean running;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a controller that connects the model and the view.
     *
     * @param manager the model; must not be null
     * @param view the view; must not be null
     * @throws IllegalArgumentException if either is null
     */
    public GotNextController(CourtManager manager, ConsoleView view)
    {
        if (manager == null || view == null)
        {
            throw new IllegalArgumentException("Model and view cannot be null");
        }
        this.manager = manager;
        this.view = view;
        this.running = false;
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Shows the menu until the user quits or input ends.
     */
    public void run()
    {
        running = true;
        while (running)
        {
            view.showMenu();
            String raw = view.readLine("Choose an option: ");
            if (raw == null)
            {
                running = false;
                break;
            }
            switch (parseMenuChoice(raw))
            {
                case 1:
                    handleRegister();
                    break;
                case 2:
                    handleStart();
                    break;
                case 3:
                    handleFinish();
                    break;
                case 4:
                    handleViewStatus();
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    view.showMessage("Invalid menu choice. Enter 1 through 5.");
                    break;
            }
        }
    }

    // ~ Private Methods .......................................................


    // ----------------------------------------------------------
    /**
     * Turns raw menu text into a menu number.
     *
     * @param raw the text the user typed
     * @return the menu number, or -1 if blank, non-numeric, or out of range
     */
    private int parseMenuChoice(String raw)
    {
        if (raw == null)
        {
            return -1;
        }
        try
        {
            int choice = Integer.parseInt(raw.trim());
            if (choice >= 1 && choice <= 5)
            {
                return choice;
            }
        }
        catch (NumberFormatException e)
        {
            // Blank, nonnumeric, and excessively large choices are invalid.
        }
        return -1;
    }


    // ----------------------------------------------------------
    /**
     * Reads a team name and five players, then registers the team.
     */
    private void handleRegister()
    {
        String name = view.readLine("Team name: ");
        if (name == null)
        {
            running = false;
            return;
        }
        List<String> players = new ArrayList<String>();
        for (int i = 1; i <= 5; i++)
        {
            String player = view.readLine("Player " + i + ": ");
            if (player == null)
            {
                running = false;
                return;
            }
            players.add(player);
        }
        view.showResult(manager.registerTeam(name, players));
        handleViewStatus();
    }


    // ----------------------------------------------------------
    /**
     * Starts a match with the first two waiting teams.
     */
    private void handleStart()
    {
        view.showResult(manager.startMatch());
        handleViewStatus();
    }


    // ----------------------------------------------------------
    /**
     * Finishes the current match. Asks who won and if the winner stays.
     */
    private void handleFinish()
    {
        if (manager.getCurrentMatch() == null)
        {
            // * the model reports that no match is in progress
            view.showResult(manager.finishMatch());
            handleViewStatus();
            return;
        }

        String winner = view.readLine("Winning team: ");
        if (winner == null)
        {
            running = false;
            return;
        }
        String stay = view.readLine("Does the winner stay? (y/n): ");
        if (stay == null)
        {
            running = false;
            return;
        }

        int choice = parseYesNo(stay);
        if (choice == -1)
        {
            view.showMessage("Invalid answer. Enter y or n.");
            handleViewStatus();
            return;
        }

        view.showResult(manager.finishMatch(winner, choice == 1));
        handleViewStatus();
    }


    // ----------------------------------------------------------
    /**
     * Turns a yes or no answer into a number.
     *
     * @param raw the text the user typed
     * @return 1 for yes, 0 for no, or -1 if it is neither
     */
    private int parseYesNo(String raw)
    {
        String answer = raw.trim().toLowerCase();
        if (answer.equals("y") || answer.equals("yes"))
        {
            return 1;
        }
        if (answer.equals("n") || answer.equals("no"))
        {
            return 0;
        }
        return -1;
    }


    // ----------------------------------------------------------
    /**
     * Shows the waiting line, the current match, and the staying winner.
     */
    private void handleViewStatus()
    {
        view.showState(
            manager.getWaitingTeams(),
            manager.getCurrentMatch(),
            manager.getStayingTeam());
    }
}