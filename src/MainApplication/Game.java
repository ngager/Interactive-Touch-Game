package MainApplication;

import GameScreens.MainGameScreen;
import ImageLoading.ImageLoader;
import org.opencv.core.Core;

import java.awt.*;

//import GameScreens.WelcomeScreen;

/**
 * Created by danny on 11/19/14.
 */
public class Game {
    private String abovePath, belowPath, maskPath, flagPath;
    private ImageLoader imgLoader;

    public Game(){
        // Point to the files you would like to use for the display!
        // To switch levels all you have to do is change these three lines!
        abovePath = getClass().getClassLoader().getResource("PortsmouthHarborEntrySat.png").getPath();
        belowPath = getClass().getClassLoader().getResource("PortsmouthHarborEntryBathy.png").getPath();
        maskPath  = getClass().getClassLoader().getResource("PortsmouthHarborEntryMask.png").getPath();
        // Create an ImageLoader to load all of the pictures for us.
        imgLoader = new ImageLoader( abovePath, belowPath, maskPath );
        // Res, res, color bit, refresh rate
        DisplayMode dm = new DisplayMode( 1920, 1080, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN );
        MainGameScreen w = new MainGameScreen( imgLoader );
        w.run( dm );
    }

    public static void main( String[] args ){
        // Load the openCV library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        new Game();
    }
}