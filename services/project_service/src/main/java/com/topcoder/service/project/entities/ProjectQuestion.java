/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.entities;

import java.util.List;

import javax.persistence.Entity;

/**
 * <p>
 * This class represents the project question concept.
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
public class ProjectQuestion extends BaseProjectQuestionAnswerEntity {
    
    /**
     * <p>
     * serial ID.
     * </p>
     */
    private static final long serialVersionUID = 541708978501126125L;

    /**
     * Question text.
     */
    private String questionText;
    
    /**
     * Question options pertaining to this question.
     */
    private List<ProjectQuestionOption> questionOptions;
    
    /**
     * Answer HTML ID.
     */
    private String answerHtmlId;
    
    /**
     * ID of the directProjectType.
     */
    private DirectProjectType directProjectType;
    
    /**
     * HTML Xpath of multiple answers. 
     */
    private String multipleAnswersHtmlXpath;
    
    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ProjectQuestion() {
        // Empty implementation.
    }

    /**
     * <p>
     * Getter of questionText field.
     * </p>
     *
     * @return the questionText
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * <p>
     * Setter of questionText field.
     * </p>
     *
     * @param questionText the questionText to set
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * <p>
     * Getter of questionOptions field.
     * </p>
     *
     * @return the questionOptions
     */
    public List<ProjectQuestionOption> getQuestionOptions() {
        return questionOptions;
    }

    /**
     * <p>
     * Setter of questionOptions field.
     * </p>
     *
     * @param questionOptions the questionOptions to set
     */
    public void setQuestionOptions(List<ProjectQuestionOption> questionOptions) {
        this.questionOptions = questionOptions;
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
     * Getter of directProjectType field.
     * </p>
     *
     * @return the directProjectType
     */
    public DirectProjectType getDirectProjectType() {
        return directProjectType;
    }

    /**
     * <p>
     * Setter of directProjectType field.
     * </p>
     *
     * @param directProjectType the directProjectType to set
     */
    public void setDirectProjectType(DirectProjectType directProjectType) {
        this.directProjectType = directProjectType;
    }

    /**
     * <p>
     * Getter of multipleAnswersHtmlXpath field.
     * </p>
     *
     * @return the multipleAnswersHtmlXpath
     */
    public String getMultipleAnswersHtmlXpath() {
        return multipleAnswersHtmlXpath;
    }

    /**
     * <p>
     * Setter of multipleAnswersHtmlXpath field.
     * </p>
     *
     * @param multipleAnswersHtmlXpath the multipleAnswersHtmlXpath to set
     */
    public void setMultipleAnswersHtmlXpath(String multipleAnswersHtmlXpath) {
        this.multipleAnswersHtmlXpath = multipleAnswersHtmlXpath;
    }
}
