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

    // Returns the image associated with the layer given,
    // ** Has to actually create the new image in order for it to be updated.
    public BufferedImage getImage(Mat m) {
        return matToImage( m );
    }
}
