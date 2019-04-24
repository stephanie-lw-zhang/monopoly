package backend.exceptions;

import javafx.scene.control.Alert;

public class TileNotFoundException extends Exception {

    public TileNotFoundException(String message) {
        super(message);
        popUp();
    }

//    public TileNotFoundException (String message, String values) {
//        super(String.format(message, values));
//    }

    public void popUp() {
        Alert formAlert = new Alert(Alert.AlertType.ERROR);
        formAlert.setContentText(this.getMessage());
        formAlert.showAndWait();
    }
}
