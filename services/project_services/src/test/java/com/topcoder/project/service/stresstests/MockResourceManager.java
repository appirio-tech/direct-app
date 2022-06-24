/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This is a mock implementation of {@link ResourceManager}.
 * </p>
 * 
 * @author stylecheck
 * @version 1.1
 * @since 1.0
 */
public class MockResourceManager implements ResourceManager {

    /**
     * <p>
     * Mock Implementation.
     * </p>
     */
    public MockResourceManager() {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param resource
     *            The resource to update
     * @param operator
     *            The operator making the update
     * @throws IllegalArgumentException
     *             If a required field of the resource is not set (if resource.getResourceRole() is null), or if
     *             the resource role is associated with a phase type and the resource role is not associated with a
     *             phase or the resource or the operator is null
     * @throws ResourcePersistenceException
     *             If there is an error updating the resource
     */
    public void updateResource(Resource resource, String operator) throws ResourcePersistenceException {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param resource
     *            The resource to remove
     * @param operator
     *            The operator making the update
     * @throws IllegalArgumentException
     *             If resource or operator is null or if the id of the resource is UNSET_ID
     * @throws ResourcePersistenceException
     *             If there is an error updating the the persistence store.
     */
    public void removeResource(Resource resource, String operator) throws ResourcePersistenceException {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param resources
     *            The resources associated with the project
     * @param project
     *            The project to update resources for
     * @param operator
     *            The operator making the update
     * @throws IllegalArgumentException
     *             if a required field of the resource is not set (if resource.getResourceRole() is null), or if
     *             the resource role is associated with a phase type and the resource role is not associated with a
     *             phase or if operator is null, or the project is &lt;= 0, or the resources is null or has null
     *             entries
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence store.
     * @throws IllegalArgumentException
     *             If resources in the array have a project that is not the same as the project argument
     */
    public void updateResources(Resource[] resources, long project, String operator)
            throws ResourcePersistenceException {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return The loaded Resource
     * @param id
     *            the id of the resource to retrieve
     * @throws IllegalArgumentException
     *             If id is &lt;= 0
     * @throws ResourcePersistenceException
     *             if there is an error reading the persistence store
     */
    public Resource getResource(long id) throws ResourcePersistenceException {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return The loaded resources
     * @param filter
     *            The filter to use
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store
     * @throws SearchBuilderException
     *             If there is an error executing the filter.
     */
    public Resource[] searchResources(Filter filter) throws ResourcePersistenceException, SearchBuilderException {

        return new Resource[] {new Resource(1), new Resource(2) };
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param resourceRole
     *            the resource role to update
     * @param operator
     *            the operator making the update
     * @throws IllegalArgumentException
     *             if a required field of the resource role is missing (i.e. name or description of the resource
     *             role is null) or if resourceRole or operator is null
     * @throws ResourcePersistenceException
     *             if there is an error updating the persistence
     */
    public void updateResourceRole(ResourceRole resourceRole, String operator) throws ResourcePersistenceException {
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param resourceRole
     *            The resource role to remove
     * @param operator
     *            The operator making the update
     * @throws IllegalArgumentException
     *             If the id of the resource role is UNSET_ID, or if resourceRole or operator is null
     * @throws ResourcePersistenceException
     *             If there is an error updating the the persistence store.
     */
    public void removeResourceRole(ResourceRole resourceRole, String operator) throws ResourcePersistenceException {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return All resource roles in the persistence store
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store.
     */
    public ResourceRole[] getAllResourceRoles() throws ResourcePersistenceException {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param filter
     *            The filter to use
     * @return The loaded resource roles
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store
     * @throws SearchBuilderException
     *             If there is an error executing the filter.
     */
    public ResourceRole[] searchResourceRoles(Filter filter) throws ResourcePersistenceException,
            SearchBuilderException {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param users
     *            The users to add notifications for
     * @param project
     *            The project the notifications apply to
     * @param notificationType
     *            The type of notifications to add
     * @param operator
     *            The operation making the update
     * @throws IllegalArgumentException
     *             If any item of users, project, or notificationType is &lt;= 0, or operator or users is null
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence store
     */
    public void addNotifications(long[] users, long project, long notificationType, String operator)
            throws ResourcePersistenceException {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param users
     *            The users to remove notifications for
     * @param project
     *            The project the notifications apply to
     * @param notificationType
     *            The type of notifications to remove
     * @param operator
     *            The operation making the update
     * @throws IllegalArgumentException
     *             If operator or users is null
     * @throws IllegalArgumentException
     *             If any item of users, project, or notificationType is &lt;= 0 or the operator or users is null.
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence store
     */
    public void removeNotifications(long[] users, long project, long notificationType, String operator)
            throws ResourcePersistenceException {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param project
     *            the project to get notifications for
     * @param notificationType
     *            the type of notifications to retrieve
     * @return The user ids of the notifications for the project and type
     * @throws IllegalArgumentException
     *             If project or notificationType is &lt;= 0
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store
     */
    public long[] getNotifications(long project, long notificationType) throws ResourcePersistenceException {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param filter
     *            The filter to use
     * @return The loaded notifications
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store
     * @throws SearchBuilderException
     *             If there is an error executing the filter.
     */
    public Notification[] searchNotifications(Filter filter) throws ResourcePersistenceException,
            SearchBuilderException {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param notificationType
     *            The notification type to update
     * @param operator
     *            The operator making the update
     * @throws IllegalArgumentException
     *             If a required field of the notification type is missing (i.e. name or description of the
     *             notification type is null), or notification or operator is null
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void updateNotificationType(NotificationType notificationType, String operator)
            throws ResourcePersistenceException {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param notificationType
     *            The notification type to remove
     * @param operator
     *            The operator making the update
     * @throws IllegalArgumentException
     *             If the id of the resource role is UNSET_ID, or notificationType or operation is null
     * @throws ResourcePersistenceException
     *             If there is an error updating the the persistence store.
     */
    public void removeNotificationType(NotificationType notificationType, String operator)
            throws ResourcePersistenceException {

    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param filter
     *            The filter to use
     * @return The loaded notification types
     * @throws IllegalArgumentException
     *             If filter is null
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store
     * @throws SearchBuilderException
     *             If there is an error executing the filter.
     */
    public NotificationType[] searchNotificationTypes(Filter filter) throws ResourcePersistenceException,
            SearchBuilderException {
        return null;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @return All notification types in the persistence store
     * @throws ResourcePersistenceException
     *             If there is an error reading the persistence store.
     */
    public NotificationType[] getAllNotificationTypes() throws ResourcePersistenceException {
        return null;
    }

}
