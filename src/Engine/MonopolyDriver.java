package Engine;

import FrontEnd.ViewMaker;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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

    public static void main(String[] args) { launch(args); }
}
