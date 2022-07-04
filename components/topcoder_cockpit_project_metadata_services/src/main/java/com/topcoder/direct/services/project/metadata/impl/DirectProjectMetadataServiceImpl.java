/*
 * Copyright (C) 2011 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataService;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataValidator;
import com.topcoder.direct.services.project.metadata.DirectProjectServiceException;
import com.topcoder.direct.services.project.metadata.EntityNotFoundException;
import com.topcoder.direct.services.project.metadata.PersistenceException;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectAccess;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadata;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataAudit;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.TcDirectProject;
import com.topcoder.direct.services.project.metadata.entities.dto.CompositeFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectMetadataDTO;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyIdValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataKeyNameValueFilter;
import com.topcoder.direct.services.project.metadata.entities.dto.MetadataValueOperator;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * This class is the default implementation of DirectProjectMetadataService. It extends
 * AbstractDirectProjectMetadataService to use entityManager and log. It is injected with
 * directProjectMetadataValidator to validate metadata before insert/update.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
 * &lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
 *      xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
 *      xmlns:tx=&quot;http://www.springframework.org/schema/tx&quot;
 *      xsi:schemaLocation=&quot;
 *      http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
 *      http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.5.xsd&quot;&gt;
 *     &lt;bean class=&quot;org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor&quot; /&gt;
 *     &lt;!--  Configuration for entityManager --&gt;
 *     &lt;bean id=&quot;entityManagerFactory&quot;
 *         class=&quot;org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean&quot;&gt;
 *         &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot; /&gt;
 *         &lt;property name=&quot;jpaVendorAdapter&quot;&gt;
 *             &lt;bean class=&quot;org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter&quot;&gt;
 *                 &lt;property name=&quot;database&quot; value=&quot;INFORMIX&quot; /&gt;
 *             &lt;/bean&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;persistenceUnitManager&quot; ref=&quot;persistenceUnitManager&quot; /&gt;
 *         &lt;property name=&quot;persistenceUnitName&quot; value=&quot;applicationPersistence&quot; /&gt;
 *         &lt;property name=&quot;jpaPropertyMap&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;entry key=&quot;hibernate.cache.use_query_cache&quot; value=&quot;true&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *     &lt;!-- Data Source --&gt;
 *     &lt;bean id=&quot;dataSource&quot; class=&quot;org.apache.commons.dbcp.BasicDataSource&quot;
 *         destroy-method=&quot;close&quot;&gt;
 *         &lt;property name=&quot;driverClassName&quot; value=&quot;com.informix.jdbc.IfxDriver&quot; /&gt;
 *         &lt;property name=&quot;url&quot;
 *             value=&quot;jdbc:informix-sqli://localhost:1526/tcs_catalog:informixserver=ol_topcoder&quot; /&gt;
 *         &lt;property name=&quot;username&quot; value=&quot;informix&quot; /&gt;
 *         &lt;property name=&quot;password&quot; value=&quot;123456&quot; /&gt;
 *         &lt;property name=&quot;minIdle&quot; value=&quot;2&quot; /&gt;
 *     &lt;/bean&gt;
 *     &lt;!--  Configuration for persistence unit manager --&gt;
 *     &lt;bean id=&quot;persistenceUnitManager&quot;
 *         class=&quot;org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager&quot;&gt;
 *         &lt;property name=&quot;persistenceXmlLocations&quot;&gt;
 *             &lt;list&gt;
 *                 &lt;value&gt;classpath*:META-INF/persistence.xml&lt;/value&gt;
 *             &lt;/list&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;dataSources&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;entry key=&quot;localDataSource&quot; value-ref=&quot;dataSource&quot; /&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;defaultDataSource&quot; ref=&quot;dataSource&quot; /&gt;
 *     &lt;/bean&gt;
 *     &lt;!-- Configuration for transaction managers and annotation driven manager enable --&gt;
 *     &lt;bean id=&quot;transactionManager&quot;
 *              class=&quot;org.springframework.orm.jpa.JpaTransactionManager&quot;&gt;
 *         &lt;property name=&quot;entityManagerFactory&quot; ref=&quot;entityManagerFactory&quot; /&gt;
 *     &lt;/bean&gt;
 *     &lt;tx:annotation-driven transaction-manager=&quot;transactionManager&quot; /&gt;
 *     &lt;!-- Validators configuration --&gt;
 *     &lt;bean id=&quot;directProjectMetadataKeyValidator&quot;
 *       class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyValidatorImpl&quot; &gt;
 *         &lt;property name=&quot;loggerName&quot; value=&quot;loggerName&quot; /&gt;
 *     &lt;/bean&gt;
 *     &lt;bean id=&quot;directProjectMetadataValidator&quot;
 *         class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataValidatorImpl&quot; &gt;
 *         &lt;property name=&quot;loggerName&quot; value=&quot;loggerName&quot; /&gt;
 *         &lt;property name=&quot;validatorMapping&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;!-- Example for budget - positive integer value --&gt;
 *                 &lt;entry key=&quot;1&quot; value=&quot;&circ;0*[1-9][0-9]*$&quot;/&gt;
 *                 &lt;!-- Example for project svn address --&gt;
 *                 &lt;entry key=&quot;2&quot; value=&quot;&circ;xx$&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *         &lt;property name=&quot;predefinedKeys&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;!-- Example for choose from predefined list --&gt;
 *                 &lt;entry key=&quot;4&quot; value=&quot;true&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *     &lt;!-- Services configuration --&gt;
 *     &lt;bean id=&quot;directProjectMetadataService&quot;
 *         class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataServiceImpl&quot; &gt;
 *         &lt;property name=&quot;loggerName&quot; value=&quot;loggerName&quot; /&gt;
 *         &lt;property name=&quot;directProjectMetadataValidator&quot;
 *                      ref=&quot;directProjectMetadataValidator&quot; /&gt;
 *         &lt;property name=&quot;auditActionTypeIdMap&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;entry key=&quot;create&quot; value=&quot;1&quot;/&gt;
 *                 &lt;entry key=&quot;update&quot; value=&quot;2&quot;/&gt;
 *                 &lt;entry key=&quot;delete&quot; value=&quot;3&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *     &lt;bean id=&quot;directProjectMetadataKeyService&quot;
 *         class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyServiceImpl&quot; &gt;
 *         &lt;property name=&quot;loggerName&quot; value=&quot;loggerName&quot; /&gt;
 *         &lt;property name=&quot;directProjectMetadataKeyValidator&quot;
 *                      ref=&quot;directProjectMetadataKeyValidator&quot; /&gt;
 *         &lt;property name=&quot;auditActionTypeIdMap&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;entry key=&quot;create&quot; value=&quot;4&quot;/&gt;
 *                 &lt;entry key=&quot;update&quot; value=&quot;5&quot;/&gt;
 *                 &lt;entry key=&quot;delete&quot; value=&quot;6&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 * &lt;/beans&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Initialize application context
 * ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(&quot;applicationContext.xml&quot;);
 *
 * // Retrieve services
 * DirectProjectMetadataService metadataService = (DirectProjectMetadataService) ctx
 *     .getBean(&quot;directProjectMetadataService&quot;);
 * DirectProjectMetadataKeyService metadataKeyService = (DirectProjectMetadataKeyService) ctx
 *     .getBean(&quot;directProjectMetadataKeyService&quot;);
 *
 * // Consider the user id is retrieved from session
 * int userId = 1;
 *
 * // Persist key entity
 * DirectProjectMetadataKey key = new DirectProjectMetadataKey();
 * key.setName(&quot;name3&quot;);
 * key.setDescription(&quot;some text&quot;);
 * key.setGrouping(null);
 * key.setClientId(null);
 * key.setSingle(true);
 *
 * // Create project metadata key
 * long keyId = metadataKeyService.createProjectMetadataKey(key, userId);
 *
 * // Persist metadata entity
 * DirectProjectMetadata metadata = new DirectProjectMetadata();
 * metadata.setTcDirectProjectId(5);
 * metadata.setProjectMetadataKey(key);
 * metadata.setMetadataValue(&quot;value&quot;);
 *
 * // Create project metadata
 * long metadataId = metadataService.createProjectMetadata(metadata, userId);
 *
 * // Update metadata entity
 * metadata.setTcDirectProjectId(8);
 * metadataService.updateProjectMetadata(metadata, userId);
 *
 * // Delete metadata entity
 * metadataService.deleteProjectMetadata(1, userId);
 *
 * // Retrieve project metadata by id
 * metadataService.getProjectMetadata(metadataId);
 *
 * DirectProjectMetadataDTO projectMetadata = new DirectProjectMetadataDTO();
 * projectMetadata.setProjectMetadataKeyId(key.getId());
 * projectMetadata.setMetadataValue(&quot;value&quot;);
 * // Add project metadata list
 * metadataService.addProjectMetadata(new long[] {7, 9}, projectMetadata, userId);
 *
 * // Search projects
 * MetadataKeyNameValueFilter filter = new MetadataKeyNameValueFilter();
 * filter.setProjectMetadataKeyName(&quot;name2&quot;);
 * filter.setMetadataValue(&quot;sec&quot;);
 * filter.setMetadataValueOperator(MetadataValueOperator.LIKE);
 * List&lt;TcDirectProject&gt; projects = metadataService.searchProjects(filter);
 *
 * // Project with id 1 should be returned
 * </pre>
 *
 * </p>
 *
 * <p>
 *     Version 1.2 (Release Assembly - TC Cockpit Report Filters Group By
 *      Metadata Feature and Coordination Improvement) changes:
 *     - Add method {@link #searchProjectIds(long, java.util.List)}.
 *     - Add method {@link #getProjectMetadataByKey(long)}.
 * </p>
 *
 * <p>
 *     Version 1.3 (Release Assembly - TC Cockpit All Projects Management Page Update) changes:
 *     <ol>
 *         <li>Add method {@link #getProjectMetadataByProjects(java.util.List)}</li>
 *     </ol>
 * </p>
 *
 * <p>
 *     Version 1.4 (Release Assembly - TopCoder Cockpit Navigation Update)
 *     <ul>
 *         <li>Added method {@link #getDirectProjectAccess(long, long, long)}</li>
 *         <li>Added method {@link #recordDirectProjectAccess(com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectAccess, long)}</li>
 *     </ul>
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The only non-thread safe part of class is configuration, but it is done in thread
 * safe manner by Spring IoC framework. The class is thread safe under these conditions.
 * </p>
 *
 * @author faeton, sparemax, GreatKevin, Blues, GreatKevin
 * @version 1.4
 */
