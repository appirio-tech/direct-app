/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.action.contest.launch.SaveDraftContestAction;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectOperationDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectOperationType;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.project.*;

import com.topcoder.management.resource.ResourceRole;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.permission.ProjectPermission;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.SoftwareCompetition;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

/**
 * This action creates a new direct project and assigns permissions for the new project.
 *
 * <p>
 *     Version 1.1 updates:
 *     - Add new logic to add copilots for the new project.
 *     - Add new logic to create draft copilot posting for the new project.
 * </p>
 *
 * @author TCSASSEMBLER
 * @version 1.1 (Release Assembly - TopCoder Cockpit Start A New Project Revamp R2)
 */
public class CreateNewProjectAction extends SaveDraftContestAction {

    /**
     * The name of the new project.
     */
    private String projectName;

    /**
     * The description of the new project.
     */
    private String projectDescription;

    /**
     * The permissions to add to the new project.
     */
    private List<ProjectPermission> permissions;

    /**
     * The copilot profile ids to represents the copilots of the new project.
     *
     * @since 1.1
     */
    private long[] copilotIds;

    /**
     * Boolean to represent whether need to create a draft copilot posting.
     *
     * @since 1.1
     */
    private boolean createCopilotPosting;

    /**
     * Gets whether to create draft copilot posting.
     *
     * @return whether to create draft copilot posting.
     * @since 1.1
     */
    public boolean isCreateCopilotPosting() {
        return createCopilotPosting;
    }

    /**
     * Sets whether to create draft copilot posting.
     *
     * @param createCopilotPosting whether to create draft copilot posting.
     * @since 1.1
     */
    public void setCreateCopilotPosting(boolean createCopilotPosting) {
        this.createCopilotPosting = createCopilotPosting;
    }

    /**
     * Gets the copilot profile ids.
     *
     * @return the copilot profile ids.
     * @since 1.1
     */
    public long[] getCopilotIds() {
        return copilotIds;
    }

    /**
     * Sets the copilot profile ids.
     *
     * @param copilotIds the copilot profile ids.
     * @since 1.1
     */
    public void setCopilotIds(long[] copilotIds) {
        this.copilotIds = copilotIds;
    }

    /**
     * Gets the project name
     *
     * @return the project name.
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the project name.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets the project description.
     *
     * @return the project description.
     */
    public String getProjectDescription() {
        return projectDescription;
    }

    /**
     * Sets the project description
     *
     * @param projectDescription the project description.
     */
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    /**
     * Gets the project permissions.
     *
     * @return the project permissions.
     */
    public List<ProjectPermission> getPermissions() {
        return permissions;
    }

    /**
     * Sets the project permissions.
     *
     * @param permissions the project permissions.
     */
    public void setPermissions(List<ProjectPermission> permissions) {
        this.permissions = permissions;
    }

