package MainApplication;

import GameScreens.CLayout;
import GameScreens.MainGameScreen;
import ImageLoading.ImageLoader;
import org.opencv.core.Core;

import javax.swing.*;
import java.lang.String;
import java.awt.*;

//import GameScreens.WelcomeScreen;

/**
 * Created by danny on 11/19/14.
 */
public class Game {
    private String abovePath, belowPath, maskPath, flagPath;
    private ImageLoader imgLoader;

    public Game(CLayout layout, JPanel panelContainer ){
        // Point to the files you would like to use for the display!
        // To switch levels all you have to do is change these three lines!
        abovePath = getClass().getClassLoader().getResource("PortsmouthHarborEntrySat.png").getPath();
        belowPath = getClass().getClassLoader().getResource("PortsmouthHarborEntryBathy.png").getPath();
        maskPath  = getClass().getClassLoader().getResource("PortsmouthHarborEntryMask.png").getPath();
        String os = System.getProperty( ("os.name") );
        if( os.contains("Windows") ){
            abovePath = abovePath.substring(1);
            belowPath = belowPath.substring(1);
            maskPath = maskPath.substring(1);
        }else if( os.contains("Mac") ){
            abovePath = abovePath.substring(0);
            belowPath = belowPath.substring(0);
            maskPath = maskPath.substring(0);
        }

        // Create an ImageLoader to load all of the pictures for us.
        imgLoader = new ImageLoader( abovePath, belowPath, maskPath );

        // Grab the current display and graphics device being used
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice vc = env.getDefaultScreenDevice();
        DisplayMode dm = vc.getDisplayMode();
        MainGameScreen w = new MainGameScreen( imgLoader, layout, panelContainer );
        w.run( dm );
    }

    public static void main( String[] args ){
        // Load the openCV library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    }
}