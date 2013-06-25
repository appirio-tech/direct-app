/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * This class aggregates a handful of commonly used static methods.
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 * 
 * @author zhu_tao
 * @version 1.0
 * @since 1.0
 */
public final class Helper {

    /**
     * Key use to store access token in session.
     */
    public static final String ACCESS_TOKEN_KEY = "accessToken";

    /**
     * Session key to store oAuth service.
     */
    public static final String OAUTH_SERVICE_KEY = "oAuthService";

    /**
     * Session key to store request token.
     */
    public static final String REQUEST_TOKEN_KEY = "requestToken";

    /**
     * Session key to store next page.
     */
    public static final String FORWARD_URL = "nextPage";

    /**
     * Parameter name of the oauth verifier from OAuth provider.
     */
    public static final String OAUTH_VERIFIER_KEY = "oauth_verifier";
    
    /**
     * Default constructor. Making it private to prevent instantiating.
     */
    private Helper() {
        //Empty.
    }

    /**
     * This method checks the given {@link String} variable, to make sure it's not null nor empty.
     * 
     * @param paraName
     *            name of the variable.
     * @param para
     *            The variable to check.
     * @param clazz
     *            Exception class to throw if validation fails.
     * @throws T
     *             If the given variable is null/empty, throw T.
     */
    public static <T extends Exception> void checkNotNullNorEmpty(String paraName, String para, Class<T> clazz)
            throws T {
        if (null == para || para.trim().length() == 0) {
            throw constructException(clazz, paraName + " should not be null/empty");
        }
    }

    /**
     * This method is to construct an exception instance using given info.
     * 
     * @param clazz
     *            Exception type.
     * @param msg
     *            Exception message.
     * @return Instance of {@link Exception}.
     */
    private static <T extends Throwable> T constructException(Class<T> clazz, String msg) {

        try {
            Constructor<T> ctor = clazz.getConstructor(String.class);
            return ctor.newInstance(msg);
        } catch (SecurityException e) {
            // Drop all exceptions quietly.
        } catch (NoSuchMethodException e) {
        } catch (IllegalArgumentException e) {
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return null;
    }

}
