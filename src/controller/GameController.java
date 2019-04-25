package controller;

import backend.assetholder.Bank;
import backend.assetholder.HumanPlayer;
import backend.board.StandardBoard;
import backend.card.ActionCard;
import backend.deck.DeckInterface;
import backend.deck.NormalDeck;
import backend.dice.AbstractDice;
import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.tile.AbstractTaxTile;
import backend.tile.IncomeTaxTile;
import backend.tile.AbstractPropertyTile;
import backend.tile.*;
import configuration.ImportPropertyFile;
import configuration.XMLData;
import exception.*;
import frontend.screens.TestingScreen;
import frontend.views.game.AbstractGameView;
import frontend.views.game.SplitScreenGameView;
import frontend.views.player_stats.PlayerFundsView;
import frontend.views.player_stats.PlayerPropertiesView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
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
    private PlayerFundsView fundsView;
    private PlayerPropertiesView propertiesView;
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
        Button rollButton = (Button) playerOptionsModal.getChildren().get(2);
        rollButton.setOnAction(f -> handleRollButton());

        // TODO: REFLECTION FOR ALL OF THIS
        Button endTurnButton = (Button) playerOptionsModal.getChildren().get(5);
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
        myTestScreen.getMyBoardView().move(myTurn.getMyCurrPlayer().getMyIcon(), myTurn.getNumMoves());

        myTurn.move();
        myTestScreen.updatePlayerPosition(myTurn.getNumMoves());
        List<String> possibleActions = myTurn.getMyCurrentTileActions();
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
        handlerMap.put("BUY",event-> {
            try {
                this.handleBuy();
            } catch (IllegalActionOnImprovedPropertyException e) {
                e.popUp();
            } catch (OutOfBuildingStructureException e) {
                e.popUp();
            } catch (IllegalInputTypeException e) {
                e.popUp();
            }
        });
        handlerMap.put("SELL TO BANK",event->this.handleSellToBank());
        handlerMap.put("SELL TO PLAYER",event->this.handleSellToPlayer());
        handlerMap.put("DRAW CARD",event->this.handleDrawCard());
        handlerMap.put("GO TO JAIL",event->this.handleGoToJail());
        handlerMap.put("PAY TAX FIXED",event->this.handlePayTaxFixed());
        handlerMap.put("PAY TAX PERCENTAGE",event->this.handlePayTaxPercentage());
        handlerMap.put("PAY RENT",event->this.handlePayRent());
        handlerMap.put("PAY BAIL",event->this.handlePayBail());
        handlerMap.put("COLLECT MONEY",event->this.handleCollectMoney());
        handlerMap.put("UPGRADE", event->this.handleUpgradeProperty());
        handlerMap.put("TRADE",event->this.handleTrade());
        handlerMap.put("mortgage", event->this.handleMortgage());
        handlerMap.put("forfeit",event->this.handleForfeit());
        handlerMap.put( "unmortgage", event->this.handleUnmortgage() );

        myGameView.createOptions(handlerMap);
        myGameView.addPlayerOptionsView();
    }

    //TODO: make sure players can't choose themselves to sell
    public void handleUpgradeProperty() {
        try {
            ObservableList<String> players = getAllOptionNames(myBoard.getPlayerNamesAsStrings());
            AbstractPlayer owner = getSelectedPlayer("Upgrade Property", "Choose who is upgrading their property ", players);

            ObservableList<String> tiles = getAllOptionNames(myBoard.getBuildingTileNamesAsStrings(owner));
            BuildingTile tile = (BuildingTile) getSelectedPropertyTile("Upgrade Property", "Choose which property to upgrade ", tiles);

//            System.out.println("initial money for owner: " + myTurn.getMyCurrPlayer().getMoney() + " " + myTurn.getMyCurrPlayer().getProperties());
//            System.out.println("initial money for bank: " + myBank.getMoney() + " House: " + myBank.getNumberOfBuildingsLeft("House") + " Hotel: " + myBank.getNumberOfBuildingsLeft("Hotel"));

            tile.upgrade(owner, myBoard.getSameSetProperties(tile));
            //TODO: add front-end implementation

//            System.out.println("after money for owner: " + myTurn.getMyCurrPlayer().getMoney() + " " + myTurn.getMyCurrPlayer().getProperties());
//            System.out.println("after money for bank: " + myBank.getMoney() + " House: " + myBank.getNumberOfBuildingsLeft("House") + " Hotel: " + myBank.getNumberOfBuildingsLeft("Hotel"));

        } catch (IllegalInputTypeException e) {
            e.popUp();
        } catch (OutOfBuildingStructureException e) {
            e.popUp();
        } catch (CancelledActionException e) {
            e.doNothing();
        } catch (PropertyNotFoundException e) {
            e.popUp();
        }
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

    public void handlePayBail(){
        try {
//                System.out.println("initial: " + myGame.getMyTurn().getMyCurrPlayer().inJail());
//                System.out.println("initial: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
            getMyTurn().getMyCurrPlayer().payFullAmountTo(getBoard().getBank(), getBoard().getJailTile().getBailAmount());
            getBoard().getJailTile().removeCriminal(getMyTurn().getMyCurrPlayer());
            myGameView.displayActionInfo("You've successfully paid bail. You're free now!");
              fundsView.updatePlayerFundsDisplay(myBoard.getMyPlayerList());
            //TODO: COMMENT LOG VIEW BACK IN
//            myLogView.gameLog.setText(getMyTurn().getMyCurrPlayer() + " has posted bail and can roll to leave Jail!");

//                System.out.println("After: " + myGame.getMyTurn().getMyCurrPlayer().inJail());
//                System.out.println("After: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
        } catch(TileNotFoundException e) {
            e.popUp();
        }
    }

    public void handleMortgage(){
        try{
            AbstractPropertyTile property = (AbstractPropertyTile) getBoard().getAdjacentTiles(getBoard().getJailTile()).get(0);

            ObservableList<String> players = getAllPlayerNames();
            String mortgagerName = myGameView.displayDropDownAndReturnResult( "Mortgage", "Select the player who wants to mortgage: ", players );
            AbstractPlayer mortgager = getBoard().getPlayerFromName( mortgagerName );

            ObservableList<String> possibleProperties = FXCollections.observableArrayList();
            for(AbstractPropertyTile p: mortgager.getProperties()){
                possibleProperties.add( p.getName() );
            }

            if (possibleProperties.size()==0){
                myGameView.displayActionInfo( "You have no properties to mortgage at this time." );
            } else{
                String propertyToMortgage = myGameView.displayDropDownAndReturnResult( "Mortgage", "Select the property to be mortgaged", possibleProperties );
                for(AbstractPropertyTile p: mortgager.getProperties()){
                    if(p.getTitleDeed().equalsIgnoreCase( propertyToMortgage )){
                        property = p;
                    }
                }
            }
            property.mortgageProperty();
            //TODO: comment log view and updatePlayerFundsDisplay back in when refactored
//            myLogView.gameLog.setText(getMyTurn().getMyCurrPlayer() + " has mortgaged " + property + ".");
            fundsView.updatePlayerFundsDisplay(myBoard.getMyPlayerList());
        } catch (MortgagePropertyException e) {
            e.popUp();
        }catch (TileNotFoundException e) {
            e.popUp();
        }catch (IllegalActionOnImprovedPropertyException i) {
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

    public void handlePayRent() {
        AbstractPropertyTile property = (AbstractPropertyTile) getBoard().getPlayerTile( getMyTurn().getMyCurrPlayer());
//                System.out.println("initial owner:" + property.getOwner().getMoney());
//                System.out.println("intial payee: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
        getMyTurn().getMyCurrPlayer().payFullAmountTo(property.getOwner(), property.calculateRentPrice( getMyTurn().getNumMoves()));
//TODO: COMMENT LOG VIEW BACK IN
        //        myLogView.gameLog.setText(getMyTurn().getMyCurrPlayer() + " has paid " + property.calculateRentPrice( getMyTurn().getNumMoves()) + " of rent to " +property.getOwner()+ ".");
//                System.out.println("After owner:" + property.getOwner().getMoney());
//                System.out.println("After payee: " + myGame.getMyTurn().getMyCurrPlayer().getMoney());
        fundsView.updatePlayerFundsDisplay(myBoard.getMyPlayerList());
    }

    private void handlePayTaxFixed() {
        myTurn.getMyCurrPlayer().payFullAmountTo( myBoard.getBank(), ((AbstractTaxTile)myTurn.currPlayerTile()).getAmountToDeduct() );
    }

    public void handleGoToJail() {
        getMyTurn().goToJail();
        myGameView.displayActionInfo( "Arrested! You're going to Jail." );
        //TODO: COMMENT LOG VIEW BACK IN
//        myLogView.gameLog.setText(myGame.getMyTurn().getMyCurrPlayer() + " has been sent to Jail!");
    }

    private void handleDrawCard() {
        ActionCard drawCardTile = ((AbstractDrawCardTile) getBoard().getPlayerTile(getMyTurn().getMyCurrPlayer())).drawCard();
        drawCardTile.applyTo(getMyTurn().getMyCurrPlayer());
    }

    private void handleSellToPlayer() {
        try {
            ObservableList<String> players = getAllOptionNames(myBoard.getPlayerNamesAsStrings());
            AbstractPlayer owner = getSelectedPlayer("Sell Property", "Choose who is selling their property ", players);

            players.remove(owner.getMyPlayerName());
            AbstractPlayer buyer = getSelectedPlayer("Sell Property", "Choose who to sell property to ", players);

            ObservableList<String> tiles = getAllOptionNames(myBoard.getPropertyTileNamesAsStrings(owner));
            AbstractPropertyTile tile = getSelectedPropertyTile("Sell Property", "Choose which property to sell ", tiles);

            double amount = 0;
            boolean sellAmountDetermined = false;
            while (!sellAmountDetermined) {
                String value = myGameView.showInputTextDialog("Amount to sell to player " + buyer.getMyPlayerName(),
                        "Enter your proposed amount:",
                        "Amount:");
                try {
                    amount = Double.parseDouble(value);
                } catch (NumberFormatException n) {
                    new IllegalInputTypeException("Input must be a number!").popUp();
                }
                List<String> options = createListOf2OptionsAsStrings("Yes", "No");
                String result = myGameView.displayOptionsPopup(options, "Proposed Amount", "Do you accept the proposed amount below?", value + " Monopoly dollars");
                if (result.equalsIgnoreCase(options.get(0))) {
                    sellAmountDetermined = true;
                    tile.sellTo(buyer,amount,myBoard.getSameSetProperties(tile));
                    if (tile.isMortgaged()) {
                        result = myGameView.displayOptionsPopup(options, "Property is mortgaged", "Would you like to lift the mortgage? ", "Choose an option");
                        if (result.equals("Yes")) { tile.unmortgageProperty(); }
                        else { tile.soldMortgagedPropertyLaterUnmortgages(); }
                    }
                }
            }
           fundsView.updatePlayerFundsDisplay( myBoard.getMyPlayerList() );
            propertiesView.updatePlayerPropertiesDisplay( myBoard.getMyPlayerList() );
        } catch (MortgagePropertyException m) {
             m.popUp();
        } catch (IllegalActionOnImprovedPropertyException e) {
            e.popUp();
        } catch (IllegalInputTypeException e) {
            e.popUp();
        } catch (NullPointerException e) {
            new CancelledActionException("").doNothing();
        } catch (OutOfBuildingStructureException e) {
            e.popUp();
        } catch (CancelledActionException e) {
            e.doNothing();
        } catch (PropertyNotFoundException e) {
            e.popUp();
        }
    }

    public void handleSellToBank() {
        try {
            ObservableList<String> players = getAllOptionNames(myBoard.getPlayerNamesAsStrings());
            AbstractPlayer owner = getSelectedPlayer("Sell Buildings", "Choose who is selling a building", players);

            ObservableList<String> tiles = getAllOptionNames(myBoard.getBuildingTileNamesAsStrings(owner));
            BuildingTile tile = (BuildingTile) getSelectedPropertyTile("Sell Buildings", "Choose which property to sell buildings from ", tiles);

//            System.out.println("initial money for owner: " + myTurn.getMyCurrPlayer().getMoney() + " " + myTurn.getMyCurrPlayer().getProperties());
//            System.out.println("initial money for bank: " + myBank.getMoney() + " House: " + myBank.getNumberOfBuildingsLeft("House") + " Hotel: " + myBank.getNumberOfBuildingsLeft("Hotel"));

            List<String> options = createListOf2OptionsAsStrings("SELL ALL BUILDINGS ON ALL PROPERTIES OF SAME GROUP",
                    "SELL ONE BUILDING ON SELECTED PROPERTY");
            String str = myTestScreen.displayOptionsPopup(options, "Sell Buildings", "Sell buildings options ", "Choose one ");
            if (str.equalsIgnoreCase(options.get(0))) {
                tile.sellAllBuildingsOnTile(myBoard.getSameSetProperties(tile));
                //TODO: add front-end implementation
            } else {
                tile.sellOneBuilding(myBoard.getSameSetProperties(tile));
                //TODO: add front-end implementation
            }
//            System.out.println("after money for owner: " + myTurn.getMyCurrPlayer().getMoney() + " " + myTurn.getMyCurrPlayer().getProperties());
//            System.out.println("after money for bank: " + myBank.getMoney() + " House: " + myBank.getNumberOfBuildingsLeft("House") + " Hotel: " + myBank.getNumberOfBuildingsLeft("Hotel"));
        } catch (IllegalActionOnImprovedPropertyException e) {
            e.popUp();
        } catch (CancelledActionException e) {
            e.doNothing();
        } catch (PropertyNotFoundException e) {
            e.popUp();
        }
    }

    private void handleBuy() throws IllegalActionOnImprovedPropertyException, IllegalInputTypeException, OutOfBuildingStructureException {
        Map.Entry<AbstractPlayer, Double> playerValue = this.getMyTurn().buy(null);
        String info = playerValue.getKey().getMyPlayerName() + " bought " + this.getMyTurn().getTileNameforPlayer(playerValue.getKey()) + " for " + playerValue.getValue() + " Monopoly Dollars!";
        myGameView.displayActionInfo(info);
    }

    private void handleForfeit(){
        ObservableList<String> players = getAllPlayerNames();
        String player = myGameView.displayDropDownAndReturnResult( "Forfeit", "Select the player who wants to forfeit: ", players );
        AbstractPlayer forfeiter = getBoard().getPlayerFromName( player );

//                System.out.println("initial money:" + player.getMoney());
//                System.out.println("initial properties:" + player.getProperties());
//                System.out.println("initial bankruptcy: "+ player.isBankrupt());

        forfeiter.declareBankruptcy(getBoard().getBank());
        getBoard().getMyPlayerList().remove( forfeiter );
        getBoard().getPlayerTileMap().remove( forfeiter );
        //TODO: COMMENT LOG VIEW BACK IN
//        myLogView.gameLog.setText(forfeiter + " has forfeited.");

//                System.out.println("After money:" + player.getMoney());
//                System.out.println("initial properties:" + player.getProperties());
//                System.out.println("initial bankruptcy: "+ player.isBankrupt());

//                System.out.println("current tile: " + myGame.getBoard().getPlayerTile(myGame.getMyTurn().getMyCurrPlayer()).getName());
        fundsView.updatePlayerFundsDisplay(myBoard.getMyPlayerList());
        for(Tab tab: propertiesView.getTabs()){
            if(tab.getText().equalsIgnoreCase( player )){
                propertiesView.getTabs().remove(tab);
            }
        }
        propertiesView.updatePlayerPropertiesDisplay(getBoard().getMyPlayerList());
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

    private void handleUnmortgage(){
        try {
            AbstractPropertyTile property = (AbstractPropertyTile) getBoard().getAdjacentTiles( getBoard().getJailTile() ).get( 0 );
            property.unmortgageProperty();
            fundsView.updatePlayerFundsDisplay(myBoard.getMyPlayerList());
        } catch (MortgagePropertyException e) {
            e.popUp();
        } catch (TileNotFoundException e ) {
            e.popUp();
        }
    }

    private Map<AbstractPlayer, Double> convertEntrytoMap(Map.Entry<AbstractPlayer,Double> param) {
        Map<AbstractPlayer, Double> mapFromSet = new HashMap<>();
        mapFromSet.put(param.getKey(), param.getValue());
        return mapFromSet;
    }

    public Node getGameNode() {
        return myGameView.getGameViewNode();
    }

    public ObservableList<String> getAllOptionNames(List<String> names) {
        ObservableList<String> options = FXCollections.observableArrayList();
        for (String name : names) {
            options.add(name);
        }
        return options;
    }

    private List<String> createListOf2OptionsAsStrings(String option1, String option2) {
        List<String> options = new ArrayList<>();
        options.add(option1);
        options.add(option2);
        return options;
    }

    private AbstractPlayer getSelectedPlayer (String title, String prompt, ObservableList<String> players) throws CancelledActionException, PropertyNotFoundException {
        String person = myTestScreen.displayDropDownAndReturnResult( title, prompt, players );
        AbstractPlayer player = null;
        player = getBoard().getPlayerFromName( person );
        return player;
    }

    private AbstractPropertyTile getSelectedPropertyTile(String title, String prompt, ObservableList<String> properties) throws CancelledActionException, PropertyNotFoundException {
        String tile = myTestScreen.displayDropDownAndReturnResult( title, prompt, properties );

        AbstractPropertyTile property = null;
        property = (AbstractPropertyTile)getBoard().getPropertyTileFromName( tile );
        return property;
    }

    private ObservableList<String> getAllPlayerNames() {
        ObservableList<String> players = FXCollections.observableArrayList();
        for (AbstractPlayer p : getBoard().getMyPlayerList()) {
            players.add( p.getMyPlayerName() );
        }
        return players;
    }
}
