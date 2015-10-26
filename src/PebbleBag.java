/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kwsh201 pyt201
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
     */
    public PebbleBag(ArrayList list){
        int size = (list.size()/3);  
        for (int x = 0; x < size ; x++){
             black1.add(list.get(x));
        }
        for (int y = size; y < 2*size; y++){
            black2.add(list.get(y));
        }
        for (int z = 2*size; z < (list.size()); z++){
            black3.add(list.get(z));
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
                return (int) black1.get(random.nextInt(black1.size()+1));
            case 2:
                if (black2.isEmpty()){
                    fill(randomNo);
                    throw new BagEmptyException();
                }
                return (int) black2.get(random.nextInt(black2.size()+1));
            default:
                if (black3.isEmpty()){
                    fill(randomNo);
                    throw new BagEmptyException();
                }
                return (int) black3.get(random.nextInt(black3.size()+1));
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
            case 2:
                black2 = white2;
                white2 = new ArrayList();
            default:
                black3 = white3;
                white3 = new ArrayList();
        }
    }     
}


  
