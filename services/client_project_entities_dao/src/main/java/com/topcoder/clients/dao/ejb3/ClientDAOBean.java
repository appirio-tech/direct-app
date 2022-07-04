/*
 * Copyright (C) 2008 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.topcoder.clients.dao.ClientDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;

/**
 * <p>
 * This class is a Stateless Session Bean realization of the ClientDAO business
 * interface.
 * </p>
 * <p>
 * This class has a default no-arg constructor.
 * </p>
 * <p>
 * This class implements the method available for the ClientDAO business
 * interface: get the projects for the corresponding client.
 * </p>
 * <p>
 * See base class for other available operations.
 * </p>
 * <p>
 * It uses the EntityManager configured in the base class to perform the needed
 * operations, retrieve the EntityManager using it's corresponding getter.
 * </p>
 * <p>
 *
 * <p>
 * Version 1.1 (Module Assembly - TC Cockpit Direct Project Related Services Update and Integration) changes
 * <ul>
 *     <li>Add method {@link #getClientForProject(long)}</li>
 *     <li>Add method {@link #getProjectsForClient(long)}</li>
 * </ul>
 * </p>
 *
 * <strong>THREAD SAFETY:</strong> This class is technically mutable since the
 * inherited configuration properties (with {@link PersistenceContext} ) are set
 * after construction, but the container will not initialize the properties more
 * than once for the session beans and the EJB3 container ensure the thread
 * safety in this case.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.1
 */
@Local(ClientDAOLocal.class)
@Remote(ClientDAORemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ClientDAOBean extends GenericEJB3DAO<Client, Long> implements
        ClientDAO, ClientDAOLocal, ClientDAORemote {

    /**
     * The query string gets the billing account via the client id.
     *
     * @since 1.1
     */
    private static final String QUERYSTRING_CLIENTID = "select p from Project p, ClientProject cp "
                + "where cp.clientId = :clientId and p.id = cp.projectId and (p.deleted is null or p.deleted = false)";

    /**
     * The query string gets the client via the billing account id.
     *
     * @since 1.1
     */
    private static final String QUERY_CLIENT_FOR_PROJECT = "select c from Client c, ClientProject cp, "
     +  "Project p where p.id = cp.projectId and c.id = cp.clientId and (p.deleted is null or p.deleted = false) and p.id = :billingAccountId";

    /**
     * Default no-arg constructor. Constructs a new 'ClientDAOBean' instance.
     */
    public ClientDAOBean() {
    }

    /**
     * <p>
     * Performs the retrieval of the list with projects with the given client
     * from the persistence. If nothing is found, return an empty list. Return
     * only the entities that are not marked as deleted.
     * </p>
     *
     * @param client
     *                the given clients to retrieve it's projects. Should not be
     *                null.
     * @return the list of Projects for the given client found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if client is null.
     * @throws EntityNotFoundException
     *                 if client is not found in the persistence.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<Project> getProjectsForClient(Client client)
        throws DAOException {
        Helper.checkNull(client, "client");

        try {
            retrieveById(client.getId());
            return getProjectsForClient(client.getId());
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
                    "Failed to get project for client.");
        }
    }


    /**
     * Gets the Client by the specified project id (the billing account id)
     *
     * @param projectId the billing account id
     * @return the client instance.
     * @throws DAOException if there is any error.
     * @since 1.1
     */
    public Client getClientForProject(long projectId) throws DAOException {

        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {
            Query query = entityManager.createQuery(QUERY_CLIENT_FOR_PROJECT);
            query.setParameter("billingAccountId", projectId);

            Client c = (Client) query.getSingleResult();

            return c;

        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
                    "Failed to get client for project.");
        }
    }

    /**
     * Gets all the billing accounts belong to the give client id.
     *
     *
     * @param clientId the client id.
     * @return a list of <code>Project</code> representing the billing account.
     * @throws DAOException if there is any error.
     * @since 1.1
     */
    public List<Project> getProjectsForClient(long clientId) throws DAOException {
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {
            Query query = entityManager.createQuery(QUERYSTRING_CLIENTID);
            query.setParameter("clientId", clientId);

            List list = query.getResultList();

            List<Project> result = new ArrayList<Project>();

            for (Object o : list) {
                result.add((Project) o);
            }

            return result;

        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
                    "Failed to get projects for client id.");
        }
    }
}
