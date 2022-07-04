/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.EntityNotFoundException;
import com.topcoder.direct.services.copilot.dao.GenericDAO;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>This class is an implementation of GenericDAO that uses Hibernate session to access entities in persistence. It's
 * assumed that this class will be initialized using Spring IoC. This class uses Logging Wrapper logger to log errors
 * and debug information.</p>
 *
 * <p><strong>Thread safety:</strong> This class has mutable attributes, thus it's not thread safe. But it's assumed
 * that it will be initialized via Spring IoC before calling any business method, this way it's always used in thread
 * safe manner. It uses thread safe SessionFactory, Session and Log instances. This class uses Spring declarative
 * transactions, thus all methods that can modify data in persistence have Transactional annotation.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 * @param <T> the type of the entity to be managed by this DAO
 */
public abstract class GenericDAOImpl<T extends IdentifiableEntity> extends BaseDAO implements GenericDAO<T> {

    /**
     * <p>Represents an hql statement used for deleting a entity with a given id from persistence.</p>
     */
    private static final String DELETE_ENTITY = "delete from {0} e where e.id = :id";

    /**
     * <p>Represents the name of the actual class for which method will be invoked.</p>
     */
    private final String className = getClass().getSimpleName();

    /**
     * <p>The entity bean class type for this generic DAO. Is initialized in the constructor and never changed after
     * that. Cannot be null. Is used in delete(), retrieve() and retrieveAll().</p>
     */
    private final Class<T> entityBeanType;

    /**
     * <p>Creates new instance of <code>{@link GenericDAOImpl}</code> class.</p>
     */
    @SuppressWarnings("unchecked")
    public GenericDAOImpl() {
        // retrieves the actual type of generic parameter
        entityBeanType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * <p>Creates the given entity in the persistence.</p>
     *
     * @param entity the entity to be created in the persistence
     *
     * @return the generated entity ID
     *
     * @throws IllegalArgumentException if entity is null
     * @throws CopilotDAOException      if some other error occurred
     */
    @Transactional
    public long create(T entity) throws CopilotDAOException {
        final String methodName = "create";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), className, methodName, new String[]{"entity"}, new Object[]{entity});

        try {
            // update audit fields of the entity
            // propagates IllegalArgumentException
            updateAuditTimestamp(entity, true);
        } catch (IllegalArgumentException e) {
            Helper.logError(getLog(), MessageFormat.format(Helper.ILLEGAL_ARGUMENT, className,
                    methodName), e);
            throw e;
        }

        try {
            // save the entity in persistence
            getSession().persist(entity);

            // get newly created id
            long result = entity.getId();

            // log method exit
            Helper.logMethodExited(getLog(), className, methodName, executionStart, result);

            // return result
            return result;
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException("Exception occurred when persisting entity.", e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, className, methodName), exc);

