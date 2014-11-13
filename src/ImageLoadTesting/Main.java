package ImageLoadTesting;

import org.opencv.core.*;
import org.opencv.highgui.*;

public class Main {

	public static void main(String[] args)
	{
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		Mat m=Highgui.imread("\\Users\\danny\\Downloads\\totoro.png",Highgui.CV_LOAD_IMAGE_COLOR);
		new LoadImage("\\Users\\danny\\Downloads\\totoro.png",m);
	}
}