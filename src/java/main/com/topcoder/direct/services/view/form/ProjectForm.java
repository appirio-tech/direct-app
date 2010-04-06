/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * <p>A form bean providing the data submitted by user for creating new project or updating existing one.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ProjectForm implements Serializable {

    /**
     * <p>A <code>String</code> providing the name of the project.</p>
     */
    private String name;

    /**
     * <p>A <code>String</code> providing the description of the project.</p>
     */
    private String description;

    /**
     * <p>Constructs new <code>ProjectForm</code> instance. This implementation does nothing.</p>
     */
    public ProjectForm() {
    }

    /**
     * <p>Gets the name of the project.</p>
     *
     * @return a <code>String</code> providing the name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets the name of the project.</p>
     *
     * @param name a <code>String</code> providing the name of the project.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Gets the description of the project.</p>
     *
     * @return a <code>String</code> providing the description of the project.
     */
    public String getDescription() {
        return description;
    }

    /**
     * <p>Sets the description of the project.</p>
     *
     * @param description a <code>String</code> providing the description of the project.
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
