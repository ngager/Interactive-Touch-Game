import java.awt.*;
import javax.swing.*;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SmoothingPolynomialBicubicSplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
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
	private BufferedImage aboveImage, belowImage, destImage;

	// Interpolation variables
	private double[] draggedXVals;
	private double[] draggedYVals;
	private double globalYDrag = 0.0;
	private int index = 0;
	double[] polynomials;

	private LinearInterpolator interpolator = new LinearInterpolator();
	///////////////////////////

	public FullScreen(){
		addMouseListener(this);
		addMouseMotionListener(this);
		draggedXVals = new double[2];
		draggedYVals = new double[2];
		// Read images into matrices
		aboveMat = Highgui.imread( "/Users/danny/Downloads/above.png", Highgui.CV_LOAD_IMAGE_COLOR);
		belowMat = Highgui.imread( "/Users/danny/Downloads/below.png", Highgui.CV_LOAD_IMAGE_COLOR);
		destination = Highgui.imread( "/Users/danny/Downloads/above.png", Highgui.CV_LOAD_IMAGE_COLOR);
		// Originally starts off as the above water section
		revealMask = Highgui.imread( "/Users/danny/Downloads/mask.png", Highgui.CV_LOAD_IMAGE_COLOR);
		//revealMask = Mat.zeros( 1920, 1080, 0);

		// Load to BufferedImages
		aboveImage = toBufferedImage( aboveMat );
		belowImage = toBufferedImage( belowMat );
		destImage = toBufferedImage( destination );

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

	// Convert a Mat to a BufferedImage
	// Displays faster this way and avoids screen flickering
	public BufferedImage toBufferedImage(Mat m){
		int type = BufferedImage.TYPE_BYTE_GRAY;
		if ( m.channels() > 1 ) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels()*m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		return image;
	}

	public void blendImages( ){
		double alpha = 0.4, beta;
		// src1 is aboveMat, src2 is belowMat

		if( aboveMat == null || belowMat == null){
			System.exit( -1 );
		}
		beta = 1.0 - alpha;
		Core.addWeighted( aboveMat, alpha, belowMat, beta, 0.0, destination );
		destImage = toBufferedImage( destination );
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());
		int radius = 50;
		Core.circle(destination, mousePoint, radius, new Scalar(255.0, 255.0, 255.0), 1, Core.LINE_AA, 0);
		destImage = toBufferedImage( destination );
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		globalYDrag = e.getY();
		org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());
		int radius = 50;
		// Clear the array after 2 vals have been stored
//		if( index == 2){
//			findInterpolation( draggedXVals, draggedYVals );
//			index = 0;
//		}
//
//		draggedXVals[index] = mousePoint.x;
//		draggedYVals[index] = mousePoint.y;
//		index++;
		Core.circle(destination, mousePoint, radius, new Scalar(255.0, 255.0, 255.0), -1, Core.LINE_AA, 0);
		destImage = toBufferedImage( destination );
		repaint((int)mousePoint.x-radius, (int)mousePoint.y-radius, radius*2, radius*2);
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

	public void findInterpolation( double[] x, double[] y ){
		polynomials = interpolator.interpolate( x, y ).getKnots();
		for (int f = 0; f < polynomials.length; f++ ){
			org.opencv.core.Point polyPoint = new org.opencv.core.Point(polynomials[f], globalYDrag );
			Core.circle(destination, polyPoint, 50, new Scalar(255.0, 255.0, 255.0), 1, Core.LINE_AA, 0);
			//System.out.println( polynomials[f]);
			repaint();
		}
	}

	public static void main( String[] args ){
		// Load the openCV library
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		// Res, res, color bit, refresh rate
		DisplayMode dm = new DisplayMode( 1920, 1080, 16, DisplayMode.REFRESH_RATE_UNKNOWN );
		FullScreen w = new FullScreen();
		w.run( dm );
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

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}