package frontend.screens;

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import backend.assetholder.HumanPlayer;
import backend.board.AbstractBoard;
import backend.board.StandardBoard;
import backend.deck.NormalDeck;
import backend.dice.SixDice;
import backend.tile.GoTile;
import backend.tile.AbstractPropertyTile;
import backend.tile.Tile;
import configuration.ImportPropertyFile;
import controller.Turn;
import frontend.views.board.AbstractBoardView;
import frontend.views.board.SquareBoardView;
import frontend.views.DiceView;
import frontend.views.board.RectangularBoardView;

import frontend.views.FormView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import controller.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * For testing purposes
 *
 * @author Sam
 */
public class TestingScreen extends AbstractScreen {

    private ImportPropertyFile   myPropertyFile = new ImportPropertyFile("OriginalMonopoly.properties");
    private final ImageView      backgroundImg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("background.jpg")));
    private RectangularBoardView myBoardView;
    private DiceView             myDiceView;
    private FormView             myFormView;
    private Scene                myScene;
    private Game                 myGame;
    private double               screenWidth;
    private double               screenHeight;

    private ObservableList<ImageView> myIconsList;

    private final Button ROLL_BUTTON = new Button("ROLL");
    private final Button END_TURN_BUTTON = new Button("END TURN");
    private final Button TRADE_BUTTON = new Button("TRADE");
    private final Button AUCTION_BUTTON = new Button("AUCTION");
    private final Button MORTGAGE_BUTTON = new Button("MORTGAGE");
    private final Button MOVE_BUTTON = new Button("MOVE");


    public TestingScreen(double width, double height, Stage stage) {
        super(width, height, stage);
        screenWidth = width;
        screenHeight = height;
    }


    @Override
    public void makeScreen() {
        BorderPane bPane = new BorderPane();
        // myScene.getStylesheets().add("../../../resources/stylesheet.css");
        backgroundImg.setSmooth(true);
        backgroundImg.setCache(true);
        backgroundImg.setFitWidth(screenWidth);
        backgroundImg.setFitHeight(screenHeight);

        myBoardView = new SquareBoardView(screenWidth*0.5, screenHeight*0.9,90,11,11, myPropertyFile);
        myFormView = new FormView(this);

        ImageView backButton = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("back.png")));
        backButton.setOnMouseClicked(f -> handleBackToMainButton(getMyStage()));
        backButton.setFitWidth(45);
        backButton.setPreserveRatio(true);
        backButton.setStyle("-fx-background-color: #FFFFFF");
        backButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                backButton.setStyle(
                        "-fx-background-color: #aed581;"
                );
            }
        });

        bPane.getChildren().add(backgroundImg);
        bPane.setAlignment(backButton, Pos.TOP_CENTER);
        bPane.setTop(backButton);
        bPane.setMargin(backButton, new Insets(35,0,-30,0));
        bPane.setCenter(myFormView);

        myScene = new Scene(bPane, screenWidth, screenHeight);
