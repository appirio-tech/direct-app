/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 * Unit tests for the {@link TcDirectProject}.
 * </p>
 *
 * @author  CaDenza
 * @version 1.0
 */
public class TcDirectProjectTest {
    /** Represents the createDate used to test again. */
    private final Date createDateValue = new Date();

    /** Represents the description used to test again. */
    private final String descriptionValue = "description";

    /** Represents the modifyDate used to test again. */
    private final Date modifyDateValue = new Date();

    /** Represents the name used to test again. */
    private final String nameValue = "name";

    /** Represents the projectId used to test again. */
    private final long projectIdValue = 1;

    /** Represents the projectStatusId used to test again. */
    private final int projectStatusIdValue = 1;

    /** Represents the userId used to test again. */
    private final int userIdValue = 1;

    /** Represents the instance used to test again. */
    private TcDirectProject testInstance;

    /**
     * Sets up test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testInstance = new TcDirectProject();
    }

    /**
     * Tears down test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        testInstance = null;
    }

    /**
     * <p>
     * Accuracy test for {@link TcDirectProject#TcDirectProject()}.
     * </p>
     * <p>
     * Instance should be correctly created.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void testTcDirectProject() throws Exception {
        new TcDirectProject();

        // Good
    }

    /**
     * <p>
     * Accuracy test for {@link TcDirectProject#getCreateDate()}
     * </p>
     * <p>
     * The value of <code>createDate</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getCreateDate() throws Exception {
        assertNull("Initial value of createDate must be null.", testInstance.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test {@link TcDirectProject#setCreateDate(Date)}.
     * </p>
     * <p>
     * The value of <code>createDate</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setCreateDate() throws Exception {
        testInstance.setCreateDate(createDateValue);
        assertEquals("Property createDate isn't set properly.", createDateValue, testInstance.getCreateDate());
    }

    /**
     * <p>
     * Accuracy test for {@link TcDirectProject#getDescription()}
     * </p>
     * <p>
     * The value of <code>description</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getDescription() throws Exception {
        assertNull("Initial value of description must be null.", testInstance.getDescription());
    }

    /**
     * <p>
     * Accuracy test {@link TcDirectProject#setDescription(String)}.
     * </p>
     * <p>
     * The value of <code>description</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setDescription() throws Exception {
        testInstance.setDescription(descriptionValue);
        assertEquals("Property description isn't set properly.", descriptionValue, testInstance.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link TcDirectProject#getModifyDate()}
     * </p>
     * <p>
     * The value of <code>modifyDate</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getModifyDate() throws Exception {
        assertNull("Initial value of modifyDate must be null.", testInstance.getModifyDate());
    }

    /**
     * <p>
     * Accuracy test {@link TcDirectProject#setModifyDate(Date)}.
     * </p>
     * <p>
     * The value of <code>modifyDate</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setModifyDate() throws Exception {
        testInstance.setModifyDate(modifyDateValue);
        assertEquals("Property modifyDate isn't set properly.", modifyDateValue, testInstance.getModifyDate());
    }

    /**
     * <p>
     * Accuracy test for {@link TcDirectProject#getName()}
     * </p>
     * <p>
     * The value of <code>name</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getName() throws Exception {
        assertNull("Initial value of name must be null.", testInstance.getName());
    }

    /**
     * <p>
     * Accuracy test {@link TcDirectProject#setName(String)}.
     * </p>
     * <p>
     * The value of <code>name</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setName() throws Exception {
        testInstance.setName(nameValue);
        assertEquals("Property name isn't set properly.", nameValue, testInstance.getName());
    }

    /**
     * <p>
     * Accuracy test for {@link TcDirectProject#getProjectId()}
     * </p>
     * <p>
     * The value of <code>projectId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getProjectId() throws Exception {
        assertEquals("Initial value of projectId must be 0.", 0, testInstance.getProjectId());
    }

    /**
     * <p>
     * Accuracy test {@link TcDirectProject#setProjectId(long)}.
     * </p>
     * <p>
     * The value of <code>projectId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectId() throws Exception {
        testInstance.setProjectId(projectIdValue);
        assertEquals("Property projectId isn't set properly.", projectIdValue, testInstance.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for {@link TcDirectProject#getProjectStatusId()}
     * </p>
     * <p>
     * The value of <code>projectStatusId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getProjectStatusId() throws Exception {
        assertEquals("Initial value of projectStatusId must be 0.", 0, testInstance.getProjectStatusId());
    }

    /**
     * <p>
     * Accuracy test {@link TcDirectProject#setProjectStatusId(int)}.
     * </p>
     * <p>
     * The value of <code>projectStatusId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setProjectStatusId() throws Exception {
        testInstance.setProjectStatusId(projectStatusIdValue);
        assertEquals("Property projectStatusId isn't set properly.",
                projectStatusIdValue, testInstance.getProjectStatusId());
    }

    /**
     * <p>
     * Accuracy test for {@link TcDirectProject#getUserId()}
     * </p>
     * <p>
     * The value of <code>userId</code> should be properly retrieved.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getUserId() throws Exception {
        assertEquals("Initial value of userId must be 0.", 0, testInstance.getUserId());
    }

    /**
     * <p>
     * Accuracy test {@link TcDirectProject#setUserId(int)}.
     * </p>
     * <p>
     * The value of <code>userId</code> should be properly set.
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_setUserId() throws Exception {
        testInstance.setUserId(userIdValue);
        assertEquals("Property userId isn't set properly.", userIdValue, testInstance.getUserId());
    }

    /**
     * <p>
     * Accuracy test for {@link TcDirectProject#toJSONString()}.
     * </p>
     * <p>
     *
     * </p>
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_toJSONString() throws Exception {
        assertNotNull("Fail construct JSON", testInstance.toJSONString());
    }
}