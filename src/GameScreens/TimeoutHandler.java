package GameScreens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nicolegager on 4/26/15.
 */
public class TimeoutHandler implements ActionListener{

    Timer timer;
    JPanel panelContainer;
    CardLayout cl;
    CLayout layout;

    public TimeoutHandler( CLayout layout, JPanel panelContainer ){

        this.panelContainer = panelContainer;
        this.cl = layout.cl;
        this.layout = layout;

        int delay = 90000; // 90 seconds
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        cl.show(panelContainer, "1");

    }
}
