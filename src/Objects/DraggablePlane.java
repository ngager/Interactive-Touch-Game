package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by danny on 3/12/15.
 */
public class DraggablePlane {
    private final String IMG_PATH = System.getProperty("user.dir") + "\\Images\\DraggableImages\\draggablePlane.png";
    public BufferedImage img;
    public int x, y, height, width;
    public Rectangle bounds;

    public DraggablePlane(int xLoc, int yLoc){
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
