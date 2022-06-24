/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import com.topcoder.management.deliverable.AuditedDeliverableStructure;

/**
 * Mock subclass of <code>AuditedDeliverableStructure</code>.
 *
 * @author assistant
 * @version 1.0
 *
 */
public class MockAuditedDeliverableStructure extends
        AuditedDeliverableStructure {

    /**
     * Default constructor.
     */
    public MockAuditedDeliverableStructure() {
        super();
    }

    /**
     * Constructor with id.
     */
    public MockAuditedDeliverableStructure(long id) {
        super(id);
    }
}
