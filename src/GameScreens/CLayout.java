package GameScreens;
import org.opencv.core.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by danny on 3/1/15.
 */
public class CLayout extends JFrame{
    // Graphics utils
    private DisplayMode dm = new DisplayMode( 1920, 1080, 16, DisplayMode.REFRESH_RATE_UNKNOWN );
    private GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private GraphicsDevice vc = env.getDefaultScreenDevice();

    public CardLayout cl = new CardLayout();
    // Container panel
    private JPanel panelContainer = new JPanel();
    WelcomeScreen welcomePanel = new WelcomeScreen(cl, panelContainer);
    private InstructionDifficultyScreen difficultyPanel = new InstructionDifficultyScreen();
    private ResultsScreen resultsPanel = new ResultsScreen();
    private JButton button1 = difficultyPanel.getButton( "1" );
    private JButton button2 = resultsPanel.getButton( "1" );

    public CLayout(){
        panelContainer.setLayout(cl);
        /**
         * add each panel to the panelContainer
         */
        panelContainer.add( welcomePanel, "1" );
        panelContainer.add( difficultyPanel, "2" );
        panelContainer.add(resultsPanel, "3");
        /**
         * show the starting panel (welcome screen number)
         */
        cl.show( panelContainer, "1");
        /**
         * if we add buttons to this class to it here (probably won't use)
         */
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show( panelContainer, "3" );
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show( panelContainer, "2" );
            }
        });

        panelContainer.setSize(1920, 1080);
        this.add(panelContainer);
        setFullScreen( dm );
    }

    public JPanel getPanelContainer(){
        return panelContainer;
    }

    // Build a window and convert to full screen
    public void setFullScreen( DisplayMode dm){
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setSize( 1920, 1080 );
        vc.setFullScreenWindow(this);

        if( dm != null && vc.isDisplayChangeSupported() ){
            try{
                vc.setDisplayMode(dm);
            }catch(Exception ex){}
        }
    }

    public static void main( String[] args ){
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        new CLayout();
    }
}
