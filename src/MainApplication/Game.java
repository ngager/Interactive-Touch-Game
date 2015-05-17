package MainApplication;

import GameScreens.CLayout;
import GameScreens.MainGameScreen;
import ImageLoading.ImageLoader;
import org.opencv.core.Core;

import javax.swing.*;
import java.lang.String;
import java.awt.*;
import java.net.URISyntaxException;

/**
 * Created by danny on 11/19/14.
 */
public class Game {
    private String abovePath, belowPath, maskPath;
    private ImageLoader imgLoader;

    public Game(CLayout layout, JPanel panelContainer ){
        // Point to the files you would like to use for the display
        // To switch levels all you have to do is change these three lines
        String dir = layout.getDirectory("level");
        if( layout.level1 ){
            abovePath = dir + "Level1-Over.png";
            belowPath = dir + "Level1-Under.png";
            maskPath = dir + "Level1-Mask.png";
            layout.level1 = false;
        }else if( layout.level2 ){
            abovePath = dir + "Level2-Over.png";
            belowPath = dir + "Level2-Under.png";
            maskPath =  dir + "Level2-Mask.png";
            layout.level2 = false;
        }else if( layout.level3 ){
            abovePath =  dir + "Level3-Over.png";
            belowPath =  dir + "Level3-Under.png";
            maskPath =   dir + "Level3-Mask.png";
            layout.level3 = false;
        }

        // Before, I had to substring the filepath differently while working on OS X.
        // Currently I do not know if I have to do that with the new version
        //        String os = System.getProperty( ("os.name") );
        //        if( os.contains("Windows") ){
        //            abovePath = abovePath.substring(1);
        //            belowPath = belowPath.substring(1);
        //            maskPath = maskPath.substring(1);
        //        }else if( os.contains("Mac") ){
        //            abovePath = abovePath.substring(0);
        //            belowPath = belowPath.substring(0);
        //            maskPath = maskPath.substring(0);
        //        }

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