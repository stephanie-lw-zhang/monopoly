package frontend.screens;

import backend.assetholder.AbstractPlayer;
import backend.board.StandardBoard;
import backend.dice.SixDice;
import backend.tile.AbstractPropertyTile;
import configuration.ImportPropertyFile;
import configuration.XMLData;
import controller.Turn;
import exception.*;
import frontend.views.LogView;
import frontend.views.board.AbstractBoardView;
import frontend.views.board.SquareBoardView;
import frontend.views.player_options.DiceView;

import frontend.views.FormView;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

    private final ImageView      backgroundImg;
    private SquareBoardView      myBoardView;
    private DiceView             myDiceView;
    private FormView             myFormView;
    private Scene                myScene;
    private GameController       myGame;
    private LogView              myLogView;
    private XMLData              myData;

    private TextArea             fundsDisplay;
    private TabPane              allPlayerProperties;

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
    private final Button UNMORTGAGE_BUTTON = new Button("Unmortgage");
    private final Button SELL_TO_BANK = new Button("SELL TO BANK");
    private final Button UPGRADE = new Button("UPGRADE");

    /**
     * TestingScreen main constructor
     * @param width
     * @param height
     * @param stage
     */
    public TestingScreen(double width, double height, Stage stage, AbstractScreen parent) {
        super(width, height, stage, parent);
        try {
            myData = new XMLData("OriginalMonopoly.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        backgroundImg = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("background.jpg")));
        myScene = makeScreen();
        myScene.setOnKeyPressed(f -> handleKeyInput(f.getCode()));
        //        Image myCursor = new Image(this.getClass().getClassLoader().getResourceAsStream("mustacheCursor.png"), 20,20,true,true);
//        myScene.setCursor(new ImageCursor(myCursor));
    }

    /**
     * Overridden makeScreen method for TestingScreen
     * @return
     */
    @Override
    public Scene makeScreen() {
        BorderPane bPane = new BorderPane();
        backgroundImg.setSmooth(true);
        backgroundImg.setCache(true);
        backgroundImg.setFitWidth(getScreenWidth());
        backgroundImg.setFitHeight(getScreenHeight());

        myFormView = new FormView(this);

        myLogView = new LogView(myData);

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

        return new Scene(bPane, getScreenWidth(), getScreenHeight());
    }

    /**
     * Handles start of game
     *
     * TODO: REFACATOR TO GAMESETUPCONTROLLER
     *
     * @param playerToIcon
     */
    public void handleStartGameButton(Map<TextField, ComboBox> playerToIcon) {
        myGame = new GameController(
                this,
                new SixDice(),
                playerToIcon
        );

        myBoardView = new SquareBoardView(
                new StandardBoard(myGame.getBoard().getMyPlayerList(), myData),
                getScreenWidth()*0.5, getScreenHeight()*0.9,
                90,11,11
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
                myBoardView.move(myGame.getMyTurn().getMyCurrPlayer().getMyIcon(), numMoves);
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
                myLogView.gameLog.setText(info);
//                System.out.println("after buy money: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
//                System.out.println("after buy properties: " + myGame.getMyTurn().getMyCurrPlayer().getProperties());
                updatePlayerFundsDisplay();
                updatePlayerPropertiesDisplay();
            }
        });

        MORTGAGE_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                myGame.handleMortgage();
            }
        });

        PAY_RENT_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            //WORKS
            @Override
            public void handle(ActionEvent actionEvent) {
                myGame.handlePayRent();
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
                myLogView.gameLog.setText(info);
                Map<AbstractPlayer, Double> playerValue = convertEntrytoMap(winner);
                myGame.getMyTurn().onAction("buy", playerValue);
                updatePlayerFundsDisplay();
                updatePlayerPropertiesDisplay();
            }
        });

        COLLECT_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            //WORKS
            @Override
            public void handle(ActionEvent actionEvent) {
                Boolean passed = true; //TODO: find a way to not dynamically get variable
                if(passed){
//                    System.out.println("initial money: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
                    myGame.getBoard().getBank().payFullAmountTo( myGame.getMyTurn().getMyCurrPlayer(), myGame.getBoard().getGoTile().getPassedMoney() );
                    displayActionInfo( "You collected " + myGame.getBoard().getGoTile().getPassedMoney() + " for passing go." );
                    myLogView.gameLog.setText(myGame.getMyTurn().getMyCurrPlayer() + " collected " + myGame.getBoard().getGoTile().getLandedOnMoney() + " for passing go.");

//                    System.out.println("After money: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());

                } else {
                    //means you landed directly on it
//                    System.out.println("initial money: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
//                    System.out.println("landing on go money: " + myGame.getBoard().getGoTile().getLandedOnMoney());

                    myGame.getBoard().getBank().payFullAmountTo( myGame.getMyTurn().getMyCurrPlayer(), myGame.getBoard().getGoTile().getLandedOnMoney() );
                    displayActionInfo( "You collected " + myGame.getBoard().getGoTile().getLandedOnMoney() + " for landing on go." );
                    myLogView.gameLog.setText(myGame.getMyTurn().getMyCurrPlayer() + " collected " + myGame.getBoard().getGoTile().getLandedOnMoney() + " for landing on go.");


//                    System.out.println("After money: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());

                }
                updatePlayerFundsDisplay();
            }
        });

        GO_TO_JAIL_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            //WORKS
            @Override
            public void handle(ActionEvent actionEvent) {
                myGame.handleGoToJail();
            }
        });

        PAY_BAIL_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
            //WORKS
            @Override
            public void handle(ActionEvent actionEvent) {
                myGame.handlePayBail();
            }
        });

