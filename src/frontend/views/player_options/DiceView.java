package frontend.views.player_options;

import controller.Turn;

import javafx.scene.media.MediaPlayer;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

import javafx.collections.ObservableList;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.io.File;

import javafx.animation.RotateTransition;
import javafx.geometry.Pos;

/**
 * This class represents the View of a Dice or Die in the game
 *
 * @author Sam
 */
public class DiceView extends HBox {

    private final MediaPlayer         diceRollSound = new MediaPlayer(
                                                      new Media(new File("./data/diceRoll.mp3")
                                                                                    .toURI().toString()));
    private List<RotateTransition>    rTList;
    private List<ImageView>           myDiceIcons;
    private String                    myPopupText;
    private int                       myNumDieStates;
    private int                       myNumDie;

    /**
     * DiceView main constructor
     * @param nDie
     * @param nDieStates
     */
    public DiceView(int nDie, int nDieStates) {
        this.setSpacing(20);
        myNumDieStates = nDieStates;
        myNumDie = nDie;
        myDiceIcons = makeDiceIcons();
        rTList = new ArrayList<>();

        this.getChildren().addAll(myDiceIcons);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    private List<ImageView> makeDiceIcons() {
        List<ImageView> list = new ArrayList<>();

        for (int i = 0; i < myNumDie; i++) {
            ImageView icon = new ImageView(
                    new Image(this.getClass().getClassLoader().getResourceAsStream(
                            "dice" + (new Random().nextInt(myNumDieStates) + 1) + ".png"
                    ))
            );
            icon.setFitWidth(55);
            icon.setFitHeight(55);

            list.add(icon);
        }

        return list;
    }

    /**
     * Specifies what actions occur on update (i.e. after a roll)
     * @param turn
     */
    public void onUpdate(final Turn turn) {
        playDiceAnimation((List) this.getChildren(), turn.getRolls());
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
        myPopupText = "Player " + turn.getMyCurrPlayer().getMyPlayerName() + " gets to move " + turn.getNumMoves() + " spots...";
        alert.setContentText(myPopupText);
        alert.showAndWait();
    }

    private void playSound() {
        diceRollSound.play();
    }

    public String getMyPopupText() {
        return myPopupText;
    }
}
