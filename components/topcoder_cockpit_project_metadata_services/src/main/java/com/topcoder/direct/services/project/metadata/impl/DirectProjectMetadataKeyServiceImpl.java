/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.project.metadata.ConfigurationException;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataKeyService;
import com.topcoder.direct.services.project.metadata.DirectProjectMetadataKeyValidator;
import com.topcoder.direct.services.project.metadata.DirectProjectServiceException;
import com.topcoder.direct.services.project.metadata.EntityNotFoundException;
import com.topcoder.direct.services.project.metadata.PersistenceException;
import com.topcoder.direct.services.project.metadata.ValidationException;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKey;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataKeyAudit;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValue;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectMetadataPredefinedValueAudit;
import com.topcoder.util.log.Log;

/**
 * <p>
 * This class is the default implementation of DirectProjectMetadataKeyService. It extends
 * AbstractDirectProjectMetadataService to use entityManager and log. It is injected with
 * directProjectMetadataKeyValidator to validate metadata before insert/update.
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
 *
 *     &lt;bean class=&quot;org.springframework.orm.jpa.support.PersistenceAnnotationBeanPostProcessor&quot; /&gt;
 *
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
 *
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
 *
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
 *
 *     &lt;!-- Configuration for transaction managers and annotation driven manager enable --&gt;
 *     &lt;bean id=&quot;transactionManager&quot;
 *              class=&quot;org.springframework.orm.jpa.JpaTransactionManager&quot;&gt;
 *         &lt;property name=&quot;entityManagerFactory&quot; ref=&quot;entityManagerFactory&quot; /&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;tx:annotation-driven transaction-manager=&quot;transactionManager&quot; /&gt;
 *
 *     &lt;!-- Validators configuration --&gt;
 *     &lt;bean id=&quot;directProjectMetadataKeyValidator&quot;
 *       class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyValidatorImpl&quot; &gt;
 *         &lt;property name=&quot;loggerName&quot; value=&quot;loggerName&quot; /&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;bean id=&quot;directProjectMetadataValidator&quot;
 *         class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataValidatorImpl&quot; &gt;
 *
 *         &lt;property name=&quot;loggerName&quot; value=&quot;loggerName&quot; /&gt;
 *         &lt;property name=&quot;validatorMapping&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;!-- Example for budget - positive integer value --&gt;
 *                 &lt;entry key=&quot;1&quot; value=&quot;&circ;0*[1-9][0-9]*$&quot;/&gt;
 *                 &lt;!-- Example for project svn address --&gt;
 *                 &lt;entry key=&quot;2&quot; value=&quot;&circ;xx$&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *
 *         &lt;property name=&quot;predefinedKeys&quot;&gt;
 *             &lt;map&gt;
 *                 &lt;!-- Example for choose from predefined list --&gt;
 *                 &lt;entry key=&quot;4&quot; value=&quot;true&quot;/&gt;
 *             &lt;/map&gt;
 *         &lt;/property&gt;
 *     &lt;/bean&gt;
 *
 *     &lt;!-- Services configuration --&gt;
 *     &lt;bean id=&quot;directProjectMetadataService&quot;
 *         class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataServiceImpl&quot; &gt;
 *
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
 *
 *     &lt;bean id=&quot;directProjectMetadataKeyService&quot;
 *         class=&quot;com.topcoder.direct.services.project.metadata.impl.DirectProjectMetadataKeyServiceImpl&quot; &gt;
 *
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
 * // Retrieve service
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
 * // Update project metadata key
 * key.setDescription(&quot;new text&quot;);
 * metadataKeyService.updateProjectMetadataKey(key, userId);
 *
 * // Retrieve project metadata key by id
 * metadataKeyService.getProjectMetadataKey(keyId);
 *
 * // Delete project metadata key
 * metadataKeyService.deleteProjectMetadataKey(keyId, userId);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> The only non-thread safe part of class is configuration, but it is done in thread
 * safe manner by Spring IoC framework. The class is thread safe under these conditions.
 * </p>
 *
 * @author faeton, sparemax
 * @version 1.0
 */
