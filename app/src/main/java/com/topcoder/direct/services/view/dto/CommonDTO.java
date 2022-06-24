/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

/**
 * <p>A base class for all DTOs which require details on user projects.</p>
 *
 * @author isv
 * @version 1.0
 */
public class CommonDTO implements UserProjectsDTO.Aware {

    /**
     * <p>A <code>UserProjectsDTO</code> providing details on projects associated with current user.</p>
     */
    private UserProjectsDTO userProjects;

    /**
     * Constructs new <code>CommonDTO</code> instance. This implementation does nothing.
     */
    public CommonDTO() {
    }

    /**
     * <p>Gets the details on current user projects.</p>
     *
     * @return a <code>UserProjectsDTO</code> providing details on projects associated with current user.
     */
    public UserProjectsDTO getUserProjects() {
        return userProjects;
    }

    /**
     * <p>Sets the details on current user projects.</p>
     *
     * @param userProjects a <code>UserProjectsDTO</code> providing details on projects associated with current user.
     */
    public void setUserProjects(UserProjectsDTO userProjects) {
        this.userProjects = userProjects;
    }
}
