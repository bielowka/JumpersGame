package jumper.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class GuiElementBox {
    Image image;

    public GuiElementBox(Image image) {
        this.image = image;
    }
    public VBox MakeBox() throws FileNotFoundException{
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        VBox box = new VBox(imageView);
        return box;
    }

}
