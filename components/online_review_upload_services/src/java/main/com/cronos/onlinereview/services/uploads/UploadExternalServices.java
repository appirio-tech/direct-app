/*
 * Copyright (C) 2007-2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.services.uploads;

import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.resource.Resource;

import javax.activation.DataHandler;
import java.rmi.RemoteException;
import java.util.Set;

/**
 * <p>
 * Defines the web service contract for managing different type of uploads and set status of submissions.
 * </p>
 *
 * <p>
 * Changes in version 1.1: Added
 * <code>{@link UploadExternalServices#uploadSpecification(long, long, String, DataHandler)}</code> method.
 * </p>
 *
 * <p>
 * Thread safety: the implementations must be thread safe.
 * </p>
 *
 * @author fabrizyo, saarixx, cyberjag, TCSDEVELOPER
 * @version 1.1
 * @since 1.0
 */
public interface UploadExternalServices {

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     * <p>
     * If the project allows multiple submissions for users, it will add the new submission and return. If multiple
     * submission are not allowed for the project, firstly it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     *
     * @param projectId  the project's id
     * @param userId     the user's id
     * @param filename   the file name to use
     * @param submission the submission file data
     *
     * @return the id of the new submission
     *
     * @throws InvalidProjectException      if the project does not exist.
     * @throws InvalidProjectPhaseException if neither Submission or Screening phase are opened
     * @throws InvalidUserException         if the user does not exist or has not the submitter role
     * @throws PersistenceException         if some error occurs in persistence layer
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws RemoteException              if an internal exception occurs (wrap it)
     * @throws IllegalArgumentException     if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @since 1.0
     */
    public long uploadSubmission(long projectId, long userId, String filename, DataHandler submission)
        throws RemoteException, UploadServicesException;

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     *
     * @param projectId the project's id
     * @param userId    the user's id
     * @param filename  the file name to use
     * @param finalFix  the final fix file data
     *
     * @return the id of the created final fix submission
     *
     * @throws InvalidProjectException      if the project does not exist
     * @throws InvalidProjectPhaseException if Final Fix phase is not opened
     * @throws InvalidUserException         if the user doesn't exists or she/he is not winner submitter
     * @throws PersistenceException         if some error occurs in persistence layer
     * @throws RemoteException              if an internal exception occurs (wrap it)
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws IllegalArgumentException     if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @since 1.0
     */
    long uploadFinalFix(long projectId, long userId, String filename, DataHandler finalFix)
        throws RemoteException, UploadServicesException;

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     *
     * @param projectId the project's id
     * @param userId    the user's id
     * @param filename  the file name to use
     * @param testCases the test cases data
     *
     * @return the id of the created test cases submission
     *
     * @throws InvalidProjectException      if the project does not exist
     * @throws InvalidProjectPhaseException if Review phase is not opened
     * @throws InvalidUserException         if the user does not exist or has not the reviewer role
     * @throws PersistenceException         if some error occurs in persistence layer
     * @throws RemoteException              if an internal exception occurs (wrap it)
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws IllegalArgumentException     if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @since 1.0
     */
    long uploadTestCases(long projectId, long userId, String filename, DataHandler testCases)
        throws RemoteException, UploadServicesException;


    /**
     * <p>Adds a new specification upload for a user in a particular project. This
     * submission always overwrite the previous ones.</p>
     *
     * @param projectId     the project's id
     * @param userId        the user's id.
     * @param filename      the file name to use.
     * @param specification the specification data
     *
     * @return the id of the created specification submission.
     *
     * @throws IllegalArgumentException     if any id is < 0, if any argument is null or trim to empty
     * @throws InvalidProjectException      if the project doesn't exist.
     * @throws InvalidProjectPhaseException if Specification Submission phase isn't opened.
     * @throws InvalidUserException         if the user doesn't exist or hasn't the Specification Submitter role.
     * @throws PersistenceException         if some error occurs in persistence layer
     * @throws RemoteException              if an internal exception occurs (wrap it)
     * @throws UploadServicesException      if any other error occurs from UploadServices
     * @since 1.1
     */
    long uploadSpecification(long projectId, long userId, String filename, DataHandler specification)
        throws RemoteException, UploadServicesException;

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     *
     * @param submissionId       the submission's id
     * @param submissionStatusId the submission status id
     * @param operator           the operator which execute the operation
     *
     * @throws RemoteException            if an internal exception occurs
     * @throws InvalidSubmissionException if the submission does not exist
     * @throws InvalidSubmissionStatusException
     *                                    if the submission status does not exist
     * @throws PersistenceException       if some error occurs in persistence layer
     * @throws RemoteException            if an internal exception occurs (wrap it)
     * @throws IllegalArgumentException   if any id is &lt; 0 or if operator is null or trim to empty
     * @since 1.0
     */
    void setSubmissionStatus(long submissionId, long submissionStatusId, String operator)
        throws RemoteException, InvalidSubmissionException, InvalidSubmissionStatusException, PersistenceException;

    /**
     * Adds the given user as a new submitter to the given project id.
     *
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     *
     * @return the added resource id
     *
     * @throws RemoteException              if an internal exception occurs (wrap it)
     * @throws InvalidProjectException      if the project id is unknown
     * @throws InvalidUserException         if the user id is unknown
     * @throws InvalidProjectPhaseException if the phase of the project is not Registration.
     * @throws UploadServicesException      if any error occurs from UploadServices
     * @throws IllegalArgumentException     if any id is &lt; 0
     * @since 1.0
     */
    long addSubmitter(long projectId, long userId)
        throws RemoteException, InvalidProjectException, InvalidUserException, InvalidProjectPhaseException,
        UploadServicesException;

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

    /**
     * Remove all submitters for a given project
     *
     * @param projectId the project id
     * @param operator  user whos added
     * @return
     * @throws InvalidProjectException
     * @throws UploadServicesException
     * @throws InvalidUserException
     * @throws InvalidProjectPhaseException
     * @since 1.1.2
     */
    Set<Long> removeAllSubmitters(long projectId, String operator) throws UploadServicesException;
}
