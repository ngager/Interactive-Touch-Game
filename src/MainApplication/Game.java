package MainApplication;

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

    public Game(CardLayout cl, JPanel panelContainer ){
        // Point to the files you would like to use for the display!
        // To switch levels all you have to do is change these three lines!
        // Don't substring this on a mac
        abovePath = getClass().getClassLoader().getResource("PortsmouthHarborEntrySat.png").getPath().substring(1);
        belowPath = getClass().getClassLoader().getResource("PortsmouthHarborEntryBathy.png").getPath().substring(1);
        maskPath  = getClass().getClassLoader().getResource("PortsmouthHarborEntryMask.png").getPath().substring(1);
        // Create an ImageLoader to load all of the pictures for us.
        imgLoader = new ImageLoader( abovePath, belowPath, maskPath );

        // Grab the current display and graphics device being used
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice vc = env.getDefaultScreenDevice();
        DisplayMode dm = vc.getDisplayMode();
        MainGameScreen w = new MainGameScreen( imgLoader, cl, panelContainer );
        w.run( dm );
    }

    public static void main( String[] args ){
        // Load the openCV library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        //new Game(cl, panelContainer);
    }
}