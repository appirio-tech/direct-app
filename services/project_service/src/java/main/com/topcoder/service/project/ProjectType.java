/*
* Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
*/
package com.topcoder.service.project;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * This entity represents the project type of the direct project.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@XmlType(name = "DirectProjectType", namespace = "com.topcoder.service.project")
@Entity
public class ProjectType implements Serializable {

    /**
     * The id of the project type.
     */
    private long projectTypeId;

    /**
     * The name of the project type.
     */
    private String name;

    /**
     * Gets the id of the project type.
     *
     * @return the id of the project type.
     */
    @Id
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

    /**
     * Gets the name of the project type.
     *
     * @return the name of the project type.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the project type.
     *
     * @param name the name of the project type.
     */
    public void setName(String name) {
        this.name = name;
    }
}
