/*
 * Copyright (C) 2008 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.topcoder.service.project.Competition;
import com.topcoder.service.project.ConfigurationException;
import com.topcoder.service.project.PersistenceException;
import com.topcoder.service.project.Project;
import com.topcoder.service.project.ProjectNotFoundException;
import com.topcoder.service.project.ProjectPersistence;

/**
 * <p>
 * This class implements the <code>{@link ProjectPersistence}</code> contract and uses JPA to persist entities. As per
 * the requirement of the contract, we also provide a no-args constructor.
 * </p>
 * <p>
 * We store the entity manager factory as a member variable. This allows us to open and close entity managers during
 * each method call.
 * </p>
 *
 * <p>
 * Version 2.0 (Module Assembly - TC Cockpit Direct Project Related Services Update and Integration) changes:
*  <ul>
 *      <li>
 *          Update method {@link #updateProject(com.topcoder.service.project.Project)} to set the new properties
 *          added to the project data.
 *      </li>
*  </ul>
 * </p>
 *
 * <p>
 * <b>Thread Safety</b>: This class is thread safe as it is immutable.
 * </p>
 *
 * @author humblefool, FireIce, TCSDEVELOPER
 * @version 2.0
 */
public class JPAProjectPersistence implements ProjectPersistence {
    /**
     * <p>
     * Represents the factory used to create entity managers when required to persist entities.
     * </p>
     * <p>
     * It is frozen as it will not be changed after being set by the constructor. It will not be null. It is retrieved
     * by the {@link #getEntityManagerFactory()} method. It is used by all the methods of this class to perform
     * persistence tasks.
     * </p>
     */
    private final EntityManagerFactory entityManagerFactory;

    /**
     * <p>
     * Constructs an <code>JPAProjectPersistence</code> instance.
     * </p>
     *
     * @throws ConfigurationException
     *             if the required property is missing or there is an exception during the call to
     *             createEntityManagerFactory.
     */
    public JPAProjectPersistence() throws ConfigurationException {
        try {
            InitialContext initCtx = new InitialContext();
            String persistenceUnitName = (String) initCtx.lookup("java:comp/env/persistence_unit_name");

            if (null == persistenceUnitName || 0 == persistenceUnitName.trim().length()) {
                throw new ConfigurationException("The 'persistence_unit_name' property is null/empty.");
            }

            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        } catch (NamingException e) {
            throw new ConfigurationException("A naming problem occurs.", e);
        } catch (ClassCastException e) {
            throw new ConfigurationException("The 'persistence_unit_name' property is not String type.", e);
        } catch (Exception e) {
            // catch any exception thrown by Persistence.createEntityManagerFactory(String)
            throw new ConfigurationException("Fail to create the factory of entity manager.", e);
        }
    }

    /**
     * <p>
     * Creates the given project.
     * </p>
     *
     * @param project
     *            The project. Must not be null. The name must also not be null/empty. The ProjectId , if any, is
     *            ignored. CreateDate must not be null. The competitions set, if present, must not contain nulls or
     *            contain Competition elements whose project element is not the given arg.
     * @return The project as it was created, with the projectId set.
     * @throws PersistenceException
     *             If a generic persistence error.
     * @throws IllegalArgumentException
     *             if the arg given was illegal.
     */
    public Project createProject(Project project) throws PersistenceException {
        checkProject(project, true);

        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        boolean success = false;
        try {
            //
            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(project);

            transaction.commit();
            success = true;
            return project;
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Project is not a entity, check mapping file.", e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("EntityManager is JTA entity manger or already closed.", e);
        } catch (javax.persistence.PersistenceException e) {
            throw new PersistenceException("Persistence error occurs.", e);
        } finally {
            if (!success) {
                rollback(transaction);
            }
            close(entityManager);
        }
    }

