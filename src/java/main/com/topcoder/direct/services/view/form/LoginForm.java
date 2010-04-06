/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * <p>A form bean providing the data submitted by user for authentication.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LoginForm implements Serializable {

    /**
     * <p>A <code>String</code> providing the username submitted by user.</p>
     */
    private String username;

    /**
     * <p>A <code>String</code> providing the password submitted by user.</p>
     */
    private String password;

    /**
     * <p>A <code>boolean</code> flag indicating whether user selected <code>Remember Me</code> option or not.</p>
     */
    private boolean remember;

    /**
     * <p>Constructs new <code>LoginForm</code> instance. This implementation does nothing.</p>
     */
    public LoginForm() {
    }

    /**
     * <p>Gets the username submitted by user for authentication.</p>
     *
     * @return a <code>String</code> providing the username submitted by user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * <p>Sets the username submitted by user for authentication.</p>
     *
     * @param username a <code>String</code> providing the username submitted by user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * <p>Gets the password submitted by user for authentication.</p>
     *
     * @return a <code>String</code> providing the password submitted by user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * <p>Sets the password submitted by user for authentication.</p>
     *
     * @param password a <code>String</code> providing the password submitted by user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>Gets the flag indicating whether user selected <code>Remember Me</code> option or not.</p>
     *
     * @return a <code>boolean</code> flag indicating whether user selected <code>Remember Me</code> option or not.
     */
    public boolean isRemember() {
        return remember;
    }

    /**
     * <p>Sets the flag indicating whether user selected <code>Remember Me</code> option or not.</p>
     *
     * @param remember a <code>boolean</code> flag indicating whether user selected <code>Remember Me</code> option or
     *        not.
     */
    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
