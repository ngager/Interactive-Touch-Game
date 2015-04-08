package GameScreens;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Created by nicolegager on 2/26/15.
 */
public class WelcomeScreen extends JPanel implements MouseListener, ActionListener{

    Image background;
    CardLayout layout;
    JPanel panelContainer;
    JTextField inputField;

    public WelcomeScreen(CardLayout c, JPanel panelContainer) {
        addMouseListener( this );
        this.setLayout( null );
        this.layout = c;
        this.panelContainer = panelContainer;
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        try{
            background = ImageIO.read(getClass().getClassLoader().getResourceAsStream("hurricane_template_splash.png"));
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }catch(IOException e ){

        }
    }

    public void actionPerformed(ActionEvent e) {}


    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if( (x >= 845 && x <= 1060) && (y > 845 && y < 990) ){
            layout.show(panelContainer, "2");
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