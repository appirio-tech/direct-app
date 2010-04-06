/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;

import java.io.Serializable;

/**
 * <p>A DTO providing the brief details for a single contest. </p>
 *
 * @author isv
 * @version 1.0
 */
public class ContestBriefDTO implements Serializable {

    /**
     * <p>A <code>long</code> providing the ID of this contest.</p>
     */
    private long id;

    /**
     * <p>A <code>String</code> providing the title of this contest.</p>
     */
    private String title;

    /**
     * <p>A <code>ProjectBriefDTO</code> providing details on project which this contest belongs to.</p>
     */
    private ProjectBriefDTO project;

    /**
     * <p>Constructs new <code>ContestBriefDTO</code> instance. This implementation does nothing.</p>
     */
    public ContestBriefDTO() {
    }

    /**
     * <p>Gets the ID of this contest.</p>
     *
     * @return a <code>long</code> providing the ID of this contest.
     */
    public long getId() {
        return id;
    }

    /**
     * <p>Sets the ID of this contest.</p>
     *
     * @param id a <code>long</code> providing the ID of this contest.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * <p>Gets the title of this contest.</p>
     *
     * @return a <code>String</code> providing the title of this contest.
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p>Sets the title of this contest.</p>
     *
     * @param title a <code>String</code> providing the title of this contest.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * <p>Gets the details on project this contest belongs to.</p>
     *
     * @return a <code>ProjectBriefDTO</code> providing details on project which this contest belongs to.</p>
     */
    public ProjectBriefDTO getProject() {
        return project;
    }

    /**
     * <p>Sets the details on project this contest belongs to.</p>
     *
     * @param project a <code>ProjectBriefDTO</code> providing details on project which this contest belongs to.</p>
     */
    public void setProject(ProjectBriefDTO project) {
        this.project = project;
    }
}
