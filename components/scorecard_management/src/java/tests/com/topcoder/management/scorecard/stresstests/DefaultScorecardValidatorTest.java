/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.stresstests;

import com.topcoder.management.scorecard.ScorecardValidator;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.management.scorecard.validation.DefaultScorecardValidator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Stress test case of DefaultScorecardValidator class.<br>
 * The test is performed against validateScorecard method.
 * @author King_Bette
 * @version 1.0
 */
public class DefaultScorecardValidatorTest extends TestCase {
    /**
     * Aggregates all tests in this class.
     * @return Test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(DefaultScorecardValidatorTest.class);
    }

    /**
     * test validateScorecard method.<br>
     * run 100 times with a simple test case.
     * @throws Exception
     *             throw exception to junit.
     */
    public void testValidateScorecard1() throws Exception {
        long startTime = System.currentTimeMillis();
        ScorecardValidator validator = new DefaultScorecardValidator("");
        for (int i = 0; i < 100; i++) {
            validator.validateScorecard(getSimpleScorecard());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("validate simple scorecard 100 times takes " + (endTime - startTime) + "ms");
    }

    /**
     * test validateScorecard method.<br>
     * run 500 times with a simple test case.
     * @throws Exception
     *             throw exception to junit.
     */
    public void testValidateScorecard2() throws Exception {
        long startTime = System.currentTimeMillis();
        ScorecardValidator validator = new DefaultScorecardValidator("");
        for (int i = 0; i < 500; i++) {
            validator.validateScorecard(getSimpleScorecard());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("validate simple scorecard 500 times takes " + (endTime - startTime) + "ms");
    }

    /**
     * test validateScorecard method.<br>
     * run 500 times with a complex test case.
     * @throws Exception
     *             throw exception to junit.
     */
    public void testValidateScorecard3() throws Exception {
        long startTime = System.currentTimeMillis();
        ScorecardValidator validator = new DefaultScorecardValidator("");
        for (int i = 0; i < 500; i++) {
            validator.validateScorecard(getComplexScorecard());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("validate complex scorecard 500 times takes " + (endTime - startTime) + "ms");
    }

    /**
     * get a simple scorecard.
     * @return a simple scorecard.
     */
    private static Scorecard getSimpleScorecard() {
        // create a question.
        Question question = new Question(1);
        question.setName("question_name");
        question.setDescription("this is a test question.");
        question.setWeight(100);

        // create a section.
        Section section = new Section(1);
        section.setName("section1");
        section.setWeight(100);
        section.addQuestion(question);

        // create a group
        Group group = new Group(1);
        group.setName("group1");
        group.setWeight(100);
        group.addSection(section);

        // create a scorecard.
        Scorecard scorecard = new Scorecard();
        scorecard.setScorecardStatus(new ScorecardStatus(1));
        scorecard.setScorecardType(new ScorecardType(1));
        scorecard.setCategory(1);
        scorecard.setName("scorecard1");
        scorecard.setVersion("1.0.1");
        scorecard.setMinScore(0);
        scorecard.setMaxScore(100);
        scorecard.addGroup(group);

        return scorecard;
    }

    /**
     * get a complex scorecard.
     * @return a complex scorecard.
     */
    private static Scorecard getComplexScorecard() {
        // create a scorecard.
        Scorecard scorecard = new Scorecard();
        scorecard.setScorecardStatus(new ScorecardStatus(1));
        scorecard.setScorecardType(new ScorecardType(1));
        scorecard.setCategory(1);
        scorecard.setName("scorecard1");
        scorecard.setVersion("1.0.1");
        scorecard.setMinScore(0);
        scorecard.setMaxScore(100);

        // create a scorecard contain 10 groups, each group contain 10 sections,
        // each sections contain 10 questions.
        for (int i = 0; i < 10; i++) {
            // create a group
            Group group = new Group(i + 1);
            group.setName("group1");
            group.setWeight(10);
            for (int j = 0; j < 10; j++) {
                // create a section.
                Section section = new Section(j + 1);
                section.setName("section1");
                section.setWeight(10);
                for (int k = 0; k < 10; k++) {
                    // create a question.
                    Question question = new Question(k + 1);
                    question.setName("question_name");
                    question.setDescription("this is a test question.");
                    question.setWeight(10);
                    section.addQuestion(question);
                }
                group.addSection(section);
            }
            scorecard.addGroup(group);
        }
        return scorecard;
    }
}
