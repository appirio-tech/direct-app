/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.permission;

import java.io.Serializable;

/**
 * <p>A simple <code>DTO</code> class providing the details for a single permission granted to a single user for
 * accessing a single project.</p>
 *
 * <p>
 * Version 1.0.1 (TC Direct - Permission Updates) Change notes:
 *   <ol>
 *     <li>Added userPermissionId field and corresponding get/set methods.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER, TCSASSEMBLER
 * @version 1.0.1
 */
public class ProjectPermission implements Serializable {

    /**
     * <p>A <code>long</code> providing the serial version ID for serialization of this class.</p>
     */
    private static final long serialVersionUID = 2L;

    /**
     * <p>A <code>long</code> providing the ID of a user which this permission is granted to.</p>
     */
    private long userId;

    /**
     * <p>A <code>long</code> providing the ID of a project which this permission grants access to.</p>
     */
    private long projectId;

    /**
     * <p>A <code>String</code> providing the name of the project which this permission grants access to.</p>
     */
    private String projectName;

    /**
     * <p>A <code>String</code> providing the handle for the user which this permission is granted to.</p>
     */
    private String handle;

    /**
     * <p>A <code>String</code> providing the permission which is granted to user for accessing the project.</p>
     */
    private String permission;

    /**
     * <p>A <code>boolean</code> providing the flag indicating whether this permission corresponds to Studio project or
     * not.</p>
     */
    private boolean studio;
    
    /**
     * <p>
     * A <code>long</code> providing the ID of a user permission which this
     * permission grants access to.
     * </p>
     * 
     * @since 1.0.1
     */
    private long userPermissionId;

    /**
     * <p>Constructs new <code>ProjectPermission</code> instance. This implementation does nothing.</p>
     */
    public ProjectPermission() {
    }

    /**
     * <p>Gets the permission which is granted to user for accessing the project.</p>
     *
     * @return a <code>String</code> providing the permission which is granted to user for accessing the project.
     */
    public String getPermission() {
        return this.permission;
    }

    /**
     * <p>Sets the permission which is granted to user for accessing the project.</p>
     *
     * @param permission a <code>String</code> providing the permission which is granted to user for accessing the
     *                   project.
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * <p>Gets the handle for the user which this permission is granted to.</p>
     *
     * @return a <code>String</code> providing the handle for the user which this permission is granted to.
     */
    public String getHandle() {
        return this.handle;
    }

    /**
     * <p>Sets the handle for the user which this permission is granted to.</p>
     *
     * @param handle a <code>String</code> providing the handle for the user which this permission is granted to.
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * <p>Gets the name of the project which this permission grants access to.</p>
     *
     * @return a <code>String</code> providing the name of the project which this permission grants access to.
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * <p>Sets the name of the project which this permission grants access to.</p>
     *
     * @param projectName a <code>String</code> providing the name of the project which this permission grants access
     *                    to.
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * <p>Gets the ID of a project which this permission grants access to.</p>
     *
     * @return a <code>long</code> providing the ID of a project which this permission grants access to.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID of a project which this permission grants access to.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a project which this permission grants access to.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * <p>Gets the ID of a user which this permission is granted to.</p>
     *
     * @return a <code>long</code> providing the ID of a user which this permission is granted to.
     */
    public long getUserId() {
        return this.userId;
    }

    /**
     * <p>Sets the ID of a user which this permission is granted to.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user which this permission is granted to.
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * <p>Gets the flag indicating whether this permission corresponds to Studio project or not.</p>
     *
     * @return a <code>boolean</code> providing the flag indicating whether this permission corresponds to Studio
     *         project or not.
     */
    public boolean getStudio() {
        return this.studio;
    }

    /**
     * <p>Sets the flag indicating whether this permission corresponds to Studio project or not.</p>
     *
     * @param studio a <code>boolean</code> providing the flag indicating whether this permission corresponds to Studio
     *               project or not.
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * <p>
     * Gets the ID of a user permission which this permission grants access to.
     * </p>
     * 
     * @return the userPermissionId
     * @since 1.0.1
     */
    public long getUserPermissionId() {
        return userPermissionId;
    }

    /**
     * <p>
     * Sets the ID of a user permission which this permission grants access to.
     * </p>
     * 
     * @param userPermissionId
     *            the userPermissionId to set
     * @since 1.0.1
     */
    public void setUserPermissionId(long userPermissionId) {
        this.userPermissionId = userPermissionId;
    }

}
