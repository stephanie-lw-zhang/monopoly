package testing.backendtests;

public class StandardBoardTest {
    /**
     private StandardBoard board;
     private List<AbstractPlayer> playerList;
     private List<tile> goNeighbor;
     private List<tile> buildingNeighbor;
     private List<tile> jailNeighbor;
     private List<tile> goToJailNeighbor;

     @BeforeEach
     void setUp(){
     playerList = new ArrayList<>();
     //        playerList.add(new HumanPlayer(0.0, new Bank(0.0)));
     Map adjacencyList = new HashMap<tile, List<tile>>();
     tile go = new GoTile(200.0,200.0);
     JailTile jail = new JailTile();
     goNeighbor = new ArrayList<>();


     //        goNeighbor.add(new BuildingTile(new Bank(0.0), new BuildingCard("Property", 0.0,0.0, java.awt.Color.BLUE,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0), "Property", 200.0, java.awt.Color.BLACK));

     //        goNeighbor.add(new BuildingTile(new Bank(0.0), new BuildingCard("Property", 0.0,0.0, java.awt.Color.BLUE,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0), "Property", 200.0, java.awt.Color.BLACK));

     buildingNeighbor = new ArrayList<>();
     buildingNeighbor.add(jail);
     jailNeighbor = new ArrayList<>();
     jailNeighbor.add(new GoToJailTile(jail));
     goToJailNeighbor = new ArrayList<>();
     goToJailNeighbor.add(go);
     adjacencyList.put(go, goNeighbor);
     adjacencyList.put(goNeighbor.get(0), buildingNeighbor);
     adjacencyList.put(buildingNeighbor.get(0), jailNeighbor);
     adjacencyList.put(jailNeighbor.get(0), goToJailNeighbor);
     Map colorMap = new HashMap<Color, List<BuildingTile>>();
     board = new StandardBoard(playerList, adjacencyList, colorMap, go);
     }

     @Test
     void movesOneTile(){
     //        board.movePlayer(playerList.get(0), play);
     assertEquals(goNeighbor.get(0), board.getPlayerTile(playerList.get(0)));
     }

     @Test
     void testBoardCycle() {
     //        board.movePlayer(playerList.get(0), new int[]{6, 6});
     assertEquals(goToJailNeighbor.get(0), board.getPlayerTile(playerList.get(0)));
     }

     @Test
     void testGoToJailTile() {
     //        board.movePlayer(playerList.get(0), new int[]{5, 6});
     board.getPlayerTile(playerList.get(0)).applyLandedOnAction(playerList.get(0));
     assertEquals(buildingNeighbor.get(0), board.getPlayerTile(playerList.get(0)));
     }
     */
}
