/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot;

import java.util.List;

import com.topcoder.direct.services.copilot.model.IdentifiableEntity;

/**
 * <p>
 * This interface represents a generic service for managing entities in persistence. It defines methods for creating,
 * updating, deleting and retrieving entities to/from persistence.
 * </p>
 * <p>
 * <b>Thread Safety:</b> Implementations of this interface must be thread safe when entities passed to them are used
 * by the caller in thread safe manner.
 * </p>
 *
 * @param <T> the type of the entity to be managed by this service.
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public interface GenericService<T extends IdentifiableEntity> {
    /**
     * Creates the given entity in the persistence.
     *
     * @param entity the entity to be created in the persistence
     * @return the generated entity ID
     * @throws IllegalArgumentException if entity is null
     * @throws CopilotServiceException if some other error occurred
     */
    public long create(T entity) throws CopilotServiceException;

    /**
     * Updates the given entity in the persistence.
     *
     * @param entity the entity to be updated in the persistence
     * @throws IllegalArgumentException if entity is null or entity.getId() <= 0
     * @throws CopilotServiceEntityNotFoundException if entity with the same ID cannot be found in the persistence
     * @throws CopilotServiceException if some other error occurred
     */
    public void update(T entity) throws CopilotServiceException;

    /**
     * Deletes an entity with the given ID from the persistence.
     *
     * @param entityId the ID of the entity to be deleted
     * @throws IllegalArgumentException if entityId <= 0
     * @throws CopilotServiceEntityNotFoundException if entity with the given ID doesn't exist in the persistence
     * @throws CopilotServiceException if some other error occurred
     */
    public void delete(long entityId) throws CopilotServiceException;

    /**
     * Retrieves an entity from persistence by its ID. Returns null if entity with the given ID is not found.
     *
     * @param entityId the ID of the entity to be retrieved
     * @return the entity with the specified ID retrieved from the persistence or null if not found
     * @throws IllegalArgumentException if entityId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public T retrieve(long entityId) throws CopilotServiceException;

    /**
     * Retrieves all entities from the persistence. Returns an empty list if no entities are found.
     *
     * @return the list of entities found in the persistence (not null, doesn't contain null)
     * @throws CopilotServiceException if any error occurred
     */
    public List<T> retrieveAll() throws CopilotServiceException;
}
