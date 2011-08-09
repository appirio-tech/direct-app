/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.project;

import java.io.Serializable;

/**
 * <p>A DTO class providing the brief details on single project.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ProjectBriefDTO implements Serializable {

    /**
     * <p>A <code>long</code> providing the project ID.</p>
     */
    private long id;

    /**
     * <p>A <code>String</code> providing the project name.</p>
     */
    private String name;

    /**
     * <p>Constructs new <code>ProjectBriefDTO</code> instance. This implementation does nothing.</p>
     */
    public ProjectBriefDTO() {
    }

    /**
     * <p>Gets the project ID.</p>
     *
     * @return a <code>long</code> providing the project ID.
     */
    public long getId() {
        return id;
    }

    /**
     * <p>Sets the project ID.</p>
     *
     * @param id a <code>long</code> providing the project ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>Gets the project name.</p>
     *
     * @return a <code>String</code> providing the project name.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Sets the project name.</p>
     *
     * @param name a <code>String</code> providing the project name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
