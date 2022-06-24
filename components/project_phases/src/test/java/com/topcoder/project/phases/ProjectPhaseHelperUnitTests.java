/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.util.Date;

import junit.framework.TestCase;

/**
 * Unit test case for <code>ProjectPhaseHelper</code>.
 *
 * @author littlebull
 * @version 2.0
 */
public class ProjectPhaseHelperUnitTests extends TestCase {
    /**
     * Represents the parameter name.
     */
    private static final String NAME = "parameter";

    /**
     * Represents the parameter value.
     */
    private static final String VALUE = "value";

    /**
     * Failure test of <code>checkObjectNotNull</code>.
     * <p>
     * With null object to check.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckObjectNotNullFailure() {
        try {
            ProjectPhaseHelper.checkObjectNotNull(null, NAME);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>checkObjectNotNull</code>.
     * <p>
     * With valid object to check.
     * </p>
     * <p>
     * Should pass the check.
     * </p>
     */
    public void testCheckObjectNotNullAccuracy() {
        ProjectPhaseHelper.checkObjectNotNull(VALUE, NAME);
        // pass
    }

    /**
     * Failure test of <code>checkLongNotNegative</code>.
     * <p>
     * With negative long value to check.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckLongNotNegativeFailure() {
        try {
            ProjectPhaseHelper.checkLongNotNegative(-1, NAME);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>checkLongNotNegative</code>.
     * <p>
     * With non negative long value to check.
     * </p>
     * <p>
     * Should pass the check.
     * </p>
     */
    public void testCheckLongNotNegativeAccuracy() {
        ProjectPhaseHelper.checkLongNotNegative(0, NAME);
        // pass
    }

    /**
     * Failure test of <code>checkDateNotBefore</code>.
     * <p>
     * With end date before start date.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckDateNotBeforeFailure() {
        try {
            ProjectPhaseHelper.checkDateNotBefore(new Date(2000000), new Date(5000000), "end date", "start date");
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>checkDateNotBefore</code>.
     * <p>
     * With end date equals start date.
     * </p>
     * <p>
     * Should pass the check.
     * </p>
     */
    public void testCheckDateNotBeforeAccuracy1() {
        ProjectPhaseHelper.checkDateNotBefore(new Date(2000000), new Date(2000000), "end date", "start date");
        // pass
    }

    /**
     * Accuracy test of <code>checkDateNotBefore</code>.
     * <p>
     * With end date after start date.
     * </p>
     * <p>
     * Should pass the check.
     * </p>
     */
    public void testCheckDateNotBeforeAccuracy2() {
        ProjectPhaseHelper.checkDateNotBefore(new Date(5000000), new Date(2000000), "end date", "start date");
        // pass
    }

    /**
     * Accuracy test of <code>checkDateNotBefore</code>.
     * <p>
     * With null end date.
     * </p>
     * <p>
     * Should pass the check.
     * </p>
     */
    public void testCheckDateNotBeforeAccuracy3() {
        ProjectPhaseHelper.checkDateNotBefore(null, new Date(2000000), "end date", "start date");
        // pass
    }

    /**
     * Accuracy test of <code>checkDateNotBefore</code>.
     * <p>
     * With null start date.
     * </p>
     * <p>
     * Should pass the check.
     * </p>
     */
    public void testCheckDateNotBeforeAccuracy4() {
        ProjectPhaseHelper.checkDateNotBefore(new Date(5000000), null, "end date", "start date");
        // pass
    }

    /**
     * Failure test of <code>checkPhaseNotSameInstance</code>.
     * <p>
     * With two same phase instance.
     * </p>
     * <p>
     * Should throw IllegalArgumentException.
     * </p>
     */
    public void testCheckPhaseNotSameInstanceFailure() {
        try {
            Project project = new Project(new Date(), new MyWorkdays());
            Phase phase1 = new Phase(project, 24);
            Phase phase2 = phase1;
            ProjectPhaseHelper.checkPhaseNotSameInstance(phase1, phase2);
            fail("Should throw IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // pass
        }
    }

    /**
     * Accuracy test of <code>checkPhaseNotSameInstance</code>.
     * <p>
     * With two different phase instance.
     * </p>
     * <p>
     * Should pass the check.
     * </p>
     */
    public void testCheckPhaseNotSameInstanceAccuracy() {
        Project project = new Project(new Date(), new MyWorkdays());
        Phase phase1 = new Phase(project, 24);
        Phase phase2 = new Phase(project, 24);
        ProjectPhaseHelper.checkPhaseNotSameInstance(phase1, phase2);
        // pass
    }
}
