package frontend.views.game;

import frontend.views.board.AbstractBoardView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.List;

abstract public class AbstractGameView {
    private Scene myScene;
    private AbstractBoardView myBoardView;
    private BorderPane myPane;

    public AbstractGameView(double screenWidth, double screenHeight, AbstractBoardView boardView){
        setBoundsForEntireGame(screenWidth,screenHeight);
        divideScreen();
    }
    abstract public Node getGameViewNode();
    abstract public void setBoundsForEntireGame(double screenWidth, double screenHeight);
    abstract public void divideScreen();
    abstract public void addPlayerOptionsView();
    abstract public void setTurnActions(List<String> turnActions);

    public abstract Node getPane();
}
