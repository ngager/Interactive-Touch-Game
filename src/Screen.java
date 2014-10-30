import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

public class Screen {
	// Control the video card / monitor
	private GraphicsDevice vc;

	public Screen(){
		// To use vc, need GraphicsEnvironment
		// Collection of all graphics device objects
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// Now have access to the computer screen
		vc = env.getDefaultScreenDevice();
	}

	// Build a window and convert to full screen
	public void setFullScreen( DisplayMode dm, JFrame window ){
		window.setUndecorated( true );
		window.setResizable( false );
		vc.setFullScreenWindow( window );

		if( dm != null && vc.isDisplayChangeSupported() ){
			try{
				vc.setDisplayMode( dm );
			}catch(Exception ex){}
		}
	}

	public Window getFullScreenWindow(){
		return vc.getFullScreenWindow();
	}

	public void restoreScreen(){
		Window w = vc.getFullScreenWindow();
		if( w != null ){
			w.dispose();
		}
		vc.setFullScreenWindow( null );
	}

	public void loadImage( String imgStr, Mat m, FullScreen window ){
		Highgui.imwrite( imgStr,  m );	
		ImageIcon image = new ImageIcon( imgStr );
		JLabel label1 = new JLabel(" ", image, JLabel.CENTER );
		window.getContentPane().add( label1 );
		window.validate();
		window.setVisible( true );
	}
}
