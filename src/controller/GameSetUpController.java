package controller;

import backend.assetholder.AbstractPlayer;
import backend.assetholder.HumanPlayer;
import backend.board.AbstractBoard;
import backend.board.StandardBoard;
import backend.dice.SixDice;
import configuration.ImportPropertyFile;
import configuration.XMLData;
import frontend.screens.AbstractScreen;
import frontend.views.FormView;

import frontend.views.board.SquareBoardView;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is a controller that manages all "set-up" before
 * the game starts (i.e. creation of board, etc.)
 *
 * @author Edward
 * @author Sam
 */
public class GameSetUpController {

    private static final String CONFIG_FILE = "OriginalMonopoly.xml";


    private ImportPropertyFile myPropertyFile = new ImportPropertyFile("OriginalMonopoly.properties");
    private Node myNode;

    private GameController myGameController;
    private FormView myFormView;
    private AbstractScreen myScreen;
    private double screenWidth,screenHeight;
    private XMLData myData;
    private AbstractBoard myBoard;

    public GameSetUpController(double sWidth, double sHeight, AbstractScreen screen){
        screenWidth = sWidth;
        screenHeight = sHeight;
        myScreen = screen;
        myFormView = new FormView(this);
        myNode = myFormView.getNode();
        try {
            myData = new XMLData(CONFIG_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //makeSetUpScreen();
    }

    private void makeSetUpScreen(Map<TextField, ComboBox> playerToIcon) {
        myBoard = makeBoard(playerToIcon);
        myGameController = new GameController(
                screenWidth,screenHeight,
                this, myBoard, myData
        );
        myNode = myGameController.getGameNode();
    }

    public Node getNode() {
        return myNode;
    }

    public void startGame(Map<TextField, ComboBox> playerToIcon) {
        makeSetUpScreen(playerToIcon);
        myScreen.changeDisplayNode(myNode);
    }

    private AbstractBoard makeBoard(Map<TextField, ComboBox> playerToIcon) {
        return new StandardBoard(
                makePlayerList(playerToIcon), myData.getAdjacencyList(),
                myData.getPropertyCategoryMap(), myData.getFirstTile(),
                myData.getBank()
        );
    }

    private List<AbstractPlayer> makePlayerList(Map<TextField, ComboBox> playerToIcon) {
        List<AbstractPlayer> playerList = new ArrayList<>();

        for (TextField pName : playerToIcon.keySet()) {
            String name = pName.getText();
            if (!name.equals(""))
                playerList.add(new HumanPlayer(
                        name,
                        (String) playerToIcon.get(pName).getValue(),
                        1500.00));
        }
        return playerList;
    }
}
