package frontend.screens;

import backend.assetholder.AbstractPlayer;
import backend.assetholder.Bank;
import backend.assetholder.HumanPlayer;
import backend.board.AbstractBoard;
import backend.board.StandardBoard;
import backend.deck.NormalDeck;
import backend.dice.SixDice;
import backend.exceptions.IllegalInputTypeException;
import backend.tile.GoTile;
import backend.tile.AbstractPropertyTile;
import backend.tile.Tile;
import configuration.ImportPropertyFile;
import controller.Turn;
import frontend.views.board.AbstractBoardView;
import frontend.views.board.SquareBoardView;
import frontend.views.DiceView;

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

import java.util.*;

/**
 * For testing purposes
 *
 * @author Sam
 */
public class TestingScreen extends AbstractScreen {

    private ImportPropertyFile   myPropertyFile = new ImportPropertyFile("OriginalMonopoly.properties");
    private final ImageView      backgroundImg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("background.jpg")));
    private SquareBoardView      myBoardView;
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
    private final Button BUY_BUTTON = new Button("BUY");


    public TestingScreen(double width, double height, Stage stage) {
        super(width, height, stage);
        screenWidth = width;
        screenHeight = height;
        END_TURN_BUTTON.setId("endTurn");
        BUY_BUTTON.setId("buy");
        AUCTION_BUTTON.setId("auction");
        MORTGAGE_BUTTON.setId("mortgage");
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
                makeBoard(playerFields),
                playerFields
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
                myGame.getMyTurn().moveCheat(numMoves);
            }
        });

        BUY_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Map.Entry<AbstractPlayer, Double> playerValue = (Map.Entry<AbstractPlayer, Double>)myGame.getMyTurn().onAction(BUY_BUTTON.getText().toLowerCase(), null);
                String info = playerValue.getKey().getMyPlayerName() + " bought " + myGame.getMyTurn().getTileNameforPlayer(playerValue.getKey()) + " for " + playerValue.getValue() + " Monopoly Dollars!";
                displayActionInfo(info);
            }
        });

        AUCTION_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Map<AbstractPlayer,Double> auctionAmount = new HashMap<>();
                for (int i = 0; i < myGame.getNumberOfPlayers(); i++) {
                    AbstractPlayer key = myGame.getPlayerAtIndex(i);
                    String value = showAuctionInputTextDialog(myGame.getPlayerNameAtIndex(i));
                    try {
                        auctionAmount.put(key, Double.parseDouble((value)));
                    } catch (NumberFormatException n) {
                         new IllegalInputTypeException("Input must be a number!");
                         i--;
                    }
                }
                Map.Entry<AbstractPlayer, Double> winner = (Map.Entry<AbstractPlayer, Double>)myGame.getMyTurn().onAction(AUCTION_BUTTON.getText().toLowerCase(), auctionAmount);
                String info = winner.getKey().getMyPlayerName() + " wins " + myGame.getMyTurn().getTileNameforPlayer(winner.getKey()) + " for " + winner.getValue() + " Monopoly Dollars!";
                displayActionInfo(info);
                Map<AbstractPlayer, Double> playerValue = convertEntrytoMap(winner);
                myGame.getMyTurn().onAction("buy", playerValue);
            }
        });

        moveCheatKey.getChildren().addAll(movesField, MOVE_BUTTON);

        playerOptionsModal.getChildren().addAll(
                myDiceView, ROLL_BUTTON,
                playersText, currPlayerText,
                END_TURN_BUTTON, TRADE_BUTTON,
                AUCTION_BUTTON, MORTGAGE_BUTTON,
                moveCheatKey, BUY_BUTTON
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

    private Map<AbstractPlayer, Double> convertEntrytoMap(Map.Entry<AbstractPlayer,Double> param) {
        Map<AbstractPlayer, Double> mapFromSet = new HashMap<>();
        mapFromSet.put(param.getKey(), param.getValue());
        return mapFromSet;
    }

    private void displayActionInfo(String info) {
        Alert formAlert = new Alert(Alert.AlertType.INFORMATION);
        formAlert.setContentText(info);
        formAlert.showAndWait();
    }

    private String showAuctionInputTextDialog(String name) {

        TextInputDialog dialog = new TextInputDialog("0");

        dialog.setTitle("Auction Amount for player " + name);
        dialog.setHeaderText("Enter your auction amount:");
        dialog.setContentText("Amount:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            return result.get();
        }
        else {
            //TODO: throw exception
            return null;
        }
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
            //should be from data file
            //throw exception
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

    public void greyOutButtons(List<String> possibleActions) {
        for (String str : possibleActions) {
            Button myButton = (Button)myScene.lookup("#" + str);
            myButton.setDisable(false);
        }
    }

    public Scene getMyScene() {
        return myScene;
    }
    public AbstractBoardView getMyBoardView() { return myBoardView; }

    public FormView getMyFormView() {
        return myFormView;
    }
}

