package GameScreens;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * Created by nicolegager on 2/26/15.
 */
public class WelcomeScreen extends JPanel implements MouseListener, ActionListener{

    Image background;
    JButton start;
    CardLayout layout;
    JPanel panelContainer;
    JTextField inputField;
    Boolean enableScreenChange;

    public WelcomeScreen(CardLayout c, JPanel panelContainer) {


        addMouseListener( this );
        this.setLayout( null );
        this.layout = c;
        this.panelContainer = panelContainer;
        enableScreenChange = false;

        inputField = new JTextField("Enter Name");
        inputField.setVisible(true);
        inputField.setBounds(500, 900, 200, 50);
        inputField.addActionListener(this);
        this.add(inputField);


    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        try{
            background = ImageIO.read(getClass().getClassLoader().getResourceAsStream("hurricane_template_splash.png"));
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }catch(IOException e ){

        }



    }

    public void actionPerformed(ActionEvent e) {
        enableScreenChange = true;
    }

    public JTextField getTextField( ){
        return inputField;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        if(enableScreenChange) {
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