            throw exc;
        } catch (CopilotDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, className, methodName), e);
            throw e;
        }
    }

    /**
     * <p>Updates the given entity in the persistence.</p>
     *
     * @param entity the entity to be updated in the persistence
     *
     * @throws IllegalArgumentException if entity is null or entity.getId() <= 0
     * @throws EntityNotFoundException  if entity with the same ID cannot be found in the persistence
     * @throws CopilotDAOException      if some other error occurred
     */
    @Transactional
    public void update(T entity) throws CopilotDAOException {
        final String methodName = "update";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), className, methodName, new String[]{"entity"}, new Object[]{entity});

        try {
            Helper.checkIsNotNull(entity, "entity");
            Helper.checkIsPositive(entity.getId(), "entity.getId()");
        } catch (IllegalArgumentException e) {
            Helper.logError(getLog(), MessageFormat.format(Helper.ILLEGAL_ARGUMENT, className,
                    methodName), e);
            throw e;
        }

        // update audit fields in the entity
        updateAuditTimestamp(entity, false);

        try {
            // retrieve hibernate session
            Session session = getSession();

            // check if entity exist
            if (session.get(this.entityBeanType, entity.getId()) == null) {
                throw new EntityNotFoundException(MessageFormat.format(
                        "Entity {0} with id {1} does not exists in persistence.",
                        entityBeanType.getSimpleName(), entity.getId()), entityBeanType.getSimpleName(),
                        entity.getId());
            }

            // update the entity in persistence
            session.update(entity);

            // log method exit
            Helper.logMethodExited(getLog(), className, methodName, executionStart);
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException("Exception occurred when updating entity.", e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, className, methodName), exc);

            throw exc;
        } catch (CopilotDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, className, methodName), e);

            throw e;
        }
    }

    /**
     * <p>Deletes an entity with the given ID from the persistence.</p>
     *
     * @param entityId the ID of the entity to be deleted
     *
     * @throws IllegalArgumentException if entityId <= 0
     * @throws EntityNotFoundException  if entity with the given ID doesn't exist in the persistence
     * @throws CopilotDAOException      if some other error occurred
     */
    @Transactional
    public void delete(long entityId) throws CopilotDAOException {
        final String methodName = "delete";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), className, methodName, new String[]{"entityId"}, new Object[]{entityId});

        Helper.checkIsPositive(getLog(), entityId, "entityId", className, methodName);

        try {
            // executes the query and retrieves number of deleted rows
            int deleteNum = getSession()
                    .createQuery(MessageFormat.format(DELETE_ENTITY, entityBeanType.getSimpleName()))
                    .setParameter("id", entityId)
                    .executeUpdate();

            // if no rows were deleted throws exception
            if (deleteNum == 0) {
                throw new EntityNotFoundException(MessageFormat.format(
                        "Entity {0} with id {1} does not exists in persistence.",
                        entityBeanType.getSimpleName(), entityId), entityBeanType.getSimpleName(), entityId);
            }

            // log method exit
            Helper.logMethodExited(getLog(), className, methodName, executionStart);
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException(
                    MessageFormat.format("Error occurred when deleting entity {0} with id {1}.",
                            entityBeanType.getSimpleName(), entityId), e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, className, methodName), exc);

            throw exc;
        } catch (CopilotDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, className, methodName), e);

            throw e;
        }
    }

    /**
     * <p>Retrieves an entity from persistence by its ID. Returns null if entity with the given ID is not found.</p>
     *
     * @param entityId the ID of the entity to be retrieved
     *
     * @return the entity with the specified ID retrieved from the persistence or null if not found
     *
     * @throws IllegalArgumentException if entityId <= 0
     * @throws CopilotDAOException      if some other error occurred
     */
    @SuppressWarnings("unchecked")
    @Transactional
    public T retrieve(long entityId) throws CopilotDAOException {
        final String methodName = "retrieve";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), className, methodName, new String[]{"entityId"}, new Object[]{entityId});

        Helper.checkIsPositive(getLog(), entityId, "entityId", className, methodName);

        try {
            // retrieves entity from persistence
            T result = (T) getSession().get(entityBeanType, entityId);

            // log method exit
            Helper.logMethodExited(getLog(), className, methodName, executionStart, result);

            // returns the result
            return result;
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException(
                    MessageFormat.format("Error occurred when retrieving entity {0} with id {1}.",
                            entityBeanType.getSimpleName(), entityId), e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, className, methodName), exc);

            throw exc;
        } catch (CopilotDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, className, methodName), e);

            throw e;
        }
    }

    /**
     * <p>Retrieves all entities from the persistence. Returns an empty list if no entities are found.</p>
     *
     * @return the list of entities found in the persistence (not null, doesn't contain null)
     *
     * @throws CopilotDAOException if any error occurred
     */
    @Transactional
    public List<T> retrieveAll() throws CopilotDAOException {
        final String methodName = "retrieveAll";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), className, methodName);

        try {
            // retrieves all entities stored in persistence - simply delegates to base class method
            List<T> result = getAllEntities(entityBeanType);

            // log method exit
            Helper.logMethodExited(getLog(), className, methodName, executionStart, result);

            // returns result
            return result;
        } catch (CopilotDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, className, methodName), e);

            throw e;
        }
    }

    /**
     * <p>Updates the creation/modification timestamp property of the given entity with the current date/time.</p>
     *
     * @param entity the entity to be updated
     * @param create true if entity will be created in persistence, false - if entity will be updated
     *
     * @throws IllegalArgumentException if entity is null
     */
    protected void updateAuditTimestamp(IdentifiableEntity entity, boolean create) {
        Helper.checkIsNotNull(entity, "entity");

        Date currentDate = new Date();

        // updates create or modify date depending on the create value
        if (create) {
            entity.setCreateDate(currentDate);
        } else {
            entity.setModifyDate(currentDate);
        }
    }
}
