/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import java.util.Date;

import junit.framework.TestCase;

/**
 * <p>
 * Test class: <code>Client</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ClientTest extends TestCase {
    /**
     * <p>
     * An instance of <code>Client</code> which is tested.
     * </p>
     */
    private Client target = null;

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        target = new Client();
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>Client</code> subclasses <code>AuditableEntity</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("Client does not subclasses AuditableEntity.", target instanceof AuditableEntity);
    }

    /**
     * <p>
     * Tests the <code>com.topcoder.clients.model.Client()</code> for proper behavior. Verifies that instance should
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
     * Tests the <code>setEndDate(Date)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetEndDate_Date() throws Exception {
        Date endDate = new Date();
        target.setEndDate(endDate);
        assertEquals("getEndDate", endDate, target.getEndDate());
    }

    /**
     * <p>
     * Tests the <code>getStartDate()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetStartDate() throws Exception {
        Date startDate = new Date();
        target.setStartDate(startDate);
        assertEquals("getStartDate", startDate, target.getStartDate());
    }

    /**
     * <p>
     * Tests the <code>setStartDate(Date)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetStartDate_Date() throws Exception {
        Date startDate = new Date();
        target.setStartDate(startDate);
        assertEquals("getStartDate", startDate, target.getStartDate());
    }

    /**
     * <p>
     * Tests the <code>setCompany(Company)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetCompany_Company() throws Exception {
        Company company = new Company();
        target.setCompany(company);
        assertEquals("getCompany", company, target.getCompany());
    }

    /**
     * <p>
     * Tests the <code>getEndDate()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetEndDate() throws Exception {
        Date endDate = new Date();
        target.setEndDate(endDate);
        assertEquals("getEndDate", endDate, target.getEndDate());
    }

    /**
     * <p>
     * Tests the <code>getClientStatus()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetClientStatus() throws Exception {
        ClientStatus clientStatus = new ClientStatus();
        target.setClientStatus(clientStatus);
        assertEquals("getClientStatus", clientStatus, target.getClientStatus());
    }

    /**
     * <p>
     * Tests the <code>setSalesTax(double)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetSalesTax_double() throws Exception {
        target.setSalesTax(3.3);
        assertEquals("getSalesTax", 3.3, target.getSalesTax());
    }

    /**
     * <p>
     * Tests the <code>getCodeName()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetCodeName() throws Exception {
        target.setCodeName("name1");
        assertEquals("getCodeName", "name1", target.getCodeName());
    }

    /**
     * <p>
     * Tests the <code>setClientStatus(ClientStatus)</code> for proper behavior. Verifies that the property should be
     * correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetClientStatus_ClientStatus() throws Exception {
        ClientStatus clientStatus = new ClientStatus();
        target.setClientStatus(clientStatus);
        assertEquals("getClientStatus", clientStatus, target.getClientStatus());
    }

    /**
     * <p>
     * Tests the <code>getPaymentTermsId()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetPaymentTermsId() throws Exception {
        target.setPaymentTermsId(1L);
        assertEquals("getPaymentTermsId", 1L, target.getPaymentTermsId());
    }

    /**
     * <p>
     * Tests the <code>getSalesTax()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetSalesTax() throws Exception {
        target.setSalesTax(3.3);
        assertEquals("getSalesTax", 3.3, target.getSalesTax());
    }

    /**
     * <p>
     * Tests the <code>setCodeName(String)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetCodeName_String() throws Exception {
        target.setCodeName("name2");
        assertEquals("getCodeName", "name2", target.getCodeName());
    }

    /**
     * <p>
     * Tests the <code>setPaymentTermsId(long)</code> for proper behavior. Verifies that the property should be
     * correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetPaymentTermsId_long() throws Exception {
        target.setPaymentTermsId(6L);
        assertEquals("getPaymentTermsId", 6L, target.getPaymentTermsId());
    }

    /**
     * <p>
     * Tests the <code>getCompany()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetCompany() throws Exception {
        Company company = new Company();
        target.setCompany(company);
        assertEquals("getCompany", company, target.getCompany());
    }

}
