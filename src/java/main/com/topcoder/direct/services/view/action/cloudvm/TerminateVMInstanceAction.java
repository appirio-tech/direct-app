/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.cloudvm;

import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.security.AuthorizationException;

import javax.servlet.http.HttpServletRequest;

/**
 * This action extends the AbstractVMAction, and it is used to terminate the VM.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public class TerminateVMInstanceAction extends AbstractVMAction {
    /**
     * Empty constructor.
     */
    public TerminateVMInstanceAction() {
    }

    /**
     * Terminate VM instance.
     *
     * @throws Exception from remote services
     */
    protected void executeAction() throws Exception {
        HttpServletRequest request = DirectUtils.getServletRequest();
        long instanceId = Long.parseLong(request.getParameter("instanceId"));

        TCSubject user = getUser();
        authorize(user);
        setResult(getCloudVMService().terminateVMInstance(user, instanceId));
    }
}

