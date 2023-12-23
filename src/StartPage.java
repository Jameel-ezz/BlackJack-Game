import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;


public class StartPage extends JComponent implements ActionListener{

    private JButton btnPlay = new JButton("PLAY"); //we initialize 4 buttons for the menu component, which will open up first in our game.
    private JButton btnExit = new JButton("EXIT");
    private JButton btnHelp = new JButton("HELP");
    private JButton btnInfo = new JButton("INFO");
    private static BufferedImage backgroundImage; //we initialize an image which will serve as the background image.

    public StartPage() { //in the default constructor of this class,
        btnPlay.addActionListener(this); //we add action listeners to all buttons to control what will happen when the user clicks them.
        btnExit.addActionListener(this);
        btnHelp.addActionListener(this);
        btnInfo.addActionListener(this);
    }

    public void paintComponent(Graphics g) {//we will decorate the component with this method.

        Graphics2D g2 = (Graphics2D) g; //we first cast Graphics g to Graphics2D g2 in order to use a more powerful brush.

        try {
            backgroundImage = ImageIO.read(new File("Pictures/Capture.JPG")); //we read a file which is the png image of a poker table for our background image.
        }
        catch(IOException e) {}

        g2.drawImage(backgroundImage, 0, 0, null); //we draw the background image to the component.

        g2.setFont(new Font("Comic Sans MS", Font.BOLD, 100)); //In these codes, we will add the title of our game and its font and color.
        g2.setColor(Color.BLACK);
        g2.drawString("Welcome", 380, 100);
        g2.drawString("to", 530, 180);
        g2.drawString("BLACKJACK!", 290, 260);

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.drawString("HERE IS JAMEEL'S PROJECT FOR CS-201", 220, 580); //Here, we add a small description to the component.

        btnPlay.setBounds(500, 300, 150, 80); //we set the bounds of the buttons.
        btnExit.setBounds(500, 400, 150, 80);
        btnHelp.setBounds(80, 75, 150, 80);
        btnInfo.setBounds(900, 75, 150, 80);

        btnPlay.setFont(new Font("Comic Sans MS", Font.BOLD, 40)); //we set the fonts of writings on the buttons.
        btnExit.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        btnHelp.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        btnInfo.setFont(new Font("Comic Sans MS", Font.BOLD, 40));

        super.add(btnPlay); //super refers to the JComponent. Thus, with these codes, we add the four buttons to the component.
        super.add(btnExit);
        super.add(btnHelp);
        super.add(btnInfo);
    }

    public void actionPerformed(ActionEvent e) {//we will control the button actions here.
        JButton selectedButton = (JButton)e.getSource();//we will assign the clicked button which created an action event to the JButton selectedButton. It is the selected button.

        if(selectedButton == btnExit) { //if the selected button is the exit button (btnExit), we will exit the game.
            System.exit(0); //this line is to exit the program.
        }
        else if(selectedButton == btnPlay) {//if the selected button is the play button (btnPlay), we start the game.
            Main.currentState = Main.STATE.GAME; //we equalize the current state to STATE.GAME where STATE was the enum declared in the Tester class. Because we will no longer be in the menu, we will jump to the game.
            Main.StartFrame.dispose(); //we first get rid of the menu frame and the components it has.
            Main.gameRefreshThread.start(); //then, simultaneously, we start our two threads and these run at the same time.
            Main.gameCheckThread.start();
            //playAmbienceMusic(); //we play the ambience music which gives a sweet background casino noise.
        }
        else if(selectedButton == btnHelp) {//if the selected button is the help button (btnHelp), we open up a J Option Pane that will contain information about the game.
            JOptionPane.showMessageDialog(this, "Black Jack is an interactive card game against the dealer (computer)" +
                            "\nIn this game, the player (user) has the option of dealing cards and seeing those random cards from a set on the screen. " +
                            "\nThe player can see both of his/her cards and only the face-up card of the dealer." +
                            "\nNext, the player has the choice to hit or stay in an attempt to get the closest to twenty-one without going over." +
                            "\nOnce the stay button is pressed, the dealer chooses to hit or stay, and the cards are revealed and the winner is chosen." +
                            "\nThe dealer must hit on sixteen or fewer and the player can hit until he/she goes over which automatically makes the dealer win." +
                            "\nAces are reduced to one if they make either hand go over twenty-one. Incorrect choices are not allowed to be clicked and the" +
                            " correct buttons are highlighted when ready.", "QUICK&EASY BLACKJACK HELP",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else if(selectedButton == btnInfo) {//if the selected button is the info button (btnInfo), we open up a J Option Pane that will contain information about the program.
            JOptionPane.showMessageDialog(this, "This is jameel's project For Cs-201" +
                    "\nI was submitted in 2023", "INFORMATION", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}