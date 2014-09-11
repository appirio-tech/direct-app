/*
 * Copyright (C) 2006-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.topcoder.management.resource.AuditableResourceStructure;
import com.topcoder.management.resource.Helper;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.search.NotificationFilterBuilder;
import com.topcoder.management.resource.search.NotificationTypeFilterBuilder;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;
import com.topcoder.search.builder.SearchBuilderConfigurationException;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.SearchBundleManager;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;

/**
 * <p>
 * The PersistenceResourceManager class implements the ResourceManager
 * interface. It ties together a persistence mechanism, several Search Builder
 * searching instances (for searching for various types of data), and several id
 * generators (for generating ids for the various types).
 * </p>
 *
 * <p>
 * This class consists of a several methods styles. The first method style just
 * calls directly to a corresponding method of the persistence. The second method style first
 * assigns values to some data fields of the object before calling a persistence
 * method. The third type of method uses a SearchBundle to execute a search and
 * then uses the persistence to load an object for each of the ids found from
 * the search.
 * </p>
 *
 * <p>
 * All the update and remove methods in this class is that the modification/creation user/date are set on
 * the various objects and then pass to the persistence.
 * </p>
 *
 * <p>
 * <pre>
 * Sample usage of this component:
 *
 * create Resource Or ResourceRole to Persistence:
 *
 *      // Note that this will assign an id to the resource and
 *      // resource role
 *      manager.updateResourceRole(resourceRole, "Operator #1");
 *      manager.updateResource(resource, "Operator #1");
 *      // The updating of notification types is entirely similar
 *      // to the calls above
 *      // The data can then be changed and the changes
 *      //  persisted
 *      resourceRole.setName("Changed name");
 *      manager.updateResourceRole(resourceRole, "Operator #1");
 *
 * search resources with a filter:
 *
 *      // Search for the Resources
 *      manager.searchResources(fullFilter);
 *
 *      // ResourceRoles, NotificationTypes, and Notifications can be
 *      // searched similarly by using the other FilterBuilder classes
 *      // and the corresponding ResourceManager methods.  They can
 *      // also be retrieved through the getAll methods
 *      manager.getAllResourceRoles();
 *
 *      manager.getAllNotificationTypes();
 *
 * You can refer to the demo or test cases for more details.
 * </pre>
 * </p>
 * <p>
 * Version 1.3 update:
 * Add new methods - addNotifications(long user, long[] projects, long notificationType, String operator)
 *                 - removeNotifications(long user, long[] projects, long notificationType, String operator)
 *                 - getNotificationsForUser(long user, long notificationType).
 * Please refer to Demo for more usage details.
 * </p>
 * <p>
 * This class is immutable and hence thread-safe.
 * </p>
 *
 * @author aubergineanode, kinfkong, George1, waits
 * @version 1.3
 * @since 1.0
 */
public class PersistenceResourceManager implements ResourceManager {

    /**
     * <p>
     * Represents the name under which the resource search
     * bundle should appear in the SearchBundleManager, if the
     * SearchBundleManager constructor is used.
     * </p>
     *
     * <p>
     * This field is public, static, and final.
     * </p>
     */
    public static final String RESOURCE_SEARCH_BUNDLE_NAME = "Resource Search Bundle";

    /**
     * <p>
     * The name under which the resource role search bundle should appear in the SearchBundleManager, if the
     * SearchBundleManager constructor is used.
     * </p>
     *
     * <p>
     * This field is public, static, and final.
     * </p>
     */
    public static final String RESOURCE_ROLE_SEARCH_BUNDLE_NAME = "Resource Role Search Bundle";

    /**
     * <p>
     * Represents the name under which the notification
     * search bundle should appear in the SearchBundleManager, if the
     * SearchBundleManager constructor is used.
     * </p>
     *
     * <p>
     * This field is public, static, and final.
     * </p>
     */
    public static final String NOTIFICATION_SEARCH_BUNDLE_NAME = "Notification Search Bundle";

    /**
     * <p>
     * Represents the name under which the notification type search bundle should appear in the SearchBundleManager,
     * if the SearchBundleManager constructor is used.
     * </p>
     *
     * <p>
     * This field is public, static, and final.
     * </p>
     */
    public static final String NOTIFICATION_TYPE_SEARCH_BUNDLE_NAME = "Notification Type Search Bundle";

    /**
     * <p>
     * Represents the name under which the id generator for resources should appear in the IDGeneratorFactory if the
     * IDGeneratorFactory constructor is used.
     * </p>
     *
     * <p>
     * This field is public, static, and final.
     * </p>
     */
    public static final String RESOURCE_ID_GENERATOR_NAME = "resource_id_seq";

    /**
     * <p>
     * Represents the name under which the id generator for resource roles should appear in the IDGeneratorFactory
     * if the IDGeneratorFactory constructor is used.
     * </p>
     *
     * <p>
     * This field is public, static, and final.
     * </p>
     */
    public static final String RESOURCE_ROLE_ID_GENERATOR_NAME = "resource_role_id_seq";

    /**
     * <p>
     * Represents the name under which the id generator for notification types should appear in the IDGeneratorFactory
     * if the IDGeneratorFactory constructor is used.
     * </p>
     *
     * <p>
     * This field is public, static, and final.
     * </p>
     */
    public static final String NOTIFICATION_TYPE_ID_GENERATOR_NAME = "notification_type_id_seq";

    /**
     * <p>
     * Represents the persistence store for Resources, ResourceRoles, Notifications, and NotificationTypes.
     * </p>
     *
     * <p>
     * This field is set in the constructor, and is immutable, and can never be null.
     * This field is used in almost all the methods of this class, as it is used either to make changes
     * to the items in the persistence, or to retrieve the items in the persistence once
     * their ids have been found with the Search Builder component.
     * </p>
     */
    private final ResourcePersistence persistence;

    /**
     * <p>
     * Represents the search bundle that is used when searching for resources.
     * </p>
     *
     * <p>
     * This field is set in the constructor, and is immutable, and can never be null.
     * This field is used to search resources, in the searchResources method.
     * </p>
     */
    private final SearchBundle resourceSearchBundle;

    /**
     * <p>
     * Represents the search bundle that is used when searching for resource roles.
     * </p>
     *
     * <p>
     * This field is set in the constructor, is immutable, and can never be null.
     * This field is used to search resource roles, in the searchResourceRoles method.
     * </p>
     */
    private final SearchBundle resourceRoleSearchBundle;

    /**
     * <p>
     * Represents the search bundle that is used when searching for notifications.
     * </p>
     *
     * <p>
     * This field is set in the constructor, and is immutable, and can never be null.
     * This field is used to search notifications, in the searchNotifications method.
     * </p>
     */
    private final SearchBundle notificationSearchBundle;

