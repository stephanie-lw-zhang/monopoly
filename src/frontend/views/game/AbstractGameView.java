package frontend.views.game;

import configuration.ImportPropertyFile;
import frontend.views.board.AbstractBoardView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public String displayOptionsPopup(List<String> options, String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        List<ButtonType> buttonOptions = new ArrayList<>();
        for (String option : options) {
            buttonOptions.add(new ButtonType(option));
        }
        alert.getButtonTypes().setAll(buttonOptions);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get().getText();
    }

    abstract public void createOptions(Map<String, EventHandler<ActionEvent>> handlerMap);

    public abstract Node getPane();
}
