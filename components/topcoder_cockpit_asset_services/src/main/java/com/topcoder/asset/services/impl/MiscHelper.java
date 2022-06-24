/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services.impl;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.codehaus.jackson.map.ObjectMapper;

import com.topcoder.asset.entities.Category;
import com.topcoder.asset.entities.FileTypeIcon;
import com.topcoder.asset.entities.User;
import com.topcoder.asset.exceptions.EntityNotFoundException;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
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
final class MiscHelper {
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
    private MiscHelper() {
        // empty
    }

    /**
     * This method will create the entity. It will also set newly assigned id to the given entity.
     *
     * @param log
     *            the log.
     * @param signature
     *            the signature.
     * @param entityManager
     *            the entity manager.
     * @param entity
     *            the entity to create.
     * @param name
     *            the name.
     *
     * @throws IllegalArgumentException
     *             if entity is null.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    static void create(Log log, String signature, EntityManager entityManager, Object entity, String name)
        throws PersistenceException {
        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {name},
            new Object[] {toString(entity)});

        try {
            ParameterCheckUtility.checkNotNull(entity, name);

            entityManager.persist(entity);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will update the entity.
     *
     * @param log
     *            the log.
     * @param signature
     *            the signature.
     * @param entityManager
     *            the entity manager.
     * @param entity
     *            the entity to update.
     * @param name
     *            the name.
     *
     * @throws IllegalArgumentException
     *             if entity is null, or entity.id is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    static void update(Log log, String signature, EntityManager entityManager, Object entity, String name)
        throws PersistenceException {
        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {name},
            new Object[] {toString(entity)});

        try {
            ParameterCheckUtility.checkNotNull(entity, name);

            long id;
            if (entity instanceof Category) {
                id = ((Category) entity).getId();
            } else {
                id = ((FileTypeIcon) entity).getId();
            }
            ParameterCheckUtility.checkPositive(id, name + ".getId()");

            Class<?> entityType = entity.getClass();
            MiscHelper.getEntity(log, signature, "Existing " + entityType.getSimpleName(), entityManager, entityType,
                id);

            // Update entity in persistence:
            entityManager.merge(entity);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will remove the entity with the given id.
     *
     * @param <T>
     *            the entity type.
     * @param log
     *            the log.
     * @param signature
     *            the signature.
     * @param entityManager
     *            the entity manager.
     * @param entityType
     *            the entity class.
     * @param entityId
     *            the entity id.
     * @param name
     *            the name.
     *
     * @throws IllegalArgumentException
     *             if entityId is not positive.
     * @throws EntityNotFoundException
     *             if the corresponding entity is not found.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    static <T> void delete(Log log, String signature, EntityManager entityManager, Class<T> entityType,
        long entityId, String name) throws PersistenceException {
        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {name},
            new Object[] {entityId});

        try {
            ParameterCheckUtility.checkPositive(entityId, name);

            T existingEntity = MiscHelper.getEntity(log, signature, "Existing " + entityType.getSimpleName(),
                entityManager, entityType, entityId);

            entityManager.remove(existingEntity);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will retrieve the entity with the given id.
     *
     * @param <T>
     *            the entity type.
     * @param log
     *            the log.
     * @param signature
     *            the signature.
     * @param entityManager
     *            the entity manager.
     * @param entityType
     *            the entity class.
     * @param entityId
     *            the entity id.
     * @param name
     *            the name.
     *
     * @return the entity with the given id
     *
     * @throws IllegalArgumentException
     *             if categoryId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    static <T> T get(Log log, String signature, EntityManager entityManager, Class<T> entityType,
        long entityId, String name) throws PersistenceException {
        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {name},
            new Object[] {entityId});

        try {
            ParameterCheckUtility.checkPositive(entityId, name);

            T result = entityManager.find(entityType, entityId);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {MiscHelper.toString(result)});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "The entity manager has been closed.", e));
        }
    }

    /**
     * This method will retrieve the entities.
     *
     * @param <T>
     *            the entity type.
     * @param log
     *            the log.
     * @param signature
     *            the signature.
     * @param entityManager
     *            the entity manager.
     * @param str
     *            the query string.
     * @param projectId
     *            the project id.
     *
     * @return the entities
     *
     * @throws IllegalArgumentException
     *             if projectId is not positive when it's not null.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     */
    @SuppressWarnings("unchecked")
    static <T> List<T> getEntities(Log log, String signature, EntityManager entityManager, String str, Long projectId)
        throws PersistenceException {
        String[] paramNames = null;
        Object[] paramValues = null;

        boolean projectIdNotNull = (projectId != null);
        if (projectIdNotNull) {
            paramNames = new String[] {"projectId"};
            paramValues = new Object[] {projectId};
        }

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature, paramNames, paramValues);

        try {
            Query query;
            if (projectIdNotNull) {
                ParameterCheckUtility.checkPositive(projectId, "projectId");

                query = entityManager.createNativeQuery(str, User.class).setParameter("projectId", projectId);
            } else {
                query = entityManager.createQuery(str);
            }

            // Get the result
            List<T> result = query.getResultList();

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {MiscHelper.toString(result)});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "An error has occurred while accessing the persistence.", e));
        } catch (IllegalStateException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new PersistenceException(
                "The entity manager has been closed.", e));
        }
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
