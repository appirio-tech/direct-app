/*
 * Copyright (C) 2011 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.entities;

import java.util.List;

import javax.persistence.Entity;

/**
 * <p>
 *  This class represents the direct project type concept.
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
public class DirectProjectType extends BaseProjectQuestionAnswerEntity {
    /**
     * <p>
     * serial ID.
     * </p>
     */
    private static final long serialVersionUID = -6093322908293873590L;

    /**
     * Name of the direct project type.
     */
    private String name;
    
    /**
     * Collection of project questions pertaining to this type.
     */
    private List<ProjectQuestion> projectQuestions;

    /**
     * <p>
     * Getter of name field.
     * </p>
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * <p>
     * Setter of name field.
     * </p>
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * Getter of projectQuestions field.
     * </p>
     *
     * @return the projectQuestions
     */
    public List<ProjectQuestion> getProjectQuestions() {
        return projectQuestions;
    }

    /**
     * <p>
     * Setter of projectQuestions field.
     * </p>
     *
     * @param projectQuestions the projectQuestions to set
     */
    public void setProjectQuestions(List<ProjectQuestion> projectQuestions) {
        this.projectQuestions = projectQuestions;
    }
}
