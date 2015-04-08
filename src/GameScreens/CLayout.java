package GameScreens;

import org.opencv.core.Core;

import javax.swing.*;
import java.awt.*;

/**
 * Created by danny on 3/1/15.
 */
public class CLayout extends JFrame {
    // Graphics utils
    private DisplayMode dm = new DisplayMode( 1920, 1080, DisplayMode.BIT_DEPTH_MULTI, DisplayMode.REFRESH_RATE_UNKNOWN );
    private GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice vc = env.getDefaultScreenDevice();

    public CardLayout cl = new CardLayout();
    // Container panel
    private JPanel panelContainer = new JPanel();
    WelcomeScreen welcomePanel = new WelcomeScreen(cl, panelContainer);
    private InstructionDifficultyScreen difficultyPanel = new InstructionDifficultyScreen(cl, panelContainer);
    private ResultsScreen resultsPanel = new ResultsScreen();
  //  private JButton button1 = difficultyPanel.getButton( "1" );
  //  private JButton button2 = resultsPanel.getButton( "1" );
  //  private JTextField inputField = welcomePanel.getTextField();
    String curPanel = "";

    public CLayout(){
        //dm = vc.getDisplayModes()[0];
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
        /**
         * if we add buttons to this class to it here (probably won't use)
         */
//        button1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cl.show( panelContainer, "3" );
//                curPanel = "results";
//            }
//        });
//
//        button2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cl.show( panelContainer, "2" );
//                curPanel = "difficulty";
//            }
//        });

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

//    @Override
//    public void mouseClicked(MouseEvent e) {
//        switch( curPanel ){
//            case "welcome":
//                System.out.println( "welcome" );
//                break;
//            case "difficulty":
//                System.out.println( "difficulty/instructions" );
//                break;
//            case "results":
//                System.out.println( "results" );
//                break;
//        }
//    }
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//
//    }
}