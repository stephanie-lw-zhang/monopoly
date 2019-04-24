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
    private Stage               myStage;
    private Scene               myIntroScene;

    /**
     * Occurs at start of application
     * @param stage
     */
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

    private void handleKeyInput(KeyCode code) {

    }

    public static void main(String[] args) { launch(args); }
}
