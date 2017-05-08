/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.payment.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ParameterCheckUtility;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.management.payment.ProjectPayment;
import com.topcoder.management.payment.ProjectPaymentManagementConfigurationException;
import com.topcoder.management.payment.ProjectPaymentManagementDataIntegrityException;
import com.topcoder.management.payment.ProjectPaymentManagementException;
import com.topcoder.management.payment.ProjectPaymentManager;
import com.topcoder.management.payment.ProjectPaymentNotFoundException;
import com.topcoder.management.payment.ProjectPaymentType;
import com.topcoder.management.payment.ProjectPaymentValidationException;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.NotFilter;
import com.topcoder.search.builder.filter.NullFilter;
import com.topcoder.util.log.Log;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;
import com.topcoder.util.sql.databaseabstraction.NullColumnValueException;

/**
 * <p>
 * This class is an implementation of ProjectPaymentManager that uses Search Builder component to search for project
 * payments in persistence and pluggable ProjectPaymentPersistence instance for CRUD operations in persistence. This
 * class uses Logging Wrapper component to log errors and debug information.
 * </p>
 *
 * <p>
 * <em>Sample Configuration:</em>
 * <pre>
 * &lt;?xml version=&quot;1.0&quot;?&gt;
 * &lt;CMConfig&gt;
 *   &lt;Config name=&quot;com.topcoder.management.payment.impl.ProjectPaymentManagerImpl&quot;&gt;
 *     &lt;Property name=&quot;loggerName&quot;&gt;
 *       &lt;Value&gt;myLogger&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;objectFactoryConfig&quot;&gt;
 *       &lt;Property name=&quot;DatabaseProjectPaymentPersistence&quot;&gt;
 *         &lt;Property name=&quot;type&quot;&gt;
 *           &lt;Value&gt;
 *               com.topcoder.management.payment.impl.persistence.DatabaseProjectPaymentPersistence
 *           &lt;/Value&gt;
 *         &lt;/Property&gt;
 *       &lt;/Property&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;searchBundleManagerNamespace&quot;&gt;
 *       &lt;Value&gt;ProjectPaymentManagerImpl.SearchBuilderManager&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;projectPaymentSearchBundleName&quot;&gt;
 *       &lt;Value&gt;Project Payment Search Bundle&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;projectPaymentPersistenceKey&quot;&gt;
 *       &lt;Value&gt;DatabaseProjectPaymentPersistence&lt;/Value&gt;
 *     &lt;/Property&gt;
 *     &lt;Property name=&quot;projectPaymentPersistenceConfig&quot;&gt;
 *       &lt;Property name=&quot;loggerName&quot;&gt;
 *         &lt;Value&gt;myLogger&lt;/Value&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name=&quot;dbConnectionFactoryConfig&quot;&gt;
 *         &lt;Property name=&quot;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&quot;&gt;
 *           &lt;Property name=&quot;connections&quot;&gt;
 *             &lt;Property name=&quot;default&quot;&gt;
 *               &lt;Value&gt;myConnection&lt;/Value&gt;
 *             &lt;/Property&gt;
 *             &lt;Property name=&quot;myConnection&quot;&gt;
 *               &lt;Property name=&quot;producer&quot;&gt;
 *                   &lt;Value&gt;com.topcoder.db.connectionfactory.producers.JDBCConnectionProducer&lt;/Value&gt;
 *               &lt;/Property&gt;
 *               &lt;Property name=&quot;parameters&quot;&gt;
 *                 &lt;Property name=&quot;jdbc_driver&quot;&gt;
 *                   &lt;Value&gt;com.informix.jdbc.IfxDriver&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name=&quot;jdbc_url&quot;&gt;
 *                   &lt;Value&gt;jdbc:informix-sqli://localhost:1526/test:informixserver=ol_topcoder&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name=&quot;SelectMethod&quot;&gt;
 *                   &lt;Value&gt;cursor&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name=&quot;user&quot;&gt;
 *                   &lt;Value&gt;informix&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *                 &lt;Property name=&quot;password&quot;&gt;
 *                   &lt;Value&gt;123456&lt;/Value&gt;
 *                 &lt;/Property&gt;
 *               &lt;/Property&gt;
 *             &lt;/Property&gt;
 *           &lt;/Property&gt;
 *         &lt;/Property&gt;
 *       &lt;/Property&gt;
 *       &lt;Property name=&quot;connectionName&quot;&gt;
 *         &lt;Value&gt;myConnection&lt;/Value&gt;
 *       &lt;/Property&gt;
 *     &lt;/Property&gt;
 *   &lt;/Config&gt;
 * &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <em>Sample Code:</em>
 * <pre>
 * // Create an instance of ProjectPaymentManagerImpl using configuration
 * ConfigurationObject configuration = getConfig(ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
 * ProjectPaymentManagerImpl projectPaymentManager = new ProjectPaymentManagerImpl(configuration);
 *
 * // Create an instance of ProjectPaymentManagerImpl using config file and namespace
 * projectPaymentManager = new ProjectPaymentManagerImpl(ProjectPaymentManagerImpl.DEFAULT_CONFIG_FILENAME,
 *     ProjectPaymentManagerImpl.DEFAULT_CONFIG_NAMESPACE);
 *
 * // Create an instance of ProjectPaymentManagerImpl using default config file
 * projectPaymentManager = new ProjectPaymentManagerImpl();
 *
 * // Retrieve the project payment with ID=1
 * ProjectPayment projectPayment = projectPaymentManager.retrieve(1);
 * // id must be 1
 * // submission id must be 1011111
 * // resource id must be 1001
 * // PACTS payment id must be 4
 * // amount must be 500.0
 * // etc.
 *
 * // Update the project payment by changing amount
 * projectPayment.setAmount(BigDecimal.valueOf(600));
 * projectPaymentManager.update(projectPayment);
 *
 * // Search for all project payments with submission ID=1011111
 * Filter submissionIdFilter = ProjectPaymentFilterBuilder.createSubmissionIdFilter(1011111);
 *
 * List&lt;ProjectPayment&gt; projectPayments = projectPaymentManager.search(submissionIdFilter);
 * // projectPayments.size() must be 1
 * // id must be 1
 * // submission id must be 1011111
 * // resource id must be 1001
 * // PACTS payment id must be 4
 * // amount must be 600.0
 * // etc.
 *
 * // Delete the project payment by id
 * projectPaymentManager.delete(2);
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is immutable and thread safe when entities passed to it are used by the
 * caller in thread safe manner. It uses thread safe SearchBundle, ProjectPaymentPersistence and Log instances.
 * </p>
 *
 * @author maksimilian, sparemax
 * @version 1.0.1
 */
