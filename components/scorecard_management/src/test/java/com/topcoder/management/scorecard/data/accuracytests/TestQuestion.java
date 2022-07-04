/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.accuracytests;

import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.QuestionType;

import junit.framework.TestCase;


/**
 * Tests for Question class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestQuestion extends TestCase {
    /** the weight of this instance. */
    private final float weight = 0.1234567f;

    /** Question instance used to test. */
    private Question question = null;

    /** QuestionType instance used to test. */
    private QuestionType type = null;

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
        question = new Question(1234567, "question", weight);
        type = new QuestionType(1234568, "type");
    }

    /**
     * Setup the test environment.
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        type = null;
        question = null;
    }

    /**
     * Tests Question() method with accuracy state.
     */
    public void testQuestionAccuracy1() {
        Question q = new Question();
        assertNull("constructor is wrong.", q.getName());
        assertEquals("constructor is wrong.", -1, q.getId());
    }

    /**
     * Tests Question(long id) method with accuracy state.
     */
    public void testQuestionAccuracy2() {
        Question q = new Question(1234567);
        assertNull("constructor is wrong.", q.getName());
        assertEquals("constructor is wrong.", 1234567, q.getId());
    }

    /**
     * Tests Question(long id, String name) method with accuracy state.
     */
    public void testQuestionAccuracy3() {
        Question q = new Question(1234567, "new name");
        assertEquals("constructor is wrong.", "new name", q.getName());
        assertEquals("constructor is wrong.", 1234567, q.getId());
    }

    /**
     * Tests Question(long id, float weight) method with accuracy state.
     */
    public void testQuestionAccuracy4() {
        Question q = new Question(1234567, weight);
        assertNull("constructor is wrong.", q.getName());
        assertEquals("constructor is wrong.", 1234567, q.getId());
        assertEquals("constructor is wrong.", weight, q.getWeight(), 0);
    }

    /**
     * Tests setQuestionType(QuestionType questionType) method with accuracy state.
     */
    public void testSetQuestionTypeAccuracy() {
        QuestionType newType = new QuestionType();
        question.setQuestionType(newType);
        assertEquals("setQuestionType is wrong.", newType, question.getQuestionType());
    }

    /**
     * Tests resetQuestionType() method with accuracy state.
     */
    public void testResetQuestionTypeAccuracy() {
        question.resetQuestionType();
        assertNull("constructor is wrong.", question.getQuestionType());
    }

    /**
     * Tests getQuestionType() method with accuracy state.
     */
    public void testGetQuestionTypeAccuracy() {
        question.setQuestionType(type);
        assertEquals("getQuestionType is wrong.", type, question.getQuestionType());
    }

    /**
     * Tests setDescription(String description) method with accuracy state.
     */
    public void testSetDescriptionAccuracy() {
        question.setDescription("new desc");
        assertEquals("setDescription is wrong.", "new desc", question.getDescription());
    }

    /**
     * Tests resetDescription() method with accuracy state.
     */
    public void testResetDescriptionAccuracy() {
        question.setDescription("new desc");
        assertEquals("resetDescription is wrong.", "new desc", question.getDescription());

        question.resetDescription();
        assertNull("resetDescription is wrong.", question.getDescription());
    }

    /**
     * Tests getDescription() method with accuracy state.
     */
    public void testGetDescriptionAccuracy() {
        assertNull("getDescription is wrong.", question.getDescription());
    }

    /**
     * Tests setGuideline(String guideline) method with accuracy state.
     */
    public void testSetGuidelineAccuracy() {
        question.setGuideline("new desc");
        assertEquals("setGuideline is wrong.", "new desc", question.getGuideline());
    }

    /**
     * Tests resetGuideline() method with accuracy state.
     */
    public void testResetGuidelineAccuracy() {
        question.setGuideline("new desc");
        assertEquals("resetGuideline is wrong.", "new desc", question.getGuideline());

        question.resetGuideline();
        assertNull("resetGuideline is wrong.", question.getGuideline());
    }

    /**
     * Tests getGuideline() method with accuracy state.
     */
    public void testGetGuidelineAccuracy() {
        assertNull("getGuideline is wrong.", question.getGuideline());
    }

    /**
     * Tests setUploadDocument(boolean uploadDocument) method with accuracy state.
     */
    public void testSetUploadDocumentAccuracy() {
        question.setUploadDocument(true);
        assertTrue("isUploadDocument is wrong.", question.isUploadDocument());
    }

    /**
     * Tests resetUploadDocument() method with accuracy state.
     */
    public void testResetUploadDocumentAccuracy() {
        question.setUploadDocument(true);
        assertTrue("resetUploadDocument is wrong.", question.isUploadDocument());

        question.resetUploadDocument();
        assertFalse("resetUploadDocument is wrong.", question.isUploadDocument());
    }

    /**
     * Tests isUploadDocument() method with accuracy state.
     */
    public void testIsUploadDocumentAccuracy() {
        assertFalse("isUploadDocument is wrong.", question.isUploadDocument());
    }

    /**
     * Tests setUploadRequired(boolean uploadRequired) method with accuracy state.
     */
    public void testSetUploadRequiredAccuracy() {
        question.setUploadRequired(true);
        assertTrue("setUploadRequired is wrong.", question.isUploadRequired());
    }

    /**
     * Tests resetUploadRequired() method with accuracy state.
     */
    public void testResetUploadRequiredAccuracy() {
        question.setUploadRequired(true);
        assertTrue("setUploadRequired is wrong.", question.isUploadRequired());

        question.resetUploadRequired();
        assertFalse("setUploadRequired is wrong.", question.isUploadRequired());
    }

    /**
     * Tests isUploadRequired() method with accuracy state.
     */
    public void testIsUploadRequiredAccuracy() {
        assertFalse("isUploadRequired is wrong.", question.isUploadRequired());
    }
}
