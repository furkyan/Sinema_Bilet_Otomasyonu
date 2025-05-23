// VeritabaniException.java
package com.sinema.exception;

public class VeritabaniException extends Exception {
    public VeritabaniException(String message) {
        super(message);
    }
    
    public VeritabaniException(String message, Throwable cause) {
        super(message, cause);
    }
}

