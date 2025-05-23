// KoltukDoluException.java
package com.sinema.exception;

public class KoltukDoluException extends Exception {
    public KoltukDoluException(String message) {
        super(message);
    }
    
    public KoltukDoluException(String message, Throwable cause) {
        super(message, cause);
    }
}

