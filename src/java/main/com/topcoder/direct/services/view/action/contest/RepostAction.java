/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.permission.PermissionServiceException;

/**
 * <p>
 * It is the repost action.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since Direct - Repost and New Version v1.0
 */
public class RepostAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Generated serial version uid.
     * </p>
     */
    private static final long serialVersionUID = 5288907989689528064L;

    /**
     * <p>
     * The <code>projectId</code> field.
     * </p>
     */
    private long projectId;

    /**
     * <p>
     * The <code>tcProjectId</code> field.
     * </p>
     */
    private long tcProjectId;

    /**
     * <p>
     * Gets the <code>tcProjectId</code> field.
     * </p>
     *
     * @return the <code>tcProjectId</code> field value
     */
    public long getTcProjectId() {
        return this.tcProjectId;
    }

    /**
     * <p>
     * Executes the action. It does the repost.
     * </p>
     *
     * @throws ContestServiceException if contest service exception occurs
     * @throws PermissionServiceException if permission exception occurs
     */
    @Override
    protected void executeAction() throws PermissionServiceException, ContestServiceException {
        Map<String, Object> result = new HashMap<String, Object>();
        long newProjectId = getContestServiceFacade().reOpenSoftwareContest(getCurrentUser(), projectId, tcProjectId);
        result.put("newProjectId", newProjectId);
        setResult(result);
    }

    /**
     * <p>
     * Sets the <code>projectId</code> field.
     * </p>
     *
     * @param projectId the projectId value to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>
     * Gets the <code>projectId</code> field.
     * </p>
     *
     * @return the <code>projectId</code> field value
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>
     * Sets the <code>tcProjectId</code> field.
     * </p>
     *
     * @param tcProjectId the tcProjectId value to set
     */
    public void setTcProjectId(long tcProjectId) {
        this.tcProjectId = tcProjectId;
    }
}
