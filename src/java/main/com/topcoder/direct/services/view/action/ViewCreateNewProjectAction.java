/*
 * Copyright (C) 2012 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.clients.model.Project;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.util.*;
import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONObject;
import com.topcoder.security.groups.model.BillingAccount;
import com.topcoder.security.groups.model.Group;
import com.topcoder.security.groups.services.GroupService;
import com.topcoder.security.groups.services.dto.GroupSearchCriteria;
import com.topcoder.service.facade.project.ProjectServiceFacade;
import com.topcoder.service.project.entities.ProjectQuestion;
import com.topcoder.service.project.entities.ProjectQuestionOption;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * <p>
 * This action get project questions from database and send it to front end as a
 * JSON string.
 * </p>
 * <p>
 * Version 1.0 (Release Assembly - TopCoder Cockpit Start New Project Data
 * Persistence)
 * </p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TC Cockpit Start Project Flow Billing Account Integration)
 * <ol>
 *     <li>Add billing accounts for the user to choose for the new project</li>
 * </ol>
 * </p>
 * 
 * @author Ghost_141, GreatKevin
 * @version 1.1
 * @since 1.0
 */
public class ViewCreateNewProjectAction extends AbstractAction {

    /**
     * <p>
     * Generated serial id.
     * </p>
     */
    private static final long serialVersionUID = 2868029982576139945L;

    /**
     * <p>
     * Represent the name of the action.
     * </p>
     */
    private static final String CLASS_NAME = ViewCreateNewProjectAction.class.getName();

    /**
     * <p>
     * Represent the logger for this class.
     * </p>
     */
    private Logger logger = Logger.getLogger(ViewCreateNewProjectAction.class);

    /**
     * <p>
     * Represent the project questions.
     * </p>
     */
    private List<ProjectQuestion> projectQuestions;

    /**
     * <p>
     * Represent the project service facade.
     * </p>
     */
    private ProjectServiceFacade projectServiceFacade;

    /**
     * Represents the group service.
     *
     * @since 1.1
     */
    private GroupService groupService;

    /**
     * Stores the available billing accounts the user has access to.
     *
     * @since 1.1
     */
    private List<IdNamePair> availableBillingAccounts;

    /**
     * <p>
     * Create an instance of <code>ViewCreateNewProjectAction</code>.
     * </p>
     */
    public ViewCreateNewProjectAction() {
        // Empty
    }

    /**
     * <p>
     * Execute the action and retrieve data from database.
     * </p>
     * 
     * @return the executed result.
     * @throws Exception
     *             if any error happens.
     */
    public String execute() throws Exception {
        try {
            if ("true".equalsIgnoreCase(DirectProperties.REDIRECT_CREATE_DIRECT_PROJECT.trim())) {
                return "forward";
            }

            String result = super.execute();

            if (SUCCESS.equals(result)) {
                projectQuestions = projectServiceFacade.getProjectQuestions();
                ValidationUtility.checkNotNullNorEmpty(projectQuestions, "projectQuestions",
                        IllegalArgumentException.class);
                JSONArray array = new JSONArray();
                for (ProjectQuestion projectQuestion : projectQuestions) {
                    JSONObject obj = new JSONObject();
                    obj.setLong("id", projectQuestion.getId());
                    obj.setString("questionText", projectQuestion.getQuestionText());
                    obj.setString("answerHtmlId", projectQuestion.getAnswerHtmlId());
                    obj.setLong("directProjectTypeId", projectQuestion.getDirectProjectType().getId());
                    obj.setString("multipleAnswersHtmlXpath", projectQuestion.getMultipleAnswersHtmlXpath());
                    if (!projectQuestion.getQuestionOptions().isEmpty()) {
                        JSONArray optionArray = new JSONArray();
                        for (ProjectQuestionOption option : projectQuestion.getQuestionOptions()) {
                            JSONObject optionObj = new JSONObject();
                            optionObj.setLong("id", option.getId());
                            optionObj.setString("questionOptionText", option.getQuestionOptionText());
                            optionObj.setLong("projectQuestionId", option.getProjectQuestion().getId());
                            optionObj.setString("answerHtmlId", option.getAnswerHtmlId());
                            optionObj.setBoolean("hasAssociatedTextbox", option.isHasAssociatedTextbox());
                            if (option.isHasAssociatedTextbox()) {
                                optionObj.setString("associatedTextboxHtmlId", option.getAssociatedTextboxHtmlId());
                            }
                            optionArray.addJSONObject(optionObj);
                        }
                        obj.setArray("questionOptions", optionArray);
                    }
                    array.addJSONObject(obj);
                }
                Map<String, String> resultMap = new HashMap<String, String>();
                resultMap.put("projectQuestions", array.toJSONString());

                setResult(resultMap);
                return SUCCESS;
            } else {
                return result;
            }
        } catch (Exception e) {
            logger.error("Error when executing action : " + CLASS_NAME + " : " + e.getMessage(), e);
            throw new Exception(e);
        }
    }

