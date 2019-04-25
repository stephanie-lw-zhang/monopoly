package controller;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.dice.AbstractDice;
import exception.IllegalActionOnImprovedPropertyException;
import exception.TileNotFoundException;
import backend.tile.*;
import backend.tile.AbstractPropertyTile;
import backend.tile.JailTile;
import backend.tile.Tile;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.*;

/**
 * This class represents a single turn in the game of Monopoly
 * from dice roll to the end of a turn.
 *
 * sub-controller of controller.game
 *
 * @author Matt
 * @author Sam
 */
public class Turn {

    private AbstractPlayer myCurrPlayer;
    private AbstractBoard  myBoard;
    private AbstractDice   myDice;
    private List<String>  myActions;
    private TurnState      myTurnState;
    private boolean        isTurnOver;
    private boolean        canRollDie;
    private int[]          myRolls;
    private int            numDoubleRolls;

    public enum TurnState {
        PRE_ROLL,
        DOUBLE_ROLL,
        POST_ROLL;
    }

    public Turn (AbstractPlayer player, AbstractDice dice, AbstractBoard board) {
        myTurnState = TurnState.PRE_ROLL;
        myCurrPlayer = player;
        myBoard = board;
        myDice = dice;
        myActions = new ArrayList<>();
        canRollDie = true;
    }

    public void start() {
        myActions = new ArrayList<>();

        isTurnOver = false;

        myRolls = rollDice(myBoard.getNumDie());
        int numMoves = getNumMoves();

        checkDoubleRolls();
        canRollDie = false;
    }

    // TODO: Refactor such that Turn.start() can just
    // TODO: be called again on double roll instead
    private void checkDoubleRolls() {
        if (isDoubleRoll(myRolls)) {
            numDoubleRolls++;

            if (numDoubleRolls == 1) {
                myRolls = rollDice(myBoard.getNumDie());
                // TODO: HANDLE ACTIONS
                checkDoubleRolls();
            }
            if (numDoubleRolls == 2) {
                // TODO: GO TO JAIL
            }
        }
        // if not double, do nothing
    }

    public void skipTurn() {
        myCurrPlayer = getNextPlayer();
    }

    public Tile currPlayerTile(){
        return myBoard.getPlayerTile( myCurrPlayer );
    }

    private AbstractPlayer getNextPlayer() {
        Iterator<AbstractPlayer> iterator = myBoard.getMyPlayerList().iterator();

        while (iterator.hasNext()) {
            AbstractPlayer current = iterator.next();
            if (current.equals(myCurrPlayer) && iterator.hasNext())  // employs custom AbstractPlayer.equals()
                return iterator.next(); // get next player if myCurrPlayer not last element
        }
        return myBoard.getMyPlayerList().get(0); // reached end of list thus modulo to beginning
    }

    private void promptEndTurn() {

    }

    private void handleEndTurn() {

    }

    // TODO: MAKE PRIVATE ONCE GAME.ROLLVALUE() IS DELETED
    public int[] rollDice(int numDie) {
        int[] rolls = new int[numDie];
        for (int i = 0; i < rolls.length; i++) rolls[i] = myDice.roll();
        myRolls = rolls;
        return rolls;
    }


    public int getNumMoves() {
        int sum = 0;
        for (int roll : myRolls) sum += roll;
        return sum;
    }

