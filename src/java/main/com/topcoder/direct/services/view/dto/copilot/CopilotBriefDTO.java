/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.copilot;

import java.io.Serializable;

/**
 * <p>
 * A DTO used to store information about a copilot.
 * </p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since TC Direct Manage Copilots Assembly
 */
public class CopilotBriefDTO implements Serializable {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 7952762140172887164L;

    /**
     * The copilot profile id.
     */
    private long copilotProfileId;

    /**
     * The copilot project id.
     */
    private long copilotProjectId;

    /**
     * The handle of copilot.
     */
    private String handle;
    
    /**
     * The copilot type.
     */
    private String copilotType;

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
     * Retrieves the handle field.
     * 
     * @return the handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the handle field.
     * 
     * @param handle
     *            the handle to set
     */
    public void setHandle(String handle) {
        this.handle = handle;
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
