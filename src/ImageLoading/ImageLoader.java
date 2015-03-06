package ImageLoading;

import java.awt.*;
import javax.swing.*;
import java.nio.ByteBuffer;

import org.opencv.core.*;
import org.opencv.highgui.*;

import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.awt.image.*;

/**
 * Created by danny on 11/25/14.
 */
public class ImageLoader {

    private Mat aboveMat, belowMat, maskMat, destination, revealMask;

    public ImageLoader(String abovePath, String belowPath, String maskPath) {
        // Read the image files into a matrix given the file path in string form
        aboveMat = Highgui.imread(abovePath);
        belowMat = Highgui.imread(belowPath);
        maskMat = Highgui.imread(maskPath);
        // starts off as zeros
        revealMask = Mat.zeros( 1080, 1920, 0);
        // starts off as the above path
        destination = Highgui.imread(abovePath);
    }

    // Returns the matrix of the desired layer
    public Mat getMatrix(String s) {
        if (s.equals("above"))
            return aboveMat;
        else if (s.equals("below"))
            return belowMat;
        else if (s.equals("mask"))
            return maskMat;
        else if (s.equals("reveal"))
            return revealMask;
        else if (s.equals("destination"))
            return destination;
        else return null;
    }

    // Returns the image associated with the layer given,
    // ** Has to actually create the new image in order for it to be updated.
    public BufferedImage getImage(Mat m) {
        return matToImage(m);
    }

    // Convert a Mat to a BufferedImage
    // Displays faster this way and avoids screen flickering
    private BufferedImage matToImage(Mat m) {
//        int type = BufferedImage.TYPE_BYTE_GRAY;
//        if (m.channels() > 1) {
            int type = BufferedImage.TYPE_3BYTE_BGR;
//        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }

    public void checkPixels(Mat reveal, Mat destination, org.opencv.core.Point mousePoint) {
        int mouseX = (int) mousePoint.x, mouseY = (int) mousePoint.y;

        // Check mask value at drag
        double[] values = maskMat.get(mouseY, mouseX);
        if( values != null ){
            // BLACK
            if( values[0] == 0.0 ){
               // System.out.println("black -- do nothing");
            // GRAY
            }else if (values[0] == 128.0) {
                // System.out.println( "gray" );
                // Have to only check the rows that we need around the circle!
                int rowStart = (int) mousePoint.y - 50;
                int rowEnd = (int) mousePoint.y + 50;

                int colStart = (int) mousePoint.x - 50;
                int colEnd = (int) mousePoint.x + 50;

                for (int r = rowStart; r < rowEnd; r++) {
                    for (int c = colStart; c < colEnd; c++) {
                        double[] vals = reveal.get(r, c);
                        if (vals == null) {
                            //System.out.println("null");
                        }
                        else if (vals[0] == 255.0) {
                            destination.put(r, c, belowMat.get(r, c));
                        }
                    }
                }
            // WHITE
            } else if (values[0] == 255.0) {
                // System.out.println( "white" );
                // Have to only check the rows that we need around the circle!
                int rowStart = (int) mousePoint.y - 50;
                int rowEnd = (int) mousePoint.y + 50;

                int colStart = (int) mousePoint.x - 50;
                int colEnd = (int) mousePoint.x + 50;

                for (int r = rowStart; r < rowEnd; r++) {
                    for (int c = colStart; c < colEnd; c++) {
                        double[] vals = reveal.get(r, c);
                        if (vals == null) {
                            //System.out.println("null");
                        }
                        else if (vals[0] == 255.0) {
                            destination.put(r, c, belowMat.get(r, c));
                        }
                    }
                }

            }
        }else{
           // System.out.println("NULL");
        }
    }
}
