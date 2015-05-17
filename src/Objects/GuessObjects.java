package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by nicolegager on 4/17/15.
 */
public class GuessObjects {

    private String IMG_PATH;
    private String name;
    public BufferedImage img;
    public int x, y, height, width;
    public Rectangle bounds;

    public GuessObjects(String name){

        this.name = name;
        IMG_PATH = System.getProperty("user.dir") + "\\Images\\GuessImages\\" + name + "_sm.png";
        try {
            img = ImageIO.read(new File(IMG_PATH));
            width = img.getWidth();
            height = img.getHeight();
//            x = xLoc;
//            y = yLoc;
            bounds = new Rectangle( x, y, width, height );
            bounds.setBounds( x, y, width, height );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName(){
        return name;
    }

}
