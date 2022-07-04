/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests.persistence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.topcoder.management.resource.persistence.PersistenceResourceManager;
import com.topcoder.management.resource.persistence.ResourcePersistence;
import com.topcoder.management.resource.search.NotificationFilterBuilder;
import com.topcoder.management.resource.search.NotificationTypeFilterBuilder;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.datavalidator.IntegerValidator;
import com.topcoder.util.datavalidator.LongValidator;
import com.topcoder.util.datavalidator.StringValidator;
import com.topcoder.util.idgenerator.IDGenerator;

import junit.framework.TestCase;

/**
 * A base class used for the failure tests of <code>PersistenceResourceManager</code>.
 * <p>
 * It declares an abstract method named <code>loadConfigurations</code>, which should be
 * implemented by sub-class to load different configurations.
 * </p>
 *
 * @author mayi
 * @version 1.1
 * @since 1.0
 */
public abstract class PersistenceResourceManagerBaseFailureTestCase extends TestCase {

    /**
     * Represents the DBFactory connection namespace.
     */
    public static final String DB_CONNECTION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * Represents the search bundle manager configuration namespace.
     */
    public static final String SEARCH_BUNDLE_MANAGER_NAMESPACE =
        "com.topcoder.search.builder.SearchBundleManager.Failure";


    /**
     * An instance of ResourcePersistence for tests.
     */
    protected ResourcePersistence persistence = null;

    /**
     * An instance of SearchBundle for tests.
     */
    protected SearchBundle resourceSearchBundle = null;

    /**
     * An instance of SearchBundle for tests.
     */
    protected SearchBundle resourceRoleSearchBundle = null;

    /**
     * An instance of SearchBundle for tests.
     */
    protected SearchBundle notificationSearchBundle = null;

    /**
     * An instance of SearchBundle for tests.
     */
    protected SearchBundle notificationTypeSearchBundle = null;

    /**
     * Represents the searchBundleManager.
     */
    protected SearchBundleManager searchBundleManager = null;

    /**
     * An instance of IDGenrator for tests.
     */
    protected IDGenerator resourceIdGenerator = null;

    /**
     * An instance of IDGenrator for tests.
     */
    protected IDGenerator resourceRoleIdGenerator = null;

    /**
     * An instance of IDGenrator for tests.
     */
    protected IDGenerator notificationTypeIdGenerator = null;

    /**
     * An instance of PersistenceResourceManager for tests.
     */
    protected PersistenceResourceManager manager = null;

    /**
     * Sets up the environment, mainly initialize the private fields.
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        loadConfigurations();

        // set the persistence
        persistence = new FailureResourcePersistence();

        // get the id generators
        resourceIdGenerator = new FailureIDGenerator();
        resourceRoleIdGenerator = new FailureIDGenerator();
        notificationTypeIdGenerator = new FailureIDGenerator();

        // get the search bundles
        searchBundleManager = new SearchBundleManager(SEARCH_BUNDLE_MANAGER_NAMESPACE);

        // create the search bundles and make them searchable
        createResourceSearchBundle();
        createResourceRoleSearchBundle();
        createNotificationSearchBundle();
        createNotificationTypeSearchBundle();

        // initialize the PersistenceResourceManager
        manager = new PersistenceResourceManager(persistence, resourceSearchBundle,
                resourceRoleSearchBundle, notificationSearchBundle,
                notificationTypeSearchBundle, resourceIdGenerator,
                resourceRoleIdGenerator, notificationTypeIdGenerator);
    }

    /**
     * Tear down. Clear all the configurations.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        clearAllConfigurations();
    }

    /**
     * Load configurations.
     *
     * @throws Exception to JUnit.
     */
    protected abstract void loadConfigurations() throws Exception;

