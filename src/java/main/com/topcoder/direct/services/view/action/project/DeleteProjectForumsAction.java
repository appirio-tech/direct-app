/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * This action is used to delete direct project forums.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class DeleteProjectForumsAction extends BaseDirectStrutsAction {

    /**
     * Represents the serial version uid of this class.
     */
    private static final long serialVersionUID = 1396689050893623175L;

    /**
     * Logger for this class.
     */
    private static final Logger logger = Logger.getLogger(DeleteProjectForumsAction.class);

    /**
     * Represents the direct project id.
     */
    private long tcDirectProjectId;

    /**
     * Represents the id of the direct project forum category.
     */
    private List<Long> forumIds;

    /**
     * <p>
     * Creates an instance of this class. It does nothing.
     * </p>
     */
    public DeleteProjectForumsAction() {
    }

    /**
     * <p>
     * Executes the action. It creates the specified forum under the direct project forum category.
     * </p>
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        if (tcDirectProjectId <= 0) {
            throw new IllegalArgumentException("The direct project id is invalid.");
        }

        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // get the project via the the direct project id
        ProjectData projectData = getProjectServiceFacade().getProject(currentUser, tcDirectProjectId);

        if (projectData == null) {
            throw new IllegalArgumentException("The direct project id to does not exist");
        }
        if (projectData.getForumCategoryId() == null || projectData.getForumCategoryId().trim().length() == 0
                || projectData.getForumCategoryId().trim().equals("0")) {
            throw new IllegalArgumentException("The direct project does not has a project forum created.");
        }

        boolean hasWritePermission = DirectUtils.hasProjectWritePermission(this, currentUser, tcDirectProjectId);
        if (!hasWritePermission) {
            logger.error("User does not have write permission to direct project " + tcDirectProjectId);
            throw new Exception("User does not have write permission to direct project.");
        }

        for (long forumId : forumIds) {
            getProjectServiceFacade().deleteTopCoderDirectProjectForum(
                    Long.parseLong(projectData.getForumCategoryId()), forumId);
        }
        Map<String, Boolean> result = new HashMap<String, Boolean>();
        result.put("success", true);
        setResult(result);
    }

    /**
     * <p>
     * Gets the direct project id.
     * </p>
     * @return the direct project id.
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }

    /**
     * <p>
     * Sets the direct project id.
     * </p>
     * @param tcDirectProjectId
     *            the direct project id to set.
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * <p>
     * Gets the id of the forums to delete.
     * </p>
     * @return the ids of the forums to delete.
     */
    public List<Long> getForumIds() {
        return forumIds;
    }

    /**
     * <p>
     * Sets the ids of the forums to delete.
     * </p>
     * @param forumIds
     *            the forum ids to set.
     */
    public void setForumIds(List<Long> forumIds) {
        this.forumIds = forumIds;
    }
}
