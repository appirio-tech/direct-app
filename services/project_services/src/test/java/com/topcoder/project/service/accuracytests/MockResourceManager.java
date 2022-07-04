/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.accuracytests;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * Mock implementation of ResourceManager used for accuracy test cases.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class MockResourceManager implements ResourceManager {

    /**
     * Not implemented.
     *
     * @param arg0
     *            long array parameter.
     * @param arg1
     *            long parameter.
     * @param arg2
     *            long parameter.
     * @param arg3
     *            String parameter.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public void addNotifications(long[] arg0, long arg1, long arg2, String arg3) throws ResourcePersistenceException {

    }

    /**
     * Not implemented.
     *
     * @return null always.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public NotificationType[] getAllNotificationTypes() throws ResourcePersistenceException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @return null always.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public ResourceRole[] getAllResourceRoles() throws ResourcePersistenceException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            long parameter.
     * @param arg1
     *            long parameter.
     * @return null always.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public long[] getNotifications(long arg0, long arg1) throws ResourcePersistenceException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            The resource id.
     * @return null always.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public Resource getResource(long arg0) throws ResourcePersistenceException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            long array parameter.
     * @param arg1
     *            long parameter.
     * @param arg2
     *            long parameter.
     * @param arg3
     *            String parameter.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public void removeNotificationType(NotificationType arg0, String arg1) throws ResourcePersistenceException {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            NotificationType parameter.
     * @param arg1
     *            String parameter.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public void removeNotifications(long[] arg0, long arg1, long arg2, String arg3)
            throws ResourcePersistenceException {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            Resource parameter.
     * @param arg1
     *            String parameter.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public void removeResource(Resource arg0, String arg1) throws ResourcePersistenceException {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            ResourceRole parameter.
     * @param arg1
     *            String parameter.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public void removeResourceRole(ResourceRole arg0, String arg1) throws ResourcePersistenceException {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            Filter parameter.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     * @throws SearchBuilderException
     *             Not thrown.
     * @throws SearchBuilderConfigurationException
     *             Not thrown.
     */
    public NotificationType[] searchNotificationTypes(Filter arg0) throws ResourcePersistenceException,
            SearchBuilderException, SearchBuilderConfigurationException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            Filter parameter.
     * @return null.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     * @throws SearchBuilderException
     *             Not thrown.
     * @throws SearchBuilderConfigurationException
     *             Not thrown.
     */
    public Notification[] searchNotifications(Filter arg0) throws ResourcePersistenceException,
            SearchBuilderException, SearchBuilderConfigurationException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            Filter parameter.
     * @return null.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     * @throws SearchBuilderException
     *             Not thrown.
     * @throws SearchBuilderConfigurationException
     *             Not thrown.
     */
    public ResourceRole[] searchResourceRoles(Filter arg0) throws ResourcePersistenceException,
            SearchBuilderException, SearchBuilderConfigurationException {
        return null;
    }

    /**
     * Returns a sample Resource array containing two Resources.
     *
     * @param arg0
     *            Filter parameter.
     * @return a sample Resource array containing two Resources.
     * @throws ResourcePersistenceException
     *             Not thrown.
     * @throws SearchBuilderException
     *             Not thrown.
     * @throws SearchBuilderConfigurationException
     *             Not thrown.
     */
    public Resource[] searchResources(Filter arg0) throws ResourcePersistenceException, SearchBuilderException,
            SearchBuilderConfigurationException {
        return new Resource[] { new Resource(1) };
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            NotificationType parameter.
     * @param arg1
     *            String parameter.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public void updateNotificationType(NotificationType arg0, String arg1) throws ResourcePersistenceException {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            Resource parameter.
     * @param arg1
     *            String parameter.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public void updateResource(Resource arg0, String arg1) throws ResourcePersistenceException {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            ResourceRole parameter.
     * @param arg1
     *            String parameter.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public void updateResourceRole(ResourceRole arg0, String arg1) throws ResourcePersistenceException {

    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            An array of Resource.
     * @param arg1
     *            long parameter.
     * @param arg2
     *            String parameter.
     *
     * @throws ResourcePersistenceException
     *             Not thrown.
     */
    public void updateResources(Resource[] arg0, long arg1, String arg2) throws ResourcePersistenceException {

    }

}
