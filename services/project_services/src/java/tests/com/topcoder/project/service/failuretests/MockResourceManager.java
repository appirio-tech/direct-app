/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.failuretests;

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
 * A mock implementation of ResourceManager.
 * </p>
 * 
 * @author mittu
 * @version 1.0
 */
public class MockResourceManager implements ResourceManager {
    /**
     * <p>
     * Represents the state of the mock.
     * </p>
     */
    private byte state = 0x0;
    
    /**
     * @see com.topcoder.management.resource.ResourceManager#updateResource(
     *      com.topcoder.management.resource.Resource, java.lang.String)
     */
    public void updateResource(Resource resource, String operator) throws ResourcePersistenceException {
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#removeResource(
     *      com.topcoder.management.resource.Resource, java.lang.String)
     */
    public void removeResource(Resource resource, String operator) throws ResourcePersistenceException {
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#updateResources(
     *      com.topcoder.management.resource.Resource[], long, java.lang.String)
     */
    public void updateResources(Resource[] resources, long project, String operator)
            throws ResourcePersistenceException {
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#getResource(long)
     */
    public Resource getResource(long id) throws ResourcePersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#searchResources(com.topcoder.search.builder.filter.Filter)
     */
    public Resource[] searchResources(Filter filter) throws ResourcePersistenceException,
            SearchBuilderException {
        if (state == 0x1) {
            throw new ResourcePersistenceException("Fail");
        }
        if (state == 0x2) {
            throw new SearchBuilderException("Fail");
        }
        return null;
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#updateResourceRole(
     *      com.topcoder.management.resource.ResourceRole, java.lang.String)
     */
    public void updateResourceRole(ResourceRole resourceRole, String operator)
            throws ResourcePersistenceException {
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#removeResourceRole(
     *      com.topcoder.management.resource.ResourceRole, java.lang.String)
     */
    public void removeResourceRole(ResourceRole resourceRole, String operator)
            throws ResourcePersistenceException {
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#getAllResourceRoles()
     */
    public ResourceRole[] getAllResourceRoles() throws ResourcePersistenceException {
        return new ResourceRole[] { new ResourceRole(1), new ResourceRole(2) };
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#searchResourceRoles(
     *      com.topcoder.search.builder.filter.Filter)
     */
    public ResourceRole[] searchResourceRoles(Filter filter) throws ResourcePersistenceException,
            SearchBuilderException {
        return null;
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#addNotifications(long[], long, long,
     *      java.lang.String)
     */
    public void addNotifications(long[] users, long project, long notificationType, String operator)
            throws ResourcePersistenceException {
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#removeNotifications(long[], long, long,
     *      java.lang.String)
     */
    public void removeNotifications(long[] users, long project, long notificationType, String operator)
            throws ResourcePersistenceException {
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#getNotifications(long, long)
     */
    public long[] getNotifications(long project, long notificationType) throws ResourcePersistenceException {
        return null;
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#searchNotifications(
     *      com.topcoder.search.builder.filter.Filter)
     */
    public Notification[] searchNotifications(Filter filter) throws ResourcePersistenceException,
            SearchBuilderException {
        return null;
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#updateNotificationType(
     *      com.topcoder.management.resource.NotificationType, java.lang.String)
     */
    public void updateNotificationType(NotificationType notificationType, String operator)
            throws ResourcePersistenceException {
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#removeNotificationType(
     *      com.topcoder.management.resource.NotificationType, java.lang.String)
     */
    public void removeNotificationType(NotificationType notificationType, String operator)
            throws ResourcePersistenceException {
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#searchNotificationTypes(
     *      com.topcoder.search.builder.filter.Filter)
     */
    public NotificationType[] searchNotificationTypes(Filter filter) throws ResourcePersistenceException,
            SearchBuilderException {
        return null;
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#getAllNotificationTypes()
     */
    public NotificationType[] getAllNotificationTypes() throws ResourcePersistenceException {
        return null;
    }
    
    /**
     * <p>
     * Sets the state of the mock.
     * </p>
     * 
     * @param state
     */
    public void setState(byte state) {
        this.state = state;
    }
}
