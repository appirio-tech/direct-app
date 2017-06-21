/*
 * Copyright (C) 2009 - 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.activation.DataHandler;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.project.FileType;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.ProjectPlatform;
import com.topcoder.management.project.ProjectGroup;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.Review;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.notification.ProjectNotification;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.ProjectPermission;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.review.specification.SpecificationReviewServiceException;
import com.topcoder.service.specreview.SpecReview;
import com.topcoder.service.user.Registrant;

/**
 * <p>
 * An interface for the web service implementing unified <code>Facade</code>
 * interface to various service components provided by global
 * <code>TopCoder</code> application.
 * </p>
 *
 * <p>
 * As of this version a facade to <code>Studio Service</code> is provided only.
 * </p>
 *
 * <p>
 * Module Cockpit Contest Service Enhancement Assembly change: Several new
 * methods related to the permission and permission type are added.
 * </p>
 *
 * <p>
 * <p>
 * Version 1.0.2 (Spec Reviews Finishing Touches v1.0) Change Notes:
 *  - Made the getSpecReviews method return instance of SpecReview rather than a list.
 *  - Added the methods to mark ready for review, review done and resubmit for review.
 * </p>
 * Module Cockpit Share Submission Integration Assembly change: Added method to
 * retrieve all permissions by projectId.
 * </p>
 *
 * * -----------------------changed in the version 1.1-----------------
 *
 * Four methods are added
 * setSubmissionCheckpointPrize(submissionId:long,checkpointPrizeId:long):void
 * getUserContests(userName:String):List<StudioCompetition>
 * getCheckpointSubmissionsForContest(contestId:long):List<SubmissionData>
 * getFinalSubmissionsForContest(contestId:long):List<SubmissionData>
 *
 * -----
 * <p>
 * Changes in v1.1.1 - Cockpit Release Assembly 11
 * Add method getDesignComponents to get design components.
 * </p>
 * <p>
 * Changes in v1.2 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI): Added a flag to
 * updateSubmissionUserRank method to support ranking checkpoint submissions.
 * </p>
 * <p>
 * Changes in v1.2: Added elegibility services.
 * </p>
 *
 * <p>
 * Changes in v1.2.1 Added support for eligibility services.
 * </p>
 * <p>
 * Changes in v1.3 (Cockpit Spec Review Backend Service Update v1.0):
 * - Added method to create specification review project for an existing project.
 * - Added method to get scorecard and review information for a specific project.
 * - Added method to upload a mock submission / final fixes to the associated specification review of a project
 *   to make it ready for review.
 * - Added method to add comments to an existing review.
 * </p>
 * <p>
 * Changes in v1.5 (Cockpit Release Assembly - Contest Repost and New Version v1.0):
 * - Added method to re open failed software contest.
 * - Added method to create new version for development or design contest.
 * </p>
 *
 * <p>
 * Changes in v1.5.1(Cockpit Security Facade V1.0):
 *  - It is not a web-service facade any more.
 *  - All the methods accepts a parameter TCSubject which contains all the security info for current user.
 *    The implementation EJB should use TCSubject and now get these info from the sessionContext.
 *  - Please use the new ContestServiceFacadeWebService as the facade now. That interface will delegates all the methods
 *    to this interface.
 * </p>
 *
 * <p>
 * Changes in v1.6 (Direct Search Assembly):
 * - Add getProjectData function
 * </p>
 * 
 * <p>
 * Changes in v1.6.1, two public methods are added (BUGR - 3706):
 * - List<ProjectNotification> getNotificationsForUser(TCSubject subject, long userId)
 * - updateNotifcationsForUser(TCSubject subject, long userId, List<ProjectNotification> notifications)
 * </p>
 *
 * <p>
 * Changes in v1.6.2 (Direct Submission Viewer Release 4 Assembly):
 * - Added {@link #updateSubmissionsGeneralFeedback(TCSubject, long, String)} method.
 * </p>
 *
 * <p>
 * Version 1.6.3 (Manage Copilot Postings Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #addReviewer(TCSubject, long, long)} method.</li>
 *     <li>Added {@link #getReview(long,long,long)} method.</li>
 *   </ol>
 * </p>
 * <p>
 * Changes in v1.6.4 (TC Direct - Permission Updates):
 * - Updated {@link #updateProjectPermissions(TCSubject, List<ProjectPermission>, long)} method, add a new input.
 * </p>
 *
 * <p>
 * Version 1.6.4 (TC Direct Replatforming Release 1) Change notes:
 * <ul>
 * <li>Add {@link #processContestCreditCardSale(TCSubject, SoftwareCompetition, CreditCardPaymentData, Date)} method.</li>
 * <li>Add {@link #processContestPurchaseOrderSale(TCSubject, SoftwareCompetition, TCPurhcaseOrderPaymentData, Date)} method.</li>
 * <li>Add {@link #createSoftwareContest(TCSubject, SoftwareCompetition, long, Date)} method.</li>
 * <li>Add {@link #updateSoftwareContest(TCSubject, SoftwareCompetition, long, Date)} method.</li>
 * <li>Add {@link #getAllFileTypes()} method.</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 1.6.5 (TC Direct Replatforming Release 3) Change notes:
 * <ul>
 * <li>Add {@link #getCheckpointSubmissions(long)} method to get the checkpoint submissions in OR.</li>
 * <li>Add {@link #getStudioSubmissionFeedback(TCSubject, long, long, PhaseType)} method to get client feedback for a specified submission.</li>
 * <li>Add {@link #saveStudioSubmisionWithRankAndFeedback(TCSubject, long, long, int, String, Boolean, PhaseType)} method to save placement and
 * client feedback for a specified submission.</li>
 * <li>Add {@link #updateSoftwareSubmissions(TCSubject, List)} method to update the submissions in OR.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.6 (TC Direct Replatforming Release 5) Change notes:
 * <ul>
 * <li>Changed method name from <code>getCheckpointSubmissions</code> to {@link #getSoftwareActiveSubmissions(long, int)}. The new method
 * support searching the active submissions for a specified submission type.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.7 (TopCoder Cockpit Project Overview R2 Project Forum Backend Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added createTopCoderDirectProjectForum(TCSubject, long, Long) method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.6.8 (TCCC-3658) Change notes:
 *   <ol>
 *     <li>Removed dependencies to studio components</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.0 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part One) change notes:
 *      Refactor all the permission related API to Permission Service Facade.
 * </p>
 *
 * <p>
 * Version 1.7.1 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part Two) change notes:
 *      Moved createTopCoderDirectProjectForum method to Project Service Facade.
 * </p>
 *
 * <p>
 * Version 1.7.2 (Release Assembly - TC Direct Cockpit Release Four) change notes:
 * - Add new method signature {@link #purchaseActivateContestAndStartSpecReview(com.topcoder.security.TCSubject,
 * com.topcoder.service.project.SoftwareCompetition, com.topcoder.service.payment.TCPurhcaseOrderPaymentData,
 * java.util.Date, java.util.Date, boolean)}
 * </p>
 *
 * <p>
 * Version 1.7.3 (Release Assembly - TC Direct Issue Tracking Tab Update Assembly 2 v1.0) change notes:
 *   <ol>
 *     <li>Added method {@link #getActiveUploads(long, int)} to retrieve the active uploads.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 1.7.4 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) change notes:
 *    <ul>
 *        <li>
 *        Added {@link #saveSoftwareCheckpointReviewWithRankAndFeedback(TCSubject, long, long[], int[],
 *        String[], Boolean, String)} to support software checkpoint management.
 *        </li>
 *    </ul>
 * </p>
 * 
 * <p>
 * Version 1.8.0 (Module Assembly - TC Cockpit Project Contests Batch Edit) changes:
 * <ul>
 *     <li>Add method {@link #deleteSoftwareContests(com.topcoder.security.TCSubject, long, java.util.List)}</li>
 *     <li>Add method {@link #batchUpdateDraftSoftwareContests(com.topcoder.security.TCSubject, long, java.util.List)}</li>
 * </ul>
 * </p>
 * <p>
 * Version 1.8.1 (Release Assembly - TopCoder Cockpit Copilot Selection Update and Other Fixes Assembly)
 * <ol>
 *     <li>Update signature of method selectCopilot to {@link #selectCopilot(com.topcoder.security.TCSubject, long, long, long, long, long)}
 *     and update the method to pick up copilot posting 1st and 2nd place in one transaction</li>
 * </ol>
 * </p>
 * 
 * <p>
 * Version 1.8.2 BUGR-8788 (TC Cockpit - New Client Billing Config Type) change notes:
 *  <ul>
 *      <li> Add {@link #requireBillingProjectCCA(long)} to check cca status of billing account</li>
 *      <li> Add {@link #requireBillingProjectsCCA(long[])} to check cca status of some of billing accounts</li>
 *  </ul>
 * </p>
 *
 * <p>
 * Version 1.8.3 (Module Assembly - TC Cockpit Launch F2F contest)
 * <ul>
 *     <li>Added {@link #getAllProjectPlatforms()} to get all project platform options</li>
 * </ul>
 * </p>
 *
 * * <p>
 * Version 1.8.4 (TOPCODER DIRECT - IMPROVEMENT FOR PRE-REGISTER MEMBERS WHEN LAUNCHING CHALLENGES):
 * <ul>
 *     <li>Added {@link #updatePreRegister(TCSubject, SoftwareCompetition, Set)} method</li>
 * </ul>
 * </p>
 *
 * Version 1.8.5 (TOPCODER DIRECT - CLOSE PRIVATE CHALLENGE IMMEDIATELY)
 * <ul>
 *     <li>Add {@link #closeSoftwareContest(TCSubject, long, long)}</li>
 *     <li>Add {@link #cancelSoftwareContestByUser(TCSubject, long)}</li>
 * </ul>
 *
 * Version 1.8.6 (TOPCODER - SUPPORT GROUPS CONCEPT FOR CHALLENGES):
 * <ul>
 *     <li>Add {@link #getAllProjectGroups()}to get all project groups</li>
 * </ul>
 * @author pulky, murphydog, waits, BeBetter, hohosky, isv, lmmortal, Veve, GreatKevin, deedee, TCSASSEMBLER, TCSCODER
 * @version 1.8.6
 */
