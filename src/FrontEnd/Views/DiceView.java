package FrontEnd.Views;

import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.List;

public class DiceView extends HBox {

    private int[]             myRolls;
    List<ImageView>           diceImages;
    List<RotateTransition>    rTList;
    private final MediaPlayer diceRollSound = new MediaPlayer(
                                              new Media(
                                                new File("./data/diceRoll.mp3")
                                                .toURI().toString()
                                              ));

    public DiceView(int[] rolls) {
        myRolls = rolls;

    }

    public void playAnimation() {
        playSound();
    }

    private void playSound() {
        diceRollSound.play();
    }
}
