package FrontEnd;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class RectangularTileView extends AbstractTileView {
    private double myWidth;
    private double myHeight;
    private StackPane myRoot;

    public RectangularTileView(String name, String description, String color) {
        super(name, description, color);
    }

    @Override
    public Node getNodeOfTileView() {
        return myRoot;
    }

    @Override
    public void makeTileViewNode(double[] dimensions) {
        myWidth = dimensions[0];
        myHeight = dimensions[1];
        myRoot.setMaxSize(myWidth, myHeight);
        myRoot.getChildren().add(makeLabel());
        myRoot.getChildren().add(makeText());
        myRoot.getChildren().add(makeBorder());
    }

    private Node makeBorder() {
        Rectangle border = new Rectangle(myWidth/2, myHeight/2, myWidth, myHeight );
        return border;
    }

    private Node makeText() {
        Text tileText = new Text(myWidth/2,myHeight/10,getMyTileName());
        return tileText;
    }

    private Node makeLabel() {
        Rectangle labelShape = new Rectangle(myWidth/2, myHeight/10, myWidth, myHeight / 5);
        labelShape.setFill(Color.BLUE);
        return labelShape;
    }

}