    /**
     * <p>
     * Represents the search bundle that is used when searching for notification types.
     * </p>
     *
     * <p>
     * This field is set in the constructor, is immutable, and can never be null.
     * This field is used to search notification types, in the searchNotificationTypes method.
     * </p>
     */
    private final SearchBundle notificationTypeSearchBundle;

    /**
     * <p>
     * Represents the generator used to create ids for new Resources.
     * </p>
     *
     * <p>
     * This field is set in the constructor, and is immutable, and can never be
     * null. This field is used when an id is needed for a new Resource, which
     * occurs in the updateResource and updateResources methods.
     * </p>
     */
    private final IDGenerator resourceIdGenerator;

    /**
     * <p>
     * Represents the generator used to create ids for new ResourceRoles.
     * </p>
     *
     * <p>
     * This field is set in the constructor, is and immutable, and
     * can never be null.
     * This field is used when an id is needed for a new
     * ResourceRole, which occurs in the updateResourceRole method.
     * </p>
     */
    private final IDGenerator resourceRoleIdGenerator;

    /**
     * <p>
     * Represents the generator used to create ids for new NotificationTypes.
     * </p>
     *
     * <p>
     * This field is set in the constructor, and is immutable,
     * and can never be null. This field is used when an id is needed for a new
     * NotificationType, which occurs in the updateNotificationType method.
     * </p>
     */
    private final IDGenerator notificationTypeIdGenerator;

    /**
     * <p>
     * Creates a new PersistenceResourceManager, initializing all fields to the given values.
     * </p>
     *
     * <p>
     * Note, in order to invoke the methods in this class, you should set the searchable fields
     * of the searchBundles using the method: setSearchableFields(Map)
     * before parsing in the constructor.
     * </p>
     *
     * @param persistence the persistence for Resources and related objects
     * @param resourceSearchBundle the search bundle for searching resources
     * @param resourceRoleSearchBundle the search bundle for searching resource roles
     * @param notificationSearchBundle the search bundle for searching notifications
     * @param notificationTypeSearchBundle the search bundle for searching notification types
     * @param resourceIdGenerator the generator for Resource ids
     * @param resourceRoleIdGenerator the generator for ResourceRole ids
     * @param notificationTypeIdGenerator the generator for NotificationType ids
     *
     * @throws IllegalArgumentException if any argument is null
     */
    public PersistenceResourceManager(ResourcePersistence persistence, SearchBundle resourceSearchBundle,
            SearchBundle resourceRoleSearchBundle, SearchBundle notificationSearchBundle,
            SearchBundle notificationTypeSearchBundle, IDGenerator resourceIdGenerator,
            IDGenerator resourceRoleIdGenerator, IDGenerator notificationTypeIdGenerator) {

        // validate the arguments
        Helper.checkNull(persistence, "persistence");

        Helper.checkNull(resourceSearchBundle, "resourceSearchBundle");

        Helper.checkNull(resourceRoleSearchBundle, "resourceRoleSearchBundle");

        Helper.checkNull(notificationSearchBundle, "notificationSearchBundle");

        Helper.checkNull(notificationTypeSearchBundle, "notificationTypeSearchBundle");

        Helper.checkNull(resourceIdGenerator, "resourceIdGenerator");

        Helper.checkNull(resourceRoleIdGenerator, "resourceRoleIdGenerator");

        Helper.checkNull(notificationTypeIdGenerator, "notificationTypeIDGenerator");

        // sets the bundles
        this.notificationSearchBundle = notificationSearchBundle;

        this.notificationTypeSearchBundle = notificationTypeSearchBundle;

        this.resourceRoleSearchBundle = resourceRoleSearchBundle;

        this.resourceSearchBundle = resourceSearchBundle;

        // sets the IdGenerators
        this.resourceIdGenerator = resourceIdGenerator;

        this.resourceRoleIdGenerator = resourceRoleIdGenerator;

        this.notificationTypeIdGenerator = notificationTypeIdGenerator;

        // sets the persistence
        this.persistence = persistence;
    }

