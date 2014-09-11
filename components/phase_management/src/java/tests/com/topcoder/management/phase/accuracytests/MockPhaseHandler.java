/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 */
package com.topcoder.management.phase.accuracytests;

import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.project.phases.Phase;

/**
 * <p>
 * A mock implementation of PhaseHandler used only for test purpose.
 * </p>
 * @author Thinfox
 * @version 1.0
 */
public class MockPhaseHandler implements PhaseHandler {
    /**
     * Represents whether operations can be performed for all phases.
     */
    private final boolean canPerform;

    /**
     * Creates a mock instance with the given canPerform flag.
     * @param canPerform whether operations can be performed for all phases.
     */
    public MockPhaseHandler(boolean canPerform) {
        this.canPerform = canPerform;
    }

    /**
     * The handler will provide the decision of whether the start, end or cancel operations can be performed for the
     * input phase.
     * @param phase phase to test
     * @return false always.
     */
    public boolean canPerform(Phase phase) {
        return this.canPerform;
    }

    /**
     * Extra logic to be used when the phase is starting, ending or canceling. This will be called by the PhaseManager
     * implementation at phase change time to perform additional tasks that are due when the input phase has changed
     * (moved to the next phase) Do nothing here.
     * @param phase phase to apply an operation to
     * @param operator operator applying
     */
    public void perform(Phase phase, String operator) {
        // do nothing.
    }
}