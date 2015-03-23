package GameScreens;

import MainApplication.Game;
import org.opencv.core.Core;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by danny vasta on 2/26/15.
 */
public class InstructionDifficultyScreen extends JPanel implements MouseListener, ActionListener{
    // Path to background image
    Image background = Toolkit.getDefaultToolkit().createImage( getClass().getClassLoader().getResource("hurricane_instructions.png") );
    // BufferedImages to be used as button icons
    BufferedImage buttonIcon1 = null, buttonIcon2 = null, buttonIcon3 = null;
    // THree play game buttons
    JButton button1, button2, button3;

    public InstructionDifficultyScreen() {
        addMouseListener( this );
        this.setLayout( null );
        // Load the icons for the buttons
        try{
            buttonIcon1 = ImageIO.read( getClass().getClassLoader().getResourceAsStream("level1Button.png") );
            buttonIcon2 = ImageIO.read( getClass().getClassLoader().getResourceAsStream("level2Button.png") );
            buttonIcon3 = ImageIO.read( getClass().getClassLoader().getResourceAsStream("level3Button.png") );
        }catch( IOException e ){

        }
        // Make each button, hardcoded to a certain location
        // Locations and sizes were found using Photoshop
        //    * Can result in a problem with changing these screens later on?
        button1 = new JButton(new ImageIcon(buttonIcon1));
        button1.setBorder(BorderFactory.createEmptyBorder());
        button1.setContentAreaFilled(false);
        button1.setBounds(1650, 400, 217, 150);
        button1.addActionListener( this );

        button2 = new JButton(new ImageIcon(buttonIcon2));
        button2.setBorder(BorderFactory.createEmptyBorder());
        button2.setContentAreaFilled(false);
        button2.setBounds(1650, 625, 217, 150);
        button2.addActionListener(this);

        button3 = new JButton(new ImageIcon(buttonIcon3));
        button3.setBorder(BorderFactory.createEmptyBorder());
        button3.setContentAreaFilled(false);
        button3.setBounds( 1650, 846, 217, 150 );
        button3.addActionListener( this );

        this.add( button1 );
        this.add( button2 );
        this.add( button3 );
    }

    // Right now just starts a game with each button -- later will change difficulty based on button
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button1){
            //new Game();
        }else if( e.getSource() == button2){
            new Game();
        }else if( e.getSource() == button3){
            new Game();
        }
    }

    public JButton getButton( String button ){
        if( button.equals("1"))
            return button1;
        else if( button.equals("2"))
            return button2;
        else if( button.equals("3"))
            return button3;
        return null;
    }

    // Very fast and easy way to set a background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    // Don't even need to check mouse click coordinates because the buttons take care of it
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
