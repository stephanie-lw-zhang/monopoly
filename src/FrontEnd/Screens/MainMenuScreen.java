package FrontEnd.Screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Extends AbstractScreen. Represents the main menu which is first loaded on game start
 *
 * @author Sam
 */
public class MainMenuScreen extends AbstractScreen {
    private static final String SCREEN_TITLE = "Monopoly";
    private Scene myIntro;

    public MainMenuScreen(int sWidth, int sHeight, Stage stage) {
        super(sWidth, sHeight, stage);
    }

    @Override
    public void makeScreen() {
        Text titleText = new Text("MONOPOLY");
        titleText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 25));
        titleText.setFill(Color.DARKGREEN);

        Button playButton = new Button("PLAY: Normal mode");
        playButton.setOnAction(f -> handleNormalModeButton(getMyStage()));

        Button randomModeButton = new Button("PLAY: some other mode???");
        // TODO:
        randomModeButton.setOnAction(r -> handleCustomModeButton(getMyStage()));

        Button instructButton = new Button("INSTRUCTIONS");
        instructButton.setOnAction(i -> handleInstructButton(getMyStage()));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(15);

        gridPane.add(playButton, 1, 0);
        gridPane.add(randomModeButton, 2, 0);
        gridPane.add(instructButton, 3, 0);

        BorderPane bPane = setBorderPane(
                getScreenWidth(),
                getScreenHeight(),
                gridPane
        );
        bPane.setCenter(titleText);

        gridPane.setAlignment(Pos.BOTTOM_CENTER);

        myIntro = new Scene(bPane, getScreenWidth(), getScreenHeight());
    }

    private void handleNormalModeButton(Stage stage) {
        AbstractScreen normalModeScreen = new NormalModeScreen(
            getScreenWidth(),
            getScreenHeight(),
            stage
        );
        completeStage(stage, normalModeScreen);
    }

    private void handleCustomModeButton(Stage stage) {
//        AbstractScreen customModeScreen = new CustomModeScreen(
//            getScreenWidth(),
//            getScreenHeight(),
//            stage
//        );
        AbstractScreen test = new TestingScreen(
            getScreenWidth(),
            getScreenHeight(),
            stage
        );
        completeStage(stage, test);
    }

    private void handleInstructButton(Stage stage) {
        AbstractScreen instructionsScreen = new InstructionsScreen(
            getScreenWidth(),
            getScreenHeight(),
            stage
        );
        completeStage(stage, instructionsScreen);
    }

    private void completeStage(Stage stage, AbstractScreen menu) {
        menu.makeScreen();
        stage.close();
        stage.setScene((menu.getMyScene()));
        stage.show();
    }

    public Scene getMyIntroScene() { return myIntro; }
}
