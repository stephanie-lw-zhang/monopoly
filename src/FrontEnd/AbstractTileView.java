package FrontEnd;

import javafx.scene.Node;

public abstract class AbstractTileView {
    private String myTileName;
    private String myDescription;
    private String myColor;

    public AbstractTileView(String name, String description, String color){
        myTileName = name;
        myDescription = description;
        myColor = color;
    }

    abstract public Node getNodeOfTileView();
    abstract public void makeTileViewNode(int[] dimensions);
    abstract public void placeTileView(int x, int y);

    public String getMyTileName(){ return myTileName; }

    public String getMyDescription(){ return myDescription; }

    public String getMyColor(){ return myColor;}

}
