package frontend.exceptions;

/**
 * Custom exception for when not enough players are entered into
 * the TextField of FormView
 *
 * @author Sam
 */
public class InsufficientPlayersException extends FormInputException {
    public InsufficientPlayersException(String s) {
        super(s);
    }

    public InsufficientPlayersException() {
        this("Not enough players have signed up!");
    }
}
