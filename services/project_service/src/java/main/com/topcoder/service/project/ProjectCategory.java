/*
* Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
*/
package com.topcoder.service.project;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * This entity represents the project category of direct project.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlType(name = "DirectProjectCategory", namespace = "com.topcoder.service.project")
@Entity
public class ProjectCategory implements Serializable {

    /**
     * The id of the project category.
     */
    private long projectCategoryId;

    /**
     * The name of the project category.
     */
    private String name;

    /**
     * The id of the project type the project category belongs to.
     */
    private long projectTypeId;

    /**
     * Gets the project category id.
     *
     * @return the project category id.
     */
    @Id
    public long getProjectCategoryId() {
        return projectCategoryId;
    }

    /**
     * Sets the project category id.
     *
     * @param projectCategoryId the project category id.
     */
    public void setProjectCategoryId(long projectCategoryId) {
        this.projectCategoryId = projectCategoryId;
    }

    /**
     * Gets the name of the project category.
     *
     * @return the name of the project category.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the project category.
     *
     * @param name the name of the project category.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the id of the project type.
     *
     * @return the id of the project type.
     */
    public long getProjectTypeId() {
        return projectTypeId;
    }

    /**
     * Sets the id of the project type.
     *
     * @param projectTypeId the id of the project type.
     */
    public void setProjectTypeId(long projectTypeId) {
        this.projectTypeId = projectTypeId;
    }
}
