/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.permission;

import com.topcoder.service.project.entities.BaseAudit;

/**
 * Represents the entity class for permission audit.
 *
 * @author TCSASSEMBER
 * @version 1.0
 *
 * @since Release Assembly - TopCoder Direct Project Audit v1.0
 */
public class PermissionAudit extends BaseAudit {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = 94365023486459234L;

    /**
     * Represents the user id of the permission.
     */
    private Long userId;

    /**
     * Represents the resource id of the permission. 
     */
    private Long resourceId;

    /**
     * Empty constructor.
     */
    public PermissionAudit() {
    }

    /**
     * Gets the user id of the permission.
     * 
     * @return the user id of the permission.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user id of the permission.
     * 
     * @param userId the user id of the permission.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the resource id of the permission. 
     * 
     * @return the resource id of the permission. 
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * Sets the resource id of the permission. 
     * 
     * @param resourceId the resource id of the permission. 
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
}
