/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.accuracytests;

import junit.framework.TestCase;

import java.util.Map;

import com.topcoder.project.phases.AttributableObject;

/**
 * Accuracy test for <code>AttributableObject</code> class.
 * <p>
 * Revision in 2.0: Update the assertion message.
 * </p>
 *
 * @author XuChuan, mayi
 * @version 2.0
 * @since 1.0
 */
public class AttributableObjectAccuracyTest extends TestCase {

    /**
     * The AttributableObject instance used for accuracy test.
     */
    private AttributableObject obj = null;

    /**
     * Setup the environment.
     */
    protected void setUp() {
        this.obj = new AttributableObject() {
        };
    }

    /**
     * Test the accuracy of AttributableObject#AttributableObject(), member variable "attributes" should be
     * initialized.
     */
    public void testConstructor() {
        //the constructor is already invoked in setUp method
        assertEquals("Failed to initialize attributes.", 0, this.obj.getAttributes().size());
    }

    /**
     * Test AttributableObject#getAttribute(Object key) with existing key.
     * <p>Corresponding value should be returned.</p>
     */
    public void testGetAttribute1() {
        this.obj.setAttribute("testKey", "testValue");
        assertEquals("Failed to get existing attribute.",
                "testValue", this.obj.getAttribute("testKey"));
    }

    /**
     * Test AttributableObject#getAttribute(Object key) with non-existing key.
     * <p>Null should be returned.</p>
     */
    public void testGetAttribute2() {
        assertNull("Failed to get non-existing attribute.", this.obj.getAttribute("testKey"));
    }

    /**
     * Test AttributableObject#getAttributes().
     * <p>This case is complicated, adn exact attributes should be returned.</p>
     */
    public void testGetAttributes1() {
        //insert 100 attributes
        for (int i = 0; i < 100; i++) {
            this.obj.setAttribute("key" + i, "value" + i);
        }

        Map attr = this.obj.getAttributes();

        //compare the size
        assertEquals("test1", 100, attr.size());

        //compare the attributes
        for (int i = 0; i < 100; i++) {
            assertEquals("Cannot get attribute: " + (i + 2), "value" + i, attr.get("key" + i));
        }
    }

    /**
     * Test AttributableObject#getAttributes().
     * <p>An unmodifiable copy should be returned.</p>
     */
    public void testGetAttributes2() {
        Map attr = this.obj.getAttributes();

        try {
            //insert attributes into attr
            attr.put("testKey", "testValue");

            //if the returned map is modifiable, inner map should be checked
            assertEquals("Should return unmodifiable copy.", 0, this.obj.getAttributes().size());
        } catch (UnsupportedOperationException e) {
            // pass
        }
    }

    /**
     * Test AttributableObject#setAttribute(Object key, Object value) with non-existing key.
     * <p>A new pair should be added.</p>
     */
    public void testSetAttribute1() {
        this.obj.setAttribute("testKey", "testValue");
        assertEquals("Failed to set non-existing attribute.",
                "testValue", this.obj.getAttribute("testKey"));
    }

    /**
     * Test AttributableObject#setAttribute(Object key, Object value) with existing key.
     * <p>Old value should be overwritten.</p>
     */
    public void testSetAttribute2() {
        this.obj.setAttribute("testKey", "oldValue");
        this.obj.setAttribute("testKey", "newValue");
        assertEquals("Failed to set existing attribute.",
                "newValue", this.obj.getAttribute("testKey"));
    }

    /**
     * Test AttributableObject#removeAttribute(Object key) with existing key.
     * <p>The value should be returned.</p>
     */
    public void testRemoveAttribute1() {
        this.obj.setAttribute("testKey", "testValue");
        assertEquals("test1", "testValue", this.obj.removeAttribute("testKey"));
    }

    /**
     * Test AttributableObject#removeAttribute(Object key) with non-existing key.
     * <p>Null should be returned.</p>
     */
    public void testRemoveAttribute2() {
        assertNull("Failed to remove non-existing attribute.",
                this.obj.removeAttribute("testKey"));
    }

    /**
     * Test AttributableObject#clearAttributes().
     * <p>All attributes should be removed.</p>
     */
    public void testClearAttributes() {
        //insert 100 attributes
        for (int i = 0; i < 100; i++) {
            this.obj.setAttribute("key" + i, "value" + i);
        }

        this.obj.clearAttributes();

        //check the size
        assertEquals("Cannot clear attributes.", 0, this.obj.getAttributes().size());
    }
}
