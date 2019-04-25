package configuration;

import backend.assetholder.Bank;
import backend.card.ActionCard;
import backend.deck.DeckInterface;
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

    //ideally all the tags names would come from properties files

    private Map<Tile, List<Tile>> adjacencyList;
    private Map<Tile, List<Integer>> indexNeighborList;
    private Map<String, List<AbstractPropertyTile>> propertyCategoryToSpecificListMap;
    private Bank bank;
    private int numDie;
    private int numDecks;
    private Tile firstTile;
    private List<DeckInterface> decks;
    private String monopolyType;

    public XMLData(String fileName) throws Exception {
        File xmlFile = new File(this.getClass().getClassLoader().getResource(fileName).toURI());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            numDie = Integer.parseInt(getTagValue("NumDie", (Element) doc.getElementsByTagName("Dice").item(0)));
            numDecks = Integer.parseInt(getTagValue("NumDecks", (Element) doc.getElementsByTagName("Decks").item(0)));
            monopolyType = getTagValue(("MonopolyType"), (Element) doc.getElementsByTagName("Type").item(0));
            bank = getBank(doc.getElementsByTagName("Bank").item(0));
            NodeList tileList = doc.getElementsByTagName("Tile");
            List<Tile> tiles = new ArrayList<>();
            indexNeighborList = new HashMap<>();
            propertyCategoryToSpecificListMap = new HashMap<>();
            for (int i = 0; i < tileList.getLength(); i++) {
                tiles.add(getTile(tileList.item(i)));
            }
            firstTile = tiles.get(0);
            initializeAdjacencyList(tiles);

            decks = new ArrayList<>();
            NodeList deck = doc.getElementsByTagName("Deck");
            for(int i = 0; i<numDecks; i++){
                Element d = (Element) deck.item(i);
                NodeList actionCards = d.getElementsByTagName("ActionCard");
                for(int j = 0; j<actionCards.getLength(); j++){
                    Element c = (Element) actionCards.item(j);
                    String cardType = getTagValue("ActionCardType", c);
                    ActionCard card = (ActionCard) Class.forName("backend.card." + cardType).getConstructor(Element.class).newInstance(c);
                    decks.get(i).putBack(card);
                }
            }

        }catch(ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace(); //change this !!!
        }
    }

    private Bank getBank(Node node) {
        Element banks = (Element) node;
        Double money = Double.parseDouble(getTagValue("Money", banks));
        NodeList properties = banks.getElementsByTagName("Properties");
        Map<String, Integer> totalPropertiesLeft = new HashMap<>();

        for(int i = 0; i<properties.getLength();i++){
            String property = getTagValue("Property", (Element)properties.item(i));
            String[] entry = property.split(",");
            totalPropertiesLeft.put(entry[0], Integer.parseInt(entry[1]));
        }
        return new Bank(money, totalPropertiesLeft);
    }

    private Tile getTile(Node node) throws Exception {
        Tile tile;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String tileType = getTagValue("TileType", element);
            if(tileType.equalsIgnoreCase("BuildingTile")|| tileType.equalsIgnoreCase("RailroadTile")||tileType.equalsIgnoreCase("UtilityTile")|| tileType.contains("Tax")){
                tile = (Tile) Class.forName("backend.tile." + tileType).getConstructor(Bank.class, Element.class).newInstance(bank, element);
                if (!tileType.contains("Tax")) {
                    updateCategoryList(element, tile);
                }
            }
            else tile = (Tile) Class.forName("backend.tile." + tileType).getConstructor(Element.class).newInstance(element);
            indexNeighborList.put(tile, new ArrayList<>());
            String [] neighbors = getTagValue("NextTiles", element).split(",");
            for(String s: neighbors){
                indexNeighborList.get(tile).add(Integer.parseInt(s));
            }
            return tile;
        }
        else{
            return null; //change this!!
        }
    }

    private ActionCard getActionCard(Node node) throws Exception{
        Element element = (Element) node;
        ActionCard card;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            String cardType = getTagValue("CardType", element);
            card = (ActionCard) Class.forName("backend.card." + cardType).getConstructor(Element.class).newInstance(element);
            return card;
        }
        else{
            return null; // change this !!!
        }
    }


    private void updateCategoryList(Element element, Tile tile){
        String color;
        try{
            color = getTagValue("TileColor", element);
        }catch (NullPointerException e){
            return;
        }
        if(color != null){
            if(!propertyCategoryToSpecificListMap.containsKey(color)) {
                propertyCategoryToSpecificListMap.put(color, new ArrayList<>());
            }
            propertyCategoryToSpecificListMap.get(color).add((AbstractPropertyTile)tile);
        }
    }

    private void initializeAdjacencyList(List<Tile> tiles){
        adjacencyList = new HashMap<>();
        for(Tile tile: tiles){
            adjacencyList.put(tile, new ArrayList<>());
            for(int i = 0; i < indexNeighborList.get(tile).size(); i++){
                int j = indexNeighborList.get(tile).get(i);
                for(Tile neighbor : tiles){
                    if(neighbor.getTileIndex() == j) {
                        adjacencyList.get(tile).add(neighbor);
                    }
                }
            }
        }
    }


    public Map<Tile, List<Tile>> getAdjacencyList(){
        return adjacencyList;
    }

    public Map<String, List<AbstractPropertyTile>> getPropertyCategoryMap(){
        return propertyCategoryToSpecificListMap;
    }

    public Bank getBank(){
        return bank;
    }

    public int getNumDie(){
        return numDie;
    }

    public String getMonopolyType() {
        return monopolyType;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public Tile getFirstTile() {
        return firstTile;
    }

    public List<DeckInterface> getDecks(){
        return decks;
    }

}
