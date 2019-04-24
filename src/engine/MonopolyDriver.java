package engine;

import javafx.application.Application;

import controller.GameController;
import frontend.ViewMaker;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Driver class of the Monopoly game, acts as the game and initializes the
 * entire game framework
 *
 * @author Sam
 */
public class MonopolyDriver extends Application {

    private final static String TITLE = "Monopoly";

    private Stage myStage;
    private Scene myIntroScene;
    private GameController myGameController;

    @Override
    public void start(Stage stage) {
        myStage = stage;
        // TODO: MonopolyDriver.start() refactor for MVC principles
        myIntroScene = new ViewMaker().makeIntroScene(myStage);
        myStage.setScene(myIntroScene);
        myStage.setTitle(TITLE);
        myIntroScene.setOnKeyPressed(f -> handleKeyInput(f.getCode()));
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
        Scene dialogScene = new Scene(dialogVBox, 300, 300);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private void handleKeyInput(KeyCode code) {
        if(code==KeyCode.K){
            showPopUp();
        }
    }

    public static void main(String[] args) { launch(args); }
}
