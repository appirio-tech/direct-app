/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.failuretests;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.topcoder.direct.services.project.milestone.EntityNotFoundException;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementException;
import com.topcoder.direct.services.project.milestone.hibernate.HibernateMilestoneService;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;


/**
 * <p>Stress test case of {@link HibernateMilestoneService}.</p>
 *
 * @author gjw99
 * @version 1.0
 */
public class HibernateMilestoneServiceTest extends TestCase {

    private static ApplicationContext CTX = new FileSystemXmlApplicationContext("test_files/failure/beans.xml");
    /**
     * <p>The HibernateMilestoneService instance to test.</p>
     */
    private HibernateMilestoneService instance;

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
        instance = (HibernateMilestoneService) CTX.getBean("milestoneService");
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
        TestSuite suite = new TestSuite(HibernateMilestoneServiceTest.class);

        return suite;
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#add().</p>
     *
     * @throws Throwable to junit
     */
    public void test_add1() throws Throwable {
        try {
        	Milestone entity = null;
        	instance.add(entity);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#add().</p>
     *
     * @throws Throwable to junit
     */
    public void test_add2() throws Throwable {
        try {
        	Milestone entity = new Milestone();
        	instance.add(entity);
        	fail("exception expected");
        } catch (ProjectMilestoneManagementException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#add().</p>
     *
     * @throws Throwable to junit
     */
    public void test_add3() throws Throwable {
        try {
        	List<Milestone> list = new ArrayList<Milestone>();
        	instance.add(list);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#add().</p>
     *
     * @throws Throwable to junit
     */
    public void test_add4() throws Throwable {
        try {
        	List<Milestone> list = new ArrayList<Milestone>();
        	list.add(null);
        	instance.add(list);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#add().</p>
     *
     * @throws Throwable to junit
     */
    public void test_add5() throws Throwable {
        try {
        	List<Milestone> list = new ArrayList<Milestone>();
        	list.add(new Milestone());
        	instance.add(list);
        	fail("exception expected");
        } catch (ProjectMilestoneManagementException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#update().</p>
     *
     * @throws Throwable to junit
     */
    public void test_update1() throws Throwable {
        try {
        	Milestone entity = null;
        	instance.update(entity);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#update().</p>
     *
     * @throws Throwable to junit
     */
    public void test_update2() throws Throwable {
        try {
        	Milestone entity = new Milestone();
        	Transaction tx = instance.getSessionFactory().getCurrentSession().getTransaction();
        	tx.begin();
        	instance.update(entity);
        	tx.commit();
        	fail("exception expected");
        } catch (EntityNotFoundException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#delete().</p>
     *
     * @throws Throwable to junit
     */
    public void test_delete1() throws Throwable {
        try {
        	Transaction tx = instance.getSessionFactory().getCurrentSession().getTransaction();
        	tx.begin();
        	instance.delete(2);
        	tx.commit();
        	fail("exception expected");
        } catch (EntityNotFoundException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#delete().</p>
     *
     * @throws Throwable to junit
     */
    public void test_delete2() throws Throwable {
        try {
        	List<Long> ids = null;
        	instance.delete(ids);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#delete().</p>
     *
     * @throws Throwable to junit
     */
    public void test_delete3() throws Throwable {
        try {
        	List<Long> ids = new ArrayList<Long>();
        	instance.delete(ids);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#delete().</p>
     *
     * @throws Throwable to junit
     */
    public void test_delete4() throws Throwable {
        try {
        	List<Long> ids = new ArrayList<Long>();
        	ids.add(null);
        	instance.delete(ids);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#get().</p>
     *
     * @throws Throwable to junit
     */
    public void test_get1() throws Throwable {
        try {
        	List<Long> ids = null;
        	instance.get(ids);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#get().</p>
     *
     * @throws Throwable to junit
     */
    public void test_get2() throws Throwable {
        try {
        	List<Long> ids = new ArrayList<Long>();
        	instance.get(ids);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#get().</p>
     *
     * @throws Throwable to junit
     */
    public void test_get3() throws Throwable {
        try {
        	List<Long> ids = new ArrayList<Long>();
        	ids.add(null);
        	instance.get(ids);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#get().</p>
     *
     * @throws Throwable to junit
     */
    public void test_getAll() throws Throwable {
        try {
        	List<MilestoneStatus> requestedStatuses = new ArrayList<MilestoneStatus>();
        	requestedStatuses.add(null);
        	instance.getAll(2, requestedStatuses, null);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#getAllInMonth().</p>
     *
     * @throws Throwable to junit
     */
    public void test_getAllInMonth1() throws Throwable {
        try {
        	List<MilestoneStatus> requestedStatuses = new ArrayList<MilestoneStatus>();
        	requestedStatuses.add(null);
        	instance.getAllInMonth(2, 2011, 11, requestedStatuses);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#getAllInMonth().</p>
     *
     * @throws Throwable to junit
     */
    public void test_getAllInMonth2() throws Throwable {
        try {
        	instance.getAllInMonth(2, 0, 11, null);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#getAllInMonth().</p>
     *
     * @throws Throwable to junit
     */
    public void test_getAllInMonth3() throws Throwable {
        try {
        	instance.getAllInMonth(2, -1, 11, null);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#getAllInMonth().</p>
     *
     * @throws Throwable to junit
     */
    public void test_getAllInMonth4() throws Throwable {
        try {
        	instance.getAllInMonth(2, 2011, 0, null);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }

    /**
     * <p>Failure test for method HibernateMilestoneService#getAllInMonth().</p>
     *
     * @throws Throwable to junit
     */
    public void test_getAllInMonth5() throws Throwable {
        try {
        	instance.getAllInMonth(2, 2011, 13, null);
        	fail("exception expected");
        } catch (IllegalArgumentException e) {
        	// pass
        }
    }
}
