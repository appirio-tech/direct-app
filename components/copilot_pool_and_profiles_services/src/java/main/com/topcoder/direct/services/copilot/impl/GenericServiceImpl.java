/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.transaction.annotation.Transactional;

import com.topcoder.direct.services.copilot.CopilotServiceEntityNotFoundException;
import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.GenericService;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.EntityNotFoundException;
import com.topcoder.direct.services.copilot.dao.GenericDAO;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;

/**
 * <p>
 * This class is an implementation of GenericService that simply uses a pluggable GenericDAO instance to manage
 * entities in persistence. It's assumed that this class will be initialized using Spring IoC. This class uses
 * Logging Wrapper logger to log errors and debug information.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class has mutable attributes, thus it's not thread safe. But it's assumed that it will
 * be initialized via Spring IoC before calling any business method, this way it's always used in thread safe manner.
 * It uses thread safe GenericDAO and Log instances. This class uses Spring declarative transactions, thus all
 * methods that can modify data in persistence have @Transactional annotation.
 * </p>
 *
 * @param <T> the type of the entity to be managed by this service
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class GenericServiceImpl<T extends IdentifiableEntity> extends LoggingEnabledService implements
    GenericService<T> {
    /**
     * The DAO used for accessing entities of type in persistence. Cannot be null after initialization
     * (validation is performed in checkInit() method). Initialized by Spring setter injection. Has protected getter
     * and public setter. Is used in checkInit(), create(), update(), delete(), retrieve() and retrieveAll().
     */
    private GenericDAO<T> genericDAO;

    /**
     * Creates an instance of GenericServiceImpl.
     */
    public GenericServiceImpl() {
        // Do nothing
    }

    /**
     * Checks whether this class was initialized by Spring properly.
     *
     * @throws CopilotServiceInitializationException if the class was not initialized properly (e.g. when required
     *             properly is not specified or property value has invalid format)
     */
    @PostConstruct
    protected void checkInit() {
        super.checkInit();
        Helper.checkNullForInjectedValue(genericDAO, "genericDAO");
    }

    /**
     * Creates the given entity in the persistence.
     *
     * @param entity the entity to be created in the persistence
     * @return the generated entity ID
     * @throws IllegalArgumentException if entity is null
     * @throws CopilotServiceException if some other error occurred
     */
    @Transactional
    public long create(T entity) throws CopilotServiceException {
        final String method = this.getClass().getSimpleName() + "#create";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        Helper.logInputArguments(getLog(), new String[] {"entity"}, new Object[] {entity});
        try {
            long id = genericDAO.create(entity);
            Helper.logReturnValue(getLog(), id);
            Helper.logMethodExit(method, getLog(), date);
            return id;
        } catch (CopilotDAOException e) {
            throw Helper
                .logError(new CopilotServiceException("Error occurred when executing genericDAO#create.", e),
                    method, getLog());
        }
    }

    /**
     * Updates the given entity in the persistence.
     *
     * @param entity the entity to be updated in the persistence
     * @throws IllegalArgumentException if entity is null or entity.getId() <= 0
     * @throws CopilotServiceEntityNotFoundException if entity with the same ID cannot be found in the persistence
     * @throws CopilotServiceException if some other error occurred
     */
    @Transactional
    public void update(T entity) throws CopilotServiceException {
        final String method = this.getClass().getSimpleName() + "#update";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        Helper.logInputArguments(getLog(), new String[] {"entity"}, new Object[] {entity});
        Helper.checkNullWithLogging(entity, "entity", method, getLog());
        try {
            genericDAO.update(entity);
            Helper.logMethodExit(method, getLog(), date);
        } catch (EntityNotFoundException e) {
            CopilotServiceEntityNotFoundException exception =
                new CopilotServiceEntityNotFoundException("Entity doesn't exist in the persistence.", e, e
                    .getEntityTypeName(), e.getEntityId());
            throw Helper.logError(exception, method, getLog());
        } catch (CopilotDAOException e) {
            throw Helper
                .logError(new CopilotServiceException("Error occurred when executing genericDAO#update.", e),
                    method, getLog());
        }
    }

    /**
     * Deletes an entity with the given ID from the persistence.
     *
     * @param entityId the ID of the entity to be deleted
     * @throws IllegalArgumentException if entityId <= 0
     * @throws CopilotServiceEntityNotFoundException if entity with the given ID doesn't exist in the persistence
     * @throws CopilotServiceException if some other error occurred
     */
    @Transactional
    public void delete(long entityId) throws CopilotServiceException {
        final String method = this.getClass().getSimpleName() + "#delete";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        Helper.logInputArguments(getLog(), new String[] {"entityId"}, new Object[] {entityId});
        try {
            genericDAO.delete(entityId);
            Helper.logMethodExit(method, getLog(), date);
        } catch (EntityNotFoundException e) {
            CopilotServiceEntityNotFoundException exception =
                new CopilotServiceEntityNotFoundException("Entity doesn't exist in the persistence.", e, e
                    .getEntityTypeName(), e.getEntityId());
            throw Helper.logError(exception, method, getLog());
        } catch (CopilotDAOException e) {
            throw Helper
                .logError(new CopilotServiceException("Error occurred when executing genericDAO#delete.", e),
                    method, getLog());
        }
    }

    /**
     * Retrieves an entity from persistence by its ID. Returns null if entity with the given ID is not found.
     *
     * @param entityId the ID of the entity to be retrieved
     * @return the entity with the specified ID retrieved from the persistence or null if not found
     * @throws IllegalArgumentException if entityId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public T retrieve(long entityId) throws CopilotServiceException {
        final String method = this.getClass().getSimpleName() + "#retrieve";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        Helper.logInputArguments(getLog(), new String[] {"entityId"}, new Object[] {entityId});
        try {
            T returnValue = genericDAO.retrieve(entityId);
            Helper.logReturnValue(getLog(), returnValue);
            Helper.logMethodExit(method, getLog(), date);
            return returnValue;
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException("Error occurred when executing genericDAO#retrieve.",
                e), method, getLog());
        }
    }

    /**
     * Retrieves all entities from the persistence. Returns an empty list if no entities are found.
     *
     * @return the list of entities found in the persistence (not null, doesn't contain null)
     * @throws CopilotServiceException if any error occurred
     */
    public List<T> retrieveAll() throws CopilotServiceException {
        final String method = this.getClass().getSimpleName() + "#retrieveAll";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        try {
            List<T> returnValue = genericDAO.retrieveAll();
            Helper.logReturnValue(getLog(), returnValue);
            Helper.logMethodExit(method, getLog(), date);
            return returnValue;
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException(
                "Error occurred when executing genericDAO#retrieveAll.", e), method, getLog());
        }
    }

    /**
     * Retrieves the DAO used for accessing entities of type <T> in persistence.
     *
     * @return the DAO used for accessing entities of type <T> in persistence
     */
    protected GenericDAO<T> getGenericDAO() {
        return genericDAO;
    }

    /**
     * Sets the DAO used for accessing entities of type <T> in persistence.
     *
     * @param genericDAO the DAO used for accessing entities of type <T> in persistence
     */
    public void setGenericDAO(GenericDAO<T> genericDAO) {
        this.genericDAO = genericDAO;
    }
}
