/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * <p>A form bean providing the ID for the project requested for some processing.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ProjectIdForm implements Serializable {

    /**
     * <p>A <code>long</code> providing the ID of a requested project.</p>
     */
    private long projectId;

    /**
     * <p>Constructs new <code>ProjectIdForm</code> instance. This implementation does nothing.</p>
     */
    public ProjectIdForm() {
    }

    /**
     * <p>Gets the ID for the requested project.</p>
     *
     * @return a <code>long</code> providing the ID of a requested project.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID for the requested project.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a requested project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
