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
 * <p>Tests <code>{@link CopilotProjectPlan}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectPlanTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private CopilotProjectPlan instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CopilotProjectPlanTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new CopilotProjectPlan();
    }

    /**
     * <p>Tests if <code>{@link CopilotProjectPlan}</code> class implements {@link java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {
        Assert.assertTrue("Class does not implements Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests if <code>{@link CopilotProjectPlan}</code> class extends {@link IdentifiableEntity} class.</p>
     */
    @Test
    public void testInheritance() {
        Assert.assertTrue("Class does not extends IdentifiableEntity class.",
                instance instanceof IdentifiableEntity);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectPlan#CopilotProjectPlan()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {

        Assert.assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests <code>plannedDuration</code> property, tests both <code>{@link
     * CopilotProjectPlan#setPlannedDuration(int)}</code>
     * and <code>{@link CopilotProjectPlan#getPlannedDuration()}</code> methods.</p>
     */
    @Test
    public void testPlannedDurationProperty() {
        Assert.assertEquals("Property plannedDuration should be 0.", 0, instance.getPlannedDuration());

        int plannedDuration = 10;
        instance.setPlannedDuration(plannedDuration);

        Assert.assertEquals("Invalid plannedDuration property value.", plannedDuration,
                instance.getPlannedDuration());
    }

    /**
     * <p>Tests <code>plannedBugRaces</code> property, tests both <code>{@link
     * CopilotProjectPlan#setPlannedBugRaces(int)}</code>
     * and <code>{@link CopilotProjectPlan#getPlannedBugRaces()}</code> methods.</p>
     */
    @Test
    public void testPlannedBugRacesProperty() {
        Assert.assertEquals("Property plannedBugRaces should be 0.", 0, instance.getPlannedBugRaces());

        int plannedBugRaces = 10;
        instance.setPlannedBugRaces(plannedBugRaces);

        Assert.assertEquals("Invalid plannedBugRaces property value.", plannedBugRaces,
                instance.getPlannedBugRaces());
    }

    /**
     * <p>Tests <code>copilotProjectId</code> property, tests both <code>{@link
     * CopilotProjectPlan#setCopilotProjectId(long)}</code>
     * and <code>{@link CopilotProjectPlan#getCopilotProjectId()}</code> methods.</p>
     */
    @Test
    public void testCopilotProjectIdProperty() {
        Assert.assertEquals("Property copilotProjectId should be 0.", 0, instance.getCopilotProjectId());

        long copilotProjectId = 10l;
        instance.setCopilotProjectId(copilotProjectId);

        Assert.assertEquals("Invalid copilotProjectId property value.", copilotProjectId,
                instance.getCopilotProjectId());
    }

    /**
     * <p>Tests <code>plannedContests</code> property, tests both <code>{@link
     * CopilotProjectPlan#setPlannedContests(Set)}</code>
     * and <code>{@link CopilotProjectPlan#getPlannedContests()}</code> methods.</p>
     */
    @Test
    public void testPlannedContestsProperty() {
        Assert.assertNull("Property plannedContests should be null.", instance.getPlannedContests());

        Set<PlannedContest> plannedContests = new HashSet<PlannedContest>();
        instance.setPlannedContests(plannedContests);

        Assert.assertEquals("Invalid plannedContests property value.", plannedContests, instance.getPlannedContests());
    }

    /**
     * <p>Tests <code>id</code> property, tests both <code>{@link CopilotProjectPlan#setId(long)}</code> and
     * <code>{@link CopilotProjectPlan#getId()}</code> methods.</p>
     */
    @Test
    public void testIdProperty() {
        Assert.assertEquals("Property id should be 0.", 0, instance.getId());

        long id = 10l;
        instance.setId(id);

        Assert.assertEquals("Invalid id property value.", id, instance.getId());
    }

    /**
     * <p>Tests <code>createUser</code> property, tests both <code>{@link
     * CopilotProjectPlan#setCreateUser(String)}</code>
     * and <code>{@link CopilotProjectPlan#getCreateUser()}</code> methods.</p>
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
     * CopilotProjectPlan#setCreateDate(java.util.Date)}</code>
     * and <code>{@link CopilotProjectPlan#getCreateDate()}</code> methods.</p>
     */
    @Test
    public void testCreateDateProperty() {
        Assert.assertNull("Property createDate should be null.", instance.getCreateDate());

        Date createDate = new Date();
        instance.setCreateDate(createDate);

        Assert.assertEquals("Invalid createDate property value.", createDate, instance.getCreateDate());
    }

    /**
     * <p>Tests <code>modifyUser</code> property, tests both <code>{@link
     * CopilotProjectPlan#setModifyUser(String)}</code>
     * and <code>{@link CopilotProjectPlan#getModifyUser()}</code> methods.</p>
     */
    @Test
    public void testModifyUserProperty() {
        Assert.assertNull("Property modifyUser should be null.", instance.getModifyUser());

        String modifyUser = "modifyUser";
        instance.setModifyUser(modifyUser);

        Assert.assertEquals("Invalid modifyUser property value.", modifyUser, instance.getModifyUser());
    }

    /**
     * <p>Tests <code>modifyDate</code> property, tests both <code>{@link CopilotProjectPlan#setModifyDate(Date)}</code>
     * and <code>{@link CopilotProjectPlan#getModifyDate()}</code> methods.</p>
     */
    @Test
    public void testModifyDateProperty() {
        Assert.assertNull("Property modifyDate should be null.", instance.getModifyDate());

        Date modifyDate = new Date();
        instance.setModifyDate(modifyDate);

        Assert.assertEquals("Invalid modifyDate property value.", modifyDate, instance.getModifyDate());
    }
}