    /**
     * <p>
     * Creates a new PersistenceResourceManager by given ResourcePersistence,
     * SearchBundleManager and IDGeneratorFactory.
     * </p>
     *
     * <p>
     * <li> The SearchBundle fields is initialized by retrieving the
     * SearchBundles from the SearchBundlerManager using the constants defined
     * in this class. </li>
     *
     * <li> The IDGenerator fields is initialized by retrieving the IDGenerators
     * from the IDGeneratorFactory using the constants defined in this class.
     * </li>
     * </p>
     *
     * <p>
     * Note, in order to invoke the methods in this class, you should set the
     * searchable fields of the searchBundles from the searchBundleManager using
     * the method: setSearchableFields(Map) before parsing in the constructor.
     * </p>
     *
     * <pre>
     *  A sample configuration of the searchBundleManager:
     *
     *  &lt;CMConfig&gt;
     *    &lt;Config name=&quot;com.topcoder.search.builder.SearchBundleManager&quot;&gt;
     *        &lt;Property name=&quot;searchBundles&quot;&gt;
     *           &lt;Property name=&quot;bundle1&quot;&gt;
     *              &lt;Property name=&quot;type&quot;&gt;
     *                  &lt;Value&gt;Database&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;name&quot;&gt;
     *                  &lt;Value&gt;Resource Search Bundle&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;context&quot;&gt;
     *                  &lt;Value&gt;SELECT resource.resource_id
     *                          FROM resource
     *                          LEFT OUTER JOIN resource_submission
     *                          ON resource.resource_id = resource_submission.resource_id
     *                          LEFT OUTER JOIN resource_info
     *                          ON resource.resource_id = resource_info.resource_id
     *                          LEFT OUTER JOIN resource_info_type_lu
     *                          ON resource_info.resource_info_type_id =
     *                          resource_info_type_lu.resource_info_type_id
     *                          WHERE
     *                  &lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;DBNamcespace&quot;&gt;
     *                      &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;connectionProducerName&quot;&gt;
     *                      &lt;Value&gt;dbconnection&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;alias&quot;&gt;
     *                  &lt;Property name=&quot;resource_id&quot;&gt;
     *                      &lt;Value&gt;resource_id&lt;/Value&gt;
     *                  &lt;/Property&gt;
     *             &lt;/Property&gt;
     *          &lt;/Property&gt;
     *          &lt;Property name=&quot;bundle2&quot;&gt;
     *              &lt;Property name=&quot;type&quot;&gt;
     *                  &lt;Value&gt;Database&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;name&quot;&gt;
     *                  &lt;Value&gt;Resource Role Search Bundle&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;context&quot;&gt;
     *                  &lt;Value&gt;SELECT resource_role_id FROM resource_role_lu WHERE&lt;/Value&gt;
     *              &lt;/Property&gt;
     *             &lt;Property name=&quot;DBNamcespace&quot;&gt;
     *                      &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;connectionProducerName&quot;&gt;
     *                      &lt;Value&gt;dbconnection&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;alias&quot;&gt;
     *                     &lt;Property name=&quot;The ID&quot;&gt;
     *                          &lt;Value&gt;peopleID&lt;/Value&gt;
     *                     &lt;/Property&gt;
     *             &lt;/Property&gt;
     *          &lt;/Property&gt;
     *
     *          &lt;Property name=&quot;bundle3&quot;&gt;
     *              &lt;Property name=&quot;type&quot;&gt;
     *                  &lt;Value&gt;Database&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;name&quot;&gt;
     *                  &lt;Value&gt;Notification Search Bundle&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;context&quot;&gt;
     *                  &lt;Value&gt;SELECT external_ref_id, project_id, notification_type_id FROM
     *                  notification WHERE&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;DBNamcespace&quot;&gt;
     *                      &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;connectionProducerName&quot;&gt;
     *                      &lt;Value&gt;dbconnection&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;alias&quot;&gt;
     *                     &lt;Property name=&quot;The ID&quot;&gt;
     *                          &lt;Value&gt;peopleID&lt;/Value&gt;
     *                     &lt;/Property&gt;
     *             &lt;/Property&gt;
     *          &lt;/Property&gt;
     *
     *          &lt;Property name=&quot;bundle4&quot;&gt;
     *              &lt;Property name=&quot;type&quot;&gt;
     *                  &lt;Value&gt;Database&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;name&quot;&gt;
     *                  &lt;Value&gt;Notification Type Search Bundle&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;context&quot;&gt;
     *                  &lt;Value&gt;SELECT notification_type_id FROM notification_type_lu WHERE&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;DBNamcespace&quot;&gt;
     *                      &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;connectionProducerName&quot;&gt;
     *                      &lt;Value&gt;dbconnection&lt;/Value&gt;
     *              &lt;/Property&gt;
     *              &lt;Property name=&quot;alias&quot;&gt;
     *                     &lt;Property name=&quot;The ID&quot;&gt;
     *                          &lt;Value&gt;peopleID&lt;/Value&gt;
     *                     &lt;/Property&gt;
     *             &lt;/Property&gt;
     *          &lt;/Property&gt;
     *
     *        &lt;/Property&gt;
     *    &lt;/Config&gt;
     *  &lt;/CMConfig&gt;
     * </pre>
     *
     * @param persistence the persistence for Resources and related objects
     * @param searchBundleManager the manager containing the various SearchBundles needed
     *
     * @throws IllegalArgumentException if any search bundle or id generator is not available under
     *         the required names, or if any argument is null
     */
    public PersistenceResourceManager(ResourcePersistence persistence, SearchBundleManager searchBundleManager) {
        // validate the arguments
        Helper.checkNull(persistence, "persistence");
        Helper.checkNull(searchBundleManager, "searchBundleManager");

        // sets the persistence
        this.persistence = persistence;

        // sets the notificationSearchBundle via SearchBundleManager, or throw IllegalArgumentException if not found
        this.notificationSearchBundle = getSearchBundle(searchBundleManager, NOTIFICATION_SEARCH_BUNDLE_NAME);

        // sets the resourceRoleSearchBundle via SearchBundleManager, or throw IllegalArgumentException if not found
        this.resourceRoleSearchBundle = getSearchBundle(searchBundleManager, RESOURCE_ROLE_SEARCH_BUNDLE_NAME);

        // sets the resourceSearchBundle via SearchBundleManager, or throw IllegalArgumentException if not found
        this.resourceSearchBundle = getSearchBundle(searchBundleManager, RESOURCE_SEARCH_BUNDLE_NAME);

        // sets the notificationTypeSearchBundle via SearchBundleManager,
        // or throw IllegalArgumentException if not found
        this.notificationTypeSearchBundle = getSearchBundle(searchBundleManager,
                NOTIFICATION_TYPE_SEARCH_BUNDLE_NAME);

        // sets the notificationTypeIdGenerator via IDGeneratorFactory,
        // or throw IllegalArgumentException if the Generator is not available
        this.notificationTypeIdGenerator = getIdGenerator(NOTIFICATION_TYPE_ID_GENERATOR_NAME);

        // sets the resourceIdGenerator via IDGeneratorFactory,
        // or throw IllegalArgumentException if the Generator is not available
        this.resourceIdGenerator = getIdGenerator(RESOURCE_ID_GENERATOR_NAME);

        // sets the resourceRoleIdGenerator via IDGeneratorFactory,
        // or throw IllegalArgumentException if the Generator is not available
        this.resourceRoleIdGenerator = getIdGenerator(RESOURCE_ROLE_ID_GENERATOR_NAME);
    }

    /**
     * <p>
     * Updates the given resource in the persistence store.
     * </p>
     *
     * <p>
     * If the resource is new (id is UNSET_ID), then an id should be assigned and
     * the resource added to the persistence store. Otherwise the resource data
     * in the persistence store would be updated.
     * </p>
     *
     *
     * @param resource the resource to update
     * @param operator the operator making the update
     *
     * @throws IllegalArgumentException if a required field of the resource is not set (if resource.getResourceRole()
     *         is null), or if the resource role is associated with a phase type and the resource is not associated
     *         with a phase, or if resource or operator is null
     * @throws ResourcePersistenceException if there is an error updating the resource
     */
    public Resource updateResource(Resource resource, String operator) throws ResourcePersistenceException {
        Helper.checkNull(resource, "resource");
        Helper.checkNull(operator, "operator");
        validateResource(resource, "resource");

        // if the id is unset
        if (resource.getId() == Resource.UNSET_ID) {

            // create a new id
            long newId = getNextID(resourceIdGenerator);

            // set it back to the resource
            resource.setId(newId);

            // set the information
            setAudit(resource, operator, true);

            // create the new record using persistence
            persistence.addResource(resource);

        } else {

            // set the information
            setAudit(resource, operator, false);

            // update it via persistence
            persistence.updateResource(resource);
        }

		return resource;

    }

    /**
     * A helper method to set the create user, modification user and create date, and modification date
     *
     * of a AuditableResourceStructure (such as Resource, ResourceRole, Notification, NotificationType, etc).
     *
     * @param audit the AuditableResourceStructure to set
     * @param operator the operator of this processing
     * @param create a flag tells whether it is a new auditable resource structure
     */
    private void setAudit(AuditableResourceStructure audit, String operator, boolean create) {

        // gets the current date
        Date current = new Date();

        // if the audit is new (id not set)
        if (create) {

            // set the creation user and modification user
            audit.setCreationUser(operator);
            audit.setModificationUser(operator);

            // set the creation time and modification time
            audit.setModificationTimestamp(current);
            audit.setCreationTimestamp(current);

        } else {
            // set the modification user
            audit.setModificationUser(operator);

            // set the modification date
            audit.setModificationTimestamp(current);

        }
    }

