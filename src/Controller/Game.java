package Controller;

import BackEnd.AssetHolder.Bank;
import BackEnd.AssetHolder.HumanPlayer;
import BackEnd.Deck.DeckInterface;
import BackEnd.Dice.AbstractDice;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;
import FrontEnd.Screens.AbstractScreen;
import FrontEnd.Screens.TestingScreen;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;
//import java.util.logging.Logger;

public class Game {

//    private System.Logger LOGGER = new Logger.getLogger(Game.class.getName());
    private AbstractDice myDice;
    private DeckInterface chanceDeck;
    private DeckInterface chestDeck;
    private AbstractBoard myBoard;
    private List<AbstractPlayer> myPlayers;
    private Turn myTurn;
    private TestingScreen myTestScreen;
    //private Bank bank;

    public Game(TestingScreen view, AbstractDice dice, DeckInterface chanceDeck, DeckInterface chestDeck, AbstractBoard board) {
        this.myDice = dice;
        this.chanceDeck = chanceDeck;
        this.chestDeck = chestDeck;
        this.myBoard = board;

        myTestScreen = view;

        // make first param list of players
        this.myTurn = new Turn(new HumanPlayer("Example", 200.0, new Bank(200.0, new HashMap<String, Integer>())), myDice, myBoard);
    }

    public int[] rollValue() {
        return myTurn.rollDice(2);
    }

    public void play() {

    }

    public void handleRollButton() {
        int[] rolls = myTurn.rollDice(2);

//        myTestScreen.updateDiceView(rolls);

//        BorderPane bPane = (BorderPane) myTestScreen.getMyScene().getRoot();

        Text diceText = new Text("You rolled a " + rolls[0] + " and a " + rolls[1] + "! " +
                "Move " + myTurn.getNumMoves() + " spots!");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("DICE ROLL");
        alert.setContentText("You rolled a " + rolls[0] + " and a " + rolls[1] + "! " +
                "Moving " + myTurn.getNumMoves() + " spots...");
        alert.showAndWait();


    }

    public AbstractBoard getBoard() {
        return myBoard;
    }
    public AbstractDice getMyDice() { return myDice; }
}