//        Image myCursor = new Image(this.getClass().getClassLoader().getResourceAsStream("mustacheCursor.png"), 20,20,true,true);
//        myScene.setCursor(new ImageCursor(myCursor));
        myScene.setOnKeyPressed(f -> handleKeyInput(f.getCode()));
    }

    public void handleStartGameButton(List<TextField> playerFields) {
        myGame = new Game(
                this,
                new SixDice(),
                new NormalDeck(),
                new NormalDeck(),
                makeBoard(playerFields)
        );

        BorderPane bPane = (BorderPane) myScene.getRoot();

        StackPane boardStackPane = new StackPane();

        VBox playerOptionsModal = new VBox();
        playerOptionsModal.setSpacing(10);

        myDiceView = new DiceView(
                myGame.getBoard().getNumDie(),
                myGame.getMyDice().getNumStates()
        );

        TextArea playersText = new TextArea();
        playersText.setText("Joined Players: \n" + getPlayersText());
        playersText.setEditable(false);
        playersText.setStyle("-fx-max-width: 150; -fx-max-height: 200");

        TextArea currPlayerText = new TextArea();
        currPlayerText.setText(myGame.getMyTurn().getMyCurrPlayer().getMyPlayerName());
        currPlayerText.setEditable(false);
        currPlayerText.setStyle("-fx-max-width: 150; -fx-max-height: 50");

        HBox moveCheatKey = new HBox();
        moveCheatKey.setSpacing(10);
        moveCheatKey.setAlignment(Pos.CENTER_RIGHT);
        TextField movesField = new TextField();
        movesField.setPrefHeight(30);
        movesField.setMaxWidth(60);
        movesField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String toReplace, String newStr) {
                if (! newStr.matches("\\d*"))
                    movesField.setText(newStr.replaceAll("[^\\d]", ""));
            }
        });

        MOVE_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int numMoves = Integer.parseInt(movesField.getText());
                myBoardView.move(numMoves);
            }
        });
        moveCheatKey.getChildren().addAll(movesField, MOVE_BUTTON);

        playerOptionsModal.getChildren().addAll(
                myDiceView, ROLL_BUTTON,
                playersText, currPlayerText,
                END_TURN_BUTTON, TRADE_BUTTON,
                AUCTION_BUTTON, MORTGAGE_BUTTON,
                moveCheatKey
        );

        playerOptionsModal.setPadding(new Insets(15, 0, 0, 15));
        playerOptionsModal.setAlignment(Pos.CENTER_RIGHT);
        myDiceView.setAlignment(Pos.CENTER_RIGHT);

        Pane boardViewPane = myBoardView.getPane();
        boardStackPane.setAlignment(boardViewPane,Pos.CENTER_LEFT);
        boardStackPane.getChildren().addAll(boardViewPane, playerOptionsModal);

        bPane.setTop(null);
        bPane.setCenter(boardStackPane);

        // TODO: CONDITION FOR GAME END LOGIC????
        myGame.startGameLoop();
    }

    private String getPlayersText() {
        StringBuilder sb = new StringBuilder();
        for (AbstractPlayer p : myGame.getBoard().getMyPlayerList())
            sb.append(p.getMyPlayerName() + "\n");
        return sb.toString();
    }

    private AbstractBoard makeBoard(List<TextField> playerFields) {
        List<AbstractPlayer> playerList = makePlayerList(playerFields);

        AbstractBoard board = new StandardBoard(
                playerList,
                new HashMap<Tile, List<Tile>>(),
                new HashMap<String, List<AbstractPropertyTile>>(),
                new GoTile(200, 200, 0),
                new Bank(200000.0, new HashMap<String, Integer>())
        );

        return board;
    }


    private List<AbstractPlayer> makePlayerList(List<TextField> playerFields) {
        Bank bank = new Bank(20000.0, new HashMap<String, Integer>());
        List<AbstractPlayer> playerList = new ArrayList<>();

        for (TextField pName : playerFields) {
            String name = pName.getText();
            if (! name.equals(""))
                playerList.add(new HumanPlayer(name, 1500.0, bank));
        }

        return playerList;
    }

    public void updateCurrentPlayer(AbstractPlayer currPlayer) {
        BorderPane bPane = (BorderPane) myScene.getRoot();
        StackPane boardStackPane = (StackPane) bPane.getCenter();
        ObservableList vList = boardStackPane.getChildren();

        // TODO: CANNOT HARDCODE GETTING 1st element in vList (the VBox)
        // TODO: Maybe use "setUserData" for the VBox and retrieve that way
        VBox playerOptionsModal = (VBox) vList.get(1);

        TextArea currPlayerText = (TextArea) playerOptionsModal.getChildren().get(3);
        currPlayerText.setText(currPlayer.getMyPlayerName());
    }

    public void updateDice(final Turn turn) {
        myDiceView.onUpdate(turn);
    }

    public void updatePlayerPosition(int roll) {
        myBoardView.move(roll);
    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.Q) {
            handleBackToMainButton(getMyStage());
        }
    }

    public Scene getMyScene() {
        return myScene;
    }
    public AbstractBoardView getMyBoardView() { return myBoardView; }
}

