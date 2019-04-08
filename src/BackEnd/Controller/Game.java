package BackEnd.Controller;

import BackEnd.Deck.DeckInterface;
import BackEnd.Dice.AbstractDice;
import BackEnd.Exceptions.JailException;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;

public class Game {

    private AbstractDice dice;
    private DeckInterface chanceDeck;
    private DeckInterface chestDeck;
    private AbstractBoard board;
    //private Bank bank;

    public Game(AbstractDice dice, DeckInterface chanceDeck, DeckInterface chestDeck, AbstractBoard board){
        this.dice = dice;
        this.chanceDeck = chanceDeck;
        this.chestDeck = chestDeck;
        this.board = board;
        //this.bank = bank;
    }

    public int[] rollValue(){
        int[] rolls = new int[2];
        rolls[0] = dice.roll();
        rolls[1] = dice.roll();
        return rolls;
    }

    public void movePlayer(AbstractPlayer p, int[] rolls){
        if((p.getTurnsInJail() != -1) && rolls[0]!=rolls[1]){
            //this should be in a properties file
        //    throw new JailException("You have been in Jail for " + p.getTurnsInJail() + " turns");
       // }
        board.movePlayer(p, rolls);
        //board.getPlayerTile(p).applyLandedOnAction();
    }

    public void turn(AbstractPlayer p, int[] rolls){

    }

}