    /**
     * <p>
     * Removes the given resource in the persistence store (by id).
     * If the id does not exist in the persistence, nothing would be removed.
     * </p>
     *
     * @param resource the resource to remove
     * @param operator the operator making the update
     *
     * @throws IllegalArgumentException if the id of the resource is UNSET_ID, or the resource or operator is null
     * @throws ResourcePersistenceException if there is an error updating the persistence store
     */
    public void removeResource(Resource resource, String operator) throws ResourcePersistenceException {
        Helper.checkNull(resource, "resource");
        Helper.checkNull(operator, "operator");

        // if the id is unset, throws IllegalArgumentException
        if (resource.getId() == Resource.UNSET_ID) {
            throw new IllegalArgumentException("The id of the resource must be set.");
        }

        // set the modification user and time
        setAudit(resource, operator, false);

        // use the persistence to remove the resource
        persistence.deleteResource(resource);
    }

    /**
     * <p>
     * Updates all resources for the given project.
     * </p>
     *
     * <p>
     * Any resources in the array with UNSET_ID are assigned an id and updated in
     * the persistence. Any resources with an id already assigned are updated in
     * the persistence. Any resources associated with the project in the
     * persistence store, but not appearing in the array are removed.
     * </p>
     *
     *
     * @param resources the resources associated with the project
     * @param project the project to update resources for
     * @param operator the operator making the update
     *
     * @throws IllegalArgumentException would be throw for the following cases:
     *         1. if resources is null or has null entries
     *         2. if project is &lt;= 0
     *         3. if operator is null
     *         4. if a required field of the resource is not set (if
     *            resource.getResourceRole() is null), or if the resource role
     *            is associated with a phase type and the resource role is not
     *            associated with a phase
     *         5. If resources in the array have a project that is not the same
     *            as the project argument
     *
     * @throws ResourcePersistenceException if there is an error updating the persistence store.
     */
    public Resource[] updateResources(Resource[] resources, long project, String operator)
        throws ResourcePersistenceException {

        Helper.checkNull(resources, "resources");
        Helper.checkNull(operator, "operator");

        Helper.checkLongPositive(project, "project");

        for (int i = 0; i < resources.length; i++) {
            Helper.checkNull(resources[i], "element in location of resources " + i);
            validateResource(resources[i], "element in location of resources " + i);

            // check if the project in the resource is the same as the project id
            // according to the forum:
            // https://software.topcoder.com/forum/c_forum_message.jsp?f=22404318&r=22884053
            // if the project is not set, IllegalArgumentException should be thrown
            if (resources[i].getProject() == null || resources[i].getProject().longValue() != project) {
                throw new IllegalArgumentException("The resource in location " + i
                        + " contains a project id which is not the same "
                        + "as the project argument or the project is null.");
            }
        }

        // a set of ids for checking the existence
        Set ids = new HashSet();

        // load the resources id to a set for existing searching later
        for (int i = 0; i < resources.length; i++) {
            ids.add(new Long(resources[i].getId()));
        }

        // find all the resources belonging to the project
        Filter filter = ResourceFilterBuilder.createProjectIdFilter(project);

        // use the searchResources to load the resources of the project
        Resource[] res = null;

        try {
            // search with the search bundle
            res = searchResources(filter);

        } catch (SearchBuilderException e) {
            // wrap the search builder exception to ResourcePresistenceException
            throw new ResourcePersistenceException("Error occur while searching the resources of the project.", e);
        }

        // for each resource in the returned result, check its existence,
        // if not exists in the argument resources, simply remove it
        for (int i = 0; i < res.length; i++) {
            if (!ids.contains(new Long(res[i].getId()))) {
                // if not exists, remove it
                removeResource(res[i], operator);
            }
        }

        // update all the resources in the argument that pass in
        for (int i = 0; i < resources.length; i++) {
            // update it
            resources[i] = updateResource(resources[i], operator);
        }

		return resources;
    }

    /**
     * <p>
     * Gets the resource with the given id from the persistence store. Returns null if there is
     * no resource with the given id.
     * </p>
     *
     * @param id the id of the resource to retrieve
     *
     * @return The loaded Resource
     *
     * @throws IllegalArgumentException if id is &lt;= 0
     * @throws ResourcePersistenceException if there is an error reading the persistence store
     */
    public Resource getResource(long id) throws ResourcePersistenceException {
        // although the persistence.loadResource would validates the id argument
        // but persistence does not belong to this component and still on developing,
        // so developer should not count on it and do the validation here.
        // so the helper check is not code redundancy.
        Helper.checkLongPositive(id, "id");
        return persistence.loadResource(id);
    }

    /**
     * <p>
     * Searches the resources in the persistence store using the given filter.
     * The filter can be formed using the field names and utility methods in ResourceFilterBuilder.
     * The return will always be a non-null (possibly 0 item) array.
     * </p>
     *
     * <p>
     * In order to invoke this method correctly, one should properly set the resourceSearchBundle.
     * <pre>
     * A sample of the context of the search bundle is:
     *                  SELECT resource.resource_id
     *                  FROM resource
     *                  LEFT OUTER JOIN resource_submission
     *                  ON resource.resource_id = resource_submission.resource_id
     *                  LEFT OUTER JOIN resource_info
     *                  ON resource.resource_id = resource_info.resource_id
     *                  LEFT OUTER JOIN resource_info_type_lu
     *                  ON resource_info.resource_info_type_id =
     *                  resource_info_type_lu.resource_info_type_id
     *                  WHERE
     * </pre>
     *
     * Note, make sure the selected column is only one column and of the type: long in the configuration.
     *
     * </p>
     *
     * @param filter the filter to use
     *
     * @return The loaded resources
     *
     * @throws IllegalArgumentException if filter is null
     * @throws ResourcePersistenceException if there is an error reading the persistence store
     * @throws SearchBuilderException if there is an error executing the filter
     * @throws SearchBuilderConfigurationException if the manager is not properly configured for searching
     */
    public Resource[] searchResources(Filter filter) throws ResourcePersistenceException,
        SearchBuilderException, SearchBuilderConfigurationException {

        Helper.checkNull(filter, "filter");

        // use the resourceSearchBundle to search
        CustomResultSet resultSet = getCustomResultSet(resourceSearchBundle.search(filter));

        return persistence.loadResources(resultSet);
    }

