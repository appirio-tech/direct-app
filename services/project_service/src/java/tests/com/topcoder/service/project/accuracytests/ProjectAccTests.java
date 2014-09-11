/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.accuracytests;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.project.Competition;
import com.topcoder.service.project.Project;

/**
 * <p>
 * Unit test for <code>{@link Project}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public class ProjectAccTests extends TestCase {

    /**
     * <p>
     * Represents the <code>Project</code> instance.
     * </p>
     */
    private Project project;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(ProjectAccTests.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        project = new Project();
    }

    /**
     * <p>
     * Unit test for <code>Project#Project()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testProject_accuracy() {
        assertNotNull("Instance isn't created in setUp.", project);
    }

    /**
     * <p>
     * Unit test for <code>Project#getUserId()</code> method.
     * </p>
     * <p>
     * It should return default value - null.
     * </p>
     */
    public void testGetUserId_default() {
        assertEquals("The user id is default to zero.", 0, project.getUserId());
    }

    /**
     * <p>
     * Unit test for <code>Project#setUserId(Long)</code> method.
     * </p>
     * <p>
     * Any Long value is valid.
     * </p>
     */
    public void testSetUserId_Negative() {
        project.setUserId(-1L);

        assertEquals("Incorrect user id.", -1L, project.getUserId());
    }

    /**
     * <p>
     * Unit test for <code>Project#setUserId(Long)</code> method.
     * </p>
     * <p>
     * Any Long value is valid.
     * </p>
     */
    public void testSetUserId_Zero() {
        project.setUserId(0L);

        assertEquals("Incorrect user id.", 0L, project.getUserId());
    }

    /**
     * <p>
     * Unit test for <code>Project#setUserId(Long)</code> method.
     * </p>
     * <p>
     * Any Long value is valid.
     * </p>
     */
    public void testSetUserId_Positive() {
        project.setUserId(1L);

        assertEquals("Incorrect user id.", 1L, project.getUserId());
    }

    /**
     * <p>
     * Unit test for <code>Project#getCreateDate()</code> method.
     * </p>
     * <p>
     * Should return null, by default.
     * </p>
     */
    public void testGetCreateDate_default() {
        assertNull("Null should return.", project.getCreateDate());
    }

    /**
     * <p>
     * Unit test for <code>Project#setCreateDate(Date)</code> method.
     * </p>
     * <p>
     * Null is valid to set.
     * </p>
     */
    public void testSetCreateDate_null() {
        project.setCreateDate(null);
        assertNull("Null should return.", project.getCreateDate());
    }

    /**
     * <p>
     * Unit test for <code>Project#setCreateDate(Date)</code> method.
     * </p>
     * <p>
     * Non-Null Date is valid to set.
     * </p>
     */
    public void testSetCreateDate_NonNull() {
        Date date = new Date();
        project.setCreateDate(date);
        assertSame("Should return the new set date.", date, project.getCreateDate());
    }

    /**
     * <p>
     * Unit test for <code>Project#getModifyDate()</code> method.
     * </p>
     * <p>
     * Should return null, by default.
     * </p>
     */
    public void testGetModifyDate_default() {
        assertNull("Null should return.", project.getModifyDate());
    }

    /**
     * <p>
     * Unit test for <code>Project#setModifyDate(Date)</code> method.
     * </p>
     * <p>
     * Null is valid to set.
     * </p>
     */
    public void testSetModifyDate_null() {
        project.setModifyDate(null);
        assertNull("Null should return.", project.getModifyDate());
    }

    /**
     * <p>
     * Unit test for <code>Project#setModifyDate(Date)</code> method.
     * </p>
     * <p>
     * Non-Null Date is valid to set.
     * </p>
     */
    public void testSetModifyDate_NonNull() {
        Date date = new Date();
        project.setModifyDate(date);
        assertSame("Should return the new set date.", date, project.getModifyDate());
    }

    /**
     * <p>
     * Unit test for <code>Project#getCompetitions()</code> method.
     * </p>
     * <p>
     * Should return null, by default.
     * </p>
     */
    public void testGetCompetitions_default() {
        assertNull("Null should return.", project.getCompetitions());
    }

    /**
     * <p>
     * Unit test for <code>Project#setCompetitions(Set)</code> method.
     * </p>
     * <p>
     * Null is valid to set.
     * </p>
     */
    public void testSetCompetitions_null() {
        project.setCompetitions(null);
        assertNull("Null should return.", project.getCompetitions());
    }

    /**
     * <p>
     * Unit test for <code>Project#setCompetitions(Set)</code> method.
     * </p>
     * <p>
     * Non-Null Date is valid to set.
     * </p>
     */
    public void testSetCompetitions_NonNull() {
        Set<Competition> competitions = new HashSet<Competition>();
        project.setCompetitions(competitions);
        assertSame("Should return the competitions set.", competitions, project.getCompetitions());
    }
}
