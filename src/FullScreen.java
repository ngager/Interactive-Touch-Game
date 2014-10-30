import java.awt.*;
import javax.swing.JFrame;
import org.opencv.core.*;
import org.opencv.highgui.*;

public class FullScreen extends JFrame{
	// Needed
	private static final long serialVersionUID = 1L;
	private static Screen s;
	// Read image into matrix
	Mat m = Highgui.imread( "C:\\Users\\dja94_000\\Desktop\\totoro.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
	
	// Creates a Screen object and sets it to full screen
	public void run( DisplayMode dm ){
		setFont( new Font("Arial", Font.PLAIN, 24) );
		s = new Screen();
		try{
			s.setFullScreen( dm, this );
			//s.loadImage( "C:\\Users\\dja94_000\\Desktop\\totoro_new.jpg", m, this);		
			blendImages();
			try{
				Thread.sleep( 5000 );
			}catch( Exception ex ){}
		}finally{
			s.restoreScreen();
		}
	}

	// Displays the screen
	public void paint( Graphics g ){
		// If g is a Graphics2D object, smooth out the text via java Rendering
		if( g instanceof Graphics2D ){
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		}
		super.paint( g );
	}

	public void blendImages( ){
		double alpha = 0.4, beta;
		Mat src1, src2, destination;
		
		src1 = Highgui.imread( "C:\\Users\\dja94_000\\Desktop\\totoro.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		src2 = Highgui.imread( "C:\\Users\\dja94_000\\Desktop\\space.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		destination = Highgui.imread( "C:\\Users\\dja94_000\\Desktop\\space.jpg", Highgui.CV_LOAD_IMAGE_COLOR);
		
		if( src1 == null || src2 == null){
			System.exit( -1 );
		}
		
		beta = 1.0 - alpha;

		Core.addWeighted( src1, alpha, src2, beta, 0.0, destination );
		s.loadImage( "C:\\Users\\dja94_000\\Desktop\\space_new.jpg", destination, this );
	}
	
	public static void main( String[] args ){
		// Load the openCV library
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		// Res, res, color bit, refresh rate
		DisplayMode dm = new DisplayMode( 1920, 1080, 16, DisplayMode.REFRESH_RATE_UNKNOWN );
		FullScreen w = new FullScreen();
		w.run( dm );	
	}
}
