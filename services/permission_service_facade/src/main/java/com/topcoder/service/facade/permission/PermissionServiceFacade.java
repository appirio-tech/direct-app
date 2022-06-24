/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.permission;

import com.topcoder.security.TCSubject;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.permission.ProjectPermission;

import java.util.List;

/**
 * Interface definition for TopCoder permission service facade.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface PermissionServiceFacade {
    /**
     * <p>
     * This method retrieve all the permissions that the user owned for any
     * projects. Returns empty list if no permission found.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param userid    user id to look for
     * @return all the permissions that the user owned for any projects.
     * @throws PermissionServiceException if any error occurs when getting permissions.
     */
    public List<Permission> getPermissionsByUser(TCSubject tcSubject, long userid)
            throws PermissionServiceException;

    /**
     * <p>
     * This method retrieve all the permissions that various users own for a
     * given project. Returns empty list if no permission found.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectid project id to look for
     * @return all the permissions that various users own for a given project.
     * @throws PermissionServiceException if any error occurs when getting permissions.
     */
    public List<Permission> getPermissionsByProject(TCSubject tcSubject, long projectid)
            throws PermissionServiceException;


    /**
     * <p>
     * This method retrieve all the permissions that the user own for a given
     * project. Returns empty list if no permission found.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param userid    user id to look for
     * @param projectid project id to look for
     * @return all the permissions that the user own for a given project.
     * @throws PermissionServiceException if any error occurs when getting permissions.
     */
    public List<Permission> getPermissions(TCSubject tcSubject, long userid, long projectid)
            throws PermissionServiceException;

    /**
     * <p>
     * This method retrieve all the permission types.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return all the permission types.
     * @throws PermissionServiceException if any error occurs when getting permission types.
     */
    public List<PermissionType> getAllPermissionType(TCSubject tcSubject) throws PermissionServiceException;

    /**
     * <p>
     * This method will add a permission type, and return the added type entity.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param type      the permission type to add.
     * @return the added permission type entity
     * @throws PermissionServiceException if any error occurs when adding the permission type.
     */
    public PermissionType addPermissionType(TCSubject tcSubject, PermissionType type)
            throws PermissionServiceException;

    /**
     * <p>
     * This method will update permission type data.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param type      the permission type to update.
     * @throws PermissionServiceException if any error occurs when updating the permission type.
     */
    public void updatePermissionType(TCSubject tcSubject, PermissionType type)
            throws PermissionServiceException;

    /**
     * <p>
     * This method updates array of permissions to the persistence.
     * </p>
     *
     * @param tcSubject   TCSubject instance contains the login security info for the current user
     * @param permissions the permissions to update.
     * @throws PermissionServiceException if any error occurs when updating the permission.
     */
    public void updatePermissions(TCSubject tcSubject, Permission[] permissions)
            throws PermissionServiceException;


    /**
     * <p>Gets the permissions set for projects which specified user has <code>Full Access</code> permission set for.
     * </p>
     *
     * @param tcSubject a <code>TCSubject</code> instance contains the login security info for the current user.
     * @return a <code>List</code> listing the project permissions set for projects which specified user has <code>Full
     *         Access</code> permission set for.
     * @throws PermissionServiceException if an unexpected error occurs.
     */
    public List<ProjectPermission> getProjectPermissions(TCSubject tcSubject)
            throws PermissionServiceException;

    /**
     * <p>Updates the permissions for specified user for accessing the projects.</p>
     *
     * @param tcSubject          a <code>TCSubject</code> instance contains the login security info for the current user.
     * @param projectPermissions a <code>List</code> listing the permissions to be set for specified user for accessing
     *                           projects.
     * @param role               the role id to add
     * @throws PermissionServiceException if an unexpected error occurs.
     */
    public void updateProjectPermissions(TCSubject tcSubject, List<ProjectPermission> projectPermissions, long role)
            throws PermissionServiceException;

}
