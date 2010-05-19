/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.view.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.direct.services.view.action.contest.launch.Helper;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.security.SimpleResource;
import com.topcoder.shared.security.User;
import com.topcoder.shared.util.DBMS;
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
 * <b>Thread safety:</b> This class is mutable and not thread safe.
 * </p>
 *
 * @author woodjhon, TCSDEVELOPER
 * @version 2.0
 */
public class AuthenticationInterceptor extends AbstractInterceptor {

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

        HttpSession session = ServletActionContext.getRequest().getSession();
        SessionData sessionData = new SessionData(session);

        if (sessionData.isAnonymousUser()) {
            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            BasicAuthentication auth = new BasicAuthentication(new SessionPersistor(request.getSession()),
                new SimpleRequest(request), new SimpleResponse(response), new SimpleResource("direct"),
                DBMS.JTS_OLTP_DATASOURCE_NAME);
            User user = auth.checkCookie();
            if (user != null && user.getId() > 0) {
                TCSubject tcSubject = new TCSubject(user.getId());
                sessionData.setCurrentUser(tcSubject);
                sessionData.setCurrentUserHandle(user.getUserName());
            } else {
                return loginPageName;
            }
        }

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
}
