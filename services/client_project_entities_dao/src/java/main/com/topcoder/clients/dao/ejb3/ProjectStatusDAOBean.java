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

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.ProjectStatusDAO;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;

/**
 * <p>
 * This class is a Stateless Session Bean realization of the ProjectStatusDAO
 * business interface.
 * </p>
 * <p>
 * This class has a default no-arg constructor.
 * </p>
 * <p>
 * This class implements the method available for the ProjectStatusDAO business
 * interface: get the projects with the corresponding status.
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
 * @version 1.0
 */
@Local(ProjectStatusDAOLocal.class)
@Remote(ProjectStatusDAORemote.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ProjectStatusDAOBean extends GenericEJB3DAO<ProjectStatus, Long>
        implements ProjectStatusDAO, ProjectStatusDAOLocal,
        ProjectStatusDAORemote {
    /**
     * The sql string for method getProjectsWithStatus.
     */
    private static final String QUERY = "select p from Project p"
            + " where p.projectStatus = :status and (p.deleted is null or p.deleted = false)";

    /**
     * Default no-arg constructor. Constructs a new 'ProjectStatusDAOBean'
     * instance.
     */
    public ProjectStatusDAOBean() {
    }

    /**
     * Performs the retrieval of the list with projects with the given status
     * from the persistence. If nothing is found, return an empty list. Return
     * only the entities that are not marked as deleted.
     *
     * @param status
     *                the given project status to retrieve it's projects. Should
     *                not be null.
     * @return the list of Projects for the given project status found in the
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
    public List<Project> getProjectsWithStatus(ProjectStatus status)
        throws EntityNotFoundException, DAOException {
        Helper.checkNull(status, "status");
        EntityManager entityManager = Helper
                .checkEntityManager(getEntityManager());

        try {
            retrieveById(status.getId());
            return Helper.getEntities("status", status, entityManager, QUERY);
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
                    "Failed to get get projects with status.");
        }
    }
}
