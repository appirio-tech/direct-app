/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.asset.services;

import java.util.List;

import com.topcoder.asset.entities.User;
import com.topcoder.asset.exceptions.PersistenceException;
import com.topcoder.asset.exceptions.ServiceException;

/**
 * <p>
 * This interface represents a manager service. It defines methods for retrieving client managers, TopCoder managers
 * and copilots for a specific project.
 * </p>
 *
 * <p>
 * <strong>Thread safety:</strong> Implementations of this interface are required to be thread safe.
 * </p>
 *
 * @author LOY, sparemax
 * @version 1.0
 */
public interface ManagerService {
    /**
     * This method will retrieve client managers of a specific project.
     *
     * @param projectId
     *            the project id
     *
     * @return client managers of a specific project
     *
     * @throws IllegalArgumentException
     *             if projectId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public List<User> getClientManagers(long projectId) throws ServiceException;

    /**
     * This method will retrieve Topcoder managers of a specific project.
     *
     * @param projectId
     *            the project id
     *
     * @return topcoder managers of a specific project
     *
     * @throws IllegalArgumentException
     *             if projectId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public List<User> getTopCoderManagers(long projectId) throws ServiceException;

    /**
     * This method will retrieve copilots of a specific project.
     *
     * @param projectId
     *            the project id
     *
     * @return copilots of a specific project
     *
     * @throws IllegalArgumentException
     *             if projectId is not positive.
     * @throws PersistenceException
     *             if some error occurred while accessing the persistence.
     * @throws ServiceException
     *             if any other error occurs.
     */
    public List<User> getCopilots(long projectId) throws ServiceException;
}
