package controller;

import backend.card.action_cards.HoldableCard;
import backend.dice.AbstractDice;
import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.dice.SixDice;
import backend.tile.AbstractPropertyTile;
import backend.tile.*;

import configuration.XMLData;
import exceptions.*;

import frontend.views.game.AbstractGameView;
import frontend.views.game.SplitScreenGameView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 */
public class GameController {

    private GameSetUpController mySetUpController;
    //TODO: make all the back-end stuff be managed by a MonopolyModel/myBoard class
//    private XMLData myData;
    private AbstractBoard myBoard;
    private AbstractDice myDice;
    private Turn myTurn;
    private Map<String, EventHandler<ActionEvent>> handlerMap = new LinkedHashMap<>();
    private int numDoubleRolls;
    private AbstractGameView myGameView;
    private TileActionController tileActionController;

    public GameController(double width, double height, GameSetUpController controller, AbstractBoard board, XMLData data){
        myBoard = board;
//        myData = data;
        myGameView = new SplitScreenGameView(width, height, data, myBoard);
        mySetUpController = controller;
        addHandlers();
        myDice = new SixDice();
        myTurn = new Turn(myBoard.getMyPlayerList().get(0), myDice, myBoard);
        this.numDoubleRolls = 0;
        tileActionController = new TileActionController( myBoard, myTurn, myGameView);
    }

    private void addHandlers(){
        handlerMap.put("Roll",event->this.handleRollButton());
        handlerMap.put("End Turn", event->this.handleEndTurnButton());
        handlerMap.put("Upgrade", event->this.handleUpgradeProperty());
        handlerMap.put("Sell to Bank",event->this.handleSellToBank());
        handlerMap.put("Sell to Player",event->this.handleSellToPlayer());
        handlerMap.put("Mortgage", event->this.handleMortgage());
        handlerMap.put("Unmortgage", event->this.handleUnmortgage() );
        handlerMap.put("Trade",event->this.handleTrade());
        handlerMap.put("Forfeit",event->this.handleForfeit());
//        handlerMap.put("Move Cheat", event->this.handleMoveCheat);
        myGameView.createOptions(handlerMap);
        myGameView.addPlayerOptionsView();
    }

    private void handleEndTurnButton() {
        myTurn.skipTurn();
        myGameView.updateCurrPlayerDisplay(myTurn.getMyCurrPlayer());
        numDoubleRolls = 0;
        myGameView.disableButton("End Turn");
        myGameView.enableButton("Roll");
    }

    private void handleRollButton() {
        myGameView.disableButton("Roll");
        if(myTurn.getMyCurrPlayer().getTurnsInJail() == 0 || myTurn.getMyCurrPlayer().getTurnsInJail() == 1) {
            new IllegalMoveException("You cannot move because you are in jail. Roll a doubles to get out of jail for free!").popUp();
        }
        myTurn.start();
        myGameView.updateDice(myTurn);

        if (myTurn.isDoubleRoll(myTurn.getRolls())) {
            if(myTurn.getMyCurrPlayer().isInJail()) {
                myTurn.getMyCurrPlayer().getOutOfJail();
                myGameView.displayActionInfo("You are released from jail because you rolled doubles. You're free now!");
                myGameView.updateAssetDisplay(myBoard.getMyPlayerList());
                handleMove(myTurn.getNumMoves());
                myGameView.enableButton("End Turn");
            }
            else {
                //TODO: get rid of magic value
                 if (numDoubleRolls < 2) {
                     numDoubleRolls++;
                     handleMove(myTurn.getNumMoves());
                     myGameView.displayActionInfo("You rolled doubles. Roll again!");
                     myGameView.enableButton("Roll");
                     //TODO: add log?
                }
                else {
                    tileActionController.handleGoToJail();
                    myGameView.enableButton("End Turn");
                 }
            }
        }
        else {
            if (myTurn.getMyCurrPlayer().getTurnsInJail() == 2) {
                myTurn.getMyCurrPlayer().getOutOfJail();
                myGameView.displayActionInfo("You have had three turns in jail! You are free after you pay the fine.");
                tileActionController.handlePayBail();
                handleMove(myTurn.getNumMoves());
            }
            else if(myTurn.getMyCurrPlayer().getTurnsInJail() != -1){
                handleMove(0);
            }
            else {
                handleMove(myTurn.getNumMoves());
            }
            myGameView.enableButton("End Turn");
        }
    }

