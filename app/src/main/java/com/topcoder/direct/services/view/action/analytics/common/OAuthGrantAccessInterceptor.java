/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.common;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.direct.services.view.action.analytics.longcontest.ConfigurationException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This interceptor will check if the current user is authenticated or not. If not, OAuth authentication flow will be
 * performed.
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable and not thread-safe.
 * </p>
 * 
 * @author sampath01, zhu_tao
 * @version 1.0
 * @since 1.0
 */
public class OAuthGrantAccessInterceptor extends AbstractInterceptor implements SessionAware {

    /**
     * Qualified name of this class.
     */
    private static final String CLASS_NAME = OAuthGrantAccessInterceptor.class.getName();

    /**
     * Log instance for logging.
     */
    private static final Log log = LogManager.getLog(CLASS_NAME);

    /**
     * The Session map.
     */
    private Map<String, Object> session;

    /**
     * Serial NO.
     */
    private static final long serialVersionUID = 6931181972199212868L;

    /**
     * Authorization URL.
     */
    private String authorizationURL;

    /**
     * The consumer key.
     */
    private String consumerKey;

    /**
     * The consumer secret.
     */
    private String consumerSecret;

    /**
     * URL for call back.
     */
    private String callBackActionUrl;

    /**
     * Provider class.
     */
    private String providerClass;

    /**
     * This method is invoked to check success of IoC.
     * 
     * @throws ConfigurationException
     *             If there is any configuration mistake detected.
     */
    @PostConstruct
    public void checkConfiguration() {
        Helper.checkNotNullNorEmpty("authorizationURL", authorizationURL, ConfigurationException.class);
        Helper.checkNotNullNorEmpty("consumer_key", consumerKey, ConfigurationException.class);
        Helper.checkNotNullNorEmpty("consumer_secret", consumerSecret, ConfigurationException.class);
        Helper.checkNotNullNorEmpty("callBackActionUrl", callBackActionUrl, ConfigurationException.class);
        Helper.checkNotNullNorEmpty("providerClass", providerClass, ConfigurationException.class);
    }

    /**
     * Default constructor.
     */
    public OAuthGrantAccessInterceptor() {
        super();
    }

    /**
     * This method is responsible for the whole business logic defined in this class. It first check if the current
     * session is already authenticated. If yes, continue to other interceptors; otherwise, carry out OAuth
     * authentication.
     * 
     * @param actionInvocation
     *            The ActionInvocation instance.
     * @throws Exception
     *             If there is any error.
     */
    @SuppressWarnings("unchecked")
    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        final String signature = CLASS_NAME + "#intercept(ActionInvocation actionInvocation)";
        LoggingWrapperUtility.logEntrance(log, signature, new String[] { "actionInvocation" },
                new Object[] { actionInvocation });
        // This if-block should never be executed.
        if (null == session) {
            session = ActionContext.getContext().getSession();
        }
        String result = null;
        if (!session.containsKey(Helper.ACCESS_TOKEN_KEY) || null == session.get(Helper.ACCESS_TOKEN_KEY)) {
            if (log.isEnabled(Level.DEBUG)) {
                log.log(Level.DEBUG, "Going to perform OAuth authentication");
            }
            
            //Temporarily disable certification checks.
            XTrustProvider.install();
            
            Class<Api> providerClassInstance = (Class<Api>) Class.forName(providerClass);
            OAuthService oAuthService = new ServiceBuilder().provider(providerClassInstance)
                    .apiKey(consumerKey)
                    .apiSecret(consumerSecret)
                    .callback(callBackActionUrl)
                    .build();
            Token requestToken = oAuthService.getRequestToken();
            authorizationURL = oAuthService.getAuthorizationUrl(requestToken);
            session.put(Helper.OAUTH_SERVICE_KEY, oAuthService);
            session.put(Helper.REQUEST_TOKEN_KEY, requestToken);
            session.put(Helper.FORWARD_URL, ServletActionContext.getRequest().getRequestURL().toString());
            if (log.isEnabled(Level.DEBUG)) {
                log.log(Level.DEBUG, "Ready to redirect to the following URL for authorization: " + authorizationURL);
            }
            ServletActionContext.getResponse().sendRedirect(authorizationURL);
            LoggingWrapperUtility.logExit(log, signature, null);
            return null;
        } else {
            result = actionInvocation.invoke();
            LoggingWrapperUtility.logExit(log, signature, new String[] { result });
            return result;
        }

    }

    /**
     * Assign the session field variable by IoC style.
     */
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getAuthorizationURL() {
        return authorizationURL;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param authorizationURL
     *            the name-sake field to set
     */
    public void setAuthorizationURL(String authorizationURL) {
        this.authorizationURL = authorizationURL;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getConsumerKey() {
        return consumerKey;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param consumerKey
     *            the name-sake field to set
     */
    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getConsumerSecret() {
        return consumerSecret;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param consumerSecret
     *            the name-sake field to set
     */
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getCallBackActionUrl() {
        return callBackActionUrl;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param callBackActionUrl
     *            the name-sake field to set
     */
    public void setCallBackActionUrl(String callBackActionUrl) {
        this.callBackActionUrl = callBackActionUrl;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public String getProviderClass() {
        return providerClass;
    }

    /**
     * Setter of the name-sake field.
     * 
     * @param providerClass
     *            the name-sake field to set
     */
    public void setProviderClass(String providerClass) {
        this.providerClass = providerClass;
    }

    /**
     * Getter of the name-sake field.
     * 
     * @return the value of name-sake field.
     */
    public Map<String, Object> getSession() {
        return session;
    }
}
