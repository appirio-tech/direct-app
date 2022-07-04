/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import java.io.Serializable;

/**
 * <p>
 * This class is a data transfer object representing a project forum template.
 * </p>
 * @author TCSASSEMBLY
 * @version 1.0
 */
public class ProjectForumTemplateDTO implements Serializable {

    /**
     * Represents the serial version uid of this class.
     */
    private static final long serialVersionUID = -8328194047877606835L;

    /**
     * Represents the name of the forum.
     */
    private String forumName;

    /**
     * Represents the description of the forum.
     */
    private String forumDescription;

    /**
     * <p>
     * Creates an instance of this class. It does nothing.
     * </p>
     */
    public ProjectForumTemplateDTO() {
    }

    /**
     * <p>
     * Gets the name of the forum.
     * </p>
     * @return the name of the forum.
     */
    public String getForumName() {
        return forumName;
    }

    /**
     * <p>
     * Sets the name of the forum.
     * </p>
     * @param forumName
     *            the forum name to set.
     */
    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    /**
     * <p>
     * Gets the description of the forum.
     * </p>
     * @return the description of the forum.
     */
    public String getForumDescription() {
        return forumDescription;
    }

    /**
     * <p>
     * Sets the description of the forum.
     * </p>
     * @param forumDescription
     *            the forum description to set.
     */
    public void setForumDescription(String forumDescription) {
        this.forumDescription = forumDescription;
    }
}
