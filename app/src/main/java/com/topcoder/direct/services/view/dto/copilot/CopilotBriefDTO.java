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
 * <p>
 *     Version 1.1 (Release Assembly - TopCoder Cockpit Project Overview Update 1) change note:
 *     - Add the property userId to store the TopCoder user id of the copilot
 * </p>
 * 
 * @author GreatKevin
 * @version 1.1
 * @since TC Direct Manage Copilots Assembly
 */
public class CopilotBriefDTO implements Serializable, Comparable<CopilotBriefDTO> {
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
     * The TopCoder user id of the copilot.
     *
     * @since 1.1
     */
    private long userId;

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

    /**
     * The compare to method.
     * 
     * @param o the copilot brief dto to compare
     */
    public int compareTo(CopilotBriefDTO o) {
        return handle.compareTo(o.handle);
    }

    /**
     * Gets the user id of the copilot.
     *
     * @return the user id of the copilot.
     * @since 1.1
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user id of the copilot.
     *
     * @param userId the user id to set.
     * @since 1.1
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