    /**
     * Does a class cast operation from Object to CustomResultSet.
     * If the Object is not an CustomResultSet, SearchBuilderConfigurationException should be thrown.
     *
     * @param returnResult the object to be casted
     *
     * @return a casted CustomResultSet
     *
     * @throws SearchBuilderConfigurationException if the object is not an instance of CustomResultSet
     */
    private CustomResultSet getCustomResultSet(Object returnResult) throws SearchBuilderConfigurationException {
        // it should be an instance of CustomResultSet
        if (!(returnResult instanceof CustomResultSet)) {
            throw new SearchBuilderConfigurationException(
                    "The returned result from the filter should be CustomResultSet.");
        }

        return (CustomResultSet) returnResult;
    }

    /**
     * <p>
     * Updates the given resource role in the persistence
     * store. If the resource role is new (id is UNSET_ID), then an id should be
     * assigned and the resource role added to the persistence store. Otherwise
     * the resource role data in the persistence store should be updated.
     * </p>
     *
     *
     * @param resourceRole the resource role to update
     * @param operator the operator making the update
     *
     * @throws IllegalArgumentException if a required field of the resource role is missing (i.e.
     *             name or description of the resource role is null), or if resourceRole or operator is null
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence
     */
    public void updateResourceRole(ResourceRole resourceRole, String operator) throws ResourcePersistenceException {

        Helper.checkNull(resourceRole, "resourceRole");
        Helper.checkNull(operator, "operator");
        validateResourceRole(resourceRole, "resourceRole");

        // if the id is unset
        if (resourceRole.getId() == ResourceRole.UNSET_ID) {

            // create a new id
            long newId = getNextID(resourceRoleIdGenerator);

            // set it back to the resourceRole
            resourceRole.setId(newId);

            // set the information
            setAudit(resourceRole, operator, true);

            // create the new record using persistence
            persistence.addResourceRole(resourceRole);

        } else {
            // set the information
            setAudit(resourceRole, operator, false);

            // update it via persistence
            persistence.updateResourceRole(resourceRole);
        }
    }

    /**
     * <p>
     * Removes a resource role from the persistence (by id).
     * </p>
     *
     * @param resourceRole the resource role to remove
     * @param operator the operator making the update
     *
     * @throws IllegalArgumentException if resourceRole or operator is null,
     *         or if the id of the resource role is UNSET_ID
     * @throws ResourcePersistenceException if there is an error updating the persistence store.
     */
    public void removeResourceRole(ResourceRole resourceRole, String operator) throws ResourcePersistenceException {

        Helper.checkNull(resourceRole, "resourceRole");
        Helper.checkNull(operator, "operator");

        // if the id is unset, throws IllegalArgumentException
        if (resourceRole.getId() == ResourceRole.UNSET_ID) {
            throw new IllegalArgumentException("The id of the resourceRole must be set.");
        }

        // set the modification user and time
        setAudit(resourceRole, operator, false);

        // use the persistence to remove the resource Role
        persistence.deleteResourceRole(resourceRole);
    }

    /**
     * <p>
     * Gets all resource roles in the persistence store.
     * </p>
     *
     *
     * @return All resource roles in the persistence store
     *
     * @throws ResourcePersistenceException if there is an error reading the persistence store.
     */
    public ResourceRole[] getAllResourceRoles() throws ResourcePersistenceException {
        // create the filter

        // Important Note:
        // Here we must use GreaterThanOrEqualToFilter(1), instead of GreaterThanFilter(0).
        // Because set have set the field using validator: LongValidator.isPositive().
        // So the parameter of value 0, passing in the filter may cause error
        Filter filter =
            new GreaterThanOrEqualToFilter(ResourceRoleFilterBuilder.RESOURCE_ROLE_ID_FIELD_NAME, new Long(1));

        // call the searchResourceRoles method to get the result
        try {

            return searchResourceRoles(filter);

        } catch (SearchBuilderException e) {

            // wrap search builder exception to ResourcePersistenceException
            throw new ResourcePersistenceException("Error occurs while searching the roles.", e);
        }
    }

    /**
     * <p>
     * Searches the resource roles in the persistence store using the given filter.
     * The filter can be formed using the field names and utility methods in
     * ResourceRoleFilterBuilder. The return will always be a non-null (possibly 0 item) array.
     * </p>
     *
     * <p>
     * In order to invoke this method correctly, one should properly set the resourceRoleSearchBundle.
     * <pre>
     * A sample of the context of the search bundle is:
     *                 SELECT resource_role_id FROM resource_role_lu WHERE
     * </pre>
     *
     * Note, make sure the selected column is only one column and of the type: long in the configuration.
     *
     * </p>
     *
     *
     * @param filter the filter to use
     *
     * @return The loaded resource roles
     *
     * @throws IllegalArgumentException if filter is null
     * @throws ResourcePersistenceException if there is an error reading the persistence store
     * @throws SearchBuilderException if there is an error executing the filter.
     * @throws SearchBuilderConfigurationException if the manager is not properly configured for searching
     */
    public ResourceRole[] searchResourceRoles(Filter filter)
        throws ResourcePersistenceException, SearchBuilderException, SearchBuilderConfigurationException {

        Helper.checkNull(filter, "filter");

        // use the resourceRoleSearchBundle to search
        CustomResultSet resultSet = getCustomResultSet(resourceRoleSearchBundle.search(filter));

        // load the resource roles from the resultSet
        return persistence.loadResourceRoles(resultSet);
    }

    /**
     * <p>
     * Adds a list of notifications for the given user ids to the persistence store.
     * All of the notification are added are for the given project and type.
     * </p>
     *
     * @param users the users to add notifications for
     * @param project the project the notifications apply to
     * @param notificationType the type of notifications to add
     * @param operator the operation making the update
     *
     * @throws IllegalArgumentException  if any item of users, project, or notificationType is &lt;= 0,
     *         or if operator or users is null
     * @throws ResourcePersistenceException if there is an error updating the persistence store
     */
    public void addNotifications(long[] users, long project, long notificationType, String operator)
        throws ResourcePersistenceException {

        Helper.checkNull(users, "users");
        Helper.checkNull(operator, "operator");
        Helper.checkLongPositive(project, "project");
        Helper.checkLongPositive(notificationType, "notificationType");

        for (int i = 0; i < users.length; i++) {
            // check if all the user ids are positive
            Helper.checkLongPositive(users[i], "element of users in location " + i);
        }

        // iterator through to add notifications
        for (int i = 0; i < users.length; i++) {
            persistence.addNotification(users[i], project, notificationType, operator);
        }
    }
    /**
     * <p>
     * Adds a list of notifications for the given projects to
     * the persistence store. All of the notification are added are for the
     * given user and type.
     * </p>
     *
     * @param user The user to add notifications for
     * @param projects The projects the notifications apply to
     * @param notificationType The type of notifications to add
     * @param operator The operation making the update
     *
     * @throws IllegalArgumentException
     *             If any item of projects, user, or notificationType is &lt;= 0,
     *             or operator or projects is null or projects is empty
     * @throws ResourcePersistenceException If there is an error updating the persistence store
     * @since 1.3
     */
    public void addNotifications(long user, long[] projects, long notificationType, String operator)
        throws ResourcePersistenceException {
        Helper.checkLongPositive(user, "user");
        Helper.checkLongPositive(notificationType, "notificationType");
        Helper.checkNull(user, "projects");
        Helper.checkNull(operator, "operator");
        //validateEmptyArray(projects, "project ids");
        for (int i = 0; i < projects.length; i++) {
            // check if all the user ids are positive
            Helper.checkLongPositive(projects[i], "element of projects in location " + i);
        }
        //persist for each project
        for (int i = 0; i < projects.length; i++) {
            this.persistence.addNotification(user, projects[i], notificationType, operator);
        }
    }

