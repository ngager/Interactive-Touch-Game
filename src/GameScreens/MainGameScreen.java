package GameScreens;

import ImageLoading.ImageLoader;
import Objects.DraggableBoat;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by danny on 2/26/15.
 */
public class MainGameScreen extends ScreenUtility.FullScreen {
    private BufferedImage boatIcon = null;
    private BufferedImage planeIcon = null;
    private static JButton boatButton, planeButton;
    private DraggableBoat dragBoat;
    private int boatX = 0, boatY = 0;
    private boolean boatActive = false, planeActive = false;
    private int curX, curY, oldX, oldY;
    private float angle;
    private int rotateCount = 0;

    // Image matrices
    private Mat aboveMat, belowMat, destination, maskMat, revealMask;
    // Load to BufferedImages
    private BufferedImage aboveImage, belowImage, destImage, maskImage, revealImage;
    private int circleRadius = 50;
    private ImageLoader imageLoader;
    public org.opencv.core.Point globalPoint;
    private JPanel panel;

    public MainGameScreen( ImageLoader imgLoader ){
        addMouseListener(this);
        addMouseMotionListener(this);
        try{
            boatIcon = ImageIO.read(getClass().getClassLoader().getResource("boat_icon1.png"));
            planeIcon = ImageIO.read(getClass().getClassLoader().getResource("plane_icon1.png"));
        }catch(IOException e ){

        }
        boatButton = new JButton(new ImageIcon(boatIcon));
        boatButton.setBorder(BorderFactory.createEmptyBorder());
        boatButton.setContentAreaFilled(false);
        boatButton.setBounds(1510, 10, 200, 200);
        boatButton.addActionListener(this);

        planeButton = new JButton(new ImageIcon(planeIcon));
        planeButton.setBorder(BorderFactory.createEmptyBorder());
        planeButton.setContentAreaFilled(false);
        planeButton.setBounds(1710, 10, 200, 200);
        planeButton.addActionListener(this);

        imageLoader = imgLoader;
        // Read images into matrices
        aboveMat = imageLoader.getMatrix( "above" );
        belowMat = imageLoader.getMatrix("below");
        maskMat = imageLoader.getMatrix("mask");
        revealMask = imageLoader.getMatrix("reveal");
        destination = imageLoader.getMatrix("destination");

        // Load to BufferedImages
        aboveImage = imageLoader.getImage( aboveMat );
        belowImage = imageLoader.getImage( belowMat );
        maskImage = imageLoader.getImage ( maskMat );
        revealImage = imageLoader.getImage( revealMask );
        destImage = imageLoader.getImage ( destination );

        // JPanel
        this.getContentPane().add(panel = new JPanel() {
            @Override
            public void update(Graphics g) {
                paintComponent(g);
            }

            // Displays the screen
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                super.paintComponent(g2);
                // Calculate the new pixels
                if (globalPoint != null)
                    imageLoader.checkPixels(revealMask, destination, globalPoint);
                // Render the new image
                destImage = imageLoader.getImage(destination);
                // Draw it
                g2.drawImage(destImage, 0, 0, null);
                g2.rotate(Math.toRadians(angle), boatX+188, boatY+14);
                g2.drawImage(dragBoat.img, boatX, boatY, null);
                dragBoat.setBounds(boatX, boatY, 200, 200);
            }
        });
        panel.setLayout(null);
        panel.add( boatButton );
        panel.add( planeButton );
        dragBoat = new DraggableBoat();
        dragBoat.setBounds( 500, 500, 200, 200);
        boatX = dragBoat.getX();
        boatY = dragBoat.getY();
        panel.add( dragBoat );
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());
//        globalPoint = mousePoint;
//
//        double[] values = maskMat.get((int) mousePoint.y, (int) mousePoint.x);
//        if( values != null ) {
//            Core.circle(revealMask, mousePoint, circleRadius, new Scalar(255.0, 255.0, 255.0), -1, 0, 0);
//        }
//
//        repaint((int) mousePoint.x - circleRadius, (int) mousePoint.y - circleRadius, circleRadius * 2, circleRadius * 2);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if( dragBoat.getBounds().contains(e.getPoint()) && boatActive) {
            curX = e.getX();
            curY = e.getY();
            if( rotateCount == 2 ){
                calculateRotation();
                rotateCount = 0;
            }
            org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());
            globalPoint = mousePoint;

            double[] values = maskMat.get((int) mousePoint.y, (int) mousePoint.x);
            if (values != null) {
                Core.circle(revealMask, mousePoint, circleRadius, new Scalar(255.0, 255.0, 255.0), -1, 0, 0);
            }
            boatX = curX - 100;
            boatY = curY - 100;
            //boatX = (curX - boatX);
            //boatY = (curY - boatY);
            if( rotateCount == 0 ){
                oldX = curX;
                oldY = curY;
            }
            //repaint((int) mousePoint.x - circleRadius, (int) mousePoint.y - circleRadius, circleRadius * 2, circleRadius * 2);
            rotateCount++;
            repaint();
        }
    }

    public void calculateRotation(){
        int dX = curX - oldX;
        int dY = curY - oldY;
        //System.out.println( "dX: " + dX + " dY: " + dY );
        //angle = (int)Math.atan2( dY, dX );
        angle = (float) Math.toDegrees(Math.atan2(dY, dX));

        if(angle < 0){
            angle += 360;
        }
        System.out.println( angle );

    }

    public void actionPerformed(ActionEvent e) {
        if( e.getSource() == boatButton ){
            boatActive = true;
            planeActive = false;
        }else if( e.getSource() == planeButton){
            boatActive = false;
            planeActive = true;
        }
    }
}
