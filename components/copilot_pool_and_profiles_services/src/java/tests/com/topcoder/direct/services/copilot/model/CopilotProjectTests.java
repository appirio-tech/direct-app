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
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Tests <code>{@link CopilotProject}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private CopilotProject instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CopilotProjectTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new CopilotProject();
    }

    /**
     * <p>Tests if <code>{@link CopilotProject}</code> class implements {@link java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {
        Assert.assertTrue("Class does not implements Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests if <code>{@link CopilotProject}</code> class extends {@link IdentifiableEntity} class.</p>
     */
    @Test
    public void testInheritance() {
        Assert.assertTrue("Class does not extends IdentifiableEntity class.",
                instance instanceof IdentifiableEntity);
    }

    /**
     * <p>Tests <code>{@link CopilotProject#CopilotProject()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {

        Assert.assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests <code>copilotProfileId</code> property, tests both <code>{@link
     * CopilotProject#setCopilotProfileId(long)}</code>
     * and <code>{@link CopilotProject#getCopilotProfileId()}</code> methods.</p>
     */
    @Test
    public void testCopilotProfileIdProperty() {
        Assert.assertEquals("Property copilotProfileId should be 0.", 0, instance.getCopilotProfileId());

        long copilotProfileId = 10l;
        instance.setCopilotProfileId(copilotProfileId);

        Assert.assertEquals("Invalid copilotProfileId property value.", copilotProfileId,
                instance.getCopilotProfileId());
    }

    /**
     * <p>Tests <code>name</code> property, tests both <code>{@link CopilotProject#setName(String)}</code> and
     * <code>{@link CopilotProject#getName()}</code> methods.</p>
     */
    @Test
    public void testNameProperty() {
        Assert.assertNull("Property name should be null.", instance.getName());

        String name = "name";
        instance.setName(name);

        Assert.assertEquals("Invalid name property value.", name, instance.getName());
    }

    /**
     * <p>Tests <code>tcDirectProjectId</code> property, tests both <code>{@link
     * CopilotProject#setTcDirectProjectId(long)}</code>
     * and <code>{@link CopilotProject#getTcDirectProjectId()}</code> methods.</p>
     */
    @Test
    public void testTcDirectProjectIdProperty() {
        Assert.assertEquals("Property tcDirectProjectId should be 0.", 0, instance.getTcDirectProjectId());

        long tcDirectProjectId = 10l;
        instance.setTcDirectProjectId(tcDirectProjectId);

        Assert.assertEquals("Invalid tcDirectProjectId property value.", tcDirectProjectId,
                instance.getTcDirectProjectId());
    }

    /**
     * <p>Tests <code>copilotType</code> property, tests both <code>{@link
     * CopilotProject#setCopilotType(CopilotType)}</code>
     * and <code>{@link CopilotProject#getCopilotType()}</code> methods.</p>
     */
    @Test
    public void testCopilotTypeProperty() {
        Assert.assertNull("Property copilotType should be null.", instance.getCopilotType());

        CopilotType copilotType = new CopilotType();
        instance.setCopilotType(copilotType);

        Assert.assertEquals("Invalid copilotType property value.", copilotType, instance.getCopilotType());
    }

    /**
     * <p>Tests <code>status</code> property, tests both <code>{@link
     * CopilotProject#setStatus(CopilotProjectStatus)}</code>
     * and <code>{@link CopilotProject#getStatus()}</code> methods.</p>
     */
    @Test
    public void testStatusProperty() {
        Assert.assertNull("Property status should be null.", instance.getStatus());

        CopilotProjectStatus status = new CopilotProjectStatus();
        instance.setStatus(status);

        Assert.assertEquals("Invalid status property value.", status, instance.getStatus());
    }

    /**
     * <p>Tests <code>customerFeedback</code> property, tests both <code>{@link
     * CopilotProject#setCustomerFeedback(String)}</code>
     * and <code>{@link CopilotProject#getCustomerFeedback()}</code> methods.</p>
     */
    @Test
    public void testCustomerFeedbackProperty() {
        Assert.assertNull("Property customerFeedback should be null.", instance.getCustomerFeedback());

        String customerFeedback = "customerFeedback";
        instance.setCustomerFeedback(customerFeedback);

        Assert.assertEquals("Invalid customerFeedback property value.", customerFeedback,
                instance.getCustomerFeedback());
    }

    /**
     * <p>Tests <code>customerRating</code> property, tests both <code>{@link
     * CopilotProject#setCustomerRating(Float)}</code>
     * and <code>{@link CopilotProject#getCustomerRating()}</code> methods.</p>
     */
    @Test
    public void testCustomerRatingProperty() {
        Assert.assertNull("Property customerRating should be null.", instance.getCustomerRating());

        Float customerRating = 10F;
        instance.setCustomerRating(customerRating);

        Assert.assertEquals("Invalid customerRating property value.", customerRating, instance.getCustomerRating());
    }

    /**
     * <p>Tests <code>pmFeedback</code> property, tests both <code>{@link CopilotProject#setPmFeedback(String)}</code>
     * and <code>{@link CopilotProject#getPmFeedback()}</code> methods.</p>
     */
    @Test
    public void testPmFeedbackProperty() {
        Assert.assertNull("Property pmFeedback should be null.", instance.getPmFeedback());

        String pmFeedback = "pmFeedback";
        instance.setPmFeedback(pmFeedback);

        Assert.assertEquals("Invalid pmFeedback property value.", pmFeedback, instance.getPmFeedback());
    }

    /**
     * <p>Tests <code>pmRating</code> property, tests both <code>{@link CopilotProject#setPmRating(Float)}</code> and
     * <code>{@link CopilotProject#getPmRating()}</code> methods.</p>
     */
    @Test
    public void testPmRatingProperty() {
        Assert.assertNull("Property pmRating should be null.", instance.getPmRating());

        Float pmRating = 10F;
        instance.setPmRating(pmRating);

        Assert.assertEquals("Invalid pmRating property value.", pmRating, instance.getPmRating());
    }

    /**
     * <p>Tests <code>privateProject</code> property, tests both <code>{@link
     * CopilotProject#setPrivateProject(boolean)}</code>
     * and <code>{@link CopilotProject#isPrivateProject()}</code> methods.</p>
     */
    @Test
    public void testPrivateProjectProperty() {
        Assert.assertFalse("Property privateProject should be false.", instance.isPrivateProject());

        boolean privateProject = true;
        instance.setPrivateProject(privateProject);

        Assert.assertEquals("Invalid privateProject property value.", privateProject, instance.isPrivateProject());
    }

    /**
     * <p>Tests <code>projectInfos</code> property, tests both <code>{@link
     * CopilotProject#setProjectInfos(java.util.Set)}</code>
     * and <code>{@link CopilotProject#getProjectInfos()}</code> methods.</p>
     */
    @Test
    public void testProjectInfosProperty() {
        Assert.assertNull("Property projectInfos should be null.", instance.getProjectInfos());

        Set<CopilotProjectInfo> projectInfos = new HashSet<CopilotProjectInfo>();
        instance.setProjectInfos(projectInfos);

        Assert.assertEquals("Invalid projectInfos property value.", projectInfos, instance.getProjectInfos());
    }

    /**
     * <p>Tests <code>completionDate</code> property, tests both <code>{@link
     * CopilotProject#setCompletionDate(Date)}</code>
     * and <code>{@link CopilotProject#getCompletionDate()}</code> methods.</p>
     */
    @Test
    public void testCompletionDateProperty() {
        Assert.assertNull("Property completionDate should be null.", instance.getCompletionDate());

        Date completionDate = new Date();
        instance.setCompletionDate(completionDate);

        Assert.assertEquals("Invalid completionDate property value.", completionDate, instance.getCompletionDate());
    }

    /**
     * <p>Tests <code>id</code> property, tests both <code>{@link CopilotProject#setId(long)}</code> and <code>{@link
     * CopilotProject#getId()}</code> methods.</p>
     */
    @Test
    public void testIdProperty() {
        Assert.assertEquals("Property id should be 0.", 0, instance.getId());

        long id = 10l;
        instance.setId(id);

        Assert.assertEquals("Invalid id property value.", id, instance.getId());
    }

    /**
     * <p>Tests <code>createUser</code> property, tests both <code>{@link CopilotProject#setCreateUser(String)}</code>
     * and <code>{@link CopilotProject#getCreateUser()}</code> methods.</p>
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
     * CopilotProject#setCreateDate(java.util.Date)}</code>
     * and <code>{@link CopilotProject#getCreateDate()}</code> methods.</p>
     */
    @Test
    public void testCreateDateProperty() {
        Assert.assertNull("Property createDate should be null.", instance.getCreateDate());

        Date createDate = new Date();
        instance.setCreateDate(createDate);

        Assert.assertEquals("Invalid createDate property value.", createDate, instance.getCreateDate());
    }

    /**
     * <p>Tests <code>modifyUser</code> property, tests both <code>{@link CopilotProject#setModifyUser(String)}</code>
     * and <code>{@link CopilotProject#getModifyUser()}</code> methods.</p>
     */
    @Test
    public void testModifyUserProperty() {
        Assert.assertNull("Property modifyUser should be null.", instance.getModifyUser());

        String modifyUser = "modifyUser";
        instance.setModifyUser(modifyUser);

        Assert.assertEquals("Invalid modifyUser property value.", modifyUser, instance.getModifyUser());
    }

    /**
     * <p>Tests <code>modifyDate</code> property, tests both <code>{@link CopilotProject#setModifyDate(Date)}</code> and
     * <code>{@link CopilotProject#getModifyDate()}</code> methods.</p>
     */
    @Test
    public void testModifyDateProperty() {
        Assert.assertNull("Property modifyDate should be null.", instance.getModifyDate());

        Date modifyDate = new Date();
        instance.setModifyDate(modifyDate);

        Assert.assertEquals("Invalid modifyDate property value.", modifyDate, instance.getModifyDate());
    }
}
