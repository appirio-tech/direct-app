/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.DeliverableChecker;
import com.topcoder.management.deliverable.persistence.DeliverableCheckingException;

/**
 * Mock implementation of DeliverableChecker used for testing.
 *
 * @author  assistant
 * @version  1.0
 */
class MockDeliverableChecker implements DeliverableChecker {

    /**
     * Mock implementation of check method.
     *
     * @param deliverable the deliverable to check
     *
     * @throws DeliverableCheckingException not used here
     */
    public void check(Deliverable deliverable)
        throws DeliverableCheckingException {

        if (deliverable == null) {
            throw new IllegalArgumentException("deliverable must not be null.");
        }
    }

    /**
     * Returns number of times check was called.
     *
     * @return  number of times a method was called
     */
    public int checkCalled() {
        return 0;
    }

    /**
     * Resets this object.
     */
    public void reset() {
    }
}
