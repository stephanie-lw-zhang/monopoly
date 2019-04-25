package exception;

import javafx.scene.control.Alert;

public class DuplicatePlayerException extends Exception {
    public DuplicatePlayerException(String message) {
        super(message);
    }

}
