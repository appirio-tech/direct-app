/*
 * Copyright (C) 2016 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

import java.io.Serializable;
import java.util.List;
/**
 * <p>A <code>DTO</code> providing the details for a member that failed to pre-register.</p>
 *
 * @author TCSCODER
 * @version 1.0 (TOPCODER DIRECT - IMPROVEMENT FOR PRE-REGISTER MEMBERS WHEN LAUNCHING CHALLENGES)
 */
public class FailedRegisterUser<C> implements Serializable{
    /**
     * User handle
     */
    private String handle;

    /**
     * Fail description
     */
    private String reason;

    /**
     * list of other properties
     */
    private List<C> properties;

    /**
     * Getter for {@link #handle}
     *
     * @return
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Setter for {@link #handle}
     * @param handle
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Getter for {@link #reason}
     *
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Setter for {@link #reason}
     *
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Getter for {@link #properties}
     *
     * @return properties
     */
    public List<C> getProperties() {
        return properties;
    }

    /**
     * Setter for {@link #properties}
     *
     * @param properties
     */
    public void setProperties(List<C> properties) {
        this.properties = properties;
    }
}
