/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for class <code>IdentifiableEntity</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MilestoneTest {

    /**
     * Represents the <code>Milestone</code> instance used to test against.
     */
    private Milestone impl;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(MilestoneTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Before
    public void setUp() throws Exception {
        impl = new Milestone();
    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @After
    public void tearDown() throws Exception {
        impl = null;
    }

    /**
     * Inheritance test, verifies <code>Milestone</code> subclasses should be correct.
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof NamedEntity);
    }

    /**
     * Accuracy test for the constructor <code>Milestone()</code>.<br>
     * Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>getDescription()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetDescription() {
        assertNull("The initial value should be null", impl.getDescription());
        String expect = "test";
        impl.setDescription(expect);
        assertEquals("The result should be same as", expect, impl.getDescription());
    }

    /**
     * Accuracy test for the method <code>setDescription(String)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetDescription() {
        assertNull("The initial value should be null", impl.getDescription());
        String expect = "test";
        impl.setDescription(expect);
        assertEquals("The result should be same as", expect, impl.getDescription());
    }

    /**
     * Accuracy test for the method <code>getDueDate()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetDueDate() {
        assertNull("The initial value should be null", impl.getDueDate());
        Date expect = new Date();
        impl.setDueDate(expect);
        assertEquals("The result should be same as", expect, impl.getDueDate());
    }

    /**
     * Accuracy test for the method <code>setDueDate(Date)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetDueDate() {
        assertNull("The initial value should be null", impl.getDueDate());
        Date expect = new Date();
        impl.setDueDate(expect);
        assertEquals("The result should be same as", expect, impl.getDueDate());
    }

    /**
     * Accuracy test for the method <code>getOwners()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetOwners() {
        assertNull("The initial value should be null", impl.getOwners());
        List<ResponsiblePerson> expect = new ArrayList<ResponsiblePerson>();
        impl.setOwners(expect);
        assertEquals("The result should be same as", expect, impl.getOwners());
    }

    /**
     * Accuracy test for the method <code>setOwners(List&lt;ResponsiblePerson&gt;)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetOwners() {
        assertNull("The initial value should be null", impl.getOwners());
        List<ResponsiblePerson> expect = new ArrayList<ResponsiblePerson>();
        impl.setOwners(expect);
        assertEquals("The result should be same as", expect, impl.getOwners());
    }

    /**
     * Accuracy test for the method <code>isSendNotifications()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testIsSendNotifications() {
        assertFalse("The initial value should be false.", impl.isSendNotifications());
        impl.setSendNotifications(true);
        assertTrue("The result should be true.", impl.isSendNotifications());
    }

    /**
     * Accuracy test for the method <code>setSendNotifications(boolean)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetSendNotifications() {
        assertFalse("The initial value should be false.", impl.isSendNotifications());
        impl.setSendNotifications(true);
        assertTrue("The result should be true.", impl.isSendNotifications());
    }

    /**
     * Accuracy test for the method <code>isCompleted()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testIsCompleted() {
        assertFalse("The initial value should be false.", impl.isCompleted());
        impl.setCompleted(true);
        assertTrue("The result should be true.", impl.isCompleted());
    }

    /**
     * Accuracy test for the method <code>setCompleted(boolean)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetCompleted() {
        assertFalse("The initial value should be false.", impl.isCompleted());
        impl.setCompleted(true);
        assertTrue("The result should be true.", impl.isCompleted());
    }

    /**
     * Accuracy test for the method <code>getProjectId()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetProjectId() {
        assertEquals("The initial value should be same as", 0, impl.getProjectId());
        long expect = 1L;
        impl.setProjectId(expect);
        assertEquals("The result should be same as", expect, impl.getProjectId());
    }

    /**
     * Accuracy test for the method <code>setProjectId(long)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetProjectId() {
        assertEquals("The initial value should be same as", 0, impl.getProjectId());
        long expect = 1L;
        impl.setProjectId(expect);
        assertEquals("The result should be same as", expect, impl.getProjectId());
    }

    /**
     * Accuracy test for the method <code>getStatus()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetStatus() {
        assertNull("The initial value should be null", impl.getStatus());
        MilestoneStatus expect = MilestoneStatus.COMPLETED;
        impl.setStatus(expect);
        assertEquals("The result should be same as", expect, impl.getStatus());
    }

    /**
     * Accuracy test for the method <code>setStatus(MilestoneStatus)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetStatus() {
        assertNull("The initial value should be null", impl.getStatus());
        MilestoneStatus expect = MilestoneStatus.COMPLETED;
        impl.setStatus(expect);
        assertEquals("The result should be same as", expect, impl.getStatus());
    }

    /**
     * Accuracy test for the method <code>toString()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testToString() {
        impl.setId(1);
        impl.setName("name1");
        impl.setCompleted(true);
        impl.setDescription("description1");
        impl.setProjectId(3);
        impl.setSendNotifications(false);
        impl.setStatus(MilestoneStatus.COMPLETED);

        List<ResponsiblePerson> people = new ArrayList<ResponsiblePerson>();
        ResponsiblePerson person = new ResponsiblePerson();
        person.setId(1);
        person.setName("person1");
        person.setUserId(123456);
        people.add(person);
        impl.setOwners(people);

        String expect = "{id:1, name:name1, description:description1, dueDate:null,"
            + " owners:[{id:1, name:person1, userId:123456}], sendNotifications:false, completed:true,"
            + " projectId:3, status:COMPLETED}";
        assertEquals("The result should be same as", expect, impl.toString());
    }

}
