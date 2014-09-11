/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.persistence.sql.accuracytests;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;

import com.topcoder.management.resource.Notification;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.persistence.sql.AbstractResourcePersistence;
import com.topcoder.management.resource.persistence.sql.DBTestUtil;
import com.topcoder.management.resource.persistence.sql.SqlResourcePersistence;

import com.topcoder.util.config.ConfigManager;

import junit.framework.TestCase;

import java.io.File;

import java.util.Date;

/**
 * Unit test cases for class <code>AbstractResourcePersistence</code>. In
 * this test class, the functionality of this component will be tested.
 * @author TCSDEVELOPER, 
 * @version 1.1
 */
public class AbstractResourcePersistenceAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the AbstractResourcePersistence instance for test.
     * </p>
     */
    private AbstractResourcePersistence instance = null;

    /**
     * <p>
     * Represents the DBConnectionFactory instance for test.
     * </p>
     */
    private DBConnectionFactory factory = null;

    /**
     * <p>
     * Set up the test environment.
     * </p>
     * @throws Exception to JUnit.
     */
    public void setUp() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        AccuracyHelper.clearConfigManager();

        File file = new File("test_files/accuracy/DBConnectionFactory.xml");

        cm.add(file.getAbsolutePath());

        String namespace = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

        factory = new DBConnectionFactoryImpl(namespace);

        // cause AbstractResourcePersistence is abstract, we use it's subclass
        // to test.
        instance = new SqlResourcePersistence(factory);

        AccuracyHelper.clearTables();

        AccuracyHelper.setupDatbase();
    }

    /**
     * <p>
     * Tear down the environment.
     * </p>
     * @throws Exception to JUnit.
     */
    public void tearDown() throws Exception {
        AccuracyHelper.clearConfigManager();
    }

    /**
     * <p>
     * Test constructor
     * <code> SqlResourcePersistence(DBConnectionFactory)</code> for accuracy.
     * </p>
     * <p>
     * This is accuracy test for the constructor.
     * </p>
     */
    public void testCtor1Accuracy() {
        assertNotNull("The SqlResourcePersistence instance should be created.", instance);
    }

    /**
     * <p>
     * Test constructor
     * <code>SqlResourcePersistence(DBConnectionFactory, String) </code>. The
     * parameter connectionName can be null.
     * </p>
     * <p>
     * This is accuracy test for the constructor.
     * </p>
     */
    public void testCtor2Accuracy() {
        instance = new SqlResourcePersistence(factory, "sysuser");
        assertNotNull("The SqlResourcePersistence instance should be created.", instance);
    }

    /**
     * <p>
     * Test constructor
     * <code>SqlResourcePersistence(DBConnectionFactory, String) </code>. The
     * parameter connectionName can be null.
     * </p>
     * <p>
     * This is accuracy test for the constructor.
     * </p>
     */
    public void testCtor3Accuracy() {
        instance = new SqlResourcePersistence(factory, null);

        assertNotNull("The SqlResourcePersistence instance should be created.", instance);
    }

    /**
     * <p>
     * Test method <code>void addResource(Resource resource) </code>.
     * </p>
     * <p>
     * In this test case, the resource has null submission and no external
     * properties.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testAddResource1Accuracy() throws Exception {
        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        r.addSubmission(new Long(1));

        instance.addResourceRole(role);

        // add resource.
        instance.addResource(r);

        // get back the resource instance from instance.
        Resource ret = instance.loadResource(r.getId());
        assertNotNull("The resource got back should not be null.", ret);

        assertTrue("The submission should be null.", ret.getSubmissions().length == 1);
        assertTrue("The external properties should be empty.", ret.getAllProperties().isEmpty());
    }

    /**
     * <p>
     * Test method <code>void addResource(Resource resource) </code>.
     * </p>
     * <p>
     * In this test case, the resource has null submission and no external
     * properties, project and phase is also not set.
     * Throws ResourcePersistenceException since audit failed.
     * </p>
     * @throws Exception to JUnit.
     */
    public void testAddResource2Failure() throws Exception {
        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        r.setPhase(null);
        r.setProject(null);
        r.addSubmission(new Long(1));

        instance.addResourceRole(role);

        try {
            // add resource.
            instance.addResource(r);
            fail("Cannot go here");
        }
        catch(ResourcePersistenceException e) {
            // OK
        }
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>. In this
     * test case, the submission will be set and also with external properties.
     * @throws Exception to JUnit.
     */
    public void testAddResource3Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.addSubmission(new Long(121));

        r.setProperty("name", new Integer(100));

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource.
        instance.addResource(r);

        // get back the resource instance from instance.
        Resource ret = instance.loadResource(r.getId());

        assertNotNull("The resource got back should not be null.", ret);
        assertEquals("The submission should be 121", new Long(121), ret.getSubmissions()[0]);

        assertEquals("The value for'name' should be 100", new Integer(100), new Integer(ret
                .getProperty("name").toString()));
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>. In this
     * test case, the submission will be set and also with multiple external
     * properties.
     * @throws Exception to JUnit.
     */
    public void testAddResource4Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");
        AccuracyHelper.insertIntoResource_info_type_lu(12, "weight");
        AccuracyHelper.insertIntoResource_info_type_lu(13, "height");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.addSubmission(new Long(121));

        r.setProperty("name", "kaka");
        r.setProperty("height", "185cm");
        r.setProperty("weight", "85kg");

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource.
        instance.addResource(r);

        // get back the resource instance from instance.
        Resource ret = instance.loadResource(r.getId());

        assertNotNull("The resource got back should not be null.", ret);
        assertEquals("The submission should be 121", new Long(121), ret.getSubmissions()[0]);

        assertEquals("The value for'name' should be kaka", "kaka", ret.getProperty("name"));
        assertEquals("The value for'height' should be 185cm", "185cm", ret.getProperty("height"));
        assertEquals("The value for'weight' should be 85kg", "85kg", ret.getProperty("weight"));
    }

    /**
     * Test method <code>void addResource(Resource resource) </code>. In this
     * test case, the resource instance will have property which is not
     * configged in the resource_info_type_lu table, such properties will be
     * ignored during inserting the resource.
     * @throws Exception to JUnit.
     */
    public void testAddResource5Accuracy() throws Exception {
        // Only have 'name' and 'weight' resource_info_type.
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");
        AccuracyHelper.insertIntoResource_info_type_lu(12, "height");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.addSubmission(new Long(121));

        r.setProperty("name", "kaka");
        r.setProperty("height", "185cm");

        // "weight" property is not valid.
        r.setProperty("weight", "85kg");
        // "shots" property is not valid.
        r.setProperty("shots", "1000");

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource.
        instance.addResource(r);

        // get back the resource instance from instance.
        Resource ret = instance.loadResource(r.getId());

        assertNotNull("The resource got back should not be null.", ret);
        assertEquals("The submission should be 121", new Long(121), ret.getSubmissions()[0]);

        assertEquals("The value for'name' should be kaka", "kaka", ret.getProperty("name"));
        assertEquals("The value for'height' should be 185cm", "185cm", ret.getProperty("height"));

        assertNull("property weight should not be inserted", ret.getProperty("weight"));
        assertNull("property shots should not be inserted", ret.getProperty("shots"));
    }
    /**
     * Test method <code>void addResource(Resource resource) </code>.
     *
     * In this test case, the submission will be set and also with multiple external properties.
     *
     * @throws Exception
     *             To JUnit.
     * @since 1.2
     */
    public void testAddResource6Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");
        AccuracyHelper.insertIntoResource_info_type_lu(12, "weight");
        AccuracyHelper.insertIntoResource_info_type_lu(13, "height");
        // create a resource instance.
        Resource r = AccuracyHelper.createResource(18, 1, 1);

        Long[] submissions = new Long[3];
        submissions[0] = new Long(121);
        submissions[1] = new Long(122);
        submissions[2] = new Long(123);
        r.setSubmissions(submissions);


        r.setProperty("name", "kaka");
        r.setProperty("height", "185cm");
        r.setProperty("weight", "85kg");

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource.
        instance.addResource(r);

        // get back the resource instance from persistence.
        Resource ret = instance.loadResource(r.getId());

        assertNotNull("The resource got back should not be null.", ret);
        assertTrue("The submission contain 121", ret.containsSubmission(new Long(121)));
        assertEquals("The submission's number should be 3", 3, ret.countSubmissions());

        assertEquals("The value for'name' should be kaka", "kaka", ret.getProperty("name"));
        assertEquals("The value for'height' should be 185cm", "185cm", ret.getProperty("height"));
        assertEquals("The value for'weight' should be 85kg", "85kg", ret.getProperty("weight"));
    }

    /**
     * Test method <code> void deleteResource(Resource resource)  </code>. In
     * this test case, the resource id does not exist in the database.
     * @throws Exception to JUnit.
     */
    public void testDeleteResource1Accuracy() throws Exception {
        Resource r = AccuracyHelper.createResource(100, 1, 1);
        ResourceRole role = DBTestUtil.createResourceRole(5);
        instance.addResourceRole(role);

        instance.deleteResource(r);
    }

    /**
     * Test method <code> void deleteResource(Resource resource)  </code>. In
     * this test case, the resource id exists in the database.
     * @throws Exception to JUnit.
     */
    public void testDeleteResource2Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.addSubmission(new Long(121));

        r.setProperty("name", new Integer(100));

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource.
        instance.addResource(r);

        // delete the resource.
        instance.deleteResource(r);

        Integer submission = AccuracyHelper.getSubmissionEntry(r);

        assertTrue("The submission should contain one element.", r.getSubmissions().length == 1);

        Resource ret = instance.loadResource(r.getId());
        assertNull("The resource should be deleted.", ret);
    }
    /**
     * Test method <code> void deleteResource(Resource resource)  </code>. In this test case, the resource id
     * exists in the database.
     *
     * @throws Exception
     *             To JUnit.
     * @since 1.2
     */
    public void testDeleteResource3Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.addSubmission(new Long(121));

        r.setProperty("name", new Integer(100));

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource.
        instance.addResource(r);

        // delete the resource.
        instance.deleteResource(r);

        Integer submission = AccuracyHelper.getSubmissionEntry(r);

        assertNull("The submission should be null.", submission);

        Resource ret = instance.loadResource(r.getId());
        assertNull("The resource should be deleted.", ret);
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. The
     * external property is updated from new Integer(100) to "topcoder".
     * @throws Exception to JUnit.
     */
    public void testUpdateResource1Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.addSubmission(new Long(121));

        r.setProperty("name", new Integer(100));

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource to database.
        instance.addResource(r);

        r.setProperty("name", "topcoder");

        instance.updateResource(r);

        Resource ret = instance.loadResource(r.getId());

        assertEquals("The correct value should be 'topcoder'", "topcoder", ret.getProperty("name")
                .toString());
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. The
     * submission is updated to 1200.
     * @throws Exception to JUnit.
     */
    public void testUpdateResource2Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");

        Long[] only121 = {new Long(121) };

        Long[] only1200 = {new Long(1200) };

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.setSubmissions(only121);
        r.setProperty("name", new Integer(100));

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource to database.
        instance.addResource(r);

        r.setSubmissions(only1200);
        r.setProject(new Long(2)); // change project id from 1 to 2
        r.setPhase(new Long(3)); // change phase id from 1 to 3.
        instance.updateResource(r);

        Resource ret = instance.loadResource(r.getId());

        assertEquals("The summission now should be 1200.", new Long(1200).longValue(), ret
                .getSubmissions()[0].longValue());
        assertEquals("The project id now should be 2.", new Long(2), ret.getProject());
        assertEquals("The phase id now should be 3.", new Long(3), ret.getPhase());
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. The
     * submission is updated to 1200. This test will focus on testing the
     * updating the submission of resource instance.
     * @throws Exception to JUnit.
     */
    public void testUpdateResource3Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");

        Long[] only121 = {new Long(121) };

        Long[] only1200 = {new Long(1200) };

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.setSubmissions(only121);

        ResourceRole role = AccuracyHelper.createResourceRole(5);
        instance.addResourceRole(role);
        // add resource to database.
        instance.addResource(r);

        // set the submission to 1200.
        r.setSubmissions(only1200);
        instance.updateResource(r);

        Resource ret = instance.loadResource(r.getId());
        assertEquals("The summission now should be 1200.", new Long(1200).longValue(), ret
                .getSubmissions()[0].longValue());

        // Set the submission to 121 and update again.
        r.setSubmissions(only121);
        instance.updateResource(r);

        // Load the resource and validate its submission is 121.
        ret = instance.loadResource(r.getId());
        assertEquals("The summission now should be 121.", new Long(121), ret.getSubmissions()[0]);

        // Simply call update again, though the submission is not modified.
        instance.updateResource(r);

        // Load the resource and validate its submission is 121.
        ret = instance.loadResource(r.getId());
        assertEquals("The summission now should be 121.", new Long(121), ret.getSubmissions()[0]);
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>. The
     * submission is updated to 1200. This test will focus on testing the
     * updating the external properties of resource instance.
     * @throws Exception to JUnit.
     */
    public void testUpdateResource4Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");
        AccuracyHelper.insertIntoResource_info_type_lu(12, "sex");
        AccuracyHelper.insertIntoResource_info_type_lu(13, "age");
        AccuracyHelper.insertIntoResource_info_type_lu(14, "height");
        AccuracyHelper.insertIntoResource_info_type_lu(15, "weight");

        ResourceRole role = AccuracyHelper.createResourceRole(5);
        instance.addResourceRole(role);

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);
        // add resource to database.
        instance.addResource(r);

        r.setProperty("name", "kaka");
        r.setProperty("sex", "m");

        instance.updateResource(r);

        Resource result = instance.loadResource(r.getId());

        // The correct one is loaded from db.
        assertEquals("Incorrect id", r.getId(), result.getId());

        // Validate its properties.
        assertEquals("Should have 2 properties", 2, result.getAllProperties().size());
        assertEquals("property 'name' should be 'kaka'", "kaka", result.getProperty("name"));
        assertEquals("property 'sex' should be 'm'", "m", result.getProperty("sex"));

        // Update the value of the property 'name' and 'sex'.
        r.setProperty("name", "cindy zj");
        r.setProperty("sex", "f");
        instance.updateResource(r);

        // Load a refresh copy of the resource from db.
        result = instance.loadResource(r.getId());
        // Validate its properties.
        assertEquals("Should have 2 properties", 2, result.getAllProperties().size());
        assertEquals("property 'name' should be 'cindy zj'", "cindy zj", result.getProperty("name"));
        assertEquals("property 'sex' should be 'f'", "f", result.getProperty("sex"));

        // Removed 'name' and 'sex' properties, added 'age', 'height' and
        // 'weight' properties and update.
        r = AccuracyHelper.createResource(r.getId(), 1, 1);
        r.setProperty("age", "25");
        r.setProperty("height", "155cm");
        r.setProperty("weight", "48kg");
        instance.updateResource(r);
        // Load a refresh copy of the resource from db.
        result = instance.loadResource(r.getId());
        // Validate its properties.
        assertEquals("Should have 3 properties", 3, result.getAllProperties().size());
        assertEquals("property 'age' should be '25'", "25", result.getProperty("age"));
        assertEquals("property 'height' should be '155cm'", "155cm", result.getProperty("height"));
        assertEquals("property 'weight' should be '48kg'", "48kg", result.getProperty("weight"));
    }
    /**
     * Test method <code>void updateResource(Resource resource) </code>.
     *
     * This test will focus on testing the updating the submission of resource instance.
     * Note:previous has no submissions,current has 3 submissions.
     *
     * @throws Exception
     *             To JUnit.
     */
    public void testUpdateResource5() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.setProperty("name", new Integer(100));

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource to database.
        instance.addResource(r);

        Long[] submissions = new Long[3];
        submissions[0] = new Long(121);
        submissions[1] = new Long(122);
        submissions[2] = new Long(123);

        r.setSubmissions(submissions);
        instance.updateResource(r);

        Resource ret = instance.loadResource(r.getId());

        assertEquals("The number of submissions now should be 3.", 3, ret.countSubmissions());
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>.
     *
     * This test will focus on testing the updating the submission of resource instance.
     * Note:previous has 3 submissions,current has 0 submission.
     *
     * @throws Exception
     *             Throw it to JUnit.
     */
    public void testUpdateResource6() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.setProperty("name", new Integer(100));

        Long[] submissions = new Long[3];
        submissions[0] = new Long(121);
        submissions[1] = new Long(122);
        submissions[2] = new Long(123);
        r.setSubmissions(submissions);

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource to database.
        instance.addResource(r);


        r.setSubmissions(new Long[0]);
        instance.updateResource(r);

        Resource ret = instance.loadResource(r.getId());

        assertEquals("The number of submissions now should be 0.", 0, ret.countSubmissions());
    }

    /**
     * Test method <code>void updateResource(Resource resource) </code>.
     *
     * This test will focus on testing the updating the submission of resource instance.
     * Note:previous has 3 submissions,current has 2 submission.
     *
     * @throws Exception
     *             Throw it to JUnit.
     */
    public void testUpdateResource7() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.setProperty("name", new Integer(100));

        Long[] submissions = new Long[3];
        submissions[0] = new Long(121);
        submissions[1] = new Long(122);
        submissions[2] = new Long(123);
        r.setSubmissions(submissions);

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource to database.
        instance.addResource(r);

        Long[] newSubmissions = new Long[2];
        newSubmissions[0] = new Long(121);
        newSubmissions[1] = new Long(122);
        r.setSubmissions(newSubmissions);
        instance.updateResource(r);

        Resource ret = instance.loadResource(r.getId());

        assertEquals("The number of submissions now should be 2.", 2, ret.countSubmissions());
    }


    /**
     * Test method <code> Resource loadResource(long resourceId) </code>.
     * @throws Exception to JUnit.
     */
    public void testLoadResource1Accuracy() throws Exception {
        assertNull("The resource does not exist.", instance.loadResource(1000));
    }

    /**
     * Test method <code> Resource loadResource(long resourceId) </code>. This
     * test case also test the update resource accuracy. The phase, project of
     * the resource are updated to null.
     * @throws Exception to JUnit.
     */
    public void testLoadResource2Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.addSubmission(new Long(121));

        r.setProperty("name", new Integer(100));

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource to database.
        instance.addResource(r);

        r.addSubmission(new Long(1200));
        r.setPhase(null);
        r.setProject(null);
        r.setModificationUser("me");

        instance.updateResource(r);

        Resource ret = instance.loadResource(r.getId());

        assertTrue("The  summission now should contain 121.", ret.containsSubmission(new Long(121)));
        assertTrue("The  summission now should contain 1200.", ret
                .containsSubmission(new Long(1200)));
        assertNull("The phase should be null.", ret.getPhase());
        assertNull("The project should be null.", ret.getProject());
        assertEquals("The modificationUser should be 'me' now", "me", ret.getModificationUser());
    }

    /**
     * Test method <code> Resource loadResource(long resourceId) </code>. This test case also test the update
     * resource accuracy. The phase, project of the resource are updated to null.
     *
     * @throws Exception
     *             To JUnit.
     *
     */
    public void testLoadResource3() throws Exception {

        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.addSubmission(new Long(121));

        r.setProperty("name", new Integer(100));

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resource to database.
        instance.addResource(r);

        r.addSubmission(new Long(1200));
        r.setPhase(null);
        r.setProject(null);
        r.setModificationUser("me");

        instance.updateResource(r);

        Resource ret = instance.loadResource(r.getId());

        assertTrue("The submission now should contain 1200.", ret.containsSubmission(new Long(1200)));
        assertNull("The phase should be null.", ret.getPhase());
        assertNull("The project should be null.", ret.getProject());
        assertEquals("The modificationUser should be 'me' now", "me", ret.getModificationUser());
    }

    /**
     * Test method
     * <code>void addNotification(long user, long project, long notificationType, String
     * operator)</code>.
     * The notificationType instance should be got from table
     * notification_type_lu.
     * @throws Exception to JUnit.
     */
    public void testAddNotification1Accuracy() throws Exception {
        NotificationType type = AccuracyHelper.createNotificationType(2);
        // first persist notificationType.
        instance.addNotificationType(type);

        instance.addNotification(1, 2, 2, "tc");

        Notification ret = instance.loadNotification(1, 2, 2);
        assertEquals("The desription should be 'what is a tree'", "what is a tree", ret
                .getNotificationType().getDescription());

        assertEquals("The modificationUser should be 'tc'", "tc", ret.getModificationUser());
    }

    /**
     * Test method
     * <code>void removeNotification(long user, long project, long notificationType, String
     * operator) </code>.
     * @throws Exception to JUnit.
     */
    public void testRemoveNotification1Accuracy() throws Exception {
        NotificationType type = AccuracyHelper.createNotificationType(2);
        // first persist notificationType.
        instance.addNotificationType(type);

        instance.removeNotification(1, 1, 2, "topcoder");
    }

    /**
     * Test method
     * <code>void removeNotification(long user, long project, long notificationType, String
     * operator) </code>.
     * In this test case, first add a Notification instance and check if it has
     * been successfully added. Then removed it and check if it does not exist
     * any longer in the database.
     * @throws Exception to JUnit.
     */
    public void testRemoveNotification2Accuracy() throws Exception {
        NotificationType type = AccuracyHelper.createNotificationType(2);

        // first persist notificationType.
        instance.addNotificationType(type);

        instance.addNotification(1, 1, 2, "developer");

        Notification ret = instance.loadNotification(1, 1, 2);
        assertNotNull("The notification instance should be got", ret);

        // remove it.
        instance.removeNotification(1, 1, 2, "topcoder");

        ret = instance.loadNotification(1, 1, 2);
        assertNull("Should be null.", ret);
    }

    /**
     * Test method
     * <code>Notification loadNotification(long user, long project, long notificationType) </code>.
     * If there is no entry for user, project, notificationType, null will be
     * returned.
     * @throws Exception to JUnit.
     */
    public void testLoadNotification1Accuracy() throws Exception {
        Notification ret = instance.loadNotification(1, 1, 2);
        assertNull("There is no notification to load.", ret);
    }

    /**
     * Test method
     * <code>Notification loadNotification(long user, long project, long notificationType) </code>.
     * There is one notification in the database, it should be correctly loaded.
     * @throws Exception to JUnit.
     */
    public void testLoadNotification2Accuracy() throws Exception {
        NotificationType type = AccuracyHelper.createNotificationType(5);
        // first persist notificationType.
        instance.addNotificationType(type);

        instance.addNotification(1, 1, 5, "c");

        Notification ret = instance.loadNotification(1, 1, 5);
        assertNotNull("The notification instance should be got", ret);

        assertEquals("The modificationUser should be 'c'", "c", ret.getModificationUser());
    }

    /**
     * Test method
     * <code>void addNotificationType(NotificationType notificationType) </code>.
     * Add a notificationType instance into the database, and get it back to
     * compare.
     * @throws Exception to JUnit.
     */
    public void testAddNotificationType1Accuracy() throws Exception {
        NotificationType type = AccuracyHelper.createNotificationType(2);

        instance.addNotificationType(type);

        NotificationType ret = instance.loadNotificationType(type.getId());
        assertNotNull("The notificationType instance should be returned.", ret);

        assertEquals("The name should be 'topcoder'", "topcoder", ret.getName());
    }

    /**
     * Test method
     * <code>void deleteNotificationType(NotificationType notificationType) </code>.
     * First add a NotificationType into the database, delete it, and check if
     * it can be loaded back or not.
     * @throws Exception to JUnit.
     */
    public void testDeleteNotificationType1Accuracy() throws Exception {
        // Run the test a number of times.
        for (int i = 0; i < 5; i++) {
            NotificationType type = AccuracyHelper.createNotificationType(i + 100);

            instance.addNotificationType(type);

            NotificationType ret = instance.loadNotificationType(type.getId());
            assertNotNull("The notificationType instance should be returned.", ret);
            assertEquals("Incorrect notification type id.", type.getId(), ret.getId());
            assertEquals("The name should be 'topcoder'", "topcoder", ret.getName());

            // delete it.
            instance.deleteNotificationType(type);

            ret = instance.loadNotificationType(type.getId());

            assertNull("The NotificationType instance should be deleted.", ret);
        }
    }

    /**
     * Test method
     * <code> void updateNotificationType(NotificationType notificationType) </code>.
     * @throws Exception to JUnit.
     */
    public void testUpdateNotificationType1Accuracy() throws Exception {
        NotificationType type = AccuracyHelper.createNotificationType(2);

        instance.addNotificationType(type);

        type.setName("developer");

        instance.updateNotificationType(type);

        NotificationType ret = instance.loadNotificationType(type.getId());

        assertEquals("The name should be updated to 'developer'", "developer", ret.getName());

        // Reset the name and update.
        type.setName("designer");
        instance.updateNotificationType(type);
        ret = instance.loadNotificationType(type.getId());
        assertEquals("The name should be updated to 'designer'", "designer", ret.getName());
    }

    /**
     * Test method
     * <code>NotificationType loadNotificationType(long notificationTypeId) </code>.
     * @throws Exception to JUnit.
     */
    public void testLoadNotificationType1Accuracy() throws Exception {
        NotificationType type = AccuracyHelper.createNotificationType(2);
        type.setName("tc");

        instance.addNotificationType(type);

        NotificationType ret = instance.loadNotificationType(type.getId());

        assertEquals("Id's should be the same", type.getId(), ret.getId());
        assertEquals("The name should be  'tc'", "tc", ret.getName());
    }

    /**
     * Test method <code>void addResourceRole(ResourceRole resourceRole) </code>.
     * First add a ResourceRole, then load it back to check if add method is
     * correct.
     * @throws Exception to JUnit.
     */
    public void testAddResourceRole1Accuracy() throws Exception {
        ResourceRole role = AccuracyHelper.createResourceRole(100);

        // delete the ResourceRole if it is already existing.
        instance.deleteResourceRole(role);

        // add the ResourceRole
        instance.addResourceRole(role);

        ResourceRole ret = instance.loadResourceRole(role.getId());

        assertNotNull("The resourceRole instance should be got back.", ret);
        assertEquals("The name should be 'tc'", "tc", ret.getName());
        assertEquals("The phaseType should be 1", new Integer(1).intValue(), ret.getPhaseType()
                .intValue());
        assertEquals("The description should be 'resource role'", "resource role", ret
                .getDescription());
    }

    /**
     * Test method
     * <code>void deleteResourceRole(ResourceRole resourceRole) </code>.
     * @throws Exception to JUnit.
     */
    public void testDeleteResourceRole1Accuracy() throws Exception {
        ResourceRole role = AccuracyHelper.createResourceRole(100);

        instance.deleteResourceRole(role);

        ResourceRole ret = instance.loadResourceRole(role.getId());
        assertNull("Should be deleted.", ret);
    }

    /**
     * Test method
     * <code>void deleteResourceRole(ResourceRole resourceRole) </code>. First
     * add a ResourceRole and check if it is added. Then delete the ResourceRole
     * and reload to check if it is deleted already.
     * @throws Exception to JUnit.
     */
    public void testDeleteResourceRole2Accuracy() throws Exception {
        ResourceRole role = AccuracyHelper.createResourceRole(100);

        instance.deleteResourceRole(role);

        // add the ResourceRole into the database.
        instance.addResourceRole(role);

        ResourceRole ret = instance.loadResourceRole(role.getId());
        // check if the ResourceRole is in the database.
        assertNotNull("Should not be null.", ret);

        // delete the ResourceRole.
        instance.deleteResourceRole(role);

        // reload the resourceRole.
        ret = instance.loadResourceRole(role.getId());

        // check if it is deleted.
        assertNull("Should be null.", ret);
    }

    /**
     * Test method
     * <code>void updateResourceRole(ResourceRole resourceRole)  </code>.
     * Update the phaseType to 2, name to "developer" and description to "test".
     * @throws Exception to JUnit.
     */
    public void testUpdateResourceRole1Accuracy() throws Exception {
        ResourceRole role = AccuracyHelper.createResourceRole(100);

        instance.deleteResourceRole(role);

        // add the ResourceRole into the database.
        instance.addResourceRole(role);

        // update the phaseType.
        role.setPhaseType(new Long(2));
        role.setName("developer");
        role.setDescription("test");

        instance.updateResourceRole(role);

        ResourceRole ret = instance.loadResourceRole(role.getId());
        // check if the ResourceRole is in the database.
        assertNotNull("Should not be null.", ret);
        assertEquals("Equal is expected.", role.getPhaseType().intValue(), ret.getPhaseType()
                .intValue());
        assertEquals("The name should be updated to 'developer'", "developer", ret.getName());
        assertEquals("The description should be updated to 'test'", "test", ret.getDescription());
    }

    /**
     * Test method
     * <code>ResourceRole loadResourceRole(long resourceRoleId) </code>.
     * @throws Exception to JUnit.
     */
    public void testLoadResourceRole1Accuracy() throws Exception {
        ResourceRole role = AccuracyHelper.createResourceRole(111);

        instance.deleteResourceRole(role);

        role.setPhaseType(new Long(2));
        role.setName("developer");
        role.setDescription("test");

        // add the ResourceRole into the database.
        instance.addResourceRole(role);

        ResourceRole ret = instance.loadResourceRole(role.getId());

        // check if the ResourceRole is in the database.
        assertNotNull("Should not be null.", ret);
        assertEquals("Equal is expected.", role.getPhaseType().intValue(), ret.getPhaseType()
                .intValue());
        assertEquals("The name should be  'developer'", "developer", ret.getName());
        assertEquals("The description should be  'test'", "test", ret.getDescription());

        role.setPhaseType(null);
        instance.updateResourceRole(role);
        instance.loadResourceRole(role.getId());
    }

    /**
     * Test method
     * <code>ResourceRole loadResourceRole(long resourceRoleId) </code>.
     * @throws Exception to JUnit.
     */
    public void testLoadResourceRole2Accuracy() throws Exception {
        ResourceRole role = new ResourceRole(5);
        role.setDescription("resource role");
        role.setName("tc");

        Date now = new Date();

        role.setCreationUser("developer");
        role.setCreationTimestamp(now);
        role.setModificationUser("developer");
        role.setModificationTimestamp(now);

        instance.deleteResourceRole(role);

        role.setName("developer");
        role.setDescription("test");

        // add the ResourceRole into the database.
        instance.addResourceRole(role);

        ResourceRole ret = instance.loadResourceRole(role.getId());

        // check if the ResourceRole is in the database.
        assertNotNull("Should not be null.", ret);
        assertEquals("The name should be  'developer'", "developer", ret.getName());
        assertEquals("The description should be  'test'", "test", ret.getDescription());
    }

    /**
     * Test method <code>Resource[] loadResources(long[] resourceIds) </code>.
     * In this test case, one resource will be added into the database.
     * @throws Exception to JUnit.
     */
    public void testLoadResources1Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");
        AccuracyHelper.insertIntoResource_info_type_lu(12, "sex");

        // create a resource instance.
        Resource r = AccuracyHelper.createResource(11, 1, 1);

        r.addSubmission(new Long(121));

        r.setProperty("name", "kaka");
        r.setProperty("sex", "male");

        ResourceRole role = AccuracyHelper.createResourceRole(5);
        instance.addResourceRole(role);

        instance.addResource(r);

        Resource[] ret = instance.loadResources(new long[] {11 });

        assertEquals("The size should be 1", 1, ret.length);

        Resource result = ret[0];
        assertEquals("The id should be 11", 11, result.getId());
        assertEquals("The submission id should be 121", 121, result.getSubmissions()[0].longValue());
        assertEquals("property 'name' value should be kaka", "kaka", result.getProperty("name"));
        assertEquals("property 'sex' value should be male", "male", result.getProperty("sex"));
    }

    /**
     * Test method <code>Resource[] loadResources(long[] resourceIds) </code>.
     * In this test case, multiple resource instance will be loaded.
     * @throws Exception to JUnit.
     */
    public void testLoadResources2Accuracy() throws Exception {
        AccuracyHelper.insertIntoResource_info_type_lu(11, "name");
        AccuracyHelper.insertIntoResource_info_type_lu(1, "name1");
        AccuracyHelper.insertIntoResource_info_type_lu(10, "name2");
        AccuracyHelper.insertIntoResource_info_type_lu(100, "name4");
        AccuracyHelper.insertIntoResource_info_type_lu(101, "name5");

        // create two resource instances.
        Resource r = AccuracyHelper.createResource(11, 1, 1);
        Resource r2 = AccuracyHelper.createResource(10, 1, 1);

        r.addSubmission(new Long(121));
        r2.addSubmission(new Long(1200));

        r.setProperty("name", new Integer(100));
        r.setProperty("name5", new Integer(1000));
        r.setProperty("name4", "value4");

        r2.setProperty("name2", "value2");
        r2.setProperty("name4", new Integer(10));

        ResourceRole role = AccuracyHelper.createResourceRole(5);

        instance.addResourceRole(role);

        // add resources;
        instance.addResource(r);
        instance.addResource(r2);

        Resource mytest1 = instance.loadResource(11);
        assertEquals("The size of the first resource should be 3", 3, mytest1.getAllProperties()
                .size());

        Resource[] ret = instance.loadResources(new long[] {11, 10 });

        assertNotNull("The resource got back should not be null.", ret);
        assertEquals("Should load 2 items", 2, ret.length);

        assertEquals("The size of the first resource should be 3", 3, ret[0].getAllProperties()
                .size());

        assertEquals("The value for name4 of the first resource should be 'value4'", "value4",
                ret[0].getProperty("name4"));

        assertEquals("The size of the second resource should be 2.", 2, ret[1].getAllProperties()
                .size());

        assertEquals("The value for name4 of the second resource should be integer 10", "10",
                ret[1].getProperty("name4"));
    }

    /**
     * Test method <code>Resource[] loadResources(long[] resourceIds) </code>.
     * In this test case, multiple resource instance will be loaded.
     * @throws Exception to JUnit.
     */
    public void testLoadResources3Accuracy() throws Exception {
        Resource[] ret = instance.loadResources(new long[] {});
        assertEquals("the length should be o", 0, ret.length);
    }

    /**
     * Test method <code>Resource[] loadResources(long[] resourceIds) </code>.
     * There is no resources in the table, should load no items.
     * @throws Exception to JUnit.
     */
    public void testLoadResourcesNone() throws Exception {
        Resource[] ret = instance.loadResources(new long[] {11, 10 });

        assertNotNull("The resource got back should not be null.", ret);
        assertEquals("There should have no item loaded", 0, ret.length);
    }

    /**
     * Test method
     * <code>NotificationType[] loadNotificationTypes(long[] notificationTypeIds) </code>.
     * @throws Exception to JUnit.
     */
    public void testLoadNotificationTypes1Accuracy() throws Exception {
        for (int i = 0; i < 5; i++) {
            NotificationType type = AccuracyHelper.createNotificationType(i + 1);
            instance.addNotificationType(type);
        }

        NotificationType[] ret = instance.loadNotificationTypes(new long[] {1, 2, 3, 4, 5 });
        assertNotNull("The notificationType instance should be returned.", ret);
        assertEquals("The size should be 5.", 5, ret.length);
    }

    /**
     * Test method
     * <code>NotificationType[] loadNotificationTypes(long[] notificationTypeIds) </code>.
     * @throws Exception to JUnit.
     */
    public void testLoadNotificationTypes2Accuracy() throws Exception {
        NotificationType[] ret = instance.loadNotificationTypes(new long[] {});
        assertEquals("The size should be 0.", 0, ret.length);
    }

    /**
     * Test method
     * <code>NotificationType[] loadNotificationTypes(long[] notificationTypeIds) </code>.
     * The notification is cleared, nothing should be loaded.
     * @throws Exception to JUnit.
     */
    public void testLoadNotificationTypesNoneAccuracy() throws Exception {
        NotificationType[] ret = instance.loadNotificationTypes(new long[] {1, 2, 3, 4, 5 });

        assertNotNull("The notificationType instance should be returned.", ret);
        assertEquals("The size should be 0.", 0, ret.length);
    }

    /**
     * Test method
     * <code>ResourceRole[] loadResourceRoles(long[] resourceRoleIds) </code>.
     * @throws Exception to JUnit.
     */
    public void testLoadResourceRolesAccuracy() throws Exception {
        ResourceRole role = AccuracyHelper.createResourceRole(100);
        ResourceRole role2 = AccuracyHelper.createResourceRole(11);

        // delete the ResourceRole if they are already existing.
        instance.deleteResourceRole(role);
        instance.deleteResourceRole(role2);

        // add the ResourceRoles.
        instance.addResourceRole(role);
        instance.addResourceRole(role2);

        ResourceRole[] ret = instance.loadResourceRoles(new long[] {100, 11 });

        assertNotNull("The resourceRole instance should be got back.", ret);
        assertEquals("The size should be 2", 2, ret.length);
    }

    /**
     * Test method
     * <code>ResourceRole[] loadResourceRoles(long[] resourceRoleIds) </code>.
     * @throws Exception to JUnit.
     */
    public void testLoadResourceRolesWith0ArrayArgumnetAccuracy() throws Exception {
        ResourceRole role = AccuracyHelper.createResourceRole(100);
        ResourceRole role2 = AccuracyHelper.createResourceRole(11);

        // delete the ResourceRole if they are already existing.
        instance.deleteResourceRole(role);
        instance.deleteResourceRole(role2);

        // add the ResourceRoles.
        instance.addResourceRole(role);
        instance.addResourceRole(role2);

        ResourceRole[] ret = instance.loadResourceRoles(new long[0]);

        assertNotNull("The resourceRole instance should be got back.", ret);
        assertEquals("The size should be 0", 0, ret.length);
    }

    /**
     * Test method
     * <code>ResourceRole[] loadResourceRoles(long[] resourceRoleIds) </code>.
     * @throws Exception to JUnit.
     */
    public void testLoadResourceRolesWith0ArrayArgumentsAccuracy() throws Exception {
        ResourceRole role = AccuracyHelper.createResourceRole(100);
        ResourceRole role2 = AccuracyHelper.createResourceRole(11);

        // delete the ResourceRole if they are already existing.
        instance.deleteResourceRole(role);
        instance.deleteResourceRole(role2);

        // add the ResourceRoles.
        instance.addResourceRole(role);
        instance.addResourceRole(role2);

        ResourceRole[] ret = instance.loadResourceRoles(new long[0]);

        assertNotNull("The resourceRole instance should be got back.", ret);
        assertEquals("The size should be 0", 0, ret.length);
    }

    /**
     * Test method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[]
     * notificationTypes) </code>.
     * @throws Exception to JUnit.
     */
    public void testLoadNotificationsAccuracy() throws Exception {
        NotificationType type = AccuracyHelper.createNotificationType(2);
        // first persist notificationType.
        instance.addNotificationType(type);

        instance.addNotification(1, 2, 2, "tc");
        instance.addNotification(2, 2, 2, "developer");

        Notification[] ret = instance.loadNotifications(new long[] {1, 2 }, new long[] {2, 2 },
                new long[] {2, 2 });

        assertEquals("The size returned should be 2.", 2, ret.length);
    }

    /**
     * Test method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[]
     * notificationTypes) </code>.
     * @throws Exception to JUnit.
     */
    public void testLoadNotificationsWithAll0ArrayArgumentsAccuracy() throws Exception {
        NotificationType type = AccuracyHelper.createNotificationType(2);
        // first persist notificationType.
        instance.addNotificationType(type);

        instance.addNotification(1, 2, 2, "tc");
        instance.addNotification(2, 2, 2, "developer");

        Notification[] ret = instance.loadNotifications(new long[0], new long[0], new long[0]);

        assertEquals("The size returned should be 0.", 0, ret.length);
    }

    /**
     * Test method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[]
     * notificationTypes) </code>.
     * The notification table is clear, should load no notifications.
     * @throws Exception to JUnit.
     */
    public void testLoadNotificationsNoneAccuracy() throws Exception {
        Notification[] ret = instance.loadNotifications(new long[] {1, 2 }, new long[] {2, 2 },
                new long[] {2, 2 });

        assertEquals("The size returned should be 0.", 0, ret.length);
    }

    /**
     * Test method
     * <code>Notification[] loadNotifications(long[] userIds, long[] projectIds, long[]
     * notificationTypes) </code>.
     * The arguments are all length-0 long array, no Notification instance would
     * be loaded.
     * @throws Exception to JUnit.
     */
    public void testLoadNotificationsWithLength0ArrayAccuracy() throws Exception {
        Notification[] ret = instance.loadNotifications(new long[0], new long[0], new long[0]);

        assertEquals("The size returned should be 0.", 0, ret.length);
    }
}
