/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.topcoder.search.builder.SearchBundle;
import com.topcoder.util.log.LogFactory;

import junit.framework.TestCase;

/**
 * Unit test for <code>{@link DeliverableHelper}</code> class.
 *
 * @author TCSDEVELOPER
 * @version 1.2
 * @since 1.2
 */
public class DeliverableHelperTests extends TestCase {

    /**
     * Represents the searchable fields.
     */
    @SuppressWarnings("unchecked")
    private Map searchableFields;

    /**
     * <p>
     * Tests <code>checkObjectNotNull(Object, String, Log)</code> method.
     * </p>
     * <p>
     * If the object is null, should throw IllegalArgumentException
     * </p>
     */
    public void testCheckObjectNotNull_null() {
        try {
            DeliverableHelper.checkObjectNotNull(null, "null", null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>checkObjectNotNull(Object, String, Log)</code> method.
     * </p>
     * <p>
     * If the object is not null, should not throw IllegalArgumentException
     * </p>
     */
    public void testCheckObjectNotNull_notNull() {
        DeliverableHelper.checkObjectNotNull(new Object(), "obj", null);
    }

    /**
     * <p>
     * Tests <code>checkObjectNotNullFullDesp(Object, String, Log)</code> method.
     * </p>
     * <p>
     * If the object is null, should throw IllegalArgumentException
     * </p>
     */
    public void testCheckObjectNotNullFullDesp_null() {
        try {
            DeliverableHelper.checkObjectNotNullFullDesp(null, "null", null);

            fail("expect IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>checkObjectNotNullFullDesp(Object, String, Log)</code> method.
     * </p>
     * <p>
     * If the object is not null, should not throw IllegalArgumentException
     * </p>
     */
    public void testCheckObjectNotNullFullDesp_notNull() {
        DeliverableHelper.checkObjectNotNullFullDesp(new Object(), "obj", null);
    }

    /**
     * <p>
     * Tests <code>checkGreaterThanZero(long, String, Log)</code> method.
     * </p>
     * <p>
     * If the number is negative, should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckGreaterThanZero_negative() {
        try {
            DeliverableHelper.checkGreaterThanZero(-1, "negative", null);

            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>checkGreaterThanZero(long, String, Log)</code> method.
     * </p>
     * <p>
     * If the number is zero, should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckGreaterThanZero_zero() {
        try {
            DeliverableHelper.checkGreaterThanZero(0, "zero", null);

            fail("expect throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * <p>
     * Tests <code>checkGreaterThanZero(long, String, Log)</code> method.
     * </p>
     * <p>
     * If the number is positive, should not throw IllegalArgumentException.
     * </p>
     */
    public void testCheckGreaterThanZero_positive() {
        DeliverableHelper.checkGreaterThanZero(1, "positive", null);

    }

    /**
     * <p>
     * Tests <code>logException(Log, T)</code> method.
     * </p>
     * <p>
     * If the logger is null, no logging is done.
     * </p>
     */
    public void testLogException_null() {
        DeliverableHelper.logException(null, new IOException());

        // no logging is done
    }

    /**
     * <p>
     * Tests <code>logException(Log, T)</code> method.
     * </p>
     * <p>
     * If the logger is not null, logging should be done.
     * </p>
     */
    public void testLogException_notNull() {
        DeliverableHelper.logException(LogFactory.getLog("Unit Test"), new IOException());

        // logging should be done
    }

    /**
     * <p>
     * Tests <code>setSearchableFields(SearchBundle, int)</code> method.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void testSetSearchableFields_accuracy1() {
        searchableFields = null;
        SearchBundle searchBundle = new SearchBundle("name", new HashMap(), "context") {
            @Override
            public synchronized void setSearchableFields(Map fields) {
                searchableFields = fields;
            }
        };

        DeliverableHelper.setSearchableFields(searchBundle, DeliverableHelper.SUBMISSION_SEARCH_BUNDLE);

        // verify the fields
        assertNotNull("the searchableFields is not set.", searchableFields);
        assertEquals("The size of searchableFields is incorrect.", 6, searchableFields.size());
        assertTrue("invalid entry", searchableFields.containsKey("upload_id"));
        assertTrue("invalid entry", searchableFields.containsKey("submission_id"));
        assertTrue("invalid entry", searchableFields.containsKey("submission_status_id"));
        assertTrue("invalid entry", searchableFields.containsKey("submission_type_id"));
        assertTrue("invalid entry", searchableFields.containsKey("project_id"));
        assertTrue("invalid entry", searchableFields.containsKey("resource_id"));
    }

    /**
     * <p>
     * Tests <code>setSearchableFields(SearchBundle, int)</code> method.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void testSetSearchableFields_accuracy2() {
        searchableFields = null;
        SearchBundle searchBundle = new SearchBundle("name", new HashMap(), "context") {
            @Override
            public synchronized void setSearchableFields(Map fields) {
                searchableFields = fields;
            }
        };

        DeliverableHelper.setSearchableFields(searchBundle, DeliverableHelper.UPLOAD_SEARCH_BUNDLE);

        // verify the fields
        assertNotNull("the searchableFields is not set.", searchableFields);
        assertEquals("The size of searchableFields is incorrect.", 5, searchableFields.size());
        assertTrue("invalid entry", searchableFields.containsKey("upload_id"));
        assertTrue("invalid entry", searchableFields.containsKey("upload_type_id"));
        assertTrue("invalid entry", searchableFields.containsKey("upload_status_id"));
        assertTrue("invalid entry", searchableFields.containsKey("project_id"));
        assertTrue("invalid entry", searchableFields.containsKey("resource_id"));
    }

    /**
     * <p>
     * Tests <code>setSearchableFields(SearchBundle, int)</code> method.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void testSetSearchableFields_accuracy3() {
        searchableFields = null;
        SearchBundle searchBundle = new SearchBundle("name", new HashMap(), "context") {
            @Override
            public synchronized void setSearchableFields(Map fields) {
                searchableFields = fields;
            }
        };

        DeliverableHelper.setSearchableFields(searchBundle, DeliverableHelper.DELIVERABLE_SEARCH_BUNDLE);

        // verify the fields
        assertNotNull("the searchableFields is not set.", searchableFields);
        assertEquals("The size of searchableFields is incorrect.", 6, searchableFields.size());
        assertTrue("invalid entry", searchableFields.containsKey("deliverable_id"));
        assertTrue("invalid entry", searchableFields.containsKey("phase_id"));
        assertTrue("invalid entry", searchableFields.containsKey("name"));
        assertTrue("invalid entry", searchableFields.containsKey("required"));
        assertTrue("invalid entry", searchableFields.containsKey("project_id"));
        assertTrue("invalid entry", searchableFields.containsKey("resource_id"));
    }

    /**
     * <p>
     * Tests <code>setSearchableFields(SearchBundle, int)</code> method.
     * </p>
     */
    @SuppressWarnings("unchecked")
    public void testSetSearchableFields_accuracy4() {
        searchableFields = null;
        SearchBundle searchBundle = new SearchBundle("name", new HashMap(), "context") {
            @Override
            public synchronized void setSearchableFields(Map fields) {
                searchableFields = fields;
            }
        };

        DeliverableHelper.setSearchableFields(searchBundle,
                DeliverableHelper.DELIVERABLE_WITH_SUBMISSIONS_SEARCH_BUNDLE);

        // verify the fields
        assertNotNull("the searchableFields is not set.", searchableFields);
        assertEquals("The size of searchableFields is incorrect.", 7, searchableFields.size());
        assertTrue("invalid entry", searchableFields.containsKey("submission_id"));
        assertTrue("invalid entry", searchableFields.containsKey("deliverable_id"));
        assertTrue("invalid entry", searchableFields.containsKey("phase_id"));
        assertTrue("invalid entry", searchableFields.containsKey("name"));
        assertTrue("invalid entry", searchableFields.containsKey("required"));
        assertTrue("invalid entry", searchableFields.containsKey("project_id"));
        assertTrue("invalid entry", searchableFields.containsKey("resource_id"));
    }
}
