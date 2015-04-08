package GameScreens;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by danny on 2/26/15.
 */
public class ResultsScreen extends JPanel implements MouseListener, ActionListener{
    // Path to background image
    Image background = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("hurricane_template_score.png"));
    // BufferedImages to be used as button icons
    BufferedImage buttonIcon1 = null, buttonIcon2 = null, buttonIcon3 = null;
    // Three play game buttons
    JButton button1;

    public ResultsScreen() {
        addMouseListener( this );
        this.setLayout( null );
        // Load the icons for the buttons
        try{
            buttonIcon1 = ImageIO.read(getClass().getClassLoader().getResource("playAgainButton.png"));
        }catch( IOException e ){

        }
        // Make each button, hardcoded to a certain location
        // Locations and sizes were found using Photoshop
        //    * Can result in a problem with changing these screens later on?
        button1 = new JButton(new ImageIcon(buttonIcon1));
        button1.setBorder(BorderFactory.createEmptyBorder());
        button1.setContentAreaFilled(false);
        button1.setBounds(843, 847, 217, 150);
        button1.addActionListener( this );

        this.add( button1 );
    }

    // Right now just starts a game with each button -- later will change difficulty based on button
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button1){

        }
    }

    public JButton getButton( String button ){
        if( button.equals("1"))
            return button1;
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
