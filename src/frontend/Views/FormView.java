package frontend.Views;

import frontend.Screens.AbstractScreen;
import frontend.Screens.TestingScreen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormView extends VBox {

    private List<TextField> playerFields;
    private Button submitFormButton;
    private TestingScreen myScreen;

    public FormView(TestingScreen screen) {
        myScreen = screen;
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #d32f2f;");
        this.setMaxSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 400);
        this.setPadding(new Insets(0, 20, 20, 20)); //
        this.maxWidthProperty().bind(this.widthProperty());

        Label headerLabel = new Label("ENTER GAME INFORMATION: ");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        TextField p1Field = new TextField();
        p1Field.setPromptText("Enter Player Name");
        addTextLimiter(p1Field, 25);
        p1Field.setPrefHeight(30);
        p1Field.setMaxWidth(200);

        TextField p2Field = new TextField();
        p2Field.setPromptText("Enter Player Name");
        addTextLimiter(p2Field, 25);
        p2Field.setPrefHeight(30);
        p2Field.setMaxWidth(200);

        TextField p3Field = new TextField();
        p3Field.setPromptText("Enter Player Name");
        addTextLimiter(p3Field, 25);
        p3Field.setPrefHeight(30);
        p3Field.setMaxWidth(200);

        TextField p4Field = new TextField();
        p4Field.setPromptText("Enter Player Name");
        addTextLimiter(p4Field, 25);
        p4Field.setPrefHeight(30);
        p4Field.setMaxWidth(200);

        submitFormButton = new Button("START GAME");
        submitFormButton.setPrefHeight(20);
        submitFormButton.setPrefWidth(150);
        submitFormButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                handleSubmitFormButton(getPlayerFields());
            }
        });

        ImageView headerImg = new ImageView();
        headerImg.setImage(new Image("monopopout.png"));
        headerImg.setFitWidth(400);
        headerImg.setPreserveRatio(true);
        headerImg.setSmooth(true);
        headerImg.setCache(true);

        this.getChildren().addAll(
                headerImg, headerLabel,
                p1Field, p2Field,
                p3Field, p4Field,
                submitFormButton
        );

        playerFields = new ArrayList<>();
        playerFields.add(p1Field);
        playerFields.add(p2Field);
        playerFields.add(p3Field);
        playerFields.add(p4Field);
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

}
