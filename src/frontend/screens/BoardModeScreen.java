package frontend.screens;

import configuration.ImportPropertyFile;
import frontend.views.board.AbstractBoardView;
import frontend.views.board.SquareBoardView;
import frontend.views.game.AbstractGameView;
import frontend.views.game.SplitScreenGameView;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
 * For BOARD testing purposes
 *
 * @author Luis
 */
public class BoardModeScreen extends AbstractScreen{

    private Scene myScene;
    private AbstractGameView myGameView;
    private AbstractBoardView myBoardView;
    private ImportPropertyFile myPropertyFile = new ImportPropertyFile("OriginalMonopoly.properties");
    public BoardModeScreen(double sWidth, double sHeight, Stage stage) {
        super(sWidth, sHeight, stage);
        myBoardView = new SquareBoardView(0.9*sWidth, 0.9*sHeight,90,11,11,myPropertyFile);
        myGameView = new SplitScreenGameView(0.9*sWidth, 0.9*sHeight,myBoardView);
    }


    @Override
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

        //bPane.setCenter(titleText);

        gridPane.setAlignment(Pos.BOTTOM_CENTER);

        myScene = new Scene(bPane, getScreenWidth(), getScreenHeight());
    }

    @Override
    public BorderPane setBorderPane(double sWidth, double sHeight, GridPane gPane) {
        BorderPane bPane = new BorderPane();

        ImageView backgroundImg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("background.jpg")));
        backgroundImg.setFitWidth(sWidth);
        backgroundImg.setFitHeight(sHeight);
        bPane.getChildren().add(backgroundImg);
        bPane.setCenter(myGameView.getPane());
        bPane.setBottom(gPane);

        bPane.setMargin(gPane, new Insets(0,0, 75, 0));

        return bPane;
    }

    private Node makeBoard() {
        return myGameView.getPane();
    }

    @Override
    public Scene  getMyScene()      { return myScene; }
}
