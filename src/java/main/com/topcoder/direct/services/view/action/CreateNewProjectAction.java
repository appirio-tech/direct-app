/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;

import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.service.project.entities.ProjectAnswer;
import com.topcoder.service.project.entities.ProjectAnswerOption;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.action.contest.launch.SaveDraftContestAction;
import com.topcoder.direct.services.view.dto.project.ProjectForumTemplateDTO;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.project.*;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.permission.ProjectPermission;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.SoftwareCompetition;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.*;

/**
 * This action creates a new direct project and assigns permissions for the new
 * project.
 * 
 * <p>
 * Version 1.1 Change notes:
 * <ul>
 * <li>Change the base class from <code>BaseDirectStrutsAction</code> to
 * <code>SaveDraftContestAction</code>.</li>
 * <li>Added {@link #createCopilotDraftPosting(ProjectData)} to create a draft
 * copilot contest for the new created Presentation Project.</li>
 * <li>Updated {@link #executeAction()} method to create a draft copilot contest
 * if the new created project is Presentation Project.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.1 updates: - Add new logic to add copilots for the new project. -
 * Add new logic to create draft copilot posting for the new project.
 * </p>
 * 
 * <p>
 * Version 1.2 updates: - change call of contest service facade
 * #updateProjectPermissions to permission service facade
 * #updateProjectPermissions
 * </p>
 * <p>
 * Version 1.3 (Release Assembly - TC Cockpit Create Project Refactoring
 * Assembly Part Two) change notes:
 * <ol>
 * <li>Updated {@link #executeAction()} method to call newly added method for
 * creating TC Direct project via Project Service Facade.</li>
 * </ol>
 * </p>
 * <p>
 * Version 1.3.1 (Release Assembly - TopCoder Cockpit Start New Mobile and PPT
 * Projects Flow) change notes:
 * <ol>
 * <li>Updated {@link #createCopilotDraftPosting()} method by setting correct
 * project name and project detailed requirements fields.</li>
 * </ol>
 * </p>
 * Version 1.3.2 (Release Assembly - TC Direct Project Forum Configuration
 * Assembly) change notes:
 * <ol>
 * <li>Added <code>forums</code> field.</li>
 * <li>Modified {@link #executeAction()} to setup customized forums for the new
 * project.</li>
 * </ol>
 * </p>
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Cockpit Start New Project Data
 * Persistence) change notes:
 * <ol>
 * <li>Added <code>projectData</code> field.</li>
 * <li>Modified {@link #executeAction()} to setup project answers for the new
 * project.</li>
 * </ol>
 * </p>
 * <p>
 * Version 1.5 (Release Assembly - Release Assembly - TopCoder Direct Prize-Project
 * Link Update) change notes:
 * <ol>
 * <li>Modified {@link #createCopilotDraftPosting()} to save the project id into the
 * prizes of new copilot posting project.</li>
 * </ol>
 * </p>
 *
 * @author Veve, isv, KennyAlive, Ghost_141, frozenfx
 * @version 1.5
 */
public class CreateNewProjectAction extends SaveDraftContestAction {

    /**
     * The lag between of the start date of the draft copilot contest.
     * 
     * @since 1.1
     */
    private static final long COPILOT_CONTEST_START_DATE_LAG = 48 * 60 * 60 * 1000;

    /**
     * The JIRA project to create issue for PPT project.
     */
    private String pptJIRAProject;

    /**
     * The id of JIRA issue type when creating issue for PPT project.
     */
    private int pptJIRAIssueTypeId;

    /**
     * The JIRA issue reporter when creating issue for PPT project.
     */
    private String pptJIRAIssueReporter;

    /**
     * The URL prefix of copilot contest page.
     */
    private String copilotURLPrefix;

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
     * The project forum configuration.
     */
    private List<ProjectForumTemplateDTO> forums;

    /**
     * A flag indicates whether the project is presentation project.
     * 
     * @since 1.1
     */
    private boolean presentationProject = false;

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
     * <p>
     * Represent the project data.
     * </p>
     */
    private ProjectData projectData;

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
     * @param createCopilotPosting
     *            whether to create draft copilot posting.
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
     * @param copilotIds
     *            the copilot profile ids.
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
     * @param projectName
     *            the project name.
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
     * @param projectDescription
     *            the project description.
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
     * @param permissions
     *            the project permissions.
     */
    public void setPermissions(List<ProjectPermission> permissions) {
        this.permissions = permissions;
    }

    /**
     * <p>
     * Gets the project forum configuration.
     * </p>
     * 
     * @return the project forum configuration.
     */
    public List<ProjectForumTemplateDTO> getForums() {
        return forums;
    }

