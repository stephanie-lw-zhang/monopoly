package Controller;

import BackEnd.Deck.DeckInterface;
import BackEnd.Dice.AbstractDice;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;

import java.util.List;

/**
 * Game Controller
 *
 * @author Matt
 * @author Sam
 */
public class Game {

    public enum GameState {
        PRE_GAME,
        INTRA_GAME,
        POST_GAME;
    }

    private List<AbstractPlayer> myPlayers;
    private DeckInterface        myChanceDeck;
    private DeckInterface        myChestDeck;
    private AbstractBoard        myBoard;
    private AbstractDice         myDice;
    private GameState            gameState;
    private Turn                 myCurrentTurn;
    //private Bank bank;

    public Game(AbstractDice dice, DeckInterface chanceDeck, DeckInterface chestDeck, AbstractBoard board, List<AbstractPlayer> players){
        gameState = GameState.PRE_GAME;
        myDice = dice;
        myChanceDeck = chanceDeck;
        myChestDeck = chestDeck;
        myBoard = board;
        myPlayers = players;

        myCurrentTurn = new Turn(myPlayers.get(0), myDice, myBoard);
    }

    public void play() {
        while (! gameIsOver()) {

        }
    }

    public boolean gameIsOver() {
        int sum = 0;
        for(AbstractPlayer p: myPlayers)
            if (p.isBankrupt()) sum++;

        return sum == myPlayers.size() - 1;
    }

    public AbstractBoard getBoard() {
        return myBoard;
    }

    public static void handleRollButton() {

    }
}
