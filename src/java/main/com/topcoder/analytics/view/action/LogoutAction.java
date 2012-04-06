/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.analytics.view.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for logout.</p>
 *
 * @author flexme
 * @version 1.0
 */
public class LogoutAction extends BaseAjaxAction {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -5648413734503098085L;

    /**
     * Construct a new <code>LogoutAction</code> instance.
     */
    public LogoutAction() {

    }

    /**
     * Handles the incoming request. Clear the state of current user.
     *
     * @throws Exception if any error occurs
     */
    protected void executeAction() throws Exception {
        HttpSession session = ServletActionContext.getRequest().getSession(false);
        if (session != null) {
            session.invalidate();
        }

        BasicAuthentication auth = new BasicAuthentication(
                new SessionPersistor(ServletActionContext.getRequest().getSession()),
                new SimpleRequest(ServletActionContext.getRequest()),
                new SimpleResponse(ServletActionContext.getResponse()),
                BasicAuthentication.MAIN_SITE,
                DBMS.JTS_OLTP_DATASOURCE_NAME);
        auth.logout();
    }
}
