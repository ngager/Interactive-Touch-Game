package Objects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by nicolegager on 4/17/15.
 */
public class ScanObjects {

    private String IMG_PATH;
    private String name;
    private int key;
    public BufferedImage img;
    public int x, y, height, width;
    public Rectangle bounds;

    public ScanObjects(String name, int num){

        this.name = name;
        this.key = num;
        IMG_PATH = name;
        System.out.println( IMG_PATH );
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