    /**
     * <p>
     * Sets the project forum configuration.
     * </p>
     * 
     * @param forums
     *            the project forum configuration to set.
     */
    public void setForums(List<ProjectForumTemplateDTO> forums) {
        this.forums = forums;
    }

    /**
     * Gets the flag indicates whether the project is presentation project.
     * 
     * @return true if the project is presentation project, false otherwise.
     * @since 1.1
     */
    public boolean isPresentationProject() {
        return presentationProject;
    }

    /**
     * Sets the flag indicates whether the project is presentation project.
     * 
     * @param presentationProject
     *            true if the project is presentation project, false otherwise.
     * @since 1.1
     */
    public void setPresentationProject(boolean presentationProject) {
        this.presentationProject = presentationProject;
    }

    /**
     * Sets the JIRA project to create issue for PPT project.
     * 
     * @param pptJIRAProject
     *            the JIRA project to create issue for PPT project.
     */
    public void setPptJIRAProject(String pptJIRAProject) {
        this.pptJIRAProject = pptJIRAProject;
    }

    /**
     * Sets the id of JIRA issue type when creating issue for PPT project.
     * 
     * @param pptJIRAIssueTypeId
     *            the id of JIRA issue type when creating issue for PPT project.
     */
    public void setPptJIRAIssueTypeId(int pptJIRAIssueTypeId) {
        this.pptJIRAIssueTypeId = pptJIRAIssueTypeId;
    }

    /**
     * Sets the JIRA issue reporter when creating issue for PPT project.
     * 
     * @param pptJIRAIssueReporter
     *            the JIRA issue reporter when creating issue for PPT project.
     */
    public void setPptJIRAIssueReporter(String pptJIRAIssueReporter) {
        this.pptJIRAIssueReporter = pptJIRAIssueReporter;
    }

    /**
     * Sets the URL prefix of copilot contest page.
     * 
     * @param copilotURLPrefix
     *            the URL prefix of copilot contest page.
     */
    public void setCopilotURLPrefix(String copilotURLPrefix) {
        this.copilotURLPrefix = copilotURLPrefix;
    }

    /**
     * <p>
     * Get the projectData.
     * </p>
     * 
     * @return projectData the projectData.
     */
    public ProjectData getProjectData() {
        return projectData;
    }

    /**
     * <p>
     * Set the projectData.
     * </p>
     * 
     * @param projectData
     *            the projectData to set.
     */
    public void setProjectData(ProjectData projectData) {
        this.projectData = projectData;
    }

    /**
     * The main logic to create the new project and assign permissions to the
     * new project.
     * 
     * @throws Exception
     *             if there is any error.
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
        // prepare the project answer.
        for (ProjectAnswer answer : getProjectData().getProjectAnswers()) {
            if (answer.getOptionAnswers() != null) {
                for (ProjectAnswerOption answerOption : answer.getOptionAnswers()) {
                    answerOption.setProjectAnswer(answer);
                }
            }
            // replace XWorkList by ArrayList.
            if (answer.getMultipleAnswers() != null) {
                List<String> s = new ArrayList<String>();
                for (String mAnswer : answer.getMultipleAnswers()) {
                    s.add(mAnswer);
                }
                answer.setMultipleAnswers(s);
            }
        }
        projectData.setProjectAnswers(getProjectData().getProjectAnswers());

        if (forums != null) {
            Map<String, String> forumsMap = new HashMap<String, String>();
            for (ProjectForumTemplateDTO forum : forums) {
                forumsMap.put(forum.getForumName(), forum.getForumDescription());
            }
            // delegate to ProjectServiceFacade to create the project.
            projectData = projectServiceFacade.createTCDirectProject(currentUser, projectData, getPermissions(),
                    forumsMap);
        } else {
            // delegate to ProjectServiceFacade to create the project.
            projectData = projectServiceFacade.createTCDirectProject(currentUser, projectData, getPermissions());
        }

        // put data into result
        result.put("projectName", projectData.getName());
        result.put("projectId", String.valueOf(projectData.getProjectId()));

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
                getContestServiceFacade().updateCopilotProjects(currentUser, copilotProjects, removeFlags);

            }

            if (presentationProject) {
                // create the draft copilot contest
                createPPTCopilotDraftPosting(projectData);
                // create JIRA issue
                Map<String, Object> conetstResult = (Map<String, Object>) getResult();
                String description = "Copilot Opportunities: " + copilotURLPrefix + conetstResult.get("projectId");
                JiraRpcServiceWrapper.createIssue(pptJIRAProject, pptJIRAIssueTypeId, projectName, description,
                        pptJIRAIssueReporter);
            }
        } else {
            createCopilotDraftPosting(projectData);
        }

        setResult(result);
    }

    /**
     * Create a draft copilot contest for the new created Presentation Project.
     * 
     * @param projectData
     *            the new created Presentation Project.
     * @throws Exception
     *             if any error occurs.
     */
    private void createPPTCopilotDraftPosting(ProjectData projectData) throws Exception {
        setTcDirectProjectId(projectData.getProjectId());
        Date startDate = new Date();
        startDate.setTime(new Date().getTime() + COPILOT_CONTEST_START_DATE_LAG);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(startDate.getTime());
        DatatypeFactory df = DatatypeFactory.newInstance();
        getAssetDTO().setProductionDate(df.newXMLGregorianCalendar(gc));

        super.executeAction();
    }

