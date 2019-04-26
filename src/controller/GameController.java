package controller;

import backend.assetholder.Bank;
import backend.assetholder.HumanPlayer;
import backend.board.StandardBoard;
import backend.card.action_cards.HoldableCard;
import backend.deck.DeckInterface;
import backend.deck.NormalDeck;
import backend.dice.AbstractDice;
import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.dice.SixDice;
import backend.tile.AbstractTaxTile;
import backend.tile.IncomeTaxTile;
import backend.tile.AbstractPropertyTile;
import backend.tile.*;
import configuration.ImportPropertyFile;
import configuration.XMLData;
import exceptions.*;
import frontend.screens.TestingScreen;
import frontend.views.LogView;
import frontend.views.game.AbstractGameView;
import frontend.views.game.SplitScreenGameView;
import frontend.views.player_stats.PlayerCardsView;
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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.lang.reflect.Method;


public class GameController {

    private GameSetUpController mySetUpController;
    //TODO: make all the back-end stuff be managed by a MonopolyModel/board class
    private XMLData myData;
    private List<AbstractPlayer> myPlayers;
    private DeckInterface myChanceDeck;
    private DeckInterface myChestDeck;
    private AbstractBoard myBoard;
    private AbstractDice myDice;
    private TestingScreen myTestScreen;
    private Bank myBank;
    private Turn myTurn;
    private ImportPropertyFile myPropertyFile;
    private Map<String, EventHandler<ActionEvent>> handlerMap = new HashMap<>();
    private PlayerFundsView fundsView;
    private PlayerPropertiesView propertiesView;
    private PlayerCardsView cardsView;
    private LogView myLogView;
    private int numDoubleRolls;
    //Strings are all actions
    private AbstractGameView myGameView;
    private TileActionController tileActionController;

    public GameController(AbstractDice dice, Map<TextField, ComboBox> playerToIcon) {
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

        // myGameView = new SplitScreenGameView(width, height);
//        addHandlers();
        myBank = myData.getBank();
        myBoard = makeBoard(playerToIcon);
        myPlayers = myBoard.getMyPlayerList();
        myTurn = new Turn(myBoard.getMyPlayerList().get(0), myDice, myBoard);
        this.numDoubleRolls = 0;
        tileActionController = new TileActionController( getBoard(), getMyTurn(), myGameView, fundsView, propertiesView, myLogView, myTestScreen.getMyBoardView());
    }

    public GameController(TestingScreen view, AbstractDice dice, Map<TextField, ComboBox> playerToIcon){
        this(dice, playerToIcon);
        myTestScreen = view;
    }

