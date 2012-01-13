/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project.edit;


import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.edit.EditCockpitProjectDTO;
import com.topcoder.direct.services.view.form.ProjectIdForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.project.ProjectData;

import java.util.List;

/**
 * <p>
 * Action for viewing edit project settings page.
 * </p>
 *
 * @version 1.0 (Module Assembly - TopCoder Cockpit Project Dashboard Edit Project)
 * @TCSASSEMBLER
 */
public class EditCockpitProjectAction extends BaseDirectStrutsAction implements FormAction<ProjectIdForm>,
        ViewAction<EditCockpitProjectDTO> {

    /**
     * <p>A <code>ProjectIdForm</code> providing the ID of a requested project.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * View data for edit project settings page.
     */
    private EditCockpitProjectDTO viewData = new EditCockpitProjectDTO();

    /**
     * Gets the view data.
     *
     * @return the view data.
     */
    public EditCockpitProjectDTO getViewData() {
        return viewData;
    }

    /**
     * Sets the view data.
     *
     * @param viewData the view data to set.
     */
    public void setViewData(EditCockpitProjectDTO viewData) {
        this.viewData = viewData;
    }

    /**
     * Gets the form data.
     *
     * @return the form data.
     */
    public ProjectIdForm getFormData() {
        return formData;
    }

    /**
     * Sets the form data.
     *
     * @param formData the form data to set.
     */
    public void setFormData(ProjectIdForm formData) {
        this.formData = formData;
    }

    /**
     * Main logic of action execution.
     *
     * @throws Exception if any error
     */
    @Override
    protected void executeAction() throws Exception {

        // get current user from session
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // get the general project information
        ProjectData projectData = getProjectServiceFacade().getProject(currentUser, getFormData().getProjectId());

        viewData.setProject(projectData);

        viewData.setClientId(DirectUtils.getClientIdForProject(currentUser, getFormData().getProjectId()));

        List<ProjectBriefDTO> projects
                = DataProvider.getUserProjects(currentUser.getUserId());
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        viewData.setUserProjects(userProjectsDTO);

        ProjectBriefDTO currentProject = new ProjectBriefDTO();
        currentProject.setId(projectData.getProjectId());
        currentProject.setName(projectData.getName());
        currentProject.setProjectForumCategoryId(projectData.getForumCategoryId());
        if (viewData.getClientId() != null) {
            currentProject.setCustomerId(viewData.getClientId());
            setCustomProjectMetadata(viewData.getClientId());
        }

        List<DirectProjectMetadata> allProjectMetadata = getMetadataService().getProjectMetadataByProject(formData.getProjectId());

        setCommonProjectMetadata(allProjectMetadata);

        getSessionData().setCurrentProjectContext(currentProject);

        List<TypedContestBriefDTO> contests
                = DataProvider.getProjectTypedContests(currentUser.getUserId(), currentProject.getId());
        getSessionData().setCurrentProjectContests(contests);

        getSessionData().setCurrentSelectDirectProjectID(currentProject.getId());

    }

    /**
     * Gets data from project's metadata and set into the DTO.
     *
     * @param allMetadata the list of all the metadata of the project.
     */
    private void setCommonProjectMetadata(List<DirectProjectMetadata> allMetadata) {

        for (DirectProjectMetadata data : allMetadata) {
            long keyId = data.getProjectMetadataKey().getId();
            String value = data.getMetadataValue();

            if (value == null || value.trim().length() == 0) {
                // value does not exist, continue
                continue;
            }

            if (keyId == 1L) {
                // client manager user ids
                getViewData().getClientManagerIds().add(data);
            } else if (keyId == 2L) {
                // tc manager user ids
                getViewData().getTcManagerIds().add(data);
            } else if (keyId == 3L) {
                // project budget
                getViewData().setBudget(data);
            } else if (keyId == 4L) {
                // svn address
                getViewData().setSvnURL(data);
            } else if (keyId == 5L) {
                // JIRA address
                getViewData().setJiraURL(data);
            } else if (keyId == 6L) {
                // duration
                getViewData().setDuration(data);
            } else if (keyId == 9L) {
                getViewData().setPrivacy(data);
            }
        }
    }

    /**
     * Gets the custom project metadata of project.
     *
     * @param clientId the client id.
     * @throws Exception if any error.
     */
    private void setCustomProjectMetadata(long clientId) throws Exception {

        List<DirectProjectMetadataKey> customKeys = getMetadataKeyService().getClientProjectMetadataKeys(clientId, null);

        for (DirectProjectMetadataKey ck : customKeys) {
            List<DirectProjectMetadata> values = getMetadataService().getProjectMetadataByProjectAndKey(getFormData().getProjectId(), ck.getId());
            getViewData().getCustomMetadata().put(ck, values);
        }
    }
}
