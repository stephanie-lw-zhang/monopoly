package frontend.views.board;

import frontend.views.IconView;
import javafx.scene.layout.Pane;

abstract public class AbstractBoardView {

    public AbstractBoardView(double screenWidth, double screenHeight){
        setRoot();
        setScreenLimits(screenWidth,screenHeight);
    }

    abstract public void move(IconView icon, int numMoves);
    abstract public void setRoot();
    abstract public void setScreenLimits(double screenWidth,double screenHeight);
    abstract public void makeBoard();
    abstract public Pane getPane();
}