    /**
     * <p>
     * Removes a list of notifications for the given user ids from the persistence store.
     * The notifications removed are for the given project and type.
     * </p>
     *
     *
     * @param users the users to remove notifications for
     * @param project the project the notifications apply to
     * @param notificationType the type of notifications to remove
     * @param operator the operation making the update
     *
     * @throws IllegalArgumentException If any item of users, project, or notificationType is &lt;= 0,
     *         or if operator or users is null
     * @throws ResourcePersistenceException if there is an error updating the persistence store
     */
    public void removeNotifications(long[] users, long project, long notificationType, String operator)
        throws ResourcePersistenceException {

        Helper.checkNull(users, "users");
        Helper.checkNull(operator, "operator");
        Helper.checkLongPositive(project, "project");
        Helper.checkLongPositive(notificationType, "notificationType");

        for (int i = 0; i < users.length; i++) {
            // check if all the user ids are positive
            Helper.checkLongPositive(users[i], "element of users in location " + i);
        }
        for (int i = 0; i < users.length; i++) {
            // iterator through to remove notifications
            persistence.removeNotification(users[i], project, notificationType, operator);
        }
    }
    /**
     * <p>
     * Removes a list of notifications for the given projects
     * from the persistence store. The notifications removed are for the
     * given user and type.
     * </p>
     *
     * @param user The user to remove notifications for
     * @param projects The projects the notifications apply to
     * @param notificationType The type of notifications to remove
     * @param operator The operation making the update
     *
     * @throws IllegalArgumentException If any item of projects, user, or notificationType is &lt;= 0
     *          or the operator or projects is null or projects is empty
     * @throws ResourcePersistenceException If there is an error updating the persistence store
     * @since 1.3
     */
    public void removeNotifications(long user, long[] projects, long notificationType, String operator)
            throws ResourcePersistenceException {
        Helper.checkLongPositive(user, "user");
        Helper.checkLongPositive(notificationType, "notificationType");
        Helper.checkNull(user, "projects");
        Helper.checkNull(operator, "operator");
        //validateEmptyArray(projects, "project ids");
        for (int i = 0; i < projects.length; i++) {
            // check if all the user ids are positive
            Helper.checkLongPositive(projects[i], "element of projects in location " + i);
        }
        //remove for each project
        for (int i = 0; i < projects.length; i++) {
            this.persistence.removeNotification(user, projects[i], notificationType, operator);
        }
    }

    /**
     * <p>
     * Gets the user id for all notifications for the given project and type.
     * The return will always be a non-null (possibly 0 item) array.
     * </p>
     *
     * @param project the project to get notifications for
     * @param notificationType the type of notifications to retrieve
     *
     * @return the user ids of the notifications for the project and type
     *
     * @throws IllegalArgumentException if project or notificationType is &lt;= 0
     * @throws ResourcePersistenceException if there is an error reading the persistence store
     */
    public long[] getNotifications(long project, long notificationType) throws ResourcePersistenceException {
        return getNotifications(-1, project, notificationType,true);
    }

    /**
     * <p>
     * Gets the project ids for all notifications for the given
     * user and type. The return will always be a non-null (possibly 0 item)
     * array.
     * </p>
     *
     * @param user the user to get notifications for
     * @param notificationType the type of notifications to retrieve
     *
     * @return The project ids of the notifications for the user and type
     *
     * @throws IllegalArgumentException If user or notificationType is &lt;= 0
     * @throws ResourcePersistenceException If there is an error reading the persistence store
     * @since 1.3
     */
    public long[] getNotificationsForUser(long user, long notificationType) throws ResourcePersistenceException {
        return getNotifications(user, -1, notificationType, false);
    }

    /**
     * <p>
     * Gets the project ids for all notifications for the given
     * user and possible project ids. The return will always be a non-null (possibly 0 item)
     * array.
     * </p>
     *
     * @param user the user to get notifications for
     * @param notificationType toficiation type
     * @param projectIds possible project ids
     *
     * @return The project ids of the notifications for the user and possible project ids
     *
     * @throws IllegalArgumentException If user or notificationType is &lt;= 0
     * @throws ResourcePersistenceException If there is an error reading the persistence store
     * @since 1.3
     */
    public long[] getNotificationsForUser(long user, long notificationType, long[] projectIds) throws ResourcePersistenceException {
        Helper.checkLongPositive(user, "user");
        for (long projectId : projectIds) {
            Helper.checkLongPositive(projectId, "project");
        }

        List filterProjectList = new ArrayList();
        for (long projectId : projectIds) {
            Filter filter = NotificationFilterBuilder.createProjectIdFilter(projectId);
            filterProjectList.add(filter);
        }

        Filter filterUser = NotificationFilterBuilder.createExternalRefIdFilter(user);

        Filter filterType = NotificationFilterBuilder.createNotificationTypeIdFilter(notificationType);

        Filter filter = new AndFilter(new OrFilter(filterProjectList), filterUser);
        filter = new AndFilter(filterType, filter);

        return getNotifications(filter, false);
    }

