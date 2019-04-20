package frontend.screens;

import frontend.Handlers;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Represents an abstraction of any screen menu within the game app
 *
 * @author Sam
 */
public class AbstractScreen extends Handlers {

    private double    screenWidth;
    private double    screenHeight;
    private Stage     myStage;
    private Scene     myScene;
    private String    myScreenTitle;

    public AbstractScreen(double sWidth, double sHeight, Stage stage) {
        screenWidth = sWidth;
        screenHeight = sHeight;
        myStage = stage;
    }

    public void makeScreen() {
        Text titleText = new Text("**MENU TITLE FROM PROPERTIES**");
        titleText.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 25));
        titleText.setFill(Color.DARKGREEN);

        Button backToMainButton = new Button("Back to Main Menu");
        backToMainButton.setOnAction(f -> handleBackToMainButton(getMyStage()));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(15);

        gridPane.addRow(0, backToMainButton);
        gridPane.setHalignment(backToMainButton, HPos.CENTER);

        BorderPane bPane = setBorderPane(
            getScreenWidth(),
            getScreenHeight(),
            gridPane
        );

        bPane.setCenter(titleText);

        gridPane.setAlignment(Pos.BOTTOM_CENTER);

        myScene = new Scene(bPane, getScreenWidth(), getScreenHeight());
        // myScene.setOnKeyPressed(f -> handleKeyInput(f.getCode()));
    }

    public BorderPane setBorderPane(double sWidth, double sHeight, GridPane gPane) {
        BorderPane bPane = new BorderPane();

        ImageView backgroundImg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("background.jpg")));
        backgroundImg.setFitWidth(sWidth);
        backgroundImg.setFitHeight(sHeight);

        bPane.getChildren().add(backgroundImg);
//        bPane.setCenter(getScreenText());
        bPane.setBottom(gPane);

        bPane.setMargin(gPane, new Insets(0,0, 75, 0));

        return bPane;
    }

//    private void handleKeyInput(KeyCode code) {
//        if (code == KeyCode.Q) {
//            System.out.println("Q");
//            myScene = new MainMenuScreen(screenWidth, screenHeight, myStage).getMyScene();
//        }
//    }

    public double    getScreenWidth()  { return screenWidth; }
    public double    getScreenHeight() { return screenHeight; }
    public Stage  getMyStage()         { return myStage; }
    public Scene  getMyScene()         { return myScene; }
    public String getScreenTitle()     { return myScreenTitle; }
}
