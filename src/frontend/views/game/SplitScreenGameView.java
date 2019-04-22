package frontend.views.game;

import configuration.ImportPropertyFile;
import frontend.views.board.AbstractBoardView;
import frontend.views.board.SquareBoardView;
import frontend.views.player_options.AbstractOptionsView;
import frontend.views.player_options.DiceView;
import frontend.views.player_options.VBoxOptionsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SplitScreenGameView extends AbstractGameView {
    private GridPane myPane;
    private AbstractBoardView myBoardView;
    private AbstractOptionsView myOptionsView;
    private DiceView myDiceView;

    public SplitScreenGameView(double screenWidth, double screenHeight, ImportPropertyFile propertyFile){
        super(screenWidth,screenHeight, propertyFile);
        myBoardView = new SquareBoardView(0.9*screenWidth, 0.9*screenHeight,90,11,11,propertyFile);
        myOptionsView = new VBoxOptionsView(this);
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
        myPane.add(myOptionsView.getOptionsViewNode(),1,0);
    }

    @Override
    public void setTurnActions(List<String> turnActions) {

    }

    @Override
    public String showInputTextDialog(String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        else {
            //TODO: throw exception
            return null;
        }
    }

    @Override
    public Node getPane() {
        return myPane;
    }

    public void setPossibleActions(Map<String, EventHandler<ActionEvent>> actionMap){

    }

    public void createOptions(Map<String, EventHandler<ActionEvent>> handlerMap){
        myOptionsView.createButtons(handlerMap);
    }

}
