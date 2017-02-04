/*
 * Copyright (C) 2009 - 2016 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.facade.contest.ejb;

import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.cronos.onlinereview.services.uploads.UploadExternalServices;
import com.cronos.onlinereview.services.uploads.UploadServicesException;
import com.cronos.onlinereview.services.uploads.impl.DefaultUploadExternalServices;
import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.CompForum;
import com.topcoder.catalog.entity.Phase;
import com.topcoder.catalog.entity.Status;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.catalog.service.CatalogService;
import com.topcoder.catalog.service.EntityNotFoundException;
import com.topcoder.clientcockpit.phases.EmailMessageGenerationException;
import com.topcoder.clientcockpit.phases.EmailMessageGenerator;
import com.topcoder.clientcockpit.phases.EmailSendingException;
import com.topcoder.clientcockpit.phases.messagegenerators.DefaultEmailMessageGenerator;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.ProjectContestFee;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.dao.CopilotProjectDAO;
import com.topcoder.direct.services.copilot.dao.LookupDAO;
import com.topcoder.direct.services.copilot.dao.impl.CopilotProfileDAOImpl;
import com.topcoder.direct.services.copilot.dao.impl.CopilotProjectDAOImpl;
import com.topcoder.direct.services.copilot.dao.impl.LookupDAOImpl;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProject;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.deliverable.search.UploadFilterBuilder;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.project.FileType;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Prize;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.project.ProjectPlatform;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.review.ReviewManagementException;
import com.topcoder.management.review.application.ReviewAuction;
import com.topcoder.management.review.application.ReviewAuctionManager;
import com.topcoder.management.review.application.ReviewAuctionManagerException;
import com.topcoder.management.review.application.ReviewAuctionType;
import com.topcoder.management.review.application.impl.ReviewAuctionManagerImpl;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.CommentType;
import com.topcoder.management.review.data.Item;
import com.topcoder.management.review.data.Review;
import com.topcoder.management.scorecard.data.Group;
import com.topcoder.management.scorecard.data.Question;
import com.topcoder.management.scorecard.data.Scorecard;
import com.topcoder.management.scorecard.data.Section;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.TCSEmailMessage;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.service.ContestSaleData;
import com.topcoder.project.service.FullProjectData;
import com.topcoder.project.service.ProjectServices;
import com.topcoder.project.service.ProjectServicesException;
import com.topcoder.project.service.ScorecardReviewData;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.SearchBundle;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.security.RolePrincipal;
import com.topcoder.security.TCSubject;
import com.topcoder.security.groups.model.GroupPermissionType;
import com.topcoder.security.groups.model.ResourceType;
import com.topcoder.security.groups.services.AuthorizationService;
import com.topcoder.security.groups.services.hibernate.HibernateAuthorizationService;
import com.topcoder.service.contest.eligibility.ContestEligibility;
import com.topcoder.service.contest.eligibility.GroupContestEligibility;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityManager;
import com.topcoder.service.contest.eligibility.dao.ContestEligibilityPersistenceException;
import com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManager;
import com.topcoder.service.contest.eligibilityvalidation.ContestEligibilityValidationManagerException;
import com.topcoder.service.facade.contest.CodeNameUtil;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.facade.contest.ProjectStatusData;
import com.topcoder.service.facade.contest.ProjectSummaryData;
import com.topcoder.service.facade.contest.SoftwareContestPaymentResult;
import com.topcoder.service.facade.contest.notification.ContestNotification;
import com.topcoder.service.facade.contest.notification.ProjectNotification;
import com.topcoder.service.facade.permission.PermissionServiceFacade;
import com.topcoder.service.payment.CreditCardPaymentData;
import com.topcoder.service.payment.PaymentData;
import com.topcoder.service.payment.PaymentException;
import com.topcoder.service.payment.PaymentProcessor;
import com.topcoder.service.payment.PaymentResult;
import com.topcoder.service.payment.TCPurhcaseOrderPaymentData;
import com.topcoder.service.payment.paypal.PayflowProPaymentProcessor;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionService;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.ProjectPermission;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.CompetionType;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.UserNotFoundFault;
import com.topcoder.service.review.specification.SpecificationReviewService;
import com.topcoder.service.review.specification.SpecificationReviewServiceException;
import com.topcoder.service.specreview.SpecReview;
import com.topcoder.service.specreview.SpecReviewService;
import com.topcoder.service.specreview.SpecReviewServiceException;
import com.topcoder.service.specreview.UpdatedSpecSectionData;
import com.topcoder.service.user.Registrant;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.ApplicationServer;
import com.topcoder.shared.util.DBMS;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.config.Property;
import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.DocumentGeneratorFactory;
import com.topcoder.util.file.Template;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.web.common.RowNotFoundException;
import com.topcoder.web.ejb.forums.Forums;
import com.topcoder.web.ejb.forums.ForumsHome;
import com.topcoder.web.ejb.project.ProjectRoleTermsOfUse;
import com.topcoder.web.ejb.project.ProjectRoleTermsOfUseHome;
import com.topcoder.web.ejb.user.UserPreference;
import com.topcoder.web.ejb.user.UserPreferenceHome;
import com.topcoder.web.ejb.user.UserTermsOfUse;
import com.topcoder.web.ejb.user.UserTermsOfUseHome;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * <p>
 * This is an implementation of <code>Contest Service Facade</code> web service in form of stateless session EJB. It
 * holds a reference to {@link StudioService} which is delegated the fulfillment of requests.
 * </p>
 * <p>
 * Module Cockpit Contest Service Enhancement Assembly change: Several new methods related to the permission and
 * permission type are added.
 * </p>
 * <p>
 * Module Cockpit Share Submission Integration Assembly change: Added method to retrieve all permissions by projectId.
 * Version 1.0.1 (Cockpit Release Assembly 5 v1.0) Change Notes: - Added method to retrieve contest fees by given
 * billing project id. </p> </p> Version 1.0.2 (Spec Reviews Finishing Touches v1.0) Change Notes: - Made the
 * getSpecReviews method return instance of SpecReview rather than a list. - Added the methods to mark ready for review,
 * review done and resubmit for review. </p>
 * <p>
 * Version 1.0.3 (Cockpit Software Contest Payments v1.0) Change notes: - For software contest, payment is made for the
 * sum of various costs. - While doing so, only the increased amount is paid (if earlier payments were made). -
 * Introduced constants for new cost types
 * </p>
 * <p>
 * Version 1.0.4 - Add 'Applications'/'Components' to resource for project
 * </p>
 * -----------------------changed in the version 1.1-----------------
 * Four methods are added
 * setSubmissionCheckpointPrize(submissionId:long,checkpointPrizeId:long):void
 * getUserContests(userName:String):List<StudioCompetition>
 * getCheckpointSubmissionsForContest(contestId:long):List<SubmissionData>
 * getFinalSubmissionsForContest(contestId:long):List<SubmissionData>
 * -----
 *
 * <p>
 * Changes in v1.2 (Studio Multi-Rounds Assembly - Launch Contest): Added default checkpoint date when contest is
 * created.
 * </p>
 * <p>
 * Changes in v1.2.1 updated to set creator user as Observer created.
 * </p>
 * <p>
 * Changes in v1.2.2 - Cockpit Release Assembly 11 Add method getDesignComponents to get design components.
 * </p>
 * <p>
 * Changes in v1.3 (Prototype Conversion Studio Multi-Rounds Assembly - Submission Viewer UI): - Added a flag to
 * updateSubmissionUserRank method to support ranking checkpoint submissions. - Added support for checkpoint
 * prizes payment.
 * </p>
 * <p>
 * Changes in v1.3.1 Added elegibility services.
 * </p>
 * <p>
 * Changes in v1.3.2 Added support for eligibility services.
 * </p>
 * <p>
 * Changes in v1.3.3 Added permission check.
 * </p>
 * <p>
 * Changes in v1.4 (Cockpit Spec Review Backend Service Update v1.0):
 * - Added method to create specification review project for an existing project.
 * - Added method to get scorecard and review information for a specific project.
 * - Added method to upload a mock submission / final fixes to the associated specification review of a
 * project to make it ready for review. - Added method to add comments to an existing review.
 * </p>
 * <p>
 * Change in v1.4.1 (Cockpit Spec Review -stage 2 v1.0)
 * - Add spec review project id
 * - After activiation of contests, create spec review project
 * </p>
 * <p>
 * Changes in v1.5 (Cockpit Release Assembly - Contest Repost and New Version v1.0):
 * - Added method to re open failed software contest.
 * - Added method to create new version for development or design contest.
 * - Refactor the create/update-software-contest methods
 * </p>
 * <p>
 * Changes in v1.5.1(Cockpit Security Facade V1.0):
 *  - It is not a web-service any more.
 *  - All the methods accepts a parameter TCSubject which contains all the security info for current user.
 *    The implementation EJB should use TCSubject and now get these info from the sessionContext.
 *  - Please use the new ContestServiceFacadeWebService as the facade now. That interface will delegates all the methods
 *    to it.
 *  - UserService is used to get the user-name for the given user-id.
 * </p>
 * <p>
 * Changes in v1.6(Direct Search Assembly v1.0): Adds getProjectData method to return project data with aggregate contest
 * information in different status. Change getCommonProjectContestData method to add payment information.
 * </p>
 * <p>
 * Changes in v1.6.1, two public methods are added (BUGR - 3706):
 * - List<ProjectNotification> getNotificationsForUser(TCSubject subject, long userId)
 * - updateNotifcationsForUser(TCSubject subject, long userId, List<ProjectNotification> notifications)
 * </p>
 *
 * <p>
 * Version 1.6.2 (Direct Permissions Setting Back-end and Integration Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getProjectPermissions(TCSubject)} method.</li>
 *     <li>Added {@link #updateProjectPermissions(TCSubject, List)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.6.3 (Direct Submission Viewer Release 4 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #updateSubmissionsGeneralFeedback(TCSubject, long, String)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.6.4 (TC Direct - Launch Copilot Selection Contest assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #COPILOT_CONTEST_PROJECT_CATEGORY_ID} field and
 *     {@link #isCopilotContest(SoftwareCompetition)} method, update {@link #createUpdateAssetDTO} method.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 1.6.5 (Manage Copilot Postings Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #addReviewer(TCSubject, long, long)} method.</li>
 *     <li>Added {@link ContestServiceFacade#getReview(long,long,long)} method.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 1.6.6 (TC Direct Replatforming Release 1) Change notes:
 * <ul>
 * <li>Add {@link #studioForumBeanProviderUrl} field.</li>
 * <li>Add {@link #processContestCreditCardSale(TCSubject, SoftwareCompetition, CreditCardPaymentData, Date)} method.</li>
 * <li>Update {@link #processContestCreditCardSale(TCSubject, SoftwareCompetition, CreditCardPaymentData)} method.</li>
 * <li>Add {@link #processContestPurchaseOrderSale(TCSubject, SoftwareCompetition, TCPurhcaseOrderPaymentData, Date)} method.</li>
 * <li>Update {@link #processContestPurchaseOrderSale(TCSubject, SoftwareCompetition, TCPurhcaseOrderPaymentData)} method.</li>
 * <li>Update {@link #processContestSaleInternal(TCSubject, SoftwareCompetition, PaymentData, Date)} method.</li>
 * <li>Add {@link #createSoftwareContest(TCSubject, SoftwareCompetition, long, Date)} method.</li>
 * <li>Update {@link #createSoftwareContest(TCSubject, SoftwareCompetition, long)} method.</li>
 * <li>Add {@link #updateSoftwareContest(TCSubject, SoftwareCompetition, long, Date)} method.</li>
 * <li>Update {@link #updateSoftwareContest(TCSubject, SoftwareCompetition, long)} method.</li>
 * <li>Add {@link #getAllFileTypes()} method.</li>
 * <ii>Update {@link #createUpdateAssetDTO(TCSubject, SoftwareCompetition)} method to create forums for studio contest.</li>
 * <li>Add {@link #createStudioForum(String, long)} method and update {@link #createForum(TCSubject, AssetDTO, long, long)} method.</li>
 * <li>Add {@link #getForumsEJBFromJNDI(String)} method.</li>
 * <li>Add @link #isStudio(SoftwareCompetition)} method.</li>
 * </ul>
 * </p>
 * @author snow01, pulky, murphydog, waits, BeBetter, hohosky, isv, tangzx, TCSASSEMBER
 * @version 1.6.6
 * </p> *
 * Version 1.6.6 (TC Direct Release Assembly 7) Change notes:
 *   <ol>
 *     <li>Updated {@link #checkStudioSubmissionPermission} method and
 *     {@link #processContestSaleInternal} method.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 1.6.7 (TC Direct - Permission Updates) Change notes:
 *   <ol>
 *     <li>Updated {@link #updateProjectPermissions(TCSubject, List<ProjectPermission>, long)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.6.8 (TC Direct Replatforming Release 2) Change notes:
 * <ul>
 * <li>Added {@link #CHECKPOINT_PRIZE_TYPE_ID} field.</li>
 * <li>Updated {@link #processContestSaleInternal(TCSubject, SoftwareCompetition, PaymentData, Date)} method to
 * process checkpoint prizes for software contest and specification review cost for studio contest.</li>
 * </ul>
 * </p>
 *
 * @author snow01, pulky, murphydog, waits, BeBetter, hohosky, isv, tangzx, TCSASSEMBER
 * @version 1.6.8
 * <p>
 * Version 1.6.8 (TC Direct - Software Contest Creation Update) Change notes:
 *   <ol>
 *     <li>Update method <code>createContestResources</code> to create copilot resource and set form permission/watch if exists.</li>
 *     <li>Update method <code>updateContestResources</code> to update copilots and update forum permission/watch</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.6.9 (BUGR-4582) Change notes:
 *   <ol>
 *     <li>Update {@link #getProjectData(TCSubject)}</li>
 *   </ol>
 * </p>
 * <p>
 * Version 1.7.0 Release Assembly - Direct Improvements Assembly Release 3 Change notes:
 *   <ol>
 *     <li>add the logic to judge whether a pay is activation or additional pay and send different emails</li>
 *     <li>correct the direct project name in the payment email of software and studio competition.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.6.9 (TC Direct Replatforming Release 3) Change notes:
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
 * Version 1.6.10 (TC Direct Replatforming Release 5) Change notes:
 * <ul>
 * <li>Changed method name from <code>getCheckpointSubmissions</code> to {@link #getSoftwareActiveSubmissions(long, int)}. The new method
 * support searching the active submissions for a specified submission type.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6.11 (TCCC-3153) Change notes:
 * <ul>
 * <li>Fixed forums management logic to update Studio and Software forums correctly.</li>
 * </ul>
 *
 * <p>
 * Version 1.7.1 Release Assembly - TopCoder Cockpit Project Status Management Change notes:
 * <ul>
 * <li>Set cockpit project status id in {@link #getProjectData(com.topcoder.security.TCSubject)}</li>
 * </ul>
 *
 * <p>
 * Version 1.7.2 (TopCoder Cockpit Project Overview R2 Project Forum Backend Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added createTopCoderDirectProjectForum(TCSubject, long, Long) method.</li>
 *     <li>Added {@link #createProjectForums} property.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.3 (TCCC-3658) Change notes:
 *   <ol>
 *     <li>Removed dependencies to studio components</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.4 (Add Reporting Contest Type) Change notes:
 *   <ol>
 *     <li>Set DR flag off for reporting contest type.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.7.5 (Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar) Change notes:
 *   <ol>
 *     <li>Add project creation date to {@link #getProjectData(com.topcoder.security.TCSubject)}</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.8.0 (Release Assembly - TC Cockpit Create Project Refactoring Assembly Part One) Change notes:
 *    <ul>
 *        <li>Refactor all the permission related methods to permission service facade</li>
 *        <li>Change the invokes of permission related methods to call permission service facade</li>
 *    </ul>
 * </p>
 *
 * <p>
 * Version 1.8.2 (Release Assembly - TC Direct Cockpit Release Two version 1.0) Change notes:
 *    <ul>
 *        <li>
 *         Update {@link #selectCopilot(com.topcoder.security.TCSubject, long, long, long, int, long)}
 *         to support 'not choose 2nd copilot'
 *         </li>
 *    </ul>
 * </p>
 *
 * <p>
 * Version 1.8.3 (Release Assembly - TopCoder Bug Hunt Assembly Integration 2) change notes:
 * <ul>
 *     <li>
 *         Add contant {@link #RESOURCE_INFO_PAYMENT}
 *     </li>
 *     <li>
 *         Add method {@link #shouldAutoCreateBugHuntContest(com.topcoder.service.project.SoftwareCompetition)} to check
 *         whether the contest to create needs a bug hunt contest auto created.
 *     </li>
 *     <li>
 *         Add method {@link #autoCreateBugHuntContest(com.topcoder.security.TCSubject,
 *         com.topcoder.service.project.SoftwareCompetition, long,
 *         com.topcoder.service.project.SoftwareCompetition, java.util.Date)} to set up the bug hunt contest for auto
 *         creation.
 *     </li>
 *     <li>
 *         Update method {@link #createContestResources(com.topcoder.security.TCSubject,
 *         com.topcoder.service.project.SoftwareCompetition, long)} to auto insert the reviewer for bug hunt contest.
 *     </li>
 *     <li>
 *         Update method {@link #createUpdateAssetDTO(com.topcoder.security.TCSubject,
 *         com.topcoder.service.project.SoftwareCompetition)} to reuse the asset DTO for auto bug hunt contest.
 *     </li>
 *     <li>
 *         Update method {@link #createSoftwareContest(com.topcoder.security.TCSubject,
 *         com.topcoder.service.project.SoftwareCompetition, long, java.util.Date, java.util.Date)} to automatically
 *         create bug hunt contest for assembly contest.
 *     </li>
 * </ul>
 * </p>
 * <p>
 * Version 1.8.4 (System Assembly - TopCoder Security Groups Frontend - Permission Propagation Integration) Change notes:
 * <ul>
 *   <li>
 *       Added fields {@link #securityGroupsHibernateConfig}, {@link #authorizationService}.
 *   </li>
 *   <li>
 *       Updated {@link #init()} to create the AuthorizationService instance.
 *   </li>
 *   <li>
 *       Added {@link #checkGroupPermissionForProject(TCSubject, long, boolean)} to check group permission for project.
 *   </li>
 *   <li>
 *       Updated {@link #checkSoftwareProjectPermission(TCSubject, long, boolean)} and
 *         {@link #checkSoftwareContestPermission(TCSubject, long, boolean)} to call new helper method
 *         {@link #checkGroupPermissionForProject(TCSubject, long, boolean)} before throwing exception.
 *   </li>
 * </ul>
 * </p>
 * <p>
 * Version 1.8.4 (Release Assembly - TC Direct Cockpit Release Three version 1.0)
 *    <ul>
 *        <li>
 *        Add {@link #customContestActivationEmailTempaltes}, {@link #customActivateContestReceiptContestTypeIds}
 *        and {@link #customActivateContestReceiptEmailTemplatePaths} to stores the custom activation email tempaltes
 *        configuration for different contest types.
 *        </li>
 *        <li>
 *        Add the logic in {@link #processContestSaleInternal} to dynamically choose email template according to
 *        contest type.
 *        </li>
 *        <li>
 *        Fix the amount of total member cost and total cost in activation email.
 *        </li>
 *        <li>
 *        Use the total member cost and total cost in the email sent when prize gets edited.
 *        </li>
 *    </ul>
 * </p>
 * <p>
 * Version 1.8.5 (Release Assembly - TC Direct Cockpit Release Four version 1.0)
 *    <ul>
 *        <li>
 *        Update to start spec review when activating contest.
 *        </li>
 *        <li>
 *        Update to sending copilot winner email when picking copilot posting winner.
 *        </li>
 *    </ul>
 * </p>
 *
 * <p>
 * Version 1.8.6 (Release Assembly - TC Direct Issue Tracking Tab Update Assembly 2 v1.0) change notes:
 *   <ol>
 *     <li>Added method {@link #getActiveUploads(long, int)} to retrieve the active uploads.</li>
 *   </ol>
 * </p>
 *
 *<p>
 * Version 1.8.6 (Release Assembly - TopCoder Cockpit Project Dashboard Project Type and Permission Notifications Integration)
 *  <ul>
 *     <li>
 *      Fixed {@link #updateNotificationsForUser} not use <code>userId</code> argument when update forum notification.
 *     </li>
 *  </ul>
 * </p>
 * <p>
 * Version 1.8.7 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) change notes:
 *    <ul>
 *        <li>
 *        Added {@link #saveSoftwareCheckpointReviewWithRankAndFeedback(TCSubject, long, long[], int[],
 *        String[], Boolean, String)} to support software checkpoint management.
 *        </li>
 *    </ul>
 * </p>
 * <p>
 *
 * <p>
 * Version 1.9.0 (Module Assembly - TC Cockpit Project Contests Batch Edit) changes:
 * <ul>
 *     <li>Add method {@link #deleteSoftwareContests(com.topcoder.security.TCSubject, long, java.util.List)}</li>
 *     <li>Add method {@link #batchUpdateDraftSoftwareContests(com.topcoder.security.TCSubject, long, java.util.List)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9.1 (Release Assembly - TopCoder Cockpit Post Software Checkpoint Feedback to Forum v1.0) changes:
 * <ul>
 *    <li>Updated method {@link #saveSoftwareCheckpointReviewWithRankAndFeedback(TCSubject, long, long[], int[],
 *        String[], Boolean, String)} to post feedbacks to forums.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9.2 (Release Assembly - TopCoder Security Groups Release 5 v1.0) changes:
 * <ul>
 *    <li>Updated method {@link #checkGroupPermissionForProject(TCSubject, long, boolean)} to fix the bug when checking
 *    whether a user has read permission on a project.</li>
 * </ul>
 * </p>
 *
 * Version 1.9.2 (Release Assembly - TC Direct Cockpit Release Eight)
 * <ul>
 *     <li>Add the property {@link #projectManagerOFKey}</li>
 *     <li>Add the property {@link #projectManager}</li>
 *     <li>Update the method {@link #createSoftwareContest(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition, long, java.util.Date, java.util.Date)}</li>
 *     <li>Update the method {@link #updateSoftwareContest(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition, long, java.util.Date, java.util.Date)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9.3 (Release Assembly - TC Direct Cockpit Release Ten)
 * <ul>
 *     <li>Add method {@link #checkContestBillingAccount(long, long)}</li>
 *     <li>Update the method {@link #createSoftwareContest(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition, long, java.util.Date, java.util.Date)} to
 *     add checking of contest billing account</li>
 *     <li>Update the method {@link #updateSoftwareContest(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition, long, java.util.Date, java.util.Date)}
 *     to add the checking of contest billing account
 *     </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9.4 (Release Assembly - TC Cockpit Operations Dashboard Improvements 2) changes:
 * <ul>
 *    <li>Updated method {@link #processContestSaleInternal} to
 *    set 'Historical Projected Cost' and 'Project Activate Date'.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version  1.9.5 (BUGR-7954) updates
 * <ol>
 *     <li>Add {@link #addReviewAuction(SoftwareCompetition)} to add review auction when activate contest.</li>
 *     <li>Add {@link #reviewAuctionManager} to process review auctions.</li>
 *     <li>Add some static fields used while adding review auctions.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 1.9.6 (Release Assembly - TopCoder Cockpit Copilot Selection Update and Other Fixes Assembly)
 * <ol>
 *     <li>Update signature of method selectCopilot to {@link #selectCopilot(com.topcoder.security.TCSubject, long, long, long, long, long)}
 *     and update the method to pick up copilot posting 1st and 2nd place in one transaction</li>
 * </ol>
 * </p>
 *
 * Version 1.9.7 BUGR-8788 (TC Cockpit - New Client Billing Config Type) change notes:
 *  <ul>
 *      <li> Add {@link #requireBillingProjectCCA(long)} to check cca status of billing account</li>
 *      <li> Add {@link #requireBillingProjectsCCA(long[])} to check cca status of some of billing accounts</li>
 *      <li> Add {@link #checkBillingProjectCCA(SoftwareCompetition)} to check and update cca status of project header</li>
 *  </ul>
 * </p>
 *
 * <p>
 * Version 1.9.8 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) Change notes:
 *   <ol>
 *     <li>Updated {@link #getSoftwareContestByProjectId(TCSubject, long)} method to remove the block of code which
 *     was clearing the dependencies and project from the project phases. This was not correct.</li>
 *     <li>Updated {@link #getStudioSubmissionFeedback(TCSubject, long, long, PhaseType)} to support getting feedbacks
 *     from Approval reviews.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.9.9 (Module Assembly - TC Cockpit Contest Milestone Association 1) change notes:
 *  <ul>
 *      <li> Updated {@link #createSoftwareContest(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition, long, java.util.Date, java.util.Date)}
 *      to add contest and milestone association when creating the new contest</li>
 *      <li> Updated {@link #updateSoftwareContest(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition, long, java.util.Date, java.util.Date)}
 *      to update contest and milestone association when updating a contest</li>
 *      <li> Add {@link #getSoftwareContestByProjectId(com.topcoder.security.TCSubject, long)} to
 *      include contest milestone association data in the retrieved contest data</li>
 *  </ul>
 * </p>
 *
 * <p>
 * Version 2.0.0 (Module Assembly - TC Cockpit Launch Code contest)
 * <ul>
 *     <li>Added method {@link #isCodeContest(com.topcoder.service.project.SoftwareCompetition)} to check if
 *     the contest is of type Code</li>
 *     <li>Updated method {@link #createUpdateAssetDTO(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition)}
 *     to set project properties For Code to disable Rated, DR, Reliability Eligible, and Approval.</li>
 *
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.0.1 (Module Assembly - TC Cockpit Launch F2F contest)
 * <ul>
 *     <li>Added method {@link #isF2FContest(com.topcoder.service.project.SoftwareCompetition)} to check if
 *     the contest is of type First2Finish</li>
 *     <li>Updated method {@link #createUpdateAssetDTO(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition)}
 *     to set project properties For First2Finish to disable Rated, DR, Reliability, Approval and allow multiple submissions.</li>
 *     <li>Added method {@link #getAllProjectPlatforms()} to get all project platform options</li>
 *
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.0.2 (Release Assembly - Publish Event on Contest Creation in TopCoder Cockpit) change notes:
 *  <ul>
 *      <li>Added {@link #amazonSNSClient} used to publish SNS event.</li>
 *      <li>Added {@link #contestCreationArn} used to represent arn.</li>
 *      <li>Added {@link #contestCreationSubject} used to represent subject.</li>
 *      <li>Added {@link #contestCreationMessageTemplate} used to represent template.</li>
 *      <li>Added {@link #AWS_CREDENTIALS_FILE} to represent aws credentials file.</li>
 *      <li>Updated {@link #createSoftwareContest(TCSubject, SoftwareCompetition, long, Date, Date)}
 *      to publish event.</li>
 *      <li>Updated {@link #init()} to init new added fields.</li>
 *  </ul>
 * </p>
 *
 * <p>
 * Version 2.1 (Release Assembly - TC Cockpit Private Challenge Update)
 * <ul>
 *     <li>Removed method getEligibilityId</li>
 *     <li>Removed method getEligibilityName</li>
 *     <li>Removed method getEligibilityAdminRole</li>
 *     <li>Updated method {@link #persistContestEligility(com.topcoder.management.project.Project, com.topcoder.service.contest.eligibility.ContestEligibility, boolean)}
 *     to use the security group specified in the SoftwareCompeitition.ProjectHeader.SecurityGroupId</li>
 *     <li>Updated method {@link #getSoftwareContestByProjectId(com.topcoder.security.TCSubject, long)} to set the SecurityGroupId of the contest</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.2 (TC Cockpit Auto Assign Reviewer for First2Finish challenge)
 * <ul>
 *     <li>Updated {@link #createContestResources(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition, long, boolean)} to
 *     add Iterative Reviewer Resource from the passed-in SoftwareCompetition</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.3 (TC Cockpit CMC Task Update Events Push)
 * <ul>
 *     <li>Added {@link #contestUpdateSubject}</li>
 *     <li>Added {@link #contestUpdateMessageTemplate}</li>
 *     <li>Updated {@link #updateSoftwareContest(com.topcoder.security.TCSubject,
 *     com.topcoder.service.project.SoftwareCompetition, long, java.util.Date, java.util.Date)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.4 (Release Assembly - TC Cockpit New Challenge types Integration Bug Fixes)
 *     <ul>
 *         <li>Updated method {@link #updateSoftwareContest(com.topcoder.security.TCSubject,
 *         com.topcoder.service.project.SoftwareCompetition, long, java.util.Date, java.util.Date)} to handle the
 *         deletion of contest milestone association.</li>
 *     </ul>
 * </p>
 *
 * <p>
 * Version 2.5 (First2Finish - TC Cockpit Auto Assign Reviewer Update)
 *     <ul>
 *         <li>Updated {@link #createContestResources(com.topcoder.security.TCSubject,
 *         com.topcoder.service.project.SoftwareCompetition, long, boolean)} to add auto assigned reviewer</li>
 *         <li>Added {@link #createIterativeReviewerResource(long, long, long)} to create iterative reviewer resource</li>
 *         <li>Added {@link #createReviewerResource(long, long, long, boolean)} to create reviewer resource</li>
 *         <li>Updated {@link #updateSoftwareContest(com.topcoder.security.TCSubject,
 *         com.topcoder.service.project.SoftwareCompetition, long)} to auto add reviewer for F2F/Code when updating contest</li>
 *     </ul>
 * </p>
 *
 * <p>
 * Version 2.6 (TC Cockpit Push Amazon SNS event for challenge update)
 * <ul>
 *      <li>Updated {@link #processContestSaleInternal(com.topcoder.security.TCSubject,
 *      com.topcoder.service.project.SoftwareCompetition,
 *      com.topcoder.service.payment.PaymentData, java.util.Date, java.util.Date)} to send Amazon SNS when activating
 *      challenge with CMC Task ID assigned</li>
 *      <li>Updated {@link #updateSoftwareContest(com.topcoder.security.TCSubject,
 *      com.topcoder.service.project.SoftwareCompetition, long, java.util.Date, java.util.Date)} send Amazon SNS when
 *      updating start date, end date of the challenge</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.7 (F2F - TC Cockpit Create Challenge Auto Add Manager)
 * <ul>
 *     <li>Updated {@link #createContestResources(com.topcoder.security.TCSubject,
 *     com.topcoder.service.project.SoftwareCompetition, long, boolean)} to set launcher as manager is it's not
 *     copilot</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.8 (F2F - TC Cockpit Update Bug Hunt type)
 * <ul>
 *     <li>Updated {@link #autoCreateBugHuntContest(com.topcoder.security.TCSubject,
 *     com.topcoder.service.project.SoftwareCompetition,
 *     long, com.topcoder.service.project.SoftwareCompetition, java.util.Date)} to set second prize of auto created
 *     bug hunt to 0</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.9 (F2F - TC Direct - Locate Activate Contest Issue)
 * <ul>
 *     <li>Updated {@link #updateSoftwareContest(com.topcoder.security.TCSubject,
 *     com.topcoder.service.project.SoftwareCompetition, long, java.util.Date, java.util.Date)} to prevent update activated
 *     contest to draft</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 3.0 (Module Assembly - TC Direct Studio Design First2Finish Challenge Type)
 * <ul>
 *  <li>Added method {@link #isDesignF2FContest(com.topcoder.service.project.SoftwareCompetition)}</li>
 *  <li>Updated method {@link #createUpdateAssetDTO(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition)}
 *  to set allow multiple submission to true of Design First2Finish and turn off rate, reliability, approval, DR</li>
 *  <li>Updated {@link #saveStudioSubmisionWithRankAndFeedback} to close the reg and sub for Design First2Finish
 *  because processing the auto review</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 3.1 (Release Assembly - Port Design Challenge Forum to use Dev Forum)
 * <ul>
 *     <li>Added {@link #studioForumRootCategoryId}</li>
 *     <li>Added method {@link #init()} to read studioForumRootCategoryId from configuration</li>
 *     <li>Updated method {@link #createUpdateAssetDTO(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition)} to set the FORUM_TYPE project info</li>
 *     <li>Updated method {@link #createStudioForum(String, long)} to use software forum EJB and call the new method createStudioForumV2</li>
 *     <li>Updated method {@link #createStudioForumWatchAndRole(long, long, boolean)} to use software forum EJB</li>
 *     <li>Updated method {@link #deleteStudioForumWatchAndRole(long, long)} to use software forum EJB instead</li>
 *     <li>Updated method {@link #updateStudioForumName(long, String)} to use software forum EJB instead</li>
 *     <li>Updated method {@link #getNotificationsForUser(com.topcoder.security.TCSubject, long)} to use software forum EJB for studio contests</li>
 *     <li>Updated method {@link #updateNotificationsForUser(com.topcoder.security.TCSubject, long, java.util.List)} to use software forum EJB for studio contests</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 3.2 (TopCoder Direct - Add Appirio Manager)
 * <ul>
 *     <li>Updated {@link #createContestResources(com.topcoder.security.TCSubject, com.topcoder.service.project.SoftwareCompetition, long, boolean)}
 *     to add Appirio Manager as manager resource if it's in the passed-in competition object</li>
 * </ul>
 * </p>
 *
 * Version 3.3 (Provide Way To Pre_register members When Launching Challenge)
 * <ul>
 *     <li> Updated {@link #processContestSaleInternal(TCSubject, SoftwareCompetition, PaymentData, Date, Date)}
 *          pre-register member for private challenge will be added for first time activation
 *     </li>
 *     <li> Added {@link #updatePreRegister(TCSubject, SoftwareCompetition, Set)} method</li>
 * </ul>
 *
 * Version 3.4 (TOPCODER DIRECT - IMPROVEMENT FOR PRE-REGISTER MEMBERS WHEN LAUNCHING CHALLENGES)
 * <ul>
 *     <li> Updated {@link #updatePreRegister(TCSubject, SoftwareCompetition, Set)}</li>
 *     <li> Add helper {@link #isPrivate(TCSubject, long, boolean)}</li>
 * </ul>
 *
 * Version 3.5 (TOPCODER DIRECT - CLOSE PRIVATE CHALLENGE IMMEDIATELY)
 * <ul>
 *     <li>Add {@link #closeSoftwareContest(TCSubject, long, long)}</li>
 *     <li>Add {@link #cancelSoftwareContestByUser(TCSubject, long)}</li>
 * </ul>
 *
 * Version 3.6 (TOPCODER DIRECT - FIXES FOR CLOSE PRIVATE CHALLENGE IMMEDIATELY)
 * <ul>
 *     <li>Refactor {@link #createReviewerResource(long, long, long, boolean, boolean)}</li>
 *     <li>Fix {@link #closeSoftwareContest(TCSubject, long, long)} to work with auto pilot</li>
 * </ul>
 * @author snow01, pulky, murphydog, waits, BeBetter, hohosky, isv, tangzx, GreatKevin, lmmortal, minhu, GreatKevin, tangzx
 * @author isv, GreatKevin, Veve, deedee, TCSCODER, TCSASSEMBLER
 * @version 3.6
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContestServiceFacadeBean implements ContestServiceFacadeLocal, ContestServiceFacadeRemote {
    /**
     * The default configuration namespace.
     */
    private static final String DEFAULT_NAMESAPCE = "com.topcoder.service.facade.contest.ejb.ContestServiceFacadeBean";

    /**
     * Private contest specifying the notification type of Contest Timeline Notification.
     */
    private static final long TIMELINE_NOTIFICATION_TYPE = 1;

    /**
     * Private constant specifying active & public status id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final long CONTEST_SALE_STATUS_PAID = 1;

    /**
     * Private constant specifying active & public status id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final long SALE_TYPE_PAYPAL_PAYFLOW = 1;

    /**
     * Private constant specifying active & public status id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final long SALE_TYPE_TC_PURCHASE_ORDER = 2;

    /**
     * Private constant specifying resource ext ref id
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_EXTERNAL_REFERENCE_ID = "External Reference ID";


    /**
     * Private constant specifying resource handle
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_HANDLE = "Handle";

    /**
     * Private constant specifying resource handle
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_HANDLE_APPLICATIONS = "Applications";

    /**
     * Private constant specifying resource handle
     *
     * @since 1.0.4
     */
    private static final String RESOURCE_INFO_HANDLE_COMPONENTS = "Components";


    /**
     * Private constant specifying resource payment anout.
     * @since 1.8.3
     */
    private static final String RESOURCE_INFO_PAYMENT = "Payment";

    /**
     * Private constant specifying resource pay
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS = "Payment Status";

    /**
     * Private constant specifying registration date
     *
     */
    private static final String RESOURCE_INFO_REGISTRATION_DATE = "Registration Date";

    /**
     * Private constant specifying resource pay
     *
     * @since Flex Cockpit Launch Contest - Integrate Software Contests v1.0
     */
    private static final String RESOURCE_INFO_PAYMENT_STATUS_NA = "N/A";

    /**
     * Private contest specifiy the N/A payment
     * @since 1.8.3
     */
    private static final String RESOURCE_INFO_PAYMENT_NA = "N/A";

    /**
     * Resource info attribute for Manual payment
     * @since 3.6
     */
    private static final String RESOURCE_INFO_MANUAL_PAYMENT = "Manual Payments";

    /**
     * Scorecard ID attibute
     * @since 3.6
     */
    private static final String SCORECARD_ID_ATTRIBUTE = "Scorecard ID";

    /**
     * Email file template source key that is used by email generator.
     */
    private static final String EMAIL_FILE_TEMPLATE_SOURCE_KEY = "fileTemplateSource";


     /**
     * Private constant specifying administrator role.
     */
    private static final String TC_STAFF_ROLE = "TC Staff";

    /**
     * The const string for configuration files.
     * @since 1.2.2
     */
    private static final String CONTEST_ELIGIBILITY_MAPPING_PREFIX = "ContestEligibilityMapping";

    /**
     * The const string for configuration name sapce.
     * @since 1.2.2
     */
    private static final String CONTEST_ELIGIBILITY_MAPPING_NAMESPACE
    = "com.topcoder.service.facade.contest.ejb.ContestServiceFacadeBean";

    /**
     * The const string for configuration EligibilityName key.
     * @since 1.2.2
     */
    private static final String ELIGIBILITY_NAME = "EligibilityName";

    /**
     * The const string for configuration EligibilityGroupId key.
     * @since 1.2.2
     */
    private static final String ELIGIBILITY_ID = "EligibilityGroupId";

     /**
     * The const string for configuration EligibilityAdminRole key.
     * @since 1.2.2
     */
    private static final String ELIGIBILITY_ADMIN_ROLE = "EligibilityAdminRole";


    /**
     * Private constant specifying administrator role.
     */
    private static final String ADMIN_ROLE = "Cockpit Administrator";

    /**
     * Private constant specifying project registration phase name.
     *
     * @since 3.6
     */
    private static final String PROJECT_REGISTRATION_PHASE_NAME = "Registration";

    /**
     * Private constant specifying project submission phase name.
     *
     * @since 1.4
     */
    private static final String PROJECT_SUBMISSION_PHASE_NAME = "Submission";

    /**
     * Private constant specifying project review phase name.
     *
     * @since 3.5
     */
    private static final String PROJECT_REVIEW_PHASE_NAME = "Review";

    /**
     * Private constant specifying project iterative review phase name.
     *
     * @since 3.5
     */
    private static final String PROJECT_ITERATIVE_REVIEW_PHASE_NAME = "Iterative Review";

    /**
     * Private constant specifying project final fix phase name.
     *
     * @since 1.4
     */
    private static final String PROJECT_FINAL_FIX_PHASE_NAME = "Final Fix";


    /**
     * The phase type if for the specification review phase of the contest.
     * @since 1.8.5
     */
    private static final long SPEC_REVIEW_PHASE_TYPE_ID = 14L;

    /**
     * The draft contest status id.
     *
     * @since 1.9
     */
    private static final long CONTEST_DRAFT_STATUS_ID = 2L;

    /**
     * Constant for zero amount.
     *
     * @since 1.6
     */
    private static final Double ZERO_AMOUNT = new Double(0);

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM.dd.yyyy hh:mm a", Locale.US);

    /**
     * active submission status id
     *
     * @since 1.6
     */
    private static final long SUBMISSION_ACTIVE_STATUS_ID = 1;

    /**
     * Represents the active upload status id.
     *
     * @since 1.8.6
     */
    private static final long UPLOAD_ACTIVE_STATUS_ID = 1;

     /**
     * DELETE submission status id
     *
     * @since 1.6
     */
    private static final long SUBMISSION_DELETE_STATUS_ID = 5;

     /**
     * COMPLETED WIHOUT A WIN submission status id
     *
     * @since 1.6
     */
    private static final long SUBMISSION_NO_WIN_STATUS_ID = 4;

    /**
     * Draft status list.
     *
     * @since 1.6
     */
    private final static List<String> DRAFT_STATUS = Arrays.asList("Draft", "Unactive - Not Yet Published","Inactive");

    /**
     * Scheduled status list.
     *
     * @since 1.6
     */
    private final static List<String> SCHEDULED_STATUS = Arrays.asList("Scheduled", "Specification Submission", "Specification Review", "Passed Spec Review");

    /**
     * Active status list.
     *
     * @since 1.6
     */
    private final static List<String> ACTIVE_STATUS = Arrays.asList("Active - Public", "Active", "Registration",
        "Submission", "Screening", "Review", "Appeals", "Appeals Response", "Aggregation", "Aggregation Review",
        "Final Fix", "Final Review", "Approval", "Action Required", "In Danger", "Extended");

    /**
     * Constant for zero amount.
     *
     * @since 1.6
     */
    private final static List<String> FINISHED_STATUS = Arrays.asList("Completed", "No Winner Chosen",
        "Insufficient Submissions - ReRun Possible", "Insufficient Submissions", "Abandoned","Inactive - Removed", "Cancelled - Failed Review",
        "Cancelled - Failed Screening", "Cancelled - Zero Submissions", "Cancelled - Winner Unresponsive", "Cancelled - Zero Registrations" );

    /**
     * Represents the checkpoint prize type id.
     *
     * @since 1.6.8
     */
    private final static long CHECKPOINT_PRIZE_TYPE_ID = 14L;

    /**
     * Cancelled status list.
     *
     * @since 1.6.9
     */
    public final static List<String> CANCELLED_STATUS = Arrays.asList("Cancelled - Client Request",
        "Cancelled - Requirement Infeasible");

    private final static String COPILOT_PERMISSION = "full";

    private final static int GLOBAL_TIMELINE_NOTIFICATION = 29;

    private final static int GLOBAL_FORUM_WATCH = 30;
    
    /**
     * CCA Not set
     * 
     * @since 1.9.6
     */
    private final static String CONFIDENTIALITY_PUBLIC = "public";
    
    /**
     * Standard CCA
     * 
     * @since 1.9.6
     */
    private final static String CONFIDENTIALITY_STANDARD = "standard_cca";

    /**
     * The aws credentials file.
     *
     * @since 1.9.10
     */
    private final static String AWS_CREDENTIALS_FILE = "AwsCredentials.properties";

    /**
     * <p>
     * A <code>ContestEligibilityValidationManager</code> providing access to available
     * <code>Contest Eligibility Validation EJB</code>.
     * </p>
     */
    @EJB(name = "ejb/ContestEligibilityValidation")
    private ContestEligibilityValidationManager contestEligibilityValidationManager = null;

    /**
     * <p>
     * A <code>ContestEligibilityManager</code> providing access to available
     * <code>Contest Eligibility Persistence EJB</code>.
     * </p>
     */
    @EJB(name = "ejb/ContestEligibilityPersistence")
    private ContestEligibilityManager contestEligibilityManager = null;

    /**
     * <p>
     * A <code>CatalogService</code> providing access to available
     * <code>Category Services EJB</code>. This bean is delegated to process the
     * calls to the methods inherited from <code>Category Services</code>
     * component.
     * </p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @EJB(name = "ejb/CatalogService")
    private CatalogService catalogService = null;

    /**
     * <p>
     * A <code>ProjectServices</code> providing access to available
     * <code>Project Services EJB</code>. This bean is delegated to process the
     * calls to the methods inherited from <code>Project Services</code>
     * component.
     * </p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @EJB(name = "ejb/ProjectServicesBean")
    private ProjectServices projectServices = null;

    /**
     * <p>
     * A <code>PermissionService</code> providing access to available
     * <code>Permission Service EJB</code>. This bean is delegated to process
     * the calls for CRUD on permissions.
     * </p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @EJB(name = "ejb/PermissionService")
    private PermissionService permissionService = null;

    /**
     * <p>
     * A <code>UserService</code> providing access to available
     * <code>User Service EJB</code>.
     * </p>
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @EJB(name = "ejb/UserService")
    private UserService userService = null;

    /**
     * <p>A <code>SpecReviewService</code> providing access to available <code>Spec Review Service</code>. This bean is
     * delegated to process the calls for CRUD spec reviews.</p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    @EJB(name = "ejb/SpecReviewService")
    private SpecReviewService specReviewService = null;

    /**
     * <p>A <code>ProjectDAO</code> providing access to available billing project related methods like retrieving
     * contest fee for given billing project.</p>
     *
     * @since 1.0.1
     */
    @EJB(name = "ejb/ProjectDAOBean")
    private ProjectDAO billingProjectDAO = null;

    /**
     * <p>A <code>ProjectService</code> providing access to project service.</p>
     */
    @EJB(name = "ejb/ProjectService")
    private ProjectService projectService = null;

    /**
     * <p>A <code>PermissionServiceFacade</code> providing access to permission related methods.</p>
     * @since 1.8
     */
    @EJB(name = "ejb/PermissionServiceFacadeBean")
    private PermissionServiceFacade permissionServiceFacade = null;

    /**
     * <p>A <code>SpecificationReviewService</code> providing access to contest specification review related methods.</p>
     * @since 1.8.5
     */
    @EJB(name = "ejb/SpecificationReviewServiceBean")
    private SpecificationReviewService specificationReviewService = null;

    /**
     * Global object factory config manager specification namespace.
     *
     * @since BUGR-3738
     */
    @Resource(name = "objectFactoryConfigName")
    private String objectFactoryConfigManagerSpecName;

    /**
     * Object Factory key for upload manager.
     *
     * @since BUGR-3738
     */
    @Resource(name = "uploadManagerOFKey")
    private String uploadManagerOFKey;

    /**
     * Object Factory key for the project manager.
     *
     * @since 1.9.2
     */
    @Resource(name = "projectManagerOFKey")
    private String projectManagerOFKey;

    /**
     * Security groups Hibernate config file.
     *
     * @since 1.8.4
     */
    @Resource(name = "securityGroupsHibernateConfig")
    private String securityGroupsHibernateConfig;

    /**
     * The authorization service.
     *
     * @since 1.8.4
     */
    private AuthorizationService authorizationService;

    /**
     * A flag indicating whether or not create the forum. It's injected, used in
     * the createSoftwareContest method. In the old version, this variable
     * misses the document, it's added in the version 1.1
     */
    private boolean createForum = false;

    /**
     * forumBeanProviderUrl is used in the jndi context to get the forum bean in
     * the createForum method. It's injected, non-null and non-empty after set.
     * In the old version, this variable misses the document, it's added in the
     * version 1.1
     */
    private String softwareForumBeanProviderUrl;

    /**
     * studioForumBeanProviderUrl is used in the jndi context to get the forum bean in
     * the createStudioForum method. It's injected, non-null and non-empty after set.
     *
     * @since 1.6.6
     */
    @Resource(name = "studioForumBeanProviderUrl")
    private String studioForumBeanProviderUrl;


    /**
     * The root forum category id the studio contest forum is created under. It will be initialized in the method init()
     * by reading the configuration file ContestServiceFacade.xml.
     * @since 3.1
     */
    private Long studioForumRootCategoryId;

    /**
     * <p>
     * A <code>PaymentProcessor</code> instance of payment processor
     * implementing class. All payment requests are processed through this
     * instance.
     * </p>
     */
    private PaymentProcessor paymentProcessor = null;

    /**
     * <p>
     * A <code>UploadExternalServices</code> instance of Online Review Upload
     * Services to expose its methods.
     * </p>
     *
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    private UploadExternalServices uploadExternalServices = null;

    /**
     * userBeanProviderUrl is used in the jndi context to get the user bean.
     * It's injected, non-null and non-empty after set.
     */
    private String userBeanProviderUrl;

    /**
     * userBeanProviderUrl is used in the jndi context to get the user bean.
     * It's injected, non-null and non-empty after set.
     */
    @Resource(name = "projectBeanProviderUrl")
    private String projectBeanProviderUrl;

    /**
     * <p>
     * Represents the sessionContext of the ejb.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * Document manager config file location.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "documentManagerConfigFile")
    private String documentManagerConfigFile;

    /**
     * Email template file path for Activate Contest Receipt.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "activateContestReceiptEmailTemplatePath")
    private String activateContestReceiptEmailTemplatePath;

    /**
     * BCC Address for Activate Contest Receipt Email
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "activateContestReceiptEmailBCCAddr")
    private String activateContestReceiptEmailBCCAddr;

    /**
     * From Address for Activate Contest Receipt Email
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "activateContestReceiptEmailFromAddr")
    private String activateContestReceiptEmailFromAddr;

    /**
     * Subject line for Activate Contest Receipt Email
     *
     * @since Cockpit Release Assembly for Receipts
     */
    @Resource(name = "activateContestReceiptEmailSubject")
    private String activateContestReceiptEmailSubject;

    /**
     * Email template file path for copilot winner email.
     * @since 1.8.5
     */
    @Resource(name = "copilotWinnerEmailTemplatePath")
    private String copilotWinnerEmailTemplatePath;

    /**
     * BCC Address for copilot winner email.
     * @since 1.8.5
     */
    @Resource(name = "copilotWinnerEmailBCCAddr")
    private String copilotWinnerEmailBCCAddr;

    /**
     * From Address for copilot winner email.
     *
     * @since 1.8.5
     */
    @Resource(name = "copilotWinnerEmailFromAddr")
    private String copilotWinnerEmailFromAddr;

    /**
     * Subject line for copilot winner email.
     *
     * @since 1.8.5
     */
    @Resource(name = "copilotWinnerEmailSubject")
    private String copilotWinnerEmailSubject;


    /**
     * Contains all the contest type ids need custom contest activation email template, the ids are
     * separated with ';' and configured in ejb-jar.xml.
     *
     * @since 1.8.4
     */
    @Resource(name = "customActivateContestReceiptContestTypeIds")
    private String customActivateContestReceiptContestTypeIds;

    /**
     * Contains all the custom contest activation email template paths, map to the ids configured
     * in <code>customActivateContestReceiptContestTypeIds</code>. The paths are separated with
     * ';' and configured in ejb-jar.xml
     *
     * @since 1.8.4
     */
    @Resource(name = "customActivateContestReceiptEmailTemplatePaths")
    private String customActivateContestReceiptEmailTemplatePaths;

    /**
     * Email template file path for Spec Review Notification Email
     *
     * @since 1.0.2
     */
    @Resource(name = "specReviewNotificationEmailTemplatePath")
    private String specReviewNotificationEmailTemplatePath;

    /**
     * BCC Address for Spec Review Notification Email
     *
     * @since 1.0.2
     */
    @Resource(name = "specReviewNotificationEmailBCCAddr")
    private String specReviewNotificationEmailBCCAddr;

    /**
     * From Address for Spec Review Notification Email
     *
     * @since 1.0.2
     */
    @Resource(name = "specReviewNotificationEmailFromAddr")
    private String specReviewNotificationEmailFromAddr;

    /**
     * Subject line for Spec Review Notification Email
     *
     * @since 1.0.2
     */
    @Resource(name = "specReviewNotificationEmailSubject")
    private String specReviewNotificationEmailSubject;

    /**
     * The default prize for spec reviews
     *
     * @since 1.4
     */
    @Resource(name = "specReviewPrize")
    private Double specReviewPrize;

    /**
     * The mock file path to use for submissions
     *
     * @since 1.4
     */
    private String mockSubmissionFilePath;

    /**
     * The mock file name to use for submissions
     *
     * @since 1.4
     */
    @Resource(name = "mockSubmissionFileName")
    private String mockSubmissionFileName;

    /**
     * Document generator that stores email templates.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private DocumentGenerator documentGenerator;

    /**
     * Email generator that generates email message from given template.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private EmailMessageGenerator emailMessageGenerator;

    /**
     * UploadManager instance which is used to get submission information.
     *
     * @since BUGR-3738
     */
    private UploadManager uploadManager;


    /**
     * Project Manager instance which is used to perform level contest related operations.
     *
     * @since 1.9.2
     */
    private ProjectManager projectManager;


    /**
     * user id for Applications
     *
     * @since 1.0.4
     */
    private long applications_user_id;


     /**
     * user id for Components
     *
     * @since 1.0.4
     */
    private long components_user_id;

    /**
     * The logger instance for logging the information in
     * ContestServiceFacadeBean.
     *
     * @since 1.1
     */
    private Logger logger = Logger.getLogger(this.getClass());

    /**
     * <p>The lookup DAO.</p>
     */
    private LookupDAO lookupDAO;

    /**
     * <p>The copilot project DAO.</p>
     */
    private CopilotProjectDAO copilotProjectDAO;

    private CopilotProfileDAO copilotProfileDAO;


    /**
     * The helper map stores the mapping of contest type id to custom contset activation email template path. It's
     * initialized in the init() method.
     *
     * @since 1.8.4
     */
    private Map<Long, String> customContestActivationEmailTempaltes;

    /**
     * The review auction manager.
     *
     * @since 1.9.5
     */
    private static ReviewAuctionManager reviewAuctionManager = new ReviewAuctionManagerImpl();

    /**
     * The categories which should exclue review auctions.
     *
     * @since 1.9.5
     */
    private static final Set<Long> EXCLUDE_REVIEW_AUCTION_CATEGORIES;

    /**
     * The regular review type.
     *
     * @since 1.9.5
     */
    private static ReviewAuctionType REGULAR_REVIEW_TYPE;

    /**
     * The dev review type.
     *
     * @since 1.9.5
     */
    private static ReviewAuctionType DEV_REVIEW_TYPE;

    /**
     * The spec review type.
     *
     * @since 1.9.5
     */
    private static ReviewAuctionType SPEC_REVIEW_TYPE;
	
	/**
     * The iterative review type.
     *
     */
    private static ReviewAuctionType ITERATIVE_REVIEW_TYPE;
	
	/**
     * The iterative review type.
     *
     */
    private static ReviewAuctionType CODE_REVIEW_TYPE;

    /**
     * The amazon SNS client.
     *
     * @since 1.9.10
     */
    private AmazonSNS amazonSNSClient;

    /**
     * The arn used to publish contest creation event.
     *
     * @since 1.9.10
     */
    private String contestCreationArn;

    /**
     * The subject used to publish contest creation event.
     *
     * @since 1.9.10
     */
    private String contestCreationSubject;

    /**
     * The subject used to publish contest creation event.
     *
     * @since 2.3
     */
    private String contestUpdateSubject;

    /**
     *  The message template used to publish contest creation event.
     *
     *  @since 2.3
     */
    private String contestUpdateMessageTemplate;


    /**
     * The message template used to publish contest creation event.
     *
     * @since 1.9.10
     */
    private String contestCreationMessageTemplate;

    /**
     * Software user forum role prefix
     * @since 3.3
     */
    private static final String SOFTWARE_USER_FORUM_ROLE_PREFIX = "Software_Users_";

    /**
     * Software moderator forum role prefix
     * @since 3.3
     */
    private static final String SOFTWARE_MODERATOR_FORUM_ROLE_PREFIX = "Software_Moderators_";

    /**
     * One minute in millisecond
     */
    private static final long MINUTE_IN_MILIS = 60000;

    /**
     * The init of static fields.
     */
    static {
        EXCLUDE_REVIEW_AUCTION_CATEGORIES = new HashSet<Long>();

        try {
            List<ReviewAuctionType> types = reviewAuctionManager.getAuctionTypes();
            for (ReviewAuctionType type : types) {
                if (type.getId() == 1) {
                    REGULAR_REVIEW_TYPE = type;
                } else if (type.getId() == 2) {
                    DEV_REVIEW_TYPE = type;
                } else if (type.getId() == 3) {
                    SPEC_REVIEW_TYPE = type;
                } else if (type.getId() == 4) {
					ITERATIVE_REVIEW_TYPE =type;
				} else if (type.getId() == 5) {
					CODE_REVIEW_TYPE =type;
				}
            }
        } catch (ReviewAuctionManagerException e) {
            // ignore, it will never happen
        }
    }

    /**
     * <p>
     * Constructs new <code>ContestServiceFacadeBean</code> instance. This
     * implementation instantiates new instance of payment processor. Current
     * implementation just support processing through PayPalCreditCard. When
     * multiple processors are desired the implementation should use factory
     * design pattern to get the right instance of the payment processor.
     * </p>
     *
     * @throws PaymentException
     *             exception when instantiating PaymentProcessor.
     *             PaymentProcessor usually do merchant authentication etc at
     *             initialization time, if this fails it is thrown as exception.
     */
    public ContestServiceFacadeBean() throws PaymentException {
    }

    /**
     * <p>
     * This initializes the API Profile to the <code>CallerServices</code>. The
     * API profile are the merchant's (in this case TopCoder) PayPal API
     * details.
     * </p>
     *
     * <p>
     * TopCoder Service Layer Integration 3 Assembly change: new instance of the
     * DefaultUploadServices for exposing its methods.
     * </p>
     *
     * <p>
     * Updated for Cockpit Release Assembly for Receipts - documentGenerator and
     * emailMessageGenerator instance created.
     * </p>
     *
     * <p>
     * Updated in version 1.8.4:
     * - Add the codes to load custom contest type id - custom contest acativation email template path mapping from
     * the ejb configuration.
     * </p>
     *
     * @throws IllegalStateException
     *             it throws this exception on any issues during caller services
     *             initialization. Issues can be: wrong authentication
     *             information, invalid information etc.
     */
    @PostConstruct
    public void init() {
        if (logger == null) {
            logger = Logger.getLogger(this.getClass());
        }

        logger.debug("Initializing PayflowProPaymentProcessor");

        ConfigManager configManager = ConfigManager.getInstance();

        try {
            Property payflowProPaymentProcessorProp = configManager.getPropertyObject(
                    DEFAULT_NAMESAPCE, "PayflowProPaymentProcessor");
            String payFlowHostAddress = payflowProPaymentProcessorProp.getValue("payFlowHostAddress");
            String payFlowUser = payflowProPaymentProcessorProp.getValue("payFlowUser");
            String payFlowPartner = payflowProPaymentProcessorProp.getValue("payFlowPartner");
            String payFlowVendor = payflowProPaymentProcessorProp.getValue("payFlowVendor");
            String payFlowPassword = payflowProPaymentProcessorProp.getValue("payFlowPassword");
            paymentProcessor = new PayflowProPaymentProcessor(payFlowHostAddress, payFlowUser, payFlowPartner,
                    payFlowVendor, payFlowPassword);
        } catch (ConfigManagerException e) {
            throw new IllegalStateException("Failed to create the PayflowProPaymentProcessor instance.", e);
        }

        try {
            String createForumProp = configManager.getString(DEFAULT_NAMESAPCE, "createForum");

            createForum = Boolean.parseBoolean(createForumProp);

            softwareForumBeanProviderUrl = configManager.getString(DEFAULT_NAMESAPCE, "forumBeanProviderUrl");

            studioForumBeanProviderUrl = configManager.getString(DEFAULT_NAMESAPCE, "studioForumBeanProviderUrl");

            userBeanProviderUrl = configManager.getString(DEFAULT_NAMESAPCE, "userBeanProviderUrl");

            projectBeanProviderUrl = configManager.getString(DEFAULT_NAMESAPCE, "projectBeanProviderUrl");

            mockSubmissionFilePath = configManager.getString(DEFAULT_NAMESAPCE, "mockSubmissionFilePath");

            studioForumRootCategoryId = Long.parseLong(
                    configManager.getString(DEFAULT_NAMESAPCE, "studioForumRootCategoryId"));

        } catch (ConfigManagerException e) {
            throw new IllegalStateException("Unable to read property from config file", e);
        }

        // TopCoder Service Layer Integration 3 Assembly
        try {
            uploadExternalServices = new DefaultUploadExternalServices();
        } catch (ConfigurationException e) {
            throw new IllegalStateException("Failed to create the DefaultUploadExternalServices instance.",
                e);
        }

        try {
            documentGenerator = getDocumentGenerator();
        } catch (ConfigurationException e) {
            throw new IllegalStateException("Failed to create the documentGenerator instance.",
                e);
        }

        // the default email message generator.
        emailMessageGenerator = new DefaultEmailMessageGenerator();


        try
        {
            components_user_id = userService.getUserId(RESOURCE_INFO_HANDLE_COMPONENTS);

            applications_user_id = userService.getUserId(RESOURCE_INFO_HANDLE_APPLICATIONS);
        }
        catch (UserServiceException e) {
            throw new IllegalStateException("Failed to get components/applications user id.", e);
        }


        // BUGR-3738 : initialize an UploadManager instance through Object Factory
        try {
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(this.objectFactoryConfigManagerSpecName));

            this.uploadManager = (UploadManager) objectFactory.createObject(this.uploadManagerOFKey);

        } catch (Exception ex) {
            throw new IllegalStateException("Failed to initialize UploadManager through Object Factory.", ex);
        }

        try {
            // initialize the project manager instance
            ObjectFactory objectFactory = new ObjectFactory(new ConfigManagerSpecificationFactory(this.objectFactoryConfigManagerSpecName));

            this.projectManager = (ProjectManager) objectFactory.createObject(this.projectManagerOFKey);

        } catch (Exception ex) {
            throw new IllegalStateException("Failed to initialize ProjectManager through Object Factory.", ex);
        }

        Configuration configuration = new AnnotationConfiguration().configure("/META-INF/hibernate.cfg.xml");

        LookupDAOImpl ldao = new LookupDAOImpl();
        ldao.setLoggerName("copilotBaseDAO");
        ldao.setSessionFactory(configuration.buildSessionFactory());
        lookupDAO = ldao;

        CopilotProjectDAOImpl c = new CopilotProjectDAOImpl();
        c.setLoggerName("copilotBaseDAO");
        c.setSessionFactory(configuration.buildSessionFactory());
        copilotProjectDAO = c;

        CopilotProfileDAOImpl cp = new CopilotProfileDAOImpl();
        cp.setLoggerName("copilotBaseDAO");
        cp.setSessionFactory(configuration.buildSessionFactory());
        copilotProfileDAO = cp;

        // intialize the custom activate email template path map
        this.customContestActivationEmailTempaltes = new HashMap<Long, String>();

        if(customActivateContestReceiptContestTypeIds != null
                && customActivateContestReceiptContestTypeIds.trim().length() > 0
                && customActivateContestReceiptEmailTemplatePaths != null
                && customActivateContestReceiptEmailTemplatePaths.trim().length() > 0) {
            // build the mapping
            String[] ids = customActivateContestReceiptContestTypeIds.split(";");
            String[] paths = customActivateContestReceiptEmailTemplatePaths.split(";");

            if(ids.length != paths.length) {
                throw new IllegalStateException(
                        "The configuration of customActivateContestReceiptContestTypeIds" +
                        " and customActivateContestReceiptEmailTemplatePaths does not match");
            }

            for(int i = 0; i < ids.length; ++i) {
                customContestActivationEmailTempaltes.put(Long.parseLong(ids[i].trim()), paths[i].trim());
            }

        }

        // create the AuthorizationService
        try {
            Configuration sgConfiguration = new AnnotationConfiguration().configure(securityGroupsHibernateConfig);
            HibernateAuthorizationService hAuthorizationService = new HibernateAuthorizationService();
            hAuthorizationService.setSessionFactory(sgConfiguration.buildSessionFactory());
            hAuthorizationService.setLogger(LogManager.getLog());
            authorizationService = hAuthorizationService;
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to initialize AuthorizationService.", ex);
        }

        // create the AmazonSNS client
        try {
            Property contestSNSProp = configManager.getPropertyObject(
                    DEFAULT_NAMESAPCE, "contestCreationSNS");
            contestCreationArn = contestSNSProp.getValue("contestCreationArn");
            contestCreationSubject = contestSNSProp.getValue("contestCreationSubject");
            contestUpdateSubject = contestSNSProp.getValue("contestUpdateSubject");
            String contestCreationMessageTemplatePath = contestSNSProp.getValue("contestCreationMessageTemplatePath");
            String contestUpdateMessageTemplatePath = contestSNSProp.getValue("contestUpdateMessageTemplatePath");


            ClassLoader loader = this.getClass().getClassLoader();
            contestCreationMessageTemplate = FileUtils.readFileToString(
                    new File(loader.getResource(contestCreationMessageTemplatePath).getFile()));
            contestUpdateMessageTemplate = FileUtils.readFileToString(
                    new File(loader.getResource(contestUpdateMessageTemplatePath).getFile()));

            URL credentialURL = loader.getResource(AWS_CREDENTIALS_FILE);
            amazonSNSClient = new AmazonSNSClient(new PropertiesCredentials(new File(credentialURL.getFile())));

        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize AmazonSNS.", e);
        }
    }

    /**
     * Creates new instance of DocumentGenerator
     *
     * @return the new instance of DocumentGenerator
     * @throws ConfigurationException
     *             if any error during instance creation.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private DocumentGenerator getDocumentGenerator()
        throws ConfigurationException {
        try {
            ConfigurationFileManager cfManager = new ConfigurationFileManager(documentManagerConfigFile);

            String docGenNamespace = DocumentGenerator.class.getPackage()
                                                            .getName();
            ConfigurationObject confObj = cfManager.getConfiguration(docGenNamespace)
                                                   .getChild(docGenNamespace);

            return DocumentGeneratorFactory.getDocumentGenerator(confObj);
        } catch (Exception e) {
            throw new ConfigurationException("Error in creating document generator instance", e);
        }
    }

    /**
     * <p>
     * Checks if the login user is of given role
     * </p>
     *
     * @param tcSubject TCSubject instance for login user
     * @return true if it is given role
     */
    private static boolean isRole(TCSubject tcSubject, String roleName) {
        Set<RolePrincipal> roles = tcSubject.getPrincipals();
        if (roles != null) {
            for (RolePrincipal role : roles) {
                if (role.getName().equalsIgnoreCase(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Get the user-name for current login user represented by tcSubject.
     * </p>
     * @param tcSubject TCSubject instance for login user
     * @return user name
     * @throws ContestServiceException fail to retrieve user-name
     */
    private String getUserName(TCSubject tcSubject) throws ContestServiceException {
        try {
            return this.userService.getUserHandle(tcSubject.getUserId());
        } catch(Exception e) {
            throw new ContestServiceException("Fail to get the user-name by user-id" + tcSubject.getUserId(), e);
        }
    }

    /**
     * <p>
     * Converts specified <code>XMLGregorianCalendar</code> instance into
     * <code>Date</code> instance.
     * </p>
     *
     * @param calendar
     *            an <code>XMLGregorianCalendar</code> representing the date to
     *            be converted.
     * @return a <code>Date</code> providing the converted value of specified
     *         calendar or <code>null</code> if specified <code>calendar</code>
     *         is <code>null</code>.
     */
    private Date getDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }

        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * <p>
     * Converts specified <code>Date</code> instance into
     * <code>XMLGregorianCalendar</code> instance.
     * </p>
     *
     * @param date
     *            a <code>Date</code> representing the date to be converted.
     * @return a <code>XMLGregorianCalendar</code> providing the converted value
     *         of specified date or <code>null</code> if specified
     *         <code>date</code> is <code>null</code> or if it can't be
     *         converted to calendar.
     */
    private XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);

        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (credit card/po details) that need to be processed.
     * @return a <code>SoftwareContestPaymentResult</code> result of the payment processing.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    public SoftwareContestPaymentResult processContestCreditCardSale(TCSubject tcSubject,
            SoftwareCompetition competition, CreditCardPaymentData paymentData) throws ContestServiceException, PermissionServiceException {
        logger.debug("processContestCreditCardSale");

        return processContestSaleInternal(tcSubject, competition, paymentData, null, null);
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (credit card/po details) that need to be processed.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase. Can be null if to use default.
     * @return a <code>SoftwareContestPaymentResult</code> result of the payment processing.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since Module Contest Service Software Contest Sales Assembly
     * @since 1.6.6
     */
    public SoftwareContestPaymentResult processContestCreditCardSale(TCSubject tcSubject,
            SoftwareCompetition competition, CreditCardPaymentData paymentData, Date multiRoundEndDate, Date endDate) throws ContestServiceException, PermissionServiceException {
        logger.debug("processContestCreditCardSale");

        return processContestSaleInternal(tcSubject, competition, paymentData, multiRoundEndDate, endDate);
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (credit card/po details) that need to be processed.
     * @return a <code>SoftwareContestPaymentResult</code> result of the payment processing.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(TCSubject tcSubject,
            SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData) throws ContestServiceException, PermissionServiceException {
        logger.debug("processPurchaseOrderSale");

        return processContestSaleInternal(tcSubject, competition, paymentData, null, null);
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (credit card/po details) that need to be processed.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase. Can be null if to use default.
     * @return a <code>SoftwareContestPaymentResult</code> result of the payment processing.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since Module Contest Service Software Contest Sales Assembly
     * @since 1.6.6
     */
    public SoftwareContestPaymentResult processContestPurchaseOrderSale(TCSubject tcSubject,
            SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData, Date multiRoundEndDate, Date endDate) throws ContestServiceException, PermissionServiceException {
        logger.debug("processPurchaseOrderSale");

        return processContestSaleInternal(tcSubject, competition, paymentData, multiRoundEndDate, endDate);
    }


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
     * @since 1.8.5
     */
    public SoftwareContestPaymentResult purchaseActivateContestAndStartSpecReview(TCSubject tcSubject,
            SoftwareCompetition competition, TCPurhcaseOrderPaymentData paymentData,
            Date multiRoundEndDate, Date endDate, boolean startSpecReviewNow) throws ContestServiceException,
            PermissionServiceException, SpecificationReviewServiceException {

        // purchase the contest and activate it
        final SoftwareContestPaymentResult softwareContestPaymentResult =
                processContestSaleInternal(tcSubject, competition, paymentData, multiRoundEndDate, endDate);

        // check if the contest has specification review phase
        final Set<com.topcoder.project.phases.Phase> allPhases =
                softwareContestPaymentResult.getSoftwareCompetition().getProjectPhases().getPhases();

        boolean hasSpecReview = false;

        for (com.topcoder.project.phases.Phase p : allPhases) {
            // contest has specification review phase
            if (p.getPhaseType().getId() == SPEC_REVIEW_PHASE_TYPE_ID) {
                hasSpecReview = true;
                break;
            }
        }

        if (hasSpecReview) {
            // only schedule the spec review if the contest has spec submission / spec review
            specificationReviewService.scheduleSpecificationReview(tcSubject,
                    softwareContestPaymentResult.getSoftwareCompetition().getId(), startSpecReviewNow);
        }

        return softwareContestPaymentResult;
    }

    /**
     * <p>
     * Processes the contest sale.
     * </p>
     * <p>
     * Updated for Cockpit Release Assembly for Receipts - Added code snippet to send email receipts on successful
     * purchase.
     * </p>
     * <p>
     * Updated for Version 1.0.3 - For software contest, payment is made for the sum of various costs. - While doing so,
     * only the increased amount is paid (if earlier payments were made).
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     *
     * <p>
     * Updates in 1.8.4:
     * - Check the DR flag before adding DR points to the total cost
     * - Fix the total member cost and total cost, they should be different. Total cost = total member cost + admin fee
     * - Add the logics to dynamic load activation email tempalte according to the contest type
     * </p>
     *
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param competition data that recognizes a contest.
     * @param paymentData payment information (credit card/po details) that need to be processed.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase. Can be null if to use default.
     * @return a <code>SoftwareContestPaymentResult</code> result of the payment processing.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since Module Contest Service Software Contest Sales Assembly
     * @since BUGR-1682 changed return value
     */
    private SoftwareContestPaymentResult processContestSaleInternal(TCSubject tcSubject,
            SoftwareCompetition competition, PaymentData paymentData, Date multiRoundEndDate, Date endDate) throws ContestServiceException, PermissionServiceException {
        logger.info("SoftwareCompetition: " + competition);
        logger.info("PaymentData: " + paymentData);
        logger.info("tcSubject: " + tcSubject.getUserId());
        logger.info("multiRoundEndDate: " + multiRoundEndDate);

        SoftwareContestPaymentResult softwareContestPaymentResult = null;

        PaymentResult result = null;

        try {
            long contestId = competition.getProjectHeader().getId();
            double pastPayment=0;
            boolean hasContestSaleData = false;
            long contestSaleId = 0L;

            SoftwareCompetition tobeUpdatedCompetition = null;

            if (contestId > 0) { // BUGR-1682
                tobeUpdatedCompetition = this.getSoftwareContestByProjectId(tcSubject, contestId); // BUGR-1682

                // calculate the past payment to calculate the differential cost.
                List<ContestSaleData> sales = tobeUpdatedCompetition.getProjectData().getContestSales();
                if (sales != null) {
                    for (ContestSaleData sale : sales) {
                        pastPayment += sale.getPrice();
                    }
                    if (sales.size() > 0) {
                        hasContestSaleData = true;
                        contestSaleId = sales.get(0).getContestSaleId();
                    }
                }
            }

            if (tobeUpdatedCompetition == null) {
                tobeUpdatedCompetition =
                    createSoftwareContest(tcSubject, competition, competition.getProjectHeader().getTcDirectProjectId(), multiRoundEndDate, endDate);
                competition.getProjectHeader().setProjectStatus(ProjectStatus.ACTIVE);
            } else {
                competition.setProjectHeaderReason("User Update");
                competition.getProjectHeader().setProjectStatus(ProjectStatus.ACTIVE);
                tobeUpdatedCompetition =
                    updateSoftwareContest(tcSubject, competition, competition.getProjectHeader().getTcDirectProjectId(), multiRoundEndDate, endDate);
            }

            Project contest = tobeUpdatedCompetition.getProjectHeader();

            // set status to active
            //contest.setProjectStatus(ProjectStatus.ACTIVE);
            // if contest does not have spec review, turn on AP here
            if (!hasSpecReview(competition))
            {
                contest.setProperty(ProjectPropertyType.AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY, "On");
            }

            projectServices.updateProject(contest, "Set to Active", Long.toString(tcSubject.getUserId()));

            double totalFee = 0;
            if (competition.getProjectHeader().getProjectCategory().getProjectType().getId() != ProjectType.STUDIO.getId()) {
                // software competition
				if (contest.getProperty(ProjectPropertyType.ADMIN_FEE_PROJECT_PROPERTY_KEY) != null
				    && !((String)contest.getProperty(ProjectPropertyType.ADMIN_FEE_PROJECT_PROPERTY_KEY)).equals(""))
				{
					totalFee += Double.parseDouble((String) contest.getProperty(ProjectPropertyType.ADMIN_FEE_PROJECT_PROPERTY_KEY));
				}

				if (contest.getProperty(ProjectPropertyType.FIRST_PLACE_COST_PROJECT_PROPERTY_KEY) != null
				    && !((String)contest.getProperty(ProjectPropertyType.FIRST_PLACE_COST_PROJECT_PROPERTY_KEY)).equals(""))
				{
					totalFee += Double.parseDouble((String) contest.getProperty(ProjectPropertyType.FIRST_PLACE_COST_PROJECT_PROPERTY_KEY));
				}

				if (contest.getProperty(ProjectPropertyType.SECOND_PLACE_COST_PROJECT_PROPERTY_KEY) != null
				    && !((String)contest.getProperty(ProjectPropertyType.SECOND_PLACE_COST_PROJECT_PROPERTY_KEY)).equals(""))
				{
					totalFee += Double.parseDouble((String) contest.getProperty(ProjectPropertyType.SECOND_PLACE_COST_PROJECT_PROPERTY_KEY));
				}

				if (contest.getProperty(ProjectPropertyType.CHECKPOINT_BONUS_COST_PROJECT_PROPERTY_KEY) != null
				    && !((String)contest.getProperty(ProjectPropertyType.CHECKPOINT_BONUS_COST_PROJECT_PROPERTY_KEY)).equals(""))
				{
					totalFee += Double.parseDouble((String) contest.getProperty(ProjectPropertyType.CHECKPOINT_BONUS_COST_PROJECT_PROPERTY_KEY));
				}

				if (contest.getProperty(ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY) != null
				    && !((String)contest.getProperty(ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY)).equals(""))
				{
					totalFee += Double.parseDouble((String) contest.getProperty(ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY));
				}

				if (contest.getProperty(ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY) != null
				    && !((String)contest.getProperty(ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY)).equals(""))
				{
					totalFee += Double.parseDouble((String) contest.getProperty(ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY));
				}

				if (contest.getProperty(ProjectPropertyType.RELIABILITY_BONUS_COST_PROJECT_PROPERTY_KEY) != null
				    && !((String)contest.getProperty(ProjectPropertyType.RELIABILITY_BONUS_COST_PROJECT_PROPERTY_KEY)).equals(""))
				{
					totalFee += Double.parseDouble((String) contest.getProperty(ProjectPropertyType.RELIABILITY_BONUS_COST_PROJECT_PROPERTY_KEY));
				}

                // checkpoint prizes
                if (competition.getProjectHeader().getPrizes() != null && competition.getProjectHeader().getPrizes().size() > 0) {
                    for (Prize prize : competition.getProjectHeader().getPrizes()) {
                        if (prize.getPrizeType().getId() == CHECKPOINT_PRIZE_TYPE_ID) {
                            totalFee += prize.getPrizeAmount() * prize.getNumberOfSubmissions();
                        }
                    }
                }
            } else {
                // studio competition

				if (contest.getProperty(ProjectPropertyType.ADMIN_FEE_PROJECT_PROPERTY_KEY) != null
				    && !((String)contest.getProperty(ProjectPropertyType.ADMIN_FEE_PROJECT_PROPERTY_KEY)).equals(""))
				{
					totalFee += Double.parseDouble((String) contest.getProperty(ProjectPropertyType.ADMIN_FEE_PROJECT_PROPERTY_KEY));
				}

				if (contest.getProperty(ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY) != null
				    && !((String)contest.getProperty(ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY)).equals(""))
				{
					totalFee += Double.parseDouble((String) contest.getProperty(ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY));
				}

				if (contest.getProperty(ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY) != null
				    && !((String)contest.getProperty(ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY)).equals(""))
				{
					totalFee += Double.parseDouble((String) contest.getProperty(ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY));
				}

                for (Prize prize : competition.getProjectHeader().getPrizes()) {
                    totalFee = totalFee + prize.getPrizeAmount() * prize.getNumberOfSubmissions();
                }
            }

            // add copilot payment if exists
            String copilotPayment = contest.getProperty(ProjectPropertyType.COPILOT_COST_PROJECT_PROPERTY_KEY);

            if (copilotPayment != null && copilotPayment.trim().length() != 0) {
                totalFee += Double.parseDouble(copilotPayment);
            }

            // calculate the DR points
            String drPayment = contest.getProperty(ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY);

            String drFlag = contest.getProperty(ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY);

            if (drPayment != null && drPayment.trim().length() != 0 && drFlag.toLowerCase().equals("on")) {
                // only add DR to total fee when the DR flag is on
                totalFee += Double.parseDouble(drPayment);
            }

            double totalCost = totalFee;
            double totalMemberCost = totalFee;

            totalMemberCost = totalMemberCost - Double.parseDouble((String) contest.getProperty(ProjectPropertyType.ADMIN_FEE_PROJECT_PROPERTY_KEY));

            if (paymentData instanceof TCPurhcaseOrderPaymentData) {

                checkBillingProjectPermission(tcSubject, tobeUpdatedCompetition);

                String billingProject = tobeUpdatedCompetition.getProjectHeader().getProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY);

                if (billingProject == null || billingProject.equals("") || billingProject.equals("0"))
                {
                    throw new ContestServiceException("Billing/PO Number is null/empty.");
                }

                String poNumber = billingProjectDAO.retrieveById(new Long(billingProject), false).getPOBoxNumber();

                // processing purchase order is not in scope of this assembly.
                result = new PaymentResult();
                result.setReferenceNumber(poNumber);
            } else if (paymentData instanceof CreditCardPaymentData) {
                // ideally client should be sending the amount,
                // but as client has some inconsistency
                // so in this case we would use the amount from contest data.
                CreditCardPaymentData creditCardPaymentData = (CreditCardPaymentData) paymentData;

                creditCardPaymentData.setAmount(String.valueOf(totalMemberCost));
                creditCardPaymentData.setComment1("Contest Fee");
                creditCardPaymentData.setComment2(String.valueOf(
                        contest.getId()));
                result = paymentProcessor.process(paymentData);
            }

            // TODO, to be fixed later
            // tobeUpdatedCompetition.getContestData().setStatusId(
            // CONTEST_STATUS_ACTIVE_PUBLIC);
            // tobeUpdatedCompetition.getContestData().setDetailedStatusId(
            // CONTEST_DETAILED_STATUS_SCHEDULED);
            ContestSaleData contestSaleData = new ContestSaleData();
            contestSaleData.setPaypalOrderId(result.getReferenceNumber());
            contestSaleData.setSaleReferenceId(result.getReferenceNumber());

            if (paymentData instanceof TCPurhcaseOrderPaymentData) {
                contestSaleData.setSaleTypeId(SALE_TYPE_TC_PURCHASE_ORDER);
            }
            // TODO, how relate to payflow
            else if (paymentData instanceof CreditCardPaymentData) {
                contestSaleData.setSaleTypeId(SALE_TYPE_PAYPAL_PAYFLOW);
            }

            contestSaleData.setContestId(contest.getId());
            contestSaleData.setSaleStatusId(CONTEST_SALE_STATUS_PAID);
            contestSaleData.setPrice(totalCost);

            if (!hasContestSaleData) {
                this.projectServices.createContestSale(contestSaleData);
            } else {
                contestSaleData.setContestSaleId(contestSaleId);
                this.projectServices.updateContestSale(contestSaleData);
            }
            // DONOT create for now
            // create forum for the contest.
            // long forumid =
            // this.studioService.createForum(tobeUpdatedCompetition
            // .getContestData().getName(), p.getUserId());
            // tobeUpdatedCompetition.getContestData().setForumId(forumid);

            // update contest
            // COMMENT OUT FOR NOW, nothing to update
            // tobeUpdatedCompetition.setProjectHeaderReason(
            // "Updated for Contest Sale");
            // tobeUpdatedCompetition =
            // this.updateSoftwareContest(tobeUpdatedCompetition,
            // contest.getTcDirectProjectId());

            // return result;
            // BUGR-1682
            softwareContestPaymentResult = new SoftwareContestPaymentResult();
            softwareContestPaymentResult.setPaymentResult(result);
            /*
             * for(com.topcoder.project.phases.Phase p :
             * tobeUpdatedCompetition.getProjectPhases().getAllPhases()) {
             * p.setProject(null); }
             */
            softwareContestPaymentResult.setSoftwareCompetition(tobeUpdatedCompetition);

            //
            // Added for Cockpit Release Assembly for Receipts
            //
            String competitionType = tobeUpdatedCompetition.getProjectHeader()
                                                           .getProjectCategory()
                                                           .getName();
            long competitionTypeId = tobeUpdatedCompetition.getProjectHeader().getProjectCategory().getId();

            String projectName = competition.getProjectHeader().getTcDirectProjectName();

            String toAddr = "";

            String purchasedByUser = getUserName(tcSubject);

            if (paymentData instanceof TCPurhcaseOrderPaymentData) {
                String currentUserEmailAddress = this.userService.getEmailAddress(tcSubject.getUserId());
                toAddr = currentUserEmailAddress;
            } else if (paymentData instanceof CreditCardPaymentData) {
                CreditCardPaymentData cc = (CreditCardPaymentData) paymentData;
                toAddr = cc.getEmail();
            }

            boolean isDevContest = competition.getProjectHeader().getProjectCategory().getId() == ProjectCategory.DEVELOPMENT.getId();

            boolean hasEligibility = contestEligibilityManager.haveEligibility(
                    new Long[] { tobeUpdatedCompetition.getProjectHeader().getId() }, false).size() > 0;

            // if creating contest, eligiblity is not commited, so above will not get back result
            if (getBillingProjectId(tobeUpdatedCompetition) != 0
                    && competition.getProjectHeader().getSecurityGroupId() > 0) {
                hasEligibility = true;
            }

            boolean isStudioContest = isStudio(tobeUpdatedCompetition);
            boolean isCopilotContest = isCopilotContest(tobeUpdatedCompetition);
            String contestIndicator;
            if (isStudioContest || isCopilotContest) {
                contestIndicator = "SUBJECTIVE";
            } else {
                contestIndicator = "OBJECTIVE";
            }

            Project toBeUpdatedContest = projectManager.getProject(contest.getId());
            toBeUpdatedContest.setProperty(
                    ProjectPropertyType.HISTORICAL_PROJECTED_COST_PROPERTY_KEY, Double.toString(totalCost));
            toBeUpdatedContest.setProperty(
                    ProjectPropertyType.PROJECT_ACTIVATE_DATE_PROPERTY_KEY, DATE_FORMAT.format(new Date()));
            projectManager.updateProject(toBeUpdatedContest,
                    "Set 'Historical Projected Cost' and 'Project Activate Date'",
                    String.valueOf(tcSubject.getUserId()));

            sendActivateContestReceiptEmail(toAddr, purchasedByUser,
                paymentData, competitionType, competitionTypeId,
                tobeUpdatedCompetition.getProjectHeader()
                                      .getProperty(ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY),
                projectName,
                competition.getAssetDTO().getProductionDate()
                           .toGregorianCalendar().getTime(), totalMemberCost, totalCost,
                result.getReferenceNumber(), hasContestSaleData, contestIndicator, isStudio(competition), competition.getProjectHeader().getId());

            // publish event to AWS SNS if needed
            try {
                String cmcTaskId = competition.getProjectHeader().getProperty(
                        ProjectPropertyType.CLOUDSPOKES_CMC_TASK_PROPERTY_KEY);
                String apiURL = "";

                if(isStudio(competition)) {
                    apiURL = "http://api.topcoder.com/v2/design/challenges/" + String.valueOf(competition.getProjectHeader().getId());
                } else {
                    apiURL = "http://api.topcoder.com/v2/develop/challenges/" + String.valueOf(competition.getProjectHeader().getId());
                }

                if (StringUtils.isNotEmpty(cmcTaskId)) {
                    String contestCreationMessage = contestCreationMessageTemplate.
                            replace("%CHALLENGE_ID%", String.valueOf(competition.getProjectHeader().getId())).
                            replace("%CMC_TASK_ID%", cmcTaskId).
                            replace("%API_URL%", apiURL);

                    String subject = contestCreationSubject.replace("%CMC_TASK_ID%", cmcTaskId);

                    PublishRequest request = new PublishRequest().
                            withSubject(subject).
                            withMessage(contestCreationMessage).
                            withTopicArn(contestCreationArn);

                    PublishResult snsResult = amazonSNSClient.publish(request);
                    logger.debug("Publish amazon SNS successfully, the published message id is "
                            + snsResult.getMessageId());
                }
            } catch (Exception e) {
                // we will only log publish error here, won't rollback contest creation.
                logger.error("Operation failed in publish amazon SNS.", e);
            }

            return softwareContestPaymentResult;
        } catch (ContestServiceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        } catch (EmailMessageGenerationException e) {
            logger.error("Error duing email message generation", e);
        } catch (EmailSendingException e) {
            logger.error("Error duing email sending", e);
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            logger.error("Error processContestSaleInternal " + e, e);
            throw new ContestServiceException(e.getMessage(), e);
        }

        return softwareContestPaymentResult;
    }

    /**
     * <p>
     * Returns a list containing all active <code>Categories</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Category> getActiveCategories(TCSubject tcSubject) throws ContestServiceException {
        logger.debug("getActiveCategories");

        try {
            return catalogService.getActiveCategories();
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Operation failed in the catalogService.", e);
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        }
    }

    /**
     * <p>
     * Returns a list containing all active <code>Technologies</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Technology> getActiveTechnologies(TCSubject tcSubject) throws ContestServiceException {
        logger.debug("getActiveTechnologies");

        try {
            return catalogService.getActiveTechnologies();
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Operation failed in the catalogService", e);
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        }
    }

    /**
     * <p>
     * Returns a list containing all <code>Phases</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return a list containing all active <code>Categories</code>. It can be empty if no objects found.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public List<Phase> getPhases(TCSubject tcSubject) throws ContestServiceException {
        logger.debug("getPhase");

        try {
            return catalogService.getPhases();
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Operation failed in the catalogService", e);
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        }
    }

    /**
     * <p>
     * Assigns a specified user to a specified <code>assetDTO</code>.
     * </p>
     * <p>
     * If the user already assigned to the asset, this method simply does nothing.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param userId the id of the user
     * @param assetId the id of the assetDTO
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void assignUserToAsset(TCSubject tcSubject, long userId, long assetId) throws ContestServiceException {
        logger.debug("assignUserToAsset");

        try {
            catalogService.assignUserToAsset(userId, assetId);
            logger.debug("Exit assignUserToAsset");
        } catch (EntityNotFoundException e) {
            logger.error("Operation failed in the catalogService.", e);
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Operation failed in the catalogService.", e);
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        }
    }

    /**
     * <p>
     * Removes a specified user from a specified <code>assetDTO</code>.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param userId the id of the user
     * @param assetId the id of the asset
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void removeUserFromAsset(TCSubject tcSubject, long userId, long assetId) throws ContestServiceException {
        logger.debug("removeUserFromAsset");

        try {
            catalogService.removeUserFromAsset(userId, assetId);
            logger.debug("Exit removeUserFromAsset");
        } catch (EntityNotFoundException e) {
            logger.error("Operation failed in the catalogService.", e);
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Operation failed in the catalogService.", e);
            throw new ContestServiceException("Operation failed in the catalogService.", e);
        }
    }

    /**
     * <p>
     * This method finds all tc direct projects. Returns empty array if no projects found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return Project array with project info, or empty array if none found
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjects(TCSubject tcSubject) throws ContestServiceException {
        logger.debug("findAllTcDirectProjects");

        try {
            Project[] projects = projectServices.findAllTcDirectProjects(tcSubject);

            SoftwareCompetition[] ret = new SoftwareCompetition[projects.length];

            for (int i = 0; i < projects.length; i++) {
                FullProjectData projectData = new FullProjectData();
                projectData.setProjectHeader(projects[i]);

                ret[i] = new SoftwareCompetition();
                ret[i].setProjectData(projectData);
                ret[i].setType(CompetionType.SOFTWARE);
                ret[i].setId(projectData.getProjectHeader().getId());
            }

            logger.debug("Exit findAllTcDirectProjects");

            return ret;
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in the projectServices.", e);
            throw new ContestServiceException("Operation failed in the projectServices.", e);
        }
    }

    /**
     * <p>
     * This method finds all given user tc direct projects . Returns empty array if no projects found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param operator The user to search for projects
     * @return Project array with project info, or empty array if none found
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition[] findAllTcDirectProjectsForUser(TCSubject tcSubject, String operator)
            throws ContestServiceException {
        logger.debug("findAllTcDirectProjectsForUser");

        try {
            Project[] projects = projectServices.findAllTcDirectProjectsForUser(tcSubject,operator);

            SoftwareCompetition[] ret = new SoftwareCompetition[projects.length];

            for (int i = 0; i < projects.length; i++) {
                FullProjectData projectData = new FullProjectData();
                projectData.setProjectHeader(projects[i]);

                ret[i] = new SoftwareCompetition();
                ret[i].setProjectData(projectData);
                ret[i].setType(CompetionType.SOFTWARE);
                ret[i].setId(projectData.getProjectHeader().getId());
            }

            logger.debug("Exit findAllTcDirectProjectsForUser");

            return ret;
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in the projectServices.", e);
            throw new ContestServiceException("Operation failed in the projectServices.", e);
        }
    }

    /**
     * <p>
     * This method retrieves the project along with all known associated information. Returns null if not found.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId The ID of the project to retrieve
     * @return the project along with all known associated information
     * @throws IllegalArgumentException If projectId is negative
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition getFullProjectData(TCSubject tcSubject, long projectId) throws ContestServiceException, PermissionServiceException {
        logger.debug("getFullProjectData");

        try {
            FullProjectData projectData = projectServices.getFullProjectData(projectId);

            if (projectData == null) {
                return null;
            }

            com.topcoder.project.phases.Phase[] allPhases = projectData.getAllPhases();

            for (int i = 0; i < allPhases.length; i++) {
                allPhases[i].setProject(null);
            }

            SoftwareCompetition contest = new SoftwareCompetition();
            contest.setProjectData(projectData);
            contest.setType(CompetionType.SOFTWARE);
            contest.setId(projectData.getProjectHeader().getId());
            logger.debug("Exit getFullProjectData");

            return contest;
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in the projectServices.", e);
            throw new ContestServiceException("Operation failed in the projectServices.", e);
        }
    }

    /**
     * Checks the permission for the given tc-direct-project-id for the current caller.
     *
     * @param tcSubject the current caller
     * @param tcDirectProjectId the project id
     * @param readOnly true to check READ permission, false to check WRITE permission
     * @throws PermissionServiceException if user(not admin) does not have the permission
     */
    private void checkSoftwareProjectPermission(TCSubject tcSubject, long tcDirectProjectId, boolean readOnly)
            throws PermissionServiceException {
        if (!isRole(tcSubject, ADMIN_ROLE) && !isRole(tcSubject, TC_STAFF_ROLE)) {
            if (!projectServices.checkProjectPermission(tcDirectProjectId, readOnly, tcSubject.getUserId())) {
                // check group permission
                checkGroupPermissionForProject(tcSubject, tcDirectProjectId, readOnly);
            }
        }
    }

    /**
     * Checks the permission for the given contestId for the current caller.
     *
     * @param tcSubject the current caller
     * @param contestId the contest id
     * @param readOnly true to check READ permission, false to check WRITE permission
     * @throws PermissionServiceException if user(not admin) does not have the permission
     */
    private void checkSoftwareContestPermission(TCSubject tcSubject, long contestId, boolean readOnly)
            throws PermissionServiceException {
        if (!isRole(tcSubject, ADMIN_ROLE) && !isRole(tcSubject, TC_STAFF_ROLE)) {
            if (!projectServices.checkContestPermission(contestId, readOnly, tcSubject.getUserId())) {
                // check group permission for project
                checkGroupPermissionForProject(tcSubject, projectServices.getTcDirectProject(contestId), readOnly);
            }
        }
    }

    /**
     * Checks the group permission for the given tc-direct-project-id for the current caller.
     *
     * @param tcSubject the current caller
     * @param tcDirectProjectId the project id
     * @param readOnly true to check READ permission, false to check WRITE permission
     * @throws PermissionServiceException if user(not admin) does not have the group permission
     * @since 1.8.4
     */
    private void checkGroupPermissionForProject(TCSubject tcSubject, long tcDirectProjectId, boolean readOnly)
        throws PermissionServiceException {
        try {
            // Check if user is administrator for client account
            Long clientId = null;

            // get client id
            DataAccess dataAccess = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
            Request request = new Request();
            request.setContentHandle("non_admin_client_billing_accounts");
            request.setProperty("tdpis", String.valueOf(tcDirectProjectId));
            ResultSetContainer resultContainer = dataAccess.getData(request).
                get("non_admin_client_billing_accounts");
            if (resultContainer != null && resultContainer.size() > 0) {
                clientId = resultContainer.getLongItem(0, "client_id");
            }
            long userId = tcSubject.getUserId();
            boolean isCustomerAdministrator = false;
            if (clientId != null) {
                isCustomerAdministrator = authorizationService.isCustomerAdministrator(userId, clientId);
            }
            if (isCustomerAdministrator) {
                return;
            } else {
                // If not then check if user is granted desired permission to access the project based on
                // security groups which user is member of
                GroupPermissionType groupPermissionType =
                    authorizationService.checkAuthorization(userId, tcDirectProjectId, ResourceType.PROJECT);
                if (groupPermissionType != null) {
                    if (readOnly && groupPermissionType != GroupPermissionType.REPORT) {
                        return;
                    }
                    if (!readOnly && (groupPermissionType == GroupPermissionType.WRITE
                            || groupPermissionType == GroupPermissionType.FULL)) {
                        return;
                    }
                }
            }
        } catch (Exception e) {
            throw new PermissionServiceException("Failed to authorize user against security groups", e);
        }

        throw new PermissionServiceException("No " + (readOnly ? "read" : "write") + " permission on project");
    }

    /**
     * Checks the billing project permission of the given contest for the current caller.
     *
     * @param contest the contest to check
     * @return billing project id, if it is -1, then no billing project
     * @throws ContestServiceException if user(not admin) does not have the permission
     */
    private void checkBillingProjectPermission(TCSubject tcSubject, SoftwareCompetition contest)
            throws PermissionServiceException, DAOException, ContestServiceException {
        if (!isRole(tcSubject, ADMIN_ROLE) && !isRole(tcSubject, TC_STAFF_ROLE)) {
            String billingProject = contest.getProjectHeader().getProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY);

            Project cur = projectServices.getProject(contest.getProjectHeader().getId());
            String curBilling = cur.getProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY);

            if (billingProject != null  && !billingProject.equals("") && !billingProject.equals("0")) {

                // if billing not changed, no need to check
                if (curBilling != null && !billingProject.equals("") && !billingProject.equals("0"))
                {
                    if (billingProject.equals(billingProject))
                    {
                        return;
                    }
                }
                long clientProjectId = Long.parseLong(billingProject);
                if (!billingProjectDAO.checkClientProjectPermission(getUserName(tcSubject), clientProjectId)) {
                    throw new PermissionServiceException("No permission on billing project " + clientProjectId);
                }
            }
        }
    }

     /**
     * get billing project id
     *
     * @param contest the contest to check
     * @return billing project id, if it is 0, then no billing project
     */
    private long getBillingProjectId(SoftwareCompetition contest)  {

        String billingProject = contest.getProjectHeader().getProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY);

        if (billingProject != null  && !billingProject.equals("") && !billingProject.equals("0")) {
            long clientProjectId = Long.parseLong(billingProject);
            return clientProjectId;
        }
        return 0;

    }

    /**
     * Checks if the contest is development contest.
     * @param contest the contest
     * @return true if yes
     */
    private boolean isDevContest(SoftwareCompetition contest) {
        return contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.DEVELOPMENT.getId();
    }


    /**
     * Checks if the contest is of type Code
     *
     * @param contest the contest to check
     * @return true if yes, false otherwise
     * @since 2.0.0
     */
    private boolean isCodeContest(SoftwareCompetition contest) {
        return contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.CODE.getId();
    }

    /**
     * Checks if the contest is of type First2Finish
     *
     * @param contest the contest to check
     * @return true if yes, false otherwise
     * @since 2.0.1
     */
    private boolean isF2FContest(SoftwareCompetition contest) {
        return contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.FIRST2FINISH.getId();
    }

    /**
     * Checks if the contest is of type Design First2Finish
     *
     * @param contest the contest tocheck
     * @return true if yes, false otherwise
     * @since 3.0
     */
    private boolean isDesignF2FContest(SoftwareCompetition contest) {
        return contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.DESIGN_FIRST2FINISH.getId();
    }


    /**
     * Checks if the contest is copilot contest.
     *
     * @param contest the contest
     * @return true if yes
     * @since 1.6.4
     */
    private boolean isCopilotContest(SoftwareCompetition contest) {
        return contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.COPILOT_POSTING.getId();
    }

    /**
     * Checks if the contest is studio contest.
     *
     * @param contest the conetst to check
     * @return true if the contest is studio contest, false otherwise
     * @since 1.6.6
     */
    private boolean isStudio(SoftwareCompetition contest) {
        return contest.getProjectHeader().getProjectCategory().getProjectType().getId() == ProjectType.STUDIO.getId();
    }

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
     * @return the created <code>SoftwareCompetition</code> as a contest
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition createSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
            long tcDirectProjectId) throws ContestServiceException, PermissionServiceException {
        return createSoftwareContest(tcSubject, contest, tcDirectProjectId, null, null);
    }

	/**
	 * Checks whether the given billing account is associated with the given direct project.
	 *
	 * @deprecated
	 * @param billingAccountId the id of the billing account
	 * @param directProjectId  the id of the direct project
	 * @throws PersistenceFault if there is any error with projectService
	 * @throws ContestServiceException if the billing account is not assciated with the direct project
	 * @since 1.9.3
	 */
	private void checkContestBillingAccount(long billingAccountId, long directProjectId)
			throws PersistenceFault, ContestServiceException {
		if(billingAccountId <= 0) {
			return;
		}

		final List<com.topcoder.clients.model.Project> billings = projectService.getBillingAccountsByProject(directProjectId);

		if(billings != null && billings.size() > 0) {
			// check if billingAccountId is in billings of the project
			for(com.topcoder.clients.model.Project billing : billings) {
				if (billing.getId() == billingAccountId) {
					return;
				}
			}
		}

		// no matched billing found
		throw new ContestServiceException("The billing account id set to contest is not belong to the project");
	}

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
     *
     * <p>
     * Update in v1.8.3: Add handling of auto creation of bug hunt for assembly competition. If the assembly contest
     * has bugHuntProjectHeader set and the properties not empty in bugHuntProjectHeader. A bug hunt contest is
     * automatically created. The bug hunt contest will
     * - have copilot inserted as reviewer (if exists)
     * - use the start date of approval date as the start date and producation date of bug hunt contest.
     * - add a "Bug Race For" link between the bug race contest and assembly contest
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contest the <code>SoftwareCompetition</code> to create as a contest
     * @param tcDirectProjectId the TC direct project id. a <code>long</code> providing the ID of a client the new
     *            competition belongs to.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase. Can be null if to use default.
     * @return the created <code>SoftwareCompetition</code> as a contest
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since 1.6.6
     */
    public SoftwareCompetition createSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
            long tcDirectProjectId, Date multiRoundEndDate, Date endDate) throws ContestServiceException, PermissionServiceException {
        logger.debug("createSoftwareContest with information : [tcSubject = " + tcSubject.getUserId() + ", tcDirectProjectId ="
                + tcDirectProjectId + ", multiRoundEndDate = " + multiRoundEndDate + "]");

        try {
            ExceptionUtils.checkNull(contest, null, null, "The contest to create is null.");
            ExceptionUtils.checkNull(contest.getProjectHeader(), null, null, "The contest#ProjectHeader to create is null.");

            // check the permission
         	//TODO liquid creaet project, and assign permission, check fails here
            //checkSoftwareProjectPermission(tcSubject, tcDirectProjectId, true);
            //check the billing project permission
            long billingProjectId = getBillingProjectId(contest);

			// check if billing project id is one of the billing accounts for the direct project
			if (billingProjectId > 0) {
				// do the checking
				checkContestBillingAccount(billingProjectId, contest.getProjectHeader().getTcDirectProjectId());
			}

            //check whether we need to auto-create-development contest for design
            boolean creatingDevContest = shouldAutoCreateDevContest(contest);

            // check whether we need to auto create bug hunt contest for the bug hunt
            boolean creatingBugHunt = shouldAutoCreateBugHuntContest(contest);

            //copy the data from design to development if it is going to do auto-dev-creating
            SoftwareCompetition devContest = null;
            if (creatingDevContest) {
                devContest = (SoftwareCompetition)contest.clone();
            }

            SoftwareCompetition bugHuntContest = null;

            if (creatingBugHunt) {
                // copy the data from assembly to bug hunt if creatingBugHunt is true
                bugHuntContest = (SoftwareCompetition) contest.clone();
            }


            boolean requireApproval = true;

            if(isStudio(contest)
			     || contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.BUG_HUNT.getId()
				 ||  contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.COPILOT_POSTING.getId() ) {
                // do not add approver for bug hunt and copilot posting
                requireApproval = false;
            }
            
            checkBillingProjectCCA(contest);
            
            //update the AssetDTO and update corresponding properties
            createUpdateAssetDTO(tcSubject, contest);

            com.topcoder.management.resource.Resource[] contestResources = createContestResources(tcSubject, contest, billingProjectId, requireApproval);

            //create contest resources
            contest.setProjectResources(contestResources);

            //set the tc-direct-project-id
            contest.getProjectHeader().setTcDirectProjectId(tcDirectProjectId);

            // set status to draft
            contest.getProjectHeader().setProjectStatus(ProjectStatus.DRAFT);

            //create project now
            FullProjectData projectData = projectServices.createProjectWithTemplate(contest.getProjectHeader(),
                        contest.getProjectPhases(), contest.getProjectResources(), multiRoundEndDate, endDate,
                        String.valueOf(tcSubject.getUserId()));

            if (contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.DEVELOPMENT.getId()) {
                projectServices.linkDevelopmentToDesignContest(projectData.getProjectHeader().getId());
            }

            if(contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.BUG_HUNT.getId()
                    && contest.getProjectHeader().isAutoCreationBugHunt() ) {
                // link the bug hunt to the parent contest if it's of type bug hunt and it's an auto created one
                projectServices.linkBugHuntToOtherContest(projectData.getProjectHeader().getId());

                // add copilot as reviewer
                for(com.topcoder.management.resource.Resource r : contestResources) {
                    if(r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID) {
                        addReviewer(tcSubject, projectData.getProjectHeader().getId(), Long.parseLong(r.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID)));
                    }
                }
            }

            if (contest.getProjectHeader().getAutoAssignReviewerId() > 0) {
                if(contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.CODE.getId()) {
                    // no review payment for the contest
                    addReviewer(tcSubject, projectData.getProjectHeader().getId(), contest.getProjectHeader().getAutoAssignReviewerId());
                }
            }

            // set copilot forum permission
            long forumId = 0;

            if (createForum) {
                forumId = contest.getAssetDTO().getForum().getJiveCategoryId();
            }

//            String adminRole = getEligibilityAdminRole(tcSubject, billingProjectId).trim();

            for (com.topcoder.management.resource.Resource r : contest.getProjectResources()) {
                long roleId = r.getResourceRole().getId();
                long uid = Long.parseLong(r.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID));


                if (r.getProperty(RESOURCE_INFO_HANDLE).equals(RESOURCE_INFO_HANDLE_COMPONENTS)
                    || r.getProperty(RESOURCE_INFO_HANDLE).equals(RESOURCE_INFO_HANDLE_APPLICATIONS)
                /**|| r.getProperty(RESOURCE_INFO_HANDLE).equals(adminRole)**/)
                {
                    continue;
                }
                boolean addNotification;
                boolean addForumWatch;

                List<Integer> preferenceIds = new ArrayList<Integer>();
                // notification preference
                preferenceIds.add(GLOBAL_TIMELINE_NOTIFICATION);
                // forum preference
                preferenceIds.add(GLOBAL_FORUM_WATCH);

                Map<Integer, String> preferences = getUserPreferenceMaps(uid, preferenceIds);

                addNotification = Boolean.parseBoolean(preferences.get(GLOBAL_TIMELINE_NOTIFICATION));
                addForumWatch = Boolean.parseBoolean(preferences.get(GLOBAL_FORUM_WATCH));
                if(forumId > 0 && createForum) {
                        // add forum watch/permission for each copilot to create
                        if (roleId == ResourceRole.RESOURCE_ROLE_COPILOT_ID) {

                            if (!isStudio(contest))
                            {
                                createSoftwareForumWatchAndRole(forumId, uid, true);
                            }
                            else
                            {
                                createStudioForumWatchAndRole(forumId, uid, true, true);
                            }
                        }
                        else if (roleId == ResourceRole.RESOURCE_ROLE_OBSERVER_ID) {
                            if (!isStudio(contest))
                            {
                                createSoftwareForumWatchAndRole(forumId, uid, addForumWatch);
                            }
                            else
                            {
                                createStudioForumWatchAndRole(forumId, uid, addForumWatch, true);
                            }

                        }
                }

                if (roleId != ResourceRole.RESOURCE_ROLE_OBSERVER_ID || addNotification)
                {
                    // set timeline notification
                    projectServices.addNotifications(uid, new long[]{projectData.getProjectHeader().getId()}, String.valueOf(tcSubject.getUserId()));
                }
            }


            // set timeline notification
            projectServices.addNotifications(tcSubject.getUserId(), new long[]{projectData.getProjectHeader().getId()}, String.valueOf(tcSubject.getUserId()));


            //preparing the result
            com.topcoder.project.phases.Phase[] allPhases = projectData.getAllPhases();
            // for now have to do these to avoid cycle
            for (int i = 0; i < allPhases.length; i++) {
                allPhases[i].setProject(null);
                allPhases[i].clearDependencies();
            }

            contest.setProjectHeader(projectData.getProjectHeader());
            contest.setProjectPhases(projectData);
            contest.setProjectResources(projectData.getResources());
            contest.setProjectData(projectData);
            contest.setId(projectData.getProjectHeader().getId());

            if (contest.getAssetDTO() != null) {
                // set null to avoid cycle
                contest.getAssetDTO().setDependencies(null);
                if (contest.getAssetDTO().getForum() != null) {
                    contest.getAssetDTO().getForum().setCompVersion(null);
                }
                if (contest.getAssetDTO().getLink() != null) {
                    contest.getAssetDTO().getLink().setCompVersion(null);
                }

                // need to remove loops before returning
                removeDocumentationLoops(contest);

                Date startDate = contest.getProjectPhases().getStartDate();
                for (com.topcoder.project.phases.Phase p : contest.getProjectPhases().getPhases())
                {
                    if (p.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId())
                    {
                        startDate = p.getFixedStartDate();
                        break;
                    }
                 }

                // set project start date in production date
                contest.getAssetDTO().setProductionDate(getXMLGregorianCalendar(startDate));
            }

            if (billingProjectId > 0) {
                persistContestEligility(contest.getProjectHeader(), null, false);
            }

            if (creatingDevContest) {
                autoCreateDevContest(tcSubject, contest, tcDirectProjectId, devContest);
                contest.setDevelopmentProjectHeader(devContest.getProjectHeader());
                contest.setDevId(devContest.getProjectHeader().getId());
            }

            if (creatingBugHunt) {
                // get the approval phase start time of assembly contest
                Date apprvalPhaseStartDate = null;

                for (com.topcoder.project.phases.Phase p : contest.getProjectPhases().getPhases()) {
                    if (p.getPhaseType().getId() == PhaseType.APPROVAL_PHASE.getId()) {
                        apprvalPhaseStartDate = p.getScheduledStartDate();
                        break;
                    }
                }

                // auto create the bug hunt contest
                autoCreateBugHuntContest(tcSubject, contest, tcDirectProjectId, bugHuntContest, apprvalPhaseStartDate);

                // set updated bug hunt project header for the parent assembly contest
                contest.setBugHuntProjectHeader(bugHuntContest.getProjectHeader());
            }

            addReviewAuction(contest);

            // create direct project milestone xref if needed
            if (contest.getDirectProjectMilestoneId() > 0) {
                projectServices.createProjectMilestoneRelation(contest.getProjectHeader().getId(),
                                                               contest.getDirectProjectMilestoneId(),
                                                               String.valueOf(tcSubject.getUserId()));
            }

            // publish event to AWS SNS if needed
            try {
                String cmcTaskId = contest.getProjectHeader().getProperty(
                        ProjectPropertyType.CLOUDSPOKES_CMC_TASK_PROPERTY_KEY);
				String apiURL = "";
				
				if(isStudio(contest)) {
					apiURL = "http://api.topcoder.com/v2/design/challenges/" + String.valueOf(contest.getProjectHeader().getId());
				} else { 
					apiURL = "http://api.topcoder.com/v2/develop/challenges/" + String.valueOf(contest.getProjectHeader().getId());
				}

                if (StringUtils.isNotEmpty(cmcTaskId)) {
                    String contestCreationMessage = contestCreationMessageTemplate.
                            replace("%CHALLENGE_ID%", String.valueOf(contest.getProjectHeader().getId())).
                            replace("%CMC_TASK_ID%", cmcTaskId).
							replace("%API_URL%", apiURL);

                    String subject = contestCreationSubject.replace("%CMC_TASK_ID%", cmcTaskId);

                    PublishRequest request = new PublishRequest().
                            withSubject(subject).
                            withMessage(contestCreationMessage).
                            withTopicArn(contestCreationArn);

                    PublishResult result = amazonSNSClient.publish(request);
                    logger.debug("Publish amazon SNS successfully, the published message id is "
                            + result.getMessageId());
                }
            } catch (Exception e) {
                // we will only log publish error here, won't rollback contest creation.
                logger.error("Operation failed in publish amazon SNS.", e);
            }

            return contest;
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } finally {
            logger.debug("Exit createSoftwareContest, the newly create contest id = " + contest.getId());
        }
    }

    /**
     * <p>
     * Detects whether the auto creating development contest is on.
     * </p>
     * @param contest the contest
     * @return true if yes
     */
    private boolean shouldAutoCreateDevContest(SoftwareCompetition contest) {
        return contest.getDevelopmentProjectHeader() != null
                                     && contest.getDevelopmentProjectHeader().getProperties() != null
                                     && contest.getDevelopmentProjectHeader().getProperties().size() != 0;
    }

    /**
     * <p>
     * Detects whether need to auto create the bug hunt contest.
     * </p>
     *
     * @param contest the contest
     * @return true if yes, false otherwise
     * @since 1.8.3
     */
    private boolean shouldAutoCreateBugHuntContest(SoftwareCompetition contest) {
        return contest.getBugHuntProjectHeader() != null
                                             && contest.getBugHuntProjectHeader().getProperties() != null
                                             && contest.getBugHuntProjectHeader().getProperties().size() != 0;
    }

    /**
     * Create or updating the AssetDTO for the contest. If the AssetDTO already exists for development contest, we need
     * to create dev-component. Also, creating forum if necessary.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * <p>
     * Update in v1.6.4: set digital run flag to 'Off' and rated to 'No' if it's copilot selection contest.
     * </p>
     *
     * <p>
     * Update in 1.8.3:
     * - Reuse the same assetDTO as the parent contest for auto creation bug hunt contest.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contest the contest
     * @throws EntityNotFoundException if any error occurs
     * @throws com.topcoder.catalog.service.PersistenceException if any error occurs
     */
    private void createUpdateAssetDTO(TCSubject tcSubject, SoftwareCompetition contest) throws EntityNotFoundException,
            com.topcoder.catalog.service.PersistenceException, DAOException, ConfigManagerException {
        //check if it is going to create development contest
        boolean isDevContest = isDevContest(contest);
        XMLGregorianCalendar productionDate = null;
        if (contest.getAssetDTO() != null) {
            AssetDTO assetDTO = contest.getAssetDTO();
            boolean useExistingAsset = false;
            if (isDevContest && assetDTO.getVersionNumber()!= null && assetDTO.getVersionNumber().longValue() != 1) {
                useExistingAsset = true;
                productionDate = assetDTO.getProductionDate();
                assetDTO = catalogService.getAssetByVersionId(assetDTO.getVersionNumber());
                // for dev, we need to insert a row in comp version dates
                catalogService.createDevComponent(assetDTO);
                // set dev only flag
            } else if (contest.getProjectHeader().isAutoCreationBugHunt()) {
                // use the asset belong to the parent contest
                useExistingAsset = true;
                productionDate = assetDTO.getProductionDate() == null ? nextDay(): assetDTO.getProductionDate();
            }
            // dev only
            else if (isDevContest)
            {
                contest.getProjectHeader().setDevOnly(true);
            }

            if (!useExistingAsset) {
                productionDate = assetDTO.getProductionDate() == null ? nextDay():assetDTO.getProductionDate();
                assetDTO.setProductionDate(null);
                if (contest.getProjectHeader() != null) {
                    // comp development, set phase to dev, otherwise to design
                    assetDTO.setPhase(isDevContest?"Development":"Design");
                }
                assetDTO = this.catalogService.createAsset(assetDTO);
                contest.setAssetDTO(assetDTO);
            }
            long forumId = 0;
            // create forum
            if (createForum) {
                if (useExistingAsset && assetDTO.getForum() != null) {
                    forumId = assetDTO.getForum().getJiveCategoryId();
                } else {
                    if (!isStudio(contest)) {
                        // software contest
                        forumId = createForum(tcSubject, assetDTO, tcSubject.getUserId(), contest.getProjectHeader().getProjectCategory().getId());
                    } else {
                        // studio contest
                        forumId = createStudioForum(assetDTO.getName(), tcSubject.getUserId());
                    }
                }
            }

            // if forum created
            if (forumId > 0 && (!useExistingAsset || assetDTO.getForum() == null)) {
                // create a comp forum
                CompForum compForum = new CompForum();
                compForum.setJiveCategoryId(forumId);
                assetDTO.setForum(compForum);
                assetDTO = this.catalogService.updateAsset(assetDTO);
                // avoid cycle
                assetDTO.getForum().setCompVersion(null);
            }
            contest.setAssetDTO(assetDTO);

            contest.getProjectHeader().setProperty(ProjectPropertyType.EXTERNAL_REFERENCE_ID_PROJECT_PROPERTY_KEY, assetDTO.getCompVersionId().toString());
            contest.getProjectHeader().setProperty(ProjectPropertyType.COMPONENT_ID_PROJECT_PROPERTY_KEY, assetDTO.getId().toString());
            contest.getProjectHeader().setProperty(ProjectPropertyType.ROOT_CATALOG_ID_PROJECT_PROPERTY_KEY, assetDTO.getRootCategory().getId().toString());
            contest.getProjectHeader().setProperty(ProjectPropertyType.SVN_MODULE_PROJECT_PROPERTY_KEY, "");
            contest.getProjectHeader().setProperty(ProjectPropertyType.NOTES_PROJECT_PROPERTY_KEY, "");
            contest.getProjectHeader().setProperty(ProjectPropertyType.PROJECT_VERSION_PROJECT_PROPERTY_KEY, "1.0");
            contest.getProjectHeader().setProperty(ProjectPropertyType.VERSION_ID_PROJECT_PROPERTY_KEY, "1");
            contest.getProjectHeader().setProperty(ProjectPropertyType.AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY, "Off");
            contest.getProjectHeader().setProperty(ProjectPropertyType.STATUS_NOTIFICATION_PROJECT_PROPERTY_KEY, "On");
            contest.getProjectHeader().setProperty(ProjectPropertyType.TIMELINE_NOTIFICATION_PROJECT_PROPERTY_KEY, "On");
            contest.getProjectHeader().setProperty(ProjectPropertyType.PUBLIC_PROJECT_PROPERTY_KEY, "Yes");
            contest.getProjectHeader().setProperty(ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY, "Yes");
            contest.getProjectHeader().setProperty(ProjectPropertyType.ELIGIBILITY_PROJECT_PROPERTY_KEY, "Open");

            // set the forum type
            if (!isStudio(contest)) {
                // software contest
                contest.getProjectHeader().setProperty(ProjectPropertyType.FORUM_TYPE, "Development");
            } else {
                // studio contest
                contest.getProjectHeader().setProperty(ProjectPropertyType.FORUM_TYPE, "Design");
            }

			if (contest.getProjectHeader().getProperty(ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY) == null)
            { contest.getProjectHeader().setProperty(ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY, "On"); }

            if(isF2FContest(contest) || isDesignF2FContest(contest)) {
                contest.getProjectHeader().setProperty(ProjectPropertyType.ALLOW_MULTIPLE_SUBMISSIONS_PROPERTY_KEY, "true");
            }

            boolean hasEligibility = false;

            long billingProjectId = getBillingProjectId(contest);

            // if creating contest, eligiblity is not commited, so above will not get back result
            if (billingProjectId != 0
                    && contest.getProjectHeader().getSecurityGroupId() > 0) {
                hasEligibility = true;
            }

            if (hasEligibility)
            {
                contest.getProjectHeader().setProperty(ProjectPropertyType.SEND_WINNDER_EMAILS_PROJECT_PROPERTY_KEY, "false");
            }
            else
            {
                contest.getProjectHeader().setProperty(ProjectPropertyType.SEND_WINNDER_EMAILS_PROJECT_PROPERTY_KEY, "true");
            }


            if (isDevContest && !hasEligibility && billingProjectId > 0)
            {
                String codename = billingProjectDAO.getProjectById(billingProjectId).getClient().getCodeName();
                String compname = contest.getAssetDTO().getName();
                if (codename != null && !codename.equals(""))
                {

				    codename = CodeNameUtil.decodeCodeName(codename, "users");
                    compname = compname.toLowerCase().replaceAll(" ", "_").replaceAll("/", "_").replaceAll("&", "_");
                    codename = codename.toLowerCase().replaceAll(" ", "");
                    contest.getProjectHeader().setProperty(ProjectPropertyType.SVN_MODULE_PROJECT_PROPERTY_KEY, "https://coder.topcoder.com/tcs/clients/"+codename+"/components/"+compname+"/trunk");
                }
            }

			contest.getProjectHeader().setProperty(ProjectPropertyType.TRACK_LATE_DELIVERABLES_PROJECT_PROPERTY_KEY, "true");

            if (isCopilotContest(contest)) {
                contest.getProjectHeader().setProperty(ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY, "Off");
				contest.getProjectHeader().setProperty(ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY, "0");
                contest.getProjectHeader().setProperty(ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY, "No");
				contest.getProjectHeader().setProperty(ProjectPropertyType.RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY, "false");
                contest.getProjectHeader().setProperty(ProjectPropertyType.CONFIDENTIALITY_TYPE_PROJECT_PROPERTY_KEY, "standard_cca");
				contest.getProjectHeader().setProperty(ProjectPropertyType.TRACK_LATE_DELIVERABLES_PROJECT_PROPERTY_KEY, "false");
            }
			
			if (contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.BUG_HUNT.getId())
			{
				contest.getProjectHeader().setProperty(ProjectPropertyType.RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY, "false");
			}

            if (isStudio(contest)) {
                contest.getProjectHeader().setProperty(ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY, "No");
				contest.getProjectHeader().setProperty(ProjectPropertyType.TRACK_LATE_DELIVERABLES_PROJECT_PROPERTY_KEY, "false");
            }

            if (isCodeContest(contest) || isF2FContest(contest) || isDesignF2FContest(contest)) {
                // no rated for Code || F2F || Design F2F
                contest.getProjectHeader().setProperty(ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY, "No");

                // no Reiliability for Code || F2F || Design F2F
                contest.getProjectHeader().setProperty(ProjectPropertyType.RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY, "false");
                contest.getProjectHeader().setProperty(ProjectPropertyType.RELIABILITY_BONUS_COST_PROJECT_PROPERTY_KEY, "0");

                // no Approval Required for Code || F2F || Design F2F
                contest.getProjectHeader().setProperty(ProjectPropertyType.APPROVAL_REQUIRED_PROJECT_PROPERTY_KEY, "false");
            }

            if(isF2FContest(contest) || isCodeContest(contest)) {
                // no Digital Run for F2F/Code
                contest.getProjectHeader().setProperty(ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY, "Off");
                contest.getProjectHeader().setProperty(ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY, "0");
            }

			if (!isStudio(contest) && !isCopilotContest(contest)
                    && contest.getProjectHeader().getProjectCategory().getId() != ProjectCategory.BUG_HUNT.getId()
                    && !isCodeContest(contest)
                    && !isF2FContest(contest))
			{
				contest.getProjectHeader().setProperty(ProjectPropertyType.REVIEW_FEEDBACK_FLAG_PROJECT_PROPERTY_KEY, "true");
			}
			else
			{
				contest.getProjectHeader().setProperty(ProjectPropertyType.REVIEW_FEEDBACK_FLAG_PROJECT_PROPERTY_KEY, "false");
			}

            if (contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.REPORTING.getId() ||
                   contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.BUG_HUNT.getId() || isDesignF2FContest(contest))
            {
                contest.getProjectHeader().setProperty(ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY, "Off");
				contest.getProjectHeader().setProperty(ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY, "0");
                contest.getProjectHeader().setProperty(ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY, "No");
            }

            if (isTask(contest)) {
                // not rate for private
                contest.getProjectHeader().setProperty(ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY, "No");
            }

//			if (contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.CONTENT_CREATION.getId())
//            {
//                contest.getProjectHeader().setProperty(ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY, "Off");
//				contest.getProjectHeader().setProperty(ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY, "0");
//            }

            if (forumId > 0) {
                contest.getProjectHeader().setProperty(ProjectPropertyType.DEVELOPER_FORUM_ID_PROJECT_PROPERTY_KEY, String.valueOf(forumId));
            }

            contest.getProjectPhases().setStartDate(getDate(productionDate));
        }
    }

    /**
     * <p>
     * If the auto creating development contest is switch on, we need to prepare the contest here.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param designContest the design contest
     * @param tcDirectProjectId tc-direct-project-id
     * @param devContest the development contest to create
     * @throws DatatypeConfigurationException if any error occurs
     * @throws ContestServiceException if any error occurs
     */
    private void autoCreateDevContest(TCSubject tcSubject, SoftwareCompetition designContest, long tcDirectProjectId,
            SoftwareCompetition devContest) throws DatatypeConfigurationException, ContestServiceException, PermissionServiceException {
        devContest.setAssetDTO(designContest.getAssetDTO());
        devContest.getProjectHeader().getProperties().putAll(
                designContest.getDevelopmentProjectHeader().getProperties());
        devContest.setDevelopmentProjectHeader(null);
        devContest.getProjectHeader().getProjectCategory().setId(ProjectCategory.DEVELOPMENT.getId());
        devContest.getAssetDTO().setProductionDate(nextDevProdDay(devContest.getAssetDTO().getProductionDate()));
        devContest.setProjectHeaderReason("Create corresponding development contest");
        createSoftwareContest(tcSubject, devContest, tcDirectProjectId);
    }
    /**
     * <p>
     *  Auto creates the bug hunt contest for the parent contest if auto create bug hunt is on. This method prepares
     *  the bug hunt contest before inserting into the persistence.
     * </p>
     *
     * @param tcSubject instance contains the login security info for the current user
     * @param assemblyContest the parent assembly contest
     * @param tcDirectProjectId the if of the direct project
     * @param bugHuntContest the bug hunt contest
     * @param bugHuntStartTime the scheduled start time of the bug hunt contest
     * @throws ContestServiceException if any error occurs realted to Contest Service facade
     * @throws PermissionServiceException if any error occurs to Permission service facade
     * @since 1.8.3
     */
    private void autoCreateBugHuntContest(TCSubject tcSubject, SoftwareCompetition assemblyContest, long tcDirectProjectId,
                                          SoftwareCompetition bugHuntContest, Date bugHuntStartTime) throws ContestServiceException, PermissionServiceException {
        // preCreationAssetDTO.setName("Bug Hunt For " + preCreationAssetDTO);
        bugHuntContest.setAssetDTO(assemblyContest.getAssetDTO());
        bugHuntContest.getProjectHeader().getProperties().putAll(assemblyContest.getBugHuntProjectHeader().getProperties());

        final Map<String, String> properties = bugHuntContest.getProjectHeader().getProperties();

        // get the first prize set
        final String firstPrizeStr = properties.get(ProjectPropertyType.FIRST_PLACE_COST_PROJECT_PROPERTY_KEY);
        double firstPrize = Double.parseDouble(firstPrizeStr);

        // update the project header with the first place prize
        final List<Prize> prizes = bugHuntContest.getProjectHeader().getPrizes();

        if(prizes != null) {
            if(prizes.size() > 1) {
                // remove the unneeded prizes
                for(int i = 1; i < prizes.size(); ++i) {
                    prizes.remove(i);
                }
            }

            for (int i = 0; i < prizes.size(); ++i) {
                if (i == 0) {
                    // override the first place prize amount
                    prizes.get(0).setPrizeAmount(firstPrize);
                    prizes.get(0).setPlace(1);
                }
            }
        }

        // update project header properties

        // set first place prize
        properties.put(ProjectPropertyType.PAYMENTS_PROJECT_PROPERTY_KEY, String.valueOf(firstPrize));

        // set second place prize
        properties.put(ProjectPropertyType.SECOND_PLACE_COST_PROJECT_PROPERTY_KEY, "0");

        // set review cost to 0
        properties.put(ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY, "0");

        // set reliability cost to 0
        properties.put(ProjectPropertyType.RELIABILITY_BONUS_COST_PROJECT_PROPERTY_KEY, "0");

        // set DR points to 0
        properties.put(ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY, "0");

        // set review cost to 0
        properties.put(ProjectPropertyType.SPEC_REVIEW_COSTS_PROJECT_PROPERTY_KEY, "0");

        // set copilot cost to 0
        properties.put(ProjectPropertyType.COPILOT_COST_PROJECT_PROPERTY_KEY, "0");

        // set the bug hunt contest name
        properties.put(ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY,
                assemblyContest.getProjectHeader().getProperties().get(ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY));

        bugHuntContest.setBugHuntProjectHeader(null);

        // set the category id to bug hunt
        bugHuntContest.getProjectHeader().getProjectCategory().setId(ProjectCategory.BUG_HUNT.getId());
        bugHuntContest.getProjectHeader().getProjectCategory().setName(ProjectCategory.BUG_HUNT.getName());

        // set the start time
        bugHuntContest.getProjectPhases().setStartDate(bugHuntStartTime);

        // set the production time
        bugHuntContest.getAssetDTO().setProductionDate(getXMLGregorianCalendar(bugHuntStartTime));

        // set the project header reason
        bugHuntContest.setProjectHeaderReason("Create corresponding bug hunt contest for assembly");


        // set the flag to indiciate it's an auto created bug hunt
        bugHuntContest.getProjectHeader().setAutoCreationBugHunt(true);

        // create the bug hunt contest
        createSoftwareContest(tcSubject, bugHuntContest, tcDirectProjectId);
    }


    /**
     * <p>
     * Persists the GroupContestEligibility for the contest and client. If the eligiblity is not null, then the
     * information will be copied from it.
     * </p>
     * @param projectHeader the contest header data
     * @param eligiblity, the existing ContestEligibility, could be null
     * @param isStudio true for studio
     * @throws ContestEligibilityPersistenceException if any error occurs
     */
    private void persistContestEligility(Project projectHeader, ContestEligibility eligiblity, boolean isStudio)
        throws ContestEligibilityPersistenceException {

        Long eligibilityGroupId = null;
        if (eligiblity == null)
        {

            eligibilityGroupId = projectHeader.getSecurityGroupId();
        }
        else
        {
            eligibilityGroupId = ((GroupContestEligibility)eligiblity).getGroupId();
        }


        if (eligibilityGroupId != null && eligibilityGroupId > 0) {
            GroupContestEligibility contestEligibility = new GroupContestEligibility();
            contestEligibility.setContestId(projectHeader.getId());
            contestEligibility.setStudio(isStudio);
            contestEligibility.setDeleted(eligiblity == null ? false : eligiblity.isDeleted());
            contestEligibility.setGroupId(eligibilityGroupId);
            contestEligibilityManager.create(contestEligibility);
        }
    }

    /**
     * <p>
     * Adding the contest resources when creating contest. manager or observer or client-manager will be added.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * <p>
     * Update in v1.6.8: gets the copilot resource from getResources() of SoftwareCompetition.
     * </p>
     *
     * <p>
     * Update in v1.8.3: For auto created bug hunt contest, insert the copilot as reviewer of the bug hunt contest
     * if the copilot exists.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contest the contest to create
     * @param billingProjectId the billing project id
     * @param requireApproval the flag of whether it's required
     * @return resource array
     * @throws ContestServiceException fail to retrieve user-handle
     */
    private com.topcoder.management.resource.Resource[] createContestResources(TCSubject tcSubject,
            SoftwareCompetition contest, long billingProjectId, boolean requireApproval) throws ContestServiceException, UserServiceException, PermissionServiceException {

         // check if contest contains copilot resource
        com.topcoder.management.resource.Resource[] contestResources = contest.getResources();
        com.topcoder.management.resource.Resource copilot = null;
        com.topcoder.management.resource.Resource appirioManager = null;

        // flag indicates whether current user is set as the copilot
        boolean isCopilotCurrentUser = false;
        boolean isAppirioManagerCurrentUser = false;

        if (contestResources.length > 1) {
            for (int i = 1; i < contestResources.length; ++i) {

                if (contestResources[i].getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID) {
                    // contains copilot resource
                    copilot = contestResources[1];

                    if (copilot.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID).equals(
                            String.valueOf(tcSubject.getUserId()))) {
                        isCopilotCurrentUser = true;
                    }
                } else if (contestResources[i].getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_MANAGER_ID) {
                    appirioManager = contestResources[i];
                    if (appirioManager.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID).equals(
                            String.valueOf(tcSubject.getUserId()))) {
                        isAppirioManagerCurrentUser = true;
                    }
                }
            }
        }

        // create an array to store the resources, if copilot exists and copilot is not current user, we create
        // an array of length 3, otherwise of length 2
        com.topcoder.management.resource.Resource[] resources =
                new com.topcoder.management.resource.Resource[2];

        resources[0] = new com.topcoder.management.resource.Resource();
        resources[0].setId(com.topcoder.management.resource.Resource.UNSET_ID);

        // manager role
        ResourceRole managerRole = new ResourceRole();
        managerRole.setId(ResourceRole.RESOURCE_ROLE_MANAGER_ID);
        managerRole.setName(ResourceRole.RESOURCE_ROLE_MANAGER_NAME);
        managerRole.setDescription(ResourceRole.RESOURCE_ROLE_MANAGER_DESC);

        // observer role
        ResourceRole observerRole = new ResourceRole();
        observerRole.setId(ResourceRole.RESOURCE_ROLE_OBSERVER_ID);
        observerRole.setName(ResourceRole.RESOURCE_ROLE_OBSERVER_NAME);
        observerRole.setDescription(ResourceRole.RESOURCE_ROLE_OBSERVER_DESC);

        // client manager role
        ResourceRole clientManagerRole = new ResourceRole();
        clientManagerRole.setId(ResourceRole.RESOURCE_ROLE_CLIENT_MANAGER_ID);
        clientManagerRole.setName(ResourceRole.RESOURCE_ROLE_CLIENT_MANAGER_NAME);
        clientManagerRole.setDescription(ResourceRole.RESOURCE_ROLE_CLIENT_MANAGER_DESC);

        // copilot role
        ResourceRole copilotRole = new ResourceRole();
        copilotRole.setId(ResourceRole.RESOURCE_ROLE_COPILOT_ID);

        // is current user TC staff role
        boolean tcstaff = isRole(tcSubject, TC_STAFF_ROLE);
        boolean isObserverCopilot = false;

        if (appirioManager != null) {
        	resources[0] = appirioManager;
        } else if (tcstaff) {
            resources[0].setResourceRole(managerRole);
        } else if (contest.getProjectHeader().getSecurityGroupId() > 0) {
            resources[0].setResourceRole(managerRole);
        } else {
            if (isCopilotCurrentUser) {
              // if copilot is current user, then set as copilot
              resources[0] = copilot;
              isObserverCopilot = true;
            } else {
              resources[0].setResourceRole(managerRole);
            }
        }

        if (!isObserverCopilot && appirioManager == null) {
            // we don't override the copilot properties if the observer is the copilot
            resources[0].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, String.valueOf(tcSubject.getUserId()));
            resources[0].setProperty(RESOURCE_INFO_HANDLE, getUserName(tcSubject));
            resources[0].setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
            resources[0].setUserId(tcSubject.getUserId());
        }

        resources[0].setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));

        // for private, check if admin role is set, and use that if so
        /** if (getEligibilityName(tcSubject, billingProjectId).trim().length() > 0) {

            String adminRole = getEligibilityAdminRole(tcSubject, billingProjectId).trim();

            if (adminRole.length() > 0)
            {
                long roleId = userService.getUserId(adminRole);
                resources[1] = new com.topcoder.management.resource.Resource();
                resources[1].setId(com.topcoder.management.resource.Resource.UNSET_ID);
                resources[1].setResourceRole(managerRole);
                resources[1].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, Long.toString(roleId));
                resources[1].setProperty(RESOURCE_INFO_HANDLE, adminRole);
                resources[1].setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
                resources[1].setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));
            }
        } **/
        // design/dev, add Components
        if (contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.DEVELOPMENT.getId()
             || contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.DESIGN.getId()) {

            resources[1] = new com.topcoder.management.resource.Resource();
            resources[1].setId(com.topcoder.management.resource.Resource.UNSET_ID);
            resources[1].setResourceRole(managerRole);
            resources[1].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, Long.toString(components_user_id));
            resources[1].setUserId(components_user_id);
            resources[1].setProperty(RESOURCE_INFO_HANDLE, RESOURCE_INFO_HANDLE_COMPONENTS);
            resources[1].setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
            resources[1].setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));
        }
        // else add Applications
        else {
            resources[1] = new com.topcoder.management.resource.Resource();
            resources[1].setId(com.topcoder.management.resource.Resource.UNSET_ID);
            resources[1].setResourceRole(managerRole);
            resources[1].setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, Long.toString(applications_user_id));
            resources[1].setUserId(applications_user_id);
            resources[1].setProperty(RESOURCE_INFO_HANDLE, RESOURCE_INFO_HANDLE_APPLICATIONS);
            resources[1].setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
            resources[1].setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));
        }

        if (copilot != null && (!isCopilotCurrentUser || appirioManager != null)) {
            com.topcoder.management.resource.Resource[] _resources = resources;
            resources = new com.topcoder.management.resource.Resource[3];
            resources[0] = _resources[0];
            resources[1] = _resources[1];
            // when copilot exists and copilot is not current user, we store resource in another array element
            resources[2] = copilot;

            // and set the registration date with current date
            resources[2].setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));
        }


        // add users has permission on cockpit project as observers
        List<Permission> permissions = this.permissionService.getPermissionsByProject(contest.getProjectHeader().getTcDirectProjectId());

        List<com.topcoder.management.resource.Resource> allResources = new ArrayList<com.topcoder.management.resource.Resource>();
        Set<Long> existingResourceIds = new HashSet<Long>();

        for(com.topcoder.management.resource.Resource r : resources) {
            allResources.add(r);
            existingResourceIds.add(Long.valueOf(r.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID)));
        }

        if (appirioManager != null) {
            allResources.add(appirioManager);
            existingResourceIds.add(appirioManager.getUserId());
        }

        for (Permission p : permissions) {
            if (!existingResourceIds.contains(p.getUserId())) {
                com.topcoder.management.resource.Resource r = new com.topcoder.management.resource.Resource();
                r.setResourceRole(observerRole);
                r.setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, String.valueOf(p.getUserId()));
                r.setUserId(p.getUserId());
                r.setProperty(RESOURCE_INFO_HANDLE, p.getUserHandle());
                r.setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
                r.setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));
                allResources.add(r);
            }
        }

        // add approval if there is approval phase and copilot exists
        if(copilot != null && requireApproval) {
            allResources.add(createApproverResourceFromCopilot(copilot, 0));
        }


        if (contest.getProjectHeader().getAutoAssignReviewerId() > 0) {
            if (contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.FIRST2FINISH.getId()) {
                allResources.add(
                        createIterativeReviewerResource(contest.getProjectHeader().getAutoAssignReviewerId(), 0, 0));
            }
        }

        return allResources.toArray(new com.topcoder.management.resource.Resource[allResources.size()]);
    }

    /**
     * Creates an Iterative Reviewer Resource to add to the contest
     *
     * @param userId the user id.
     * @param contestId the contest id.
     * @param phaseId the phase if the resource adds to
     * @return the created Iterative Reviewer Resource
     * @throws UserServiceException if error when getting resource handle
     * @since 2.5
     */
    private com.topcoder.management.resource.Resource createIterativeReviewerResource(long userId, long contestId, long phaseId) throws UserServiceException {
        com.topcoder.management.resource.Resource resource = new com.topcoder.management.resource.Resource();
        // unset id
        resource.setId(-1);

        // set resource to iterative reviewer
        resource.setResourceRole(new ResourceRole(ResourceRole.RESOURCE_ROLE_ITERATIVE_REVIEWER_ID));

        resource.setProperty(RESOURCE_INFO_HANDLE, userService.getUserHandle(userId));
        resource.setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, String.valueOf(userId));
        resource.setUserId(userId);

        if(contestId > 0) {
            resource.setProject(contestId);
        }

        if (phaseId > 0) {
            resource.setPhase(phaseId);
        }

        // set registration date to now
        resource.setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));

        return resource;
    }


    /**
     * Creates a Reviewer Resource to add to the contest
     *
     * @param userId the user id
     * @param contestId the contest id.
     * @param phaseId the phase the resource adds to, 0 for not add
     * @param hasPayment whether this resource should be paid.
     * @return the created Reviewer resource to add.
     * @throws UserServiceException if error when getting resource handle.
     * @since 2.5
     */
    private com.topcoder.management.resource.Resource createReviewerResource(long userId, long contestId, long phaseId,
                                                                            boolean hasPayment) throws UserServiceException {
        return createReviewerResource(userId, contestId, phaseId, hasPayment, false);
    }

    /**
     * Creates a Reviewer Resource to add to the contest
     *
     * @param userId the user id
     * @param contestId the contest id.
     * @param phaseId the phase the resource adds to, 0 for not add
     * @param hasPayment whether this resource should be paid.
     * @param iterativeReviewer whether this iterative review
     * @return the created Reviewer resource to add.
     * @throws UserServiceException if error when getting resource handle.
     * @since 3.6
     */
    private com.topcoder.management.resource.Resource createReviewerResource(long userId, long contestId, long phaseId,
                                                                             boolean hasPayment, boolean iterativeReviewer) throws UserServiceException {
        com.topcoder.management.resource.Resource resource = new com.topcoder.management.resource.Resource();
        // unset id
        resource.setId(-1);

        // set resource to reviewer
        if (iterativeReviewer){
            resource.setResourceRole(new ResourceRole(ResourceRole.RESOURCE_ROLE_ITERATIVE_REVIEWER_ID));
        }else {
            resource.setResourceRole(new ResourceRole(ResourceRole.RESOURCE_ROLE_REVIEWER_ID));
        }

        resource.setProperty(RESOURCE_INFO_HANDLE, userService.getUserHandle(userId));
        resource.setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, String.valueOf(userId));
        resource.setUserId(userId);

        if(contestId > 0) {
            resource.setProject(contestId);
        }

        if (phaseId > 0) {
            resource.setPhase(phaseId);
        }

        if(!hasPayment) {
            resource.setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
            resource.setProperty(RESOURCE_INFO_PAYMENT, RESOURCE_INFO_PAYMENT_NA);
            resource.setProperty(RESOURCE_INFO_MANUAL_PAYMENT, "true");
        }

        // set registration date to now
        resource.setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));

        return resource;
    }


    /**
     * Creates the approver contest resource with the same information from the provided copilot contest resource.
     *
     * @param copilot the copilot contest resouce.
     * @param projectId the contest id of the resource
     * @return the created approver resource
     * @since 1.9.2
     */
    private com.topcoder.management.resource.Resource createApproverResourceFromCopilot(com.topcoder.management.resource.Resource copilot, long projectId) {
        com.topcoder.management.resource.Resource r = new com.topcoder.management.resource.Resource();
        // approval role
        ResourceRole approverRole = new ResourceRole();
        approverRole.setId(ResourceRole.RESOURCE_ROLE_APPROVER_ID);
        r.setResourceRole(approverRole);
        r.setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, copilot.getProperties().get(RESOURCE_INFO_EXTERNAL_REFERENCE_ID));
        r.setUserId(Long.parseLong(copilot.getProperties().get(RESOURCE_INFO_EXTERNAL_REFERENCE_ID)));
        r.setProperty(RESOURCE_INFO_HANDLE, copilot.getProperties().get(RESOURCE_INFO_HANDLE));
        r.setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
        r.setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));
        if(projectId > 0) {
            r.setProject(projectId);
        }
        return r;
    }


    /**
     * Deletes the specified contest, it marks the contest status to "Deleted". User can only delete the contests
     * with status:Draft. If the contest is not draft, it won't be deleted.
     *
     * @param tcSubject the tcsubject instance.
     * @param tcDirectProjectId the id of the direct project
     * @param softwareContestIds the ids of the contests to delete
     * @return the deleted contests
     * @throws ContestServiceException if any error related to contest service facade.
     * @throws PermissionServiceException if any error related to permission service facade.
     * @since 1.9
     */
    public List<SoftwareCompetition> deleteSoftwareContests(TCSubject tcSubject, long tcDirectProjectId,
                                                            List<Long> softwareContestIds)
            throws ContestServiceException, PermissionServiceException {

        logger.debug("deleteSoftwareContests with information : [tcSubject = " + tcSubject.getUserId() + ", tcDirectProjectId ="
                + tcDirectProjectId + ", softwareContestIds = " + softwareContestIds.toString() + "]");

        try {
            List<SoftwareCompetition> softwareContests = new ArrayList<SoftwareCompetition>();

            for (Long contestId : softwareContestIds) {
                softwareContests.add(getSoftwareContestByProjectId(tcSubject, contestId));
            }

            List<SoftwareCompetition> updatedSoftwareContests = new ArrayList<SoftwareCompetition>();

            // check if the contest is draft
            for (SoftwareCompetition contest : softwareContests) {

                // if the contest to delete is draft, mark it as status deleted. Do not delete non-draft contests
                if (contest.getProjectHeader().getProjectStatus().getId() == CONTEST_DRAFT_STATUS_ID) {
                    // update the contest status
                    contest.getProjectHeader().setProjectStatus(ProjectStatus.DELETED);
                    contest.setProjectHeaderReason("User:" + tcSubject.getUserId() + " deletes the contest via batch edit for direct project:" + tcDirectProjectId);
                    final SoftwareCompetition updatedContest = updateSoftwareContest(tcSubject, contest, tcDirectProjectId);
                    updatedSoftwareContests.add(updatedContest);
                }
            }

            return updatedSoftwareContests;
        } catch (ContestServiceException e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade # deleteSoftwareContests", e);
            throw e;
        } catch (PermissionServiceException e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade # deleteSoftwareContests", e);
            throw e;
        } finally {
            logger.debug("Exit deleteSoftwareContests");
        }
    }

    /**
     * Updates all the passed in draft <code>SoftwareCompetition</code> in one trasaction. If there is <code>SoftwareCompetition</code>
     * not in draft status, it won't be updated.
     *
     * @param tcSubject the tcSubject instance.
     * @param tcDirectProjectId the id of the tc direct project.
     * @param draftContests a list of <code>SoftwareCompetition</code> to update.
     * @return the list of updated <code>SoftwareCompetition</code>
     * @throws ContestServiceException if any error related to contest service facade.
     * @throws PermissionServiceException if any error related to permission service facade.
     * @since 1.9
     */
    public List<SoftwareCompetition> batchUpdateDraftSoftwareContests(TCSubject tcSubject, long tcDirectProjectId,
                                                                      List<SoftwareCompetition> draftContests)
            throws ContestServiceException, PermissionServiceException {

        logger.debug("batchUpdateDraftSoftwareContests with information : [tcSubject = " + tcSubject.getUserId() + ", tcDirectProjectId ="
                + tcDirectProjectId + ", draftContests size: = " + draftContests.size() + "]");

        try {

            List<SoftwareCompetition> result = new ArrayList<SoftwareCompetition>();

            for (SoftwareCompetition draftContest : draftContests) {
                // only update the draft contest
                if (draftContest.getProjectHeader().getProjectStatus().getId() == CONTEST_DRAFT_STATUS_ID) {
                    result.add(updateSoftwareContest(tcSubject, draftContest, tcDirectProjectId));
                }
            }

            return result;

        } catch (ContestServiceException cse) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade # batchUpdateDraftSoftwareContests", cse);
            throw cse;
        } catch (PermissionServiceException pse) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade # batchUpdateDraftSoftwareContests", pse);
            throw pse;
        } finally {
            logger.debug("Exit batchUpdateDraftSoftwareContests");
        }
    }

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
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public SoftwareCompetition updateSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
            long tcDirectProjectId) throws ContestServiceException, PermissionServiceException {
        return updateSoftwareContest(tcSubject, contest, tcDirectProjectId, null, null);
    }

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
     *
     * <p>
     * Update in 1.9.2: Update to support add copilot as the approver of the contest when updating the contest.
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contest the <code>SoftwareCompetition</code> to update as a contest
     * @param tcDirectProjectId the TC direct project id.
     * @param multiRoundEndDate the end date for the multiround phase. No multiround if it's null.
     * @param endDate the end date for submission phase. Can be null if to use default.
     * @throws IllegalArgumentException if the input argument is invalid.
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since 1.6.6
     */
    public SoftwareCompetition updateSoftwareContest(TCSubject tcSubject, SoftwareCompetition contest,
            long tcDirectProjectId, Date multiRoundEndDate, Date endDate) throws ContestServiceException, PermissionServiceException {
        logger.debug("updateSoftwareContest");

        try {
            XMLGregorianCalendar productionDate = null;

            if (contest.getAssetDTO() != null) {
                // product date is used to pass the project start date
                // bcoz we need to use XMLGregorianCalendar and project start
                // date
                // is Date and since it is not DTO and hard to change, we use
                // product date for now, but we need to set it null so it will
                // not
                // saved in catalog
                productionDate = contest.getAssetDTO().getProductionDate();
                contest.getAssetDTO().setProductionDate(null);

                // TODO: for some reason, versionid is not passed
                contest.getAssetDTO().setCompVersionId(contest.getAssetDTO().getVersionNumber());
                contest.setAssetDTO(this.catalogService.updateAsset(contest.getAssetDTO()));
            }

            String oldCMCTaskId = null;
            String cmcTaskId = null;
            Date oldStartDate = null;
            Date oldCheckpointEndDate = null;
            Date oldEndDate = null;

            if (contest.getProjectHeader() != null) {

                cmcTaskId = contest.getProjectHeader().getProperty(
                        ProjectPropertyType.CLOUDSPOKES_CMC_TASK_PROPERTY_KEY);

                FullProjectData oldProjectData = projectServices.getFullProjectData(
                                contest.getProjectHeader().getId());

                Project oldProject = oldProjectData.getProjectHeader();

                if(StringUtils.isNotEmpty(cmcTaskId)) {

                    oldStartDate = oldProjectData.getStartDate();

                    com.topcoder.project.phases.Phase[] oldPhases = oldProjectData.getAllPhases();

                    for(com.topcoder.project.phases.Phase oldP : oldPhases) {
                        if(oldP.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId()) {
                            oldStartDate = oldP.getScheduledStartDate();
                        }
                        if(oldP.getPhaseType().getId() == PhaseType.CHECKPOINT_SUBMISSION_PHASE.getId()) {
                            oldCheckpointEndDate = oldP.getScheduledEndDate();
                        }
                        if(oldP.getPhaseType().getId() == PhaseType.SUBMISSION_PHASE.getId()) {
                            oldEndDate = oldP.getScheduledEndDate();
                        }
                    }

                    // get the old cmcTaskID
                    oldCMCTaskId = oldProject.getProperty(ProjectPropertyType.CLOUDSPOKES_CMC_TASK_PROPERTY_KEY);
                }

                // check the contest status - we do not accept change a active contet back to draft by the direct front end
                if(oldProject.getProjectStatus().getId() == ProjectStatus.ACTIVE.getId()
                        && contest.getProjectHeader().getProjectStatus().getId() == ProjectStatus.DRAFT.getId()) {
                    // old is active, the new value is draft, not allow, set back to active
                    contest.getProjectHeader().setProjectStatus(ProjectStatus.ACTIVE);

                    // keep the auto pilot property
                    contest.getProjectHeader().setProperty(ProjectPropertyType.AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY,
                            oldProject.getProperty(ProjectPropertyType.AUTOPILOT_OPTION_PROJECT_PROPERTY_KEY));
                }

                // check the permissions
                checkSoftwareContestPermission(tcSubject, contest.getProjectHeader().getId(), false);
                //checkBillingProjectPermission(tcSubject, contest);
                
                //check billing cca
                checkBillingProjectCCA(contest);
                
                Set<com.topcoder.project.phases.Phase> phaseset = contest.getProjectPhases().getPhases();
                com.topcoder.project.phases.Phase[] phases =  phaseset.toArray(new com.topcoder.project.phases.Phase[phaseset.size()]);

                // add back project on phase
                for (int i = 0; i < phases.length; i++) {
                    phases[i].setProject(contest.getProjectPhases());
                }

                contest.getProjectPhases().setStartDate(getDate(productionDate));

                contest.getProjectHeader()
                       .setTcDirectProjectId(tcDirectProjectId);

                // update name in project info in case name is changed.
                contest.getProjectHeader().setProperty(ProjectPropertyType.PROJECT_NAME_PROJECT_PROPERTY_KEY, contest.getAssetDTO().getName());

                long billingProjectId = getBillingProjectId(contest);

				/*if (billingProjectId > 0) {
					// check billing account
					checkContestBillingAccount(billingProjectId, contest.getProjectHeader().getTcDirectProjectId());
				}*/

                // dont send wiiner email for private
                if (contest.getProjectHeader().getSecurityGroupId() > 0)
                {
                    contest.getProjectHeader().setProperty(ProjectPropertyType.SEND_WINNDER_EMAILS_PROJECT_PROPERTY_KEY, "false");
                    contest.getProjectHeader().setProperty(ProjectPropertyType.SVN_MODULE_PROJECT_PROPERTY_KEY, "");

                }
                else
                {
                    contest.getProjectHeader().setProperty(ProjectPropertyType.SEND_WINNDER_EMAILS_PROJECT_PROPERTY_KEY, "true");
                    boolean isDevContest = isDevContest(contest);

                    if (isDevContest && billingProjectId > 0)
                    {

                        String codename = billingProjectDAO.getProjectById(billingProjectId).getClient().getCodeName();
                        String compname = contest.getAssetDTO().getName();
                        if (codename != null && !codename.equals(""))
                        {
							codename = CodeNameUtil.decodeCodeName(codename, "users");
                            compname = compname.toLowerCase().replaceAll(" ", "_").replaceAll("/", "_").replaceAll("&", "_");
                            codename = codename.toLowerCase().replaceAll(" ", "");
                            contest.getProjectHeader().setProperty(ProjectPropertyType.SVN_MODULE_PROJECT_PROPERTY_KEY, "https://coder.topcoder.com/tcs/clients/"+codename+"/components/"+compname+"/trunk");
                        }
                        else
                        {
                            contest.getProjectHeader().setProperty(ProjectPropertyType.SVN_MODULE_PROJECT_PROPERTY_KEY, "");
                        }
                    }
                }

				if (isCopilotContest(contest)) {
					contest.getProjectHeader().setProperty(ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY, "Off");
					contest.getProjectHeader().setProperty(ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY, "0");
					contest.getProjectHeader().setProperty(ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY, "No");
					contest.getProjectHeader().setProperty(ProjectPropertyType.CONFIDENTIALITY_TYPE_PROJECT_PROPERTY_KEY, "standard_cca");
					contest.getProjectHeader().setProperty(ProjectPropertyType.RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY, "false");
					contest.getProjectHeader().setProperty(ProjectPropertyType.TRACK_LATE_DELIVERABLES_PROJECT_PROPERTY_KEY, "false");
				}
				
				if (contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.BUG_HUNT.getId())
				{
					contest.getProjectHeader().setProperty(ProjectPropertyType.RELIABILITY_BONUS_ELIGIBLE_PROJECT_PROPERTY_KEY, "false");
				}

				if (isStudio(contest)) {
					contest.getProjectHeader().setProperty(ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY, "No");
					contest.getProjectHeader().setProperty(ProjectPropertyType.TRACK_LATE_DELIVERABLES_PROJECT_PROPERTY_KEY, "false");
				}

				if (contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.REPORTING.getId() ||
					   contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.BUG_HUNT.getId())
				{
					contest.getProjectHeader().setProperty(ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY, "Off");
					contest.getProjectHeader().setProperty(ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY, "0");
					contest.getProjectHeader().setProperty(ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY, "No");
				}

                if (isTask(contest)) {
                    contest.getProjectHeader().setProperty(ProjectPropertyType.RATED_PROJECT_PROPERTY_KEY, "No");
                }


//				if (contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.CONTENT_CREATION.getId())
//				{
//					contest.getProjectHeader().setProperty(ProjectPropertyType.DIGITAL_RRUN_FLAG_PROJECT_PROPERTY_KEY, "Off");
//					contest.getProjectHeader().setProperty(ProjectPropertyType.DR_POINTS_PROJECT_PROPERTY_KEY, "0");
//				}

                //preserve the information before update
                boolean isTcDirectProjectChanged = (contest.getProjectHeader().getTcDirectProjectId() != this.projectServices.getTcDirectProject(contest.getId()));

                //get old copilots before update
                com.topcoder.management.resource.Resource[] oldCopilots = projectServices.searchResources(contest.getProjectHeader().getId(), ResourceRole.RESOURCE_ROLE_COPILOT_ID);

                //get old approvers before update
                com.topcoder.management.resource.Resource[] oldApprovers = projectServices.searchResources(contest.getProjectHeader().getId(), ResourceRole.RESOURCE_ROLE_APPROVER_ID);

                List<com.topcoder.management.resource.Resource> updatedResources = new ArrayList<com.topcoder.management.resource.Resource>();
                Set<String> updatedCopilots = new HashSet<String>();

                for(com.topcoder.management.resource.Resource r : contest.getProjectResources()) {
                    if(r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID) {
                        updatedCopilots.add(r.getProperties().get(RESOURCE_INFO_EXTERNAL_REFERENCE_ID));
                    }
                }

                for(com.topcoder.management.resource.Resource r : contest.getProjectResources()) {
                    if(r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID) {
                        // check if current copilot has corresponding approver resource created
                        boolean hasApproverAdded = false;
                        for(com.topcoder.management.resource.Resource oldApprover : oldApprovers) {
                            if (oldApprover.getProperties().get(RESOURCE_INFO_EXTERNAL_REFERENCE_ID).equals(r.getProperties().get(RESOURCE_INFO_EXTERNAL_REFERENCE_ID))) {
                                hasApproverAdded = true;
                            }
                        }
                        if (!hasApproverAdded) {
                            // no added as approver, add it now
                            updatedResources.add(createApproverResourceFromCopilot(r, r.getProject()));
                        }
                    }

                    if(r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_APPROVER_ID) {
                        // check if the old approver needs to be removed
                        boolean needApproverRemoved = false;
                        for(com.topcoder.management.resource.Resource oldCopilot : oldCopilots) {
                            if (oldCopilot.getProperties().get(RESOURCE_INFO_EXTERNAL_REFERENCE_ID).equals(r.getProperties().get(RESOURCE_INFO_EXTERNAL_REFERENCE_ID))) {
                                needApproverRemoved = true;
                            }
                        }

                        // check if the old copilot is not in the new copilot list
                        if(needApproverRemoved && !updatedCopilots.contains(r.getProperties().get(RESOURCE_INFO_EXTERNAL_REFERENCE_ID))) {
                            continue;
                        }
                    }
//                    if (r.getResourceRole().getId() != ResourceRole.RESOURCE_ROLE_SUBMITTER) {
                        updatedResources.add(r);
//                    }
                }

                if(isF2FContest(contest)) {
                    // check if need to auto create reviewer for F2F contest
                    com.topcoder.management.resource.Resource[] oldReviewers= projectServices.searchResources(contest.getProjectHeader().getId(), ResourceRole.RESOURCE_ROLE_ITERATIVE_REVIEWER_ID);

                    if(oldReviewers != null && oldReviewers.length == 0 && contest.getProjectHeader().getAutoAssignReviewerId() > 0) {
                        // no reviewers added and has auto reviewer id > 0 set, auto add iterative reviewer

                        // 1) found out the iterative review phase
                        com.topcoder.project.phases.Phase iterativeReviewPhase = null;
                        for(com.topcoder.project.phases.Phase p : contest.getProjectPhases().getAllPhases()) {
                            if(p.getPhaseType().getId() == PhaseType.ITERATIVE_REVIEW_PHASE.getId()) {
                                iterativeReviewPhase = p;
                                break;
                            }
                        }

                        if(iterativeReviewPhase != null) {
                            updatedResources.add(
                                    createIterativeReviewerResource(contest.getProjectHeader().getAutoAssignReviewerId(), contest.getProjectHeader().getId(), iterativeReviewPhase.getId()));
                        }
                    }

                } else if(isCodeContest(contest)) {
                    // check if need to auto create reviewer for Code contest
                    com.topcoder.management.resource.Resource[] oldReviewers= projectServices.searchResources(contest.getProjectHeader().getId(), ResourceRole.RESOURCE_ROLE_REVIEWER_ID);

                    if(oldReviewers != null && oldReviewers.length == 0 && contest.getProjectHeader().getAutoAssignReviewerId() > 0) {

                        // 1) found out the review phase
                        com.topcoder.project.phases.Phase reviewPhase = null;
                        for(com.topcoder.project.phases.Phase p : contest.getProjectPhases().getAllPhases()) {
                            if(p.getPhaseType().getId() == PhaseType.REVIEW_PHASE.getId()) {
                                reviewPhase = p;
                                break;
                            }
                        }

                        if(reviewPhase != null) {
                            // no reviewers added and has auto reviewer id > 0 set, auto add reviewer without any payment

                            updatedResources.add(
                                    createReviewerResource(contest.getProjectHeader().getAutoAssignReviewerId(),
                                            contest.getProjectHeader().getId(), reviewPhase.getId(), false));

                            // no review payment
                            contest.getProjectHeader().setProperty(
                                    ProjectPropertyType.REVIEW_COSTS_PROJECT_PROPERTY_KEY, "0");
                            reviewPhase.setAttribute("Reviewer Number", "1");
                        }
                    }
                }

                FullProjectData projectData = projectServices.updateProject(contest.getProjectHeader(),
                        contest.getProjectHeaderReason(),
                        contest.getProjectPhases(),
                        updatedResources.toArray(
                                new com.topcoder.management.resource.Resource[updatedResources.size()]),
                        multiRoundEndDate,
                        endDate,
                        String.valueOf(tcSubject.getUserId()));

                //update project observer resources if tcDirectProject has been changed
                if(isTcDirectProjectChanged) {
                   updateContestObserversFromDirectProject(tcSubject, contest);
                }

                // TCCC-1438 - it's better to refetch from backend.
                projectData.setContestSales(projectServices.getContestSales(projectData.getProjectHeader().getId()));

                contest.setProjectHeader(projectData.getProjectHeader());
                contest.setProjectPhases(projectData);
                contest.setProjectResources(projectData.getResources());
                contest.setProjectData(projectData);
                contest.setId(projectData.getProjectHeader().getId());

                // update contest forum
		        long forumId = projectServices.getForumId(projectData.getProjectHeader().getId());
                boolean forumTypeExists = oldProjectData.getProjectHeader().getAllProperties().containsKey(
                        ProjectPropertyType.FORUM_TYPE);

                
                if (forumId > 0 && createForum && !isStudio(contest))
                {
                    updateForumName(forumId, contest.getAssetDTO().getName());

                    // update forum permission for copilots
                    List<String> currentCopilots = new ArrayList<String>();

                    for(com.topcoder.management.resource.Resource r : contest.getProjectResources()) {
                        // get updated copilots from project resources
                        if (r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID) {
                            currentCopilots.add(r.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID));
                        }
                    }

                    // remove copilot forum watch/permission for all old copilots
                    for(com.topcoder.management.resource.Resource r : oldCopilots) {
                            deleteSoftwareForumWatchAndRole(forumId, Long.parseLong(r.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID)));
                    }

                    // insert copilot forum watch/permission for all new copilots
                    for(String copilotId : currentCopilots) {
                            createSoftwareForumWatchAndRole(forumId, Long.parseLong(copilotId), true);
                    }


                } else if (forumId > 0 && createForum && isStudio(contest))
                {
                    //updateForumName(forumId, contest.getAssetDTO().getName());

                    // update forum permission for copilots
                    List<String> currentCopilots = new ArrayList<String>();

                    for(com.topcoder.management.resource.Resource r : contest.getProjectResources()) {
                        // get updated copilots from project resources
                        if (r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID) {
                            currentCopilots.add(r.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID));
                        }
                    }

                    // remove copilot forum watch/permission for all old copilots
                    for(com.topcoder.management.resource.Resource r : oldCopilots) {
                            deleteStudioForumWatchAndRole(forumId, Long.parseLong(r.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID)), forumTypeExists);
                    }

                    // insert copilot forum watch/permission for all new copilots
                    for(String copilotId : currentCopilots) {
                            createStudioForumWatchAndRole(forumId, Long.parseLong(copilotId), true, forumTypeExists);
                    }


                }

                com.topcoder.project.phases.Phase[] allPhases = projectData.getAllPhases();

                // this is to avoid cycle
                for (int i = 0; i < allPhases.length; i++) {
                    allPhases[i].setProject(null);
                    allPhases[i].clearDependencies();
                }

                // billing projct can change, set or unset
                // so for now easy way is removing current, and add if any
                List<ContestEligibility> contestEligibilities =
                 contestEligibilityManager.getContestEligibility(contest.getProjectHeader().getId(), false);
                for (ContestEligibility ce:contestEligibilities){
                    contestEligibilityManager.remove(ce);
                }

                if (billingProjectId > 0) {
                    persistContestEligility(contest.getProjectHeader(), null, false);
                }
            }

             Date startDate = contest.getProjectPhases().getStartDate();
                for (com.topcoder.project.phases.Phase p : contest.getProjectPhases().getPhases())
                {
                    if (p.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId())
                    {
                        startDate = p.getFixedStartDate();
                        break;
                    }
                 }


            // set project start date in production date
            contest.getAssetDTO()
                   .setProductionDate(getXMLGregorianCalendar(startDate));

            // need to remove loops before returning
            removeDocumentationLoops(contest);

            // update the contest / direct project milestone xref if needed
            long directProjectMilestoneId = projectServices.getProjectMilestoneRelation(
                    contest.getProjectHeader().getId());

            if (directProjectMilestoneId <= 0) {
                // has no exiting xref, see if we need to add one
                if (contest.getDirectProjectMilestoneId() > 0) {
                    projectServices.createProjectMilestoneRelation(contest.getProjectHeader().getId(),
                                                                   contest.getDirectProjectMilestoneId(),
                                                                   String.valueOf(tcSubject.getUserId()));

                }
            } else if (directProjectMilestoneId != contest.getDirectProjectMilestoneId()) {
                // changed, let's do the update
                if(contest.getDirectProjectMilestoneId() > 0) {
                    // change to another milestone, do the update
                    projectServices.updateProjectMilestoneRelation(contest.getProjectHeader().getId(),
                            contest.getDirectProjectMilestoneId(),
                            String.valueOf(tcSubject.getUserId()));
                } else {
                    // change to empty, delete the milestone
                    projectServices.deleteProjectMilestoneRelation(contest.getProjectHeader().getId(),
                            String.valueOf(tcSubject.getUserId()));
                }
            }


            // publish event to AWS SNS if needed

            try {

                String apiURL = "";

                if(isStudio(contest)) {
                    apiURL = "http://api.topcoder.com/v2/design/challenges/" + String.valueOf(contest.getProjectHeader().getId());
                } else {
                    apiURL = "http://api.topcoder.com/v2/develop/challenges/" + String.valueOf(contest.getProjectHeader().getId());
                }

                if (StringUtils.isNotEmpty(cmcTaskId) &&
                        (oldCMCTaskId == null ||
                                (oldStartDate != null && startDate != null && !oldStartDate.equals(startDate)) ||
                                (oldCheckpointEndDate != null && multiRoundEndDate != null &&
                                        !oldCheckpointEndDate.equals(multiRoundEndDate)) ||
                                (oldEndDate != null && endDate != null && !oldEndDate.equals(endDate))
                        )) {
                    // update from none -> CMC Task ID, sent creation notification
                    String contestCreationMessage = contestCreationMessageTemplate.
                            replace("%CHALLENGE_ID%", String.valueOf(contest.getProjectHeader().getId())).
                            replace("%CMC_TASK_ID%", cmcTaskId).
                            replace("%API_URL%", apiURL);

                    String subject = contestCreationSubject.replace("%CMC_TASK_ID%", cmcTaskId);

                    PublishRequest request = new PublishRequest().
                            withSubject(subject).
                            withMessage(contestCreationMessage).
                            withTopicArn(contestCreationArn);

                    PublishResult result = amazonSNSClient.publish(request);
                    logger.debug("Publish amazon SNS successfully, the published message id is "
                            + result.getMessageId());
                }

                if (StringUtils.isNotEmpty(oldCMCTaskId) && StringUtils.isNotEmpty(cmcTaskId) &&
                        !oldCMCTaskId.equals(cmcTaskId)) {
                    // the old and new cmc Task id are not empty && the task ID is changed, sent change notification
                    // update from none -> CMC Task ID, sent creation notification
                    String contestUpdateMessage = contestUpdateMessageTemplate.
                            replace("%CHALLENGE_ID%", String.valueOf(contest.getProjectHeader().getId())).
                            replace("%CMC_TASK_ID%", cmcTaskId).
                            replace("%OLD_CMC_TASK_ID%", oldCMCTaskId).
                            replace("%API_URL%", apiURL);

                    String subject = contestUpdateSubject.replace("%CMC_TASK_ID%", cmcTaskId);

                    PublishRequest request = new PublishRequest().
                            withSubject(subject).
                            withMessage(contestUpdateMessage).
                            withTopicArn(contestCreationArn);

                    PublishResult result = amazonSNSClient.publish(request);
                    logger.debug("Publish amazon SNS successfully, the published message id is "
                            + result.getMessageId());
                }


            } catch (Exception e) {
                // we will only log publish error here, won't rollback contest creation.
                logger.error("Operation failed in publish amazon SNS.", e);
            }

            if (StringUtils.isNotEmpty(oldCMCTaskId) && !StringUtils.isNotEmpty(cmcTaskId)) {
                throw new ContestServiceException("You cannot delete CMC Task ID of a created challenge");
            }

            logger.debug("Exit updateSoftwareContest");

            return contest;
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } catch (ProjectServicesException e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } catch (EntityNotFoundException e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        }
    }

    /**
     * Update pre-register users
     *
     *
     * @param tcSubject
     * @param contest
     * @param preRegisterMembers
     * @return successfully added user
     * @throws ContestServiceException
     * @since 3.3
     */
    public Set<Long> updatePreRegister(TCSubject tcSubject, SoftwareCompetition contest,
                                          Set<Long> preRegisterMembers) throws ContestServiceException
    {

        long forumId;
        String userRoleId = "";
        String moderatorRoleId = "";
        Forums forum = null;
        try {
            com.topcoder.management.resource.Resource[] regs = this.projectServices.searchResources(contest.getId(),
                    ResourceRole.RESOURCE_ROLE_SUBMITTER);

            Set<Long> removedUsers = new HashSet<Long>();
            Set<Long> addedUsers = new HashSet<Long>();
            for (com.topcoder.management.resource.Resource r : regs){
                if (!preRegisterMembers.contains(r.getUserId())){
                    removedUsers.add(r.getUserId());
                } else{
                    preRegisterMembers.remove(r.getUserId());
                    addedUsers.add(r.getUserId());
                }
            }

            removedUsers = uploadExternalServices.removeSubmitters(contest.getId(), removedUsers,
                               String.valueOf(tcSubject.getUserId()));
            //remove forum
            if (createForum) {
                try {
                    forumId = contest.getAssetDTO().getForum().getJiveCategoryId();
                    forum = getSoftwareForums();
                    userRoleId = SOFTWARE_USER_FORUM_ROLE_PREFIX + forumId;
                    moderatorRoleId = SOFTWARE_MODERATOR_FORUM_ROLE_PREFIX + forumId;
                    for (Long userId : removedUsers) {
                        forum.removeRole(userId, userRoleId);
                        forum.removeRole(userId, moderatorRoleId);
                        forum.removeUserPermission(userId, forumId);
                    }
                } catch (Exception e) {
                    logger.error("Failed to remove user from forum", e);
                }
            }

            for (Long member : preRegisterMembers) {
                try {
                    this.addSubmitter(tcSubject, contest.getId(), member);
                    if (createForum) {
                        forum.assignRole(member, userRoleId);
                    }
                    addedUsers.add(member);
                } catch (EJBException e) {
                    logger.error("Failed to assign forum role for user: " + member, e);
                } catch (Exception e) {
                    logger.error("Failed to add pre-register member: " + member, e);
                }
            }
            return addedUsers;
        } catch (Exception e){
            throw new ContestServiceException("Failed to pre-register user", e);
        }
    }


    /**
     * Checks whether the contest has project info FORUM TYPE
     *
     * @param contest the contest to check
     * @return true if exists, false otherwise
     * @since 3.1
     */
    private boolean hasForumType(Project contest) {
        return contest.getAllProperties().containsKey(ProjectPropertyType.FORUM_TYPE);
    }

    /**
     * <p>
     * Updates contest observer resources from direct project if the direct project associated is changed.
     * </p>
     *
     * @param tcSubject the current user
     * @param contest the contest to be updated
     * @throws Exception  if any error occurs
     */
    private void updateContestObserversFromDirectProject(TCSubject tcSubject, SoftwareCompetition contest) throws Exception {
        long forumId = projectServices.getForumId(contest.getId());

       // Remove all current observers
        for(com.topcoder.management.resource.Resource resource : this.projectServices.searchResources(contest.getId(), ResourceRole.RESOURCE_ROLE_OBSERVER_ID)) {
            deleteForumWatchAndRoleForResource(forumId, resource, contest, hasForumType(contest.getProjectHeader()));
            this.projectServices.removeResource(resource, String.valueOf(tcSubject.getUserId()));

        }

        // Add observers
        // add users has permission on cockpit project as observers
        List<Permission> permissions = this.permissionService.getPermissionsByProject(contest.getProjectHeader().getTcDirectProjectId());

        ResourceRole observerRole = new ResourceRole();
        observerRole.setId(ResourceRole.RESOURCE_ROLE_OBSERVER_ID);
        observerRole.setName(ResourceRole.RESOURCE_ROLE_OBSERVER_NAME);
        observerRole.setDescription(ResourceRole.RESOURCE_ROLE_OBSERVER_DESC);

        for (Permission p : permissions) {
                com.topcoder.management.resource.Resource r = new com.topcoder.management.resource.Resource();
                r.setResourceRole(observerRole);
                r.setProject(contest.getId());
                r.setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, String.valueOf(p.getUserId()));
                r.setProperty(RESOURCE_INFO_HANDLE, p.getUserHandle());
                r.setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
                r.setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));
                r.setUserId(p.getUserId());
                this.projectServices.updateResource(r, String.valueOf(tcSubject.getUserId()));
                addForumWatchAndRoleForResource(forumId, r, contest, tcSubject, hasForumType(contest.getProjectHeader()));
        }
    }

    /**
     * <p>
     *  Deletes forum and role for the resource.
     *  </p>
     *
     * @param forumId the forum id
     * @param r the resource
     * @param contest the contest data
     * @param isNewForum whether the forum is ported from studio to software
     */
    private void deleteForumWatchAndRoleForResource(long forumId, com.topcoder.management.resource.Resource r,
        SoftwareCompetition contest, boolean isNewForum) {
        if (forumId <= 0 || !createForum) {
            return;
        }

        long userId = Long.parseLong(r.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID));
        if (!isStudio(contest)) {
            deleteSoftwareForumWatchAndRole(forumId, userId);
        } else {
            deleteStudioForumWatchAndRole(forumId, userId, isNewForum);
        }
    }

    /**
     * <p>
     *  Add forum and role for the resource and also notification day.
     *  </p>
     *
     * @param forumId the forum id
     * @param r the resource
     * @param contest the contest data
     * @param isNewForum whether the forum is ported from studio to software
     */
    private void addForumWatchAndRoleForResource(long forumId, com.topcoder.management.resource.Resource r,
        SoftwareCompetition contest, TCSubject tcSubject, boolean isNewForum) throws Exception {
        if (forumId <= 0 || !createForum) {
            return;
        }

        long userId = Long.parseLong(r.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID));
        long roleId = r.getResourceRole().getId();
        boolean addNotification;
        boolean addForumWatch;

        List<Integer> preferenceIds = new ArrayList<Integer>();
        // notification preference
        preferenceIds.add(GLOBAL_TIMELINE_NOTIFICATION);
        // forum preference
        preferenceIds.add(GLOBAL_FORUM_WATCH);

        Map<Integer, String> preferences = getUserPreferenceMaps(userId, preferenceIds);

        addNotification = Boolean.parseBoolean(preferences.get(GLOBAL_TIMELINE_NOTIFICATION));
        addForumWatch = Boolean.parseBoolean(preferences.get(GLOBAL_FORUM_WATCH));

        if (!isStudio(contest)) {
            createSoftwareForumWatchAndRole(forumId, userId, addForumWatch);
        } else {
            createStudioForumWatchAndRole(forumId, userId, addForumWatch, isNewForum);
        }

        if (roleId != ResourceRole.RESOURCE_ROLE_OBSERVER_ID || addNotification)
        {
            // set timeline notification
            projectServices.addNotifications(userId, new long[]{contest.getId()}, String.valueOf(tcSubject.getUserId()));
        }
    }

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     * <p>
     * If the project allows multiple submissions for users, it will add the new submission and return. If multiple
     * submission are not allowed for the project, firstly it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project's id
     * @param filename the file name to use
     * @param submission the submission file data
     * @return the id of the new submission
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadSubmission(TCSubject tcSubject,long projectId, String filename,
                                 DataHandler submission) throws ContestServiceException{
        return uploadSubmission(tcSubject.getUserId(), projectId, filename, submission);
    }

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
    public long uploadSubmission(long userId, long projectId, String filename, DataHandler submission)
            throws ContestServiceException {
        logger.debug("uploadSubmission");

        try {
            logger.debug("Exit updateSoftwareContest");

            return uploadExternalServices.uploadSubmission(projectId,
                    userId, filename, submission);
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        } catch (RemoteException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.",
                e);
        }
    }

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This submission always overwrite the previous
     * ones.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project's id
     * @param filename the file name to use
     * @param finalFix the final fix file data
     * @return the id of the created final fix submission
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadFinalFix(TCSubject tcSubject, long projectId, String filename, DataHandler finalFix)
            throws ContestServiceException {
        logger.debug("uploadFinalFix");

        try {
            logger.debug("Exit uploadFinalFix");

            return uploadExternalServices.uploadFinalFix(projectId, tcSubject.getUserId(), filename, finalFix);
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        } catch (RemoteException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        }
    }

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This submission always overwrite the previous
     * ones.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project's id
     * @param filename the file name to use
     * @param testCases the test cases data
     * @return the id of the created test cases submission
     * @throws IllegalArgumentException if any id is &lt; 0, if any argument is <code>null</code> or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long uploadTestCases(TCSubject tcSubject, long projectId, String filename, DataHandler testCases)
            throws ContestServiceException {
        logger.debug("uploadTestCases");

        try {
            logger.debug("Exit uploadTestCases");

            return uploadExternalServices.uploadTestCases(projectId, tcSubject.getUserId(), filename, testCases);
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        } catch (RemoteException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        }
    }

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param submissionId the submission's id
     * @param submissionStatusId the submission status id
     * @param operator the operator which execute the operation
     * @throws IllegalArgumentException if any id is &lt; 0 or if operator is null or trim to empty
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public void setSubmissionStatus(TCSubject tcSubject, long submissionId, long submissionStatusId, String operator)
            throws ContestServiceException {
        logger.debug("setSubmissionStatus");

        try {
            uploadExternalServices.setSubmissionStatus(submissionId, submissionStatusId, operator);
            logger.debug("Exit setSubmissionStatus");
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        } catch (RemoteException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        }
    }

    /**
     * Adds the given user as a new submitter to the given project id.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project to which the user needs to be added
     * @param userId the user to be added
     * @return the added resource id
     * @throws IllegalArgumentException if any id is &lt; 0
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since TopCoder Service Layer Integration 3 Assembly
     */
    public long addSubmitter(TCSubject tcSubject, long projectId, long userId) throws ContestServiceException {
        logger.debug("AddSubmitter (tcSubject = " + tcSubject.getUserId() + ", " + projectId + "," + userId + ")");

        try {
            return uploadExternalServices.addSubmitter(projectId, userId);
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        } catch (RemoteException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        }
    }

    /**
     * Get the EJB handler for Forum EJB service.
     *
     * @param url the EJB bean url
     * @return the forum EJB service handler.
     * @throws NamingException if a naming exception is encountered.
     * @throws RemoteException if remote error occurs.
     * @throws CreateException if error occurs when creating EJB handler
     *
     * @since 1.6.6
     */
    private Forums getForumsEJBFromJNDI(String url) throws NamingException, CreateException, RemoteException {
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY,
            "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES,
            "org.jboss.naming:org.jnp.interfaces");

        p.put(Context.PROVIDER_URL, url);

        Context c = new InitialContext(p);
        ForumsHome forumsHome = (ForumsHome) c.lookup(ForumsHome.EJB_REF_NAME);

        return forumsHome.create();
    }

    /**
     * Get the Software Forum EJB service for Software competitions.
     *
     * @return the forums EJB service handler.
     * @throws NamingException if a naming exception is encountered.
     * @throws RemoteException if remote error occurs.
     * @throws CreateException if error occurs when creating EJB handler
     *
     * @since 1.6.11
     */
    private Forums getSoftwareForums() throws RemoteException, NamingException, CreateException {
        return getForumsEJBFromJNDI(softwareForumBeanProviderUrl);
    }

    /**
     * Get the Studio Forum EJB service for Studio competitions.
     *
     * @return the forums EJB service handler.
     * @throws NamingException if a naming exception is encountered.
     * @throws RemoteException if remote error occurs.
     * @throws CreateException if error occurs when creating EJB handler
     *
     * @since 1.6.11
     */
    private Forums getStudioForums() throws RemoteException, NamingException, CreateException {
        return getForumsEJBFromJNDI(studioForumBeanProviderUrl);
    }

    /**
     * Create studio forum with given parameters. It will lookup the ForumsHome interface, and create the studio forum
     * by the ejb home interface.
     *
     * @param name the forum name
     * @param userId the user id to user
     * @return the long id of the create fourm
     * @since 1.6.6
     */
    private long createStudioForum(String name, long userId) {
        logger.debug("createStudioForm (name = " + name + ", userId = " + userId + ")");

        try {
            Forums forums = getSoftwareForums();
            long forumId = forums.createStudioForumV2(studioForumRootCategoryId, name);
            if (forumId < 0) {
                throw new Exception("createStudioForum returned negative forum ID: " + forumId);
            }
            forums.createForumWatch(userId, forumId);
            return forumId;
        } catch (Exception e) {
            logger.error("*** Could not create a studio forum for " + name);
            logger.error(e);
        }
        return -1;
    }

    /**
     * create forum with given parameters. It will lookup the ForumsHome interface, and ceate the forum by the ejb home
     * interface. In the old version, this method misses the document, it's added in the version 1.1
     *
     * @param asset The asset DTO to user
     * @param userId userId The user id to use
     * @param projectCategoryId The project category id to
     * @return The long id of the created forum
     */
    public long createForum(TCSubject tcSubject, AssetDTO asset, long userId, long projectCategoryId) {
        long forumId = -1;
        logger.debug("createForum (tcSubject = " + tcSubject.getUserId() + ", " + userId + ")");

        try {
            Forums forums = getSoftwareForums();

            long phaseId = 0;

            try {
                phaseId = Long.parseLong(asset.getPhase());
            } catch (Exception ee) {
            }

            forumId = forums.createSoftwareComponentForums(asset.getName(),
                    asset.getId(), asset.getCompVersionId(), phaseId,
                    Status.REQUESTED.getStatusId(),
                    asset.getRootCategory().getId(),
                    asset.getShortDescription(), asset.getVersionText(), false,
                    projectCategoryId);

            if (forumId < 0) {
                throw new Exception("createStudioForum returned -1");
            }

            logger.error("Created forum " + forumId + " for " +
                asset.getName());

            forums.assignRole(userId, "Software_Moderators_" + forumId); //BUGR-
                                                                         // 1677

            forums.createCategoryWatch(userId, forumId);
            logger.debug("Exit createForum (" + userId + ")");

            return forumId;
        } catch (Exception e) {
            logger.error("*** Could not create a forum for " + asset.getName());
            logger.error(e);

            return forumId;
        }
    }

    /**
     * Close the forum
     *
     * @param forumid The long id of the forum
     */
    public void closeForum(long forumid) {
        long forumId = -1;
        logger.debug("closeForum (forumid = " + forumid + ")");

        try {
            Properties p = new Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES,
                "org.jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, softwareForumBeanProviderUrl);

            Context c = new InitialContext(p);
            ForumsHome forumsHome = (ForumsHome) c.lookup(ForumsHome.EJB_REF_NAME);

            Forums forums = forumsHome.create();

            forums.closeCategory(forumid);

            logger.debug("Exit closeForum (" + forumid + ")");

        } catch (Exception e) {
            logger.error("*** Could not close forum for " + forumId);
            logger.error(e);
        }
    }

    /**
     * <p>
     * Gets the list of all existing contests related to given project for my
     * project widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     *
     * @param pid
     *            given project id
     * @return a <code>List</code> listing all existing contests. Empty list is
     *         returned if there are no contests found.
     * @throws PersistenceException
     *             if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CommonProjectContestData> getCommonProjectContestDataByPID(TCSubject tcSubject, long pid) {
        logger.debug("getCommonProjectContestDataByPID (tcSubject = " + tcSubject.getUserId() + ", " + pid + ")");

        List<CommonProjectContestData> ret = new ArrayList<CommonProjectContestData>();


        for (com.topcoder.management.project.SimpleProjectContestData data : projectServices.getSimpleProjectContestData(
                pid)) {
            CommonProjectContestData newData = new CommonProjectContestData();
            newData.setCname(data.getCname());
            newData.setContestId(data.getContestId());
            newData.setProjectId(data.getProjectId());
            newData.setPname(data.getPname());
            newData.setDescription(data.getDescription());
            newData.setEndDate(getXMLGregorianCalendar(data.getEndDate()));
            newData.setForumId(data.getForumId());
            newData.setNum_for(data.getNum_for());
            newData.setNum_reg(data.getNum_reg());
            newData.setNum_sub(data.getNum_sub());
            newData.setProjectId(data.getProjectId());
            newData.setSname(data.getSname());
            newData.setStartDate(getXMLGregorianCalendar(data.getStartDate()));
            newData.setType(data.getType());
            newData.setCreateUser(data.getCreateUser());
            newData.setPperm(data.getPperm());
            newData.setCperm(data.getCperm());
            newData.setSpecReviewStatus(data.getSpecReviewStatus());
            newData.setSpecReviewProjectId(data.getSpecReviewProjectId());
            newData.setSubmissionEndDate(getXMLGregorianCalendar(data.getSubmissionEndDate()));
            newData.setContestFee(data.getContestFee());
            ret.add(newData);
        }

        // sort/group by project id
        Collections.sort(ret,
            new Comparator() {
                public int compare(Object o1, Object o2) {
                    CommonProjectContestData p1 = (CommonProjectContestData) o1;
                    CommonProjectContestData p2 = (CommonProjectContestData) o2;

                    return p1.getProjectId().compareTo(p2.getProjectId());
                }
            });
        logger.debug("Exit getCommonProjectContestDataByPID (" + pid + ")");

        return ret;
    }

    /**
     * <p>
     * Gets the list of all existing contests for my project widget.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * <p>
     * Updated for v1.6 Direct Search Assembly
     *      - provided contest fee for each contest data
     * </p>
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @return a <code>List</code> listing all existing contests. Empty list is returned if there are no contests found.
     * @throws PersistenceException if any error occurs when getting contest.
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<CommonProjectContestData> getCommonProjectContestData(TCSubject tcSubject) {
        logger.debug("getCommonProjectContestDataByContestData(tcSubject = " + tcSubject.getUserId() + ")");

        List<CommonProjectContestData> ret = new ArrayList<CommonProjectContestData>();

        for (com.topcoder.management.project.SimpleProjectContestData data :
                projectServices.getSimpleProjectContestData(tcSubject)) {
            CommonProjectContestData newData = new CommonProjectContestData();
            newData.setCname(data.getCname());
            newData.setContestId(data.getContestId());
            newData.setProjectId(data.getProjectId());
            newData.setPname(data.getPname());
            newData.setDescription(data.getDescription());
            newData.setEndDate(getXMLGregorianCalendar(data.getEndDate()));
            newData.setForumId(data.getForumId());
            newData.setNum_for(data.getNum_for());
            newData.setNum_reg(data.getNum_reg());
            newData.setNum_sub(data.getNum_sub());
            newData.setProjectId(data.getProjectId());
            newData.setSname(data.getSname());
            newData.setStartDate(getXMLGregorianCalendar(data.getStartDate()));
            newData.setType(data.getType());
            newData.setCreateUser(data.getCreateUser());
            newData.setPperm(data.getPperm());
            newData.setCperm(data.getCperm());
            newData.setSpecReviewStatus(data.getSpecReviewStatus());
            newData.setSpecReviewProjectId(data.getSpecReviewProjectId());
            newData.setSubmissionEndDate(getXMLGregorianCalendar(data.getSubmissionEndDate()));
            newData.setContestFee(data.getContestFee());
            ret.add(newData);
        }

        // sort/group by project id
        Collections.sort(ret,
            new Comparator() {
                public int compare(Object o1, Object o2) {
                    CommonProjectContestData p1 = (CommonProjectContestData) o1;
                    CommonProjectContestData p2 = (CommonProjectContestData) o2;

                    return p1.getProjectId().compareTo(p2.getProjectId());
                }
            });
        logger.debug("Exit getCommonProjectContestDataByContestData");

        return ret;
    }


    /**
     * Gets all project data with aggregated statistics data for each type of contest status.
     *
     * <p>Version 1.7.1 - set project status id into the ProjectSummaryData returned</p>
     *
     * <p>Version 1.7.5 - set project creation date into the ProjectSummaryData returned</p>
     *
     * @param tcSubject <code>TCSubject</code> object
     * @return a list of <code>ProjectSummaryData</code> objects
     *
     * @throws ContestServiceException if any error occurs during processing
     *
     * @since 1.6
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ProjectSummaryData> getProjectData(TCSubject tcSubject) throws ContestServiceException {
        ExceptionUtils.checkNull(tcSubject, null, null, "The tcSubject parameter is null.");

        List<ProjectSummaryData> result = new ArrayList<ProjectSummaryData>();
        Map<Long, ProjectSummaryData> projectDataMap = new HashMap<Long, ProjectSummaryData>();

        try {
            List<ProjectData> projects = new ArrayList<ProjectData>();

            if (isRole(tcSubject, ADMIN_ROLE)) {
                projects = projectService.getAllProjects();
            } else {
                projects = projectService.getProjectsForUser(tcSubject.getUserId());
            }


            for (ProjectData project : projects) {
                ProjectSummaryData data = new ProjectSummaryData();
                data.setProjectId(project.getProjectId());
                data.setProjectName(project.getName());
                data.setDirectProjectStatusId(project.getProjectStatusId());
                data.setProjectCreationDate(project.getCreationDate());
                data.setProjectCompletionDate(project.getCompletionDate());
                data.setDirectProjectType(project.getProjectType());
                data.setDirectProjectCategory(project.getProjectCategory());

                result.add(data);
                projectDataMap.put(data.getProjectId(), data);
            }

            List<CommonProjectContestData> contests;

            contests = getCommonProjectContestData(tcSubject);

            for (CommonProjectContestData contest : contests) {
                ProjectSummaryData data = projectDataMap.get(contest.getProjectId());
                if (data == null) {
                    continue;
                }
                if (DRAFT_STATUS.contains(contest.getSname())) {
                    addToStatusData(data.getDraft(), contest.getContestFee());
                } else if (SCHEDULED_STATUS.contains(contest.getSname())) {
                    addToStatusData(data.getScheduled(), contest.getContestFee());
                } else if (ACTIVE_STATUS.contains(contest.getSname())) {
                    addToStatusData(data.getActive(), contest.getContestFee());
                } else if (FINISHED_STATUS.contains(contest.getSname())) {
                    addToStatusData(data.getFinished(), contest.getContestFee());
                } else if (CANCELLED_STATUS.contains(contest.getSname())) {
                    addToStatusData(data.getCancelled(), contest.getContestFee());
                } else {
                    String infoMsg = "status " + contest.getSname()
                        + " is not recognized as one of Scheduled/Draft/Active/Finished or skipped intentionally";
                    if (logger.isDebugEnabled()) {
                        logger.debug(infoMsg);
                    }
                }
            }

            return result;
        } catch (PersistenceFault e) {
            logger.error("Fail to get project data from project service : " + e.getMessage(), e);
            sessionContext.setRollbackOnly();
            throw new ContestServiceException("Fail to get project data from project service : " + e.getMessage(), e);
        } catch (UserNotFoundFault e) {
            logger.error("Fail to get project data from project service : " + e.getMessage(), e);
            sessionContext.setRollbackOnly();
            throw new ContestServiceException("Fail to get project data from project service : " + e.getMessage(), e);
        } catch (AuthorizationFailedFault e) {
            logger.error("Fail to get project data from project service : " + e.getMessage(), e);
            sessionContext.setRollbackOnly();
            throw new ContestServiceException("Fail to get project data from project service : " + e.getMessage(), e);
        }
    }

    /**
     * Adds the payment into status data.
     *
     * @param data status data to be added on
     * @param payment the new payment
     */
    private void addToStatusData(ProjectStatusData data, Double payment) {
        if (payment == null) {
            payment = ZERO_AMOUNT;
        }

        data.setTotalNumber(data.getTotalNumber() + 1);
        data.setTotalPayment(data.getTotalPayment() + payment);
    }

    /**
     * <p>
     * BURG-1716: We need to add a method to get software contest by project id, the method wil get all OR project
     * related data, then from project property to get comp version id then to call getAssetByVersionId to get assetDTO,
     * please check create software contest to see what data need to be returned.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the OR Project Id
     * @return SoftwareCompetition
     * @throws ContestServiceException if an error occurs when interacting with the service layer.
     * @since BURG-1716
     */
    public SoftwareCompetition getSoftwareContestByProjectId(TCSubject tcSubject, long projectId)
            throws ContestServiceException, PermissionServiceException {
        logger.debug("getSoftwareContestByProjectId (tcSubject = " + tcSubject.getUserId() + ", " + projectId + ")");

        SoftwareCompetition contest = new SoftwareCompetition();

        try {

            checkSoftwareContestPermission(tcSubject, projectId, true);

            FullProjectData fullProjectData = this.projectServices.getFullProjectData(projectId);
            Long compVersionId = Long.parseLong(fullProjectData.getProjectHeader()
                                                               .getProperty(ProjectPropertyType.EXTERNAL_REFERENCE_ID_PROJECT_PROPERTY_KEY));
            contest.setAssetDTO(this.catalogService.getAssetByVersionId(
                    compVersionId));
            contest.setProjectHeader(fullProjectData.getProjectHeader());
            contest.setProjectData(fullProjectData);
            contest.setProjectPhases(fullProjectData);
            contest.getProjectPhases()
                   .setId(fullProjectData.getProjectHeader().getId());
            contest.setId(projectId);
            contest.setProjectResources(fullProjectData.getResources());

            com.topcoder.project.phases.Phase[] allPhases = fullProjectData.getAllPhases();

            Date startDate = contest.getProjectPhases().getStartDate();
            for (com.topcoder.project.phases.Phase p : contest.getProjectPhases().getPhases())
            {
                if (p.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId())
                {
                    startDate = p.getFixedStartDate();
                    break;
                }
             }

            // set project start date in production date
            contest.getAssetDTO()
                   .setProductionDate(getXMLGregorianCalendar(startDate));

            // set null to avoid cycle
            contest.getAssetDTO().setDependencies(null);

            if (contest.getAssetDTO().getForum() != null) {
                contest.getAssetDTO().getForum().setCompVersion(null);
            }

            if (contest.getAssetDTO().getLink() != null) {
                contest.getAssetDTO().getLink().setCompVersion(null);
            }

            // need to remove loops before returning
            removeDocumentationLoops(contest);

            // get the direct project milestone xref
            contest.setDirectProjectMilestoneId(projectServices.getProjectMilestoneRelation(contest.getProjectHeader().getId()));

            // get the security group id if there is any
            List<ContestEligibility> contestEligibilities =
                    contestEligibilityManager.getContestEligibility(contest.getProjectHeader().getId(), false);

            if (contestEligibilities != null && contestEligibilities.size() > 0 && contestEligibilities.get(0) != null
                    && contestEligibilities.get(0) instanceof GroupContestEligibility) {
                contest.getProjectHeader().setSecurityGroupId(((GroupContestEligibility) contestEligibilities.get(0)).getGroupId());
            }

            logger.debug("Exit getSoftwareContestByProjectId (" + projectId +
                ")");

            return contest;
        } catch (ProjectServicesException pse) {
            logger.error("Fail to get project data from project services.", pse);
            sessionContext.setRollbackOnly();
            throw new ContestServiceException("Fail to get project data from project services.",
                pse);
        } catch (NumberFormatException nfe) {
            logger.error("the properites 'Version ID' is not of Long value in project.",
                nfe);
            sessionContext.setRollbackOnly();
            throw new ContestServiceException("the properites 'Version ID' is not of Long value in project.",
                nfe);
        } catch (EntityNotFoundException e) {
            logger.error("the version id does not exist.", e);
            sessionContext.setRollbackOnly();
            throw new ContestServiceException("the version id does not exist.",
                e);
        } catch (com.topcoder.catalog.service.PersistenceException e) {
            logger.error("Fail to get project asset.", e);
            sessionContext.setRollbackOnly();
            throw new ContestServiceException("Fail to get project asset.", e);
        } catch (ContestEligibilityPersistenceException cepe) {
            logger.error("Fail to get contest eligibility.", cepe);
            sessionContext.setRollbackOnly();
            throw new ContestServiceException("Fail to get contest eligibility.", cepe);
        }
    }

    /**
     * <p>
     * Sends the email for specified template to specified to, cc, bcc address
     * from specified from address.
     * </p>
     *
     * @param templateSource
     *            the template source
     * @param templateName
     *            the template name or the file path of the template.
     * @param subject
     *            the email subject line
     * @param toAddr
     *            the to-address of the email
     * @param ccAddr
     *            the from-address of the email
     * @param bccAddr
     *            the bcc-address of the email
     * @param fromAddr
     *            the from-address of the email
     * @param phase
     *            the phase data
     * @throws EmailMessageGenerationException
     *             thrown if error during email generation
     * @throws EmailSendingException
     *             thrown if error during email sending.
     *
     * @since Cockpit Release Assembly for Receipts.
     */
    private void sendEmail(String templateSource, String templateName, String subject, String[] toAddrs, String ccAddr, String bccAddr, String fromAddr, com.topcoder.project.phases.Phase phase)
        throws EmailMessageGenerationException, EmailSendingException {
        boolean messageGenerated = false;

        try {
            // Generate the message body first
            Template template = (templateSource == null)
                ? documentGenerator.getTemplate(templateName)
                : documentGenerator.getTemplate(templateSource, templateName);
            String messageBody = this.emailMessageGenerator.generateMessage(documentGenerator,
                    template, phase);

            logger.debug("Generated following email message of subject [" +
                subject + "] to be sent to [" + fromAddr + "] \n" +
                messageBody);

            // Create a TCSEmailMessage to be sent
            TCSEmailMessage email = new TCSEmailMessage();

            // Set subject, from address and message body.
            email.setSubject(subject);
            email.setFromAddress(fromAddr);
            email.setBody(messageBody);
            email.setContentType("text/html");

            ExceptionUtils.checkNull(toAddrs, null, null, "To address must be non-null.");
            for (String toAddr : toAddrs) {
                email.addToAddress(toAddr, TCSEmailMessage.TO);
            }

            if (ccAddr != null) {
                email.addToAddress(ccAddr, TCSEmailMessage.CC);
            }

            if (bccAddr != null) {
                email.addToAddress(bccAddr, TCSEmailMessage.BCC);
            }

            // Now the email message is generated successfully
            messageGenerated = true;

            // Send email
            EmailEngine.send(email);
            logger.debug("Sent email message of subject [" + subject +
                "] to [" + fromAddr + "]");
        } catch (BaseException e) {
            rethrowEmailError(e, messageGenerated);
        } catch (ConfigManagerException e) {
            rethrowEmailError(e, messageGenerated);
        } catch (IllegalArgumentException e) {
            rethrowEmailError(e, messageGenerated);
        }
    }

    /**
     * <p>
     * Wrap the given error while sending email and re throw it.
     * </p>
     *
     * <p>
     * If given <code>messageGenerated</code> is false, then this error occurs
     * while generating email message and thus an
     * <code>EmailMessageGenerationException</code> will be thrown. Otherwise it
     * means the error occurs while sending email and thus an
     * <code>EmailSendingException</code> will be thrown.
     * </p>
     *
     * @param e
     *            The root error cause to be wrapped and re thrown.
     * @param messageGenerated
     *            Indicates whether the email message has been generated
     *            successfully.
     *
     * @param phase
     * @throws EmailMessageGenerationException
     *             If <code>messageGenerated</code> is false.
     * @throws EmailSendingException
     *             If <code>messageGenerated</code> is true.
     *
     * @since Cockpit Release Assembly for Receipts.
     */
    private void rethrowEmailError(Throwable e, boolean messageGenerated)
        throws EmailMessageGenerationException, EmailSendingException {
        try {
            if (messageGenerated) {
                throw (e instanceof EmailSendingException)
                ? (EmailSendingException) e
                : new EmailSendingException("Error while sending email.", e);
            } else {
                throw (e instanceof EmailMessageGenerationException)
                ? (EmailMessageGenerationException) e
                : new EmailMessageGenerationException("Error while generating email to be sent.",
                    e);
            }
        } catch (Exception e1) {
            logger.error("*** Could not generate or send an email to creator of contest",
                e1);
        }
    }

    /**
     * Creates and sends email for the activate contest receipt email.
     *
     * <p>
     *  Updates in version 1.8.4:
     *  - Add the logic to dynamically load activation email template from the passed in <code>competitionTypeId</code>
     * </p>
     *
     *
     * @param toAddr
     *            the to address for email send.
     * @param purchasedBy
     *            the name of the person who purchased.
     * @param paymentData
     *            the payment data. it is one of TCPurhcaseOrderPaymentData or
     *            CreditCardPaymentData
     * @param competitionType
     *            the competition type, person activated.
     * @param competitionTypeId
     *            the competition type id.
     * @param competitionTitle
     *            the competition title, person activated.
     * @param projectName
     *            the project name, person activated.
     * @param launchTime
     *            the launch of the competition.
     * @param price
     *            the price the person paid
     * @param totalCost
     *            the total price the person paid
     * @param orderNumber
     *            the order number of the purchase.
     * @param contestIndicator
     *            an indicator represents how winner selection will occur.
     *
     * @throws EmailMessageGenerationException
     *             throws if error during email message generation
     * @throws EmailSendingException
     *             throws if error during email sending.
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private void sendActivateContestReceiptEmail(String toAddr,
        String purchasedBy, PaymentData paymentData, String competitionType, long competitionTypeId,
        String competitionTitle, String projectName, Date launchTime,
        Double price, Double totalCost, String orderNumber, boolean hasContestSaleData, String contestIndicator,
        boolean isDesign, long challengeId)
        throws EmailMessageGenerationException, EmailSendingException {
        com.topcoder.project.phases.Phase phase = new com.topcoder.project.phases.Phase();

        setReceiptEmailCommonProperties(phase, purchasedBy, paymentData,
            competitionType, competitionTitle, projectName, isDesign, challengeId);

        phase.setAttribute("LAUNCH_TIME", launchTime);
        phase.setAttribute("CONTEST_COST", price);
        phase.setAttribute("TOTAL_COST", totalCost);

        phase.setAttribute("FROM_ADDRESS", activateContestReceiptEmailFromAddr);
        phase.setAttribute("CONTEST_INDICATOR", contestIndicator);

        if(hasContestSaleData) {
            // if it's paid before, set "paid" to yes
            phase.setAttribute("PAID", "YES");
        } else {
            // if it's not paid before, set "paid" to no
            phase.setAttribute("PAID", "NO");
        }

        // set to default template
        String templateToUse = activateContestReceiptEmailTemplatePath;

        // check if there custom email template path configured for the competitionTypeId
        if(customContestActivationEmailTempaltes.containsKey(competitionTypeId)) {
            templateToUse = customContestActivationEmailTempaltes.get(competitionTypeId);
        }

        String file = Thread.currentThread().getContextClassLoader().getResource(
                templateToUse).getFile();
        Logger.getLogger(this.getClass()).debug("File name for template: " + file);

        sendEmail(EMAIL_FILE_TEMPLATE_SOURCE_KEY, file, activateContestReceiptEmailSubject.replace("%ORDER_NUMBER%",
                orderNumber), new String[] {toAddr}, null, null, activateContestReceiptEmailFromAddr,
                phase);
    }


    /**
     * Helper method to send email to copilot posting winner when the winner is chosen as the copilot.
     *
     * @param copilotUserId the user id of the copilot
     * @param project the direct project
     * @param contest the copilot posting contest
     * @throws UserServiceException if fail to get email address of the copilot
     * @throws EmailMessageGenerationException if fail to generate the content of copilot winner email
     * @throws EmailSendingException i fail to send the copilot winner email
     * @since 1.8.5
     */
    private void sendCopilotWinnerEmail(long copilotUserId, ProjectData project, SoftwareCompetition contest)
            throws UserServiceException, EmailMessageGenerationException, EmailSendingException {
        com.topcoder.project.phases.Phase phase = new com.topcoder.project.phases.Phase();

        // get the copilot's email address
        String toAddr = this.userService.getEmailAddress(copilotUserId);

        // set direct project name
        phase.setAttribute("PROJECT_NAME", project.getName());

        // set the direct project id
        phase.setAttribute("PROJECT_ID", project.getProjectId());

        // set the project forum id
        phase.setAttribute("CATEGORY_ID", project.getForumCategoryId() == null ? "none" : project.getForumCategoryId());

        // set the copilot posting contest name
        phase.setAttribute("COPILOT_POSTING_NAME", contest.getProjectHeader().getProperty("Project Name"));

        String file = Thread.currentThread().getContextClassLoader().getResource(
                copilotWinnerEmailTemplatePath).getFile();
        Logger.getLogger(this.getClass()).debug("File name for template: " + file);

        // send the email
        sendEmail(EMAIL_FILE_TEMPLATE_SOURCE_KEY, file, copilotWinnerEmailSubject.replace("%PROJECT_NAME%",
                project.getName()), new String[]{toAddr}, null, copilotWinnerEmailBCCAddr, copilotWinnerEmailFromAddr,
                phase);
    }


    /**
     * Sets the common properties for the receipt email
     *
     * @param phase
     *            the phase object in which properties need to be set.
     * @param purchasedBy
     *            the name of the person who purchased.
     * @param paymentData
     *            the payment data. it is one of TCPurhcaseOrderPaymentData or
     *            CreditCardPaymentData
     * @param competitionType
     *            the competition type
     * @param competitionTitle
     *            the competition title
     * @param projectName
     *            the project name
     *
     * @since Cockpit Release Assembly for Receipts
     */
    private void setReceiptEmailCommonProperties(
        com.topcoder.project.phases.Phase phase, String purchasedBy,
        PaymentData paymentData, String competitionType,
        String competitionTitle, String projectName, boolean isDesign, long challengeId) {
        // TODO: keep the commented portion, once if/else start working in
        // document generator we should switch to it.
        StringBuffer sb = new StringBuffer();

        if (paymentData instanceof TCPurhcaseOrderPaymentData) {
            TCPurhcaseOrderPaymentData po = (TCPurhcaseOrderPaymentData) paymentData;

            if (po.getClientName() != null)
            {
                sb.append("Client Name:").append(po.getClientName().replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
            }
            else
            {
                sb.append("Client Name:").append(po.getClientName());
            }

            sb.append("\n    ");

            if (po.getProjectName() != null)
            {
                sb.append("Billing Project Name:").append(po.getProjectName().replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;"));
            }
            else
            {
                sb.append("Billing Project Name:").append(po.getProjectName());
            }

            sb.append("\n    ");
            sb.append("PO #:").append(po.getPoNumber());
        } else if (paymentData instanceof CreditCardPaymentData) {
            CreditCardPaymentData cc = (CreditCardPaymentData) paymentData;

            sb.append(cc.getFirstName());
            sb.append("\n    ");
            sb.append(cc.getAddress());
            sb.append("\n    ");
            sb.append(cc.getCity()).append(", ").append(cc.getState())
              .append(cc.getZipCode());
            sb.append("\n    ");
            sb.append(cc.getCountry());
        }

        phase.setAttribute("PURCHASER_DETAILS", sb.toString());

        phase.setAttribute("PURCHASED_BY", purchasedBy);
        phase.setAttribute("COMPETITION_TYPE", competitionType);
        phase.setAttribute("COMPETITION_TITLE", competitionTitle);
        phase.setAttribute("PROJECT_NAME", projectName);

        if (isDesign) {
        	phase.setAttribute("COMPETITION_URL", "http://" + ApplicationServer.NEW_COMMUNITY_SERVER_NAME + "/challenge-details/" + challengeId + "/?type=design");
        } else {
        	phase.setAttribute("COMPETITION_URL", "http://" + ApplicationServer.NEW_COMMUNITY_SERVER_NAME + "/challenge-details/" + challengeId + "/?type=develop");
        }
    }

    /**
     * Gets the spec reviews for specified contest id.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the contest id
     * @param studio indicates whether the specified contest id is for studio contests.
     * @return the spec review that matches the specified contest id.
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public SpecReview getSpecReviews(TCSubject tcSubject, long contestId, boolean studio)
            throws ContestServiceException {
        try {
            return this.specReviewService.getSpecReviews(contestId, studio);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during getSpecReviews", e);
        }
    }

    /**
     * Save specified review comment and review status for specified section and specified contest id to persistence.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the contest id
     * @param studio indicates whether the specified contest id is for studio contests.
     * @param sectionName the section name
     * @param comment the comment
     * @param isPass the is pass
     * @param role the user role type
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void saveReviewStatus(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
            String comment, boolean isPass, String role) throws ContestServiceException {
        try {
            this.specReviewService.saveReviewStatus(tcSubject, contestId, studio, sectionName, comment, isPass, role);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during saveReviewStatus", e);
        }
    }

    /**
     * Save specified review comment for specified section and specified contest id to persistence.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the contest id
     * @param studio indicates whether the specified contest id is for studio contests.
     * @param sectionName the section name
     * @param comment the comment
     * @param role the user role type
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void saveReviewComment(TCSubject tcSubject, long contestId, boolean studio, String sectionName,
            String comment, String role) throws ContestServiceException {
        try {
            this.specReviewService.saveReviewComment(tcSubject, contestId, studio, sectionName, comment, role);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during saveReviewComment", e);
        }
    }

    /**
     * Mark review comment with specified comment id as seen.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param commentId the comment id
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since Cockpit Launch Contest - Inline Spec Review Part 2
     */
    public void markReviewCommentSeen(TCSubject tcSubject, long commentId) throws ContestServiceException {
        try {
            this.specReviewService.markReviewCommentSeen(tcSubject, commentId);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during markReviewCommentSeen", e);
        }
    }
    /**
     * Marks 'review done' by reviewer of the specs for specified contest. Persistence is updated and all end users
     * having write/full permission on the contest are notified by email.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the specified contest id.
     * @param contestName the contest name
     * @param studio whether contest is studio or not.
     * @tcDirectProjectId the tc direct project id.
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReviewDone(TCSubject tcSubject, long contestId, String contestName, boolean studio,
            long tcDirectProjectId) throws ContestServiceException {
        try {
            // get updates.
            List<UpdatedSpecSectionData> updates = this.specReviewService.getReviewerUpdates(contestId, studio);

            this.specReviewService.markReviewDone(tcSubject, contestId, studio);

            // notify all users who have write permission by email.
            Set<String> toAddresses = new HashSet<String>();

            List<Permission> permissions = this.permissionService.getPermissionsByProject(contestId);
            for (Permission p : permissions) {
                if (p.getPermissionType().getPermissionTypeId() == 6 || p.getPermissionType().getPermissionTypeId() == 5) {
                    String toAddr = this.userService.getEmailAddress(p.getUserHandle());
                    toAddresses.add(toAddr);
                }
            }

            permissions = this.permissionService.getPermissionsByProject(tcDirectProjectId);
            for (Permission p : permissions) {
                if (p.getPermissionType().getPermissionTypeId() == 2 || p.getPermissionType().getPermissionTypeId() == 3) {
                    String toAddr = this.userService.getEmailAddress(p.getUserHandle());
                    toAddresses.add(toAddr);
                }
            }

            // send email to all toAddresses.
            sendSpecReviewNotificationEmail(toAddresses.toArray(new String[0]), updates, contestName);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during markReviewDone", e);
        } catch (PermissionServiceException e) {
            throw new ContestServiceException("Error during retrieving permissions", e);
        } catch (UserServiceException e) {
            throw new ContestServiceException("Error during retrieving permissions", e);
        } catch (EmailMessageGenerationException e) {
            // ignore email error.
            Logger.getLogger(this.getClass()).error("Email Error : "+e);
        } catch (EmailSendingException e) {
            // ignore email error.
            Logger.getLogger(this.getClass()).error("Email Error : "+e);
        }
    }

    /**
     * Marks 'ready for review' by the writer of the specs for specified contest. Persistence is updated, on update the
     * spec would appear as review opportunity on tc site.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the specified contest id.
     * @param studio whether contest is studio or not.
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void markReadyForReview(TCSubject tcSubject, long contestId, boolean studio) throws ContestServiceException {
        try {
            this.specReviewService.markReadyForReview(tcSubject, contestId, studio);
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during markReadyForReview", e);
        }
    }

    /**
     * Marks 'resubmit for review' by the writer of the specs for specified contest. Persistence is updated. Reviewer
     * (if any) is notified about the updates.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param contestId the specified contest id.
     * @param contestName the contest name
     * @param studio whether contest is studio or not.
     * @param reviewerUserId reviewer user id.
     * @throws ContestServiceException if any error during retrieval/save from persistence
     * @since 1.0.1
     */
    public void resubmitForReview(TCSubject tcSubject, long contestId, String contestName, boolean studio,
            long reviewerUserId) throws ContestServiceException {
        try {
            // get updates.
            List<UpdatedSpecSectionData> updates = this.specReviewService.getReviewerUpdates(contestId, studio);

            this.specReviewService.resubmitForReview(tcSubject, contestId, studio);

            // do not send email if no updates are there.
            if (updates.size() <= 0) {
                return;
            }

            // notify the reviewer about updates.
            String reviewerEmail = this.userService.getEmailAddress(reviewerUserId);

            if (reviewerEmail != null) {
                sendSpecReviewNotificationEmail(new String[] {reviewerEmail}, updates, contestName);
            }
        } catch (SpecReviewServiceException e) {
            throw new ContestServiceException("Error during resubmit for review.", e);
        } catch (UserServiceException e) {
            throw new ContestServiceException("Error during retrieving email for reviewer.", e);
        } catch (EmailMessageGenerationException e) {
            // ignore any email errors.
            Logger.getLogger(this.getClass()).error("Email Error : "+e);
        } catch (EmailSendingException e) {
            // ignore any email errors.
            Logger.getLogger(this.getClass()).error("Email Error : "+e);
        }
    }

    /**
     * Sends spec review notification email
     *
     * @param toAddrs
     *            array of to addresses to which spec review notification email should be sent.
     * @param updates
     *            the data about updated sections.
     * @param contestName
     *            the name of the contest
     * @throws EmailMessageGenerationException
     *             thrown if error during email message generation
     * @throws EmailSendingException
     *             thrown if error during email sending.
     * @since 1.0.2
     */
    private void sendSpecReviewNotificationEmail(String[] toAddrs, List<UpdatedSpecSectionData> updates,
            String contestName) throws EmailMessageGenerationException, EmailSendingException {
        com.topcoder.project.phases.Phase phase = new com.topcoder.project.phases.Phase();

        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (UpdatedSpecSectionData d : updates) {
            StringBuffer s = new StringBuffer();
            s.append("Section: ").append(d.getSectionName()).append("\n");
            s.append("Review Status: ").append(d.getStatus()).append("\n");
            s.append("Updated By: ").append(d.getUser()).append("\n");
            s.append("Comment: ").append(d.getComment());

            if (!first) {
                sb.append("\n\n");
            }

            sb.append(s.toString());
            first = false;
        }

        phase.setAttribute("SECTIONS", sb.toString());

        String file = Thread.currentThread().getContextClassLoader().getResource(
                specReviewNotificationEmailTemplatePath).getFile();
        Logger.getLogger(this.getClass()).debug("File name for template: " + file);
        sendEmail(EMAIL_FILE_TEMPLATE_SOURCE_KEY, file, specReviewNotificationEmailSubject.replace(
                "%CONTEST_NAME%", contestName), toAddrs, null, specReviewNotificationEmailBCCAddr,
                specReviewNotificationEmailFromAddr, phase);
    }

    /**
     * Gets all contest fees by billing project id.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the billing project id
     * @return the list of project contest fees for the given project id
     * @throws ContestServiceException if any persistence or other error occurs
     * @since 1.0.1
     */
    public List<ProjectContestFee> getContestFeesByProject(TCSubject tcSubject, long projectId)
            throws ContestServiceException {
        try {
            return this.billingProjectDAO.getContestFeesByProject(projectId);
        } catch(DAOException e) {
            throw new ContestServiceException("Error in retrieving contest fees by project: " + projectId, e);
        }
    }

    /**
     * Get all design components.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @throws ContestServiceException if any other error occurs
     * @since 1.1
     */
    public List<DesignComponents> getDesignComponents(TCSubject tcSubject) throws ContestServiceException {
        String methodName = "getDesignComponents";
        logger.info("Enter: " + methodName);

        try {
            return projectServices.getDesignComponents(tcSubject, 0);
        } catch (ProjectServicesException pe) {
            logger.error(pe.getMessage(), pe);
            throw new ContestServiceException(pe.getMessage(), pe);
        } finally {
            logger.info("Exit: " + methodName);
        }
    }

    /**
     * Returns whether a user is eligible for a particular contest.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param userId The user id
     * @param contestId The contest id
     * @param isStudio true if the contest is a studio contest, false otherwise.
     * @return true if the user is eligible for the specified contest, false otherwise.
     * @throws ContestServiceException if any other error occurs
     * @since 1.2.2
     */
    public boolean isEligible(TCSubject tcSubject, long userId, long contestId, boolean isStudio)
            throws ContestServiceException {
        String methodName = "isEligible";
        logger.info("Enter: " + methodName);

        boolean eligible = false;

        try {
            List<ContestEligibility> eligibilities = contestEligibilityManager.getContestEligibility(contestId,
                    isStudio);
            eligible = contestEligibilityValidationManager.validate(userId, eligibilities);
        } catch (ContestEligibilityPersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new ContestServiceException(e.getMessage(), e);
        } catch (ContestEligibilityValidationManagerException e) {
            logger.error(e.getMessage(), e);
            throw new ContestServiceException(e.getMessage(), e);
        }

        logger.info("Exit: " + methodName);
        return eligible;
    }

    /**
     * Find eligibility name for the billing project.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param billingProjectId; The ID of the billing project.
     * @return The name of the eligibility group.
     * @since 1.2.3
     */
    public String getEligibilityName(TCSubject tcSubject, long billingProjectId) {
        String methodName = "getEligibilityName :  billing project id = "+ billingProjectId;
        logger.info("Enter: " + methodName);
        try {
            ConfigManager cfgMgr = ConfigManager.getInstance();
            Property rootProperty = cfgMgr.getPropertyObject(CONTEST_ELIGIBILITY_MAPPING_NAMESPACE,
                CONTEST_ELIGIBILITY_MAPPING_PREFIX);
            Property eligibility = rootProperty.getProperty(Long.toString(billingProjectId));
            if (eligibility != null && !eligibility.equals("")) {

                return (String)(eligibility.getValue(ELIGIBILITY_NAME));
            }
            return "";
        } catch (Exception e) {
            logger.error("Cannot retrieve eligibility name.");
            return "";
        } finally {
            logger.info("Exit: " + methodName);
        }
    }

    /**
     * Find eligibility admin role for the billing project.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param billingProjectId; The ID of the billing project.
     * @return The name of the eligibility group.
     * @since 1.2.3
     *
    public String getEligibilityAdminRole(TCSubject tcSubject, long billingProjectId) {
        String methodName = "getEligibilityAdminRole :  billing project id = "+ billingProjectId;
        logger.info("Enter: " + methodName);
        try {
            ConfigManager cfgMgr = ConfigManager.getInstance();
            Property rootProperty = cfgMgr.getPropertyObject(CONTEST_ELIGIBILITY_MAPPING_NAMESPACE,
                CONTEST_ELIGIBILITY_MAPPING_PREFIX);
            Property eligibility = rootProperty.getProperty(Long.toString(billingProjectId));
            if (eligibility != null && !eligibility.equals("")) {

                return (String)(eligibility.getValue(ELIGIBILITY_ADMIN_ROLE));
            }
            return "";
        } catch (Exception e) {
            logger.error("Cannot retrieve eligibility admin role.");
            return "";
        } finally {
            logger.info("Exit: " + methodName);
        }
    } **/

    /**
     * Private helper method to remove loops within documentation collection in AssetDTO
     *
     * @param contest the contest which needs loops removal
     *
     * @since 1.3.4
     */
    private void removeDocumentationLoops(SoftwareCompetition contest) {
        if (contest.getAssetDTO().getDocumentation() != null && contest.getAssetDTO().getDocumentation().size() > 0) {
            for (CompDocumentation doc : contest.getAssetDTO().getDocumentation()) {
                doc.setCompVersion(null);
            }
        }
    }

    /**
     * Returns whether the contest is private.
     *
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
    public boolean isPrivate(TCSubject tcSubject, long contestId, boolean isStudio) throws ContestServiceException {
        String methodName = "isPrivate";
        logger.info("Enter: " + methodName);

        List<ContestEligibility> eligibilities;
        try {
            eligibilities = contestEligibilityManager.getContestEligibility(contestId, isStudio);
        } catch (ContestEligibilityPersistenceException e) {
            logger.error(e.getMessage(), e);
            throw new ContestServiceException(e.getMessage(), e);
        }

        logger.info("Exit: " + methodName);
        return !eligibilities.isEmpty();
    }

    /**
     * This method creates a Specification Review project associated to a project determined by parameter.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project id to create a Specification Review for
     * @return the created project
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     * @since 1.4
     */
    public FullProjectData createSpecReview(TCSubject tcSubject, long projectId) throws ContestServiceException {
        String method = "createSpecReview(tcSubject = " + tcSubject.getUserId() + "," + projectId + ")";
        logger.info("Enter: " + method);

        FullProjectData specReview = null;
        try {
            specReview = projectServices.createSpecReview(projectId, specReviewPrize, String.valueOf(tcSubject.getUserId()), getUserName(tcSubject));
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in ProjectServices.", e);
            throw new ContestServiceException("Operation failed in Project Services.", e);
        } finally {
            logger.info("Exit: " + method);
        }

        return specReview;
    }

    /**
     * This method retrieves scorecard and review information associated to a project determined by parameter. Note: a
     * single reviewer / review is assumed.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project id to search for
     * @return the aggregated scorecard and review data
     * @throws ContestServiceException if any unexpected error occurs in the underlying services
     * @since 1.4
     */
    public ScorecardReviewData getScorecardAndReview(TCSubject tcSubject, long projectId)
            throws ContestServiceException {
        String method = "getScorecardAndReview(tcSubject = " + tcSubject.getUserId() + "," + projectId + ")";
        logger.info("Enter: " + method);

        ScorecardReviewData scorecardReviewData =  null;
        try {
            scorecardReviewData =  projectServices.getScorecardAndReview(projectId);
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in Project Services.", e);
            throw new ContestServiceException("Operation failed in Project Services.", e);
        } finally {
            logger.info("Exit: " + method);
        }

        return scorecardReviewData;
    }

    /**
     * This method uploads a mock file to the corresponding specification review project of the specified project id, so
     * that it can continue with review. Regular submission or final fix will be uploaded according to the open phase.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project id of the original project
     * @throws ContestServiceException if any unexpected error occurs in the underlying services, if the associated
     *             specification review project id cannot be found or if neither submission or final fixes phase are
     *             open.
     * @since 1.4
     */
    public void markSoftwareContestReadyForReview(TCSubject tcSubject, long projectId) throws ContestServiceException {
        String method = "markSoftwareContestReadyForReview(tcSubject = " + tcSubject.getUserId() + "," + projectId + ")";
        logger.info("Enter: " + method);

        try {
            // get associated specification review project id
            long specReviewProjectId = projectServices.getSpecReviewProjectId(projectId);
            if (specReviewProjectId < 0) {
                throw new ContestServiceException("Failed to get associated specification review.");
            }

            // get associated specification review open phases
            Set<String> openPhases = projectServices.getOpenPhases(specReviewProjectId);

            // prepare mock file for upload
            DataHandler dataHandler = new DataHandler(new FileDataSource(mockSubmissionFilePath +
                   mockSubmissionFileName));

            // upload regular submission or final fix according to open phase
            if (openPhases.contains(PROJECT_SUBMISSION_PHASE_NAME)) {
                uploadSubmission(tcSubject, specReviewProjectId, mockSubmissionFileName, dataHandler);
            } else if (openPhases.contains(PROJECT_FINAL_FIX_PHASE_NAME)) {
                uploadFinalFix(tcSubject, specReviewProjectId, mockSubmissionFileName, dataHandler);
            } else {
                throw new ContestServiceException("Submission or Final Fix phase should be open.");
            }
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in Project Services.", e);
            throw new ContestServiceException("Operation failed in Project Services.", e);
        } finally {
            logger.info("Exit: " + method);
        }
    }

    /**
     * This method adds a review comment to a review. It simply delegates all logic to underlying services.
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param reviewId the review id to add the comment to
     * @param comment the review comment to add
     * @throws ContestServiceException if any unexpected error occurs in the underlying services.
     * @throws IllegalArgumentException if comment is null
     * @since 1.4
     */
    public void addReviewComment(TCSubject tcSubject, long reviewId, Comment comment) throws ContestServiceException {
        if (comment == null) {
            throw new IllegalArgumentException("The comment cannot be null");
        }

        String method = "addReviewComment(tcSubject = " + tcSubject.getUserId() + "," + reviewId + ", " + comment + ")";
        logger.info("Enter: " + method);

        try {
            projectServices.addReviewComment(reviewId, comment, String.valueOf(tcSubject.getUserId()));
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in Project Services.", e);
            throw new ContestServiceException("Operation failed in Project Services.", e);
        } finally {
            logger.info("Exit: " + method);
        }
    }

    /**
     * create software forum watch with given parameters. It will lookup the ForumsHome
     * interface, and ceate the forum by the ejb home interface. In the old
     * version, this method misses the document, it's added in the version 1.1
     *
     *
     * @param asset
     *            The asset DTO to user
     * @param userId
     *            userId The user id to use
     * @param projectCategoryId
     *            The project category id to
     * @return The long id of the created forum
     */
    private void createSoftwareForumWatchAndRole(long forumId, long userId, boolean watch) {
        logger.debug("createSoftwareForumWatchAndRole (" + forumId + ", " + userId + ")");

        try {
            Forums forums = getSoftwareForums();

            String roleId = "Software_Moderators_" + forumId;
            if (watch)
            {
                forums.createCategoryWatch(userId, forumId);
            }

            forums.assignRole(userId, roleId);

            logger.debug("Exit createSoftwareForumWatchAndRole (" + forumId + ", " + userId + ")");

        } catch (Exception e) {
            logger.error("*** Could not create a softwaer forum watch for " + forumId + ", " + userId );
            logger.error(e);
        }
    }

    /**
     * create stduio forum watch with given parameters. It will lookup the ForumsHome
     * interface, and ceate the forum by the ejb home interface. In the old
     * version, this method misses the document, it's added in the version 1.1
     *
     *
     * @param asset
     *            The asset DTO to user
     * @param userId
     *            userId The user id to use
     * @param projectCategoryId
     *            The project category id to
     * @param isNewStudioForum whether the forum is ported to the studio part.
     * @return The long id of the created forum
     */
    private void createStudioForumWatchAndRole(long forumId, long userId, boolean watch, boolean isNewStudioForum) {
        logger.debug("createStudioForumWatchAndRole (" + forumId + ", " + userId + ")");

        try {
            Forums forums = isNewStudioForum ? getSoftwareForums() : getStudioForums();

            String roleId = "Software_Moderators_" + forumId;
            if (watch)
            {
                forums.createForumWatch(userId, forumId);
            }

            forums.assignRole(userId, roleId);

            logger.debug("Exit createStudioForumWatchAndRole (" + forumId + ", " + userId + ")");

        } catch (Exception e) {
            logger.error("*** Could not create a studio forum watch for " + forumId + ", " + userId );
            logger.error(e);
        }
    }

    /**
     * delete software forum watch with given parameters. It will lookup the ForumsHome
     * interface, and ceate the forum by the ejb home interface. In the old
     * version, this method misses the document, it's added in the version 1.1
     *
     *
     * @param forumId
     *            The forum id to delete watch.
     * @param userId
     *            userId The user id to use
     */
    private void deleteSoftwareForumWatchAndRole(long forumId, long userId) {
        logger.info("deleteForumWatch (" + forumId + ", " + userId + ")");

        try {
            Forums forums = getSoftwareForums();

            String roleId = "Software_Moderators_" + forumId;
            forums.deleteCategoryWatch(userId, forumId);
            forums.removeRole(userId, roleId);
            logger.debug("Exit deleteForumWatch (" + forumId + ", " + userId + ")");

        } catch (Exception e) {
            logger.error("*** Could not delete forum watch for " + forumId + ", " + userId );
            logger.error(e);
        }
    }

    /**
     * delete forum watch with given parameters. It will lookup the ForumsHome
     * interface, and ceate the forum by the ejb home interface. In the old
     * version, this method misses the document, it's added in the version 1.1
     *
     *
     * @param forumId
     *            The forum id to delete watch.
     * @param userId
     *            userId The user id to use
     * @param  isNewStudioForum whethether the studio forum is ported to the software forum
     */
    private void deleteStudioForumWatchAndRole(long forumId, long userId, boolean isNewStudioForum) {
        logger.info("deleteForumWatch (" + forumId + ", " + userId + ")");

        try {
            Forums forums = isNewStudioForum ? getSoftwareForums() : getStudioForums();

            String roleId = "Software_Moderators_" + forumId;
            forums.deleteForumWatch(userId, forumId);
            forums.removeRole(userId, roleId);
            logger.debug("Exit deleteForumWatch (" + forumId + ", " + userId + ")");

        } catch (Exception e) {
            logger.error("*** Could not delete forum watch for " + forumId + ", " + userId );
            logger.error(e);
        }
    }


    /**
     * update forum name
     *
     * @param forumId
     *            The forum id to update
     * @param name
     *            The name to use
     */
    private void updateForumName(long forumId, String name) {
        logger.info("updateForumName (" + forumId + ", " + name + ")");

        try {
            Forums forums = getSoftwareForums();

            forums.updateComponentName(forumId, name);

            logger.debug("Exit updateForumName (" + forumId + ", " + name + ")");

        } catch (Exception e) {
            logger.error("*** Could not updateForumName for " + forumId + ", " + name );
            logger.error(e);
        }
    }

    /**
     * Update studio forum name.
     *
     * @param forumId
     *            The forum id to update
     * @param name
     *            The name to use
     */
    private void updateStudioForumName(long forumId, String name) {
        logger.info("updateStudioForumName (" + forumId + ", " + name + ")");

        try {
            Forums forums = getSoftwareForums();

            forums.updateStudioForumName(forumId, name);

            logger.debug("Exit updateStudioForumName (" + forumId + ", " + name + ")");

        } catch (Exception e) {
            logger.error("*** Could not updateStudioForumName for " + forumId + ", " + name );
            logger.error(e);
        }
    }

    /**
     * check if user agrees the term(s) associate with the project (if any)
     * by role
     *
     * @param projectId
     *            OR project id
     * @param userId
     *            userId The user id to use
     * @param roleId
     *            role id
     * @return true if user agreed terms or no term associated with project
     */
    private boolean checkTerms(long projectId, long userId, int[] roleIds) {
        logger.info("checkTerms (" + projectId + ", " + userId + ", " + roleIds + ")");

        try {
            Properties p = new Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES,
                "org.jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, userBeanProviderUrl);

            Context c = new InitialContext(p);
            UserTermsOfUseHome userTermsOfUseHome = (UserTermsOfUseHome) c.lookup(UserTermsOfUseHome.EJB_REF_NAME);

            UserTermsOfUse userTerm = userTermsOfUseHome.create();

            Properties p2 = new Properties();
            p2.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
            p2.put(Context.URL_PKG_PREFIXES,
                "org.jboss.naming:org.jnp.interfaces");
            p2.put(Context.PROVIDER_URL, projectBeanProviderUrl);

            Context c2 = new InitialContext(p2);
            ProjectRoleTermsOfUseHome projectRoleTermsOfUseHome = (ProjectRoleTermsOfUseHome) c2.lookup(ProjectRoleTermsOfUseHome.EJB_REF_NAME);

            ProjectRoleTermsOfUse projectTerm = projectRoleTermsOfUseHome.create();

            List<Long>[] necessaryTerms = projectTerm.getTermsOfUse((int)projectId, roleIds, "java:/DS");

            // if project does not have term
            if (necessaryTerms == null || necessaryTerms.length == 0)
            {
                return true;
            }

            for (int i = 0; i < necessaryTerms.length; i++)
            {
                if (necessaryTerms[i] != null)
                {
                    for (int j = 0; j < necessaryTerms[i].size(); j++)
                    {
                        Long termId = necessaryTerms[i].get(j);
                        // if user has not agreed
                        if (!userTerm.hasTermsOfUse(userId, termId, "java:/DS"))
                        {
                            return false;
                        }
                    }
                }
            }

            logger.debug("checkTerms (" + projectId + ", " + userId + ", " + roleIds + ")");
            return true;

        } catch (Exception e) {
            logger.error("*** eorr in checkTerms (" + projectId + ", " + userId + ", " + roleIds + ")");

            logger.error(e);
            return false;
        }
    }

    /**
     * The next day for production-date.
     * @return new date
     */
    private XMLGregorianCalendar nextDay(){
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.setTime(new Date());
        startDate.add(Calendar.HOUR, 24 * 14); // BUGR-1789
        int m = startDate.get(Calendar.MINUTE);
        startDate.add(Calendar.MINUTE, m + (15 - m % 15) % 15);
        return getXMLGregorianCalendar(startDate.getTime());
    }
    /**
     * The next production-date for the re-open and new release contest.
     * @return new date
     */
    private XMLGregorianCalendar nextReOpenNewReleaseDay(){
        GregorianCalendar startDate = new GregorianCalendar();
        startDate.setTime(new Date());
        startDate.add(Calendar.HOUR, 24);
        int m = startDate.get(Calendar.MINUTE);
        startDate.add(Calendar.MINUTE, m + (15 - m % 15) % 15);
        return getXMLGregorianCalendar(startDate.getTime());
    }
    /**
     * Finds the next development production date for the design.
     * @param date date to calcuate base on
     * @return the next development prod date
     * @throws DatatypeConfigurationException if any error occurs
     */
    private XMLGregorianCalendar nextDevProdDay(XMLGregorianCalendar date) throws DatatypeConfigurationException{
        Duration elevenDay = DatatypeFactory.newInstance().newDurationDayTime(true, 11, 0, 0, 0);
        date.add(elevenDay);
        return date;
    }
    /**
     * <p>
     * Create new version for design or development contest. (project_status_id = 4-10 in
     * tcs_catalog:project_status_lu).
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project to create new version
     * @param tcDirectProjectId tc direct project id
     * @param autoDevCreating if it is true and it is design contest, then will create development too
     * @param startDate the start date for the new version contest
     * @return newly version contest id
     * @throws ContestServiceException if any error occurs
     */
    private long createNewVersionForDesignDevContest(TCSubject tcSubject, long projectId, long tcDirectProjectId,
            boolean autoDevCreating, XMLGregorianCalendar startDate, boolean minorVersion)
            throws ContestServiceException, PermissionServiceException {
        try {
            //0.check the permission first
            checkSoftwareProjectPermission(tcSubject, tcDirectProjectId, true);
            //1. for now, only completed can create new version
            FullProjectData contest = this.projectServices.getFullProjectData(projectId);
            // if auto dev creating, dont check, since we pass the new design project id
            if (!autoDevCreating && contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.COMPLETED_STATUS_ID) {
                throw new ProjectServicesException("The design project or its corresponding development project is not completed."
                        + " You can not create new version for it.");
            }
            boolean isDevContest =
                contest.getProjectHeader().getProjectCategory().getId() == ProjectCategory.DEVELOPMENT.getId();

            //2.create new version
            Long compVersionId = Long.parseLong(contest.getProjectHeader().getProperty(ProjectPropertyType.EXTERNAL_REFERENCE_ID_PROJECT_PROPERTY_KEY));
            AssetDTO dto = catalogService.getAssetByVersionId(compVersionId);
            // close current version
            dto.setPhase("Completed");
            com.topcoder.project.phases.Phase[] phases = contest.getAllPhases();
            dto.setProductionDate(getXMLGregorianCalendar(phases[phases.length-1].getActualEndDate()));
            // close current forum
            if (createForum && dto.getForum() != null)
            {
                closeForum(dto.getForum().getJiveCategoryId());
            }
            dto.setForum(null);
            catalogService.updateAsset(dto);

            //create minor or major version
            dto.setToCreateMinorVersion(minorVersion);
            dto.setProductionDate(null);

            //if it is dev only, or design, create new version here
            if (!isDevContest || !autoDevCreating) {
                //clear the version
                dto.setCompVersionId(null);
                dto.setForum(null);
                dto.setDocumentation(new ArrayList<CompDocumentation>());
                dto.setPhase("Design");
                dto = catalogService.createVersion(dto);
            }
            //if it is auto-creating-dev and is creating dev now
            else if (autoDevCreating && isDevContest) {
                // need to get the latest verion by component id
                dto = catalogService.getAssetById(dto.getId(), false);
                dto = catalogService.createDevComponent(dto);
            }

            contest.getProjectHeader().setProperty(ProjectPropertyType.EXTERNAL_REFERENCE_ID_PROJECT_PROPERTY_KEY, String.valueOf(dto.getVersionNumber()));
            contest.getProjectHeader().setProperty(ProjectPropertyType.PROJECT_VERSION_PROJECT_PROPERTY_KEY, String.valueOf(dto.getVersionText()));
            contest.getProjectHeader().setProperty(ProjectPropertyType.VERSION_ID_PROJECT_PROPERTY_KEY, String.valueOf(dto.getVersion()));

             // set status to draft
            contest.getProjectHeader().setProjectStatus(ProjectStatus.DRAFT);

            boolean isDevOnly = projectServices.isDevOnly(contest.getProjectHeader().getProjectCategory().getId());

            long forumId = 0;
            // create forum BUGR 4036: only create forum if it is dev only contest when it is dev contest
            boolean needForum = true;
            FullProjectData associateddesignContest = null;

            // use DTO forum
            if(autoDevCreating && isDevContest) {
               needForum = false;
               if (dto.getForum() != null)
               {
                    forumId = dto.getForum().getJiveCategoryId();
               }

            }
            if (createForum && needForum) {
                forumId = createForum(tcSubject, dto, tcSubject.getUserId(), contest.getProjectHeader().getProjectCategory().getId());
            }

            // if forum created
            if (forumId > 0 && dto.getForum() == null)
            {
                // create a comp forum
                CompForum compForum = new CompForum();
                compForum.setJiveCategoryId(forumId);
                dto.setForum(compForum);
                dto = this.catalogService.updateAsset(dto);
                // avoid cycle
                dto.getForum().setCompVersion(null);
            }

            if (forumId > 0) {
                contest.getProjectHeader().setProperty(ProjectPropertyType.DEVELOPER_FORUM_ID_PROJECT_PROPERTY_KEY, String.valueOf(forumId));
            }

            contest.setStartDate(getDate(startDate));
            //3.create the project
            FullProjectData newVersionORProject = projectServices.createNewVersionContest(contest, String.valueOf(tcSubject.getUserId()));

            List<ContestEligibility> contestEligibilities =
                 contestEligibilityManager.getContestEligibility(contest.getProjectHeader().getId(), false);
            for (ContestEligibility ce:contestEligibilities){
                persistContestEligility(newVersionORProject.getProjectHeader(), ce, false);
            }

            //4.if also auto-dev-creating for design, create it
            if (autoDevCreating && !isDevContest) {
                long developmentProjectId = projectServices.getDevelopmentContestId(projectId);
                if (developmentProjectId > 0){
                    logger.debug("create new version development project, the dev project id is :" + developmentProjectId);
                    createNewVersionForDesignDevContest(tcSubject, developmentProjectId, tcDirectProjectId, true, nextDevProdDay(startDate), minorVersion);
                }
            }

            //BUGR 4036
            if(isDevContest) {
                //it will link to design contest if it exists, it forwards to project link manager
                projectServices.linkDevelopmentToDesignContest(newVersionORProject.getProjectHeader().getId());
            }
			
			SoftwareCompetition com = new SoftwareCompetition();
			
			com.setProjectHeader(newVersionORProject.getProjectHeader());
            com.setProjectPhases(newVersionORProject);
            com.setProjectResources(newVersionORProject.getResources());
            com.setProjectData(newVersionORProject);
            com.setId(newVersionORProject.getProjectHeader().getId());
			
			addReviewAuction(com);

            logger.debug("Exit createNewVersionForDesignDevContest");
            return newVersionORProject.getProjectHeader().getId();
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        }
    }
    /**
     * <p>
     * Create new version for design or development contest. (project_status_id = 4-10 in
     * tcs_catalog:project_status_lu).
     * </p>
     * <p>
     * since version 1.5.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project to create new version
     * @param tcDirectProjectId tc direct project id
     * @param autoDevCreating if it is true and it is design contest, then will create development too
     * @return newly version contest id
     * @throws ContestServiceException if any error occurs
     */
    public long createNewVersionForDesignDevContest(TCSubject tcSubject, long projectId, long tcDirectProjectId,
            boolean autoDevCreating, boolean minorVersion) throws ContestServiceException, PermissionServiceException {
        logger.debug("createNewVersionForDesignDevContest with parameter [TCSubject " + tcSubject.getUserId() + ", projectId =" + projectId
                     + ", tcDirectProjectId =" +tcDirectProjectId+", autoDevCreating="+ autoDevCreating +"].");

        return createNewVersionForDesignDevContest(tcSubject, projectId, tcDirectProjectId, autoDevCreating,
                nextReOpenNewReleaseDay(), minorVersion);
    }

    /**
     * <p>
     * Reopen the software contest.
     * </p>
     * <p>
     * since version 1.5.
     * </p>
     * <p>
     * Update in v1.5.1: add parameter TCSubject which contains the security info for current user.
     * </p>
     * @param tcSubject TCSubject instance contains the login security info for the current user
     * @param projectId the project to repost
     * @param tcDirectProjectId the tc direct project id
     * @return the newly created OR project id
     * @throws ContestServiceException if any error occurs during repost
     */
    public long reOpenSoftwareContest(TCSubject tcSubject, long projectId, long tcDirectProjectId)
            throws ContestServiceException, PermissionServiceException {
        logger.debug("reOpenSoftwareContest with parameter [TCSubject " + tcSubject.getUserId() + ", projectId =" + projectId + ", tcDirectProjectId =" +tcDirectProjectId+"].");

        long reOpenContestId = 0;
        try {

            //0.check the permission first
            checkSoftwareProjectPermission(tcSubject, tcDirectProjectId, true);

            //1.make sure it is failed status and can be re-opened.
            FullProjectData contest = projectServices.getFullProjectData(projectId);
            if (contest == null) {
                throw new ContestServiceException("The project does not exist.");
            }
            if (contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.CANCELLED_FAILED_REVIEW_ID
                && contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.CANCELLED_FAILED_SCREENING_ID
                && contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.CANCELLED_ZERO_SUBMISSION_ID
                && contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.CANCELLED_WINNER_UNRESPONSIVE_ID
                && contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.CANCELLED_CLIENT_REQUEST.getId()
                && contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.CANCELLED_REQUIREMENTS_INFEASIBLE.getId()
                && contest.getProjectHeader().getProjectStatus().getId() != ProjectStatus.CANCELLED_ZERO_REGISTRATIONS.getId()) {
                throw new ProjectServicesException("The project is not failed. You can not re-open it.");
            }

             // set status to draft
            contest.getProjectHeader().setProjectStatus(ProjectStatus.DRAFT);

            contest.setStartDate(getDate(nextReOpenNewReleaseDay()));
            //2.create the project
            FullProjectData reOpendedProject =
                projectServices.createReOpenContest(contest, String.valueOf(tcSubject.getUserId()));

            //3. keep terms and eligibility
            List<ContestEligibility> contestEligibilities =
                 contestEligibilityManager.getContestEligibility(contest.getProjectHeader().getId(), false);
            for (ContestEligibility ce:contestEligibilities){
                persistContestEligility(reOpendedProject.getProjectHeader(), ce, false);
            }
			
		
            reOpenContestId = reOpendedProject.getProjectHeader().getId();
			
			SoftwareCompetition com = new SoftwareCompetition();
			
			com.setProjectHeader(reOpendedProject.getProjectHeader());
            com.setProjectPhases(reOpendedProject);
            com.setProjectResources(reOpendedProject.getResources());
            com.setProjectData(reOpendedProject);
            com.setId(reOpendedProject.getProjectHeader().getId());
			
			addReviewAuction(com);
			
            return reOpenContestId;
        } catch (Exception e) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", e);
            throw new ContestServiceException("Operation failed in the contest service facade.",
                e);
        } finally {
            logger.debug("Exit reOpenSoftwareContest with the new contest " + reOpenContestId);
        }
    }

    /**
     * Assign the given roleId to the specified userId in the given project.
     *
     * @param tcSubject the TCSubject instance.
     * @param projectId the id of the project.
     * @param roleId the id of the role.
     * @param userId the id of the user.
     * @param phase the <code>Phase</code> associated with the resource.
     * @param isStudio whether assign to studio contest.
     * @since 1.6.9
     */
    private void assignRole(TCSubject tcSubject, long projectId, long roleId, long userId, com.topcoder.project.phases.Phase phase,
            boolean addNotification, boolean addForumWatch, 	boolean isStudio, boolean checkTerm)
        throws ContestServiceException {
        logger.debug("enter methods assignRole");

        try {
          //  com.topcoder.management.resource.Resource[] resources = projectServices.searchResources(projectId, roleId);

            boolean found = false;

            found = projectServices.resourceExists(projectId, roleId, userId);

            boolean termChecking = !checkTerm || checkTerms(projectId, userId, new int[] { (int) roleId });


            // if not found && user agreed terms (if any) && is eligible, add resource
            if (!found && termChecking
                    && isEligible(tcSubject, userId, projectId, false)) {

                com.topcoder.management.resource.Resource newRes = new com.topcoder.management.resource.Resource();
                newRes.setId(com.topcoder.management.resource.Resource.UNSET_ID);
                newRes.setProject(projectId);

                ResourceRole[] allroles = projectServices.getAllResourceRoles();
                ResourceRole roleToSet = null;
                if (allroles != null && allroles.length > 0)
                {
                    for (ResourceRole role :  allroles)
                    {
                        if (role.getId() == roleId)
                        {
                            roleToSet = role;
                        }
                    }
                }

                if (roleToSet == null)
                {
                    throw new ContestServiceException("Invalid role id " + roleId);
                }

                newRes.setResourceRole(roleToSet);
                if (phase != null) {
                    newRes.setPhase(phase.getId());
                }

                newRes.setProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID, String.valueOf(userId));
                newRes.setProperty(RESOURCE_INFO_HANDLE, String.valueOf(userService.getUserHandle(userId)));
                newRes.setProperty(RESOURCE_INFO_PAYMENT_STATUS, RESOURCE_INFO_PAYMENT_STATUS_NA);
                newRes.setProperty(RESOURCE_INFO_REGISTRATION_DATE, DATE_FORMAT.format(new Date()));
                newRes.setUserId(userId);
                projectServices.updateResource(newRes, String.valueOf(tcSubject.getUserId()));

                // only check notification setting for observer, else always addd
                if (roleId != ResourceRole.RESOURCE_ROLE_OBSERVER_ID || addNotification) {
                    projectServices.addNotifications(userId,
                            new long[] { projectId },
                            String.valueOf(tcSubject.getUserId()));
                }


                // create forum watch
                long forumId = projectServices.getForumId(projectId);

                // only check notification for observer
                if (roleId != ResourceRole.RESOURCE_ROLE_OBSERVER_ID)
                {
                    addForumWatch = true;
                }

                if (forumId > 0 && createForum && !isStudio) {
                    createSoftwareForumWatchAndRole(forumId, userId, addForumWatch);
                }

            }

        } catch (UserServiceException use) {
            sessionContext.setRollbackOnly();
            throw new ContestServiceException(use.getMessage(), use);
        } catch (ContestServiceException cse) {
            sessionContext.setRollbackOnly();
            throw new ContestServiceException(cse.getMessage(), cse);
        } finally {
            logger.debug("exist method assignRole");
        }
    }

    /**
     * Assign the given roleId to the specified userId in the given project.
     *
     * @param tcSubject the TCSubject instance.
     * @param projectId the id of the project.
     * @param roleId the id of the role.
     * @param userId the id of the user.
     *
     * @since BUGR-3731
     */
    public void assginRole(TCSubject tcSubject, long projectId, long roleId, long userId)
            throws ContestServiceException {
        assignRole(tcSubject, projectId, roleId, userId, null, true, true, false, false);
    }

     /* Assigns the role for the given tc project and user, it will assign all projects
     * uder tc direct projct
     *
     * @param tcprojectId the id of the tc direct project.
     * @param roleId the id of the role
     * @param userId the id of the user.
     * @throws ContestServiceException if any error occurs
     * @since BUGR - 3731
     */
    public void assginRoleByTCDirectProject(TCSubject tcSubject, long tcprojectId, long roleId, long userId) throws ContestServiceException
    {

        List<Long> projectIds = projectIds = projectServices.getProjectIdByTcDirectProject(tcprojectId);

        if (projectIds != null && projectIds.size() >0)
        {
            // for each OR project, find all observers
            for (Long pid : projectIds)
            {
                // delegate to new method added in BUGR-3731
                this.assignRole(tcSubject, pid.longValue(), roleId, userId, null, true, true, false, false);

            }

        }

    }

     /**
     * Gets the notification information for the given user id. The notification information will be
     * returned as a list of ProjectNotification instance.
     *
     * @param subject the TCSubject instance.
     * @param userId the id of the user.
     * @return a list of ProjectNotification instances.
     * @throws ContestServiceExeption if any error occurs, exception from forum EJB service will be
     *             caught and logged, but no thrown out.
     * @since 1.6.1 BUGR-3706
     */
    public List<ProjectNotification> getNotificationsForUser(TCSubject subject, long userId)
        throws ContestServiceException {
            logger.info("getNotificationsForUser with arguments [TCSubject " + subject.getUserId() + ", userId =" + userId
                    + "]");
            ArrayList<ProjectNotification> result = new ArrayList<ProjectNotification>();
            List<com.topcoder.management.project.SimpleProjectContestData> contests;

        try {

            // Get all the active/draft contests information belongs to this user
            contests = this.projectServices.getActiveDraftContestsForUser(subject, userId);

            List<Long> swForumIdsList = new ArrayList<Long>();
            List<Long> stForumIdsList = new ArrayList<Long>();
            List<Long> stNewForumIdsList = new ArrayList<Long>();

            long[] contestIds = new long[contests.size()];

            for (int i = 0; i < contests.size(); ++i) {
                if (contests.get(i).getForumId() != null) {
                    if (contests.get(i).isStudio())
                    {
                        if(contests.get(i).getForumType() == null) {
                            stForumIdsList.add(new Long(contests.get(i).getForumId().intValue()));
                        } else {
                            stNewForumIdsList.add(new Long(contests.get(i).getForumId().intValue()));
                        }

                    }
                    else
                    {
                        swForumIdsList.add(new Long(contests.get(i).getForumId().intValue()));
                    }
                }
                contestIds[i] = contests.get(i).getContestId();
            }

            long[] swForumIds = new long[swForumIdsList.size()];
            long[] stForumIds = new long[stForumIdsList.size()];
            long[] stNewForumIds = new long[stNewForumIdsList.size()];

            for (int i = 0; i < swForumIdsList.size(); i++)
            {
                swForumIds[i] = (Long)swForumIdsList.get(i);
            }

            for (int i = 0; i < stForumIdsList.size(); i++)
            {
                stForumIds[i] = (Long)stForumIdsList.get(i);
            }

            for (int i = 0; i < stNewForumIdsList.size(); i++)
            {
                stNewForumIds[i] = (Long)stNewForumIdsList.get(i);
            }

            long[] watchedSwForumIds = new long[0];
            long[] watchedStForumIds = new long[0];
            long[] watchedNewStForumIds = new long[0];

            if (this.createForum) {
                Forums stForums = getStudioForums();
                Forums swForums = getSoftwareForums();

                // get the watched forums Ids of the user
                watchedSwForumIds = swForums.areCategoriesWatched(userId, swForumIds);
                watchedStForumIds = stForums.areForumsWatched(userId, stForumIds);
                watchedNewStForumIds = swForums.areForumsWatched(userId, stNewForumIds);
            }


            // Use a hash set to store watched forum IDs
            Set<Long> watchedSwForumsSet = new HashSet<Long>();
            Set<Long> watchedStForumsSet = new HashSet<Long>();
            Set<Long> watchedNewStForumsSet = new HashSet<Long>();

            for (long id : watchedSwForumIds)
                watchedSwForumsSet.add(id);

            for (long id : watchedStForumIds)
                watchedStForumsSet.add(id);

            for (long id : watchedNewStForumIds)
                watchedNewStForumsSet.add(id);

            // get the IDs of contests of which notifications are on
            long[] notifiedContestIds = this.projectServices.getNotificationsForUser(userId,
                    TIMELINE_NOTIFICATION_TYPE, contestIds);

            // Use a hash set to store notified contest Ids
            Set<Long> notifiedContestsSet = new HashSet<Long>();
            for (long id : notifiedContestIds)
                notifiedContestsSet.add(id);

            // create a map to store mapping : project_id <---> ProjectNotification
            Map<Long, ProjectNotification> map = new HashMap<Long, ProjectNotification>();

            for (com.topcoder.management.project.SimpleProjectContestData c : contests) {

                ProjectNotification pn;

                if (!map.containsKey(c.getProjectId())) {
                    // does not contain the project, create a new one
                    pn = new ProjectNotification();

                    // initialize with project id and project name
                    pn.setProjectId(c.getProjectId());
                    pn.setName(c.getPname());
                    pn.setContestNotifications(new ArrayList<ContestNotification>());

                    map.put(c.getProjectId(), pn);

                } else {
                    // already exists, directly assign it to pn
                    pn = map.get(c.getProjectId());
                }

                ContestNotification cn = new ContestNotification();

                cn.setContestId(c.getContestId());
                if (c.getForumId() != null) {
                    cn.setForumId(c.getForumId());
                }
                cn.setName(c.getCname());
                // added in Direct Notification assembly
                cn.setType(c.getType());
                if (c.isStudio())
                {
                    if(c.getForumType() == null) {
                        cn.setForumNotification(watchedStForumsSet.contains(cn.getForumId()));
                    } else {
                        cn.setForumNotification(watchedNewStForumsSet.contains(cn.getForumId()));
                    }
                }
                else
                {
                    cn.setForumNotification(watchedSwForumsSet.contains(cn.getForumId()));
                }

                cn.setProjectNotification(notifiedContestsSet.contains(cn.getContestId()));

                cn.setIsStudio(c.isStudio());

                // add new ContestNotification into coressponding ProjectNotification
                pn.getContestNotifications().add(cn);

            }

                result = new ArrayList<ProjectNotification>(map.values());

                // sort the ProjectNotification by alphabetical order
                Collections.sort(result, new Comparator<ProjectNotification>(){
                        public int compare(ProjectNotification o1, ProjectNotification o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());
                        }
                        });

                // for each ProjectNotification, sort ContestNotifications by alphabetical order
                for (ProjectNotification pn : result){
                    Collections.sort(pn.getContestNotifications(), new Comparator<ContestNotification>(){
                            public int compare(ContestNotification o1, ContestNotification o2) {
                            return o1.getName().compareToIgnoreCase(o2.getName());
                            }
                            }) ;
                }

                return result;

        } catch (ProjectServicesException pse) {
            logger.error("ProjectServices operation failed in the contest service facade.", pse);
            throw new ContestServiceException("Error occurs when operating with ProjectServices", pse);
        } catch (Exception ex) {
            // forum related exception should be caught and logged but not thrown out
            logger.error("Operation failed in the contest service facade.", ex);
            return result;
        } finally {
            logger.info("Exit getNotificationsForUser");
        }

    }

    /**
     * Updates the notifications for the given user, the notifications which need to update are
     * passed in as a list of ProjectNotification instances.
     *
     * @param subject the TCSubject instance.
     * @param userId the id of the user.
     * @param notifications a list of ProjectNotification instances to update.
     * @throws ContestServiceExeption if any error occurs, exception from forum EJB service will be
     *             caught and logged, but no thrown out.
     * @since 1.6.1 BUGR-3706
     */
    public void updateNotificationsForUser(TCSubject subject, long userId, List<ProjectNotification> notifications)
            throws ContestServiceException {

        logger.debug("updateNotifcationsForUser with arguments [TCSubject " + subject.getUserId() + ", notifications ="
                + getProjectNotificationsDebugInfo(notifications) + "]");

        try {

            List<Long> watchSwForumIdList = new ArrayList<Long>();
            List<Long> watchStForumIdList = new ArrayList<Long>();
            List<Long> watchNewStForumIdList = new ArrayList<Long>();
            List<Long> unwatchSwForumIdList = new ArrayList<Long>();
            List<Long> unwatchStForumIdList = new ArrayList<Long>();
            List<Long> unwatchNewStForumIdList = new ArrayList<Long>();

            List<Long> allContestIdList = new ArrayList<Long>();
            List<Long> notifyContestIdList = new ArrayList<Long>();

            for (ProjectNotification pn : notifications) {
                for (ContestNotification cn : pn.getContestNotifications()) {
                    if (cn.getForumId() != 0) {
                        boolean useNewForumForStudio = false;
                        if (cn.isStudio()) {
                            Project contest = projectManager.getProject(cn.getContestId());
                            useNewForumForStudio = hasForumType(contest);
                        }

                        if (cn.isForumNotification()) {
                            if (cn.isStudio()) {

                                if (useNewForumForStudio) {
                                    watchNewStForumIdList.add(cn.getForumId());
                                } else {
                                    watchStForumIdList.add(cn.getForumId());
                                }
                            } else {
                                watchSwForumIdList.add(cn.getForumId());
                            }
                        } else {
                            if (cn.isStudio()) {
                                if (useNewForumForStudio) {
                                    unwatchNewStForumIdList.add(cn.getForumId());
                                } else {
                                    unwatchStForumIdList.add(cn.getForumId());
                                }
                            } else {
                                unwatchSwForumIdList.add(cn.getForumId());
                            }
                        }
                    }

                    allContestIdList.add(cn.getContestId());

                    if (cn.isProjectNotification()) {
                        notifyContestIdList.add(cn.getContestId());
                    }

                }
            }

            if (this.createForum) {

                Forums stForums = getStudioForums();
                Forums swForums = getSoftwareForums();

                // sets the forum watches using Forum EJB service
                swForums.deleteCategoryWatches(userId, getPrimitiveArray(unwatchSwForumIdList));
                swForums.createCategoryWatches(userId, getPrimitiveArray(watchSwForumIdList));

                stForums.deleteForumWatches(userId, getPrimitiveArray(unwatchStForumIdList));
                stForums.createForumWatches(userId, getPrimitiveArray(watchStForumIdList));

                swForums.deleteForumWatches(userId, getPrimitiveArray(unwatchNewStForumIdList));
                swForums.createForumWatches(userId, getPrimitiveArray(watchNewStForumIdList));
            }

            // remove notifications of all contests of this user first
            this.projectServices.removeNotifications(userId, getPrimitiveArray(allContestIdList), String
                    .valueOf(subject.getUserId()));

            // add notifications
            this.projectServices.addNotifications(userId, getPrimitiveArray(notifyContestIdList), String
                    .valueOf(subject.getUserId()));

        } catch (Exception ex) {
            sessionContext.setRollbackOnly();
            logger.error("Operation failed in the contest service facade.", ex);
            if (ex instanceof ProjectServicesException) {
                // we only throw the exception out if it comes from ProjectServices
                // exception comes from Forum EJB service is not thrown out
                throw new ContestServiceException("Operation failed in the contest service facade.", ex);
            }
        } finally {
            logger.debug("Exit updateNotifcationsForUser");
        }

    }

    /**
     * Generates a string which contains debug info of a list of ProjectNotification instances.
     *
     * @param notifications the list of ProjectNotification instances.
     * @return the generated string.
     * @since 1.6.1
     */
    private String getProjectNotificationsDebugInfo(List<ProjectNotification> notifications) {
        StringBuffer sb = new StringBuffer();

        for (ProjectNotification pn : notifications) {
            sb.append("Direct Project:" + pn.getProjectId() + " " + pn.getName() + "\n");

            for (ContestNotification cn : pn.getContestNotifications()) {
                sb.append("\tContest:" + cn.getContestId() + " " + cn.getName() + " contest notification:"
                        + cn.isProjectNotification() + " forum watch:" + cn.isForumNotification() + "\n");
            }

        }

        return sb.toString();
    }

    /**
     * Utility method which coverts a List of Long to primitive long[].
     *
     * @param list a list of Long.
     * @return converted primitive long[]
     */
    private long[] getPrimitiveArray(List<Long> list) {
        long[] result = new long[list.size()];
        for(int i = 0; i < result.length; ++i) {
            result[i] = list.get(i);
        }
        return result;
    }


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
    public List<Registrant> getRegistrantsForProject(TCSubject tcSubject, long projectId)
            throws ContestServiceException {

        logger.debug("getRegistrantsForProject with parameter [TCSubject " + tcSubject.getUserId() + ", projectId ="
                + projectId + "].");

        // create an empty list first to store the result
        List<Registrant> result = new ArrayList<Registrant>();

        try {
            // user 1 for resource role ID which is the ID of role 'submitter'
            com.topcoder.management.resource.Resource[] regs = this.projectServices.searchResources(projectId, 1);

            for (com.topcoder.management.resource.Resource r : regs) {
                // Create a Registrant instance for every resource in regs
                Registrant item = new Registrant();

                String userId = r.getProperty("External Reference ID");
                String handle = r.getProperty("Handle");
                String regDate = r.getProperty("Registration Date");

                // rating and reliability may be null
                String rating = r.getProperty("Rating");
                String reliability = r.getProperty("Reliability");

                item.setHandle(handle);
                item.setUserId(Long.valueOf(userId));

                try {
                    item.setRating(Double.valueOf(rating));
                } catch (Exception ex) {
                    // if any exception occurs, set rating to null
                    item.setRating(null);
                }

                try {
                    item.setReliability(Double.valueOf(reliability));
                } catch (Exception ex) {
                    // if any exception occurs, set reliability to null
                    item.setReliability(null);
                }

                DateFormat format = new SimpleDateFormat("MM.dd.yyyy hh:mm a");

                item.setRegistrationDate((Date) format.parse(regDate));

                Long[] submissionIds = r.getSubmissions();

                // set the property submission date if there is at least one submission
                if (submissionIds.length > 0) {

                    Long max = Long.MIN_VALUE;

                    // pick up the largest submission ID
                    for (Long id : submissionIds) {
                        if (id.compareTo(max) > 0) {
                            max = id;
                        }
                    }

                    // Get the submission instance with UploadManager
                    Submission submission = this.uploadManager.getSubmission(max.longValue());

                    // Set the submission date
                    item.setSubmissionDate(submission.getCreationTimestamp());

                }

                // finally add the Registrant into the result list
                result.add(item);
            }

            return result;

        } catch (Exception ex) {
            // if any exception occurs, log it and wrap into ContestServiceException and throw out
            logger.error("Operation failed when calling getRegistrantsForProject", ex);
            throw new ContestServiceException("Operation failed when calling getRegistrantsForProject", ex);
        } finally {
            // log the exit of method
            logger.debug("Exits getRegistrantsForProject with parameter [TCSubject " + tcSubject.getUserId() + ", projectId ="
                    + projectId + " ].");
        }
    }

    /**
     * Adds the given user as a new reviewer to the given project id.
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user.
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     * @return the added resource id
     * @throws ContestServiceException if any error occurs from UploadServices
     * @throws IllegalArgumentException if any id is &lt; 0
     * @since 1.6.5
     */
    public com.topcoder.management.resource.Resource addReviewer(TCSubject tcSubject, long projectId, long userId)
        throws ContestServiceException {
        logger.debug("addReviewer (tcSubject = " + tcSubject.getUserId() + ", " + projectId + "," + userId + ")");

        try {
            return uploadExternalServices.addReviewer(projectId, userId);
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        } catch (PhaseManagementException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        }
    }

    /**
     * Adds the given user as a new reviewer to the given project id.
     *
     * @param tcSubject TCSubject instance contains the login security info for the current user.
     * @param projectId the project to which the user needs to be added
     * @param userId    the user to be added
     * @return the added resource id
     * @throws ContestServiceException if any error occurs from UploadServices
     * @throws IllegalArgumentException if any id is &lt; 0
     * @since 1.6.5
     */
    public com.topcoder.management.resource.Resource addPrimaryScreener(TCSubject tcSubject, long projectId, long userId)
        throws ContestServiceException {
        logger.debug("addPrimaryScreener (tcSubject = " + tcSubject.getUserId() + ", " + projectId + "," + userId + ")");

        try {
            return uploadExternalServices.addPrimaryScreener(projectId, userId);
        } catch (UploadServicesException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        } catch (PhaseManagementException e) {
            logger.error("Operation failed in the uploadExternalServices.", e);
            throw new ContestServiceException("Operation failed in the uploadExternalServices.", e);
        }
    }

    /**
     * <p>Gets the review for specified submission.</p>
     *
     * @param projectId a <code>long</code> providing the project ID.
     * @param reviewerResourceId a <code>long</code> providing the ID for reviewer resource.
     * @param submissionId a <code>long</code> providing the ID for submission.
     * @return a <code>ScorecardReviewData</code> providing the details for review or <code>null</code> if review and
     *         scorecard is not found,
     * @since 1.6.5
     */
    public ScorecardReviewData getReview(long projectId, long reviewerResourceId, long submissionId) {
        List<ScorecardReviewData> data = projectServices.getScorecardAndReviews(projectId, reviewerResourceId);
        for (ScorecardReviewData r : data) {
            Review review = r.getReview();
            if (review != null) {
                if (review.getSubmission() == submissionId) {
                    return r;
                }
            }
        }

        data.get(0).setReview(null);
        return data.get(0);
    }

    /**
     * <p>Gets the <code>ScorecardReviewData</code> data for a specified submission.</p>
     *
     * @param projectId a <code>long</code> providing the project ID.
     * @param reviewerResourceId a <code>long</code> providing the ID for reviewer resource.
     * @param submissionId a <code>long</code> providing the ID for submission.
     * @return a <code>ScorecardReviewData</code> providing the details for review or <code>null</code> if review and
     *         scorecard is not found,
     * @since 1.6.9
     */
    private ScorecardReviewData getCheckpointReview(long projectId, long reviewerResourceId, long submissionId) {
        List<ScorecardReviewData> data = projectServices.getScorecardAndCheckpointReviews(projectId, reviewerResourceId);
        for (ScorecardReviewData r : data) {
            Review review = r.getReview();
            if (review != null) {
                if (review.getSubmission() == submissionId) {
                    return r;
                }
            }
        }

        data.get(0).setReview(null);
        return data.get(0);
    }

    /**
     * <p>Gets the screening for specified submission.</p>
     *
     * @param projectId a <code>long</code> providing the project ID.
     * @param screenerResourceId a <code>long</code> providing the ID for screener resource.
     * @param submissionId a <code>long</code> providing the ID for submission.
     * @return a <code>ScorecardReviewData</code> providing the details for review or <code>null</code> if review and
     *         scorecard is not found,
     * @since 1.6.5
     */
    public ScorecardReviewData getScreening(long projectId, long screenerResourceId, long submissionId) {
        List<ScorecardReviewData> data = projectServices.getScorecardAndScreening(projectId, screenerResourceId);
        for (ScorecardReviewData r : data) {
            Review review = r.getReview();
            if (review != null) {
                if (review.getSubmission() == submissionId) {
                    return r;
                }
            }
        }

        return data.get(0);
    }

    /**
     * <p>Gets the submissions for specified software project.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a project.
     * @return a <code>List</code> listing the submissions for project.
     * @throws SearchBuilderException if an unexpected error occurs.
     * @throws UploadPersistenceException if an unexpected error occurs.
     * @since 1.6.5
     */
    public Submission[] getSoftwareProjectSubmissions(TCSubject currentUser, long projectId)
        throws SearchBuilderException, UploadPersistenceException, PermissionServiceException {

        checkSoftwareContestPermission(currentUser, projectId, true);

        Filter filter = SubmissionFilterBuilder.createProjectIdFilter(projectId);
        Filter filter2 = SearchBundle.buildNotFilter(SubmissionFilterBuilder.createSubmissionStatusIdFilter(SUBMISSION_DELETE_STATUS_ID));
        Filter andfilter = SearchBundle.buildAndFilter(filter, filter2);
        return uploadManager.searchSubmissions(andfilter);
    }

    /**
     * <p>Gets the active submissions for specified project with the specified submission type.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a project.
     * @param submissionType a <code>int</code> providing the id of the submission type.
     * @return a <code>List</code> listing the checkpoint submissions for project.
     * @throws SearchBuilderException if an unexpected error occurs.
     * @throws UploadPersistenceException if an unexpected error occurs.
     * @since 1.6.9
     */
    public Submission[] getSoftwareActiveSubmissions(long projectId, int submissionType)
        throws SearchBuilderException, UploadPersistenceException {
        Filter filter = SubmissionFilterBuilder.createProjectIdFilter(projectId);
        Filter filter2 = SubmissionFilterBuilder.createSubmissionStatusIdFilter(SUBMISSION_ACTIVE_STATUS_ID);
        Filter filter3 = SubmissionFilterBuilder.createSubmissionStatusIdFilter(SUBMISSION_NO_WIN_STATUS_ID);
        Filter filter4 = new OrFilter(filter2, filter3);
        Filter filter5 = SubmissionFilterBuilder.createSubmissionTypeIdFilter(submissionType);
        Filter andFilter = new AndFilter(Arrays.asList(new Filter[] {filter, filter4, filter5}));
        return uploadManager.searchSubmissions(andFilter);
    }

    /**
     * <p>Gets the active uploads for specified project with the specified upload type.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a project.
     * @param uploadType an <code>int</code> providing the id of the upload  type.
     * @return the retrieved uploads
     * @throws SearchBuilderException if an unexpected error occurs.
     * @throws UploadPersistenceException if an unexpected error occurs.
     * @since 1.8.6
     */
    public Upload[] getActiveUploads(long projectId, int uploadType)
        throws SearchBuilderException, UploadPersistenceException {
        Filter filter = UploadFilterBuilder.createProjectIdFilter(projectId);
        Filter filter2 = UploadFilterBuilder.createUploadStatusIdFilter(UPLOAD_ACTIVE_STATUS_ID);
        Filter filter3 = UploadFilterBuilder.createUploadTypeIdFilter(uploadType);
        Filter andFilter = new AndFilter(Arrays.asList(new Filter[] {filter, filter2, filter3}));
        return uploadManager.searchUploads(andFilter);
    }

    /**
     * <p>Creates specified review for software project.</p>
     *
     * @param review a <code>Review</code> providing the details for review to be created.
     * @throws ReviewManagementException if an unexpected error occurs.
     * @since 1.6.5
     */
    public void createReview(Review review) throws ReviewManagementException {
        projectServices.createReview(review);
    }

    /**
     * <p>Selects copilot for specified TC Direct project.</p>
     *
     * <p>
     * Updates in version 1.8.5 (Release Assembly - TC Direct Cockpit Release Four)
     * - add codes to send copilot winner email to the chosen copilot
     * </p>
     *
     * <p>
     * Updates in version 1.9.6
     * - Do the copilot and 2nd place pick up in one transaction
     * </p>
     *
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param tcDirectProjectId a <code>long</code> providing the TC Direct project ID.
     * @param copilotPostingProjectId a <code>long</code> providing the ID for <code>Copilot Posting</code> contest.
     * @param winnerProfileId a <code>long</code> providing the winner copilot profile ID.
     * @param winnerSubmissionId a <code>String</code> providing the winner copilot submission ID.
     * @param secondPlaceSubmissionId a <code>String</code> providing the second place copilot submission ID.
     *
     * @throws PermissionServiceException if current user is not allowed to perform the specified action.
     * @throws ContestServiceException if an unexpected error occurs.
     */
    public void selectCopilot(TCSubject currentUser, long tcDirectProjectId, long copilotPostingProjectId,
                              long winnerProfileId, long winnerSubmissionId,
                              long secondPlaceSubmissionId)
        throws PermissionServiceException, ContestServiceException {

        logger.debug("selectCopilot");

        checkSoftwareProjectPermission(currentUser, tcDirectProjectId, false);
        checkSoftwareContestPermission(currentUser, copilotPostingProjectId, false);
        try {

            // Find a review for specified resource and submission and if not exists then create one
            Submission[] submissions = getSoftwareProjectSubmissions(currentUser, copilotPostingProjectId);
            SoftwareCompetition softwareCompetition = getSoftwareContestByProjectId(currentUser, copilotPostingProjectId);

            // Create copilot project for winning copilot only
            if (winnerProfileId > 0) {
                insertCopilotProject(tcDirectProjectId, winnerProfileId, currentUser);

                // retrieve user id
                CopilotProfile copilotProfile = copilotProfileDAO.retrieve(winnerProfileId);
                long userId = copilotProfile.getUserId();

                // create project permission
                ProjectPermission permission = new ProjectPermission();
                permission.setPermission(COPILOT_PERMISSION);
                permission.setProjectId(tcDirectProjectId);
                permission.setUserId(userId);
                permission.setStudio(false);
                permission.setHandle(userService.getUserHandle(userId));

                // get the direct project
                final ProjectData project = projectService.getProject(currentUser, tcDirectProjectId);

                // set project name
                permission.setProjectName(project.getName());

                // retrieve user permissions
                Map<Long, Map<Long, Long>> userPermissionMaps = getUserPermissionMaps(currentUser);

                if (userPermissionMaps.containsKey(tcDirectProjectId)
                        && userPermissionMaps.get(tcDirectProjectId)
                        .containsKey(userId)) {
                    // update permission
                    permission.setUserPermissionId(userPermissionMaps.get(tcDirectProjectId).get(userId));
                } else {
                    // add permission
                    permission.setUserPermissionId(-1L);
                }

                // update project permissions
                List<ProjectPermission> permissionsToAdd = new ArrayList<ProjectPermission>();
                permissionsToAdd.add(permission);
                permissionServiceFacade.updateProjectPermissions(currentUser,
                        permissionsToAdd, ResourceRole.RESOURCE_ROLE_COPILOT_ID);

                // send an email to the chosen copilot
                sendCopilotWinnerEmail(userId, project, softwareCompetition);
            }

            //TO REMOVE, handle existing copilot postings that have screening
            /*if (hasScreening(softwareCompetition))
            {
                // Find the screener resource for current user; if there is none then create one
                com.topcoder.management.resource.Resource screener
                    = addPrimaryScreener(currentUser, copilotPostingProjectId, currentUser.getUserId());
				
				long screeningPhaseId = getScreening(softwareCompetition).getId();

                // we will pass screening for all
                for (int i = 0; i < submissions.length; i++) {
                    Submission submission = submissions[i];
                    ScorecardReviewData screeningData = getScreening(copilotPostingProjectId, screener.getId(), submission.getId());
                     if ((screeningData.getReview() == null)
                        || (screeningData.getReview().getSubmission() != submission.getId())) {
                        createScreening(screener, submission.getId(), screeningData.getScorecard(), screeningPhaseId);
                    }
                }
            }*/



            // Find the Reviewer resource for current user; if there is none then create one
            com.topcoder.management.resource.Resource reviewer
                = addReviewer(currentUser, copilotPostingProjectId, currentUser.getUserId());
            long reviewPhaseId = getReview(softwareCompetition).getId();

            if(winnerSubmissionId > 0) {
                ScorecardReviewData reviewData = getReview(copilotPostingProjectId, reviewer.getId(), winnerSubmissionId);

                if ((reviewData.getReview() == null) || (reviewData.getReview().getSubmission() != winnerSubmissionId)) {
                    createReview(reviewer, winnerSubmissionId, 1, reviewData.getScorecard(), reviewPhaseId);
                }
            }

            if(secondPlaceSubmissionId > 0) {
                ScorecardReviewData reviewData = getReview(copilotPostingProjectId, reviewer.getId(), secondPlaceSubmissionId);
                if ((reviewData.getReview() == null) || (reviewData.getReview().getSubmission() != secondPlaceSubmissionId)) {
                    createReview(reviewer, secondPlaceSubmissionId, 2, reviewData.getScorecard(), reviewPhaseId);
                }
            }


            // Fill scorecards for non-winning submissions if necessary
            for (int i = 0; i < submissions.length; i++) {
                Submission submission = submissions[i];
                if(winnerSubmissionId == submission.getId() || secondPlaceSubmissionId == submission.getId()) {
                    continue;
                }
                ScorecardReviewData reviewData = getReview(copilotPostingProjectId, reviewer.getId(), submission.getId());
                if ((reviewData.getReview() == null)
                        || (reviewData.getReview().getSubmission() != submission.getId())) {
                    createReview(reviewer, submission.getId(), 3, reviewData.getScorecard(), reviewPhaseId);
                }
            }

        } catch (UserServiceException e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to select copilot", e);
        } catch (CopilotDAOException e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to select copilot", e);
        } catch (ContestServiceException e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to select copilot", e);
        } catch (SearchBuilderException e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to select copilot", e);
        } catch (UploadPersistenceException e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to select copilot", e);
        } catch (ReviewManagementException e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to select copilot", e);
        } catch (PersistenceFault e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to select copilot", e);
        } catch (ProjectNotFoundFault e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to select copilot", e);
        } catch (AuthorizationFailedFault e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to select copilot", e);
        } catch (EmailMessageGenerationException ege) {
            sessionContext.setRollbackOnly();
            logger.error(ege.getMessage());
            throw new ContestServiceException("Failed to generate email for copilot contest winner", ege);
        } catch (EmailSendingException ese) {
            sessionContext.setRollbackOnly();
            logger.error(ese.getMessage());
            throw new ContestServiceException("Failed to send email to copilot contest winner", ese);
        }
    }

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
            throws PermissionServiceException, ContestServiceException {
        // check permissions
        for (CopilotProject copilotProject : copilotProjects) {
            checkSoftwareProjectPermission(currentUser,
                    copilotProject.getTcDirectProjectId(), false);
        }

        try {
            // retrieve user permissions
            Map<Long, Map<Long, Long>> userPermissionMaps = getUserPermissionMaps(currentUser);

            // generate project permissions
            Map<Long, String> projectNames = new HashMap<Long, String>();
            List<ProjectPermission> permissionsToAdd = new ArrayList<ProjectPermission>();

            // update copilot project
            for (int i = 0; i < copilotProjects.size(); i++) {
                CopilotProject copilotProject = copilotProjects.get(i);
                Boolean removeFlag = removeFlags.get(i);

                CopilotProfile copilotProfile = copilotProfileDAO.retrieve(copilotProject.getCopilotProfileId());
                long userId = copilotProfile.getUserId();

                if (removeFlag) {
                    // remove copilot project
                    copilotProjectDAO.delete(copilotProject.getId());

                    // set project permission
                    if (userPermissionMaps.containsKey(copilotProject.getTcDirectProjectId())
                            && userPermissionMaps.get(copilotProject.getTcDirectProjectId())
                                    .containsKey(userId)) {
                        ProjectPermission permission = new ProjectPermission();
                        permission.setPermission("");
                        permission.setProjectId(copilotProject.getTcDirectProjectId());
                        permission.setUserId(userId);
                        permission.setUserPermissionId(userPermissionMaps.get(copilotProject.getTcDirectProjectId()).get(userId));
                        permission.setStudio(false);

                        permissionsToAdd.add(permission);
                    } else {
                        // ignore, the copilot has no permission on this project
                    }
                } else {
                    // insert copilot project
                    CopilotProject cProject = insertCopilotProject(copilotProject.getTcDirectProjectId(),
                            copilotProject.getCopilotProfileId(), currentUser);
                    copilotProject.setId(cProject.getId());
                    copilotProject.setCopilotType(cProject.getCopilotType());

                    // set project permission
                    ProjectPermission permission = new ProjectPermission();
                    permission.setPermission(COPILOT_PERMISSION);
                    permission.setProjectId(copilotProject.getTcDirectProjectId());
                    permission.setUserId(userId);
                    permission.setStudio(false);
                    permission.setHandle(userService.getUserHandle(userId));

                    // set project name
                    if (!projectNames.containsKey(copilotProject.getTcDirectProjectId())) {
                        projectNames.put(copilotProject.getTcDirectProjectId(),
                                projectService.getProject(currentUser,
                                        copilotProject.getTcDirectProjectId()).getName());

                    }
                    permission.setProjectName(projectNames.get(copilotProject.getTcDirectProjectId()));

                    if (userPermissionMaps.containsKey(copilotProject.getTcDirectProjectId())
                            && userPermissionMaps.get(copilotProject.getTcDirectProjectId())
                            .containsKey(userId)) {
                        // update permission
                        permission.setUserPermissionId(userPermissionMaps.get(
                                copilotProject.getTcDirectProjectId()).get(userId));
                    } else {
                        // add permission
                        permission.setUserPermissionId(-1L);
                    }

                    permissionsToAdd.add(permission);
                }
            }

            // update project permissions
            permissionServiceFacade.updateProjectPermissions(currentUser,
                    permissionsToAdd, ResourceRole.RESOURCE_ROLE_OBSERVER_ID);

            return copilotProjects;
        } catch (CopilotDAOException e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to update copilot projects", e);
        } catch (UserServiceException e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to update copilot projects", e);
        } catch (PersistenceFault e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to update copilot projects", e);
        } catch (ProjectNotFoundFault e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to update copilot projects", e);
        } catch (AuthorizationFailedFault e) {
            sessionContext.setRollbackOnly();
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to update copilot projects", e);
        }
    }

    private boolean hasSpecReview(SoftwareCompetition SoftwareCompetition)
    {


        Set<com.topcoder.project.phases.Phase> allPhases = SoftwareCompetition.getProjectPhases().getPhases();
        for (com.topcoder.project.phases.Phase phase : allPhases) {
            PhaseType phaseType = phase.getPhaseType();
            if ("Specification Submission".equals(phaseType.getName())) {
                return true;
            }
        }

        return false;

    }

    private boolean hasScreening(SoftwareCompetition SoftwareCompetition)
    {


        Set<com.topcoder.project.phases.Phase> allPhases = SoftwareCompetition.getProjectPhases().getPhases();
        for (com.topcoder.project.phases.Phase phase : allPhases) {
            PhaseType phaseType = phase.getPhaseType();
            if ("Screening".equals(phaseType.getName())) {
                return true;
            }
        }

        return false;

    }
	
	private com.topcoder.project.phases.Phase getScreening(SoftwareCompetition SoftwareCompetition)
    {
        Set<com.topcoder.project.phases.Phase> allPhases = SoftwareCompetition.getProjectPhases().getPhases();
        for (com.topcoder.project.phases.Phase phase : allPhases) {
            PhaseType phaseType = phase.getPhaseType();
            if (PhaseType.SCREENING_PHASE.getId() == phaseType.getId()) {
                return phase;
            }
        }
        return null;
    }
	
	private com.topcoder.project.phases.Phase getReview(SoftwareCompetition SoftwareCompetition)
    {
        Set<com.topcoder.project.phases.Phase> allPhases = SoftwareCompetition.getProjectPhases().getPhases();
        for (com.topcoder.project.phases.Phase phase : allPhases) {
            PhaseType phaseType = phase.getPhaseType();
            if (PhaseType.REVIEW_PHASE.getId() == phaseType.getId()) {
                return phase;
            }
        }
        return null;
    }

    /**
     * Gets all FileType entities.
     *
     * @return the found FileType entities, return empty if cannot find any.
     * @throws ContestServiceException
     *             if there are any exceptions.
     * @since 1.6.6
     */
    public FileType[] getAllFileTypes() throws ContestServiceException {
        logger.debug("getAllFileTypes");

        try {
            return projectServices.getAllFileTypes();
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in the getAllFileTypes.", e);
            throw new ContestServiceException("Operation failed in the getAllFileTypes.", e);
        }
    }

    /**
     * Gets all the project platforms.
     *
     * @return all the project platforms avaiable.
     * @throws ContestServiceException if there is any error.
     * @since 2.0.1
     */
    public ProjectPlatform[] getAllProjectPlatforms() throws ContestServiceException {
        logger.debug("getAllProjectPlatforms");

        try {
            return projectServices.getAllProjectPlatforms();
        } catch (ProjectServicesException e) {
            logger.error("Operation failed in the getAllProjectPlatforms.", e);
            throw new ContestServiceException("Operation failed in the getAllProjectPlatforms.", e);
        }
    }

    /**
     * <p>Insert a copilot project record.</p>
     *
     * @return a <code>long</code> providing the ID of a generated copilot project,
     * @throws UserServiceException if any exception occurs when retrieving user handle.
     * @throws CopilotDAOException if any exception occurs when performing DB operation.
     */
    private CopilotProject insertCopilotProject(long tcDirectProjectId, long profileId, TCSubject tcSubject)
        throws UserServiceException, CopilotDAOException {
        CopilotProject copilotProject = new CopilotProject();

        // populate actual values
        copilotProject.setTcDirectProjectId(tcDirectProjectId);
        copilotProject.setCopilotProfileId(profileId);
        copilotProject.setCreateUser(String.valueOf(tcSubject.getUserId()));
        copilotProject.setCreateDate(new Date());
        copilotProject.setModifyUser(String.valueOf(tcSubject.getUserId()));
        copilotProject.setModifyDate(new Date());

        // populate copilot type
        for (CopilotType copilotType : lookupDAO.getAllCopilotTypes()) {
            if (copilotType.getId() == 1L) {
                copilotProject.setCopilotType(copilotType);
            }
        }
        for (CopilotProjectStatus copilotProjectStatus : lookupDAO.getAllCopilotProjectStatuses()) {
            if (copilotProjectStatus.getId() == 1L) {
                copilotProject.setStatus(copilotProjectStatus);
            }
        }
        copilotProject.setPrivateProject(false);

        // insert into DB
        copilotProjectDAO.create(copilotProject);

        return copilotProject;
    }

    /**
     * <p>Creates review for specified submission based on specified scorecard.</p>
     *
     * @param reviewer a <code>long</code> providing the reviewer ID.
     * @param submissionId a <code>long</code> providing the submission ID.
     * @param placement an <code>int</code> providing the placement.
     * @param scorecard a <code>Scorecard</code> providing the details for scorecard.
     * @throws ReviewManagementException if an unexpected error occurs.
     */
    private void createReview(com.topcoder.management.resource.Resource reviewer, long submissionId, int placement,
                              Scorecard scorecard, long projectPhaseId)
        throws ReviewManagementException {
        Review review = new Review();
        review.setAuthor(reviewer.getId());
        review.setCommitted(true);
		review.setProjectPhase(projectPhaseId);
        review.setCreationUser(String.valueOf(reviewer.getId()));
        review.setCreationTimestamp(new Date());
        review.setModificationUser(String.valueOf(reviewer.getId()));
        review.setModificationTimestamp(new Date());
        if (placement == 1) {
            review.setInitialScore(100F);
            review.setScore(100F);
        } else if (placement == 2) {
            review.setInitialScore(80F);
            review.setScore(80F);
        } else {
            review.setInitialScore(10F);
            review.setScore(10F);
        }
        review.setSubmission(submissionId);
        review.setScorecard(scorecard.getId());

        List<Item> items = new ArrayList<Item>();
        Group[] groups = scorecard.getAllGroups();
        for (int i = 0; i < groups.length; i++) {
            Group group = groups[i];
            Section[] allSections = group.getAllSections();
            for (int j = 0; j < allSections.length; j++) {
                Section section = allSections[j];
                Question[] questions = section.getAllQuestions();
                for (int k = 0; k < questions.length; k++) {
                    Question question = questions[k];
                    Item item = new Item();
                    if (placement == 1) {
                        item.setAnswer("10");
                    } else if (placement == 2) {
                        item.setAnswer("8");
                    } else {
                        item.setAnswer("1");
                    }
                    item.setQuestion(question.getId());
                    Comment comment = new Comment();
                    comment.setAuthor(reviewer.getId());
                    comment.setComment("Ok");
                    comment.setCommentType(CommentType.COMMENT_TYPE_COMMENT);
                    item.addComment(comment);
                    items.add(item);
                }
            }
        }

        review.setItems(items);
        createReview(review);
    }

    /**
     * <p>Creates screening for specified submission based on specified scorecard.</p>
     *
     * @param screener a <code>long</code> providing the screener ID.
     * @param submissionId a <code>long</code> providing the submission ID.
     * @param placement an <code>int</code> providing the placement.
     * @param scorecard a <code>Scorecard</code> providing the details for scorecard.
     * @throws ReviewManagementException if an unexpected error occurs.
     */
    private void createScreening(com.topcoder.management.resource.Resource screener, long submissionId, Scorecard scorecard, long projectPhaseId)
        throws ReviewManagementException {
        Review review = new Review();
        review.setAuthor(screener.getId());
        review.setCommitted(true);
		review.setProjectPhase(projectPhaseId);
        review.setCreationUser(String.valueOf(screener.getId()));
        review.setCreationTimestamp(new Date());
        review.setModificationUser(String.valueOf(screener.getId()));
        review.setModificationTimestamp(new Date());

        review.setInitialScore(100F);
        review.setScore(100F);

        review.setSubmission(submissionId);
        review.setScorecard(scorecard.getId());

        List<Item> items = new ArrayList<Item>();
        Group[] groups = scorecard.getAllGroups();
        for (int i = 0; i < groups.length; i++) {
            Group group = groups[i];
            Section[] allSections = group.getAllSections();
            for (int j = 0; j < allSections.length; j++) {
                Section section = allSections[j];
                Question[] questions = section.getAllQuestions();
                for (int k = 0; k < questions.length; k++) {
                    Question question = questions[k];
                    Item item = new Item();
                    // Yes
                    item.setAnswer("1");
                    item.setQuestion(question.getId());
                    Comment comment = new Comment();
                    comment.setAuthor(screener.getId());
                    comment.setComment("Ok");
                    comment.setCommentType(CommentType.COMMENT_TYPE_COMMENT);
                    item.addComment(comment);
                    items.add(item);
                }
            }
        }

        review.setItems(items);
        createReview(review);
    }

    /**
     * Get user permission maps.
     *
     * @param currentUser
     *            current user
     * @return retrieved user permission map
     * @throws PermissionServiceException
     *             if current user has no permission on it
     */
    private Map<Long, Map<Long, Long>> getUserPermissionMaps(
            TCSubject currentUser) throws PermissionServiceException {
        List<ProjectPermission> permissions = this.permissionService.getProjectPermissions(currentUser.getUserId());
        Map<Long, Map<Long, Long>> userPermissionMaps = new HashMap<Long, Map<Long, Long>>();
        for (ProjectPermission permission : permissions) {
            if (!userPermissionMaps.containsKey(permission.getProjectId())) {
                userPermissionMaps.put(permission.getProjectId(),
                        new HashMap<Long, Long>());
            }
            userPermissionMaps.get(permission.getProjectId()).put(
                    permission.getUserId(), permission.getUserPermissionId());
        }

        return userPermissionMaps;
    }

    private Map<Integer, String> getUserPreferenceMaps(long userId,
            List<Integer> preferenceIds) throws Exception {
        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        p.put(Context.PROVIDER_URL, userBeanProviderUrl);

        Context c = new InitialContext(p);

        UserPreferenceHome userPreferenceHome = (UserPreferenceHome) c
                .lookup("com.topcoder.web.ejb.user.UserPreferenceHome");
        UserPreference userPreference = userPreferenceHome.create();
        Map<Integer, String> ret = new HashMap<Integer, String>();

        for (int preferenceId : preferenceIds) {
            String value;

            try {
                value = userPreference.getValue(userId, preferenceId,
                        DBMS.COMMON_OLTP_DATASOURCE_NAME);

            } catch (RowNotFoundException e) {
                value = "false";
            }

            ret.put(preferenceId, value);
        }

        return ret;
    }

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
     * @since 1.6.9
     */
    public String getStudioSubmissionFeedback(TCSubject tcSubject, long projectId, long submissionId, PhaseType phaseType)
        throws ContestServiceException {

        // gets the reviewer resource role id based on the phase type
        long resourceRoleId;
        if (phaseType.getId() == PhaseType.CHECKPOINT_REVIEW_PHASE.getId()) {
            resourceRoleId = ResourceRole.RESOURCE_ROLE_CHECKPOINT_REVIEWER_ID;
        } else if (phaseType.getId() == PhaseType.REVIEW_PHASE.getId()) {
            resourceRoleId = ResourceRole.RESOURCE_ROLE_REVIEWER_ID;
        } else if (phaseType.getId() == PhaseType.APPROVAL_PHASE.getId()) {
            resourceRoleId = ResourceRole.RESOURCE_ROLE_APPROVER_ID;
        } else {
            throw new ContestServiceException("The phaseType can only be Checkpoint Review phase or Review phase.");
        }

        // gets the reviewer resource, the user of reviewer resource must be current user
        com.topcoder.management.resource.Resource reviewerResource = null;

        com.topcoder.management.resource.Resource[] resources = projectServices.searchResources(projectId, resourceRoleId);

        if (resources.length == 0) {
            return "";
        } else if (resources.length == 1) {
            reviewerResource = resources[0];
        } else {
            throw new ContestServiceException("There should be exactly one Checkpoint Reviewer or Reviewer.");
        }

        // gets the review data
        ScorecardReviewData reviewData;
        if (phaseType.getId() == PhaseType.CHECKPOINT_REVIEW_PHASE.getId()) {
            reviewData = getCheckpointReview(projectId, reviewerResource.getId(), submissionId);
        } else if (phaseType.getId() == PhaseType.APPROVAL_PHASE.getId()) {
            reviewData = getReview(projectId, reviewerResource.getId(), submissionId);
        } else {
            reviewData = getReview(projectId, reviewerResource.getId(), submissionId);
        }
        if (reviewData.getReview() == null) {
            return "";
        }
        return reviewData.getReview().getItem(0).getComment(0).getComment();
    }

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
     * @since 1.6.9
     */
    public void saveStudioSubmisionWithRankAndFeedback(TCSubject tcSubject, long projectId, long submissionId,
            int placement, String feedback, Boolean committed, PhaseType phaseType)
        throws ContestServiceException {

        try {
            // gets the reviewer resoruce role id based on the phase type
            long resourceRoleId;
            if (phaseType.getId() == PhaseType.CHECKPOINT_REVIEW_PHASE.getId()) {
                resourceRoleId = ResourceRole.RESOURCE_ROLE_CHECKPOINT_REVIEWER_ID;
            } else if (phaseType.getId() == PhaseType.REVIEW_PHASE.getId()) {
                resourceRoleId = ResourceRole.RESOURCE_ROLE_REVIEWER_ID;
            } else {
                throw new ContestServiceException("The phaseType can only be Checkpoint Review phase or Review phase.");
            }

            // gets the reviewer resource, the user of reviewer resource must be current user
            com.topcoder.management.resource.Resource reviewerResource = null;
            com.topcoder.management.resource.Resource[] resources = projectServices.searchResources(projectId, resourceRoleId);

            FullProjectData contestData = projectServices.getFullProjectData(projectId);

            com.topcoder.project.phases.Phase[] phases = contestData.getAllPhases();

            if (contestData.getProjectHeader().getProjectCategory().getId() ==
                    ProjectCategory.DESIGN_FIRST2FINISH.getId()
                    && phaseType.getId() == PhaseType.REVIEW_PHASE.getId()) {
                // special handling for Design First2Finish

                boolean isRegClosed = true;
                boolean isSubClosed = true;

                com.topcoder.project.phases.Project projectPhases = projectServices.getPhases(projectId);

                Date currentDate = new Date();

                for (com.topcoder.project.phases.Phase phase : projectPhases.getAllPhases()) {
                    if (phase.getPhaseType().getId() == PhaseType.REGISTRATION_PHASE.getId()) {
                        if (phase.getPhaseStatus().getId() == PhaseStatus.OPEN.getId()) {
                            isRegClosed = false;
                            phase.setPhaseStatus(PhaseStatus.CLOSED);
                            phase.setScheduledEndDate(currentDate);
                            phase.setActualEndDate(currentDate);
                        }
                    }
                    if (phase.getPhaseType().getId() == PhaseType.SUBMISSION_PHASE.getId()) {
                        if (phase.getPhaseStatus().getId() == PhaseStatus.OPEN.getId()) {
                            isSubClosed = false;
                            phase.setPhaseStatus(PhaseStatus.CLOSED);
                            phase.setScheduledEndDate(currentDate);
                            phase.setActualEndDate(currentDate);
                        }
                    }
                }

                if(!isRegClosed || !isSubClosed) {
                    projectServices.updatePhases(projectPhases, String.valueOf(tcSubject.getUserId()));
                }
            }


            if (resources.length == 0) {
                // no reviewer resource, add the current user as reviewer resource
                com.topcoder.project.phases.Phase targetPhase = null;
                for (com.topcoder.project.phases.Phase phase : phases) {
                    if (phase.getPhaseType().getId() == phaseType.getId()) {
                        targetPhase = phase;
                        break;
                    }
                }
                assignRole(tcSubject, projectId, resourceRoleId, tcSubject.getUserId(), targetPhase, true, true, true, false);

                for (com.topcoder.management.resource.Resource resource : projectServices.searchResources(projectId, resourceRoleId)) {
                    if (Long.parseLong(resource.getProperty(RESOURCE_INFO_EXTERNAL_REFERENCE_ID)) == tcSubject.getUserId()) {
                        reviewerResource = resource;
                        break;
                    }
                }

                if (reviewerResource == null) {
                // failed to add the current user as reviwer resource
                throw new ContestServiceException("Failed to add the current user as reviewer/checkpoint reviewer resource.");
                }
            } else if (resources.length == 1) {
                reviewerResource = resources[0];
            } else {
                throw new ContestServiceException("There should be exactly one Checkpoint Reviewer or Reviewer.");
            }

            // gets the review data
            ScorecardReviewData reviewData;
            if (phaseType.getId() == PhaseType.CHECKPOINT_REVIEW_PHASE.getId()) {
                reviewData = getCheckpointReview(projectId, reviewerResource.getId(), submissionId);
            } else {
                reviewData = getReview(projectId, reviewerResource.getId(), submissionId);
            }
			
			com.topcoder.project.phases.Phase targetPhase = null;
			for (com.topcoder.project.phases.Phase phase : phases) {
				if (phase.getPhaseType().getId() == phaseType.getId()) {
					targetPhase = phase;
					break;
				}
			}
				
            Scorecard scorecard = reviewData.getScorecard();
            if (reviewData.getReview() == null) {
                // no review board yet, create a new review
                Review review = new Review();
                review.setAuthor(reviewerResource.getId());
                review.setCommitted(committed);
                review.setCreationUser(String.valueOf(tcSubject.getUserId()));
				review.setProjectPhase(targetPhase.getId());
                review.setCreationTimestamp(new Date());
                review.setModificationUser(String.valueOf(tcSubject.getUserId()));
                review.setModificationTimestamp(new Date());
                review.setSubmission(submissionId);
                review.setScorecard(scorecard.getId());

                List<Item> items = new ArrayList<Item>();
                int rate = 11 - placement;
                for (Group group : scorecard.getAllGroups()) {
                    for (Section section : group.getAllSections()) {
                        for (Question question : section.getAllQuestions()) {
                            Item item = new Item();
                            item.setAnswer(String.valueOf(rate) + "/10");
                            item.setQuestion(question.getId());
                            Comment comment = new Comment();
                            comment.setAuthor(reviewerResource.getId());
                            comment.setComment(feedback == null ? "" : feedback);
                            comment.setCommentType(CommentType.COMMENT_TYPE_COMMENT);
                            item.addComment(comment);
                            items.add(item);
                        }
                    }
                }

                review.setItems(items);
                review.setInitialScore(10.0f * rate);
                review.setScore(10.0f * rate);
                projectServices.createReview(review);
            } else {
                // update the exists review board
                Review review = reviewData.getReview();
                review.setCommitted(committed);
                review.setModificationUser(String.valueOf(tcSubject.getUserId()));
                review.setModificationTimestamp(new Date());
                int itemIndex = 0;
                int rate = 11 - placement;
                for (Group group : scorecard.getAllGroups()) {
                    for (Section section : group.getAllSections()) {
                        for (Question question : section.getAllQuestions()) {
                            Item item = review.getItem(itemIndex++);
                            item.setAnswer(String.valueOf(rate) + "/10");
                            for (Comment comment : item.getAllComments()) {
                                if (feedback != null) {
                                    comment.setComment(feedback);
                                }
                            }
                        }
                    }
                }
                review.setInitialScore(10.0f * rate);
                review.setScore(10.0f * rate);
                projectServices.updateReview(review);
            }
        } catch (ReviewManagementException e) {
            throw new ContestServiceException("Error occurs when saving the review.", e);
        }
    }

    /**
     * <p>Update the software submissions.</p>
     *
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param submissions a <code>List</code> providing the submissions to be updated.
     * @throws ContestServiceException if any error occurs.
     * @since 1.6.9
     */
    public void updateSoftwareSubmissions(TCSubject currentUser, List<Submission> submissions) throws ContestServiceException {
        try {
            for (Submission submission : submissions) {
                uploadManager.updateSubmission(submission, String.valueOf(currentUser.getUserId()));
            }
        } catch (UploadPersistenceException e) {
            throw new ContestServiceException("Error occurs when updating submission.", e);
        }
    }

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
     * @since 1.8.7
     */
    public void saveSoftwareCheckpointReviewWithRankAndFeedback(TCSubject tcSubject, long projectId,
        long[] submissionIds, int[] placements, String[] feedbacks, Boolean committed, String generalFeedback)
        throws ContestServiceException {
        try {
            checkSoftwareContestPermission(tcSubject, projectId, false);

            // reuse #saveStudioSubmisionWithRankAndFeedback
            for (int i = 0; i < submissionIds.length; ++i) {
                saveStudioSubmisionWithRankAndFeedback(tcSubject, projectId, submissionIds[i], placements[i],
                    feedbacks[i], committed, PhaseType.CHECKPOINT_REVIEW_PHASE);
            }

            // save general feedback
            projectServices.saveSoftwareCheckpointSubmissionsGeneralFeedback(projectId, generalFeedback);

            if (committed) {
                Long jiveCategoryId = null;
                if (createForum) {
                    jiveCategoryId = getSoftwareContestByProjectId(tcSubject, projectId).getAssetDTO().getForum().getJiveCategoryId();
                }
                if (jiveCategoryId != null && jiveCategoryId > 0) {
                    Forums forums = getSoftwareForums();
                    if (generalFeedback != null && generalFeedback.trim().length() > 0) {
                        // post the general feedback thread
                        forums.postThreadToQuestionForum(jiveCategoryId, "General Checkpoint Feedback", generalFeedback, tcSubject.getUserId());
                    }
                    for (int i = 0; i < submissionIds.length; i++) {
                        if (feedbacks[i] != null && feedbacks[i].trim().length() > 0) {
                            forums.postThreadToQuestionForum(jiveCategoryId, "#" + submissionIds[i] + " Checkpoint Feedback", feedbacks[i], tcSubject.getUserId());
                        }
                    }
                }
            }
        } catch (ProjectServicesException e) {
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to save software checkpoint review with rank and feedback", e);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ContestServiceException("Failed to save software checkpoint review with rank and feedback", e);
        }
    }


    /**
     * Add review auction if needed.
     *
     * @param softwareCompetition the contest
     * @throws com.topcoder.management.review.application.ReviewAuctionManagerException if fail to create review auction.
     * @throws ContestEligibilityPersistenceException if fail to get contest eligibility
     * @since 1.9.5
     */
    private void addReviewAuction(SoftwareCompetition softwareCompetition) throws ReviewAuctionManagerException, ContestEligibilityPersistenceException {  
        if (isStudio(softwareCompetition)) {
            return;
        }

        if (EXCLUDE_REVIEW_AUCTION_CATEGORIES.contains(
                softwareCompetition.getProjectHeader().getProjectCategory().getId())) {
            return;
        }
		
        boolean hasEligibility = contestEligibilityManager.haveEligibility(
                new Long[]{softwareCompetition.getProjectHeader().getId()}, false).size() > 0;
        long billingProjectId = getBillingProjectId(softwareCompetition);

        // if creating contest, eligibility is not committed, so above will not get back result
        if (billingProjectId != 0
                && softwareCompetition.getProjectHeader().getSecurityGroupId() > 0) {
            hasEligibility = true;
        }

        if (hasEligibility) {
            return;
        }

        boolean hasSpecReview = false;
        boolean hasReview = false;
		boolean hasIterativeReview = false;
        for (com.topcoder.project.phases.Phase p : softwareCompetition.getProjectPhases().getPhases()) {
            if (p.getPhaseType().getId() == PhaseType.SPECIFICATION_REVIEW_PHASE.getId()) {
                hasSpecReview = true;
            }
            if (p.getPhaseType().getId() == PhaseType.REVIEW_PHASE.getId()) {
                hasReview = true;
            }
			if (p.getPhaseType().getId() == PhaseType.ITERATIVE_REVIEW_PHASE.getId()) {
                hasIterativeReview = true;
            }
        }

        if (hasSpecReview) {
            ReviewAuction reviewAuction = new ReviewAuction();
            reviewAuction.setProjectId(softwareCompetition.getProjectHeader().getId());
            reviewAuction.setAuctionType(SPEC_REVIEW_TYPE);
            reviewAuctionManager.createAuction(reviewAuction);
        }
		
		if (hasIterativeReview) {
            ReviewAuction reviewAuction = new ReviewAuction();
            reviewAuction.setProjectId(softwareCompetition.getProjectHeader().getId());
            reviewAuction.setAuctionType(ITERATIVE_REVIEW_TYPE);
            reviewAuctionManager.createAuction(reviewAuction);
        }
		
        if (hasReview) {
            ReviewAuction reviewAuction = new ReviewAuction();
            reviewAuction.setProjectId(softwareCompetition.getProjectHeader().getId());
			
			
			
            if (softwareCompetition.getProjectHeader().getProjectCategory().getId() == ProjectCategory.DEVELOPMENT.getId()) {
                reviewAuction.setAuctionType(DEV_REVIEW_TYPE);
            } else if (softwareCompetition.getProjectHeader().getProjectCategory().getId() == ProjectCategory.CODE.getId()) {
                reviewAuction.setAuctionType(CODE_REVIEW_TYPE);
            } else {
                reviewAuction.setAuctionType(REGULAR_REVIEW_TYPE);
            }
            reviewAuctionManager.createAuction(reviewAuction);
        }
    }
    /**
     * Check cca status of contest. If billing account must use cca while current property not, the change it
     * 
     * @param contest the contest
     * @throws PersistenceException
     * 
     * @since 1.9.6
     */
    private void checkBillingProjectCCA(SoftwareCompetition contest) throws PersistenceException {

        String projectCCA = contest.getProjectHeader().getProperty(ProjectPropertyType.CONFIDENTIALITY_TYPE_PROJECT_PROPERTY_KEY);

        Map<String, String> allProperties = contest.getProjectHeader().getAllProperties();

        if (projectCCA.toLowerCase().equals(CONFIDENTIALITY_PUBLIC)){
            String billingProject = contest.getProjectHeader().getProperty(ProjectPropertyType.BILLING_PROJECT_PROJECT_PROPERTY_KEY);
            if (requireBillingProjectCCA(Long.valueOf(billingProject))){
                contest.getProjectHeader().setProperty(ProjectPropertyType.CONFIDENTIALITY_TYPE_PROJECT_PROPERTY_KEY, CONFIDENTIALITY_STANDARD);
            }
        }
    }
    /**
     * Check if the array of billing project required CCA.
     * 
     * @param billingProjectId 
     * @return boolean status of cca required
     * @throws PersistenceException if any other error occurs.
     * 
     * @since 1.9.6
     */
    public boolean requireBillingProjectCCA(long billingProjectId) throws PersistenceException {
         return projectManager.requireBillingProjectCCA(billingProjectId);
    }
    
    /**
     * Check if the array of billing project required CCA.
     * 
     * @param billingProjectId array of billing project id
     * @return array of boolean status of cca required
     * @throws PersistenceException if any other error occurs.
     * 
     * @since 1.2.5
     */
    public boolean[] requireBillingProjectsCCA(long[] billingProjectIds) throws PersistenceException {
        return projectManager.requireBillingProjectsCCA(billingProjectIds);
    }

    /**
     * Check whether the project is private
     *
     * @param contest
     * @return True if project if private
     * @since 3.4
     */
    private boolean isTask(SoftwareCompetition contest){
        return contest.getProjectHeader().getProperty(ProjectPropertyType.TASK_FLAG) != null && "1".equals(contest.getProjectHeader().getProperty(ProjectPropertyType.TASK_FLAG));
    }

    /**
     * Close project immediately and pick winner
     *
     * @param tcSubject TCSubject
     * @param projectId project id
     * @param winnerId user id of choosen winner
     * @throws ContestServiceException
     * @since 3.5
     */
    public void closeSoftwareContest(TCSubject tcSubject, long projectId, long winnerId) throws ContestServiceException {
        logger.debug("Entering #closeSoftwareContest");

        try {
            Project contest = projectServices.getProject(projectId);

            if (contest.getProjectStatus().getId() != ProjectStatus.ACTIVE.getId()) {
                logger.error("Closing challenge only applicable against ACTIVE challenge");
                throw new ContestServiceException("Close challenge only applicable against ACTIVE challenge");
            }

            com.topcoder.management.resource.Resource[] regs = this.projectServices.searchResources(contest.getId(),
                    ResourceRole.RESOURCE_ROLE_SUBMITTER);
            boolean notRegistrant = true;
            for (com.topcoder.management.resource.Resource r : regs) {
                if (r.getUserId() == winnerId) {
                    notRegistrant = false;
                    break;
                }
            }
            if (notRegistrant) {
                logger.error("userId " + String.valueOf(winnerId) + " is not registered to this challenge");
                throw new ContestServiceException("This user is not registered to this challenge");
            }

            com.topcoder.project.phases.Project projectPhases = projectServices.getPhases(contest.getId());
            com.topcoder.project.phases.Phase[] phases = projectPhases.getAllPhases();
            boolean needToUpdate = false;
            //make sure we have submission phase is opened if not update it
            for (com.topcoder.project.phases.Phase phase : phases) {
                if (PROJECT_SUBMISSION_PHASE_NAME.equals(phase.getPhaseType().getName())) {
                    if (phase.getPhaseStatus().getId() != PhaseStatus.OPEN.getId()) {
                        needToUpdate = true;
                        phase.setPhaseStatus(PhaseStatus.OPEN);
                    }
                } else if (PROJECT_ITERATIVE_REVIEW_PHASE_NAME.equals(phase.getPhaseType().getName()) ||
                        PROJECT_REVIEW_PHASE_NAME.equals(phase.getPhaseType().getName())){
                    //check whether iterative/review open and winner has been choosen
                    //which mean challenge has been close
                    if (phase.getPhaseStatus().getId() == PhaseStatus.OPEN.getId()) {
                        Submission[] submissions = uploadManager.getProjectSubmissions(contest.getId());
                        for (Submission submission : submissions){
                            if(submission.getPlacement() != null && submission.getPlacement() == 1L){
                                logger.error("Challenge has been closed");
                                throw new ContestServiceException("Winner for this challenge has been selected or " +
                                "challenge has been closed before");
                            }
                        }
                    }
                }
            }
            if (needToUpdate) {
                projectPhases.setPhases(new HashSet<com.topcoder.project.phases.Phase>(Arrays.asList(phases)));
                projectServices.updatePhases(projectPhases, String.valueOf(tcSubject.getUserId()));
            }

            //upload dummy submission for winner
            new FileOutputStream(mockSubmissionFilePath + mockSubmissionFileName, false).close();
            DataHandler dataHandler = new DataHandler(new FileDataSource(mockSubmissionFilePath +
                    mockSubmissionFileName));
            long submissionId = uploadSubmission(winnerId, contest.getId(), mockSubmissionFileName, dataHandler);

            //close submission and review phase
            com.topcoder.project.phases.Phase submissionPhase = null;
            com.topcoder.project.phases.Phase reviewPhase = null;
            for (com.topcoder.project.phases.Phase phase : phases) {
                if (PROJECT_REGISTRATION_PHASE_NAME.equals(phase.getPhaseType().getName()) ||
                        PROJECT_SUBMISSION_PHASE_NAME.equals(phase.getPhaseType().getName()) ||
                        PROJECT_REVIEW_PHASE_NAME.equals(phase.getPhaseType().getName()) ||
                        PROJECT_ITERATIVE_REVIEW_PHASE_NAME.equals(phase.getPhaseType().getName())) {
                    Date currentDate = new Date();
                    //length 1 hour
                    long length = 60 * MINUTE_IN_MILIS;
                    //reg start 3h before
                    Date regStartDate = new Date(currentDate.getTime() - 180 * MINUTE_IN_MILIS);
                    //submision start 2h before
                    Date submissionStartDate = new Date(currentDate.getTime() - 120 * MINUTE_IN_MILIS);
                    //submission end / review start 1h before
                    Date submissionEndDate = new Date(currentDate.getTime() - 60 * MINUTE_IN_MILIS);

                    if (PROJECT_SUBMISSION_PHASE_NAME.equals(phase.getPhaseType().getName())) {
                        phase.setScheduledStartDate(submissionStartDate);
                        phase.setActualStartDate(submissionStartDate);
                        phase.setScheduledEndDate(submissionEndDate);
                        phase.setActualEndDate(submissionEndDate);
                        phase.setLength(length);
                        phase.setPhaseStatus(PhaseStatus.CLOSED);
                        submissionPhase = phase;
                    } else if (PROJECT_REGISTRATION_PHASE_NAME.equals(phase.getPhaseType().getName())) {
                        phase.setScheduledStartDate(regStartDate);
                        phase.setFixedStartDate(regStartDate);
                        phase.setActualStartDate(regStartDate);
                        phase.setScheduledEndDate(submissionEndDate);
                        phase.setActualEndDate(submissionEndDate);
                        phase.setLength(2 * length);
                        phase.setPhaseStatus(PhaseStatus.CLOSED);
                    } else {
                        phase.setScheduledStartDate(submissionEndDate);
                        phase.setActualStartDate(submissionEndDate);
                        phase.setScheduledEndDate(currentDate);
                        phase.setLength(length);
                        phase.setPhaseStatus(PhaseStatus.OPEN);
                        reviewPhase = phase;
                    }
                }
            }
            projectPhases.setPhases(new HashSet<com.topcoder.project.phases.Phase>(Arrays.asList(phases)));
            projectServices.updatePhases(projectPhases, String.valueOf(tcSubject.getUserId()));

            //set submission score and upload phase
            Submission submission = uploadManager.getSubmission(submissionId);
            submission.setInitialScore(100.0);
            submission.setFinalScore(100.0);
            submission.setPlacement(1L);
            submission.setPrize(contest.getPrizes().get(0));
            uploadManager.updateSubmission(submission, String.valueOf(tcSubject.getUserId()));

            //create reviewer, remove if there is
            com.topcoder.management.resource.Resource[] reviewers = this.projectServices.searchResources(contest.getId(),
                    ResourceRole.RESOURCE_ROLE_ITERATIVE_REVIEWER_ID);
            for (com.topcoder.management.resource.Resource r : reviewers) {
                this.projectServices.removeResource(r, String.valueOf(tcSubject.getUserId()));
            }
            com.topcoder.management.resource.Resource reviewer = createReviewerResource(tcSubject.getUserId(), contest.getId(),
                    reviewPhase.getId(), false, ProjectCategory.FIRST2FINISH.getName().equals(contest.getProjectCategory().getName()));

            reviewer = projectServices.updateResource(reviewer, String.valueOf(tcSubject.getUserId()));

            //create review
            Scorecard scorecard = projectServices.getScorecard(Long.parseLong((String) reviewPhase.getAttribute(SCORECARD_ID_ATTRIBUTE)));
            createReview(reviewer, submissionId, 1, scorecard, reviewPhase.getId());

            Upload upload = submission.getUpload();
            upload.setProjectPhase(submissionPhase.getId());
            uploadManager.updateUpload(upload, String.valueOf(tcSubject.getUserId()));
        } catch (IOException e) {
            logger.error("Failed to create submission file");
            throw new ContestServiceException("Failed to create submission file", e);
        } catch (UserServiceException e) {
            logger.error("User not found: " + String.valueOf(winnerId));
            throw new ContestServiceException("User not found: " + String.valueOf(winnerId), e);
        } catch (ReviewManagementException e) {
            logger.error("Failed to create review");
            throw new ContestServiceException("Failed to create review", e);
        } catch (ProjectServicesException e) {
            logger.error("Failed to update phase");
            throw new ContestServiceException("Failed to update phase", e);
        } catch (UploadPersistenceException e) {
            logger.error("Failed to get/update submission");
            throw new ContestServiceException("Failed to get/update submission", e);
        }
    }

    /**
     * Cancel project
     *
     * @param tcSubject TCSubject
     * @param projectId project id
     * @throws ContestServiceException
     * @since 3.5
     */
    public void cancelSoftwareContestByUser(TCSubject tcSubject, long projectId) throws ContestServiceException{
        logger.debug("Entering #cancelSoftwareContestByUser");
        try {
            Project contest = projectServices.getProject(projectId);
            com.topcoder.project.phases.Project projectPhases = projectServices.getPhases(contest.getId());
            com.topcoder.project.phases.Phase[] phases = projectPhases.getAllPhases();

            //check whether iterative/review open and winner has been choosen
            //which mean challenge has been close before
            for (com.topcoder.project.phases.Phase phase : phases) {
                if (PROJECT_ITERATIVE_REVIEW_PHASE_NAME.equals(phase.getPhaseType().getName()) ||
                        PROJECT_REVIEW_PHASE_NAME.equals(phase.getPhaseType().getName())){
                    //check whether iterative/review open and winner has been choosen
                    //which mean challenge has been close
                    if (phase.getPhaseStatus().getId() == PhaseStatus.OPEN.getId()) {
                        Submission[] submissions = uploadManager.getProjectSubmissions(contest.getId());
                        for (Submission submission : submissions){
                            if(submission.getPlacement() != null && submission.getPlacement() == 1L){
                                logger.error("Challenge has been closed");
                                throw new ContestServiceException("Can't  cancel this challenge because winner " +
                                            "for this challenge has been selected or challenge has been closed before");
                            }
                        }
                    }
                }
            }

            contest.setProjectStatus(ProjectStatus.CANCELLED_CLIENT_REQUEST);
            projectManager.updateProject(contest, "cancel-client request", String.valueOf(tcSubject.getUserId()));
        } catch (ContestServiceException e) {
            throw  e;
        } catch (Exception e) {
            logger.error("Failed to update challenge");
            throw new ContestServiceException("Failed to update challenge", e);
        }
    }
}
