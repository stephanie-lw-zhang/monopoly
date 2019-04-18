package frontend.Views;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormView extends VBox {

    private List<TextField> playerFields;
    private Button startGameButton;

    public FormView() {
        this.setSpacing(10);

        Label headerLabel = new Label("Enter Player Information: ");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label p1Name = new Label("Player 1 Name: ");
        TextField p1Field = new TextField();
        addTextLimiter(p1Field, 25);
        p1Field.setPrefHeight(30);
        p1Field.setMaxWidth(200);

        Label p2Name = new Label("Player 2 Name: ");
        TextField p2Field = new TextField();
        addTextLimiter(p2Field, 25);
        p2Field.setPrefHeight(30);
        p2Field.setMaxWidth(200);

        Label p3Name = new Label("Player 3 Name: ");
        TextField p3Field = new TextField();
        addTextLimiter(p3Field, 25);
        p3Field.setPrefHeight(30);
        p3Field.setMaxWidth(200);

        Label p4Name = new Label("Player 4 Name: ");
        TextField p4Field = new TextField();
        addTextLimiter(p4Field, 25);
        p4Field.setPrefHeight(30);
        p4Field.setMaxWidth(200);


        startGameButton = new Button("START GAME");
        startGameButton.setPrefHeight(20);
        startGameButton.setPrefWidth(150);

        this.getChildren().addAll(
                headerLabel,
                p1Name, p1Field,
                p2Name, p2Field,
                p3Name, p3Field,
                p4Name, p4Field,
                startGameButton
        );

        this.setStyle("-fx-background-color: #FFFFFF;");
        this.setMaxSize(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), 400);
        this.setPadding(new Insets(30, 0, 0, 30));

        playerFields = new ArrayList<>();
        playerFields.add(p1Field);
        playerFields.add(p2Field);
        playerFields.add(p3Field);
        playerFields.add(p4Field);
    }

    public List<TextField> getPlayerFields() {
        return playerFields;
    }

    public Button getStartGameButton() {
        return startGameButton;
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
}
