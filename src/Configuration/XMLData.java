package Configuration;

import BackEnd.AssetHolder.Bank;
import BackEnd.Board.AbstractBoard;
import BackEnd.Tile.AbstractPropertyTile;
import BackEnd.Tile.TileInterface;
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
import java.util.List;
import java.util.Map;

public class XMLData {

    private Map<TileInterface, List<TileInterface>> adjacencyList;
    private Map<String, List<AbstractPropertyTile>> propertyCategoryToSpecificListMap;
    //private Bank bank;
    private AbstractBoard board;

    public XMLData(String fileName) throws Exception {
        //this.bank = bank;
        File xmlFile = new File(this.getClass().getClassLoader().getResource(fileName).toURI());
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList tileList = doc.getElementsByTagName("Tile");
            //NodeList cardList = doc.getElementsByTagName("Card");

            List<TileInterface> tiles = new ArrayList<>();
            for (int i = 0; i < tileList.getLength(); i++) {
                tiles.add(getTile(tileList.item(i)));
            }
            for (TileInterface tile : tiles) {
                System.out.println(tile);
            }
        }catch(ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace(); //change this !!!
        }
    }

    private TileInterface getTile(Node node) throws Exception {
        TileInterface tile;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String tileType = getTagValue("TileType", element);
            //System.out.println(tileType);
            if(tileType.equalsIgnoreCase("BuildingTile")){
                tile = (TileInterface) Class.forName("BackEnd.Tile." + tileType).getConstructor(Element.class, Bank.class).newInstance(element, bank);
            }
            else tile = (TileInterface) Class.forName("BackEnd.Tile." + tileType).getConstructor(Element.class).newInstance(element);
            return tile;
        }
        else{
            return null; //change this!!
        }
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
