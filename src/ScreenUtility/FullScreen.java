package ScreenUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FullScreen extends JFrame implements MouseListener, MouseMotionListener, ActionListener{
	private static final long serialVersionUID = 1L;
	private Screen s;

	public FullScreen(){

	}

    // Creates a Screen object and sets it to full screen
	public void run( DisplayMode dm ){
		s = new Screen();
		try {
			s.setFullScreen(dm, this);
		}catch( Exception e ){
				System.out.println( e.getStackTrace() );
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	// Unused MouseEvents
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

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}