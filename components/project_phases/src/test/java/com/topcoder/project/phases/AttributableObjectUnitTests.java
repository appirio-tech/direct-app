/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.util.Map;

import junit.framework.TestCase;

/**
 * Unit test case for <code>AttributableObject</code>. Since <code>AttributableObject</code> is abstract, a mock
 * class is used to test.
 *
 * @author littlebull
 * @version 2.0
 */
public class AttributableObjectUnitTests extends TestCase {
    /**
     * Represents the attribute key.
     */
    private static final String KEY = "Component";

    /**
     * Represents the attribute value.
     */
    private static final String VALUE = "Project Phase 2.0";

    /**
     * An <code>AttributableObject</code> instance for testing.
     */
    private AttributableObject attribute;

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        attribute = new MockAttributableObject();
    }

    /**
     * Accuracy test of constructor.
     * <p>
     * Should create the instance successfully.
     * </p>
     */
    public void testConstructorAccuracy() {
        assertNotNull("Should create the instance successfully.", attribute);
        assertEquals("The attribute map should be empty.", 0, attribute.getAttributes().size());
    }

    /**
     * Failure test of <code>getAttribute</code>.
     * <p>
     * With null key.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testGetAttributeFailureWithNullKey() {
        try {
            attribute.getAttribute(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>getAttribute</code>.
     * <p>
     * With no entry exist.
     * </p>
     * <p>
     * Should return null.
     * </p>
     */
    public void testGetAttributeAccuracyWithNoEntryExist() {
        assertNull("Should return null.", attribute.getAttribute(KEY));
    }

    /**
     * Accuracy test of <code>getAttribute</code>.
     * <p>
     * With entry exist.
     * </p>
     * <p>
     * Should return the proper value.
     * </p>
     */
    public void testGetAttributeAccuracyWithEntryExist() {
        attribute.setAttribute(KEY, VALUE);
        assertEquals("Should return the proper value.", VALUE, attribute.getAttribute(KEY));
    }

    /**
     * Accuracy test of <code>getAttributes</code>.
     * <p>
     * With no entry exist.
     * </p>
     * <p>
     * Should return empty map.
     * </p>
     */
    public void testGetAttributesAccuracyWithNoEntryExist() {
        Map attributes = attribute.getAttributes();
        assertTrue("Should return null.", attributes.size() == 0);
    }

    /**
     * Accuracy test of <code>getAttributes</code>.
     * <p>
     * With entry exist.
     * </p>
     * <p>
     * Should return the proper map.
     * </p>
     */
    public void testGetAttributesAccuracyWithEntryExist() {
        attribute.setAttribute(KEY, VALUE);
        Map attributes = attribute.getAttributes();
        assertEquals("Should return the proper value.", VALUE, attributes.get(KEY));
    }

    /**
     * Failure test of <code>setAttribute</code>.
     * <p>
     * With null key.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetAttributeFailureWithNullKey() {
        try {
            attribute.setAttribute(null, VALUE);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of <code>setAttribute</code>.
     * <p>
     * With null value.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetAttributeFailureWithNullValue() {
        try {
            attribute.setAttribute(KEY, null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>setAttribute</code>.
     * <p>
     * With valid key and value.
     * </p>
     * <p>
     * Should set the attribute.
     * </p>
     */
    public void testSetAttributeAccuracy() {
        attribute.setAttribute(KEY, VALUE);
        assertEquals("Should set the attribute.", VALUE, attribute.getAttribute(KEY));
    }

    /**
     * Failure test of <code>removeAttribute</code>.
     * <p>
     * With null key.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testRemoveAttributeFailureWithNullKey() {
        try {
            attribute.removeAttribute(null);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>removeAttribute</code>.
     * <p>
     * With no entry exist.
     * </p>
     * <p>
     * Should return null.
     * </p>
     */
    public void testRemoveAttributeAccuracyWithNoEntryExist() {
        assertNull("Should return null.", attribute.removeAttribute(KEY));
        assertNull("Should return null.", attribute.getAttribute(KEY));
    }

    /**
     * Accuracy test of <code>removeAttribute</code>.
     * <p>
     * With entry exist.
     * </p>
     * <p>
     * Should return the proper value.
     * </p>
     */
    public void testRemoveAttributeAccuracyWithEntryExist() {
        attribute.setAttribute(KEY, VALUE);
        assertEquals("Should return the proper value.", VALUE, attribute.removeAttribute(KEY));
        assertNull("Should return null.", attribute.getAttribute(KEY));
    }

    /**
     * Accuracy test of <code>clearAttributes</code>.
     * <p>
     * With entry exist.
     * </p>
     * <p>
     * Should clear the attribute map.
     * </p>
     */
    public void testClearAttributesAccuracy() {
        attribute.setAttribute(KEY, VALUE);
        attribute.clearAttributes();

        assertEquals("Should clear the attribute map.", 0, attribute.getAttributes().size());
    }

    /**
     * Mock class extends <code>AttributableObject</code>.
     *
     * @author TCSDEVELOPER
     * @version 2.0
     */
    private class MockAttributableObject extends AttributableObject {
    }
}
