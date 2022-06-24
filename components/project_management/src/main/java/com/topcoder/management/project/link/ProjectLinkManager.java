/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.link;

import com.topcoder.management.project.PersistenceException;

/**
 * <p>
 * Project link manager. It handles persistence operations for project link as well as project link type. It currently
 * relies on the project manager to get <code>Project</code> entities.
 * </p>
 * <p>
 * It is created for "OR Project Linking" assembly.
 * </p>
 *
 * @author BeBetter, isv
 * @version 1.1
 */
public interface ProjectLinkManager {
    /**
     * <p>
     * Gets all project link types.
     * </p>
     *
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    public ProjectLinkType[] getAllProjectLinkTypes() throws PersistenceException;

    /**
     * <p>
     * Gets all project links based on source project id.
     * </p>
     *
     * @param sourceProjectId source project id
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    public ProjectLink[] getDestProjectLinks(long sourceProjectId) throws PersistenceException;

    /**
     * <p>
     * Gets all project links based on destination project id.
     * </p>
     *
     * @param destProjectId destination project id
     * @return all project link types
     * @throws PersistenceException if any persistence error occurs
     */
    public ProjectLink[] getSourceProjectLinks(long destProjectId) throws PersistenceException;

    /**
     * <p>
     * Updates project links for given source project id. It will delete all old links and use passed in project
     * links. There are 2 arrays passed in, one is for destination project ids and other for link type ids. The id at
     * the same position in each array represents a project link information.
     * </p>
     *
     * @param sourceProjectId the source project id
     * @param destProjectIds the destination project ids
     * @param linkTypeIds the type ids
     * @throws IllegalArgumentException if any array is null or it is not equal in length for dest project id array
     *             and link type array
     * @throws PersistenceException if any persistence error occurs.
     * @throws ProjectLinkCycleException if there is a cycle detected in the project links.
     */
    public void updateProjectLinks(long sourceProjectId, long[] destProjectIds, long[] linkTypeIds)
        throws PersistenceException;

    /**
     * <p>
     * Add a new project link
     * </p>
     *
     * @param sourceProjectId the source project id
     * @param destProjectId the destination project id
     * @param linkTypeId the type id
     * @throws PersistenceException if any persistence error occurs
     */
    public void addProjectLink(long sourceProjectId, long destProjectId, long linkTypeId)
        throws PersistenceException;

        /**
     * <p>
     * Remove a new project link
     * </p>
     *
     * @param sourceProjectId the source project id
     * @param destProjectId the destination project id
     * @param linkTypeId the type id
     * @throws PersistenceException if any persistence error occurs
     */
    public void removeProjectLink(long sourceProjectId, long destProjectId, long linkTypeId)
        throws PersistenceException;

    
    /**
     * <p>Checks if specified project is a part of cycle in project dependencies.</p>
     *
     * @param sourceProjectId a <code>long</code> providing the ID of a project.
     * @throws PersistenceException if any persistence error occurs.
     * @throws ProjectLinkCycleException if there is a cycle detected in the project links.
     * @since 1.1
     */
    public void checkForCycle(long sourceProjectId) throws PersistenceException;
}
