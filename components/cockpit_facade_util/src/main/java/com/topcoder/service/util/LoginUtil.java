/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.util;

import java.util.Properties;

import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.topcoder.security.GeneralSecurityException;
import com.topcoder.security.TCSubject;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.security.authenticationmanager.credentials.UserPasswordCredentials;
import com.topcoder.security.login.AuthenticationException;
import com.topcoder.security.login.LoginRemote;
import com.topcoder.security.login.LoginRemoteHome;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This is the Utility class for Cockpit Application.
 * Introduced in Cockpit Security Facade  version 1.0 Assembly.
 * </p>
 *
 * @author waits
 * @version 1.0
 */
public class LoginUtil {

    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private static final Log LOG = LogManager.getLog("LoginUtil");
    /**
     * <p>
     * Do the login for the current user. The result TCSubject represents all of the principals that can be used to
     * resolve permissions.
     * </p>
     *
     * @param loginBeanURL the login EJB-Bean url
     * @param loginBeanDSJndiName the login-Bean DataSource name
     * @param sessionContext the current SessionContext
     * @return The TCSubject representation of the user that includes the user's roles.
     * @throws AuthenticationException Thrown when username/password combination does not exist in the db.
     * @throws GeneralSecurityException Thrown when SQLExcpetion or any other error occurs when login.
     */
    public static TCSubject login(String loginBeanURL, String loginBeanDSJndiName, SessionContext sessionContext)
            throws AuthenticationException, GeneralSecurityException {
        try {
            Properties p = new Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, loginBeanURL);

            Context c = new InitialContext(p);
            LoginRemoteHome loginEJBRemoteHome = (LoginRemoteHome) c.lookup(LoginRemoteHome.EJB_REF_NAME);
            LoginRemote remote = loginEJBRemoteHome.create();

            // get the user-name/password from the Login Principal
            UserProfilePrincipal principal = (UserProfilePrincipal) sessionContext.getCallerPrincipal();
            if (principal.getCredentials() == null) {
                throw new GeneralSecurityException("The principal does not have credentials.");
            }
            String password = null;
            for (com.topcoder.security.authenticationmanager.Credentials credential : principal.getCredentials()) {
                if (credential instanceof UserPasswordCredentials) {
                    UserPasswordCredentials upc = (UserPasswordCredentials) credential;
                    password = upc.getPassword();
                    break;
                }
            }
            if (password == null || principal.getName() == null) {
                throw new GeneralSecurityException("The principal does not have user/password credentials.");
            }
            return remote.login(principal.getName(), password, loginBeanDSJndiName);
        } catch(GeneralSecurityException e){
            LOG.log(Level.ERROR, "error occurs during login", e);
            throw e;
        } catch (Exception e) {
            LOG.log(Level.ERROR, "error occurs during login", e);
            throw new GeneralSecurityException("The login module is not correctly configed.", e);
        }
    }
}
