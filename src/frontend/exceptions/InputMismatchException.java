package frontend.exceptions;

/**
 * Custom exception to handle when a user has inputted a name but
 * has not chosen an icon or vice versa during the pre-game form
 */
public class InputMismatchException extends FormInputException {
    public InputMismatchException(String s) {
        super(s);
    }

    /**
     * Default chained constructor
     */
    public InputMismatchException() {
        this("Icon missing or player name missing!");
    }
}
