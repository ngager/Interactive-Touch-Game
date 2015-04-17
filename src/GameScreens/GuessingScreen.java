package GameScreens;

import Objects.GuessObjects;
import Objects.ScanObjects;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by danny on 2/26/15.
 */
public class GuessingScreen extends JPanel implements MouseListener{
    // Path to background image
    Image background = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("hurricane_template_guessing.png"));
    CardLayout cl;
    JPanel panelContainer;
    int imageX = 1295 + 10;
    int image1Y = 162 + 10, image2Y = 380 + 10, image3Y = 598 + 10, image4Y = 816 + 10;
    private final int NUM_OBJECTS = 5;
    BufferedImage leftScan;
    GuessObjects guesses[];
    int indeces[];

    String objectNames[] = { "dock", "house", "nets", "sailboat", "schoolbus", "tanker", "telpole", "train", "tree", "truck" };
    GuessObjects guessObjects[];
    ScanObjects scanObjects[];

    public GuessingScreen(CardLayout cl, JPanel panelContainer) {
        addMouseListener(this);
        this.setLayout(null);
        this.cl = cl;
        this.panelContainer = panelContainer;

        // Set up all of the game objects
        guesses = new GuessObjects[4];
        indeces = new int[4];
        guessObjects = new GuessObjects[10];
        scanObjects = new ScanObjects[10];
        int i = 0;
        for( String s : objectNames ){
            guessObjects[i] = new GuessObjects(s);
            scanObjects[i] = new ScanObjects(s);
            i++;
        }
        System.out.println("here");
        randomizeObjects();
    }

    public void randomizeObjects(){
        Random rand = new Random();
        int index, i = 0;
        while( true ){
            System.out.println( "i: " + i);
            index = rand.nextInt(10);
            if( !Arrays.asList(indeces).contains(index) ){
                indeces[i] = index;
                i++;
                if( i == 4 ) break;
            }
        }
        String name  = objectNames[indeces[0]];
        String name2 = objectNames[indeces[1]];
        String name3 = objectNames[indeces[2]];
        String name4 = objectNames[indeces[3]];

        for( int j : indeces )
            System.out.println( j );

        Random places = new Random();

        // Pick the object for the left
        for(ScanObjects s : scanObjects){
            if(s.getName().equals(name)){
                leftScan = s.img;
            }
        }
        // Pick the winning object for the right
        for(GuessObjects g : guessObjects ) {
            if (g.getName().equals(name)) {
                int place = places.nextInt(4);
                guesses[place] = g; //TODO repeating numbers here
            } else if (g.getName().equals(name2)) {
                int place = places.nextInt(4);
                guesses[place] = g;
            } else if (g.getName().equals(name3)) {
                int place = places.nextInt(4);
                guesses[place] = g;
            } else if (g.getName().equals(name4)) {
                int place = places.nextInt(4);
                guesses[place] = g;
            }
        }
    }

    // Very fast and easy way to set a background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        for( int i = 0; i < guesses.length; i++ ){
            if( guesses[i] != null ) {
                switch(i){
                    case 0:
                        g.drawImage(guesses[i].img, imageX, image1Y, guesses[i].width, guesses[i].height, this);
                        break;
                    case 1:
                        g.drawImage(guesses[i].img, imageX, image2Y, guesses[i].width, guesses[i].height, this);
                        break;
                    case 2:
                        g.drawImage(guesses[i].img, imageX, image3Y, guesses[i].width, guesses[i].height, this);
                        break;
                    case 3:
                        g.drawImage(guesses[i].img, imageX, image4Y, guesses[i].width, guesses[i].height, this);
                        break;
                    default:
                        break;
                }
            }
        }
        g.drawImage( leftScan, 500, 500, 400, 400, this);
    }

    // Don't even need to check mouse click coordinates because the buttons take care of it
    @Override
    public void mouseClicked(MouseEvent e) {
        int curX = e.getX();
        int curY = e.getY();

        if( curX > imageX && curX < (imageX+200) ){
            // BUTTON 1
            if( curY > image1Y && curY < (image1Y+200)) {
                System.out.println("BUTTON 1");
            }
            // BUTTON 2
            else if( curY > image2Y && curY < (image2Y+200)){
                System.out.println("BUTTON 2");
            }
            // BUTTON 3
            else if( curY > image3Y && curY < (image3Y+200)){
                System.out.println( "BUTTON 3" );
            }
            // BUTTON 4
            else if( curY > image4Y && curY < (image4Y+200)){
                System.out.println("BUTTON 4");
            }else{}
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
