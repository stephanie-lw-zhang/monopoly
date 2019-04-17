package Controller;

import BackEnd.AssetHolder.Bank;
import BackEnd.AssetHolder.HumanPlayer;
import BackEnd.Deck.DeckInterface;
import BackEnd.Dice.AbstractDice;
import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.Board.AbstractBoard;
import Configuration.ImportPropertyFile;
import FrontEnd.Screens.TestingScreen;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.List;

public class Game {

    private List<AbstractPlayer> myPlayers;
    private DeckInterface        chanceDeck;
    private DeckInterface        chestDeck;
    private AbstractBoard        myBoard;
    private AbstractDice         myDice;
    private TestingScreen        myTestScreen;
    private Turn                 myTurn;
    private ImportPropertyFile myPropertyFile;

    public Game(TestingScreen view, AbstractDice dice, DeckInterface chanceDeck, DeckInterface chestDeck, AbstractBoard board) {
        myDice = dice;
        chanceDeck = chanceDeck;
        chestDeck = chestDeck;
        myBoard = board;

        myTestScreen = view;

        // make first param list of players
        myTurn = new Turn(myBoard.getMyPlayerList().get(0), myDice, myBoard);
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

        // TODO: REFLECTION FOR ALL OF THIS
        Button endTurnButton = (Button) playerOptionsModal.getChildren().get(4);
        endTurnButton.setOnAction(f -> handleEndTurnButton());
    }

    private void handleEndTurnButton() {
        myTurn.skipTurn();
        myTestScreen.updateCurrentPlayer(myTurn.getMyCurrPlayer());
    }

    private void handleRollButton() {
        myTurn.start();

        myTestScreen.updateDiceView(myTurn.getRolls());
        myTestScreen.displayRollsPopup(myTurn);

        myTurn.onAction(Actions.MOVE);
        //TODO: move player front end
        List<Actions> possibleActions = myTurn.getMyActions();
        //TODO: front end display these two possible actions

        myTestScreen.updateCurrentPlayer(myTurn.getMyCurrPlayer());
    }

    public AbstractBoard getBoard() { return myBoard; }
    public AbstractDice getMyDice() { return myDice; }
    public Turn getMyTurn() { return myTurn; }
}
