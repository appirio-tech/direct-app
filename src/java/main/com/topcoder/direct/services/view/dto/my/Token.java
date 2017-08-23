/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.my;


/**
 * Model for jwt token
 */
public class Token {
    /**
     * Id
     */
    private String id;

    /**
     * Token
     */
    private String token;

    /**
     * Get Id
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Set Id
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get Token
     *
     * @return
     */
    public String getToken() {
        return token;
    }

    /**
     * Set Token
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }
}
