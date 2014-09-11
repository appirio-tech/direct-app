/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests.persistence;

import java.io.File;

import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.PersistenceResourceManager;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.NotificationFilterBuilder;
import com.topcoder.management.resource.search.NotificationTypeFilterBuilder;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.management.resource.search.ResourceRoleFilterBuilder;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.util.config.ConfigManager;

/**
 * Failure tests for <code>PersistenceResourceManager</code>.
 * <p>
 * For this test case, invalid configuration files are used.
 * </p>
 *
 * @see PersistenceResourceManagerFailureTest_InvalidSearchBundle
 *
 * @author mayi
 * @author liuliquan
 * @version 1.1
 * @since 1.0
 */
public class PersistenceResourceManagerFailureTest extends PersistenceResourceManagerBaseFailureTestCase {

    /**
     * The config file path for the DBConnectionFactory.
     */
    private static final String DB_CONNECTION_CONFIG = "failure" + File.separator + "ConnectionFactory.xml";

    /**
     * The config file path for the SearchBundleManager.
     */
    private static final String SEARCH_BUNDLE_MANAGER_CONFIG = "failure" + File.separator + "SearchBundleManager.xml";

    /**
     * Load configurations used for this test case.
     *
     * @throws Exception to JUnit.
     */
    protected void loadConfigurations() throws Exception {
        clearAllConfigurations();

        ConfigManager cm = ConfigManager.getInstance();
        cm.add(DB_CONNECTION_CONFIG);
        cm.add(SEARCH_BUNDLE_MANAGER_CONFIG);
    }

