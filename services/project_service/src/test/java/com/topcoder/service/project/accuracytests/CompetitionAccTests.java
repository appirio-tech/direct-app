/*
 * Copyright (C) 2008-2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.project.accuracytests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.service.project.Competition;
import com.topcoder.service.project.Project;
import com.topcoder.service.project.StudioCompetition;

/**
 * <p>
 * Unit test for <code>{@link Competition}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER, isv
 * @version 1.2
 * @since 1.0
 */
public class CompetitionAccTests extends TestCase {

    /**
     * <p>
     * Represents the <code>Competition</code> instance.
     * </p>
     */
    private Competition competition;

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

        competition = new StudioCompetition("C");
    }

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(CompetitionAccTests.class);
    }

    /**
     * <p>
     * Unit test for <code>Competition#Competition()</code> constructor.
     * </p>
     * <p>
     * Instance should be always created.
     * </p>
     */
    public void testCompetition_accuracy() {
        assertNotNull("Instance isn't created in setUp.", competition);
    }

    /**
     * <p>
     * Unit test for <code>Competition#getCompetitionId()</code> method.
     * </p>
     * <p>
     * It should return default value - null.
     * </p>
     */
    public void testGetCompetitionId_default() {
        assertNull("The competition id is default to null.", competition.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>Competition#setCompetitionId(Long)</code> method.
     * </p>
     * <p>
     * Null value is valid. Should set internally.
     * </p>
     */
    public void testSetCompetitionId_null() {
        // nothing happens when set null value.
        competition.setCompetitionId(null);
        assertNull("The competition id should be null.", competition.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>Competition#setCompetitionId(Long)</code> method.
     * </p>
     * <p>
     * Any Long value is valid.
     * </p>
     */
    public void testSetCompetitionId_Negative() {
        competition.setCompetitionId(-1L);

        assertEquals("Incorrect competition id.", new Long(-1L), competition.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>Competition#setCompetitionId(Long)</code> method.
     * </p>
     * <p>
     * Any Long value is valid.
     * </p>
     */
    public void testSetCompetitionId_Zero() {
        competition.setCompetitionId(0L);

        assertEquals("Incorrect competition id.", new Long(0L), competition.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>Competition#setCompetitionId(Long)</code> method.
     * </p>
     * <p>
     * Any Long value is valid.
     * </p>
     */
    public void testSetCompetitionId_Positive() {
        competition.setCompetitionId(1L);

        assertEquals("Incorrect competition id.", new Long(1L), competition.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>Competition#getProject()</code> method.
     * </p>
     * <p>
     * It should return default value - null.
     * </p>
     */
    public void testGetProject_default() {
        assertNull("The project is default to null.", competition.getProject());
    }

    /**
     * <p>
     * Unit test for <code>Competition#setProject(Project)</code> method.
     * </p>
     * <p>
     * Null value is valid. Should set internally.
     * </p>
     */
    public void testSetProject_null() {
        // nothing happens when set null value.
        competition.setProject(null);
        assertNull("The project should be null.", competition.getCompetitionId());
    }

    /**
     * <p>
     * Unit test for <code>Competition#setProject(Project)</code> method.
     * </p>
     * <p>
     * Should set the non-null project instance.
     * </p>
     */
    public void testSetProject_accuracy() {
        Project project = new Project();

        competition.setProject(project);

        assertSame("Incorrect project.", project, competition.getProject());
    }

    /**
     * <p>
     * Unit test for <code>Competition#hashCode()</code> method.
     * </p>
     * <p>
     * If the competition id is set, the hash code should be same as the competition id's hash code.
     * </p>
     */
    public void testHashCode_accuracy1() {
        competition.setCompetitionId(1L);

        assertEquals("Incorrect hash code.", new Long(1L).hashCode(), competition.hashCode());
    }

    /**
     * <p>
     * Unit test for <code>Competition#hashCode()</code> method.
     * </p>
     * <p>
     * The hash code is different when competition id is set or not.
     * </p>
     */
    public void testHashCode_accuracy2() {
        int hashCode1 = competition.hashCode();
        competition.setCompetitionId(1L);

        assertFalse("Incorrect hash code.", hashCode1 == competition.hashCode());
    }

    /**
     * <p>
     * Unit test for <code>Competition#equals(Object)</code> method.
     * </p>
     * <p>
     * If compared with null value, false will return.
     * </p>
     */
    public void testEquals_null() {
        assertFalse("Should return false.", competition.equals(null));
    }

    /**
     * <p>
     * Unit test for <code>Competition#equals(Object)</code> method.
     * </p>
     * <p>
     * If compared value is not the same type, false will return.
     * </p>
     */
    public void testEquals_NotSameType() {
        assertFalse("Should return false.", competition.equals(new Object()));
    }

    /**
     * <p>
     * Unit test for <code>Competition#equals(Object)</code> method.
     * </p>
     * <p>
     * If object compared with itself, always return true.
     * </p>
     */
    public void testEquals_SameObject() {
        assertTrue("Should return true.", competition.equals(competition));

        competition.setCompetitionId(1L);
        assertTrue("Should return true.", competition.equals(competition));
    }

    /**
     * <p>
     * Unit test for <code>Competition#equals(Object)</code> method.
     * </p>
     * <p>
     * Different competitions compared. If both competition ids are not set, should return false.
     * </p>
     */
    public void testEquals_DifferentObjects1() {
        Competition anotherCompetition = new StudioCompetition("C");
        assertFalse("Should return false.", competition.equals(anotherCompetition));
        assertFalse("Should return false.", anotherCompetition.equals(competition));
    }

    /**
     * <p>
     * Unit test for <code>Competition#equals(Object)</code> method.
     * </p>
     * <p>
     * Different competitions compared. If any competition ids are not set, should return false.
     * </p>
     */
    public void testEquals_DifferentObjects2() {
        Competition anotherCompetition = new StudioCompetition("C");
        anotherCompetition.setCompetitionId(1L);
        assertFalse("Should return false.", competition.equals(anotherCompetition));
        assertFalse("Should return false.", anotherCompetition.equals(competition));
    }

    /**
     * <p>
     * Unit test for <code>Competition#equals(Object)</code> method.
     * </p>
     * <p>
     * Different competitions compared. If both competition ids are set but not same, should return false.
     * </p>
     */
    public void testEquals_DifferentObjects3() {
        Competition anotherCompetition = new StudioCompetition("C");
        anotherCompetition.setCompetitionId(1L);
        competition.setCompetitionId(2L);
        assertFalse("Should return false.", competition.equals(anotherCompetition));
        assertFalse("Should return false.", anotherCompetition.equals(competition));
    }

    /**
     * <p>
     * Unit test for <code>Competition#equals(Object)</code> method.
     * </p>
     * <p>
     * Different competitions compared. If both competition ids are set but not same, should return false.
     * </p>
     */
    public void testEquals_DifferentObjects4() {
        Competition anotherCompetition = new StudioCompetition("C");
        anotherCompetition.setCompetitionId(1L);
        competition.setCompetitionId(1L);
        assertTrue("Should return true.", competition.equals(anotherCompetition));
        assertTrue("Should return true.", anotherCompetition.equals(competition));
    }
}
