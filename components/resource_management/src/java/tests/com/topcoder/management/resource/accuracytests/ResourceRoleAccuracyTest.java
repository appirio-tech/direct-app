/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.resource.accuracytests;

import com.topcoder.management.resource.AuditableResourceStructure;
import com.topcoder.management.resource.IdAlreadySetException;
import com.topcoder.management.resource.ResourceRole;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * This accuracy tests addresses the functionality provided by the
 * <code>ResourceRole</code> class. It tests the ResourceRole(),
 * ResourceRole(long) and ResourceRole(long, String, long) constructors;
 * getDescription(), getId(), getName(), getPhaseType(), setDescription(String),
 * setId(long), setPhaseType(Long) and setName(String) methods.
 * </p>
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ResourceRoleAccuracyTest extends TestCase {
    /**
     * <p>
     * The id of <code>ResourceRole</code> instance for test.
     * </p>
     */
    private long id = 1;

    /**
     * <p>
     * The name of <code>ResourceRole</code> instance for test.
     * </p>
     */
    private String name = "ResourceRole";

    /**
     * <p>
     * The phase type of <code>ResourceRole</code> instance for test.
     * </p>
     */
    private long phaseType = 2;

    /**
     * <p>
     * The instance of <code>ResourceRole</code> for test.
     * </p>
     */
    private ResourceRole role = null;

    /**
     * <p>
     * Test suite of <code>ResourceRoleAccuracyTest</code>.
     * </p>
     * @return Test suite of <code>ResourceRoleAccuracyTest</code>.
     */
    public static Test suite() {
        return new TestSuite(ResourceRoleAccuracyTest.class);
    }

    /**
     * <p>
     * Accuracy test of <code>ResourceRole()</code> constructor.
     * </p>
     */
    public void testResourceRoleCtor1() {
        role = new ResourceRole();

        // check null here.
        assertNotNull("Create ResourceRole failed.", role);

        // check the type here.
        assertTrue("ResourceRole should extend AuditableResourceStructure.",
                role instanceof AuditableResourceStructure);

        // check the fields here.
        assertEquals("The id should be: -1.", -1, role.getId());
        assertNull("The role name should be: null.", role.getName());
        assertNull("The role description should be: null.", role.getDescription());
        assertNull("The phase type should be: null.", role.getPhaseType());
        assertEquals("The 'UNSET_ID' should be: -1.", -1, ResourceRole.UNSET_ID);
    }

    /**
     * <p>
     * Accuracy test of <code>ResourceRole(long)</code> constructor.
     * </p>
     */
    public void testResourceRoleCtor2() {
        role = new ResourceRole(id);

        // check null here.
        assertNotNull("Create ResourceRole failed.", role);

        // check the type here.
        assertTrue("ResourceRole should extend AuditableResourceStructure.",
                role instanceof AuditableResourceStructure);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, role.getId());
        assertNull("The role name should be: null.", role.getName());
        assertNull("The role description should be: null.", role.getDescription());
        assertNull("The phase type should be: null.", role.getPhaseType());
        assertEquals("The 'UNSET_ID' should be: -1.", -1, ResourceRole.UNSET_ID);
    }

    /**
     * <p>
     * Accuracy test of <code>ResourceRole(long, String, long)</code>
     * constructor.
     * </p>
     */
    public void testResourceRoleCtor3() {
        role = new ResourceRole(id, name, phaseType);

        // check null here.
        assertNotNull("Create ResourceRole failed.", role);

        // check the type here.
        assertTrue("ResourceRole should extend AuditableResourceStructure.",
                role instanceof AuditableResourceStructure);

        // check the fields here.
        assertEquals("The id should be: " + id + ".", id, role.getId());
        assertEquals("The role name should be: " + name + ".", name, role.getName());
        assertNull("The role description should be: null.", role.getDescription());
        assertEquals("The phase type should be: " + phaseType + ".", phaseType, role.getPhaseType()
                .longValue());
        assertEquals("The 'UNSET_ID' should be: -1.", -1, ResourceRole.UNSET_ID);
    }

    /**
     * <p>
     * Accuracy test of the <code>getId()</code> method.
     * </p>
     */
    public void testMethod_getId() {
        role = new ResourceRole();

        assertEquals("The id should be: -1.", -1, role.getId());

        // set and check the id here.
        role.setId(id);
        assertEquals("The id should be: " + id + ".", id, role.getId());
    }

    /**
     * <p>
     * Accuracy test of the <code>setId(long)</code> method.
     * </p>
     */
    public void testMethod_setId() {
        role = new ResourceRole();

        // check the id here.
        role.setId(id);
        assertEquals("The id should be: " + id + ".", id, role.getId());

        // set the id again.
        try {
            role.setId(100);
            fail("IdAlreadySetException should be thrown here.");
        } catch (IdAlreadySetException e) {
            // success
        }
    }

    /**
     * <p>
     * Accuracy test of the <code>getName()</code> method.
     * </p>
     */
    public void testMethod_getName() {
        role = new ResourceRole(id, name, phaseType);

        // check the name here.
        assertEquals("The role name should be: " + name + ".", name, role.getName());

        // set the name to null and then test it.
        role.setName(null);
        assertNull("The role name should be: null.", role.getName());
    }

    /**
     * <p>
     * Accuracy test of the <code>setName(String)</code> method.
     * </p>
     */
    public void testMethod_setName() {
        role = new ResourceRole(id);
        role.setName(name);

        // check the name here.
        assertEquals("The role name should be: " + name + ".", name, role.getName());

        // set the name to null and then test it.
        role.setName(null);
        assertNull("The role name should be: null.", role.getName());

        // set the name and then test it.
        role = new ResourceRole(id, "newName", phaseType);

        // check the name here.
        assertEquals("The role name should be: newName.", "newName", role.getName());
    }

    /**
     * <p>
     * Accuracy test of the <code>getDescription()</code> method.
     * </p>
     */
    public void testMethod_getDescription() {
        role = new ResourceRole(id, name, phaseType);
        assertNull("The role description should be: null.", role.getDescription());

        // set the description and then test it.
        String description = "This is a resource role.";
        role.setDescription(description);
        assertEquals("The role description should be: " + description + ".", description, role
                .getDescription());
    }

    /**
     * <p>
     * Accuracy test of the <code>setDescription(String)</code> method.
     * </p>
     */
    public void testMethod_setDescription() {
        // set the description and then test it.
        role = new ResourceRole(id);

        String description = "This is a resource role.";
        role.setDescription(description);
        assertEquals("The role description should be: " + description + ".", description, role
                .getDescription());

        // set to null and then test it.
        role.setDescription(null);
        assertNull("The type description should be: null.", role.getDescription());
    }

    /**
     * <p>
     * Accuracy test of the <code>getPhaseType()</code> method.
     * </p>
     */
    public void testMethod_getPhaseType() {
        role = new ResourceRole();
        assertNull("The phase type should be: null.", role.getPhaseType());

        // test with setting phase type in constructor.
        role = new ResourceRole(id, name, phaseType);
        assertEquals("The phase type should be: " + phaseType + ".", phaseType, role.getPhaseType()
                .longValue());

        // set it and then test it.
        role.setPhaseType(new Long(5));
        assertEquals("The phase type should be: 5.", 5, role.getPhaseType().longValue());
    }

    /**
     * <p>
     * Accuracy test of the <code>setPhaseType(long)</code> method.
     * </p>
     */
    public void testMethod_setPhaseType() {
        role = new ResourceRole();
        assertNull("The phase type should be: null.", role.getPhaseType());

        // set phase type in constructor.
        role.setPhaseType(new Long(phaseType));
        assertEquals("The phase type should be: " + phaseType + ".", phaseType, role.getPhaseType()
                .longValue());

        // set it and then test it.
        role.setPhaseType(new Long(5));
        assertEquals("The phase type should be: 5.", 5, role.getPhaseType().longValue());
    }
}
