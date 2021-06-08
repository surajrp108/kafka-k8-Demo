package com.srp.product.exceptions;

public class GeneralException extends RuntimeException{

    public GeneralException(String message){
        super(message);
    }

    public static GeneralException getInstance(String message){
        return new GeneralException(message);
    }
}
