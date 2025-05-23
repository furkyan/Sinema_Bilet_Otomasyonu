// GirisHatasiException.java
package com.sinema.exception;

public class GirisHatasiException extends Exception {
    public GirisHatasiException(String message) {
        super(message);
    }
    
    public GirisHatasiException(String message, Throwable cause) {
        super(message, cause);
    }
}