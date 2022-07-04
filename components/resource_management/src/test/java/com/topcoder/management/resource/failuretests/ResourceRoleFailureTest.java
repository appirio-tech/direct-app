/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.failuretests;

import com.topcoder.management.resource.IdAlreadySetException;
import com.topcoder.management.resource.ResourceRole;

import junit.framework.TestCase;

/**
 * Failure test for {@link ResourceRole} class.
 *
 * @author mayi
 * @version 1.1
 * @since 1.0
 */
public class ResourceRoleFailureTest extends TestCase {
    /**
     * A <code>resourceRole</code> to test against.
     */
    private ResourceRole resourceRole = null;

    /**
     * Set up.
     * <p>Initialize the <code>resourceRole</code> instance to test.</p>
     */
    protected void setUp() {
        resourceRole = new ResourceRole();
    }

    /**
     * Test <code>ResourceRole(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_Long_ZeroId() {
        try {
            new ResourceRole(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>ResourceRole(long)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_Long_NegativeId() {
        try {
            new ResourceRole(-1);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>ResourceRole(long, String, long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongStringLong_ZeroId() {
        try {
            new ResourceRole(0, "name", 1L);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>ResourceRole(long, String, long)</code> with negative id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongStringLong_NegativeId() {
        try {
            new ResourceRole(-1, "name", 1L);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>ResourceRole(long, String, long)</code> with null name.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongStringLong_NullName() {
        try {
            new ResourceRole(1L, null, 1L);
            fail("Should throw IllegalArgumentException for null name.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>ResourceRole(long, String, long)</code> with zero phaseType.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongStringLong_ZeroPhaseType() {
        try {
            new ResourceRole(1L, "name", 0L);
            fail("Should throw IllegalArgumentException for zero phaseType.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>ResourceRole(long, String, long)</code> with negative phaseType.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testConstructor_LongStringLong_NegativePhaseType() {
        try {
            new ResourceRole(1, "name", -1L);
            fail("Should throw IllegalArgumentException for negative phaseType.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setId(long)</code> with zero id.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetId_ZeroId() {
        try {
            resourceRole.setId(0);
            fail("Should throw IllegalArgumentException for zero id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setId(long)</code> with negative id after id is set.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetId_NegativeId() {
        resourceRole.setId(1);

        try {
            resourceRole.setId(-1);
            fail("Should throw IllegalArgumentException for negative id.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setId(long)</code> after id is set.
     * <p>IdAlreadySetException should be thrown.</p>
     */
    public void testSetId_AlreadySet() {
        resourceRole.setId(1);

        try {
            resourceRole.setId(1);
            fail("Should throw IdAlreadySetException for negative id.");
        } catch (IdAlreadySetException e) {
            // pass
        }
    }

    /**
     * Test <code>setPhaseType(long)</code> with zero phaseType.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetId_ZeroPhaseType() {
        try {
            resourceRole.setPhaseType(new Long(0));
            fail("Should throw IllegalArgumentException for zero phaseType.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Test <code>setPhaseType(long)</code> with negative phaseType.
     * <p>IllegalArgumentException should be thrown.</p>
     */
    public void testSetId_NegativePhaseType() {
        resourceRole.setPhaseType(new Long(1));

        try {
            resourceRole.setPhaseType(new Long(-1));
            fail("Should throw IllegalArgumentException for negative phaseType.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }
}
