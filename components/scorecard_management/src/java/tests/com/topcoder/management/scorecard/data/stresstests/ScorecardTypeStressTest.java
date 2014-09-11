/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.data.stresstests;

import com.topcoder.management.scorecard.data.ScorecardType;


/**
 * Stress test cases for ScorecardType class.
 *
 * @author Wendell
 * @version 1.0
 */
public class ScorecardTypeStressTest extends AbstractTest {
    /** The id of the question type. */
    private final long ID = 1;

    /** The name of the question type. */
    private final String NAME = "type";

    /**
     * Tests ScorecardType() constructor.
     */
    public void testScorecardType1() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            ScorecardType type = new ScorecardType();
            assertNotNull("fail to instantiate ScorecardType", type);
        }

        end("ScorecardType()");
    }

    /**
     * Tests ScorecardType(long) constructor.
     */
    public void testScorecardType2() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            ScorecardType type = new ScorecardType(ID);
            assertNotNull("fail to instantiate ScorecardType", type);
            assertEquals("id incorrect", ID, type.getId());
        }

        end("ScorecardType(long)");
    }

    /**
     * Tests ScorecardType(long, String) constructor.
     */
    public void testScorecardType3() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            ScorecardType type = new ScorecardType(ID, NAME);
            assertNotNull("fail to instantiate ScorecardType", type);
            assertEquals("id incorrect", ID, type.getId());
            assertEquals("name incorrect", NAME, type.getName());
        }

        end("ScorecardType(long, String)");
    }
}
