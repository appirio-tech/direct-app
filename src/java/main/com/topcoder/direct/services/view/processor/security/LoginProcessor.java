/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.security;

import com.topcoder.direct.services.configs.ServerConfiguration;
import com.topcoder.direct.services.view.action.LoginAction;
import com.topcoder.direct.services.view.form.LoginForm;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DirectProperties;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.jwt.DirectJWTSigner;
import com.topcoder.security.TCSubject;
import com.topcoder.security.login.AuthenticationException;
import com.topcoder.security.login.LoginRemote;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.util.DBMS;
import com.topcoder.shared.util.logging.Logger;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.Constants;
import com.topcoder.web.common.security.SessionPersistor;
import org.apache.struts2.ServletActionContext;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>A processor to be used for handling requests for user authentication to application.</p>
 * <p/>
 * <p>This processor expects the actions of {@link LoginAction} type to be passed to it. It gets the submitted user
 * credentials from action's form and uses them to authenticate user using <code>Security Manager EJB</code>.</p>
 * <p/>
 * <p>
 * Version 1.1 (System Assembly - Direct Topcoder Scorecard Tool Integration) changes notes:
 * <ul>
 * <li>Set cookie to be used by scorecard application.</li>
 * <li>Use MAIN_SITE cookie for direct application.</li>
 * <li>Use 1 day as expiration time for MAIN_SITE cookie if remember me flag is not set.</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * Version 1.2 (BUG TCCC-5802) Change notes:
 * <ul>
 * <li>Remove direct_sso cookie and its related logic.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.3 (TopCoder Direct - JWT token generation)
 * <ul>
 *     <li>Updated {@link #processRequest(com.topcoder.direct.services.view.action.LoginAction)} to
 *     add JWT cookie</li>
 * </ul>
 * </p>
 *
 * @author isv, pvmagacho, ecnu_haozi, Veve
 * @version 1.3
 */
public class LoginProcessor implements RequestProcessor<LoginAction> {
    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(LoginProcessor.class);

    /**
     * The Options for JWT generation.
     *
     * @since 1.3
     */
    private static final DirectJWTSigner.Options JWT_OPTIONS;

    static {
        JWT_OPTIONS = new DirectJWTSigner.Options();
        JWT_OPTIONS.setExpirySeconds(DirectProperties.JWT_EXPIRATION_SECONDS);
        JWT_OPTIONS.setIssuedAt(true);
    }

    /**
     * <p>Constructs new <code>LoginProcessor</code> instance. This implementation does nothing.</p>
     */
    public LoginProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(LoginAction action) {
        LoginForm form = action.getFormData();
        String username = form.getUsername();
        String password = form.getPassword();

        if ((username == null || (username != null && username.trim().length() == 0))
                && (password == null || (password != null && password.trim().length() == 0))) {
            action.setResultCode(LoginAction.RC_EMPTY_CREDENTIALS);
            return;
        }

        try {
            TCSubject tcSubject = login(username, password);
            action.getSessionData().setCurrentUser(tcSubject);
            action.getSessionData().setCurrentUserHandle(username);
            action.setResultCode(LoginAction.RC_SUCCESS);
            log.info("User " + username + "  has been authenticated successfully");

            // Changed to use the TC SSO cookie
            BasicAuthentication auth = new BasicAuthentication(
                    new SessionPersistor(ServletActionContext.getRequest().getSession()),
                    new SimpleRequest(ServletActionContext.getRequest()),
                    new SimpleResponse(ServletActionContext.getResponse()),
                    BasicAuthentication.MAIN_SITE,
                    DBMS.JTS_OLTP_DATASOURCE_NAME);
            //auth.setBigCookieTime(MAIN_COOKIE_TIME);
            auth.login(new SimpleUser(tcSubject.getUserId(), username, password), action.getFormData().isRemember());


            // generate the jwt cookie
            DirectJWTSigner jwtSigner = new DirectJWTSigner(DirectProperties.CLIENT_SECRET_AUTH0);

            Map<String, Object> claims = new HashMap<String, Object>();
            claims.put("iss", "https://" + DirectProperties.DOMAIN_AUTH0);
            claims.put("sub", "ad|" + tcSubject.getUserId());
            claims.put("aud", DirectProperties.CLIENT_ID_AUTH0);

            String sign = jwtSigner.sign(claims, JWT_OPTIONS);

            // add session cookie, use -1 for expiration time
            log.info("Signed JWT: " + sign);
            DirectUtils.addDirectCookie(ServletActionContext.getResponse(),
                    ServerConfiguration.JWT_COOKIE_KEY, sign, -1);

        } catch (AuthenticationException e) {
            log.error("User " + username + " failed to authenticate successfully due to invalid credentials", e);
            action.setResultCode(LoginAction.RC_INVALID_CREDENTIALS);
        } catch (Exception e) {
            log.error("Failed to authenticate user due to unexpected error", e);
            action.setResultCode(LoginAction.RC_INVALID_CREDENTIALS);
        }
    }

    /**
     * <p>Authenticates the user with specified credentials to this application.</p>
     *
     * @param username a <code>String</code> providing the username for authentication.
     * @param password a <code>String</code> providing the password for authentication.
     * @return a <code>TCSubject</code> representing the authenticated user.
     * @throws AuthenticationException if specified credentials are invalid.
     * @throws Exception               if an unexpected error occurs.
     */
    public static TCSubject login(String username, String password) throws Exception {
        LoginRemote login = (LoginRemote) Constants.createEJB(LoginRemote.class);
        TCSubject tcSubject = login.login(username, password);
        return tcSubject;
    }
}
