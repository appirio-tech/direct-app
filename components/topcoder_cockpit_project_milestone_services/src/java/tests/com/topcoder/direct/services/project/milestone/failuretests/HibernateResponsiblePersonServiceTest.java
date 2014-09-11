/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.failuretests;

import java.sql.Connection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementException;
import com.topcoder.direct.services.project.milestone.ResponsiblePersonService;
import com.topcoder.direct.services.project.milestone.hibernate.HibernateResponsiblePersonService;


/**
 * <p>Stress test case of {@link HibernateResponsiblePersonService}.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class HibernateResponsiblePersonServiceTest extends TestCase {

    private static ApplicationContext CTX = new FileSystemXmlApplicationContext("test_files/failure/beans.xml");
    /**
     * <p>The HibernateResponsiblePersonService instance to test.</p>
     */
    private ResponsiblePersonService instance;

    /** Represents the JDBC connection. */
    private Connection connection;

    /**
     * <p>Sets up test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        connection = TestHelper.createConnection();
        TestHelper.executeSqlFile(connection, "test_files/failure/clear.sql");
        instance = (ResponsiblePersonService) CTX.getBean("responsiblePersonService");
    }

    /**
     * <p>Tears down test environment.</p>
     *
     * @throws Exception to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
        TestHelper.executeSqlFile(connection, "test_files/failure/clear.sql");

        if (connection != null) {
            connection.close();
        }
    }

    /**
     * <p>Creates a test suite for the tests in this test case.</p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(HibernateResponsiblePersonServiceTest.class);

        return suite;
    }

    /**
     * <p>Failure test for method HibernateResponsiblePersonService#getAllResponsiblePeople().</p>
     *
     * @throws Throwable to junit
     */
    public void test_getAllResponsiblePeople() throws Throwable {
        try {
        	instance.getAllResponsiblePeople(999);
        	fail("exception expected");
        } catch (ProjectMilestoneManagementException e) {
        	// pass
        }
    }
}
