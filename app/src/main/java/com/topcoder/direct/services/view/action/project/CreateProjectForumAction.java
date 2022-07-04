/*
 * Copyright (C) 2011-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Action which creates the project forum for the direct project.
 *
 * <p>
 * Version 1.1 (TopCoder Cockpit Project Overview R2 Project Forum Backend Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #executeAction()} method to implement the logic for forum creation.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two) change notes:
 *    <ol>
 *        <li>Using <code>Project Service Facade</code> instead of <code>Contest Service Facade</code> for creating
 *        project forums.</li>
 *    </ol>  
 * </p>
 *
 * <p>
 * Version 1.3 (Release Assembly - TC Direct Project Forum Configuration Assembly ) change notes:
 *    <ol>
 *        <li>Updated according to the change in project service facade.</li>
 *    </ol>  
 * </p>
 *
 * @author GreatKevin, isv, TCSASSEMBLY
 * @version 1.3
 */
public class CreateProjectForumAction extends BaseDirectStrutsAction {

    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CreateProjectForumAction.class);

    /**
     * The direct project id.
     */
    private long tcDirectProjectId;

    /**
     * <p>Constructs new <code>CreateProjectForumAction</code> instance. This implementation does nothing.</p>
     */
    public CreateProjectForumAction() {
    }

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     */
    public long getTcDirectProjectId() {
        return tcDirectProjectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param tcDirectProjectId the direct project id to set.
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }

    /**
     * Handles the project forum creation request.
     *
     * @throws Exception
     */
    @Override
    protected void executeAction() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();

        // check if the tc direct project id is valid
        if (getTcDirectProjectId() <= 0) {
            throw new IllegalArgumentException("The direct project id to create forum is invalid.");
        }

        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // get the project via the the direct project id
        ProjectData projectData = getProjectServiceFacade().getProject(currentUser, getTcDirectProjectId());

        if (projectData == null) {
            throw new IllegalArgumentException("The direct project id to create forum does not exist");
        }

        if (projectData.getForumCategoryId() != null && projectData.getForumCategoryId().trim().length() != 0) {
            // not going to happen, but should always check
            throw new IllegalArgumentException("The direct project already has a project forum created.");
        }

        // check if has write permission on the project
        boolean hasWritePermission = DirectUtils.hasProjectWritePermission(this, currentUser, getTcDirectProjectId());

        if (hasWritePermission) {
            // Create project forums
            logger.debug("Calling Forums EJB for creating the forum for project " + getTcDirectProjectId());
            long forumId = getProjectServiceFacade().createTopCoderDirectProjectForum(currentUser,
                                                                                      projectData.getProjectId(), null, null);
            result.put("projectForumId", forumId);
        } else {
            logger.error("User does not have write permission for project " + getTcDirectProjectId());
            throw new Exception("You don't have permission to create project forum.");
        }

        // set the result
        setResult(result);
    }
}
