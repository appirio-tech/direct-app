/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.phase;

import com.topcoder.project.phases.Phase;
/**
 * <p> Optional, pluggable phase handling mechanism that can be configured per phase type/operation. The handler
 * will provide the decision of whether the start, end or cancel operations can be performed as well as extra logic
 * when the phase is starting, ending or canceling. Notice that the status and timestamp persistence is still
 * handled by the component. </p>
 *
 * <p>When a user wants a phase to be changed, the manager will check if a handler for that phase (i.e. for that
 * PhaseType and for the operation being done such as START, END, or CANCEL a phase) exists and will then use the
 * handler to make decisions about what to do, as well as use the handler for additional work if phase can be
 * changed.</p>
 *
 * We have the following invocation scenarios from a PhaseManager implementation with reference to phase handlers:
 * <ul>
 *   <li>{@link PhaseManager#canStart PhaseManager.canStart()} - if a handler exists in the registry then the
 *        manager invokes the handler's {@link #canPerform canPerform()} method to see if we can change phase
 *        (i.e. start a new phase) and if yes, then we use {@link #perform handler.perform()} for any additional
 *        tasks to be performed.</li>
 *
 *   <li>{@link PhaseManager#canEnd PhaseManager.canEnd()} - if a handler exists in the registry then the
 *        manager invokes the handler's {@link #canPerform canPerform()} method to see if we can change phase
 *        (i.e. end current phase) and if yes, then we use {@link #perform handler.perform()} for any additional
 *        tasks to be performed.</li>
 *
 *   <li>{@link PhaseManager#canCancel PhaseManager.canCancel()} - if a handler exists in the registry then the
 *        manager invokes the handler's {@link #canPerform canPerform()} method to see if we can change phase
 *        (i.e. cancel current phase) and if yes, then we use {@link #perform handler.perform()} for any additional
 *        tasks to be performed.</li>
 *
 *   <li>{@link PhaseManager#start PhaseManager.start()} - if a handler exists call {@link #perform
 *       handler.perform()} before performing the associated persistence operations</li>
 *
 *   <li>{@link PhaseManager#end PhaseManager.end()} - if a handler exists call {@link #perform
 *       handler.perform()} before performing the associated persistence operations</li>
 *
 *   <li>{@link PhaseManager#cancel PhaseManager.cancel()} - if a handler exists call {@link #perform
 *       handler.perform()} before performing the associated persistence operations</li>
 * </ul>
 *
 * @author AleaActaEst, RachaelLCook
 * @version 1.0
 */

public interface PhaseHandler {
    /**
     * The handler will make the decision as to whether the start, end or cancel operations can be performed for the
     * specified phase.
     *
     * @throws IllegalArgumentException if <code>phase</code> is <code>null</code>
     * @throws PhaseHandlingException if an exception occurs while determining whether the operation can be performed
     *
     * @param phase phase to test
     * @return <code>true</code> if the associated action can be performed
     */
    public boolean canPerform(Phase phase) throws PhaseHandlingException;

    /**
     * Extra logic to be used when the phase is starting, ending or canceling. This will be called by the {@link
     * PhaseManager PhaseManager} implementation at phase change time to perform additional tasks that are due when
     * the specified phase has changed (moved to the next phase).
     *
     * @throws IllegalArgumentException if any argument is <code>null</code>, or if <code>operator</code> is an empty
     *   string
     * @throws PhaseHandlingException if an exception occurs while performing the phase transition
     *
     * @param phase phase to apply an operation to
     * @param operator operator applying
     */
    public void perform(Phase phase, String operator) throws PhaseHandlingException;
}


