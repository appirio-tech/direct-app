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
 * <p>Tests <code>{@link CopilotProjectInfo}</code> class.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectInfoTests {

    /**
     * <p>Represents instance of tested class.</p>
     */
    private CopilotProjectInfo instance;

    /**
     * <p>Returns test suite for this class.</p>
     *
     * @return test suite for this class
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(CopilotProjectInfoTests.class);
    }

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception if any error occurs
     */
    @Before
    public void setUp() throws Exception {

        instance = new CopilotProjectInfo();
    }

    /**
     * <p>Tests if <code>{@link CopilotProjectInfo}</code> class implements {@link java.io.Serializable} interface.</p>
     */
    @Test
    public void testSerializable() {
        Assert.assertTrue("Class does not implements Serializable interface.",
                instance instanceof Serializable);
    }

    /**
     * <p>Tests if <code>{@link CopilotProjectInfo}</code> class extends {@link IdentifiableEntity} class.</p>
     */
    @Test
    public void testInheritance() {
        Assert.assertTrue("Class does not extends IdentifiableEntity class.",
                instance instanceof IdentifiableEntity);
    }

    /**
     * <p>Tests <code>{@link CopilotProjectInfo#CopilotProjectInfo()}</code> constructor.</p>
     */
    @Test
    public void testCtor() {

        Assert.assertNotNull("Object not instantiated.", instance);
    }

    /**
     * <p>Tests <code>infoType</code> property, tests both <code>{@link
     * CopilotProjectInfo#setInfoType(CopilotProjectInfoType)}</code>
     * and <code>{@link CopilotProjectInfo#getInfoType()}</code> methods.</p>
     */
    @Test
    public void testInfoTypeProperty() {
        Assert.assertNull("Property infoType should be null.", instance.getInfoType());

        CopilotProjectInfoType infoType = new CopilotProjectInfoType();
        instance.setInfoType(infoType);

        Assert.assertEquals("Invalid infoType property infoType.", infoType, instance.getInfoType());
    }

    /**
     * <p>Tests <code>copilotProjectId</code> property, tests both <code>{@link
     * CopilotProjectInfo#setCopilotProjectId(long)}</code>
     * and <code>{@link CopilotProjectInfo#getCopilotProjectId()}</code> methods.</p>
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
     * <p>Tests <code>value</code> property, tests both <code>{@link
     * CopilotProjectInfo#setValue(String)}</code> and
     * <code>{@link CopilotProjectInfo#getValue()}</code> methods.</p>
     */
    @Test
    public void testValueProperty() {
        Assert.assertNull("Property value should be null.", instance.getValue());

        String value = "value";
        instance.setValue(value);

        Assert.assertEquals("Invalid value property value.", value, instance.getValue());
    }

    /**
     * <p>Tests <code>id</code> property, tests both <code>{@link CopilotProjectInfo#setId(long)}</code> and
     * <code>{@link CopilotProjectInfo#getId()}</code> methods.</p>
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
     * CopilotProjectInfo#setCreateUser(String)}</code>
     * and <code>{@link CopilotProjectInfo#getCreateUser()}</code> methods.</p>
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
     * CopilotProjectInfo#setCreateDate(java.util.Date)}</code>
     * and <code>{@link CopilotProjectInfo#getCreateDate()}</code> methods.</p>
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
     * CopilotProjectInfo#setModifyUser(String)}</code>
     * and <code>{@link CopilotProjectInfo#getModifyUser()}</code> methods.</p>
     */
    @Test
    public void testModifyUserProperty() {
        Assert.assertNull("Property modifyUser should be null.", instance.getModifyUser());

        String modifyUser = "modifyUser";
        instance.setModifyUser(modifyUser);

        Assert.assertEquals("Invalid modifyUser property value.", modifyUser, instance.getModifyUser());
    }

    /**
     * <p>Tests <code>modifyDate</code> property, tests both <code>{@link CopilotProjectInfo#setModifyDate(Date)}</code>
     * and <code>{@link CopilotProjectInfo#getModifyDate()}</code> methods.</p>
     */
    @Test
    public void testModifyDateProperty() {
        Assert.assertNull("Property modifyDate should be null.", instance.getModifyDate());

        Date modifyDate = new Date();
        instance.setModifyDate(modifyDate);

        Assert.assertEquals("Invalid modifyDate property value.", modifyDate, instance.getModifyDate());
    }
}
