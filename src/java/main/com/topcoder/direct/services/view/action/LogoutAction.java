/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
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
 * @author isv
 * @version 1.0
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
        BasicAuthentication auth = new BasicAuthentication(
            new SessionPersistor(ServletActionContext.getRequest().getSession()),
            new SimpleRequest(ServletActionContext.getRequest()),
            new SimpleResponse(ServletActionContext.getResponse()),
            new SimpleResource("direct"),
            DBMS.JTS_OLTP_DATASOURCE_NAME);
        auth.logout();
        return SUCCESS;
    }
}
