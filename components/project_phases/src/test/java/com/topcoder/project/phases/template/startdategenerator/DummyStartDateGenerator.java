/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.startdategenerator;

import java.util.Date;

import com.topcoder.project.phases.template.StartDateGenerationException;
import com.topcoder.project.phases.template.StartDateGenerator;

/**
 * <p>
 * Dummy implementation of <code>StartDateGenerator</code> for test.
 * </p>
 * @author flying2hk
 * @version 1.0
 */
public class DummyStartDateGenerator implements StartDateGenerator {
    /**
     * <p>
     * Generate a start date according to a specific generation logic.
     * </p>
     * @return the generated start date
     * @throws StartDateGenerationException if any error occurs so that the
     *             start date can not be generated(not used in this initial
     *             version, just for future extension)
     */
    public Date generateStartDate() throws StartDateGenerationException {
        return null;
    }
}
