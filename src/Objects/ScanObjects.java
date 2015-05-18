package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by nicolegager on 4/17/15.
 * This class takes images and saves them as ScanObjects with properties that help to maneuver them in other classes.
 */
public class ScanObjects {

    private String IMG_PATH;
    private String name;
    private int key;
    public BufferedImage img;
    public int x, y, height, width;
    public Rectangle bounds;

    // ScanObjects takes a String (a path) and saves it as the name of the object as well as uses it to specify the
    // object's image path. A key is saved as a tag and is a portion of the path that is used to check for matches.
    public ScanObjects(String name, int num){

        this.name = name;
        this.key = num;
        IMG_PATH = name;

        // System.out.println( IMG_PATH );
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

    public int getKey() {
        return key;
    }
}
