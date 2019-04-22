package controller;

import configuration.ImportPropertyFile;
import frontend.views.FormView;
import frontend.views.board.AbstractBoardView;
import frontend.views.board.SquareBoardView;
import frontend.views.game.AbstractGameView;
import frontend.views.game.SplitScreenGameView;
import javafx.scene.Node;

public class GameSetUpController {

    private static final String CONFIG_FILE = "OriginalMonopoly.xml";

    private ImportPropertyFile myPropertyFile = new ImportPropertyFile("OriginalMonopoly.properties");
    private Node myNode;

    private GameController myGameController;
    private FormView myFormView;
    private double screenWidth,screenHeight;

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
