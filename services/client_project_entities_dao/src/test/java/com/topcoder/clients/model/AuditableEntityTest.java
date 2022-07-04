/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import java.io.Serializable;
import java.util.Date;

import junit.framework.TestCase;

/**
 * <p>
 * Test class: <code>AuditableEntity</code>.
 * </p>
 * <p>
 * Some change to remove ambiguous asserts.
 * </p>
 *
 * @author TCSDEVELOPER, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class AuditableEntityTest extends TestCase {
    /**
     * <p>
     * An instance of <code>AuditableEntity</code> which is tested.
     * </p>
     */
    private AuditableEntity target = null;

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        target = new MockAuditableEntity();
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>AuditableEntity</code> implements <code>Serializable</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("AuditableEntity does not implements Serializable.", target instanceof Serializable);
    }

    /**
     * <p>
     * Tests the <code>com.topcoder.clients.model.AuditableEntity()</code> for proper behavior. Verifies that the
     * instance should be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor() throws Exception {
        assertNotNull("Instance should be created.", target);
    }

    /**
     * <p>
     * Tests the <code>setModifyUsername(String)</code> for proper behavior. Verifies that the property should be
     * correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetModifyUsername_String() throws Exception {
        target.setModifyUsername("ModifyUsername");
        assertEquals("getModifyUsername", "ModifyUsername", target.getModifyUsername());
    }

    /**
     * <p>
     * Tests the <code>getId()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetId() throws Exception {
        target.setId(10L);
        assertEquals("getId", 10L, target.getId());
    }

    /**
     * <p>
     * Tests the <code>getModifyDate()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetModifyDate() throws Exception {
        Date modifyDate = new Date();
        target.setModifyDate(modifyDate);
        assertEquals("getModifyDate", modifyDate, target.getModifyDate());
    }

    /**
     * <p>
     * Tests the <code>setId(long)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetId_long() throws Exception {
        target.setId(10L);
        assertEquals("getId", 10L, target.getId());
    }

    /**
     * <p>
     * Tests the <code>setName(String)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetName_String() throws Exception {
        target.setName("name1");
        assertEquals("getName", "name1", target.getName());
    }

    /**
     * <p>
     * Tests the <code>getModifyUsername()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetModifyUsername() throws Exception {
        target.setModifyUsername("name2");
        assertEquals("getModifyUsername", "name2", target.getModifyUsername());
    }

    /**
     * <p>
     * Tests the <code>setModifyDate(Date)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetModifyDate_Date() throws Exception {
        Date modifyDate = new Date();
        target.setModifyDate(modifyDate);
        assertEquals("getModifyDate", modifyDate, target.getModifyDate());
    }

    /**
     * <p>
     * Tests the <code>setCreateUsername(String)</code> for proper behavior. Verifies that the property should be
     * correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetCreateUsername_String() throws Exception {
        target.setCreateUsername("name3");
        assertEquals("getCreateUsername", "name3", target.getCreateUsername());
    }

    /**
     * <p>
     * Tests the <code>getName()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetName() throws Exception {
        target.setName("name1");
        assertEquals("getName", "name1", target.getName());
    }

    /**
     * <p>
     * Tests the <code>setDeleted(boolean)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetDeleted_boolean() throws Exception {
        target.setDeleted(true);
        assertTrue(target.isDeleted());
    }

    /**
     * <p>
     * Tests the <code>getCreateUsername()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetCreateUsername() throws Exception {
        target.setCreateUsername("name3");
        assertEquals("getCreateUsername", "name3", target.getCreateUsername());
    }

    /**
     * <p>
     * Tests the <code>getCreateDate()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetCreateDate() throws Exception {
        Date createDate = new Date();
        target.setCreateDate(createDate);
        assertEquals("getCreateDate", createDate, target.getCreateDate());
    }

    /**
     * <p>
     * Tests the <code>setCreateDate(Date)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetCreateDate_Date() throws Exception {
        Date createDate = new Date();
        target.setCreateDate(createDate);
        assertEquals("getCreateDate", createDate, target.getCreateDate());
    }

    /**
     * <p>
     * Tests the <code>isDeleted()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodIsDeleted() throws Exception {
        target.setDeleted(true);
        assertTrue(target.isDeleted());
    }

    /**
     * Mock class extends AuditableEntity for testing.
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    @SuppressWarnings("serial")
    class MockAuditableEntity extends AuditableEntity {
        /**
         * Default ctor.
         */
        public MockAuditableEntity() {
            super();
        }
    }
}
