/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.data.accuracytests;

import com.topcoder.management.scorecard.data.QuestionType;

import junit.framework.TestCase;


/**
 * Tests for QuestionType class.
 *
 * @author crackme
 * @version 1.0
 */
public class TestQuestionType extends TestCase {
    /** the id of this instance. */
    private final long id = new Long(1234567).longValue();

    /**
     * Tests QuestionType() method with accuracy state.
     */
    public void testQuestionTypeAccuracy1() {
        QuestionType type = new QuestionType();
        assertNull("constructor is wrong.", type.getName());
        assertEquals("constructor is wrong.", -1, type.getId());
    }

    /**
     * Tests QuestionType(long id) method with accuracy state.
     */
    public void testQuestionTypeAccuracy2() {
        QuestionType type = new QuestionType(id);
        assertNull("constructor is wrong.", type.getName());
        assertEquals("constructor is wrong.", id, type.getId());
    }

    /**
     * Tests QuestionType(long id, String name) method with accuracy state.
     */
    public void testQuestionTypeAccuracy3() {
        QuestionType type = new QuestionType(id, "new name");
        assertEquals("constructor is wrong.", "new name", type.getName());
        assertEquals("constructor is wrong.", id, type.getId());
    }
}
