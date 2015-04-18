package GameScreens;

import Objects.GuessObjects;
import Objects.ScanObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
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
    String leftScanName;
    GuessObjects guessOptions[];
    int indeces[];
    int guessIndeces[];
    String objectNames[] = { "dock", "house", "nets", "sailboat", "schoolbus", "tanker", "telpole", "train", "tree", "truck" };
    GuessObjects allGuesses[];
    ScanObjects allScans[];
    String pastLeftScans[];
    int pastIndex = 0;

    public GuessingScreen(CardLayout cl, JPanel panelContainer) {
        addMouseListener(this);
        this.setLayout(null);
        this.cl = cl;
        this.panelContainer = panelContainer;

        // Set up all of the game objects
        pastLeftScans = new String[10];
        guessOptions = new GuessObjects[NUM_OBJECTS - 1];
        indeces = new int[NUM_OBJECTS - 1];
        guessIndeces = new int[NUM_OBJECTS - 1];
        allGuesses = new GuessObjects[10];
        allScans = new ScanObjects[10];
        int i = 0;
        for( String s : objectNames ){
            allGuesses[i] = new GuessObjects(s);
            allScans[i] = new ScanObjects(s);
            i++;
        }
        randomizeObjects();
    }

    public void randomizeObjects(){
        Random rand = new Random();
        // First, pick the 4 random objects to be shown as options.
        int index, i = 0;
        while( true ){
            index = rand.nextInt(10);
            if( indeces[0] != index && indeces[1] != index && indeces[2] != index && indeces[3] != index ){
                indeces[i] = index;
                i++;
                if( i == 4 ) break;
            }
        }
        // Save the names of each one
        String name  = objectNames[indeces[0]];
        String name2 = objectNames[indeces[1]];
        String name3 = objectNames[indeces[2]];
        String name4 = objectNames[indeces[3]];

        System.out.println("--- Objects ---");
        System.out.println(name);
        System.out.println( name2 );
        System.out.println( name3 );
        System.out.println( name4 );
        System.out.println( "---------------" );

        // Pick the object for the left
        for(ScanObjects s : allScans){
            if(s.getName().equals(name) || s.getName().equals(name2)|| s.getName().equals(name3) || s.getName().equals(name4)){
                if( pastIndex > 0 ){
                    if( !(Arrays.asList(pastLeftScans).contains(s.getName())) ){
                        leftScan = s.img;
                        leftScanName = s.getName();
                    }
                }else{
                    leftScan = s.img;
                    leftScanName = s.getName();
                }
            }
        }
        // Create the random indeces for each guess object
        i = 0;
        while( true ){
            index = rand.nextInt(NUM_OBJECTS - 1);
            if( guessIndeces[0] != index && guessIndeces[1] != index && guessIndeces[2] != index && guessIndeces[3] != index ){
                guessIndeces[i] = index;
                i++;
                if( i == 3 ) break; //TODO this part seems sketchy to me
            }
        }

        // Pick the winning object for the right
        for(GuessObjects g : allGuesses) {
            if (g.getName().equals(name)) {
                guessOptions[guessIndeces[0]] = g;
                System.out.println( "0: " + g.getName() );
            } else if (g.getName().equals(name2)) {
                guessOptions[guessIndeces[1]] = g;
                System.out.println( "1: " + g.getName() );
            } else if (g.getName().equals(name3)) {
                guessOptions[guessIndeces[2]] = g;
                System.out.println( "2: " + g.getName() );
            } else if (g.getName().equals(name4)) {
                guessOptions[guessIndeces[3]] = g;
                System.out.println( "3: " + g.getName() );
            }
        }
    }

    // Reset everything and rebuild the screen with new objects!
    public void newRound(){
        pastLeftScans[pastIndex] = leftScanName;
        pastIndex++;
        if( pastIndex == (NUM_OBJECTS)){
            System.out.println( "CALCULATE THE RESULTS NOW" );
        }
        leftScan = null;
        leftScanName = "";
        guessOptions = new GuessObjects[NUM_OBJECTS - 1];
        indeces = new int[NUM_OBJECTS - 1];
        guessIndeces = new int[NUM_OBJECTS - 1];
        randomizeObjects();
        repaint();
    }

    // Very fast and easy way to set a background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        for( int i = 0; i < guessOptions.length; i++ ){
            if( guessOptions[i] != null ) {
                // Switch to determine which Y coordinate to use while drawing the image
                switch(i){
                    case 0:
                        g.drawImage(guessOptions[guessIndeces[i]].img, imageX, image1Y, guessOptions[guessIndeces[i]].width, guessOptions[guessIndeces[i]].height, this);
                        break;
                    case 1:
                        g.drawImage(guessOptions[guessIndeces[i]].img, imageX, image2Y, guessOptions[guessIndeces[i]].width, guessOptions[guessIndeces[i]].height, this);
                        break;
                    case 2:
                        g.drawImage(guessOptions[guessIndeces[i]].img, imageX, image3Y, guessOptions[guessIndeces[i]].width, guessOptions[guessIndeces[i]].height, this);
                        break;
                    case 3:
                        g.drawImage(guessOptions[guessIndeces[i]].img, imageX, image4Y, guessOptions[guessIndeces[i]].width, guessOptions[guessIndeces[i]].height, this);
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
                if( guessOptions[0].getName() == leftScanName ){
                    System.out.println("RIGHT");
                }else System.out.println( "WRONG" );
            }
            // BUTTON 2
            else if( curY > image2Y && curY < (image2Y+200)){
                if( guessOptions[1].getName() == leftScanName ){
                    System.out.println( "RIGHT" );
                }else System.out.println( "WRONG" );
            }
            // BUTTON 3
            else if( curY > image3Y && curY < (image3Y+200)){
                if( guessOptions[2].getName() == leftScanName ){
                    System.out.println( "RIGHT" );
                }else System.out.println( "WRONG" );
            }
            // BUTTON 4
            else if( curY > image4Y && curY < (image4Y+200)){
                if( guessOptions[3].getName() == leftScanName ){
                    System.out.println( "RIGHT" );
                }else System.out.println( "WRONG" );
            }
            newRound();
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
