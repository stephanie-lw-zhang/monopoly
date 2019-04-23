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
import frontend.views.player_options.DiceView;

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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import controller.GameController;

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
    private GameController       myGame;
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

    private final Button COLLECT_BUTTON = new Button("COLLECT");
    private final Button GO_TO_JAIL_BUTTON = new Button("Go To Jail");
    private final Button PAY_RENT_BUTTON = new Button("Pay Rent");
    private final Button PAY_BAIL_BUTTON = new Button("Pay Bail");
    private final Button FORFEIT_BUTTON = new Button("Forfeit");
    private final Button MOVE_HANDLER_BUTTON = new Button("Move handler");


    public TestingScreen(double width, double height, Stage stage) {
        super(width, height, stage);
        screenWidth = width;
        screenHeight = height;
        END_TURN_BUTTON.setId("endTurn");
        BUY_BUTTON.setId("buy");
        AUCTION_BUTTON.setId("auction");
        MORTGAGE_BUTTON.setId("mortgage");
        COLLECT_BUTTON.setId( "collect" );
        PAY_RENT_BUTTON.setId( "payRent" );
        PAY_BAIL_BUTTON.setId( "payBail" );
        FORFEIT_BUTTON.setId("forfeit");
        MOVE_BUTTON.setId( "move handler" );
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

    public void handleStartGameButton(Map<TextField, ComboBox> playerToIcon) {
        myGame = new GameController(
                this,
                new SixDice(),
                playerToIcon
        );

        BorderPane bPane = (BorderPane) myScene.getRoot();

        StackPane boardStackPane = new StackPane();

        VBox playerOptionsModal = new VBox();
        playerOptionsModal.setSpacing(10);

        myDiceView = new DiceView(
                myGame.getBoard().getNumDie(),
                myGame.getMyDice().getNumStates()
        );

        TextFlow playersText = createPlayersText();

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
//                System.out.println("initial money: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
                Map.Entry<AbstractPlayer, Double> playerValue = (Map.Entry<AbstractPlayer, Double>)myGame.getMyTurn().onAction(BUY_BUTTON.getText().toLowerCase(), null);
                String info = playerValue.getKey().getMyPlayerName() + " bought " + myGame.getMyTurn().getTileNameforPlayer(playerValue.getKey()) + " for " + playerValue.getValue() + " Monopoly Dollars!";
                displayActionInfo(info);
//                System.out.println("after buy money: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
//                System.out.println("after buy properties: " + myGame.getMyTurn().getMyCurrPlayer().getProperties());

            }
        });

        MORTGAGE_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AbstractPropertyTile property = (AbstractPropertyTile) myGame.getBoard().getAdjacentTiles( myGame.getBoard().getJailTile() ).get( 0 );
                //HARDCODED
                property.mortgageProperty();

            }
        });

        PAY_RENT_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            //WORKS
            @Override
            public void handle(ActionEvent actionEvent) {
                AbstractPropertyTile property = (AbstractPropertyTile) myGame.getBoard().getPlayerTile( myGame.getMyTurn().getMyCurrPlayer());
//                System.out.println("initial owner:" + property.getOwner().getMoney());
//                System.out.println("intial payee: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
                myGame.getMyTurn().getMyCurrPlayer().payFullAmountTo(property.getOwner(), property.calculateRentPrice( myGame.getMyTurn().getNumMoves()));
//                System.out.println("After owner:" + property.getOwner().getMoney());
//                System.out.println("After payee: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
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

        COLLECT_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            //WORKS
            @Override
            public void handle(ActionEvent actionEvent) {
                Boolean passed = true; //temp variable
                if(passed){
//                    System.out.println("initial money: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
                    myGame.getBoard().getBank().payFullAmountTo( myGame.getMyTurn().getMyCurrPlayer(), myGame.getBoard().getGoTile().getPassedMoney() );
                    displayActionInfo( "You collected " + myGame.getBoard().getGoTile().getPassedMoney() + " for passing go." );
//                    System.out.println("After money: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());

                } else {
                    //means you landed directly on it
//                    System.out.println("initial money: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
//                    System.out.println("landing on go money: " + myGame.getBoard().getGoTile().getLandedOnMoney());

                    myGame.getBoard().getBank().payFullAmountTo( myGame.getMyTurn().getMyCurrPlayer(), myGame.getBoard().getGoTile().getLandedOnMoney() );
                    displayActionInfo( "You collected " + myGame.getBoard().getGoTile().getLandedOnMoney() + " for landing on go." );

//                    System.out.println("After money: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());

                }
            }
        });

        GO_TO_JAIL_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            //WORKS
            @Override
            public void handle(ActionEvent actionEvent) {
                myGame.getMyTurn().goToJail();
                displayActionInfo( "Arrested! You're going to Jail." );
//                System.out.println("current tile: " + myGame.getBoard().getPlayerTile(myGame.getMyTurn().getMyCurrPlayer()).getName());
            }
        });

        PAY_BAIL_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            //WORKS
            @Override
            public void handle(ActionEvent actionEvent) {
//                System.out.println("initial: " + myGame.getMyTurn().getMyCurrPlayer().inJail());
//                System.out.println("initial: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
                myGame.getMyTurn().getMyCurrPlayer().payFullAmountTo( myGame.getBoard().getBank(), myGame.getBoard().getJailTile().getBailAmount() );
                myGame.getBoard().getJailTile().removeCriminal( myGame.getMyTurn().getMyCurrPlayer() );
                displayActionInfo( "You've successfully paid bail. You're free now!" );
//                System.out.println("After: " + myGame.getMyTurn().getMyCurrPlayer().inJail());
//                System.out.println("After: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
            }
        });

        FORFEIT_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            //WORKS
            @Override
            public void handle(ActionEvent actionEvent) {
                AbstractPlayer player = myGame.getMyTurn().getMyCurrPlayer();
                //TEMPORARY FIX
//                System.out.println("initial money:" + player.getMoney());
//                System.out.println("initial properties:" + player.getProperties());
//                System.out.println("initial bankruptcy: "+ player.isBankrupt());
                player.declareBankruptcy(myGame.getBoard().getBank());
                myGame.getBoard().getMyPlayerList().remove( player );
                myGame.getBoard().getPlayerTileMap().remove( player );
                //MUST REMOVE FROM FRONT END

//                System.out.println("After money:" + player.getMoney());
//                System.out.println("initial properties:" + player.getProperties());
//                System.out.println("initial bankruptcy: "+ player.isBankrupt());

//                System.out.println("current tile: " + myGame.getBoard().getPlayerTile(myGame.getMyTurn().getMyCurrPlayer()).getName());
            }
        });

        MOVE_HANDLER_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            //WORKS
            @Override
            public void handle(ActionEvent actionEvent) {
                myGame.getMyTurn().goToJail();
                displayActionInfo( "Arrested! You're going to Jail." );
//                System.out.println("current tile: " + myGame.getBoard().getPlayerTile(myGame.getMyTurn().getMyCurrPlayer()).getName());
            }
        });


        moveCheatKey.getChildren().addAll(movesField, MOVE_BUTTON);
        playerOptionsModal.getChildren().addAll(
                myDiceView, ROLL_BUTTON,
                playersText, currPlayerText,
                END_TURN_BUTTON, TRADE_BUTTON,
                AUCTION_BUTTON, MORTGAGE_BUTTON,
                moveCheatKey, BUY_BUTTON, COLLECT_BUTTON, GO_TO_JAIL_BUTTON, PAY_RENT_BUTTON, PAY_BAIL_BUTTON, FORFEIT_BUTTON
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

    private TextFlow createPlayersText() {
        TextFlow playersText = new TextFlow();
        Text title = new Text("Joined Players: \n");
        playersText.getChildren().add( title );
        setPlayerNameAndIcon( playersText );
        playersText.setStyle("-fx-max-width: 150; -fx-max-height: 200");
        playersText.setStyle( "-fx-background-color: white" );
        playersText.setMaxWidth( Control.USE_PREF_SIZE);
        playersText.setMaxHeight(Control.USE_PREF_SIZE);
        return playersText;
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

    private void setPlayerNameAndIcon(TextFlow box) {
        for (TextField p : myFormView.getPlayerToIconMap().keySet()){
            if(!p.getText().equals("")){
                Text player = new Text(p.getText());
                ImageView icon = new ImageView(  myFormView.getPlayerToIconMap().get( p ).getValue() + ".png");
                icon.setFitWidth( 25 );
                icon.setFitHeight( 25 );
                icon.setPreserveRatio( true );
                Text nextLine = new Text("\n");
                box.getChildren().addAll( player, icon, nextLine );
            }
        }
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

