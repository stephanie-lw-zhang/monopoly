package Controller;

import BackEnd.Deck.DeckInterface;
import BackEnd.Dice.AbstractDice;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;

import java.util.List;
//import java.util.logging.Logger;

public class Game {

//    private System.Logger LOGGER = new Logger.getLogger(Game.class.getName());
    private AbstractDice dice;
    private DeckInterface chanceDeck;
    private DeckInterface chestDeck;
    private AbstractBoard board;
    private List<AbstractPlayer> players;
    //private Bank bank;

    public Game(AbstractDice dice, DeckInterface chanceDeck, DeckInterface chestDeck, AbstractBoard board, List<AbstractPlayer> players){
        this.dice = dice;
        this.chanceDeck = chanceDeck;
        this.chestDeck = chestDeck;
        this.board = board;
        this.players = players;
    }

    public int[] rollValue(){
        int[] rolls = new int[2];
        rolls[0] = dice.roll();
        rolls[1] = dice.roll();
        return rolls;
    }

    public void play(){

    }

    public static void handleRollButton() {

    }

    public AbstractBoard getBoard() {
        return board;
    }
}
