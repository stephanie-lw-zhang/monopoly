package frontend.views;

import controller.GameSetUpController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class GameConfigView {
    private HBox myButtonBox;
    private GameSetUpController myController;

    public GameConfigView(GameSetUpController controller){
        myController = controller;
        makeButtons();
    }

    private void makeButtons() {
        myButtonBox = new HBox();
        Button saveButton = new Button("Save");
        Button loadButton = new Button("Load");
        saveButton.setOnAction(actionEvent -> myController.handleSave());
        loadButton.setOnAction(actionEvent -> myController.handleLoad());
        myButtonBox.getChildren().addAll(saveButton,loadButton);
    }

    public Node getNode(){
        return myButtonBox;
    }
}
