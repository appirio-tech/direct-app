/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.review.specification;

import java.util.Date;
import java.util.List;

import com.topcoder.security.TCSubject;

/**
 * <p>
 * This interface represents a specification review service. It provides methods for
 * scheduling specification review, submitting specification as a file or a string,
 * retrieving specification review, retrieving specification review status and retrieving
 * IDs of projects for which specification review positions are open.
 * </p>
 * <p>
 * Thread Safety: Implementations of this interface must be thread safe.
 * </p>
 *
 * @author saarixx, myxgyy
 * @version 1.0
 */
public interface SpecificationReviewService {
    /**
     * Schedules a specification review for the given project on the given date.
     *
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @param reviewStartDate
     *            the date the review is to begin (immediately if null or in the past).
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId is not positive.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public void scheduleSpecificationReview(TCSubject tcSubject, long projectId, boolean startnow)
        throws SpecificationReviewServiceException;

    /**
     * Uploads a specification in the form of a file, for the given project by the given
     * user. This method can be used for an initial submission or an updated submission.
     *
     * @param tcSubject
     *            the user making the request.
     * @param filename
     *            the name of the file with the submission to upload.
     * @param projectId
     *            the ID of the project.
     * @return the submission ID.
     * @throws IllegalArgumentException
     *             if tcSubject is null, or projectId is not positive, or if filename is
     *             null or empty.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public long submitSpecificationAsFile(TCSubject tcSubject, long projectId, String filename)
        throws SpecificationReviewServiceException;

    /**
     * Uploads a specification in the form of a String, for the given project by the given
     * user. This method can be used for an initial submission or an updated submission.
     *
     * @param content
     *            the content to upload.
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @return the submission ID.
     * @throws IllegalArgumentException
     *             if tcSubject is null, or projectId is not positive, or content is null
     *             or empty.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public long submitSpecificationAsString(TCSubject tcSubject, long projectId, String content)
        throws SpecificationReviewServiceException;

    /**
     * Uploads a mock specification, for the given project by the given
     * user. This method can be used for an initial submission or an updated submission.
     *
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @return the submission ID.
     * @throws IllegalArgumentException
     *             if tcSubject is null, or projectId is not positive, or content is null
     *             or empty.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public long resubmitSpecification(TCSubject tcSubject, long projectId)
        throws SpecificationReviewServiceException;        
        
    /**
     * Gets a specification review, including the scorecard structure as well as the
     * review content. Returns null if not found.
     *
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @return the entity with scorecard and review (null if not found).
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId is not positive.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public SpecificationReview getSpecificationReview(TCSubject tcSubject, long projectId)
        throws SpecificationReviewServiceException;

    /**
     * Gets the specification review status of the given project. It can be in
     * specification submission phase, or specification review phase, or null if neither.
     *
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @return the status of the specification review (null if not (&quot;waiting for fixes&quot; or
     *         &quot;pending review&quot;)).
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId is not positive.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public SpecificationReviewStatus getSpecificationReviewStatus(TCSubject tcSubject, long projectId)
        throws SpecificationReviewServiceException;

    /**
     * Gets the IDs of all projects whose specification review positions are yet not
     * filled. Will return an empty list if there are no such projects.
     *
     * @param tcSubject
     *            the user making the request.
     * @return the IDs of all projects whose specification review positions are yet not
     *         filled (not null; doesn't contain null).
     * @throws IllegalArgumentException
     *             if tcSubject is null.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public List<Long> getOpenSpecificationReviewPositions(TCSubject tcSubject)
        throws SpecificationReviewServiceException;
    

    /**
     * Gets all specification reviews, including the scorecard structure as well as the
     * review content. Returns a empty list if not found.
     *
     * @param tcSubject
     *            the user making the request.
     * @param projectId
     *            the ID of the project.
     * @return the list of the entity with scorecard and review (empty if not found).
     * @throws IllegalArgumentException
     *             if tcSubject is null or projectId is not positive.
     * @throws SpecificationReviewServiceException
     *             if there are any errors during this operation.
     */
    public List<SpecificationReview> getAllSpecificationReviews(TCSubject tcSubject, long projectId)
        throws SpecificationReviewServiceException;
}