    private void handleMove(int numMoves) {
        try {
            for (int i = 0; i < 5; i++) {
                Tile passedTile = myBoard.movePlayerByOne( myTurn.getMyCurrPlayer());
                if (passedTile != null && i != myTurn.getNumMoves()-1) {
                   handlePassedTiles(passedTile);
                }
            }
        } catch (MultiplePathException e) {
            e.popUp();
        }
        myGameView.updateIconDisplay(myTurn.getMyCurrPlayer(), myTurn.getNumMoves());
        //TODO: slow down options list popup
        handleTileLanding(myBoard.getPlayerTile(myTurn.getMyCurrPlayer()));
        myGameView.updateAssetDisplay(myBoard.getMyPlayerList());
    }

    public void handleTileLanding(Tile tile) {
        try {
            List<String> actions = tile.applyLandedOnAction( myTurn.getMyCurrPlayer() );
            if (!(actions.size() == 0)) {
                String desiredAction = determineDesiredActionForReflection(actions);
                TileActionController tileActionController = new TileActionController(myBoard, myTurn, myGameView);
                Method handle = tileActionController.getClass().getMethod("handle" + desiredAction);
                handle.invoke(tileActionController);
            }
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

    private void handlePassedTiles(Tile passedTile) {
        try {
            List<String> actions = passedTile.applyPassedAction(myTurn.getMyCurrPlayer());
            if (!(actions.size() == 0)) {
                String desiredAction = determineDesiredActionForReflection(actions);
                PassedTileActionController passedTileActionController = new PassedTileActionController( myBoard, myTurn, myGameView);
                Method handle = passedTileActionController.getClass().getMethod("handle" + desiredAction);
                handle.invoke(passedTileActionController);
            }
        } catch (IllegalAccessException e) {
            myGameView.displayActionInfo("Illegal access exception");
        } catch (InvocationTargetException e) {
            myGameView.displayActionInfo("Invocation target exception");
        }  catch (NoSuchMethodException e) {
            myGameView.displayActionInfo("No such method exception");
        }
    }

    private String determineDesiredActionForReflection(List<String> actions) {
        String desiredAction;
        if (actions.size() > 1) {
            List<String> readableActions = new ArrayList<>();
            for (String each : actions) {
                readableActions.add(makeReadable(each));
            }
            String pickedOption = myGameView.displayOptionsPopup(readableActions, "Options", "Tile Actions", "Choose One");
            desiredAction = translateReadable(pickedOption);
        } else {
            desiredAction = actions.get(0);
        }
        return desiredAction;
    }

//    public void handleMoveCheat() {
//        int numMoves = Integer.parseInt(movesField.getText());
////        myGameView.move(myTurn.getMyCurrPlayer().getMyIcon(), numMoves);
//        try {
//            myBoard.movePlayer(myTurn.getMyCurrPlayer(), numMoves);
//        } catch (MultiplePathException e) {
//            e.popUp();
//        }
//    }

    public void handleUpgradeProperty() {
        try {
            ObservableList<String> players = getAllOptionNames(myBoard.getPlayerNamesAsStrings());
            AbstractPlayer owner = getSelectedPlayer("Upgrade Property", "Choose who is upgrading their property ", players);

            ObservableList<String> tiles = getAllOptionNames(myBoard.getBuildingTileNamesAsStrings(owner));
            BuildingTile tile = (BuildingTile) getSelectedPropertyTile("Upgrade Property", "Choose which property to upgrade ", tiles);
            tile.upgrade(owner, myBoard.getSameSetProperties(tile));
            myGameView.updateAssetDisplay(myBoard.getMyPlayerList());
        } catch (IllegalInputTypeException e) {
            e.popUp();
        } catch (OutOfBuildingStructureException e) {
            e.popUp();
        } catch (CancelledActionException e) {
            e.doNothing();
        } catch (PropertyNotFoundException e) {
            e.popUp();
        } catch (NotEnoughMoneyException e) {
            e.popUp();
        }
    }

    private void handleTrade() {
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
        } catch (NotEnoughMoneyException e) {
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
            String str = myGameView.displayOptionsPopup(options, "Sell Buildings", "Sell buildings options ", "Choose one ");
            if (str.equalsIgnoreCase(options.get(0))) {
                tile.sellAllBuildingsOnTile(myBoard.getSameSetProperties(tile));
                //TODO: add front-end implementation
            } else {
                tile.sellOneBuilding(myBoard.getSameSetProperties(tile));
                //TODO: add front-end implementation
            }
            myGameView.updateAssetDisplay(myBoard.getMyPlayerList());
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
        String player = null;
        try {
            player = myGameView.displayDropDownAndReturnResult("Forfeit", "Select the player who wants to forfeit: ", players);
        } catch (CancelledActionException e) {
            e.doNothing();
        } catch (PropertyNotFoundException e) {
            e.popUp();
        }
        AbstractPlayer forfeiter = myBoard.getPlayerFromName(player);

        forfeiter.declareBankruptcy(myBoard.getBank());
        myBoard.getMyPlayerList().remove(forfeiter);
        myBoard.getPlayerTileMap().remove(forfeiter);
        myGameView.updateAssetDisplay(myBoard.getMyPlayerList());
    }

    public void handleUseHoldable(List<Object> parameters) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        HoldableCard card = null;
        ObservableList<String> players = getAllPlayerNames();
        String playerName = null;
        try {
            playerName = myGameView.displayDropDownAndReturnResult( "Use Card", "Select the player who wants to use a card: ", players );
        } catch (CancelledActionException e) {
            e.doNothing();
        } catch (PropertyNotFoundException e) {
            e.popUp();
        }
        AbstractPlayer player = myBoard.getPlayerFromName( playerName );

        ObservableList<String> possibleCards = FXCollections.observableArrayList();
        for(HoldableCard c: player.getCards()){
            possibleCards.add( c.getName() );
        }
        if (possibleCards.size()==0){
            myGameView.displayActionInfo( "You have no cards to use at this time." );
        } else{
            String desiredCard = null;
            try {
                desiredCard = myGameView.displayDropDownAndReturnResult( "Use Card", "Select the card you want to use: ", possibleCards );
            } catch (CancelledActionException e) {
                e.doNothing();
            } catch (PropertyNotFoundException e) {
                e.popUp();
            }
            for(HoldableCard c: player.getCards()){
                if(c.getName().equalsIgnoreCase( desiredCard )){
                    card = c;
                }
            }
        }

        ActionCardController actionCardController = new ActionCardController(myBoard, myTurn, myGameView);
        Method handle = actionCardController.getClass().getMethod("handle" + card.getActionType(), List.class);
        handle.invoke(actionCardController, card.getParameters());
        myGameView.displayActionInfo( "You've successfully used " + card.getName());
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
                possibleProperties.add( p.getTitleDeed() );
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
            myGameView.updateAssetDisplay(myBoard.getMyPlayerList());
        } catch (MortgagePropertyException e) {
            e.popUp();
        }catch (IllegalActionOnImprovedPropertyException i) {
            i.popUp();
        } catch (PropertyNotFoundException e) {
            e.popUp();
        } catch (CancelledActionException e) {
            e.doNothing();
        }
    }

