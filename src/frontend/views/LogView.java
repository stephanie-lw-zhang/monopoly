package frontend.views;

import configuration.XMLData;
import frontend.views.board.boardcomponents.AbstractTileView;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

//import

import javax.swing.*;
import java.util.ArrayList;


public class LogView {

    private XMLData myData;
    public TextArea gameLog;

    private double myWidth;
    private double myHeight;
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

    public void moveTo(Node n){
        myRoot.setAlignment(n, Pos.CENTER);
        myRoot.getChildren().add(n);
    }

    public void moveFrom(Node n){
        myRoot.getChildren().remove(n);
    }

    public Node getNodeOfTileView() {
        return myRoot;
    }

    public void makeLogViewNode(double[] dimensions) {
        myWidth = dimensions[0];
        myHeight = dimensions[1];
        myRoot.setMaxSize(myWidth, myHeight);
        myRoot.getChildren().add(makeBorder());
    }

    public double getmyX() {
        return myRoot.getLayoutX();
    }

    public double getmyY() {
        return myRoot.getLayoutY();
    }

    private Node makeBorder() {
        Rectangle border = new Rectangle(myWidth / 2, myHeight / 2, myWidth, myHeight);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(2);
        border.setStrokeType(StrokeType.INSIDE);
        myRoot.setAlignment(border, Pos.CENTER);
        return border;
    }



}
