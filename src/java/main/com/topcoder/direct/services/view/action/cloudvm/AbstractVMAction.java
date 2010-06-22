/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.cloudvm;

import com.topcoder.direct.cloudvm.service.CloudVMService;
import com.topcoder.direct.services.view.action.contest.launch.AggregateDataModel;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.security.AuthorizationException;

import javax.servlet.http.HttpServletRequest;

/**
 * This action extends the AbstractAction and it's used as the base action for some actions in this architecture.
 *
 * Thread-safety: Mutable and not thread-safe.
 *
 * @author Standlove, TCSDEVELOPER
 * @version 1.0
 */
public abstract class AbstractVMAction extends BaseDirectStrutsAction {
    /**
     * Represents the CloudVMService to manage the VM. It has getter & setter. Can be any value.
     */
    private CloudVMService cloudVMService;

    /**
     * Constructor.
     */
    protected AbstractVMAction() {
        setModel(new AggregateDataModel());
        getModel().setAction(getAction());
    }

    /**
     * Getter for cloud vm service.
     *
     * @return service referene
     */
    public CloudVMService getCloudVMService() {
        return cloudVMService;
    }

    /**
     * Setter for cloud vm service.
     *
     * @param cloudVMService value to set
     */
    public void setCloudVMService(CloudVMService cloudVMService) {
        this.cloudVMService = cloudVMService;
    }

    /**
     * Get the user from session.
     *
     * @return the user.
     * @throws IllegalStateException if the tcSubjectSessionKey is null or empty string, or session is null, or the
     *                               retrieved value is not instance of TCSubject
     */
    public static TCSubject getUser() {
        HttpServletRequest request = DirectUtils.getServletRequest();
        if (request == null) {
            return null;
        }
        return new SessionData(request.getSession()).getCurrentUser();
    }

    /**
     * Performs user authorization.
     *
     * @param user user to check
     * @throws AuthorizationException if authorization failed
     */
    public static void authorize(TCSubject user) throws AuthorizationException {
        if (!DashboardVMAction.inRole(user, "Administrator") && !DashboardVMAction.inRole(user, "VMManager")) {
            throw new AuthorizationException(
                "User must have role of Administrator or VMManager to perform this operation.");
        }
    }
}
