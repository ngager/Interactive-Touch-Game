package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by nicolegager on 4/17/15.
 * This class takes images and saves them as GuessObjects with properties that help to maneuver them in other classes.
 */
public class GuessObjects {

    public BufferedImage img;
    private String IMG_PATH;
    private String name;
    private int key;
    public int x, y, height, width;
    public Rectangle bounds;

    // GuessObjects takes a String (a path) and saves it as the name of the object as well as uses it to specify the
    // object's image path. A key is saved as a tag and is a portion of the path that is used to check for matches.
    public GuessObjects(String name, int num){

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
