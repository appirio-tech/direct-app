/*
 * Copyright (C) 2010 - 2019 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.clients.model.ProjectContestFeePercentage;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectAccess;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.SortOrder;
import com.topcoder.direct.services.view.action.accounting.BaseContestFeeAction;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.dto.contest.ContestCopilotDTO;
import com.topcoder.direct.services.view.dto.contest.ProblemDTO;
import com.topcoder.direct.services.view.dto.contest.ReviewScorecardDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.util.AuthorizationProvider;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.challenge.CostCalculationService;
import com.topcoder.management.project.ProjectGroup;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.project.DAOFault;
import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.*;

/**
 * <p>
 * Common action to handle some common requests.
 * </p>
 *
 * <p> Version 1.1 Changes:
 * - New property directProjectId is added.
 * - New method getDirectProjectCopilots to return copilots of direct project as JSON.
 * </p>
 *
 * <p> Version 1.2 Changes (Module Assembly - Contest Fee Based on % of Member Cost User Part):
 * - Modified method getBillingProjectContestFees. Added support for fee percentage.
 * - If the billing is configured by percentage of member cost, the contest fee will be calculated 
 * - as a percentage of the member cost.
 * </p>
 *
 * <p>
 * Version 1.3 Changes (Release Assembly - TopCoder Cockpit Billing Account Project Association)
 * - Add method {@link #getBillingAccountsForProject()} to handle ajax request to get billing accounts for project.
 * </p>
 *
 * <p>
 * Version 1.4 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
 * - Updates method {@link #getBillingAccountsForProject()} to return all the billing accounts of the project without
 * checking the billing account permission of the users.
 * </p>
 * 
 * <p>
 * Version 1.5 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match)
 * - New method {@link #getActiveProblemSet()} to return active problems as JSON.
 * </p>
 *
 * <p>
 * Version 1.6 (Module Assembly - TC Cockpit Contest Milestone Association 1)
 * <ul>
 *     <li>Adds method {@link #getDirectProjectMilestones()} to get milestones of the specified project by ajax</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.7 (Release Assembly - TopCoder Cockpit Navigation Update)
 * <ul>
 *     <li>Added method {@link #getCurrentUserRecentProjects()}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.8 (Release Assembly - TopCoder Cockpit Right Sidebar Update)
 * <ul>
 *     <li>Added method {@link #getDirectProjectContests()} to process the AJAX request to
 *     get all the contests of a direct project</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9 (Release Assembly - TC Cockpit Private Challenge Update)
 * <ul>
 *     <li>Updated method {@link #getBillingProjectContestFees()} to include the billing account
 *     groups data</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.10 (Topcoder - Add effort hours field)
 * <ul>
 *     <li>Add enable effort hours</li>
 * </ul>
 * </p>
 *
 * Version 1.11 (Topcoder - Integrate Direct with Groups V5)
 * <ul>
 *     <li>Refactor projectGroup to comply with v5</li>
 * </ul>
 * @author BeBetter, pvmagacho, GreatKevin, bugbuka, GreatKevin, TCSCODER
 * @version 1.11
 */
public class CommonAction extends BaseContestFeeAction {
    /**
     * <p>
     * Billing project id field.
     * </p>
     */
    private long billingProjectId;

    /**
     * <p>
     * The direct project id field.
     * </p>
     *
     * @since 1.1
     */
    private long directProjectId;

    /**
     * <p>
     * Software catalog id.
     * </p>
     */
    private long catalogId = -1;

    private CostCalculationService costCalculationService;

    private long categoryId;

    /**
     * Endpoint to group of a user
     */
    private String userGroupsApiEndpoint;


