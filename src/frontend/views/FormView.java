package frontend.views;

import frontend.screens.TestingScreen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.*;
import java.util.List;

public class FormView extends GridPane {

    private Button submitFormButton;
    private TestingScreen myScreen;
    private final int POSSIBLE_PLAYERS = 4; // TODO: READ IN FROM DATA FILE
    private Map<TextField, ComboBox> playerToIcon;

    public FormView(TestingScreen screen) {
        myScreen = screen;
        this.setHgap(5);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #d32f2f;");
        this.setMaxSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 400);
        this.setPadding(new Insets(20, 20, 20, 20));
        this.maxWidthProperty().bind(this.widthProperty());

         this.getColumnConstraints().add(new ColumnConstraints( 200 ) );
         this.getColumnConstraints().add(new ColumnConstraints(200));

//        Label headerLabel = new Label("ENTER GAME INFORMATION: ");
//        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
//        this.setConstraints( headerLabel,0,0 );

        ImageView headerImg = new ImageView();
        headerImg.setImage(new Image("monopopout.png"));
        headerImg.setFitWidth(400);
        headerImg.setPreserveRatio(true);
        headerImg.setSmooth(true);
        headerImg.setCache(true);

        this.getChildren().add( headerImg );

        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll("icon1", "icon2", "icon3", "icon4",
                "icon5", "icon6", "icon7", "icon8"
        );

        playerToIcon = new HashMap<>();

        for (int i = 1; i <= POSSIBLE_PLAYERS; i++) {
            ComboBox<String> comboBox = createIconDropDown( options, i );
            TextField pField = createPlayerTextField( i );
            this.getChildren().addAll( comboBox, pField );
            playerToIcon.put( pField, comboBox );
        }

        submitFormButton = new Button("START GAME");
        submitFormButton.setPrefHeight(20);
        submitFormButton.setPrefWidth(150);
        submitFormButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                handleSubmitFormButton(getPlayerToIconMap());
            }
        });
        this.setConstraints( submitFormButton, 0, 6);
        this.getChildren().add( submitFormButton );
    }

    /**
     * Creates a player field for player name entry
     * @param row
     * @return TextField,
     */
    private TextField createPlayerTextField(int row) {
        TextField pField = new TextField();
        pField.setPromptText( "Enter Player Name" );
        addTextLimiter( pField, 25 );
        pField.setPrefHeight( 30 );
        pField.setMaxWidth( 200 );
        this.setConstraints( pField, 0, row );
        return pField;
    }

    /**
     * Creates the Dropdown of Icons to choose from
     * @param options
     * @param row
     * @return ComboBox
     */
    private ComboBox<String> createIconDropDown(ObservableList<String> options, int row) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll( options );
        this.setConstraints( comboBox, 1, row );
        comboBox.setCellFactory( param -> new CellFactory() );
        comboBox.setButtonCell( new CellFactory() );
        return comboBox;
    }

    /**
     * Alerts if not enough players have signed up, or if
     * enough have signed up, calls for start of game
     * @param playerToIcon
     */
    private void handleSubmitFormButton(Map<TextField, ComboBox> playerToIcon) {
        // cleanPlayerIconMap();
        if (! this.hasEnoughPlayers()) {
            Alert formAlert = new Alert(Alert.AlertType.ERROR);
            formAlert.setContentText("Not enough players signed up! (need >= 2)");
            formAlert.showAndWait();
            return;
        }
        if (this.hasDuplicatePlayers()) {
            Alert formAlert = new Alert(Alert.AlertType.ERROR);
            formAlert.setContentText("Duplicate player names not allowed!");
            formAlert.showAndWait();
            return;
        }
//        if (this.hasDuplicateIcons(playerToIcon)) {
//            Alert formAlert = new Alert(Alert.AlertType.ERROR);
//            formAlert.setContentText("Duplicate icons not allowed!");
//            formAlert.showAndWait();
//            return;
//        }
        // TODO: delete myScreen to gamesetupcontorl
        myScreen.handleStartGameButton(playerToIcon);
    }

    /**
     * Clears playerIconMap of empty entries
     * so that duplication errors can be
     * accurately detected
     */
    private void cleanPlayerIconMap() {
        for (TextField t : playerToIcon.keySet()) {
            if (t.getText().equals("")) {
                playerToIcon.remove(t);
            }
        }
    }

    private boolean hasDuplicateIcons(Map<TextField, ComboBox> playerToIcon) {
        Set<String> set = new HashSet<>();
        for (TextField t : playerToIcon.keySet()) {
            set.add((String) playerToIcon.get(t).getValue());
        }
        System.out.println(playerToIcon.keySet().size());
        System.out.println(set.size());
        return playerToIcon.keySet().size() != set.size();
    }

    /**
     * Checks if there are enough players entered in
     * the form before the start of a game
     * @return boolean, whether or not enough players have been entered
     */
    private boolean hasEnoughPlayers() {
        int empties = 0;
        for (TextField p : playerToIcon.keySet())
            if (p.getText().equals(""))
                empties++;
        return empties <= 2;
    }

    /**
     * Checks if there exists duplicate player names
     * in the form at start of the game
     * @return boolean, whether duplicate names exist
     */
    private boolean hasDuplicatePlayers() {
        Set<String> set = new HashSet<>();
        List<String> list = new ArrayList<>();
        for (TextField p : playerToIcon.keySet()) {
            if (! p.getText().equals("")) {
                set.add(p.getText());
                list.add(p.getText());
            }
        }

        return set.size() != list.size();
    }

    /**
     * Limits size of user input
     * @param tf
     * @param maxLength
     */
    public void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    public Map<TextField, ComboBox> getPlayerToIconMap(){
        return playerToIcon;
    }

}
