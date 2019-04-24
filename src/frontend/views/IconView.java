package frontend.views;


import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconView {

    private ImageView myImageView;

    public IconView(ImageView icon){
        myImageView = icon;
    }

    public Node getMyNode(){
        return  myImageView;
    }

    public void setHeight(double v) {
        myImageView.setFitHeight(v);
    }

    public void setWidth(double v) {
        myImageView.setFitWidth(v);
    }
}
