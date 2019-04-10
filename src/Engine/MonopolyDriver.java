package Engine;

import FrontEnd.ViewMaker;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * The Driver class of the Monopoly game, acts as the Game and initializes the
 * entire game framework
 *
 * @author Sam
 */
public class MonopolyDriver extends Application {

    private final static String TITLE = "Monopoly";

    private Stage myStage;
    private Scene myIntroScene;

    @Override
    public void start(Stage stage) {
        myStage = stage;
        // TODO: MonopolyDriver.start() refactor for MVC principles
        myIntroScene = new ViewMaker().makeIntroScene(myStage);

        myStage.setScene(myIntroScene);
        myStage.setTitle(TITLE);
        myStage.show();
    }

    private void step() {

    }

    private void showPopUp(){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(myStage);
        VBox dialogVBox = new VBox(20);
        Button button = new Button("Test!!!");
        dialogVBox.getChildren().add(new Text("This is a Dialog"));
        dialogVBox.getChildren().add(button);
        //button.setOnAction(e -> execute());
        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void handleKeyInput(KeyCode code) {}

    public static void main(String[] args) { launch(args); }
}
