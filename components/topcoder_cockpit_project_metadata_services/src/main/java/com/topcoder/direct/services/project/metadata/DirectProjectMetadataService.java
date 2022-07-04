/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata;

import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectAccess;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectMetadataDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This interface is the service for managing the direct project metadata. It is used to create, update, delete and
 * search the metadata based on the input parameters. Before storing in the database, project metadata is validated
 * for the proper conditions. Audit is performed when creating, updating or deleting records.
 * </p>
 *
 * <p>
 *     Version 1.1 changes:
 *     - Add method to batch saving a list of project metadata.
 *     - Add method to get project metadata by project id and metadata key id.
 * </p>
 *
 * <p>
 *     Version 1.2 (Release Assembly - TC Cockpit Report Filters Group By
 *      Metadata Feature and Coordination Improvement) changes:
 *     - Add method {@link #searchProjectIds(long, java.util.List)}.
 *     - Add method {@link #getProjectMetadataByKey(long)}.
 * </p>
 *
 * <p>
 *     Version 1.3 (Release Assembly - TC Cockpit All Projects Management Page Update) changes:
 *     <ol>
 *         <li>Add method {@link #getProjectMetadataByProjects(java.util.List)}</li>
 *     </ol>
 * </p>
 *
 * <p>
 *     Version 1.4 (Release Assembly - TopCoder Cockpit Navigation Update)
 *     <ul>
 *         <li>Added method {@link #getDirectProjectAccess(long, long, long)}</li>
 *         <li>Added method {@link #recordDirectProjectAccess(com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectAccess, long)}</li>
 *     </ul>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The implementations are required to be thread safe.
 * </p>
 *
 * @author faeton, sparemax, GreatKevin, Blues, GreatKevin
 * @version 1.4
 */
public interface DirectProjectMetadataService {
    /**
     * Creates project metadata and returns the generated id for the entity.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to create.
     * @return the generated id for created entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long createProjectMetadata(DirectProjectMetadata projectMetadata, long userId) throws ValidationException,
        PersistenceException;

    /**
     * Updates project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to update.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void updateProjectMetadata(DirectProjectMetadata projectMetadata, long userId)
        throws EntityNotFoundException, ValidationException, PersistenceException;

    /**
     * Creates or updates project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to create or update.
     *
     * @return the id of the entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long saveProjectMetadata(DirectProjectMetadata projectMetadata, long userId) throws ValidationException,
        PersistenceException;

    /**
     * Batch save a list of project metadata.
     *
     * @param projectMetadataList a list of project metadata.
     * @param userId the id of the user.
     * @return a list of updated or created project metadata IDs.
     * @throws ValidationException if entities fail the validation.
     * @throws PersistenceException if  any problem with persistence occurs.
     * @since 1.1
     */
    public long[] saveProjectMetadata(List<DirectProjectMetadata> projectMetadataList, long userId) throws ValidationException,
                PersistenceException;

    /**
     * Deletes project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataId
     *            the project metadata id to delete.
     *
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void deleteProjectMetadata(long projectMetadataId, long userId) throws EntityNotFoundException,
        PersistenceException;

    /**
     * Gets project metadata.
     *
     * @param projectMetadataId
     *            the project metadata id to get.
     *
     * @return the ProjectMetadata for the id or null if the entity is not found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public DirectProjectMetadata getProjectMetadata(long projectMetadataId) throws PersistenceException;

    /**
     * Gets project list of metadata by project id.
     *
     * @param tcDirectProjectId
     *            the topcoder direct project id to get project list of metadata.
     *
     * @return the List of ProjectMetadata entities for the id or empty list if no entity was found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public List<DirectProjectMetadata> getProjectMetadataByProject(long tcDirectProjectId)
        throws PersistenceException;


    /**
     * Gets all the project metadata of all the projects specified the list of project ids.
     *
     * @param tcDirectProjectIds the list of project ids.
     * @return the list project metadata.
     * @throws PersistenceException if any problem with persistence occurs.
     * @since 1.3
     */
    public List<DirectProjectMetadata> getProjectMetadataByProjects(List<Long> tcDirectProjectIds)
                throws PersistenceException;

    /**
     * Gets the project metadata by project id and project metadata key id.
     *
     * @param tcDirectProjectId the id of the direct project.
     * @param projectMetadataKey the id of the project metadata key.
     * @return a list of project metadata retrieved.
     * @throws PersistenceException if any problem with persistence occurs.
     * @since 1.1
     */
    public List<DirectProjectMetadata> getProjectMetadataByProjectAndKey(long tcDirectProjectId, long projectMetadataKey)
                throws PersistenceException;

    /**
     * Gets all the project metadata values by the specified project metadata key id.
     *
     * @param projectMetadataKeyId the id of the metadata id
     * @return the list of <code>DirectProjectMetadata</code>
     * @throws PersistenceException if any problem with persistence occurs
     * @since 1.2
     */
    public List<DirectProjectMetadata> getProjectMetadataByKey(long projectMetadataKeyId)
            throws PersistenceException;

    /**
     * Searches the project ids with the specified metadata key id and list of metadata values to search.
     *
     * @param projectMetadataKeyId the metadata key id used to search.
     * @param projectMetadataValues the list of metadata values used to search.
     * @return the project ids stored in a set.
     * @throws PersistenceException if any problem with persistence occurs.
     * @since 1.2
     */
    public Set<Long> searchProjectIds(long projectMetadataKeyId, List<String> projectMetadataValues)
            throws PersistenceException;

    /**
     * Searches the project metadata with metadata key id and the values set.
     *
     * @param projectMetadataKeyId  the metadata key id.
     * @param projectMetadataValues the metadata values set.
     * @return the map from project id to project metadata value.
     * @throws PersistenceException if any problem with persistence occurs.
     */
    public Map<Long, String> searchProjectIdsWithMetadataValues(long projectMetadataKeyId,
            List<String> projectMetadataValues) throws PersistenceException;

    /**
     * Adds list of project metadata to the given tc project.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataList
     *            the list of project metadata to add.
     * @param tcDirectProjectId
     *            the topcoder direct project id to add list of project metadata.
     *
     * @throws IllegalArgumentException
     *             if projectMetadataList is null or contains null elements.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void addProjectMetadata(long tcDirectProjectId, List<DirectProjectMetadataDTO> projectMetadataList,
        long userId) throws ValidationException, PersistenceException;

    /**
     * Adds project metadata to the given list of tc projects.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param tcDirectProjectIds
     *            the topcoder direct project ids to add project metadata.
     * @param projectMetadata
     *            the project metadata to add to projects.
     *
     * @throws IllegalArgumentException
     *             if tcDirectProjectIds or projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void addProjectMetadata(long[] tcDirectProjectIds, DirectProjectMetadataDTO projectMetadata, long userId)
        throws ValidationException, PersistenceException;

    /**
     * Searches the projects by the given search filter.
     *
     * @param filter
     *            the direct project filter to search projects.
     *
     * @return the List of projects for the filter or empty list of no entity was found.
     *
     * @throws IllegalArgumentException
     *             if filter is null.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public List<TcDirectProject> searchProjects(DirectProjectFilter filter) throws PersistenceException;

    /**
     * Gets the direct project access by the specified user id, access type id and access item id.
     *
     * @param userId the user id.
     * @param accessTypeId the access type id.
     * @param accessItemId the access item id.
     * @return the <code>DirectProjectAccess</code> is found, null if not
     * @throws PersistenceException if any error
     * @since 1.4
     */
    @SuppressWarnings("unchecked")
    public DirectProjectAccess getDirectProjectAccess(long userId, long accessTypeId, long accessItemId)
            throws PersistenceException;

    /**
     * Records a user access to the resource. If it's the first time a access, a new record is added, otherwise
     * the existing record is updated with new access time.
     *
     * @param access the <code>DirectProjectAccess</code> instance
     * @param userId the user id
     * @return the record id
     * @throws PersistenceException if any error.
     * @since 1.4
     */
    public long recordDirectProjectAccess(DirectProjectAccess access, long userId) throws PersistenceException;

}
