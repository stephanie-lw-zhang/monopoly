package backend.exceptions;

public class CancelledActionException extends Exception {

    public CancelledActionException(String message) {
        super(message);
    }


    public void doNothing() {
        return;
    }
}
