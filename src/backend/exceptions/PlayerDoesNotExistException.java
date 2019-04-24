package backend.exceptions;

import javafx.scene.control.Alert;

public class PlayerDoesNotExistException extends Exception{
    public PlayerDoesNotExistException(String message) {
        super(message);
    }

    public void popUp() {
        Alert formAlert = new Alert(Alert.AlertType.ERROR);
        formAlert.setContentText(this.getMessage());
        formAlert.showAndWait();
    }
}
