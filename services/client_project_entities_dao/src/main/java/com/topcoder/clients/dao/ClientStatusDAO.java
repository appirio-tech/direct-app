/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;

/**
 * <p>
 * This interface represents the ClientStatusDAO business interface.
 * </p>
 * <p>
 * This interface defines the specific methods available for the ClientStatusDAO
 * business interface: get the clients with the corresponding status.
 * </p>
 * <p>
 * See base interface for other available operations.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> Implementations of this interface should be
 * thread safe.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
public interface ClientStatusDAO extends GenericDAO<ClientStatus, Long> {
    /**
     * <p>
     * This static final String field represents the 'BEAN_NAME' property of the
     * ClientStatusDAO business interface. Represents the EJB session bean name.
     * </p>
     * <p>
     * It is initialized to a default value: "ClientStatusDAOBean" String during
     * runtime.
     * </p>
     */
    public static final String BEAN_NAME = "ClientStatusDAOBean";

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with
     * clients with the given status from the persistence. If nothing is found,
     * return an empty list.
     * </p>
     *
     * @param status
     *                the given client status to retrieve it's clients. Should
     *                not be null.
     * @return the list of Clients for the given client status found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if status is null.
     * @throws EntityNotFoundException
     *                 if status is not found in the persistence.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<Client> getClientsWithStatus(ClientStatus status)
        throws EntityNotFoundException, DAOException;
}
