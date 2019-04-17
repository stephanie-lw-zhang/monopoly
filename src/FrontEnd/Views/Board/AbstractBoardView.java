package FrontEnd.Views.Board;

import javafx.scene.layout.Pane;

abstract public class AbstractBoardView {
    public AbstractBoardView(double screenWidth, double screenHeight){
        setRoot();
        setScreenLimits(screenWidth,screenHeight);
    }

    abstract public void setRoot();
    abstract public void setScreenLimits(double screenWidth,double screenHeight);
    abstract public void makeBoard();
    abstract public Pane getPane();
}
