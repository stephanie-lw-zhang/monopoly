package frontend.views;

import exceptions.*;
import frontend.screens.TestingScreen;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.control.*;

import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Represents the View component of the pre-game Form
 * for user input
 *
 * @author Sam
 */
public class FormView extends GridPane {

    private final int                POSSIBLE_PLAYERS = 4; // TODO: READ IN FROM DATA FILE
    private TestingScreen            myScreen;
    private Button                   submitFormButton;
    private Map<TextField, ComboBox> playerToIcon;

    /**
     * FormView main constructor
     *
     * TODO: REFACTOR OUT
     * TODO: STILL NEED MYSCREEN AS INSTANCE VARIABLE???
     * TODO: REFACTOR PLAYERTOICON MAPPING TO TEXTFIELD -> ICONVIEW
     * @param screen
     */
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
                try {
                    handleSubmitFormButton(getPlayerToIconMap());
                } catch (FormInputException e) {
                    e.popUp();
                }
            }
        });
        this.setConstraints( submitFormButton, 0, 6);
        this.getChildren().add( submitFormButton );
    }

    /**
     * Creates a player field for player name entry
     * @param row
     * @return TextField
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
    private void handleSubmitFormButton(Map<TextField, ComboBox> playerToIcon) throws FormInputException {
        if (this.hasUnassignedIcon(playerToIcon)) {
            throw new InputIconMismatchException();
        }
        if (this.hasUnassignedName(playerToIcon)) {
            throw new InputPlayerNameMismatchException();
        }
        if (! this.hasEnoughPlayers()) {
            throw new InsufficientPlayersException();
        }
        if (this.hasDuplicatePlayers() || this.hasDuplicateIcons(playerToIcon)) {
            throw new DuplicatePlayerException();
        }

        // TODO: delete myScreen to gamesetupcontorl
        myScreen.handleStartGameButton(playerToIcon);
    }

    private boolean hasUnassignedIcon(Map<TextField, ComboBox> playerToIcon) {
        for (TextField t : playerToIcon.keySet()) {
            if (! t.getText().equals("") && playerToIcon.get(t).getValue() == null) {
                return true;
            }
        }
        return false;
    }
    private boolean hasUnassignedName(Map<TextField, ComboBox> playerToIcon) {
        for (TextField t : playerToIcon.keySet()) {
            if (t.getText().equals("") && playerToIcon.get(t).getValue() != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks for duplicate icons
     * @param playerToIcon
     * @return
     */
    private boolean hasDuplicateIcons(Map<TextField, ComboBox> playerToIcon) {
        Map<TextField, String> convertedMap = new HashMap<>();
        for (TextField key : playerToIcon.keySet())
            if (! key.getText().equals(""))
                convertedMap.put(key, (String) playerToIcon.get(key).getValue());

        Set<String> set = new HashSet<>(convertedMap.values());

        return convertedMap.values().size() != set.size();
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
     * Limits size of user input to given length of characters
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

    /**
     * Getter for the player to icon mapping
     * @return
     */
    public Map<TextField, ComboBox> getPlayerToIconMap() {
        return playerToIcon;
    }
}
