
package BackEnd.Game;

import BackEnd.Deck.DeckInterface;
import BackEnd.Dice.AbstractDice;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;

import java.util.List;

public class Game {

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

    public boolean gameIsOver(){
        int sum = 0;
        for(AbstractPlayer p: players){
            if(p.isBankrupt()) sum++;
        }
        return sum==players.size()-1;
    }

}