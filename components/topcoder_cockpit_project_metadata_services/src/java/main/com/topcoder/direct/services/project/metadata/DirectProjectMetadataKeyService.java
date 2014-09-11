/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata;

import java.util.List;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;

/**
 * <p>
 * This interface is the service for managing the direct project metadata keys. It is used to create, update, delete
 * and retrieve metadata key by id. Before storing in the database, project metadata key is validated for the proper
 * conditions. Audit is performed when creating, updating or deleting records.
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The implementations are required to be thread safe.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
public interface DirectProjectMetadataKeyService {
    /**
     * Creates project metadata key and returns the generated id for the entity.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataKey
     *            the project metadata key to create.
     *
     * @return the generated id for created entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadataKey is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long createProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long userId)
        throws ValidationException, PersistenceException;

    /**
     * Updates project metadata key.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataKey
     *            the project metadata key to update.
     *
     * @throws IllegalArgumentException
     *             if projectMetadataKey is null.
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void updateProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long userId)
        throws EntityNotFoundException, ValidationException, PersistenceException;

    /**
     * Creates or updates project metadata key.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataKey
     *            the project metadata key to create or update.
     *
     * @return the id of the entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadataKey is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long saveProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long userId)
        throws ValidationException, PersistenceException;

    /**
     * Deletes project metadata key.
     *
     * @param projectMetadataKeyId
     *            the project metadata key id to delete.
     * @param userId
     *            the id of the user performed the operation.
     *
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void deleteProjectMetadataKey(long projectMetadataKeyId, long userId) throws EntityNotFoundException,
        PersistenceException;

    /**
     * Gets project metadata key.
     *
     * @param projectMetadataKeyId
     *            the project metadata key id to get.
     *
     * @return the ProjectMetadataKey for the id or null if the entity is not found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public DirectProjectMetadataKey getProjectMetadataKey(long projectMetadataKeyId) throws PersistenceException;

    /**
     * Gets common project metadata keys, whose client id is set to null.
     *
     * @return the List of project metadata keys with client id set to null or empty list, if no entity was found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public List<DirectProjectMetadataKey> getCommonProjectMetadataKeys() throws PersistenceException;

    /**
     * Gets specific project metadata keys by client id and optional grouping filter parameter.
     *
     * @param grouping
     *            the grouping filter parameter.
     * @param clientId
     *            the client id to search project metadata keys.
     *
     * @return the List of project metadata keys for client set to null or empty list, if no entity was found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public List<DirectProjectMetadataKey> getClientProjectMetadataKeys(long clientId, Boolean grouping)
        throws PersistenceException;
}
