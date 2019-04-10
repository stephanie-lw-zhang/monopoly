package BackEnd.Board;

import BackEnd.AssetHolder.AbstractPlayer;
import BackEnd.AssetHolder.Bank;
import BackEnd.AssetHolder.HumanPlayer;
import BackEnd.Card.PropertyCard;
import BackEnd.Tile.PropertyTiles.BuildingTile;
import BackEnd.Tile.TileInterface;
import BackEnd.Tile.GoTile;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StandardBoardTest {

    @BeforeEach
    void setUp(){
        List playerList = new ArrayList<AbstractPlayer>();
        Map adjacencyList = new HashMap<TileInterface, List<TileInterface>>();
        Map colorMap = new HashMap<Color, List<BuildingTile>>();
        StandardBoard board = new StandardBoard(playerList, adjacencyList, colorMap);
        playerList.add(new HumanPlayer(0.0, new Bank(0.0)));
        TileInterface go = new GoTile(200.0,200.0);
        List<TileInterface> nextTile = new ArrayList<>();
        nextTile.add(new BuildingTile(new Bank(0.0), new PropertyCard("Property", 20, 100), "Property", 200.0, Color.BLUE, board));
        adjacencyList.put(go, nextTile);


    }

    @Test
    void movePlayer() {
    }
}