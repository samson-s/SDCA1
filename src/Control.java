/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hui Ka Wang
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Control {
    //constructor
    public Control(){}
    
    //methods
    public int numberOfPlayers(){
        int numberOfPlayers = 0;
        while(numberOfPlayers < 2){
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try{
                String input = reader.readLine();
                if ("x".equals(input)) System.exit(0);
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
    
    private void checkWeight(int number) throws NegativeWeightException{
        if (number < 1)
            throw new NegativeWeightException();
    }
    
    public ArrayList readFile(){
        ArrayList content = new ArrayList();
        int number;
        boolean done = false;
        while(!done){
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String path = in.readLine();
                if(path.equals("x")) System.exit(0);
                BufferedReader reader = new BufferedReader(
                        new FileReader(path));
                String input ;
                while ((input = reader.readLine()) != null){
                    try{
                        input = input.replace(",", "");
                    }catch(Exception e){}
                    number = Integer.parseInt(input);
                    checkWeight(number);
                }
                done = true;
            }catch(IOException | NumberFormatException | NegativeWeightException e){
                System.out.println(e);
            }
        }
        return content;
    }
}
