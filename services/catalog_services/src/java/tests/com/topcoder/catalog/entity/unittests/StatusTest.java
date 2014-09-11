/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.catalog.entity.unittests;

import com.topcoder.catalog.entity.Status;
import junit.framework.TestCase;

/**
 * <p>This test verifies to-from integer conversion for <code>Status</code> enumeration.</p>
 *
 * @author Retunsky
 * @version 1.0
 */
public class StatusTest extends TestCase {
    /**
     * <p>Tests that <code>valueOf(int)</code> method returns correct values.</p>
     */
    public void testValueOf() {
        final Status[] statuses = Status.values();
        for (Status status : statuses) {
            final Status obtainedStatus = Status.valueOf(status.getStatusId());
            assertTrue("The objects must be identical", status == obtainedStatus);
        }
    }

    /**
     * <p>Tests that <code>valueOf(int)</code> method with invalid <code>statusId</code>.</p>
     */
    public void testValueOfNonExistent() {
        assertNull("There is no Status with given statusId", Status.valueOf(-1));
    }
}