@Transactional(rollbackFor = DirectProjectServiceException.class)
public class DirectProjectMetadataServiceImpl extends AbstractDirectProjectMetadataService implements
    DirectProjectMetadataService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DirectProjectMetadataServiceImpl.class.getName();

    /**
     * <p>
     * Represents the SQL string to query the project metadata by project id.
     * </p>
     */
    private static final String QUERY_PROJECT_METADATA_BY_PROJECT =
        "SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
        + " WHERE directProjectMetadata.tcDirectProjectId=:tcDirectProjectId order by directProjectMetadata.metadataValue asc";

    /**
     * <p>
     * Represents the SQL string to query the project metadata by a list of project ids.
     * </p>
     *
     * @since 1.3
     */
    private static final String QUERY_PROJECT_METADATA_BY_PROJECTS =
            "SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
                    + " WHERE directProjectMetadata.tcDirectProjectId IN (:tcDirectProjectIds)";

    /**
     * <p>
     *  Represents the SQL string to query the project metadata by project id and project metadata key id.
     * </p>
     * @version 1.1
     */
    private static final String QUERY_PROJECT_METADATA_BY_PROJECT_AND_KEY =
            "SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
            + " WHERE directProjectMetadata.tcDirectProjectId=:tcDirectProjectId AND directProjectMetadata.projectMetadataKey.id=:projectMetadataKeyId order by directProjectMetadata.metadataValue asc";

    /**
     * <p>
     *  Represents the SQL string to query the project metadata by project metadata key id.
     * </p>
     * @since 1.2
     */
    private static final String QUERY_PROJECT_METADATA_BY_KEY =
                "SELECT directProjectMetadata FROM DirectProjectMetadata directProjectMetadata"
                + " WHERE directProjectMetadata.projectMetadataKey.id=:projectMetadataKeyId order by directProjectMetadata.metadataValue asc";

    /**
     * <p>
     * Represents the SQL string to query the project id.
     * </p>
     */
    private static final String QUERY_PROJECT_ID =
        "SELECT distinct tc_direct_project_id FROM direct_project_metadata INNER JOIN direct_project_metadata_key"
        + " ON direct_project_metadata.project_metadata_key_id=direct_project_metadata_key.project_metadata_key_id WHERE ";


    /**
     * <p>
     * Represents the JPA query to get project metadata with key and values set.
     * </p>
     */
    private static final String QUERY_PROJECT_ID_WITH_META_VALUE =
            "SELECT m FROM DirectProjectMetadata m WHERE m.projectMetadataKey.id=:projectMetadataKeyId AND m.metadataValue IN (:values)";

    /**
     * <p>
     * Represents the JPA query to get direct project access with user id, access type id, and access item id.
     * </p>
     * @since 1.4
     */
    private static final String QUERY_DIRECT_PROJECT_ACCESS =
            "SELECT a FROM DirectProjectAccess a WHERE a.userId = :userId AND a.accessTypeId = :accessTypeId AND a.accessItemId = :accessItemId";

    /**
     * <p>
     * Represents the string '%'.
     * </p>
     */
    private static final String PERCENT_SIGN = "%";

    /**
     * <p>
     * The directProjectMetadataValidator to validate metadata before insert/update.
     * </p>
     *
     * <p>
     * The default value is null. It can not be null after configuration. It is accessed by getter and modified by
     * setter. Its' legality is checked in checkInitialization method.
     * </p>
     */
    private DirectProjectMetadataValidator directProjectMetadataValidator;

    /**
     * Creates an instance of DirectProjectMetadataServiceImpl.
     */
    public DirectProjectMetadataServiceImpl() {
        // Empty
    }

    /**
     * Gets the direct project access by the specified user id, access type id and access item id.
     *
     * @param userId the user id.
     * @param accessTypeId the access type id.
     * @param accessItemId the access item id.
     * @return the <code>DirectProjectAccess</code> is found, null if not
     * @throws PersistenceException if any error
     * @since 1.4
     */
    @SuppressWarnings("unchecked")
    public DirectProjectAccess getDirectProjectAccess(long userId, long accessTypeId, long accessItemId)
            throws PersistenceException {
        String signature = CLASS_NAME + ".getDirectProjectAccess(long userId, long accessTypeId, long accessItemId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
                new String[] {"userId", "accessTypeId", "accessItemId"},
                new Object[] {String.valueOf(userId), String.valueOf(accessTypeId), String.valueOf(accessItemId)});

        DirectProjectAccess returnValue = null;

        try {

            EntityManager entityManager = getEntityManager();

            Query query = entityManager.createQuery(QUERY_DIRECT_PROJECT_ACCESS);
            query.setParameter("userId", userId);
            query.setParameter("accessTypeId", accessTypeId);
            query.setParameter("accessItemId", accessItemId);

            List<DirectProjectAccess> result = query.getResultList();

            if(result != null && result.size() > 0) {
                returnValue = result.get(0);
            }

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {returnValue == null ? "null" : String.valueOf(returnValue.getAccessItemId())});

            return returnValue;

        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                    "Failed to retrieve the DirectProjectAccess.", e));
        }
    }

    /**
     * Gets the direct project access by the specified user id, access type id and access item id.
     *
     * @param userId the user id.
     * @param accessTypeId the access type id.
     * @param accessItemId the access item id.
     * @return the <code>DirectProjectAccess</code> is found, null if not
     * @throws PersistenceException if any error
     * @since 1.4
     */
    public long recordDirectProjectAccess(DirectProjectAccess access, long userId) throws PersistenceException {
        String signature = CLASS_NAME + ".recordDirectProjectAccess(DirectProjectAccess access, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
                new String[]{"access", "userId"},
                new Object[]{String.valueOf(access.getAccessItemId()), userId});

        try {
            DirectProjectAccess directProjectAccess = getDirectProjectAccess(access.getUserId(),
                    access.getAccessTypeId(), access.getAccessItemId());

            DirectProjectAccess result = null;

            if (directProjectAccess == null) {
                access.setAccessTime(new Date());
                getEntityManager().persist(access);
                result = access;
            } else {
                directProjectAccess.setAccessTime(new Date());
                getEntityManager().merge(directProjectAccess);
                result = directProjectAccess;
            }


            LoggingWrapperUtility.logExit(logger, signature, new Object[]{String.valueOf(result.getId())});
            return result.getId();
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                    "Failed to create the project metadata.", e));
        }
    }

    /**
     * Creates project metadata and returns the generated id for the entity.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to create.
     * @return the generated id for created entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long createProjectMetadata(DirectProjectMetadata projectMetadata, long userId) throws ValidationException,
        PersistenceException {
        String signature = CLASS_NAME + ".createProjectMetadata(DirectProjectMetadata projectMetadata, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadata", "userId"},
            new Object[] {Helper.toString(projectMetadata), userId});

        directProjectMetadataValidator.validate(projectMetadata);

        try {
            EntityManager entityManager = getEntityManager();
            entityManager.persist(projectMetadata);

            performAudit(projectMetadata, userId, getAuditActionTypeIdMap().get(Helper.ACTION_CREATE));

            long result = projectMetadata.getId();

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {result});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to create the project metadata.", e));
        }
    }

    /**
     * Updates project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to update.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void updateProjectMetadata(DirectProjectMetadata projectMetadata, long userId)
        throws EntityNotFoundException, ValidationException, PersistenceException {
        String signature = CLASS_NAME + ".updateProjectMetadata(DirectProjectMetadata projectMetadata, long userId) ";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadata", "userId"},
            new Object[] {Helper.toString(projectMetadata), userId});

        directProjectMetadataValidator.validate(projectMetadata);

        try {
            EntityManager entityManager = getEntityManager();
            // Check that the entity exists
            DirectProjectMetadata obj = entityManager
                .find(DirectProjectMetadata.class, projectMetadata.getId());
            if (obj == null) {
                throw new EntityNotFoundException("The requested entity with ID [" + projectMetadata.getId()
                    + "] is not found in db.");
            }

            // Update the entity
            entityManager.merge(projectMetadata);
            performAudit(projectMetadata, userId, getAuditActionTypeIdMap().get(Helper.ACTION_UPDATE));

            LoggingWrapperUtility.logExit(logger, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to update the project metadata.", e));
        }
    }

    /**
     * Creates or updates project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadata
     *            the project metadata to create or update.
     *
     * @return the id of the entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long saveProjectMetadata(DirectProjectMetadata projectMetadata, long userId) throws ValidationException,
        PersistenceException {
        String signature = CLASS_NAME + ".saveProjectMetadata(DirectProjectMetadata projectMetadata, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadata", "userId"},
            new Object[] {Helper.toString(projectMetadata), userId});

        directProjectMetadataValidator.validate(projectMetadata);

        try {
            EntityManager entityManager = getEntityManager();
            // Update the entity
            DirectProjectMetadata mergedEntity = entityManager.merge(projectMetadata);
            performAudit(mergedEntity, userId, getAuditActionTypeIdMap().get(Helper.ACTION_UPDATE));

            long result = mergedEntity.getId();
            LoggingWrapperUtility.logExit(logger, signature, new Object[]{result});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to create or update the project metadata.", e));
        }
    }

    /**
     * Batch save a list of project metadata.
     *
     * @param projectMetadataList a list of project metadata.
     * @param userId the id of the user.
     * @return a list of updated or created project metadata IDs.
     * @throws ValidationException if entities fail the validation.
     * @throws PersistenceException if  any problem with persistence occurs.
     * @since 1.1
     */
    public long[] saveProjectMetadata(List<DirectProjectMetadata> projectMetadataList, long userId) throws ValidationException,
            PersistenceException {
        String signature = CLASS_NAME + ".saveProjectMetadata(List<DirectProjectMetadata> projectMetadataList, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
                new String[]{"projectMetadataList", "userId"},
                new Object[]{Helper.toString(projectMetadataList), userId});

        try {
            EntityManager entityManager = getEntityManager();

            long[] result = new long[projectMetadataList.size()];
            int count = 0;

            for (DirectProjectMetadata data : projectMetadataList) {
                // Update the entity
                directProjectMetadataValidator.validate(data);
                DirectProjectMetadata mergedEntity = entityManager.merge(data);
                performAudit(mergedEntity, userId, getAuditActionTypeIdMap().get(Helper.ACTION_UPDATE));
                result[count++] = mergedEntity.getId();
            }

            LoggingWrapperUtility.logExit(logger, signature, new Object[]{result});

            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                    "Failed to create or update the list of project metadata.", e));
        }
    }

    /**
     * Deletes project metadata.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataId
     *            the project metadata id to delete.
     *
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void deleteProjectMetadata(long projectMetadataId, long userId) throws EntityNotFoundException,
        PersistenceException {
        String signature = CLASS_NAME + ".deleteProjectMetadata(long projectMetadataId, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadataId", "userId"},
            new Object[] {projectMetadataId, userId});

        try {
            EntityManager entityManager = getEntityManager();
            DirectProjectMetadata projectMetadata = getProjectMetadata(projectMetadataId);
            if (projectMetadata == null) {
                throw new EntityNotFoundException("The requested entity with ID [" + projectMetadataId
                    + "] is not found in db.");
            }

            performAudit(projectMetadata, userId, getAuditActionTypeIdMap().get(Helper.ACTION_DELETE));

            entityManager.remove(projectMetadata);

            LoggingWrapperUtility.logExit(logger, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to delete the project metadata.", e));
        }
    }

    /**
     * Gets project metadata.
     *
     * @param projectMetadataId
     *            the project metadata id to get.
     *
     * @return the ProjectMetadata for the id or null if the entity is not found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public DirectProjectMetadata getProjectMetadata(long projectMetadataId) throws PersistenceException {
        String signature = CLASS_NAME + ".getProjectMetadata(long projectMetadataId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadataId"},
            new Object[] {projectMetadataId});

        EntityManager entityManager = getEntityManager();

        DirectProjectMetadata result = entityManager.find(DirectProjectMetadata.class, projectMetadataId);

        LoggingWrapperUtility.logExit(logger, signature, new Object[] {Helper.toString(result)});
        return result;
    }

    /**
     * Gets project list of metadata by project id.
     *
     * @param tcDirectProjectId
     *            the topcoder direct project id to get project list of metadata.
     *
     * @return the List of ProjectMetadata entities for the id or empty list if no entity was found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    @SuppressWarnings("unchecked")
    public List<DirectProjectMetadata> getProjectMetadataByProject(long tcDirectProjectId)
        throws PersistenceException {
        String signature = CLASS_NAME + ".getProjectMetadataByProject(long tcDirectProjectId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"tcDirectProjectId"},
            new Object[] {tcDirectProjectId});

        try {
            EntityManager entityManager = getEntityManager();

            Query query = entityManager.createQuery(QUERY_PROJECT_METADATA_BY_PROJECT);
            query.setParameter("tcDirectProjectId", tcDirectProjectId);

            List<DirectProjectMetadata> result = query.getResultList();

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {Helper.toString(result)});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to retrieve the project metadata.", e));
        }
    }

    /**
     * Gets all the project metadata of all the projects specified the list of project ids.
     *
     * @param tcDirectProjectIds the list of project ids.
     * @return the list project metadata.
     * @throws PersistenceException if any problem with persistence occurs.
     * @since 1.3
     */
    @SuppressWarnings("unchecked")
    public List<DirectProjectMetadata> getProjectMetadataByProjects(List<Long> tcDirectProjectIds)
            throws PersistenceException {
        String signature = CLASS_NAME + ".getProjectMetadataByProjects(List<Long> tcDirectProjectIds)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
                new String[]{"tcDirectProjectIds"},
                new Object[]{tcDirectProjectIds});

        try {
            EntityManager entityManager = getEntityManager();

            Query query = entityManager.createQuery(QUERY_PROJECT_METADATA_BY_PROJECTS);
            query.setParameter("tcDirectProjectIds", tcDirectProjectIds);

            List<DirectProjectMetadata> result = query.getResultList();

            LoggingWrapperUtility.logExit(logger, signature, new Object[]{Helper.toString(result)});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                    "Failed to retrieve the project metadata of projects.", e));
        }
    }

    /**
     * Gets the project metadata by project id and project metadata key id.
     *
     * @param tcDirectProjectId  the id of the direct project.
     * @param projectMetadataKeyId the id of the project metadata key.
     * @return a list of project metadata retrieved.
     * @throws PersistenceException if any problem with persistence occurs.
     * @since 1.1
     */
    @SuppressWarnings("unchecked")
    public List<DirectProjectMetadata> getProjectMetadataByProjectAndKey(long tcDirectProjectId, long projectMetadataKeyId)
            throws PersistenceException {
        String signature = CLASS_NAME + ".getProjectMetadataByProjectAndKey(long tcDirectProjectId, long projectMetadataKeyId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
                new String[]{"tcDirectProjectId", "projectMetadataKey"},
                new Object[]{tcDirectProjectId, projectMetadataKeyId});

        try {
            EntityManager entityManager = getEntityManager();

            Query query = entityManager.createQuery(QUERY_PROJECT_METADATA_BY_PROJECT_AND_KEY);
            query.setParameter("tcDirectProjectId", tcDirectProjectId);
            query.setParameter("projectMetadataKeyId", projectMetadataKeyId);

            List<DirectProjectMetadata> result = query.getResultList();

            LoggingWrapperUtility.logExit(logger, signature, new Object[]{Helper.toString(result)});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                    "Failed to retrieve the project metadata.", e));
        }
    }

    /**
     * Gets all the project metadata values by the specified project metadata key id.
     *
     * @param projectMetadataKeyId the id of the metadata id
     * @return the list of <code>DirectProjectMetadata</code>
     * @throws PersistenceException if any problem with persistence occurs
     * @since 1.2
     */
    @SuppressWarnings("unchecked")
    public List<DirectProjectMetadata> getProjectMetadataByKey(long projectMetadataKeyId)
            throws PersistenceException {
        String signature = CLASS_NAME + ".getProjectMetadataByKey(long projectMetadataKeyId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
                new String[]{"projectMetadataKey"},
                new Object[]{projectMetadataKeyId});

        try {
            EntityManager entityManager = getEntityManager();

            Query query = entityManager.createQuery(QUERY_PROJECT_METADATA_BY_KEY);
            query.setParameter("projectMetadataKeyId", projectMetadataKeyId);

            List<DirectProjectMetadata> result = query.getResultList();

            LoggingWrapperUtility.logExit(logger, signature, new Object[]{Helper.toString(result)});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                    "Failed to retrieve the project metadata.", e));
        }
    }

    /**
     * Adds list of project metadata to the given tc project.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataList
     *            the list of project metadata to add.
     * @param tcDirectProjectId
     *            the topcoder direct project id to add list of project metadata.
     *
     * @throws IllegalArgumentException
     *             if projectMetadataList is null or contains null elements.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void addProjectMetadata(long tcDirectProjectId, List<DirectProjectMetadataDTO> projectMetadataList,
        long userId) throws ValidationException, PersistenceException {
        String signature = CLASS_NAME + ".addProjectMetadata(long tcDirectProjectId,"
            + " List<DirectProjectMetadataDTO> projectMetadataList, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"tcDirectProjectId", "projectMetadataList", "userId"},
            new Object[] {tcDirectProjectId, Helper.toString(projectMetadataList), userId});

        try {
            ParameterCheckUtility.checkNotNull(projectMetadataList, "projectMetadataList");
            ParameterCheckUtility.checkNotNullElements(projectMetadataList, "projectMetadataList");
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        }

        EntityManager entityManager = getEntityManager();
        for (DirectProjectMetadataDTO dto : projectMetadataList) {
            // Find the key
            DirectProjectMetadataKey projectMetadataKey =
                entityManager.find(DirectProjectMetadataKey.class, dto.getProjectMetadataKeyId());

            createProjectMetadata(
                    getDirectProjectMetadata(tcDirectProjectId, dto.getMetadataValue(), projectMetadataKey),
                    userId);
        }

        LoggingWrapperUtility.logExit(logger, signature, null);
    }

    /**
     * Adds project metadata to the given list of tc projects.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param tcDirectProjectIds
     *            the topcoder direct project ids to add project metadata.
     * @param projectMetadata
     *            the project metadata to add to projects.
     *
     * @throws IllegalArgumentException
     *             if tcDirectProjectIds or projectMetadata is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void addProjectMetadata(long[] tcDirectProjectIds, DirectProjectMetadataDTO projectMetadata, long userId)
        throws ValidationException, PersistenceException {
        String signature = CLASS_NAME
            + ".addProjectMetadata(long[] tcDirectProjectIds, DirectProjectMetadataDTO projectMetadata, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"tcDirectProjectIds", "projectMetadata", "userId"},
            new Object[] {toList(tcDirectProjectIds), Helper.toString(projectMetadata), userId});

        try {
            ParameterCheckUtility.checkNotNull(tcDirectProjectIds, "tcDirectProjectIds");
            ParameterCheckUtility.checkNotNull(projectMetadata, "projectMetadata");
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        }

        EntityManager entityManager = getEntityManager();

        // Find the key
        DirectProjectMetadataKey projectMetadataKey = entityManager.find(DirectProjectMetadataKey.class,
            projectMetadata.getProjectMetadataKeyId());
        if (projectMetadataKey == null) {
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Direct project metadata key with the id '" + projectMetadata.getProjectMetadataKeyId()
                    + "' cannot be found."));
        }

        for (long tcDirectProjectId : tcDirectProjectIds) {
            createProjectMetadata(
                getDirectProjectMetadata(tcDirectProjectId, projectMetadata.getMetadataValue(), projectMetadataKey),
                userId);
        }

        LoggingWrapperUtility.logExit(logger, signature, null);
    }

    /**
     * Searches the projects by the given search filter.
     *
     * @param filter
     *            the direct project filter to search projects.
     *
     * @return the List of projects for the filter or empty list of no entity was found.
     *
     * @throws IllegalArgumentException
     *             if filter is null or invalid.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    @SuppressWarnings("unchecked")
    public List<TcDirectProject> searchProjects(DirectProjectFilter filter) throws PersistenceException {
        String signature = CLASS_NAME + ".searchProjects(DirectProjectFilter filter)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"filter"},
            new Object[] {Helper.toString(filter)});

        try {
            ParameterCheckUtility.checkNotNull(filter, "filter");

            EntityManager entityManager = getEntityManager();

            Query query = entityManager.createNativeQuery(QUERY_PROJECT_ID + createSearchWhereClause(filter));

            List<BigDecimal> projectIds = query.getResultList();
            List<TcDirectProject> result = new ArrayList<TcDirectProject>();
            for (BigDecimal tcDirectProjectId : projectIds) {
                result.add(entityManager.find(TcDirectProject.class, tcDirectProjectId.longValue()));
            }

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {Helper.toString(result)});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to search the projects.", e));
        }
    }

    /**
     * Searches the project ids with the specified metadata key id and list of metadata values to search.
     *
     * @param projectMetadataKeyId the metadata key id used to search.
     * @param projectMetadataValues the list of metadata values used to search.
     * @return the project ids stored in a set.
     * @throws PersistenceException if any problem with persistence occurs.
     * @since 1.2
     */
    public Set<Long> searchProjectIds(long projectMetadataKeyId, List<String> projectMetadataValues) throws PersistenceException {
            String signature = CLASS_NAME + ".searchProjects(long projectMetadataKeyId, List<String> projectMetadataValues)";
            Log logger = getLogger();

            LoggingWrapperUtility.logEntrance(logger, signature,
                new String[] {"projectMetadataKeyId", "projectMetadataValues"},
                new Object[] {Helper.toString(projectMetadataKeyId), Helper.toString(projectMetadataValues)});

            try {
                if(projectMetadataValues == null || projectMetadataValues.size() == 0) {
                    return new HashSet<Long>();
                }
                
                EntityManager entityManager = getEntityManager();

                StringBuilder whereClause = new StringBuilder();
                
                whereClause.append(" direct_project_metadata.project_metadata_key_id=").append(projectMetadataKeyId)
                        .append(" AND LOWER(direct_project_metadata.metadata_value) IN (");
                
                for(int i = 0, n = projectMetadataValues.size(); i < n; ++i) {
                    whereClause.append('\'' + projectMetadataValues.get(i).toLowerCase() + '\'');
                    if (i != (n - 1)) {
                        whereClause.append(',');
                    }
                }

                whereClause.append(')');

                logger.log(Level.DEBUG, QUERY_PROJECT_ID + whereClause.toString());

                Query query = entityManager.createNativeQuery(QUERY_PROJECT_ID + whereClause.toString());

                List<BigDecimal> projectIds = query.getResultList();

                Set<Long> result = new HashSet<Long>();
                
                for(BigDecimal id : projectIds) {
                    result.add(id.longValue());
                }

                LoggingWrapperUtility.logExit(logger, signature, new Object[] {Helper.toString(result)});
                return result;
            } catch (IllegalArgumentException e) {
                // Log exception
                throw LoggingWrapperUtility.logException(logger, signature, e);
            } catch (javax.persistence.PersistenceException e) {
                // Log exception
                throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                    "Failed to search the projects.", e));
            }
        }

    /**
     * Searches the project metadata with metadata key id and the values set.
     *
     *
     * @param projectMetadataKeyId the metadata key id.
     * @param projectMetadataValues the metadata values set.
     * @return the map from project id to project metadata value.
     * @throws PersistenceException if any problem with persistence occurs.
     */
    public Map<Long, String> searchProjectIdsWithMetadataValues(long projectMetadataKeyId,
                                                                List<String> projectMetadataValues) throws PersistenceException {
        String signature = CLASS_NAME + ".searchProjectIdsWithMetadataValues(long projectMetadataKeyId, List<String> projectMetadataValues)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
                new String[]{"projectMetadataKeyId", "projectMetadataValues"},
                new Object[]{Helper.toString(projectMetadataKeyId), Helper.toString(projectMetadataValues)});

        try {
            if (projectMetadataValues == null || projectMetadataValues.size() == 0) {
                return new HashMap<Long, String>();
            }

            EntityManager entityManager = getEntityManager();

            Query query = entityManager.createQuery(QUERY_PROJECT_ID_WITH_META_VALUE);
            query.setParameter("projectMetadataKeyId", projectMetadataKeyId);
            query.setParameter("values", projectMetadataValues);

            List<DirectProjectMetadata> projects = query.getResultList();

            Map<Long, String> result = new HashMap<Long, String>();

            for (DirectProjectMetadata p : projects) {
                result.put(p.getTcDirectProjectId(), p.getMetadataValue());
            }

            LoggingWrapperUtility.logExit(logger, signature, new Object[]{Helper.toString(result)});
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                    "Failed to search the projects with metadata values set.", e));
        }
    }

    /**
     * Sets the direct project metadata validator to validate metadata before insert/update.
     *
     * @param directProjectMetadataValidator
     *            the direct project metadata validator to validate metadata before insert/update.
     */
    public void setDirectProjectMetadataValidator(DirectProjectMetadataValidator directProjectMetadataValidator) {
        this.directProjectMetadataValidator = directProjectMetadataValidator;
    }

    /**
     * This method is used to check, whether the dependencies were properly initialized for the class.
     *
     * @throws ConfigurationException
     *             if entityManager or directProjectMetadataValidator is <code>null</code>;
     *             auditActionTypeIdMap is <code>null</code>, contains <code>null</code>/empty keys,
     *             <code>null</code> values or does not contain values for keys "create", "update", "delete".
     */
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();

        ValidationUtility.checkNotNull(directProjectMetadataValidator, "directProjectMetadataValidator",
            ConfigurationException.class);
    }

    /**
     * Converts the array to a list.
     *
     * @param array
     *            the array.
     *
     * @return the list or <code>null</code> if array is <code>null</code>.
     */
    private static List<Long> toList(long[] array) {
        if (array == null) {
            return null;
        }
        List<Long> list = new ArrayList<Long>();

        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }

        return list;
    }

    /**
     * Creates a DirectProjectMetadata instance.
     *
     * @param tcDirectProjectId
     *            the tc direct project id.
     * @param metadataValue
     *            the metadata value.
     * @param projectMetadataKey
     *            the project metadata key.
     *
     * @return the DirectProjectMetadata instance.
     */
    private static DirectProjectMetadata getDirectProjectMetadata(long tcDirectProjectId, String metadataValue,
        DirectProjectMetadataKey projectMetadataKey) {
        DirectProjectMetadata metadata = new DirectProjectMetadata();
        metadata.setTcDirectProjectId(tcDirectProjectId);
        metadata.setMetadataValue(metadataValue);

        metadata.setProjectMetadataKey(projectMetadataKey);

        return metadata;
    }

    /**
     * This is a helper method, which is used to create where clause for search projects query.
     *
     * @param filter
     *            the direct project filter.
     *
     * @return the where clause for search projects query.
     *
     * @throws IllegalArgumentException
     *             if the query can not be created(the filter parameter is invalid).
     */
    private static String createSearchWhereClause(DirectProjectFilter filter) {
        if (filter instanceof CompositeFilter) {
            CompositeFilter compositeFilter = (CompositeFilter) filter;
            List<DirectProjectFilter> projectFilters = compositeFilter.getProjectFilters();
            ParameterCheckUtility.checkNotNull(projectFilters, "compositeFilter.getProjectFilters()");
            if (projectFilters.size() != 2) {
                throw new IllegalArgumentException("Size of 'compositeFilter.getProjectFilters()' should be 2.");
            }

            DirectProjectFilter left = projectFilters.get(0);
            DirectProjectFilter right = projectFilters.get(1);
            return new StringBuilder().append("(" + createSearchWhereClause(left)).append(") ").append(
                compositeFilter.getCompositeOperator().name()).append(" (").append(createSearchWhereClause(right))
                .append(")").toString();
        }

        if (filter instanceof MetadataKeyIdValueFilter) {
            MetadataKeyIdValueFilter metadataKeyIdValueFilter = (MetadataKeyIdValueFilter) filter;

            if (metadataKeyIdValueFilter.getMetadataValueOperator() == MetadataValueOperator.EQUALS) {
                return new StringBuilder().append("direct_project_metadata.project_metadata_key_id=").append(
                    metadataKeyIdValueFilter.getProjectMetadataKeyId()).append(
                    " AND LOWER(direct_project_metadata.metadata_value)='").append(
                    checkMetadataValue(metadataKeyIdValueFilter.getMetadataValue().toLowerCase())).append("'").toString();
            }
            if (metadataKeyIdValueFilter.getMetadataValueOperator() == MetadataValueOperator.LIKE) {
                return new StringBuilder().append("direct_project_metadata.project_metadata_key_id=").append(
                    metadataKeyIdValueFilter.getProjectMetadataKeyId()).append(
                    " AND LOWER(direct_project_metadata.metadata_value) LIKE '%").append(
                    checkMetadataValue(metadataKeyIdValueFilter.getMetadataValue().toLowerCase())).append("%'").toString();
            }
        }

        if (filter instanceof MetadataKeyNameValueFilter) {
            MetadataKeyNameValueFilter metadataKeyNameValueFilter = (MetadataKeyNameValueFilter) filter;

            if (metadataKeyNameValueFilter.getMetadataValueOperator() == MetadataValueOperator.EQUALS) {
                return new StringBuilder().append("direct_project_metadata_key.name='").append(
                    metadataKeyNameValueFilter.getProjectMetadataKeyName()).append(
                    "' AND LOWER(direct_project_metadata.metadata_value)='").append(
                    checkMetadataValue(metadataKeyNameValueFilter.getMetadataValue().toLowerCase())).append("'").toString();
            }
            if (metadataKeyNameValueFilter.getMetadataValueOperator() == MetadataValueOperator.LIKE) {
                return new StringBuilder().append("direct_project_metadata_key.name='").append(
                    metadataKeyNameValueFilter.getProjectMetadataKeyName()).append(
                    "' AND LOWER(direct_project_metadata.metadata_value) LIKE '%").append(
                    checkMetadataValue(metadataKeyNameValueFilter.getMetadataValue().toLowerCase())).append("%'").toString();
            }
        }

        throw new IllegalArgumentException("'filter' is not of the expected type.");
    }

    /**
     * Checks if the value contains '%'.
     *
     * @param value
     *            the value.
     *
     * @return the value.
     *
     * @throws IllegalArgumentException
     *             if value contains '%'.
     */
    private static String checkMetadataValue(String value) {
        if ((value != null) && value.contains(PERCENT_SIGN)) {
            throw new IllegalArgumentException("The metadata value cannot contain '%'.");
        }

        return value;
    }

    /**
     * This is a helper method, which is used to perform audit in create/update/delete operations.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param auditActionTypeId
     *            the id of audit action type.
     * @param projectMetadata
     *            the direct project metadata.
     *
     * @throws javax.persistence.PersistenceException
     *             if any problem with persistence occurs.
     */
    private void performAudit(DirectProjectMetadata projectMetadata, long userId, int auditActionTypeId) {
        DirectProjectMetadataAudit audit = new DirectProjectMetadataAudit();

        audit.setAuditActionTypeId(auditActionTypeId);
        audit.setActionDate(new Date());
        audit.setActionUserId(userId);

        audit.setProjectMetadataId(projectMetadata.getId());
        audit.setTcDirectProjectId(projectMetadata.getTcDirectProjectId());
        audit.setProjectMetadataKeyId(projectMetadata.getProjectMetadataKey().getId());
        audit.setMetadataValue(projectMetadata.getMetadataValue());

        EntityManager entityManager = getEntityManager();
        entityManager.persist(audit);
    }
}
