package frontend.Views.Game;

import frontend.Views.Board.AbstractBoardView;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

abstract public class AbstractGameView {
    private Scene myScene;
    private AbstractBoardView myBoardView;
    private BorderPane myPane;

    public AbstractGameView(double screenWidth, double screenHeight){
        setBoundsForEntireGame(screenWidth,screenHeight);
        divideScreen();
    }
    abstract public Node getGameViewNode();
    abstract public void setBoundsForEntireGame(double screenWidth, double screenHeight);
    abstract public void divideScreen();
    abstract public void addBoardView(AbstractBoardView boardView);
    abstract public void addPlayerOptionsView();

    public abstract Node getPane();
}
