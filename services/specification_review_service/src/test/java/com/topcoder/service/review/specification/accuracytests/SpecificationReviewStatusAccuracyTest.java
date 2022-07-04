/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification.accuracytests;

import java.util.Arrays;
import java.util.List;

import com.topcoder.service.review.specification.SpecificationReviewStatus;

import junit.framework.TestCase;

/**
 * <p>
 * Accuracy tests for class <code>SpecificationReviewStatus</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class SpecificationReviewStatusAccuracyTest extends TestCase {

    /**
     * <p>
     * Accuracy test for the size of enum <code>SpecificationReviewStatus</code>.
     * </p>
     */
    public void testLookupValueTypeSize() {
        // test the size of the enum
        assertEquals("The number of enum element is incorrect.", 2,
            SpecificationReviewStatus.values().length);
    }

    /**
     * <p>
     * Accuracy test for the elements of enum <code>SpecificationReviewStatus</code>.
     * </p>
     */
    public void testLookupValueTypeElem() {
        // test the enum elements
        List<SpecificationReviewStatus> list = Arrays.asList(SpecificationReviewStatus.values());
        assertTrue("WAITING_FOR_FIXES should be contained in enum", list
            .contains(SpecificationReviewStatus.WAITING_FOR_FIXES));
        assertTrue("PENDING_REVIEW should be contained in enum", list
            .contains(SpecificationReviewStatus.PENDING_REVIEW));
    }
}