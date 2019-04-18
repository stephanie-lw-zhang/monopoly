package frontend.Views.Board.BoardComponents;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class NormalDeckView extends AbstractTileView {
    private double myWidth;
    private double myHeight;
    private StackPane myRoot;

    public NormalDeckView(String name, String description, String color) {
        super(name, description);
        myRoot = new StackPane();
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
        myRoot.getChildren().add(makeText());
        myRoot.getChildren().add(makeBorder());
    }

    private Node makeBorder() {
        Rectangle border = new Rectangle(myWidth/2, myHeight/2, myWidth, myHeight );
        border.setFill(Color.TRANSPARENT);
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
        myRoot.setAlignment(tileText,Pos.CENTER);
        return tileText;
    }



}

