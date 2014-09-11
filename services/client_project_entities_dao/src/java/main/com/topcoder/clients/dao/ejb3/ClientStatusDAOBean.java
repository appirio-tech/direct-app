/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.topcoder.clients.dao.ClientStatusDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;

/**
 * <p>
 * This class is a Stateless Session Bean realization of the ClientStatusDAO
 * business interface.
 * </p>
 * <p>
 * This class has a default no-arg constructor.
 * </p>
 * <p>
 * This class implements the method available for the ClientStatusDAO business
 * interface: get the clients with the corresponding status.
 * </p>
 * <p>
 * See base class for other available operations.
 * </p>
 * <p>
 * It uses the EntityManager configured in the base class to perform the needed
 * operations, retrieve the EntityManager using it's corresponding getter.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> This class is technically mutable since the
 * inherited configuration properties (with {@link PersistenceContext} ) are set
 * after construction, but the container will not initialize the properties more
 * than once for the session beans and the EJB3 container ensure the thread
 * safety in this case.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
@Local(ClientStatusDAOLocal.class)
@Remote(ClientStatusDAORemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ClientStatusDAOBean extends GenericEJB3DAO<ClientStatus, Long>
        implements ClientStatusDAO, ClientStatusDAOLocal, ClientStatusDAORemote {
    /**
     * The query string used in method getClientsWithStatus.
     */
    private static final String QUERYSTRING = "select c from Client c"
            + " where c.clientStatus = :status and c.deleted = false";

    /**
     * Default no-arg constructor. Constructs a new 'ClientStatusDAOBean'
     * instance.
     */
    public ClientStatusDAOBean() {
    }

    /**
     * Performs the retrieval of the list with clients with the given status
     * from the persistence. If nothing is found, return an empty list. Return
     * only the entities that are not marked as deleted.
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
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<Client> getClientsWithStatus(ClientStatus status)
        throws EntityNotFoundException, DAOException {
        Helper.checkNull(status, "status");
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {
            retrieveById(status.getId());
            return Helper.getEntities("status", status, entityManager, QUERYSTRING);
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
                    "Failed to get Clients with Status.");
        }
    }
}
