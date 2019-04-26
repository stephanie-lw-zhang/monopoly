//package frontend.views;
//
//import controller.CustomController;
//import frontend.screens.RulesScreen;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Node;
//import javafx.scene.control.Button;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.scene.control.Label;
//import javafx.scene.layout.HBox;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;
//
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import java.awt.*;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//public class RulesView {
//    /** Modifiable things
//     * Money: passing go, luxury tax amount, income tax fixed amount, income tax percentage
//     *
//     * Properties: upgrade order, property names, rent price
//     *
//     * Dice: how many sides
//     *
//     *
//     *
//     */
//    private Button submitFormButton;
//    private GridPane myPane;
//    private RulesScreen myScreen;
//    private CustomController myController;
//    private ArrayList<TextField> integerFields = new ArrayList<>(  );
//    private ArrayList<TextField> doubleFields = new ArrayList<>();
//    private TextField playerNum;
//    private TextField passGo;
//    private TextField landGo;
//    private TextField luxuryTax;
//    private TextField incomeTaxFixed;
//    private TextField incomeTaxPercentage;
//    private TextField diceSides;
//    private TextField diceNum;
//    private TextField playerStartMoney;
//
//
//
//    public RulesView(RulesScreen screen){
//        initialize();
//        myScreen = screen;
//    }
//
//    public RulesView(CustomController myController){
//        initialize();
//        this.myController = myController;
//    }
//
//
//    private void initialize(){
//        myPane = new GridPane();
//        myPane.setHgap(5);
//        myPane.setVgap(10);
//        myPane.setAlignment(Pos.CENTER);
//        myPane.setMaxSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 400);
//        myPane.setPadding(new Insets(20, 20, 20, 20));
//        myPane.maxWidthProperty().bind(myPane.widthProperty());
//        myPane.setVgap( 10 );
//
//        myPane.getColumnConstraints().add(new ColumnConstraints( 500 ) );
//
//
//        playerNum = createInputField( "Number of players: ", 0, 0, 25, true);
//        passGo = createInputField( "Pass Go money: ", 0, 1, 25, false);
//        landGo = createInputField( "Land on Go money: ", 0, 2, 25, false);
//        luxuryTax = createInputField( "Luxury Tax amount: ", 0, 3, 25, false);
//        incomeTaxFixed = createInputField( "Income Tax amount (fixed): ", 0, 4, 25, false);
//        incomeTaxPercentage = createInputField( "Income Tax amount (percentage): ", 0, 5, 25, false);
//        diceSides = createInputField( "Number of sides for dice: ", 0, 6, 25, true);
//        diceNum = createInputField( "Number of dice: ", 0, 7, 25, true);
//        playerStartMoney = createInputField( "Player starting money: ", 0, 8, 25, false);
//
//        submitFormButton = new Button("Submit");
//        submitFormButton.setPrefHeight(20);
//        submitFormButton.setPrefWidth(150);
//        submitFormButton.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent actionEvent) {
//                try {
//                    handleSubmitRulesButton();
//                } catch (ParserConfigurationException e) {
//                    e.printStackTrace();
//                } catch (SAXException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        myPane.setConstraints( submitFormButton, 0, 10);
//        myPane.getChildren().add( submitFormButton );
//    }
//
//    public void handleSubmitRulesButton() throws ParserConfigurationException, SAXException, IOException {
//        createCustomXML();
//    }
//
//    public Node getNode() {
//        return myPane;
//    }
//
//
//    private TextField createInputField(String prompt, int col, int row, int maxLength, Boolean returnsInteger) {
//        HBox hb = new HBox();
//        Label label = new Label(prompt);
//        TextField field = new TextField();
//        addTextLimiter( field, maxLength );
//        field.setPrefHeight( 30 );
//        field.setMaxWidth( 200 );
//        hb.getChildren().addAll( label, field );
//        hb.setSpacing(10);
//        myPane.setConstraints( hb, col, row );
//        myPane.getChildren().add(hb);
//        if(returnsInteger){
//            integerFields.add( field );
//        } else {
//            //returns double
//            doubleFields.add( field );
//        }
//        return field;
//    }
//
//    public Document createCustomXML() throws IOException, SAXException, ParserConfigurationException {
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder db = dbf.newDocumentBuilder();
//
//        Document originalDocument = db.parse(new File( "/Users/stephaniezhang/Desktop/School/CS307/monopoly_team05/properties/OriginalMonopoly.xml"));
//        org.w3c.dom.Node originalRoot = originalDocument.getDocumentElement();
//
//        Document copiedDocument = db.newDocument();
//        org.w3c.dom.Node copiedRoot = copiedDocument.importNode(originalRoot, true);
//        copiedDocument.appendChild(copiedRoot);
//        updateXML( copiedDocument );
//        return copiedDocument;
//
//    }
//
//    private void updateXML(Document doc){
//        /*
//        private TextField playerNum;
//    private TextField passGo;
//    private TextField landGo;
//    private TextField luxuryTax;
//    private TextField incomeTaxFixed;
//    private TextField incomeTaxPercentage;
//    private TextField diceSides;
//    private TextField diceNum;
//    private TextField playerStartMoney;
//         */
//        updateNode("NumDie", (Element) doc.getElementsByTagName("Dice").item(0), String.valueOf(diceNum.getText()));
//
//        updateNode("NumDie", (Element) doc.getElementsByTagName("Dice").item(0), String.valueOf(diceNum.getText()));
//    }
//
//    private void updateNode(String tag, Element element, String value) {
//        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
//        org.w3c.dom.Node node = nodeList.item(0);
//        node.setNodeValue( value );
//    }
//
//    public void addTextLimiter(final TextField tf, final int maxLength) {
//        tf.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
//                if (tf.getText().length() > maxLength) {
//                    String s = tf.getText().substring(0, maxLength);
//                    tf.setText(s);
//                }
//            }
//        });
//    }
//
//}
