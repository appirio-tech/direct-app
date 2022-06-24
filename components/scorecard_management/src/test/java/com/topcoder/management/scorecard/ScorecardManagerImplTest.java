/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard;

import java.util.Iterator;

import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.QuestionType;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.ScorecardStatus;
import com.topcoder.management.scorecard.data.ScorecardType;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.management.scorecard.validation.ValidationException;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.util.config.ConfigManager;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for ScorecardManagerImpl class. Since ScorecardManagerImpl always pass the parameter to the persistence,
 * some static variables are used to store these parameters received by <code>MockScorecardPersistence</code>.
 * After that, these stored information can be compared with the real objects.
 * @author zhuzeyuan
 * @version 1.0
 */
public class ScorecardManagerImplTest extends TestCase {

    /**
     * To record the value <code>ids</code> <code>MockScorecardPersistence</code> class received as parameter.
     */
    private static long[] ids = null;

    /**
     * To record the value <code>id</code> <code>MockScorecardPersistence</code> class received as parameter.
     */
    private static long id = -1;

    /**
     * To record the value <code>complete</code> <code>MockScorecardPersistence</code> class received as
     * parameter.
     */
    private static boolean complete = false;

    /**
     * This ScorecardType array will be used by <code>MockScorecardPersistence</code> as return value, and hence we
     * can check if the corresponding methods for ScorecardManagerImpl works properly.
     */
    private static ScorecardType[] returnScorecardTypes = {new ScorecardType()};

    /**
     * This QuestionType array will be used by <code>MockScorecardPersistence</code> as return value, and hence we
     * can check if the corresponding methods for ScorecardManagerImpl works properly.
     */
    private static QuestionType[] returnQuestionTypes = {new QuestionType()};

    /**
     * This ScorecardStatus array will be used by <code>MockScorecardPersistence</code> as return value, and hence
     * we can check if the corresponding methods for ScorecardManagerImpl works properly.
     */
    private static ScorecardStatus[] returnScorecardStatuses = {new ScorecardStatus()};

    /**
     * Store the namespace to retrieve property from the Configuration Manager.
     */
    private static final String MANAGEMENT_IMPL = "com.topcoder.management.scorecard";

    /**
     * Store the namespace to retrieve property from the Configuration Manager.
     */
    private static final String SEACH_BUILDER = "com.topcoder.searchbuilder";

    /**
     * Provide a default ScorecardManagerImpl for testing.
     */
    private ScorecardManager manager;

    /**
     * Provide an instance of ConfigManager to add config files.
     */
    private ConfigManager config;

    /**
     * Provide a valid scorecard for testing.
     */
    private Scorecard scorecard;

    /**
     * Provide a string for scorecard operator.
     */
    private String operator;

    /**
     * To record the value <code>ids</code> <code>MockScorecardPersistence</code> class received as parameter.
     * @param ids
     *            the parameter passed to <code>MockScorecardPersistence</code>
     */
    public static void setIds(long[] ids) {
        ScorecardManagerImplTest.ids = ids;
    }

    /**
     * To record the value <code>id</code> <code>MockScorecardPersistence</code> class received as parameter.
     * @param id
     *            the parameter passed to <code>MockScorecardPersistence</code>
     */
    public static void setId(long id) {
        ScorecardManagerImplTest.id = id;
    }

    /**
     * To record the value <code>complete</code> <code>MockScorecardPersistence</code> class received as
     * parameter.
     * @param complete
     *            the parameter passed to <code>MockScorecardPersistence</code>
     */
    public static void setComplete(boolean complete) {
        ScorecardManagerImplTest.complete = complete;
    }

    /**
     * This ScorecardType array will be used by <code>MockScorecardPersistence</code> as return value, and hence we
     * can check if the corresponding methods for ScorecardManagerImpl works properly.
     * @return the ScorecardType array that <code>MockScorecardPersistence</code> should use.
     */
    public static ScorecardType[] getReturnScorecardTypes() {
        return returnScorecardTypes;
    }

    /**
     * This QuestionType array will be used by <code>MockScorecardPersistence</code> as return value, and hence we
     * can check if the corresponding methods for ScorecardManagerImpl works properly.
     * @return the QuestionType array that <code>MockScorecardPersistence</code> should use.
     */
    public static QuestionType[] getReturnQuestionTypes() {
        return returnQuestionTypes;
    }

