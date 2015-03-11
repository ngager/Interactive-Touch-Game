package GameScreens;
import org.opencv.core.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by danny on 3/1/15.
 */
public class CLayout extends JFrame{
    // Graphics utils
    DisplayMode dm = new DisplayMode( 1920, 1080, 16, DisplayMode.REFRESH_RATE_UNKNOWN );
    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice vc = env.getDefaultScreenDevice();

    JButton button1 = new JButton("Switch to panel 2");
    JButton button2 = new JButton("Switch to panel 3");
    JButton button3 = new JButton("Switch to panel 1");
    
    // Container panel
    JPanel panelContainer = new JPanel();
    //WelcomeScreen welcomePanel = new WelcomeScreen();
    InstructionDifficultyScreen difficultyPanel = new InstructionDifficultyScreen();
    JPanel panel3 = new JPanel();

    CardLayout cl = new CardLayout();

    public CLayout(){
        panelContainer.setLayout( cl );
        
        panel3.setLayout( new BoxLayout(panel3, BoxLayout.Y_AXIS) );

        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX( Component.CENTER_ALIGNMENT );
        button3.setAlignmentX( Component.CENTER_ALIGNMENT );

        // welcomePanel.add( button1 );
        panel3.add( button3 );

        // welcomePanel.setBackground( Color.RED );
        panel3.setBackground( Color.BLUE);

        /**
         * add each panel to the panelContainer
         */
        // panelContainer.add( welcomePanel, "1" );
        panelContainer.add( difficultyPanel, "2" );
        panelContainer.add( panel3, "3" );

        /**
         * show the starting panel (welcome screen number)
         */
        cl.show( panelContainer, "2");

        /**
         * if we add buttons to this class to it here (probably won't use)
         */
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show( panelContainer, "2" );
            }
        });

        button2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent e ){
                cl.show( panelContainer, "3" );
            }
        });

        button3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent e ){
                cl.show( panelContainer, "2" );
            }
        });

        panelContainer.setSize(1920, 1080);
        this.add(panelContainer);
        setFullScreen( dm );
    }

    // Build a window and convert to full screen
    public void setFullScreen( DisplayMode dm){
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);

        this.setSize( 1920, 1080 );
        vc.setFullScreenWindow( this );

        if( dm != null && vc.isDisplayChangeSupported() ){
            try{
                vc.setDisplayMode( dm );
            }catch(Exception ex){}
        }
    }
    public static void main( String[] args ){
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        new CLayout();
    }
}
