/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql;

import java.io.File;
import java.util.Arrays;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.util.config.ConfigManager;

/**
 * Unit test cases for class <code>AbstractResourcePersistence </code>. In this test class, the
 * functionality of this component will be tested.
 *
 * @author Chenhong, mittu, TCSDEVELOPER
 * @version 1.2
 * @since 1.1
 */
public class TestAbstractResourcePersistence extends TestCase {

    /**
     * Represents the AbstractResourcePersistence instance for test.
     */
    protected AbstractResourcePersistence persistence1, persistence2;

    /**
     * Represents the DBConnectionFactory instance for test.
     */
    protected DBConnectionFactory factory = null;

    /**
     * Set up the environment. Create AbstractResourcePersistence instance for test.
     *
     * @throws Exception to junit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        DBTestUtil.clearConfigManager();

        File file = new File("test_files/DBConnectionFactory.xml");

        cm.add(file.getAbsolutePath());

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

        factory = new DBConnectionFactoryImpl(namespace);
        persistence1 = new SqlResourcePersistence(factory, "sysuser");
        persistence2 = new SqlResourcePersistence(factory, "sysuser");

        DBTestUtil.clearTables();

        DBTestUtil.setupDatbase();
    }

    /**
     * Tear down the environment. Clear all namespaces in the config manager.
     *
     * @throws Exception to junit.
     */
    public void tearDown() throws Exception {
        DBTestUtil.clearTables();
        DBTestUtil.clearConfigManager();
    }

    /**
     * Test constructor <code> AbstractResourcePersistence(DBConnectionFactory) </code>.
     */
    public void testSqlResourcePersistenceDBConnectionFactory() {
        assertNotNull("The AbstractResourcePersistence instance should be created.", persistence1);
    }

