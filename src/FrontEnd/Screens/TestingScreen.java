package FrontEnd.Screens;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.AssetHolder.HumanPlayer;
import BackEnd.Board.AbstractBoard;
import BackEnd.Board.StandardBoard;
import BackEnd.Deck.NormalDeck;
import BackEnd.Dice.SixDice;
import BackEnd.Tile.GoTile;
import BackEnd.Tile.PropertyTiles.AbstractPropertyTile;
import BackEnd.Tile.TileInterface;
import FrontEnd.BoardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import Controller.Game;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * For testing purposes
 *
 * @author Sam
 */
public class TestingScreen extends AbstractScreen {

    private double    screenWidth;
    private double    screenHeight;
    private Stage  testStage;
    private Scene  testScene;
    private Scene myScene;
    private BoardView myBoardView;

    private Game myGame;

    private final Button ROLL_BUTTON = new Button("ROLL");

    public TestingScreen(double width, double height, Stage stage) {
        super(width, height, stage);
        screenWidth = width;
        screenHeight = height;
        testStage = stage;
        myBoardView = new BoardView(width*0.9, height*0.9,90,11,11);
    }

    @Override
    public void makeScreen() {
        BorderPane bPane = new BorderPane();
        GridPane form = new GridPane();

        ImageView backgroundImg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("background.jpg")));
        backgroundImg.setFitWidth(screenWidth);
        backgroundImg.setFitHeight(screenHeight);

        bPane.getChildren().add(backgroundImg);

        Label headerLabel = new Label("Enter Player Information: ");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        form.add(headerLabel, 0,0,2,1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

        Label p1Name = new Label("Player 1 Name: ");
        form.add(p1Name, 0, 1);
        TextField p1Field = new TextField();
        p1Field.setPrefHeight(40);
        form.add(p1Field, 1,1);

        Label p2Name = new Label("Player 2 Name: ");
        form.add(p2Name, 0, 2);
        TextField p2Field = new TextField();
        p2Field.setPrefHeight(40);
        form.add(p2Field, 1,2);

        Label p3Name = new Label("Player 3 Name: ");
        form.add(p3Name, 0, 3);
        TextField p3Field = new TextField();
        p3Field.setPrefHeight(40);
        form.add(p3Field, 1,3);

        Label p4Name = new Label("Player 4 Name: ");
        form.add(p4Name, 0, 4);
        TextField p4Field = new TextField();
        p4Field.setPrefHeight(40);
        form.add(p4Field, 1,4);

        Button startGameButton = new Button("START GAME");
        startGameButton.setPrefHeight(40);
        startGameButton.setPrefWidth(200);
        List<TextField> playerFields = new ArrayList<>();
        playerFields.add(p1Field);
        playerFields.add(p2Field);
        playerFields.add(p3Field);
        playerFields.add(p4Field);

        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleStartGameButton(playerFields);
            }
        });

        Button backToMainButton = new Button("Back to Main Menu");
        backToMainButton.setOnAction(f -> handleBackToMainButton(getMyStage()));

        bPane.setAlignment(form, Pos.CENTER);
        bPane.setTop(form);
        bPane.setAlignment(startGameButton, Pos.CENTER);
        bPane.setCenter(startGameButton);
        bPane.setAlignment(backToMainButton, Pos.CENTER);
        bPane.setBottom(backToMainButton);

        testScene = new Scene(bPane, screenWidth, screenHeight);
    }

    private void handleStartGameButton(List<TextField> playerFields) {
        if (! hasEnoughPlayers(playerFields)) {
            Alert formAlert = new Alert(Alert.AlertType.ERROR);
            formAlert.setContentText("Not enough players signed up! (need >= 2)");
            formAlert.showAndWait();
            return;
        }

        myGame = new Game(
                this,
                new SixDice(),
                new NormalDeck(),
                new NormalDeck(),
                makeBoard(playerFields)
        );

        BorderPane bPane = (BorderPane) testScene.getRoot();

        StackPane boardStackModal = new StackPane();

        TilePane playerOptionsModal = new TilePane();
//        playerOptionsModal.getChildren().add(ROLL_BUTTON);

        ImageView dice1 = new ImageView();
        dice1.setImage(new Image(this
                .getClass()
                .getClassLoader()
                .getResourceAsStream(
                        "dice" + new Random().nextInt() + ".png"
                )
        ));

        ImageView dice2 = new ImageView();
        dice2.setImage(new Image(this.getClass().getClassLoader().getResourceAsStream("dice1.png")));

        playerOptionsModal.getChildren().addAll(ROLL_BUTTON, dice1, dice2);

        boardStackModal.getChildren().addAll(myBoardView.getBoardPane(), ROLL_BUTTON, playerOptionsModal);

        bPane.setCenter(null); // gets rid of startGameButton
        bPane.setTop(boardStackModal);

        ROLL_BUTTON.setOnAction(f -> myGame.handleRollButton());
    }

    private AbstractBoard makeBoard(List<TextField> playerFields) {
        List<AbstractPlayer> playerList = makePlayerList(playerFields);

        AbstractBoard board = new StandardBoard(
            playerList,
            new HashMap<TileInterface, List<TileInterface>>(),
            new HashMap<String, List<AbstractPropertyTile>>(),
            new GoTile(200, 200)
        );

        return board;
    }

    private List<AbstractPlayer> makePlayerList(List<TextField> playerFields) {
        Bank bank = new Bank(20000.0, new HashMap<String, Integer>());
        List<AbstractPlayer> playerList = new ArrayList<>();
        playerList.add(new HumanPlayer("Player 1", 200.0, bank));
        playerList.add(new HumanPlayer("Player 2", 1500.0, bank));
        playerList.add(new HumanPlayer("Player 3", 1500.0, bank));
        playerList.add(new HumanPlayer("Player 4", 1500.0, bank));

        return playerList;
    }

    private boolean hasEnoughPlayers(List<TextField> playerFields) {
        int empties = 0;
        for (TextField p : playerFields)
            if (p.getText().equals(""))
                empties++;
        return empties <= 2;
    }

    public Scene getTestScene() {
        return testScene;
    }
}
