package GameScreens;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by nicolegager on 2/26/15.
 */
public class WelcomeScreen extends JPanel implements MouseListener, ActionListener{

    Image background;
    CLayout layout;
    JPanel panelContainer;

    public WelcomeScreen(CLayout cl, JPanel panelContainer) {
        addMouseListener( this );
        this.setLayout(null);
        this.layout = cl;
        this.panelContainer = panelContainer;
        background = Toolkit.getDefaultToolkit().createImage( layout.getDirectory("menu") + "welcome_background.png" );
        System.out.println( layout.getDirectory("menu") + "welcome_background.png");
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    public void actionPerformed(ActionEvent e) {}


    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //TODO -- real coords
        if( (x >= 845 && x <= 1100) && (y >= 845 && y <= 1000) ){
            layout.cl.show(panelContainer, "2");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}