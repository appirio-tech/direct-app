/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * <p>
 * Mock implementation of <code>CallbackHandler</code> used in Unit tests.
 * </p>
 *
 * <p>
 * Used in UnitTests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockCallBackHandler implements CallbackHandler {
    /**
     * <p>
     * The username.
     * </p>
     */
    private final String user;

    /**
     * <p>
     * The password.
     * </p>
     */
    private final String pass;

    /**
     * <p>
     * Constructor that takes username and password.
     * </p>
     *
     * @param user The username.
     * @param pass The password.
     */
    public MockCallBackHandler(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    /**
     * <p>
     * Handle the call back.
     * Set the username and password with values got in constructor.
     * </p>
     *
     * @param callbacks The array of <code>Callback</code>s to handle.
     *
     * @throws IOException If any <code>Callback</code> is type of <code>FailedNameCallback</code>.
     * @throws UnsupportedCallbackException If any <code>Callback</code> is type of <code>FailedPasswordCallback</code>
     *         , or is not type of <code>NameCallback</code> or <code>PasswordCallback</code>.
     */
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        if (callbacks == null) {
            return;
        }
        int len = callbacks.length;
        Callback cb;

        for (int i = 0; i < len; i++) {
            cb = callbacks[i];

            if (cb == null) {
                continue;
            }
            if (cb instanceof FailedNameCallback) {
                throw new IOException("FailedNameCallback");
            }
            if (cb instanceof FailedPasswordCallback) {
                throw new UnsupportedCallbackException(cb, "FailedPasswordCallback");
            }

            if (cb instanceof NameCallback) {
                NameCallback ncb = (NameCallback) cb;
                ncb.setName(user);
            } else if (cb instanceof PasswordCallback) {
                PasswordCallback pcb = (PasswordCallback) cb;
                pcb.setPassword(pass == null ? null : pass.toCharArray());
            } else {
                throw new UnsupportedCallbackException(cb, "Don't know what to do with this!!");
            }
        } //end of for-loop
    }
}