    /**
     * Test constructor <code>AbstractResourcePersistence(DBConnectionFactory, String) </code>. The
     * parameter connectionName can be null.
     */
    public void testSqlResourcePersistenceDBConnectionFactoryString_() {
        assertNotNull("The AbstractResourcePersistence instance should be created.", persistence2);
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>.
     *
     * In this test case, the resource has one submission and no external properties.
     *
     * @throws Exception to junit.
     */
    public void testAddResource_1() throws Exception {

        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        ResourceRole role = DBTestUtil.createResourceRole(5);

        r.addSubmission(new Long(121));

        persistence1.addResourceRole(role);

        // add resource.
        persistence1.addResource(r);

        // get back the resource instance from persistence.
        Resource ret = persistence1.loadResource(r.getId());
        assertNotNull("The resource got back should not be null.", ret);

        assertTrue("The external properties should be empty.", ret.getAllProperties().isEmpty());
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>.
     *
     * In this test case, the resource has null submission and no external properties, project and phase is
     * also not set.
     * Exception thrown when audit failed.
     *
     * @throws Exception to junit.
     */
    public void testAddResourceFailure() throws Exception {
        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        ResourceRole role = DBTestUtil.createResourceRole(5);

        r.setPhase(null);
        r.setProject(null);
        r.setSubmissions(new Long[0]);

        persistence1.addResourceRole(role);

        try {
            // add resource.
            persistence1.addResource(r);
            fail("Cannot go here");
        } catch (ResourcePersistenceException e) {
            // OK
        }
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>.
     *
     * In this test case, the submissions will be set and also with external properties.
     *
     * @throws Exception to junit.
     */
    public void testAddResource_3() throws Exception {
        DBTestUtil.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        r.addSubmission(new Long(121));
        r.addSubmission(new Long(1200));

        r.setProperty("name", new Integer(100));

        ResourceRole role = DBTestUtil.createResourceRole(5);

        persistence1.addResourceRole(role);

        // add resource.
        persistence1.addResource(r);

        // get back the resource instance from persistence.
        Resource ret = persistence1.loadResource(r.getId());

        assertNotNull("The resource got back should not be null.", ret);
        Long[] submissions = ret.getSubmissions();

        assertEquals("The submissions should be as expected", 2, submissions.length);
        if (submissions[0].longValue() == 121) {
            assertEquals("The submissions should be as expected", 1200, submissions[1].longValue());
        } else {
            assertEquals("The submissions should be as expected", 121, submissions[1].longValue());
        }
        assertEquals("The value for'name' should be 100", new Integer(100), new Integer(ret.getProperty(
            "name").toString()));
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>.
     *
     * In this test case, the submission will be set and also with multiple external properties.
     *
     * @throws Exception to junit.
     */
    public void testAddResource_4() throws Exception {
        DBTestUtil.insertIntoResource_info_type_lu(11, "name");
        DBTestUtil.insertIntoResource_info_type_lu(12, "weight");
        DBTestUtil.insertIntoResource_info_type_lu(13, "height");
        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        r.addSubmission(new Long(121));
        r.addSubmission(new Long(1200));

        r.setProperty("name", "kaka");
        r.setProperty("height", "185cm");
        r.setProperty("weight", "85kg");

        ResourceRole role = DBTestUtil.createResourceRole(5);

        persistence1.addResourceRole(role);

        // add resource.
        persistence1.addResource(r);

        // get back the resource instance from persistence.
        Resource ret = persistence1.loadResource(r.getId());

        assertNotNull("The resource got back should not be null.", ret);

        Long[] submissions = ret.getSubmissions();
        assertEquals("The submissions should be as expected", 2, submissions.length);
        if (submissions[0].longValue() == 121) {
            assertEquals("The submissions should be as expected", 1200, submissions[1].longValue());
        } else {
            assertEquals("The submissions should be as expected", 121, submissions[1].longValue());
        }

        assertEquals("The value for'name' should be kaka", "kaka", ret.getProperty("name"));
        assertEquals("The value for'height' should be 185cm", "185cm", ret.getProperty("height"));
        assertEquals("The value for'weight' should be 85kg", "85kg", ret.getProperty("weight"));
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>.
     *
     * In this test case, the resource instance will have property which is not configged in the
     * resource_info_type_lu table, such properties will be ignored during inserting the resource.
     *
     * @throws Exception to junit.
     */
    public void testAddResource_5() throws Exception {
        // Only have 'name' and 'weight' resource_info_type.
        DBTestUtil.insertIntoResource_info_type_lu(11, "name");
        DBTestUtil.insertIntoResource_info_type_lu(12, "height");

        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        r.addSubmission(new Long(121));
        r.addSubmission(new Long(1200));

        r.setProperty("name", "kaka");
        r.setProperty("height", "185cm");

        // "weight" property is not valid.
        r.setProperty("weight", "85kg");
        // "shots" property is not valid.
        r.setProperty("shots", "1000");

        ResourceRole role = DBTestUtil.createResourceRole(5);

        persistence1.addResourceRole(role);

        // add resource.
        persistence1.addResource(r);

        // get back the resource instance from persistence.
        Resource ret = persistence1.loadResource(r.getId());

        assertNotNull("The resource got back should not be null.", ret);
        Long[] submissions = ret.getSubmissions();
        assertEquals("The submissions should be as expected", 2, submissions.length);
        if (submissions[0].longValue() == 121) {
            assertEquals("The submissions should be as expected", 1200, submissions[1].longValue());
        } else {
            assertEquals("The submissions should be as expected", 121, submissions[1].longValue());
        }

        assertEquals("The value for'name' should be kaka", "kaka", ret.getProperty("name"));
        assertEquals("The value for'height' should be 185cm", "185cm", ret.getProperty("height"));

        assertNull("property weight should not be inserted", ret.getProperty("weight"));
        assertNull("property shots should not be inserted", ret.getProperty("shots"));
    }

    /**
     * Test method <code> void deleteResource(Resource resource)  </code>. In this test case, the resource id
     * does not exist in the database.
     *
     * @throws Exception to junit.
     */
    public void testDeleteResource_1() throws Exception {
        Resource r = DBTestUtil.createResource(100, 1, 1);
        ResourceRole role = DBTestUtil.createResourceRole(5);
        persistence1.addResourceRole(role);

        persistence1.deleteResource(r);
    }

    /**
     * Test method <code> void deleteResource(Resource resource)  </code>. In this test case, the resource id
     * exists in the database.
     *
     * @throws Exception to junit.
     */
    public void testDeleteResource_2() throws Exception {
        DBTestUtil.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        r.addSubmission(new Long(121));
        r.addSubmission(new Long(1200));

        r.setProperty("name", new Integer(100));

        ResourceRole role = DBTestUtil.createResourceRole(5);

        persistence1.addResourceRole(role);

        // add resource.
        persistence1.addResource(r);

        // delete the resource.
        persistence1.deleteResource(r);

        Integer submission = DBTestUtil.getSubmissionEntry(r);

        assertNull("The submission should be null.", submission);

        Resource ret = persistence1.loadResource(r.getId());
        assertNull("The resource should be deleted.", ret);
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. The external property is updated
     * from new Integer(100) to "topcoder".
     *
     * @throws Exception to junit.
     */
    public void testUpdateResource_1() throws Exception {
        DBTestUtil.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        r.addSubmission(new Long(121));
        r.addSubmission(new Long(122));

        r.setProperty("name", new Integer(100));

        ResourceRole role = DBTestUtil.createResourceRole(5);

        persistence1.addResourceRole(role);

        // add resource to database.
        persistence1.addResource(r);

        r.setProperty("name", "topcoder");
        persistence1.updateResource(r);

        Resource ret = persistence1.loadResource(r.getId());

        assertEquals("The correct value should be 'topcoder'", "topcoder", ret.getProperty("name").toString());
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. The submission is updated to 1200.
     *
     * @throws Exception to junit.
     */
    public void testUpdateResource_2() throws Exception {

        DBTestUtil.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        r.addSubmission(new Long(121));
        r.setProperty("name", new Integer(100));

        ResourceRole role = DBTestUtil.createResourceRole(5);

        persistence1.addResourceRole(role);

        // add resource to database.
        persistence1.addResource(r);

        r.removeSubmission(new Long(121));
        r.addSubmission(new Long(1200));
        r.setProject(new Long(2)); // change project id from 1 to 2
        r.setPhase(new Long(3)); // change phase id from 1 to 3.
        persistence1.updateResource(r);

        Resource ret = persistence1.loadResource(r.getId());

        assertEquals("The summission now should be 1200.", new Long(1200), ret.getSubmissions()[0]);
        assertEquals("The project id now should be 2.", new Long(2), ret.getProject());
        assertEquals("The phase id now should be 3.", new Long(3), ret.getPhase());
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. The submission is updated to 1200.
     *
     * This test will focus on testing the updating the submission of resource instance.
     *
     * @throws Exception to junit.
     */
    public void testUpdateResource_3() throws Exception {

        DBTestUtil.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        r.addSubmission(new Long(121));
        ResourceRole role = DBTestUtil.createResourceRole(5);
        persistence1.addResourceRole(role);
        // add resource to database.
        persistence1.addResource(r);

        // set the submission to 1200.
        r.removeSubmission(new Long(121));
        r.addSubmission(new Long(1200));
        persistence1.updateResource(r);

        Resource ret = persistence1.loadResource(r.getId());
        assertEquals("The summission now should be 1200.", new Long(1200), ret.getSubmissions()[0]);

        // Clear all the submissions.
        r.clearSubmissions();
        persistence1.updateResource(r);

        // Load the resource and validate its submission is null.
        ret = persistence1.loadResource(r.getId());
        assertFalse("All the submissions are cleared.", ret.hasSubmissions());

        // Set the submission to 121 and update again.
        r.addSubmission(new Long(121));
        persistence1.updateResource(r);

        // Load the resource and validate its submission is 121.
        ret = persistence1.loadResource(r.getId());
        assertEquals("The summission now should be 121.", new Long(121), ret.getSubmissions()[0]);

        // Simply call update again, though the submission is not modified.
        persistence1.updateResource(r);

        // Load the resource and validate its submission is 121.
        ret = persistence1.loadResource(r.getId());
        assertEquals("The summission now should be 121.", new Long(121), ret.getSubmissions()[0]);
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. The submission is updated to 1200.
     *
     * This test will focus on testing the updating the external properties of resource instance.
     *
     * @throws Exception to junit.
     */
    public void testUpdateResource_4() throws Exception {
        DBTestUtil.insertIntoResource_info_type_lu(11, "name");
        DBTestUtil.insertIntoResource_info_type_lu(12, "sex");
        DBTestUtil.insertIntoResource_info_type_lu(13, "age");
        DBTestUtil.insertIntoResource_info_type_lu(14, "height");
        DBTestUtil.insertIntoResource_info_type_lu(15, "weight");

        ResourceRole role = DBTestUtil.createResourceRole(5);
        persistence1.addResourceRole(role);

        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);
        // add resource to database.
        persistence1.addResource(r);

        r.setProperty("name", "kaka");
        r.setProperty("sex", "m");

        persistence1.updateResource(r);

        Resource result = persistence1.loadResource(r.getId());

        // The correct one is loaded from db.
        assertEquals("Incorrect id", r.getId(), result.getId());

        // Validate its properties.
        assertEquals("Should have 2 properties", 2, result.getAllProperties().size());
        assertEquals("property 'name' should be 'kaka'", "kaka", result.getProperty("name"));
        assertEquals("property 'sex' should be 'm'", "m", result.getProperty("sex"));

        // Update the value of the property 'name' and 'sex'.
        r.setProperty("name", "cindy zj");
        r.setProperty("sex", "f");
        persistence1.updateResource(r);

        // Load a refresh copy of the resource from db.
        result = persistence1.loadResource(r.getId());
        // Validate its properties.
        assertEquals("Should have 2 properties", 2, result.getAllProperties().size());
        assertEquals("property 'name' should be 'cindy zj'", "cindy zj", result.getProperty("name"));
        assertEquals("property 'sex' should be 'f'", "f", result.getProperty("sex"));

        // Removed 'name' and 'sex' properties, added 'age', 'height' and 'weight' properties and update.
        r = DBTestUtil.createResource(r.getId(), 1, 1);
        r.setProperty("age", "25");
        r.setProperty("height", "155cm");
        r.setProperty("weight", "48kg");
        persistence1.updateResource(r);
        // Load a refresh copy of the resource from db.
        result = persistence1.loadResource(r.getId());
        // Validate its properties.
        assertEquals("Should have 3 properties", 3, result.getAllProperties().size());
        assertEquals("property 'age' should be '25'", "25", result.getProperty("age"));
        assertEquals("property 'height' should be '155cm'", "155cm", result.getProperty("height"));
        assertEquals("property 'weight' should be '48kg'", "48kg", result.getProperty("weight"));
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code> for accuracy.
     *
     * @throws Exception to junit.
     */
    public void testUpdateResource_5() throws Exception {

        DBTestUtil.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        r.addSubmission(new Long(121));
        r.addSubmission(new Long(122));
        r.addSubmission(new Long(123));
        r.setProperty("name", new Integer(100));

        ResourceRole role = DBTestUtil.createResourceRole(5);

        persistence1.addResourceRole(role);

        // add resource to database.
        persistence1.addResource(r);

        r.removeSubmission(new Long(121));
        r.addSubmission(new Long(1200));
        r.addSubmission(new Long(1201));
        r.setProject(new Long(2)); // change project id from 1 to 2
        r.setPhase(new Long(3)); // change phase id from 1 to 3.
        persistence1.updateResource(r);

        Resource ret = persistence1.loadResource(r.getId());

        assertEquals("The summissions should be as expected.", 4, ret.countSubmissions());
        Long[] submissions = ret.getSubmissions();
        Arrays.sort(submissions);
        assertEquals("The summission now should be 122.", new Long(122), submissions[0]);
        assertEquals("The summission now should be 123.", new Long(123), submissions[1]);
        assertEquals("The summission now should be 1200.", new Long(1200), submissions[2]);
        assertEquals("The summission now should be 1201.", new Long(1201), submissions[3]);

        assertEquals("The project id now should be 2.", new Long(2), ret.getProject());
        assertEquals("The phase id now should be 3.", new Long(3), ret.getPhase());
    }

    /**
     * Test method <code> Resource loadResource(long resourceId) </code>.
     *
     * @throws Exception to junit.
     *
     */
    public void testLoadResource_1() throws Exception {
        assertNull("The resource does not exist.", persistence1.loadResource(1000));
    }

    /**
     * Test method <code> Resource loadResource(long resourceId) </code>. This test case also test the update
     * resource accuracy. The phase, project of the resource are updated to null.
     *
     * @throws Exception to junit.
     *
     */
    public void testLoadResource_2() throws Exception {

        DBTestUtil.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        r.addSubmission(new Long(121));

        r.setProperty("name", new Integer(100));

        ResourceRole role = DBTestUtil.createResourceRole(5);

        persistence1.addResourceRole(role);

        // add resource to database.
        persistence1.addResource(r);

        r.addSubmission(new Long(1200));
        r.setPhase(null);
        r.setProject(null);
        r.setModificationUser("me");

        persistence1.updateResource(r);

        Resource ret = persistence1.loadResource(r.getId());

        Long[] submissions = ret.getSubmissions();

        assertEquals("The submissions should be as expected", 2, submissions.length);
        if (submissions[0].longValue() == 121) {
            assertEquals("The submissions should be as expected", 1200, submissions[1].longValue());
        } else {
            assertEquals("The submissions should be as expected", 121, submissions[1].longValue());
        }

        assertNull("The phase should be null.", ret.getPhase());
        assertNull("The project should be null.", ret.getProject());
        assertEquals("The modificationUser should be 'me' now", "me", ret.getModificationUser());
    }

    /**
     * Test method
     * <code>void addNotification(long user, long project, long notificationType, String operator)</code>.
     * The notificationType instance should be got from table notification_type_lu.
     *
     * @throws Exception to junit.
     */
    public void testAddNotification() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(2);
        // first persist notificationType.
        persistence1.addNotificationType(type);

        persistence1.addNotification(1, 2, 2, "tc");

        Notification ret = persistence1.loadNotification(1, 2, 2);
        assertEquals("The desription should be 'what is a tree'", "what is a tree", ret.getNotificationType()
            .getDescription());

        assertEquals("The modificationUser should be 'tc'", "tc", ret.getModificationUser());
    }

    /**
     * Test method
     * <code>void removeNotification(long user, long project, long notificationType, String operator) </code>.
     *
     * @throws Exception to junit.
     */
    public void testRemoveNotification_1() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(2);
        // first persist notificationType.
        persistence1.addNotificationType(type);

        persistence1.removeNotification(1, 1, 2, "topcoder");
    }

    /**
     * Test method
     * <code>void removeNotification(long user, long project, long notificationType, String operator) </code>.
     * In this test case, first add a Notification instance and check if it has been successfully added. Then
     * removed it and check if it does not exist any longer in the database.
     *
     * @throws Exception to junit.
     */
    public void testRemoveNotification_2() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(2);

        // first persist notificationType.
        persistence1.addNotificationType(type);

        persistence1.addNotification(1, 1, 2, "developer");

        Notification ret = persistence1.loadNotification(1, 1, 2);
        assertNotNull("The notification instance should be got", ret);

        // remove it.
        persistence1.removeNotification(1, 1, 2, "topcoder");

        ret = persistence1.loadNotification(1, 1, 2);
        assertNull("Should be null.", ret);
    }

    /**
     * Test method <code>Notification loadNotification(long user, long project, long notificationType) </code>.
     * If there is no entry for user, project, notificationType, null will be returned.
     *
     * @throws Exception to junit.
     */
    public void testLoadNotification_1() throws Exception {
        Notification ret = persistence1.loadNotification(1, 1, 2);
        assertNull("There is no notification to load.", ret);
    }

    /**
     * Test method <code>Notification loadNotification(long user, long project, long notificationType) </code>.
     * There is one notification in the database, it should be correctly loaded.
     *
     * @throws Exception to junit.
     */
    public void testLoadNotification_2() throws Exception {

        NotificationType type = DBTestUtil.createNotificationType(5);
        // first persist notificationType.
        persistence1.addNotificationType(type);

        persistence1.addNotification(1, 1, 5, "c");

        Notification ret = persistence1.loadNotification(1, 1, 5);
        assertNotNull("The notification instance should be got", ret);

        assertEquals("The modificationUser should be 'c'", "c", ret.getModificationUser());
    }

    /**
     * Test method <code>void addNotificationType(NotificationType notificationType) </code>. Add a
     * notificationType instance into the database, and get it back to compare.
     *
     * @throws Exception to junit.
     */
    public void testAddNotificationType() throws Exception {

        NotificationType type = DBTestUtil.createNotificationType(2);

        persistence1.addNotificationType(type);

        NotificationType ret = persistence1.loadNotificationType(type.getId());
        assertNotNull("The notificationType instance should be returned.", ret);

        assertEquals("The name should be 'topcoder'", "topcoder", ret.getName());
    }

    /**
     * Test method <code>void deleteNotificationType(NotificationType notificationType) </code>. First add a
     * NotificationType into the database, delete it, and check if it can be loaded back or not.
     *
     * @throws Exception to junit.
     */
    public void testDeleteNotificationType() throws Exception {

        // Run the test a number of times.
        for (int i = 0; i < 5; i++) {
            NotificationType type = DBTestUtil.createNotificationType(i + 100);

            persistence1.addNotificationType(type);

            NotificationType ret = persistence1.loadNotificationType(type.getId());
            assertNotNull("The notificationType instance should be returned.", ret);
            assertEquals("Incorrect notification type id.", type.getId(), ret.getId());
            assertEquals("The name should be 'topcoder'", "topcoder", ret.getName());

            // delete it.
            persistence1.deleteNotificationType(type);

            ret = persistence1.loadNotificationType(type.getId());

            assertNull("The NotificationType instance should be deleted.", ret);
        }
    }

    /**
     * Test method <code> void updateNotificationType(NotificationType notificationType) </code>.
     *
     * @throws Exception to junit.
     */
    public void testUpdateNotificationType() throws Exception {

        NotificationType type = DBTestUtil.createNotificationType(2);

        persistence1.addNotificationType(type);

        type.setName("developer");

        persistence1.updateNotificationType(type);

        NotificationType ret = persistence1.loadNotificationType(type.getId());

        assertEquals("The name should be updated to 'developer'", "developer", ret.getName());

        // Reset the name and update.
        type.setName("designer");
        persistence1.updateNotificationType(type);
        ret = persistence1.loadNotificationType(type.getId());
        assertEquals("The name should be updated to 'designer'", "designer", ret.getName());
    }

    /**
     * Test method <code>NotificationType loadNotificationType(long notificationTypeId) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadNotificationType() throws Exception {

        NotificationType type = DBTestUtil.createNotificationType(2);
        type.setName("tc");

        persistence1.addNotificationType(type);

        NotificationType ret = persistence1.loadNotificationType(type.getId());

        assertEquals("Id's should be the same", type.getId(), ret.getId());
        assertEquals("The name should be  'tc'", "tc", ret.getName());
    }

    /**
     * Test method <code>void addResourceRole(ResourceRole resourceRole) </code>. First add a ResourceRole,
     * then load it back to check if add method is correct.
     *
     * @throws Exception to junit.
     */
    public void testAddResourceRole() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(100);

        // delete the ResourceRole if it is already existing.
        persistence1.deleteResourceRole(role);

        // add the ResourceRole
        persistence1.addResourceRole(role);

        ResourceRole ret = persistence1.loadResourceRole(role.getId());

        assertNotNull("The resourceRole instance should be got back.", ret);
        assertEquals("The name should be 'tc'", "tc", ret.getName());
        assertEquals("The phaseType should be 1", new Integer(1).intValue(), ret.getPhaseType().intValue());
        assertEquals("The description should be 'resource role'", "resource role", ret.getDescription());
    }

    /**
     * Test method <code>void deleteResourceRole(ResourceRole resourceRole) </code>.
     *
     * @throws Exception to junit.
     */
    public void testDeleteResourceRole_1() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(100);

        persistence1.deleteResourceRole(role);

        ResourceRole ret = persistence1.loadResourceRole(role.getId());
        assertNull("Should be deleted.", ret);
    }

    /**
     * Test method <code>void deleteResourceRole(ResourceRole resourceRole) </code>. First add a
     * ResourceRole and check if it is added. Then delete the ResourceRole and reload to check if it is
     * deleted already.
     *
     * @throws Exception to junit.
     */
    public void testDeleteResourceRole_2() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(100);

        persistence1.deleteResourceRole(role);

        // add the ResourceRole into the database.
        persistence1.addResourceRole(role);

        ResourceRole ret = persistence1.loadResourceRole(role.getId());
        // check if the ResourceRole is in the database.
        assertNotNull("Should not be null.", ret);

        // delete the ResourceRole.
        persistence1.deleteResourceRole(role);

        // reload the resourceRole.
        ret = persistence1.loadResourceRole(role.getId());

        // check if it is deleted.
        assertNull("Should be null.", ret);
    }

    /**
     * Test method <code>void updateResourceRole(ResourceRole resourceRole)  </code>. Update the phaseType to
     * 2, name to "developer" and description to "test".
     *
     * @throws Exception to junit.
     */
    public void testUpdateResourceRole() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(100);

        persistence1.deleteResourceRole(role);

        // add the ResourceRole into the database.
        persistence1.addResourceRole(role);

        // update the phaseType.
        role.setPhaseType(new Long(2));
        role.setName("developer");
        role.setDescription("test");

        persistence1.updateResourceRole(role);

        ResourceRole ret = persistence1.loadResourceRole(role.getId());
        // check if the ResourceRole is in the database.
        assertNotNull("Should not be null.", ret);
        assertEquals("Equal is expected.", role.getPhaseType().intValue(), ret.getPhaseType().intValue());
        assertEquals("The name should be updated to 'developer'", "developer", ret.getName());
        assertEquals("The description should be updated to 'test'", "test", ret.getDescription());
    }

    /**
     * Test method <code>ResourceRole loadResourceRole(long resourceRoleId) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadResourceRole() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(111);

        persistence1.deleteResourceRole(role);

        role.setPhaseType(new Long(2));
        role.setName("developer");
        role.setDescription("test");

        // add the ResourceRole into the database.
        persistence1.addResourceRole(role);

        ResourceRole ret = persistence1.loadResourceRole(role.getId());

        // check if the ResourceRole is in the database.
        assertNotNull("Should not be null.", ret);
        assertEquals("Equal is expected.", role.getPhaseType().intValue(), ret.getPhaseType().intValue());
        assertEquals("The name should be  'developer'", "developer", ret.getName());
        assertEquals("The description should be  'test'", "test", ret.getDescription());
    }

    /**
     * Test method <code>Resource[] loadResources(long[] resourceIds) </code>. In this test case, one
     * resource will be added into the database.
     *
     * @throws Exception to junit.
     */
    public void testLoadResources_1() throws Exception {
        DBTestUtil.insertIntoResource_info_type_lu(11, "name");
        DBTestUtil.insertIntoResource_info_type_lu(12, "sex");

        // create a resource instance.
        Resource r = DBTestUtil.createResource(11, 1, 1);

        r.addSubmission(new Long(121));
        r.addSubmission(new Long(1200));

        r.setProperty("name", "kaka");
        r.setProperty("sex", "male");

        ResourceRole role = DBTestUtil.createResourceRole(5);
        persistence1.addResourceRole(role);

        persistence1.addResource(r);

        Resource[] ret = persistence1.loadResources(new long[] {11});

        assertEquals("The size should be 1", 1, ret.length);

        Resource result = ret[0];
        assertEquals("The id should be 11", 11, result.getId());
        Long[] submissions = result.getSubmissions();
        assertEquals("The submissions should be as expected", 2, submissions.length);
        if (submissions[0].longValue() == 121) {
            assertEquals("The submissions should be as expected", 1200, submissions[1].longValue());
        } else {
            assertEquals("The submissions should be as expected", 121, submissions[1].longValue());
        }

        assertEquals("property 'name' value should be kaka", "kaka", result.getProperty("name"));
        assertEquals("property 'sex' value should be male", "male", result.getProperty("sex"));
    }

    /**
     * Test method <code>Resource[] loadResources(long[] resourceIds) </code>.
     *
     * In this test case, multiple resource instance will be loaded.
     *
     * @throws Exception to junit.
     */
    public void testLoadResources_2() throws Exception {
        DBTestUtil.insertIntoResource_info_type_lu(11, "name");
        DBTestUtil.insertIntoResource_info_type_lu(1, "name1");
        DBTestUtil.insertIntoResource_info_type_lu(10, "name2");
        DBTestUtil.insertIntoResource_info_type_lu(100, "name4");
        DBTestUtil.insertIntoResource_info_type_lu(101, "name5");

        // create two resource instances.
        Resource r = DBTestUtil.createResource(11, 1, 1);
        Resource r2 = DBTestUtil.createResource(10, 1, 1);

        r.addSubmission(new Long(121));
        r.addSubmission(new Long(1200));
        r2.addSubmission(new Long(1200));
        r2.addSubmission(new Long(121));
        r2.addSubmission(new Long(122));

        r.setProperty("name", new Integer(100));
        r.setProperty("name5", new Integer(1000));
        r.setProperty("name4", "value4");

        r2.setProperty("name2", "value2");
        r2.setProperty("name4", new Integer(10));

        ResourceRole role = DBTestUtil.createResourceRole(5);

        persistence1.addResourceRole(role);

        // add resources;
        persistence1.addResource(r);
        persistence1.addResource(r2);

        Resource[] ret = persistence1.loadResources(new long[] {11, 10});

        assertNotNull("The resource got back should not be null.", ret);
        assertEquals("Should load 2 items", 2, ret.length);

        assertEquals("The size of the first resource should be 3", 3, ret[0].getAllProperties().size());

        assertEquals("The value for name4 of the first resource should be 'value4'", "value4", ret[0]
            .getProperty("name4"));

        assertEquals("The size of the second resource should be 2.", 2, ret[1].getAllProperties().size());

        assertEquals("The value for name4 of the second resource should be integer 10", "10", ret[1]
            .getProperty("name4"));

        Long[] submissions1 = ret[0].getSubmissions();
        assertEquals("The submissions should be as expected", 2, submissions1.length);
        if (submissions1[0].longValue() == 121) {
            assertEquals("The submissions should be as expected", 1200, submissions1[1].longValue());
        } else {
            assertEquals("The submissions should be as expected", 121, submissions1[1].longValue());
        }

        Long[] submissions2 = ret[1].getSubmissions();
        assertEquals("The submissions should be as expected", 3, submissions2.length);
    }

    /**
     * Test method <code>Resource[] loadResources(long[] resourceIds) </code>.
     *
     * There is no resources in the table, should load no items.
     *
     * @throws Exception to junit.
     */
    public void testLoadResourcesNone() throws Exception {

        Resource[] ret = persistence1.loadResources(new long[] {11, 10});

        assertNotNull("The resource got back should not be null.", ret);
        assertEquals("There should have no item loaded", 0, ret.length);
    }

    /**
     * Test method <code>NotificationType[] loadNotificationTypes(long[] notificationTypeIds) </code>.
     *
     * @throws Exception to junit.
     *
     */
    public void testLoadNotificationTypes() throws Exception {
        for (int i = 0; i < 5; i++) {
            NotificationType type = DBTestUtil.createNotificationType(i + 1);
            persistence1.addNotificationType(type);
        }

        NotificationType[] ret = persistence1.loadNotificationTypes(new long[] {1, 2, 3, 4, 5});
        assertNotNull("The notificationType instance should be returned.", ret);
        assertEquals("The size should be 5.", 5, ret.length);
    }

    /**
     * Test method <code>NotificationType[] loadNotificationTypes(long[] notificationTypeIds) </code>.
     *
     * The notification is cleared, nothing should be loaded.
     *
     * @throws Exception to junit.
     *
     */
    public void testLoadNotificationTypesNone() throws Exception {
        NotificationType[] ret = persistence1.loadNotificationTypes(new long[] {1, 2, 3, 4, 5});

        assertNotNull("The notificationType instance should be returned.", ret);
        assertEquals("The size should be 0.", 0, ret.length);
    }

    /**
     * Test method <code>ResourceRole[] loadResourceRoles(long[] resourceRoleIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadResourceRoles() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(100);
        ResourceRole role2 = DBTestUtil.createResourceRole(11);

        // delete the ResourceRole if they are already existing.
        persistence1.deleteResourceRole(role);
        persistence1.deleteResourceRole(role2);

        // add the ResourceRoles.
        persistence1.addResourceRole(role);
        persistence1.addResourceRole(role2);

        ResourceRole[] ret = persistence1.loadResourceRoles(new long[] {100, 11});

        assertNotNull("The resourceRole instance should be got back.", ret);
        assertEquals("The size should be 2", 2, ret.length);
    }

    /**
     * Test method <code>ResourceRole[] loadResourceRoles(long[] resourceRoleIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadResourceRolesWith0ArrayArgumnet() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(100);
        ResourceRole role2 = DBTestUtil.createResourceRole(11);

        // delete the ResourceRole if they are already existing.
        persistence1.deleteResourceRole(role);
        persistence1.deleteResourceRole(role2);

        // add the ResourceRoles.
        persistence1.addResourceRole(role);
        persistence1.addResourceRole(role2);

        ResourceRole[] ret = persistence1.loadResourceRoles(new long[0]);

        assertNotNull("The resourceRole instance should be got back.", ret);
        assertEquals("The size should be 0", 0, ret.length);
    }

    /**
     * Test method <code>ResourceRole[] loadResourceRoles(long[] resourceRoleIds) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadResourceRolesWith0ArrayArguments() throws Exception {
        ResourceRole role = DBTestUtil.createResourceRole(100);
        ResourceRole role2 = DBTestUtil.createResourceRole(11);

        // delete the ResourceRole if they are already existing.
        persistence1.deleteResourceRole(role);
        persistence1.deleteResourceRole(role2);

        // add the ResourceRoles.
        persistence1.addResourceRole(role);
        persistence1.addResourceRole(role2);

        ResourceRole[] ret = persistence1.loadResourceRoles(new long[0]);

        assertNotNull("The resourceRole instance should be got back.", ret);
        assertEquals("The size should be 0", 0, ret.length);
    }

    /**
     * Test method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadNotifications() throws Exception {

        NotificationType type = DBTestUtil.createNotificationType(2);
        // first persist notificationType.
        persistence1.addNotificationType(type);

        persistence1.addNotification(1, 2, 2, "tc");
        persistence1.addNotification(2, 2, 2, "developer");

        Notification[] ret =
            persistence1.loadNotifications(new long[] {1, 2}, new long[] {2, 2}, new long[] {2, 2});

        assertEquals("The size returned should be 2.", 2, ret.length);
    }

    /**
     * Test method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     *
     * @throws Exception to junit.
     */
    public void testLoadNotificationsWithAll0ArrayArguments() throws Exception {
        NotificationType type = DBTestUtil.createNotificationType(2);
        // first persist notificationType.
        persistence1.addNotificationType(type);

        persistence1.addNotification(1, 2, 2, "tc");
        persistence1.addNotification(2, 2, 2, "developer");

        Notification[] ret = persistence1.loadNotifications(new long[0], new long[0], new long[0]);

        assertEquals("The size returned should be 0.", 0, ret.length);
    }

    /**
     * Test method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     * The notification table is clear, should load no notifications.
     *
     * @throws Exception to junit.
     */
    public void testLoadNotificationsNone() throws Exception {
        Notification[] ret =
            persistence1.loadNotifications(new long[] {1, 2}, new long[] {2, 2}, new long[] {2, 2});

        assertEquals("The size returned should be 0.", 0, ret.length);
    }

    /**
     * Test method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[] notificationTypes) </code>.
     * The arguments are all length-0 long array, no Notification instance would be loaded.
     *
     * @throws Exception to junit.
     */
    public void testLoadNotificationsWithLength0Array() throws Exception {

        Notification[] ret = persistence1.loadNotifications(new long[0], new long[0], new long[0]);

        assertEquals("The size returned should be 0.", 0, ret.length);
    }

    /**
     * Test method <code>String getConnectionName()</code>. Expects the same connection name.
     *
     * @throws Exception to junit.
     */
    public void testGetConnectionName() throws Exception {
        assertEquals("ConnectionName is wrong.", "sysuser", persistence2.getConnectionName());
    }

    /**
     * Test method <code>DBConnectionFactory getConnectionFactory()</code>. Expects the same connection
     * name.
     *
     * @throws Exception to junit.
     */
    public void testGetConnectionFactory() throws Exception {
        assertNotNull("ConnectionFactorys is null.", persistence2.getConnectionFactory());
    }
}
