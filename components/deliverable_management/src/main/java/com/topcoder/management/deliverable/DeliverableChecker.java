/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable;

import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;

/**
 * <p>
 * The DeliverableChecker interface is responsible for deciding if a deliverable is complete. If so,
 * it sets the completion date of the deliverable.
 * </p>
 * <p>
 * Implementations of this interface are not required to be thread safe.
 * </p>
 *
 * @author aubergineanode, singlewood
 * @version 1.0.2
 */
public interface DeliverableChecker {
    /**
     * Checks the given deliverable to see if it is complete.
     *
     * @param deliverable The deliverable to check
     * @throws IllegalArgumentException If deliverable is null
     * @throws DeliverableCheckingException If there is an error when determining whether a
     *             Deliverable has been completed or not
     */
    public void check(Deliverable deliverable) throws DeliverableCheckingException;
}
