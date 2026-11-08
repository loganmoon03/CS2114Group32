// -------------------------------------------------------------------------
/**
 * Keeps the outcome of one action: whether it succeeded and the message
 * explaining why. A Result cannot change after it is created.
 *
 * @author
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
     * @param success whether the action was accepted
     * @param message why it succeeded or was rejected; must not be null or
     *            blank
     * @throws IllegalArgumentException if message is null or blank
     */
    public Result(boolean success, String message)
    {
        // TODO: validate and trim message
        this.success = success;
        this.message = message;
    }

    // ~ Public Methods ........................................................


    // ----------------------------------------------------------
    /**
     * Checks whether the action succeeded.
     *
     * @return true if the action was accepted
     */
    public boolean isSuccess()
    {
        return success;
    }


    // ----------------------------------------------------------
    /**
     * Gets the outcome message.
     *
     * @return the trimmed message
     */
    public String getMessage()
    {
        return message;
    }
}
