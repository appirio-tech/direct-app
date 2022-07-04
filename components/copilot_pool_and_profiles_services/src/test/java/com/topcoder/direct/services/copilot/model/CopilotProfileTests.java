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
 * <p>Tests <code>{@link CopilotProfile}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProfileTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private CopilotProfile instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CopilotProfileTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new CopilotProfile();
    }

    /**
     * <p>Tests if <code>{@link CopilotProfile}</code> class implements {@link java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {
        Assert.assertTrue("Class does not implements Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests if <code>{@link CopilotProfile}</code> class extends {@link IdentifiableEntity} class.</p>
     */
    @Test
    public void testInheritance() {
        Assert.assertTrue("Class does not extends IdentifiableEntity class.",
                instance instanceof IdentifiableEntity);
    }

    /**
     * <p>Tests <code>{@link CopilotProfile#CopilotProfile()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {

        Assert.assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests <code>userId</code> property, tests both <code>{@link CopilotProfile#setUserId(long)}</code> and
     * <code>{@link CopilotProfile#getUserId()}</code> methods.</p>
     */
    @Test
    public void testUserIdProperty() {
        Assert.assertEquals("Property userId should be 0.", 0, instance.getUserId());

        long userId = 10l;
        instance.setUserId(userId);

        Assert.assertEquals("Invalid userId property value.", userId,
                instance.getUserId());
    }

    /**
     * <p>Tests <code>status</code> property, tests both <code>{@link
     * CopilotProfile#setStatus(CopilotProfileStatus)}</code>
     * and <code>{@link CopilotProfile#getStatus()}</code> methods.</p>
     */
    @Test
    public void testStatusProperty() {
        Assert.assertNull("Property status should be null.", instance.getStatus());

        CopilotProfileStatus status = new CopilotProfileStatus();
        instance.setStatus(status);

        Assert.assertEquals("Invalid status property value.", status, instance.getStatus());
    }

    /**
     * <p>Tests <code>suspensionCount</code> property, tests both <code>{@link
     * CopilotProfile#setSuspensionCount(int)}</code>
     * and <code>{@link CopilotProfile#getSuspensionCount()}</code> methods.</p>
     */
    @Test
    public void testSuspensionCountProperty() {
        Assert.assertEquals("Property suspensionCount should be 0.", 0, instance.getSuspensionCount());

        int suspensionCount = 10;
        instance.setSuspensionCount(suspensionCount);

        Assert.assertEquals("Invalid suspensionCount property value.", suspensionCount, instance.getSuspensionCount());
    }

    /**
     * <p>Tests <code>reliability</code> property, tests both <code>{@link CopilotProfile#setReliability(float)}</code>
     * and <code>{@link CopilotProfile#getReliability()}</code> methods.</p>
     */
    @Test
    public void testReliabilityProperty() {
        Assert.assertEquals("Property reliability should be 0.", 0, 0, instance.getReliability());

        float reliability = 10F;
        instance.setReliability(reliability);

        Assert.assertEquals("Invalid reliability property value.", reliability, 0, instance.getReliability());
    }

    /**
     * <p>Tests <code>activationDate</code> property, tests both <code>{@link
     * CopilotProfile#setActivationDate(java.util.Date)}</code>
     * and <code>{@link CopilotProfile#getActivationDate()}</code> methods.</p>
     */
    @Test
    public void testActivationDateProperty() {
        Assert.assertNull("Property activationDate should be null.", instance.getActivationDate());

        Date activationDate = new Date();
        instance.setActivationDate(activationDate);

        Assert.assertEquals("Invalid activationDate property value.", activationDate, instance.getActivationDate());
    }

    /**
     * <p>Tests <code>showCopilotEarnings</code> property, tests both <code>{@link
     * CopilotProfile#setShowCopilotEarnings(boolean)}</code>
     * and <code>{@link CopilotProfile#isShowCopilotEarnings()}</code> methods.</p>
     */
    @Test
    public void testShowCopilotEarningsProperty() {
        Assert.assertFalse("Property showCopilotEarnings should be false.", instance.isShowCopilotEarnings());

        boolean showCopilotEarnings = true;
        instance.setShowCopilotEarnings(showCopilotEarnings);

        Assert.assertEquals("Invalid showCopilotEarnings property value.", showCopilotEarnings,
                instance.isShowCopilotEarnings());
    }

    /**
     * <p>Tests <code>profileInfos</code> property, tests both <code>{@link
     * CopilotProfile#setProfileInfos(java.util.Set)}</code>
     * and <code>{@link CopilotProfile#getProfileInfos()}</code> methods.</p>
     */
    @Test
    public void testProfileInfosProperty() {
        Assert.assertNull("Property profileInfos should be null.", instance.getProfileInfos());

        Set<CopilotProfileInfo> profileInfos = new HashSet<CopilotProfileInfo>();
        instance.setProfileInfos(profileInfos);

        Assert.assertEquals("Invalid profileInfos property value.", profileInfos, instance.getProfileInfos());
    }

    /**
     * <p>Tests <code>id</code> property, tests both <code>{@link CopilotProfile#setId(long)}</code> and <code>{@link
     * CopilotProfile#getId()}</code> methods.</p>
     */
    @Test
    public void testIdProperty() {
        Assert.assertEquals("Property id should be 0.", 0, instance.getId());

        long id = 10l;
        instance.setId(id);

        Assert.assertEquals("Invalid id property value.", id, instance.getId());
    }

    /**
     * <p>Tests <code>createUser</code> property, tests both <code>{@link CopilotProfile#setCreateUser(String)}</code>
     * and <code>{@link CopilotProfile#getCreateUser()}</code> methods.</p>
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
     * CopilotProfile#setCreateDate(java.util.Date)}</code>
     * and <code>{@link CopilotProfile#getCreateDate()}</code> methods.</p>
     */
    @Test
    public void testCreateDateProperty() {
        Assert.assertNull("Property createDate should be null.", instance.getCreateDate());

        Date createDate = new Date();
        instance.setCreateDate(createDate);

        Assert.assertEquals("Invalid createDate property value.", createDate, instance.getCreateDate());
    }

    /**
     * <p>Tests <code>modifyUser</code> property, tests both <code>{@link CopilotProfile#setModifyUser(String)}</code>
     * and <code>{@link CopilotProfile#getModifyUser()}</code> methods.</p>
     */
    @Test
    public void testModifyUserProperty() {
        Assert.assertNull("Property modifyUser should be null.", instance.getModifyUser());

        String modifyUser = "modifyUser";
        instance.setModifyUser(modifyUser);

        Assert.assertEquals("Invalid modifyUser property value.", modifyUser, instance.getModifyUser());
    }

    /**
     * <p>Tests <code>modifyDate</code> property, tests both <code>{@link CopilotProfile#setModifyDate(Date)}</code> and
     * <code>{@link CopilotProfile#getModifyDate()}</code> methods.</p>
     */
    @Test
    public void testModifyDateProperty() {
        Assert.assertNull("Property modifyDate should be null.", instance.getModifyDate());

        Date modifyDate = new Date();
        instance.setModifyDate(modifyDate);

        Assert.assertEquals("Invalid modifyDate property value.", modifyDate, instance.getModifyDate());
    }
}
