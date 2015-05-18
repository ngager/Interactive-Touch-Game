package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by danny on 3/12/15.
 * This class reads the image of a boat into a BufferedImage and gives the flag certain properties to easily handle the
 * image in the game.
 */
public class DraggableBoat {

    // Path of the boat image
    private final String IMG_PATH = System.getProperty("user.dir") + "\\Images\\DraggableImages\\draggableBoat.png";

    public BufferedImage img;

    // Boat properties
    public int x, y, height, width;
    public Rectangle bounds;

    public DraggableBoat(int xLoc, int yLoc){

        try {
            img = ImageIO.read(new File(IMG_PATH));
            width = img.getWidth();
            height = img.getHeight();
            x = xLoc;
            y = yLoc;
            bounds = new Rectangle( x, y, width, height );
            bounds.setBounds( x, y, width, height );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
