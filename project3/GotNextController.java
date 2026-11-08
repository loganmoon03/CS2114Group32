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
        // TODO: reject null
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
        // TODO: loop: showMenu, readLine, parseMenuChoice, call a handler,
        // then view.showResult(...) and view.showState(...)
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
        // TODO
        return -1;
    }


    // ----------------------------------------------------------
    /**
     * Reads a team name and five players, then registers the team.
     */
    private void handleRegister()
    {
        // TODO
    }


    // ----------------------------------------------------------
    /**
     * Starts a match with the first two waiting teams.
     */
    private void handleStart()
    {
        // TODO
    }


    // ----------------------------------------------------------
    /**
     * Finishes the current match.
     */
    private void handleFinish()
    {
        // TODO
    }


    // ----------------------------------------------------------
    /**
     * Shows the waiting line and the current match.
     */
    private void handleViewStatus()
    {
        // TODO
    }
}
