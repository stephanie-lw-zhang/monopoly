package configuration;

import backend.assetholder.Bank;
import backend.board.AbstractBoard;
import backend.tile.AbstractPropertyTile;
import backend.tile.Tile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLData {

    private Map<Tile, List<Tile>> adjacencyList;
    private Map<Tile, List<Integer>> indexNeighborList;
    private Map<String, List<AbstractPropertyTile>> propertyCategoryToSpecificListMap;
    private Bank bank;

    public XMLData(String fileName) throws Exception {
        File xmlFile = new File(this.getClass().getClassLoader().getResource(fileName).toURI());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList tileList = doc.getElementsByTagName("tile");
            NodeList banks = doc.getElementsByTagName("Bank");
            getBank(banks.item(0));
            List<Tile> tiles = new ArrayList<>();
            indexNeighborList = new HashMap<>();
            propertyCategoryToSpecificListMap = new HashMap<>();
            for (int i = 0; i < tileList.getLength(); i++) {
                //System.out.println(i);
                tiles.add(getTile(tileList.item(i)));
            }
            initializeAdjacencyList(tiles);
        }catch(ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace(); //change this !!!
        }
    }

    private void getBank(Node node) {
        Element banks = (Element) node;
        Double money = Double.parseDouble(getTagValue("Money", banks));
        NodeList properties = banks.getElementsByTagName("Properties");
        Map<String, Integer> totalPropertiesLeft = new HashMap<>();

        for(int i = 0; i<properties.getLength();i++){
            String property = getTagValue("Property", (Element)properties.item(i));
            String[] entry = property.split(",");
            totalPropertiesLeft.put(entry[0], Integer.parseInt(entry[1]));
        }
        bank = new Bank(money, totalPropertiesLeft);
    }

    private Tile getTile(Node node) throws Exception {
        Tile tile;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String tileType = getTagValue("TileType", element);
            System.out.println(tileType);
            if(tileType.equalsIgnoreCase("BuildingTile")|| tileType.equalsIgnoreCase("RailroadTile")||tileType.equalsIgnoreCase("UtilityTile")|| tileType.contains("Tax")){
                tile = (Tile) Class.forName("backend.tile." + tileType).getConstructor(Bank.class, Element.class).newInstance(bank, element);
            }
            else tile = (Tile) Class.forName("backend.tile." + tileType).getConstructor(Element.class).newInstance(element);
            indexNeighborList.put(tile, new ArrayList<>());
            String [] neighbors = getTagValue("NextTiles", element).split(",");
            for(String s: neighbors){
                indexNeighborList.get(tile).add(Integer.parseInt(s));
            }
            updateCategoryList(element, tile);
            return tile;
        }
        else{
            return null; //change this!!
        }
    }

    private void updateCategoryList(Element element, Tile tile){
        String color = "";
        try{
            color = getTagValue("TileColor", element);
        }catch (NullPointerException e){
            return;
        }
        if(color != null){
            if(!propertyCategoryToSpecificListMap.containsKey(color)) {
                propertyCategoryToSpecificListMap.put(color, new ArrayList<>());
            }
            else propertyCategoryToSpecificListMap.get(color).add((AbstractPropertyTile) tile);
        }
    }

    private void initializeAdjacencyList(List<Tile> tiles){
        adjacencyList = new HashMap<>();
        for(Tile tile: tiles){
            adjacencyList.put(tile, new ArrayList<>());
            for(int i: indexNeighborList.get(tile)){
                for(Tile neighbor: tiles){
                    if(neighbor.getTileIndex() == i) adjacencyList.get(tile).add(neighbor);
                }
            }
        }
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}