    /**
     * <p>
     * Retrieves the project with the given project ID.
     * </p>
     *
     * @param projectId
     *            The ID of the project to retrieve.
     * @return The project with the given ID or null if none exists.
     * @throws PersistenceException
     *             If a generic persistence error.
     * @throws ProjectNotFoundException
     *             If no project with the given ID exists.
     */
    public Project getProject(long projectId) throws PersistenceException {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();

        EntityTransaction transaction = null;
        boolean success = false;
        try {
            transaction = entityManager.getTransaction();

            transaction.begin();

            Project project = entityManager.find(Project.class, projectId);

            if (null == project) {
                throw new ProjectNotFoundException("No project with id - " + projectId);
            }

            // force lazy loading happen.
            Set<Competition> competitions = project.getCompetitions();
            if (competitions != null) {
                for (Competition competition : competitions) {
                    competition.getCompetitionId();
                }
            }

            transaction.commit();

            success = true;
            return project;

        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Project is not a entity, check mapping file.", e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("EntityManager is JTA entity manger or already closed.", e);
        } catch (javax.persistence.PersistenceException e) {
            throw new PersistenceException("Persistence error occurs.", e);
        } finally {
            if (!success) {
                rollback(transaction);
            }
            close(entityManager);
        }
    }

    /**
     * <p>
     * Retrieves all the projects for the given user.
     * </p>
     *
     * @param userId
     *            The ID of the user whose projects are to be retrieved.
     * @return all the projects for the given user. The returned collection will not be null or contain nulls. Possibly
     *         empty.
     * @throws PersistenceException
     *             If a generic persistence error.
     */
    @SuppressWarnings("unchecked")
    public List<Project> getProjectsForUser(long userId) throws PersistenceException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userId", userId);

