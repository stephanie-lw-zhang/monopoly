package backend.exceptions;

import javafx.scene.control.Alert;

public class OutOfBuildingStructureException extends Exception {
    public OutOfBuildingStructureException(String message) {
        super(message);
    }

    public void popUp() {
        Alert formAlert = new Alert(Alert.AlertType.ERROR);
        formAlert.setContentText(this.getMessage());
        formAlert.showAndWait();
    }
}
