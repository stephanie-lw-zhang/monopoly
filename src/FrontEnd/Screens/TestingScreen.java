package FrontEnd.Screens;

import FrontEnd.BoardView;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import Controller.Game;

/**
 * For testing purposes
 *
 * @author Sam
 */
public class TestingScreen extends AbstractScreen {

    private int    screenWidth;
    private int    screenHeight;
    private Stage  testStage;
    private Scene  testScene;
    private Scene myScene;
    private BoardView myBoardView;

    private final Button ROLL_BUTTON = new Button("ROLL");

    public TestingScreen(int width, int height, Stage stage) {
        super(width, height, stage);
        screenWidth = width;
        screenHeight = height;
        testStage = stage;
        myBoardView = new BoardView(width*0.9, height*0.9,90,11,11);

    }

    @Override
    public void makeScreen() {
        Text title = new Text("Testing");
        title.setFill(Color.BLACK);

        Button backToMainButton = new Button("Back to Main Menu");
        backToMainButton.setOnAction(f -> handleBackToMainButton(getMyStage()));

        Button startGameButton = new Button("START GAME");
        startGameButton.setOnAction(f -> handleStartGameButton(getMyStage()));

        BorderPane bPane = new BorderPane();

        ImageView backgroundImg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("background.jpg")));
        backgroundImg.setFitWidth(screenWidth);
        backgroundImg.setFitHeight(screenHeight);

        bPane.getChildren().add(backgroundImg);
        bPane.setAlignment(title, Pos.CENTER);
        bPane.setTop(title);
        bPane.setAlignment(backToMainButton, Pos.CENTER);
        bPane.setBottom(backToMainButton);
        bPane.setAlignment(startGameButton, Pos.CENTER);
        bPane.setCenter(startGameButton);

        testScene = new Scene(bPane, screenWidth, screenHeight);
    }

    private void handleStartGameButton(Stage stage) {
        BorderPane bPane = (BorderPane) testScene.getRoot();

        TilePane playerOptionsModal = new TilePane();
        playerOptionsModal.getChildren().add(ROLL_BUTTON);

        bPane.setAlignment(playerOptionsModal, Pos.TOP_CENTER);
        bPane.setCenter(myBoardView.getBoardPane());
        bPane.setTop(playerOptionsModal);

        ROLL_BUTTON.setOnAction(f -> Game.handleRollButton());
    }

    public Scene getTestScene() {
        return testScene;
    }
}
