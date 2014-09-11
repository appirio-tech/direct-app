/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompClient;
import com.topcoder.catalog.entity.CompUser;
import com.topcoder.catalog.entity.CompVersion;
import com.topcoder.catalog.entity.Component;
import com.topcoder.catalog.entity.Status;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Unit test case for {@link Component}.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class ComponentTest extends TestCase {
    /**
     * <p>Represents Component instance for testing.</p>
     */
    private Component component;

    /**
     * <p>Creates a new instance of Component.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        component = new Component();
    }

    /**
     * <p>Tears down the test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    protected void tearDown() throws Exception {
        component = null;
        super.tearDown();
    }

    /**
     * <p>Tests <code>Component()</code> constructor.</p>
     */
    public void testComponent() {
        assertNotNull("Unable to instantiate Component", component);
    }

    /**
     * <p>Tests <code>getId()</code> and
     * <code>getId()</code> methods for accuracy.</p>
     */
    public void testGetSetId() {
        assertNull("Incorrect default id value", component.getId());
        Long id = 1L;
        // set a id
        component.setId(id);
        assertEquals("Incorrect id value after setting a new one",
            id, component.getId());
    }

    /**
     * <p>Tests <code>setId(null)</code>.</p>
     */
    public void testSetIdAllowsNull() {
        // set null
        try {
            component.setId(null);
            assertNull("Field 'id' should contain 'null' value",
                component.getId());
        } catch (IllegalArgumentException e) {
            fail("Field 'id' should allow null values");
        }
    }


    /**
     * <p>Tests <code>getCategories()</code> and
     * <code>setCategories()</code> methods for accuracy.</p>
     */
    public void testGetSetCategories() {
        assertNull("Incorrect default categories value", component.getCategories());
        List<Category> value = new ArrayList<Category>();
        // set a categories
        component.setCategories(value);
        assertEquals("Incorrect categories value after setting a new one",
            value, component.getCategories());
    }

    /**
     * <p>Tests <code>setCategories(null)</code>.</p>
     */
    public void testCategoriesAllowsNull() {
        // set a categories
        // set null
        try {
            component.setCategories(null);
            assertNull("Field 'categories' should contain 'null' value",
                component.getCategories());
        } catch (IllegalArgumentException e) {
            fail("Field 'categories' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getClients()</code> and
     * <code>setClients()</code> methods for accuracy.</p>
     */
    public void testGetSetClients() {
        assertNull("Incorrect default clients value", component.getClients());
        Set<CompClient> value = new HashSet<CompClient>();
        // set a clients
        component.setClients(value);
        assertEquals("Incorrect clients value after setting a new one",
            value, component.getClients());
    }

    /**
     * <p>Tests <code>setClients(null)</code>.</p>
     */
    public void testClientsAllowsNull() {
        // set a clients
        // set null
        try {
            component.setClients(null);
            assertNull("Field 'clients' should contain 'null' value",
                component.getClients());
        } catch (IllegalArgumentException e) {
            fail("Field 'clients' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getCurrentVersion()</code> and
     * <code>setCurrentVersion()</code> methods for accuracy.</p>
     */
    public void testGetSetCurrentVersion() {
        assertNull("Incorrect default currentVersion value", component.getCurrentVersion());
        CompVersion value = new CompVersion();
        // set a currentVersion
        component.setCurrentVersion(value);
        assertEquals("Incorrect currentVersion value after setting a new one",
            value, component.getCurrentVersion());
    }

    /**
     * <p>Tests <code>setCurrentVersion(null)</code>.</p>
     */
    public void testCurrentVersionAllowsNull() {
        // set a currentVersion
        // set null
        try {
            component.setCurrentVersion(null);
            assertNull("Field 'currentVersion' should contain 'null' value",
                component.getCurrentVersion());
        } catch (IllegalArgumentException e) {
            fail("Field 'currentVersion' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getDescription()</code> and
     * <code>setDescription()</code> methods for accuracy.</p>
     */
    public void testGetSetDescription() {
        assertNull("Incorrect default description value", component.getDescription());
        String value = "9";
        // set a description
        component.setDescription(value);
        assertEquals("Incorrect description value after setting a new one",
            value, component.getDescription());
    }

    /**
     * <p>Tests <code>setDescription(null)</code>.</p>
     */
    public void testDescriptionAllowsNull() {
        // set a description
        // set null
        try {
            component.setDescription(null);
            assertNull("Field 'description' should contain 'null' value",
                component.getDescription());
        } catch (IllegalArgumentException e) {
            fail("Field 'description' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getFunctionalDesc()</code> and
     * <code>setFunctionalDesc()</code> methods for accuracy.</p>
     */
    public void testGetSetFunctionalDesc() {
        assertNull("Incorrect default functionalDesc value", component.getFunctionalDesc());
        String value = "10";
        // set a functionalDesc
        component.setFunctionalDesc(value);
        assertEquals("Incorrect functionalDesc value after setting a new one",
            value, component.getFunctionalDesc());
    }

    /**
     * <p>Tests <code>setFunctionalDesc(null)</code>.</p>
     */
    public void testFunctionalDescAllowsNull() {
        // set a functionalDesc
        // set null
        try {
            component.setFunctionalDesc(null);
            assertNull("Field 'functionalDesc' should contain 'null' value",
                component.getFunctionalDesc());
        } catch (IllegalArgumentException e) {
            fail("Field 'functionalDesc' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getName()</code> and
     * <code>setName()</code> methods for accuracy.</p>
     */
    public void testGetSetName() {
        assertNull("Incorrect default name value", component.getName());
        String value = "11";
        // set a name
        component.setName(value);
        assertEquals("Incorrect name value after setting a new one",
            value, component.getName());
    }

    /**
     * <p>Tests <code>setName(null)</code>.</p>
     */
    public void testNameAllowsNull() {
        // set a name
        // set null
        try {
            component.setName(null);
            assertNull("Field 'name' should contain 'null' value",
                component.getName());
        } catch (IllegalArgumentException e) {
            fail("Field 'name' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getRootCategory()</code> and
     * <code>setRootCategory()</code> methods for accuracy.</p>
     */
    public void testGetSetRootCategory() {
        assertNull("Incorrect default rootCategory value", component.getRootCategory());
        Category value = new Category();
        // set a rootCategory
        component.setRootCategory(value);
        assertEquals("Incorrect rootCategory value after setting a new one",
            value, component.getRootCategory());
    }

    /**
     * <p>Tests <code>setRootCategory(null)</code>.</p>
     */
    public void testRootCategoryAllowsNull() {
        // set a rootCategory
        // set null
        try {
            component.setRootCategory(null);
            assertNull("Field 'rootCategory' should contain 'null' value",
                component.getRootCategory());
        } catch (IllegalArgumentException e) {
            fail("Field 'rootCategory' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getShortDesc()</code> and
     * <code>setShortDesc()</code> methods for accuracy.</p>
     */
    public void testGetSetShortDesc() {
        assertNull("Incorrect default shortDesc value", component.getShortDesc());
        String value = "12";
        // set a shortDesc
        component.setShortDesc(value);
        assertEquals("Incorrect shortDesc value after setting a new one",
            value, component.getShortDesc());
    }

    /**
     * <p>Tests <code>setShortDesc(null)</code>.</p>
     */
    public void testShortDescAllowsNull() {
        // set a shortDesc
        // set null
        try {
            component.setShortDesc(null);
            assertNull("Field 'shortDesc' should contain 'null' value",
                component.getShortDesc());
        } catch (IllegalArgumentException e) {
            fail("Field 'shortDesc' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getStatus()</code> and
     * <code>setStatus()</code> methods for accuracy.</p>
     */
    public void testGetSetStatus() {
        assertNull("Incorrect default status value", component.getStatus());
        Status value = Status.APPROVED;
        // set a status
        component.setStatus(value);
        assertEquals("Incorrect status value after setting a new one",
            value, component.getStatus());
    }

    /**
     * <p>Tests <code>setStatus(null)</code>.</p>
     */
    public void testStatusAllowsNull() {
        // set a status
        // set null
        try {
            component.setStatus(null);
            assertNull("Field 'status' should contain 'null' value",
                component.getStatus());
        } catch (IllegalArgumentException e) {
            fail("Field 'status' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getUsers()</code> and
     * <code>setUsers()</code> methods for accuracy.</p>
     */
    public void testGetSetUsers() {
        assertNull("Incorrect default users value", component.getUsers());
        Set<CompUser> value = new HashSet<CompUser>();
        // set a users
        component.setUsers(value);
        assertEquals("Incorrect users value after setting a new one",
            value, component.getUsers());
    }

    /**
     * <p>Tests <code>setUsers(null)</code>.</p>
     */
    public void testUsersAllowsNull() {
        // set a users
        // set null
        try {
            component.setUsers(null);
            assertNull("Field 'users' should contain 'null' value",
                component.getUsers());
        } catch (IllegalArgumentException e) {
            fail("Field 'users' should allow null values");
        }
    }

    /**
     * <p>Tests <code>getVersions()</code> and
     * <code>setVersions()</code> methods for accuracy.</p>
     */
    public void testGetSetVersions() {
        assertNull("Incorrect default versions value", component.getVersions());
        List<CompVersion> value = new ArrayList<CompVersion>();
        // set a versions
        component.setVersions(value);
        assertEquals("Incorrect versions value after setting a new one",
            value, component.getVersions());
    }

    /**
     * <p>Tests <code>setVersions(versions)</code> method for accuracy in filtering
     * out <code>null</code> and duplicates.</p>
     */
    public void testSetVersions() {
        // Set up individual CompVersion elements
        CompVersion cv1 = new CompVersion();
        cv1.setVersion(5L);
        CompVersion cv2 = new CompVersion();
        cv2.setVersion(3L);
        CompVersion cv3 = new CompVersion();
        cv3.setVersion(15L);
        CompVersion cv4 = new CompVersion(); // leave the version number as null

        // Set up the dirty CompVersion list for input ...
        List<CompVersion> versions = new ArrayList<CompVersion>();
        versions.add(null);
        versions.add(null);
        versions.add(cv3);
        versions.add(cv2);
        versions.add(cv2);
        versions.add(null);
        versions.add(null);
        versions.add(null);
        versions.add(cv1);
        versions.add(cv4);
        versions.add(null);
        versions.add(cv4);
        versions.add(cv1);

        // Set up the expected result
        List<CompVersion> expectedVersions = new ArrayList<CompVersion>();
        expectedVersions.add(cv3);
        expectedVersions.add(cv2);
        expectedVersions.add(cv1);

        try {
            component.setVersions(versions);
            assertEquals("Incorrect versions value after setting a new one",
                expectedVersions, component.getVersions());
        } catch (Exception e) {
            fail("The setVersions method shouldn't throw any exception");
        }
    }

    /**
     * <p>Tests <code>setVersions(null)</code>.</p>
     */
    public void testVersionsAllowsNull() {
        // set a versions
        // set null
        try {
            component.setVersions(null);
            assertNull("Field 'versions' should contain 'null' value",
                component.getVersions());
        } catch (IllegalArgumentException e) {
            fail("Field 'versions' should allow null values");
        }
    }


}
