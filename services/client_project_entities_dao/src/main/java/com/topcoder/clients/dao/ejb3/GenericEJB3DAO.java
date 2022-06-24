/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.dao.ejb3;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.EntityNotFoundException;
import com.topcoder.clients.dao.GenericDAO;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;

/**
 * <p>
 * This abstract class represents the base GenericEJB3DAO class.
 * </p>
 * <p>
 * This base class implements the generic methods available for all the concrete DAOs: retrieve entity by id, retrieve
 * all entities, search entities by name, search entities using the given filter, save an given entity, delete an given
 * entity.
 * </p>
 * <p>
 * It is configured with an EntityManager (initialized by the EJB container through dependency injection) needed to
 * perform operations on the persistence and also uses TopCoder Search Builder 1.4 component to perform the search using
 * the given Filter.
 * </p>
 * <p>
 * NOTE: it is not an Stateless session bean.
 * </p>
 * <p>
 * <b>sample usage of EJBs:</b>
 *
 * <pre>
 * // retrieve bean
 * InitialContext ctx = new InitialContext();
 * ProjectDAORemote bean = (ProjectDAORemote) ctx.lookup(&quot;client_project_entities_dao/ProjectDAOBean/remote&quot;);
 *
 * Filter filter = new EqualToFilter(&quot;projectStatus&quot;, project.getProjectStatus().getId());
 *
 * List&lt;Project&gt; projects;
 *
 * // get project for corresponding id
 * Project tempProject = bean.retrieveById(100L);
 *
 * // get all projects
 * projects = bean.retrieveAll();
 *
 * // get all projects with the name &quot;name&quot;
 * projects = bean.searchByName(&quot;name&quot;);
 *
 * // get all that match the given filter
 * projects = bean.search(filter);
 *
 * // save or update a project
 * bean.save(project);
 *
 * // delete the project
 * bean.delete(project);
 *
 * // get project for corresponding id without projectChildren
 * tempProject = bean.retrieveById(100L, false);
 *
 * // get project for corresponding id with projectChildren
 * tempProject = bean.retrieveById(100L, true);
 *
 * // get all projects without projectChildrens
 * projects = bean.retrieveAll(false);
 *
 * // get all projects with projectChildrens
 * projects = bean.retrieveAll(true);
 *
 * // get projects by user
 * projects = bean.getProjectsByUser(&quot;username&quot;);
 *
 * // get all projects only
 * projects = bean.retrieveAllProjectsOnly();
 *
 * // search projects by project name
 * projects = bean.searchProjectsByProjectName(&quot;projectname&quot;);
 *
 * // search projects by client name
 * projects = bean.searchProjectsByClientName(&quot;clientname&quot;);
 *
 * // get contest fees by project
 * List&lt;ProjectContestFee&gt; fees = bean.getContestFeesByProject(100L);
 *
 * // save contest fees
 * bean.saveContestFees(fees, 100L);
 *
 * // check client project permission
 * boolean clientProjectPermission = bean.checkClientProjectPermission(&quot;username&quot;, 100L);
 *
 * // check po number permission
 * boolean poNumberPermission = bean.checkPoNumberPermission(&quot;username&quot;, &quot;123456A&quot;);
 *
 * // add user to billing projects.
 * bean.addUserToBillingProjects(&quot;username&quot;, new long[] {100, 101, 102});
 *
 * // remove user from billing projects.
 * bean.removeUserFromBillingProjects(&quot;ivern&quot;, new long[] {100, 201});
 *
 * // get the projects by the given client id.
 * projects = bean.getProjectsByClientId(200);
 * </pre>
 *
 * </p>
 * <p>
 * <strong>THREAD SAFETY:</strong> Implementations of this interface should be thread safe.
 * </p>
 *
 * @param <T>
 *            The entity type to operate
 * @param <Id>
 *            The type of id of entity
 * @author Mafy, TCSDEVELOPER
 * @version 1.1
 */
