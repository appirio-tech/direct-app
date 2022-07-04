/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * This action handles callback from OAuth provider.
 * <p>
 * <strong>Thread Safety:</strong> This class is mutable as its parent class is mutable; but it's only used in
 * thread-safe manner by Struts framework.
 * </p>
 * 
 * @author sampath01, zhu_tao
 * @version 1.0
 * @since 1.0
 */
public class OAuthCallBackAction extends ActionSupport {

    /**
     * Qualified name of this class.
     */
    private static final String CLASS_NAME = OAuthCallBackAction.class.getName();

    /**
     * Log instance.
     */
    private static final Log log = LogManager.getLog(CLASS_NAME);

    /**
     * Serial ID.
     */
    private static final long serialVersionUID = -1452195505224765938L;

    /**
     * This method process of OAuth call back to complete OAuth authentication.
     * 
     * @throws Exception
     *             If there is any error.
     */
    @Override
    public String execute() throws Exception {
        final String signature = CLASS_NAME + "#execute()";
        LoggingWrapperUtility.logEntrance(log, signature, null, null);

        try {
            // Temporarily disable certification check.
            XTrustProvider.install();

            HttpServletRequest request = ServletActionContext.getRequest();
            HttpServletResponse response = ServletActionContext.getResponse();
            HttpSession session = request.getSession();
            String oauthVerifier = request.getParameter(Helper.OAUTH_VERIFIER_KEY);
            Verifier verifier = new Verifier(oauthVerifier);
            OAuthService oAuthService = (OAuthService) session.getAttribute(Helper.OAUTH_SERVICE_KEY);
            Token requestToken = (Token) session.getAttribute(Helper.REQUEST_TOKEN_KEY);
            Token accessToken = oAuthService.getAccessToken(requestToken, verifier);
            session.setAttribute(Helper.ACCESS_TOKEN_KEY, accessToken);
            String nextURL = (String) session.getAttribute(Helper.FORWARD_URL);
            response.sendRedirect(nextURL);
            if (log.isEnabled(Level.DEBUG)) {
                log.log(Level.DEBUG, "Authencation done. Ready to redirect back to previous requesting URL.");
            }
        } catch (Exception e) {
            LoggingWrapperUtility.logException(log, signature, e);
            throw new Exception("Error when executing action : " + signature + " : " + e.getMessage());
        }

        LoggingWrapperUtility.logExit(log, signature, null);
        return null;
    }

}
