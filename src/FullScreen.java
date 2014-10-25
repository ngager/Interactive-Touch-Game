import java.awt.*;
import javax.swing.JFrame;

public class FullScreen extends JFrame{
	private static final long serialVersionUID = 1L;
	
	// Creates a Screen object and sets it to full screen
	public void run( DisplayMode dm ){
		getContentPane().setBackground( Color.PINK );
		setForeground( Color.WHITE );
		setFont( new Font("Arial", Font.PLAIN, 24) );
		
		Screen s = new Screen();
		try{
			s.setFullScreen( dm, this );
			try{
				Thread.sleep( 5000 );
			}catch( Exception ex ){}
		}finally{
			s.restoreScreen();
		}
	}
	
	// Displays the screen
	// Add text as a test
	public void paint( Graphics g ){
		// If g is a Graphics2D object, smooth out the text via java Rendering
		if( g instanceof Graphics2D ){
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
		}
		super.paint( g );
		g.drawString( "Woo fullscreen!", 200, 200 );
	}
	
	public static void main( String[] args ){
		// Res, res, color bit, refresh rate
		// REFRESH_RATE_UNKNOWN -- keep it generic
		DisplayMode dm = new DisplayMode( 1920, 1080, 16, DisplayMode.REFRESH_RATE_UNKNOWN );
		FullScreen w = new FullScreen();
		w.run( dm );
	}
}
