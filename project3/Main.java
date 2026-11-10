import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 * Program entry point. Creates the model, view, and controller and starts
 * the menu loop.
 *
 * @author Dochan (Logan) Moon
 * @version 7 Nov 2026
 */
public class Main
{

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Starts the program.
     *
     * @param args not used
     */
    public static void main(String[] args)
    {
        CourtManager manager = new CourtManager();
        ConsoleView view = new ConsoleView(new Scanner(System.in));
        GotNextController controller = new GotNextController(manager, view);
        controller.run();
    }
}
