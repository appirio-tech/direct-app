/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.CompLink;
import com.topcoder.catalog.entity.CompVersion;
import junit.framework.TestCase;

/**
 * <p>Unit test case for {@link CompLink}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class CompLinkTest extends TestCase {
    /**
     * <p>Represents CompLink instance for testing.</p>
     */
    private CompLink compLink;

    /**
     * <p>Creates a new instance of CompLink.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        compLink = new CompLink();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        compLink = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>CompLink()</code> constructor.</p>
     */
    public void testCompLink() {
        assertNotNull("Unable to instantiate CompLink", compLink);
    }

    /**
     * <p>Tests <code>getId()</code> and
     * <code>getId()</code> methods for accuracy.</p>
     */
    public void testGetSetId() {
        assertNull("Incorrect default id value", compLink.getId());
        Long id = 1L;
        // set a id
        compLink.setId(id);
        assertEquals("Incorrect id value after setting a new one",
            id, compLink.getId());
    }

    /**
     * <p>Tests <code>setId(null)</code>.</p>
     */
    public void testSetIdAllowsNull() {
        // set null
        try {
            compLink.setId(null);
            assertNull("Field 'id' should contain 'null' value",
                compLink.getId());
        } catch (IllegalArgumentException e) {
            fail("Field 'id' should allow null values");
        }
    }


    /**
     * <p>Tests <code>getLink()</code> and
     * <code>setLink()</code> methods for accuracy.</p>
     */
    public void testGetSetLink() {
        assertNull("Incorrect default link value", compLink.getLink());
        String value = "8";
        // set a link
        compLink.setLink(value);
        assertEquals("Incorrect link value after setting a new one",
            value, compLink.getLink());
    }

    /**
     * <p>Tests <code>setLink(null)</code>.</p>
     */
    public void testLinkAllowsNull() {
        // set a link
        // set null
        try {
            compLink.setLink(null);
            assertNull("Field 'link' should contain 'null' value",
                compLink.getLink());
        } catch (IllegalArgumentException e) {
            fail("Field 'link' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getCompVersion()</code> and
     * <code>setCompVersion()</code> methods for accuracy.</p>
     */
    public void testGetSetVersion() {
        assertNull("Incorrect default version value", compLink.getCompVersion());
        CompVersion value = new CompVersion();
        // set a version
        compLink.setCompVersion(value);
        assertEquals("Incorrect version value after setting a new one",
            value, compLink.getCompVersion());
    }

    /**
     * <p>Tests <code>setCompVersion(null)</code>.</p>
     */
    public void testVersionAllowsNull() {
        // set a version
        // set null
        try {
            compLink.setCompVersion(null);
            assertNull("Field 'version' should contain 'null' value",
                compLink.getCompVersion());
        } catch (IllegalArgumentException e) {
            fail("Field 'version' should allow null values");
        }
    }
}
