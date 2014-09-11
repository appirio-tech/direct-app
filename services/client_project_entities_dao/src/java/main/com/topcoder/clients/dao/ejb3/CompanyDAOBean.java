/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
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

import com.topcoder.clients.dao.CompanyDAO;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;

/**
 * <p>
 * This class is a Stateless Session Bean realization of the CompanyDAO business
 * interface.
 * </p>
 * <p>
 * This class has a default no-arg constructor.
 * </p>
 * <p>
 * This class implements the method available for the CompanyDAO business
 * interface: retrieve clients for company and retrieve projects for company.
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
 * inherited configuration properties (with {@link PersistenceContext}) are set
 * after construction, but the container will not initialize the properties more
 * than once for the session beans and the EJB3 container ensure the thread
 * safety in this case.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.1
 */
@Local(CompanyDAOLocal.class)
@Remote(CompanyDAORemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CompanyDAOBean extends GenericEJB3DAO<Company, Long> implements
        CompanyDAO, CompanyDAOLocal, CompanyDAORemote {
    /**
     * The query string for method getClientsForCompany.
     */
    private static final String QUERY_CLIENTS = "select c from Client c"
            + " where c.company = :company and (c.deleted is null or c.deleted = false)";

    /**
     * The query string for method getProjectsForCompany.
     */
    private static final String QUERY_PROJECTS = "select p from Project p"
            + " where p.company = :company and (p.deleted is null or p.deleted = false)";

    /**
     * Default no-arg constructor. Constructs a new 'CompanyDAOBean' instance.
     */
    public CompanyDAOBean() {
    }

    /**
     * Performs the retrieval of the list with clients for the given company
     * from the persistence. If nothing is found, return an empty list. Return
     * only the entities that are not marked as deleted.
     *
     *
     * @param company
     *                the given company to retrieve it's clients. Should not be
     *                null.
     * @return the list of Clients for the given company found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if company is null.
     * @throws EntityNotFoundException
     *                 if company is not found in the persistence.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<Client> getClientsForCompany(Company company)
        throws EntityNotFoundException, DAOException {
        Helper.checkNull(company, "company");
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {
            retrieveById(company.getId());
            return Helper.getEntities("company", company, entityManager, QUERY_CLIENTS);
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
                    "Failed to get clients for company.");
        }
    }

    /**
     * Performs the retrieval of the list with projects for the given company
     * from the persistence. If nothing is found, return an empty list. Return
     * only the entities that are not marked as deleted.
     *
     *
     * @param company
     *                the given company to retrieve it's projects. Should not be
     *                null.
     * @return the list of Projects for the given company found in the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if company is null.
     * @throws EntityNotFoundException
     *                 if company is not found in the persistence.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<Project> getProjectsForCompany(Company company)
        throws EntityNotFoundException, DAOException {
        Helper.checkNull(company, "company");
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {
            retrieveById(company.getId());
            return Helper.getEntities("company", company, entityManager, QUERY_PROJECTS);
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
                    "Failed to get clients for company.");
        }
    }
}
