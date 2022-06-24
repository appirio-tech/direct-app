/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util.jwt;

import com.topcoder.util.errorhandling.BaseException;

/**
 * Base exception for Jwt token
 */
public class JWTException extends BaseException {
    public JWTException(String message) {
        super(message);
    }

    public JWTException(String message, Throwable cause) {
        super(message, cause);
    }
}