@Transactional(rollbackFor = DirectProjectServiceException.class)
public class DirectProjectMetadataKeyServiceImpl extends AbstractDirectProjectMetadataService implements
    DirectProjectMetadataKeyService {
    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = DirectProjectMetadataKeyServiceImpl.class.getName();

    /**
     * <p>
     * Represents the SQL string to query the common direct project metadata key.
     * </p>
     */
    private static final String QUERY_COMMON_PROJECT_METADATA_KEY =
        "SELECT directProjectMetadataKey FROM DirectProjectMetadataKey directProjectMetadataKey"
        + " WHERE directProjectMetadataKey.clientId IS NULL";

    /**
     * <p>
     * Represents the SQL string to query the client direct project metadata key.
     * </p>
     */
    private static final String QUERY_CLIENT_PROJECT_METADATA_KEY_1 =
        "SELECT directProjectMetadataKey FROM DirectProjectMetadataKey directProjectMetadataKey"
        + " WHERE directProjectMetadataKey.clientId=:clientId";

    /**
     * <p>
     * The directProjectMetadataKeyValidator to validate metadata key before insert/update.
     * </p>
     *
     * <p>
     * The default value is null. It can not be null after configuration. It is accessed by getter and modified by
     * setter. Its' legality is checked in checkInitialization method.
     * </p>
     */
    private DirectProjectMetadataKeyValidator directProjectMetadataKeyValidator;

    /**
     * Creates an instance of DirectProjectMetadataKeyServiceImpl.
     */
    public DirectProjectMetadataKeyServiceImpl() {
        // Empty
    }

    /**
     * Creates project metadata key and returns the generated id for the entity.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataKey
     *            the project metadata key to create.
     *
     * @return the generated id for created entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadataKey is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long createProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long userId)
        throws ValidationException, PersistenceException {
        String signature = CLASS_NAME
            + ".createProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadataKey", "userId"},
            new Object[] {Helper.toString(projectMetadataKey), userId});

        directProjectMetadataKeyValidator.validate(projectMetadataKey);

        try {

            EntityManager entityManager = getEntityManager();
            entityManager.persist(projectMetadataKey);

            performAudit(projectMetadataKey, userId, getAuditActionTypeIdMap().get(Helper.ACTION_CREATE));

            long result = projectMetadataKey.getId();

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {result});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to create the project metadata key.", e));
        }
    }

    /**
     * Updates project metadata key.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataKey
     *            the project metadata key to update.
     *
     * @throws IllegalArgumentException
     *             if projectMetadataKey is null.
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void updateProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long userId)
        throws EntityNotFoundException, ValidationException, PersistenceException {
        String signature = CLASS_NAME
            + ".updateProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadataKey", "userId"},
            new Object[] {Helper.toString(projectMetadataKey), userId});

        directProjectMetadataKeyValidator.validate(projectMetadataKey);

        try {

            EntityManager entityManager = getEntityManager();
            // Check that the entity exists
            DirectProjectMetadataKey obj =
                entityManager.find(DirectProjectMetadataKey.class, projectMetadataKey.getId());
            if (obj == null) {
                throw new EntityNotFoundException("The requested entity with ID [" + projectMetadataKey.getId()
                    + "] is not found in db.");
            }

            // Update the entity
            entityManager.merge(projectMetadataKey);
            performAudit(projectMetadataKey, userId, getAuditActionTypeIdMap().get(Helper.ACTION_UPDATE));

            LoggingWrapperUtility.logExit(logger, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to update the project metadata key.", e));
        }
    }

    /**
     * Creates or updates project metadata key. If the passed entity does not have id set, then creation is performed,
     * otherwise update is done.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataKey
     *            the project metadata key to create or update.
     *
     * @return the id of the entity.
     *
     * @throws IllegalArgumentException
     *             if projectMetadataKey is null.
     * @throws ValidationException
     *             if entity fails the validation.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public long saveProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long userId)
        throws ValidationException, PersistenceException {
        String signature = CLASS_NAME
            + ".saveProjectMetadataKey(DirectProjectMetadataKey projectMetadataKey, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadataKey", "userId"},
            new Object[] {Helper.toString(projectMetadataKey), userId});

        directProjectMetadataKeyValidator.validate(projectMetadataKey);

        try {
            EntityManager entityManager = getEntityManager();
            // Update the entity
            DirectProjectMetadataKey mergedEntity = entityManager.merge(projectMetadataKey);
            performAudit(mergedEntity, userId, getAuditActionTypeIdMap().get(Helper.ACTION_UPDATE));

            long result = mergedEntity.getId();
            LoggingWrapperUtility.logExit(logger, signature, new Object[] {result});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to create or update the project metadata key.", e));
        }
    }

    /**
     * Deletes project metadata key.
     *
     * @param projectMetadataKeyId
     *            the project metadata key id to delete.
     * @param userId
     *            the id of the user performed the operation.
     *
     * @throws EntityNotFoundException
     *             if requested entity is not found in db.
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    public void deleteProjectMetadataKey(long projectMetadataKeyId, long userId) throws EntityNotFoundException,
        PersistenceException {
        String signature = CLASS_NAME + ".deleteProjectMetadataKey(long projectMetadataKeyId, long userId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadataKeyId", "userId"},
            new Object[] {projectMetadataKeyId, userId});

        try {
            EntityManager entityManager = getEntityManager();
            DirectProjectMetadataKey projectMetadataKey = getProjectMetadataKey(projectMetadataKeyId);
            if (projectMetadataKey == null) {
                throw new EntityNotFoundException("The requested entity with ID [" + projectMetadataKeyId
                    + "] is not found in db.");
            }

            performAudit(projectMetadataKey, userId, getAuditActionTypeIdMap().get(Helper.ACTION_DELETE));

            entityManager.remove(projectMetadataKey);

            LoggingWrapperUtility.logExit(logger, signature, null);
        } catch (EntityNotFoundException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, e);
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to delete the project metadata key.", e));
        }
    }

    /**
     * Gets project metadata key.
     *
     * @param projectMetadataKeyId
     *            the project metadata key id to get.
     *
     * @return the ProjectMetadataKey for the id or null if the entity is not found.
     */
    public DirectProjectMetadataKey getProjectMetadataKey(long projectMetadataKeyId) {
        String signature = CLASS_NAME + ".getProjectMetadataKey(long projectMetadataKeyId)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"projectMetadataKeyId"},
            new Object[] {projectMetadataKeyId});

        EntityManager entityManager = getEntityManager();

        DirectProjectMetadataKey result = entityManager.find(DirectProjectMetadataKey.class, projectMetadataKeyId);

        LoggingWrapperUtility.logExit(logger, signature, new Object[] {Helper.toString(result)});
        return result;
    }

    /**
     * Gets common project metadata keys, whose client id is set to null.
     *
     * @return the List of project metadata keys with client id set to null or empty list, if no entity was found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    @SuppressWarnings("unchecked")
    public List<DirectProjectMetadataKey> getCommonProjectMetadataKeys() throws PersistenceException {
        String signature = CLASS_NAME + ".getCommonProjectMetadataKeys()";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature, null, null);

        try {
            EntityManager entityManager = getEntityManager();
            Query query = entityManager.createQuery(QUERY_COMMON_PROJECT_METADATA_KEY);
            List<DirectProjectMetadataKey> result =
                query.getResultList();

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {Helper.toString(result)});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to retrieve the common project metadata keys.", e));
        }
    }

    /**
     * Gets specific project metadata keys by client id and optional grouping filter parameter.
     *
     * @param grouping
     *            the grouping filter parameter.
     * @param clientId
     *            the client id to search project metadata keys.
     *
     * @return the List of project metadata keys for client set to null or empty list, if no entity was found.
     *
     * @throws PersistenceException
     *             if any problem with persistence occurs.
     */
    @SuppressWarnings("unchecked")
    public List<DirectProjectMetadataKey> getClientProjectMetadataKeys(long clientId, Boolean grouping)
        throws PersistenceException {
        String signature = CLASS_NAME + ".getClientProjectMetadataKeys(long clientId, Boolean grouping)";
        Log logger = getLogger();

        LoggingWrapperUtility.logEntrance(logger, signature,
            new String[] {"clientId", "grouping"},
            new Object[] {clientId, grouping});

        try {
            EntityManager entityManager = getEntityManager();
            Query query = entityManager.createQuery(QUERY_CLIENT_PROJECT_METADATA_KEY_1);
            query.setParameter("clientId", clientId);

            List<DirectProjectMetadataKey> list = query.getResultList();

            List<DirectProjectMetadataKey> result;
            if (grouping != null) {
                result = new ArrayList<DirectProjectMetadataKey>();
                for (DirectProjectMetadataKey obj : list) {
                    if (grouping.equals(obj.getGrouping())) {
                        result.add(obj);
                    }
                }
            } else {
                result = list;
            }

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {Helper.toString(result)});
            return result;
        } catch (javax.persistence.PersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(logger, signature, new PersistenceException(
                "Failed to retrieve the client project metadata keys.", e));
        }
    }

    /**
     * Sets the directProjectMetadataKeyValidator to validate metadata key before insert/update.
     *
     * <p>
     * It is called by Spring injection framework.
     * </p>
     *
     * @param directProjectMetadataKeyValidator
     *            the directProjectMetadataKeyValidator to validate metadata key before insert/update.
     */
    public void setDirectProjectMetadataKeyValidator(
        DirectProjectMetadataKeyValidator directProjectMetadataKeyValidator) {
        this.directProjectMetadataKeyValidator = directProjectMetadataKeyValidator;
    }

    /**
     * This method is used to check, whether the dependencies were properly initialized for the class.
     *
     * @throws ConfigurationException
     *             if entityManager or directProjectMetadataKeyValidator is <code>null</code>; auditActionTypeIdMap
     *             is <code>null</code>, contains <code>null</code>/empty keys, <code>null</code> values or
     *             does not contain values for keys "create", "update", "delete".
     */
    @Override
    @PostConstruct
    protected void checkInitialization() {
        super.checkInitialization();

        ValidationUtility.checkNotNull(directProjectMetadataKeyValidator, "directProjectMetadataKeyValidator",
            ConfigurationException.class);
    }

    /**
     * This is a helper method, which is used to perform audit in create/update/delete operations.
     *
     * @param userId
     *            the id of the user performed the operation.
     * @param projectMetadataKey
     *            the direct project metadata key.
     * @param auditActionTypeId
     *            the id of audit action type.
     *
     * @throws javax.persistence.PersistenceException
     *             if any problem with persistence occurs.
     */
    private void performAudit(DirectProjectMetadataKey projectMetadataKey, long userId, int auditActionTypeId) {
        EntityManager entityManager = getEntityManager();

        DirectProjectMetadataKeyAudit audit = new DirectProjectMetadataKeyAudit();

        audit.setAuditActionTypeId(auditActionTypeId);
        audit.setActionDate(new Date());
        audit.setActionUserId(userId);

        audit.setProjectMetadataKeyId(projectMetadataKey.getId());
        audit.setName(projectMetadataKey.getName());
        audit.setDescription(projectMetadataKey.getDescription());
        audit.setGrouping(projectMetadataKey.getGrouping());
        audit.setClientId(projectMetadataKey.getClientId());
        audit.setSingle(projectMetadataKey.isSingle());

        entityManager.persist(audit);

        List<DirectProjectMetadataPredefinedValue> predefinedValues = projectMetadataKey.getPredefinedValues();
        if (predefinedValues != null) {
            for (DirectProjectMetadataPredefinedValue predefinedValue : projectMetadataKey.getPredefinedValues()) {
                DirectProjectMetadataPredefinedValueAudit predefinedValueAudit =
                    new DirectProjectMetadataPredefinedValueAudit();

                predefinedValueAudit.setAuditActionTypeId(auditActionTypeId);
                predefinedValueAudit.setActionDate(new Date());
                predefinedValueAudit.setActionUserId(userId);

                predefinedValueAudit.setProjectMetadataPredefinedValueId(predefinedValue.getId());
                predefinedValueAudit.setPredefinedMetadataValue(predefinedValue.getPredefinedMetadataValue());
                predefinedValueAudit.setPosition(predefinedValue.getPosition());
                predefinedValueAudit.setProjectMetadataKeyId(projectMetadataKey.getId());

                entityManager.persist(predefinedValueAudit);
            }
        }
    }
}
