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
    CLayout layout;
    Rectangle r1, r2, r3;

    public InstructionDifficultyScreen(CLayout layout, JPanel panelContainer) {
        this.cl = layout.cl;
        this.layout = layout;
        this.panelContainer = panelContainer;

        addMouseListener( this );
        this.setLayout( null );
    }
    // Very fast and easy way to set a background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

    }

    // Pseudo-buttons
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        // Level 1
        if( (x >= 1695 && x <= 1820) && (y >= 415 && y <= 540) ){
            new Game( layout, panelContainer );
        }
        // Level 2
        else if( (x >= 1695 && x <= 1820) && (y >= 632 && y <= 757) ){
            new Game( layout, panelContainer );
        }
        // Level 3
        else if( (x >= 1695 && x <= 1820) && (y >= 859 && y <= 984) ){
            new Game( layout, panelContainer );
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
