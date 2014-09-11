/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.metadata.impl;

import com.topcoder.direct.services.project.metadata.entities.dto.DirectProjectFilter;

/**
 * <p>
 * A mock implementation of DirectProjectFilter. Used for testing.
 * </p>
 *
 * @author sparemax
 * @version 1.0
 */
@SuppressWarnings("serial")
public class MockDirectProjectFilter implements DirectProjectFilter {
    /**
     * Creates an instance of MockDirectProjectFilter.
     */
    public MockDirectProjectFilter() {
        // Empty
    }

    /**
     * Converts the entity to a JSON string that can be used for logging.
     *
     * @return the JSON string with entity data (not null)
     */
    public String toJSONString() {
        return "";
    }
}