public class ProjectPaymentManagerImpl implements ProjectPaymentManager {
    /**
     * The default configuration file path for this class. It's an immutable static constant.
     */
    public static final String DEFAULT_CONFIG_FILENAME =
        "com/topcoder/management/payment/impl/ProjectPaymentManagerImpl.properties";

    /**
     * The default configuration namespace for this class. It's an immutable static constant.
     */
    public static final String DEFAULT_CONFIG_NAMESPACE =
        "com.topcoder.management.payment.impl.ProjectPaymentManagerImpl";

    /**
     * <p>
     * Represents the class name.
     * </p>
     */
    private static final String CLASS_NAME = ProjectPaymentManagerImpl.class.getName();

    /**
     * <p>
     * Represents the property key 'searchBundleManagerNamespace'.
     * </p>
     */
    private static final String KEY_SBM_NAMESPACE = "searchBundleManagerNamespace";

    /**
     * <p>
     * Represents the property key 'projectPaymentSearchBundleName'.
     * </p>
     */
    private static final String KEY_SB_NAME = "projectPaymentSearchBundleName";

    /**
     * <p>
     * Represents the property key 'projectPaymentPersistenceKey'.
     * </p>
     */
    private static final String KEY_PERSISTENCE_KEY = "projectPaymentPersistenceKey";

