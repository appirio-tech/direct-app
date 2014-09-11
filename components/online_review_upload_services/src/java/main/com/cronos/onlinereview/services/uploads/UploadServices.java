/*
 * Copyright (C) 2007-2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads;

import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.resource.Resource;


/**
 * <p>
 * Defines the contract for managing different type of uploads and set status of submissions.
 * </p>
 *
 * <p>
 * Changes in version 1.1: Added <code>{@link UploadServices#uploadSpecification(long, long, String)}</code> method.
 * </p>
 *
 * <p>
 * Version 1.1.1 (Manage Copilot Postings Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #addReviewer(long, long)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Thread safety: the implementations must be thread safe.
 * </p>
 *
 * @author fabrizyo, saarixx, cyberjag, TCSDEVELOPER
 * @version 1.1.1
 * @since 1.0
 */
public interface UploadServices {

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     *
     * <p>
     * If the project allows multiple submissions for users, it will add the new submission and return. If multiple
     * submission are not allowed for the project firstly, it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     *
     * @param projectId the project's id
     * @param userId    the user's id
     * @param filename  the file name to use
     *
     * @return the id of the new submission
     *
     * @throws InvalidProjectException      if the project does not exist
     * @throws InvalidProjectPhaseException if neither Submission or Screening phase are opened
     * @throws InvalidUserException         if the user does not exist or has not the submitter role
     * @throws PersistenceException         if some error occurs in persistence layer
     * @throws UploadServicesException      if some other exception occurs in the process (wrap it)
     * @throws IllegalArgumentException     if any id is &lt; 0, if filename is <code>null</code> or trim to empty
     * @since 1.0
     */
    long uploadSubmission(long projectId, long userId, String filename) throws UploadServicesException;

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     *
     * @param projectId the project's id
     * @param userId    the user's id
     * @param filename  the file name to use
     *
     * @return the id of the created final fix submission.
     *
     * @throws InvalidProjectException      if the project does not exist
     * @throws InvalidProjectPhaseException if Final Fix phase is not opened
     * @throws InvalidUserException         if the user does not exist or she/he is not winner submitter
     * @throws PersistenceException         if some error occurs in persistence layer
     * @throws UploadServicesException      if some other exception occurs in the process (wrap it)
     * @throws IllegalArgumentException     if any id is &lt; 0, if filename is <code>null</code> or trim to empty
     * @since 1.0
     */
    long uploadFinalFix(long projectId, long userId, String filename) throws UploadServicesException;

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     *
     * @param projectId the project's id
     * @param userId    the user's id
     * @param filename  the file name to use
     *
     * @return the id of the created test cases submission
     *
     * @throws InvalidProjectException      if the project does not exist
     * @throws InvalidProjectPhaseException if Review phase is not opened
     * @throws InvalidUserException         if the user does not exist or has not the reviewer role
     * @throws PersistenceException         if some error occurs in persistence layer
     * @throws UploadServicesException      if some other exception occurs in the process (wrap it)
     * @throws IllegalArgumentException     if any id is &lt; 0, if filename is <code>null</code> or trim to empty
     * @since 1.0
     */
    long uploadTestCases(long projectId, long userId, String filename) throws UploadServicesException;

    /**
     * <p>Adds a specification submission for a user in a particular project.</p>
     *
     * @param projectId the project's id
     * @param userId    the user's id.
     * @param filename  the file name to use.
     *
     * @return the id of the new submission.
     *
     * @throws InvalidProjectException      if the project doesn't exist.
     * @throws InvalidProjectPhaseException if Specification Submission phase is not opened.
     * @throws InvalidUserException         if the user doesn't exist or hasn't the Specification Submitter role.
     * @throws PersistenceException         if some error occurs in persistence layer
     * @throws UploadServicesException      if some other exception occurs in the process (wrap it)
     * @throws IllegalArgumentException     if any id is < 0, if filename is null or trim to empty
     * @since 1.1
     */
    long uploadSpecification(long projectId, long userId, String filename) throws UploadServicesException;

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     *
     * @param submissionId       the submission's id
     * @param submissionStatusId the submission status id
     * @param operator           the operator which execute the operation
     *
     * @throws InvalidSubmissionException if the submission does not exist
     * @throws InvalidSubmissionStatusException
     *                                    if the submission status does not exist
     * @throws PersistenceException       if some error occurs in persistence layer
     * @throws IllegalArgumentException   if any id is &lt; 0 or if operator is null or trim to empty
     * @since 1.0
     */
    void setSubmissionStatus(long submissionId, long submissionStatusId, String operator)
         throws InvalidSubmissionException, InvalidSubmissionStatusException, PersistenceException;


    /**
     * Adds the given user as a new submitter to the given project id.
     *
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     *
     * @return the added resource id
     *
     * @throws InvalidProjectException      if the project id is unknown
     * @throws InvalidUserException         if the user id is unknown
     * @throws InvalidProjectPhaseException if the phase of the project is not Registration.
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws IllegalArgumentException     if any id is &lt; 0
     * @since 1.0
     */
    long addSubmitter(long projectId, long userId) throws InvalidProjectException,
            InvalidUserException, UploadServicesException, InvalidProjectPhaseException;

    /**
     * Adds the given user as a new reviewer to the given project id.
     *
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     * @return the added resource id
     * @throws InvalidProjectException      if the project id is unknown
     * @throws InvalidUserException         if the user id is unknown
     * @throws InvalidProjectPhaseException if the phase of the project is not Registration.
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws PhaseManagementException if an unexpected error occurs.
     * @throws IllegalArgumentException     if any id is &lt; 0
     * @since 1.1.1
     */
    Resource addReviewer(long projectId, long userId) throws UploadServicesException, PhaseManagementException;

    /**
     * Adds the given user as a primary screener to the given project id.
     *
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     * @return the added resource id
     * @throws InvalidProjectException      if the project id is unknown
     * @throws InvalidUserException         if the user id is unknown
     * @throws InvalidProjectPhaseException if the phase of the project is not Registration.
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws PhaseManagementException if an unexpected error occurs.
     * @throws IllegalArgumentException     if any id is &lt; 0
     * @since 1.1.1
     */
    Resource addPrimaryScreener(long projectId, long userId) throws UploadServicesException, PhaseManagementException;
}
