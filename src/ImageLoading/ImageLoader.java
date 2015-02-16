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

    public void checkPixels( Mat above, Mat destination, org.opencv.core.Point mousePoint ){
        int mouseX = (int) mousePoint.x, mouseY = (int) mousePoint.y;

        // Check mask value at drag
        double[] values = maskMat.get( mouseX, mouseY );
        // BLACK
        if( values == null ){

        // GRAY
        }else if( values[0] == 128.0 ){
            /**
             * OPTIMIZED BLENDING ALGORITHM
             */
            // Have to only check the rows that we need around the circle!
            int rowStart = (int)mousePoint.y - 50;
            int rowEnd = (int)mousePoint.y + 50;

            int colStart = (int)mousePoint.x - 50;
            int colEnd = (int)mousePoint.x + 50;

            for( int r = rowStart; r < rowEnd; r++ ) {
                for (int c = colStart; c < colEnd; c++) {

                    double[] vals = above.get(r, c);
                    if (vals == null) {
                        System.out.println("null");
                    }
                    // TEMP, using 7.0 because that is what I paint on drag.
                    else if (vals[0] == 7.0) {
                        destination.put(r, c, belowMat.get(r, c));
                    }
                }
            }
        // WHITE
        }else if( values[0] == 255.0 ){
            /**
             * OPTIMIZED BLENDING ALGORITHM
             */
            // Have to only check the rows that we need around the circle!
            int rowStart = (int)mousePoint.y - 50;
            int rowEnd = (int)mousePoint.y + 50;

            int colStart = (int)mousePoint.x - 50;
            int colEnd = (int)mousePoint.x + 50;

            for( int r = rowStart; r < rowEnd; r++ ){
                for( int c = colStart; c < colEnd; c++ ){

                    double[] vals = above.get( r, c );
                    if( vals == null ){
                        System.out.println( "null" );
                    }
                    // TEMP, using 7.0 because that is what I paint on drag.
                    else if( vals[0] == 7.0 ){
                        destination.put( r, c, belowMat.get( r, c ) );
                    }
                }
            }

        }

        /**
         * Original code, not optimized.
         * - Checked the whole screen and made it horrificly slow.
         */
//            int rows = destination.rows();
//            int cols = destination.cols();
//            for( int r = 0; r < rows; r++ ){
//                for( int c = 0; c < cols; c++ ){
//                    double[] vals = destination.get( r, c );
//                    if( vals == null ){
//                        System.out.println( "null" );
//                    }
//                    else if( vals[0] == 7.0 ){
//                        destination.put( r, c, belowMat.get( r, c ) );
//                    }
//                }
//            }
//        System.out.println( "---" );
//        destination.put( x, y, belowMat.get( x, y));
//
//                byte[] screenBuffer =   new byte[ destination.rows() * destination.cols() * destination.channels()];
//                byte[] maskBuffer =     new byte[ maskMat.rows() * maskMat.cols() * maskMat.channels()];
//        		byte[] aboveBuffer =    new byte[ aboveMat.rows() * aboveMat.cols() * aboveMat.channels()];
//        		byte[] belowBuffer =    new byte[ belowMat.rows() * belowMat.cols() * belowMat.channels()];
//                ByteBuffer screenBuf = ByteBuffer.wrap( screenBuffer );
//                ByteBuffer maskBuf = ByteBuffer.wrap( maskBuffer );
//                ByteBuffer aboveBuf = ByteBuffer.wrap( aboveBuffer );
//                ByteBuffer belowBuf = ByteBuffer.wrap( belowBuffer );
//
//                destination.get( 0, 0, screenBuffer );
//
//        		for(int y = 0; y < 1080; y++)
//        		{
//        			for(int x = 0; x < 1920; x++)
//        			{
//        				int index = y * getImage( destination ).getWidth() + x;
//        				// Used to read the pixel value - the 0xFF is needed to cast from
//        				// an unsigned byte to an int.
//        				byte aValue = aboveBuf.get(index);
//        				byte bValue = belowBuf.get(index);
//        				byte maskValue = maskBuf.get(index);
//        				if (maskValue == 0)
//        					screenBuf.put(index, aValue);
//        				else if (maskValue == 1)
//        					screenBuf.put(index, bValue);
//        			}
//        		}
//                destination.put( 0, 0, screenBuffer );
    }
}
