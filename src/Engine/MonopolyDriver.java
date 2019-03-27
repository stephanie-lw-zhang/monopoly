package Engine;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MonopolyDriver extends Application {

    private static String TITLE = "Monopoly";
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 400;
    private static final int SCREEN_HEIGHT_FULL = 600;
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
        makeIntroScene(myStage);
    }

    private void makeIntroScene(Stage stage) {

    }

    public void setupSim() {

    }

    private void animateSim() {

    }

    private void step() {

    }

    public static void main(String[] args) { launch(args); }
}
