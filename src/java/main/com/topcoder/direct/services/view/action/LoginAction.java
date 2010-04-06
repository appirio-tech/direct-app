/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.direct.services.view.form.LoginForm;
import com.topcoder.shared.security.SimpleResource;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.SimpleRequest;
import com.topcoder.web.common.SimpleResponse;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.common.security.SessionPersistor;
import org.apache.struts2.ServletActionContext;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for user authentication to application.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LoginAction extends LandingPage implements FormAction<LoginForm> {

    /**
     * <p>An <code>int</code> providing the result code to be set by the respective processor when authentication to
     * application is unsuccessful due to invalid credentials.</p>
     */
    public static final int RC_INVALID_CREDENTIALS = 1;

    /**
     * <p>A <code>LoginForm</code> providing the input parameters submitted by user.</p>
     */
    private LoginForm formData = new LoginForm();

    /**
     * <p>Constructs new <code>LoginAction</code> instance. This implementation does nothing.</p>
     */
    public LoginAction() {
    }

    /**
     * <p>Gets the form data submitted by user.</p>
     *  
     * @return a <code>LoginForm</code> providing the input parameters submitted by user.
     */
    public LoginForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Handles the incoming request.</p>
     *
     * <p>This method gets executed after respective request processor had authenticated user to application and set
     * the respective result code. This method analyzes the result code set by processor and routes request to
     * appropriate view.</p>
     *
     * <p>Also if user selected <code>Remember Me</code> option then appropriate cookie holding the username is sent
     * back to client.</p>
     *
     * @return a <code>String</code> referencing the next view or action to route request to.
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            if (this.formData.isRemember()) {
                BasicAuthentication auth = new BasicAuthentication(
                    new SessionPersistor(ServletActionContext.getRequest().getSession()),
                    new SimpleRequest(ServletActionContext.getRequest()),
                    new SimpleResponse(ServletActionContext.getResponse()),
                    new SimpleResource("direct"),
                    DBMS.JTS_OLTP_DATASOURCE_NAME);
                auth.setCookie(getSessionData().getCurrentUserId(), true);
            }
            return SUCCESS;
        } else if (RC_INVALID_CREDENTIALS == getResultCode()) {
            addActionError("Username or password is not valid");
            return INPUT;
        } else {
            return result;
        }
    }
}
