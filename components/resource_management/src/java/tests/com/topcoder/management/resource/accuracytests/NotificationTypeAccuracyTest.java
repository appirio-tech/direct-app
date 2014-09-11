/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.management.resource.AuditableResourceStructure;
import com.topcoder.management.resource.IdAlreadySetException;
import com.topcoder.management.resource.NotificationType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>NotificationType</code> class. It tests the NotificationType(),
 * NotificationType(long) and NotificationType(long, String) constructors;
 * getDescription(), getId(), getName(), setDescription(String), setId(long) and
 * setName(String) methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class NotificationTypeAccuracyTest extends TestCase {
    /**
     * <p>
     * The id of <code>NotificationType</code> instance for test.
     * </p>
     */
    private long id = 1;

    /**
     * <p>
     * The name of <code>NotificationType</code> instance for test.
     * </p>
     */
    private String name = "NotificationType#1";

    /**
     * <p>
     * The instance of <code>NotificationType</code> for test.
     * </p>
     */
    private NotificationType type = null;

    /**
     * <p>
     * Test suite of <code>NotificationTypeAccuracyTest</code>.
     * </p>
     * @return Test suite of <code>NotificationTypeAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(NotificationTypeAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>NotificationType()</code> constructor.
     * </p>
     */
    public void testNotificationTypeCtor1() {
        type = new NotificationType();

        // check null here.
        assertNotNull("Create NotificationType failed.", type);

        // check the type here.
        assertTrue("NotificationType should extend AuditableResourceStructure.",
                type instanceof AuditableResourceStructure);

        // check the fields here.
        assertEquals("The id should be: -1.", -1, type.getId());
        assertNull("The type name should be: null.", type.getName());
        assertNull("The type description should be: null.", type.getDescription());
        assertEquals("The 'UNSET_ID' should be: -1.", -1, NotificationType.UNSET_ID);
    }

    /**
     * <p>
     * Accuracy test of <code>NotificationType(long)</code> constructor.
     * </p>
     */
    public void testNotificationTypeCtor2() {
        type = new NotificationType(id);

        // check null here.
        assertNotNull("Create NotificationType failed.", type);

        // check the type here.
        assertTrue("NotificationType should extend AuditableResourceStructure.",
                type instanceof AuditableResourceStructure);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, type.getId());
        assertNull("The type name should be: null.", type.getName());
        assertNull("The type description should be: null.", type.getDescription());
        assertEquals("The 'UNSET_ID' should be: -1.", -1, NotificationType.UNSET_ID);
    }

    /**
     * <p>
     * Accuracy test of <code>NotificationType(long, String)</code>
     * constructor.
     * </p>
     */
    public void testNotificationTypeCtor3() {
        type = new NotificationType(id, name);

        // check null here.
        assertNotNull("Create NotificationType failed.", type);

        // check the type here.
        assertTrue("NotificationType should extend AuditableResourceStructure.",
                type instanceof AuditableResourceStructure);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, type.getId());
        assertEquals("The type name should be: " + name + ".", name, type.getName());
        assertNull("The type description should be: null.", type.getDescription());
        assertEquals("The 'UNSET_ID' should be: -1.", -1, NotificationType.UNSET_ID);
    }

    /**
     * <p>
     * Accuracy test of the <code>getId()</code> method.
     * </p>
     */
    public void testMethod_getId() {
        type = new NotificationType();

        assertEquals("The id should be: -1.", -1, type.getId());

        // set and check the id here.
        type.setId(id);
        assertEquals("The id should be: " + id + ".", id, type.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>setId(long)</code> method.
     * </p>
     */
    public void testMethod_setId() {
        type = new NotificationType();

        // check the id here.
        type.setId(id);
        assertEquals("The id should be: " + id + ".", id, type.getId());

        // set the id again.
        try {
            type.setId(100);
            fail("IdAlreadySetException should be thrown here.");
        } catch (IdAlreadySetException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>getName()</code> method.
     * </p>
     */
    public void testMethod_getName() {
        type = new NotificationType(id, name);

        // check the name here.
        assertEquals("The type name should be: " + name + ".", name, type.getName());

        // set the name to null and then test it.
        type.setName(null);
        assertNull("The type name should be: null.", type.getName());
    }

    /**
     * <p>
     * Accuracy test of the <code>setName(String)</code> method.
     * </p>
     */
    public void testMethod_setName() {
        type = new NotificationType(id);
        type.setName(name);

        // check the name here.
        assertEquals("The type name should be: " + name + ".", name, type.getName());

        // set the name to null and then test it.
        type.setName(null);
        assertNull("The type name should be: null.", type.getName());

        // set the name and then test it.
        type = new NotificationType(id, "newName");

        // check the name here.
        assertEquals("The type name should be: newName.", "newName", type.getName());
    }

    /**
     * <p>
     * Accuracy test of the <code>getDescription()</code> method.
     * </p>
     */
    public void testMethod_getDescription() {
        type = new NotificationType(id, name);
        assertNull("The type description should be: null.", type.getDescription());

        // set the description and then test it.
        String description = "This is a notification type.";
        type.setDescription(description);
        assertEquals("The type description should be: " + description + ".", description, type
                .getDescription());
    }

    /**
     * <p>
     * Accuracy test of the <code>setDescription(String)</code> method.
     * </p>
     */
    public void testMethod_setDescription() {
        // set the description and then test it.
        type = new NotificationType(id, name);

        String description = "This is a notification type.";
        type.setDescription(description);
        assertEquals("The type description should be: " + description + ".", description, type
                .getDescription());

        // set to null and then test it.
        type.setDescription(null);
        assertNull("The type description should be: null.", type.getDescription());
    }
}
