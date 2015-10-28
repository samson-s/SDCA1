/**
 *
 * @author kwsh201 pyt201
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class App {
    private static int numberOfPlayers = 0;
    private static boolean someoneHasWon = false;
    private static int winner;
    
    public static class Player implements Runnable{
        Thread t;
        Random ran = new Random();
        FileWriter fileWriter;
        PebbleBag pebbleBag ;
        private static int firstTenDraw = 0 ;
        private static boolean allFirstTenDraw = false;
        private static final int winningNum = 700;
        private int playerNum;
        private int temp;           //storing the number from the draw
        int hand[] = new int[10];
        int currentBag;
        public static Object lock = new Object();
        //constuctors

        /**
         *This is the constuctor of the Player class
         * @param playerNum
         * @param pebbleBag
         * @param fileWriter
         */
        public Player(int playerNum, PebbleBag pebbleBag, FileWriter fileWriter){
            this.playerNum  = playerNum;
            this.pebbleBag  = pebbleBag;
            this.fileWriter = fileWriter;
        }
        
        //methods
        /**
         * This is the run() method which implemented the runnable run mehtod
         * This run method is the process of the player moves
         */
        @Override
        public void run(){
            //the playing process
            firstTenDraw();
            //this while loop is to wait for all players drawn their first 
            //ten pebbles before the game start
            while(!allFirstTenDraw){}
            //while there is no player won yet, they will keep doing the action
            //method
            while(!someoneHasWon){
                 action();
            }
            int sum = 0;
            //final caculating hand when someone has a winning hand
            for(int item : hand){
                sum += item;
            }
            if (sum == winningNum){
                winner = playerNum;
            }
        }
        /**
         * This is the start() method to start a new Thread and trigger the 
         * run() method to start the thread
         */
        public void start(){
            t = new Thread(this);
            t.start();
        }
        /**
         * This is the custom join() method to join the the threads when someone
         * has won the game
         */
        public void join(){
            try{
                t.join();
            }catch(Exception e){}
        }
        
        public void sleep(int time){
            try {
                t.sleep(time);
            } catch (InterruptedException ex) {}
        }
        /**
         * This method behaves like a player's action with a series for methods
         * written in the method so basically this is a method to give better
         * control of the flow
         */
        public void action(){
            synchronized(lock){
                if (!someoneHasWon){    
                    draw();
                    discard();
                    countHand();
                }
            }
        }
        /**
         * This is a mehthod to draw the first ten pebbles for the player
         */
        public void firstTenDraw(){
            synchronized(lock){    
                for(int i=0; i<10; i++){
                    //draw the fisrt ten pebbles from the bag
                    try{
                        hand[i] = pebbleBag.nextPebble(ran.nextInt(3)+1);
                    }catch(Exception e){};
                }
                //These are for making sure all players have draw 10 pebbles 
                //before starting the game
                //However if this is done before the thread start, it would be
                //easier and simpler
                firstTenDraw += 1;
                if (firstTenDraw == numberOfPlayers){
                   allFirstTenDraw = true;
                }
            }
        }
        /**
         * This is the method for drawing each time
         */
        public void draw() { 
            //select a random bag
            currentBag = ran.nextInt(3) + 1;
            boolean done = false;
            while(!done){
                try{
                    //try to get a pebble from that bag
                    temp = pebbleBag.nextPebble(currentBag);
                    done = true;
                }catch(BagEmptyException e){
                    //if that bag is empty, the player will get a pebble from 
                    //the next bag
                    if (currentBag == 3){
                       currentBag = 1;
                 }else
                     currentBag ++;
                }
            }
            String output;
            output = "player" + playerNum + " has drawn a " + temp +
                    " from bag " + currentBag + " player" + playerNum +" hand is ";
            for(int item : hand){
                output = output + item + ", ";
            }
            output = output + temp;
            try {
                //this write the output in the the text file
                fileWriter.write(output+"\r\n");
                fileWriter.flush();
            } catch (IOException ex) {}
            System.out.println(output);
        }
        /**
         * This is method to discard a pebble to return to the PebbleBag class
         */
        public void discard(){
            //choose a random position to store the pebble that is drawn
            int position = ran.nextInt(10);
            int discard = hand[position];
            hand[position] = temp;
            //the discarded pebble is that put back to pebbleBag class. 
            pebbleBag.pebbleIn(discard, currentBag);
            String output;
            output = "player" + playerNum + " has discarded a " + discard
                   + " to bag"+ currentBag +" player" + playerNum + " hand is ";
            
            for(int i=0; i<9; i++){
                output = output + hand[i] + ", ";
            }
            output = output + hand[9];
            try {
                //this write the output in the the text file
                fileWriter.write(output+"\r\n");
                fileWriter.flush();
            } catch (IOException ex) {}
            System.out.println(output);
        }
        
        /**
         * this is a method to sum up the pebbles and check weather they add to 100 or the 
         * desired winning number
         * @return 
         */
        public boolean countHand(){
            int sum = 0;
            for(int item:hand){
                sum += item;
            }
            if (sum == winningNum){
                someoneHasWon = true;
                return true;
            }else return false;
        }
        /**
         * This is a test method to show all the pebbles that the player is 
         * holding
         */
        public void showHand(){
            for(int item: hand){
                System.out.println(item);
            }
        }
        /**
         * this is a method to assign a player number to the player
         * @param number 
         */
        public void assignPlayerNumber(int number){
            playerNum = number;
        }
    }
    
    public static void main(String args[]) throws IOException{
        Control control = new Control();
        System.out.println("Hi, welcome to the Pebbles.");
        System.out.println("Please enter the number of players");
        
        //works with the input of number of players amd performs checks
        numberOfPlayers = control.numberOfPlayers();
        //Read the files
        System.out.println("Input the first csv file path.");
        ArrayList fileContent1 = control.readFile();
        System.out.println("Input the second csv file path.");
        ArrayList fileContent2 = control.readFile();
        System.out.println("Input the final csv file path.");
        ArrayList fileContent3 = control.readFile();
        
        //construct a pebbleBag object
        PebbleBag pebbleBag = new PebbleBag(fileContent1, 
                fileContent2, fileContent3);
        
        //construct a fileWriter object
        FileWriter fileWriter;
        File file = new File("result.txt");//this is the name of the file produced
        fileWriter = new FileWriter(file);
        
        //this is the thread pool for the numberOfPlayers input
        Player[] player = new Player[numberOfPlayers];
        for(int i=0;i<numberOfPlayers; i++){
            player[i] = new Player(i, pebbleBag, fileWriter);
            player[i].start();
        }
        //wait all players finish their threads then carry on
        for(int i=0;i<numberOfPlayers; i++){
            player[i].join();
        }
        String output = "player"+winner+" has won the game!\r\n";
        System.out.println(output);
        try{
            fileWriter.write(output);
            fileWriter.flush();
        }catch(Exception e){}
    }

}   


