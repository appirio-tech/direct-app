/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.direct.services.copilot.model;

import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Tests <code>{@link PlannedContest}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class PlannedContestTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private PlannedContest instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(PlannedContestTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new PlannedContest();
    }

    /**
     * <p>Tests if <code>{@link PlannedContest}</code> class implements {@link java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {
        Assert.assertTrue("Class does not implements Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests if <code>{@link PlannedContest}</code> class extends {@link IdentifiableEntity} class.</p>
     */
    @Test
    public void testInheritance() {
        Assert.assertTrue("Class does not extends IdentifiableEntity class.",
                instance instanceof IdentifiableEntity);
    }

    /**
     * <p>Tests <code>{@link PlannedContest#PlannedContest()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {

        Assert.assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests <code>name</code> property, tests both <code>{@link PlannedContest#setName(String)}</code> and
     * <code>{@link PlannedContest#getName()}</code> methods.</p>
     */
    @Test
    public void testNameProperty() {
        Assert.assertNull("Property name should be null.", instance.getName());

        String name = "name";
        instance.setName(name);

        Assert.assertEquals("Invalid name property value.", name, instance.getName());
    }

    /**
     * <p>Tests <code>description</code> property, tests both <code>{@link PlannedContest#setDescription(String)}</code>
     * and <code>{@link PlannedContest#getDescription()}</code> methods.</p>
     */
    @Test
    public void testDescriptionProperty() {
        Assert.assertNull("Property description should be null.", instance.getDescription());

        String description = "description";
        instance.setDescription(description);

        Assert.assertEquals("Invalid description property value.", description, instance.getDescription());
    }

    /**
     * <p>Tests <code>projectCategoryId</code> property, tests both <code>{@link
     * PlannedContest#setProjectCategoryId(long)}</code>
     * and <code>{@link PlannedContest#getProjectCategoryId()}</code> methods.</p>
     */
    @Test
    public void testProjectCategoryIdProperty() {
        Assert.assertEquals("Property projectCategoryId should be 0.", 0, instance.getProjectCategoryId());

        long projectCategoryId = 10l;
        instance.setProjectCategoryId(projectCategoryId);

        Assert.assertEquals("Invalid projectCategoryId property value.", projectCategoryId,
                instance.getProjectCategoryId());
    }

    /**
     * <p>Tests <code>copilotProjectPlanId</code> property, tests both <code>{@link
     * PlannedContest#setCopilotProjectPlanId(long)}</code> and <code>{@link
     * PlannedContest#getCopilotProjectPlanId()}</code>
     * methods.</p>
     */
    @Test
    public void testCopilotProjectPlanIdProperty() {
        Assert.assertEquals("Property copilotProjectPlanId should be 0.", 0, instance.getCopilotProjectPlanId());

        long copilotProjectPlanId = 10l;
        instance.setCopilotProjectPlanId(copilotProjectPlanId);

        Assert.assertEquals("Invalid copilotProjectPlanId property value.", copilotProjectPlanId,
                instance.getCopilotProjectPlanId());
    }

    /**
     * <p>Tests <code>startDate</code> property, tests both <code>{@link PlannedContest#setStartDate(Date)}</code> and
     * <code>{@link PlannedContest#getStartDate()}</code> methods.</p>
     */
    @Test
    public void testStartDateProperty() {
        Assert.assertNull("Property startDate should be null.", instance.getStartDate());

        Date startDate = new Date();
        instance.setStartDate(startDate);

        Assert.assertEquals("Invalid startDate property value.", startDate, instance.getStartDate());
    }

    /**
     * <p>Tests <code>endDate</code> property, tests both <code>{@link PlannedContest#setEndDate(Date)}</code> and
     * <code>{@link PlannedContest#getEndDate()}</code> methods.</p>
     */
    @Test
    public void testEndDateProperty() {
        Assert.assertNull("Property endDate should be null.", instance.getEndDate());

        Date endDate = new Date();
        instance.setEndDate(endDate);

        Assert.assertEquals("Invalid endDate property value.", endDate, instance.getEndDate());
    }

    /**
     * <p>Tests <code>id</code> property, tests both <code>{@link PlannedContest#setId(long)}</code> and <code>{@link
     * PlannedContest#getId()}</code> methods.</p>
     */
    @Test
    public void testIdProperty() {
        Assert.assertEquals("Property id should be 0.", 0, instance.getId());

        long id = 10l;
        instance.setId(id);

        Assert.assertEquals("Invalid id property value.", id, instance.getId());
    }

    /**
     * <p>Tests <code>createUser</code> property, tests both <code>{@link PlannedContest#setCreateUser(String)}</code>
     * and <code>{@link PlannedContest#getCreateUser()}</code> methods.</p>
     */
    @Test
    public void testCreateUserProperty() {
        Assert.assertNull("Property createUser should be null.", instance.getCreateUser());

        String createUser = "createUser";
        instance.setCreateUser(createUser);

        Assert.assertEquals("Invalid createUser property value.", createUser, instance.getCreateUser());
    }

    /**
     * <p>Tests <code>createDate</code> property, tests both <code>{@link
     * PlannedContest#setCreateDate(java.util.Date)}</code>
     * and <code>{@link PlannedContest#getCreateDate()}</code> methods.</p>
     */
    @Test
    public void testCreateDateProperty() {
        Assert.assertNull("Property createDate should be null.", instance.getCreateDate());

        Date createDate = new Date();
        instance.setCreateDate(createDate);

        Assert.assertEquals("Invalid createDate property value.", createDate, instance.getCreateDate());
    }

    /**
     * <p>Tests <code>modifyUser</code> property, tests both <code>{@link PlannedContest#setModifyUser(String)}</code>
     * and <code>{@link PlannedContest#getModifyUser()}</code> methods.</p>
     */
    @Test
    public void testModifyUserProperty() {
        Assert.assertNull("Property modifyUser should be null.", instance.getModifyUser());

        String modifyUser = "modifyUser";
        instance.setModifyUser(modifyUser);

        Assert.assertEquals("Invalid modifyUser property value.", modifyUser, instance.getModifyUser());
    }

    /**
     * <p>Tests <code>modifyDate</code> property, tests both <code>{@link PlannedContest#setModifyDate(Date)}</code> and
     * <code>{@link PlannedContest#getModifyDate()}</code> methods.</p>
     */
    @Test
    public void testModifyDateProperty() {
        Assert.assertNull("Property modifyDate should be null.", instance.getModifyDate());

        Date modifyDate = new Date();
        instance.setModifyDate(modifyDate);

        Assert.assertEquals("Invalid modifyDate property value.", modifyDate, instance.getModifyDate());
    }
}
