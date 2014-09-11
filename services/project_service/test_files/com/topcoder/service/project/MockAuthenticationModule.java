/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project;

import com.topcoder.security.authenticationmanager.AuthenticationException;
import com.topcoder.security.authenticationmanager.AuthenticationModule;
import com.topcoder.security.authenticationmanager.AuthenticationModuleResponse;
import com.topcoder.security.authenticationmanager.AuthenticationModuleResponseType;
import com.topcoder.security.authenticationmanager.AuthenticationModuleType;
import com.topcoder.security.authenticationmanager.Credentials;
import com.topcoder.security.authenticationmanager.InvalidCredentialsException;
import com.topcoder.security.authenticationmanager.SecurityToken;
import com.topcoder.security.authenticationmanager.credentials.UserPasswordCredentials;
import com.topcoder.util.errorhandling.ExceptionUtils;

/**
 * <p>
 * Mock implementation for <code>AuthenticationModule</code>.
 * </p>
 * <p>
 * <strong>Thread Safety:</strong> This class is thread-safe by immutable states and synchronized methods on
 * mutable state.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockAuthenticationModule implements AuthenticationModule {
    /**
     * if the password equals this , the authentication will success.
     */
    public static final String AUTHENTICATION_SUCCESS = "success";

    /**
     * if the password equals this , the authentication will fail.
     */
    public static final String AUTHENTICATION_FAIL = "fail";

    /**
     * if the password equals this , the authentication will fail and exception will be thrown.
     */
    public static final String AUTHENTICATION_EXCEPTION = "exception";

    /**
     * <p>
     * Module type.
     * </p>
     */
    private final AuthenticationModuleType type;

    /**
     * <p>
     * Module name.
     * </p>
     */
    private final String uniqueName;

    /**
     * <p>
     * Constructs a new <code>MockAuthenticationModule</code>.
     * </p>
     * @param type
     *            The module type.
     * @param uniqueName
     *            The module name.
     * @throws IllegalArgumentException
     *             If type is null or empty or not recognized.
     */
    public MockAuthenticationModule(String type, String uniqueName) {

        if (uniqueName == null || uniqueName.trim().length() == 0) {
            throw new IllegalArgumentException("from mock method");
        }
        this.type = AuthenticationModuleType.REQUIRED;
        this.uniqueName = "mockModuleName";
    }

    /**
     * <p>
     * Gets module type.
     * </p>
     * @return The module type. Will never be null.
     */
    public AuthenticationModuleType getType() {
        return this.type;
    }

    /**
     * <p>
     * Gets module name.
     * </p>
     * @return The module name.
     */
    public String getUniqueName() {
        return this.uniqueName;
    }

    /**
     * <p>
     * Perform authentication.
     * </p>
     * @param credentials
     *            The credentials to use for authentication.
     * @return The module response.
     * @throws IllegalArgumentException -
     *             If any parameter is null or empty.
     * @throws AuthenticationException -
     *             If something wrong happened during operation.
     */
    public synchronized AuthenticationModuleResponse authenticate(Credentials credentials)
        throws AuthenticationException {
        ExceptionUtils.checkNull(credentials, null, null, "Credentials should not be null.");

        UserPasswordCredentials credential = (UserPasswordCredentials) credentials;
        String password = credential.getPassword();

        if (password.equals(AUTHENTICATION_EXCEPTION)) {
            throw new AuthenticationException("AUTHENTICATION_EXCEPTION");
        }
        if (password.equals(AUTHENTICATION_FAIL)) {
            throw new InvalidCredentialsException("AUTHENTICATION_FAIL");
        }

        return new AuthenticationModuleResponse(this, AuthenticationModuleResponseType.ALLOW, "authenticated", null);

    }

    /**
     * <p>
     * Register given credentials as valid for authentication.
     * </p>
     * @param credentials
     *            The credentials to be added.
     * @throws IllegalArgumentException
     *             If any parameter is null.
     * @throws AuthenticationException
     *             If given credentials is not type of <code>UserPasswordCredentials</code>.
     */
    public synchronized void addCredentials(Credentials credentials) throws AuthenticationException {

    }

    /**
     * <p>
     * Unregister given credentials as valid for authentication.
     * </p>
     * @param credentials
     *            The credentials to be removed.
     * @throws IllegalArgumentException -
     *             If any parameter is null.
     * @throws AuthenticationException -
     *             If given credentials is not type of <code>UserPasswordCredentials</code>.
     */
    public synchronized void removeCredentials(Credentials credentials) throws AuthenticationException {

    }

    /**
     * <p>
     * Replace given credentials by new ones.
     * </p>
     * @param oldCredentials
     *            The old credentials.
     * @param newCredentials
     *            The new credentials.
     * @throws IllegalArgumentException
     *             If any parameter is null.
     * @throws AuthenticationException -
     *             If given credentials is not type of <code>UserPasswordCredentials</code>.
     */
    public synchronized void updateCredentials(Credentials oldCredentials, Credentials newCredentials)
        throws AuthenticationException {

    }

	public void logout(SecurityToken arg0) {
		// TODO Auto-generated method stub
		
	}
}
