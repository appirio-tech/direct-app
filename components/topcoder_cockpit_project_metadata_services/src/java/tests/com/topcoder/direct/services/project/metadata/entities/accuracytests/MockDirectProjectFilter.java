/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.entities.accuracytests;

import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;

/**
 * <p>Mock implementation of {@link DirectProjectFilter} class.</p>
 */
public class MockDirectProjectFilter implements DirectProjectFilter {

    /**
     * <p>Creates new instance of {@link DirectProjectFilter} class.</p>
     */
    public MockDirectProjectFilter() {
        // empty constructor
    }

    /**
     * Mock implementation, returns null.
     *
     * @return always returns null
     */
    public String toJSONString() {
        return "MockDirectProjectFilter";
    }
}
