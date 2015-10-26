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
        private static final int winningNum = 100;
        private int playerNum;
        private int temp;           //storing the number from the draw
        int hand[] = new int[10];
        int currentBag;
        public static Object lock = new Object();
        //constuctors

        /**
         *
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
        @Override
        public void run(){
            //the playing process
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
        
        public void start(){
            t = new Thread(this);
            t.start();
        }
        
        public void join(){
            try{
                t.join();
            }catch(Exception e){}
        }
        
        public synchronized void action(){
            synchronized(lock){
                draw();
                discard();
                countHand(); 
            }
        }
        
        synchronized public void firstTenDraw(){
            for(int i=0; i<10; i++){
                try{
                    hand[i] = pebbleBag.nextPebble(ran.nextInt(3)+1);
                }catch(Exception e){};
            }
        }
        
        synchronized public void draw() { 
            currentBag = ran.nextInt(3) + 1;
            boolean done = false;
            while(!done){
                try{
                    temp = pebbleBag.nextPebble(currentBag);
                    done = true;
                }catch(BagEmptyException e){
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
                fileWriter.write(output+"\r\n");
            } catch (IOException ex) {}
            System.out.println(output);
        }
        
        synchronized public void discard(){
            int position = ran.nextInt(10);
            int discard = hand[position];
            hand[position] = temp;
            pebbleBag.pebbleIn(discard, currentBag);
            String output;
            output = "player" + playerNum + " has discarded a " + discard
                   + " to bag"+ currentBag +" player" + playerNum + " hand is ";
            
            for(int i=0; i<9; i++){
                output = output + hand[i] + ", ";
            }
            output = output + hand[9];
            try {
                fileWriter.write(output+"\r\n");
            } catch (IOException ex) {}
            System.out.println(output);
        }
        
        synchronized public boolean countHand(){
            int sum = 0;
            for(int item:hand){
                sum += item;
            }
            if (sum == winningNum){
                someoneHasWon = true;
                return true;
            }else return false;
        }
        
        public void showHand(){
            for(int item: hand){
                System.out.println(item);
            }
        }
        
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
        
        PebbleBag pebbleBag = new PebbleBag(fileContent1, 
                fileContent2, fileContent3);
        
        FileWriter fileWriter;
        File file = new File("result.txt");
        fileWriter = new FileWriter(file);
        
        Player[] player = new Player[numberOfPlayers];
        for(int i=0;i<numberOfPlayers; i++){
            player[i] = new Player(i, pebbleBag, fileWriter);
            player[i].firstTenDraw();
            player[i].start();
        }
        for(int i=0;i<numberOfPlayers; i++){
            player[i].join();
        }
        System.out.println("player"+winner+" has won the game");
    }

}   


