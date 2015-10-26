/**
 *
 * @author kwsh201 pyt201
 */
import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    private static int numberOfPlayers = 0;
    private static boolean someoneHasWon = false;
    private static int winner;
    
    public class Player implements Runnable{
        Thread t;
        Random ran = new Random();
        App app = new App();
        PebbleBag pebbleBag = new PebbleBag();
        private int playerNum;
        private int temp;           //storing the number from the draw
        int hand[] = new int[10];
        int currentBag;
        
        //constuctors
        public Player(){
        }
        
        public Player(int playerNum){
            this.playerNum = playerNum;
        }
        
        //methods
        @Override
        public void run(){
            while(!someoneHasWon){
                action();
            }
            int sum = 0;
            for(int item : hand){
                sum += item;
            }
            if (sum == 100){
                winner = playerNum;
            }
        }
        
        public void start(){
            t = new Thread();
            t.start();
        }
        
        public synchronized void action(){
            draw();
            discard();
            countHand();
            try{
                notifyAll();
                wait();
            }catch(Exception e){}
        }
        
        public synchronized void draw(){
            currentBag = ran.nextInt(3) + 1;
            boolean check = true;
            while(check){
                try{
                    temp = pebbleBag.nextPebble(currentBag);
                    check = false;
                }catch(BagEmptyException e){
                    if (currentBag == 3){
                       currentBag = 1;
                 }else
                     currentBag ++;
                }
            }
            System.out.print("player" + playerNum + "  has drawn a " + temp +
                    " from bag " + currentBag + " player" + playerNum +" is "         
            );
            for(int item : hand){
                System.out.print(item + ", ");
            }
            System.out.println(temp);
        }
        
        public synchronized void discard(){
            int position = ran.nextInt(10);
            int discard = hand[position];
            hand[position] = temp;
            pebbleBag.pebbleIn(discard, currentBag);
            System.out.print("player" + playerNum + " has discarded a " + discard
                    + " to bag"+ currentBag +" player" + playerNum + " hand is "
            );
            for(int item : hand){
                System.out.print(item + ", ");
            }
            System.out.println("");
        }
        
        public synchronized boolean countHand(){
            int sum = 0;
            for(int item:hand){
                sum += item;
            }
            if (sum == 100){
                someoneHasWon = true;
                return true;
            }else return false;
        }
    }
    
    public static void main(String args[]){
        System.out.println("Hi, welcome to the Pebbles.");
        System.out.println("Please enter the number of players");
        
        //works with the input of number of players amd performs checks
        while(numberOfPlayers < 2){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try{
                String input = reader.readLine();
                numberOfPlayers = Integer.parseInt(input);
            }catch(IOException | NumberFormatException e){
                System.out.println("Please enter a valid number that more then 1");
                continue;
            }
            if (numberOfPlayers < 2)
                System.out.println("Please enter a number that more then 1");
        }
        
        //Read the files
        
    }
}
