/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistence;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;

/**
 * <p>
 * Use of <code>{@link UnmanagedTransactionResourcePersistence}</code> in an EJB. This bean implements the
 * ResourcePersistence interface and uses the <code>UnmanagedTransactionResourcePersistence</code> as the
 * persistence.
 * </p>
 *
 * @author mittu
 * @version 1.1
 *
 */
public class ResourceBean implements SessionBean, ResourcePersistence {
    /**
     * <p>
     * This is a session context that is used to obtain the run time session context.
     * </p>
     */
    private SessionContext sessionContext;

    /**
     * <p>
     * Represents the unmanaged persistence for demo.
     * </p>
     */
    private transient AbstractResourcePersistence persistence;

    /**
     * Does nothing.
     */
    public void ejbActivate() {
    }

    /**
     * Does nothing.
     */
    public void ejbPassivate() {
    }

    /**
     * Does nothing.
     */
    public void ejbRemove() {
    }

    /**
     * Sets the sessionContext.
     *
     * @param context
     *            the session context
     */
    public void setSessionContext(SessionContext context) {
        this.sessionContext = context;
    }

    /**
     *
     * @throws CreateException
     *             if there was an issue with creation (we rethrow)
     */
    public void ejbCreate() throws CreateException {
        try {
            DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl();
            persistence = new UnmanagedTransactionResourcePersistence(connectionFactory);
        } catch (Exception e) {
            throw new CreateException("Failed to create UnManagedTransactionPersistence - " + e.getMessage());
        }
    }

    /**
     * @see com.topcoder.management.resource.ResourceManager#addNotifications(long[], long, long,
     *      java.lang.String)
     */
    public void addNotification(long users, long project, long notificationType, String operator)
        throws ResourcePersistenceException {
        try {
            persistence.addNotification(users, project, notificationType, operator);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#addNotificationType
     *      (com.topcoder.management.resource.NotificationType)
     */
    public void addNotificationType(NotificationType notificationType) throws ResourcePersistenceException {
        try {
            persistence.addNotificationType(notificationType);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#addResource
     *      (com.topcoder.management.resource.Resource)
     */
    public void addResource(Resource resource) throws ResourcePersistenceException {
        try {
            persistence.addResource(resource);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#addResourceRole
     *      (com.topcoder.management.resource.ResourceRole)
     */
    public void addResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {
        try {
            persistence.addResourceRole(resourceRole);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#deleteNotificationType
     *      (com.topcoder.management.resource.NotificationType)
     */
    public void deleteNotificationType(NotificationType notificationType) throws ResourcePersistenceException {
        try {
            persistence.deleteNotificationType(notificationType);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#deleteResource
     *      (com.topcoder.management.resource.Resource)
     */
    public void deleteResource(Resource resource) throws ResourcePersistenceException {
        try {
            persistence.deleteResource(resource);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#deleteResourceRole
     *      (com.topcoder.management.resource.ResourceRole)
     */
    public void deleteResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {
        try {
            persistence.deleteResourceRole(resourceRole);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#loadNotification(long, long,
     *      long)
     */
    public Notification loadNotification(long user, long project, long notificationType)
        throws ResourcePersistenceException {
        try {
            return persistence.loadNotification(user, project, notificationType);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#loadNotificationType(long)
     */
    public NotificationType loadNotificationType(long notificationTypeId) throws ResourcePersistenceException {
        try {
            return persistence.loadNotificationType(notificationTypeId);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#loadNotificationTypes(long[])
     */
    public NotificationType[] loadNotificationTypes(long[] notificationTypeIds)
        throws ResourcePersistenceException {
        try {
            return persistence.loadNotificationTypes(notificationTypeIds);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#loadNotifications(long[], long[],
     *      long[])
     */
    public Notification[] loadNotifications(long[] userIds, long[] projectId, long[] notificationTypes)
        throws ResourcePersistenceException {
        try {
            return persistence.loadNotifications(userIds, projectId, notificationTypes);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#loadResource(long)
     */
    public Resource loadResource(long resourceId) throws ResourcePersistenceException {
        try {
            return persistence.loadResource(resourceId);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#loadResourceRole(long)
     */
    public ResourceRole loadResourceRole(long resourceId) throws ResourcePersistenceException {
        try {
            return persistence.loadResourceRole(resourceId);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#loadResourceRoles(long[])
     */
    public ResourceRole[] loadResourceRoles(long[] resourceRoleIds) throws ResourcePersistenceException {
        try {
            return persistence.loadResourceRoles(resourceRoleIds);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#loadResources(long[])
     */
    public Resource[] loadResources(long[] resourceIds) throws ResourcePersistenceException {
        try {
            return persistence.loadResources(resourceIds);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#removeNotification(long, long,
     *      long, java.lang.String)
     */
    public void removeNotification(long user, long project, long notificationType, String operator)
        throws ResourcePersistenceException {
        try {
            persistence.removeNotification(user, project, notificationType, operator);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#updateNotificationType
     *      (com.topcoder.management.resource.NotificationType)
     */
    public void updateNotificationType(NotificationType notificationType) throws ResourcePersistenceException {
        try {
            persistence.updateNotificationType(notificationType);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#updateResource
     *      (com.topcoder.management.resource.Resource)
     */
    public void updateResource(Resource resource) throws ResourcePersistenceException {
        try {
            persistence.updateResource(resource);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * @see com.topcoder.management.resource.persistence.ResourcePersistence#updateResourceRole
     *      (com.topcoder.management.resource.ResourceRole)
     */
    public void updateResourceRole(ResourceRole resourceRole) throws ResourcePersistenceException {
        try {
            persistence.updateResourceRole(resourceRole);
        } catch (ResourcePersistenceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }
}
