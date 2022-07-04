/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.interceptors;


import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.topcoder.direct.services.view.util.jwt.JWTToken;
import com.topcoder.direct.services.view.util.jwt.TokenExpiredException;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.direct.services.configs.ServerConfiguration;
import com.topcoder.direct.services.view.action.contest.launch.Helper;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.DirectProperties;
import com.topcoder.security.TCPrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.security.User;
import com.topcoder.shared.util.DBMS;
import com.topcoder.shared.util.logging.Logger;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;



/**
 * <p>
 * This interceptor will be responsible for checking if the user info is in the session. It will look for user info in
 * the session using <code>userSessionIdentityKey</code>. If found, it allows the action to proceed. If not, it sends
 * control to the login page.
 * </p>
 *
 * <p>
 * This class extends the AbstractInterceptor class. The user session key and login page are configurable.
 * </p>
 *
 * <p>
 * <strong>Demo:</strong>
 *
 * <pre>
 * // create the struts2 action proxy
 * ActionProxy proxy = getActionProxy(&quot;/login&quot;);
 * TestHelper.prepareActionProxy(proxy);
 *
 * // call the intercept method, result will be the login page
 * String result = authenticationInterceptorInstance.intercept(proxy.getInvocation());
 * System.out.println(&quot;result of authenticationInterceptor when credential &quot; + &quot;data is missing is &quot; + result);
 *
 * // create a new ActionProxy
 * proxy = getActionProxy(&quot;/login&quot;);
 * TestHelper.prepareActionProxy(proxy);
 *
 * // set the login credential
 * proxy.getInvocation().getInvocationContext().getSession().put(TestHelper.KEY_FOR_LOGIN_CHECK, &quot;topcoder&quot;);
 *
 * // inject the login and password parameters
 * Map&lt;String, Object&gt; parameters = new HashMap&lt;String, Object&gt;();
 * parameters.put(&quot;loginName&quot;, &quot;topcoder&quot;);
 * parameters.put(&quot;password&quot;, &quot;password&quot;);
 * proxy.getInvocation().getInvocationContext().setParameters(parameters);
 *
 * // call the intercept method, result will be success since
 * // credential information was provided
 * result = authenticationInterceptorInstance.intercept(proxy.getInvocation());
 * System.out.println(&quot;result of authenticationInterceptor when credential data &quot; + &quot;is provided is &quot; + result);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Example configuration for struts2 (shows how to configure the interceptors and actions):</strong>
 *
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;!DOCTYPE struts PUBLIC
 *     &quot;-//Apache Software Foundation//DTD Struts Configuration 2.0//EN&quot;
 *     &quot;http://struts.apache.org/dtds/struts-2.0.dtd&quot;&gt;
 *
 * &lt;struts&gt;
 *     &lt;constant name=&quot;struts.devMode&quot; value=&quot;true&quot; /&gt;
 *
 *     &lt;package name=&quot;tcs-default&quot; extends=&quot;struts-default&quot; abstract=&quot;true&quot;&gt;
 *         &lt;!-- Setup interceptors stack --&gt;
 *         &lt;interceptors&gt;
 *             &lt;interceptor name=&quot;authentication&quot; class=&quot;authenticationInterceptor&quot; /&gt;
 *             &lt;interceptor name=&quot;logging&quot; class=&quot;loggingInterceptor&quot; /&gt;
 *
 *             &lt;interceptor-stack name=&quot;defaultTCSStack&quot;&gt;
 *                 &lt;interceptor-ref name=&quot;authentication&quot;/&gt;
 *                 &lt;interceptor-ref name=&quot;logging&quot;/&gt;
 *                 &lt;interceptor-ref name=&quot;defaultStack&quot;/&gt;
 *             &lt;/interceptor-stack&gt;
 *         &lt;/interceptors&gt;
 *
 *         &lt;!-- Make the default one used for all actions unless otherwise configured. --&gt;
 *         &lt;default-interceptor-ref name=&quot;defaultTCSStack&quot; /&gt;
 *
 *         &lt;!-- Configure global results for AuthenticationInterceptor --&gt;
 *         &lt;global-results&gt;
 *             &lt;result name=&quot;login&quot;&gt;/login.jsp&lt;/result&gt;
 *         &lt;/global-results&gt;
 *
 *     &lt;/package&gt;
 *
 *     &lt;package name=&quot;default&quot; namespace=&quot;/&quot; extends=&quot;tcs-default&quot;&gt;
 *
 *         &lt;interceptors&gt;
 *             &lt;!-- for the login stack, we exclude the authentication interceptor --&gt;
 *             &lt;interceptor-stack name=&quot;loginTCSStack&quot;&gt;
 *                 &lt;interceptor-ref name=&quot;logging&quot;/&gt;
 *                 &lt;interceptor-ref name=&quot;defaultStack&quot;/&gt;
 *             &lt;/interceptor-stack&gt;
 *         &lt;/interceptors&gt;
 *
 *         &lt;action name=&quot;login&quot; class=&quot;loginAction&quot;&gt;
 *             &lt;interceptor-ref name=&quot;loginTCSStack&quot; /&gt;
 *             &lt;result name=&quot;input&quot;&gt;/login.jsp&lt;/result&gt;
 *             &lt;result type=&quot;redirect&quot;&gt;employees&lt;/result&gt;
 *         &lt;/action&gt;
 *
 *         &lt;action name=&quot;employees&quot; class=&quot;employeeAction&quot;&gt;
 *             &lt;result&gt;/employees.jsp&lt;/result&gt;
 *         &lt;/action&gt;
 *
 *     &lt;/package&gt;
 *
 * &lt;/struts&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Example configuration (shows how to configure AuthenticationInterceptor bean in
 * applicationContext.xml):</strong>
 *
 * <pre>
 * &lt;bean id=&quot;authenticationInterceptor&quot;
 *   class=&quot;com.topcoder.service.interceptors.AuthenticationInterceptor&quot;&gt;
 *     &lt;property name=&quot;loginPageName&quot; value=&quot;login&quot;&gt;&lt;/property&gt;
 *     &lt;property name=&quot;userSessionIdentityKey&quot; value=&quot;USER_ID_KEY&quot;&gt;&lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Update in Launch Contest Assembly
 *   - update the intercept to perform checking login as well as remember me type of service
 * </p>
 *
 * <p>
 * Version 2.1 (Direct Improvements Assembly Release 1) Change notes:
 * <ul>
 * <li>Added support to redirect to the latest URL after user login in.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.2 (BUG TCCC-3698) Change notes:
 *  <ul>
 *      <li>Change the method used to check for cookie from <code>checkCookie</code> to <code>getActiveUser()</code>.</li>
 *  </ul>
 * </p>
 *
 * <p>
 * Version 2.3 (BUG TCCC-5802) Change notes:
 *  <ul>
 *   <li>Check SSO cookie and update auth related object in session each time.</li>
 *  </ul>
 * </p>
 *
 * <p>
 * <b>Thread safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER, pvmagacho, ecnu_haozi
 * @version 2.3
 */
