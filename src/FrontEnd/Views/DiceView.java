package FrontEnd.Views;

import javafx.animation.RotateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.List;
import java.util.Random;

public class DiceView extends HBox {

    private int[]             myRolls;
    List<ImageView>           diceImages;
    List<RotateTransition>    rTList;
    private final MediaPlayer diceRollSound = new MediaPlayer(
                                              new Media(
                                                new File("./data/diceRoll.mp3")
                                                .toURI().toString()
                                              ));

    public DiceView(int numDie) {
        this.setSpacing(20);
//        myRolls = makeInitial
        makeDiceImgList();
    }

    private void makeDiceImgList() {
        for (int i : myRolls) {
            ImageView dView = new ImageView();
            dView.setImage(new Image(this
                    .getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "dice" + i + ".png"
                    )
            ));
            dView.setFitWidth(50);
            dView.setFitHeight(50);

            diceImages.add(dView);
        }
    }

    private void setDice(ImageView dView, final int roll) {
        dView.setImage(new Image(
                this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "dice" + roll + ".png"
                    )
        ));
    }

    public void playAnimation() {
        playSound();
        for (RotateTransition rt : rTList)
            rt.play();
    }

    private void playSound() {
        diceRollSound.play();
    }
}
