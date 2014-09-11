/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.topcoder.management.scorecard.data;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Contains the unit tests for the {@link ScorecardStatus} class.
 *
 * @author      UFP2161
 * @copyright   Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 * @version     1.0
 */
public class ScorecardStatusTest extends NamedScorecardStructureTest {

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Static Methods

    /**
     * Creates a test suite for the tests in this test case.
     *
     * @return  A TestSuite for this test case.
     */
    public static Test suite() {
        return new TestSuite(ScorecardStatusTest.class);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Overridden Abstract Methods

    /**
     * Creates a new ScorecardStatus instance using the zero argument constructor.
     *
     * @return  A ScorecardStatus instance created using the zero argument constructor.
     */
    protected NamedScorecardStructure createInstance() {
        return new ScorecardStatus();
    }

    /**
     * Creates a new ScorecardStatus instance using the one argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     *
     * @return  A ScorecardStatus instance created using the one argument constructor.
     */
    protected NamedScorecardStructure createInstance(long id) {
        return new ScorecardStatus(id);
    }

    /**
     * Creates a new ScorecardStatus instance using the two argument constructor.
     *
     * @param   id
     *          The identifier to initialize with.
     * @param   name
     *          The name to initialize with.
     *
     * @return  A ScorecardStatus instance created using the two argument constructor.
     */
    protected NamedScorecardStructure createInstance(long id, String name) {
        return new ScorecardStatus(id, name);
    }
}
