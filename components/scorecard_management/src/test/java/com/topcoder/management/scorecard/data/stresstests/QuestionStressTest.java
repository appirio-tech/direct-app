/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.data.stresstests;

import com.topcoder.management.scorecard.data.Question;


/**
 * Stress test cases for Question class.
 *
 * @author Wendell
 * @version 1.0
 */
public class QuestionStressTest extends AbstractTest {
    /** The id of the question. */
    private final long ID = 1;

    /**
     * Tests Question() constructor.
     */
    public void testQuestion1() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Question question = new Question();
            assertNotNull("failed to instantiate Question", question);
        }

        end("Question()");
    }

    /**
     * Tests Question(long) constructor.
     */
    public void testQuestion2() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Question question = new Question(ID);
            assertNotNull("failed to instantiate Question", question);
            assertEquals("id incorrect", ID, question.getId());
        }

        end("Question(long)");
    }
}
