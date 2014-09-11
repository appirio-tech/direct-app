/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * <p>
 * The class mapped to the 'software_competition_pipeline_info' table.
 * </p>
 *
 * @since Pipeline Conversion Service Layer Assembly v1.0
 */
@Entity
@Table(name = "software_competition_pipeline_info")
public class SoftwareCompetitionPipelineInfo extends CompetitionPipelineInfo {
    /** Default serial version id. */
    private static final long serialVersionUID = 1L;

    /** The component id. */
    @Column(name = "component_id")
    private Long componentId;

    /**
     * Creates a new SoftwareCompetitionPipelineInfo object.
     */
    public SoftwareCompetitionPipelineInfo() {
    }

    /**
     * <p>
     * Gets the componentId
     * </p>
     *
     * @return the componentId
     */
    public Long getComponentId() {
        return componentId;
    }

    /**
     * <p>
     * Sets the componentId
     * </p>
     *
     * @param componentId the componentId to set
     */
    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }
}
