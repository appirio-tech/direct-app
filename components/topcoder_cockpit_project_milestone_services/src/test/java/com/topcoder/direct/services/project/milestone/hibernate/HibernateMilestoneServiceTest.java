/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.milestone.hibernate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;

import com.topcoder.direct.services.project.milestone.EntityNotFoundException;
import com.topcoder.direct.services.project.milestone.MilestoneService;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementConfigurationException;
import com.topcoder.direct.services.project.milestone.ProjectMilestoneManagementException;
import com.topcoder.direct.services.project.milestone.model.Milestone;
import com.topcoder.direct.services.project.milestone.model.MilestoneStatus;
import com.topcoder.direct.services.project.milestone.model.SortOrder;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * Unit tests for class <code>HibernateMilestoneService</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class HibernateMilestoneServiceTest extends BaseUnitTest {

    /**
     * Represents the <code>HibernateMilestoneService</code> instance used to test against.
     */
    private HibernateMilestoneService impl;

    /**
     * Creates a test suite for unit tests in this test case.
     *
     * @return a Test suite for this test case.
     */
    public static junit.framework.Test suite() {
        return new junit.framework.JUnit4TestAdapter(HibernateMilestoneServiceTest.class);
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
        super.setUp();
        impl = new HibernateMilestoneService();
        impl.setSessionFactory((SessionFactory) APP_CONTEXT.getBean("sessionFactory"));
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
        super.tearDown();
        impl = null;
    }

    /**
     * Inheritance test, verifies <code>HibernateMilestoneService</code> subclasses should be correct.
     */
    @SuppressWarnings("cast")
    @Test
    public void testInheritance() {
        assertTrue("The instance's subclass is not correct.", impl instanceof InitializingBean);
        assertTrue("The instance's subclass is not correct.", impl instanceof MilestoneService);
    }

    /**
     * Accuracy test for the constructor <code>HibernateMilestoneService()</code>.<br>
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
     * sessionFactory is null.<br>
     * Expect ProjectMilestoneManagementConfigurationException.
     * </p>
     */
    @Test(expected = ProjectMilestoneManagementConfigurationException.class)
    public void testAfterPropertiesSet_SessionFactoryNull() {
        impl.setSessionFactory(null);
        impl.afterPropertiesSet();
    }

    /**
     * Accuracy test for the method <code>add(Milestone)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testAddMilestone() throws Exception {
        Milestone milestone1 = createMilestone(1);
        long id = impl.add(milestone1);

        assertTrue("The created entity id should be positive", id > 0);

        Milestone res = impl.get(id);
        assertNotNull("entity should be not null", res);
        assertEquals("'name' should be correct", "name1", res.getName());
        assertEquals("'description' should be correct", "description1", res.getDescription());
        assertNotNull("'dueDate' should be correct", res.getDueDate());
        assertFalse("'sendNotifications' should be correct", res.isSendNotifications());
        assertTrue("'completed' should be correct", res.isCompleted());
        assertEquals("'projectId' should be correct", 1, res.getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.COMPLETED, res.getStatus());
    }

    /**
     * <p>
     * Failure test for the method <code>add(Milestone)</code>.<br>
     * parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddMilestone_ParameterNull() throws Exception {
        impl.add((Milestone) null);
    }

    /**
     * <p>
     * Failure test for the method <code>add(Milestone)</code>.<br>
     * HibernateException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testAddMilestone_HibernateExceptionOccurs() throws Exception {
        impl.setSessionFactory((SessionFactory) APP_CONTEXT_INVALID.getBean("sessionFactory"));
        impl.add(createMilestone(1));
    }

    /**
     * Accuracy test for the method <code>add(List&lt;Milestone&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testAddListOfMilestone() throws Exception {
        Milestone milestone1 = createMilestone(1);

        Milestone milestone2 = createMilestone(2);
        milestone2.setDueDate(new Date(System.currentTimeMillis() + 1000 * 3600 * 24));
        milestone2.setCompleted(false);

        List<Milestone> milestones = Arrays.asList(milestone1, milestone2);
        impl.add(milestones);

        assertTrue("The created entity id should be positive", milestone1.getId() > 0);
        assertTrue("The created entity id should be positive", milestone2.getId() > 0);

        Milestone res = impl.get(milestone1.getId());
        assertNotNull("entity should be not null", res);
        assertEquals("'name' should be correct", "name1", res.getName());
        assertEquals("'description' should be correct", "description1", res.getDescription());
        assertNotNull("'dueDate' should be correct", res.getDueDate());
        assertFalse("'sendNotifications' should be correct", res.isSendNotifications());
        assertTrue("'completed' should be correct", res.isCompleted());
        assertEquals("'projectId' should be correct", 1, res.getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.COMPLETED, res.getStatus());
        assertEquals("'owners' should be correct", 2, res.getOwners().size());
        assertEquals("'owner.name' should be correct", "person1_1", res.getOwners().get(0).getName());
        assertEquals("'owner.userId' should be correct", 1, res.getOwners().get(0).getUserId());
        assertEquals("'owner.name' should be correct", "person1_2", res.getOwners().get(1).getName());
        assertEquals("'owner.userId' should be correct", 2, res.getOwners().get(1).getUserId());

        res = impl.get(milestone2.getId());
        assertNotNull("entity should be not null", res);
        assertEquals("'name' should be correct", "name2", res.getName());
        assertEquals("'description' should be correct", "description2", res.getDescription());
        assertNotNull("'dueDate' should be correct", res.getDueDate());
        assertFalse("'sendNotifications' should be correct", res.isSendNotifications());
        assertFalse("'completed' should be correct", res.isCompleted());
        assertEquals("'projectId' should be correct", 2, res.getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.UPCOMING, res.getStatus());
        assertEquals("'owners' should be correct", 2, res.getOwners().size());
        assertEquals("'owner.name' should be correct", "person2_1", res.getOwners().get(0).getName());
        assertEquals("'owner.userId' should be correct", 1, res.getOwners().get(0).getUserId());
        assertEquals("'owner.name' should be correct", "person2_2", res.getOwners().get(1).getName());
        assertEquals("'owner.userId' should be correct", 2, res.getOwners().get(1).getUserId());
    }

    /**
     * <p>
     * Failure test for the method <code>add(List&lt;Milestone&gt;)</code>.<br>
     * parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddListOfMilestone_ParameterNull() throws Exception {
        impl.add((List<Milestone>) null);
    }

    /**
     * <p>
     * Failure test for the method <code>add(List&lt;Milestone&gt;)</code>.<br>
     * parameter is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddListOfMilestone_ParameterEmpty() throws Exception {
        impl.add(new ArrayList<Milestone>());
    }

    /**
     * <p>
     * Failure test for the method <code>add(List&lt;Milestone&gt;)</code>.<br>
     * parameter contains null element.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddListOfMilestone_ParameterContainsNull() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        milestones.add(createMilestone(1));
        milestones.add(null);
        milestones.add(createMilestone(3));
        impl.add(milestones);
    }

    /**
     * <p>
     * Failure test for the method <code>add(List&lt;Milestone&gt;)</code>.<br>
     * HibernateException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testAddListOfMilestone_HibernateExceptionOccurs() throws Exception {
        impl.setSessionFactory((SessionFactory) APP_CONTEXT_INVALID.getBean("sessionFactory"));

        List<Milestone> milestones = new ArrayList<Milestone>();
        milestones.add(createMilestone(1));
        impl.add(milestones);
    }

    /**
     * Accuracy test for the method <code>update(Milestone)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testUpdateMilestone() throws Exception {
        Milestone milestone1 = createMilestone(1);
        long id = impl.add(milestone1);

        Milestone res = impl.get(id);
        assertNotNull("entity should be not null", res);
        assertEquals("'name' should be correct", "name1", res.getName());
        assertEquals("'description' should be correct", "description1", res.getDescription());
        assertNotNull("'dueDate' should be correct", res.getDueDate());
        assertFalse("'sendNotifications' should be correct", res.isSendNotifications());
        assertTrue("'completed' should be correct", res.isCompleted());
        assertEquals("'projectId' should be correct", 1, res.getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.COMPLETED, res.getStatus());

        milestone1.setName("name2");
        milestone1.setDescription("description2");
        milestone1.setSendNotifications(true);
        milestone1.setCompleted(false);
        milestone1.setProjectId(2);

        impl.update(milestone1);

        res = impl.get(id);
        assertNotNull("entity should be not null", res);
        assertEquals("'name' should be correct", "name2", res.getName());
        assertEquals("'description' should be correct", "description2", res.getDescription());
        assertNotNull("'dueDate' should be correct", res.getDueDate());
        assertTrue("'sendNotifications' should be correct", res.isSendNotifications());
        assertFalse("'completed' should be correct", res.isCompleted());
        assertEquals("'projectId' should be correct", 2, res.getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.OVERDUE, res.getStatus());
    }

    /**
     * <p>
     * Failure test for the method <code>update(Milestone)</code>.<br>
     * parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateMilestone_ParameterNull() throws Exception {
        impl.update((Milestone) null);
    }

    /**
     * <p>
     * Failure test for the method <code>update(Milestone)</code>.<br>
     * Entity is not exist.<br>
     * Expect EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EntityNotFoundException.class)
    public void testUpdateMilestone_EntityNotExist() throws Exception {
        impl.update(createMilestone(1));
    }

    /**
     * <p>
     * Failure test for the method <code>update(Milestone)</code>.<br>
     * HibernateException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testUpdateMilestone_HibernateExceptionOccurs() throws Exception {
        impl.setSessionFactory((SessionFactory) APP_CONTEXT_INVALID.getBean("sessionFactory"));
        impl.update(createMilestone(1));
    }

    /**
     * Accuracy test for the method <code>update(List&lt;Milestone&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testUpdateListOfMilestone() throws Exception {
        Milestone milestone1 = createMilestone(1);

        Milestone milestone2 = createMilestone(2);
        milestone2.setDueDate(new Date(System.currentTimeMillis() + 1000 * 3600 * 24));
        milestone2.setCompleted(false);

        List<Milestone> milestones = Arrays.asList(milestone1, milestone2);
        impl.add(milestones);

        assertTrue("The created entity id should be positive", milestone1.getId() > 0);
        assertTrue("The created entity id should be positive", milestone2.getId() > 0);

        Milestone res = impl.get(milestone1.getId());
        assertNotNull("entity should be not null", res);
        assertEquals("'name' should be correct", "name1", res.getName());
        assertEquals("'description' should be correct", "description1", res.getDescription());
        assertNotNull("'dueDate' should be correct", res.getDueDate());
        assertFalse("'sendNotifications' should be correct", res.isSendNotifications());
        assertTrue("'completed' should be correct", res.isCompleted());
        assertEquals("'projectId' should be correct", 1, res.getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.COMPLETED, res.getStatus());
        assertEquals("'owners' should be correct", 2, res.getOwners().size());
        assertEquals("'owner.name' should be correct", "person1_1", res.getOwners().get(0).getName());
        assertEquals("'owner.userId' should be correct", 1, res.getOwners().get(0).getUserId());
        assertEquals("'owner.name' should be correct", "person1_2", res.getOwners().get(1).getName());
        assertEquals("'owner.userId' should be correct", 2, res.getOwners().get(1).getUserId());

        res = impl.get(milestone2.getId());
        assertNotNull("entity should be not null", res);
        assertEquals("'name' should be correct", "name2", res.getName());
        assertEquals("'description' should be correct", "description2", res.getDescription());
        assertNotNull("'dueDate' should be correct", res.getDueDate());
        assertFalse("'sendNotifications' should be correct", res.isSendNotifications());
        assertFalse("'completed' should be correct", res.isCompleted());
        assertEquals("'projectId' should be correct", 2, res.getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.UPCOMING, res.getStatus());
        assertEquals("'owners' should be correct", 2, res.getOwners().size());
        assertEquals("'owner.name' should be correct", "person2_1", res.getOwners().get(0).getName());
        assertEquals("'owner.userId' should be correct", 1, res.getOwners().get(0).getUserId());
        assertEquals("'owner.name' should be correct", "person2_2", res.getOwners().get(1).getName());
        assertEquals("'owner.userId' should be correct", 2, res.getOwners().get(1).getUserId());

        milestone1.setName("new_name1");
        milestone1.setDescription("new_description1");
        milestone1.setCompleted(false);
        milestone1.getOwners().get(0).setName("new_person1_1");

        milestone2.setName("new_name2");
        milestone2.setDescription("new_description2");
        milestone2.setCompleted(true);
        milestone2.getOwners().get(1).setName("new_person2_2");
        milestone2.getOwners().get(1).setUserId(10);

        impl.update(milestones);

        res = impl.get(milestone1.getId());
        assertNotNull("entity should be not null", res);
        assertEquals("'name' should be correct", "new_name1", res.getName());
        assertEquals("'description' should be correct", "new_description1", res.getDescription());
        assertNotNull("'dueDate' should be correct", res.getDueDate());
        assertFalse("'sendNotifications' should be correct", res.isSendNotifications());
        assertFalse("'completed' should be correct", res.isCompleted());
        assertEquals("'projectId' should be correct", 1, res.getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.OVERDUE, res.getStatus());
        assertEquals("'owners' should be correct", 2, res.getOwners().size());
        assertEquals("'owner.name' should be correct", "new_person1_1", res.getOwners().get(0).getName());
        assertEquals("'owner.userId' should be correct", 1, res.getOwners().get(0).getUserId());
        assertEquals("'owner.name' should be correct", "person1_2", res.getOwners().get(1).getName());
        assertEquals("'owner.userId' should be correct", 2, res.getOwners().get(1).getUserId());

        res = impl.get(milestone2.getId());
        assertNotNull("entity should be not null", res);
        assertEquals("'name' should be correct", "new_name2", res.getName());
        assertEquals("'description' should be correct", "new_description2", res.getDescription());
        assertNotNull("'dueDate' should be correct", res.getDueDate());
        assertFalse("'sendNotifications' should be correct", res.isSendNotifications());
        assertTrue("'completed' should be correct", res.isCompleted());
        assertEquals("'projectId' should be correct", 2, res.getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.COMPLETED, res.getStatus());
        assertEquals("'owners' should be correct", 2, res.getOwners().size());
        assertEquals("'owner.name' should be correct", "person2_1", res.getOwners().get(0).getName());
        assertEquals("'owner.userId' should be correct", 1, res.getOwners().get(0).getUserId());
        assertEquals("'owner.name' should be correct", "new_person2_2", res.getOwners().get(1).getName());
        assertEquals("'owner.userId' should be correct", 10, res.getOwners().get(1).getUserId());
    }

    /**
     * <p>
     * Failure test for the method <code>update(List&lt;Milestone&gt;)</code>.<br>
     * parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateListOfMilestone_ParameterNull() throws Exception {
        impl.update((List<Milestone>) null);
    }

    /**
     * <p>
     * Failure test for the method <code>update(List&lt;Milestone&gt;)</code>.<br>
     * parameter is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateListOfMilestone_ParameterEmpty() throws Exception {
        impl.update(new ArrayList<Milestone>());
    }

    /**
     * <p>
     * Failure test for the method <code>update(List&lt;Milestone&gt;)</code>.<br>
     * parameter contains null element.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateListOfMilestone_ParameterContainsNull() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        milestones.add(createMilestone(1));
        milestones.add(null);
        milestones.add(createMilestone(3));
        impl.update(milestones);
    }

    /**
     * <p>
     * Failure test for the method <code>update(List&lt;Milestone&gt;)</code>.<br>
     * Entity is not exist.<br>
     * Expect EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EntityNotFoundException.class)
    public void testUpdateListOfMilestone_EntityNotExist() throws Exception {
        List<Milestone> milestones = new ArrayList<Milestone>();
        milestones.add(createMilestone(1));
        impl.update(milestones);
    }

    /**
     * <p>
     * Failure test for the method <code>update(List&lt;Milestone&gt;)</code>.<br>
     * HibernateException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testUpdateListOfMilestone_HibernateExceptionOccurs() throws Exception {
        impl.setSessionFactory((SessionFactory) APP_CONTEXT_INVALID.getBean("sessionFactory"));

        List<Milestone> milestones = new ArrayList<Milestone>();
        milestones.add(createMilestone(1));
        impl.update(milestones);
    }

    /**
     * Accuracy test for the method <code>delete(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testDeleteLong() throws Exception {
        Milestone milestone1 = createMilestone(1);
        long id = impl.add(milestone1);

        Milestone res = impl.get(id);
        assertNotNull("entity should be not null", res);

        impl.delete(id);

        res = impl.get(id);
        assertNull("entity should be null", res);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long)</code>.<br>
     * Entity is not exist.<br>
     * Expect EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EntityNotFoundException.class)
    public void testDeleteLong_EntityNotExist() throws Exception {
        impl.delete(-1L);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(long)</code>.<br>
     * HibernateException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testDeleteLong_HibernateExceptionOccurs() throws Exception {
        impl.setSessionFactory((SessionFactory) APP_CONTEXT_INVALID.getBean("sessionFactory"));
        impl.delete(1L);
    }

    /**
     * Accuracy test for the method <code>delete(List&lt;Long&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testDeleteListOfLong() throws Exception {
        Milestone milestone1 = createMilestone(1);
        long id1 = impl.add(milestone1);

        Milestone milestone2 = createMilestone(2);
        long id2 = impl.add(milestone2);

        Milestone res = impl.get(id1);
        assertNotNull("entity should be not null", res);
        res = impl.get(id2);
        assertNotNull("entity should be not null", res);

        List<Long> ids = Arrays.asList(id1, id2);
        impl.delete(ids);

        res = impl.get(id1);
        assertNull("entity should be null", res);
        res = impl.get(id2);
        assertNull("entity should be null", res);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(List&lt;Long&gt;)</code>.<br>
     * parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteListOfLong_ParameterNull() throws Exception {
        impl.delete((List<Long>) null);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(List&lt;Long&gt;)</code>.<br>
     * parameter is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteListOfLong_ParameterEmpty() throws Exception {
        impl.delete(new ArrayList<Long>());
    }

    /**
     * <p>
     * Failure test for the method <code>delete(List&lt;Long&gt;)</code>.<br>
     * parameter contains null element.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testDeleteListOfLong_ParameterContainsNull() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(null);
        ids.add(3L);
        impl.delete(ids);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(List&lt;Long&gt;)</code>.<br>
     * Entity is not exist.<br>
     * Expect EntityNotFoundException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = EntityNotFoundException.class)
    public void testDeleteListOfLong_EntityNotExist() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        ids.add(-1L);
        impl.delete(ids);
    }

    /**
     * <p>
     * Failure test for the method <code>delete(List&lt;Long&gt;)</code>.<br>
     * HibernateException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testDeleteListOfLong_HibernateExceptionOccurs() throws Exception {
        impl.setSessionFactory((SessionFactory) APP_CONTEXT_INVALID.getBean("sessionFactory"));

        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        impl.delete(ids);
    }

    /**
     * Accuracy test for the method <code>get(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetLong() throws Exception {
        Milestone milestone1 = createMilestone(1);
        long id = impl.add(milestone1);

        assertTrue("The created entity id should be positive", id > 0);

        Milestone res = impl.get(id);
        assertNotNull("entity should be not null", res);
        assertEquals("'name' should be correct", "name1", res.getName());
        assertEquals("'description' should be correct", "description1", res.getDescription());
        assertNotNull("'dueDate' should be correct", res.getDueDate());
        assertFalse("'sendNotifications' should be correct", res.isSendNotifications());
        assertTrue("'completed' should be correct", res.isCompleted());
        assertEquals("'projectId' should be correct", 1, res.getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.COMPLETED, res.getStatus());
        assertEquals("'owners' should be correct", 2, res.getOwners().size());
        assertEquals("'owner.name' should be correct", "person1_1", res.getOwners().get(0).getName());
        assertEquals("'owner.userId' should be correct", 1, res.getOwners().get(0).getUserId());
        assertEquals("'owner.name' should be correct", "person1_2", res.getOwners().get(1).getName());
        assertEquals("'owner.userId' should be correct", 2, res.getOwners().get(1).getUserId());
    }

    /**
     * Accuracy test for the method <code>get(long)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetLong2() throws Exception {
        Milestone res = impl.get(-1L);
        assertNull("entity should be null", res);
    }

    /**
     * <p>
     * Failure test for the method <code>get(long)</code>.<br>
     * HibernateException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testGetLong_HibernateExceptionOccurs() throws Exception {
        impl.setSessionFactory((SessionFactory) APP_CONTEXT_INVALID.getBean("sessionFactory"));

        impl.get(1L);
    }

    /**
     * Accuracy test for the method <code>get(List&lt;Long&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetListOfLong() throws Exception {
        Milestone milestone1 = createMilestone(1);
        long id1 = impl.add(milestone1);

        Milestone milestone2 = createMilestone(2);
        milestone2.setCompleted(false);
        long id2 = impl.add(milestone2);

        List<Milestone> res = impl.get(Arrays.asList(99999L, id1, 99998L, 0L, id2, -1L));
        assertNotNull("entity should be not null", res);
        assertEquals("'list size' should be correct", 6, res.size());

        assertNull("'entity' should be null", res.get(0));

        assertEquals("'id' should be correct", id1, res.get(1).getId());
        assertEquals("'name' should be correct", "name1", res.get(1).getName());
        assertEquals("'description' should be correct", "description1", res.get(1).getDescription());
        assertNotNull("'dueDate' should be correct", res.get(1).getDueDate());
        assertFalse("'sendNotifications' should be correct", res.get(1).isSendNotifications());
        assertTrue("'completed' should be correct", res.get(1).isCompleted());
        assertEquals("'projectId' should be correct", 1, res.get(1).getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.COMPLETED, res.get(1).getStatus());
        assertEquals("'owners' should be correct", 2, res.get(1).getOwners().size());
        assertEquals("'owner.name' should be correct", "person1_1", res.get(1).getOwners().get(0).getName());
        assertEquals("'owner.userId' should be correct", 1, res.get(1).getOwners().get(0).getUserId());
        assertEquals("'owner.name' should be correct", "person1_2", res.get(1).getOwners().get(1).getName());
        assertEquals("'owner.userId' should be correct", 2, res.get(1).getOwners().get(1).getUserId());

        assertNull("'entity' should be null", res.get(2));
        assertNull("'entity' should be null", res.get(3));

        assertEquals("'id' should be correct", id2, res.get(4).getId());
        assertEquals("'name' should be correct", "name2", res.get(4).getName());
        assertEquals("'description' should be correct", "description2", res.get(4).getDescription());
        assertNotNull("'dueDate' should be correct", res.get(4).getDueDate());
        assertFalse("'sendNotifications' should be correct", res.get(4).isSendNotifications());
        assertFalse("'completed' should be correct", res.get(4).isCompleted());
        assertEquals("'projectId' should be correct", 2, res.get(4).getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.OVERDUE, res.get(4).getStatus());
        assertEquals("'owners' should be correct", 2, res.get(4).getOwners().size());
        assertEquals("'owner.name' should be correct", "person2_1", res.get(4).getOwners().get(0).getName());
        assertEquals("'owner.userId' should be correct", 1, res.get(4).getOwners().get(0).getUserId());
        assertEquals("'owner.name' should be correct", "person2_2", res.get(4).getOwners().get(1).getName());
        assertEquals("'owner.userId' should be correct", 2, res.get(4).getOwners().get(1).getUserId());

        assertNull("'entity' should be null", res.get(5));
    }

    /**
     * Accuracy test for the method <code>get(List&lt;Long&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetListOfLong2() throws Exception {
        Milestone milestone1 = createMilestone(1);
        long id1 = impl.add(milestone1);

        Milestone milestone2 = createMilestone(2);
        milestone2.setCompleted(false);
        long id2 = impl.add(milestone2);

        List<Milestone> res = impl.get(Arrays.asList(id2, id2, id1));
        assertNotNull("entity should be not null", res);
        assertEquals("'list size' should be correct", 3, res.size());

        assertEquals("'id' should be correct", id2, res.get(0).getId());
        assertEquals("'name' should be correct", "name2", res.get(0).getName());
        assertEquals("'description' should be correct", "description2", res.get(0).getDescription());
        assertNotNull("'dueDate' should be correct", res.get(0).getDueDate());
        assertFalse("'sendNotifications' should be correct", res.get(0).isSendNotifications());
        assertFalse("'completed' should be correct", res.get(0).isCompleted());
        assertEquals("'projectId' should be correct", 2, res.get(0).getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.OVERDUE, res.get(0).getStatus());

        assertEquals("'id' should be correct", id2, res.get(1).getId());
        assertEquals("'name' should be correct", "name2", res.get(1).getName());
        assertEquals("'description' should be correct", "description2", res.get(1).getDescription());
        assertNotNull("'dueDate' should be correct", res.get(1).getDueDate());
        assertFalse("'sendNotifications' should be correct", res.get(1).isSendNotifications());
        assertFalse("'completed' should be correct", res.get(1).isCompleted());
        assertEquals("'projectId' should be correct", 2, res.get(1).getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.OVERDUE, res.get(1).getStatus());

        assertEquals("'id' should be correct", id1, res.get(2).getId());
        assertEquals("'name' should be correct", "name1", res.get(2).getName());
        assertEquals("'description' should be correct", "description1", res.get(2).getDescription());
        assertNotNull("'dueDate' should be correct", res.get(2).getDueDate());
        assertFalse("'sendNotifications' should be correct", res.get(2).isSendNotifications());
        assertTrue("'completed' should be correct", res.get(2).isCompleted());
        assertEquals("'projectId' should be correct", 1, res.get(2).getProjectId());
        assertEquals("'status' should be correct", MilestoneStatus.COMPLETED, res.get(2).getStatus());
    }

    /**
     * <p>
     * Failure test for the method <code>get(List&lt;Long&gt;)</code>.<br>
     * parameter is null.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetListOfLong_ParameterNull() throws Exception {
        impl.get((List<Long>) null);
    }

    /**
     * <p>
     * Failure test for the method <code>get(List&lt;Long&gt;)</code>.<br>
     * parameter is empty.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetListOfLong_ParameterEmpty() throws Exception {
        impl.get(new ArrayList<Long>());
    }

    /**
     * <p>
     * Failure test for the method <code>get(List&lt;Long&gt;)</code>.<br>
     * parameter contains null element.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetListOfLong_ParameterContainsNull() throws Exception {
        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        ids.add(null);
        ids.add(3L);
        impl.get(ids);
    }

    /**
     * <p>
     * Failure test for the method <code>get(List&lt;Long&gt;)</code>.<br>
     * HibernateException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testGetListOfLong_HibernateExceptionOccurs() throws Exception {
        impl.setSessionFactory((SessionFactory) APP_CONTEXT_INVALID.getBean("sessionFactory"));

        List<Long> ids = new ArrayList<Long>();
        ids.add(1L);
        impl.get(ids);
    }

    /**
     * Accuracy test for the method <code>getAll(long, List&lt;MilestoneStatus&gt;, SortOrder)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAll() throws Exception {
        Milestone milestone1 = createMilestone(1);
        long id1 = impl.add(milestone1);

        Milestone milestone2 = createMilestone(2);
        milestone2.setCompleted(false);
        milestone2.setProjectId(1);
        long id2 = impl.add(milestone2);

        Milestone milestone3 = createMilestone(3);
        milestone3.setCompleted(false);
        milestone3.setDueDate(new Date(System.currentTimeMillis() + 1000 * 3600 * 24));
        milestone3.setProjectId(1);
        long id3 = impl.add(milestone3);

        List<Milestone> res = impl.getAll(1, null, null);

        assertEquals("The list size should be correct", 3, res.size());

        assertEquals("'id' should be correct", id2, res.get(0).getId());
        assertEquals("'name' should be correct", "person2_1", res.get(0).getOwners().get(0).getName());
        assertEquals("'id' should be correct", id3, res.get(1).getId());
        assertEquals("'name' should be correct", "person3_1", res.get(1).getOwners().get(0).getName());
        assertEquals("'id' should be correct", id1, res.get(2).getId());
        assertEquals("'name' should be correct", "person1_1", res.get(2).getOwners().get(0).getName());
    }

    /**
     * Accuracy test for the method <code>getAll(long, List&lt;MilestoneStatus&gt;, SortOrder)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAll2() throws Exception {
        Milestone milestone11 = createMilestone(11);
        milestone11.setProjectId(1);
        long id11 = impl.add(milestone11);
        Milestone milestone12 = createMilestone(12);
        milestone12.setDueDate(new Date(milestone11.getDueDate().getTime() + 1));
        milestone12.setProjectId(1);
        long id12 = impl.add(milestone12);

        Milestone milestone21 = createMilestone(21);
        milestone21.setCompleted(false);
        milestone21.setProjectId(1);
        long id21 = impl.add(milestone21);
        Milestone milestone22 = createMilestone(22);
        milestone22.setCompleted(false);
        milestone22.setDueDate(new Date(milestone21.getDueDate().getTime() + 1));
        milestone22.setProjectId(1);
        long id22 = impl.add(milestone22);

        Milestone milestone31 = createMilestone(31);
        milestone31.setCompleted(false);
        milestone31.setDueDate(new Date(System.currentTimeMillis() + 1000 * 3600 * 24));
        milestone31.setProjectId(1);
        long id31 = impl.add(milestone31);
        Milestone milestone32 = createMilestone(32);
        milestone32.setCompleted(false);
        milestone32.setDueDate(new Date(milestone31.getDueDate().getTime() + 1));
        milestone32.setProjectId(1);
        long id32 = impl.add(milestone32);

        List<Milestone> res = impl.getAll(1, new ArrayList<MilestoneStatus>(), null);

        assertEquals("The list size should be correct", 6, res.size());
        assertEquals("'id' should be correct", id21, res.get(0).getId());
        assertEquals("'id' should be correct", id22, res.get(1).getId());
        assertEquals("'id' should be correct", id31, res.get(2).getId());
        assertEquals("'id' should be correct", id32, res.get(3).getId());
        assertEquals("'id' should be correct", id11, res.get(4).getId());
        assertEquals("'id' should be correct", id12, res.get(5).getId());

        res = impl.getAll(1, null, SortOrder.ASCENDING);

        assertEquals("The list size should be correct", 6, res.size());
        assertEquals("'id' should be correct", id21, res.get(0).getId());
        assertEquals("'id' should be correct", id22, res.get(1).getId());
        assertEquals("'id' should be correct", id31, res.get(2).getId());
        assertEquals("'id' should be correct", id32, res.get(3).getId());
        assertEquals("'id' should be correct", id11, res.get(4).getId());
        assertEquals("'id' should be correct", id12, res.get(5).getId());

        res = impl.getAll(1, null, SortOrder.DESCENDING);

        assertEquals("The list size should be correct", 6, res.size());
        assertEquals("'id' should be correct", id22, res.get(0).getId());
        assertEquals("'id' should be correct", id21, res.get(1).getId());
        assertEquals("'id' should be correct", id32, res.get(2).getId());
        assertEquals("'id' should be correct", id31, res.get(3).getId());
        assertEquals("'id' should be correct", id12, res.get(4).getId());
        assertEquals("'id' should be correct", id11, res.get(5).getId());
    }

    /**
     * Accuracy test for the method <code>getAll(long, List&lt;MilestoneStatus&gt;, SortOrder)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAll3() throws Exception {
        Milestone milestone11 = createMilestone(11);
        milestone11.setProjectId(1);
        long id11 = impl.add(milestone11);
        Milestone milestone12 = createMilestone(12);
        milestone12.setDueDate(new Date(milestone11.getDueDate().getTime() + 1));
        milestone12.setProjectId(1);
        long id12 = impl.add(milestone12);

        Milestone milestone21 = createMilestone(21);
        milestone21.setCompleted(false);
        milestone21.setProjectId(1);
        long id21 = impl.add(milestone21);
        Milestone milestone22 = createMilestone(22);
        milestone22.setCompleted(false);
        milestone22.setDueDate(new Date(milestone21.getDueDate().getTime() + 1));
        milestone22.setProjectId(1);
        long id22 = impl.add(milestone22);

        Milestone milestone31 = createMilestone(31);
        milestone31.setCompleted(false);
        milestone31.setDueDate(new Date(System.currentTimeMillis() + 1000 * 3600 * 24));
        milestone31.setProjectId(1);
        long id31 = impl.add(milestone31);
        Milestone milestone32 = createMilestone(32);
        milestone32.setCompleted(false);
        milestone32.setDueDate(new Date(milestone31.getDueDate().getTime() + 1));
        milestone32.setProjectId(1);
        long id32 = impl.add(milestone32);

        List<MilestoneStatus> requestedStatuses = new ArrayList<MilestoneStatus>();
        requestedStatuses.add(MilestoneStatus.OVERDUE);
        requestedStatuses.add(MilestoneStatus.UPCOMING);
        requestedStatuses.add(MilestoneStatus.COMPLETED);
        List<Milestone> res = impl.getAll(1, requestedStatuses, null);

        assertEquals("The list size should be correct", 6, res.size());
        assertEquals("'id' should be correct", id21, res.get(0).getId());
        assertEquals("'id' should be correct", id22, res.get(1).getId());
        assertEquals("'id' should be correct", id31, res.get(2).getId());
        assertEquals("'id' should be correct", id32, res.get(3).getId());
        assertEquals("'id' should be correct", id11, res.get(4).getId());
        assertEquals("'id' should be correct", id12, res.get(5).getId());

        requestedStatuses.remove(MilestoneStatus.UPCOMING);
        res = impl.getAll(1, requestedStatuses, null);

        assertEquals("The list size should be correct", 4, res.size());
        assertEquals("'id' should be correct", id21, res.get(0).getId());
        assertEquals("'id' should be correct", id22, res.get(1).getId());
        assertEquals("'id' should be correct", id11, res.get(2).getId());
        assertEquals("'id' should be correct", id12, res.get(3).getId());

        res = impl.getAll(1, requestedStatuses, SortOrder.DESCENDING);

        assertEquals("The list size should be correct", 4, res.size());
        assertEquals("'id' should be correct", id22, res.get(0).getId());
        assertEquals("'id' should be correct", id21, res.get(1).getId());
        assertEquals("'id' should be correct", id12, res.get(2).getId());
        assertEquals("'id' should be correct", id11, res.get(3).getId());
    }

    /**
     * Accuracy test for the method <code>getAll(long, List&lt;MilestoneStatus&gt;, SortOrder)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAll4() throws Exception {
        List<Milestone> res = impl.getAll(1, null, null);

        assertEquals("The list size should be correct", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAll(long, List&lt;MilestoneStatus&gt;, SortOrder)</code>.<br>
     * parameter contains null element.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAll_ParameterContainsNull() throws Exception {
        List<MilestoneStatus> requestedStatuses = new ArrayList<MilestoneStatus>();
        requestedStatuses.add(MilestoneStatus.OVERDUE);
        requestedStatuses.add(null);
        requestedStatuses.add(MilestoneStatus.COMPLETED);
        impl.getAll(1, requestedStatuses, null);
    }

    /**
     * <p>
     * Failure test for the method <code>getAll(long, List&lt;MilestoneStatus&gt;, SortOrder)</code>.<br>
     * parameter contains duplicate element.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAll_ParameterContainsDuplicate() throws Exception {
        List<MilestoneStatus> requestedStatuses = new ArrayList<MilestoneStatus>();
        requestedStatuses.add(MilestoneStatus.OVERDUE);
        requestedStatuses.add(MilestoneStatus.COMPLETED);
        requestedStatuses.add(MilestoneStatus.COMPLETED);
        impl.getAll(1, requestedStatuses, null);
    }

    /**
     * <p>
     * Failure test for the method <code>getAll(long, List&lt;MilestoneStatus&gt;, SortOrder)</code>.<br>
     * HibernateException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testGetAll_HibernateExceptionOccurs() throws Exception {
        impl.setSessionFactory((SessionFactory) APP_CONTEXT_INVALID.getBean("sessionFactory"));

        impl.getAll(1, null, null);
    }

    /**
     * Accuracy test for the method <code>getAllInMonth(long, int, int, List&lt;MilestoneStatus&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAllInMonth() throws Exception {
        int year = 2011;
        int month = 11;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 2, 3, 4);

        Milestone milestone1 = createMilestone(1);
        milestone1.setDueDate(calendar.getTime());
        long id1 = impl.add(milestone1);

        Milestone milestone2 = createMilestone(2);
        milestone2.setCompleted(false);
        milestone2.setProjectId(1);
        milestone2.setDueDate(calendar.getTime());
        long id2 = impl.add(milestone2);

        Milestone milestone3 = createMilestone(3);
        milestone3.setCompleted(false);
        calendar.set(year, month - 1, 2, 3, 4, 5);
        milestone3.setDueDate(calendar.getTime());
        milestone3.setProjectId(1);
        long id3 = impl.add(milestone3);

        List<Milestone> res = impl.getAllInMonth(1, month, year, null);

        assertEquals("The list size should be correct", 3, res.size());

        assertEquals("'id' should be correct", id2, res.get(0).getId());
        assertEquals("'id' should be correct", id3, res.get(1).getId());
        assertEquals("'id' should be correct", id1, res.get(2).getId());
    }

    /**
     * Accuracy test for the method <code>getAllInMonth(long, int, int, List&lt;MilestoneStatus&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAllInMonth2() throws Exception {
        int year = 2010;
        int month = 8;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 2, 3, 4);

        Milestone milestone1 = createMilestone(1);
        milestone1.setDueDate(calendar.getTime());
        long id1 = impl.add(milestone1);

        Milestone milestone2 = createMilestone(2);
        milestone2.setCompleted(false);
        milestone2.setProjectId(1);
        milestone2.setDueDate(calendar.getTime());
        long id2 = impl.add(milestone2);

        Milestone milestone3 = createMilestone(3);
        milestone3.setCompleted(false);
        calendar.set(year, month - 1, 2, 3, 4, 5);
        milestone3.setDueDate(calendar.getTime());
        milestone3.setProjectId(1);
        long id3 = impl.add(milestone3);

        List<MilestoneStatus> requestedStatuses = new ArrayList<MilestoneStatus>();
        requestedStatuses.add(MilestoneStatus.OVERDUE);
        requestedStatuses.add(MilestoneStatus.UPCOMING);
        requestedStatuses.add(MilestoneStatus.COMPLETED);
        List<Milestone> res = impl.getAllInMonth(1, month, year, requestedStatuses);

        assertEquals("The list size should be correct", 3, res.size());
        assertEquals("'id' should be correct", id2, res.get(0).getId());
        assertEquals("'id' should be correct", id3, res.get(1).getId());
        assertEquals("'id' should be correct", id1, res.get(2).getId());

        requestedStatuses.remove(MilestoneStatus.UPCOMING);
        res = impl.getAllInMonth(1, month, year, requestedStatuses);
        assertEquals("The list size should be correct", 3, res.size());
        assertEquals("'id' should be correct", id2, res.get(0).getId());
        assertEquals("'id' should be correct", id3, res.get(1).getId());
        assertEquals("'id' should be correct", id1, res.get(2).getId());

        requestedStatuses.remove(MilestoneStatus.OVERDUE);
        res = impl.getAllInMonth(1, month, year, requestedStatuses);
        assertEquals("The list size should be correct", 1, res.size());
        assertEquals("'id' should be correct", id1, res.get(0).getId());
    }

    /**
     * Accuracy test for the method <code>getAllInMonth(long, int, int, List&lt;MilestoneStatus&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAllInMonth3() throws Exception {
        int year = 2011;
        int month = 12;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 2, 3, 4);

        Milestone milestone1 = createMilestone(1);
        milestone1.setDueDate(calendar.getTime());
        long id1 = impl.add(milestone1);

        Milestone milestone2 = createMilestone(2);
        milestone2.setCompleted(false);
        milestone2.setProjectId(1);
        milestone2.setDueDate(calendar.getTime());
        long id2 = impl.add(milestone2);

        Milestone milestone3 = createMilestone(3);
        milestone3.setCompleted(false);
        calendar.set(year, month - 1, 30, 3, 4, 5);
        milestone3.setDueDate(calendar.getTime());
        milestone3.setProjectId(1);
        long id3 = impl.add(milestone3);

        List<MilestoneStatus> requestedStatuses = new ArrayList<MilestoneStatus>();
        requestedStatuses.add(MilestoneStatus.OVERDUE);
        requestedStatuses.add(MilestoneStatus.UPCOMING);
        requestedStatuses.add(MilestoneStatus.COMPLETED);
        List<Milestone> res = impl.getAllInMonth(1, month, year, requestedStatuses);

        assertEquals("The list size should be correct", 3, res.size());
        assertEquals("'id' should be correct", id2, res.get(0).getId());
        assertEquals("'id' should be correct", id3, res.get(1).getId());
        assertEquals("'id' should be correct", id1, res.get(2).getId());

        requestedStatuses.remove(MilestoneStatus.UPCOMING);
        res = impl.getAllInMonth(1, month, year, requestedStatuses);
        assertEquals("The list size should be correct", 2, res.size());
        assertEquals("'id' should be correct", id2, res.get(0).getId());
        assertEquals("'id' should be correct", id1, res.get(1).getId());

        requestedStatuses.remove(MilestoneStatus.COMPLETED);
        res = impl.getAllInMonth(1, month, year, requestedStatuses);
        assertEquals("The list size should be correct", 1, res.size());
        assertEquals("'id' should be correct", id2, res.get(0).getId());
    }

    /**
     * Accuracy test for the method <code>getAllInMonth(long, int, int, List&lt;MilestoneStatus&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAllInMonth4() throws Exception {
        int year = 2011;
        int month = 12;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 2, 3, 4);

        Milestone milestone1 = createMilestone(1);
        milestone1.setDueDate(calendar.getTime());
        long id1 = impl.add(milestone1);

        Milestone milestone2 = createMilestone(2);
        milestone2.setCompleted(false);
        milestone2.setProjectId(1);
        calendar.set(year + 1, month - 1, 30, 3, 4, 5);
        milestone2.setDueDate(calendar.getTime());
        long id2 = impl.add(milestone2);

        Milestone milestone3 = createMilestone(3);
        milestone3.setCompleted(false);
        calendar.set(year, month - 1, 30, 3, 4, 5);
        milestone3.setDueDate(calendar.getTime());
        milestone3.setProjectId(1);
        long id3 = impl.add(milestone3);

        List<Milestone> res = impl.getAllInMonth(1, month, year, null);

        assertEquals("The list size should be correct", 2, res.size());
        assertEquals("'id' should be correct", id3, res.get(0).getId());
        assertEquals("'id' should be correct", id1, res.get(1).getId());

        res = impl.getAllInMonth(1, month, year + 1, null);
        assertEquals("The list size should be correct", 1, res.size());
        assertEquals("'id' should be correct", id2, res.get(0).getId());
    }

    /**
     * Accuracy test for the method <code>getAllInMonth(long, int, int, List&lt;MilestoneStatus&gt;)</code>.<br>
     * Expects no error occurs.
     *
     * @throws Exception
     *             to JUnit
     */
    @Test
    public void testGetAllInMonth5() throws Exception {
        List<Milestone> res = impl.getAllInMonth(1, 1, 2010, null);
        assertEquals("The list size should be correct", 0, res.size());
    }

    /**
     * <p>
     * Failure test for the method <code>getAllInMonth(long, int, int, List&lt;MilestoneStatus&gt;)</code>.<br>
     * parameter contains null element.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllInMonth_ParameterContainsNull() throws Exception {
        List<MilestoneStatus> requestedStatuses = new ArrayList<MilestoneStatus>();
        requestedStatuses.add(MilestoneStatus.OVERDUE);
        requestedStatuses.add(null);
        requestedStatuses.add(MilestoneStatus.COMPLETED);
        impl.getAllInMonth(1, 1, 2010, requestedStatuses);
    }

    /**
     * <p>
     * Failure test for the method <code>getAllInMonth(long, int, int, List&lt;MilestoneStatus&gt;)</code>.<br>
     * parameter contains duplicate element.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllInMonth_ParameterContainsDuplicate() throws Exception {
        List<MilestoneStatus> requestedStatuses = new ArrayList<MilestoneStatus>();
        requestedStatuses.add(MilestoneStatus.OVERDUE);
        requestedStatuses.add(MilestoneStatus.COMPLETED);
        requestedStatuses.add(MilestoneStatus.COMPLETED);
        impl.getAllInMonth(1, 1, 2010, requestedStatuses);
    }

    /**
     * <p>
     * Failure test for the method <code>getAllInMonth(long, int, int, List&lt;MilestoneStatus&gt;)</code>.<br>
     * month is too small.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllInMonth_MonthTooSmall() throws Exception {
        impl.getAllInMonth(1, 0, 2010, null);
    }

    /**
     * <p>
     * Failure test for the method <code>getAllInMonth(long, int, int, List&lt;MilestoneStatus&gt;)</code>.<br>
     * month is too big.<br>
     * Expect IllegalArgumentException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetAllInMonth_MonthTooBig() throws Exception {
        impl.getAllInMonth(1, 13, 2010, null);
    }

    /**
     * <p>
     * Failure test for the method <code>getAllInMonth(long, int, int, List&lt;MilestoneStatus&gt;)</code>.<br>
     * HibernateException occurs.<br>
     * Expect ProjectMilestoneManagementException.
     * </p>
     *
     * @throws Exception
     *             to JUnit
     */
    @Test(expected = ProjectMilestoneManagementException.class)
    public void testGetAllInMonth_HibernateExceptionOccurs() throws Exception {
        impl.setSessionFactory((SessionFactory) APP_CONTEXT_INVALID.getBean("sessionFactory"));

        impl.getAllInMonth(1, 12, 2010, null);
    }

    /**
     * Accuracy test for the method <code>getLogger()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetLogger() {
        impl = new HibernateMilestoneService();
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
        impl = new HibernateMilestoneService();
        assertNull("The initial value should be null", impl.getLogger());
        Log expect = LogManager.getLog("test");
        impl.setLogger(expect);
        assertEquals("The result should be same as", expect, impl.getLogger());
    }

    /**
     * Accuracy test for the method <code>getSessionFactory()</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testGetSessionFactory() {
        impl = new HibernateMilestoneService();
        assertNull("The initial value should be null", impl.getSessionFactory());
        SessionFactory expect = (SessionFactory) APP_CONTEXT.getBean("sessionFactory");
        impl.setSessionFactory(expect);
        assertEquals("The result should be same as", expect, impl.getSessionFactory());
    }

    /**
     * Accuracy test for the method <code>setSessionFactory(SessionFactory)</code>.<br>
     * Expects no error occurs.
     */
    @Test
    public void testSetSessionFactory() {
        impl = new HibernateMilestoneService();
        assertNull("The initial value should be null", impl.getSessionFactory());
        SessionFactory expect = (SessionFactory) APP_CONTEXT.getBean("sessionFactory");
        impl.setSessionFactory(expect);
        assertEquals("The result should be same as", expect, impl.getSessionFactory());
    }
}
