import java.awt.*;
import javax.swing.*;
import org.opencv.core.*;
import org.opencv.highgui.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.awt.image.*;

public class FullScreen extends JFrame implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;

	private static Screen s;
	// Image matrices
	private Mat aboveMat, belowMat, destination, revealMask;
	// Load to BufferedImages
	private BufferedImage aboveImage, belowImage, destImage;

	public FullScreen(){
		addMouseListener(this);
		addMouseMotionListener( this );
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
		try{
			s.setFullScreen(dm, this);
			//s.loadImage( "/Users/danny/Downloads/above.png", aboveMat, this );
			//try{
			//	Thread.sleep( 5000 );
			//}catch( Exception ex ){}
		}finally{
			//s.restoreScreen();
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
	
	public static void main( String[] args ){
		// Load the openCV library
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		// Res, res, color bit, refresh rate
		DisplayMode dm = new DisplayMode( 1920, 1080, 16, DisplayMode.REFRESH_RATE_UNKNOWN );
		FullScreen w = new FullScreen();
		w.run( dm );
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());

		int radius = 50;

		System.out.println( mousePoint );
		Core.circle(destination, mousePoint, radius, new Scalar(255.0, 255.0, 255.0), 1, Core.LINE_AA, 0);
		destImage = toBufferedImage( destination );

		repaint();
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
	public void mouseDragged(MouseEvent e) {
		org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());

		int radius = 50;

		System.out.println( mousePoint );
		Core.circle(destination, mousePoint, radius, new Scalar(255.0, 255.0, 255.0), 1, Core.LINE_AA, 0);
		destImage = toBufferedImage( destination );

		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}