public abstract class GenericEJB3DAO<T extends AuditableEntity, Id extends Serializable>
        implements GenericDAO<T, Id> {
    /**
     * <p>
     * Represents the EntityManager injected by the EJB container automatically.
     * </p>
     * <p>
     * It is initialized during deploying with not null value using
     * {@link PersistenceContext} annotation (we do not check this because
     * depends on configuration of EJB, but if it does not correspond to the
     * given configuration container will report about deployment error).
     * </p>
     * <p>
     * Used by the all the methods that operates on the entities and in flush()
     * and clear() methods.
     * </p>
     * <p>
     * Using corresponding setter, clear and flush methods. Should not be
     * changed after injection.
     * </p>
     * <p>
     * Can not be null after injected when this bean is instantiated.
     * </p>
     */
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    /**
     * <p>
     * Represents the search bundle manager namespace.
     * </p>
     * <p>
     * Injected by the EJB container with non null and not empty value and never
     * changed afterwards.
     * </p>
     * <p>
     * Used by the initialize() method.
     * </p>
     * <p>
     * Will not change after initialization.
     * </p>
     * <p>
     * Can not be null or empty String.
     * </p>
     */
    @Resource(name = "search_bundle_manager_namespace")
    private String searchBundleManagerNamespace;

    /**
     * <p>
     * Represents the configuration file name.
     * </p>
     * <p>
     * Injected by the EJB container with non null and not empty value and never
     * changed afterwards.
     * </p>
     * <p>
     * Used by the initialize() method.
     * </p>
     * <p>
     * Will not change after initialization.
     * </p>
     * <p>
     * Can not be null or empty String.
     * </p>
     */
    @Resource(name = "config_file_name")
    private String configFileName;

    /**
     * <p>
     * Represents the configuration namespace.
     * </p>
     * <p>
     * Injected by the EJB container with non null and not empty value and never
     * changed afterwards.
     * </p>
     * <p>
     * Used by the initialize() method.
     * </p>
     * <p>
     * Will not change after initialization.
     * </p>
     * <p>
     * Can not be null or empty String.
     * </p>
     */
    @Resource(name = "config_namespace")
    private String configNamespace;

    /**
     * <p>
     * Represents the entity bean class type.
     * </p>
     * <p>
     * Initialized in the constructor with non null value and never changed
     * afterwards.
     * </p>
     * <p>
     * Used by the getEntityBeanType() method.
     * </p>
     * <p>
     * Will not change after initialization.
     * </p>
     * <p>
     * Can not be null.
     * </p>
     */
    private final Class<T> entityBeanType;

    /**
     * <p>
     * Represents the search by filter utility needed to perform the search with
     * a given filter.
     * </p>
     * <p>
     * Initialized in the initialize() method with non null value and never
     * changed afterwards.
     * </p>
     * <p>
     * Used by the search() method.
     * </p>
     * <p>
     * Will not change after initialization.
     * </p>
     * <p>
     * Can not be null.
     * </p>
     */
    private SearchByFilterUtility<T, Id> searchByFilterUtility;

    /**
     * <p>
     * Default no-arg constructor.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public GenericEJB3DAO() {
        // Initialize the entityBeanType field:
        entityBeanType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * <p>
     * Initialize the searchByFilterUtility and makes sure the initialization of
     * this session bean is ok.
     * </p>
     * <p>
     * This method is called after this bean is constructed by the EJB
     * container.
     * </p>
     *
     * @throws DAOConfigurationException
     *                 if the String fields are null or empty, if needed
     *                 configurations are missing or entityManager is invalid
     *                 (invalid means null here), if Object Factory cannot be
     *                 initialized or cannot create SearchByFilterUtility
     *                 specific object.
     */
    @PostConstruct
    @SuppressWarnings("unchecked")
    protected void initialize() {
        checkRequiredFields();
        try {
            // Create new instance of ConfigurationFileManager:
            ConfigurationFileManager cfm = new ConfigurationFileManager(
                    configFileName);
            // Get ConfigurationObject with key configNamespace and extract
            // properties as per CS 3.2.3.
            ConfigurationObject config = cfm.getConfiguration(configNamespace);
            if (config == null) {
                throw new DAOConfigurationException(
                        "Cannot found ConfigurationObject with namespace:"
                                + configNamespace);
            }
            ConfigurationObject configObject = config.getChild(configNamespace);
            if (configObject == null) {
                throw new DAOConfigurationException(
                        "Cannot found ConfigurationObject with name:"
                                + configNamespace);
            }
            String tokenKey = "search_by_filter_utility_token";
            String token = null;
            try {
                token = (String) configObject.getPropertyValue(tokenKey);
            } catch (ClassCastException e) {
                throw new DAOConfigurationException("The '" + tokenKey
                        + "' should be String.", e);
            }
            if (token == null || token.trim().length() == 0) {
                throw new DAOConfigurationException(
                        "The 'search_by_filter_utility_token' property should"
                                + " be configed and not be empty string.");
            }
            // Create a new SearchByFilterUtility instance through Object
            // Factory get ConfigurationObjectSpecificationFactory
            ConfigurationObjectSpecificationFactory configurationObjectSpecificationFactory =
                new ConfigurationObjectSpecificationFactory(
                    configObject);
            // get ObjectFactory
            ObjectFactory objectFactory = new ObjectFactory(
                    configurationObjectSpecificationFactory);
            String searchBundleName = "HibernateSearchBundle_"
                    + entityBeanType.getSimpleName();
            Object obj = objectFactory.createObject(token, null,
                    (ClassLoader) null, new String[] {
                            searchBundleManagerNamespace, searchBundleName },
                    new Class[] { String.class, String.class },
                    ObjectFactory.BOTH);
            if (!(obj instanceof SearchByFilterUtility)) {
                throw new DAOConfigurationException(
                        "The object configed use key:"
                                + token
                                + "should be instance of SearchByFilterUtility.");
            }
            // Initialize the searchByFilterUtility field.
            this.searchByFilterUtility = (SearchByFilterUtility<T, Id>) obj;
        } catch (Exception e) {
            if (!(e instanceof DAOConfigurationException)) {
                throw new DAOConfigurationException(
                        "Failed to initialize searchByFilterUtility fields of DAO.",
                        e);
            }
            throw (DAOConfigurationException) e;
        }
    }

    /**
     * This method check required fields for non-null, non-empty for string.
     *
     * @throwsDAOConfigurationException if required fields are invalid.
     */
    private void checkRequiredFields() {
        // check the fields which should be non-null
        if (configFileName == null || configFileName.trim().length() == 0) {
            throw new DAOConfigurationException(
                    "The 'configFileName' should not be null.");
        }
        if (configNamespace == null || configNamespace.trim().length() == 0) {
            throw new DAOConfigurationException(
                    "The 'configNamespace' should not be null.");
        }
        if (searchBundleManagerNamespace == null
                || searchBundleManagerNamespace.trim().length() == 0) {
            throw new DAOConfigurationException(
                    "The 'searchBundleManagerNamespace' should not be null.");
        }
        if (entityManager == null) {
            throw new DAOConfigurationException(
                    "The 'entityManager' should not be null.");
        }
    }

    /**
     * Setter used by container injection mechanism for 'entityManager'
     * property. Please refer to the related 'entityManager' field for more
     * information.
     *
     * @param entityManager
     *                the entityManager used in persistence
     */
    @PersistenceContext(unitName = "persistenceUnit", type = PersistenceContextType.TRANSACTION)
    public void setEntityManager(EntityManager entityManager) {
        Helper.checkNull(entityManager, "entityManager");
        this.entityManager = entityManager;
    }

    /**
     * Getter for 'entityManager' property. Please refer to the related
     * 'entityManager' field for more information. Protected because it is used
     * by subclasses only.
     *
     * @return the entityManager
     */
    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * Getter for 'entityBeanType' property. Please refer to the related
     * 'entityBeanType' field for more information.
     *
     * @return the entity bean class type
     */
    public Class<T> getEntityBeanType() {
        return entityBeanType;
    }

    /**
     * Flush operation over the configured EntityManager. Synchronize the
     * persistence context to the underlying database.
     */
    public void flush() {
        entityManager.flush();
    }

    /**
     * Clear operation over the configured EntityManager. Clear the persistence
     * context, causing all managed entities to become detached.
     */
    public void clear() {
        entityManager.clear();
    }

    /**
     * Performs the retrieval of an entity using the given id from the
     * persistence. Return null if the entity is deleted.
     *
     * @param id
     *                the identifier of the entity that should be retrieved.
     *                Should be positive and not null.
     * @return the entity with the given id retrieved from the persistence.
     * @throws IllegalArgumentException
     *                 if id <= 0 or id is null.
     * @throws EntityNotFoundException
     *                 if an entity for the given id is not found in the
     *                 persistence.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    @SuppressWarnings("unchecked")
    public T retrieveById(Id id) throws EntityNotFoundException, DAOException {
        if (id == null) {
            throw new IllegalArgumentException("'id' should not be null.");
        }
        if ((id instanceof Long) && ((Long) id) <= 0) {
            throw new IllegalArgumentException("'id' should not be <= 0.");
        }
        Helper.checkEntityManager(entityManager);

        String queryString = "select e from "
                + entityBeanType.getCanonicalName()
                + " e where e.id = :id and (e.deleted is null or e.deleted = false)";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id", id);

        // Involves an unchecked conversion:
        try {
            return (T) query.getSingleResult();
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Cannot find entity with id:"
                    + id + ".", e);
        } catch (Exception e) {
            throw new DAOException("Failed to get entity.", e);
        }
    }

    /**
     * Performs the retrieval of all entities from the persistence. If nothing
     * is found, return an empty list. Return only the entities that are not
     * marked as deleted.
     *
     * @return the list of entities found in the persistence. If nothing is
     *         found, return an empty list.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    @SuppressWarnings("unchecked")
    public List<T> retrieveAll() throws DAOException {
        String queryString = "select e from "
                + entityBeanType.getCanonicalName() + " as e"
                + " where e.deleted is null or e.deleted = false";
        Query query = Helper.checkEntityManager(entityManager).createQuery(
                queryString);

        try {
            // Involves an unchecked conversion:
            return query.getResultList();
        } catch (Exception e) {
            throw new DAOException("Failed to get all entities.", e);
        }
    }

    /**
     * Performs the retrieval of all entities that have the given name in the
     * persistence. If nothing is found, return an empty list. Return only the
     * entities that are not marked as deleted.
     *
     * @param name
     *                the name of the entity that should be retrieved. Should be
     *                not empty and not null.
     * @return the list with entities with the given name retrieved from the
     *         persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if name is null or empty string.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<T> searchByName(String name) throws DAOException {
        Helper.checkNullAndEmpty(name, "name");
        Helper.checkEntityManager(entityManager);

        String queryString = "select e from "
                + entityBeanType.getCanonicalName() + " as e"
                + " where e.name = :name" + " and e.deleted = false";

        try {
            return Helper.getEntities("name", name, entityManager, queryString);
        } catch (Exception e) {
            throw new DAOException("Failed to search entities by name:" + name + ".", e);
        }
    }

    /**
     * Performs the retrieval of all entities that match the given filter in the
     * persistence. If nothing is found, return an empty list. Return only the
     * entities that are not marked as deleted.
     *
     * @param filter
     *                the filter that should be used to search the matched
     *                entities. Should not be null.
     * @return the list with entities that match the given filter retrieved from
     *         the persistence. If nothing is found, return an empty list.
     * @throws IllegalArgumentException
     *                 if filter is null.
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public List<T> search(Filter filter) throws DAOException {
        Helper.checkNull(filter, "filter");
        EqualToFilter equalToFilter = new EqualToFilter("deleted", new Boolean(
                false));
        AndFilter resultedFilter = new AndFilter(filter, equalToFilter);
        return searchByFilterUtility.search(resultedFilter);
    }

    /**
     * Performs the salvation of an entity in the persistence.
     *
     * @param entity
     *                the entity that should be saved. Should not be null.
     * @return the entity saved in the persistence.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    public T save(T entity) throws DAOException {
        Helper.checkNull(entity, "entity");
        Helper.checkEntityManager(entityManager);
        try {
            return entityManager.merge(entity);
        } catch (Exception e) {
            throw new DAOException("Failed to save entity.", e);
        }
    }

    /**
     * Performs the deletion of an entity from the persistence.
     *
     * @param entity
     *                the entity that should be deleted from the persistence.
     *                Should not be null.
     * @throws IllegalArgumentException
     *                 if entity is null.
     * @throws EntityNotFoundException
     *                 if entity is not found in the persistence.
     * @throws DAOConfigurationException
     *                 if the configured entityManager is invalid (invalid means
     *                 null here).
     * @throws DAOException
     *                 if any error occurs while performing this operation.
     */
    @SuppressWarnings("unchecked")
    public void delete(T entity) throws EntityNotFoundException, DAOException {
        Helper.checkNull(entity, "entity");
        Helper.checkEntityManager(entityManager);

        try {
            Id id = ((Id) ((Long) entity.getId()));
            T persitedEntity = retrieveById(id);
            persitedEntity.setDeleted(true);
            entityManager.merge(persitedEntity);
        } catch (Exception e) {
            throw Helper.wrapWithDAOException(e,
                    "Failed to delete entity.");
        }
    }

}
