/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * <p>
 * Test class: <code>Project</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ProjectTest extends TestCase {
    /**
     * <p>
     * An instance of <code>Project</code> which is tested.
     * </p>
     */
    private Project target = null;

    /**
     * <p>
     * setUp() routine.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        target = new Project();
    }

    /**
     * <p>
     * Inheritance test.
     * </p>
     * <p>
     * Verifies <code>Project</code> subclasses <code>AuditableEntity</code>.
     * </p>
     */
    public void testInheritance() {
        assertTrue("Project does not subclasses AuditableEntity.", target instanceof AuditableEntity);
    }

    /**
     * <p>
     * Tests the <code>com.topcoder.clients.model.Project()</code> for proper behavior. Verifies that Verifies that
     * instance should be created.
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
     * Tests the <code>getDescription()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetDescription() throws Exception {
        target.setDescription("name1");
        assertEquals("getDescription", "name1", target.getDescription());
    }

    /**
     * <p>
     * Tests the <code>setActive(boolean)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetActive_boolean() throws Exception {
        target.setActive(true);
        assertEquals("isActive", true, target.isActive());
    }

    /**
     * <p>
     * Tests the <code>isActive()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodIsActive() throws Exception {
        target.setActive(true);
        assertEquals("isActive", true, target.isActive());
    }

    /**
     * <p>
     * Tests the <code>getClient()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetClient() throws Exception {
        Client client = new Client();
        target.setClient(client);
        assertEquals("getClient", client, target.getClient());
    }

    /**
     * <p>
     * Tests the <code>setProjectStatus(ProjectStatus)</code> for proper behavior. Verifies that the property should
     * be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetProjectStatus_ProjectStatus() throws Exception {
        ProjectStatus projectStatus = new ProjectStatus();
        target.setProjectStatus(projectStatus);
        assertEquals("getProjectStatus", projectStatus, target.getProjectStatus());
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
     * Tests the <code>getChildProjects()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetChildProjects() throws Exception {
        List<Project> childProjects = new ArrayList<Project>();
        target.setChildProjects(childProjects);
        assertEquals("getChildProjects", childProjects, target.getChildProjects());
    }

    /**
     * <p>
     * Tests the <code>setChildProjects(List)</code> for proper behavior. Verifies that the property should be
     * correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetChildProjects_List() throws Exception {
        List<Project> childProjects = new ArrayList<Project>();
        target.setChildProjects(childProjects);
        assertEquals("getChildProjects", childProjects, target.getChildProjects());
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
        target.setSalesTax(10d);
        assertEquals("getSalesTax", 10d, target.getSalesTax());
    }

    /**
     * <p>
     * Tests the <code>getPOBoxNumber()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetPOBoxNumber() throws Exception {
        target.setPOBoxNumber("numb1");
        assertEquals("getPOBoxNumber", "numb1", target.getPOBoxNumber());
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
        target.setPaymentTermsId(5L);
        assertEquals("getPaymentTermsId", 5L, target.getPaymentTermsId());
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
        target.setPaymentTermsId(4L);
        assertEquals("getPaymentTermsId", 4L, target.getPaymentTermsId());
    }

    /**
     * <p>
     * Tests the <code>getParentProjectId()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetParentProjectId() throws Exception {
        target.setParentProjectId(3L);
        assertEquals("getParentProjectId", 3L, target.getParentProjectId());
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

    /**
     * <p>
     * Tests the <code>setClient(Client)</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetClient_Client() throws Exception {
        Client client = new Client();
        target.setClient(client);
        assertEquals("getClient", client, target.getClient());
    }

    /**
     * <p>
     * Tests the <code>getProjectStatus()</code> for proper behavior. Verifies that the property should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodGetProjectStatus() throws Exception {
        ProjectStatus projectStatus = new ProjectStatus();
        target.setProjectStatus(projectStatus);
        assertEquals("getProjectStatus", projectStatus, target.getProjectStatus());
    }

    /**
     * <p>
     * Tests the <code>setParentProjectId(long)</code> for proper behavior. Verifies that the property should be
     * correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetParentProjectId_long() throws Exception {
        target.setParentProjectId(2L);
        assertEquals("getParentProjectId", 2L, target.getParentProjectId());
    }

    /**
     * <p>
     * Tests the <code>setPOBoxNumber(String)</code> for proper behavior. Verifies that the property should be
     * correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetPOBoxNumber_String() throws Exception {
        target.setPOBoxNumber("numb");
        assertEquals("getPOBoxNumber", "numb", target.getPOBoxNumber());
    }

    /**
     * <p>
     * Tests the <code>setDescription(String)</code> for proper behavior. Verifies that the property should be
     * correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    public void testMethodSetDescription_String() throws Exception {
        target.setDescription("desc");
        assertEquals("getDescription", "desc", target.getDescription());
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
        target.setSalesTax(10d);
        assertEquals("getSalesTax", 10d, target.getSalesTax());
    }

}
