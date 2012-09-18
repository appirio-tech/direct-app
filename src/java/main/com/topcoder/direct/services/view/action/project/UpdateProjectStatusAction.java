/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.dto.project.ProjectStatusType;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     Action to update the cockpit project status.
 * </p>
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Project Status Management)
 */
public class UpdateProjectStatusAction extends BaseDirectStrutsAction {

    /**
     * The warning result type set into json response
     */
    private static final String WARNING_RESULT = "warning";

    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * The direct project status id.
     */
    private long directProjectStatusId;

    /**
     * The core business logic of updating the project status.
     *
     * @throws Exception if any error happens.
     */
    @Override
    protected void executeAction() throws Exception {

        // get the current user
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        if (getProjectServiceFacade() == null) {
            throw new IllegalStateException("The direct project service facade is not initialized.");
        }

        if (getContestServiceFacade() == null) {
            throw new IllegalStateException("The contest service facade is not initialized");
        }

        // check if the status id is valid
        if(ProjectStatusType.getProjectStatusType(directProjectStatusId) == null) {
            throw new IllegalArgumentException("The project status id is invalid.");
        }

        // create the map to store the ajax response
        Map<String, Object> jsonResultData = new HashMap<String, Object>();

        // check if the current user has write permission to the project
        boolean hasPermission = DirectUtils.hasProjectWritePermission(this, currentUser, directProjectId);

        jsonResultData.put("directProjectId", directProjectId);

        // validation if user is allowed to access the project
        if (hasPermission) {
            ProjectData projectData = getProjectServiceFacade().getProject(currentUser, directProjectId);

            // check if status is deleted
            if (ProjectStatusType.getProjectStatusType(directProjectStatusId) == ProjectStatusType.CANCELLED) {
                // yes, check contest status
                ProjectContestsListDTO projectContests = DataProvider.getProjectContests(currentUser.getUserId(), directProjectId);
                List<ProjectContestDTO> contests = projectContests.getContests();

                // check contests
                for(ProjectContestDTO contest : contests) {
                    if (!canDelete(contest.getStatus())) {
                        // has one contest in active / scheduled / completed status
                        jsonResultData.put(WARNING_RESULT, "You cannot delete project with active / scheduled / completed contests.");
                        setResult(jsonResultData);
                        return;
                    }
                }

            }

            // check if the project status is changed
            if (projectData.getProjectStatusId() != directProjectStatusId) {
                projectData.setProjectStatusId(directProjectStatusId);
                getProjectServiceFacade().updateProject(currentUser, projectData);
                jsonResultData.put("updatedStatusId", directProjectStatusId);
                jsonResultData.put("updatedStatusName", ProjectStatusType.getProjectStatusType(directProjectStatusId).getProjectStatusName());
            }

        } else {
            jsonResultData.put(WARNING_RESULT, "You don't have permission to edit the project status.");
        }

        setResult(jsonResultData);

    }

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param directProjectId the direct project id.
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * Gets the direct projects status id.
     *
     * @return the direct project status id.
     */
    public long getDirectProjectStatusId() {
        return directProjectStatusId;
    }

    /**
     * Sets the direct project status id.
     *
     * @param directProjectStatusId the direct project status id.
     */
    public void setDirectProjectStatusId(long directProjectStatusId) {
        this.directProjectStatusId = directProjectStatusId;
    }

    /**
     * Checks whether the give contest status in project allows the project to be deleted.
     *
     * @param status the contest status
     * @return true if allow to delete, false otherwise
     */
    private static boolean canDelete(ContestStatus status) {
        if (status == ContestStatus.DRAFT || status == ContestStatus.DELETED || status == ContestStatus.UNACTIVE) {
            return true;
        } else {
            return false;
        }
    }
}
