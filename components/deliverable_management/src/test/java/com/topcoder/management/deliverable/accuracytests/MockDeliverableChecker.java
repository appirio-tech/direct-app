/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.accuracytests;

import com.topcoder.management.deliverable.Deliverable;
import com.topcoder.management.deliverable.DeliverableChecker;

import java.util.Date;


/**
 * A mock class implements <code>DeliverableChecker</code> interface.
 *
 * @author skatou
 * @version 1.0
 */
public class MockDeliverableChecker implements DeliverableChecker {
    /**
     * Sets the completion date of the given deliverable. Always succeeds.
     *
     * @param deliverable the deliverable to be checked.
     */
    public void check(Deliverable deliverable) {
        if (deliverable.getCompletionDate() == null) {
            deliverable.setCompletionDate(new Date());
        }
    }
}
