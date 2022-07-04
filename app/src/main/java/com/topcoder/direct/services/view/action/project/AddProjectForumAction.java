/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;

/**
 * <p>
 * This action is used to add a direct project forum into its project forum category.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class AddProjectForumAction extends BaseDirectStrutsAction {

    /**
     * Represents the serial version uid of this class.
     */
    private static final long serialVersionUID = 6305419326874142092L;

    /**
     * Logger for this class.
     */
    private static final Logger logger = Logger.getLogger(AddProjectForumAction.class);

    /**
     * Represents the direct project id.
     */
    private long tcDirectProjectId;

    /**
     * Represents the name of the forum to create.
     */
    private String forumName;

    /**
     * Represents the description of the forum to create.
     */
    private String forumDescription;

    /**
     * <p>
     * Creates an instance of this class. It does nothing.
     * </p>
     */
    public AddProjectForumAction() {
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

        long forumId = getProjectServiceFacade().addTopCoderDirectProjectForum(
                Long.parseLong(projectData.getForumCategoryId()), forumName, forumDescription);
        Map<String, Long> result = new HashMap<String, Long>();
        result.put("forumId", forumId);
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
     * Gets the name of the forum to create.
     * </p>
     * @return the name of the forum to create.
     */
    public String getForumName() {
        return forumName;
    }

    /**
     * <p>
     * Sets the name of the forum to create.
     * </p>
     * @param forumName
     *            the name of the forum to set.
     */
    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    /**
     * <p>
     * Gets the description of the forum to create.
     * </p>
     * @return the description of the forum to create.
     */
    public String getForumDescription() {
        return forumDescription;
    }

    /**
     * <p>
     * Sets the description of the forum to create.
     * </p>
     * @param forumDescription
     *            the description of the forum to set.
     */
    public void setForumDescription(String forumDescription) {
        this.forumDescription = forumDescription;
    }
}
