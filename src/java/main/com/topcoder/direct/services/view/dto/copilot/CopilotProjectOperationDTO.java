/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.copilot;

import java.io.Serializable;

/**
 * <p>
 * A DTO used to store information of one copilot project operation.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since TC Direct Manage Copilots Assembly
 */
public class CopilotProjectOperationDTO implements Serializable {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -8468756999266280094L;

    /**
     * The project id.
     */
    private long projectId;

    /**
     * The copilot profile id.
     */
    private long copilotProfileId;

    /**
     * The copilot project id.
     */
    private long copilotProjectId;

    /**
     * The type of operation.
     */
    private CopilotProjectOperationType operation;
    
    /**
     * The copilot type.
     */
    private String copilotType;

    /**
     * Retrieves the projectId field.
     * 
     * @return the projectId
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the projectId field.
     * 
     * @param projectId
     *            the projectId to set
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Retrieves the copilotProfileId field.
     * 
     * @return the copilotProfileId
     */
    public long getCopilotProfileId() {
        return copilotProfileId;
    }

    /**
     * Sets the copilotProfileId field.
     * 
     * @param copilotProfileId
     *            the copilotProfileId to set
     */
    public void setCopilotProfileId(long copilotProfileId) {
        this.copilotProfileId = copilotProfileId;
    }

    /**
     * Retrieves the copilotProjectId field.
     * 
     * @return the copilotProjectId
     */
    public long getCopilotProjectId() {
        return copilotProjectId;
    }

    /**
     * Sets the copilotProjectId field.
     * 
     * @param copilotProjectId
     *            the copilotProjectId to set
     */
    public void setCopilotProjectId(long copilotProjectId) {
        this.copilotProjectId = copilotProjectId;
    }

    /**
     * Retrieves the operation field.
     * 
     * @return the operation
     */
    public CopilotProjectOperationType getOperation() {
        return operation;
    }

    /**
     * Sets the operation field.
     * 
     * @param operation
     *            the operation to set
     */
    public void setOperation(CopilotProjectOperationType operation) {
        this.operation = operation;
    }

    /**
     * Retrieves the copilotType field.
     *
     * @return the copilotType
     */
    public String getCopilotType() {
        return copilotType;
    }

    /**
     * Sets the copilotType field.
     *
     * @param copilotType the copilotType to set
     */
    public void setCopilotType(String copilotType) {
        this.copilotType = copilotType;
    }

}