    public void handleUnmortgage() {
        try {
            AbstractPropertyTile property = (AbstractPropertyTile) myBoard.getAdjacentTiles(myBoard.getJailTile()).get(0);
            property.unmortgageProperty();
            myGameView.updateAssetDisplay(myBoard.getMyPlayerList());
        } catch (MortgagePropertyException e) {
            e.popUp();
        } catch (TileNotFoundException e) {
            e.popUp();
        }
    }

    public Node getGameNode() {
        return myGameView.getGameViewNode();
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
        String person = myGameView.displayDropDownAndReturnResult(title, prompt, players);
        AbstractPlayer player = null;
        player = myBoard.getPlayerFromName(person);
        return player;
    }

    private AbstractPropertyTile getSelectedPropertyTile(String title, String prompt, ObservableList<String> properties) throws CancelledActionException, PropertyNotFoundException {
        String tile = myGameView.displayDropDownAndReturnResult(title, prompt, properties);
        AbstractPropertyTile property = null;
        property = (AbstractPropertyTile) myBoard.getPropertyTileFromName(tile);
        return property;
    }

    private ObservableList<String> getAllPlayerNames() {
        ObservableList<String> players = FXCollections.observableArrayList();
        for (AbstractPlayer p : myBoard.getMyPlayerList()) {
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

//    private AbstractBoard makeBoard(Map<TextField, ComboBox> playerToIcon) {
//        return new StandardBoard(
//                makePlayerList(playerToIcon), myData.getAdjacencyList(),
//                myData.getPropertyCategoryMap(), myData.getFirstTile(),
//                myData.getBank()
//        );
//    }

//    private List<AbstractPlayer> makePlayerList(Map<TextField, ComboBox> playerToIcon) {
//        List<AbstractPlayer> playerList = new ArrayList<>();
//
//        for (TextField pName : playerToIcon.keySet()) {
//            String name = pName.getText();
//            if (!name.equals(""))
//                playerList.add(new HumanPlayer(
//                        name,
//                        makeIcon((String) playerToIcon.get(pName).getValue()),
//                        1500.00));
//        }
//        return playerList;
//    }
