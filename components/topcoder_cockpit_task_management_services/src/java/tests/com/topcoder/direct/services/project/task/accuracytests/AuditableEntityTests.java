/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.accuracytests;

import com.topcoder.direct.services.project.task.model.AuditableEntity;
import com.topcoder.direct.services.project.task.model.IdentifiableEntity;

import junit.framework.TestCase;

import java.util.Date;


/**
 * <p>Unit tests for <code>{@link AuditableEntity}</code> class.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class AuditableEntityTests extends TestCase {
    /** AuditableEntity instance to be used for the testing. */
    private MockAuditableEntity instance = null;

    /**
     * <p>Sets up the environment for the TestCase.</p>
     */
    public void setUp() {
        instance = new MockAuditableEntity();
    }

    /**
     * <p>Tears down the environment after the TestCase.</p>
     */
    public void tearDown() {
        instance = null;
    }

    /**
     * <p>Accuracy test for the constructor.</p>
     */
    public void test_ctor() {
        assertNotNull("instance should be created.", instance);
    }

    /**
     * <p>Accuracy test for the inheritence.</p>
     */
    public void test_inheritence() {
        assertTrue("invalid inheritence.", AuditableEntity.class.getSuperclass() == IdentifiableEntity.class);
    }

    /**
     * <p>Accuracy test for {@link AuditableEntity#getCreatedDate()}.</p>
     */
    public void test_getCreatedDate() {
        assertEquals("Invalid default value.", null, instance.getCreatedDate());

        Date date = new Date();
        instance.setCreatedDate(date);
        assertEquals("Invalid return value.", date, instance.getCreatedDate());
    }

    /**
     * <p>Accuracy test for {@link AuditableEntity#setCreatedDate()}.</p>
     */
    public void test_setCreatedDate() {
        Date date = new Date();
        instance.setCreatedDate(date);
        assertEquals("Invalid return value.", date, instance.getCreatedDate());
        instance.setCreatedDate(null);
        assertEquals("Invalid value is set.", null, instance.getCreatedDate());
    }

    /**
     * <p>Accuracy test for {@link AuditableEntity#getCreatedBy()}.</p>
     */
    public void test_getCreatedBy() {
        assertEquals("Invalid default value.", null, instance.getCreatedBy());
        instance.setCreatedBy("tester");
        assertEquals("Invalid return value.", "tester", instance.getCreatedBy());
    }

    /**
     * <p>Accuracy test for {@link AuditableEntity#setCreatedBy()}.</p>
     */
    public void test_setCreatedBy() {
        instance.setCreatedBy("tester");
        assertEquals("Invalid value is set.", "tester", instance.getCreatedBy());
        instance.setCreatedBy(null);
        assertEquals("Invalid value is set.", null, instance.getCreatedBy());
    }

    /**
     * <p>Accuracy test for {@link AuditableEntity#getLastModifiedDate()}.</p>
     */
    public void test_getLastModifiedDate() {
        assertEquals("Invalid default value.", null, instance.getLastModifiedDate());

        Date date = new Date();
        instance.setLastModifiedDate(date);
        assertEquals("Invalid return value.", date, instance.getLastModifiedDate());
    }

    /**
     * <p>Accuracy test for {@link AuditableEntity#setLastModifiedDate()}.</p>
     */
    public void test_setLastModifiedDate() {
        Date date = new Date();
        instance.setLastModifiedDate(date);
        assertEquals("Invalid return value.", date, instance.getLastModifiedDate());
        instance.setLastModifiedDate(null);
        assertEquals("Invalid value is set.", null, instance.getLastModifiedDate());
    }

    /**
     * <p>Accuracy test for {@link AuditableEntity#getLastModifiedBy()}.</p>
     */
    public void test_getLastModifiedBy() {
        assertEquals("Invalid default value.", null, instance.getLastModifiedBy());
        instance.setLastModifiedBy("tester");
        assertEquals("Invalid return value.", "tester", instance.getLastModifiedBy());
    }

    /**
     * <p>Accuracy test for {@link AuditableEntity#setLastModifiedBy()}.</p>
     */
    public void test_setLastModifiedBy() {
        instance.setLastModifiedBy("tester");
        assertEquals("Invalid value is set.", "tester", instance.getLastModifiedBy());
        instance.setLastModifiedBy(null);
        assertEquals("Invalid value is set.", null, instance.getLastModifiedBy());
    }
}
