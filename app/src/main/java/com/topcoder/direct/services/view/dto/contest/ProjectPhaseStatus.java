/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>A <code>Enum</code> providing all project phase status.</p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since TC Direct Contest Dashboard Update Assembly
 */
public enum ProjectPhaseStatus {
    /**
     * The scheduled status, with no html class.
     */
    SCHEDULED(1, ""),
    
    /**
     * The open status, with current html class.
     */
    OPEN(2, "current"),
    
    /**
     * The closed status, with completed html class.
     */
    CLOSED(3, "completed");
    
    /**
     * The phase status id.
     */
    private int phaseStatusId;
    
    /**
     * The html class.
     */
    private String htmlClass;
    
    /**
     * The constructor.
     * 
     * @param id the id
     * @param clazz the html class
     */
    private ProjectPhaseStatus(int id, String clazz) {
        phaseStatusId = id;
        htmlClass = clazz;
    }
    
    /**
     * Find project phase status with specified id.
     * 
     * @param id the id
     * @return the phase status
     */
    public static ProjectPhaseStatus findProjectPhaseStatus(int id) {
        for (ProjectPhaseStatus status : ProjectPhaseStatus.values()) {
            if (status.phaseStatusId == id) {
                return status;
            }
        }
        return SCHEDULED;
    }

    /**
     * Gets the phaseStatusId field.
     * 
     * @return the phaseStatusId
     */
    public int getPhaseStatusId() {
        return phaseStatusId;
    }

    /**
     * Gets the htmlClass field.
     * 
     * @return the htmlClass
     */
    public String getHtmlClass() {
        return htmlClass;
    }
}
