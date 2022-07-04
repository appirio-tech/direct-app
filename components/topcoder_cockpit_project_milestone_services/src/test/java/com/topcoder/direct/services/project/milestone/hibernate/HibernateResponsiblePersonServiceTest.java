/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;

import com.topcoder.direct.services.project.metadata.DirectProjectMetadataService;
import com.topcoder.direct.services.project.metadata.MockDirectProjectMetadataService;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementException;
import com.topcoder.direct.services.project.milestone.ResponsiblePersonService;
import com.topcoder.direct.services.project.milestone.model.ResponsiblePerson;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for class <code>HibernateResponsiblePersonService</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HibernateResponsiblePersonServiceTest extends BaseUnitTest {

    /**
     * Represents the <code>HibernateResponsiblePersonService</code> instance used to test against.
     */
    private HibernateResponsiblePersonService impl;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(HibernateResponsiblePersonServiceTest.class);
    }

    /**
     * Set up the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @Before
    public void setUp() throws Exception {
        impl = new HibernateResponsiblePersonService();
        impl.setDirectProjectMetadataService((DirectProjectMetadataService) APP_CONTEXT
            .getBean("directProjectMetadataService"));
        impl.setLogger(LogManager.getLog("test"));
    }

    /**
     * Tear down the test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    @Override
    @After
    public void tearDown() throws Exception {
        impl = null;
    }

    /**
     * Inheritance test, verifies <code>HibernateResponsiblePersonService</code> subclasses should be correct.
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof InitializingBean);
        assertTrue("The instance's subclass is not correct.", impl instanceof ResponsiblePersonService);
    }

    /**
     * Accuracy test for the constructor <code>HibernateResponsiblePersonService()</code>.<br>
     * Instance should be created successfully.
     */
    @Test
    public void testCtor() {
        assertNotNull("Instance should be created", impl);
    }

    /**
     * Accuracy test for the method <code>afterPropertiesSet()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testAfterPropertiesSet() {
        impl.afterPropertiesSet();
    }

    /**
     * <p>
     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
     * logger is null.<br>
     * Expect ProjectMilestoneManagementConfigurationException.
     * </p>
     */
    @Test(expected = ProjectMilestoneManagementConfigurationException.class)
    public void testAfterPropertiesSet_LoggerNull() {
        impl.setLogger(null);
        impl.afterPropertiesSet();
    }

    /**
     * <p>
     * Failure test for the method <code>afterPropertiesSet()</code>.<br>
     * directProjectMetadataService is null.<br>
     * Expect ProjectMilestoneManagementConfigurationException.
     * </p>
     */
    @Test(expected = ProjectMilestoneManagementConfigurationException.class)
    public void testAfterPropertiesSet_DirectProjectMetadataServiceNull() {
        impl.setDirectProjectMetadataService(null);
        impl.afterPropertiesSet();
    }

    /**
     * Accuracy test for the method <code>getAllResponsiblePeople(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAllResponsiblePeople() throws Exception {
        List<ResponsiblePerson> res = impl.getAllResponsiblePeople(1);

        assertEquals("'list size' should be correct", 2, res.size());

        assertEquals("'userId' should be correct", 654321, res.get(0).getUserId());
        assertEquals("'name' should be correct", "handle1", res.get(0).getName());
        assertEquals("'userId' should be correct", 123456, res.get(1).getUserId());
        assertEquals("'name' should be correct", "heffan", res.get(1).getName());
    }

    /**
     * Accuracy test for the method <code>getAllResponsiblePeople(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAllResponsiblePeople2() throws Exception {
        List<ResponsiblePerson> res = impl.getAllResponsiblePeople(2);

        assertEquals("'list size' should be correct", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAllResponsiblePeople(long)</code>.<br>
     * metaData value's format is invalid.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testGetAllResponsiblePeople_MetaDataValueInvalid() throws Exception {
        impl.getAllResponsiblePeople(101);
    }

    /**
     * <p>
     * Failure test for the method <code>getAllResponsiblePeople(long)</code>.<br>
     * NumberFormatException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testGetAllResponsiblePeople_NumberFormatExceptionOccurs() throws Exception {
        impl.getAllResponsiblePeople(102);
    }

    /**
     * <p>
     * Failure test for the method <code>getAllResponsiblePeople(long)</code>.<br>
     * PersistenceException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testGetAllResponsiblePeople_PersistenceExceptionOccurs() throws Exception {
        impl.getAllResponsiblePeople(103);
    }

    /**
     * <p>
     * Failure test for the method <code>getAllResponsiblePeople(long)</code>.<br>
     * Exception occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testGetAllResponsiblePeople_ExceptionOccurs() throws Exception {
        impl.getAllResponsiblePeople(999);
    }

    /**
     * Accuracy test for the method <code>getLogger()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetLogger() {
        impl = new HibernateResponsiblePersonService();
        assertNull("The initial value should be null", impl.getLogger());
        Log expect = LogManager.getLog("test");
        impl.setLogger(expect);
        assertEquals("The result should be same as", expect, impl.getLogger());
    }

    /**
     * Accuracy test for the method <code>setLogger(Log)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetLogger() {
        impl = new HibernateResponsiblePersonService();
        assertNull("The initial value should be null", impl.getLogger());
        Log expect = LogManager.getLog("test");
        impl.setLogger(expect);
        assertEquals("The result should be same as", expect, impl.getLogger());
    }

    /**
     * Accuracy test for the method <code>getDirectProjectMetadataService()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetDirectProjectMetadataService() {
        impl = new HibernateResponsiblePersonService();
        assertNull("The initial value should be null", impl.getDirectProjectMetadataService());
        DirectProjectMetadataService expect = new MockDirectProjectMetadataService();
        impl.setDirectProjectMetadataService(expect);
        assertEquals("The result should be same as", expect, impl.getDirectProjectMetadataService());
    }

    /**
     * Accuracy test for the method <code>setDirectProjectMetadataService(DirectProjectMetadataService)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetDirectProjectMetadataService() {
        impl = new HibernateResponsiblePersonService();
        assertNull("The initial value should be null", impl.getDirectProjectMetadataService());
        DirectProjectMetadataService expect = new MockDirectProjectMetadataService();
        impl.setDirectProjectMetadataService(expect);
        assertEquals("The result should be same as", expect, impl.getDirectProjectMetadataService());
    }

}
