package com.topcoder.direct.services.view.util.jwt;

public class JWTEndpointException extends JWTException {
    public JWTEndpointException(String message) {
        super(message);
    }

    public JWTEndpointException(String message, Throwable cause) {
        super(message, cause);
    }
}
