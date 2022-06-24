/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import java.io.Serializable;
import java.util.Date;

import com.topcoder.direct.services.copilot.model.IdentifiableEntity;

import junit.framework.TestCase;

/**
 * Accuracy tests for <code>IdentifiableEntity</code>.
 * 
 * @author morehappiness
 * @version 1.0
 */
public class IdentifiableEntityAccuracyTests extends TestCase {
    /**
     * Instance used for tests.
     */
    private IdentifiableEntity instance;

    /**
     * Sets up the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        instance = new IdentifiableEntity() {
        };
    }

    /**
     * Clears down the environment.
     * 
     * @throws Exception
     *             to JUnit
     */
    protected void tearDown() throws Exception {
    }

    /**
     * Tests for the constructor.
     */
    public void test_ctor() {
        assertNotNull("Should not be null", instance);
        assertTrue("Should be true", Serializable.class.isInstance(instance));
    }

    /**
     * Tests for the id field.
     */
    public void test_id() {
        long id = 1;
        instance.setId(id);
        assertEquals("Should be the same", id, instance.getId());
    }

    /**
     * Tests for the createUser field.
     */
    public void test_createUser() {
        String createUser = "createUser";
        instance.setCreateUser(createUser);
        assertEquals("Should be the same", createUser, instance.getCreateUser());
    }

    /**
     * Tests for the createDate field.
     */
    public void test_createDate() {
        Date createDate = new Date();
        instance.setCreateDate(createDate);
        assertEquals("Should be the same", createDate, instance.getCreateDate());
    }

    /**
     * Tests for the modifyUser field.
     */
    public void test_modifyUser() {
        String modifyUser = "modifyUser";
        instance.setCreateUser(modifyUser);
        assertEquals("Should be the same", modifyUser, instance.getCreateUser());
    }

    /**
     * Tests for the modifyDate field.
     */
    public void test_modifyDate() {
        Date modifyDate = new Date();
        instance.setCreateDate(modifyDate);
        assertEquals("Should be the same", modifyDate, instance.getCreateDate());
    }

}
