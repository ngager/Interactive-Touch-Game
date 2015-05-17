package GameScreens;

import Objects.GuessObjects;
import Objects.ScanObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by danny on 2/26/15.
 */
public class GuessingScreen extends JPanel implements MouseListener{
    Image background;
    CLayout layout;
    CardLayout cl;
    JPanel panelContainer;
    int imageX = 1295;
    int image1Y = 162 , image2Y = 380 , image3Y = 598 , image4Y = 816 ;
    int leftScanX = 356;
    int leftScanY = 426;
    private final int NUM_GUESSES = 5;
    private final int NUM_SCANS_AND_OBJ = 30;
    int pastIndex = 0;
    int userScore = 0;
    BufferedImage leftScanImage;
    int leftScanKey;
    GuessObjects guessOptions[];
    int indeces[];
    int guessIndeces[];
    String objectNames[];
    GuessObjects allGuesses[];
    ScanObjects allScans[];
    ScanObjects leftScan;
    int pastLeftScans[];

    public GuessingScreen(final CLayout layout, final JPanel panelContainer) {
        addMouseListener(this);
        this.setLayout(null);
        this.layout = layout;
        this.cl = layout.cl;
        this.panelContainer = panelContainer;
        // Path to the background image
        background =  Toolkit.getDefaultToolkit().createImage(layout.getDirectory("menu") + "guessing_background.png");
        // Set up all of the game objects
        pastLeftScans = new int[NUM_SCANS_AND_OBJ];
        guessOptions = new GuessObjects[NUM_GUESSES - 1];
        indeces = new int[NUM_GUESSES - 1];
        guessIndeces = new int[NUM_GUESSES - 1];
        allGuesses = new GuessObjects[NUM_SCANS_AND_OBJ];
        allScans = new ScanObjects[NUM_SCANS_AND_OBJ];
        objectNames = new String[NUM_SCANS_AND_OBJ];

        // Guess objects
        String guessDir = layout.getDirectory("guess");
        File guessDirectory = new File(guessDir);
        for( int f = 0; f < guessDirectory.listFiles().length; f++ ) {
            String gName = guessDirectory.listFiles()[f].toString();
            allGuesses[f] = new GuessObjects(gName, f);
        }
        
        // Scan objects
        String scanDir = layout.getDirectory("scans");
        File scanDirectory = new File(scanDir);
        for( int f = 0; f < scanDirectory.listFiles().length; f++ ) {
            String sName = scanDirectory.listFiles()[f].toString();
            allScans[f] = new ScanObjects(sName, f);
        }

        randomizeObjects();

        // Setup Timer
        //new InactivityListener( layout, panelContainer );
        Action reset = new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                cl.show(panelContainer, "1");
            }
        };
        new InactivityListener(this.layout, reset, 1).start();
    }

    public void randomizeObjects(){
        Random rand = new Random();
        // Create random indeces to grab random guess objects
        int index, i = 0;
        while( true ){
            index = rand.nextInt(NUM_SCANS_AND_OBJ);
            if( indeces[0] != index && indeces[1] != index && indeces[2] != index && indeces[3] != index ){
                indeces[i] = index;
                i++;
                if( i == 4 ) break;
            }
        }
        // Save the names of each one
        String scanName  = allScans[indeces[0]].getName();
        String scanName2 = allScans[indeces[1]].getName();
        String scanName3 = allScans[indeces[2]].getName();
        String scanName4 = allScans[indeces[3]].getName();

        String guessName  = allGuesses[indeces[0]].getName();
        String guessName2 = allGuesses[indeces[1]].getName();
        String guessName3 = allGuesses[indeces[2]].getName();
        String guessName4 = allGuesses[indeces[3]].getName();
        
        // Pick the object for the left
        for(ScanObjects s : allScans){
            if(s.getName().equals(scanName) || s.getName().equals(scanName2)|| s.getName().equals(scanName3) || s.getName().equals(scanName4)){
                if( pastIndex > 0 ){
                    if( !(Arrays.asList(pastLeftScans).contains(s.getName())) ){
                        leftScan = s;
                        leftScanImage = s.img;
                        leftScanKey = s.getKey();
                    }
                }else{
                    leftScan = s;
                    leftScanImage = s.img;
                    leftScanKey = s.getKey();
                }
            }
        }

        // Create the random INDECES 1-4 IN WHICH THE OBJECTS WILL APPEAR ON SCREEN
        i = 0;
        while( true ){
            index = rand.nextInt(NUM_GUESSES - 1);
            if( guessIndeces[0] != index && guessIndeces[1] != index && guessIndeces[2] != index && guessIndeces[3] != index ){
                guessIndeces[i] = index;
                i++;
                if( i == 3 ) break; //TODO this part seems sketchy
            }
        }

        // Pick the winning object for the right
        for(GuessObjects g : allGuesses) {
            if (g.getName().equals(guessName)) {
                guessOptions[guessIndeces[0]] = g;
                System.out.println( "0: " + g.getName() );
            } else if (g.getName().equals(guessName2)) {
                guessOptions[guessIndeces[1]] = g;
                System.out.println( "1: " + g.getName() );
            } else if (g.getName().equals(guessName3)) {
                guessOptions[guessIndeces[2]] = g;
                System.out.println( "2: " + g.getName() );
            } else if (g.getName().equals(guessName4)) {
                guessOptions[guessIndeces[3]] = g;
                System.out.println( "3: " + g.getName() );
            }
        }
    }

    // Reset everything and rebuild the screen with new objects!
    public void newRound(){
        pastLeftScans[pastIndex] = leftScanKey;
        pastIndex++;
        if( pastIndex == (NUM_GUESSES)){
            System.out.println("CALCULATE THE RESULTS NOW");
            layout.passResults(userScore, NUM_GUESSES);
            layout.cl.show(panelContainer, "3");
            leftScanImage = null;
            guessOptions = new GuessObjects[NUM_GUESSES - 1];
            indeces = new int[NUM_GUESSES - 1];
            guessIndeces = new int[NUM_GUESSES - 1];
            pastIndex = 0;
        }else{
            leftScanImage = null;
            guessOptions = new GuessObjects[NUM_GUESSES - 1];
            indeces = new int[NUM_GUESSES - 1];
            guessIndeces = new int[NUM_GUESSES - 1];
            randomizeObjects();
            repaint();
        }
    }

    // Very fast and easy way to set a background image
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        g2.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        for( int i = 0; i < guessOptions.length; i++ ){
            if( guessOptions[i] != null ) {
                // Switch to determine which Y coordinate to use while drawing the image
                switch(i){
                    case 0:
                        g2.drawImage(guessOptions[guessIndeces[i]].img, imageX, image1Y, guessOptions[guessIndeces[i]].width, guessOptions[guessIndeces[i]].height, this);
                        break;
                    case 1:
                        g2.drawImage(guessOptions[guessIndeces[i]].img, imageX, image2Y, guessOptions[guessIndeces[i]].width, guessOptions[guessIndeces[i]].height, this);
                        break;
                    case 2:
                        g2.drawImage(guessOptions[guessIndeces[i]].img, imageX, image3Y, guessOptions[guessIndeces[i]].width, guessOptions[guessIndeces[i]].height, this);
                        break;
                    case 3:
                        g2.drawImage(guessOptions[guessIndeces[i]].img, imageX, image4Y, guessOptions[guessIndeces[i]].width, guessOptions[guessIndeces[i]].height, this);
                        break;
                    default:
                        break;
                }
            }
        }
        g2.drawImage(leftScanImage, leftScanX, leftScanY, 400, 400, this);
    }

    // Don't even need to check mouse click coordinates because the buttons take care of it
    @Override
    public void mouseClicked(MouseEvent e) {
        int curX = e.getX();
        int curY = e.getY();

        if( curX > imageX && curX < (imageX+200) ){
            // BUTTON 1
            if( curY > image1Y && curY < (image1Y+200)) {
                if( guessOptions[guessIndeces[0]].getKey() == leftScanKey ){
                    System.out.println("RIGHT");
                    userScore++;
                }else System.out.println( "WRONG" );
            }
            // BUTTON 2
            else if( curY > image2Y && curY < (image2Y+200)){
                if( guessOptions[guessIndeces[1]].getKey() == leftScanKey ){
                    System.out.println( "RIGHT" );
                    userScore++;
                }else System.out.println( "WRONG" );
            }
            // BUTTON 3
            else if( curY > image3Y && curY < (image3Y+200)){
                if( guessOptions[guessIndeces[2]].getKey() == leftScanKey ){
                    System.out.println( "RIGHT" );
                    userScore++;
                }else System.out.println( "WRONG" );
            }
            // BUTTON 4
            else if( curY > image4Y && curY < (image4Y+200)){
                if( guessOptions[guessIndeces[3]].getKey() == leftScanKey ){
                    System.out.println( "RIGHT" );
                    userScore++;
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
