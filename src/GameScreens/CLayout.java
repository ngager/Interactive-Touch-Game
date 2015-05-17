package GameScreens;

import org.opencv.core.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;

/**
 * Created by danny on 3/1/15.
 */
public class CLayout extends JFrame {
    String curPanel = "";
    public boolean level1, level2, level3;

    // Graphics utils
    private GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice vc = env.getDefaultScreenDevice();
    public DisplayMode dm = vc.getDisplayMode();

    // CardLayout for the menu system
    public CardLayout cl = new CardLayout();
    // Container panel
    public JPanel panelContainer = new JPanel();

    // Game screens
    private WelcomeScreen welcomePanel;
    private InstructionDifficultyScreen difficultyPanel;
    private ResultsScreen resultsPanel;
    //private GuessingScreen guessingPanel = new GuessingScreen(this, panelContainer);

    // Current directory used for accessing all images
    private String gameDirectory = "";

    public CLayout(){
        System.out.println( "swag" );
        System.out.println( System.getProperty( "user.dir" ) );
        gameDirectory = System.getProperty("user.dir");
        // Make the panels ///////
        welcomePanel = new WelcomeScreen(this, panelContainer);
        difficultyPanel = new InstructionDifficultyScreen(this, panelContainer);
        resultsPanel = new ResultsScreen(this, panelContainer);

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

        // Initialize flags to indicate which level was selected
        level1 = false;
        level2 = false;
        level3 = false;

        panelContainer.setSize(1920, 1080);
        this.add(panelContainer);

        Action reset = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                cl.show(panelContainer, "1");
            }
        };
        new InactivityListener(this, reset, 1).start();

        setFullScreen( dm );
    }

    // Build a window and convert to full screen
    public void setFullScreen( DisplayMode dm ){
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

    // Grab the directory for the image folder we are looking for
    // Using this instead of the resources folder
    public String getDirectory(String folder){
        String path = this.gameDirectory;
        switch( folder ) {
            case "guess":
                path += "\\Images\\GuessImages\\";
                return path;
            case "menu":
                path += "\\Images\\MenuImages\\";
                return path;
            case "scans":
                path += "\\Images\\ScanImages\\";
                return path;
            case "level":
                path += "\\Images\\LevelBackgroundImages\\";
                return path;
            case "":
                path += "\\Images\\";
                return path;
        }
        return path;
    }

    public void passResults( int scores, int total ){
        resultsPanel.setResults( scores, total );
    }

    public static void main( String[] args ){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new CLayout();
    }
}