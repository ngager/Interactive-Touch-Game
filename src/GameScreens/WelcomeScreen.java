package GameScreens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by nicolegager on 2/26/15.
 */
public class WelcomeScreen extends JPanel implements MouseListener, ActionListener{

    Image background = Toolkit.getDefaultToolkit().createImage("/Users/cypher/Downloads/hurricane_template_splash.png");
    JButton start;
    CardLayout layout;
    JPanel panelContainer;

    public WelcomeScreen(CardLayout c, JPanel panelContainer) {
        addMouseListener( this );
        this.setLayout( null );
        this.layout = c;
        this.panelContainer = panelContainer;

       /* start = new JButton("start!!");
        start.setBorder(BorderFactory.createEmptyBorder());
        start.setContentAreaFilled(false);
        start.setBounds(1650, 400, 217, 150);
        start.addActionListener( this );
        this.add(start);*/

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g.fillRect(880,850,150,150);
    }

    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void mouseClicked(MouseEvent e) {

        System.out.println("Pressed on button: (" + e.getX() + "," + e.getY() + ")");
        if(e.getX() > 880 && e.getX() < 1030 && e.getY() < 850 && e.getY() > 1000){
            System.out.println("Pressed on button");
            layout.show( panelContainer, "1");        }


    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX() > 880 && e.getX() < 1030 && e.getY() < 850 && e.getY() > 1000){
            System.out.println("Pressed on button");
        }

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