public class AuthenticationInterceptor extends AbstractInterceptor {

    private static final Logger logger = Logger.getLogger(AuthenticationInterceptor.class);

    /**
     * <p>
     * Represents the login page name attribute.
     * <p>
     *
     * <p>
     * It can be set and accessed in the set/get methods. It must be non-null and non-empty string.
     * </p>
     */
    private String loginPageName;

    /**
     * <p>
     * Represents the user session identity key attribute.
     * </p>
     *
     * <p>
     * It can be set and accessed in the set/get methods. It must be non-null and non-empty string.
     * </p>
     */
    private String userSessionIdentityKey = "user";

    /**
     * Represents the redirect back URL session identity key attribute.
     * 
     * @since 2.1
     */
    private String redirectBackUrlIdentityKey;

    /**
     * Endpoint from token updater
     */
    private String authorizationURL;

    /**
     * Default constructor, constructs an instance of this class.
     */
    public AuthenticationInterceptor() {
        // does nothing
    }

    /**
     * <p>
     * This method provides the intercept logic.
     * </p>
     *
     * <p>
     * It retrieves the user credentials from session and based on this data, it redirects to login page if a user
     * needs authentication, or invokes the action and returns its result otherwise.
     * </p>
     *
     * <p>
     * Update in Launch Contest Assembly: update to see cookie based login.
     * </p>
     *
     * @param invocation the action invocation to intercept
     *
     * @return the interception result, which will either be the login page if user needs authentication, or the
     *         action result otherwise
     *
     * @throws IllegalStateException if the login page name or user session identity key are not injected (they must
     *             be not null and not empty)
     *
     * @throws Exception if an error occurs when invoking the action
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        if (loginPageName == null) {
            throw new IllegalStateException("loginPageName has not been injected.");
        }

        if (userSessionIdentityKey == null) {
            throw new IllegalStateException("userSessionIdentityKey has not been injected.");
        }
        
        if (redirectBackUrlIdentityKey == null) {
            throw new IllegalStateException("redirectBackUrlIdentityKey has not been injected.");
        }

        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionData sessionData = new SessionData(session);

        HttpServletRequest request = ServletActionContext.getRequest();

        // Support Single-Sign-On login, Because the user may log out from another
        // app without notification to current app. Thus the auth related object in session may invalidate at any time.
        // In this case we need to check SSO cookie and update auth related object in session every time.

        HttpServletResponse response = ServletActionContext.getResponse();
        BasicAuthentication auth = new BasicAuthentication(
            new SessionPersistor(request.getSession()), new SimpleRequest(request),
            new SimpleResponse(response), BasicAuthentication.MAIN_SITE, DBMS.JTS_OLTP_DATASOURCE_NAME);
        User user = auth.getActiveUser();

        Cookie jwtCookie = DirectUtils.getCookieFromRequest(ServletActionContext.getRequest(),
                ServerConfiguration.JWT_V3_COOKIE_KEY);

        if (jwtCookie == null) {
            return loginPageName;
        }

        JWTToken jwtToken = null;
        try {
            jwtToken = new JWTToken(jwtCookie.getValue(),DirectProperties.JWT_V3_SECRET,
                    DirectProperties.JWT_VALID_ISSUERS, authorizationURL, new JWTToken.SecretEncoder());
            jwtToken.verify();
        } catch (TokenExpiredException e) {
            logger.error("Token is expired. Try to refresh");
            try {
                //TODO .refresh() should use v3jwt for all algo
                if ("HS256".equals(jwtToken.getAlgorithm())) {
                    jwtToken = jwtToken.refresh(DirectUtils.getCookieFromRequest(ServletActionContext.getRequest(),
                            ServerConfiguration.JWT_COOKIE_KEY).getValue());
                } else {
                    jwtToken = jwtToken.refresh();
                }
                DirectUtils.addDirectCookie(ServletActionContext.getResponse(), ServerConfiguration.JWT_V3_COOKIE_KEY,
                    jwtToken.getToken(), -1);
            } catch (Exception ex) {
                logger.error("Failed to refresh token: " + ex.getMessage());
                logger.info("Redirect to login page");
                return loginPageName;
            }
        } catch (Exception e) {
            return loginPageName;
        }

        sessionData.setToken(jwtToken.getToken());

        if (user != null  && !user.isAnonymous()) {
            // get user roles for the user id
            Set<TCPrincipal> roles = DirectUtils.getUserRoles(user.getId());
            TCSubject tcSubject = new TCSubject(roles, user.getId());

            sessionData.setCurrentUser(tcSubject);
            sessionData.setCurrentUserHandle(user.getUserName());
         } else {
            if ("GET".equalsIgnoreCase(request.getMethod())) {
                StringBuffer redirectBackUrl = new StringBuffer();
                redirectBackUrl.append("https://");
                redirectBackUrl.append(request.getServerName());
                redirectBackUrl.append(request.getRequestURI());
                if (request.getQueryString() != null && request.getQueryString().trim().length() != 0) {
                    redirectBackUrl.append('?');
                    redirectBackUrl.append(request.getQueryString());
                }
                request.getSession().setAttribute(redirectBackUrlIdentityKey, redirectBackUrl.toString());
            } else {
                // Get the referer URL if the request method is POST
                final String referer = request.getHeader("Referer");
                if (referer != null && referer.trim().length() != 0) {
                    request.getSession().setAttribute(redirectBackUrlIdentityKey, referer);
                }
            }
            return loginPageName;
        }        

        String servletPath = request.getContextPath() + request.getServletPath();
        String query = request.getQueryString();
        String queryString = (query == null) ? ("") : ("?" + query);

        StringBuffer buf = new StringBuffer(200);
        buf.append("https://");        
        buf.append(request.getServerName());
        buf.append(servletPath);
        buf.append(queryString);
        String requestString = buf.toString();
        String handle = "";
        if (sessionData != null && sessionData.getCurrentUserHandle() != null && !sessionData.getCurrentUserHandle().equals(""))
        {
            handle = sessionData.getCurrentUserHandle();
        }

        //I-137967(https://appirio.my.salesforce.com/a3v50000000D2Lt)
        //loginfo.append(request.getRemoteAddr());
        String remoteAddr = request.getHeader("X-Forwarded-For");
        if(remoteAddr == null || remoteAddr.trim().length()==0) {
            remoteAddr = request.getRemoteAddr();
        }

        StringBuffer loginfo = new StringBuffer(100);
        loginfo.append("[* ");
        loginfo.append(handle);
        loginfo.append(" * ");
        loginfo.append(remoteAddr);
        loginfo.append(" * ");
        loginfo.append(request.getMethod());
        loginfo.append(" ");
        loginfo.append(requestString);
        loginfo.append(" *]");
        logger.info(loginfo.toString());


        // process the action and return its result
        return invocation.invoke();
    }

    /**
     * Getter for the login page name.
     *
     * @return the login page name
     */
    public String getLoginPageName() {
        return loginPageName;
    }

