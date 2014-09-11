/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.lang.reflect.Field;
import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit test case for <code>Project</code>. This part test the basic methods, including: <code>constructor</code>,
 * <code>getProject</code>, <code>getLength</code>, <code>setLength</code>, <code>getId</code>,
 * <code>setId</code>, <code>getPhaseType</code>, <code>setPhaseType</code>, <code>getPhaseStatus</code>,
 * <code>setPhaseStatus</code>, <code>getFixedStartDate</code>, <code>setFixedStartDate</code>,
 * <code>getScheduledStartDate</code>, <code>setScheduledStartDate</code>, <code>getScheduledEndDate</code>,
 * <code>setScheduledEndDate</code>, <code>getActualStartDate</code>, <code>setActualStartDate</code>,
 * <code>getActualEndDate</code>, <code>setActualEndDate</code>, <code>setCachedStartDate</code>,
 * <code>setCachedEndDate</code> and <code>notifyChange</code>. Other methods test please refer to
 * <code>PhaseDependencyUnitTests</code> and <code>PhaseDateUnitTests</code>.
 *
 * @author littlebull
 * @version 2.0
 */
public class PhaseBasicUnitTests extends TestCase {
    /**
     * Represents the length of phase.
     */
    private static final long LENGTH = 24 * 3600 * 1000L;

    /**
     * Represents the <code>Project</code> instance used for testing. Initialized in <code>setUp</code> method.
     */
    private Project project;

    /**
     * A <code>Phase</code> instance used for testing. Initialized in <code>setUp</code> method.
     */
    private Phase phase;

    /**
     * Set up the test environment.
     */
    protected void setUp() {
        project = new Project(new Date(), new MyWorkdays());
        project.setChanged(false);
        phase = new Phase(project, LENGTH);
    }