    /**
     * This ScorecardStatus array will be used by <code>MockScorecardPersistence</code> as return value, and hence
     * we can check if the corresponding methods for ScorecardManagerImpl works properly.
     * @return the ScorecardStatus array that <code>MockScorecardPersistence</code> should use.
     */
    public static ScorecardStatus[] getReturnScorecardStatuses() {
        return returnScorecardStatuses;
    }

    /**
     * Aggragates all tests in this class.
     * @return Test suite aggragating all tests.
     */
    public static Test suite() {
        return new TestSuite(ScorecardManagerImplTest.class);
    }

    /**
     * Sets up the environment for the TestCase.
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void setUp() throws Exception {
        // Preset the config file.
        config = ConfigManager.getInstance();
        config.add("config.xml");
        manager = new ScorecardManagerImpl();

        // Preset a question.
        Question question = new Question(1, 100);
        question.setName("question_name");
        question.setDescription("this is the description");
        question.setGuideline("this is the guideline");

        // Preset a section with the above question.
        Section section = new Section(1, "section_name", 100);
        section.addQuestion(question);

        // Preset a group with the above section.
        Group group = new Group(1, "group_name", 100);
        group.addSection(section);

        // Preset a scorecard with the above group.
        scorecard = new Scorecard(1);
        scorecard.addGroup(group);
        ScorecardType scorecardType = new ScorecardType(1, "scorecard_type_name");
        scorecard.setScorecardType(scorecardType);
        ScorecardStatus scorecardStatus = new ScorecardStatus(1, "scorecard_status_name");
        scorecard.setScorecardStatus(scorecardStatus);
        scorecard.setCategory(1);
        scorecard.setName("scorecard_name");
        scorecard.setVersion("1.1");
        scorecard.setMinScore(5.0f);
        scorecard.setMaxScore(95.0f);

        // Preset an operator.
        operator = "operator";

        // Reset the recording values.
        ids = null;
        id = -1;
        complete = false;
    }

    /**
     * Tears down the environment for the TestCase.
     * @throws Exception
     *             throw exception to JUnit.
     */
    protected void tearDown() throws Exception {
        // Remove all the potentially added properties.
        for (Iterator itr = config.getAllNamespaces(); itr.hasNext();) {
            config.removeNamespace((String) itr.next());
        }
    }