    public Object onAction(String action, Map<AbstractPlayer,Double> paramMap) {
        Method method = null;
        try {
            method = this.getClass().getMethod(action, Map.class);
            Class<?>[] types = method.getParameterTypes();
            if (types.length == 0) {
                return method.invoke(this);
            }
            else if (types.length == 1) {
                return method.invoke(this,paramMap);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.getCause();
        }
        return null;
    }

    public void endTurn(){
        isTurnOver = true;
        myCurrPlayer = getNextPlayer();
    }

    public void move() {
//        if (myRolls == null){
//            //throw exception that dice must be rolled first
//        }
//        if(myCurrPlayer.isBankrupt()){
//            return;
//        }
        if (myCurrPlayer.getTurnsInJail() == 1 || myCurrPlayer.getTurnsInJail() == 1){
            //
        }
        else if (myCurrPlayer.getTurnsInJail() == 3) {
            //player must either pay 50 and move or skip one turn
            //series of states OR dialogue boxes
            //try to get out of jail() {
            //prompt user
            //get boolean
            //}
            //button.setOnAction -> eventHandler
            //states: waiting for user to answer question
            //controller calls a method in the UI to present a choice
            //model initiates question of roll or pay
            //model tells controller
            //controller tells view to show the choice
            //show popup or modal dialogue box
            //button prompts event handler
        }
        //assuming player chose to either pay $50 or skip one turn
        //else if(player.getTurnsInJail()!=-1 && ){}

        //assuming player chose to roll in jail
        else if(myCurrPlayer.getTurnsInJail()!=-1 && myRolls[0] != myRolls[1]){
            return;
        }
        //player is not in jail and moves normally
        //
        else{
            myBoard.movePlayer(myCurrPlayer, getNumMoves());
            myActions = myBoard.getPlayerTile(myCurrPlayer).applyLandedOnAction(myCurrPlayer);
        }
    }

    /**
     * FOR TESTING
     * @param n number of moves
     */
    public void moveCheat(int n) {
        myBoard.movePlayer(myCurrPlayer, n);
    }

    public Map.Entry<AbstractPlayer, Double> goToJail() {
        try {
        JailTile jail = (JailTile) myBoard.getJailTile();
        myBoard.getPlayerTileMap().put( myCurrPlayer, jail);
        jail.addCriminal( myCurrPlayer );
        myCurrPlayer.addTurnInJail();

        } catch (TileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> payRent() {
        AbstractPropertyTile property;
        property = (AbstractPropertyTile) currPlayerTile();
        myCurrPlayer.payFullAmountTo( property.getOwner(), property.calculateRentPrice( getNumMoves() ) );
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> buy(Map<AbstractPlayer,Double> paramMap) throws IllegalActionOnImprovedPropertyException {
        AbstractPlayer player = null;
        double value = 0;
        if (paramMap != null) {
            if (paramMap.keySet().size()==1) {
                for (AbstractPlayer p : paramMap.keySet()) {
                    player = p;
                }
            }
            value = paramMap.get(player);
        }
        else {
            player = myCurrPlayer;
            value = ((AbstractPropertyTile)currPlayerTile()).getTilePrice();
        }
        buyProperty(player, value);
        Map.Entry<AbstractPlayer,Double> ret = new AbstractMap.SimpleEntry<>(player, value);
        //endTurn();
        return ret;
    }

    public void buyProperty(AbstractPlayer player, Double value) throws IllegalActionOnImprovedPropertyException {
//        System.out.println(player.getMyPlayerName() + ": " + player.getMoney());
        AbstractPropertyTile property;
        property = (AbstractPropertyTile) currPlayerTile();
        List<AbstractPropertyTile> sameSetProperties = myBoard.getColorListMap().get( property.getCard().getCategory());
        property.sellTo( player, value, sameSetProperties );
//        System.out.println(player.getMyPlayerName() + ": " + player.getMoney());
    }

    public Map.Entry<AbstractPlayer, Double> payBail() {

        myCurrPlayer.payFullAmountTo(myBoard.getBank(), 1500.00);

        // TODO: set debt as Turn or Player instance? replace 1500 w/ that instance
        // MUST BE FROM DATA FILE, CURRENTLY HARD CODED
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> trade() {

//      TODO: handle Receiver input and debt as instances
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> auction(Map<AbstractPlayer,Double> auctionAmount) {
        AbstractPropertyTile property = (AbstractPropertyTile) currPlayerTile();
        return property.determineAuctionResults(auctionAmount);
    }

//    public Map.Entry<AbstractPlayer, Double> collectMoney() {
//
//        Boolean passed = true; //temp variable
//        if(passed){
//            myBoard.getBank().payFullAmountTo( myCurrPlayer, myBoard.getGoTile().getPassedMoney() );
//            myGameView.displayActionInfo( "You collected " + myBoard.getGoTile().getPassedMoney() + " for passing go." );
//        } else {
//            //means you landed directly on it
//            myBoard.getBank().payFullAmountTo( myCurrPlayer, myBoard.getGoTile().getLandedOnMoney() );
//            myGameView.displayActionInfo( "You collected " + myBoard.getGoTile().getLandedOnMoney() +" for landing on go." );
//        }
//    }

    public Map.Entry<AbstractPlayer, Double> sellToPlayer() {
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> sellToBank(){
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> drawCard(){
        ((AbstractDrawCardTile) currPlayerTile()).drawCard();
//       assume draw card tile
        return null;
    }

//    public Map.Entry<AbstractPlayer, Double> payTaxFixed() {
//
//        myCurrPlayer.payFullAmountTo( myBoard.getBank(), 200.0 );
//
////      MUST BE FROM DATA FILE, CURRENTLY HARD CODED
//        return null;
//    }
//
//    public Map.Entry<AbstractPlayer, Double> payTaxPercentage() {
//
//        myCurrPlayer.payFullAmountTo( myBoard.getBank(),myCurrPlayer.getMoney() * 0.1 );
//
////      MUST BE FROM DATA FILE, CURRENTLY HARD CODED
//        return null;
//    }


    //in a turn a player can roll/move, trade, mortgage
    public void setNextPlayer(AbstractPlayer nextPlayer) {
        myCurrPlayer = nextPlayer;
    }

    public boolean isDoubleRoll(int[] rolls) {
        return new HashSet<Integer>((Collection) Arrays.asList(rolls)).size() == 1;
    }

    public boolean isTurnOver(){
        return isTurnOver;
    }

    public AbstractPlayer getMyCurrPlayer() { return myCurrPlayer; }
    public int[] getRolls() { return myRolls; }

    public List<String> getMyActions() {
        return myActions;
    }

    public String getTileNameforPlayer(AbstractPlayer p) {
        //TODO: exception if current tile is not abstract property tile
        return ((AbstractPropertyTile)myBoard.getPlayerTile(p)).getTitleDeed();
    }
}