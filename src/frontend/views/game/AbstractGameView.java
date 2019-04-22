package frontend.views.game;

import configuration.ImportPropertyFile;
import frontend.views.board.AbstractBoardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.util.List;
import java.util.Map;

abstract public class AbstractGameView {
    private Scene myScene;
    private AbstractBoardView myBoardView;
    private BorderPane myPane;

    public AbstractGameView(double screenWidth, double screenHeight, ImportPropertyFile propertyFile){
        setBoundsForEntireGame(screenWidth,screenHeight);
        divideScreen();
    }
    abstract public Node getGameViewNode();
    abstract public void setBoundsForEntireGame(double screenWidth, double screenHeight);
    abstract public void divideScreen();
    abstract public void addPlayerOptionsView();
    abstract public void setTurnActions(List<String> turnActions);
    abstract public String showInputTextDialog(String title, String header, String content);

    public void displayActionInfo(String info) {
        Alert formAlert = new Alert(Alert.AlertType.INFORMATION);
        formAlert.setContentText(info);
        formAlert.showAndWait();
    }
    abstract public void createOptions(Map<String, EventHandler<ActionEvent>> handlerMap);

    public abstract Node getPane();
}
