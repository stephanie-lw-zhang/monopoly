package frontend.views.game;

import configuration.ImportPropertyFile;
import frontend.views.board.AbstractBoardView;
import frontend.views.board.SquareBoardView;
import frontend.views.player_options.AbstractOptionsView;
import frontend.views.player_options.DiceView;
import frontend.views.player_options.VBoxOptionsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SplitScreenGameView extends AbstractGameView {
    private GridPane myPane;
    private AbstractBoardView myBoardView;
    private AbstractOptionsView myOptionsView;
    private DiceView myDiceView;
//    private JFrame frame;
//    private JOptionPane optionPane;
//    private JDialog dialog;

    public SplitScreenGameView(double screenWidth, double screenHeight, ImportPropertyFile propertyFile){
        super(screenWidth,screenHeight, propertyFile);
        myBoardView = new SquareBoardView(0.9*screenWidth, 0.9*screenHeight,90,11,11,propertyFile);
        myOptionsView = new VBoxOptionsView(this);
        myPane.add(myBoardView.getPane(),0,0);
//        frame = new JFrame("Frame");
    }
    @Override
    public Node getGameViewNode() {
        return myPane;
    }

    @Override
    public void setBoundsForEntireGame(double screenWidth, double screenHeight) {
        myPane = new GridPane();
        myPane.setMaxWidth(screenWidth);
        myPane.setMaxHeight(screenHeight);
    }

    @Override
    public void divideScreen() {
        ColumnConstraints leftCol = new ColumnConstraints();
        leftCol.setPercentWidth(50);
        ColumnConstraints rightCol = new ColumnConstraints();
        rightCol.setPercentWidth(50);
        myPane.getColumnConstraints().addAll(leftCol,rightCol);
    }

    @Override
    public void addPlayerOptionsView() {
        myPane.add(myOptionsView.getOptionsViewNode(),1,0);
    }

    @Override
    public void setTurnActions(List<String> turnActions) {

    }

    @Override
    public String showInputTextDialog(String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        else {
            //TODO: throw exception
            return null;
        }
    }

//https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#stayup
//    @Override
//    public void displayOptionsPopup(Object[] options, String message, String title) {
////        int n = JOptionPane.showOptionDialog(frame,message,title, JOptionPane.YES_NO_OPTION,
////                JOptionPane.QUESTION_MESSAGE,null, options, options[0]);
//
//        optionPane = new JOptionPane(
//                "The only way to close this dialog is by\n"
//                        + "pressing one of the following buttons.\n"
//                        + "Do you understand?",
//                JOptionPane.QUESTION_MESSAGE,
//                JOptionPane.YES_NO_OPTION);
//
//        dialog = new JDialog(frame, title, true);
//        dialog.setContentPane(optionPane);
//        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
//        dialog.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent we) {
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setContentText("Must choose an option.");
//            }
//        });
//        optionPane.addPropertyChangeListener(
//                new PropertyChangeListener() {
//                    public void propertyChange(PropertyChangeEvent e) {
//                        String prop = e.getPropertyName();
//
//                        if (dialog.isVisible()
//                                && (e.getSource() == optionPane)
//                                && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
//                            //If you were going to check something
//                            //before closing the window, you'd do
//                            //it here.
//                            dialog.setVisible(false);
//                        }
//                    }
//                });
//        dialog.pack();
//        dialog.setVisible(true);
//
//        int n = ((Integer)optionPane.getValue()).intValue();
//        if (n == 0) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setContentText("PAY TAX PERCENTAGE");
//        } else if (n == 1) {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setContentText("PAY TAX FIXED");
//        }
//    }

    @Override
    public Node getPane() {
        return myPane;
    }

    public void setPossibleActions(Map<String, EventHandler<ActionEvent>> actionMap){

    }

    public void createOptions(Map<String, EventHandler<ActionEvent>> handlerMap){
        myOptionsView.createButtons(handlerMap);
    }

}
