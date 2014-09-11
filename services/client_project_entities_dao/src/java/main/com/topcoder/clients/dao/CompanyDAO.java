/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao;

import java.util.List;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;

/**
 * <p>
 * This interface represents the CompanyDAO business interface.
 * </p>
 * <p>
 * This interface defines the specific methods available for the CompanyDAO business interface: retrieve clients for
 * company and retrieve projects for company.
 * </p>
 * <p>
 * See base interface for other available operations.
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> Implementations of this interface should be thread safe.
 * </p>
 *
 * @author Mafy, TCSDEVELOPER
 * @version 1.0
 */
public interface CompanyDAO extends GenericDAO<Company, Long> {
    /**
     * <p>
     * This static final String field represents the 'BEAN_NAME' property of the CompanyDAO business interface.
     * Represents the EJB session bean name.
     * </p>
     * <p>
     * It is initialized to a default value: "CompanyDAOBean" String during runtime.
     * </p>
     */
    public static final String BEAN_NAME = "CompanyDAOBean";

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with clients for the given company from the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's clients. Should not be null.
     * @return the list of Clients for the given company found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws IllegalArgumentException
     *             if company is null.
     * @throws EntityNotFoundException
     *             if company is not found in the persistence.
     * @throws DAOException
     *             if any error occurs while performing this operation.
     */
    public List<Client> getClientsForCompany(Company company) throws DAOException;

    /**
     * <p>
     * Defines the operation that performs the retrieval of the list with projects for the given company from the
     * persistence. If nothing is found, return an empty list.
     * </p>
     *
     * @param company
     *            the given company to retrieve it's projects. Should not be null.
     * @return the list of Projects for the given company found in the persistence. If nothing is found, return an empty
     *         list.
     * @throws IllegalArgumentException
     *             if company is null.
     * @throws EntityNotFoundException
     *             if company is not found in the persistence.
     * @throws DAOException
     *             if any error occurs while performing this operation.
     */
    public List<Project> getProjectsForCompany(Company company) throws DAOException;
}
