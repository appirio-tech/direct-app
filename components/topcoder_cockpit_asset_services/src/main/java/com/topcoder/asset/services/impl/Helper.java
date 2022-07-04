/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.codehaus.jackson.map.ObjectMapper;

import com.topcoder.asset.entities.AuditRecord;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.ServiceException;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.util.log.Log;

/**
 * <p>
 * Helper class for the component. It provides useful common methods for all the classes in this component.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class has no state, and thus it is thread safe.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
final class Helper {

    /**
     * <p>
     * Represents the object mapper.
     * </p>
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * <p>
     * Prevents to create a new instance.
     * </p>
     */
    private Helper() {
        // empty
    }

    /**
     * Gets the entity.
     *
     * @param <T>
     *            the entity type
     * @param log
     *            the log
     * @param signature
     *            the signature
     * @param name
     *            the name
     * @param entityManager
     *            the entity manager
     * @param type
     *            the type
     * @param id
     *            the id
     *
     * @return the entity.
     *
     * @throws javax.persistence.PersistenceException
     *             if an error occurs while accessing the persistence
     * @throws IllegalStateException
     *             if the entity manager has been closed
     * @throws EntityNotFoundException
     *             if the entity can't be found
     */
    static <T> T getEntity(Log log, String signature, String name, EntityManager entityManager, Class<T> type, long id)
        throws EntityNotFoundException {
        try {
            T existingEntity = entityManager.find(type, id);
            ValidationUtility.checkNotNull(existingEntity, name, EntityNotFoundException.class);

            return existingEntity;
        } catch (EntityNotFoundException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * <p>
     * Performs the audit.
     * </p>
     *
     * @param entityManager
     *            the entity manager.
     * @param userId
     *            the user id.
     * @param action
     *            the action.
     * @param entityType
     *            the entity type.
     * @param entityId
     *            the entity id.
     * @param oldValue
     *            the old value.
     * @param newValue
     *            the new value.
     *
     * @throws PersistenceException
     *             if any error occurs
     */
    static void performAudit(EntityManager entityManager, long userId, String action, String entityType,
        long entityId, String oldValue, String newValue) {
        AuditRecord auditRecord = new AuditRecord();
        auditRecord.setTimestamp(new Date());
        auditRecord.setUserId(userId);
        auditRecord.setAction(action);
        auditRecord.setEntityType(entityType);
        auditRecord.setEntityId(entityId);
        auditRecord.setOldValue(oldValue);
        auditRecord.setNewValue(newValue);

        entityManager.persist(auditRecord);
    }

    /**
     * Converts the object to string.
     *
     * @param log
     *            the log
     * @param signature
     *            the signature
     * @param obj
     *            the object
     *
     * @return the string representation of the object.
     *
     * @throws ServiceException
     *             if any error occurs.
     */
    static String toString(Log log, String signature, Object obj) throws ServiceException {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ServiceException(
                "Failed to convert the object to a string.", e));
        }
    }

    /**
     * Converts the object to string.
     *
     * @param obj
     *            the object
     *
     * @return the string representation of the object.
     */
    static String toString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            return String.valueOf(obj);
        }
    }
}
