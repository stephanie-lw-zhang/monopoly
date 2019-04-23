package backend.exceptions;

import javafx.scene.control.Alert;

public class MortgagePropertyException extends Exception {
    public MortgagePropertyException(String message) {
        super(message);
    }

    public void popUp() {
        Alert formAlert = new Alert(Alert.AlertType.ERROR);
        formAlert.setContentText(this.getMessage());
        formAlert.showAndWait();
    }
}
