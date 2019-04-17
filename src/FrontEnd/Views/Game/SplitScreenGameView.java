package FrontEnd.Views.Game;

import FrontEnd.Views.Board.AbstractBoardView;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class SplitScreenGameView extends AbstractGameView {
    private GridPane myPane;

    public SplitScreenGameView(double screenWidth, double screenHeight){
        super(screenWidth,screenHeight);
    }
    @Override
    public Node getGameViewNode() {
        return null;
    }

    @Override
    public void setBoundsForEntireGame(double screenWidth, double screenHeight) {
        myPane = new GridPane();
        myPane.setMaxWidth(screenWidth);
        myPane.setMaxHeight(screenHeight);
    }

    @Override
    public void divideScreen() {
        ColumnConstraints leftCol = new ColumnConstraints();
        leftCol.setPercentWidth(50);
        ColumnConstraints rightCol = new ColumnConstraints();
        rightCol.setPercentWidth(50);
        myPane.getColumnConstraints().addAll(leftCol,rightCol);
    }

    @Override
    public void addPlayerOptionsView() {

    }

    @Override
    public Node getPane() {
        return myPane;
    }

    @Override
    public void addBoardView(AbstractBoardView boardView){
        myPane.add(boardView.getPane(),0,0);
    }

}
