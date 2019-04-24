package backend.exceptions;

import javafx.scene.control.Alert;

public class IllegalInputTypeException extends Exception {

    public IllegalInputTypeException(String message) {
        super(message);
    }

    public void popUp() {
        Alert formAlert = new Alert(Alert.AlertType.ERROR);
        formAlert.setContentText(this.getMessage());
        formAlert.showAndWait();
    }

    public void doNothing() {
        return;
    }
}
