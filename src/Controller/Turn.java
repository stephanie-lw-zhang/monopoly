package Controller;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;
import BackEnd.Dice.AbstractDice;
import BackEnd.Tile.AbstractDrawCardTile;
import BackEnd.Tile.AbstractPropertyTile;
import BackEnd.Tile.JailTile;
import BackEnd.Tile.Tile;

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

    public void onAction(Actions action) {
        AbstractPropertyTile property;
        switch (action) {
            case MOVE:
//                myBoard.movePlayer(myCurrPlayer, getNumMoves());
//                myBoard.getPlayerTile(myCurrPlayer).applyLandedOnAction(myCurrPlayer);
//                move();
                break;
            case TRADE:
//                myCurrPlayer.paysTo(myCurrPlayer, 1500.00);
                // TODO: handle Receiver input and debt as instances
                break;
//            case END_TURN:
//                isTurnOver = true;
//                myCurrPlayer = getNextPlayer();
//                break;
            case PAY_BAIL:
                myCurrPlayer.paysTo(myCurrPlayer.getBank(), 1500.00);
                // TODO: set debt as Turn or Player instance? replace 1500 w/ that instance
                //MUST BE FROM DATA FILE, CURRENTLY HARD CODED
                break;
            case BUY:
                //buy from bank
                property = (AbstractPropertyTile) currPlayerTile();
                List<AbstractPropertyTile> sameSetProperties = myBoard.getColorListMap().get( property.getCard().getCategory());
                Double currTilePrice = property.getCard().getTilePrice();
                property.sellTo( myCurrPlayer, currTilePrice, sameSetProperties );
                break;
            case AUCTION:

                break;
            case PAY_RENT:
                property = (AbstractPropertyTile) currPlayerTile();
                myCurrPlayer.paysTo( property.getOwner(), property.calculateRentPrice( getNumMoves() ) );
                break;
            case PAY_TAX_FIXED:
                myCurrPlayer.paysTo( myBoard.getBank(), 200.0 );
                //MUST BE FROM DATA FILE, CURRENTLY HARD CODED
                break;
            case PAY_TAX_PERCENTAGE:
                myCurrPlayer.paysTo( myBoard.getBank(),myCurrPlayer.getMoney() * 0.1 );
                //MUST BE FROM DATA FILE, CURRENTLY HARD CODED
                break;
            case DRAW_CARD:
                ((AbstractDrawCardTile) currPlayerTile()).drawCard();
                //assume draw card tile
                break;
            case SELL_TO_BANK:
                break;
            case SELL_TO_PLAYER:
                break;
            case COLLECT_MONEY:
                onAction(Actions.END_TURN);
                break;
            case GO_TO_JAIL:
                JailTile jail = (JailTile) myBoard.getJailTile();
                myBoard.getPlayerTileMap().put( myCurrPlayer, jail);
                jail.addCriminal( myCurrPlayer );
                myCurrPlayer.addTurnInJail();
                //error
                break;
            default:
                throw new IllegalArgumentException("Illegal Turn Action!");
        }
        isTurnOver = true;
        myCurrPlayer = getNextPlayer();
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

    public List<Actions> getMyActions() {
        return myActions;
    }
}