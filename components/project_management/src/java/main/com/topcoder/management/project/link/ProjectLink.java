/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.link;

import java.io.Serializable;

import com.topcoder.management.project.Project;

/**
 * <p>
 * Project link entity. It represents a linking between 2 projects.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectLink implements Serializable {
    /**
     * <p>
     * Generated Serialized UID.
     * </p>
     */
    private static final long serialVersionUID = 7098255451121192101L;

    /**
     * <p>
     * The sourceProject field.
     * </p>
     */
    private Project sourceProject;

    /**
     * <p>
     * The destProject field.
     * </p>
     */
    private Project destProject;

    /**
     * <p>
     * The type field.
     * </p>
     */
    private ProjectLinkType type;

    /**
     * <p>
     * Sets the <code>sourceProject</code> field value.
     * </p>
     *
     * @param sourceProject the value to set
     */
    public void setSourceProject(Project sourceProject) {
        this.sourceProject = sourceProject;
    }

    /**
     * <p>
     * Gets the <code>sourceProject</code> field value.
     * </p>
     *
     * @return the <code>sourceProject</code> field value
     */
    public Project getSourceProject() {
        return this.sourceProject;
    }

    /**
     * <p>
     * Sets the <code>destProject</code> field value.
     * </p>
     *
     * @param destProject the value to set
     */
    public void setDestProject(Project destProject) {
        this.destProject = destProject;
    }

    /**
     * <p>
     * Gets the <code>destProject</code> field value.
     * </p>
     *
     * @return the <code>destProject</code> field value
     */
    public Project getDestProject() {
        return this.destProject;
    }

    /**
     * <p>
     * Sets the <code>type</code> field value.
     * </p>
     *
     * @param type the value to set
     */
    public void setType(ProjectLinkType type) {
        this.type = type;
    }

    /**
     * <p>
     * Gets the <code>type</code> field value.
     * </p>
     *
     * @return the <code>type</code> field value
     */
    public ProjectLinkType getType() {
        return this.type;
    }

}
