/*
 * Copyright (C) 2009-2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.specreview.ejb;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.topcoder.security.TCSubject;
import com.topcoder.service.specreview.ReviewComment;
import com.topcoder.service.specreview.ReviewCommentView;
import com.topcoder.service.specreview.ReviewStatus;
import com.topcoder.service.specreview.ReviewUserRoleType;
import com.topcoder.service.specreview.SectionType;
import com.topcoder.service.specreview.SpecReview;
import com.topcoder.service.specreview.SpecSectionReview;
import com.topcoder.service.specreview.SpecReviewServiceException;
import com.topcoder.service.specreview.SpecReviewServiceLocal;
import com.topcoder.service.specreview.SpecReviewServiceRemote;
import com.topcoder.service.specreview.UpdatedSpecSectionData;
import com.topcoder.service.user.UserService;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;

/**
 * The Class SpecReviewServiceBean - the ejb mean class that provides various methods to read/update spec reviews.
 *
 * Version 1.0.1 (Spec Review Finishing Touches v1.0) Change Notes:
 *  - Made the getSpecReviews method return instance of SpecReview rather than a list.
 *  - Added the methods to get reviewer and writer updates.
 *  - Added the methods to mark ready for review, review done and resubmit for review.
 *
 * Version 1.0.2 (Cockpit Security Facade V1.0)
 *  - Remove annotation: RolesAllowed( { "Cockpit User", "Cockpit Administrator" })
 *                       DeclareRoles( { "Cockpit User", "Cockpit Administrator" })
 *  - Add TCSubject as parameter for methods:saveReviewStatus,saveReviewComment,markReviewCommentSeen,markReviewDone
 *                                           markReadyForReview, resubmitForReview
 * @author snow01, waits
 * @since Cockpit Launch Contest - Inline Spec Review Part 2
 * @version 1.0.2
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class SpecReviewServiceBean implements SpecReviewServiceRemote, SpecReviewServiceLocal {

    /**
     * <p>
     * Represents the sessionContext of the EJB.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * This field represents the persistence unit name to lookup the <code>EntityManager</code> from the
     * <code>SessionContext</code>. It is initialized in the <code>initialize</code> method, and never changed
     * afterwards. It must be non-null, non-empty string.
     * </p>
     */
    @Resource(name = "unitName")
    private String unitName;

    /**
     * <p>
     * Represents the loggerName used to retrieve the logger.
     * </p>
     */
    @Resource(name = "logName")
    private String logName;

    /**
     * <p>
     * Represents the log used to log the methods logic of this class.
     * </p>
     */
    private Log logger;

    /**
     * Represents the instance of entity manager.
     */
    private EntityManager entityManager;

    /**
     * Constant variable representing get spec review Named Query
     */
    private static final String SpecReviewsQuery = "getSpecReviews";

    /**
     * Constant variable representing native query to get spec review id by contest id.
     *
     * @since 1.0.1
     */
    private static final String SpecReviewIdByContestQuery = "SELECT spec_review_id from spec_review where contest_id = :contestId and is_studio = :isStudio";

    /**
     * Constant variable representing get spec section reviews by spec review id Named Query
     *
     * Updated for 1.0.1 - changed from SpecReviewByContestAndSectionQuery to SpecSectionReviewByContestAndNameQuery.
     */
    private static final String SpecSectionReviewsQuery = "getSpecSectionReviews";

    /**
     * Constant variable representing get spec section review by contest id and section name Named Query
     *
     * Updated for 1.0.1 - changed from SpecReviewByContestAndSectionQuery to SpecSectionReviewByContestAndNameQuery.
     */
    private static final String SpecSectionReviewByContestAndSectionQuery = "getSpecSectionReviewByContestAndSection";

    /**
     * Constant variable representing get section type by name Named Query.
     */
    private static final String SectionTypeByNameQuery = "getSectionTypeByName";

    /**
     * Constant variable representing get review status by name Named Query
     */
    private static final String ReviewStatusByNameQuery = "getReviewStatusByName";

    /**
     * Constant variable representing get review user role type by name Named Query
     */
    private static final String ReviewUserRoleTypeByNameQuery = "getReviewUserRoleTypeByName";

    /**
     * Constant variable representing get review comment views by comment ids Named Query
     *
     * Updated for 1.0.1 - not getting used so commented out.
     */
    //private static final String ReviewCommentViewsByCommentIdsQuery = "getReviewCommentViewsByCommentIds";

    /**
     * Constant variable representing Native Query that updates overall review status for specified contest.
     *
     * @since 1.0.1
     */
    private static final String UpdateOverallReviewStatusQuery = "UPDATE spec_review " +
      " SET review_status_type_id = (select case when SUM(NVL(b.review_status_type_id - 1, 10000)) > 10000 then 3" +
      "                              WHEN SUM(NVL(b.review_status_type_id - 1, 10000)) = 0 then 1 else 2 end " +
      "                              from spec_review_section_type_lu as a " +
      "                              left join spec_section_review as b " +
      "                              on a.review_section_type_id = b.review_section_type_id" +
            "                              and b.spec_review_id = spec_review.spec_review_id" +
      "                              where a.is_studio = :isStudio)" +
      " WHERE contest_id = :contestId and is_studio = :isStudio";

    /**
     * Constant variable representing Native Query that updates spec_review for 'ready for review' for specified reviewId.
     *
     * @since 1.0.1
     */
    private static final String MarkReadyForReviewQuery = "UPDATE spec_review " +
            " SET review_status_type_id = 4, ready_for_review_time = CURRENT, modification_user = :modifyUser, modification_time = CURRENT" +
            " WHERE spec_review_id = :specReviewId";

    /**
     * Constant variable representing Native Query that updates spec_review for 'review done' for specified contest.
     *
     * @since 1.0.1
     */
    private static final String MarkReviewDoneQuery = "UPDATE spec_review " +
            " SET review_done_time = CURRENT, modification_user = :modifyUser, modification_time = CURRENT" +
            " WHERE contest_id = :contestId and is_studio = :isStudio";

    /**
     * Constant variable representing Native Query that updates spec_review for 'resubmit for review' for specified contest.
     *
     * @since 1.0.1
     */
    private static final String ResubmitForReviewQuery = "UPDATE spec_review " +
            " SET ready_for_review_time = CURRENT, modification_user = :modifyUser, modification_time = CURRENT" +
            " WHERE contest_id = :contestId and is_studio = :isStudio";


    /**
     * Constant string representing the native query to get section updates made by reviewer.
     *
     * @since 1.0.1
     */
    private static final String GetReviewerUpdatesQuery = "select  " +
        "         b.spec_section_review_id  as sectionId,  " +
        "         (select name  " +
        "             from spec_review_section_type_lu as c " +
        "             where c.is_studio = :isStudio  " +
        "             and c.review_section_type_id = b.review_section_type_id) as sectionName, " +
        "         (select name  " +
        "             from spec_review_status_type_lu as c " +
        "             where c.review_status_type_id = b.review_status_type_id) as statusName, " +
        "         (select review_comment " +
        "         from spec_section_review_comment as d " +
        "         where d.comment_id = (select max(comment_id)  " +
        "                                 from spec_section_review_comment as c " +
        "                                 where c.spec_section_review_id = b.spec_section_review_id " +
        "                                 and c.create_time >= b.modification_time) " +
        "         ) as comment, " +
        "         b.modification_user as user " +
        " from spec_review as a " +
        " join spec_section_review as b " +
        " on a.spec_review_id = b.spec_review_id " +
        " and a.is_studio = :isStudio " +
        " and a.contest_id = :contestId " +
        " and (a.review_done_time is null " +
        " or b.modification_time > a.review_done_time)";

    /**
     * Constant string representing the native query to get section updates made by creator.
     *
     * @since 1.0.1
     */
    private static final String GetWriterUpdatesQuery = "select  " +
        "         b.spec_section_review_id  as sectionId,  " +
        "         (select name  " +
        "             from spec_review_section_type_lu as c " +
        "             where c.is_studio = :isStudio  " +
        "             and c.review_section_type_id = b.review_section_type_id) as sectionName, " +
        "         (select name  " +
        "             from spec_review_status_type_lu as c " +
        "             where c.review_status_type_id = b.review_status_type_id) as statusName, " +
        "         (select review_comment " +
        "         from spec_section_review_comment as d " +
        "         where d.comment_id = (select max(comment_id)  " +
        "                                 from spec_section_review_comment as c " +
        "                                 where c.spec_section_review_id = b.spec_section_review_id " +
        "                                 and c.create_time >= b.modification_time) " +
        "         ) as comment, " +
        "         b.modification_user as user " +
        " from spec_review as a " +
        " join spec_section_review as b " +
        " on a.spec_review_id = b.spec_review_id " +
        " and a.is_studio = :isStudio " +
        " and a.contest_id = :contestId " +
        " and (a.ready_for_review_time is null " +
        " or b.modification_time > a.ready_for_review_time)";


    /**
     * Constant representing the pending status name.
     *
     * @since 1.0.1
     */
    private static final String PendingStatus = "PENDING";

    /**
     * Constant representing the not ready status name.
     *
     * @since 1.0.1
     */
    private static final String NotReadyStatus = "NOT_READY";

    /**
     * Constant representing the ready status name.
     *
     * @since 1.0.1
     */
    private static final String ReadyStatus = "READY";

    /**
     * Id that is set for unset entities.
     */
    private static final long UnsetId = -1;
    /**
     * <p>
     * A <code>UserService</code> providing access to available <code>User Service EJB</code>.
     * </p>
     * @since 1.0.2
     */
    @EJB(name = "ejb/UserService")
    private UserService userService = null;
    /**
     * A default empty constructor.
     */
    public SpecReviewServiceBean() {
    }

    /**
     * <p>
     * This is method is performed after the construction of the bean, at this point all the bean's resources will be
     * ready.
     * </p>
     */
    @PostConstruct
    public void init() {
        if (logName != null) {
            if (logName.trim().length() == 0) {
                throw new IllegalStateException("logName parameter not supposed to be empty.");
            }

            logger = LogManager.getLog(logName);
        }

        try {
            entityManager = getEntityManager();
        } catch (SpecReviewServiceException e) {
            throw new IllegalStateException("Error during init()", e);
        }
        if (userService == null) {
            throw new IllegalStateException("The userService is not injected.");
        }
        // first record in logger
        logExit("init");
    }
    /**
     * <p>
     * Get the user-name for current login user represented by tcSubject.
     * </p>
     * @param tcSubject TCSubject instance for login user
     * @return user name
     * @throws ProjectServicesException fail to retrieve user-name
     */
    private String getUserName(TCSubject tcSubject) throws SpecReviewServiceException {
        try {
            return this.userService.getUserHandle(tcSubject.getUserId());
        } catch (Exception e) {
            throw new SpecReviewServiceException("Fail to get the user-name by user-id" + tcSubject.getUserId(), e);
        }
    }
    /**
     * Gets the instance of <code>SpecReview</code> for specified contest id.
     *
     * Updated for Version 1.0.1
     *  - Changed to get SpecReview rather than list.
     *  - SpecReview contains list of spec section reviews.
     *  - In case of no result is found then an empty SpecReview is returned.
     *
     * @param contestId
     *            the contest id
     * @param studio
     *            indicates whether the specified contest id is for studio contests.
     *
     * @return the instance of <code>SpecReview</code> that matches the specified contest id.
     *
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    @SuppressWarnings("unchecked")
    public SpecReview getSpecReviews(long contestId, boolean studio) throws SpecReviewServiceException {
        SpecReview result = null;
        try {
            logEnter("getSpecReviews(contestId, studio)", contestId, studio);

            Query query = entityManager.createNamedQuery(SpecReviewsQuery);
            query.setParameter("contestId", contestId);
            query.setParameter("studio", studio);

            result = (SpecReview) query.getSingleResult();

            result.setSectionReviews(new HashSet<SpecSectionReview>());

            query = entityManager.createNamedQuery(SpecSectionReviewsQuery);
            query.setParameter("specReviewId", result.getSpecReviewId());

            try {
                List<SpecSectionReview> sectionReviews = (List<SpecSectionReview>) query.getResultList();
                result.getSectionReviews().addAll(sectionReviews);
            } catch (NoResultException nre) {
                // ignore it
            }

        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (NoResultException e) {
            result = new SpecReview();
            result.setContestId(contestId);
            result.setStudio(studio);
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while retrieving the spec reviews.");
        } finally {
            logExit("getSpecReviews(contestId, studio)", result);
        }

        return result;
    }

    /**
     * Mark review comment with specified comment id as seen.
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param commentId
     *            the comment id
     *
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    public void markReviewCommentSeen(TCSubject tcSubject, long commentId) throws SpecReviewServiceException {
        try {
            logEnter("markReviewCommentSeen(tcSubject, commentId)", tcSubject.getUserId(), commentId);


            String userName = getUserName(tcSubject);

            ReviewCommentView rcv = new ReviewCommentView();
            rcv.setViewId(UnsetId);
            rcv.setCommentId(commentId);
            rcv.setViewUser(userName);
            rcv.setViewTime(new Date());

            entityManager.persist(rcv);
            entityManager.flush();
        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while saving the review comment seen.");
        } finally {
            logExit("markReviewCommentSeen(tcSubject, commentId)");
        }

    }

    /**
     * Save specified review comment for specified section and specified contest id to persistence.
     *
     * Updated for Version 1.0.1
     *  - updates made as per the schema changes.
     *  - now SpecReview contains a list of SpecSectionReview. Each SpecSectionReview can contain set of comments.
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
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
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    public void saveReviewComment(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
            String comment, String role) throws SpecReviewServiceException {
        try {
            logEnter("saveReviewComment(tcSubject, contestId, studio, sectionName, comment, role)", tcSubject.getUserId(), contestId, studio,
                    sectionName, comment, role);

            String userName = getUserName(tcSubject);

            logDebug("UserName: " + userName);

            Query query = null;

            query = entityManager.createNativeQuery(SpecReviewIdByContestQuery);
            query.setParameter("contestId", contestId);
            query.setParameter("isStudio", studio ? 1 : 0);

            long specReviewId = 0;

            try {
                specReviewId = Long.parseLong(query.getSingleResult().toString());
                logDebug("Found specReview: " + specReviewId);
            } catch (NoResultException e) {
                // ignore it.
            }

            if (specReviewId == 0) {
                query = entityManager.createNamedQuery(ReviewStatusByNameQuery);
                query.setParameter("name", NotReadyStatus);

                ReviewStatus notReadyRS = (ReviewStatus) query.getSingleResult();

                logDebug("Found notReadyRS: " + notReadyRS);

                SpecReview specReview = new SpecReview();
                specReview.setSpecReviewId(UnsetId);
                specReview.setContestId(contestId);
                specReview.setStudio(studio);
                specReview.setReviewStatus(notReadyRS);

                specReview.setCreateUser(userName);
                specReview.setCreateTime(new Date());
                specReview.setModifyUser(userName);
                specReview.setModifyTime(new Date());
                specReview.setReadyForReviewTime(null);
                specReview.setReviewDoneTime(null);
                specReview.setReviewerId(new Long(0));
                specReview.setSectionReviews(null);

                entityManager.persist(specReview);
                entityManager.flush();

                specReviewId = specReview.getSpecReviewId();

                logDebug("Persisted specReview: " + specReviewId);
            }

            query = entityManager.createNamedQuery(SpecSectionReviewByContestAndSectionQuery);
            query.setParameter("specReviewId", specReviewId);
            query.setParameter("name", sectionName);

            SpecSectionReview specSectionReview = null;

            try {
                specSectionReview = (SpecSectionReview) query.getSingleResult();

                logDebug("Found specSectionReview: " + specSectionReview);
            } catch (NoResultException e) {
                // ignore it.
            }

            if (specSectionReview == null) {
                specSectionReview = new SpecSectionReview();
                specSectionReview.setSpecSectionReviewId(UnsetId);
                specSectionReview.setSpecReviewId(specReviewId);

                query = entityManager.createNamedQuery(ReviewStatusByNameQuery);
                query.setParameter("name", PendingStatus);

                ReviewStatus pendingRS = (ReviewStatus) query.getSingleResult();

                logDebug("Found pendingRS: " + pendingRS);

                query = entityManager.createNamedQuery(SectionTypeByNameQuery);
                query.setParameter("name", sectionName);
                query.setParameter("studio", studio);

                SectionType sectionType = (SectionType) query.getSingleResult();

                logDebug("Found sectionType: " + sectionType);

                specSectionReview.setReviewStatus(pendingRS);
                specSectionReview.setComments(null);
                specSectionReview.setCreateUser(userName);
                specSectionReview.setCreateTime(new Date());
                specSectionReview.setModifyUser(userName);
                specSectionReview.setModifyTime(new Date());
                specSectionReview.setSectionType(sectionType);

                entityManager.persist(specSectionReview);
                entityManager.flush();

                logDebug("Persisted specSectionReview: " + specSectionReview.getSpecReviewId());
            } else {

                 specSectionReview.setModifyUser(userName);
                 specSectionReview.setModifyTime(new Date());
                 entityManager.merge(specSectionReview);
                 entityManager.flush();
                 logDebug("Updated specSectionReview: " + specSectionReview.getSpecReviewId());
            }


            query = entityManager.createNamedQuery(ReviewUserRoleTypeByNameQuery);
            query.setParameter("name", role);
            ReviewUserRoleType roleType = (ReviewUserRoleType) query.getSingleResult();

            logDebug("Found roleType: " + roleType);

            ReviewComment rc = new ReviewComment();
            rc.setCommentId(UnsetId);
            rc.setComment(comment);
            rc.setRoleType(roleType);
            rc.setSpecSectionReviewId(specSectionReview.getSpecSectionReviewId());
            rc.setCreateUser(userName);
            rc.setCreateTime(new Date());

            entityManager.persist(rc);
            entityManager.flush();

            logDebug("Persisted ReviewComment: " + rc.getCommentId());
        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while saving review comment.");
        } finally {
            logExit("saveReviewComment(tcSubject, contestId, studio, sectionName, comment, role)");
        }
    }

    /**
     * Save specified review comment and review status for specified section and specified contest id to persistence.
     *
     * Updated for Version 1.0.1
     *  - updates made as per the schema changes.
     *  - now SpecReview contains a list of SpecSectionReview. Each SpecSectionReview can contain set of comments.
     *  - Overall status of the spec is saved to SpecReview.
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
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
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     */
    public void saveReviewStatus(TCSubject tcSubject, long contestId, boolean studio, String sectionName, String comment, boolean isPass,
            String role) throws SpecReviewServiceException {
        try {
            logEnter("saveReviewStatus(tcSubject, contestId, studio, sectionName, isPass, role)", tcSubject.getUserId(), contestId, studio,
                    sectionName, isPass, role);


            String userName = getUserName(tcSubject);

            logDebug("UserName: " + userName);

            Query query = null;

            query = entityManager.createNativeQuery(SpecReviewIdByContestQuery);
            query.setParameter("contestId", contestId);
            query.setParameter("isStudio", studio ? 1 : 0);

            long specReviewId = 0;

            try {
                specReviewId = Long.parseLong(query.getSingleResult().toString());
                logDebug("Found specReview: " + specReviewId);
            } catch (NoResultException e) {
                // ignore it.
            }

            if (specReviewId == 0) {
                query = entityManager.createNamedQuery(ReviewStatusByNameQuery);
                query.setParameter("name", NotReadyStatus);

                ReviewStatus notReadyRS = (ReviewStatus) query.getSingleResult();

                logDebug("Found notReadyRS: " + notReadyRS);

                SpecReview specReview = new SpecReview();
                specReview.setSpecReviewId(UnsetId);
                specReview.setContestId(contestId);
                specReview.setStudio(studio);
                specReview.setReviewStatus(notReadyRS);

                specReview.setCreateUser(userName);
                specReview.setCreateTime(new Date());
                specReview.setModifyUser(userName);
                specReview.setModifyTime(new Date());
                specReview.setReadyForReviewTime(null);
                specReview.setReviewDoneTime(null);
                specReview.setReviewerId(new Long(0));
                specReview.setSectionReviews(null);

                entityManager.persist(specReview);
                entityManager.flush();

                specReviewId = specReview.getSpecReviewId();

                logDebug("Persisted specReview: " + specReviewId);
            }

            query = entityManager.createNamedQuery(SpecSectionReviewByContestAndSectionQuery);
            query.setParameter("specReviewId", specReviewId);
            query.setParameter("name", sectionName);

            SpecSectionReview specSectionReview = null;

            try {
                specSectionReview = (SpecSectionReview) query.getSingleResult();

                logDebug("Found specSectionReview: " + specSectionReview);
            } catch (NoResultException e) {
                // ignore it.
            }

            if (specSectionReview == null) {
                specSectionReview = new SpecSectionReview();
                specSectionReview.setSpecSectionReviewId(UnsetId);
                specSectionReview.setSpecReviewId(specReviewId);

                query = entityManager.createNamedQuery(ReviewStatusByNameQuery);
                query.setParameter("name", isPass ? "PASSED" : "FAILED");

                ReviewStatus rs = (ReviewStatus) query.getSingleResult();

                logDebug("Found rs: " + rs);

                query = entityManager.createNamedQuery(SectionTypeByNameQuery);
                query.setParameter("name", sectionName);
                query.setParameter("studio", studio);

                SectionType sectionType = (SectionType) query.getSingleResult();

                logDebug("Found sectionType: " + sectionType);

                specSectionReview.setReviewStatus(rs);
                specSectionReview.setComments(null);
                specSectionReview.setCreateUser(userName);
                specSectionReview.setCreateTime(new Date());
                specSectionReview.setModifyUser(userName);
                specSectionReview.setModifyTime(new Date());
                specSectionReview.setSectionType(sectionType);

                entityManager.persist(specSectionReview);
                entityManager.flush();

                logDebug("Persisted specSectionReview: " + specSectionReview.getSpecReviewId());
            } else {
                query = entityManager.createNamedQuery(ReviewStatusByNameQuery);
                query.setParameter("name", isPass ? "PASSED" : "FAILED");

                ReviewStatus rs = (ReviewStatus) query.getSingleResult();
                specSectionReview.setReviewStatus(rs);
                specSectionReview.setModifyUser(userName);
                specSectionReview.setModifyTime(new Date());

                entityManager.merge(specSectionReview);
                entityManager.flush();
                logDebug("Persisted specSectionReview: " + specSectionReview.getSpecReviewId());
            }

            query = entityManager.createNamedQuery(ReviewUserRoleTypeByNameQuery);
            query.setParameter("name", role);
            ReviewUserRoleType roleType = (ReviewUserRoleType) query.getSingleResult();

            logDebug("Found roleType: " + roleType);

            ReviewComment rc = new ReviewComment();
            rc.setCommentId(UnsetId);
            rc.setComment(comment);
            rc.setRoleType(roleType);
            rc.setSpecSectionReviewId(specSectionReview.getSpecSectionReviewId());
            rc.setCreateUser(userName);
            rc.setCreateTime(new Date());

            entityManager.persist(rc);
            entityManager.flush();

            logDebug("Persisted ReviewComment: " + rc.getCommentId());

            Query statusUpdateQuery = entityManager.createNativeQuery(UpdateOverallReviewStatusQuery);
            statusUpdateQuery.setParameter("contestId", contestId);
            statusUpdateQuery.setParameter("isStudio", studio ? 1 : 0);
            int ret = statusUpdateQuery.executeUpdate();
            logDebug("Updated review status: " + ret);
        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while retrieving the spec reviews.");
        } finally {
            logExit("saveReviewStatus(tcSubject, contestId, studio, sectionName, comment, isPass, role)");
        }
    }

    /**
     * Gets List of <code>UpdatedSpecSectionData</code>, the updates made by reviewer of specs for specified contest id,
     * since last notification.
     *
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether the contest is studio or not
     * @return List of <code>UpdatedSpecSectionData</code>, the updates made by reviewer for specified contest id, since
     *         last notification.
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    @SuppressWarnings("unchecked")
    public List<UpdatedSpecSectionData> getReviewerUpdates(long contestId, boolean studio)
            throws SpecReviewServiceException {
        try {
            logEnter("getReviewerUpdates(contestId, studio)", contestId, studio);

            Query query = entityManager.createNativeQuery(GetReviewerUpdatesQuery, "UpdatedSpecSectionDataResults");
            query.setParameter("contestId", contestId);
            query.setParameter("isStudio", studio ? 1 : 0);

            List<UpdatedSpecSectionData> result = new ArrayList<UpdatedSpecSectionData>();

            List list = query.getResultList();


            for (int i = 0; i < list.size(); i++) {
                UpdatedSpecSectionData data = (UpdatedSpecSectionData) list.get(i);
                result.add(data);

            }


           /*
            List<UpdatedSpecSectionData> ret = new LinkedList<UpdatedSpecSectionData>();

            List resultList = (List) query.getResultList();
            for (int i = 0; i < resultList.size(); i++) {
                // row[0] ==> section name.
                // row[1] ==> status name.
                // row[2] ==> comment
                // row[3] ==> user who updated.
                UpdatedSpecSectionData r = new UpdatedSpecSectionData();
                Object[] row = (Object[]) resultList.get(i);
                r.setSectionName(row[0].toString());
                r.setStatus(row[1].toString());
                if (row[2] != null) {
                    r.setComment(row[2].toString());
                } else {
                    r.setComment("");
                }

                r.setUser(row[3].toString());

                ret.add(r);
            } */

            return result;
        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while retrieving the reviewer updates.");
        } finally {
            logExit("getReviewerUpdates(contestId, studio)");
        }
    }

    /**
     * Gets List of <code>UpdatedSpecSectionData</code>, the updates made by writer of specs for specified contest id,
     * since last notification.
     *
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether the contest is studio or not
     * @return List of <code>UpdatedSpecSectionData</code>, the updates made by writer for specified contest id, since
     *         last notification.
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    @SuppressWarnings("unchecked")
    public List<UpdatedSpecSectionData> getWriterUpdates(long contestId, boolean studio)
            throws SpecReviewServiceException {
        try {
            logEnter("getReviewerUpdates(contestId, studio)", contestId, studio);

            Query query = entityManager.createNativeQuery(GetWriterUpdatesQuery, "UpdatedSpecSectionDataResults");
            query.setParameter("contestId", contestId);
            query.setParameter("isStudio", studio ? 1 : 0);

            List<UpdatedSpecSectionData> result = new ArrayList<UpdatedSpecSectionData>();

            List list = query.getResultList();


            for (int i = 0; i < list.size(); i++) {
                UpdatedSpecSectionData data = (UpdatedSpecSectionData) list.get(i);
                result.add(data);

            }


            /*for (Object[] row : resultList) {
                // row[0] ==> section name.
                // row[1] ==> status name.
                // row[2] ==> comment
                // row[3] ==> user who updated.
                UpdatedSpecSectionData r = new UpdatedSpecSectionData();
                r.setSectionName(row[0].toString());
                r.setStatus(row[1].toString());
                if (row[2] != null) {
                    r.setComment(row[2].toString());
                } else {
                    r.setComment("");
                }

                r.setUser(row[3].toString());

                ret.add(r);
            }*/

            return result;
        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while retrieving the reviewer updates.");
        } finally {
            logExit("getReviewerUpdates(contestId, studio)");
        }
    }

    /**
     * Marks the spec_review to review done for specified contest id.
     *
     * It simply updates the review_done_time to the current.
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether contest is studio or not.
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReviewDone(TCSubject tcSubject, long contestId, boolean studio) throws SpecReviewServiceException {
        try {
            logEnter("markReviewDone(tcSubject, contestId, studio)", tcSubject.getUserId(), contestId, studio);


            String userName = getUserName(tcSubject);

            logDebug("UserName: " + userName);
            Query statusUpdateQuery = entityManager.createNativeQuery(MarkReviewDoneQuery);
            statusUpdateQuery.setParameter("contestId", contestId);
            statusUpdateQuery.setParameter("isStudio", studio ? 1 : 0);
            statusUpdateQuery.setParameter("modifyUser", userName);
            int ret = statusUpdateQuery.executeUpdate();

            logDebug("Updated review status: " + ret);
        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while markReviewDone.");
        } finally {
            logExit("markReviewDone(tcSubject, contestId, studio)");
        }
    }

    /**
     * Marks the spec_review to ready for review for specified contest id.
     *
     * It simply updates the ready_for_review_time to the current and updates the review status to READY.
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether contest is studio or not.
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReadyForReview(TCSubject tcSubject, long contestId, boolean studio) throws SpecReviewServiceException {
        try {
            logEnter("markReadyForReview(tcSubject, contestId, studio)", tcSubject.getUserId(), contestId, studio);

            String userName = getUserName(tcSubject);

            logDebug("UserName: " + userName);

            Query query = null;

            query = entityManager.createNativeQuery(SpecReviewIdByContestQuery);
            query.setParameter("contestId", contestId);
            query.setParameter("isStudio", studio ? 1 : 0);

            long specReviewId = 0;

            try {
                specReviewId = Long.parseLong(query.getSingleResult().toString());
                logDebug("Found specReview: " + specReviewId);
            } catch (NoResultException e) {
                // ignore it.
            }

            if (specReviewId == 0) {
                query = entityManager.createNamedQuery(ReviewStatusByNameQuery);
                query.setParameter("name", ReadyStatus);

                ReviewStatus readyRS = (ReviewStatus) query.getSingleResult();

                logDebug("Found readyRS: " + readyRS);

                SpecReview specReview = new SpecReview();
                specReview.setSpecReviewId(UnsetId);
                specReview.setContestId(contestId);
                specReview.setStudio(studio);
                specReview.setReviewStatus(readyRS);

                specReview.setCreateUser(userName);
                specReview.setCreateTime(new Date());
                specReview.setModifyUser(userName);
                specReview.setModifyTime(new Date());
                specReview.setReadyForReviewTime(new Date());
                specReview.setReviewDoneTime(null);
                specReview.setReviewerId(new Long(0));
                specReview.setSectionReviews(null);

                entityManager.persist(specReview);
                entityManager.flush();

                specReviewId = specReview.getSpecReviewId();

                logDebug("Persisted specReview: " + specReviewId);
            } else {
                Query statusUpdateQuery = entityManager.createNativeQuery(MarkReadyForReviewQuery);
                statusUpdateQuery.setParameter("specReviewId", specReviewId);
                statusUpdateQuery.setParameter("modifyUser", userName);
                int ret = statusUpdateQuery.executeUpdate();

                logDebug("Updated review status: " + ret);
            }
        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while markReadyForReview.");
        } finally {
            logExit("markReadyForReview(tcSubject, contestId, studio)");
        }
    }

    /**
     * Marks the spec_review to resubmit for review for specified contest id.
     *
     * It simply updates the ready_for_review_time to the current.
     * <p>
     * Update in v1.0.2: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId
     *            the specified contest id.
     * @param studio
     *            whether contest is studio or not.
     * @throws SpecReviewServiceException
     *             if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void resubmitForReview(TCSubject tcSubject, long contestId, boolean studio)
        throws SpecReviewServiceException {
        try {
            logEnter("resubmitForReview(tcSubject, contestId, studio)", contestId, studio);

            String userName = getUserName(tcSubject);

            logDebug("UserName: " + userName);
            Query statusUpdateQuery = entityManager.createNativeQuery(ResubmitForReviewQuery);
            statusUpdateQuery.setParameter("contestId", contestId);
            statusUpdateQuery.setParameter("isStudio", studio ? 1 : 0);
            statusUpdateQuery.setParameter("modifyUser", userName);
            int ret = statusUpdateQuery.executeUpdate();

            logDebug("Updated review status: " + ret);
        } catch (IllegalStateException e) {
            throw wrapSpecReviewServiceException(e, "The EntityManager is closed.");
        } catch (PersistenceException e) {
            throw wrapSpecReviewServiceException(e, "There are errors while resubmitForReview.");
        } finally {
            logExit("resubmitForReview(tcSubject, contestId, studio)");
        }
    }

    /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session context.
     * </p>
     *
     * @return the EntityManager looked up from the session context
     *
     * @throws SpecReviewServiceException
     *             the spec review service exception
     */
    private EntityManager getEntityManager() throws SpecReviewServiceException {
        try {
            Object obj = sessionContext.lookup(unitName);

            if (obj == null) {
                throw wrapSpecReviewServiceException("The object for jndi name '" + unitName + "' doesn't exist.");
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
            throw wrapSpecReviewServiceException(e, "The jndi name for '" + unitName
                    + "' should be EntityManager instance.");
        }
    }

    /**
     * <p>
     * This method used to log enter in method. It will persist both method name and it's parameters if any.
     * </p>
     *
     * @param method
     *            name of the entered method
     * @param params
     *            array containing parameters used to invoke method
     */
    private void logEnter(String method, Object... params) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Enter method UserSyncServiceBean.{0} with parameters {1}.", method, Arrays
                    .deepToString(params));
        }
    }

    /**
     * <p>
     * This method used to log arbitrary debug message. It will persist debug message.
     * </p>
     *
     * @param message
     *            message information
     */
    private void logDebug(String msg) {
        if (logger != null) {
            logger.log(Level.DEBUG, msg);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     *
     * @param method
     *            name of the leaved method
     */
    private void logExit(String method) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Leave method {0}.", method);
        }
    }

    /**
     * <p>
     * This method used to log leave of method. It will persist method name.
     * </p>
     *
     * @param method
     *            name of the leaved method
     * @param returnValue
     *            value returned from the method
     */
    private void logExit(String method, Object returnValue) {
        if (logger != null) {
            logger.log(Level.DEBUG, "Leave method {0} with return value {1}.", method, returnValue);
        }
    }

    /**
     * <p>
     * This method used to log arbitrary error. It will persist error's data.
     * </p>
     *
     * @param error
     *            exception describing error
     * @param message
     *            additional message information
     */
    private void logError(Throwable error, String message) {
        if (error == null) {
            logError(message);
        }
        if (logger != null) {
            logger.log(Level.ERROR, error, message);
        }
    }

    /**
     * <p>
     * This method used to log arbitrary error. It will persist error's data.
     * </p>
     *
     * @param message
     *            message information
     */
    private void logError(String message) {
        if (logger != null) {
            logger.log(Level.ERROR, message);
        }
    }

    /**
     * <p>
     * Creates a <code>SpecReviewServiceException</code> with inner exception and message. It will log the exception,
     * and set the sessionContext to rollback only.
     * </p>
     *
     * @param e
     *            the inner exception
     * @param message
     *            the error message
     *
     * @return the created exception
     */
    private SpecReviewServiceException wrapSpecReviewServiceException(Exception e, String message) {
        SpecReviewServiceException ce = new SpecReviewServiceException(message, e);
        logError(ce, message);
        sessionContext.setRollbackOnly();

        return ce;
    }

    /**
     * <p>
     * Creates a <code>ContestManagementException</code> with inner exception and message. It will log the exception,
     * and set the sessionContext to rollback only.
     * </p>
     *
     * @param message
     *            the error message
     *
     * @return the created exception
     */
    private SpecReviewServiceException wrapSpecReviewServiceException(String message) {
        SpecReviewServiceException ce = new SpecReviewServiceException(message);
        logError(ce, message);
        sessionContext.setRollbackOnly();

        return ce;
    }
}
