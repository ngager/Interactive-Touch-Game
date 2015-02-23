package ScreenUtility;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import ImageLoading.ImageLoader;
import org.opencv.core.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.awt.image.*;

public class FullScreen extends JFrame implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;

	private Screen s;
	// Image matrices
	private Mat aboveMat, belowMat, destination, maskMat, revealMask;
	// Load to BufferedImages
	private BufferedImage aboveImage, belowImage, destImage, maskImage, revealImage;
	private int circleRadius = 50;
	private ImageLoader imageLoader;
	public org.opencv.core.Point globalPoint;

	public FullScreen( ImageLoader imgLoader ){
		addMouseListener(this);
		addMouseMotionListener(this);

		imageLoader = imgLoader;
		// Read images into matrices
		aboveMat = imageLoader.getMatrix( "above" );
		belowMat = imageLoader.getMatrix("below");
		maskMat = imageLoader.getMatrix("mask");
		revealMask = imageLoader.getMatrix("reveal");
		destination = imageLoader.getMatrix("destination");

		// Load to BufferedImages
		aboveImage = imageLoader.getImage( aboveMat );
		belowImage = imageLoader.getImage( belowMat );
		maskImage = imageLoader.getImage ( maskMat );
		revealImage = imageLoader.getImage( revealMask );
		destImage = imageLoader.getImage ( destination );

		// JPanel
		this.getContentPane().add(new JPanel() {
			@Override
			public void update(Graphics g) {
				paintComponent(g);
			}

			// Displays the screen
			public void paintComponent(Graphics g) {

				// If g is a Graphics2D object, smooth out the text via java Rendering
				if (g instanceof Graphics2D) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				}
				super.paintComponent(g);
//				setBorder(new CompoundBorder(
//						BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE),
//						BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED)));
				// Calculate the new pixels
				if (globalPoint != null)
					imageLoader.checkPixels(revealMask, destination, globalPoint);
				// Render the new image
				destImage = imageLoader.getImage(destination);
				// Draw it
				g.drawImage(destImage, 0, 0, null);
			}
		});
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
		org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());
		globalPoint = mousePoint;

		System.out.println( "First test: " + mousePoint.x + ", " + mousePoint.y );

		double[] values = maskMat.get((int) mousePoint.y, (int) mousePoint.x);
		if( values != null ) {
			System.out.println("---");
			for (Double d : values)
				System.out.println(d);
			Core.circle(revealMask, mousePoint, circleRadius, new Scalar(255.0, 255.0, 255.0), -1, 0, 0);
			System.out.println("---");
		}

		repaint((int) mousePoint.x - circleRadius, (int) mousePoint.y - circleRadius, circleRadius * 2, circleRadius * 2);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());
		globalPoint = mousePoint;

		System.out.println( mousePoint.x + ", " + mousePoint.y );

		double[] values = maskMat.get((int)mousePoint.y, (int)mousePoint.x);
		if( values != null )
		{
			Core.circle(revealMask, mousePoint, circleRadius, new Scalar(255.0, 255.0, 255.0), -1, 0, 0);
		}

		repaint((int) mousePoint.x - circleRadius, (int) mousePoint.y - circleRadius, circleRadius * 2, circleRadius * 2);
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
}