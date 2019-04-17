package FrontEnd.Views;

import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
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
    private int numDie;
    private int numDieStates;

    public DiceView(int nDie, int nDieStates) {
        this.setSpacing(20);
        numDie = nDie;
        numDieStates = nDieStates;

        makeInitialView();
    }

    private void makeInitialView() {
        List<ImageView> diceList = new ArrayList<>();
        for (int i = 0; i < numDie; i++) {
            ImageView diceImg= new ImageView();
            diceImg.setImage(new Image(this
                    .getClass()
                    .getClassLoader()
                    .getResourceAsStream(
                            "dice" + (new Random().nextInt(numDieStates) + 1) + ".png"
                    )
            ));
            diceImg.setFitHeight(50);
            diceImg.setFitWidth(50);

            diceList.add(diceImg);
        }

        this.getChildren().addAll(diceList);
        this.setAlignment(Pos.CENTER_LEFT);
    }

    public void setMyRolls(int[] rolls) {
        myRolls = rolls;
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
