/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.permission;

import java.io.Serializable;

/**
 * Represents the entity class for permission.
 * 
 * <p>
 * Updated for Cockpit Resource Admin Release Assembly 1 v1.0 - changed the variable names from having 'project' to
 * 'resource' - added 'studio' boolean attribute to determine if the permission is for studio or for s/w projects etc.
 * </p>
 * 
 * @author PE
 * @version 1.0
 * 
 * @since Module Cockpit Contest Service Enhancement Assembly
 */
@SuppressWarnings("serial")
public class Permission implements Serializable {
    /** Represents the entity id. */
    private Long permissionId;

    /** Represents the user id. */
    private Long userId;

    /**
     * Represents the user handle corresponding to user id.
     * 
     * @since Cockpit Share Submission Integration Assembly v1.0
     */
    private String userHandle;

    /**
     * Represents the resource id.
     * 
     * Updated for Cockpit Resource Admin Release Assembly v1.0 - Changed the name from projectId to resourceId.
     */
    private Long resourceId;

    /**
     * Represents the name of the resource corresponding to given resource id
     * 
     * Updated for Cockpit Resource Admin Release Assembly v1.0 - Changed the name from projectName to resourceName.
     * 
     * @since Cockpit Share Submission Integration Assembly v1.0
     */
    private String resourceName;

    /** Represents the permission type. */
    private PermissionType permissionType;

    /**
     * Represents whether the permission is for studio contests/projects or for software projects.
     * 
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    private boolean studio;

    /**
     * Default constructor.
     */
    public Permission() {
        // empty
    }

    /**
     * Gets the permissionId.
     * 
     * @return the permissionId.
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * Sets the permissionId.
     * 
     * @param permissionId
     *            the permissionId to set.
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * Gets the userId.
     * 
     * @return the userId.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the userId.
     * 
     * @param userId
     *            the userId to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the resourceId.
     * 
     * Updated for Cockpit Project Admin Release Assembly v1.0 - changed the name from having 'project' to 'resource'
     * 
     * @return the resourceId.
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * Sets the resourceId.
     * 
     * Updated for Cockpit Project Admin Release Assembly v1.0 - changed the name from having 'project' to 'resource'
     * 
     * @param resourceId
     *            the resourceId to set.
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * Gets the permissionType.
     * 
     * @return the permissionType.
     */
    public PermissionType getPermissionType() {
        return permissionType;
    }

    /**
     * Sets the permissionType.
     * 
     * @param permissionType
     *            the permissionType to set.
     */
    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * Gets the handle of the user for this permission.
     * 
     * @return the userHandle
     * 
     * @since Cockpit Share Submission Integration v1.0
     */
    public String getUserHandle() {
        return this.userHandle;
    }

    /**
     * Sets the handle of the user for this permission.
     * 
     * @param userHandle
     *            the userHandle to set
     * 
     * @since Cockpit Share Submission Integration v1.0
     */
    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }

    /**
     * Gets the resource name for this permission.
     * 
     * Updated for Cockpit Project Admin Release Assembly v1.0 - changed the name from having 'project' to 'resource'
     * 
     * @return the resourceName
     * 
     * @since Cockpit Share Submission Integration v1.0
     */
    public String getResourceName() {
        return this.resourceName;
    }

    /**
     * Sets the resource name for this permission.
     * 
     * Updated for Cockpit Project Admin Release Assembly v1.0 - changed the name from having 'project' to 'resource'
     * 
     * @param resourceName
     *            the resourceName to set
     * 
     * @since Cockpit Share Submission Integration v1.0
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * Compares this object with the passed object for equality. Only the id will be compared.
     * 
     * @param obj
     *            the {@code Object} to compare to this one
     * 
     * @return true if this object is equal to the other, {@code false} if not
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Permission) {
            if (getPermissionId() == null) {
                return (((Permission) obj).getPermissionId() == null);
            }

            return getPermissionId().equals(((Permission) obj).getPermissionId());
        }

        return false;
    }

    /**
     * Overrides {@code Object.hashCode()} to provide a hash code consistent with this class's {@link #equals(Object)}
     * method.
     * 
     * @return a hash code for this {@code Permission}
     */
    @Override
    public int hashCode() {
        return Helper.calculateHash(permissionId);
    }

    /**
     * Gets if this permission is for studio project/contests.
     * 
     * @return true if this permission is for studio project/contests otherwise false.
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public boolean isStudio() {
        return studio;
    }

    /**
     * Sets if this permission is for studio project/contests.
     * 
     * @param studio
     *            true if this permission is for studio project/contests otherwise false.
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }
}
