package MainApplication;

import ScreenUtility.FullScreen;
import ImageLoading.ImageLoader;
import java.awt.*;
import org.opencv.core.*;

/**
 * Created by danny on 11/19/14.
 */
public class Game {
    //Mat destination = Highgui.imread( "/Users/danny/Downloads/above.png", Highgui.CV_LOAD_IMAGE_COLOR);
    private String abovePath, belowPath, maskPath;
    private ImageLoader imgLoader;

    public Game(){
        // Point to the files you would like to use for the display!
        // To switch levels all you have to do is change these three lines!
        abovePath = "/Users/danny/Downloads/above.png";
        belowPath = "/Users/danny/Downloads/below.png";
        maskPath  = "/Users/danny/Downloads/mask.png";
        // Create an ImageLoader to load all of the pictures for us.
        imgLoader = new ImageLoader( abovePath, belowPath, maskPath );

        // Res, res, color bit, refresh rate
        DisplayMode dm = new DisplayMode( 1920, 1080, 16, DisplayMode.REFRESH_RATE_UNKNOWN );
        FullScreen w = new FullScreen( imgLoader );
        w.run( dm );
    }

    public static void main( String[] args ){
        // Load the openCV library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        new Game();
    }
}
