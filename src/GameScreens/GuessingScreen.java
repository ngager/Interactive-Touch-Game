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
public class GuessingScreen extends JPanel implements MouseListener{
    // Path to background image
    Image background = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("hurricane_template_guessing.png"));
    CardLayout cl;
    JPanel panelContainer;
    int imageX = 1295;
    int image1Y = 162, image2Y = 380, image3Y = 598, image4Y = 816;
    public GuessingScreen(CardLayout cl, JPanel panelContainer) {
        addMouseListener( this );
        this.setLayout( null );
        this.cl = cl;
        this.panelContainer = panelContainer;

    }

    // Very fast and easy way to set a background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    // Don't even need to check mouse click coordinates because the buttons take care of it
    @Override
    public void mouseClicked(MouseEvent e) {
        int curX = e.getX();
        int curY = e.getY();

        if( curX > imageX && curX < (imageX+200) ){
            // BUTTON 1
            if( curY > image1Y && curY < (image1Y+200)) {
                System.out.println("BUTTON 1");
            }
            // BUTTON 2
            else if( curY > image2Y && curY < (image2Y+200)){
                System.out.println("BUTTON 2");
            }
            // BUTTON 3
            else if( curY > image3Y && curY < (image3Y+200)){
                System.out.println( "BUTTON 3" );
            }
            // BUTTON 4
            else if( curY > image4Y && curY < (image4Y+200)){
                System.out.println("BUTTON 4");
            }else{}
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
