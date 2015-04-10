package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by nicolegager on 4/8/15.
 */
public class DebrisFlagger {

    private final String IMG_PATH = getClass().getClassLoader().getResource("foundFlag.png").getPath();
    public BufferedImage img;
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
