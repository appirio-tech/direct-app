/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.io.Serializable;
import java.util.List;

import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.search.builder.filter.Filter;

/**
 * <p>
 * This interface represents the base GenericDAO business interface.
 * </p>
 * <p>
 * This interface defines the generic methods available for all the DAOs:
 * retrieve entity by id, retrieve all entities, search entities by name, search
 * entities using the given filter, save an given entity, delete an given
 * entity.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> Implementations of this interface should be
 * thread safe.
 * </p>
 *
 * @param <T> The entity type to operate
 * @param <Id> The type of id of entity
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
public interface GenericDAO<T extends AuditableEntity, Id extends Serializable> {
    /**
     * <p>
     * Defines the operation that performs the retrieval of an entity using the
     * given id from the persistence.
     * </p>
     *
     * @param id
     *                the identifier of the entity that should be retrieved.
     *                Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundException
     *                 if an entity for the given id is not found in the
     *                 persistence.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public T retrieveById(Id id) throws EntityNotFoundException, DAOException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities from
     * the persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @return the list of entities found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<T> retrieveAll() throws DAOException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * have the given name in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<T> searchByName(String name) throws DAOException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of all entities that
     * match the given filter in the persistence. If nothing is found, return an
     * empty list.
     * </p>
     *
     * @param filter
     *                the filter that should be used to search the matched
     *                entities. Should not be null.
     * @return the list with entities that match the given filter retrieved from
     *         the persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if filter is null.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<T> search(Filter filter) throws DAOException;

    /**
     * <p>
     * Defines the operation that performs the salvation of an entity in the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public T save(T entity) throws DAOException;

    /**
     * <p>
     * Defines the operation that performs the deletion of an entity from the
     * persistence.
     * </p>
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundException
     *                 if entity is not found in the persistence.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public void delete(T entity) throws EntityNotFoundException, DAOException;
}
