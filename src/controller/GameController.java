package controller;

import backend.assetholder.Bank;
import backend.assetholder.HumanPlayer;
import backend.board.StandardBoard;
import backend.deck.DeckInterface;
import backend.deck.NormalDeck;
import backend.dice.AbstractDice;
import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.exceptions.IllegalInputTypeException;
import backend.exceptions.TileNotFoundException;
import backend.exceptions.ImprovedPropertyException;
import backend.exceptions.MortgagePropertyException;
import backend.tile.AbstractTaxTile;
import backend.tile.IncomeTaxTile;
import backend.tile.AbstractPropertyTile;
import configuration.ImportPropertyFile;
import configuration.XMLData;
import frontend.screens.TestingScreen;
import frontend.views.game.AbstractGameView;
import frontend.views.game.SplitScreenGameView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {

    //TODO: make all the back-end stuff be managed by a MonopolyModel/board class
    private XMLData              myData;
    private List<AbstractPlayer> myPlayers;
    private DeckInterface        myChanceDeck;
    private DeckInterface        myChestDeck;
    private AbstractBoard        myBoard;
    private AbstractDice         myDice;
    private TestingScreen        myTestScreen;
    private Bank myBank;
    private Turn                 myTurn;
    private ImportPropertyFile   myPropertyFile;
    private Map<String, EventHandler<ActionEvent>> handlerMap = new HashMap<>();
    //Strings are all actions
    private AbstractGameView myGameView;

    public GameController(TestingScreen view, AbstractDice dice, Map<TextField, ComboBox> playerToIcon) {
        myDice = dice;
        // TODO: CHANGE THIS TO JUST BEING READ IN FROM DATA
        // TODO: TO BE A PART OF BOARD NOT GAME CONTROLLER
        myChanceDeck = new NormalDeck();
        myChestDeck = new NormalDeck();
        // TODO: CHANGE THIS TO JUST BEING READ IN FROM DATA
        // TODO: TO BE A PART OF BOARD NOT GAME CONTROLLER

        //TODO: need money and totalPropertiesLeft read in from Data File
        try {
            myData = new XMLData("OriginalMonopoly.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        myBank = myData.getBank();
        myTestScreen = view;
        myBoard = makeBoard(playerToIcon);
        myPlayers = myBoard.getMyPlayerList();
        myTurn = new Turn(myBoard.getMyPlayerList().get(0), myDice, myBoard);
    }

    private AbstractBoard makeBoard(Map<TextField, ComboBox> playerToIcon) {
        return new StandardBoard(
                makePlayerList(playerToIcon), myData.getAdjacencyList(),
                myData.getPropertyCategoryMap(), myData.getFirstTile(),
                myBank
        );
    }

    private List<AbstractPlayer> makePlayerList(Map<TextField, ComboBox> playerToIcon) {
        List<AbstractPlayer> playerList = new ArrayList<>();

        for (TextField pName : playerToIcon.keySet()) {
            String name = pName.getText();
            if (! name.equals(""))
                playerList.add(new HumanPlayer(
                        name,
                        makeIcon((String) playerToIcon.get(pName).getValue()),
                        1500.00));
        }

        return playerList;
    }

    private ImageView makeIcon(String iconPath) {
        Image image = new Image(iconPath + ".png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        return imageView;
    }

    public GameController(double width, double height, ImportPropertyFile propertyFile, String configFile) {
        //TODO: need money and totalPropertiesLeft read in from Data File
        XMLData myData = null;
        try {
            myData = new XMLData(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        myGameView = new SplitScreenGameView(width, height);
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
        myTestScreen.getMyScene().lookup("#endTurn");
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
//        handlerMap.put("SELL TO PLAYER",event->this.handleSellToPlayer());
        handlerMap.put("DRAW CARD",event->this.handleDrawCard());
        handlerMap.put("GO TO JAIL",event->this.handleGoToJail());
        handlerMap.put("PAY TAX FIXED",event->this.handlePayTaxFixed());
        handlerMap.put("PAY TAX PERCENTAGE",event->this.handlePayTaxPercentage());
//        handlerMap.put("PAY RENT",event->this.handlePayRent());
        handlerMap.put("PAY BAIL",event->this.handlePayBail());
        handlerMap.put("COLLECT MONEY",event->this.handleCollectMoney());
        handlerMap.put("UPGRADE", event->this.upgradeProperty());
        handlerMap.put("TRADE",event->this.handleTrade());
//        handlerMap.put("mortgage", event->this.handleMortgage());
//        handlerMap.put("forfeit",event->this.handleForfeit());

        myGameView.createOptions(handlerMap);
        myGameView.addPlayerOptionsView();
    }

    private void upgradeProperty() {
    }

    private void handleCollectMoney() {
        Boolean passed = true; //temp variable
        if(passed){
            myBank.payFullAmountTo( myTurn.getMyCurrPlayer(), myBoard.getGoTile().getPassedMoney() );
            myGameView.displayActionInfo( "You collected " + myBoard.getGoTile().getPassedMoney() + " for passing go." );
        } else {
            //means you landed directly on it
            myBank.payFullAmountTo( myTurn.getMyCurrPlayer(), myBoard.getGoTile().getLandedOnMoney() );
            myGameView.displayActionInfo( "You collected " + myBoard.getGoTile().getLandedOnMoney() +" for landing on go." );
        }
    }

    private void handlePayBail(){
        try {
            myTurn.getMyCurrPlayer().payFullAmountTo(myBank, myBoard.getJailTile().getBailAmount());
            myBoard.getJailTile().removeCriminal(myTurn.getMyCurrPlayer());
            myGameView.displayActionInfo("You've successfully paid bail. You're free now!");
        } catch (TileNotFoundException e) {
            e.printStackTrace();

        }
    }

    private void handleMortgage(AbstractPropertyTile property){
        try {
            property.mortgageProperty();
        } catch (MortgagePropertyException m) {
            m.popUp();
        }
        catch (ImprovedPropertyException i) {
            i.popUp();
        }
    }

    private void handleTrade() {
    }

    private void handlePayTaxPercentage() {
        myTurn.getMyCurrPlayer().payFullAmountTo( myBoard.getBank(),myTurn.getMyCurrPlayer().getMoney() * ((IncomeTaxTile)myTurn.currPlayerTile()).getTaxMultiplier() );
    }

    private void handleTileLanding() {
        List<String> actions = new ArrayList<>();
        actions.add("PAY TAX PERCENTAGE");
        actions.add("PAY TAX FIXED");
        String action = myGameView.displayOptionsPopup(actions, "Options", "Tile Actions", "Choose One");
    }

    private void handlePayRent(AbstractPropertyTile property) {
        myTurn.getMyCurrPlayer().payFullAmountTo(property.getOwner(), property.calculateRentPrice( myTurn.getNumMoves()));
    //all methods with payment involved have to update front end display of money as well
    }

    private void handlePayTaxFixed() {
        myTurn.getMyCurrPlayer().payFullAmountTo( myBoard.getBank(), ((AbstractTaxTile)myTurn.currPlayerTile()).getAmountToDeduct() );
    }

    private void handleGoToJail() {
       myTurn.goToJail();
       myGameView.displayActionInfo( "Arrested! You're going to Jail." );
    }

    private void handleDrawCard() {

    }

    private void handleSellToPlayer(AbstractPlayer buyer, AbstractPropertyTile tile) {
//        System.out.println("initial money for owner: " + myTurn.getMyCurrPlayer().getMoney() + " " + myTurn.getMyCurrPlayer().getProperties() + " " + tile.isMortgaged());
//        System.out.println("initial money for buyer: " + buyer.getMoney() + " " + buyer.getProperties());
        //TODO: check if property is improved
        try {
            double amount = 0;
            boolean sellAmountDetermined = false;
            while (!sellAmountDetermined) {
                String value = myGameView.showInputTextDialog("Amount to sell to player " + buyer.getMyPlayerName(),
                        "Enter your proposed amount:",
                        "Amount:");
                try {
                    amount = Double.parseDouble(value);
                } catch (NumberFormatException n) {
                    new IllegalInputTypeException("Input must be a number!");
                }
                List<String> options = listYesNoOptionsOnly();
                String result = myGameView.displayOptionsPopup(options, "Proposed Amount", "Do you accept the proposed amount below?", value + " Monopoly dollars");
                if (result.equals("Yes")) {
                    sellAmountDetermined = true;
                    tile.sellTo(buyer,amount,getSameSetProperties(tile));
//                System.out.println("after money for owner: " + myTurn.getMyCurrPlayer().getMoney() + " " + myTurn.getMyCurrPlayer().getProperties());
//                System.out.println("after money for buyer: " + buyer.getMoney() + " " + buyer.getProperties() + " " + tile.isMortgaged());
                    if (tile.isMortgaged()) {
                        result = myGameView.displayOptionsPopup(options, "Property is mortgaged", "Would you like to lift the mortgage? ", "Choose an option");
                        if (result.equals("Yes")) {
                            tile.unmortgageProperty();
                        }
                        else {
                            tile.soldMortgagedPropertyLaterUnmortgages();
                        }
                    }
                }
            }
        } catch (MortgagePropertyException m) {
             m.popUp();
        }
//        System.out.println("after after money for owner: " + myTurn.getMyCurrPlayer().getMoney() + " " + myTurn.getMyCurrPlayer().getProperties());
//        System.out.println("after after money for buyer: " + buyer.getMoney() + " " + buyer.getProperties() + " " + tile.isMortgaged());
    }

    public List<String> listYesNoOptionsOnly() {
        List<String> options = new ArrayList<>();
        options.add("Yes");
        options.add("No");
        return options;
    }

    private void handleSellToBank() {

    }

    private void handleBuy() {
        Map.Entry<AbstractPlayer, Double> playerValue = this.getMyTurn().buy(null);
        String info = playerValue.getKey().getMyPlayerName() + " bought " + this.getMyTurn().getTileNameforPlayer(playerValue.getKey()) + " for " + playerValue.getValue() + " Monopoly Dollars!";
        myGameView.displayActionInfo(info);
    }

    private void handleForfeit(AbstractPlayer player){
        player.declareBankruptcy(getBoard().getBank());
        //Grey out all player info, remove them from board (something getChildren.remove)
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

    private List<AbstractPropertyTile> getSameSetProperties(AbstractPropertyTile property) {
        return myBoard.getColorListMap().get( property.getCard().getCategory());
    }
}
