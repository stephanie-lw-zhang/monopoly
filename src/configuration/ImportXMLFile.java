package configuration;


public class ImportXMLFile {

    public static void main(String args[]) {

        try{
            XMLData data = new XMLData("OriginalMonopoly.xml");
        }catch(Exception e){
            e.printStackTrace();
        }
        /**
        try {
            File xmlFile = new File("");
            File xmlFile = new File("C:\\Users\\Matt Rose\\CS307\\monopoly_team05\\properties\\OriginalMonopoly.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("tile");
            System.out.println("----------------------------");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    System.out.println("tile Type : " + eElement.getAttribute("TileType"));
                    System.out.println("TileNumber : " + eElement.getElementsByTagName("TileNumber").item(0).getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         */
    }

}

