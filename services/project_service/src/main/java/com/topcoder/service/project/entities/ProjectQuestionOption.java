/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.entities;

import javax.persistence.Entity;

/**
 * <p>
 * This class represents the project question option;
 * </p>
 * <p>
 * <strong>Thread Safety: </strong> It's mutable and not thread safe.
 * </p>
 *
 * @author leo_lol
 * @version 1.0
 * @since 1.0
 */
@Entity
public class ProjectQuestionOption extends BaseProjectQuestionAnswerEntity {
    
    /**
     * <p>
     * serial ID.
     * </p>
     */
    private static final long serialVersionUID = -6153777881548246500L;

    /**
     * Text description of this question option.
     */
    private String questionOptionText;
    
    /**
     * Associated projectQuestion.
     */
    private ProjectQuestion projectQuestion;
    
    /**
     * Answer HTML ID.
     */
    private String answerHtmlId;
    
    /**
     * Indicate if this project question option has associated textbox or not.
     */
    private boolean hasAssociatedTextbox;
    
    /**
     * Associate textbox html ID;
     */
    private String associatedTextboxHtmlId;
    
    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ProjectQuestionOption() {
        // empty implementation.
    }

    /**
     * <p>
     * Getter of questionOptionText field.
     * </p>
     *
     * @return the questionOptionText
     */
    public String getQuestionOptionText() {
        return questionOptionText;
    }

    /**
     * <p>
     * Setter of questionOptionText field.
     * </p>
     *
     * @param questionOptionText the questionOptionText to set
     */
    public void setQuestionOptionText(String questionOptionText) {
        this.questionOptionText = questionOptionText;
    }

    /**
     * <p>
     * Getter of projectQuestion field.
     * </p>
     *
     * @return the projectQuestion
     */
    public ProjectQuestion getProjectQuestion() {
        return projectQuestion;
    }

    /**
     * <p>
     * Setter of projectQuestion field.
     * </p>
     *
     * @param projectQuestion the projectQuestion to set
     */
    public void setProjectQuestion(ProjectQuestion projectQuestion) {
        this.projectQuestion = projectQuestion;
    }

    /**
     * <p>
     * Getter of answerHtmlId field.
     * </p>
     *
     * @return the answerHtmlId
     */
    public String getAnswerHtmlId() {
        return answerHtmlId;
    }

    /**
     * <p>
     * Setter of answerHtmlId field.
     * </p>
     *
     * @param answerHtmlId the answerHtmlId to set
     */
    public void setAnswerHtmlId(String answerHtmlId) {
        this.answerHtmlId = answerHtmlId;
    }

    /**
     * <p>
     * Getter of hasAssociatedTextbox field.
     * </p>
     *
     * @return the hasAssociatedTextbox
     */
    public boolean isHasAssociatedTextbox() {
        return hasAssociatedTextbox;
    }

    /**
     * <p>
     * Setter of hasAssociatedTextbox field.
     * </p>
     *
     * @param hasAssociatedTextbox the hasAssociatedTextbox to set
     */
    public void setHasAssociatedTextbox(boolean hasAssociatedTextbox) {
        this.hasAssociatedTextbox = hasAssociatedTextbox;
    }

    /**
     * <p>
     * Getter of associatedTextboxHtmlId field.
     * </p>
     *
     * @return the associatedTextboxHtmlId
     */
    public String getAssociatedTextboxHtmlId() {
        return associatedTextboxHtmlId;
    }

    /**
     * <p>
     * Setter of associatedTextboxHtmlId field.
     * </p>
     *
     * @param associatedTextboxHtmlId the associatedTextboxHtmlId to set
     */
    public void setAssociatedTextboxHtmlId(String associatedTextboxHtmlId) {
        this.associatedTextboxHtmlId = associatedTextboxHtmlId;
    }
}
