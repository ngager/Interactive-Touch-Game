package GameScreens;

import ImageLoading.ImageLoader;
import Objects.DebrisFlagger;
import Objects.DraggableBoat;
import Objects.DraggablePlane;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Created by danny on 2/26/15.
 */
public class MainGameScreen extends JPanel implements MouseListener, MouseMotionListener {
    private DraggableBoat dragBoat;
    private DraggablePlane dragPlane;
    private boolean boatActive = false, planeActive = false;
    private int curX, curY, oldX, oldY;
    private float angle;
    private int rotateBoat = 0;
    private int rotatePlane = 0;
    private boolean onLand = false, onShallow = false;
    private MouseEvent globalDragEvent;
    private final int NUM_OBJECTS = 5;
    int foundCount = 0;

    // Image matrices
    private Mat aboveMat, belowMat, destination, maskMat, revealMask;
    public Mat fadedCircleMask, fadedCircleMat;
    // Load to BufferedImages
    private BufferedImage destImage;
    private DebrisFlagger flagImages[];
    private int randomX[];
    private int randomY[];
    private int circleRadius = 50;
    private ImageLoader imageLoader;
    public org.opencv.core.Point globalPoint;
    private JLabel label;
    boolean done = false;
    CardLayout cl;
    CLayout layout;
    JPanel panelContainer;

    public MainGameScreen( ImageLoader imgLoader, CLayout layout, final JPanel panelContainer ){
        addMouseListener(this);
        addMouseMotionListener(this);
        this.layout = layout;
        this.cl = layout.cl;
        this.panelContainer = panelContainer;

        setupCounter();

        flagImages = new DebrisFlagger[NUM_OBJECTS];
        randomX = new int[NUM_OBJECTS];
        randomY = new int[NUM_OBJECTS];

        imageLoader = imgLoader;
        // Read images into matrices
        aboveMat = imageLoader.getMatrix( "above" );
        belowMat = imageLoader.getMatrix("below");
        maskMat = imageLoader.getMatrix("mask");
        revealMask = imageLoader.getMatrix("reveal");
        destination = imageLoader.getMatrix("destination");
        fadedCircleMask = Mat.zeros(1080, 1920, 0);
        fadedCircleMat = imageLoader.getMatrix("above");

        // Load to BufferedImage
        destImage = imageLoader.getImage ( destination );

        placeFlags();

        this.setLayout(null);
        this.add(label);
        dragBoat = new DraggableBoat(1000, 500);
        dragPlane = new DraggablePlane(100, 200);

        // Setup Timer
        new TimeoutHandler( layout, panelContainer );
    }


