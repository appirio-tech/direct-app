/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.topcoder.security.TCSubject;
import com.topcoder.service.review.specification.ejb.BaseTest;
import com.topcoder.service.review.specification.ejb.SpecificationReviewServiceRemote;

/**
 * Tests demo of this component.
 *
 * @author saarixx, myxgyy
 * @version 1.0
 */
public class Demo extends BaseTest {
    /**
     * Represents the ear name.
     */
    private static final String EAR_NAME = "specification_review_service";

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception
     *             throws exception if any.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests the API usage.
     *
     * @throws Exception
     *             to JUnit.
     */
    @SuppressWarnings("unused")
    public void testAPIUsage() throws Exception {
        // Get specification review service
        Context context = new InitialContext();
        SpecificationReviewService specificationReviewService = (SpecificationReviewServiceRemote) context
            .lookup(EAR_NAME + "/SpecificationReviewServiceBean/remote");

        TCSubject tcSubject = new TCSubject(null, 1001L);
        // Schedule specification review for the project with ID=1 to start immediately
        long projectId = 1;
        Date reviewStartDate = new Date();
        specificationReviewService.scheduleSpecificationReview(tcSubject, projectId, reviewStartDate);
        // Retrieve the open specification review positions
        List<Long> projectIds = specificationReviewService
            .getOpenSpecificationReviewPositions(tcSubject);
        // projectIds must contain 1
        // Assume that specification reviewer registered for this project

        // Retrieve the open specification review positions again
        projectIds = specificationReviewService.getOpenSpecificationReviewPositions(tcSubject);
        // projectIds must not contain 1
        // Assume that specification was rejected here

        // Re-submit specification as file for this project
        String filename = "sample_component_1.0_requirements_specification.rtf";
        long submissionId = specificationReviewService.submitSpecificationAsFile(tcSubject, projectId,
            filename);
        // Retrieve the specification review status
        SpecificationReviewStatus specificationReviewStatus = specificationReviewService
            .getSpecificationReviewStatus(tcSubject, projectId);
        // specificationReviewStatus must be SpecificationReviewStatus.PENDING_REVIEW
        // Assume that specification was rejected here again

        // Retrieve the specification review status
        specificationReviewStatus = specificationReviewService.getSpecificationReviewStatus(tcSubject,
            projectId);
        // specificationReviewStatus must be SpecificationReviewStatus.WAITING_FOR_FIXES
        // Re-submit specification as content string
        String content = "This is a text for sample specification";
        submissionId = specificationReviewService.submitSpecificationAsString(tcSubject, projectId,
            content);
        // Assume that specification was accepted here

        // Retrieve the specification review for this project
        SpecificationReview specificationReview = specificationReviewService.getSpecificationReview(
            tcSubject, projectId);
        // specificationReview.getReview() should not be not
        // specificationReview.getScorecard() should not be null
    }
}