package controller;

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
        myGameController = new GameController(
                this,
                new SixDice(),
                playerToIcon
        );

        myBoardView = new SquareBoardView(
                new StandardBoard(myGame.getBoard().getMyPlayerList(), myData),
                getScreenWidth()*0.5, getScreenHeight()*0.9,
                90,11,11
        );
        myGameController = new GameController(screenWidth,screenHeight,myPropertyFile,CONFIG_FILE);
        myNode = myGameController.getGameNode();
    }

    public Node getNode() {
        return myNode;
    }

    public void startGame(Map<TextField, ComboBox> playerToIcon) {
        makeSetUpScreen(playerToIcon);
        myScreen.changeDisplayNode(myNode);
    }
}
