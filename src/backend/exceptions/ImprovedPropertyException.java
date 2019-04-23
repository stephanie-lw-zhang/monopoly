package backend.exceptions;

import javafx.scene.control.Alert;

public class ImprovedPropertyException extends Exception {
    public ImprovedPropertyException(String message) {
        super(message);
    }

    public void popUp() {
        Alert formAlert = new Alert(Alert.AlertType.ERROR);
        formAlert.setContentText(this.getMessage());
        formAlert.showAndWait();
    }
}
