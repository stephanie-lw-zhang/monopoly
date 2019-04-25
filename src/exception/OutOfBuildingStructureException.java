package exception;

import javafx.scene.control.Alert;

public class OutOfBuildingStructureException extends MonopolyException {
    public OutOfBuildingStructureException(String message) {
        super(message);
    }

}
