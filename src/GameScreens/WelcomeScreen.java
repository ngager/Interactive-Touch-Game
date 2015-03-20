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

    Image background = Toolkit.getDefaultToolkit().createImage("/Users/nicolegager/Downloads/hurricane_template_splash.png");
    JButton start;

    public WelcomeScreen() {
        addMouseListener( this );
        this.setLayout( null );

        start = new JButton("start!!");
        start.setBorder(BorderFactory.createEmptyBorder());
        start.setContentAreaFilled(false);
        start.setBounds(1650, 400, 217, 150);
        start.addActionListener( this );
        this.add(start);

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null); // see javadoc for more info on the parameters
        g.fillRect(900,800,100,100);
    }

    public void actionPerformed(ActionEvent e) {

    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getX() > 850 && e.getX() < 950 && e.getY() < 850 && e.getY() > 750){
            System.out.println("Clicked on button");
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX() > 850 && e.getX() < 950 && e.getY() < 850 && e.getY() > 750){
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