    /**
     * Failure test of constructor.
     * <p>
     * With null project.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testConstructorFailureWithNullProject() {
        try {
            new Phase(null, LENGTH);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Failure test of constructor.
     * <p>
     * With negative length.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testConstructorFailureWithNegativeLength() {
        try {
            new Phase(project, -1);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of constructor.
     * <p>
     * Should create the instance successfully.
     * </p>
     */
    public void testConstructorAccuracy() {
        assertNotNull("Should create the instance successfully.", phase);
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>getProject</code>.
     * <p>
     * Should return the proper project.
     * </p>
     */
    public void testGetProjectAccuracy() {
        assertEquals("Should return the proper project.", project, phase.getProject());
    }

    /**
     * Accuracy test of <code>getLength</code>.
     * <p>
     * Should return the proper length.
     * </p>
     */
    public void testGetLengthAccuracy() {
        assertEquals("Should return the proper length.", LENGTH, phase.getLength());
    }

    /**
     * Failure test of <code>setLength</code>.
     * <p>
     * With negative length.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetLengthFailureWithNegativeLength() {
        try {
            phase.setLength(-1);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>setLength</code>.
     * <p>
     * With zero length.
     * </p>
     * <p>
     * Should set length to zero.
     * </p>
     */
    public void testSetLengthAccuracyWithZero() {
        phase.setLength(0);
        assertEquals("Should set length to zero.", 0, phase.getLength());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setLength</code>.
     * <p>
     * With positive length.
     * </p>
     * <p>
     * Should set length to positive value.
     * </p>
     */
    public void testSetLengthAccuracyWithPositive() {
        phase.setLength(1);
        assertEquals("Should set length to positive value.", 1, phase.getLength());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>getId</code>.
     * <p>
     * With default phase id.
     * </p>
     * <p>
     * Should return the proper id.
     * </p>
     */
    public void testGetIdAccuracyWithDefault() {
        assertEquals("Should return the proper id.", 0, phase.getId());
    }

    /**
     * Failure test of <code>setId</code>.
     * <p>
     * With negative id.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetIdFailureWithNegativeId() {
        try {
            phase.setId(-1);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>setId</code>.
     * <p>
     * With zero id.
     * </p>
     * <p>
     * Should set the id to zero.
     * </p>
     */
    public void testSetIdAccuracyZero() {
        phase.setId(0);
        assertEquals("Should set the id to zero.", 0, phase.getId());
    }

    /**
     * Accuracy test of <code>setId</code>.
     * <p>
     * With positive id.
     * </p>
     * <p>
     * Should set the id to positive.
     * </p>
     */
    public void testSetIdAccuracyPositive() {
        phase.setId(1);
        assertEquals("Should set the id.", 1, phase.getId());
    }

    /**
     * Accuracy test of <code>getPhaseType</code>.
     * <p>
     * With default phase type.
     * </p>
     * <p>
     * Should return null.
     * </p>
     */
    public void testGetPhaseTypeAccuracyWithDefault() {
        assertNull("Should return null.", phase.getPhaseType());
    }

    /**
     * Accuracy test of <code>setPhaseType</code>.
     * <p>
     * With null phase type.
     * </p>
     * <p>
     * Should set the phase type to null.
     * </p>
     */
    public void testSetPhaseTypeAccuracyWithNull() {
        phase.setPhaseType(null);
        assertNull("Should return null.", phase.getPhaseType());
    }

    /**
     * Accuracy test of <code>setPhaseType</code>.
     * <p>
     * With not null phase type.
     * </p>
     * <p>
     * Should set the phase type.
     * </p>
     */
    public void testSetPhaseTypeAccuracyWithNotNull() {
        phase.setPhaseType(new PhaseType(0, "type"));
        assertNotNull("Should set the phase type.", phase.getPhaseType());
    }

    /**
     * Accuracy test of <code>getPhaseStatus</code>.
     * <p>
     * With default phase type.
     * </p>
     * <p>
     * Should return null.
     * </p>
     */
    public void testGetPhaseStatusAccuracyWithDefault() {
        assertNull("Should return null.", phase.getPhaseStatus());
    }

    /**
     * Accuracy test of <code>setPhaseStatus</code>.
     * <p>
     * With null phase type.
     * </p>
     * <p>
     * Should set the phase type to null.
     * </p>
     */
    public void testSetPhaseStatusAccuracyWithNull() {
        phase.setPhaseStatus(null);
        assertNull("Should return null.", phase.getPhaseStatus());
    }

    /**
     * Accuracy test of <code>setPhaseStatus</code>.
     * <p>
     * With not null phase type.
     * </p>
     * <p>
     * Should set the phase type.
     * </p>
     */
    public void testSetPhaseStatusAccuracyWithNotNull() {
        phase.setPhaseStatus(new PhaseStatus(0, "status"));
        assertNotNull("Should set the phase type.", phase.getPhaseStatus());
    }

    /**
     * Accuracy test of <code>getFixedStartDate</code>.
     * <p>
     * With default value.
     * </p>
     * <p>
     * Should return null.
     * </p>
     */
    public void testGetFixedStartDateAccuracyDefaultValue() {
        assertNull("Should return null.", phase.getFixedStartDate());
    }

    /**
     * Accuracy test of <code>setFixedStartDate</code>.
     * <p>
     * With null fixed start date.
     * </p>
     * <p>
     * Should set fixed start date to null.
     * </p>
     */
    public void testSetFixedStartDateAccuracyWithNull() {
        phase.setFixedStartDate(null);
        assertNull("Should set fixed start date to null.", phase.getFixedStartDate());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setFixedStartDate</code>.
     * <p>
     * With not null fixed start date.
     * </p>
     * <p>
     * Should set fixed start date.
     * </p>
     */
    public void testSetFixedStartDateAccuracyWithNotNull() {
        phase.setFixedStartDate(new Date());
        assertNotNull("Should set fixed start date.", phase.getFixedStartDate());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>getScheduledStartDate</code>.
     * <p>
     * With default value.
     * </p>
     * <p>
     * Should return null.
     * </p>
     */
    public void testGetScheduledStartDateAccuracyDefaultValue() {
        assertNull("Should return null.", phase.getScheduledStartDate());
    }

    /**
     * Accuracy test of <code>setScheduledStartDate</code>.
     * <p>
     * With null scheduled start date.
     * </p>
     * <p>
     * Should set scheduled start date to null.
     * </p>
     */
    public void testSetScheduledStartDateAccuracyWithNull() {
        phase.setScheduledStartDate(null);
        assertNull("Should set fixed start date to null.", phase.getScheduledStartDate());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setScheduledStartDate</code>.
     * <p>
     * With not null valid scheduled start date.
     * </p>
     * <p>
     * Should set scheduled start date.
     * </p>
     */
    public void testSetScheduledStartDateAccuracyWithNotNull() {
        phase.setScheduledStartDate(new Date());
        assertNotNull("Should set fixed start date.", phase.getScheduledStartDate());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Failure test of <code>setScheduledStartDate</code>.
     * <p>
     * With scheduled start date after scheduled end date.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetScheduledStartDateFailure() {
        try {
            phase.setScheduledEndDate(new Date(2000000));
            phase.setScheduledStartDate(new Date(5000000));
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>getScheduledEndDate</code>.
     * <p>
     * With default value.
     * </p>
     * <p>
     * Should return null.
     * </p>
     */
    public void testGetScheduledEndDateAccuracyDefaultValue() {
        assertNull("Should return null.", phase.getScheduledEndDate());
    }

    /**
     * Accuracy test of <code>setScheduledEndDate</code>.
     * <p>
     * With null scheduled end date.
     * </p>
     * <p>
     * Should set scheduled end date to null.
     * </p>
     */
    public void testSetScheduledEndDateAccuracyWithNull() {
        phase.setScheduledEndDate(null);
        assertNull("Should set fixed end date to null.", phase.getScheduledEndDate());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setScheduledEndDate</code>.
     * <p>
     * With not null valid scheduled end date.
     * </p>
     * <p>
     * Should set scheduled end date.
     * </p>
     */
    public void testSetScheduledEndDateAccuracyWithNotNull() {
        phase.setScheduledEndDate(new Date());
        assertNotNull("Should set fixed end date.", phase.getScheduledEndDate());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Failure test of <code>setScheduledEndDate</code>.
     * <p>
     * With scheduled end date before scheduled start date.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetScheduledEndDateFailure() {
        try {
            phase.setScheduledStartDate(new Date(5000000));
            phase.setScheduledEndDate(new Date(2000000));
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>getActualStartDate</code>.
     * <p>
     * With default value.
     * </p>
     * <p>
     * Should return null.
     * </p>
     */
    public void testGetActualStartDateAccuracyDefaultValue() {
        assertNull("Should return null.", phase.getActualStartDate());
    }

    /**
     * Accuracy test of <code>setActualStartDate</code>.
     * <p>
     * With null actual start date.
     * </p>
     * <p>
     * Should set actual start date to null.
     * </p>
     */
    public void testSetActualStartDateAccuracyWithNull() {
        phase.setActualStartDate(null);
        assertNull("Should set fixed start date to null.", phase.getActualStartDate());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setActualStartDate</code>.
     * <p>
     * With not null valid actual start date.
     * </p>
     * <p>
     * Should set actual start date.
     * </p>
     */
    public void testSetActualStartDateAccuracyWithNotNull() {
        phase.setActualStartDate(new Date());
        assertNotNull("Should set fixed start date.", phase.getActualStartDate());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Failure test of <code>setActualStartDate</code>.
     * <p>
     * With actual start date after actual end date.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetActualStartDateFailure() {
        try {
            phase.setActualEndDate(new Date(2000000));
            phase.setActualStartDate(new Date(5000000));
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>getActualEndDate</code>.
     * <p>
     * With default value.
     * </p>
     * <p>
     * Should return null.
     * </p>
     */
    public void testGetActualEndDateAccuracyDefaultValue() {
        assertNull("Should return null.", phase.getActualEndDate());
    }

    /**
     * Accuracy test of <code>setActualEndDate</code>.
     * <p>
     * With null actual end date.
     * </p>
     * <p>
     * Should set actual end date to null.
     * </p>
     */
    public void testSetActualEndDateAccuracyWithNull() {
        phase.setActualEndDate(null);
        assertNull("Should set fixed end date to null.", phase.getActualEndDate());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setActualEndDate</code>.
     * <p>
     * With not null valid actual end date.
     * </p>
     * <p>
     * Should set actual end date.
     * </p>
     */
    public void testSetActualEndDateAccuracyWithNotNull() {
        phase.setActualEndDate(new Date());
        assertNotNull("Should set fixed end date.", phase.getActualEndDate());
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Failure test of <code>setActualEndDate</code>.
     * <p>
     * With actual end date before actual start date.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testSetActualEndDateFailure() {
        try {
            phase.setActualStartDate(new Date(5000000));
            phase.setActualEndDate(new Date(2000000));
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>notifyChange</code>.
     * <p>
     * Should set the changed flag to true.
     * </p>
     */
    public void testNotifyChangeAccuracy() {
        phase.notifyChange();
        assertTrue("Should set the changed flag to true.", project.isChanged());
    }

    /**
     * Accuracy test of <code>setCachedStartDate</code>.
     * <p>
     * With null cached start date.
     * </p>
     * <p>
     * Should set cached start date to null.
     * </p>
     */
    public void testSetCachedStartDateAccuracyWithNull() {
        phase.setCachedStartDate(null);
        checkFieldValue("cachedStartDate", null);
    }

    /**
     * Accuracy test of <code>setCachedStartDate</code>.
     * <p>
     * With not null valid cached start date.
     * </p>
     * <p>
     * Should set cached start date.
     * </p>
     */
    public void testSetCachedStartDateAccuracyWithNotNull() {
        Date date = new Date();
        phase.setCachedStartDate(date);
        checkFieldValue("cachedStartDate", date);
    }

    /**
     * Accuracy test of <code>setCachedEndDate</code>.
     * <p>
     * With null cached end date.
     * </p>
     * <p>
     * Should set cached end date to null.
     * </p>
     */
    public void testSetCachedEndDateAccuracyWithNull() {
        phase.setCachedEndDate(null);
        checkFieldValue("cachedEndDate", null);
    }

    /**
     * Accuracy test of <code>setCachedEndDate</code>.
     * <p>
     * With not null valid cached end date.
     * </p>
     * <p>
     * Should set cached end date.
     * </p>
     */
    public void testSetCachedEndDateAccuracyWithNotNull() {
        Date date = new Date();
        phase.setCachedEndDate(date);
        checkFieldValue("cachedEndDate", date);
    }

    /**
     * Check the specified field's value is the same as expected.
     *
     * @param name
     *            the name of the field.
     * @param expected
     *            the expected field value.
     */
    private void checkFieldValue(String name, Object expected) {
        try {
            Field field = Phase.class.getDeclaredField(name);
            field.setAccessible(true);
            assertEquals("The value of " + name + " is invalid.", expected, field.get(phase));
        } catch (NoSuchFieldException e) {
            // Just ignore
        } catch (IllegalAccessException e) {
            // Just ignore
        }
    }
}
