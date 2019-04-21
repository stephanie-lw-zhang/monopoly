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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormView extends GridPane {

    private List<TextField> playerFields;
    private Button submitFormButton;
    private TestingScreen myScreen;
    private Map<String, String> playersToIcon = new HashMap<String, String>(  );


    public FormView(TestingScreen screen) {
        myScreen = screen;
        this.setHgap(5);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #d32f2f;");
        this.setMaxSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 400);
        this.setPadding(new Insets(20, 20, 20, 100)); //
        this.maxWidthProperty().bind(this.widthProperty());

        this.getColumnConstraints().add(new ColumnConstraints( 200 ) );
        this.getColumnConstraints().add(new ColumnConstraints(200));

//        Label headerLabel = new Label("ENTER GAME INFORMATION: ");
//        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
//        this.setConstraints( headerLabel,0,0 );

        ObservableList<String> options = FXCollections.observableArrayList();
        options.addAll( "icon1", "icon2", "icon3", "icon4" );
        ComboBox<String> comboBox1 = createIconDropDown( options, 1 );
        ComboBox<String> comboBox2 = createIconDropDown( options, 2 );
        ComboBox<String> comboBox3 = createIconDropDown( options, 3 );
        ComboBox<String> comboBox4 = createIconDropDown( options, 4 );


        TextField p1Field = new TextField();
        p1Field.setPromptText("Enter Player Name");
        addTextLimiter(p1Field, 25);
        p1Field.setPrefHeight(30);
        p1Field.setMaxWidth(200);
        this.setConstraints( p1Field, 0, 1 );


        TextField p2Field = new TextField();
        p2Field.setPromptText("Enter Player Name");
        addTextLimiter(p2Field, 25);
        p2Field.setPrefHeight(30);
        p2Field.setMaxWidth(200);
        this.setConstraints( p2Field, 0, 2 );



        TextField p3Field = new TextField();
        p3Field.setPromptText("Enter Player Name");
        addTextLimiter(p3Field, 25);
        p3Field.setPrefHeight(30);
        p3Field.setMaxWidth(200);
        this.setConstraints( p3Field, 0, 3 );


        TextField p4Field = new TextField();
        p4Field.setPromptText("Enter Player Name");
        addTextLimiter(p4Field, 25);
        p4Field.setPrefHeight(30);
        p4Field.setMaxWidth(200);
        this.setConstraints( p4Field, 0, 4 );


        submitFormButton = new Button("START GAME");
        submitFormButton.setPrefHeight(20);
        submitFormButton.setPrefWidth(150);
        submitFormButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleSubmitFormButton(getPlayerFields());
            }
        });
        this.setConstraints( submitFormButton, 1, 6);


        ImageView headerImg = new ImageView();
        headerImg.setImage(new Image("monopopout.png"));
        headerImg.setFitWidth(400);
        headerImg.setPreserveRatio(true);
        headerImg.setSmooth(true);
        headerImg.setCache(true);

        this.getChildren().addAll(
                headerImg,
                p1Field, comboBox1,
                p2Field, comboBox2,
                p3Field, comboBox3,
                p4Field, comboBox4,
                submitFormButton
        );

        playerFields = new ArrayList<>();
        playerFields.add(p1Field);
        playerFields.add(p2Field);
        playerFields.add(p3Field);
        playerFields.add(p4Field);

//        playersToIcon.put( p1Field.getText(), comboBox1.getValue() );
//        playersToIcon.put( p2Field.getText(), comboBox2.getValue() );
//        playersToIcon.put( p3Field.getText(), comboBox3.getValue() );
//        playersToIcon.put( p4Field.getText(), comboBox4.getValue() );
//
//        System.out.println("\n" + "value:" + comboBox1.getValue());

    }

    private ComboBox<String> createIconDropDown(ObservableList<String> options, int i) {
        ComboBox<String> comboBox2 = new ComboBox<>();
        comboBox2.getItems().addAll( options );
        this.setConstraints( comboBox2, 1, i );
        comboBox2.setCellFactory( param -> new CellFactory() );
        comboBox2.setButtonCell( new CellFactory() );
        return comboBox2;
    }

    private void handleSubmitFormButton(List<TextField> playerFields) {
        if (! this.hasEnoughPlayers()) {
            Alert formAlert = new Alert(Alert.AlertType.ERROR);
            formAlert.setContentText("Not enough players signed up! (need >= 2)");
            formAlert.showAndWait();
            return;
        }
        myScreen.handleStartGameButton(playerFields);
    }

    public boolean hasEnoughPlayers() {
        int empties = 0;
        for (TextField p : playerFields)
            if (p.getText().equals(""))
                empties++;
        return empties <= 2;
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

    private List<TextField> getPlayerFields() {
        return playerFields;
    }

    public String getIconFor(String player){
        return playersToIcon.get( player );
    }




}