    /**
     * Clear all the configurations in <code>ConfigManager</code>.
     *
     * @throws Exception to JUnit.
     */
    protected void clearAllConfigurations() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator itr = cm.getAllNamespaces(); itr.hasNext(); ) {
            String ns = (String) itr.next();
            if (cm.existsNamespace(ns)) {
                cm.removeNamespace(ns);
            }
        }
    }

    /**
     * Get the notificationTypeSearchBundle and makes it searchable.
     */
    private void createNotificationTypeSearchBundle() {
        // get the notificationTypeSearchBundle and makes it searchable.
        notificationTypeSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.NOTIFICATION_TYPE_SEARCH_BUNDLE_NAME);
        Map notificationTypeFields = new HashMap();
        notificationTypeFields.put(NotificationTypeFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME,
                LongValidator.greaterThanOrEqualTo(0));
        notificationTypeFields.put(NotificationTypeFilterBuilder.NAME_FIELD_NAME,
                StringValidator.hasLength(IntegerValidator.greaterThanOrEqualTo(0)));
        notificationTypeSearchBundle.setSearchableFields(notificationTypeFields);
    }

    /**
     * Get the notificationSearchBundle and makes it searchable.
     */
    private void createNotificationSearchBundle() {
        // get the notificationSearchBundle and makes it searchable.
        notificationSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.NOTIFICATION_SEARCH_BUNDLE_NAME);
        Map notificationFields = new HashMap();
        notificationFields.put(NotificationFilterBuilder.PROJECT_ID_FIELD_NAME,
                LongValidator.greaterThan(0));
        notificationFields.put(NotificationFilterBuilder.EXTERNAL_REF_ID_FIELD_NAME,
                LongValidator.greaterThan(0));
        notificationFields.put(NotificationFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME,
                LongValidator.greaterThan(0));
        notificationSearchBundle.setSearchableFields(notificationFields);
    }

    /**
     * Get the resourceRoleSearchBundle and makes it searchable.
     */
    private void createResourceRoleSearchBundle() {
        // get the resourceRoleSearchBundle and makes it searchable.
        resourceRoleSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.RESOURCE_ROLE_SEARCH_BUNDLE_NAME);
        Map roleFields = new HashMap();
        roleFields.put(ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME,
                LongValidator.greaterThanOrEqualTo(0));
        roleFields.put(ResourceRoleFilterBuilder.PHASE_TYPE_ID_FIELD_NAME,
                LongValidator.greaterThan(0));
        roleFields.put(ResourceRoleFilterBuilder.NAME_FIELD_NAME,
                StringValidator.hasLength(IntegerValidator.greaterThanOrEqualTo(0)));
        resourceRoleSearchBundle.setSearchableFields(roleFields);
    }

    /**
     * Get the resourceSearchBundle and makes it searchable.
     */
    private void createResourceSearchBundle() {
        // get the resourceSearchBundle and makes it searchable.
        resourceSearchBundle = searchBundleManager.getSearchBundle(
                PersistenceResourceManager.RESOURCE_SEARCH_BUNDLE_NAME);
        Map resourceFields = new HashMap();
        resourceFields.put(ResourceFilterBuilder.RESOURCE_ID_FIELD_NAME,
                LongValidator.greaterThan(0));
        resourceFields.put(ResourceFilterBuilder.PROJECT_ID_FIELD_NAME,
                LongValidator.greaterThanOrEqualTo(0));
        resourceFields.put(ResourceFilterBuilder.PHASE_ID_FIELD_NAME,
                LongValidator.greaterThan(0));
        resourceFields.put(ResourceFilterBuilder.SUBMISSION_ID_FIELD_NAME,
                LongValidator.greaterThan(0));
        resourceFields.put(ResourceFilterBuilder.EXTENSION_PROPERTY_NAME_FIELD_NAME,
                StringValidator.hasLength(IntegerValidator.greaterThan(1)));
        resourceFields.put(ResourceFilterBuilder.EXTENSION_PROPERTY_VALUE_FIELD_NAME,
                StringValidator.hasLength(IntegerValidator.greaterThan(0)));
        resourceSearchBundle.setSearchableFields(resourceFields);
    }

}
