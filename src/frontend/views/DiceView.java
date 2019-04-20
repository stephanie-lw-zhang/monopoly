package frontend.views;

import controller.Turn;
import javafx.animation.RotateTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceView extends HBox {

    List<ImageView>           diceImages;
    List<RotateTransition>    rTList;
    private final MediaPlayer diceRollSound = new MediaPlayer(
                                              new Media(
                                                new File("./data/diceRoll.mp3")
                                                .toURI().toString()
                                              ));
    private int numDieStates;

    public DiceView(int nDie, int nDieStates) {
        this.setSpacing(20);
        numDieStates = nDieStates;
        diceImages = new ArrayList<>();
        rTList = new ArrayList<>();

        ImageView dice1 = new ImageView();
        dice1.setImage(new Image(this
                .getClass()
                .getClassLoader()
                .getResourceAsStream(
                        "dice" + (new Random().nextInt(numDieStates) + 1) + ".png"
                )
        ));
        dice1.setFitHeight(55);
        dice1.setFitWidth(55);

        ImageView dice2 = new ImageView();
        dice2.setImage(new Image(this
                .getClass()
                .getClassLoader()
                .getResourceAsStream(
                        "dice" + (new Random().nextInt(numDieStates) + 1) + ".png"
                )
        ));
        dice2.setFitHeight(55);
        dice2.setFitWidth(55);

        this.getChildren().addAll(dice1, dice2);
        this.setAlignment(Pos.CENTER_LEFT);

    }

    public void onUpdate(final Turn turn) {
        playDiceAnimation((ObservableList) this.getChildren(), turn.getRolls());
        displayRollsPopup(turn);
    }

    // TODO: MAKE REFLECTION TO MAKE ROTATETRANSITIONS GIVEN DICEVIEWS/ROLLS
    public void playDiceAnimation(List<ImageView> diceImageViews, int[] rolls) {
        playSound();
        RotateTransition rt1 = new RotateTransition(Duration.seconds(1.5), diceImageViews.get(0));
        RotateTransition rt2 = new RotateTransition(Duration.seconds(1.5), diceImageViews.get(1));
        rt1.setFromAngle(0);
        rt1.setToAngle(720);
        rt2.setFromAngle(0);
        rt2.setToAngle(720);
        rt1.setOnFinished(e -> setDice(diceImageViews.get(0), rolls[0]));
        rt2.setOnFinished(e -> setDice(diceImageViews.get(1), rolls[1]));
        rt1.play();
        rt2.play();
    }

    private void setDice(ImageView diceView, final int roll) {
        diceView.setImage(new Image(
                this.getClass()
                        .getClassLoader()
                        .getResourceAsStream(
                                "dice" + roll + ".png"
                        )
        ));
    }

    public void displayRollsPopup(final Turn turn) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("DICE ROLL");
        alert.setContentText("Player " + turn.getMyCurrPlayer().getMyPlayerName()
                + " gets to move " + turn.getNumMoves() + " spots...");
        alert.showAndWait();
    }

    private void playSound() {
        diceRollSound.play();
    }

}
