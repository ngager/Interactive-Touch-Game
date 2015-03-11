package GameScreens;

import MainApplication.Game;
import org.opencv.core.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nicolegager on 2/26/15.
 */
public class InstructionDifficultyScreen extends JPanel{

    JButton button1, button2, button3;

    public InstructionDifficultyScreen() {
        //this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setLayout( new BorderLayout() );
//        button1 = new JButton( "Easy" );
//        button2 = new JButton( "Medium" );
//        button3 = new JButton( "Hard" );
//
//        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
//        button2.setAlignmentX( Component.CENTER_ALIGNMENT );
//        button3.setAlignmentX( Component.CENTER_ALIGNMENT );
//
//        this.add(button1);
//        this.add(Box.createVerticalStrut(200));
//        this.add(button2);
//        this.add(Box.createVerticalStrut(200));
//        this.add(button3);
//
//        button1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new Game();
//            }
//        });
//
//        button2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new Game();
//            }
//        });
//
//        button3.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                new Game();
//            }
//        });
//
//        this.add( button1 );
//        this.add( button2 );
//        this.add( button3 );
        ImageIcon image = new ImageIcon("Users/danny/Downloads/hurricane_instructions.png");
        JLabel label = new JLabel("", image, JLabel.CENTER);
        this.add( label, BorderLayout.CENTER );
    }

    public static void main( String[] args ){
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        //DisplayMode dm = new DisplayMode( 1920, 1080, 16, DisplayMode.REFRESH_RATE_UNKNOWN );
        new InstructionDifficultyScreen();
        //s.run(dm);
    }
}
