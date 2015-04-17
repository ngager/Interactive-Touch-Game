package GameScreens;

import org.opencv.core.Core;

import javax.swing.*;
import java.awt.*;

/**
 * Created by danny on 3/1/15.
 */
public class CLayout extends JFrame {
    // Graphics utils
    private GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice vc = env.getDefaultScreenDevice();
    private DisplayMode dm = vc.getDisplayMode();

    public CardLayout cl = new CardLayout();
    // Container panel
    private JPanel panelContainer = new JPanel();
    // Game screens
    WelcomeScreen welcomePanel = new WelcomeScreen(cl, panelContainer);
    private InstructionDifficultyScreen difficultyPanel = new InstructionDifficultyScreen(cl, panelContainer);
    private ResultsScreen resultsPanel = new ResultsScreen();
    String curPanel = "";

    public CLayout(){
        panelContainer.setLayout(cl);
        /**
         * add each panel to the panelContainer
         */
        panelContainer.add( welcomePanel, "1" );
        panelContainer.add( difficultyPanel, "2" );
        panelContainer.add( resultsPanel, "3");
        /**
         * show the starting panel (welcome screen number)
         */
        cl.show( panelContainer, "1");
        curPanel = "welcome";

        panelContainer.setSize(1920, 1080);
        this.add(panelContainer);
        setFullScreen( dm );
    }

    // Build a window and convert to full screen
    public void setFullScreen( DisplayMode dm){
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setSize(1920, 1080);
        this.setResizable(false);
        this.setVisible(true);
        vc.setFullScreenWindow(this);

        if( dm != null && vc.isFullScreenSupported() ){
            try{
                vc.setDisplayMode(dm);
            }catch(Exception ex){ex.printStackTrace();}
        }
    }
    public static void main( String[] args ){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new CLayout();
    }
}