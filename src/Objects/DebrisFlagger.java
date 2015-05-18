package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by nicolegager on 4/8/15.
 * This class reads the image of a flag into a BufferedImage and gives the flag certain properties to easily handle the
 * image in the game.
 */
public class DebrisFlagger {

    // Path of the flag image
    private final String IMG_PATH = System.getProperty("user.dir") + "\\Images\\foundFlag.png";

    public BufferedImage img;

    // Flag properties
    public int x, y, height, width;
    public Rectangle bounds;
    public boolean uncovered = false;

    public DebrisFlagger(int xLoc, int yLoc){

        try {
            img = ImageIO.read(new File(IMG_PATH));
            width = img.getWidth();
            height = img.getHeight();
            x = xLoc;
            y = yLoc;
            bounds = new Rectangle( x, y, width, height );
            bounds.setBounds( x, y, width, height );
            uncovered = false;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
