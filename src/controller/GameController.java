package controller;

import backend.assetholder.Bank;
import backend.assetholder.HumanPlayer;
import backend.board.StandardBoard;
import backend.deck.DeckInterface;
import backend.dice.AbstractDice;
import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.exceptions.IllegalInputTypeException;
import backend.tile.AbstractTaxTile;
import backend.tile.IncomeTaxTile;
import configuration.ImportPropertyFile;
import configuration.XMLData;
import frontend.screens.BoardModeScreen;
import frontend.screens.TestingScreen;
import frontend.views.game.AbstractGameView;
import frontend.views.game.SplitScreenGameView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static controller.Actions.GO_TO_JAIL;

public class GameController {

    //TODO: make all the back-end stuff be managed by a MonopolyModel/board class
    private List<AbstractPlayer> myPlayers;
    private AbstractDice         myDice;
    private TestingScreen        myTestScreen;
    private Bank myBank;


    private AbstractBoard        myBoard;
    private Turn                 myTurn;
    private ImportPropertyFile  myPropertyFile;
    private Map<String, EventHandler<ActionEvent>> handlerMap = new HashMap<>();
    //Strings are all actions
    private AbstractGameView myGameView;

    public GameController(TestingScreen view, AbstractDice dice, DeckInterface chanceDeck, DeckInterface chestDeck, AbstractBoard board, Map<TextField, ComboBox> playerToIcon) {
        myDice = dice;
        chanceDeck = chanceDeck;
        chestDeck = chestDeck;

        //TODO: need money and totalPropertiesLeft read in from Data File
        XMLData myData = null;
        try {
            myData = new XMLData("OriginalMonopoly.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        myBank = myData.getBank();

        myTestScreen = view;

        // make first param list of players
        myPlayers = new ArrayList<>();
        //TODO: need money read in from data file
        for (TextField player: playerToIcon.keySet()){
            if (!player.getText().equals("")) {
                myPlayers.add(new HumanPlayer(player.getText(), 1500.0, myBank));
            }
        }

        myBoard = new StandardBoard(
                myPlayers,
                myData.getAdjacencyList(),
                myData.getPropertyCategoryMap(),
                myData.getFirstTile(),
                myData.getBank());

        myTurn = new Turn(myBoard.getMyPlayerList().get(0), myDice, myBoard);
    }

    public GameController(double width, double height, ImportPropertyFile propertyFile, String configFile) {
        //TODO: need money and totalPropertiesLeft read in from Data File
        XMLData myData = null;
        try {
            myData = new XMLData(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        myGameView = new SplitScreenGameView(width, height, propertyFile);
//        myBoard = new StandardBoard(
//                myPlayers,
//                myData.getAdjacencyList(),
//                myData.getPropertyCategoryMap(),
//                myData.getFirstTile(),
//                myData.getBank(),
//                myFormView.getDice(),
//                myFormView.getPlayerToIcon());
        addHandlers();
        //myTurn = new Turn(myBoard.getMyPlayerList().get(0), myDice, myBoard);
    }

    public void startGameLoop() {
        BorderPane bPane = (BorderPane) myTestScreen.getMyScene().getRoot();
        StackPane boardStackPane = (StackPane) bPane.getCenter();
        ObservableList vList = boardStackPane.getChildren();

        // TODO: CANNOT HARDCODE GETTING 1st element in vList (the VBox)
        // TODO: Maybe use "setUserData" for the VBox and retrieve that way
        VBox playerOptionsModal = (VBox) vList.get(1);

        // TODO: SIMILAR AS TODO ABOVE, SHOULDN'T HARDCODE FOR 0th ELEMENT
        // TODO: In VBOX FOR INNER HBOX
        Button rollButton = (Button) playerOptionsModal.getChildren().get(1);
        rollButton.setOnAction(f -> handleRollButton());

        // TODO: REFLECTION FOR ALL OF THIS
        Button endTurnButton = (Button) playerOptionsModal.getChildren().get(4);
        endTurnButton.setOnAction(f -> handleEndTurnButton());
    }

    private void handleEndTurnButton() {
        myTurn.skipTurn();
        myTestScreen.updateCurrentPlayer(myTurn.getMyCurrPlayer());
    }

    private void handleRollButton() {
        myTurn.start();
        myTestScreen.updateDice(myTurn);
        myTestScreen.getMyBoardView().move(myTurn.getNumMoves());

        myTurn.move();
        myTestScreen.updatePlayerPosition(myTurn.getNumMoves());
        List<String> possibleActions = myTurn.getMyActions();
        //TODO: front end display these two possible actions

    }

    public AbstractBoard getBoard() { return myBoard; }
    public AbstractDice getMyDice() { return myDice; }
    public Turn getMyTurn() { return myTurn; }
    public int getNumberOfPlayers() {
        return myPlayers.size();
    }

    public AbstractPlayer getPlayerAtIndex(int i) {
        return myPlayers.get(i);
    }

    public String getPlayerNameAtIndex(int i) {
        return getPlayerAtIndex(i).getMyPlayerName();
    }

    private void addHandlers(){
        handlerMap.put("AUCTION",event->this.handleAuction());
        handlerMap.put("BUY",event->this.handleBuy());
        handlerMap.put("SELL TO BANK",event->this.handleSellToBank());
        handlerMap.put("SELL TO PLAYER",event->this.handleSellToPlayer());
        handlerMap.put("DRAW CARD",event->this.handleDrawCard());
        handlerMap.put("GO TO JAIL",event->this.handleGoToJail());
        handlerMap.put("PAY TAX FIXED",event->this.handlePayTaxFixed());
        handlerMap.put("PAY TAX PERCENTAGE",event->this.handlePayTaxPercentage());
        handlerMap.put("PAY RENT",event->this.handlePayRent());
        handlerMap.put("PAY BAIL",event->this.handlePayBail());
        handlerMap.put("COLLECT MONEY",event->this.handleCollectMoney());
        handlerMap.put("UPGRADE", event->this.upgradeProperty());
        handlerMap.put("TRADE",event->this.handleTrade());
        myGameView.createOptions(handlerMap);
        myGameView.addPlayerOptionsView();
    }

    private void upgradeProperty() {
    }

    private void handleCollectMoney() {
    }

    private void handlePayBail(){
    }

    private void handleTrade() {
    }

    public void handlePayTaxPercentage() {
        System.out.println("initial money: " + myTurn.getMyCurrPlayer().getMoney());
        myTurn.getMyCurrPlayer().payFullAmountTo( myBoard.getBank(),myTurn.getMyCurrPlayer().getMoney() * ((IncomeTaxTile)myTurn.currPlayerTile()).getTaxMultiplier() );
        System.out.println("after money: " + myTurn.getMyCurrPlayer().getMoney());
    }

    private void handlePayRent() {

    }

    private void handleTileLanding() {
        List<String> actions = new ArrayList<>();
        actions.add("PAY TAX PERCENTAGE");
        actions.add("PAY TAX FIXED");
        String action = myGameView.showOnTileLandingActions(actions,"Options", "Tile Actions", "Choose One");
    }

    private void handlePayTaxFixed() {
        myTurn.getMyCurrPlayer().payFullAmountTo( myBoard.getBank(), ((AbstractTaxTile)myTurn.currPlayerTile()).getAmountToDeduct() );
    }

    private void handleGoToJail() {
    }

    private void handleDrawCard() {
    }

    private void handleSellToPlayer() {
    }

    private void handleSellToBank() {
    }

    private void handleBuy() {
        Map.Entry<AbstractPlayer, Double> playerValue = this.getMyTurn().buy(null);
        String info = playerValue.getKey().getMyPlayerName() + " bought " + this.getMyTurn().getTileNameforPlayer(playerValue.getKey()) + " for " + playerValue.getValue() + " Monopoly Dollars!";
        myGameView.displayActionInfo(info);
    }

    private void handleAuction() {
        Map<AbstractPlayer,Double> auctionAmount = new HashMap<>();
        for (int i = 0; i < myPlayers.size(); i++) {
            AbstractPlayer key = getPlayerAtIndex(i);
            String value = myGameView.showInputTextDialog("Auction Amount for player " + getPlayerNameAtIndex(i),
                    "Enter your auction amount:",
                    "Amount:");
            try {
                auctionAmount.put(key, Double.parseDouble((value)));
            } catch (NumberFormatException n) {
                new IllegalInputTypeException("Input must be a number!");
                i--;
            }
        }
        Map.Entry<AbstractPlayer, Double> winner = getMyTurn().auction(auctionAmount);
        String info = winner.getKey().getMyPlayerName() + " wins " + this.getMyTurn().getTileNameforPlayer(winner.getKey()) + " for " + winner.getValue() + " Monopoly Dollars!";
        myGameView.displayActionInfo(info);
        Map<AbstractPlayer, Double> playerValue = convertEntrytoMap(winner);
        this.getMyTurn().onAction("buy", playerValue);
    }

    private Map<AbstractPlayer, Double> convertEntrytoMap(Map.Entry<AbstractPlayer,Double> param) {
        Map<AbstractPlayer, Double> mapFromSet = new HashMap<>();
        mapFromSet.put(param.getKey(), param.getValue());
        return mapFromSet;
    }

    public Node getGameNode() {
        return myGameView.getPane();
    }
}