    /**
     * <p>
     * Represents the child key 'projectPaymentPersistenceConfig'.
     * </p>
     */
    private static final String KEY_PERSISTENCE_CONFIG = "projectPaymentPersistenceConfig";

    /**
     * The logger used by this class for logging errors and debug information. It is null if logging is not required.
     * Initialized in the constructor and never changed after that. Used in all methods. Please see section
     * 1.3.1 of CS for logging details.
     */
    private final Log log;

    /**
     * The search bundle used by this class when searching for project payments. Initialized in the constructor and
     * never changed after that. Cannot be null. Used in search().
     */
    private final SearchBundle searchBundle;

    /**
     * The project payment persistence instance used by this class for CRUD operations. Initialized in the constructor
     * and never changed after that. Cannot be null. Used in CRUD methods.
     */
    private final ProjectPaymentPersistence persistence;

    /**
     * Creates an instance of this class and initializes it from the default configuration file.
     *
     * @throws ProjectPaymentManagementConfigurationException
     *             if error occurred while reading the configuration or initializing this class
     */
    public ProjectPaymentManagerImpl() {
        this(DEFAULT_CONFIG_FILENAME, DEFAULT_CONFIG_NAMESPACE);
    }

    /**
     * Creates an instance of this class and initializes it from the specified configuration file.
     *
     * @param filePath
     *            the path of the configuration file
     * @param namespace
     *            the configuration namespace
     *
     * @throws IllegalArgumentException
     *             if filePath or namespace is null/empty
     * @throws ProjectPaymentManagementConfigurationException
     *             if error occurred while reading the configuration or initializing this class
     */
    public ProjectPaymentManagerImpl(String filePath, String namespace) {
        this(Helper.getConfiguration(filePath, namespace));
    }

    /**
     * Creates an instance of this class and initializes it from the given configuration object.
     *
     * @param configuration
     *            the configuration object for this class
     *
     * @throws IllegalArgumentException
     *             if configuration is null
     * @throws ProjectPaymentManagementConfigurationException
     *             if error occurred while reading the configuration or initializing this class
     */
    public ProjectPaymentManagerImpl(ConfigurationObject configuration) {
        ParameterCheckUtility.checkNotNull(configuration, "configuration");

        // Get logger
        log = Helper.getLog(configuration);

        try {
            // Get search bundle manager namespace from the configuration:
            String searchBundleManagerNamespace = Helper.getProperty(configuration, KEY_SBM_NAMESPACE, true);
            // Create search bundle manager:
            SearchBundleManager searchBundleManager = new SearchBundleManager(searchBundleManagerNamespace);

            String searchBundleName = Helper.getProperty(configuration, KEY_SB_NAME, true);

            searchBundle = searchBundleManager.getSearchBundle(searchBundleName);

            if (searchBundle == null) {
                throw new ProjectPaymentManagementConfigurationException(
                    "There is no search bundle found with the name '" + searchBundleName + "'.");
            }
        } catch (SearchBuilderConfigurationException e) {
            throw new ProjectPaymentManagementConfigurationException(
                "Failed to create an instance of SearchBundleManager.", e);
        }

        // Create the persistence instance
        persistence = Helper.createPersistence(ProjectPaymentPersistence.class, configuration, KEY_PERSISTENCE_KEY,
            KEY_PERSISTENCE_CONFIG);
    }

