package frontend.views.game;

import frontend.views.board.AbstractBoardView;
import javafx.scene.Node;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.List;

public class SplitScreenGameView extends AbstractGameView {
    private GridPane myPane;
    private AbstractBoardView myBoardView;

    public SplitScreenGameView(double screenWidth, double screenHeight,AbstractBoardView boardView){
        super(screenWidth,screenHeight, boardView);
        myBoardView = boardView;
        myPane.add(myBoardView.getPane(),0,0);
    }
    @Override
    public Node getGameViewNode() {
        return myPane;
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
    public void setTurnActions(List<String> turnActions) {

    }

    @Override
    public Node getPane() {
        return myPane;
    }


}
