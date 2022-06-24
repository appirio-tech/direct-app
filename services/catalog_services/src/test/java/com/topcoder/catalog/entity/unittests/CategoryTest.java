/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Status;
import junit.framework.TestCase;

/**
 * <p>Unit test case for {@link Category}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CategoryTest extends TestCase {
    /**
     * <p>Represents Category instance for testing.</p>
     */
    private Category category;

    /**
     * <p>Creates a new instance of Category.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        category = new Category();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        category = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>Category()</code> constructor.</p>
     */
    public void testCategory() {
        assertNotNull("Unable to instantiate Category", category);
    }

    /**
     * <p>Tests <code>getId()</code> and
     * <code>getId()</code> methods for accuracy.</p>
     */
    public void testGetSetId() {
        assertNull("Incorrect default id value", category.getId());
        Long id = 1L;
        // set a id
        category.setId(id);
        assertEquals("Incorrect id value after setting a new one",
            id, category.getId());
    }

    /**
     * <p>Tests <code>setId(null)</code>.</p>
     */
    public void testSetIdAllowsNull() {
        // set null
        try {
            category.setId(null);
            assertNull("Field 'id' should contain 'null' value",
                category.getId());
        } catch (IllegalArgumentException e) {
            fail("Field 'id' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getCatalogName()</code> and
     * <code>setCatalogName()</code> methods for accuracy.</p>
     */
    public void testGetSetCatalogName() {
        assertNull("Incorrect default catalogName value", category.getCatalogName());
        String value = "1";
        // set a catalogName
        category.setCatalogName(value);
        assertEquals("Incorrect catalogName value after setting a new one",
            value, category.getCatalogName());
    }

    /**
     * <p>Tests <code>setCatalogName(null)</code>.</p>
     */
    public void testCatalogNameAllowsNull() {
        // set a catalogName
        // set null
        try {
            category.setCatalogName(null);
            assertNull("Field 'catalogName' should contain 'null' value",
                category.getCatalogName());
        } catch (IllegalArgumentException e) {
            fail("Field 'catalogName' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getName()</code> and
     * <code>setName()</code> methods for accuracy.</p>
     */
    public void testGetSetName() {
        assertNull("Incorrect default name value", category.getName());
        String value = "2";
        // set a name
        category.setName(value);
        assertEquals("Incorrect name value after setting a new one",
            value, category.getName());
    }

    /**
     * <p>Tests <code>setName(null)</code>.</p>
     */
    public void testNameAllowsNull() {
        // set a name
        // set null
        try {
            category.setName(null);
            assertNull("Field 'name' should contain 'null' value",
                category.getName());
        } catch (IllegalArgumentException e) {
            fail("Field 'name' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getDescription()</code> and
     * <code>setDescription()</code> methods for accuracy.</p>
     */
    public void testGetSetDescription() {
        assertNull("Incorrect default description value", category.getDescription());
        String value = "2";
        // set a description
        category.setDescription(value);
        assertEquals("Incorrect description value after setting a new one",
            value, category.getDescription());
    }

    /**
     * <p>Tests <code>setDescription(null)</code>.</p>
     */
    public void testDescriptionAllowsNull() {
        // set a description
        // set null
        try {
            category.setDescription(null);
            assertNull("Field 'description' should contain 'null' value",
                category.getDescription());
        } catch (IllegalArgumentException e) {
            fail("Field 'description' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getParentCategory()</code> and
     * <code>setParentCategory()</code> methods for accuracy.</p>
     */
    public void testGetSetParentCategory() {
        assertNull("Incorrect default parentCategory value", category.getParentCategory());
        Category value = new Category();
        // set a parentCategory
        category.setParentCategory(value);
        assertEquals("Incorrect parentCategory value after setting a new one",
            value, category.getParentCategory());
    }

    /**
     * <p>Tests <code>setParentCategory(null)</code>.</p>
     */
    public void testParentCategoryAllowsNull() {
        // set a parentCategory
        // set null
        try {
            category.setParentCategory(null);
            assertNull("Field 'parentCategory' should contain 'null' value",
                category.getParentCategory());
        } catch (IllegalArgumentException e) {
            fail("Field 'parentCategory' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getStatus()</code> and
     * <code>setStatus()</code> methods for accuracy.</p>
     */
    public void testGetSetStatus() {
        assertNull("Incorrect default status value", category.getStatus());
        Status value = Status.APPROVED;
        // set a status
        category.setStatus(value);
        assertEquals("Incorrect status value after setting a new one",
            value, category.getStatus());
    }

    /**
     * <p>Tests <code>setStatus(null)</code>.</p>
     */
    public void testStatusAllowsNull() {
        // set a status
        // set null
        try {
            category.setStatus(null);
            assertNull("Field 'status' should contain 'null' value",
                category.getStatus());
        } catch (IllegalArgumentException e) {
            fail("Field 'status' should allow null values");
        }
    }

    /**
     * <p>Tests <code>isViewable()</code> and
     * <code>setViewable()</code> methods for accuracy.</p>
     */
    public void testGetSetViewable() {
        assertFalse("Incorrect default viewable value", category.isViewable());
        boolean value = true;
        // set a viewable
        category.setViewable(value);
        assertEquals("Incorrect viewable value after setting a new one",
            value, category.isViewable());
    }
}
