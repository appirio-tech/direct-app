/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.data.stresstests;

import com.topcoder.management.scorecard.data.ScorecardStatus;


/**
 * Stress test cases for ScorecardStatus class.
 *
 * @author Wendell
 * @version 1.0
 */
public class ScorecardStatusStressTest extends AbstractTest {
    /** The id of the question type. */
    private final long ID = 1;

    /** The name of the question type. */
    private final String NAME = "type";

    /**
     * Tests ScorecardStatus() constructor.
     */
    public void testQuestionType1() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            ScorecardStatus type = new ScorecardStatus();
            assertNotNull("fail to instantiate ScorecardStatus", type);
        }

        end("ScorecardStatus()");
    }

    /**
     * Tests ScorecardStatus(long) constructor.
     */
    public void testQuestionType2() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            ScorecardStatus type = new ScorecardStatus(ID);
            assertNotNull("fail to instantiate ScorecardStatus", type);
            assertEquals("id incorrect", ID, type.getId());
        }

        end("ScorecardStatus(long)");
    }

    /**
     * Tests ScorecardStatus(long, String) constructor.
     */
    public void testQuestionType3() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            ScorecardStatus type = new ScorecardStatus(ID, NAME);
            assertNotNull("fail to instantiate ScorecardStatus", type);
            assertEquals("id incorrect", ID, type.getId());
            assertEquals("name incorrect", NAME, type.getName());
        }

        end("ScorecardStatus(long, String)");
    }
}
