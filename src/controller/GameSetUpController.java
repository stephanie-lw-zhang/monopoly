package controller;

import configuration.ImportPropertyFile;
import frontend.views.FormView;

import javafx.scene.Node;

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
    private GameController     myGameController;
    private FormView           myFormView;
    private double             screenWidth,screenHeight;
    private Node               myNode;

    public GameSetUpController(double sWidth, double sHeight){
        screenWidth = sWidth;
        screenHeight = sHeight;
        makeSetUpScreen();
    }

    private void makeSetUpScreen() {
        myGameController = new GameController(screenWidth,screenHeight,myPropertyFile,CONFIG_FILE);
        myNode = myGameController.getGameNode();
    }

    public Node getNode() {
        return myNode;
    }
}
