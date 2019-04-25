package exception;

import javafx.scene.control.Alert;

public class DuplicatePlayerException extends MonopolyException {
    public DuplicatePlayerException(String message) {
        super(message);
    }

}
