package backend.exceptions;

import javafx.scene.control.Alert;

public class PropertyNotFoundException extends Exception {
    public PropertyNotFoundException (String message) {
        super(message);
    }

    public void popUp() {
        Alert formAlert = new Alert(Alert.AlertType.ERROR);
        formAlert.setContentText(this.getMessage());
        formAlert.showAndWait();
    }
}
