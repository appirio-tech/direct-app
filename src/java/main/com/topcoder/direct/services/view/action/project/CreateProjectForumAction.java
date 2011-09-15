package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;

import java.util.HashMap;
import java.util.Map;

/**
 * Action which creates the project forum for the direct project.
 *
 * @author TCSASSEMBLER
 * @version 1.0
 */
public class CreateProjectForumAction extends BaseDirectStrutsAction {

    /**
     * The direct project id.
     */
    private long tcDirectProjectId;

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
            // add project forum creation / permission setting logic here
            // PLACEHOLDER for following assembly

            // use direct project id as forum id to test
            projectData.setForumCategoryId(String.valueOf(getTcDirectProjectId()));

            getProjectServiceFacade().updateProject(currentUser, projectData);

            result.put("projectForumId", projectData.getForumCategoryId());
        } else {
            throw new Exception("You don't have permission to create project forum.");
        }

        // set the result
        setResult(result);
    }
}
