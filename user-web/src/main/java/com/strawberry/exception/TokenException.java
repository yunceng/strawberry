package com.strawberry.exception;

public class TokenException extends RuntimeException{

    static final long serialVersionUID = -7034897190745766939L;

    public TokenException(){
        super();
    }

    public TokenException(String message){
        super(message);
    }

    public TokenException(String message, Throwable cause){
        super(message, cause);
    }

    public TokenException(Throwable cause){
        super(cause);
    }

}
