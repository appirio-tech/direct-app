/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases.template.accuracytests;

import java.util.Date;

import com.topcoder.project.phases.template.StartDateGenerationException;
import com.topcoder.project.phases.template.StartDateGenerator;

/**
 * mock class only for testing purpose.
 * @author FireIce
 * @version 1.0
 */
public class MockStartDateGenerator implements StartDateGenerator {

    /**
     * default no-arg constructor.
     */
    public MockStartDateGenerator() {

    }

    /**
     * constructor using namespace to config.
     */
    public MockStartDateGenerator(String namespace) {

    }

    /**
     * mock method.
     */
    public Date generateStartDate() throws StartDateGenerationException {
        return new Date();
    }

}
