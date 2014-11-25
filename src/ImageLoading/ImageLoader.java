package ImageLoading;

import java.awt.*;
import javax.swing.*;

import org.opencv.core.*;
import org.opencv.highgui.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.awt.image.*;

/**
 * Created by danny on 11/25/14.
 */
public class ImageLoader {

    private Mat aboveMat, belowMat, maskMat, destination;

    public ImageLoader(String abovePath, String belowPath, String maskPath) {
        // Read the image files into a matrix given the file path in string form
        aboveMat = Highgui.imread(abovePath, Highgui.CV_LOAD_IMAGE_COLOR);
        belowMat = Highgui.imread(belowPath, Highgui.CV_LOAD_IMAGE_COLOR);
        maskMat = Highgui.imread(maskPath, Highgui.CV_LOAD_IMAGE_COLOR);
        destination = Highgui.imread( abovePath, Highgui.CV_LOAD_IMAGE_COLOR );
    }

    public Mat getMatrix(String s) {
        if (s.equals("above"))
            return aboveMat;
        else if (s.equals("below"))
            return belowMat;
        else if (s.equals("mask"))
            return maskMat;
        else return null;
    }

    // Returns the image associated with the layer given,
    // ** Has to actually create the new image in order for it to be updated.
    public BufferedImage getImage(Mat m) {
        return matToImage( m );
    }

    // Convert a Mat to a BufferedImage
    // Displays faster this way and avoids screen flickering
    public BufferedImage matToImage(Mat m) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }

//    public void blendImages( ){
//        double alpha = 0.4, beta;
//        // src1 is aboveMat, src2 is belowMat
//
//        if( aboveMat == null || belowMat == null){
//            System.exit( -1 );
//        }
//        beta = 1.0 - alpha;
//        Core.addWeighted( aboveMat, alpha, belowMat, beta, 0.0, destination );
//        repaint();
//    }

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
}
