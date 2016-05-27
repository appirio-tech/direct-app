/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.shared.util.TCResourceBundle;
import com.topcoder.shared.util.logging.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.MissingResourceException;

/**
 * <p>
 * A collection of properties for Direct application.
 * </p>
 *
 *
 * <p>
 *   Version 1.1 (Release Assembly - TopCoder Website Social Login) change log:
 *   <ol>
*     <li>Add DOMAIN_AUTH0 constant</li>
 *     <li>Add CLIENT_ID_AUTH0 constant</li>
 *     <li>Add REDIRECT_URL_AUTH0 constant</li>
 *     <li>Add REG_SERVER_NAME constant.</li>
 *   </ol>
 * </p>
 *
 * <p>
 *   Version 1.2 (TopCoder Direct - Update Login and add Admin Login) @author Veve @challenge 30044719
 *   <ul>
 *       <li>Add {@link #LDAP_AUTH0_CONNECTION_NAME}</li>
 *   </ul>
 * </p>
 *
 * <p>
 *   Version 1.3 (TopCoder Direct - JWT token generation)
 *   <ul>
 *       <li>Add {@link #JWT_EXPIRATION_SECONDS}</li>
 *   </ul>
 * </p>
 *<p>
 *   Version 1.4 (TopCoder Direct - Update competition costs report to use Redshift)
 *   <ul>
 *       <li>Add {@link #REDSHIFT_JDBC_URL}</li>
 *       <li>Add {@link #REDSHIFT_JDBC_USERNAME}</li>
 *       <li>Add {@link #REDSHIFT_JDBC_PASSWORD}</li>
 *   </ul>
 * </p>
 * @author BeBetter, ecnu_haozi, Veve, kalc
 * @version 1.4
 */
public final class DirectProperties {
    /**
     * <p>
     * A <code>Logger</code> to be used for logging the events.
     * </p>
     */
    private static final Logger log = Logger.getLogger(DirectProperties.class);

    /**
     * <p>
     * A <code>TCResourceBundle</code> providing access to configuration properties for this class.
     * </p>
     */
    private static final TCResourceBundle bundle = new TCResourceBundle("Direct");

    /**
     * <p>
     * contest service facade jndi name.
     * </p>
     */
    public static String CONTEST_SERVICE_FACADE_JNDI_NAME = "remote/ContestServiceFacadeBean";

    /**
     * <p>
     * contest service facade context factory.
     * </p>
     */
    public static String CONTEST_SERVICE_FACADE_CONTEXT_FACTORY = "org.jnp.interfaces.NamingContextFactory";

    /**
     * <p>
     * contest service facade ejb provider url.
     * </p>
     */
    public static String CONTEST_SERVICE_FACADE_PROVIDER_URL = "jnp://localhost:1399";

     /**
     * <p>A <code>String</code> providing the auth0 domain.</p>
     *
     * @since 1.1
     */
    public static String DOMAIN_AUTH0;
    
   /**
     * <p>A <code>String</code> providing the client id in auth0.com to enable login with 
     * social accounts like Google, Facebook.</p>
     *
     * @since 1.1
     */
    public static String CLIENT_ID_AUTH0;

    /**
     * <p>A <code>String</code> providing the client secret in auth0.com to enable login with 
     * social accounts like Google, Facebook.</p>
     *
     * @since 1.1
     */
    public static String CLIENT_SECRET_AUTH0;

    /**
     * <p>
     * The expiration seconds of the JWT.
     * </p>
     *
     * @since 1.3
     */
    public static int JWT_EXPIRATION_SECONDS;
    

    /**
     * <p>The callback url of the Auth0 account.</p>
     * 
     * @since 1.1
     */
    public static String REDIRECT_URL_AUTH0;

    /**
     * <p>The server name of tc_reg_revamp host.</p>
     * 
     *  @since 1.1
     */
    public static String REG_SERVER_NAME;

    /**
     * The auth0 connection name to connect to the LDAP authentication.
     *
     * @since 1.2
     */
    public static String LDAP_AUTH0_CONNECTION_NAME;

    /**
     * Paramater whether we use loginProcessor or not
     *
     */
    public static String USE_LOGIN_PROCESSOR;

    /**
     * URL for Redshift database
     *
     */
    public static String REDSHIFT_JDBC_URL;

    /**
     * username for Redshift database
     *
     */
    public static String REDSHIFT_JDBC_USERNAME;

    /**
     * Password for Redshift database
     *
     */
    public static String REDSHIFT_JDBC_PASSWORD;

    /**
     * <p>
     * Initializes non-final static fields for this class with values for the same-named properties from the resource
     * bundle.
     * </p>
     */
    static {
        initialize();
    }

    /**
     * <p>
     * Constructs new <code>DirectProperties</code> instance. This implementation does nothing.
     * </p>
     */
    private DirectProperties() {
    }

    /**
     * <p>
     * Initializes non-final static fields for this class with values for the same-named properties from the resource
     * bundle.
     * </p>
     */
    public static void initialize() {
        Field[] f = DirectProperties.class.getFields();
        for (int i = 0; i < f.length; i++) {
            try {
                if (!Modifier.isFinal(f[i].getModifiers())) {
                    if (f[i].getType().getName().equals("int")) {
                        try {
                            f[i].setInt(null, bundle.getIntProperty(f[i].getName()));
                        } catch (MissingResourceException ignore) {
                        }
                    } else if (f[i].getType().getName().equals("java.lang.String")) {
                        try {
                            System.out.println(f[i].getName() + ":"
                                + bundle.getProperty(f[i].getName()));
                            f[i].set(null, bundle.getProperty(f[i].getName()));
                        } catch (MissingResourceException ignore) {
                        }
                    } else {
                        throw new Exception("Unrecognized type: " + f[i].getType().getName());
                    }
                }
                if (f[i].get(null) == null) {
                    log.error("**DID NOT LOAD** " + f[i].getName() + " constant");
                } else {
                    if (log.isDebugEnabled()) {
                        log.debug(f[i].getName() + " <== " + f[i].get(null));
                    }
                }
            } catch (Exception e) {
                /* probably harmless, could just be a type or modifier mismatch */
                e.printStackTrace();
            }
        }
    }

}
