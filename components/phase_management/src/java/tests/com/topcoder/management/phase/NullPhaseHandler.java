/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.project.phases.Phase;

/**
 * A simple phase handler that allows all operations to be performed.
 *
 * @author RachaelLCook
 * @version 1.0
 */

public class NullPhaseHandler implements PhaseHandler {
    /**
     * Used by some subclasses to track whether or not the correct method invocation was made.
     */
    private boolean passed = false;

    public NullPhaseHandler() {
    	// do nothing
    }

    /**
     * Returns whether or not the test should pass.
     *
     * @return whether or not the test should pass
     */
    boolean getPassed() {
        return passed;
    }

    /**
     * Sets whether or not the test should pass.
     *
     * @param passed whether or not the test should pass
     */
    void setPassed(boolean passed) {
        this.passed = passed;
    }

    /**
     * Always returns <code>true</code>.
     *
     * @param phase the phaes to perform
     * @return <code>true</code>
     */
    public boolean canPerform(Phase phase) { return true; }

    /**
     * Does nothing.
     *
     * @param phase the phase
     * @param operator the operator
     */
    public void perform(Phase phase, String operator) { }
}