    /**
     * <p>
     * Get the projectQuestions.
     * </p>
     * 
     * @return projectQuestions the projectQuestions.
     */
    public List<ProjectQuestion> getProjectQuestions() {
        return projectQuestions;
    }

    /**
     * <p>
     * Set the projectQuestions.
     * </p>
     * 
     * @param projectQuestions
     *            the projectQuestions to set.
     */
    public void setProjectQuestions(List<ProjectQuestion> projectQuestions) {
        this.projectQuestions = projectQuestions;
    }

    /**
     * <p>
     * Get the projectServiceFacade.
     * </p>
     * 
     * @return projectServiceFacade the projectServiceFacade.
     */
    public ProjectServiceFacade getProjectServiceFacade() {
        return projectServiceFacade;
    }

    /**
     * Gets the group service.
     *
     * @return the group service.
     *
     * @since 1.1
     */
    public GroupService getGroupService() {
        return groupService;
    }

    /**
     * Sets the group service.
     *
     * @param groupService the group service.
     *
     * @since 1.1
     */
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * <p>
     * Set the projectServiceFacade.
     * </p>
     * 
     * @param projectServiceFacade
     *            the projectServiceFacade to set.
     */
    public void setProjectServiceFacade(ProjectServiceFacade projectServiceFacade) {
        this.projectServiceFacade = projectServiceFacade;
    }

    /**
     * Gets the available billing accounts the user has permission to use.
     *
     * @return the billing accounts the user has permission with
     * @throws Exception if any error.
     * @since 1.1
     */
    public List<IdNamePair> getAvailableBillingAccounts() throws Exception {
        if (availableBillingAccounts == null) {
            List<Project> billingAccountsOfUser = getProjectServiceFacade().getClientProjectsByUser(DirectStrutsActionsHelper
                                                                                                            .getTCSubjectFromSession());
            Map<Long, IdNamePair> result = new HashMap<Long, IdNamePair>();

            for (Project bp : billingAccountsOfUser) {
                IdNamePair billing = new IdNamePair();
                billing.setId(bp.getId());
                billing.setName(bp.getName());
                result.put(bp.getId(), billing);
            }

            GroupSearchCriteria groupSearchCriteria = new GroupSearchCriteria();
            groupSearchCriteria.setUserId(DirectUtils.getTCSubjectFromSession().getUserId());

            List<Group> userGroups = getGroupService().search(groupSearchCriteria, 0, 0).getValues();

            // add billing accounts in the security groups the user has access to
            for (Group securityGroup : userGroups) {
                final List<BillingAccount> securityGroupBillingAccounts
                        = securityGroup.getBillingAccounts();
                for (BillingAccount ba : securityGroupBillingAccounts) {
                    if (!result.containsKey(ba.getId())) {
                        IdNamePair billing = new IdNamePair();
                        billing.setId(ba.getId());
                        billing.setName(ba.getName());
                        result.put(ba.getId(), billing);
                    }
                }
            }

            availableBillingAccounts =  new ArrayList<IdNamePair>(result.values());
            Collections.sort(availableBillingAccounts, new DirectUtils.IdNamePairNameCaseInsensitiveComparator());
        }

        return availableBillingAccounts;
    }
}
