/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.stresstests;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;

import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;


/**
 * Mock implementation of ResourceManager.
 *
 * @author onsky
 * @version 1.0
 */
public class MockResourceManager implements ResourceManager {
    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     * @param arg2 Mock!
     * @param arg3 Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public void addNotifications(long[] arg0, long arg1, long arg2, String arg3)
        throws ResourcePersistenceException {
    }

    /**
     * Mock!
     *
     * @return Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public NotificationType[] getAllNotificationTypes()
        throws ResourcePersistenceException {
        return null;
    }

    /**
     * Mock!
     *
     * @return Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public ResourceRole[] getAllResourceRoles() throws ResourcePersistenceException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @return Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public long[] getNotifications(long arg0, long arg1)
        throws ResourcePersistenceException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     *
     * @return Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public Resource getResource(long arg0) throws ResourcePersistenceException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public void removeNotificationType(NotificationType arg0, String arg1)
        throws ResourcePersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     * @param arg2 Mock!
     * @param arg3 Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public void removeNotifications(long[] arg0, long arg1, long arg2, String arg3)
        throws ResourcePersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public void removeResource(Resource arg0, String arg1)
        throws ResourcePersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public void removeResourceRole(ResourceRole arg0, String arg1)
        throws ResourcePersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     *
     * @return Mock!
     *
     * @throws ResourcePersistenceException Mock!
     * @throws SearchBuilderException Mock!
     */
    public NotificationType[] searchNotificationTypes(Filter arg0)
        throws ResourcePersistenceException, SearchBuilderException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     *
     * @return Mock!
     *
     * @throws ResourcePersistenceException Mock!
     * @throws SearchBuilderException Mock!
     */
    public Notification[] searchNotifications(Filter arg0)
        throws ResourcePersistenceException, SearchBuilderException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     *
     * @return Mock!
     *
     * @throws ResourcePersistenceException Mock!
     * @throws SearchBuilderException Mock!
     */
    public ResourceRole[] searchResourceRoles(Filter arg0)
        throws ResourcePersistenceException, SearchBuilderException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     *
     * @return Mock!
     *
     * @throws ResourcePersistenceException Mock!
     * @throws SearchBuilderException Mock!
     */
    public Resource[] searchResources(Filter arg0) throws ResourcePersistenceException, SearchBuilderException {
        return null;
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public void updateNotificationType(NotificationType arg0, String arg1)
        throws ResourcePersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public void updateResource(Resource arg0, String arg1)
        throws ResourcePersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public void updateResourceRole(ResourceRole arg0, String arg1)
        throws ResourcePersistenceException {
    }

    /**
     * Mock!
     *
     * @param arg0 Mock!
     * @param arg1 Mock!
     * @param arg2 Mock!
     *
     * @throws ResourcePersistenceException Mock!
     */
    public void updateResources(Resource[] arg0, long arg1, String arg2)
        throws ResourcePersistenceException {
    }
}
