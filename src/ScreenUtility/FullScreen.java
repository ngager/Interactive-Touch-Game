package ScreenUtility;

import java.awt.*;
import javax.swing.*;
import ImageLoading.ImageLoader;
import org.opencv.core.*;
import org.opencv.highgui.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.awt.image.*;
import java.nio.*;
import org.apache.commons.math3.analysis.*;

public class FullScreen extends JFrame implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;

	private Screen s;
	// Image matrices
	private Mat aboveMat, belowMat, destination, revealMask;
	// Load to BufferedImages
	private BufferedImage aboveImage, belowImage, destImage, maskImage;
	private int circleRadius = 50;
	private ImageLoader imageLoader;


	public FullScreen( ImageLoader imgLoader ){
		addMouseListener(this);
		addMouseMotionListener(this);

		imageLoader = imgLoader;
		// Read images into matrices
		aboveMat = imageLoader.getMatrix( "above" );
		belowMat = imageLoader.getMatrix("below");
		revealMask = imageLoader.getMatrix( "mask");
		//revealMask = Mat.zeros( 1920, 1080, 0);
		// Originally starts off as the above water section
		destination = imageLoader.getMatrix( "above" );

		// Load to BufferedImages
		aboveImage = imageLoader.getImage( aboveMat );
		belowImage = imageLoader.getImage( belowMat );
		maskImage = imageLoader.getImage ( revealMask );
		destImage = imageLoader.getImage ( destination );

		// JPanel
		this.add(new JPanel() {
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
				super.paintComponent( g );

				destImage = imageLoader.getImage( destination );
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

	public void blendImages( ){
		double alpha = 0.4, beta;
		// src1 is aboveMat, src2 is belowMat

		if( aboveMat == null || belowMat == null){
			System.exit( -1 );
		}
		beta = 1.0 - alpha;
		Core.addWeighted( aboveMat, alpha, belowMat, beta, 0.0, destination );
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());
		Core.circle(destination, mousePoint, circleRadius, new Scalar(255.0, 255.0, 255.0), 1, Core.LINE_AA, 0);
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());

		Core.circle(destination, mousePoint, circleRadius, new Scalar(255.0, 255.0, 255.0), -1, Core.LINE_AA, 0);
		repaint((int)mousePoint.x-circleRadius, (int)mousePoint.y-circleRadius, circleRadius*2, circleRadius*2);
	}

//	public void checkPixels( Mat destination ){
//		ByteBuffer screenbuffer = destImage.getByteBuffer();
//		ByteBuffer imgAbuffer = aboveImage.getByteBuffer();
//		ByteBuffer imgBbuffer = belowImage.getByteBuffer();
//		ByteBuffer revealbuffer = revealMask.getByteBuffer();
//
//		for(int y = 0; y < 1080; y++)
//		{
//			for(int x = 0; x < 1920; x++)
//			{
//				int index = y * destImage.widthStep() + x * destImage.nChannels();
//
//				// Used to read the pixel value - the 0xFF is needed to cast from
//				// an unsigned byte to an int.
//				int aValue = imgAbuffer.get(index) & 0xFF;
//				int bValue = imgBbuffer.get(index) & 0xFF;
//				int maskValue = revealbuffer.get(index) & 0xFF;
//
//				if (maskValue == 0)
//					screenbuffer.put(index, aValue);
//				else if (maskValue == 1)
//					screenbuffer.put(index, bValue);
//
//			}
//		}
//	}

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