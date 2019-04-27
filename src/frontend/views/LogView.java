package frontend.views;

import configuration.XMLData;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
public class LogView {

    private XMLData myData;
    private TextArea myGameLog;
    private StringBuilder myLogContent;

    private StackPane myRoot;

    public LogView(XMLData data) {
        myData = data;
        myRoot = new StackPane();
        myLogContent = new StringBuilder("Welcome to " + myData.getMonopolyType() + ".");
        myLogContent.append("\nhello");
        myLogContent.append("\nok");
        myLogContent.append("\nsweet");
        myGameLog = new TextArea(myLogContent.toString());
        myGameLog.setEditable(false);
        myGameLog.setStyle("-fx-max-width: 400; -fx-max-height: 100");
    }

    public void updateLogDisplay(final String message) {
        myLogContent.append("\n");
        myLogContent.append(message);
        myGameLog.setText(myLogContent.toString());
    }

    /**
     *
     * @return
     */
    public Node getNode() {
        return myGameLog;
    }

}