//        FORFEIT_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
//            //WORKS
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                myGame.handleForfeit();
//            }
//        });

        MOVE_HANDLER_BUTTON.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent actionEvent) {
                if(myGame.getMyTurn().getMyCurrPlayer().getTurnsInJail() == 1 || myGame.getMyTurn().getMyCurrPlayer().getTurnsInJail() == 2){
                    new IllegalMoveException( "Cannot move because you are in jail." );
                }
                else{
                    myGame.getBoard().movePlayer( myGame.getMyTurn().getMyCurrPlayer(), myGame.getMyTurn().getNumMoves() );
                }
            }
        });

//        UNMORTGAGE_BUTTON.setOnAction(new EventHandler<ActionEvent>() {
//            //WORKS
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                myGame.handleUnmortgage();
//            }
//        });

        SELL_TO_BANK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                myGame.handleSellToBank();
            }
        });

        UPGRADE.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                myGame.handleUpgradeProperty();
            }
        });

        moveCheatKey.getChildren().addAll(movesField, MOVE_BUTTON);
        playerOptionsModal.getChildren().addAll(
                myLogView.gameLog,
                myDiceView, ROLL_BUTTON,
                playersText, currPlayerText,
                END_TURN_BUTTON, TRADE_BUTTON, UPGRADE,
                AUCTION_BUTTON, MORTGAGE_BUTTON, BUY_BUTTON,
                moveCheatKey, createPlayerPropertiesDisplay(),
//                BUY_BUTTON, COLLECT_BUTTON, GO_TO_JAIL_BUTTON, PAY_RENT_BUTTON, PAY_BAIL_BUTTON,
                FORFEIT_BUTTON,
                MOVE_HANDLER_BUTTON, UNMORTGAGE_BUTTON, SELL_TO_BANK, createPlayerFundsDisplay()
        );

        playerOptionsModal.setPadding(new Insets(15, 0, 0, 15));
        playerOptionsModal.setAlignment(Pos.CENTER_RIGHT);
        myDiceView.setAlignment(Pos.CENTER_RIGHT);

        Pane boardViewPane = (Pane) myBoardView.getBoardViewNode();
        boardStackPane.setAlignment(boardViewPane,Pos.CENTER_LEFT);
        boardStackPane.getChildren().addAll(boardViewPane, playerOptionsModal);

        Pane logViewPane = myLogView.getPane();

        bPane.setTop(null);
        bPane.setCenter(boardStackPane);

        // TODO: CONDITION FOR GAME END LOGIC????
        myGame.startGameLoop();
    }

    //TODO: delete these (FOR TESTING rn)
    public String showInputTextDialog(String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        else {
            //TODO: throw exception
            return null;
        }
    }

    public String displayOptionsPopup(List<String> options, String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        List<ButtonType> buttonOptions = new ArrayList<>();
        for (String option : options) {
            buttonOptions.add(new ButtonType(option));
        }
        alert.getButtonTypes().setAll(buttonOptions);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get().getText();
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

    private TabPane createPlayerPropertiesDisplay(){
        allPlayerProperties = new TabPane( );
        for(AbstractPlayer p: myGame.getBoard().getMyPlayerList()){
            Tab tab = new Tab(p.getMyPlayerName());
            tab.setId( p.getMyPlayerName() );
            writeInPlayerProperties( p, tab );
            allPlayerProperties.getTabs().add( tab );
        }
        allPlayerProperties.setMaxHeight( 200 );
        allPlayerProperties.setMaxWidth( 200 );
        allPlayerProperties.setTabClosingPolicy( TabPane.TabClosingPolicy.UNAVAILABLE);
        return allPlayerProperties;

    }

    private void updatePlayerPropertiesDisplay() {
        for(Tab tab: allPlayerProperties.getTabs()){
            AbstractPlayer player = myGame.getBoard().getPlayerFromName( tab.getText() );
            writeInPlayerProperties(player, tab);
        }
    }

    private void writeInPlayerProperties(AbstractPlayer player, Tab tab){
        TextArea properties = new TextArea();
        String text = "";
        for(AbstractPropertyTile prop: player.getProperties()){
            text = text + prop.getTitleDeed() + "\n";
        }
        properties.setText( text );
        tab.setContent( properties );
    }

    private TextArea createPlayerFundsDisplay(){
        fundsDisplay = new TextArea( );
        updatePlayerFundsDisplay();
        fundsDisplay.setMaxHeight( 150 );
        fundsDisplay.setMaxWidth( 200 );
        fundsDisplay.setStyle( "-fx-background-color: white" );
        return fundsDisplay;
    }

    private void updatePlayerFundsDisplay(){
        String text = "Player Funds \n";
        for(AbstractPlayer p: myGame.getBoard().getMyPlayerList() ){
            text = text + p.getMyPlayerName() + ": " + p.getMoney() + "\n";
        }
        fundsDisplay.setText( text );
    }

    private Map<AbstractPlayer, Double> convertEntrytoMap(Map.Entry<AbstractPlayer,Double> param) {
        Map<AbstractPlayer, Double> mapFromSet = new HashMap<>();
        mapFromSet.put(param.getKey(), param.getValue());
        return mapFromSet;
    }

    private void displayActionInfo(String info) {
        Alert formAlert = new Alert(Alert.AlertType.INFORMATION);
        formAlert.setContentText(info);
        myLogView.gameLog.setText(info);
        formAlert.showAndWait();
    }

    public String displayDropDownAndReturnResult(String title, String prompt, ObservableList<String> options) throws CancelledActionException, PropertyNotFoundException {
        try {
            ChoiceDialog choices = new ChoiceDialog( options.get( 0 ), options );
            choices.setHeaderText( title );
            choices.setContentText(prompt);
            //"Select the player who wants to mortgage a property: "
            Optional<String> result = choices.showAndWait();
//        choices.showAndWait();
            if (result.isEmpty()) {
                throw new CancelledActionException("Action has been cancelled");
            }
            return (String) choices.getSelectedItem();
        } catch(IndexOutOfBoundsException n) {
            throw new PropertyNotFoundException("You do not own any properties");
        }
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

        TextArea currPlayerText = (TextArea) playerOptionsModal.getChildren().get(4);
        currPlayerText.setText(currPlayer.getMyPlayerName());
    }

    public void updateDice(final Turn turn) {
        myDiceView.onUpdate(turn);
        myLogView.gameLog.setText(myDiceView.getMyPopupText());
    }

    public void updatePlayerPosition(int roll) {
        myBoardView.move(myGame.getMyTurn().getMyCurrPlayer().getMyIcon(), roll);
    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.Q) {
            backToParent();
            //handleBackToMainButton(getMyStage());
        }
    }

    public Scene getMyScene() {
        return myScene;
    }

    @Override
    public void changeDisplayNode(Node n) {
    }

    public AbstractBoardView getMyBoardView() { return myBoardView; }
}