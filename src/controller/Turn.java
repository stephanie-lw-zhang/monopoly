package controller;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.dice.AbstractDice;
import exceptions.*;
import backend.tile.AbstractPropertyTile;
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
    private List<String>   myCurrentTileActions;
    private Map<Tile,String> myPassedTileActions;
    private TurnState      myTurnState;
    private boolean        isTurnOver;
    private boolean        canRollDie;
    private Integer[]          myRolls;
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
        myCurrentTileActions = new ArrayList<>();
        canRollDie = true;
    }

    public void start() {
        myRolls = rollDice(myBoard.getNumDie());
        int numMoves = getNumMoves();
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
    public Integer[] rollDice(int numDie) {
        Integer[] rolls = new Integer[numDie];
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

    public void move() throws MultiplePathException {
        if(myCurrPlayer.getTurnsInJail() == 1 || myCurrPlayer.getTurnsInJail() == 2){
            new IllegalMoveException( "Cannot move because you are in jail." );
            myBoard.movePlayer(myCurrPlayer,0);
        }
        else{
            myBoard.movePlayer( myCurrPlayer, getNumMoves() );
        }
    }

    /**
     * FOR TESTING
     * @param n number of moves
     */
    public void moveCheat(int n) throws MultiplePathException {
        myBoard.movePlayer(myCurrPlayer, n);
    }

    public Map.Entry<AbstractPlayer, Double> buy(Map<AbstractPlayer,Double> paramMap) throws IllegalActionOnImprovedPropertyException, IllegalInputTypeException, OutOfBuildingStructureException {
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
        return ret;
    }

    public void buyProperty(AbstractPlayer player, Double value) throws IllegalActionOnImprovedPropertyException, IllegalInputTypeException, OutOfBuildingStructureException {
        AbstractPropertyTile property;
        property = (AbstractPropertyTile) currPlayerTile();
        List<AbstractPropertyTile> sameSetProperties = myBoard.getColorListMap().get( property.getCard().getCategory());
        property.sellTo( player, value, sameSetProperties );
//        System.out.println(player.getMyPlayerName() + ": " + player.getMoney());
    }

    //in a turn a player can roll/move, trade, mortgage
    public void setNextPlayer(AbstractPlayer nextPlayer) {
        myCurrPlayer = nextPlayer;
    }

    public boolean isDoubleRoll(Integer[] rolls) {
        HashSet<Integer> doubles = new HashSet(Arrays.asList(rolls));
        return doubles.size() == 1;
    }

    public boolean isTurnOver(){
        return isTurnOver;
    }

    public AbstractPlayer getMyCurrPlayer() { return myCurrPlayer; }
    public Integer[] getRolls() { return myRolls; }

    public List<String> getMyCurrentTileActions() {
        return myCurrentTileActions;
    }

    public String getTileNameforPlayer(AbstractPlayer p) {
        //TODO: exceptions if current tile is not abstract property tile
        return ((AbstractPropertyTile)myBoard.getPlayerTile(p)).getTitleDeed();
    }
}