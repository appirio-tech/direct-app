/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.persistence.stresstests;

import java.sql.Connection;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.management.scorecard.persistence.InformixScorecardPersistence;

/**
 * <p>
 * The set of stress tests.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ScorecardManagementPersistenceStressTest extends TestCase {

    /**
     * Instance of InformixScorecardPersistence for testing.
     */
    private InformixScorecardPersistence informixScorecardPersistence;

    /**
     * Instance of Connection for testing.
     */
    private Connection connection;

    /**
     * Instance of Scorecard for testing.
     */
    private Scorecard scorecard;

    /**
     * set up for each test case.
     *
     * @throws Exception wrap all exceptions
     */
    protected void setUp() throws Exception {
        StressTestHelper.loadAllConfig();
        scorecard = getScorecard();
        informixScorecardPersistence = new InformixScorecardPersistence(
                "com.topcoder.management.scorecard.persistence.StressTest");
        DBConnectionFactory connectionFactory = new DBConnectionFactoryImpl(
                "com.topcoder.db.connectionfactory.DBConnectionFactoryImplTest");
        connection = connectionFactory.createConnection();
        StressTestHelper.clearTables(connection);
        StressTestHelper.addTestData(connection);
    }

    /**
     * Testing scorecard creation 10 times.
     *
     * @throws Exception wrap all exceptions
     */
    public final void testCreateScorecard10() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            informixScorecardPersistence.createScorecard(scorecard,
                    "creationUser");
        }
        System.out.println("--- creating of 10 scorecards have taken "
                + (System.currentTimeMillis() - startTime)
                + " milliseconds ---");
    }

    /**
     * Testing scorecard creation 30 times.
     *
     * @throws Exception wrap all exceptions
     */
    public final void testCreateScorecard30() throws Exception {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            informixScorecardPersistence.createScorecard(scorecard,
                    "creationUser");
        }
        System.out.println("--- creating of 30 scorecards have taken "
                + (System.currentTimeMillis() - startTime)
                + " milliseconds ---");
    }

    /**
     * Testing scorecard update 10 times.
     *
     * @throws Exception wrap all exceptions
     */
    public final void testUpdateScorecard10() throws Exception {
        informixScorecardPersistence.createScorecard(scorecard, "creationUser");
        scorecard.setName("name2");
        scorecard.setVersion("version2");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            informixScorecardPersistence.updateScorecard(scorecard,
                    "modificationUser");
        }
        System.out.println("--- updating of 10 scorecards have taken "
                + (System.currentTimeMillis() - startTime)
                + " milliseconds ---");
    }

    /**
     * Testing scorecard update 30 times.
     *
     * @throws Exception wrap all exceptions
     */
    public final void testUpdateScorecard30() throws Exception {
        informixScorecardPersistence.createScorecard(scorecard, "creationUser");
        scorecard.setName("name2");
        scorecard.setVersion("version2");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            informixScorecardPersistence.updateScorecard(scorecard,
                    "modificationUser");
        }
        System.out.println("--- creating of 30 scorecards have taken "
                + (System.currentTimeMillis() - startTime)
                + " milliseconds ---");
    }

    /**
     * Testing scorecard getting 10 times.
     *
     * @throws Exception wrap all exceptions
     */
    public final void testGetScorecard10() throws Exception {
        informixScorecardPersistence.createScorecard(scorecard, "creationUser");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            assertNotNull("scorecard must exist within persistence",
                    informixScorecardPersistence.getScorecard(
                            scorecard.getId(), true));
        }
        System.out.println("--- getting of 10 scorecards have taken "
                + (System.currentTimeMillis() - startTime)
                + " milliseconds ---");
    }

    /**
     * Testing scorecard getting 30 times.
     *
     * @throws Exception wrap all exceptions
     */
    public final void testGetScorecard30() throws Exception {
        informixScorecardPersistence.createScorecard(scorecard, "creationUser");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            assertNotNull("scorecard must exist within persistence",
                    informixScorecardPersistence.getScorecard(
                            scorecard.getId(), true));
        }
        System.out.println("--- getting of 30 scorecards have taken "
                + (System.currentTimeMillis() - startTime)
                + " milliseconds ---");
    }

    /**
     * tear down for each test case.
     *
     * @throws Exception wrap all exceptions
     */
    protected void tearDown() throws Exception {
        StressTestHelper.clearTestConfig();
        StressTestHelper.clearTables(connection);
        connection.close();
    }

    /**
     * Creating instance of Scorecard for testing.
     *
     * @return scorecard created scorecard
     */
    private Scorecard getScorecard() {
        Scorecard currentScorecard = new Scorecard();
        // creating and setting scorecard status
        ScorecardStatus scorecardStatus = new ScorecardStatus(1);
        currentScorecard.setScorecardStatus(scorecardStatus);
        // creating and setting scorecard type
        ScorecardType scorecardType = new ScorecardType(1);
        currentScorecard.setScorecardType(scorecardType);
        // populating
        currentScorecard.setName("name");
        currentScorecard.setVersion("version");
        currentScorecard.setCategory(1);
        currentScorecard.setMaxScore(100);
        currentScorecard.setMinScore(75);
        // adding groups
        for (int i = 1; i < 6; i++) {
            Group group = new Group(i, "group" + i);
            for (int j = 1; j < 6; j++) {
                Section section = new Section(j, "section" + j);
                for (int k = 1; k < 6; k++) {
                    Question question = new Question(k);
                    question.setName("name");
                    question.setDescription("description");
                    question.setQuestionType(new QuestionType(1));
                    section.addQuestion(question);
                }
                group.addSection(section);
            }
            currentScorecard.addGroup(group);
        }
        return currentScorecard;
    }
}