    /**
     * Accuracy test of <code>ScorecardManagerImpl()</code> constructor.
     * <p>
     * Create a new ScorecardManagerImpl instance without "PersistenceNamespace" and "ValidatorNamespace" properties
     * in the config file. These two properties are optional only.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testScorecardManagerImplAccuracy_EmptyConstructor() throws Exception {
        config.removeNamespace(MANAGEMENT_IMPL);
        config.add("config_good1.xml");
        new ScorecardManagerImpl();
    }

    /**
     * Failure test of <code>ScorecardManagerImpl()</code> constructor.
     * <p>
     * Create a new ScorecardManagerImpl instance without "SearchBuilderNamespace" property in the config file. This
     * property is required!
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testScorecardManagerImplFailure1_EmptyConstructor() throws Exception {
        config.removeNamespace(MANAGEMENT_IMPL);
        config.add("config_bad1.xml");
        try {
            new ScorecardManagerImpl();
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ScorecardManagerImpl()</code> constructor.
     * <p>
     * Create a new ScorecardManagerImpl instance without "PersistenceClass" property in the config file. This
     * property is required!
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testScorecardManagerImplFailure2_EmptyConstructor() throws Exception {
        config.removeNamespace(MANAGEMENT_IMPL);
        config.add("config_bad2.xml");
        try {
            new ScorecardManagerImpl();
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ScorecardManagerImpl()</code> constructor.
     * <p>
     * Create a new ScorecardManagerImpl instance without "ValidatorClass" property in the config file. This property
     * is required!
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testScorecardManagerImplFailure3_EmptyConstructor() throws Exception {
        config.removeNamespace(MANAGEMENT_IMPL);
        config.add("config_bad3.xml");
        try {
            new ScorecardManagerImpl();
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ScorecardManagerImpl()</code> constructor.
     * <p>
     * Create a new ScorecardManagerImpl instance with bad ScorecardSearchBundle property.
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testScorecardManagerImplFailure4_EmptyConstructor() throws Exception {
        config.removeNamespace(SEACH_BUILDER);
        config.add("config_bad4.xml");
        try {
            new ScorecardManagerImpl();
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>ScorecardManagerImpl(String ns)</code> constructor.
     * <p>
     * Create a new ScorecardManagerImpl instance without "PersistenceNamespace" and "ValidatorNamespace" properties
     * in the config file. These two properties are optional only. The namespace "HELLOWORLD" is used instead of
     * default namespace.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testScorecardManagerImplAccuracy_NamespaceConstructor() throws Exception {
        config.removeNamespace(MANAGEMENT_IMPL);
        config.add("config_good2.xml");
        new ScorecardManagerImpl("HELLOWORLD");
    }

    /**
     * Failure test of <code>ScorecardManagerImpl(String ns)</code> constructor.
     * <p>
     * Create a new ScorecardManagerImpl instance without "SearchBuilderNamespace" property in the config file. This
     * property is required!
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testScorecardManagerImplFailure1_NamespaceConstructor() throws Exception {
        config.removeNamespace(MANAGEMENT_IMPL);
        config.add("config_bad1.xml");
        try {
            new ScorecardManagerImpl(ScorecardManagerImpl.NAMESPACE);
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ScorecardManagerImpl(String ns)</code> constructor.
     * <p>
     * Create a new ScorecardManagerImpl instance without "PersistenceClass" property in the config file. This
     * property is required!
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testScorecardManagerImplFailure2_NamespaceConstructor() throws Exception {
        config.removeNamespace(MANAGEMENT_IMPL);
        config.add("config_bad2.xml");
        try {
            new ScorecardManagerImpl(ScorecardManagerImpl.NAMESPACE);
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ScorecardManagerImpl(String ns)</code> constructor.
     * <p>
     * Create a new ScorecardManagerImpl instance without "ValidatorClass" property in the config file. This property
     * is required!
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testScorecardManagerImplFailure3_NamespaceConstructor() throws Exception {
        config.removeNamespace(MANAGEMENT_IMPL);
        config.add("config_bad3.xml");
        try {
            new ScorecardManagerImpl(ScorecardManagerImpl.NAMESPACE);
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>ScorecardManagerImpl(String ns)</code> constructor.
     * <p>
     * Create a new ScorecardManagerImpl instance with bad ScorecardSearchBundle property.
     * </p>
     * <p>
     * Expect ConfigurationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testScorecardManagerImplFailure4_NamespaceConstructor() throws Exception {
        config.removeNamespace(SEACH_BUILDER);
        config.add("config_bad4.xml");
        try {
            new ScorecardManagerImpl(ScorecardManagerImpl.NAMESPACE);
            fail("Expect ConfigurationException.");
        } catch (ConfigurationException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>createScorecard(Scorecard scorecard, String operator)</code> method.
     * <p>
     * Pass a valid scorecard to the manager.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateScorecardAccuracy() throws Exception {
        manager.createScorecard(scorecard, operator);
    }

    /**
     * Failure test of <code>createScorecard(Scorecard scorecard, String operator)</code> method.
     * <p>
     * Call this method with null scorecard.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateScorecardFailure1() throws Exception {
        try {
            manager.createScorecard(null, operator);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createScorecard(Scorecard scorecard, String operator)</code> method.
     * <p>
     * Call this method with empty operator.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateScorecardFailure2() throws Exception {
        try {
            manager.createScorecard(scorecard, " ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>createScorecard(Scorecard scorecard, String operator)</code> method.
     * <p>
     * Call this method with incorrect scorecard (it won't pass the validator's examination).
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testCreateScorecardFailure3() throws Exception {
        scorecard.setVersion("v1.1");
        try {
            manager.createScorecard(scorecard, operator);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>updateScorecard(Scorecard scorecard, String operator)</code> method.
     * <p>
     * Pass a valid scorecard to the manager.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateScorecardAccuracy() throws Exception {
        manager.updateScorecard(scorecard, operator);
    }

    /**
     * Failure test of <code>updateScorecard(Scorecard scorecard, String operator)</code> method.
     * <p>
     * Call this method with null scorecard.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateScorecardFailure1() throws Exception {
        try {
            manager.updateScorecard(null, operator);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateScorecard(Scorecard scorecard, String operator)</code> method.
     * <p>
     * Call this method with empty operator.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateScorecardFailure2() throws Exception {
        try {
            manager.updateScorecard(scorecard, " ");
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>updateScorecard(Scorecard scorecard, String operator)</code> method.
     * <p>
     * Call this method with incorrect scorecard (it won't pass the validator's examination).
     * </p>
     * <p>
     * Expect ValidationException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testUpdateScorecardFailure3() throws Exception {
        scorecard.setVersion("v1.1");
        try {
            manager.updateScorecard(scorecard, operator);
            fail("Expect ValidationException.");
        } catch (ValidationException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getScorecard(long id)</code> method.
     * <p>
     * Simply call this method with a positive id.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetScorecardAccuracy() throws Exception {
        manager.getScorecard(12);
        assertEquals("id passed to persistence incorrect", 12, id);
    }

    /**
     * Failure test of <code>getScorecard(long id)</code> method.
     * <p>
     * Simply call this method with a non-positive id.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetScorecardFailure() throws Exception {
        try {
            manager.getScorecard(-1);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>searchScorecards(Filter filter, boolean complete)</code> method.
     * <p>
     * Call this method with a simple TypeId EqualToFilter. No scorecard should be retrieved, since no scorecard exist
     * in this manager.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSearchScorecardsAccuracy() throws Exception {
        Filter filter = ScorecardSearchBundle.buildTypeIdEqualFilter(1);
        Scorecard[] scorecards = manager.searchScorecards(filter, true);
        assertEquals("No scorecard can be retrieved.", 0, scorecards.length);
    }

    /**
     * Failure test of <code>searchScorecards(Filter filter, boolean complete)</code> method.
     * <p>
     * Call this method with a null filter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testSearchScorecardsFailure() throws Exception {
        try {
            manager.searchScorecards(null, true);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Accuracy test of <code>getAllScorecardTypes()</code> method.
     * <p>
     * Simply call this method, and compare with the desired return value. (This value should be what the *mock* class
     * is forced to return)
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetAllScorecardTypesAccuracy() throws Exception {
        assertEquals("cannot fetch correct scorecard types", returnScorecardTypes, manager.getAllScorecardTypes());
    }

    /**
     * Accuracy test of <code>getAllQuestionTypes()</code> method.
     * <p>
     * Simply call this method, and compare with the desired return value. (This value should be what the *mock* class
     * is forced to return)
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetAllQuestionTypesAccuracy() throws Exception {
        assertEquals("cannot fetch correct question types", returnQuestionTypes, manager.getAllQuestionTypes());
    }

    /**
     * Accuracy test of <code>getAllScorecardStatuses()</code> method.
     * <p>
     * Simply call this method, and compare with the desired return value. (This value should be what the *mock* class
     * is forced to return)
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetAllScorecardStatusesAccuracy() throws Exception {
        assertEquals("cannot fetch correct scorecard statuses", returnScorecardStatuses, manager
            .getAllScorecardStatuses());
    }

    /**
     * Accuracy test of <code>getScorecards(long[] ids, boolean complete)</code> method.
     * <p>
     * Simply call this method with a sample id array.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetScorecardsAccuracy() throws Exception {
        long[] tmpIds = new long[] {4, 5, 6};
        manager.getScorecards(tmpIds, true);
        assertTrue("persistence must be called with complete=true", complete);
        assertTrue("persistence must be called with correct ids", ids != null);
        assertEquals("persistence must be called with correct ids", 3, ids.length);
        assertEquals("persistence must be called with correct ids", 4, ids[0]);
        assertEquals("persistence must be called with correct ids", 5, ids[1]);
        assertEquals("persistence must be called with correct ids", 6, ids[2]);
    }

    /**
     * Failure test of <code>getScorecards(long[] ids, boolean complete)</code> method.
     * <p>
     * Call this method with null parameter.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetScorecardsFailure1() throws Exception {
        try {
            manager.getScorecards(null, true);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getScorecards(long[] ids, boolean complete)</code> method.
     * <p>
     * Call this method with empty array.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetScorecardsFailure2() throws Exception {
        try {
            manager.getScorecards(new long[] {}, true);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }

    /**
     * Failure test of <code>getScorecards(long[] ids, boolean complete)</code> method.
     * <p>
     * Call this method with non-positive element.
     * </p>
     * <p>
     * Expect IllegalArgumentException.
     * </p>
     * @throws Exception
     *             throw exception to JUnit.
     */
    public void testGetScorecardsFailure3() throws Exception {
        try {
            manager.getScorecards(new long[] {2, -1}, true);
            fail("Expect IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // expect
        }
    }
}
