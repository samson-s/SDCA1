/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 620041195 640026665
 */
import java.util.*;
public class PebbleBag {
    
    // creat 6 array lists to store the pebbles
    ArrayList black1 = new ArrayList();
    ArrayList black2 = new ArrayList();
    ArrayList black3 = new ArrayList();
    ArrayList white1 = new ArrayList();
    ArrayList white2 = new ArrayList();
    ArrayList white3 = new ArrayList();
   
    /**
     * Adding element to the black bags from the csv file
     * @param list 
     * @param list1 
     * @param list2 
     */
public PebbleBag(ArrayList list, ArrayList list1, ArrayList list2){ 
      
        for (int x = 0; x < list.size() ; x++){

             black1.add(list.get(x));
        }
        
        for (int y = 0; y < list1.size(); y++){
            black2.add(list1.get(y));
        }
        
        for (int z = 0; z < list2.size(); z++){
            black3.add(list2.get(z));
        }
    }
    
    /** 
     * Get the random bag number from player 
     * and return the pebble to player
     * @param randomNo
     * @return 
     * @throws BagEmptyException
     */ 
    public synchronized int nextPebble(int randomNo) throws BagEmptyException{
        
        // using random number generator to create randdom number
        Random random = new Random();
        int temp;
        /** 
         * Check the bag is empty or not
         * If it is empty throw exception,
         * player will draw pebble from another bag
         * If it is not empty, player will draw the random pebble from this bag
         */
        switch(randomNo){
            
            case 1:
                if (black1.isEmpty()){
                    fill(randomNo);
                    throw new BagEmptyException();
                }
                temp = (int) black1.remove(random.nextInt(black1.size()));
                return temp;
            case 2:
                if (black2.isEmpty()){
                    fill(randomNo);
                    throw new BagEmptyException();
                }
                temp = (int) black2.remove(random.nextInt(black2.size()));
                return temp;
            default:
                if (black3.isEmpty()){
                    fill(randomNo);
                    throw new BagEmptyException();
                }
                temp = (int) black3.remove(random.nextInt(black3.size()));
                return temp;
        }   
    }
    
    /**
     * Add the Pebble to the corresponding white bag
     *
     * @param pebble
     * @param bagNo 
     */
    public synchronized void pebbleIn(int pebble, int bagNo){
        switch(bagNo){
            case 1:
                white1.add(pebble);
                break;
            case 2:
                white2.add(pebble);
                break;
            default:
                white3.add(pebble);
                break;
        }
    }
    
    /**
     * Fill the bag if the bag is empty.
     * The black bag is replaced by the corresponding white bag
     * and the create a new array list for the white bag
     * @param bagNo
     */
    public void fill(int bagNo){
        switch(bagNo){
            case 1:
                black1 = white1;
                white1 = new ArrayList();
                break;
            case 2:
                black2 = white2;
                white2 = new ArrayList();
                break;
            default:
                black3 = white3;
                white3 = new ArrayList();
        }
    }     
}


  
