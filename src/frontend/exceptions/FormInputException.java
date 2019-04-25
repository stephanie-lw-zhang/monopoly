package frontend.exceptions;

import javafx.scene.control.Alert;

/**
 * Custom exception that is a general representation
 * of some error that occurs with player input during
 * the pre-game signup
 *
 * @author Sam
 */
public class FormInputException extends Exception {

    public FormInputException(String s) {
        super(s);
        popUp();
    }

    /**
     * Default chain constructor
     */
    public FormInputException() {
        this("Error occurred with user input. Re-check values!");
    }

    public void popUp() {
        Alert formAlert = new Alert(Alert.AlertType.ERROR);
        formAlert.setContentText(this.getMessage());
        formAlert.showAndWait();
    }
}
