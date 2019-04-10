package FrontEnd;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class CornerTileView extends AbstractTileView{

    private double mySideLength;
    private StackPane myRoot;

    public CornerTileView(String name, String description, String color) {
        super(name, description, color);
        myRoot = new StackPane();
    }

    @Override
    public Node getNodeOfTileView() {
        return myRoot;
    }

    @Override
    public void makeTileViewNode(double[] dimensions) {
        mySideLength = dimensions[0];
        //myRoot.setMaxSize(myWidth, myHeight);
        myRoot.getChildren().add(makeText());
        myRoot.getChildren().add(makeBorder());
    }

    private Node makeBorder() {
        Rectangle border = new Rectangle(mySideLength/2, mySideLength/2, mySideLength, mySideLength );
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(2);
        border.setStrokeType(StrokeType.INSIDE);
        myRoot.setAlignment(border, Pos.CENTER);
        return border;
    }

    private Node makeText() {
        Text tileText = new Text(getMyTileName());
        myRoot.setAlignment(tileText,Pos.CENTER);
        return tileText;
    }


}
