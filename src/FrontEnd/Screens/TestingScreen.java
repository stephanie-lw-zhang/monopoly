package FrontEnd.Screens;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.AssetHolder.HumanPlayer;
import BackEnd.Board.AbstractBoard;
import BackEnd.Board.StandardBoard;
import BackEnd.Deck.NormalDeck;
import BackEnd.Dice.SixDice;
import BackEnd.Tile.GoTile;
import BackEnd.Tile.AbstractPropertyTile;
import BackEnd.Tile.Tile;
import Configuration.ImportPropertyFile;
import Controller.Turn;
import FrontEnd.Views.DiceView;
import FrontEnd.Views.Board.RectangularBoardView;
import FrontEnd.Views.Board.SquareBoardView;
import javafx.animation.RotateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import FrontEnd.Views.FormView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import Controller.Game;

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

    private Scene     testScene;
    private RectangularBoardView myBoardView;
    private ImportPropertyFile myPropertyFile = new ImportPropertyFile("OriginalMonopoly.properties");

    private double    screenWidth;
    private double    screenHeight;
    private DiceView diceLayout;
    private Stage     testStage;
    private Game      myGame;

    private ObservableList<ImageView> myIconsList;

    private final Button ROLL_BUTTON = new Button("ROLL");
    private final Button END_TURN_BUTTON = new Button("END TURN");
    private final Button TRADE_BUTTON = new Button("TRADE");

    public TestingScreen(double width, double height, Stage stage) {
        super(width, height, stage);
        screenWidth = width;
        screenHeight = height;
        testStage = stage;

        myBoardView = new RectangularBoardView(width*0.89, height*0.9,90,11,11,myPropertyFile);
    }

    @Override
    public void makeScreen() {
        BorderPane bPane = new BorderPane();

        ImageView backgroundImg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("background.jpg")));
        backgroundImg.setFitWidth(screenWidth);
        backgroundImg.setFitHeight(screenHeight);

        bPane.getChildren().add(backgroundImg);

        FormView form = new FormView();

        List<TextField> playerFields = form.getPlayerFields();
        Button startGameButton = form.getStartGameButton();

        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleStartGameButton(playerFields);
            }
        });

        Button backToMainButton = new Button("Back to Main Menu");
        backToMainButton.setOnAction(f -> handleBackToMainButton(getMyStage()));

        Image logo = new Image("monopopout.png");
        ImageView iv1 = new ImageView();
        // resizes the image to have width of 100 while preserving the ratio and using
        // higher quality filtering method; this ImageView is also cached to
        // improve performance
        ImageView iv2 = new ImageView();
        iv2.setImage(logo);
        iv2.setFitWidth(400);
        iv2.setPreserveRatio(true);
        iv2.setSmooth(true);
        iv2.setCache(true);

        bPane.setTop(iv2);
        bPane.setAlignment(iv2, Pos.CENTER);
        bPane.setAlignment(form, Pos.CENTER);
        bPane.setCenter(form);
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

        StackPane boardStackPane = new StackPane();

        VBox playerOptionsModal = new VBox();
        playerOptionsModal.setSpacing(10);


        diceLayout = new DiceView(
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

        playerOptionsModal.getChildren().addAll(
                diceLayout, ROLL_BUTTON,
                playersText, currPlayerText,
                END_TURN_BUTTON
        );
        playerOptionsModal.setPadding(new Insets(15, 0, 0, 15));
        playerOptionsModal.setAlignment(Pos.CENTER_RIGHT);

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
            new GoTile(200, 200),
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

    public void updateCurrentPlayer(AbstractPlayer currPlayer) {
        BorderPane bPane = (BorderPane) testScene.getRoot();
        StackPane boardStackPane = (StackPane) bPane.getCenter();
        ObservableList vList = boardStackPane.getChildren();

        // TODO: CANNOT HARDCODE GETTING 1st element in vList (the VBox)
        // TODO: Maybe use "setUserData" for the VBox and retrieve that way
        VBox playerOptionsModal = (VBox) vList.get(1);

        TextArea currPlayerText = (TextArea) playerOptionsModal.getChildren().get(3);
        currPlayerText.setText(currPlayer.getMyPlayerName());
    }


    public void updateDice(final Turn turn) {
        List<ImageView> diceViews = (ObservableList) diceLayout.getChildren();

        diceLayout.playDiceAnimation(diceViews, turn.getRolls());
        this.displayRollsPopup(turn);
    }

    public void displayRollsPopup(final Turn turn) {
        int[] rolls = turn.getRolls();

        Text diceText = new Text("You rolled a " + rolls[0] + " and a " + rolls[1] + "! " +
                "Move " + turn.getNumMoves() + " spots!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("DICE ROLL");
        alert.setContentText("You rolled a " + rolls[0] + " and a " + rolls[1] + "! " +
                "Moving " + turn.getNumMoves() + " spots...");
        alert.showAndWait();
    }
}
