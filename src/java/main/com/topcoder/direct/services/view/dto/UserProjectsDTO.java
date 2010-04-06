/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto;

import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;

import java.io.Serializable;
import java.util.List;

/**
 * <p>A DTO providing the details on projects associated with current user.</p>
 *
 * @author isv
 * @version 1.0
 */
public class UserProjectsDTO implements Serializable {

    /**
     * <p>An interface to be implemented by the parties interested in getting the details on projects associated with
     * current user.</p>
     */
    public static interface Aware {

        /**
         * <p>Sets the details on user projects.</p>
         *
         * @param userProjects a <code>UserProjectsDTO</code> providing details on user projects.
         */
        public void setUserProjects(UserProjectsDTO userProjects);
    }

    /**
     * <p>A <code>List</code> of projects associated with the user.</p>
     */
    private List<ProjectBriefDTO> projects;

    /**
     * <p>Constructs new <code>UserProjectsDTO</code> instance. This implementation does nothing.</p>
     */
    public UserProjectsDTO() {
    }

    /**
     * <p>Gets the list of user projects.</p>
     *
     * @return a <code>List</code> of projects associated with the user.
     */
    public List<ProjectBriefDTO> getProjects() {
        return projects;
    }

    /**
     * <p>Sets the list of user projects.</p>
     *
     * @param projects a <code>List</code> of projects associated with the user.
     */
    public void setProjects(List<ProjectBriefDTO> projects) {
        this.projects = projects;
    }
}
