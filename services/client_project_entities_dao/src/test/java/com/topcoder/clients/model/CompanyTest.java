/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import junit.framework.TestCase;

/**
 * <p>
 * Test class: <code>Company</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class CompanyTest extends TestCase {
    /**
     * <p>
     * An instance of <code>Company</code> which is tested.
     * </p>
     */
    private Company target = null;

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        target = new Company();
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>Company</code> subclasses <code>AuditableEntity</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("Company does not subclasses AuditableEntity.", target instanceof AuditableEntity);
    }

    /**
     * <p>
     * Tests the <code>com.topcoder.clients.model.Company()</code> for proper behavior. Verifies that instance should
     * be created.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testConstructor() throws Exception {
        assertNotNull("Instance should be created.", target);
    }

    /**
     * <p>
     * Tests the <code>setPasscode(String)</code> for proper behavior. Verifies that the property should be correct..
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetPasscode_String() throws Exception {
        target.setPasscode("passcode1");
        assertEquals("getPasscode", "passcode1", target.getPasscode());
    }

    /**
     * <p>
     * Tests the <code>getPasscode()</code> for proper behavior. Verifies that the property should be correct..
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetPasscode() throws Exception {
        target.setPasscode("passcode2");
        assertEquals("getPasscode", "passcode2", target.getPasscode());
    }

}
