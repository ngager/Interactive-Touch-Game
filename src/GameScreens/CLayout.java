package GameScreens;
import ImageLoading.ImageLoader;
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

    JPanel panelCont = new JPanel();
 //   WelcomeScreen welcomePanel = new WelcomeScreen();
    DifficultyScreen panel2 = new DifficultyScreen();
    JPanel panel3 = new JPanel();
    JPanel buttonPanel = new JPanel();
    JButton button1 = new JButton("Switch to panel 2");
    JButton button2 = new JButton("Switch to panel 3");
    JButton button3 = new JButton("Switch to panel 1");
    CardLayout cl = new CardLayout();

    public CLayout(){
        panelCont.setLayout( cl );
        
        panel3.setLayout( new BoxLayout(panel3, BoxLayout.Y_AXIS) );

        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX( Component.CENTER_ALIGNMENT );
        button3.setAlignmentX( Component.CENTER_ALIGNMENT );

//        welcomePanel.add( button1 );
        panel2.add( button2 );
        panel3.add( button3 );

        buttonPanel.setBackground( Color.CYAN );
 //       welcomePanel.setBackground( Color.RED );
        panel2.setBackground( Color.GREEN);
        panel3.setBackground( Color.BLUE);

        panelCont.add( buttonPanel, "0");
 //       panelCont.add( welcomePanel, "1" );
        panelCont.add( panel2, "2" );
        panelCont.add( panel3, "3" );

        cl.show( panelCont, "1");

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show( panelCont, "2" );
            }
        });

        button2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent e ){
                cl.show( panelCont, "3" );
            }
        });

        button3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed( ActionEvent e ){
                cl.show( panelCont, "1" );
            }
        });

        panelCont.setSize(1920, 1080);
        this.add(panelCont);

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

        CLayout s = new CLayout();
    }
}
