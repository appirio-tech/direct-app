/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.stresstests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistence;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;

/**
 * <p>
 * This mock class is used in the stress test.
 * </p>
 *
 * @author King_Bette
 * @version 1.0
 */
public class MockResourcePersistence implements ResourcePersistence {

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param resource
     *            The resource to add to the persistence store
     * @throws IllegalArgumentException
     *             If resource is null or its id is UNSET_ID or its ResourceRole
     *             is null or its creation/modification user/date is null
     * @throws ResourcePersistenceException
     *             If there is a failure to persist the change or a Resource
     *             with the id is already in the persistence.
     */
    public void addResource(Resource resource) throws ResourcePersistenceException {
        // do nothing.
    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param resource
     *            The resource to remove
     * @throws IllegalArgumentException
     *             If resource is null or its id is UNSET_ID or its ResourceRole
     *             is null
     * @throws ResourcePersistenceException
     *             If there is a failure to persist the change.
     */
    public void deleteResource(Resource resource) throws ResourcePersistenceException {
        // do nothing.
    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param resource
     *            The resource to update
     * @throws IllegalArgumentException
     *             If resource is null or its id is UNSET_ID or its ResourceRole
     *             s null or its modification user/date is null
     * @throws ResourcePersistenceException
     *             If there is a failure to persist the Resource, or the
     *             Resource is not already in the persistence.
     */
    public void updateResource(Resource resource) throws ResourcePersistenceException {
        // do noting.
    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param resourceId
     *            The id of the Resource to load
     * @return The loaded Resource
     * @throws IllegalArgumentException
     *             If resourceId is &lt;= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading the Resource
     */
    public Resource loadResource(long resourceId) throws ResourcePersistenceException {
        return null;
    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param user
     *            The user id to add as a notification
     * @param project
     *            The project the notification is related to
     * @param notificationType
     *            The id of the notification type
     * @param operator
     *            The operator making the change
     * @throws IllegalArgumentException
     *             If user, project, or notificationType is &lt;= 0
     * @throws IllegalArgumentException
     *             If operator is null
     * @throws ResourcePersistenceException
     *             If there is an error adding the change to the persistence
     *             store.
     */
    public void addNotification(long user, long project, long notificationType, String operator)
        throws ResourcePersistenceException {
        // do nothing.
    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param user
     *            The user id of the notification to remove
     * @param project
     *            The project id of the notification to remove
     * @param notificationType
     *            The notification type id of the notification to remove
     * @param operator
     *            The operator making the change
     * @throws IllegalArgumentException
     *             If user, project, or notificationType is &lt;= 0
     * @throws IllegalArgumentException
     *             If operator is null
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence store.
     */
    public void removeNotification(long user, long project, long notificationType, String operator)
        throws ResourcePersistenceException {
        // do nothing.
    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @return The loaded notification
     * @param user
     *            The id of the user
     * @param project
     *            The id of the project
     * @param notificationType
     *            The id of the notificationType
     * @throws IllegalArgumentException
     *             If user, project, or notificationType is &lt;= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading from the persistence
     */
    public Notification loadNotification(long user, long project, long notificationType)
        throws ResourcePersistenceException {
        return null;
    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param notificationType
     *            The notification type to add
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is
     *             NotificationType.UNSET_ID or its name/description is null or
     *             its creation/modification user/date are null
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void addNotificationType(NotificationType notificationType) throws ResourcePersistenceException {

    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param notificationType
     *            The notification type to delete
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is UNSET_ID.
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void deleteNotificationType(NotificationType notificationType) throws ResourcePersistenceException {

    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param notificationType
     *            The notification type to update
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is UNSET_ID or its
     *             name/description is null or its modification user/date is
     *             null
     */
    public void updateNotificationType(NotificationType notificationType) {

    }

    /**
     * <p>
     * do noting.
     * </p>
     *
     * @param notificationTypeId
     *            The id of the notification type to load
     * @return The loaded notification type
     * @throws IllegalArgumentException
     *             If notificationTypeId is &lt;= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading the notification type
     */
    public NotificationType loadNotificationType(long notificationTypeId) throws ResourcePersistenceException {
        return null;
    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param resourceRole
     *            The resource role to add
     * @throws IllegalArgumentException
     *             If resourceRole is null or its id is UNSET_ID or its
     *             name/description is null or its creation/modification
     *             date/user is null
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void addResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {

    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param resourceRole
     *            The notification type to delete.
     * @throws IllegalArgumentException
     *             If notificationType is null or its id is UNSET_ID
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void deleteResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {

    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param resourceRole
     *            The resource role to update
     * @throws IllegalArgumentException
     *             If resourceRole is null or its id is UNSET_ID or its
     *             name/description is null or its modification user/date is
     *             null
     * @throws ResourcePersistenceException
     *             if there is an error updating the persistence
     */
    public void updateResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {

    }

    /**
     * <p>
     * do nothing.
     * </p>
     *
     * @param resourceId
     *            The id of the resource role to load
     * @return The loaded resource role
     * @throws IllegalArgumentException
     *             If resourceRoleId is &lt;= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading the resource role
     */
    public ResourceRole loadResourceRole(long resourceId) throws ResourcePersistenceException {
        return null;
    }

    /**
     * <p>
     * return a array of resources with given resource ids.
     * </p>
     *
     * @param resourceIds
     *            The ids of resources to load
     * @return Theloaded resources
     * @throws IllegalArgumentException
     *             If any id is &lt;= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading the Resources
     */
    public Resource[] loadResources(long[] resourceIds) throws ResourcePersistenceException {
        List resouceList = new ArrayList();
        Set used = new HashSet();
        for (int i = 0; i < resourceIds.length; i++) {
            if (!used.contains(new Long(resourceIds[i]))) {
                used.add(new Long(resourceIds[i]));
                resouceList.add(new Resource(resourceIds[i]));
            }
        }
        return (Resource[]) resouceList.toArray(new Resource[resouceList.size()]);
    }

    /**
     * <p>
     * return a array of notificationType with given notificationTypeIds
     * </p>
     *
     * @param notificationTypeIds
     *            The ids of notification types to load
     * @return The loaded notification types
     * @throws IllegalArgumentException
     *             If any id is &lt;= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading from persistence
     */
    public NotificationType[] loadNotificationTypes(long[] notificationTypeIds)
        throws ResourcePersistenceException {
        List notificationTypeList = new ArrayList();
        Set used = new HashSet();
        for (int i = 0; i < notificationTypeIds.length; i++) {
            if (!used.contains(new Long(notificationTypeIds[i]))) {
                used.add(new Long(notificationTypeIds[i]));
                notificationTypeList.add(new NotificationType(notificationTypeIds[i]));
            }
        }
        return (NotificationType[]) notificationTypeList
            .toArray(new NotificationType[notificationTypeList.size()]);
    }

    /**
     * <p>
     * return a array of ResourceRole with given resourceRoleIds.
     * </p>
     *
     * @param resourceRoleIds
     *            The ids of resource roles to load
     * @return The loaded resource roles
     * @throws IllegalArgumentException
     *             If any id is &lt;= 0 or the array is null
     * @throws ResourcePersistenceException
     *             If there is an error loading from persistence
     */
    public ResourceRole[] loadResourceRoles(long[] resourceRoleIds) throws ResourcePersistenceException {
        List resourceRoleList = new ArrayList();
        Set used = new HashSet();
        for (int i = 0; i < resourceRoleIds.length; i++) {
            if (!used.contains(new Long(resourceRoleIds[i]))) {
                used.add(new Long(resourceRoleIds[i]));
                resourceRoleList.add(new ResourceRole(resourceRoleIds[i]));
            }
        }
        return (ResourceRole[]) resourceRoleList.toArray(new ResourceRole[resourceRoleList.size()]);
    }

    /**
     * <p>
     * return a array of Notification with given userIds, projectId,
     * notificationTypes.
     * </p>
     *
     * @param userIds
     *            The ids of the users
     * @param projectId
     *            The ids of the projects
     * @param notificationTypes
     *            The ids of the notification types
     * @return The loaded notifications
     * @throws IllegalArgumentException
     *             Any array is null, all three arrays do not have the same
     *             length, any id is &lt;= 0
     * @throws ResourcePersistenceException
     *             If there is an error loading from the persistence
     */
    public Notification[] loadNotifications(long[] userIds, long[] projectId, long[] notificationTypes)
        throws ResourcePersistenceException {
        List notificationList = new ArrayList();
        Set used = new HashSet();
        for (int i = 0; i < userIds.length; i++) {
            if (!used.contains(new Long(userIds[i]))) {
                used.add(new Long(userIds[i]));
                notificationList.add(new Notification(projectId[i], new NotificationType(notificationTypes[i]),
                    userIds[i]));
            }
        }
        return (Notification[]) notificationList.toArray(new Notification[notificationList.size()]);
    }
}
