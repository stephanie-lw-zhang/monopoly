package backend.assetholder;

import javafx.scene.image.ImageView;

public class AutomatedPlayer extends AbstractPlayer {
    public AutomatedPlayer(Double money, ImageView icon) {
        super("CPU", icon, money);
    }
}
