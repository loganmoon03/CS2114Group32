// -------------------------------------------------------------------------
/**
 * Keeps the outcome of one action: if it worked and a message saying why.
 * A result cannot change after it is made.
 *
 * @author Dochan (Logan) Moon
 * @version 7 Nov 2026
 */
public class Result
{

    // ~ Fields ................................................................

    private final boolean success;
    private final String message;

    // ~ Constructors ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates the outcome of one action.
     *
     * @param success true if the action worked
     * @param message why it worked or failed
     * @throws IllegalArgumentException if message is null or blank
     */
    public Result(boolean success, String message)
    {
        if (message == null || message.trim().isEmpty())
        {
            throw new IllegalArgumentException("Message cannot be blank");
        }

        this.success = success;
        this.message = message.trim();
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Checks if the action worked.
     *
     * @return true if the action worked
     */
    public boolean isSuccess()
    {
        return success;
    }


    // ----------------------------------------------------------
    /**
     * Gets the message.
     *
     * @return the trimmed message
     */
    public String getMessage()
    {
        return message;
    }
}
