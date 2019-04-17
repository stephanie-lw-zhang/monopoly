package Configuration;

import BackEnd.AssetHolder.Bank;
import BackEnd.Board.AbstractBoard;
import BackEnd.Card.AbstractCard;
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
import java.util.List;
import java.util.Map;

public class XMLData {

    private Map<Tile, List<Tile>> adjacencyList;
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

            List<Tile> tiles = new ArrayList<>();
            for (int i = 0; i < tileList.getLength(); i++) {
                tiles.add(getTile(tileList.item(i)));
            }
            for (Tile tile : tiles) {
                System.out.println(tile);
            }
        }catch(ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace(); //change this !!!
        }
    }

    private Tile getTile(Node node) throws Exception {
        Tile tile;
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            String tileType = getTagValue("TileType", element);
            //System.out.println(tileType);
            tile = (Tile) Class.forName("BackEnd.Tile." + tileType).getConstructor(Element.class).newInstance(element);
            return tile;
        }
        else{
            return null; //change this!!
        }
    }

    private AbstractCard getCard(Node node) throws Exception {
        return null;
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
