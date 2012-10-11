/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.service.project.entities.ProjectQuestion;
import com.topcoder.service.project.entities.ProjectQuestionOption;
import com.topcoder.json.object.JSONArray;
import com.topcoder.json.object.JSONObject;
import com.topcoder.service.facade.project.ProjectServiceFacade;

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
 * @author Ghost_141
 * @version 1.0
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

}
