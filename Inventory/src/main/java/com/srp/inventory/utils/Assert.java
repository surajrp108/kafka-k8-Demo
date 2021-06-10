package com.srp.inventory.utils;

public class Assert {
    public static void isTrue(boolean value, String failMessage){
        if(!value){
            new IllegalArgumentException(failMessage);
        }
    }
}
