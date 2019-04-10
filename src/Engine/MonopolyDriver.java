package Engine;

import FrontEnd.Screens.MainMenuScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The Driver class of the Monopoly game, acts as the Game and initializes the
 * entire game framework
 *
 * @author Sam
 */
public class MonopolyDriver extends Application {

    private static String TITLE = "Monopoly";
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 400;
    private double FRAMES_PER_SECOND = 3;
    private double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Group root;
    private Stage myStage;
    private Scene myScene;

    private Timeline animation;

    @Override
    public void start(Stage stage) {
        myStage = stage;
        setupGame(stage);
        makeIntroScene(myStage);
    }

    private void makeIntroScene(Stage stage) {
        MainMenuScreen mainMenuScreen = new MainMenuScreen(SCREEN_WIDTH, SCREEN_HEIGHT, stage);
        mainMenuScreen.makeScreen();
        stage.setScene(mainMenuScreen.getMyIntroScene());
        stage.setTitle(TITLE);
        stage.show();
    }

    private void setupGame(Stage stage) {
        myStage = stage;
        root = new Group();
        myScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        myStage.close();
        myStage.setScene(myScene);
        myStage.show();

        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        animateGame();
    }

    private void animateGame() {
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
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
