/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import junit.framework.JUnit4TestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.topcoder.direct.services.project.task.EntityNotFoundException;
import com.topcoder.direct.services.project.task.PermissionException;
import com.topcoder.direct.services.project.task.PersistenceException;
import com.topcoder.direct.services.project.task.TaskManagementConfigurationException;
import com.topcoder.direct.services.project.task.model.ContestDTO;
import com.topcoder.direct.services.project.task.model.UserDTO;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserService;
import com.topcoder.util.log.Log;

/**
 * <p>
 * All unit tests for <code>ServiceHelper</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 *
 */
public class ServiceHelperTests extends BaseUnitTests {

    /**
     * <p>
     * Represents the logger for unit tests.
     * </p>
     */
    private Log log;

    /**
     * <p>
     * Represents the entity manager for unit tests.
     * </p>
     */
    private EntityManager entityManager;

    /**
     * <p>
     * Adapter for earlier versions of JUnit.
     * </p>
     *
     * @return a test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(ServiceHelperTests.class);
    }


    /**
     * <p>
     * Sets up the test environment.
     * </p>
     * @throws Exception to JUnit.
     */
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        log = (Log) super.getApplicationContext().getBean("logBean");
        entityManager = super.getEntityManager();
    }

    /**
     * <p>
     * Accuracy test for the method <code>logEntrance(Logger logger, String signature, String[] paramNames,
     * Object[] params)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void testLogEntrance1() {
        ServiceHelper.logEntrance(log, "signature", new String[] {"p1", "p2"}, new Object[] {"v1", new Integer(12)});
    }

    /**
     * <p>
     * Accuracy test for the method <code>logEntrance(Logger logger, String signature, String[] paramNames,
     *  Object[] params)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void testLogEntrance2() {
        ServiceHelper.logEntrance(log, "signature", null, null);
    }

    /**
     * <p>
     * Accuracy test for the method <code>logEntrance(Logger logger, String signature, String[] paramNames,
     *  Object[] params)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void testLogEntrance3() {
        ServiceHelper.logEntrance(null, "signature", null, null);
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>logExit(Logger logger, String signature)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void testLogExit1() {
        ServiceHelper.logExit(log, "signature");
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>logExit(Logger logger, String signature, T obj)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void testLogExit2() {
        ServiceHelper.logExit(log, "signature", "return value");
    }

    /**
     * <p>
     * Accuracy test for the method
     * <code>logExit(Logger logger, String signature, T obj, boolean lazyFetch)</code>.<br>
     * The result should be correct.
     * </p>
     */
    @Test
    public void testLogExit3() {
        ServiceHelper.logExit(log, "signature", "abc", false);
    }

    /**
     * <p>
     * Accuracy test for the method <code>logException(Logger logger, String signature, T exception)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testLogException1() throws Exception {
        Throwable e = new Exception("Test");

        ServiceHelper.logException(log, "signature", e);
    }

    /**
     * <p>
     * Accuracy test for the method <code>logException(Logger logger, String signature, T exception)</code>.<br>
     * The result should be correct.
     * </p>
     *
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testLogException2() throws Exception {
        log = null;
        Throwable e = new Exception("Test");

        ServiceHelper.logException(log, "signature", e);
    }


    /**
     * <p>
     * Tests failure of <code>checkState(boolean isInvalid, String message)</code> method with
     * <code>isInvalid</code> is <code>true</code>.<br>
     * <code>TaskManagementConfigurationException</code> is expected.
     * </p>
     */
    @Test(expected = TaskManagementConfigurationException.class)
    public void testCheckState_Invalid() {
        ServiceHelper.checkState(true, "message");
    }

    /**
     * <p>
     * Tests accuracy of <code>checkState(boolean isInvalid, String message)</code> method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void testCheckState() {
        ServiceHelper.checkState(false, "message");

        // Good
    }

    /**
     * <p>
     * Tests accuracy of <code>checkNull(Logger logger, String signature, Object value, String name)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void testCheckNull() {
        Object value = new Object();

        ServiceHelper.checkNull(log, "signature", value, "name");

        // Good
    }

    /**
     * <p>
     * Tests failure of <code>checkNullOrEmpty(Logger logger, String signature, String, String)</code> method
     * with <code>value</code> is <code>null</code>.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNullOrEmpty_Null() {
        ServiceHelper.checkNullOrEmpty(log, "signature", null, "name");
    }

    /**
     * <p>
     * Tests failure of <code>checkNullOrEmpty(Logger logger, String signature, String, String)</code> method
     * with <code>value</code> is empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNullOrEmpty_Empty() {
        ServiceHelper.checkNullOrEmpty(log, "signature", "", "name");
    }

    /**
     * <p>
     * Tests failure of <code>checkNullOrEmpty(Logger logger, String signature, String, String)</code> method
     * with <code>value</code> is trimmed empty.<br>
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNullOrEmpty_TrimmedEmpty() {
        ServiceHelper.checkNullOrEmpty(log, "signature", " \t ", "name");
    }

    /**
     * <p>
     * Tests accuracy of <code>checkNullOrEmpty(Logger logger, String signature, String, String)</code>
     * method.<br>
     * Result should be correct.
     * </p>
     */
    @Test
    public void testCheckNullOrEmpty() {
        ServiceHelper.checkNullOrEmpty(log, "signature", "str", "name");

        // Good
    }


    /**
     * <p>
     * Tests the method: {@link ServiceHelper#queryListResult(Log, String, EntityManager, String, Object[])}.
     * </p>
     * Accuracy test to check if the list result can be retrieved.
     */
    @Test
    public void testQueryListResult() {
        super.createUsers();
        String query = "select u from UserDTO u";
        List<UserDTO> users = ServiceHelper.queryListResult(log, "test", entityManager, query, null);
        assertTrue("users can be retrieve.", users.size() > 0);
    }

    /**
     * <p>
     * Tests the method: {@link ServiceHelper#querySingleResult(Log, String, EntityManager, String, Object[], Class)}.
     * </p>
     * Null should be return if the entity not found.
     * @throws Exception to JUnit.
     */
    @Test
    public void testQuerySingleResultNotFound() throws Exception {
        String query = "select u from UserDTO u where u.userId = ?1";
        UserDTO dto = ServiceHelper.querySingleResult(
            log, "test", entityManager, query, new Object[] {1L}, UserDTO.class);
        assertNull("should be null.", dto);
    }

    /**
     * <p>
     * Tests the method: {@link ServiceHelper#querySingleResult(Log, String, EntityManager, String, Object[], Class)}.
     * </p>
     * Exception should be thrown if more than one result can get.
     * @throws Exception to JUnit.
     */
    @Test (expected = PersistenceException.class)
    public void testQuerySingleResultMoreThanOne() throws Exception {
        super.createUsers();
        String query = "select u from UserDTO u";
        ServiceHelper.querySingleResult(
            log, "test", entityManager, query, null, UserDTO.class);
    }

    /**
     * <p>
     * Tests the method: {@link ServiceHelper#querySingleResult(Log, String, EntityManager, String, Object[], Class)}.
     * </p>
     * If the type is invalid, exception should be thrown.
     * @throws Exception to JUnit.
     */
    @Test (expected = PersistenceException.class)
    public void testQuerySingleResultInvalidType() throws Exception {
        User user = super.createUsers().get(0);
        String query = "select u from UserDTO u where u.userId = ?1";
        ServiceHelper.querySingleResult(
            log, "test", entityManager, query, new Object[] {user.getUserId()}, String.class);
    }

    /**
     * <p>
     * Tests the method: {@link ServiceHelper#querySingleResult(Log, String, EntityManager, String, Object[], Class)}.
     * </p>
     *
     * Accuracy test to check if the result can be retrieved.
     * @throws Exception to JUnit.
     */
    @Test
    public void testQuerySingleResultAccuracy() throws Exception {
        User user = super.createUsers().get(0);
        String query = "select u from UserDTO u where u.userId = ?1";
        UserDTO dto = ServiceHelper.querySingleResult(
            log, "test", entityManager, query, new Object[] {user.getUserId()}, UserDTO.class);
        assertEquals("should has the same handle.", user.getHandle(), dto.getHandle());
    }

    /**
     * <p>
     * Tests the method: {@link ServiceHelper#queryRecordNum(Log, String, EntityManager, String, Object[])}.
     * </p>
     * Accuracy to check if the records are correct.
     * @throws Exception to JUnit.
     */
    @Test
    public void testQueryRecordNum() throws Exception {
        String query = "select count(t) from Task t";
        int result = ServiceHelper.queryRecordNum(log, "test", entityManager, query, null);
        assertEquals("should be zero.", 0, result);
    }

    /**
     * <p>
     * Tests the  method: {@link ServiceHelper#existsProject(Log, String, EntityManager, long)}.
     * </p>
     * Accuracy to check if the project exists.
     * @throws Exception to JUnit.
     */
    @Test
    public void testExistsProject() throws Exception {
        ContestDTO contest = new ContestDTO();
        contest.setContestId(123);
        contest.setContestName("abc");
        persist(contest);
        ServiceHelper.existsProject(log, "test", entityManager, contest.getContestId());
        // good
    }

    /**
     * <p>
     * Tests the  method: {@link ServiceHelper#existsProject(Log, String, EntityManager, long)}.
     * </p>
     * Exception should throw if no projects.
     * @throws Exception to JUnit.
     */
    @Test (expected = EntityNotFoundException.class)
    public void testExistsProjectNotFound() throws Exception {
        ServiceHelper.existsProject(log, "test", entityManager, 123);
        // good
    }

    /**
     * <p>
     * Tests the method: {@link ServiceHelper#getUserHandle(Log, String, UserService, long)}.
     * </p>
     * If the user does not exist, exception should be thrown.
     * @throws Exception to JUnit.
     */
    @Test (expected = PermissionException.class)
    public void testGetUserHandleNotExist() throws Exception {
        UserService userService = (UserService) super.getApplicationContext().getBean("mockUserService");
        ServiceHelper.getUserHandle(log, "test", userService, 888);
    }

    /**
     * <p>
     * Tests the method: {@link ServiceHelper#getUserHandle(Log, String, UserService, long)}.
     * </p>
     * Accuracy test to check if the user handle can be retrieved.
     * @throws Exception to JUnit.
     */
    @Test
    public void testGetUserHandle() throws Exception {
        User user = super.createUsers().get(0);
        UserService userService = (UserService) super.getApplicationContext().getBean("mockUserService");
        String handle = ServiceHelper.getUserHandle(log, "test", userService, user.getUserId());
        assertEquals("should be equal.", user.getHandle(), handle);
    }

    /**
     * <p>
     * Tests the method: {@link ServiceHelper#flush(Log, String, EntityManager)}.
     * </p>
     * @throws Exception to JUnit.
     */
    @Test
    public void testFlush() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setHandle("abc");
        userDTO.setUserId(123);
        entityManager.getTransaction().begin();
        entityManager.persist(userDTO);
        // should flush to database
        ServiceHelper.flush(log, "testFlush", entityManager);
        entityManager.getTransaction().commit();
    }

}
