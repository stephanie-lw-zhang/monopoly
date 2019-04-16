package Controller;

import BackEnd.AssetHolder.Bank;
import BackEnd.AssetHolder.HumanPlayer;
import BackEnd.Deck.DeckInterface;
import BackEnd.Dice.AbstractDice;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;
import FrontEnd.Screens.AbstractScreen;
import FrontEnd.Screens.TestingScreen;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

    public void startGameLoop() {
        BorderPane bPane = (BorderPane) myTestScreen.getTestScene().getRoot();
        StackPane boardStackPane = (StackPane) bPane.getCenter();
        ObservableList vList = boardStackPane.getChildren();

        // TODO: CANNOT HARDCODE GETTING 1st element in vList (the VBox)
        // TODO: Maybe use "setUserData" for the VBox and retrieve that way
        VBox playerOptionsModal = (VBox) vList.get(1);

        // TODO: SIMILAR AS TODO ABOVE, SHOULDN'T HARDCODE FOR 0th ELEMENT
        // TODO: In VBOX FOR INNER HBOX
        Button rollButton = (Button) playerOptionsModal.getChildren().get(1);
        rollButton.setOnAction(f -> handleRollButton());
    }

    public int[] rollValue() {
        return myTurn.rollDice(2);
    }

    public void handleRollButton() {
        myTurn.start();

        myTestScreen.updateDiceView(myTurn.getRolls());

        myTestScreen.displayRollsPopup(myTurn);
    }

    public AbstractBoard getBoard() {
        return myBoard;
    }
    public AbstractDice getMyDice() { return myDice; }
}
