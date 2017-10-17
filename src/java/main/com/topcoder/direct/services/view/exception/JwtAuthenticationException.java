package com.topcoder.direct.services.view.exception;

import com.topcoder.util.errorhandling.BaseException;

public class JwtAuthenticationException extends BaseException {
    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
