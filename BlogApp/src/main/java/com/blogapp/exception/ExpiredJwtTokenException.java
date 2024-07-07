package com.blogapp.exception;

public class ExpiredJwtTokenException extends RuntimeException{
    public ExpiredJwtTokenException(String message){
        super(message);
    }
}
