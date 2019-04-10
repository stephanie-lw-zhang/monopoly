package BackEnd.Game;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;
import BackEnd.Dice.AbstractDice;

/**
 * This class represents a single turn in the game of Monopoly
 * from dice roll to the end of a turn.
 *
 * @author Matt
 * @author Sam
 */
public class Turn {

    private AbstractPlayer myCurrPlayer;
    private AbstractBoard  myBoard;
    private AbstractDice   myDice;
    private boolean        isTurnOver;
    private int[]          myRolls;

    public Turn (AbstractPlayer player, AbstractDice dice, AbstractBoard board) {
        myCurrPlayer = player;
        myBoard = board;
        myDice = dice;
        isTurnOver = false;
    }

    public void start() {

    }

    public int[] rollDice(){
        myRolls = new int[2];
        myRolls[0] = myDice.roll();
        myRolls[1] = myDice.roll();
        myRolls = rollDice();
        return myRolls;
    }

    public void move(){
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
        else{
            myBoard.movePlayer(myCurrPlayer, myRolls);
            myBoard.getPlayerTile(myCurrPlayer).applyLandedOnAction(myCurrPlayer);
        }
    }

    //in a turn a player can roll/move, trade, mortgage

    public boolean rolledDoubles(){
        return myRolls[0] == myRolls[1];
    }

    public boolean isTurnOver(){
        return isTurnOver;
    }

}
