package com.srp.order.exceptions;

public class GeneralException extends RuntimeException{

    public GeneralException(String message){
        super(message);
    }

    public GeneralException(String message, Throwable ex) {
        super(message, ex);
    }

    public static GeneralException getInstance(String message){
        return new GeneralException(message);
    }
    public static GeneralException getInstance(String message, Throwable ex){
        return new GeneralException(message, ex);
    }
}
