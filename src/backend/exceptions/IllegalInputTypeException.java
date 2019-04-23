package backend.exceptions;

import javafx.scene.control.Alert;

public class IllegalInputTypeException extends NumberFormatException {

    public IllegalInputTypeException(String message) {
        super(message);
        popUp();
    }

    private void popUp() {
        Alert formAlert = new Alert(Alert.AlertType.ERROR);
        formAlert.setContentText(this.getMessage());
        formAlert.showAndWait();
    }
}