    /**
     * The main logic to create the new project and assign permissions to the new project.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        TCSubject currentUser = DirectStrutsActionsHelper.getTCSubjectFromSession();

        Map<String, String> result = new HashMap<String, String>();

        // create new project first
        ProjectServiceFacade projectServiceFacade = getProjectServiceFacade();

        if (null == projectServiceFacade) {
            throw new IllegalStateException("The project service facade is not initialized");
        }

        // create the project data with the input parameters.
        ProjectData projectData = new ProjectData();
        projectData.setName(getProjectName());
        projectData.setDescription(getProjectDescription());

        // delegate to ProjectServiceFacade to create the project.
        projectData = projectServiceFacade.createProject(currentUser, projectData);

        // put data into result
        result.put("projectName", projectData.getName());
        result.put("projectId", String.valueOf(projectData.getProjectId()));

        setResult(result);

        if (getPermissions() != null) {

            List<ProjectPermission> permissionsToAdd = new ArrayList<ProjectPermission>();

            // add project permission
            for (ProjectPermission p : getPermissions()) {

                p.setProjectId(projectData.getProjectId());
                p.setProjectName(projectData.getName());
                p.setStudio(false);
                p.setUserPermissionId(-1); // always new permission

                // do not add permission for current user who creates the new project
                if (p.getUserId() != currentUser.getUserId()) {
                    permissionsToAdd.add(p);
                }
            }

            ContestServiceFacade contestServiceFacade = getContestServiceFacade();

            if (null == contestServiceFacade) {
                throw new IllegalStateException("The contest service facade is not initialized");
            }

            if (permissionsToAdd.size() > 0) {
                // update when the there is at least 1 permission
                contestServiceFacade.updateProjectPermissions(currentUser, permissionsToAdd, ResourceRole.RESOURCE_ROLE_OBSERVER_ID);
            }

        }

        if (!isCreateCopilotPosting()) {
            // check whether has copilots to add
            if (getCopilotIds() != null && getCopilotIds().length > 0) {
                // add the copilot into the new project
                List<CopilotProject> copilotProjects = new ArrayList<CopilotProject>();
                List<Boolean> removeFlags = new ArrayList<Boolean>();

                for (long id : getCopilotIds()) {
                    removeFlags.add(false);

                    CopilotProject copilotProject = new CopilotProject();
                    copilotProject.setTcDirectProjectId(projectData.getProjectId());
                    copilotProject.setCopilotProfileId(id);
                    copilotProject.setId(0);
                    copilotProjects.add(copilotProject);
                }


                // update copilots projects
                getContestServiceFacade()
                        .updateCopilotProjects(currentUser, copilotProjects,
                                removeFlags);

            }
        } else {
            createCopilotDraftPosting(projectData);
        }
    }


    /**
     * Creates draft copilot posting for the newly created contest.
     *
     * @param directProject the direct project.
     * @return the created competition
     * @throws Exception if error happens when creating the contest.
     * @since 1.1
     */
    private SoftwareCompetition createCopilotDraftPosting(ProjectData directProject) throws Exception {
        SoftwareCompetition cp = new SoftwareCompetition();
        cp.setAssetDTO(getAssetDTOForNewSoftware());
        cp.getAssetDTO().setName(directProject.getName());
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 2);
        XMLGregorianCalendar contestStartDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        cp.getAssetDTO().setProductionDate(contestStartDate);

        // build the project header
        Project projectHeader = new Project();
        ProjectCategory projectCategory = new ProjectCategory();
        projectCategory.setId(29);
        projectCategory.setName("Copilot Posting");
        projectCategory.setProjectType(ProjectType.APPLICATION);
        projectHeader.setProjectCategory(projectCategory);
        projectHeader.setId(-1L);
        projectHeader.setProperty("Billing Project", "0");
        projectHeader.setProperty("Confidentiality Type", "public");
        projectHeader.setProperty("Copilot Cost", "0");
        projectHeader.setProperty("Project Name", directProject.getName());
        projectHeader.setTcDirectProjectId(directProject.getProjectId());
        projectHeader.setTcDirectProjectName(directProject.getName());

        // set spec info - do not need spec review
        ProjectSpec projectSpec = new ProjectSpec();
        projectSpec.setProjectSpecId(0L);
        projectHeader.setProjectSpec(projectSpec);

        // add prize
        List<Prize> prizes = new ArrayList<Prize>();
        Prize firstPlace = new Prize();
        firstPlace.setNumberOfSubmissions(1);
        firstPlace.setPlace(1);
        firstPlace.setPrizeAmount(150);
        com.topcoder.management.project.PrizeType prizeType = new com.topcoder.management.project.PrizeType();
        prizeType.setDescription("Contest Prize");
        prizeType.setId(15L);
        firstPlace.setPrizeType(prizeType);
        prizes.add(firstPlace);

        projectHeader.setPrizes(prizes);
        projectHeader.setProperty("Admin Fee", "0");
        projectHeader.setProperty("Copilot Cost", "0");
        projectHeader.setProperty("DR points", "0");
        projectHeader.setProperty("Payments", "150");
        projectHeader.setProperty("First Place Cost", "150");
        projectHeader.setProperty("Reliability Bonus Cost", "0");
        projectHeader.setProperty("Milestone Bonus Cost", "0");
        projectHeader.setProperty("Spec Review Cost", "0");
        projectHeader.setProperty("Second Place Cost", "0");
        projectHeader.setProperty("Review Cost", "0");

        cp.setId(-1L);
        cp.setProjectHeader(projectHeader);

        initializeCompetition(cp);
        populateCompetition(cp);

        cp = getContestServiceFacade().createSoftwareContest(DirectUtils.getTCSubjectFromSession(), cp,
                directProject.getProjectId(), null, null);

        return cp;
    }

}
