/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <p>
 * The software competition change history mapped to the software_competition_change table.
 * </p>
 *
 * @author TCSASSEMBLER
 * @since Pipeline Conversion Service Layer Assembly v1.0
 * @version 1.0
 *
 */
@Entity
@Table(name = "software_competition_change_history")
@AttributeOverride(name = "pipelineInfoId", column = @Column(name = "software_competition_pipeline_info_id"))
public class SoftwareCompetitionChangeHistory extends CompetitionChangeHistory {
    
    /**
	 * serial version UID.
	 */
	private static final long serialVersionUID = 1409083363183553353L;
	
	/**
	 * The pipeline info that the change history belongs to.
	 */
	@ManyToOne(targetEntity=SoftwareCompetitionPipelineInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "software_competition_pipeline_info_id", insertable = false, updatable = false)
    private SoftwareCompetitionPipelineInfo pipelineInfo;

    /**
     * <p>
     * Gets the pipelineInfo
     * </p>
     *
     * @return the pipelineInfo
     */
    public SoftwareCompetitionPipelineInfo getPipelineInfo() {
        return pipelineInfo;
    }

    /**
     * <p>
     * Sets the pipelineInfo
     * </p>
     *
     * @param pipelineInfo the pipelineInfo to set
     */
    public void setPipelineInfo(SoftwareCompetitionPipelineInfo pipelineInfo) {
        this.pipelineInfo = pipelineInfo;
    }
}
