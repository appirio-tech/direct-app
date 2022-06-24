/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.stresstests;

import com.cronos.onlinereview.external.ConfigException;
import com.cronos.onlinereview.external.ExternalProject;
import com.cronos.onlinereview.external.ProjectRetrieval;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.impl.ExternalProjectImpl;
import com.topcoder.db.connectionfactory.DBConnectionFactory;

/**
 * <p>
 * This is a mock implementation of {@link ProjectRetrieval}.
 * </p>
 * 
 * @author stylecheck
 * @version 1.1
 * @since 1.0
 */
public class MockProjectRetrieval implements ProjectRetrieval {
    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param connFactory
     *            the connection factory to use with this object.
     * @param connName
     *            the connection name to use when creating connections.
     * @param forumType
     *            the forum type code to use in the retrieve queries.
     * @throws IllegalArgumentException
     *             if connFactory or connName is <code>null</code> or if forumType is negative or connName is
     *             empty after trimmed.
     * @throws ConfigException
     *             if the connection name doesn't correspond to a connection the factory knows about.
     */
    public MockProjectRetrieval(DBConnectionFactory connFactory, String connName, int forumType)
            throws ConfigException {
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @throws IllegalArgumentException
     *             if the parameter is <code>null</code> or empty after trim.
     * @throws ConfigException
     *             if the namespace could not be found, or if the connection factory could not be instantiated with
     *             the given namespace, or if the connection name is unknown to the connection factory, or if the
     *             "forumType" property was not positive.
     */
    public MockProjectRetrieval() throws ConfigException {
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param id
     *            the id of the project we are interested in.
     * @return the external project which has the given id, or null if not found.
     * @throws IllegalArgumentException
     *             if id is not positive.
     * @throws RetrievalException
     *             if any exception occurred during processing; it will wrap the underlying exception.
     */
    public ExternalProject retrieveProject(long id) throws RetrievalException {
        ExternalProjectImpl externalProject = new ExternalProjectImpl(1, 1, "1");
        return (ExternalProject) externalProject;
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param name
     *            the name of the project we are interested in.
     * @param version
     *            the version of the project we are interested in.
     * @throws IllegalArgumentException
     *             if either argument is <code>null</code> or empty after trim.
     * @return external projects which have the given name and version text.
     * @throws RetrievalException
     *             if any exception occurred during processing; it will wrap the underlying exception.
     */
    public ExternalProject[] retrieveProject(String name, String version) throws RetrievalException {
        // Delegates to retrieveProjects(String[], String[]).
        return retrieveProjects(new String[] {name }, new String[] {version });
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param ids
     *            the ids of the projects we are interested in.
     * @return an array of external projects who have the given ids. If none of the given ids were found, an empty
     *         array will be returned. The index of the entries in the array will not necessarily directly
     *         correspond to the entries in the ids array.
     * @throws IllegalArgumentException
     *             if ids is <code>null</code> or any entry is not positive.
     * @throws RetrievalException
     *             if any exception occurred during processing; it will wrap the underlying exception.
     */
    public ExternalProject[] retrieveProjects(long[] ids) throws RetrievalException {
        // Delegates to retrieveProjects(String, Object, int).
        return retrieveProjects("", ids, ids.length);
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param names
     *            the names of the projects we are interested in; each entry corresponds to the same indexed entry
     *            in the versions array.
     * @param versions
     *            the versions of the projects we are interested in; each entry corresponds to the same indexed
     *            entry in the names array.
     * @return an array of external projects who have the given names and versions. If none were found, an empty
     *         array will be returned. The index of the entries in the array will not necessarily directly
     *         correspond to the entries in the input array.
     * @throws IllegalArgumentException
     *             if either array is <code>null</code> or any entry in either array is null or empty after trim,
     *             or the sizes of these two array are not the same.
     * @throws RetrievalException
     *             if any exception occurred during processing; it will wrap the underlying exception.
     */
    public ExternalProject[] retrieveProjects(String[] names, String[] versions) throws RetrievalException {
        return retrieveProjects("", "", names.length);
    }

    /**
     * <p>
     * Mock Implementation.
     * </p>
     * 
     * @param queryAndClause
     *            the query of the prepareStatement, given be the caller.
     * @param queryParameter
     *            the parameter of the query, it can be long[] or String[], due to the different caller.
     * @param paramLength
     *            the length of the queryParameter array.
     * @return an array of external projects who have the given names and versions. If none were found, an empty
     *         array will be returned. The index of the entries in the array will not necessarily directly
     *         correspond to the entries in the input array.
     * @throws RetrievalException
     *             if any exception occurred during processing; it will wrap the underlying exception.
     */
    private ExternalProject[] retrieveProjects(String queryAndClause, Object queryParameter, int paramLength)
            throws RetrievalException {
        return null;
    }

}
