/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.permission;

import java.util.List;

import javax.ejb.Remote;

import com.topcoder.security.TCSubject;

/**
 * <p>
 * It provides CRUD on permission object.
 * </p>
 *
 * <p>
 * Version 1.1 (Direct Permissions Setting Back-end and Integration Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getProjectPermissions(long)} method.</li>
 *     <li>Added {@link #updateProjectPermissions(java.util.List, long)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TopCoder Direct Project Audit v1.0) change notes:
 *   <ol>
 *      <li>Added parameter <code>TCSubject</code> to method {@link #updatePermissions(TCSubject, Permission[])}.</li>
 *      <li>Added parameter <code>TCSubject</code> to
 *      method {@link #updateProjectPermissions(TCSubject, List, long)}.</li>
 *   </ol>
 * </p>
 * 
 * @author TCSASSEMBLER, TCSDEVELOPER
 * 
 * @since Cockpit Project Admin Release Assembly v1.0
 * @version 1.2
 */
@Remote
public interface PermissionService {

	/**
     * <p>
     * This method retrieve all the permissions that the user owned for any projects. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param userid user id to look for
     *
     * @return all the permissions that the user owned for any projects.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when getting permissions.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public List<Permission> getPermissionsByUser(long userid) throws PermissionServiceException;
    
    /**
     * <p>
     * This method retrieve all the permissions that various users own for a given project. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param projectid project id to look for
     *
     * @return all the permissions that various users own for a given project.
     *
     * @throws ContestManagementException if any error occurs when getting permissions.
     *
     * @since Cockpit Share Submission Integration
     */
    public List<Permission> getPermissionsByProject(long projectid) throws PermissionServiceException;

    /**
     * <p>
     * This method retrieve all the permissions that the user own for a given project. Returns empty list if no
     * permission found.
     * </p>
     *
     * @param userid user id to look for
     * @param projectid project id to look for
     *
     * @return all the permissions that the user own for a given project.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when getting permissions.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public List<Permission> getPermissions(long userid, long projectid) throws PermissionServiceException;

    /**
     * <p>
     * This method retrieve all the permission types.
     * </p>
     *
     * @return all the permission types.
     *
     * @throws PermissionServiceException if any error occurs when getting permission types.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public List<PermissionType> getAllPermissionType() throws PermissionServiceException;

    /**
     * <p>
     * This method will add a permission type, and return the added type entity.
     * </p>
     *
     * @param type the permission type to add.
     *
     * @return the added permission type entity
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when adding the permission type.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public PermissionType addPermissionType(PermissionType type) throws PermissionServiceException;



    /**
     * <p>
     * This method will update permission type data.
     * </p>
     *
     * @param type the permission type to update.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when updating the permission type.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public void updatePermissionType(PermissionType type) throws PermissionServiceException;



    /**
     * <p>
     * This method will update permission type data, return true if the permission type data exists and removed
     * successfully, return false if it doesn't exist.
     * </p>
     *
     * @param typeid the permission type to delete.
     *
     * @return true if the permission type data exists and removed successfully.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when deleting the permission.
     *
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public boolean deletePermissionType(long typeid) throws PermissionServiceException;


    
    /**
     * <p>
     * This method updates array of permissions to the persistence.
     * </p>
     * 
     * @param tcSubject the <code>TCSubject</code> instance represented the operator.
     * @param permissions the permissions to update.
     *
     * @throws IllegalArgumentWSException if the argument is invalid
     * @throws PermissionServiceException if any error occurs when updating the permission.
     *
     * @since Cockpit Project Admin Release Assembly.
     */
    public void updatePermissions(TCSubject tcSubject, Permission[] permissions) throws PermissionServiceException;


    /**
     * <p>
     * Get permission by id
     * </p>
     * 
     * @param id
     *            id to look for
     * 
     * @return permission
     * 
     * @throws PermissionServiceException
     *             if any error occurs when getting permissions.
     */
    public Permission getPermissionsById(long id) throws PermissionServiceException;

    /**
     * <p>Gets the permissions set for projects which specified user has <code>Full Access</code> permission set for.
     * </p>
     *
     * @param userId a <code>long</code> providing the ID of a user to get project permissions for.
     * @return a <code>List</code> listing the project permissions set for projects which specified user has <code>Full
     *         Access</code> permission set for.
     * @throws com.topcoder.service.permission.PermissionServiceException if an unexpected error occurs.
     * @since 1.1
     */
    public List<ProjectPermission> getProjectPermissions(long userId) throws PermissionServiceException;

    /**
     * <p>Updates the permissions for specified user for accessing the projects.</p>
     *
     * @param tcSubject the <code>TCSubject</code> instance represented the operator.
     * @param projectPermissions a <code>List</code> listing the permissions to be set for specified user for accessing
     *        projects.
     * @param userId a <code>long</code> providing the ID of a user to update permissions for.
     * @throws PermissionServiceException if an unexpected error occurs.
     * @since 1.1
     */
    public void updateProjectPermissions(TCSubject tcSubject, List<ProjectPermission> projectPermissions, long userId)
        throws PermissionServiceException;

}