    /**
     * Creates the given project payment in persistence.
     *
     * @param projectPayment
     *            the project payment with updated data
     * @param operator
     *            ID of the user doing the operation
     *
     * @return the inserted project payment with generated id.
     *
     * @throws IllegalArgumentException
     *             if projectPayment is null or if operator is null or empty string
     * @throws ProjectPaymentValidationException
     *             if projectPayment's id is not null, projectPayment's type is null, projectPayment's resourceId is
     *             null, projectPayment's amount is null or negative, projectPayment's createDate is null.
     * @throws ProjectPaymentManagementDataIntegrityException
     *             if project payment integrity is broken, for example if project payment is being persisted with
     *             payment type that is not present in DB.
     * @throws ProjectPaymentManagementPersistenceException
     *             if some other error occurred when accessing the persistence for example.
     */
    public ProjectPayment create(ProjectPayment projectPayment, String operator) throws ProjectPaymentValidationException,
        ProjectPaymentManagementDataIntegrityException, ProjectPaymentManagementPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".create(ProjectPayment projectPayment)";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"projectPayment", "operator"},
            new Object[] {Helper.toString(projectPayment), operator});

        try {
            ProjectPayment result = persistence.create(projectPayment, operator);
            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {Helper.toString(result)}, entranceTimestamp);
            return result;
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentValidationException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentManagementDataIntegrityException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentManagementPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Updates the given project payment in persistence.
     *
     * @param projectPayment
     *            the project payment with updated data
     * @param operator
     *            ID of the user doing the operation
     *
     * @throws IllegalArgumentException
     *             if projectPayment is null or if operator is null or empty string
     * @throws ProjectPaymentValidationException
     *             if projectPayment's id is null or negative, projectPayment's resourceId is null, projectPayment's
     *             amount is null or negative, projectPayment's createDate is null.
     * @throws ProjectPaymentNotFoundException
     *             if project payment with ID equal to projectPayment.getProjectPaymentId() doesn't exist in
     *             persistence
     * @throws ProjectPaymentManagementDataIntegrityException
     *             if project payment integrity is broken, for example if project payment is being persisted with
     *             payment type that is not present in DB.
     * @throws ProjectPaymentManagementPersistenceException
     *             if some other error occurred when accessing the persistence for example.
     */
    public void update(ProjectPayment projectPayment, String operator) throws ProjectPaymentValidationException,
        ProjectPaymentNotFoundException, ProjectPaymentManagementDataIntegrityException,
        ProjectPaymentManagementPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".update(ProjectPayment projectPayment)";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"projectPayment", "operator"},
            new Object[] {Helper.toString(projectPayment), operator});

        try {
            persistence.update(projectPayment, operator);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, null, entranceTimestamp);
        } catch (IllegalArgumentException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentValidationException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentManagementDataIntegrityException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        } catch (ProjectPaymentManagementPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Retrieves the given project payment from persistence by its id.
     *
     * @param projectPaymentId
     *            the id of the project payment
     *
     * @return the project payment, null if nothing was found
     *
     * @throws ProjectPaymentManagementPersistenceException
     *             if some error occurred when accessing the persistence for example.
     */
    public ProjectPayment retrieve(long projectPaymentId) throws ProjectPaymentManagementPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".retrieve(long projectPaymentId)";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"projectPaymentId"},
            new Object[] {projectPaymentId});

        try {
            ProjectPayment result = persistence.retrieve(projectPaymentId);
            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {Helper.toString(result)}, entranceTimestamp);
            return result;
        } catch (ProjectPaymentManagementPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Deletes the given project payment from persistence.
     *
     * @param projectPaymentId
     *            the id of project payment to delete
     *
     * @return the flag indicating if project payment was deleted
     *
     * @throws ProjectPaymentManagementPersistenceException
     *             if some error occurred when accessing the persistence for example.
     */
    public boolean delete(long projectPaymentId) throws ProjectPaymentManagementPersistenceException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".delete(long projectPaymentId)";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"projectPaymentId"},
            new Object[] {projectPaymentId});

        try {
            boolean result = persistence.delete(projectPaymentId);
            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {result}, entranceTimestamp);
            return result;
        } catch (ProjectPaymentManagementPersistenceException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Searches for all project payments that are matched with the given filter. Returns an empty list if none found.
     *
     * @param filter
     *            the filter
     *
     * @return the list with found project payments that are matched with the given filter (not null, doesn't contain
     *         null)
     *
     * @throws ProjectPaymentManagementPersistenceException
     *             if some error occurred when accessing the persistence
     * @throws ProjectPaymentManagementException
     *             if some other error occurred
     */
    public List<ProjectPayment> search(Filter filter) throws ProjectPaymentManagementException {
        Date entranceTimestamp = new Date();
        String signature = CLASS_NAME + ".search(Filter filter)";

        // Log Entrance
        LoggingWrapperUtility.logEntrance(log, signature,
            new String[] {"filter"},
            new Object[] {filter});

        // Create filter that matches all records if filter is null
        if (filter == null) {
            filter = new NotFilter(new NullFilter("projectPaymentId"));
        }

        try {
            // Perform the search using Search Builder:
            CustomResultSet resultSet = (CustomResultSet) searchBundle.search(filter);
            // Get results from the result set:
            List<ProjectPayment> result = getProjectPayments(resultSet);

            // Log Exit
            LoggingWrapperUtility.logExit(log, signature, new Object[] {Helper.toString(result)}, entranceTimestamp);
            return result;
        } catch (SearchBuilderException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature,
                new ProjectPaymentManagementPersistenceException(
                    "Failed to search project payments with the filter.", e));
        } catch (ClassCastException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, new ProjectPaymentManagementException(
                "The result is invalid.", e));
        } catch (ProjectPaymentManagementException e) {
            // Log exception
            throw LoggingWrapperUtility.logException(log, signature, e);
        }
    }

    /**
     * Retrieves the list of project payments from the provided custom result set.
     *
     * @param resultSet
     *            the custom result set from which project payments should be retrieved
     *
     * @return the retrieved project payments (not null, doesn't contain null)
     *
     * @throws ProjectPaymentManagementException
     *             if some error occurred when retrieving data
     */
    private List<ProjectPayment> getProjectPayments(CustomResultSet resultSet)
        throws ProjectPaymentManagementException {
        // Create a list for result:
        List<ProjectPayment> result = new ArrayList<ProjectPayment>();
        try {
            while (resultSet.next()) {
                ProjectPayment projectPayment = new ProjectPayment();
                projectPayment.setProjectPaymentId(resultSet.getLong("project_payment_id"));
                projectPayment.setResourceId(resultSet.getLong("resource_id"));

                Object value = resultSet.getObject("submission_id");
                if (value != null) {
                    projectPayment.setSubmissionId(((Number) value).longValue());
                }

                projectPayment.setAmount(resultSet.getBigDecimal("amount"));

                value = resultSet.getObject("pacts_payment_id");
                if (value != null) {
                    projectPayment.setPactsPaymentId(((Number) value).longValue());
                }

                projectPayment.setCreateUser(resultSet.getString("create_user"));
                projectPayment.setCreateDate(resultSet.getTimestamp("create_date"));
                projectPayment.setModifyUser(resultSet.getString("modify_user"));
                projectPayment.setModifyDate(resultSet.getTimestamp("modify_date"));

                ProjectPaymentType projectPaymentType = new ProjectPaymentType();
                projectPaymentType.setProjectPaymentTypeId(resultSet.getLong("project_payment_type_id"));
                projectPaymentType.setName(resultSet.getString("name"));
                projectPaymentType.setMergeable(resultSet.getBoolean("mergeable"));
                projectPaymentType.setPactsPaymentTypeId(resultSet.getLong("pacts_payment_type_id"));
                projectPayment.setProjectPaymentType(projectPaymentType);

                result.add(projectPayment);
            }
        } catch (InvalidCursorStateException e) {
            throw new ProjectPaymentManagementException("The cursor state is invalid.", e);
        } catch (NullColumnValueException e) {
            throw new ProjectPaymentManagementException("The column value can't be null.", e);
        } catch (IllegalArgumentException e) {
            throw new ProjectPaymentManagementException("The column does not exist.", e);
        }
        return result;
    }
}
