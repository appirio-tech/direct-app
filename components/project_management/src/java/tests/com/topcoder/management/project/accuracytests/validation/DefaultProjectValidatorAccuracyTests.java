/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.accuracytests.validation;

import com.topcoder.management.project.validation.DefaultProjectValidator;

import junit.framework.TestCase;


/**
 * Accuracy test cases for <code>DefaultProjectValidator</code>.
 *
 * @author skatou, Beijing2008
 * @version 1.0
 */
public class DefaultProjectValidatorAccuracyTests extends TestCase {
    /**
     * Tests constructor.
     */
    public void testConstructor() {
        DefaultProjectValidator validator = new DefaultProjectValidator("");
        assertNotNull(validator);
    }
}
