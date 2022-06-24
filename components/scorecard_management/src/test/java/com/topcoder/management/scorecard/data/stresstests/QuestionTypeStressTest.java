/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.data.stresstests;

import com.topcoder.management.scorecard.data.QuestionType;


/**
 * Stress test cases for QuestionType class.
 *
 * @author Wendell
 * @version 1.0
 */
public class QuestionTypeStressTest extends AbstractTest {
    /** The id of the question type. */
    private final long ID = 1;

    /** The name of the question type. */
    private final String NAME = "type";

    /**
     * Tests QuestionType() constructor.
     */
    public void testQuestionType1() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            QuestionType type = new QuestionType();
            assertNotNull("fail to instantiate QuestionType", type);
        }

        end("QuestionType()");
    }

    /**
     * Tests QuestionType(long) constructor.
     */
    public void testQuestionType2() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            QuestionType type = new QuestionType(ID);
            assertNotNull("fail to instantiate QuestionType", type);
            assertEquals("id incorrect", ID, type.getId());
        }

        end("QuestionType(long)");
    }

    /**
     * Tests QuestionType(long, String) constructor.
     */
    public void testQuestionType3() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            QuestionType type = new QuestionType(ID, NAME);
            assertNotNull("fail to instantiate QuestionType", type);
            assertEquals("id incorrect", ID, type.getId());
            assertEquals("name incorrect", NAME, type.getName());
        }

        end("QuestionType(long, String)");
    }
}