    /**
     * <p>
     * This is a private methods for the getNotificationXXX method. If it is byProject, the the project id is used
     * and the return value is of user id. Otherwise, user is used and the return value is of project id.
     * </p>
     * @param user the user id, used when byProject is false
     * @param project the project id,used when byProject is true
     * @param notificationType the notification id
     * @param byProject true for getNotifications and false for getNotificationsForUser
     * @return the project ids or user ids
     * @throws ResourcePersistenceException If there is an error reading the persistence store
     * @since 1.3
     */
    private long[] getNotifications(long user, long project, long notificationType, boolean byProject)
        throws  ResourcePersistenceException {
        if (byProject) {
            Helper.checkLongPositive(project, "project");
        } else {
            Helper.checkLongPositive(user, "user");
        }
        Helper.checkLongPositive(notificationType, "notificationType");

        // creates the filters
        Filter filter1 = null;
        if (byProject) {
            filter1 = NotificationFilterBuilder.createProjectIdFilter(project);
        } else {
            filter1 = NotificationFilterBuilder.createExternalRefIdFilter(user);
        }
        Filter filter2 = NotificationFilterBuilder.createNotificationTypeIdFilter(notificationType);

        // generates the AND filter
        Filter andFilter = new AndFilter(filter1, filter2);

        return getNotifications(andFilter, byProject);
    }

    /**
     * <p>
     * This is a private methods for the getNotificationXXX method. If it is byProject, the project id is used
     * and the return value is of user id. Otherwise, user is used and the return value is of project id.
     * </p>
     * @param filter the filter to search notifications
     * @param byProject true for getNotifications and false for getNotificationsForUser
     * @return the project ids or user ids
     * @throws ResourcePersistenceException If there is an error reading the persistence store
     * @since 1.3
     */
    private long[] getNotifications(Filter filter, boolean byProject)
        throws  ResourcePersistenceException {
        // call the searchNotifications via the filters to get a list of notifications
        Notification[] notifications = null;

        try {
            notifications = searchNotifications(filter);
        } catch (SearchBuilderException e) {
            // wrap the search builder exception to ResourcePersistenceException
            throw new ResourcePersistenceException("Error occurs while searching the notifications.", e);
        }

        // extract the id from the returned array.
        long[] ids = new long[notifications.length];

        for (int i = 0; i < ids.length; i++) {
            // extract the ids
            if (byProject) {
                ids[i] = notifications[i].getExternalId();
            } else {
                ids[i] = notifications[i].getProject();
            }
        }
        return ids;
    }

    /**
     * <p>
     * searchNotifications: Searches the notifications in the persistence store
     * using the given filter. The filter can be formed using the field names
     * and utility methods in NotificationFilterBuilder. The return will always
     * be a non-null (possibly 0 item) array.
     * </p>
     *
     * <p>
     * Note, the configuration of the NotificaionSearchBundle should with the columns:
     * user id, project id, notification type id (in this order).
     * A sample configuration of the search bundle's context is:
     * <pre>
     * SELECT external_ref_id, project_id, notification_type_id FROM notification WHERE
     * </pre>
     *
     * </p>
     *

     * @param filter the filter to use
     *
     * @return The loaded notifications
     *
     * @throws IllegalArgumentException if filter is null
     *
     * @throws ResourcePersistenceException if there is an error reading the persistence store
     * @throws SearchBuilderException if there is an error executing the filter.
     * @throws SearchBuilderConfigurationException if the manager is not properly configured for searching
     */
    public Notification[] searchNotifications(Filter filter)
        throws ResourcePersistenceException, SearchBuilderException, SearchBuilderConfigurationException {

        Helper.checkNull(filter, "filter");

        // use the resourceSearchBundle to search
        CustomResultSet resultSet = getCustomResultSet(notificationSearchBundle.search(filter));

        return persistence.loadNotifications(resultSet);
    }

    /**
     * <p>
     * Updates the given notification type in the persistence store.
     * If the notification type is new (id is UNSET_ID), then an id should be assigned and the notification
     * type added to the persistence store. Otherwise the notification type data in the persistence store should
     * be updated.
     * </p>
     *
     * @param notificationType the notification type to update
     * @param operator the operator making the update
     *
     * @throws IllegalArgumentException If a required field of the notification type is missing (i.e.
     *         name or description of the notification type is null), or if notificationType or operator is null
     * @throws ResourcePersistenceException if there is an error updating the persistence
     */
    public void updateNotificationType(NotificationType notificationType, String operator)
        throws ResourcePersistenceException {

        Helper.checkNull(notificationType, "notificationType");
        Helper.checkNull(operator, "operator");
        validateNotificationType(notificationType, "notificationType");

        // if the id is unset
        if (notificationType.getId() == NotificationType.UNSET_ID) {

            // create a new id
            long newId = getNextID(notificationTypeIdGenerator);

            // set it back to the notificationType
            notificationType.setId(newId);

            // set the information
            setAudit(notificationType, operator, true);

            // create the new record using persistence
            persistence.addNotificationType(notificationType);

        } else {
            // set the information
            setAudit(notificationType, operator, false);

            // update it via persistence
            persistence.updateNotificationType(notificationType);
        }
    }

    /**
     * <p>
     * Removes a notification type from the persistence (by id).
     * </p>
     *
     * @param notificationType the notification type to remove
     * @param operator the operator making the update
     *
     * @throws IllegalArgumentException if notificationType or operator is null,
     *         or if the id of the notification type is UNSET_ID
     *
     * @throws ResourcePersistenceException
     *             If there is an error updating the persistence store.
     */
    public void removeNotificationType(NotificationType notificationType, String operator)
        throws ResourcePersistenceException {

        Helper.checkNull(notificationType, "resourceRole");
        Helper.checkNull(operator, "operator");

        // if the id is unset, throws IllegalArgumentException
        if (notificationType.getId() == ResourceRole.UNSET_ID) {
            throw new IllegalArgumentException("The id of the notificationType must be set.");
        }

        // set the modification user and time
        setAudit(notificationType, operator, false);

        // use the persistence to remove the resource Role
        persistence.deleteNotificationType(notificationType);
    }

    /**
     * <p>
     * Searches the notification types in the persistence store using the given filter.
     * The filter can be formed using the field names and utility methods in NotificationTypeFilterBuilder.
     * The return will always be a non-null (possibly 0 item) array.
     * </p>
     *
     * <p>
     * In order to invoke this method correctly, one should properly set the notificationTypeSearchBundle.
     * <pre>
     * A sample of the context of the search bundle is:
     *                 SELECT notification_type_id FROM notification_type_lu WHERE
     * </pre>
     *
     * Note, make sure the selected column is only one column and of the type: long in the configuration.
     *
     * </p>
     *
     *
     * @param filter the filter to use
     *
     * @return The loaded notification types
     *
     * @throws IllegalArgumentException if filter is null
     * @throws ResourcePersistenceException if there is an error reading the persistence store
     * @throws SearchBuilderException if there is an error executing the filter.
     * @throws SearchBuilderConfigurationException if the manager is not properly configured for searching
     */
    public NotificationType[] searchNotificationTypes(Filter filter)
        throws ResourcePersistenceException, SearchBuilderException, SearchBuilderConfigurationException {

        Helper.checkNull(filter, "filter");

        // use the notificationTypeSearchBundle to search
        CustomResultSet resultSet = getCustomResultSet(notificationTypeSearchBundle.search(filter));

        return persistence.loadNotificationTypes(resultSet);
    }

