/*
 * Copyright (C) 2010 - 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.shared.security.SimpleResource;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for logging the users ouf of application.</p>
 *
 * <p>
 * Version 1.1 (System Assembly - Direct Topcoder Scorecard Tool Integration) changes notes: 
 *    <ul>
 *       <li>Remove cookie used by scorecard application.</li>
 *    </ul> 
 * </p>
 *
 * <p>
 * Version 1.2 (BUG TCCC-5802) Change notes:
 *  <ul>
 *   <li>Remove direct_sso cookie and its related logic.</li>
 *  </ul>
 * </p>
 *
 * @author isv, pvmagacho, ecnu_haozi
 * @version 1.2
 */
public class LogoutAction extends AbstractAction {
    /**
     * <p>Constructs new <code>LogoutAction</code> instance. This implementation does nothing.</p>
     */
    public LogoutAction() {
    }

    /**
     * <p>Handles the incoming request. Logs the user out of application by invalidating the current session associated
     * with user.</p>
     *
     * @return a <code>String</code> referencing the next view or action to route request to. This implementation
     *         returns {@link #SUCCESS} always.
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    @Override
    public String execute() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Changed to use the TC SSO cookie
	BasicAuthentication auth = new BasicAuthentication(
            new SessionPersistor(ServletActionContext.getRequest().getSession()),
            new SimpleRequest(ServletActionContext.getRequest()),
            new SimpleResponse(ServletActionContext.getResponse()),
            BasicAuthentication.MAIN_SITE,
            DBMS.JTS_OLTP_DATASOURCE_NAME);
        auth.logout();

        return SUCCESS;
    }
}