    public GameController(double width, double height, GameSetUpController controller, AbstractBoard board, XMLData data){
        myBoard = board;
        myData = data;
        myGameView = new SplitScreenGameView(width, height, data, myBoard);
        mySetUpController = controller;
        addHandlers();
        myDice = new SixDice();
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
        myGameView = new SplitScreenGameView(width, height, myData,myBoard);
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

    private AbstractBoard makeBoard(Map<TextField, ComboBox> playerToIcon) {
        return new StandardBoard(
                makePlayerList(playerToIcon), myData.getAdjacencyList(),
                myData.getPropertyCategoryMap(), myData.getFirstTile(),
                myData.getBank()
        );
    }

    private List<AbstractPlayer> makePlayerList(Map<TextField, ComboBox> playerToIcon) {
        List<AbstractPlayer> playerList = new ArrayList<>();

        for (TextField pName : playerToIcon.keySet()) {
            String name = pName.getText();
            if (!name.equals(""))
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
        numDoubleRolls= 0;
    }

    private void handleRollButton() {
//        if(myGameView!=null){
//            myGameView.updateDice(myTurn);
//        } else {
//            myTestScreen.updateDice(myTurn);
//            myTestScreen.getMyBoardView().move(myTurn.getMyCurrPlayer().getMyIcon(), myTurn.getNumMoves());
//        }
        if(getMyTurn().getMyCurrPlayer().getTurnsInJail() == 0 || getMyTurn().getMyCurrPlayer().getTurnsInJail() == 1) {
            new IllegalMoveException("You cannot move because you are in jail. Roll a doubles to get out of jail for free!").popUp();
        }
        myTurn.start();
        myGameView.updateDice(myTurn);

        if (getMyTurn().isDoubleRoll(getMyTurn().getRolls())) {
            if(getMyTurn().getMyCurrPlayer().isInJail()) {
                getMyTurn().getMyCurrPlayer().getOutOfJail();
                myGameView.displayActionInfo("You are released from jail because you rolled doubles. You're free now!");
                myLogView.gameLog.setText(getMyTurn().getMyCurrPlayer().getMyPlayerName() + " rolled doubles and is out of Jail!");
                handleMove();
                //TODO: grey out roll button
            }
            else {
                //TODO: get rid of magic value
                 if (numDoubleRolls < 2) {
                     numDoubleRolls++;
                     handleMove();
                     myTestScreen.displayActionInfo("You rolled doubles. Roll again!");
                     //TODO: add log?
                }
                else { tileActionController.handleGoToJail(); }
            }
        }
        else {
            if (getMyTurn().getMyCurrPlayer().getTurnsInJail() == 2) {
                getMyTurn().getMyCurrPlayer().getOutOfJail();
                myGameView.displayActionInfo("You have had three turns in jail! You are free after you pay the fine.");
                tileActionController.handlePayBail();
                handleMove();
                //TODO: grey out roll button
            }
            else if(getMyTurn().getMyCurrPlayer().getTurnsInJail() == 1 || getMyTurn().getMyCurrPlayer().getTurnsInJail() == 2){
                new IllegalMoveException( "Cannot move because you are in jail." );
            }
            else if(getMyTurn().getMyCurrPlayer().getTurnsInJail() != -1){
                try {
                    myBoard.movePlayer( getMyTurn().getMyCurrPlayer(), 0);
                } catch (MultiplePathException e) {
                    e.popUp();
                }
                handleTileLanding( myBoard.getPlayerTile(myTurn.getMyCurrPlayer()));
            }
            else { handleMove(); }
        }
        //TODO: make roll button grey
        //TODO: make button not grey
    }

    private void handleMove() {
        try {
            List<Tile> passedTiles = myBoard.movePlayer(getMyTurn().getMyCurrPlayer(), getMyTurn().getNumMoves());
            if (passedTiles.size() > 0) {
                handlePassedTiles(passedTiles);
            }
        } catch (MultiplePathException e) {
            e.popUp();
        }
        myTestScreen.updatePlayerPosition(myTurn.getNumMoves());
        //TODO: slow down options list popup
        handleTileLanding(myBoard.getPlayerTile(myTurn.getMyCurrPlayer()));
    }

    private void handlePassedTiles(List<Tile> passedTiles) {
        for (Tile t : passedTiles) {
            List<String> actions = t.applyPassedAction(getMyTurn().getMyCurrPlayer());
            for (String str : actions) {
                Method handle = null;
                try {
                    handle = this.getClass().getMethod("handle" + str);
                    handle.invoke(this);
                } catch (NoSuchMethodException e) {
                    myGameView.displayActionInfo("There is no such method");
                } catch (IllegalAccessException e) {
                    myGameView.displayActionInfo("Illegal access exception");
                } catch (IllegalArgumentException e) {
                    myGameView.displayActionInfo("Illegal argument");
                } catch (InvocationTargetException e) {
                    myGameView.displayActionInfo("Invocation target exception");
                }
            }
        }
    }

    public AbstractBoard getBoard() {
        return myBoard;
    }

    public AbstractDice getMyDice() {
        return myDice;
    }

    public Turn getMyTurn() {
        return myTurn;
    }

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
        handlerMap.put("roll",event->this.handleRollButton());
        handlerMap.put("SELL TO BANK",event->this.handleSellToBank());
        handlerMap.put("SELL TO PLAYER",event->this.handleSellToPlayer());
        handlerMap.put("UPGRADE", event->this.handleUpgradeProperty());
        handlerMap.put("TRADE",event->this.handleTrade());
        handlerMap.put("forfeit",event->this.handleForfeit());
        handlerMap.put( "unmortgage", event->this.handleUnmortgage() );
        handlerMap.put("end turn", event->this.handleEndTurnButton());
        myGameView.createOptions(handlerMap);
        myGameView.addPlayerOptionsView();
    }

    public void handleUpgradeProperty() {
        try {
            ObservableList<String> players = getAllOptionNames(myBoard.getPlayerNamesAsStrings());
            AbstractPlayer owner = getSelectedPlayer("Upgrade Property", "Choose who is upgrading their property ", players);

            ObservableList<String> tiles = getAllOptionNames(myBoard.getBuildingTileNamesAsStrings(owner));
            BuildingTile tile = (BuildingTile) getSelectedPropertyTile("Upgrade Property", "Choose which property to upgrade ", tiles);
            tile.upgrade(owner, myBoard.getSameSetProperties(tile));
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

    private void handleTrade() {
    }


    public void handleTileLanding(Tile tile) {
        try {
            List<String> actions = tile.applyLandedOnAction( getMyTurn().getMyCurrPlayer() );
            String desiredAction;
            if(actions.size() > 1){
                List<String> readableActions = new ArrayList<>();
                for(String each: actions){
                    readableActions.add( makeReadable( each ) );
                }
                String pickedOption = myTestScreen.displayOptionsPopup(readableActions, "Options", "Tile Actions", "Choose One");
                desiredAction = translateReadable( pickedOption );
            } else {
                desiredAction = actions.get(0);
            }
            TileActionController tileActionController = new TileActionController( getBoard(), getMyTurn(), myGameView, fundsView, propertiesView, myLogView, myTestScreen.getMyBoardView());
            Method handle = tileActionController.getClass().getMethod("handle" + desiredAction);
            handle.invoke(tileActionController);
        } catch (NoSuchMethodException e) {
            myGameView.displayActionInfo("There is no such method");
        } catch (SecurityException e) {
            myGameView.displayActionInfo("Security exception");
        } catch (IllegalAccessException e) {
            myGameView.displayActionInfo("Illegal access exception");
        } catch (IllegalArgumentException e) {
            myGameView.displayActionInfo("Illegal argument");
        } catch (InvocationTargetException e) {
            myGameView.displayActionInfo("Invocation target exception");
        }
    }

    public void handleSellToPlayer() {
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
                    tile.sellTo(buyer, amount, myBoard.getSameSetProperties(tile));
                    if (tile.isMortgaged()) {
                        result = myGameView.displayOptionsPopup(options, "Property is mortgaged", "Would you like to lift the mortgage? ", "Choose an option");
                        if (result.equals("Yes")) {
                            tile.unmortgageProperty();
                        } else {
                            tile.soldMortgagedPropertyLaterUnmortgages();
                        }
                    }
                }
            }
        myGameView.updateAssetDisplay(myBoard.getMyPlayerList());
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
        } catch (IllegalActionOnImprovedPropertyException e) {
            e.popUp();
        } catch (CancelledActionException e) {
            e.doNothing();
        } catch (PropertyNotFoundException e) {
            e.popUp();
        }
    }


