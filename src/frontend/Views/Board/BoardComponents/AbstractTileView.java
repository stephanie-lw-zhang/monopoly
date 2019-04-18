package frontend.Views.Board.BoardComponents;

import javafx.scene.Node;

public abstract class AbstractTileView {
    private String myTileName;
    private String myDescription;

    public AbstractTileView(String name, String description){
        myTileName = name;
        myDescription = description;
    }

    abstract public Node getNodeOfTileView();
    abstract public void makeTileViewNode(double[] dimensions);
    //abstract public void placeTileView(int x, int y);

    public String getMyTileName(){ return myTileName; }

    public String getMyDescription(){ return myDescription; }

    abstract public double getmyX();
    abstract public double getmyY();

    abstract public void moveTo(Node n);
    abstract public void moveFrom(Node n);



}
