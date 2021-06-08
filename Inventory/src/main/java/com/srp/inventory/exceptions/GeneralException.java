package com.srp.inventory.exceptions;

public class GeneralException extends RuntimeException{

    public GeneralException(String message){
        super(message);
    }

    public GeneralException(String message, Exception ex){
        super(message, ex);
    }

    public static GeneralException getInstance(String message, Exception ex){
        return new GeneralException(message, ex);
    }
    public static GeneralException getInstance(String message){
        return new GeneralException(message);
    }
}
