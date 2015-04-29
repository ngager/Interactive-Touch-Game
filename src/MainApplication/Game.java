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

        if( layout.level1 ){
            abovePath = getClass().getClassLoader().getResource("PortsmouthHarborEntrySat.png").getPath();
            belowPath = getClass().getClassLoader().getResource("PortsmouthHarborEntryBathy.png").getPath();
            maskPath = getClass().getClassLoader().getResource("PortsmouthHarborEntryMask.png").getPath();
            layout.level1 = false;
        }else if( layout.level2 ){
            abovePath = getClass().getClassLoader().getResource("Level1-Over.png").getPath();
            belowPath = getClass().getClassLoader().getResource("Level1-Under.png").getPath();
            maskPath = getClass().getClassLoader().getResource("Level1-Mask.png").getPath();
            layout.level2 = false;
        }else if( layout.level3 ){
            abovePath = getClass().getClassLoader().getResource("Level3-Over.png").getPath();
            belowPath = getClass().getClassLoader().getResource("Level3-Under.png").getPath();
            maskPath = getClass().getClassLoader().getResource("Level3-Mask.png").getPath();
            layout.level3 = false;
        }

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

        // Create the new game panel!
        MainGameScreen w = new MainGameScreen( imgLoader, layout, panelContainer );
        layout.panelContainer.add(w, "5");
        layout.cl.show(panelContainer, "5");



    }

    public static void main( String[] args ){
        // Load the openCV library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    }
}