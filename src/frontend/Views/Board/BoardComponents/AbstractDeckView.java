package frontend.Views.Board.BoardComponents;

import javafx.scene.Node;

public abstract class AbstractDeckView {
    private String myTileName;
    private String myDescription;

    public AbstractDeckView(String name, String description){
        myTileName = name;
        myDescription = description;
    }

    abstract public Node getNodeOfTileView();
    abstract public void makeTileViewNode(double[] dimensions);
    //abstract public void placeTileView(int x, int y);

    public String getMyTileName(){ return myTileName; }

    public String getMyDescription(){ return myDescription; }


}
