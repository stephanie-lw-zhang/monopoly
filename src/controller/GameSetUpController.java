package controller;

import configuration.ImportPropertyFile;
import frontend.screens.AbstractScreen;
import frontend.views.FormView;
import frontend.views.board.AbstractBoardView;
import frontend.views.board.SquareBoardView;
import frontend.views.game.AbstractGameView;
import frontend.views.game.SplitScreenGameView;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Map;

public class GameSetUpController {

    private static final String CONFIG_FILE = "OriginalMonopoly.xml";

    private ImportPropertyFile myPropertyFile = new ImportPropertyFile("OriginalMonopoly.properties");
    private Node myNode;

    private GameController myGameController;
    private FormView myFormView;
    private AbstractScreen myScreen;
    private double screenWidth,screenHeight;

    public GameSetUpController(double sWidth, double sHeight, AbstractScreen screen){
        screenWidth = sWidth;
        screenHeight = sHeight;
        myScreen = screen;
        myFormView = new FormView(this);
        myNode = myFormView;
        //makeSetUpScreen();
    }

    private void makeSetUpScreen() {
        myGameController = new GameController(screenWidth,screenHeight,myPropertyFile,CONFIG_FILE);
        myNode = myGameController.getGameNode();
    }

    public Node getNode() {
        return myNode;
    }

    public void startGame(Map<TextField, ComboBox> playerToIcon) {
        makeSetUpScreen();
        myScreen.changeDisplayNode(myNode);
    }
}
