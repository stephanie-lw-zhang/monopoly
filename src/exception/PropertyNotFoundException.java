package exception;

import javafx.scene.control.Alert;

public class PropertyNotFoundException extends MonopolyException {
    public PropertyNotFoundException (String message) {
        super(message);
    }

}