    /**
     * <p>
     * Executes the action.
     * </p>
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * <p>
     * Gets the copilot user ids / copilot handle for the given direct project id.
     * </p>
     *
     * @return the success result
     * @throws Exception if any error occurs
     * @since 1.1
     */
    public String getDirectProjectCopilots() throws Exception {
        // get all the copilots for the given direct project
        List<ContestCopilotDTO> copilots = DataProvider.getCopilotsForDirectProject(this.directProjectId);
        long currentUserId = getCurrentUser().getUserId();
        boolean isCurrentUser = false;

        // use map to store the copilots result, key is copilot user id, value is copilot handle
        Map<String, String> value = new HashMap<String, String>();
        for (ContestCopilotDTO copilot : copilots) {
            value.put(String.valueOf(copilot.getUserId()), copilot.getHandle());
            
            // current user is one of the copilots
            if(currentUserId == copilot.getUserId()) {
                isCurrentUser = true;
            }
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("copilots", value);
        result.put("selected", (isCurrentUser ? String.valueOf(currentUserId) : "0"));
        setResult(result);
        return SUCCESS;
    }

    /**
     * Handles the ajax request to get the milestones of the specified project.
     *
     * @return the result code.
     * @since 1.6
     */
    public String getDirectProjectMilestones() {

        try {
            List<Milestone> milestones = getMilestoneService().getAll(this.directProjectId,
                                                               Arrays.asList(new MilestoneStatus[]{MilestoneStatus.OVERDUE, MilestoneStatus.UPCOMING}),
                                                               SortOrder.ASCENDING);
            ObjectMapper m = new ObjectMapper();

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

            for (Milestone mi : milestones) {
                mi.setName(StringEscapeUtils.escapeHtml4(StringEscapeUtils.escapeJson(mi.getName())));
                mi.setDescription(StringEscapeUtils.escapeHtml4(StringEscapeUtils.escapeJson((mi.getDescription()))));
                result.add(m.convertValue(mi, Map.class));
            }

            setResult(result);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    public String getReviewScorecards() {
       try {
           List<ReviewScorecardDTO> reviewScorecards = DataProvider.getReviewScorecardDTO(this.categoryId);
           List<Map<String, String>> result = new ArrayList<Map<String, String>>();
           for (ReviewScorecardDTO c : reviewScorecards) {
               Map<String, String> reviewScorecard = new HashMap<String, String>();
               reviewScorecard.put("id", String.valueOf(c.getScorecardId()));
               reviewScorecard.put("projectCategory", String.valueOf(c.getProjectCategoryId()));
               reviewScorecard.put("scorecardName", c.getScorecardName());
               reviewScorecard.put("scorecardVersion", c.getScorecardVersion());
               result.add(reviewScorecard);
           }
           setResult(result);
       } catch (Throwable e) {
           if (getModel() != null) {
               setResult(e);
           }
       }
       return SUCCESS;
    }

    /**
     * Handles the ajax request to get all the contests of the direct project specified by the form input
     * <code>directProjectId</code>.
     *
     * @return the result code.
     * @since 1.8
     */
    public String getDirectProjectContests() {

        try {

            // check if the current login user has permission to access
            if(!AuthorizationProvider.isUserGrantedAccessToProject(DirectUtils.getTCSubjectFromSession(), this.directProjectId)) {
                throw new IllegalArgumentException(
                        "Current user does not have access to the direct project:" + this.directProjectId);
            }

            // get all the contests of the direct project through query
            List<TypedContestBriefDTO> contests = DataProvider
                    .getProjectTypedContests(DirectUtils.getTCSubjectFromSession().getUserId(),
                            this.directProjectId);

            // the result list which will be serialized to json and returned by the AJAX response
            List<Map<String, String>> result = new ArrayList<Map<String, String>>();

            for (TypedContestBriefDTO c : contests) {
                Map<String, String> contestResult = new HashMap<String, String>();
                contestResult.put("id", String.valueOf(c.getId()));
                contestResult.put("name", c.getTitle());
                contestResult.put("statusName", c.getStatus().getName());
                contestResult.put("statusShortName", c.getStatus().getShortName());
                contestResult.put("typeName", c.getContestType().getName());
                contestResult.put("typeShortName", c.getContestType().getLetter());
                result.add(contestResult);
            }

            setResult(result);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    
    /**
     * <p>
     * Gets the active problems.
     * </p>
     * 
     * @return the active problems.
     * @since 1.5
     */
    public String getActiveProblemSet() throws Exception {

        // gets the billing accounts associated to the project
        List<ProblemDTO> problems = DataProvider.getActiveProblemSet();

        Map<String, String> result = new HashMap<String, String>();

        // filter out the billing accounts user has access to
        for(ProblemDTO p : problems) {
            result.put(String.valueOf(p.getId()), p.getName());
        }

        setResult(result);
        return SUCCESS;
    }
    

    /**
     * <p>
     * Gets the studio configurations.
     * </p>
     *
     * @return the success page
     * @throws Exception if any error occurs
     */
    public String getContestConfigs() throws Exception {

        Map<String, Object> configs = new HashMap<String, Object>();
        configs.put("overview", ConfigUtils.getStudioOverviews());
        configs.put("fileTypes", ConfigUtils.getFileTypes().getFileTypes());


        ConfigUtils.ChallengeFeeConfiguration challengeFeeConfiguration = ConfigUtils.getChallengeFeeConfiguration();

        configs.put("softwareContestFees", challengeFeeConfiguration.getDevelopment());
        configs.put("studioContestFees", challengeFeeConfiguration.getDesign());
        configs.put("algorithmContestFees", challengeFeeConfiguration.getData());



        configs.put("copilotFees", ConfigUtils.getCopilotFees());
        configs.put("billingInfos", getBillingProjectInfos());
        configs.put("platforms", getReferenceDataBean().getPlatforms());
        configs.put("technologies", getReferenceDataBean().getTechnologies());
        setResult(configs);
        return SUCCESS;
    }

    /**
     * <p>
     * Gets project studio contest fee.
     * </p>
     *
     * @return the success page
     * @throws Exception if any error occurs
     */
    public String getBillingProjectContestFees() throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        
        // set the percentage info
        ProjectContestFeePercentage percentage = getContestFeePercentageService().getByProjectId(billingProjectId);
        if (percentage != null && percentage.isActive()) {
            result.put("percentage", percentage);
        } else {
            // Only return fee if percentage is not set
            TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
            List<ProjectContestFee> contestFees = getAdminServiceFacade().getContestFeesByProject(tcSubject,
                billingProjectId);
            result.put("fees", contestFees);
        }

        // set the security group info
        List<IdNamePair> groups = DataProvider.getSecurityGroupsForBillingAccount(
                billingProjectId);

        result.put("groups", groups);

        setResult(result);
        return SUCCESS;
    }

    /**
     * Gets the billing accounts for the project.
     *
     * @return the result code
     * @since 1.3
     */
    public String getBillingAccountsForProject() {
        try {
            // gets the billing accounts associated to the project
            List<Project> billingAccountsByProject = getProjectServiceFacade().getBillingAccountsByProject(getDirectProjectId());

            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            
            long[] billingAccountIds = new long[billingAccountsByProject.size()];
            
            for (int i = 0; i < billingAccountIds.length; i++){
                billingAccountIds[i] = billingAccountsByProject.get(i).getId();
            }
            
            boolean[] requireCCAs = getContestServiceFacade().requireBillingProjectsCCA(billingAccountIds);
            
            for (int i = 0; i < billingAccountIds.length; i++){
                Map<String, Object> billingAccount = new HashMap<String, Object>();
                billingAccount.put("id", String.valueOf(billingAccountsByProject.get(i).getId()));
                billingAccount.put("name", billingAccountsByProject.get(i).getName());
                billingAccount.put("cca", String.valueOf(requireCCAs[i]));
                // Add enableEffortHours for each billing account
                Client client = billingAccountsByProject.get(i).getClient();
                if (client != null && client.isEffortHoursEnabled() != null) {
                    billingAccount.put("enableEffortHours", String.valueOf(client.isEffortHoursEnabled()));
                } else {
                    billingAccount.put("enableEffortHours", "false");
                }

                result.add(billingAccount);
            }
            setResult(result);
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    /**
     * Gets the projects the current user recently accessed.
     *
     * @return the result code.
     * @since 1.7
     */
    public String getCurrentUserRecentProjects() {
        try {
            List<DirectProjectAccess> userRecentDirectProjects = DataProvider.getUserRecentDirectProjects(
                    DirectUtils.getTCSubjectFromSession().getUserId());

            ObjectMapper m = new ObjectMapper();

            setResult(m.convertValue(userRecentDirectProjects, List.class));
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }

        return SUCCESS;
    }

    
    /**
     * <p>
     * Gets the billing project information.
     * </p>
     *
     * @return the billing project information. each project is represented in a map object.
     * @throws DAOFault if a DAO error occurs
     */
    private List<Map<String, Object>> getBillingProjectInfos() throws DAOFault {
        List<Map<String, Object>> billings = new ArrayList<Map<String, Object>>();

        for (Project project : getProjectServiceFacade().getClientProjectsByUser(
            DirectStrutsActionsHelper.getTCSubjectFromSession())) {
            Map<String, Object> projectInfo = new HashMap<String, Object>();
            projectInfo.put("id", project.getId());
            projectInfo.put("manualPrizeSetting", project.isManualPrizeSetting());
            billings.add(projectInfo);
        }

        return billings;
    }

    /**
     * <p>
     * Gets all project groups
     * </p>
     *
     * @return the billing project information. each project is represented in a map object.
     * @throws ContestServiceException if contest service exception occurs
     */
    private List<ProjectGroup> getAllProjectGroups() throws ContestServiceException {
        return Arrays.asList(getContestServiceFacade().getAllProjectGroups(DirectStrutsActionsHelper.getTCSubjectFromSession()));
    }


    /**
     * <p>
     * Gets a list of design components for the given user.
     * </p>
     *
     * @return a list of design components.
     * @throws ContestServiceException if contest service exception occurs
     */
    public String getDesignComponents() throws ContestServiceException {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("designs", getContestServiceFacade().getDesignComponents(getCurrentUser()));
        setResult(result);
        return SUCCESS;
    }

    /**
     * <p>
     * Gets the categories.
     * </p>
     *
     * @return the categories
     */
    public String getCategories() {
        setResult(getReferenceDataBean().getCatalogToCategoriesMap().get(catalogId));
        return SUCCESS;
    }

    /**
     * @return the billingProjectId
     */
    public long getBillingProjectId() {
        return billingProjectId;
    }

    /**
     * @param billingProjectId the billingProjectId to set
     */
    public void setBillingProjectId(long billingProjectId) {
        this.billingProjectId = billingProjectId;
    }

    /**
     * Gets the catalog id.
     *
     * @return the catalog id
     */
    public long getCatalogId() {
        return catalogId;
    }

    /**
     * Sets the catalog id.
     *
     * @param catalogId the catalog id
     */
    public void setCatalogId(long catalogId) {
        this.catalogId = catalogId;
    }

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     * @since 1.1
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param directProjectId the direct project id to set.
     * @since 1.1
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    public void setCostCalculationService(CostCalculationService costCalculationService) {
        this.costCalculationService = costCalculationService;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Get Accessible security groups from group Api
     *
     * @return
     */
    public String getGroups()  {
        try {
            TCSubject tcSubject = DirectUtils.getTCSubjectFromSession();
            Set<Map<String, String>> projectGroups = DirectUtils.getGroups(tcSubject, userGroupsApiEndpoint);
            setResult(projectGroups);
        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }
        return SUCCESS;
    }

    public String getUserGroupsApiEndpoint() {
        return userGroupsApiEndpoint;
    }

    public void setUserGroupsApiEndpoint(String userGroupsApiEndpoint) {
        this.userGroupsApiEndpoint = userGroupsApiEndpoint;
    }
}
