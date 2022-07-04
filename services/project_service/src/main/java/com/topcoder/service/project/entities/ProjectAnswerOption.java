/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.entities;

import javax.persistence.Entity;

/**
 * <p>
 * This class models the project answer option concept.
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
public class ProjectAnswerOption extends BaseProjectQuestionAnswerEntity {

    /**
     * <p>
     * serial ID.
     * </p>
     */
    private static final long serialVersionUID = 8085171573079742077L;

    /**
     * Associated {@link ProjectQuestionOption} instance.
     */
    private ProjectQuestionOption projectQuestionOption;

    /**
     * Associated {@link ProjectAnswer} instance;
     */
    private ProjectAnswer projectAnswer;

    /**
     * Answer value in HTML format.
     */
    private String answerHtmlValue;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ProjectAnswerOption() {
        // empty implementation.
    }

    /**
     * <p>
     * Getter of projectQuestionOption field.
     * </p>
     * 
     * @return the projectQuestionOption
     */
    public ProjectQuestionOption getProjectQuestionOption() {
        return projectQuestionOption;
    }

    /**
     * <p>
     * Setter of projectQuestionOption field.
     * </p>
     * 
     * @param projectQuestionOption
     *            the projectQuestionOption to set
     */
    public void setProjectQuestionOption(ProjectQuestionOption projectQuestionOption) {
        this.projectQuestionOption = projectQuestionOption;
    }

    /**
     * <p>
     * Getter of projectAnswer field.
     * </p>
     * 
     * @return the projectAnswer
     */
    public ProjectAnswer getProjectAnswer() {
        return projectAnswer;
    }

    /**
     * <p>
     * Setter of projectAnswer field.
     * </p>
     * 
     * @param projectAnswer
     *            the projectAnswer to set
     */
    public void setProjectAnswer(ProjectAnswer projectAnswer) {
        this.projectAnswer = projectAnswer;
    }

    /**
     * <p>
     * Getter of answerHtmlValue field.
     * </p>
     * 
     * @return the answerHtmlValue
     */
    public String getAnswerHtmlValue() {
        return answerHtmlValue;
    }

    /**
     * <p>
     * Setter of answerHtmlValue field.
     * </p>
     * 
     * @param answerHtmlValue
     *            the answerHtmlValue to set
     */
    public void setAnswerHtmlValue(String answerHtmlValue) {
        this.answerHtmlValue = answerHtmlValue;
    }
}
