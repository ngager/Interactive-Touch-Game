package MainApplication;

import ImageLoading.ImageLoader;
import ScreenUtility.FullScreen;
import org.opencv.core.Core;

import java.awt.*;

/**
 * Created by danny on 11/19/14.
 */
public class Game {
    private String abovePath, belowPath, maskPath;
    private ImageLoader imgLoader;

    public Game(){
        // Point to the files you would like to use for the display!
        // To switch levels all you have to do is change these three lines!
        abovePath = "/Users/nicolegager/Downloads/above.png";
        belowPath = "/Users/nicolegager/Downloads/below.png";
        maskPath  = "/Users/nicolegager/Downloads/mask.png";

        // Create an ImageLoader to load all of the pictures for us.
        imgLoader = new ImageLoader( abovePath, belowPath, maskPath );

        // Res, res, color bit, refresh rate
        DisplayMode dm = new DisplayMode( 1920, 1080, 16, DisplayMode.REFRESH_RATE_UNKNOWN );
        FullScreen w = new FullScreen( imgLoader );

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        System.out.println( width + " x " + height );

        w.run( dm );
    }

    public static void main( String[] args ){
        // Load the openCV library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        new Game();
    }
}
