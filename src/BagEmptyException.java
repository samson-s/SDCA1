/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hui Ka Wang
 */
public class BagEmptyException extends Exception {
    public BagEmptyException(){}
    public BagEmptyException(String message){
        super(message);
    }
}
