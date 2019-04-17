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
import FrontEnd.Views.BoardView;
import javafx.animation.RotateTransition;
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
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import Controller.Game;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.io.File;

/**
 * For testing purposes
 *
 * @author Sam
 */
public class TestingScreen extends AbstractScreen {

    private BoardView myBoardView;
    private double    screenWidth;
    private double    screenHeight;
    private Stage     testStage;
    private Scene     testScene;
    private Game      myGame;
    private ImportPropertyFile myPropertyFile = new ImportPropertyFile("Board Templates/OriginalMonopoly.properties");

    private final Button ROLL_BUTTON = new Button("ROLL");
    private final Button END_TURN_BUTTON = new Button("END TURN");

    public TestingScreen(double width, double height, Stage stage) {
        super(width, height, stage);
        screenWidth = width;
        screenHeight = height;
        testStage = stage;
        myBoardView = new BoardView(width*0.9, height*0.9,90,11,11, myPropertyFile);
    }

    @Override
    public void makeScreen() {
        BorderPane bPane = new BorderPane();
        VBox form = new VBox();
        form.setSpacing(10);

        ImageView backgroundImg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("background.jpg")));
        backgroundImg.setFitWidth(screenWidth);
        backgroundImg.setFitHeight(screenHeight);

        bPane.getChildren().add(backgroundImg);

        Label headerLabel = new Label("Enter Player Information: ");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label p1Name = new Label("Player 1 Name: ");
        TextField p1Field = new TextField();
        addTextLimiter(p1Field, 25);
        p1Field.setPrefHeight(30);
        p1Field.setMaxWidth(200);

        Label p2Name = new Label("Player 2 Name: ");
        TextField p2Field = new TextField();
        addTextLimiter(p2Field, 25);
        p2Field.setPrefHeight(30);
        p2Field.setMaxWidth(200);

        Label p3Name = new Label("Player 3 Name: ");
        TextField p3Field = new TextField();
        addTextLimiter(p3Field, 25);
        p3Field.setPrefHeight(30);
        p3Field.setMaxWidth(200);

        Label p4Name = new Label("Player 4 Name: ");
        TextField p4Field = new TextField();
        addTextLimiter(p4Field, 25);
        p4Field.setPrefHeight(30);
        p4Field.setMaxWidth(200);

        Button startGameButton = new Button("START GAME");
        startGameButton.setPrefHeight(20);
        startGameButton.setPrefWidth(150);

        form.getChildren().addAll(
                headerLabel,
                p1Name, p1Field,
                p2Name, p2Field,
                p3Name, p3Field,
                p4Name, p4Field,
                startGameButton
        );

        form.setStyle("-fx-background-color: #FFFFFF;");
        form.setMaxSize(screenWidth, 400);
        form.setPadding(new Insets(30, 0, 0, 30));

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

        HBox diceLayout = new HBox();
        diceLayout.setSpacing(10);
        diceLayout.setUserData("diceLayout");

        ImageView dice1 = new ImageView();
        dice1.setImage(new Image(this
                .getClass()
                .getClassLoader()
                .getResourceAsStream(
                        "dice" + (new Random().nextInt(myGame.getMyDice().getNumStates()) + 1) + ".png"
                )
        ));
        dice1.setFitHeight(30);
        dice1.setFitWidth(30);

        ImageView dice2 = new ImageView();
        dice2.setImage(new Image(this
                .getClass()
                .getClassLoader()
                .getResourceAsStream(
                        "dice" + (new Random().nextInt(myGame.getMyDice().getNumStates()) + 1) + ".png"
                )
        ));
        dice2.setFitHeight(30);
        dice2.setFitWidth(30);

        diceLayout.getChildren().addAll(dice1, dice2);
        diceLayout.setAlignment(Pos.CENTER_LEFT);

        TextArea playersText = new TextArea();
        playersText.setText("Joined Players: \n" + getPlayersText());
        playersText.setEditable(false);
        playersText.setStyle("-fx-max-width: 150; -fx-max-height: 200");

        TextArea currPlayerText = new TextArea();
        currPlayerText.setText(myGame.getMyTurn().getMyCurrPlayer().getMyPlayerName());
        currPlayerText.setEditable(false);
        currPlayerText.setStyle("-fx-max-width: 150; -fx-max-height: 50");

        playerOptionsModal.getChildren().addAll(diceLayout, ROLL_BUTTON, playersText, currPlayerText, END_TURN_BUTTON);
        playerOptionsModal.setPadding(new Insets(15, 0, 0, 15));
        playerOptionsModal.setAlignment(Pos.CENTER_LEFT);

        boardStackPane.getChildren().addAll(myBoardView.getBoardPane(), playerOptionsModal);

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
                new Bank(20000.0, new HashMap<String, Integer>())
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

    public void updateDiceView(int[] rolls) {
        BorderPane bPane = (BorderPane) testScene.getRoot();
        StackPane boardStackPane = (StackPane) bPane.getCenter();
//        VBox playerOptionsModal = (VBox) boardStackPa
        ObservableList vList = boardStackPane.getChildren();

        // TODO: CANNOT HARDCODE GETTING 1st element in vList (the VBox)
        // TODO: Maybe use "setUserData" for the VBox and retrieve that way
        VBox playerOptionsModal = (VBox) vList.get(1);

        // TODO: SIMILAR AS TODO ABOVE, SHOULDN'T HARDCODE FOR 0th ELEMENT
        // TODO: In VBOX FOR INNER HBOX
        HBox diceLayout = (HBox) playerOptionsModal.getChildren().get(0);

        List<ImageView> diceViews = (ObservableList) diceLayout.getChildren();

        playDiceAnimation(diceViews, rolls);
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

    // TODO: MAKE REFLECTION TO MAKE ROTATETRANSITIONS GIVEN DICEVIEWS/ROLLS
    private void playDiceAnimation(List<ImageView> diceViews, int[] rolls) {
        Media diceRollSound = new Media(new File("./data/diceRoll.mp3").toURI().toString());
        MediaPlayer diceSoundPlayer = new MediaPlayer(diceRollSound);
        diceSoundPlayer.play();
        RotateTransition rt1 = new RotateTransition(Duration.seconds(1.5), diceViews.get(0));
        RotateTransition rt2 = new RotateTransition(Duration.seconds(1.5), diceViews.get(1));
        rt1.setFromAngle(0);
        rt1.setToAngle(720);
        rt2.setFromAngle(0);
        rt2.setToAngle(720);
        rt1.setOnFinished(e -> setDice(diceViews.get(0), rolls[0]));
        rt2.setOnFinished(e -> setDice(diceViews.get(1), rolls[1]));
        rt1.play();
        rt2.play();
    }

    private void setDice(ImageView diceView, final int roll) {
        diceView.setImage(new Image(
                this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                        "dice" + roll + ".png"
                    )
                ));
    }

    /**
     * Limits size of user input
     * @param tf
     * @param maxLength
     */
    public void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }
}