    @Override
    public void update(Graphics g) {
        paintComponent(g);
    }
    // Displays the screen
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);

        // FOUND ALL
        if( done ){
            startGuessing();
        }
        updateCounterText();

        // Calculate the new pixels for the background
        if (globalPoint != null) {
            imageLoader.checkPixels(revealMask, destination, globalPoint, boatActive, planeActive);
            destination = imageLoader.addFade( fadedCircleMask, destination, globalPoint, planeActive, boatActive, fadedCircleMat );
        }
        // Render the new image
        destImage = imageLoader.getImage(destination);
        // Draw background
        g2.drawImage(destImage, 0, 0, null);
        // Draw flags
        if( globalDragEvent != null ){
            for( DebrisFlagger f : flagImages) {
                if (f.bounds.getBounds().contains(globalDragEvent.getPoint()) && !f.uncovered) {
                    f.uncovered = true;
                    foundCount++;
                }
                if( f.uncovered ){
                    g2.drawImage(f.img, f.x, f.y, null);
                    f.bounds.setBounds(f.x, f.y, f.width, f.height);
                }
            }
        }
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
        if( boatActive && !planeActive){
            if( !onLand && !onShallow)
                g2.rotate(Math.toRadians(angle), dragBoat.x + 100, dragBoat.y + 100);
            g2.drawImage(dragBoat.img, dragBoat.x, dragBoat.y, null);
            dragBoat.bounds.setBounds(dragBoat.x, dragBoat.y, dragBoat.width, dragBoat.height);
            // Restore to normal transformation
            g2.setTransform( oldXForm );
            g2.drawImage(dragPlane.img, dragPlane.x, dragPlane.y, null);
        }else if( planeActive && !boatActive){
            g2.rotate(Math.toRadians(angle), dragPlane.x + 100, dragPlane.y + 100);
            g2.drawImage(dragPlane.img, dragPlane.x, dragPlane.y, null);
            dragPlane.bounds.setBounds(dragPlane.x, dragPlane.y, dragPlane.width, dragPlane.height);
            // Restore to normal transformation
            g2.setTransform( oldXForm );
            g2.drawImage(dragBoat.img, dragBoat.x, dragBoat.y, null);
        }

        // WE ARE DONE HERE
        if( foundCount == NUM_OBJECTS ){
            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("counterBackgroundDone.png")));
            label.setIcon( icon );
            label.setText("<html>   All objects<br>   found!</html>");
            label.setHorizontalTextPosition(JLabel.CENTER);
            System.out.println("*** FOUND ALL *** ");
            done = true;
        }

    }

    public void setupCounter(){
        // Label in top right
        label = new JLabel();
        label.setSize(200, 100);
        label.setLocation(1700, 20);
        label.setOpaque(true);
        //Icon (background)
        ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("counterBackground.png")));
        label.setIcon( icon );
        // Set up the text
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", 1, 24));
        label.setText("<html>   Found: " + foundCount + "<br>Remaining: " + (NUM_OBJECTS - foundCount) + "</html>");
        label.setHorizontalTextPosition(JLabel.CENTER);
    }

    public void updateCounterText(){
//        if( done ){
//            ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("counterBackgroundDone.png")));
//            label.setIcon( icon );
//            label.setText("<html>   All objects found!</html>");
//        }else{
        label.setText( "<html>   Found: " + foundCount + "<br>Remaining: " + (NUM_OBJECTS-foundCount) + "</html>" );
        // }
        label.setHorizontalTextPosition(JLabel.CENTER);
    }

    public void placeFlags(){
        // Place random flags
//        Random randX = new Random();
//        Random randY = new Random();
//        int x, y;
//        for( int r = 0; r < NUM_OBJECTS; r++ ){
//            x = randX.nextInt( 1820 + 1 );
//            y = randY.nextInt( 980 + 1  );
//
//            // Make sure we don't place on land
//            double checkMask[] = maskMat.get( y, x );
//            if( checkMask[0] == 0.0 && checkMask[1] == 0.0 && checkMask[2] == 0.0){
//                System.out.println( "woops, on land" );
//                r--;
//            }else {
//                randomX[r] = x;
//                randomY[r] = y;
//            }
//        }
        // CURRENTLY USING FIXED POINTS JUST FOR DEMO PURPOSES:
        randomX[0] = 375;
        randomX[1] = 760;
        randomX[2] = 1100;
        randomX[3] = 1530;
        randomX[4] = 1090;

        randomY[0] = 300;
        randomY[1] = 750;
        randomY[2] = 800;
        randomY[3] = 480;
        randomY[4] = 280;

        // Print out the flag locations AND MAKE FLAGS
        for( int f = 0; f < flagImages.length; f++ ){
            System.out.println( randomX[f] + ", " +  randomY[f] );
            flagImages[f] = new DebrisFlagger(randomX[f], randomY[f]);
        }
        // Print out the flag locations
        for( int f = 0; f < flagImages.length; f++ ){
            //System.out.println( randomX[f] + ", " +  randomY[f] );
            flagImages[f] = new DebrisFlagger(randomX[f], randomY[f]);
        }
    }

    public void startGuessing(){
        try {
            Thread.sleep( 1500 );
            //this.dispose();
            // TODO -- right now we are making a brand new CardLayout, this sucks a lot.
            //layout.dispose();
            //layout = new CLayout();
            //cl = layout.cl;
            //panelContainer = layout.panelContainer;
            GuessingScreen guessingPanel = new GuessingScreen(this.layout, panelContainer);
            panelContainer.add(guessingPanel, "4");
            cl.show(panelContainer, "4");

            //layout.setVisible(true);
            //layout.setFullScreen( layout.dm );
            //layout.enableOSXFullscreen(layout);
            //cl.removeLayoutComponent((guessingPanel));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        globalDragEvent = e;
        if( dragBoat.bounds.getBounds().contains(e.getPoint())) {
            boatActive = true;
            planeActive = false;
            curX = e.getX();
            curY = e.getY();
            // Rotate every other pixel
            if( rotateBoat == 1 ){
                calculateRotation();
                rotateBoat = 0;
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
                if( values[0] == 128.0 && values[1] == 128.0 && values[2] == 128.0 ){
                    onShallow = true;
                }else onShallow = false;
                Core.circle(revealMask, mousePoint, circleRadius, new Scalar(255.0, 255.0, 255.0), -1, 0, 0);
                Core.circle(fadedCircleMask, mousePoint, circleRadius + 5, new Scalar(255.0, 255.0, 255.0), -1, 0, 0 );
            }
            // Make sure we are not dragging the boat over land or shallow water
            if( !onLand && !onShallow ) {
                dragBoat.x = curX - 100;
                if( dragBoat.x < 0 )
                    dragBoat.x = 0;
                if( dragBoat.x > 1920-150 )
                    dragBoat.x = 1920-150;

                dragBoat.y = curY - 100;
                if( dragBoat.y < 0 )
                    dragBoat.y = 0;
                if( dragBoat.y > 1080 )
                    dragBoat.y = 1080;
            }

            if( rotateBoat == 0 ){
                oldX = curX;
                oldY = curY;
            }
            rotateBoat++;
            repaint();
        }else if( dragPlane.bounds.getBounds().contains(e.getPoint() )){
            planeActive = true;
            boatActive = false;
            curX = e.getX();
            curY = e.getY();
            // Rotate every other pixel
            if( rotatePlane == 1 ){
                calculateRotation();
                rotatePlane = 0;
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
                if( values[0] == 128.0 && values[1] == 128.0 && values[2] == 128.0 ){
                    onShallow = true;
                }else onShallow = false;
                Core.circle(revealMask, mousePoint, circleRadius, new Scalar(255.0, 255.0, 255.0), -1, 0, 0);
                Core.circle(fadedCircleMask, mousePoint, circleRadius + 5, new Scalar(255.0, 255.0, 255.0), -1, 0, 0 );
            }
            dragPlane.x = curX - 100;
            if( dragPlane.x < 0 )
                dragPlane.x = 0;
            if( dragPlane.x > 1920-150 )
                dragPlane.x = 1920-150;

            dragPlane.y = curY - 100;
            if( dragPlane.y < 0 )
                dragPlane.y = 0;
            if( dragPlane.y > 1080 )
                dragPlane.y = 1080;

            if( rotatePlane == 0 ){
                oldX = curX;
                oldY = curY;
            }
            rotatePlane++;
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

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
