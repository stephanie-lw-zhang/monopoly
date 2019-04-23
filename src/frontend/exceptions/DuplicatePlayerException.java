package frontend.exceptions;

import javafx.scene.control.Alert;

public class DuplicatePlayerException extends Exception {
    public DuplicatePlayerException(String s) {
        super(s);
        Alert formAlert = new Alert(Alert.AlertType.ERROR);
        formAlert.setContentText("Duplicate player names not allowed!");
        formAlert.showAndWait();
    }

    public DuplicatePlayerException() {
        this("Illegal Duplicate Player Names Found!");
    }
}
