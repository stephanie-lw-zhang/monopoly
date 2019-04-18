package frontend.Views.Board.BoardComponents;
import configuration.ImportPropertyFile;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

public class CornerTileView extends AbstractTileView {

    private double mySideLength;
    private StackPane myRoot;
    ImportPropertyFile myPropertyFile;
    String myDetails;

    public CornerTileView(String name, String details, String description, String color) {
        super(name, description);
        myRoot = new StackPane();
        myDetails = details;
    }

    @Override
    public Node getNodeOfTileView() {
        return myRoot;
    }

    @Override
    public void makeTileViewNode(double[] dimensions) {
        mySideLength = dimensions[0];
        myRoot.getChildren().add(makeBorder());
        myRoot.getChildren().add(makeText());
    }

    @Override
    public double getmyX() {
        return myRoot.getLayoutX();
    }

    @Override
    public double getmyY() {
        return myRoot.getLayoutY();
    }

    public void moveTo(Node n){
        myRoot.setAlignment(n,Pos.CENTER);
        myRoot.getChildren().add(n);
    }

    public void moveFrom(Node n){
        myRoot.getChildren().remove(n);
    }

    private Node makeBorder() {
        Rectangle border = new Rectangle(mySideLength/2, mySideLength/2, mySideLength, mySideLength );
        border.setFill(Color.rgb(197,225,164));
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
