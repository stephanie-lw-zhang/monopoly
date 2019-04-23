package backend.assetholder;

import javafx.scene.image.ImageView;

public class AutomatedPlayer extends AbstractPlayer {
    public AutomatedPlayer(Double money, ImageView icon, Bank bank) {
        super("CPU", icon, money, bank );
    }


}
