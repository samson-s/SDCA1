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
    ArrayList black1 = new ArrayList();
    ArrayList black2 = new ArrayList();
    ArrayList black3 = new ArrayList();
    ArrayList white1 = new ArrayList();
    ArrayList white2 = new ArrayList();
    ArrayList white3 = new ArrayList();
    
    
    public synchronized int nextPebble(int randomNo) throws BagEmptyException{
        Random random = new Random();
        
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
     *
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


  
