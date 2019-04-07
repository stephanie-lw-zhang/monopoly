package api.Monopoly.BackEnd;

public class Game {

    private AbstractDice dice;
    private AbstractDeck chanceDeck;
    private AbstractDeck chestDeck;
    private AbstractBoard board;
    private Bank bank;

    public Game(AbstractDice dice, AbstractDeck chanceDeck, AbstractDeck chestDeck, AbstractBoard board, Bank bank){
        this.dice = dice;
        this.chanceDeck = chanceDeck;
        this.chestDeck = chestDeck;
        this.board = board;
        this.bank = bank;
    }

    public int[] rollValue(){
        int[] rolls = new int[2];
        rolls[0] = dice.roll();
        rolls[1] = dice.roll();
        return rolls;
    }

    public void turn(AbstractPlayer p){
        int[] rolls = rollValue();
        board.movePlayer(p, rolls, false);
        board.getPlayerTile(p).handleLanding();
    }

}