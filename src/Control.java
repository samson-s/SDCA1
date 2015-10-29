/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 620041195 640026665
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Control {
    private static int numberOfPlayers = 0;
    //constructor
    public Control(){}
    
    //methods
    /**
     * this method takes in and check the input of the number of players from the user.
     * @return 
     */
    public int numberOfPlayers(){
        // if the number of players < 2, this will be keep looping until 
        // a expected correct input
        while(numberOfPlayers < 2){
            //let user to type a number
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try{
                String input = reader.readLine();
                if ("x".equals(input)) System.exit(0);
                //this check the input is a int or not also change the input
                //type from string to int
                numberOfPlayers = Integer.parseInt(input);
            }catch(IOException | NumberFormatException e){
                System.out.println("Please enter a valid number that more then 1");
                continue;
            }
            if (numberOfPlayers < 2)
                System.out.println("Please enter a number that more then 1");
        }
        return numberOfPlayers;
    }
    
    /**
     * this method takes in a number and check the pebble has a positive weight or not
     * @param number
     * @throws NegativeWeightException 
     */
    private void checkWeight(int number) throws NegativeWeightException{
        if (number < 1)
            throw new NegativeWeightException();
    }
    /**
     * This method checks the pebbles in the csv file is 11 times of the number of players or not
     * @param pebbles
     * @throws PebblesNotEnoughForPlayerException 
     */
    private void checkPebblesRatio(int pebbles) throws PebblesNotEnoughForPlayerException{
        if (pebbles <= numberOfPlayers * 11)
            throw new PebblesNotEnoughForPlayerException();
    }
    
    /**
     * this method read the path from the input and and check weather is a correct type of file
     * and return the data in the file for further process
     * @return 
     */
    public ArrayList readFile(){
        ArrayList content = new ArrayList();
        int number;
        boolean done = false;
        //the while loop will keep looping until a correct input is given
        while(!done){
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String path = in.readLine();
                if(path.equals("x")) System.exit(0);
                BufferedReader reader = new BufferedReader(
                        new FileReader(path));
                String input ;
                //this read the number of the csv file everyline and replace the 
                //, with nothing and change the type of the content from string
                //to int and put them into a arraylist
                while ((input = reader.readLine()) != null){
                    try{
                        input = input.replace(",", "");
                    }catch(Exception e){}
                    number = Integer.parseInt(input);
                    checkWeight(number);
                    content.add(number);
                }
                checkPebblesRatio(content.size());
                done = true;
            }catch(IOException | NumberFormatException | NegativeWeightException 
                    | PebblesNotEnoughForPlayerException e){
                System.out.println(e);
                System.out.println("Please enter the path again.");
            }
        }
        return content;
    }
}
