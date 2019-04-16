package Controller;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;
import BackEnd.Dice.AbstractDice;
import java.util.*;

/**
 * This class represents a single turn in the game of Monopoly
 * from dice roll to the end of a turn.
 *
 * sub-controller of Controller.Game
 *
 * @author Matt
 * @author Sam
 */
public class Turn {

    private AbstractPlayer myCurrPlayer;
    private AbstractBoard  myBoard;
    private AbstractDice   myDice;
    private List<Actions>  myActions;
    private TurnState      myTurnState;
    private boolean        isTurnOver;
    private boolean        canRollDie;
    private int[]          myRolls;
    private int            numDoubleRolls;

    public enum Actions {
        MOVE,
        PROPERTY_EVENTS,
        TRADE,
        GET_OUT_OF_JAIL,
        PAY_BAIL,
        END_TURN;
    }

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
        myRolls = rollDice(myBoard.getNumDie());
        // TODO: send myRolls to FE to be displayed
        int numMoves = getNumMoves();

        if (isDoubleRoll(myRolls))
        canRollDie = false;

    }

    public void onAction(Actions action) {
        switch (action) {
            case MOVE:
                myBoard.movePlayer(myCurrPlayer, getNumMoves());
                myBoard.getPlayerTile(myCurrPlayer).applyLandedOnAction(myCurrPlayer);
                break;
            case TRADE:
                myCurrPlayer.paysTo(myCurrPlayer, 1500.00);
                // TODO: handle Receiver input and debt as instances
                break;
            case END_TURN:
                isTurnOver = true;
                // TODO: HANDLE END TURN
                break;
            case PAY_BAIL:
                myCurrPlayer.paysTo(myCurrPlayer.getBank(), 1500.00);
                // TODO: set debt as Turn or Player instance? replace 1500 w/ that instance
                break;
            case GET_OUT_OF_JAIL:
                myCurrPlayer.getOutOfJail();
                break;
            case PROPERTY_EVENTS:
//                myActions.add
                break;
            default:
                throw new IllegalArgumentException("Illegal Turn Action!");
        }
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

    public void move() {
        if (myRolls == null){
            //throw exception that dice must be rolled first
        }
        if(myCurrPlayer.isBankrupt()){
            return;
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
//            myBoard.movePlayer(myCurrPlayer, getNumMoves());
//            myBoard.getPlayerTile(myCurrPlayer).applyLandedOnAction(myCurrPlayer);
        }
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

    public int[] getRolls() { return myRolls; }
}
