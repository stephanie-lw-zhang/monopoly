package controller;

import backend.assetholder.AbstractPlayer;
import backend.board.AbstractBoard;
import backend.dice.AbstractDice;
import backend.tile.AbstractPropertyTile;
import backend.tile.Tile;


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
    private Integer[]      myRolls;

    public Turn (AbstractPlayer player, AbstractDice dice, AbstractBoard board) {
        myCurrPlayer = player;
        myBoard = board;
        myDice = dice;
        myCurrentTileActions = new ArrayList<>();
    }

    public void start() {
        myRolls = rollDice(myBoard.getNumDie());
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

    //in a turn a player can roll/move, trade, mortgage
    public void setNextPlayer(AbstractPlayer nextPlayer) {
        myCurrPlayer = nextPlayer;
    }

    public boolean isDoubleRoll(Integer[] rolls) {
        HashSet<Integer> doubles = new HashSet(Arrays.asList(rolls));
        return doubles.size() == 1;
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