public interface ContestServiceFacade {

    /**
    /**
     * <p>
     * Gets the list of all existing contests for my project widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws ContestServiceException
     *             if any error occurs when getting contest.
     */
    public List<CommonProjectContestData> getCommonProjectContestData(TCSubject tcSubject)
        throws ContestServiceException;
    
    /**
     * Gets all project data with aggregated statistics data for each type of contest status.
     *
     * @param tcSubject <code>TCSubject</code> object
     * @return a list of <code>ProjectSummaryData</code> objects
     *
     * @throws ContestServiceException if any error occurs during processing
     *
     * @since 1.6
     */
    public List<ProjectSummaryData> getProjectData(TCSubject tcSubject) throws ContestServiceException;

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param competition
     *            data that recognizes a contest.
     * @param paymentData
     *            payment information (TCSubject tcSubject,credit card/po details) that need to be
     *            processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    public SoftwareContestPaymentResult processContestCreditCardSale(TCSubject tcSubject,
        SoftwareCompetition competition, CreditCardPaymentData paymentData)
        throws ContestServiceException, PermissionServiceException;

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param competition
     *            data that recognizes a contest.
     * @param paymentData
     *            payment information (TCSubject tcSubject,credit card/po details) that need to be
     *            processed.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase. Can be null if to use default.
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since 1.6.4
     */
    public SoftwareContestPaymentResult processContestCreditCardSale(TCSubject tcSubject,
        SoftwareCompetition competition, CreditCardPaymentData paymentData, Date multiRoundEndDate, Date endDate)
        throws ContestServiceException, PermissionServiceException;

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param competition
     *            data that recognizes a contest.
     * @param paymentData
     *            payment information (TCSubject tcSubject,credit card/po details) that need to be
     *            processed.
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(TCSubject tcSubject,
        SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData)
        throws ContestServiceException, PermissionServiceException;

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param competition
     *            data that recognizes a contest.
     * @param paymentData
     *            payment information (TCSubject tcSubject,credit card/po details) that need to be
     *            processed.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase. Can be null if to use default.
     * @return a <code>PaymentResult</code> result of the payment processing.
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     * @since 1.6.4
     */
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(TCSubject tcSubject,
        SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData, Date multiRoundEndDate, Date endDate)
        throws ContestServiceException, PermissionServiceException;

