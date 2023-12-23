import javax.swing.JFrame;

public class Main {
    //Frame contains basic options like 'Play' and 'Exit'.
    public static JFrame StartFrame = new JFrame();
    //This is the frame in which the real blackjack game will be played.
    public static JFrame Game = new JFrame();
    //we have the player score, which starts as 0.
    private static int PScore = 0;
    //we have the dealer score, which starts as 0.
    private static int DScore = 0;
    //we have the balance, which starts with 1000.
    public static int Balance = 500;
    //we initialize a 'Game' in order to control, start, and calculate the blackjack game.
    public static Game newGame = new Game(Game);
    //this boolean value will check if the game is newly started for the first time.
    private static boolean isFirstTime = true;
    //This enum represents the state of the game which is either menu or game.
    // While it is menu, we will show the user the menu. While it is game, we will show the user the game.
    public static enum STATE{
        MENU,
        GAME
    };

    public static STATE currentState = STATE.MENU; //the first state is the MENU state.

    public static void main(String[] args) throws InterruptedException {
        if(currentState == STATE.MENU) {
            openMenu(); //we open the menu by a static method.
        }
    }

    public static void openMenu() { //this method literally opens the menu.
        StartFrame.setTitle("BLACKJACK!"); //we set the frame title to 'BLACKJACK!'
        StartFrame.setSize(1130, 665);
        StartFrame.setLocationRelativeTo(null); //we position the frame to the center.
        StartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        StartFrame.setResizable(false); //we make it impossible for the user to resize the frame.

        StartPage beginningComponent = new StartPage(); //we initialize an OptionsComponent which is the JComponent that contains the properties (buttons, titles, etc.) of the menu.
        StartFrame.add(beginningComponent); //We add this component to the frame.
        StartFrame.setVisible(true); //we make the frame visible.
    }

    public static Thread gameRefreshThread = new Thread () { //this first thread (each thread must have a run method) serves to continuously [since we put true inside the while loop, it will continue forever.] refresh the component.
        public void run () {
            while(true){
                newGame.GameComponents.refresh(Balance, PScore, DScore -1, newGame.faceDown); //this line calls the refresh method of the GameComponent atmosphere component which is declared inside the Game class. This method updates the score and balance values.
                //the reason why we put the parameter dealerscore-1 is because dealerscore starts as 1 in our game.
            }
        }
    };

    public static Thread gameCheckThread = new Thread () { //the second thread continually[while(true)] checks the game for a round over situation.
        public void run () {
            while(true) {
                if (isFirstTime||newGame.roundOver) {//if this is the first time the game is started or the round is over (which was thanks to the checkHand method in Game),
                    System.out.println("Lets refresh the game!");
                    if (newGame.dealerWon){//if dealer won the game,
                        DScore++; //we add one to the score of dealer.
                        Balance -= GameTools.currentBet; //we also subtract the bet from the current balance.
                    }
                    else {//if dealer didn't win, then the user won.
                        PScore++; //we add one to the score of player.
                        Balance += GameTools.currentBet*2; //we add two times the bet to the current balance.
                    }
                    Game.getContentPane().removeAll(); //we remove everything from the frame.
                    newGame = new Game(Game); //we initialize a new game on the same frame.
                    newGame.formGame(); //we set the atmosphere of the game(which is everything except the cards.)

                    isFirstTime = false; //once this thread worked, the game can't be opening for the first time (because it already did before this thread executed) after this so we set this boolean value to false.
                }
            }
        }
    };
}

