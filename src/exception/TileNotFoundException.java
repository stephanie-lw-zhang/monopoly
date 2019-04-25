package exception;

import javafx.scene.control.Alert;

public class TileNotFoundException extends MonopolyException {

    public TileNotFoundException(String message) {
        super(message);
    }

}
