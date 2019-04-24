package frontend.views;

import configuration.XMLData;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
public class LogView {

    private XMLData myData;
    public TextArea gameLog;

    private StackPane myRoot;

    public LogView(XMLData data) {
        myData=data;
        myRoot = new StackPane();
        gameLog = new TextArea("Welcome to " + myData.getMonopolyType() + ".");
        gameLog.setText("Welcome to " + myData.getMonopolyType() + ".");
        gameLog.setEditable(false);
        gameLog.setStyle("-fx-max-width: 400; -fx-max-height: 100");
        }

    public Pane getPane() {
        return myRoot;
    }

}
