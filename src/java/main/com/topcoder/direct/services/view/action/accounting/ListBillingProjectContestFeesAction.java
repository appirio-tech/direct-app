/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.accounting;

import com.topcoder.clients.dao.ContestFeeServiceException;
import com.topcoder.util.log.Level;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>An action to be used for servicing requests for viewing the list of existing contest fees for selected billing
 * project.</p>
 * 
 * @author isv
 * @version 1.0 (Release Assembly - Project Contest Fees Management Update 1 Assembly)
 */
public class ListBillingProjectContestFeesAction extends BaseContestFeeAction {

    /**
     * <p>Constructs new <code>ListBillingProjectContestFeesAction</code> instance. This implementation does nothing.</p>
     */
    public ListBillingProjectContestFeesAction() {
    }

    /**
     * <p>Handles the incoming request.</p>
     * 
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String projectIdStr = request.getParameter("projectId");
        setProjectId(Long.parseLong(projectIdStr));
        try {
            setFormData(getBillingAccount(getProjectId()));
        } catch (ContestFeeServiceException e) {
            getLogger().log(Level.ERROR, e);
            throw e;
        }
    }
}
