/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;

import java.io.Serializable;

/**
 * <p>A <code>DTO</code> providing the details for a single dependency for a project.</p>
 *
 * @author isv
 * @version 1.0 (Direct Contest Dashboard assembly)
 */
public class DependencyDTO implements Serializable {

    /**
     * <p>A <code>ProjectBriefDTO</code> providing the details for project which current project depends on.</p>
     */
    private ProjectBriefDTO dependencyProject;

    /**
     * <p>A <code>String</code> providing the textual presentation of type of the dependency.</p>
     */
    private String dependencyType;

    /**
     * <p>Constructs new <code>DependencyDTO</code> instance. This implementation does nothing.</p>
     */
    public DependencyDTO() {
    }

    /**
     * <p>Gets the textual presentation of type of the dependency.</p>
     *
     * @return a <code>String</code> providing the textual presentation of type of the dependency.
     */
    public String getDependencyType() {
        return this.dependencyType;
    }

    /**
     * <p>Sets the textual presentation of type of the dependency.</p>
     *
     * @param dependencyType a <code>String</code> providing the textual presentation of type of the dependency.
     */
    public void setDependencyType(String dependencyType) {
        this.dependencyType = dependencyType;
    }

    /**
     * <p>Gets the details for project which current project depends on.</p>
     *
     * @return a <code>ProjectBriefDTO</code> providing the details for project which current project depends on.
     */
    public ProjectBriefDTO getDependencyProject() {
        return this.dependencyProject;
    }

    /**
     * <p>Sets the details for project which current project depends on.</p>
     *
     * @param dependencyProject a <code>ProjectBriefDTO</code> providing the details for project which current project
     *                          depends on.
     */
    public void setDependencyProject(ProjectBriefDTO dependencyProject) {
        this.dependencyProject = dependencyProject;
    }
}