        return getResultList("SELECT p FROM Project p WHERE p.userId=:userId", parameters);

    }

    /**
     * <p>
     * Retrieves all the projects.
     * </p>
     *
     * @return All the projects. The returned collection will not be null or contain nulls. Possibly empty.
     * @throws PersistenceException
     *             If a generic persistence error.
     */
    @SuppressWarnings("unchecked")
    public List<Project> getAllProjects() throws PersistenceException {
        return getResultList("SELECT p FROM Project p", null);
    }

    /**
     * <p>
     * Updates the given project.
     * </p>
     * <p>
     * Notes, For all properties of the project which are null (apart from ProjectId), those properties will not be
     * updated.
     * </p>
     *
     * @param project
     *            The project to update. Must not be null. The name must also not be empty. The ProjectId must not be
     *            null. The competitions set, if present, must not contain nulls or contain Competition elements whose
     *            project element is not the given arg.
     * @throws PersistenceException
     *             If a generic persistence error.
     * @throws IllegalArgumentException
     *             If the arg given was illegal.
     * @throws ProjectNotFoundException
     *             If no project with the given ID exists.
     */
    public void updateProject(Project project) throws PersistenceException {
        checkProject(project, false);

        EntityManager entityManager = getEntityManagerFactory().createEntityManager();

        EntityTransaction transaction = null;
        boolean success = false;

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Long projectId = project.getProjectId();
            Project prj = entityManager.find(Project.class, projectId);

            if (null == prj) {
                throw new ProjectNotFoundException("Not project with id - " + projectId);
            }

            // only update the non-null fields.
            if (project.getCompetitions() != null) {
                prj.setCompetitions(project.getCompetitions());
            }

            if (project.getCreateDate() != null) {
                prj.setCreateDate(project.getCreateDate());
            }

            if (project.getDescription() != null) {
                prj.setDescription(project.getDescription());
            }

            if (project.getModifyDate() != null) {
                prj.setModifyDate(project.getModifyDate());
            }

            if (project.getName() != null) {
                prj.setName(project.getName());
            }
            
            if (project.getCompletionDate() != null) {
                prj.setCompletionDate(project.getCompletionDate());
            }
            
            if (project.getProjectType() != null) {
                prj.setProjectType(project.getProjectType());
            }
            
            if (project.getProjectCategory() != null) {
                prj.setProjectCategory(project.getProjectCategory());
            }

            prj.setUserId(project.getUserId());

            entityManager.merge(prj);

            transaction.commit();
            success = true;
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Project is not a entity, check mapping file.", e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("EntityManager is JTA entity manger or already closed.", e);
        } catch (javax.persistence.PersistenceException e) {
            throw new PersistenceException("Persistence error occurs.", e);
        } finally {
            if (!success) {
                rollback(transaction);
            }
            close(entityManager);
        }
    }

    /**
     * <p>
     * Deletes the project with the given project ID.
     * </p>
     *
     * @param projectId
     *            The ID of the project to delete.
     * @return Whether the project was found, and thus deleted.
     * @throws PersistenceException
     *             If a generic persistence error.
     */
    public boolean deleteProject(long projectId) throws PersistenceException {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();

        EntityTransaction transaction = null;
        boolean success = false;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();

            Query query = entityManager.createQuery("DELETE FROM Project p WHERE p.projectId=:projectId");
            query.setParameter("projectId", projectId);

            boolean removed = query.executeUpdate() > 0;

            transaction.commit();

            success = true;
            return removed;
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("Project is not a entity, check mapping file.", e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("EntityManager is JTA entity manger or already closed.", e);
        } catch (javax.persistence.PersistenceException e) {
            throw new PersistenceException("Persistence error occurs.", e);
        } finally {
            if (!success) {
                rollback(transaction);
            }
            close(entityManager);
        }
    }

    /**
     * <p>
     * Gets the entity manager factory used by this class to accomplish persistence.
     * </p>
     *
     * @return the entity manager factory used by this class to accomplish persistence.
     */
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /**
     * <p>
     * Gets the result list from persistence.
     * </p>
     *
     * @param qlString
     *            the query string
     * @param parameters
     *            the key and value of parameters.
     * @return the result list, possible empty. never null.
     * @throws PersistenceException
     *             If any error occurs except the NoResultException.
     */
    @SuppressWarnings("unchecked")
    private List getResultList(String qlString, Map<String, Object> parameters) throws PersistenceException {
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();

        EntityTransaction transaction = null;
        boolean success = false;
        try {
            transaction = entityManager.getTransaction();

            transaction.begin();

            Query query = entityManager.createQuery(qlString);

            if (null != parameters) {
                for (Entry<String, Object> entry : parameters.entrySet()) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }

            List<Project> result = query.getResultList();

            transaction.commit();
            success = true;
            return result;
        } catch (IllegalArgumentException e) {
            throw new PersistenceException("The query string is not valid.", e);
        } catch (IllegalStateException e) {
            throw new PersistenceException("Operation in illegal state.", e);
        } catch (javax.persistence.PersistenceException e) {
            throw new PersistenceException("Persistence error occurs.", e);
        } finally {
            if (!success) {
                rollback(transaction);
            }
            close(entityManager);
        }
    }

    /**
     * <p>
     * Rollback the transaction.
     * </p>
     *
     * @param transaction
     *            the transaction to rollback
     */
    private static void rollback(EntityTransaction transaction) {
        if (null != transaction && transaction.isActive()) {
            try {
                transaction.rollback();
            } catch (Exception e) {
                // just ignore any exception, as we will programmatically throw it.
            }
        }
    }

    /**
     * <p>
     * Close the entity manager.
     * </p>
     *
     * @param entityManager
     *            the entity manger to close.
     */
    private static void close(EntityManager entityManager) {
        if (null != entityManager && entityManager.isOpen()) {
            try {
                entityManager.close();
            } catch (Exception e) {
                // just ignore any exception, as we will programmatically throw it.
            }
        }
    }

    /**
     * <p>
     * Checks the passed Project object. It must not be null. The name,description must also not be null/empty. The
     * ProjectId , if any, is ignored. CreateDate must not be null. The competitions set, if present, must not contain
     * nulls or contain Competition elements whose project element is not the given arg.
     * </p>
     *
     * @param project
     *            the project to check
     * @param isCreate
     *            whether is the create operation, in order to check the id.
     * @throws IllegalArgumentException
     *             If the project given is illegal.
     */
    private static void checkProject(Project project, boolean isCreate) {
        if (null == project) {
            throw new IllegalArgumentException("The passed [project] is null.");
        }

        if (!isCreate && null == project.getProjectId()) {
            throw new IllegalArgumentException("The project id should not null.");
        }

        String name = project.getName();
        if (null == name) {
            if (isCreate) {
                throw new IllegalArgumentException("The project name can not be null.");
            }
        } else if (0 == name.trim().length()) {
            throw new IllegalArgumentException("The project name can not be empty.");
        }

        // create date.
        if (null == project.getCreateDate() && isCreate) {
            throw new IllegalArgumentException("The create date can not be null.");
        }

        // competition set.
        Set<Competition> competitions = project.getCompetitions();
        if (null != competitions) {
            for (Competition competition : competitions) {
                if (null == competition) {
                    throw new IllegalArgumentException("The competition set contains null element.");
                }

                if (project != competition.getProject()) {
                    throw new IllegalArgumentException("The competition is not belong the passed project");
                }
            }
        }
    }
}
