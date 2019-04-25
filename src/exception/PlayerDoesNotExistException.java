package exception;

import javafx.scene.control.Alert;

public class PlayerDoesNotExistException extends MonopolyException{
    public PlayerDoesNotExistException(String message) {
        super(message);
    }

}
