/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.review.data.accuracytests;

import com.topcoder.management.review.data.CommentType;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.Serializable;


/**
 * <p>
 * This accuracy tests addresses the functionality provided by the <code>CommentType</code> class.
 * It tests the CommentType(), CommentType(long), CommentType(long, String) constructors; getId(),
 * getName(), resetId(), resetName() setId(long) and setName(String) methods.
 * </p>
 *
 * @author icyriver
 * @version 1.0
 */
public class CommentTypeAccuracyTest extends TestCase {
    /**
     * <p>
     * The id of <code>CommentType</code> instance for test.
     * </p>
     */
    private long id = 1;

    /**
     * <p>
     * The name of <code>CommentType</code> instance for test.
     * </p>
     */
    private String name = "CommentType";

    /**
     * <p>
     * The instance of <code>CommentType</code> for test.
     * </p>
     */
    private CommentType type = null;

    /**
     * <p>
     * Test suite of <code>CommentTypeAccuracyTest</code>.
     * </p>
     *
     * @return Test suite of <code>CommentTypeAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(CommentTypeAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>CommentType()</code> constructor.
     * </p>
     */
    public void testCommentTypeCtor1() {
        type = new CommentType();

        // check null here.
        assertNotNull("Create CommentType failed.", type);

        // check the type here.
        assertTrue("CommentType should extend Serializable.", type instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: -1.", -1, type.getId());
        assertNull("The name should be: null.", type.getName());
    }

    /**
     * <p>
     * Accuracy test of <code>CommentType(long)</code> constructor.
     * </p>
     */
    public void testCommentTypeCtor2() {
        type = new CommentType(id);

        // check null here.
        assertNotNull("Create CommentType failed.", type);

        // check the type here.
        assertTrue("CommentType should extend Serializable.", type instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, type.getId());
        assertNull("The name should be: null.", type.getName());
    }

    /**
     * <p>
     * Accuracy test of <code>CommentType(long, String)</code> constructor.
     * </p>
     */
    public void testCommentTypeCtor3() {
        type = new CommentType(id, name);

        // check null here.
        assertNotNull("Create CommentType failed.", type);

        // check the type here.
        assertTrue("CommentType should extend Serializable.", type instanceof Serializable);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, type.getId());
        assertEquals("The name should be: " + name + ".", name, type.getName());
    }

    /**
     * <p>
     * Accuracy test of the <code>getId()</code> method.
     * </p>
     */
    public void testMethod_getId() {
        type = new CommentType(id, name);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, type.getId());

        // reset the id and then check it.
        type.resetId();
        assertEquals("The id should be: -1.", -1, type.getId());

        // set the id again and then check it.
        type.setId(100);
        assertEquals("The id should be: 100.", 100, type.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>setId()</code> method.
     * </p>
     */
    public void testMethod_setId() {
        type = new CommentType();

        // set the id and then check it.
        type.setId(10);
        assertEquals("The id should be: 10.", 10, type.getId());

        // set the id again and then check it.
        type.setId(100);
        assertEquals("The id should be: 100.", 100, type.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetId()</code> method.
     * </p>
     */
    public void testMethod_resetId() {
        type = new CommentType(id, name);

        // reset the id and then check it.
        type.resetId();
        assertEquals("The id should be: -1.", -1, type.getId());

        // set the id again and then check it.
        type.setId(100);
        type.resetId();
        assertEquals("The id should be: -1.", -1, type.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>getName()</code> method.
     * </p>
     */
    public void testMethod_getName() {
        type = new CommentType(id, name);
        assertEquals("The name should be: " + name + ".", name, type.getName());

        // set the name and then check it.
        type.setName("newName");
        assertEquals("The name should be: newName.", "newName", type.getName());

        // set the name to null and then check it.
        type.setName(null);
        assertNull("The name should be: null.", type.getName());

        // reset the name and then check it.
        type.resetName();
        assertNull("The name should be: null.", type.getName());
    }

    /**
     * <p>
     * Accuracy test of the <code>setName()</code> method.
     * </p>
     */
    public void testMethod_setName() {
        type = new CommentType(id, name);

        // set the name and then check it.
        type.setName("newName");
        assertEquals("The name should be: newName.", "newName", type.getName());

        // set the name to null and then check it.
        type.setName(null);
        assertNull("The name should be: null.", type.getName());
    }

    /**
     * <p>
     * Accuracy test of the <code>resetName()</code> method.
     * </p>
     */
    public void testMethod_resetName() {
        type = new CommentType(id, name);

        // reset the name and then check it.
        type.resetName();
        assertNull("The name should be: null.", type.getName());

        // set the name again and then check it.
        type.setName(name);
        type.resetName();
        assertNull("The name should be: null.", type.getName());
    }
}
