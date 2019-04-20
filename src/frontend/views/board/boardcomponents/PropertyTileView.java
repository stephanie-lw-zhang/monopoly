package frontend.views.board.boardcomponents;

import configuration.ImportPropertyFile;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class PropertyTileView extends AbstractTileView {
    private double myWidth;
    private double myHeight;
    private StackPane myRoot;
    private Paint myColor;
    private ImportPropertyFile myPropertyFile;
    private ImportPropertyFile myDetails;

    public PropertyTileView(String name, ImportPropertyFile details, String description, Paint paint) {
        super(name, description);
        myColor = paint;
        myRoot = new StackPane();
        myDetails=details;

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

    @Override
    public Node getNodeOfTileView() {
        return myRoot;
    }

    @Override
    public void makeTileViewNode(double[] dimensions) {
        myWidth = dimensions[0];
        myHeight = dimensions[1];
        myRoot.setMaxSize(myWidth, myHeight);
        myRoot.getChildren().add(makeBorder());
        myRoot.getChildren().add(makeLabel());
        myRoot.getChildren().add(makeText());

    }



    private Node makeBorder() {
        Rectangle border = new Rectangle(myWidth/2, myHeight/2, myWidth, myHeight );
        border.setFill(Color.rgb(197,225,164));
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(2);
        border.setStrokeType(StrokeType.INSIDE);
        myRoot.setAlignment(border,Pos.CENTER);
        return border;
    }

    private Node makeText() {
        Text tileText = new Text(getMyTileName());
        tileText.setTextAlignment(TextAlignment.CENTER);
        tileText.setWrappingWidth(myWidth);
        tileText.setFont(Font.font("Verdana",myWidth/5));
        myRoot.setAlignment(tileText,Pos.BOTTOM_CENTER);
        return tileText;
    }

    private Node makeLabel() {
        Rectangle labelShape = new Rectangle(myWidth, myHeight / 3);
        labelShape.setFill(myColor);
        labelShape.setStrokeWidth(1);
        labelShape.setStroke(Color.BLACK);
        labelShape.setStrokeType(StrokeType.INSIDE);
        myRoot.setAlignment(labelShape,Pos.TOP_CENTER);
        return labelShape;
    }

}