    /**
     * <p>
     * Processes the contest sale, activate the contest and start the specification review of the contest.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (TCSubject tcSubject,credit card/po details) that need to be processed.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase. Can be null if to use default.
     * @param startSpecReviewNow the flag whether to start spec review now.
     * @return a <code>PaymentResult</code> result of the payment processing.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @throws PermissionServiceException if there is error when assigning permission to user.
     * @throws SpecificationReviewServiceException if fail to start the spec review of the contest.
     * @since 1.7.2
     */
    public SoftwareContestPaymentResult purchaseActivateContestAndStartSpecReview(TCSubject tcSubject,
        SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData,
        Date multiRoundEndDate, Date endDate, boolean startSpecReviewNow)
        throws ContestServiceException, PermissionServiceException, SpecificationReviewServiceException;

    /**
     * <p>
     * Returns a list containing all active <code>Categories</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Category> getActiveCategories(TCSubject tcSubject) throws ContestServiceException;

    /**
     * <p>
     * Returns a list containing all active <code>Technologies</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Technology> getActiveTechnologies(TCSubject tcSubject)
        throws ContestServiceException;

    /**
     * <p>
     * Returns a list containing all <code>Phases</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return a list containing all active <code>Categories</code>. It can be
     *         empty if no objects found.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Phase> getPhases(TCSubject tcSubject) throws ContestServiceException;

    /**
     * <p>
     * Creates a new <code>SoftwareCompetition</code> in the persistence.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contest
     *            the <code>SoftwareCompetition</code> to create as a contest
     * @param tcDirectProjectId
     *            the TC direct project id.
     *
     * @return the created <code>SoftwareCompetition</code> as a contest
     *
     * @throws IllegalArgumentException
     *             if the input argument is invalid.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition createSoftwareContest(TCSubject tcSubject,SoftwareCompetition contest, long tcDirectProjectId)
        throws ContestServiceException, PermissionServiceException;

    /**
     * <p>
     * Creates a new <code>SoftwareCompetition</code> in the persistence.
     * </p>
     * Updated for Version 1.0.1 - BUGR-2185: For development contests, if asset (or component) exists from design
     * contests then that is used to create a new contest. Otherwise a new asset is also created. Updated for Version1.5
     * the code is refactored by the logic: 1. check the permission 2. update or create the asset 3. set default
     * resources 4. create project 5. prepare the return value 6. persist the eligility
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contest the <code>SoftwareCompetition</code> to create as a contest
     * @param tcDirectProjectId the TC direct project id. a <code>long</code> providing the ID of a client the new
     *            competition belongs to.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase. Can be null if to use default.
     * @return the created <code>SoftwareCompetition</code> as a contest
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since 1.6.4
     */
    public SoftwareCompetition createSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
            long tcDirectProjectId, Date multiRoundEndDate, Date endDate) throws ContestServiceException, PermissionServiceException;
    
    /**
     * <p>
     * BURG-1716: We need to add a method to get software contest by project id,
     * the method wil get all OR project related data, then from project
     * property to get comp version id then to call getAssetByVersionId to get
     * assetDTO, please check create software contest to see what data need to
     * be returned.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            the OR Project Id
     * @return SoftwareCompetition
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since BURG-1716
     */
    public SoftwareCompetition getSoftwareContestByProjectId(TCSubject tcSubject,long projectId)
        throws ContestServiceException, PermissionServiceException;

    /**
     * <p>
     * Updates a <code>SoftwareCompetition</code> in the persistence.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param contest
     *            the <code>SoftwareCompetition</code> to update as a contest
     * @param tcDirectProjectId
     *            the TC direct project id.
     *
     * @throws IllegalArgumentException
     *             if the input argument is invalid.
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition updateSoftwareContest(TCSubject tcSubject,
        SoftwareCompetition contest, long tcDirectProjectId)
        throws ContestServiceException, PermissionServiceException;

    /**
     * <p>
     * Updates a <code>SoftwareCompetition</code> in the persistence.
     * </p>
     * <p>
     * Update in version 1.5, reduce the code redundancy in permission checking.
     * <p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contest the <code>SoftwareCompetition</code> to update as a contest
     * @param tcDirectProjectId the TC direct project id.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase. Can be null if to use default.
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since 1.6.4
     */
    public SoftwareCompetition updateSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
            long tcDirectProjectId, Date multiRoundEndDate, Date endDate) throws ContestServiceException, PermissionServiceException;
    
    /**
     * <p>
     * Assigns a specified user to a specified <code>assetDTO</code>.
     * </p>
     *
     * <p>
     * If the user already assigned to the asset, this method simply does
     * nothing.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param userId
     *            the id of the user
     * @param assetId
     *            the id of the assetDTO
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void assignUserToAsset(TCSubject tcSubject,long userId, long assetId)
        throws ContestServiceException;

    /**
     * <p>
     * Removes a specified user from a specified <code>assetDTO</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param userId
     *            the id of the user
     * @param assetId
     *            the id of the asset
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void removeUserFromAsset(TCSubject tcSubject,long userId, long assetId)
        throws ContestServiceException;

    /**
     * <p>
     * This method finds all tc direct projects. Returns empty array if no
     * projects found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @return Project array with project info, or empty array if none found
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjects(TCSubject tcSubject)
        throws ContestServiceException;

    /**
     * <p>
     * This method finds all given user tc direct projects . Returns empty array
     * if no projects found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param operator
     *            The user to search for projects
     *
     * @return Project array with project info, or empty array if none found
     *
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjectsForUser(TCSubject tcSubject,String operator)
        throws ContestServiceException;

    /**
     * <p>
     * This method retrieves the project along with all known associated
     * information. Returns null if not found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            The ID of the project to retrieve
     *
     * @return the project along with all known associated information
     *
     * @throws IllegalArgumentException
     *             If projectId is negative
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition getFullProjectData(TCSubject tcSubject,long projectId)
        throws ContestServiceException, PermissionServiceException;

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     *
     * <p>
     * If the project allows multiple submissions for users, it will add the new
     * submission and return. If multiple submission are not allowed for the
     * project, firstly it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param submission
     *            the submission file data
     *
     * @return the id of the new submission
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if any argument is <code>null</code> or
     *             trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadSubmission(TCSubject tcSubject,long projectId, String filename,
                                DataHandler submission) throws ContestServiceException;

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
     * @param userId user Id
     * @param projectId project Id
     * @param filename filename
     * @param submission submission data
     * @return
     * @throws ContestServiceException
     * @since 3.5
     */
    public long uploadSubmission(long userId, long projectId, String filename,
                                 DataHandler submission) throws ContestServiceException;

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This
     * submission always overwrite the previous ones.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param finalFix
     *            the final fix file data
     *
     * @return the id of the created final fix submission
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if any argument is <code>null</code> or
     *             trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadFinalFix(TCSubject tcSubject,long projectId, String filename,
        DataHandler finalFix) throws ContestServiceException;

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This
     * submission always overwrite the previous ones.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            the project's id
     * @param filename
     *            the file name to use
     * @param testCases
     *            the test cases data
     *
     * @return the id of the created test cases submission
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if any argument is <code>null</code> or
     *             trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadTestCases(TCSubject tcSubject,long projectId, String filename,
        DataHandler testCases) throws ContestServiceException;

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param submissionId
     *            the submission's id
     * @param submissionStatusId
     *            the submission status id
     * @param operator
     *            the operator which execute the operation
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0 or if operator is null or trim to empty
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void setSubmissionStatus(TCSubject tcSubject,long submissionId, long submissionStatusId,
        String operator) throws ContestServiceException, PermissionServiceException;

    /**
     * Adds the given user as a new submitter to the given project id.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId
     *            the project to which the user needs to be added
     * @param userId
     *            the user to be added
     *
     * @return the added resource id
     *
     * @throws IllegalArgumentException
     *             if any id is &lt; 0
     * @throws ContestServiceException
     *             if an error occurs when interacting with the service layer.
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long addSubmitter(TCSubject tcSubject,long projectId, long userId)
        throws ContestServiceException;
    
    /**
     * Gets all contest fees by billing project id.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * 
     * @param projectId the billing project id
     * @return the list of project contest fees for the given project id
     * @throws ContestServiceException  if any persistence or other error occurs
     * @since 1.0.1
     */
    public List<ProjectContestFee> getContestFeesByProject(TCSubject tcSubject,long projectId) 
        throws ContestServiceException, PermissionServiceException;


      
    /**
     * Gets the spec review for specified contest id.
     * 
     * Updated for Spec Reviews Finishing Touches v1.0
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * 
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * 
     * @return the spec review that matches the specified contest id.
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public SpecReview getSpecReviews(TCSubject tcSubject,long contestId, boolean studio) throws ContestServiceException;

    /**
     * Save specified review comment and review status for specified section and specified contest id to persistence.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param isPass
     *            the is pass
     * @param role
     *            the user role type           
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void saveReviewStatus(TCSubject tcSubject,long contestId, boolean studio, String sectionName, String comment, boolean isPass, String role)
            throws ContestServiceException;

    /**
     * Save specified review comment for specified section and specified contest id to persistence.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     * @param sectionName
     *            the section name
     * @param comment
     *            the comment
     * @param role
     *            the user role type           
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void saveReviewComment(TCSubject tcSubject,long contestId, boolean studio, String sectionName, String comment, String role)
            throws ContestServiceException;

    /**
     * Mark review comment with specified comment id as seen.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param commentId
     *            the comment id
     * 
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void markReviewCommentSeen(TCSubject tcSubject,long commentId) throws ContestServiceException;
    
    /**
     * Marks 'review done' by reviewer of the specs for specified contest.
     * Persistence is updated and all end users having write/full permission on the contest are notified by email.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param contestName
     *            the contest name.            
     * @param studio
     *            whether contest is studio or not.
     * @param tcDirectProjectId
     *            the tc direct project id.            
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReviewDone(TCSubject tcSubject,long contestId, String contestName, boolean studio, long tcDirectProjectId) throws ContestServiceException;

    /**
     * Marks 'ready for review' by the writer of the specs for specified contest.
     * Persistence is updated, on update the spec would appear as review opportunity on tc site.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether contest is studio or not.
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReadyForReview(TCSubject tcSubject,long contestId, boolean studio) throws ContestServiceException;

    /**
     * Marks 'resubmit for review' by the writer of the specs for specified contest.
     * Persistence is updated. Reviewer (TCSubject tcSubject,if any) is notified about the updates.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param contestName
     *            the contest name.            
     * @param studio
     *            whether contest is studio or not.
     * @param reviewerUserId
     *            reviewer user id.
     * @throws ContestServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void resubmitForReview(TCSubject tcSubject,long contestId, String contestName, boolean studio, long reviewerUserId) throws ContestServiceException;

    /**
     * Get all design components.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.1.1
     */
    public List<DesignComponents> getDesignComponents(TCSubject tcSubject) throws ContestServiceException;
    
    
    /**
     * Returns whether a user is eligible for a particular contest.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param userId
     *            The user id
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     * 
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.2
     */
    public boolean isEligible(TCSubject tcSubject,long userId, long contestId, boolean isStudio) throws ContestServiceException;

    /**
     * Find eligibility name for the client.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * 
     * @param billingProjectId;
     * 			The ID of the billingProject.
     * @return
     * 			The name of the eligibility group.
     * @since 1.2.1
     */
    public String getEligibilityName(TCSubject tcSubject,long billingProjectId);

    /**
     * This method creates a Specification Review project associated to a project determined by parameter.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project id to create a Specification Review for
     * @return the created project
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     *
     * @since 1.4
     */
    public FullProjectData createSpecReview(TCSubject tcSubject,long projectId) throws ContestServiceException;

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter.
     * Note: a single reviewer / review is assumed.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project id to search for
     * @return the aggregated scorecard and review data
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     *
     * @since 1.4
     */
    public ScorecardReviewData getScorecardAndReview(TCSubject tcSubject,long projectId) throws ContestServiceException;

    /**
     * This method uploads a mock file to the corresponding specification review project of the specified project
     * id, so that it can continue with review. Regular submission or final fix will be uploaded according to the
     * open phase.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param projectId the project id of the original project
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services, if the associated
     * specification review project id cannot be found or if neither submission or final fixes phase are open.
     *
     * @since 1.4
     */
    public void markSoftwareContestReadyForReview(TCSubject tcSubject,long projectId) throws ContestServiceException;

    /**
     * This method adds a review comment to a review. It simply delegates all logic to underlying services.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param reviewId the review id to add the comment to
     * @param comment the review comment to add
     *
     * @throws ContestServiceException if any unexpected error occurs in the underlying services.
     * @throws IllegalArgumentException if comment is null
     *
     * @since 1.4
     */
    public void addReviewComment(TCSubject tcSubject,long reviewId, Comment comment) throws ContestServiceException;
    /**
     * <p>
     * Re-Open the software project in status of (TCSubject tcSubject,project_status_id = 4-6, 8-10 in tcs_catalog:project_status_lu).
     * </p>
     *<p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project id to open
     * @param tcDirectProjectId the tc-direct-project-id
     * @return returns the newly created contest id
     * @throws ContestServiceException if any problem occurs
     */
    public long reOpenSoftwareContest(TCSubject tcSubject,long projectId, long tcDirectProjectId) throws ContestServiceException, PermissionServiceException;
    /**
     * <p>
     * Create new version for design or development contest. (TCSubject tcSubject,project_status_id = 4-10 in tcs_catalog:project_status_lu).
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project to create new version
     * @param tcDirectProjectId tc direct project id
     * @param autoDevCreating if it is true and it is design contest, then will create development too
     * @param minorVersion true for minor, false for major
     * @return newly version contest id
     * @throws ContestServiceException if any error occurs
     */
    public long createNewVersionForDesignDevContest(TCSubject tcSubject,long projectId, long tcDirectProjectId, boolean autoDevCreating,
                                                    boolean minorVersion) throws ContestServiceException, PermissionServiceException;

    /**
     * Returns whether the contest is private.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            The contest id
     * @param isStudio
     *            true if the contest is a studio contest, false otherwise.
     * @return true if the contest is a private one, false otherwise.
     * 
     * @throws ContestServiceException
     *             if any other error occurs
     * @since 1.2.3
     */
    public boolean isPrivate(TCSubject tcSubject,long contestId, boolean isStudio) throws ContestServiceException;
    
    /**
     * Assigns the role for the given project and user.
     * 
     * @param projectId the id of the project.
     * @param roleId the id of the role
     * @param userId the id of the user.
     * @throws ContestServiceException if any error occurs
     * @since BUGR - 3731
     */
    public void assginRole(TCSubject tcSubject, long projectId, long roleId, long userId) throws ContestServiceException;


    /**
     * Assigns the role for the given tc project and user, it will assign all projects 
     * uder tc direct projct
     * 
     * @param tcprojectId the id of the tc direct project.
     * @param roleId the id of the role
     * @param userId the id of the user.
     * @throws ContestServiceException if any error occurs
     * @since BUGR - 3731
     */
    public void assginRoleByTCDirectProject(TCSubject tcSubject, long tcprojectId, long roleId, long userId) throws ContestServiceException;

    /**
     * Gets the notification information for the given user id. The notification information will be
     * returned as a list of ProjectNotification instance.
     * 
     * @param subject the TCSubject instance.
     * @param userId the id of the user.
     * @return a list of ProjectNotification instances.
     * @throws ContestServiceExeption if any error occurs, exception from forum EJB service will be
     *             caught and logged, but no thrown out.
     * @since 1.6.1
     */
    public List<ProjectNotification> getNotificationsForUser(TCSubject subject, long userId) throws ContestServiceException;
    
    /**
     * Updates the notifications for the given user, the notifications which need to update are
     * passed in as a list of ProjectNotification instances.
     * 
     * @param subject the TCSubject instance.
     * @param userId the id of the user.
     * @param notifications a list of ProjectNotification instances to update.
     * @throws ContestServiceExeption if any error occurs, exception from forum EJB service will be
     *             caught and logged, but no thrown out.
     * @since 1.6.1
     */
    public void updateNotificationsForUser(TCSubject subject, long userId, List<ProjectNotification> notifications) throws ContestServiceException;

     /**
     * Gets the registrant information for the given project. If the project is of type Studio, a
     * boolean flag isStudio should be set to true and passed as argument.
     * 
     * @param tcSubject the TCSubject instance.
     * @param ProjectId the id of the project.
     * @param isStudio the flag indicates whether the project is of type Studio.
     * @return the retrieved list of Registrant instances.
     * @throws ContestServiceException if any error occurs.
     * 
     * @since BUGR-3738
     */
    public List<Registrant> getRegistrantsForProject(TCSubject tcSubject, long ProjectId)
            throws ContestServiceException;

    
    /**
     * Adds the given user as a new reviewer to the given project id.
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user.
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     * @return the added resource id
     * @throws ContestServiceException      if any error occurs from UploadServices
     * @throws IllegalArgumentException     if any id is &lt; 0
     * @since 1.6.3
     */
    Resource addReviewer(TCSubject tcSubject, long projectId, long userId) throws ContestServiceException;

    /**
     * <p>Gets the review for specified submission.</p>
     *  
     * @param projectId a <code>long</code> providing the project ID.
     * @param reviewerResourceId a <code>long</code> providing the ID for reviewer resource.
     * @param submissionId a <code>long</code> providing the ID for submission.   
     * @return a <code>ScorecardReviewData</code> providing the details for review or <code>null</code> if review and 
     *         scorecard is not found.
     * @since 1.6.3
     */
    ScorecardReviewData getReview(long projectId, long reviewerResourceId, long submissionId);

    /**
     * <p>Gets the submissions for specified software project.</p>
     * 
     * @param projectId a <code>long</code> providing the ID of a project.
     * @return a <code>List</code> listing the submissions for project.
     * @throws SearchBuilderException if an unexpected error occurs.
     * @throws UploadPersistenceException if an unexpected error occurs.
     * @since 1.6.3 
     */
    Submission[] getSoftwareProjectSubmissions(TCSubject currentUser, long projectId) throws SearchBuilderException, 
                                                                      UploadPersistenceException, PermissionServiceException;
    /**
     * <p>Gets the active submissions for specified project with the specified submission type.</p>.
     * 
     * @param projectId a <code>long</code> providing the ID of a project.
     * @param submissionType a <code>int</code> providing the id of the submission type.
     * @return a <code>List</code> listing the checkpoint submissions for project.
     * @throws SearchBuilderException if an unexpected error occurs.
     * @throws UploadPersistenceException if an unexpected error occurs.
     * @since 1.6.5
     */
    public Submission[] getSoftwareActiveSubmissions(long projectId, int submissionType) throws SearchBuilderException,
                                                                       UploadPersistenceException;
    
    /**
     * <p>Gets the active uploads for specified project with the specified upload type.</p>
     * 
     * @param projectId a <code>long</code> providing the ID of a project.
     * @param uploadType an <code>int</code> providing the id of the upload  type.
     * @return the retrieved uploads
     * @throws SearchBuilderException if an unexpected error occurs.
     * @throws UploadPersistenceException if an unexpected error occurs.
     * @since 1.7.3
     */
    public Upload[] getActiveUploads(long projectId, int uploadType)
        throws SearchBuilderException, UploadPersistenceException;

    /**
     * <p>Creates specified review for software project.</p>
     * 
     * @param review a <code>Review</code> providing the details for review to be created.
     * @throws ReviewManagementException if an unexpected error occurs.
     * @since 1.6.3 
     */
    void createReview(Review review) throws ReviewManagementException;
    
    /**
     * Gets all FileType entities.
     *
     * @return the found FileType entities, return empty if cannot find any.
     * @throws ContestServiceException
     *             if there are any exceptions.
     * @since 1.6.4
     */
    public FileType[] getAllFileTypes() throws ContestServiceException;

    /**
     * Gets all project platforms.
     *
     * @return all the project platforms available, return empty if cannot find any.
     * @throws ContestServiceException if there are any exceptions.
     * @since 1.8.3
     */
    public ProjectPlatform[] getAllProjectPlatforms() throws ContestServiceException;
    
    /**
     * <p>Selects copilot for specified TC Direct project.</p>
     * 
     * @param currentUser a <code>TCSubject</code> representing the current user. 
     * @param tcDirectProjectId a <code>long</code> providing the TC Direct project ID.
     * @param copilotPostingProjectId a <code>long</code> providing the ID for <code>Copilot Posting</code> contest.
     * @param winnerProfileId a <code>long</code> providing the winner copilot profile ID.
     * @param winnerSubmissionId a <code>String</code> providing the winner copilot submission ID.
     * @param secondPlaceSubmissionId a <code>String</code> providing the second place copilot submission ID.
     * @throws PermissionServiceException if current user is not allowed to perform the specified action.
     * @throws ContestServiceException if an unexpected error occurs.
     * @since 1.6.3
     */
    public void selectCopilot(TCSubject currentUser, long tcDirectProjectId, long copilotPostingProjectId,
                              long winnerProfileId, long winnerSubmissionId,
                              long secondPlaceSubmissionId)
        throws PermissionServiceException, ContestServiceException;

    /**
     * Update copilot projects and related permissions.
     * 
     * @param currentUser
     *            current user
     * @param copilotProjects
     *            the copilot projects to update
     * @param removeFlags
     *            whether to remove or add
     * @return updated copilot projects
     * @throws PermissionServiceException
     *             if current user has no permission to perform this operation
     * @throws ContestServiceException
     *             if any exception occurs
     */
    public List<CopilotProject> updateCopilotProjects(TCSubject currentUser,
            List<CopilotProject> copilotProjects, List<Boolean> removeFlags)
            throws PermissionServiceException, ContestServiceException;
    
    /**
     * <p>Gets the client feedback of the specified studio submission. The client feedback is the comment in the review board
     * of the submission.</p>
     *
     * @param currentUser a <code>TCSubject</code> representing the current user. 
     * @param projectId a <code>long</code> providing the ID of a project.
     * @param submissionId a <code>long</code> providing the ID of the submission.
     * @param phaseType a <code>PhaseType</code> providing the phase type which the submission belongs to. 
     * @return a <code>String</code> providing the client feedback of the submission.
     * @throws ContestServiceException if any error occurs.
     * @since 1.6.5
     */
    public String getStudioSubmissionFeedback(TCSubject currentUser, long projectId, long submissionId, PhaseType phaseType) throws ContestServiceException ;
    
    /**
     * <p>save the rank and client feedback for a specified submission. The reviewer is the current user. And the review board is assumed only have one
     * question rating from 1 to 10. The client feedback is the comment in the review board.</p>
     *
     * @param tcSubject a <code>TCSubject</code> representing the current user. 
     * @param projectId a <code>long</code> providing the ID of a project.
     * @param submissionId a <code>long</code> providing the ID of the submission.
     * @param placement a <code>int</code> providing the placement of the submission.
     * @param feedback a <code>String</code> providing the client feedback of the submission. Feedback will not changed if it is null.
     * @param committed a <code>boolean</code> representing whether to commit the review board.
     * @param phaseType a <code>PhaseType</code> providing the phase type which the submission belongs to. 
     * @throws ContestServiceException if any error occurs.
     * @since 1.6.5
     */
    public void saveStudioSubmisionWithRankAndFeedback(TCSubject tcSubject, long projectId, long submissionId,
            int placement, String feedback, Boolean committed, PhaseType phaseType)
        throws ContestServiceException;
    
    /**
     * <p>Update the software submissions.</p>
     * 
     * @param currentUser a <code>TCSubject</code> representing the current user. 
     * @param submissions a <code>List</code> providing the submissions to be updated.
     * @throws ContestServiceException if any error occurs.
     * @since 1.6.5
     */
    public void updateSoftwareSubmissions(TCSubject currentUser, List<Submission> submissions) throws ContestServiceException;

    /**
     * <p>Save the ranks and feedbacks for the checkpoint sumbissions of a contest. The reviewer is the current user.
     * And the review board is assumed only have one question rating from 1 to 10.
     * The feedback is the comment in the review board.</p>
     *
     * @param tcSubject a <code>TCSubject</code> representing the current user
     * @param projectId a <code>long</code> providing the ID of a contest
     * @param submissionIds a <code>long</code> array providing the IDs of the submissions
     * @param placements a <code>int</code> array providing the placements of the submissions
     * @param feedbacks a <code>String</code> array providing the client feedbacks of the submissions
     * @param committed a <code>boolean</code> representing whether to commit the review board
     * @param generalFeedback a <code>String</code> providing the general feedback of the submissions
     * @throws ContestServiceException if any error occurs
     * @since 1.7.4
     */
    public void saveSoftwareCheckpointReviewWithRankAndFeedback(TCSubject tcSubject, long projectId,
        long[] submissionIds, int[] placements, String[] feedbacks, Boolean committed,
        String generalFeedback) throws ContestServiceException;
	 
	/**
     * Deletes the specified contest, it marks the contest status to "Deleted". User can only delete the contests
     * with status:Draft. If the contest is not draft, it won't be deleted.
     *
     * @param tcSubject          the tcsubject instance.
     * @param tcDirectProjectId  the id of the direct project
     * @param softwareContestIds the ids of the contests to delete
     * @return the deleted contests
     * @throws ContestServiceException    if any error related to contest service facade.
     * @throws PermissionServiceException if any error related to permission service facade.
     * @since 1.8
     */
    public List<SoftwareCompetition> deleteSoftwareContests(TCSubject tcSubject, long tcDirectProjectId,
                                                            List<Long> softwareContestIds)
            throws ContestServiceException, PermissionServiceException;

    /**
     * Updates all the passed in draft <code>SoftwareCompetition</code> in one trasaction. If there is <code>SoftwareCompetition</code>
     * not in draft status, it won't be updated.
     *
     * @param tcSubject         the tcSubject instance.
     * @param tcDirectProjectId the id of the tc direct project.
     * @param draftContests     a list of <code>SoftwareCompetition</code> to update.
     * @return the list of updated <code>SoftwareCompetition</code>
     * @throws ContestServiceException    if any error related to contest service facade.
     * @throws PermissionServiceException if any error related to permission service facade.
     * @since 1.8
     */
    public List<SoftwareCompetition> batchUpdateDraftSoftwareContests(TCSubject tcSubject, long tcDirectProjectId,
                                                                      List<SoftwareCompetition> draftContests)
            throws ContestServiceException, PermissionServiceException;
    
    /**
     * Check if the array of billing project required CCA.
     * 
     * @param billingProjectId 
     * @return boolean status of cca required
     * @throws PersistenceException if any other error occurs.
     * 
     * @since 1.8.1
     */
    public boolean requireBillingProjectCCA(long billingProjectId) throws PersistenceException;
    
    /**
     * Check if the array of billing project required CCA.
     * 
     * @param billingProjectId array of billing project id
     * @return array of boolean status of cca required
     * @throws PersistenceException if any other error occurs.
     * 
     * @since 1.8.1
     */
    public boolean[] requireBillingProjectsCCA(long[] billingProjectIds) throws PersistenceException;

    /**
     * Update pre-register users
     *
     *
     * @param tcSubject
     * @param contest
     * @param preRegisterMembers
     * @return
     * @throws ContestServiceException\
     * @since 1.8.4
     */
    Set<Long> updatePreRegister(TCSubject tcSubject, SoftwareCompetition contest,
                                        Set<Long> preRegisterMembers) throws ContestServiceException;

    /**
     * Close project immediately and pick winner
     *
     * @param tcSubject TCSubject
     * @param projectId project id
     * @param winnerId user id of choosen winner
     * @throws ContestServiceException
     * @since 1.8.5
     */
    void closeSoftwareContest(TCSubject tcSubject, long projectId, long winnerId) throws ContestServiceException;

    /**
     * Cancel project
     *
     * @param tcSubject TCSubject
     * @param projectId project id
     * @throws ContestServiceException
     * @since 1.8.5
     */
    void cancelSoftwareContestByUser(TCSubject tcSubject, long projectId) throws ContestServiceException;

    /**
     * Get all project groups
     *
     * @return array of all project groups
     * @throws ContestServiceException if any database related exception occur
     * @since 1.8.6
     */
    public ProjectGroup[] getAllProjectGroups(TCSubject tcSubject) throws ContestServiceException;
}
