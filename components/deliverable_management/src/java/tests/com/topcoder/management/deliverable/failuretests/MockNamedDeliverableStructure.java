/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.deliverable.failuretests;

import com.topcoder.management.deliverable.NamedDeliverableStructure;


/**
 * Mock subclass of {@link NamedDeliverableStructure}.
 *
 * @author assistant
 * @version 1.0
 */
public class MockNamedDeliverableStructure extends NamedDeliverableStructure {

    /**
     * Default constructor.
     */
    public MockNamedDeliverableStructure() {
        super();
    }

    /**
     * Constructor with id.
     */
    public MockNamedDeliverableStructure(long id) {
        super(id);
    }

    /**
     * Constructor with id and name.
     */
    public MockNamedDeliverableStructure(long id, String name) {
        super(id, name);
    }

}