    public void handleForfeit() {
        ObservableList<String> players = getAllPlayerNames();
        String player = myGameView.displayDropDownAndReturnResult("Forfeit", "Select the player who wants to forfeit: ", players);
        AbstractPlayer forfeiter = getBoard().getPlayerFromName(player);

        forfeiter.declareBankruptcy(getBoard().getBank());
        getBoard().getMyPlayerList().remove(forfeiter);
        getBoard().getPlayerTileMap().remove(forfeiter);
        myLogView.gameLog.setText(forfeiter.getMyPlayerName() + " has forfeited.");
        myGameView.updateAssetDisplay(getBoard().getMyPlayerList());
        for(Tab tab: propertiesView.getTabs()){
            if(tab.getText().equalsIgnoreCase( player )){
                propertiesView.getTabs().remove(tab);
            }
        }
        propertiesView.update(getBoard().getMyPlayerList());
    }

    public void handleUseHoldable(List<Object> parameters) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        HoldableCard card = null;
        ObservableList<String> players = getAllPlayerNames();
        String playerName = myGameView.displayDropDownAndReturnResult( "Use Card", "Select the player who wants to use a card: ", players );
        AbstractPlayer player = myBoard.getPlayerFromName( playerName );

        ObservableList<String> possibleCards = FXCollections.observableArrayList();
        for(HoldableCard c: player.getCards()){
            possibleCards.add( c.getName() );
        }
        if (possibleCards.size()==0){
            myGameView.displayActionInfo( "You have no cards to use at this time." );
        } else{
            String desiredCard = myGameView.displayDropDownAndReturnResult( "Use Card", "Select the card you want to use: ", possibleCards );
            for(HoldableCard c: player.getCards()){
                if(c.getName().equalsIgnoreCase( desiredCard )){
                    card = c;
                }
            }
        }
        ActionCardController actionCardController = new ActionCardController(myBoard, myTurn, fundsView, myTestScreen.getMyBoardView(), myGameView);
        Method handle = actionCardController.getClass().getMethod("handle" + card.getActionType(), List.class);
        handle.invoke(actionCardController, card.getParameters());
        myGameView.displayActionInfo( "You've successfully used " + card.getName());
        myLogView.gameLog.setText(myTurn.getMyCurrPlayer().getMyPlayerName() + " used " + card.getName() + ".");
        myGameView.updateAssetDisplay(myBoard.getMyPlayerList());
    }

    public void handleMortgage(){
        try{
            AbstractPropertyTile property = null;
            ObservableList<String> players = getAllPlayerNames();
            String mortgagerName = myGameView.displayDropDownAndReturnResult( "Mortgage", "Select the player who wants to mortgage: ", players );
            AbstractPlayer mortgager = myBoard.getPlayerFromName( mortgagerName );

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
            myGameView.displayActionInfo( "You've successfully mortgaged " + property.getName());
            myLogView.gameLog.setText(myTurn.getMyCurrPlayer().getMyPlayerName() + " has mortgaged " + property.getName() + ".");
            fundsView.update(myBoard.getMyPlayerList());
        } catch (MortgagePropertyException e) {
            e.popUp();
        }catch (IllegalActionOnImprovedPropertyException i) {
            i.popUp();
        }
    }

    public void handleUnmortgage() {
        try {
            AbstractPropertyTile property = (AbstractPropertyTile) getBoard().getAdjacentTiles(getBoard().getJailTile()).get(0);
            property.unmortgageProperty();
            fundsView.update(myBoard.getMyPlayerList());
            propertiesView.update( myBoard.getMyPlayerList() );
        } catch (MortgagePropertyException e) {
            e.popUp();
        } catch (TileNotFoundException e) {
            e.popUp();
        }
    }

    public Node getGameNode() {
        return myGameView.getGameViewNode();
    }

    private Map<AbstractPlayer, Double> convertEntrytoMap(Map.Entry<AbstractPlayer, Double> param) {
        Map<AbstractPlayer, Double> mapFromSet = new HashMap<>();
        mapFromSet.put(param.getKey(), param.getValue());
        return mapFromSet;
    }

    private ObservableList<String> getAllOptionNames(List<String> names) {
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

    private AbstractPlayer getSelectedPlayer(String title, String prompt, ObservableList<String> players) throws CancelledActionException, PropertyNotFoundException {
        String person = myTestScreen.displayDropDownAndReturnResult(title, prompt, players);
        AbstractPlayer player = null;
        player = getBoard().getPlayerFromName(person);
        return player;
    }

    private AbstractPropertyTile getSelectedPropertyTile(String title, String prompt, ObservableList<String> properties) throws CancelledActionException, PropertyNotFoundException {
        String tile = myTestScreen.displayDropDownAndReturnResult(title, prompt, properties);

        AbstractPropertyTile property = null;
        property = (AbstractPropertyTile) getBoard().getPropertyTileFromName(tile);
        return property;
    }

    private ObservableList<String> getAllPlayerNames() {
        ObservableList<String> players = FXCollections.observableArrayList();
        for (AbstractPlayer p : getBoard().getMyPlayerList()) {
            players.add(p.getMyPlayerName());
        }
        return players;
    }

    private String makeReadable(String s){
        String label = s.substring( 0,1 );
        for(int i = 1; i < s.length(); i++){
            //start at 1 so doesn't add a space before the first letter
            if(Character.isUpperCase( s. charAt( i ))){
                label += " " + s.charAt( i );
            } else{
                label += s.charAt( i );
            }
        }
        return label;
    }

    private String translateReadable(String s){
        return s.replaceAll("\\s+","");
    }
}