    /**
     * Creates draft copilot posting for the newly created project.
     * 
     * @param directProject
     *            the direct project.
     * @return the created competition
     * @throws Exception
     *             if error happens when creating the contest.
     * @since 1.1
     */
    private SoftwareCompetition createCopilotDraftPosting(ProjectData directProject) throws Exception {
        SoftwareCompetition cp = new SoftwareCompetition();
        cp.setAssetDTO(getAssetDTOForNewSoftware());

        String name = getAssetDTO().getName();
        if (name == null || name.equals("")) {
            name = directProject.getName();
        }
        cp.getAssetDTO().setName(name);

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
        projectHeader.setProperty("Project Name", name);
        projectHeader.setTcDirectProjectId(directProject.getProjectId());
        projectHeader.setTcDirectProjectName(directProject.getName());

        // set spec info - do not need spec review
        ProjectSpec projectSpec = new ProjectSpec();
        projectSpec.setProjectSpecId(0L);
        if (getProjectHeader() != null && getProjectHeader().getProjectSpec() != null) {
            projectSpec.setDetailedRequirements(getProjectHeader().getProjectSpec().getDetailedRequirements());
        }
        projectHeader.setProjectSpec(projectSpec);

        // add prize
        List<Prize> prizes = new ArrayList<Prize>();
        Prize firstPlace = new Prize();
        firstPlace.setNumberOfSubmissions(1);
        firstPlace.setPlace(1);
        firstPlace.setPrizeAmount(150);
        firstPlace.setProjectId(directProject.getProjectId());

        Prize secondPlace = new Prize();
        secondPlace.setNumberOfSubmissions(1);
        secondPlace.setPlace(2);
        secondPlace.setPrizeAmount(75);
        secondPlace.setProjectId(directProject.getProjectId());

        com.topcoder.management.project.PrizeType prizeType = new com.topcoder.management.project.PrizeType();
        prizeType.setDescription("Contest Prize");
        prizeType.setId(15L);

        firstPlace.setPrizeType(prizeType);
        prizes.add(firstPlace);
        secondPlace.setPrizeType(prizeType);
        prizes.add(secondPlace);

        projectHeader.setPrizes(prizes);
        projectHeader.setProperty(ProjectPropertyType.ADMIN_FEE_PROJECT_PROPERTY_KEY, "0");
        projectHeader.setProperty(ProjectPropertyType.COPILOT_COST_PROJECT_PROPERTY_KEY, "0");
        projectHeader.setProperty(ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY, "0");
        projectHeader.setProperty(ProjectPropertyType.PAYMENTS_PROJECT_PROPERTY_KEY, "150");
        projectHeader.setProperty(ProjectPropertyType.FIRST_PLACE_COST_PROJECT_PROPERTY_KEY, "150");
        projectHeader.setProperty(ProjectPropertyType.RELIABILITY_BONUS_COST_PROJECT_PROPERTY_KEY, "0");
        projectHeader.setProperty(ProjectPropertyType.MILESTONE_BONUS_COST_PROJECT_PROPERTY_KEY, "0");
        projectHeader.setProperty(ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY, "0");
        projectHeader.setProperty(ProjectPropertyType.SECOND_PLACE_COST_PROJECT_PROPERTY_KEY, "75");
        projectHeader.setProperty(ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY, "0");

        cp.setId(-1L);
        cp.setProjectHeader(projectHeader);

        initializeCompetition(cp);
        populateCompetition(cp);

        cp = getContestServiceFacade().createSoftwareContest(DirectUtils.getTCSubjectFromSession(), cp,
                directProject.getProjectId(), null, null);

        return cp;
    }
}
