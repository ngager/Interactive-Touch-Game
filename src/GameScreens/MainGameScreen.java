package GameScreens;

import ImageLoading.ImageLoader;
import Objects.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by danny on 2/26/15.
 */
public class MainGameScreen extends ScreenUtility.FullScreen {
    private BufferedImage boatIcon = null;
    private BufferedImage planeIcon = null;
    private static JButton boatButton, planeButton;
    private DraggableBoat dragBoat;
    private DraggablePlane dragPlane;
    private boolean boatActive = false, planeActive = false;
    private int curX, curY, oldX, oldY;
    private float angle;
    private int rotateCount = 0;
    private boolean onLand = false, onShallow = false, onDeep = false;

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
            boatIcon = ImageIO.read(getClass().getClassLoader().getResource("boat_temp.png"));
            planeIcon = ImageIO.read(getClass().getClassLoader().getResource("plane_temp.png"));

        }catch(IOException e ){

        }

        boatButton = new JButton(new ImageIcon(boatIcon));
        boatButton.setBorder( BorderFactory.createLineBorder( Color.RED ));
        //boatButton.setContentAreaFilled(false);
        boatButton.setOpaque(true);
        boatButton.setBackground( Color.GRAY.darker() );
        boatButton.setBounds(1510, 10, boatIcon.getWidth(), boatIcon.getHeight());
        boatButton.addActionListener(this);

        planeButton = new JButton(new ImageIcon(planeIcon));
        planeButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        //planeButton.setContentAreaFilled(false);
        planeButton.setOpaque(true);
        planeButton.setBackground( Color.GRAY.darker() );
        planeButton.setBounds(1710, 10, planeIcon.getWidth(), planeIcon.getHeight());
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

                // Calculate the new pixels for the background
                if (globalPoint != null)
                    imageLoader.checkPixels(revealMask, destination, globalPoint, boatActive, planeActive);
                // Render the new image
                destImage = imageLoader.getImage(destination);
                // Draw background
                g2.drawImage(destImage, 0, 0, null);
                // Draw plane and boat in beginning
                if( !boatActive && !planeActive ){
                    // Draw plane
                    g2.drawImage(dragPlane.img, dragPlane.x, dragPlane.y, null);
                    dragPlane.bounds.setBounds(dragPlane.x, dragPlane.y, dragPlane.width, dragPlane.height);
                    // Draw boat
                    g2.drawImage(dragBoat.img, dragBoat.x, dragBoat.y, null);
                    dragBoat.bounds.setBounds(dragBoat.x, dragBoat.y, dragBoat.width, dragBoat.height);
                }
                // Need to save the old transformation or else the buttons get messed up
                AffineTransform oldXForm = g2.getTransform();
                if( boatActive ){
                    if( !onLand && !onShallow)
                        g2.rotate(Math.toRadians(angle), dragBoat.x + 100, dragBoat.y + 100);
                    g2.drawImage(dragBoat.img, dragBoat.x, dragBoat.y, null);
                    dragBoat.bounds.setBounds(dragBoat.x, dragBoat.y, dragBoat.width, dragBoat.height);
                    // Restore to normal transformation
                    g2.setTransform( oldXForm );
                    g2.drawImage(dragPlane.img, dragPlane.x, dragPlane.y, null);
                    //dragPlane.bounds.setBounds(dragPlane.x, dragPlane.y, dragPlane.width, dragPlane.height);
                }else if( planeActive ){
                    if( !onLand && !onDeep)
                        g2.rotate(Math.toRadians(angle), dragPlane.x + 100, dragPlane.y + 100);
                    g2.drawImage(dragPlane.img, dragPlane.x, dragPlane.y, null);
                    dragPlane.bounds.setBounds(dragPlane.x, dragPlane.y, dragPlane.width, dragPlane.height);
                    // Restore to normal transformation
                    g2.setTransform( oldXForm );
                    g2.drawImage(dragBoat.img, dragBoat.x, dragBoat.y, null);
                   // dragBoat.bounds.setBounds(dragBoat.x, dragBoat.y, dragBoat.width, dragBoat.height);
                }
            }
        });

        panel.setLayout(null);
        panel.add( boatButton );
        panel.add( planeButton );
        dragBoat = new DraggableBoat(1000, 500);
        dragPlane = new DraggablePlane(500, 500);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if( dragBoat.bounds.getBounds().contains(e.getPoint()) && boatActive) {
            curX = e.getX();
            curY = e.getY();
            // Rotate every other pixel
            if( rotateCount == 1 ){
                calculateRotation();
                rotateCount = 0;
            }
            org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());
            globalPoint = mousePoint;

            double[] values = maskMat.get((int) mousePoint.y, (int) mousePoint.x);
            if (values != null) {
                // Check if on land
                if( values[0] == 0.0 && values[1] == 0.0 && values[2] == 0.0 ){
                    onLand = true;
                }else onLand = false;
                // Check if on shallow water
                if( values[0] == 125.0 && values[1] == 125.0 && values[2] == 125.0 ){
                    onShallow = true;
                }else onShallow = false;
                // Check if on deep
                if( values[0] == 255.0 && values[1] == 255.0 && values[2] == 255.0 ){
                    onDeep = true;
                }else onDeep = false;
                Core.circle(revealMask, mousePoint, circleRadius, new Scalar(255.0, 255.0, 255.0), -1, 0, 0);
            }
            // Make sure we are not dragging the boat over land or shallow water
            if( !onLand && !onShallow ) {
                dragBoat.x = curX - 100;
                dragBoat.y = curY - 100;
            }

            if( rotateCount == 0 ){
                oldX = curX;
                oldY = curY;
            }
            rotateCount++;
            repaint();
        }else if( dragPlane.bounds.getBounds().contains(e.getPoint()) && planeActive ){
            curX = e.getX();
            curY = e.getY();
            // Rotate every other pixel
            if( rotateCount == 1 ){
                calculateRotation();
                rotateCount = 0;
            }
            org.opencv.core.Point mousePoint = new org.opencv.core.Point(e.getX(), e.getY());
            globalPoint = mousePoint;

            double[] values = maskMat.get((int) mousePoint.y, (int) mousePoint.x);
            if (values != null) {
                // Check if on land
                if( values[0] == 0.0 && values[1] == 0.0 && values[2] == 0.0 ){
                    onLand = true;
                }else onLand = false;
                // Check if on shallow water
                if( values[0] == 125.0 && values[1] == 125.0 && values[2] == 125.0 ){
                    onShallow = true;
                }else onShallow = false;
                // Check if on deep
                if( values[0] == 255.0 && values[1] == 255.0 && values[2] == 255.0 ){
                    onDeep = true;
                }else onDeep = false;
                Core.circle(revealMask, mousePoint, circleRadius, new Scalar(255.0, 255.0, 255.0), -1, 0, 0);
            }
            // Make sure we are not dragging the plane over land or deep water
            if( !onLand && !onDeep ) {
                dragPlane.x = curX - 100;
                dragPlane.y = curY - 100;
            }

            if( rotateCount == 0 ){
                oldX = curX;
                oldY = curY;
            }
            rotateCount++;
            repaint();
        }
    }

    public void calculateRotation(){
        int dX = curX - oldX;
        int dY = curY - oldY;
        angle = (float) Math.toDegrees(Math.atan2(dY, dX));
        if(angle < 0){
            angle += 360;
        }
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

    @Override
    public void mouseClicked(MouseEvent e) {}
}
