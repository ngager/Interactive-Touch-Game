package Objects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by danny on 3/12/15.
 */
public class DraggableBoat extends JComponent{
    private static final String IMG_PATH = "/Users/danny/Downloads/boat_icon2.png";
    public BufferedImage img;
    ImageIcon icon;
    JLabel label;

    public DraggableBoat(){
        try {
            img = ImageIO.read(new File(IMG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
