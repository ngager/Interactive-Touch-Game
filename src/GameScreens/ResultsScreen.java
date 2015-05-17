package GameScreens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by danny on 2/26/15.
 */
public class ResultsScreen extends JPanel implements MouseListener{
    Image background;
    private int finalScore;
    private int possibleTotal;
    private float percent;
    CLayout layout;
    CardLayout cl;
    JPanel panelContainer;

    public ResultsScreen(final CLayout layout, final JPanel panelContainer){
        addMouseListener(this);
        this.setLayout(null);
        this.layout = layout;
        this.cl = layout.cl;
        this.panelContainer = panelContainer;

        // Path to background image
        background = Toolkit.getDefaultToolkit().createImage( layout.getDirectory("menu") + "score_background.png");

        Action reset = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                cl.show(panelContainer, "1");
            }
        };
        new InactivityListener(this.layout, reset, 1).start();
    }

    public void setResults( int score, int total ){
        finalScore = score;
        possibleTotal = total;
        percent = (finalScore * 100.0f) / possibleTotal;
    }

    // Very fast and easy way to set a background image
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        g2.setColor(new Color(248, 248, 243));

        g2.setFont(new Font("Arial", 1, 64));
        g2.drawString("Final score:", 800, 480);

        g2.setFont(new Font("Arial", 1, 44));
        g2.drawString(finalScore + " / " + possibleTotal + " (" + percent + "%)", 850, 540);
        if( percent <= 40.0 ){
            g2.drawString("Better luck next time!", 800, 600 );
        }
        else if( percent <= 80.0 ){
            g2.drawString("Not bad!", 850, 600 );
        }
        else if( percent == 100.0 ){
            g2.drawString("Perfect! Nice job!", 800, 600 );
        }
    }

    // Don't even need to check mouse click coordinates because the buttons take care of it
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        //TODO -- real coords
        if( (x >= 845 && x <= 1100) && (y >= 845 && y <= 1000) ){
            cl.show(panelContainer, "2");
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
