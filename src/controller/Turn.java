package controller;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.dice.AbstractDice;
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
        myActions.clear();
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

    private Tile currPlayerTile(){
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
        System.out.println(sum);
        return sum;
    }

    public Object onAction(String action, Map<AbstractPlayer,Double> amountMap) {
        Method method = null;
        Object ret = null;
        try {
            method = this.getClass().getMethod(action, Map.class);
            Class<?>[] types = method.getParameterTypes();
            if (types.length == 0) {
                ret = method.invoke(this);
            }
            else if (types.length == 1) {
                ret = method.invoke(this,amountMap);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.getCause();
        }
        isTurnOver = true;
        myCurrPlayer = getNextPlayer();
        return ret;
    }

    public void move() {
        System.out.println("MOVING");
        if (myRolls == null){
            //throw exception that dice must be rolled first
        }
//        if(myCurrPlayer.isBankrupt()){
//            return;
//        }
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

    public Map.Entry<AbstractPlayer, Double> goToJail() {
        JailTile jail = (JailTile) myBoard.getJailTile();
        myBoard.getPlayerTileMap().put( myCurrPlayer, jail);
        jail.addCriminal( myCurrPlayer );
        myCurrPlayer.addTurnInJail();
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> payRent() {
        AbstractPropertyTile property;
        property = (AbstractPropertyTile) currPlayerTile();
        myCurrPlayer.paysTo( property.getOwner(), property.calculateRentPrice( getNumMoves() ) );
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> buy() {
        System.out.println(myCurrPlayer.getMyPlayerName() + ": " + myCurrPlayer.getMoney());
        AbstractPropertyTile property;
        property = (AbstractPropertyTile) currPlayerTile();
        List<AbstractPropertyTile> sameSetProperties = myBoard.getColorListMap().get( property.getCard().getCategory());
        Double currTilePrice = property.getCard().getTilePrice();
        property.sellTo( myCurrPlayer, currTilePrice, sameSetProperties );
        System.out.println(myCurrPlayer.getMyPlayerName() + ": " + myCurrPlayer.getMoney());
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> payBail() {
        myCurrPlayer.paysTo(myCurrPlayer.getBank(), 1500.00);
        // TODO: set debt as Turn or Player instance? replace 1500 w/ that instance
        // MUST BE FROM DATA FILE, CURRENTLY HARD CODED
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> trade() {
//      myCurrPlayer.paysTo(myCurrPlayer, 1500.00);
//      TODO: handle Receiver input and debt as instances
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> auction(Map<AbstractPlayer,Double> auctionAmount) {
        System.out.println("HI!!!!!!");
        AbstractPropertyTile property = (AbstractPropertyTile) currPlayerTile();
        return property.determineAuctionResults(auctionAmount);
    }

    public Map.Entry<AbstractPlayer, Double> collectMoney() {
        return null;
    }

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

    public Map.Entry<AbstractPlayer, Double> payTaxFixed() {
        myCurrPlayer.paysTo( myBoard.getBank(), 200.0 );
//      MUST BE FROM DATA FILE, CURRENTLY HARD CODED
        return null;
    }

    public Map.Entry<AbstractPlayer, Double> payTaxPercentage() {
        myCurrPlayer.paysTo( myBoard.getBank(),myCurrPlayer.getMoney() * 0.1 );
//      MUST BE FROM DATA FILE, CURRENTLY HARD CODED
        return null;
    }


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
}
