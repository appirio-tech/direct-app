/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.accuracytests;

import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Section;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;


/**
 * Tests for Section class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestSection extends TestCase {
    /** Section instance used to test. */
    private Section section = null;

    /** Question instance used to test. */
    private Question question1 = new Question(777771, "elegant api1", 56.788f);

    /** Question instance used to test. */
    private Question question2 = new Question(777772, "elegant api2", 56.789f);

    /** Question instances used to test. */
    private Question[] questions = new Question[] { question1, question2 };

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        section = new Section(777773, "design document", 56.789f);
    }

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests Section() method with accuracy state.
     */
    public void testSectionAccuracy1() {
        Section s = new Section();
        assertNull("constructor is wrong.", s.getName());
        assertEquals("constructor is wrong.", -1, s.getId());
    }

    /**
     * Tests Section(long id) method with accuracy state.
     */
    public void testSectionAccuracy2() {
        Section s = new Section(777777);
        assertNull("constructor1 is wrong.", s.getName());
        assertEquals("constructor2 is wrong.", 777777, s.getId());
    }

    /**
     * Tests Section(long id, String name) method with accuracy state.
     */
    public void testSectionAccuracy3() {
        Section s = new Section(777777, "new section");
        assertEquals("constructor1 is wrong.", "new section", s.getName());
        assertEquals("constructor2 is wrong.", 777777, s.getId());
    }

    /**
     * Tests Section(long id, float weight) method with accuracy state.
     */
    public void testSectionAccuracy4() {
        Section s = new Section(777777, 56.432f);
        assertNull("constructor1 is wrong.", s.getName());
        assertEquals("constructor2 is wrong.", 777777, s.getId());
        assertEquals("constructor is wrong.", 56.432f, s.getWeight(), 0);
    }

    /**
     * Tests addQuestion(Question question) method with accuracy state.
     */
    public void testAddQuestionAccuracy1() {
        Question[] qs = section.getAllQuestions();
        assertEquals("addQuestion1 is wrong.", 0, qs.length);

        section.addQuestion(question1);
        assertEquals("addQuestion2 is wrong.", 1, section.getAllQuestions().length);
        assertEquals("addQuestion3 is wrong.", question1, section.getQuestion(0));
    }
    
    /**
     * Tests addQuestion(Question question) method with accuracy state.
     */
    public void testAddQuestionAccuracy2() {
        Question[] qs = section.getAllQuestions();
        assertEquals("addQuestion1 is wrong.", 0, qs.length);

        section.addQuestion(question1);
        assertEquals("addQuestion2 is wrong.", 1, section.getAllQuestions().length);
        assertEquals("addQuestion3 is wrong.", question1, section.getQuestion(0));
        
        section.addQuestion(question1);
        assertEquals("addQuestion2 is wrong.", 1, section.getAllQuestions().length);
        assertEquals("addQuestion3 is wrong.", question1, section.getQuestion(0));
    }

    /**
     * Tests addQuestions(Question[] questions) method with accuracy state.
     */
    public void testAddQuestionsAccuracy1() {
        assertEquals("addQuestions1 is wrong.", 0, section.getAllQuestions().length);

        section.addQuestions(questions);

        Question[] qs = section.getAllQuestions();
        assertEquals("addQuestions2 is wrong.", 2, qs.length);

        List ques = Arrays.asList(qs);
        assertTrue("addQuestions3 is wrong.", ques.indexOf(question1) != -1);
        assertTrue("addQuestions4 is wrong.", ques.indexOf(question2) != -1);
    }
    
    /**
     * Tests addQuestions(Question[] questions) method with accuracy state.
     */
    public void testAddQuestionsAccuracy2() {
        assertEquals("addQuestions1 is wrong.", 0, section.getAllQuestions().length);

        section.addQuestions(questions);

        Question[] qs = section.getAllQuestions();
        assertEquals("addQuestions2 is wrong.", 2, qs.length);

        List ques = Arrays.asList(qs);
        assertTrue("addQuestions3 is wrong.", ques.indexOf(question1) != -1);
        assertTrue("addQuestions4 is wrong.", ques.indexOf(question2) != -1);
        
        section.addQuestions(questions);

        Question[] qs2 = section.getAllQuestions();
        assertEquals("addQuestions2 is wrong.", 2, qs2.length);

        List ques2 = Arrays.asList(qs);
        assertTrue("addQuestions3 is wrong.", ques2.indexOf(question1) != -1);
        assertTrue("addQuestions4 is wrong.", ques2.indexOf(question2) != -1);
    }

    /**
     * Tests removeQuestion(Question question) method with accuracy state.
     */
    public void testRemoveQuestion1Accuracy1() {
        assertEquals("removeQuestion1 is wrong.", 0, section.getAllQuestions().length);

        section.addQuestions(questions);

        Question[] qs = section.getAllQuestions();
        assertEquals("removeQuestion2 is wrong.", 2, qs.length);

        List ques = Arrays.asList(qs);
        assertTrue("removeQuestion3 is wrong.", ques.indexOf(question1) != -1);
        assertTrue("removeQuestion4 is wrong.", ques.indexOf(question2) != -1);

        section.removeQuestion(question2);

        assertEquals("removeQuestion5 is wrong.", 1, section.getAllQuestions().length);
        assertEquals("removeQuestion6 is wrong.", question1, section.getQuestion(0));
    }
    
    /**
     * Tests removeQuestion(Question question) method with accuracy state.
     */
    public void testRemoveQuestion1Accuracy2() {
        assertEquals("removeQuestion1 is wrong.", 0, section.getAllQuestions().length);

        section.addQuestions(questions);

        Question[] qs = section.getAllQuestions();
        assertEquals("removeQuestion2 is wrong.", 2, qs.length);

        List ques = Arrays.asList(qs);
        assertTrue("removeQuestion3 is wrong.", ques.indexOf(question1) != -1);
        assertTrue("removeQuestion4 is wrong.", ques.indexOf(question2) != -1);

        Question qq = new Question(8743);
        section.removeQuestion(qq);

        Question[] qs2 = section.getAllQuestions();
        assertEquals("removeQuestion2 is wrong.", 2, qs2.length);

        List ques2 = Arrays.asList(qs);
        assertTrue("removeQuestion3 is wrong.", ques2.indexOf(question1) != -1);
        assertTrue("removeQuestion4 is wrong.", ques2.indexOf(question2) != -1);
    }

    /**
     * Tests removeQuestions(Question[] questions) method with accuracy state.
     */
    public void testRemoveQuestionsAccuracy() {
        assertEquals("removeQuestions1 is wrong.", 0, section.getAllQuestions().length);

        section.addQuestions(questions);

        Question question3 = new Question(777774);
        section.addQuestion(question3);

        assertEquals("removeQuestions2 is wrong.", 3, section.getAllQuestions().length);

        Question[] remove = new Question[] { question1, question3 };
        section.removeQuestions(remove);

        assertEquals("removeQuestions3 is wrong.", 1, section.getAllQuestions().length);
        assertEquals("removeQuestions4 is wrong.", question2, section.getQuestion(0));
    }

    /**
     * Tests clearQuestions() method with accuracy state.
     */
    public void testClearQuestionsAccuracy() {
        assertEquals("clearQuestions1 is wrong.", 0, section.getAllQuestions().length);

        section.addQuestions(questions);

        Question question3 = new Question(777774);
        section.addQuestion(question3);

        assertEquals("clearQuestions2 is wrong.", 3, section.getAllQuestions().length);

        section.clearQuestions();

        assertEquals("clearQuestions3 is wrong.", 0, section.getAllQuestions().length);
    }

    /**
     * Tests getQuestion(int questionIndex) method with accuracy state.
     */
    public void testGetQuestionAccuracy() {
        assertEquals("getQuestion1 is wrong.", 0, section.getAllQuestions().length);

        section.addQuestions(questions);

        Question question3 = new Question(777774);
        section.addQuestion(question3);

        assertEquals("getQuestion2 is wrong.", 3, section.getAllQuestions().length);
        assertEquals("getQuestion3 is wrong.", question3, section.getQuestion(2));
    }

    /**
     * Tests getAllQuestions() method with accuracy state.
     */
    public void testGetAllQuestionsAccuracy() {
        assertEquals("getAllQuestions1 is wrong.", 0, section.getAllQuestions().length);

        section.addQuestions(questions);

        Question question3 = new Question(777774);
        section.addQuestion(question3);

        Question[] qs = section.getAllQuestions();
        assertEquals("getAllQuestions2 is wrong2.", 3, qs.length);

        List ques = Arrays.asList(qs);
        assertTrue("getAllQuestions3 is wrong.", ques.indexOf(question1) != -1);
        assertTrue("getAllQuestions4 is wrong.", ques.indexOf(question2) != -1);
        assertTrue("getAllQuestions5 is wrong.", ques.indexOf(question3) != -1);
    }

    /**
     * Tests getNumberOfQuestions() method with accuracy state.
     */
    public void testGetNumberOfQuestionsAccuracy() {
        assertEquals("getNumberOfQuestions1 is wrong.", 0,
            section.getAllQuestions().length);

        section.addQuestions(questions);

        assertEquals("getNumberOfQuestions2 is wrong.", 2, section.getNumberOfQuestions());

        Question question3 = new Question(777774);
        section.addQuestion(question3);

        assertEquals("getNumberOfQuestions3 is wrong.", 3, section.getNumberOfQuestions());
    }

    /**
     * Tests checkWeights(float tolerance) method with accuracy state.
     */
    public void testCheckWeightsAccuracy1() {
        section.addQuestions(questions);
        assertFalse("checkWeights is wrong", section.checkWeights(13.576f));
    }

    /**
     * Tests checkWeights(float tolerance) method with accuracy state.
     */
    public void testCheckWeightsAccuracy2() {
        section.addQuestions(questions);

        // question1 + question2 = 56.789 + 56.789 = 113.578
        assertTrue("checkWeights is wrong", section.checkWeights(13.578f));
    }

    /**
     * Tests insertQuestion(Question question, int index) method with accuracy state.
     */
    public void testInsertQuestionAccuracy() {
        assertEquals("insertQuestion1 is wrong.", 0, section.getAllQuestions().length);

        section.addQuestions(questions);

        Question question3 = new Question(777774);
        section.insertQuestion(question3, 1);

        assertEquals("insertQuestion2 is wrong.", 3, section.getNumberOfQuestions());
        assertEquals("insertQuestion3 is wrong.", question3, section.getQuestion(1));
    }

    /**
     * Tests removeQuestion(int index) method with accuracy state.
     */
    public void testRemoveQuestion2Accuracy() {
        assertEquals("removeQuestion1 is wrong.", 0, section.getAllQuestions().length);

        section.addQuestions(questions);

        assertEquals("removeQuestion2 is wrong.", 2, section.getNumberOfQuestions());

        section.removeQuestion(1);

        assertEquals("removeQuestion3 is wrong.", 1, section.getNumberOfQuestions());
        assertEquals("removeQuestion4 is wrong.", question1, section.getQuestion(0));
    }
}
