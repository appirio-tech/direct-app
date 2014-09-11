/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.entities;

import java.util.List;

import javax.persistence.Entity;

import com.topcoder.service.project.Project;

/**
 * <p>
 * This class models the project answer concept.
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
public class ProjectAnswer extends BaseProjectQuestionAnswerEntity {
    
    /**
     * <p>
     * serial ID.
     * </p>
     */
    private static final long serialVersionUID = -7370953481365544419L;

    /**
     * Textual answer.
     */
    private String textualAnswer;

    /**
     * Multiple answers.
     */
    private List<String> multipleAnswers;

    /**
     * Option answers.
     */
    private List<ProjectAnswerOption> optionAnswers;

    /**
     * Associated {@link ProjectQuestion} instance.
     */
    private ProjectQuestion projectQuestion;

    /**
     * The associated TcDirectProject.
     */
    private Project project;

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ProjectAnswer() {
        // empty implementation
    }

    /**
     * <p>
     * Getter of textualAnswer field.
     * </p>
     * 
     * @return the textualAnswer
     */
    public String getTextualAnswer() {
        return textualAnswer;
    }

    /**
     * <p>
     * Setter of textualAnswer field.
     * </p>
     * 
     * @param textualAnswer
     *            the textualAnswer to set
     */
    public void setTextualAnswer(String textualAnswer) {
        this.textualAnswer = textualAnswer;
    }

    /**
     * <p>
     * Getter of multipleAnswers field.
     * </p>
     * 
     * @return the multipleAnswers
     */
    public List<String> getMultipleAnswers() {
        return multipleAnswers;
    }

    /**
     * <p>
     * Setter of multipleAnswers field.
     * </p>
     * 
     * @param multipleAnswers
     *            the multipleAnswers to set
     */
    public void setMultipleAnswers(List<String> multipleAnswers) {
        this.multipleAnswers = multipleAnswers;
    }

    /**
     * <p>
     * Getter of optionAnswers field.
     * </p>
     * 
     * @return the optionAnswers
     */
    public List<ProjectAnswerOption> getOptionAnswers() {
        return optionAnswers;
    }

    /**
     * <p>
     * Setter of optionAnswers field.
     * </p>
     * 
     * @param optionAnswers
     *            the optionAnswers to set
     */
    public void setOptionAnswers(List<ProjectAnswerOption> optionAnswers) {
        this.optionAnswers = optionAnswers;
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
     * Getter of project field.
     * </p>
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * <p>
     * Setter of project field.
     * </p>
     *
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }
}
