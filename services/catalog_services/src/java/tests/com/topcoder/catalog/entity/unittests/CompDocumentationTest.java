/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import junit.framework.TestCase;

import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompVersion;

/**
 * <p>Unit test case for {@link CompDocumentation}.</p>
 *
 * @author KingStone
 * @version 1.1
 */
public class CompDocumentationTest extends TestCase {
    /**
     * <p>Represents CompDocumentation instance for testing.</p>
     */
    private CompDocumentation compDocumentation;

    /**
     * <p>Creates a new instance of CompDocumentation.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        compDocumentation = new CompDocumentation();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        compDocumentation = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>CompDocumentation()</code> constructor.</p>
     */
    public void testCompDocumentation() {
        assertNotNull("Unable to initialize CompDocumentation.", compDocumentation);
    }

    /**
     * <p>Tests <code>getId()</code> and
     * <code>setId()</code> methods for accuracy.</p>
     */
    public void testGetSetId() {
        assertNull("Incorrect default id value", compDocumentation.getId());
        Long value = 2576203783L;
        // set a id
        compDocumentation.setId(value);
        assertEquals("Incorrect id value after setting a new one",
            value, compDocumentation.getId());
    }

    /**
     * <p>Tests <code>setId(null)</code>.</p>
     */
    public void testIdAllowsNull() {
        // set a id
        // set null
        try {
            compDocumentation.setId(null);
            assertNull("Field 'id' should contain 'null' value",
                    compDocumentation.getId());
        } catch (IllegalArgumentException e) {
            fail("Field 'Id' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getCompVersion()</code> and
     * <code>setCompVersion()</code> methods for accuracy.</p>
     */
    public void testGetSetVersion() {
        assertNull("Incorrect default version value", compDocumentation.getCompVersion());
        CompVersion value = new CompVersion();
        // set a version
        compDocumentation.setCompVersion(value);
        assertEquals("Incorrect version value after setting a new one",
            value, compDocumentation.getCompVersion());
    }

    /**
     * <p>Tests <code>setCompVersion(null)</code>.</p>
     */
    public void testVersionAllowsNull() {
        // set a version
        // set null
        try {
            compDocumentation.setCompVersion(null);
            assertNull("Field 'version' should contain 'null' value",
                    compDocumentation.getCompVersion());
        } catch (IllegalArgumentException e) {
            fail("Field 'version' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getDocumentTypeId()</code> and
     * <code>setDocumentTypeId()</code> methods for accuracy.</p>
     */
    public void testGetSetDocumentTypeId() {
        assertNull("Incorrect default DocumentTypeId value", compDocumentation.getDocumentTypeId());
        Long value = 2121412412L;
        // set a DocumentTypeId
        compDocumentation.setDocumentTypeId(value);
        assertEquals("Incorrect DocumentTypeId value after setting a new one",
            value, compDocumentation.getDocumentTypeId());
    }

    /**
     * <p>Tests <code>setDocumentTypeId(null)</code>.</p>
     */
    public void testDocumentTypeIdAllowsNull() {
        // set a DocumentTypeId
        // set null
        try {
            compDocumentation.setDocumentTypeId(null);
            assertNull("Field 'documentTypeId' should contain 'null' value",
                    compDocumentation.getDocumentTypeId());
        } catch (IllegalArgumentException e) {
            fail("Field 'documentTypeId' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getDocumentType()</code> and
     * <code>setDocumentType()</code> methods for accuracy.</p>
     */
    public void testGetSetDocumentType() {
        assertNull("Incorrect default documentType value", compDocumentation.getDocumentType());
        String value = "online review template";
        // set a DocumentType
        compDocumentation.setDocumentType(value);
        assertEquals("Incorrect documentType value after setting a new one",
            value, compDocumentation.getDocumentType());
        // set a empty String
        value = "  ";
        compDocumentation.setDocumentType(value);
        // empty String should be set
        assertEquals("Incorrect documentType value after setting a new one",
            value, compDocumentation.getDocumentType());
    }

    /**
     * <p>Tests <code>setDocumentType(null)</code>.</p>
     */
    public void testDocumentTypeAllowsNull() {
        // set a DocumentType
        // set null
        try {
            compDocumentation.setDocumentType(null);
            assertNull("Field 'documentType' should contain 'null' value",
                    compDocumentation.getDocumentType());
        } catch (IllegalArgumentException e) {
            fail("Field 'DocumentType' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getDocumentName()</code> and
     * <code>setDocumentName()</code> methods for accuracy.</p>
     */
    public void testGetSetDocumentName() {
        assertNull("Incorrect default DocumentName value", compDocumentation.getDocumentName());
        String value = "catalog xxxx";
        // set a DocumentName
        compDocumentation.setDocumentName(value);
        assertEquals("Incorrect DocumentName value after setting a new one",
            value, compDocumentation.getDocumentName());
        // set a empty String
        value = "   ";
        compDocumentation.setDocumentName(value);
        // empty String should be set
        assertEquals("Incorrect DocumentName value after setting a new one",
            value, compDocumentation.getDocumentName());
    }

    /**
     * <p>Tests <code>setDocumentName(null)</code>.</p>
     */
    public void testDocumentNameAllowsNull() {
        // set a DocumentName
        // set null
        try {
            compDocumentation.setDocumentName(null);
            assertNull("Field 'documentName' should contain 'null' value",
                    compDocumentation.getDocumentName());
        } catch (IllegalArgumentException e) {
            fail("Field 'DocumentName' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getUrl()</code> and
     * <code>setUrl()</code> methods for accuracy.</p>
     */
    public void testGetSetUrl() {
        assertNull("Incorrect default url value", compDocumentation.getUrl());
        String value = "catalog xxxx";
        // set a Url
        compDocumentation.setUrl(value);
        assertEquals("Incorrect url value after setting a new one",
            value, compDocumentation.getUrl());
        // set a empty String
        value = "   ";
        compDocumentation.setUrl(value);
        // empty String should be set
        assertEquals("Incorrect url value after setting a new one",
            value, compDocumentation.getUrl());
    }

    /**
     * <p>Tests <code>setUrl(null)</code>.</p>
     */
    public void testUrlAllowsNull() {
        // set a Url
        // set null
        try {
            compDocumentation.setUrl(null);
            assertNull("Field 'url' should contain 'null' value",
                    compDocumentation.getUrl());
        } catch (IllegalArgumentException e) {
            fail("Field 'Url' should allow null values");
        }
    }
}
