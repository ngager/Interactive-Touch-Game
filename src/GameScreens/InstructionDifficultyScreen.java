package GameScreens;

import MainApplication.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by danny vasta on 2/26/15.
 */
public class InstructionDifficultyScreen extends JPanel implements MouseListener{
    Image background;
    JPanel panelContainer;
    CardLayout cl;
    CLayout layout;

    public InstructionDifficultyScreen(final CLayout layout, final JPanel panelContainer) {
        this.cl = layout.cl;
        this.layout = layout;
        this.panelContainer = panelContainer;
        // Path to background image
        background = Toolkit.getDefaultToolkit().createImage( layout.getDirectory("menu") + "instructions_background.png");

        addMouseListener( this );
        this.setLayout(null);

        // Setup Timer
        //new InactivityListener( layout, panelContainer );

    }
    // Very fast and easy way to set a background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        Action reset = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                cl.show(panelContainer, "1");
            }
        };
        new InactivityListener(this.layout, reset, 1).start();
    }

    // Pseudo-buttons
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //TODO -- use real coords
        // Level 1
        if( (x >= 1670 && x <= 1850) && (y >= 415 && y <= 540) ){
            layout.level1 = true;
            layout.level2 = false;
            layout.level3 = false;
            new Game( layout, panelContainer );
        }
        // Level 2
        else if( (x >= 1670 && x <= 1850) && (y >= 632 && y <= 757) ){
            layout.level1 = false;
            layout.level2 = true;
            layout.level3 = false;
            new Game( layout, panelContainer );
        }
        // Level 3
        else if( (x >= 1670 && x <= 1850) && (y >= 859 && y <= 984) ){
            layout.level1 = false;
            layout.level2 = false;
            layout.level3 = true;
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