    /**
     * Test PersistenceResourceManager(ResourcePersistence, SearchBundle, SearchBundle,
     * SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     * <p>IllegalArgumentException should be thrown for null <code>persistence</code>.</p>
     */
    public void testConstructor1_NullPersistence() {
        try {
            new PersistenceResourceManager(null, resourceSearchBundle,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("Should throw IllegalArgumentException for null persistence.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test PersistenceResourceManager(ResourcePersistence, SearchBundle, SearchBundle,
     * SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     * <p>IllegalArgumentException should be thrown for null <code>resourceSearchBundle</code>.</p>
     */
    public void testConstructor1_NullResourceSearchBundle() {
        try {
            new PersistenceResourceManager(persistence, null,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("Should throw IllegalArgumentException for null resourceSearchBundle.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test PersistenceResourceManager(ResourcePersistence, SearchBundle, SearchBundle,
     * SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     * <p>IllegalArgumentException should be thrown for null
     * <code>resourceRoleSearchBundle</code>.</p>
     */
    public void testConstructor1_NullResourceRoleSearchBundle() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    null, notificationSearchBundle,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("Should throw IllegalArgumentException for null resourceRoleSearchBundle.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test PersistenceResourceManager(ResourcePersistence, SearchBundle, SearchBundle,
     * SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     * <p>IllegalArgumentException should be thrown for null
     * <code>notificationSearchBundle</code>.</p>
     */
    public void testConstructor1_NullNotificationSearchBundle() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    resourceRoleSearchBundle, null,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("Should throw IllegalArgumentException for null notificationSearchBundle.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test PersistenceResourceManager(ResourcePersistence, SearchBundle, SearchBundle,
     * SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     * <p>IllegalArgumentException should be thrown for null
     * <code>notificationTypeSearchBundle</code>.</p>
     */
    public void testConstructor1_NullNotificationTypeSearchBundle() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    null, resourceIdGenerator,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("Should throw IllegalArgumentException for null notificationTypeSearchBundle.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test PersistenceResourceManager(ResourcePersistence, SearchBundle, SearchBundle,
     * SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     * <p>IllegalArgumentException should be thrown for null <code>resourceIdGenerator</code>.</p>
     */
    public void testConstructor1_NullResourceIdGenerator() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    notificationTypeSearchBundle, null,
                    resourceRoleIdGenerator, notificationTypeIdGenerator);
            fail("Should throw IllegalArgumentException for null resourceIdGenerator.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test PersistenceResourceManager(ResourcePersistence, SearchBundle, SearchBundle,
     * SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     * <p>IllegalArgumentException should be thrown for null
     * <code>resourceRoleIdGenerator</code>.</p>
     */
    public void testConstructor1_NullResourceRoleIdGenerator() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    null, notificationTypeIdGenerator);
            fail("Should throw IllegalArgumentException for null resourceRoleIdGenerator.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test PersistenceResourceManager(ResourcePersistence, SearchBundle, SearchBundle,
     * SearchBundle, SearchBundle, IDGenerator, IDGenerator, IDGenerator).
     * <p>IllegalArgumentException should be thrown for null
     * <code>notificationTypeIdGenerator</code>.</p>
     */
    public void testConstructor1_NullNotificationTypeIdGenerator() {
        try {
            new PersistenceResourceManager(persistence, resourceSearchBundle,
                    resourceRoleSearchBundle, notificationSearchBundle,
                    notificationTypeSearchBundle, resourceIdGenerator,
                    resourceRoleIdGenerator, null);
            fail("Should throw IllegalArgumentException for null notificationTypeIdGenerator.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>PersistenceResourceManager(ResourcePersistence, SearchBundleManager)</code>
     * with null persistence.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testPersistenceResourceManager2_NullPersistence() {
        try {
            new PersistenceResourceManager(null, searchBundleManager);
            fail("Should throw IllegalArgumentException for null persistence.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>PersistenceResourceManager(ResourcePersistence, SearchBundleManager)</code>
     * with null searchBundleManager.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testPersistenceResourceManager2_NullSearchBundleManager() {
        try {
            new PersistenceResourceManager(persistence, null);
            fail("Should throw IllegalArgumentException for null searchBundleManager.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResource(Resource, String)</code> with null resource.
     * <p>It should throw IllegalArgumentException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_NullResource() throws Exception {
        try {
            manager.updateResource(null, "operator");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResource(Resource, String)</code> with null operator.
     * <p>It should throw IllegalArgumentException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_NullOperator() throws Exception {
        Resource resource = new Resource();
        ResourceRole resourceRole = new ResourceRole();
        resource.setResourceRole(resourceRole);

        try {
            manager.updateResource(resource, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResource(Resource, String)</code> when the resource doesn't
     * contain role.
     * <p>It should throw IllegalArgumentException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_NoResourceRole() throws Exception {
        Resource resource = new Resource();

        try {
            manager.updateResource(resource, "operator");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResource(Resource, String)</code> when the associated resource
     * role is invalid.
     * <p>It should throw IllegalArgumentException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_InvalidResourceRole() throws Exception {
        Resource resource = new Resource();
        ResourceRole resourceRole = new ResourceRole();
        resourceRole.setPhaseType(new Long(1234567L));
        resource.setResourceRole(resourceRole);

        try {
            manager.updateResource(resource, "operator");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResource(Resource, String)</code> when the persistence
     * failed to update the resource.
     * <p>It should throw ResourcePersistenceException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResource_PersistenceException() throws Exception {
        Resource resource = new Resource();
        ResourceRole resourceRole = new ResourceRole();
        resource.setResourceRole(resourceRole);

        try {
            manager.updateResource(resource, "operator");
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>removeResource(Resource, String)</code> with null resource.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_NullResource() throws Exception {
        try {
            manager.removeResource(null, "operator");
            fail("Should throw IllegalArgumentException for null arg.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeResource(Resource, String)</code> for null operator.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_NullOperator() throws Exception {
        Resource resource = new Resource();
        resource.setId(1234);

        try {
            manager.removeResource(resource, null);
            fail("Should throw IllegalArgumentException for null arg.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeResource(Resource, String)</code> when the id
     * of resource is not set.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_UnSetId() throws Exception {
        Resource resource = new Resource();

        try {
            manager.removeResource(resource, "operator");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeResource(Resource, String)</code> when the
     * underlying persistence failed to remove.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResource_PersistenceException() throws Exception {
        Resource resource = new Resource();
        resource.setId(1234);

        try {
            manager.removeResource(resource, "operator");
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResources(Resource[], long, String)</code> with null resources
     * array.
     * <p>It should throw IllegalArgumentException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_NullResourcesArray() throws Exception {
        try {
            manager.updateResources(null, 1, "operator");
            fail("Should throw IllegalArgumentException for null array.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResources(Resource[], long, String)</code> with null resources
     * element in array.
     * <p>It should throw IllegalArgumentException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_NullResourcesElement() throws Exception {
        Resource[] resources = new Resource[2];
        resources[1] = new Resource();
        resources[1].setResourceRole(new ResourceRole());
        resources[1].setProject(new Long(1));

        try {
            manager.updateResources(resources, 1, "operator");
            fail("Should throw IllegalArgumentException for null array.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResources(Resource[], long, String)</code> with negative project
     * id.
     * <p>It should throw IllegalArgumentException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_NegativeProjectId() throws Exception {
        Resource[] resources = new Resource[1];
        resources[0] = new Resource();
        resources[0].setResourceRole(new ResourceRole());

        try {
            manager.updateResources(resources, -1, "operator");
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResources(Resource[], long, String)</code> with null operator.
     * <p>It should throw IllegalArgumentException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_NullOpeartor() throws Exception {
        Resource[] resources = new Resource[1];
        resources[0] = new Resource();
        resources[0].setResourceRole(new ResourceRole());
        resources[0].setProject(new Long(1));

        try {
            manager.updateResources(resources, 1, null);
            fail("Should throw IllegalArgumentException for null operator.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }


    /**
     * Test <code>updateResources(Resource[], long, String)</code> when one resource
     * doesn't contain role.
     * <p>It should throw IllegalArgumentException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_NoResourceRole() throws Exception {
        Resource[] resources = new Resource[2];
        resources[0] = new Resource();
        resources[0].setResourceRole(new ResourceRole());
        resources[0].setProject(new Long(1));

        resources[1] = new Resource();
        resources[1].setProject(new Long(1));

        try {
            manager.updateResources(resources, 1, "operator");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResources(Resource[], long, String)</code> when the associated resource
     * role is invalid.
     * <p>It should throw IllegalArgumentException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_InvalidResourceRole() throws Exception {
        Resource[] resources = new Resource[2];
        resources[0] = new Resource();
        resources[0].setResourceRole(new ResourceRole());
        resources[0].setProject(new Long(1));

        resources[1] = new Resource();
        ResourceRole resourceRole = new ResourceRole();
        resourceRole.setPhaseType(new Long(1234567L));
        resources[1].setResourceRole(resourceRole);
        resources[1].setProject(new Long(1));

        try {
            manager.updateResources(resources, 1, "operator");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResources(Resource[], long, String)</code> when the associated resource
     * role is invalid.
     * <p>It should throw IllegalArgumentException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_InvalidProjectId() throws Exception {
        Resource[] resources = new Resource[2];
        resources[0] = new Resource();
        resources[0].setResourceRole(new ResourceRole());
        resources[0].setProject(new Long(1));

        resources[1] = new Resource();
        resources[1].setResourceRole(new ResourceRole());
        resources[1].setProject(new Long(100));

        try {
            manager.updateResources(resources, 1, "operator");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResources(Resource[], long, String)</code> when persistence
     * failed to update resources.
     * <p>It should throw ResourcePersistenceException.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResources_PersistenceException() throws Exception {
        Resource[] resources = new Resource[1];
        resources[0] = new Resource();
        resources[0].setResourceRole(new ResourceRole());
        resources[0].setProject(new Long(1));

        try {
            manager.updateResources(resources, 1, "operator");
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>getResource(long)</code> with non-positive id.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetResource_NonPositiveId() throws Exception {
        try {
            manager.getResource(0);
            fail("Should throw IllegalArgumentException with non-positive id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>getResource(long)</code> when the underlying persistence failed to
     * get resource.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetResource_PersistenceException() throws Exception {
        try {
            manager.getResource(10);
            fail("Should throw ResourcePersistenceException with non-positive id.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>searchResources(Filter)</code> with null filter.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchResources_NullFilter() throws Exception {
        try {
            manager.searchResources(null);
            fail("Should throw IllegalArgumentException for null filter.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>searchResources(Filter)</code> with invalid filter.
     * <p>SearchBuilderException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchResources_InvalidFilter() throws Exception {
        try {
            manager.searchResources(
                    ResourceRoleFilterBuilder.createPhaseTypeIdFilter(123875));
            fail("Should throw SearchBuilderException for invalid filter.");
        } catch (SearchBuilderException e) {
            // pass
        }
    }

    /**
     * Test <code>searchResources(Filter)</code> when the underlying persistence
     * failed to search.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchResources_PersistenceException() throws Exception {
        try {
            manager.searchResources(
                    ResourceFilterBuilder.createPhaseIdFilter(1));
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResourceRole(ResourceRole, String)</code> with null resourceRole.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_NullResourceRole() throws Exception {
        try {
            manager.updateResourceRole(null, "operator");
            fail("Should throw IllegalArgumentException null resource role.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }


    /**
     * Test <code>updateResourceRole(ResourceRole, String)</code> with null operator.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_NullOperator() throws Exception {
        ResourceRole role = new ResourceRole();
        role.setName("name");
        role.setDescription("description");

        try {
            manager.updateResourceRole(role, null);
            fail("Should throw IllegalArgumentException for null operator.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResourceRole(ResourceRole, String)</code> when the resourceRole
     * doesn't contain name.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_MissName() throws Exception {
        ResourceRole role = new ResourceRole();
        role.setDescription("description");

        try {
            manager.updateResourceRole(role, "operator");
            fail("Should throw IllegalArgumentException for missing name.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResourceRole(ResourceRole, String)</code> when the resourceRole
     * doesn't contain description.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_MissDescription() throws Exception {
        ResourceRole role = new ResourceRole();
        role.setName("name");

        try {
            manager.updateResourceRole(role, "operator");
            fail("Should throw IllegalArgumentException for missing description.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateResourceRole(ResourceRole, String)</code> when the persistence
     * failed to update the resourceRole.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole_PersistenceException() throws Exception {
        ResourceRole role = new ResourceRole();
        role.setName("name");
        role.setDescription("description");

        try {
            manager.updateResourceRole(role, "operator");
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>removeResourceRole(ResourceRole, String)</code> with null role.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_NullResourceRole() throws Exception {
        try {
            manager.removeResourceRole(null, "operator");
            fail("Should throw IllegalArgumentException for null arg.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeResourceRole(ResourceRole, String)</code> for null operator.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_NullOperator() throws Exception {
        ResourceRole role = new ResourceRole();
        role.setId(1234);

        try {
            manager.removeResourceRole(role, null);
            fail("Should throw IllegalArgumentException for null arg.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeResourceRole(ResourceRole, String)</code> when the id
     * of role is not set.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_UnSetId() throws Exception {
        ResourceRole role = new ResourceRole();

        try {
            manager.removeResourceRole(role, "operator");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeResourceRole(ResourceRole, String)</code> when the
     * underlying persistence failed to remove.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveResourceRole_PersistenceException() throws Exception {
        ResourceRole role = new ResourceRole();
        role.setId(1234);

        try {
            manager.removeResourceRole(role, "operator");
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>searchResourceRoles(Filter)</code> with null filter.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchResourceRoles_NullFilter() throws Exception {
        try {
            manager.searchResourceRoles(null);
            fail("Should throw IllegalArgumentException for null filter.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>searchResourceRoles(Filter)</code> with invalid filter.
     * <p>SearchBuilderException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchResourceRoles_InvalidFilter() throws Exception {
        try {
            manager.searchResourceRoles(
                    ResourceFilterBuilder.createExtensionPropertyValueFilter("foo"));
            fail("Should throw SearchBuilderException for invalid filter.");
        } catch (SearchBuilderException e) {
            // pass
        }
    }

    /**
     * Test <code>searchResourceRoles(Filter)</code> when the underlying persistence
     * failed to search.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchResourceRoles_PersistenceException() throws Exception {
        try {
            manager.searchResourceRoles(
                    ResourceRoleFilterBuilder.createNameFilter("foo"));
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }


    /**
     * Test <code>addNotifications(long[], long, long, String)</code> with null users.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_NullUsers() throws Exception {
        try {
            manager.addNotifications(null, 1, 1, "operator");
            fail("Should throw IllegalArgumentException for null users.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>addNotifications(long[], long, long, String)</code> with null operator.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_NullOperator() throws Exception {
        long[] users = new long[]{1, 2, 3};

        try {
            manager.addNotifications(users, 1, 1, null);
            fail("Should throw IllegalArgumentException for null operator.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>addNotifications(long[], long, long, String)</code> with non-positive
     * project id.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_NonPositiveProject() throws Exception {
        long[] users = new long[]{1, 2, 3};

        try {
            manager.addNotifications(users, 0, 1, "operator");
            fail("Should throw IllegalArgumentException for non-positive project.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>addNotifications(long[], long, long, String)</code> with non-positive
     * notificationType id.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_NonPositiveNotificationType() throws Exception {
        long[] users = new long[]{1, 2, 3};

        try {
            manager.addNotifications(users, 1, -1, "operator");
            fail("Should throw IllegalArgumentException for non-positive notificationType.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>addNotifications(long[], long, long, String)</code> when the underlying
     * persistence failed to add notifications.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testAddNotifications_PersistenceException() throws Exception {
        long[] users = new long[]{1, 2, 3};

        try {
            manager.addNotifications(users, 1, 1, "operator");
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>removeNotifications(long[], long, long, String)</code> with null users.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_NullUsers() throws Exception {
        try {
            manager.removeNotifications(null, 1, 1, "operator");
            fail("Should throw IllegalArgumentException for null users.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeNotifications(long[], long, long, String)</code> with null operator.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_NullOperator() throws Exception {
        long[] users = new long[]{1, 2, 3};

        try {
            manager.removeNotifications(users, 1, 1, null);
            fail("Should throw IllegalArgumentException for null operator.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeNotifications(long[], long, long, String)</code> with non-positive
     * project id.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_NonPositiveProject() throws Exception {
        long[] users = new long[]{1, 2, 3};

        try {
            manager.removeNotifications(users, 0, 1, "operator");
            fail("Should throw IllegalArgumentException for non-positive project.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeNotifications(long[], long, long, String)</code> with non-positive
     * notificationType id.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_NonPositiveNotificationType() throws Exception {
        long[] users = new long[]{1, 2, 3};

        try {
            manager.removeNotifications(users, 1, -1, "operator");
            fail("Should throw IllegalArgumentException for non-positive notificationType.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeNotifications(long[], long, long, String)</code> when the underlying
     * persistence failed to remove notifications.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotifications_PersistenceException() throws Exception {
        long[] users = new long[]{1, 2, 3};

        try {
            manager.removeNotifications(users, 1, 1, "operator");
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>getNotifications(long, long)</code> with non-positive project id.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetNotifications_NonPositiveProjectId() throws Exception {
        try {
            manager.getNotifications(-1, 1);
            fail("Should throw IllegalArgumentException with non-positive project id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>getNotifications(long, long)</code> with non-positive notificationType
     * id.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testGetNotifications_NonPositiveNotificationTypeId() throws Exception {
        try {
            manager.getNotifications(1, 0);
            fail("Should throw IllegalArgumentException with non-positive notification type.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>searchNotifications(Filter)</code> with null filter.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchNotifications_NullFilter() throws Exception {
        try {
            manager.searchNotifications(null);
            fail("Should throw IllegalArgumentException for null filter.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>searchNotifications(Filter)</code> with invalid filter.
     * <p>SearchBuilderException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchNotifications_InvalidFilter() throws Exception {
        try {
            manager.searchNotifications(
                    ResourceFilterBuilder.createExtensionPropertyValueFilter("foo"));
            fail("Should throw SearchBuilderException for invalid filter.");
        } catch (SearchBuilderException e) {
            // pass
        }
    }

    /**
     * Test <code>searchNotifications(Filter)</code> when the underlying persistence
     * failed to search.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchNotifications_PersistenceException() throws Exception {
        try {
            manager.searchNotifications(
                    NotificationFilterBuilder.createProjectIdFilter(10));
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>updateNotificationType(NotificationType, String)</code> with null resourceRole.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_NullNotificationType() throws Exception {
        try {
            manager.updateNotificationType(null, "operator");
            fail("Should throw IllegalArgumentException null resource role.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateNotificationType(NotificationType, String)</code> with null operator.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_NullOperator() throws Exception {
        NotificationType type = new NotificationType();
        type.setName("name");
        type.setDescription("description");

        try {
            manager.updateNotificationType(type, null);
            fail("Should throw IllegalArgumentException for null operator.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateNotificationType(NotificationType, String)</code> when the resourceRole
     * doesn't contain name.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_MissName() throws Exception {
        NotificationType type = new NotificationType();
        type.setDescription("description");

        try {
            manager.updateNotificationType(type, "operator");
            fail("Should throw IllegalArgumentException for missing name.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateNotificationType(NotificationType, String)</code> when the resourceRole
     * doesn't contain description.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_MissDescription() throws Exception {
        NotificationType type = new NotificationType();
        type.setName("name");

        try {
            manager.updateNotificationType(type, "operator");
            fail("Should throw IllegalArgumentException for missing description.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>updateNotificationType(NotificationType, String)</code> when the persistence
     * failed to update the resourceRole.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType_PersistenceException() throws Exception {
        NotificationType type = new NotificationType();
        type.setName("name");
        type.setDescription("description");

        try {
            manager.updateNotificationType(type, "operator");
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>removeNotificationType(NotificationType, String)</code> with null type.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_NullNotificationType() throws Exception {
        try {
            manager.removeNotificationType(null, "operator");
            fail("Should throw IllegalArgumentException for null arg.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeNotificationType(NotificationType, String)</code> for null operator.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_NullOperator() throws Exception {
        NotificationType type = new NotificationType();
        type.setId(1234);

        try {
            manager.removeNotificationType(type, null);
            fail("Should throw IllegalArgumentException for null arg.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeNotificationType(NotificationType, String)</code> when the id
     * of type is not set.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_UnSetId() throws Exception {
        NotificationType type = new NotificationType();

        try {
            manager.removeNotificationType(type, "operator");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>removeNotificationType(NotificationType, String)</code> when the
     * underlying persistence failed to remove.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testRemoveNotificationType_PersistenceException() throws Exception {
        NotificationType type = new NotificationType();
        type.setId(1234);

        try {
            manager.removeNotificationType(type, "operator");
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

    /**
     * Test <code>searchNotificationTypes(Filter)</code> with null filter.
     * <p>IllegalArgumentException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchNotificationTypes_NullFilter() throws Exception {
        try {
            manager.searchNotificationTypes(null);
            fail("Should throw IllegalArgumentException for null filter.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>searchNotificationTypes(Filter)</code> with invalid filter.
     * <p>SearchBuilderException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchNotificationTypes_InvalidFilter() throws Exception {
        try {
            manager.searchNotificationTypes(
                    ResourceFilterBuilder.createExtensionPropertyValueFilter("foo"));
            fail("Should throw SearchBuilderException for invalid filter.");
        } catch (SearchBuilderException e) {
            // pass
        }
    }

    /**
     * Test <code>searchNotificationTypes(Filter)</code> when the underlying persistence
     * failed to search.
     * <p>ResourcePersistenceException should be thrown.</p>
     *
     * @throws Exception to JUnit.
     */
    public void testSearchNotificationTypes_PersistenceException() throws Exception {
        try {
            manager.searchNotificationTypes(
                    NotificationTypeFilterBuilder.createNameFilter("foo"));
            fail("Should throw ResourcePersistenceException.");
        } catch (ResourcePersistenceException e) {
            // pass
        }
    }

}
