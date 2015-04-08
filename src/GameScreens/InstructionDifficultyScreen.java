package GameScreens;

import MainApplication.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * Created by danny vasta on 2/26/15.
 */
public class InstructionDifficultyScreen extends JPanel implements MouseListener{
    // Path to background image
    Image background = Toolkit.getDefaultToolkit().createImage( getClass().getClassLoader().getResource("hurricane_template_instructions.png") );
    // BufferedImages to be used as button icons
    BufferedImage buttonIcon1 = null, buttonIcon2 = null, buttonIcon3 = null;
    // THree play game buttons
    JButton button1, button2, button3;
    JPanel panelContainer;
    CardLayout cl;
    Rectangle r1, r2, r3;

    public InstructionDifficultyScreen(CardLayout cl, JPanel panelContainer) {
        this.cl = cl;
        this.panelContainer = panelContainer;

        addMouseListener( this );
        this.setLayout( null );
//        // Load the icons for the buttons
//        try{
//            buttonIcon1 = ImageIO.read( getClass().getClassLoader().getResourceAsStream("level1Button.png") );
//            buttonIcon2 = ImageIO.read( getClass().getClassLoader().getResourceAsStream("level2Button.png") );
//            buttonIcon3 = ImageIO.read( getClass().getClassLoader().getResourceAsStream("level3Button.png") );
//        }catch( IOException e ){
//
//        }
//        // Make each button, hardcoded to a certain location
//        // Locations and sizes were found using Photoshop
//        //    * Can result in a problem with changing these screens later on?
//        button1 = new JButton(new ImageIcon(buttonIcon1));
//        button1.setBorder(BorderFactory.createEmptyBorder());
//        button1.setContentAreaFilled(false);
//        button1.setBounds(1650, 400, 217, 150);
//        button1.addActionListener( this );
//
//        button2 = new JButton(new ImageIcon(buttonIcon2));
//        button2.setBorder(BorderFactory.createEmptyBorder());
//        button2.setContentAreaFilled(false);
//        button2.setBounds(1650, 625, 217, 150);
//        button2.addActionListener(this);
//
//        button3 = new JButton(new ImageIcon(buttonIcon3));
//        button3.setBorder(BorderFactory.createEmptyBorder());
//        button3.setContentAreaFilled(false);
//        button3.setBounds( 1650, 846, 217, 150 );
//        button3.addActionListener( this );
//
//        this.add( button1 );
//        this.add( button2 );
//        this.add( button3 );
    }
    // Very fast and easy way to set a background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
      //  g.setColor( Color.BLACK );
      //  g.fillRect( 1695, 415, (1820-1695), (540-415) );
    }
//    // Don't even need to check mouse click coordinates because the buttons take care of it
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // Level 1
        if( (x >= 1695 && x <= 1820) && (y >= 415 && y <= 540) ){
            new Game();
        }
        // Level 2
        else if( (x >= 1695 && x <= 1820) && (y >= 632 && y <= 757) ){
            new Game();
        }
        // Level 3
        else if( (x >= 1695 && x <= 1820) && (y >= 859 && y <= 984) ){
            new Game();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println( "RELEASE!" );
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
