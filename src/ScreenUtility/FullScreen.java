package ScreenUtility;

import ImageLoading.ImageLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class FullScreen extends JFrame implements MouseListener, MouseMotionListener, ActionListener{
	private static final long serialVersionUID = 1L;
	private Screen s;

	public FullScreen(){

	}

    // Creates a Screen object and sets it to full screen
	public void run( DisplayMode dm ){
		setFont( new Font("Arial", Font.PLAIN, 24) );
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