    /**
     * <p>
     * Gets all notification types in the persistence store.
     * </p>
     *
     *
     * @return All notification types in the persistence store
     *
     * @throws ResourcePersistenceException if there is an error reading the persistence store.
     */
    public NotificationType[] getAllNotificationTypes() throws ResourcePersistenceException {
        // create a GreaterThanFilter
        Filter filter =
            new GreaterThanOrEqualToFilter(NotificationTypeFilterBuilder.NOTIFICATION_TYPE_ID_FIELD_NAME, new Long(1));

        NotificationType[] notificationTypes = null;
        try {
            // use the search bundle and filter to get the result
            notificationTypes = searchNotificationTypes(filter);
        } catch (SearchBuilderException e) {
            // wrap it ResourcePersistenceException
            throw new ResourcePersistenceException("Error occurs while searching the notification types.", e);

        }
        return notificationTypes;
    }


    /**
     * Search resources by project id and role id
     *
     * @param projectId project id
     * @param roleId role id
     *
     * @return array of resoureces
     *
     * @throws ResourcePersistenceException if there is an error reading the persistence store.
     */
    public Resource[] searchResources(long projectId, long roleId) throws ResourcePersistenceException {

        try
        {
            AndFilter andFilter = new AndFilter(ResourceFilterBuilder.createProjectIdFilter(projectId), 
                                                ResourceFilterBuilder.createResourceRoleIdFilter(roleId));
            return searchResources(andFilter);
        }
        catch (SearchBuilderException e)
        {
            throw new ResourcePersistenceException("Error occurs while searchResources", e);

        }
    }

      /**
     * cehck if resource exists
     *
     * @param projectId project id
     * @param roleId role 
     * @param userId user id
     *
     * @return boolean
     *
     * @throws ResourcePersistenceException if there is an error reading the persistence store.
     */
    public boolean resourceExists(long projectId, long roleId, long userId) throws ResourcePersistenceException
    {
        return persistence.resourceExists(projectId, roleId, userId);
    }

    /**
     * <p>
     * A helper method to look up the given search bundle from the searchBundlemanager.
     * </p>
     *
     * @param searchBundleManager the searchBundleManager to look up
     * @param searchBundleName the name of the search bundle to get from a SearchBundleManager
     *
     * @return the specific search bundle
     *
     * @throws IllegalArgumentException if the searchBundleName does not found
     */
    private SearchBundle getSearchBundle(SearchBundleManager searchBundleManager, String searchBundleName) {
        // look up the search bundle from the searchBundleManager
        SearchBundle res = searchBundleManager.getSearchBundle(searchBundleName);

        // if the name of the bundle not found
        if (res == null) {
            // throws IllegalArgumentException
            throw new IllegalArgumentException("The searchBundle name: " + searchBundleName + " does not exists.");
        }

        return res;
    }

    /**
     * <p>
     * Returns an IDGenerator from the IDGeneratorFactory.
     * </p>
     *
     * @param idGeneratorName the name of the IDGenerator
     *
     * @return the id generator with the specific name
     *
     * @throws IllegalArgumentException if the IdGenerator is not available
     */
    private IDGenerator getIdGenerator(String idGeneratorName) {
        IDGenerator idGenerator = null;
        try {
            idGenerator = IDGeneratorFactory.getIDGenerator(idGeneratorName);
        } catch (IDGenerationException e) {

            // wrap it to IllegalArgumentException
            throw new IllegalArgumentException("The IDGenerator:" + idGeneratorName + " is not available.\n" , e);
        }

        return idGenerator;
    }

    /**
     * Returns the next id from a given IDGenerator and wrap the IDGeneratorException to ResourcePersistenceException.
     *
     * @param idGenerator the IDGenerator
     *
     * @return the next id
     *
     * @throws ResourcePersistenceException if any error occurs
     */
    private long getNextID(IDGenerator idGenerator) throws ResourcePersistenceException {
        long res = 0;
        try {
            res = idGenerator.getNextID();
        } catch (IDGenerationException e) {
            // wrap it to ResourcePersistenceException
            throw new ResourcePersistenceException("Cannot get the next id.", e);
        }
        return res;
    }

    /**
     * Validates a resource, IllegalArgumentException would be thrown if it is invalid.
     *
     * @param resource the resource to check
     * @param argName the variable name of the resource
     *
     * @throws IllegalArgumentException if a required field of the resource is not set (if resource.getResourceRole()
     *         is null), or if the resource role is associated with a phase type and the resource is not associated
     *         with a phase, or if resource is null
     */
    private static void validateResource(Resource resource, String argName) {
        // check null
        Helper.checkNull(resource, argName);

        // if the getReourceRole() is null
        if (resource.getResourceRole() == null) {
            throw new IllegalArgumentException("The resource role must be set.");
        }

        // if the resource role is associated with a phase type but the resource role is not associated
        // with a phase
        if (resource.getResourceRole().getPhaseType() != null && resource.getPhase() == null) {
            throw new IllegalArgumentException("The phase in the resource and the phase type in the resource role"
                    + "are not consistent.");
        }
    }

    /**
     * Validates a instance of ResourceRoles is valid.
     *
     * @param resourceRole the resourceRole to validate
     * @param name the name of the argument
     *
     * @throws IllegalArgumentException
     *             If a required field of the resource role is missing (i.e.
     *             name or description of the resource role is null)
     */
    private static void validateResourceRole(ResourceRole resourceRole, String name) {
        Helper.checkNull(resourceRole.getName(), "name of "  + name);
        Helper.checkNull(resourceRole.getDescription(), "description of " + name);
    }

    /**
     * Validates a instance of NotificationType is valid.
     *
     * @param notificationType the NotificationType to validate
     * @param name the name of the argument
     *
     * @throws IllegalArgumentException
     *             If a required field of the notificationType is missing (i.e.
     *             name or description of the notificationType is null)
     */
    private static void validateNotificationType(NotificationType notificationType, String name) {
        Helper.checkNull(notificationType.getName(), "name of "  + name);
        Helper.checkNull(notificationType.getDescription(), "description of " + name);
    }

    /**
     * Checks if the given values array is null/empty.
     * @param values the values array to check
     * @param name the name of the array
     * @throws IllegalArgumentException if the values array is null or empty
     */
    private static void validateEmptyArray(long[] values, String name) {
        Helper.checkNull(values, name);
        if (values.length == 0) {
            throw new IllegalArgumentException("The " + name + " array is empty.");
        }
    }
}
