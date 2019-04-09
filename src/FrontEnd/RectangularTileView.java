package FrontEnd;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class RectangularTileView extends AbstractTileView {
    private double myWidth;
    private double myHeight;
    private StackPane myRoot;

    public RectangularTileView(String name, String description, String color){
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
        myRoot.setMaxSize(myWidth,myHeight);
        myRoot.getChildren().add(makeLabel());
        myRoot.getChildren().add(makeText());
        myRoot.getChildren().add(makeBorder());
    }

    private Node makeLabel() {
        Rectangle labelShape = new Rectangle();

    }

    @Override
    public void placeTileView(int x, int y) {

    }
}
