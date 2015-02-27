package GameScreens;

import MainApplication.Game;
import ScreenUtility.FullScreen;
import org.opencv.core.Core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by nicolegager on 2/26/15.
 */
public class WelcomeScreen extends FullScreen implements ActionListener{

    JButton b2;

    public WelcomeScreen() {
        b2 = new JButton("Click Me!");
        b2.setVerticalTextPosition(AbstractButton.BOTTOM);
        b2.setHorizontalTextPosition(AbstractButton.CENTER);
        b2.setMnemonic(KeyEvent.VK_M);
        b2.addActionListener(this);
        this.add(b2);

    }

    public void actionPerformed(ActionEvent e) {
        if ("disable".equals(e.getActionCommand())) {
            b2.setEnabled(false);


        } else {
            b2.setEnabled(true);
            new Game();
            this.dispose();

        }
    }

    public static void main( String[] args ){
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        DisplayMode dm = new DisplayMode( 1920, 1080, 16, DisplayMode.REFRESH_RATE_UNKNOWN );
        WelcomeScreen s = new WelcomeScreen();
        s.run(dm);
    }
}