    /**
     * Setter for the login page name.
     *
     * @param loginPageName login page name
     *
     * @throws IllegalArgumentException if argument is null or empty
     */
    public void setLoginPageName(String loginPageName) {
        Helper.checkNotNullOrEmpty(loginPageName, "loginPageName");
        this.loginPageName = loginPageName;
    }

    /**
     * Getter for the user session identity key.
     *
     * @return the user session identity key
     */
    public String getUserSessionIdentityKey() {
        return userSessionIdentityKey;
    }

    /**
     * Setter for the user session identity key.
     *
     * @param userSessionIdentityKey user session identity key
     *
     * @throws IllegalArgumentException if argument is null or empty
     */
    public void setUserSessionIdentityKey(String userSessionIdentityKey) {
        Helper.checkNotNullOrEmpty(userSessionIdentityKey, "userSessionIdentityKey");
        this.userSessionIdentityKey = userSessionIdentityKey;
    }

    /**
     * Gets the redirect back URL session identity key attribute.
     * 
     * @return the redirect back URL session identity key attribute.
     * @since 2.1
     */
    public String getRedirectBackUrlIdentityKey() {
        return redirectBackUrlIdentityKey;
    }

    /**
     * Sets the redirect back URL session identity key attribute.
     * 
     * @param redirectBackUrlIdentityKey the redirect back URL session identity key attribute.
     * @since 2.1
     */
    public void setRedirectBackUrlIdentityKey(String redirectBackUrlIdentityKey) {
        Helper.checkNotNullOrEmpty(redirectBackUrlIdentityKey, "redirectBackUrlIdentityKey");
        this.redirectBackUrlIdentityKey = redirectBackUrlIdentityKey;
    }

    public String getAuthorizationURL() {
        return authorizationURL;
    }

    public void setAuthorizationURL(String authorizationURL) {
        this.authorizationURL = authorizationURL;
    }
}
