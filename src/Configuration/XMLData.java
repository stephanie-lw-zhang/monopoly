package Configuration;

import BackEnd.AssetHolder.Bank;
import BackEnd.Board.AbstractBoard;
import BackEnd.Card.BuildingCard;
import BackEnd.Card.PropertyCard;
import BackEnd.Tile.AbstractPropertyTile;
import BackEnd.Tile.Tile;
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
    private Map<String, List<AbstractPropertyTile>> propertyCategoryToSpecificListMap;
    private Bank bank;
    private AbstractBoard board;

    public XMLData(String fileName) throws Exception {
        File xmlFile = new File(this.getClass().getClassLoader().getResource(fileName).toURI());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList banks = doc.getElementsByTagName("Bank");
            getBank(banks.item(0));

            NodeList tileList = doc.getElementsByTagName("Tile");
            List<Tile> tiles = new ArrayList<>();
            for (int i = 0; i < tileList.getLength(); i++) {
                tiles.add(getTile(tileList.item(i)));
            }
            for (Tile tile : tiles) {
                //System.out.println(tile);
            }
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
            //get tag values from properties file//do we need to split the array
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
            if(tileType.equalsIgnoreCase("BuildingTile")){
                BuildingCard card = new BuildingCard(element.getElementsByTagName("Card").item(0));
                tile = (Tile) Class.forName("BackEnd.Tile." + tileType).getConstructor(Bank.class, PropertyCard.class, Element.class).newInstance(bank, card, element);
            }
            else if(tileType.equalsIgnoreCase("RailroadTile") || tileType.equalsIgnoreCase("UtilityTile")){
                PropertyCard card = new PropertyCard(element.getElementsByTagName("Card").item(0));
                tile = (Tile) Class.forName("BackEnd.Tile." + tileType).getConstructor(Bank.class, PropertyCard.class, Element.class).newInstance(bank, card, element);
            }
            else if(tileType.contains("Tax")){
                tile = (Tile) Class.forName("BackEnd.Tile." + tileType).getConstructor(Bank.class, Element.class).newInstance(bank, element);
            }
            else tile = (Tile) Class.forName("BackEnd.Tile." + tileType).getConstructor(Element.class).newInstance(element);
            return tile;
        }
        else{
            return null; //change this!!
        }
    }

    private static void initializeAdjacencyList(List<Tile> tiles){
        //TODO loop through list and create adjacency list
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public AbstractBoard getBoard(){
        return board;
    }
}
