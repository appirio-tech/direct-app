/*
 * Copyright (c) 2006, TopCoder, Inc. All rights reserved
 */
package com.topcoder.management.scorecard.data.stresstests;

import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Section;


/**
 * Stress test cases for Section class.
 *
 * @author Wendell
 * @version 1.0
 */
public class SectionStressTest extends AbstractTest {
    /** The id of the section. */
    private final long ID = 1;

    /** The name of the section. */
    private final String NAME = "name";

    /** The weight of the section. */
    private final float WEIGHT = 20.5f;

    /** The Section instance to test against. */
    private Section section;

    /**
     * Creates the Section instance for testing.
     */
    protected void setUp() {
        section = new Section(ID, NAME, WEIGHT);
    }

    /**
     * Tests Section() constructor.
     */
    public void testSection1() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Section section = new Section();
            assertNotNull("fail to instantiate Section", section);
        }

        end("Section()");
    }

    /**
     * Tests Section(long) constructor.
     */
    public void testSection2() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Section section = new Section(ID);
            assertNotNull("fail to instantiate Section", section);
            assertEquals("id incorrect", ID, section.getId());
        }

        end("Section(long)");
    }

    /**
     * Tests Section(long, String) constructor.
     */
    public void testSection3() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Section section = new Section(ID, NAME);
            assertNotNull("fail to instantiate Section", section);
            assertEquals("id incorrect", ID, section.getId());
            assertEquals("name incorrect", NAME, section.getName());
        }

        end("Section(long, String)");
    }

    /**
     * Tests Section(long, float) constructor.
     */
    public void testSection4() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Section section = new Section(ID, WEIGHT);
            assertNotNull("fail to instantiate Section", section);
            assertEquals("id incorrect", ID, section.getId());
            assertEquals("weight incorrect", WEIGHT, section.getWeight(), DELTA);
        }

        end("Section(long, float)");
    }

    /**
     * Tests Section(long, String, float) constructor.
     */
    public void testSection5() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            Section section = new Section(ID, NAME, WEIGHT);
            assertNotNull("fail to instantiate Section", section);
            assertEquals("id incorrect", ID, section.getId());
            assertEquals("name incorrect", NAME, section.getName());
            assertEquals("weight incorrect", WEIGHT, section.getWeight(), DELTA);
        }

        end("Section(long, String, float)");
    }

    /**
     * Tests addQuestion(Question) method.
     */
    public void testAddQuestion() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            section.addQuestion(new Question());
        }

        assertEquals("question count incorrect.", TIMES, section.getNumberOfQuestions());
        end("addQuestion(Question)");
    }

    /**
     * Tests addQuestions(Question[]) method.
     */
    public void testAddQuestions() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            section.addQuestions(new Question[] {new Question()});
        }

        assertEquals("question count incorrect.", TIMES, section.getNumberOfQuestions());

        end("addQuestions(Question[])");
    }

    /**
     * Tests removeQuestion(Question) method.
     */
    public void testRemoveQuestion1() {
        Question[] questions = new Question[TIMES];

        for (int i = 0; i < TIMES; i++) {
            questions[i] = new Question();
        }

        begin();

        for (int i = 0; i < TIMES; i++) {
            section.removeQuestion(questions[i]);
        }

        assertEquals("question count incorrect", 0, section.getNumberOfQuestions());
        end("removeQuestion(Question)");
    }

    /**
     * Tests removeQuestion(int) method.
     */
    public void testRemoveQuestion2() {
        Question[] questions = new Question[TIMES];

        for (int i = 0; i < TIMES; i++) {
            questions[i] = new Question();
            section.addQuestion(questions[i]);
        }

        begin();

        for (int i = 0; i < TIMES; i++) {
            section.removeQuestion(0);
        }

        assertEquals("question count incorrect", 0, section.getNumberOfQuestions());
        end("removeQuestion(int)");
    }

    /**
     * Tests removeQuestions(Question[]) method.
     */
    public void testRemoveQuestions() {
        Question[] questions = new Question[TIMES];

        for (int i = 0; i < TIMES; i++) {
            questions[i] = new Question();
        }

        begin();

        for (int i = 0; i < TIMES; i++) {
            section.removeQuestions(new Question[] {questions[i]});
        }

        assertEquals("question count incorrect", 0, section.getNumberOfQuestions());
        end("removeQuestions(Question[])");
    }

    /**
     * Tests insertQuestion(Question, int) method.
     */
    public void testInsertQuestion() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            section.insertQuestion(new Question(), 0);
        }

        assertEquals("question count incorrect", TIMES, section.getNumberOfQuestions());
        end("insertQuestion(Question, int)");
    }

    /**
     * Tests checkWeights(float) method.
     */
    public void testCheckWeights() {
        begin();

        for (int i = 0; i < TIMES; i++) {
            section.checkWeights(DELTA);
        }

        end("checkWeights(float)");
    }
}
