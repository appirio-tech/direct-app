/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.cloudvm;

import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.security.AuthorizationException;

import javax.servlet.http.HttpServletRequest;

/**
 * This action extends the AbstractVMAction, and it is used to terminate the VM.
 *
 * <p>
 * Changes in 1.1 (TopCoder Direct Contest VM Instances Management):
 * <ul>
 *     <li>Updated executeAction() to handle terminate contest VM instance.</li>
 *     <li>Added {@link #authorize(com.topcoder.security.TCSubject, long, boolean)} method to do contest level authorization.</li>
 * </ul>
 * </p>
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author Standlove, gentva
 * @version 1.1
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
        String contestIdParam = request.getParameter("contestId");

        TCSubject user = getUser();

        if(getCloudVMService().isVMCreator(user, instanceId)) {
            // vm create should always has permission to terminate his created vm.
            setResult(getCloudVMService().terminateVMInstance(user, instanceId));
        } else {
            if (contestIdParam != null && contestIdParam.trim().length() > 0) {
                long contestId = Long.parseLong(request.getParameter("contestId"));
                boolean isStudio = Boolean.parseBoolean(request.getParameter("studio"));
                authorize(user, contestId, isStudio);
                setResult(getCloudVMService().terminateContestVMInstance(user, instanceId, contestId));
            }  else {
                authorize(user);

                setResult(getCloudVMService().terminateVMInstance(user, instanceId));
            }
        }
    }

    /**
     * Performs user authorization.
     *
     * @param user user to check
     * @throws AuthorizationException if authorization failed
     * @since 1.1
     */
    private void authorize(TCSubject user, long contestId, boolean isStudio) throws AuthorizationException {

        boolean authorized = false;

        try {
            authorized = DirectUtils.hasWritePermission(this, user, contestId, isStudio);
        } catch (Exception e) {
            throw new AuthorizationException(
                "Fail to check write permission for given contest - " + contestId, e);
        }

        if (!authorized) {
            throw new AuthorizationException(
                    "User must have role of Administrator or Write/Full role to perform this operation.");
        }

    }
}

