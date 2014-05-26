/*
 * Copyright (C) 2010 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.clients.invoices.dao.InvoiceRecordDAO;
import com.topcoder.clients.invoices.model.InvoiceType;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.copilot.dto.CopilotPoolMember;
import com.topcoder.direct.services.copilot.model.CopilotProjectFeedback;
import com.topcoder.direct.services.exception.DirectException;
import com.topcoder.direct.services.payments.entities.CashOutflowPotential;
import com.topcoder.direct.services.payments.entities.CashOutflowPotentialResult;
import com.topcoder.direct.services.payments.entities.DateRange;
import com.topcoder.direct.services.payments.entities.PaymentHistory;
import com.topcoder.direct.services.payments.entities.PaymentHistoryCriteria;
import com.topcoder.direct.services.payments.entities.PaymentHistoryResult;
import com.topcoder.direct.services.payments.entities.PaymentMethod;
import com.topcoder.direct.services.payments.entities.PaymentStatus;
import com.topcoder.direct.services.payments.entities.PaymentTrend;
import com.topcoder.direct.services.payments.entities.PaymentTrendSearchCriteria;
import com.topcoder.direct.services.payments.entities.PaymentsByStatus;
import com.topcoder.direct.services.payments.entities.PaymentsByStatusResult;
import com.topcoder.direct.services.payments.entities.PullablePayments;
import com.topcoder.direct.services.payments.entities.TopMemberPayment;
import com.topcoder.direct.services.payments.entities.TopMemberPaymentCriteria;
import com.topcoder.direct.services.project.metadata.entities.dao.DirectProjectAccess;
import com.topcoder.direct.services.view.dto.ActivityDTO;
import com.topcoder.direct.services.view.dto.ActivityType;
import com.topcoder.direct.services.view.dto.ClientBillingDirectProjectMappingDTO;
import com.topcoder.direct.services.view.dto.ClientUserStatsDTO;
import com.topcoder.direct.services.view.dto.CoPilotStatsDTO;
import com.topcoder.direct.services.view.dto.IdNamePair;
import com.topcoder.direct.services.view.dto.LatestActivitiesDTO;
import com.topcoder.direct.services.view.dto.MemberPhotoDTO;
import com.topcoder.direct.services.view.dto.SoftwareContestWinnerDTO;
import com.topcoder.direct.services.view.dto.TcJiraIssue;
import com.topcoder.direct.services.view.dto.TopCoderDirectFactsDTO;
import com.topcoder.direct.services.view.dto.UpcomingActivitiesDTO;
import com.topcoder.direct.services.view.dto.UserDTO;
import com.topcoder.direct.services.view.dto.admin.CopilotFeedbackAdminDTO;
import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestCopilotDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDTO;
import com.topcoder.direct.services.view.dto.contest.ContestDashboardDTO;
import com.topcoder.direct.services.view.dto.contest.ContestFinalFixDTO;
import com.topcoder.direct.services.view.dto.contest.ContestHealthDTO;
import com.topcoder.direct.services.view.dto.contest.ContestIssuesTrackingDTO;
import com.topcoder.direct.services.view.dto.contest.ContestReceiptDTO;
import com.topcoder.direct.services.view.dto.contest.ContestReceiptEntry;
import com.topcoder.direct.services.view.dto.contest.ContestRegistrantDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.dto.contest.DependenciesStatus;
import com.topcoder.direct.services.view.dto.contest.DependencyDTO;
import com.topcoder.direct.services.view.dto.contest.ForumPostDTO;
import com.topcoder.direct.services.view.dto.contest.ProblemDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseDTO;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseStatus;
import com.topcoder.direct.services.view.dto.contest.ProjectPhaseType;
import com.topcoder.direct.services.view.dto.contest.RegistrationStatus;
import com.topcoder.direct.services.view.dto.contest.ReviewersSignupStatus;
import com.topcoder.direct.services.view.dto.contest.RunningPhaseStatus;
import com.topcoder.direct.services.view.dto.contest.SoftwareContestSubmissionsDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareSubmissionDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareSubmissionReviewDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotBriefDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotContestDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotProjectDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotSkillDTO;
import com.topcoder.direct.services.view.dto.copilot.CopilotStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardContestSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardCostBreakDownDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardMemberSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardProjectSearchResultDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardContestStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardDetailedProjectStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardProjectStatDTO;
import com.topcoder.direct.services.view.dto.dashboard.EnterpriseDashboardStatType;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.BillingCostReportEntryDTO;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.InvoiceRecordBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.billingcostreport.PaymentType;
import com.topcoder.direct.services.view.dto.dashboard.costreport.CostDetailsDTO;
import com.topcoder.direct.services.view.dto.dashboard.jirareport.JiraIssueStatus;
import com.topcoder.direct.services.view.dto.dashboard.jirareport.JiraIssuesReportEntryDTO;
import com.topcoder.direct.services.view.dto.dashboard.participationreport.ParticipationAggregationReportDTO;
import com.topcoder.direct.services.view.dto.dashboard.participationreport.ParticipationBasicReportDTO;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineDraftsRatioDTO;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineScheduledContestsViewType;
import com.topcoder.direct.services.view.dto.dashboard.projectreport.ProjectMetricsReportEntryDTO;
import com.topcoder.direct.services.view.dto.dashboard.specialistreport.PlatformSpecialistReportDTO;
import com.topcoder.direct.services.view.dto.dashboard.volumeview.EnterpriseDashboardVolumeViewDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.ContestPipelineDrillInDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardMonthPipelineDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardMonthProjectPipelineDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardProjectFinancialDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardProjectsWidgetDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.EnterpriseDashboardTotalSpendDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.ProjectPipelineDrillInDTO;
import com.topcoder.direct.services.view.dto.enterpriseDashboard.TotalSpendDrillInDTO;
import com.topcoder.direct.services.view.dto.project.LatestProjectActivitiesDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.dto.project.ProjectCopilotDTO;
import com.topcoder.direct.services.view.dto.project.ProjectCopilotStatDTO;
import com.topcoder.direct.services.view.dto.project.ProjectForumStatusDTO;
import com.topcoder.direct.services.view.dto.project.ProjectForumTemplateDTO;
import com.topcoder.direct.services.view.dto.project.ProjectGeneralInfoDTO;
import com.topcoder.direct.services.view.dto.project.ProjectStatsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectStatusType;
import com.topcoder.direct.services.view.dto.project.milestone.MilestoneContestDTO;
import com.topcoder.direct.services.view.dto.search.ContestSearchResult;
import com.topcoder.direct.services.view.dto.search.ProjectSearchResult;
import com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm;
import com.topcoder.direct.services.view.util.jira.JiraRpcServiceWrapper;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.marathonmatch.service.dto.MMDownloadSubmissionDTO;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.facade.contest.ForumPoster;
import com.topcoder.service.facade.contest.ProjectSummaryData;
import com.topcoder.service.project.ProjectData;
import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer.ResultSetRow;
import com.topcoder.shared.dataAccess.resultSet.TCResultItem;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.CachedDataAccess;
import com.topcoder.web.common.cache.MaxAge;
import com.topcoder.web.common.tag.HandleTag;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <p>An utility class providing the methods for getting various data from persistent data store. Such a data is usually
 * obtained using pre-defined queries from <code>Query Tool</code> and this utility class provides the methods which
 * translate into calls to appropriate queries from <code>Query Tool</code>.</p>
 *
 * <p>Sub-sequent assemblies may expand this class with additional methods for calling other queries when there is a
 * need or they can fully re-work the concept of data provider.</p>
 *
 * <p>
 * Version 2.0 - Direct Search Assembly - add search and filtering for searchUserProjects,searchUserContests
 * </p>
 * <p>
 * Version 2.1 - Edit Software Contests Assembly - identify if it is software or studio in search contest result
 * </p>
 *
 * <p>
 * Version 2.1.1 (Direct Software Submission Viewer Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #setSoftwareSubmissionsData(SoftwareContestSubmissionsDTO)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.1.2 (Direct Enterprise Dashboard Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getEnterpriseProjectStats(List)} method.</li>
 *     <li>Added {@link #getAllProjectCategories()} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.1.3 (Direct Contest Dashboard Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getContestDashboardData(long, boolean, boolean)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.1.4 (Direct Project Dashboard Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #getContestDashboardData(long, boolean, boolean)},
 *     {@link #getSoftwareContestDashboardData(long, boolean)} and {@link #getStudioContestDashboardData(long, boolean)}
 *     methods to add cached model.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.1.5 (Direct Pipeline Stats Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getPipelineDraftsRatioStats(PipelineScheduledContestsViewType, long)} method.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.1.6 (TC Direct Manage Copilots Assembly 1.0) Change notes:
 * <ol>
 * <li>Added getCopilotProjects method.</li>
 * </ol>
 * </p>
 *
 *  <p>
 * Version 2.1.7 (Direct Release 6 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getTopCoderDirectFacts()} method to calculate stats for bug races.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.1.8 (Direct Manage Copilot Postings Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getCopilotPostingContests(TCSubject)} method.</li>
 *     <li>Added {@link #getCurrentPhases(long)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.1.9 (Cockpit - Enterprise Dashboard 2 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Renamed <code>getEnterpriseStatsForProject</code> method to <code>getEnterpriseStatsForProjects</code> and
 *     updated it to accept additional parameters for client and billing account IDs.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.2.0 (Cockpit - Enterprise Dashboard 3 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added <code>{@link #getEnterpriseStatsForContests(long[],long[], Date, Date, long[], long[])}</code> method </li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.2.0 (Cockpit - Enterprise Dashboard 3 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added <code>{@link #getEnterpriseStatsForAllContests(long[], Date, Date)}</code> method </li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.2.0 (Cockpit - Enterprise Dashboard 3 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added <code>{@link #getEnterpriseContestsAvgStatus(long[], Date, Date)}</code> method </li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.3.0 (TC Direct - Software Creation Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Add method getCopilotsForDirectProject to get copilots of the direct project</li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.4.0 (TC Cockpit - Cost Report Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Add method getDashboardCostReportDetails to get cost details of each contest with specified filters.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.5.0 (Cockpit Performance Improvement Project Overview and Manage Copilot Posting) Change notes:
 *   <ol>
 *     <li>Update method getLatestActivitiesForUserProjects - a new request input 'tcdirectid' is added to
 *      query 'direct_latest_activities', we set it to 0 in this method to get data for all user projects</li>
 *     <li>Update method getLatestActivitiesForProject - update the method to use query 'direct_latest_activities'
 *     with a new request input 'tcdirectid', is set to the id of the direct project. The method is no longer
 *     depends on getLatestActivitiesForUserProjects which affected the performance.</li>
 *     <li>Update method getUpcomingActivitiesForUserProjects - a new request input 'tcdirectid' is added to
 *      query 'direct_upcoming_activities', we set it to 0 in this method to get data for all user projects</li>
 *     <li>Add a new method getUpcomingActivitiesForProject - the method uses query 'direct_upcoming_activities'
 *     with a new request input 'tcdirectid', is set to the id of the direct project.</li>
 *     <li>Add a new method getProjectContestsHealth - the method uses a new query
 *     'direct_project_overview_contests_health' to get the contest health data of all the active and scheduled
 *     contests of the specified project.</li>
 *     <li>update method getProjectStats to use a simplified query 'direct_project_overview_statistics'.</li>
 *     <li>update method getCopilotPostingContests to use query 'direct_my_copilot_postings' to improve the
 *     performance</li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.6.0 (TC Cockpit Billing Cost Report Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Rename method getDashboardClientBillingProjectMappingsForAdmin to
 *      getDashboardClientBillingProjectMappings, because it's not only for admin user. Removes the argument projects
 *      which is not needed.</li>
 *     <li>Add method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.6.1 (TC Cockpit Enterprise Dashboard Update Cost Breakdown Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #getDashboardCostBreakDown(long[], long[], Date, Date)} method to get the cost break down data
 *     for contests or market.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.6.2 (TC Cockpit Bug Tracking R1 Cockpit Project Tracking version 1.0) Change notes:
 *   <ol>
 *       <li>Added {@link #getContestIssues(long, boolean)} to get issues of the contest.<li>
 *       <li>Added (@link #getDirectProjectIssues(List<? extends ContestBriefDTO>)} to get issues of the direct project.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.6.3 (TC Direct - Page Layout Update Assembly) Change notes:
 *   <ol>
 *     <li>Updated {@link #getCopilotPostingContests(TCSubject)} method to get a list of ProjectContestDTO.</li>
 *   </ol>
 * </p>
 * <p>
 *
 * Version 2.6.4 (TC Cockpit Contest Duration Calculation Updates Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #getDirectProjectStats(List, long)} method to calculate average contest duration for project.
 *     </li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.6.5 (Project Health Update Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #getStudioContestDashboardData(long, boolean)} method to calculate current and next phases for
 *     <code>Studio</code> contests.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.6.6 (TC Direct Contest Dashboard Update Assembly) Change notes:
 *   <ol>
 *     <li>Updated {@link #getSoftwareContestDashboardData(long, boolean)} method to get all phases.</li>

 *     <li>Updated {@link #getStudioContestDashboardData(long, boolean)} method to set reg progress percent.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 2.6.7 (Release Assembly - TC Cockpit Enterprise Dashboard Update Assembly 1) Change notes:
 *   <ol>
 *     <li>Add {@link #getEnterpriseDashboardSummaryRange(long[], long[], java.util.Date, java.util.Date, long[], long[])}
 *     method to get the min and max values of contest duration and contest cost of customer and market.
 *     </li>
 *   </ol>
 * </p>
 <p>
 * Version 2.6.8 (Release Assembly - Cockpit Customer Right Sidebar and Active Contests Coordination) Change notes:
 *   <ol>
 *     <li>Updated {@link #getUserProjects(long)} method to get add customers data.</li>

 *     <li>Updated {@link #getActiveContests(long)} method to add customer id of each active contest.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.7.0 (Direct Replatforming Release 4) Change notes:
 *   <ol>
 *     <li>Remove the method getStudioContestDashboardData.</li>
 *     <li>Update {@link # getProjectContests(long, long)} to use the new
 *      direct_my_contests_replatformingg query</li>
 *     <li>Update {@link # getActiveContests(long)} to use the new
 *      direct_active_contests_replatforming query</li>
 *   </ol>
 * </p>
 *
 * <p>Version 2.8.0 (Release Assembly - TopCoder Cockpit Project Overview Update 1) change notes:
 * <ul>
 *     <li>Add method {@link #getDirectProjectCopilotStats(long)} to get copilots statistics
 *     for project overview page </li>
 *     <li>Add method {@link #getCopilotProject(long, long)} to get copilots data of direct project</li>
 *     <li>Update method {@link #getCopilotProjects(long)}</li>
 *     <li>Added method {@link #getTopCoderDirectProjectForumThreadsCount(Long)}.</li>
 *     <li>Added method {@link #getProjectForumStatus(long, long)}.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.9.0 (TC Cockpit Participation Metrics Report Part One Assembly 1) change notes:
 *   <ol>
 *     <li>Added {@link #getDashboardParticipationReport(long, long[], long, long, String[], Date, Date, List, List)}
 *     method to get the participation metrics report.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.9.1 (TC Cockpit Permission and Report Update One) change log:
 * <ol>
 *   <li>Added a parameter <code>TCSubject</code> to
 *   method {@link #getDashboardCostBreakDown(TCSubject, long[], long[], Date, Date)}. The parameter is used to check the permission.</li>
 *   <li>Added a parameter <code>TCSubject</code> to
 *   method {@link #getDashboardParticipationReport(TCSubject, long, long[], long, long, String[], Date, Date, List, List)}. The parameter
 *   is used to check the permission.</li>
 *   <li>Added a parameter <code>TCSubject</code> to
 *   method {@link #getDashboardCostReportDetails(TCSubject, long, long[], long[], long, long, long[], Date, Date, Map)}. The parameter
 *   is used to check the permission.</li>
 *   <li>Added a parameter <code>TCSubject</code> to
 *   method {@link #getDashboardBillingCostReport(TCSubject, long, long[], long[], long[], long, long, long[], long, Date, Date, Map, Map)}.
 *   The parameter is used to check the permission.</li>
 *   <li>Added {@link #setReportQueryParameters(Request, TCSubject, long, long, long)} method to set common query parameters
 *   for report query.</li>
 *   <li>Methods {@link #getDashboardParticipationReport(TCSubject, long, long[], long, long, String[], Date, Date, List, List)}
 *   and {@link #getDashboardCostReportDetails(TCSubject, long, long[], long[], long, long, long[], Date, Date, Map)}
 *   and {@link #getDashboardBillingCostReport(TCSubject, long, long[], long[], long[], long, long, long[], long, Date, Date, Map, Map)}
 *   were updated to use {@link #setReportQueryParameters(Request, TCSubject, long, long, long)} to set the common query parameters.</li>
 *   <li>Added method {@link #setUserPermissionQueryParameter(Request, TCSubject)} method to set user parameter for report query to check
 *   the user's permission.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 2.9.1 (TC Accounting Tracking Invoiced Payments) change notes:
 *   <ol>
 *     <li>Added {@link #concatenate(Iterable, String)} method to concatenate iterable objects to string.</li>
 *     <li>Added {@link #getInvoiceRecordRelatedData(List, List)} method to get the invoice record related data for payment data.</li>
 *     <li>Updated {@link #getDashboardBillingCostReport(InvoiceRecordDAO, List, long[], long[], long[], long[], long[], long[], long[], long, Date, Date, Map, Map)}
 *     method to get payment_id, processed fields for billing cost data.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 3.0 (Release Assembly - TC Cockpit Enterprise Dashboard Volume View Assembly) change notes:
 *   <ol>
 *     <li>Added {@link #getEnterpriseDashboardVolumeView(long, long, long, long[], java.util.Date, java.util.Date)} .</li>
 *   </ol>
 * </p>
 *
 * </ol>
 * </p>
 *
 * <p>
 * Changes in version 3.1 (Release Assembly - TopCoder Cockpit DataTables Filter Panel and Search Bar):
 * <ol>
 * <li>Added {@link #getProjectsCustomers(long[])} ()} method.</li>
 * <li>Updated {@link #searchUserProjects(com.topcoder.security.TCSubject, String)} to add customer information</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 3.2 (Release Assembly - TC Direct Select From Copilot Pool Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #getMemberPhotos(long[])} method to retrieve member photos.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 3.3 (TC Accounting Tracking Invoiced Payments Part 2) change log:
 * <ol>
 *   <li>Removed <code>invoiceRecordDAO</code> from the parameter list from
 *   {@link #getDashboardBillingCostReport(List, TCSubject, long, long[], long[], long[], long, long, long[], long, Date, Date, Map, Map)}.</li>
 *   <li>Updated {@link #getDashboardBillingCostReport(List, TCSubject, long, long[], long[], long[], long, long, long[], long, Date, Date, Map, Map)}
 *   to populate the invoice number, invoice id and payment description.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 3.4 (Module Assembly - TC Cockpit Project Overview Project General Info) change log:
 * <ol>
 *     <li>Add method {@link #setProjectGeneralInfo(com.topcoder.direct.services.view.dto.project.ProjectGeneralInfoDTO)}
 *     to retrieve the project general information for project overview page.
 *     </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 3.5 (Release Assembly - TC Cockpit Edit Project and Project General Info Update) change log:
 * <ol>
 *     <li>Updated method method {@link #setProjectGeneralInfo(com.topcoder.direct.services.view.dto.project.ProjectGeneralInfoDTO)}
 *     to set projected duration and projected cost of the project.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 3.6 (Release Assembly - TC Direct Cockpit Release Two) change log:
 * <ol>
 *     <li>Add method ${@link #setSoftwareCheckpointSubmissionsData(com.topcoder.direct.services.view.dto.contest.SoftwareContestSubmissionsDTO)}.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 3.7 (Release Assembly - TC Cockpit All Projects Management Page Update) change log:
 * <ol>
 *     <li>
 *         Update method {@link #getProjectData(com.topcoder.security.TCSubject)} to add project forum data.
 *     </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 3.8 (Release Assembly - TC Direct Cockpit Release Four) change log:
 * <ol>
 *     <li>
 *         Update method {@link #setSoftwareSubmissionsData(com.topcoder.direct.services.view.dto.contest.SoftwareContestSubmissionsDTO)}
 *         to not set winner if the submission is not passed review.
 *     </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 3.8.1 (Release Assembly - TC Cockpit - Member Participation Metrics Report Performance Enhancement Assembly)
 * Change notes:
 *   <ol>
 *     <li>Updated {@link #getDashboardParticipationReport(TCSubject, long, long[], long, long, String[], Date, Date,
 *     ParticipationBasicReportDTO, List, List, List, List)} method to load report data from TCS_DW database instead of
 *     TCS_CATALOG database.</li>
 *   </ol>
 * </p>
 * <p>
 * Version 3.9 (Module Assembly - Add Monthly Platform Fee Feature to Admin Page) change log:
 * <ol>
 *     <li>
 *         Update method {@link #getDashboardBillingCostReport(List, TCSubject, long, long[], long[], long[],
 *         long, long, long[], long, String, Date, Date, Map, Map)} and method {@link #getInvoiceRecordRelatedData(
 *         List, List, List)} to support the platform fee records.
 *     </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 3.9 (Module Assembly - TC Cockpit Public Page for TopCoder Road map and RSS syndication) change log:
 * <ol>
 *     <li>Add method {@link #getDirectProjectsForClient(String)}</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 3.10 (Release Assembly - TopCoder Cockpit Software Checkpoint Management) change log:
 * <ol>
 *     <li>Updated method {@link #setSoftwareCheckpointSubmissionsData(SoftwareContestSubmissionsDTO)}
 *     to support software checkpoint management.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 4.0 (Module Assembly - TC Cockpit Project Contests Batch Edit)
 * <ol>
 *     <li>Update {@link #getProjectContests(long, long)} to add billing account info for the contest</li>
 *     <li>Add method {@link #getAllProjectCategoriesGrouped()}</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 4.1 (Release Assembly - TopCoder Cockpit Project Contest Results
 *          Export Part 1)
 * <ol>
 *     <li>Add method for exporting contests submissions.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 4.2 (Release Assembly - TC Direct Cockpit Release Five)
 * <ol>
 *     <li>Update method {@link #getContestDashboardData(long, boolean, boolean)} to always set registration health to
 *     healthy for copilot posting</li>
 *     <li>Update method {@link #getContestReceipt(long, boolean)} to add contest launcher info</li>
 *     <li>Update method {@link #getProjectContestsHealth(long, long, boolean)} to always set registration health to
 *     healthy for copilot posting</li>
 * </ol>
 * Version 4.3 (Module Assembly - TC Cockpit Project Metrics Report ) change log:
 * <ol>
 *     <li>Add method {@link #getDashboardProjectMetricsReport(TCSubject, long, long, long, String[],
            Date, Date, List<ProjectMetricsReportEntryDTO>)}</li> method to load project metrics report data.</li>
 * </ol>
 * </p>
 *
 *
 * <p>
 * Version 4.3 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Setup and Financial part)
 * <ol>
 *     <li>Update method {@link #getUserProjects(long)}  and {@link #getUserProjectsList(long)}</li>
 *     <li>Add method {@link #getAllDirectProjectStatus()}</li>
 *     <li>Add method {@link #getEnterpriseDashboardProjectsFinancialInfo(com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm)}</li>
 *     <li>Add method {@link #getEnterpriseDashboardTotalSpend(com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm)}</li>
 * </ol>
 * </p>
 * <p>
 * Version 4.4 (Release Assembly - TC Direct Project Forum Configuration Assembly)
 * <ol>
 *     <li>Added {@link #getDirectProjectForumTemplates(long)} method.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 4.5 (Module Assembly - TC Cockpit Enterprise Dashboard Pipeline Part) changes:
 * <ol>
 *     <li>Add method {@link #getEnterpriseDashboardContestsPipeline(com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm)}</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 4.6 (Release Assembly - TC Direct Cockpit Release Six) changes:
 * <ol>
 *     <li>Add method {@link #getInvoiceNumbersFromBilling(long)}</li>
 *     <li>Add method {@link #getProjectsForInvoiceNumberAndBilling(String, long)}</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 4.6.1 (Release Assembly - TopCoder Security Groups Release 5 v1.0) changes:
 * <ol>
 *     <li>Updated {@link #getProjectData(TCSubject)} to populate <code>hasWritePermission</code> field.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 4.7 (Module Assembly - TopCoder Cockpit New Enterprise Dashboard Roadmap part)
 * <ol>
 *     <li>Updated {@link #getEnterpriseDashboardFilteredProjectIds(com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm)}</li>
 *     <li>Added {@link #getEnterpriseDashboardFilteredProjects(com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm)} method.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 4.8 (Module Assembly - JIRA issues loading update and report creation)
 * <ol>
 *     <li>Add method {@link #getDashboardJiraIssuesReport(com.topcoder.security.TCSubject, long, long, long, long[], java.util.Date, java.util.Date)}</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 4.9 (Module Assembly - TopCoder Copilot Feedback Integration) changes:
 * <ol>
 *     <li>Add method {@link #getAllCopilotFeedback()}</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 5.0 (Release Assembly - TC Direct Cockpit Release Seven version 1.0)
 * <ul>
 *     <li>Updated method {@link #getDashboardCostReportDetails(com.topcoder.security.TCSubject, long, long[], long[], long, long, long[], java.util.Date, java.util.Date, java.util.Map)} to store launch date</li>
 *     <li>Updated method {@link #getActiveContests(long)} to detect contest stalled status</li>
 *     <li>Updated method {@link #getContestReceipt(long, boolean)} to store showReceipt flag</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 5.1 (TC Cockpit - Member Participation Metrics Report Upgrade)
 * <ol>
 *     <li>Updated method{@link #getDashboardParticipationReport(TCSubject, long, long[], long, long, String[],
 *     java.util.Date, java.util.Date, ParticipationBasicReportDTO, List, List, List, List)} to fetch the
 *     member participation contest report </li>
 *     <li>Added method {@link #getDashboardParticipationExcelData(TCSubject, long, long[], long,
 *     long, String[], java.util.Date, java.util.Date,String)} to get the contest participation metrics excel data with
 *     the given parameters. </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 5.2 (Module Assembly - TC Cockpit Operations Dashboard For PMs) changes:
 * <ol>
 *     <li>Refactor method {@link #searchUserProjects(TCSubject, String)}, decompose a new method method
 *     {@link #getFilteredProjects(String, List<ProjectSummaryData>)} that can be reused.</li>
 *     <li>Added method {@link #getFilteredProjects(String, List)} to Search the project by
 *     searchFor criteria, and set the customer id to the project dto.</li>
 *     <li>Added method {@link #searchPMUserProjects(TCSubject, String)} to get the details on Platform Managers'
 *     projects associated with specified user and matching the specified criteria for operations dashboard page</li>
 *     <li>Added method {@link #getPMProjectData(TCSubject)} to get the list of Platform Managers' project summary data
 *     associated with specified user.</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 5.3 (Release Assembly - TopCoder Direct Billing Project Links)
 * <ul>
 *     <li>Update {@link #getProjectsCustomers(long[])} to use new queries</li>
 *     <li>Update {@link #getDirectProjectsForClient(String)} to use new queries</li>
 *     <li>Add method {@link #getDashboardClientBillingProjectMappingsV2(com.topcoder.security.TCSubject)}</li>
 * </ul>
 * </p>
 *
 *
 * <p>
 * Version 5.4 (Module Assembly - TC Cockpit Enterprise Dashboard Analysis 1)
 * <ol>
 *     <li>Update method {@link #getEnterpriseDashboardVolumeView(long, long, long, long[], java.util.Date, java.util.Date, boolean)}</li>
 * </ol>
 * </p>
* <p>
 * Version 5.5 (Module Assembly - TC Client Users Stats Report Generation v1.0) changes:
 * <ol>
 *    <li>Add method {@link #getClientUserStats()}</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 5.6 (Module Assembly - Cockpit Copilot Posting Registrants Analysis)
 * <ul>
 *     <li>
 *         Add method {@link #setCopilotFullStatistics(java.util.List, long)}
 *     </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 5.7 (Release Assembly - TC Direct Cockpit Release Eight)
 * <ul>
 *     <li>
 *        Update method {@link #getPMProjectData(com.topcoder.security.TCSubject)}  to add late status.
 *     </li>
 *     <li>
 *         Update method {@link #getActiveContests(long)} to add multiple active phases and late status.
 *     </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 5.8  (Release Assembly - TC Cockpit Operations Dashboard Bug Fix and Improvements 1)
 * <ol>
 *     <li>Add method {@link #getLatestThreePosters(String)}</li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 5.9  (Module Assembly - TC Cockpit Invoice History Page Update)
 * <ol>
 *   <li>Update method {@link #getInvoiceRecordRelatedData(List, List, List)} to add cockpit project id.</li>
 *   <li>Add method {@link #getRelatedSecondInstallment(List, List)} to get second installment data.</li>
 * </ol>
 * </p>
 * <p>
 *
 * Version 6.0 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
 * <ol>
 *     <li>
 *         Update method {@link #getActiveContests(long)} to include the checkpoint / final submission number and isMultipleRound.
 *     </li>
 * </ol>
 * </p>
 *
 * Version 6.1 (Release Assembly - TC Cockpit Enterprise Dashboard Project Pipeline and Project Completion Date Update)
 * <ol>
 *   <li>
 *    Updted {@link #setProjectGeneralInfo(com.topcoder.direct.services.view.dto.project.ProjectGeneralInfoDTO)} to
 *    use completion date to calculate project actual duration if the project is completed
 *   </li>
 *   <li>
 *    Add method {@link #getEnterpriseDashboardProjectsPipeline(com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm)}
 *    to gets the projects pipeline data.
 *   </li>
 * </ol>
 * </p>
 *
 * <p>
 * Version 6.2 (Module Assembly - Cockpit Copilot Posting Skills Update and Submission Revamp)
 * <ul>
 *     <li>
 *         Update the method {@link #setCopilotFullStatistics(java.util.List, long)} to set the copilot profile id,
 *         positive and negative feedback number.
 *     </li>
 *     <li>
 *         Add the method {@link #getCopilotSkillRules()} to get all the copilot skill rules.
 *     </li>
 * </ul>
 * </p>
 *
 * <p>
 *  Version 6.3 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 1)
 *  <ul>
 *      <li></li>
 *  </ul>
 * </p>
 *
 * <p>
 * Version 6.3 (Module Assembly - TopCoder Cockpit Instant Search)
 * <ul>
 *     <li>Add method {@link #getInstantSearchResult(String, long)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.4 (Release Assembly - Cockpit Enterprise Dashboard Chart Drill-In)
 * <ul>
 *     <li>Add method {@link #getEnterpriseDashboardTotalSpendDrillIn(EnterpriseDashboardFilterForm)}</li>
 *     <li>Add method {@link #getEnterpriseDashboardContestsPipelineDrillIn(EnterpriseDashboardFilterForm)}</li>
 *     <li>Add method {@link #getEnterpriseDashboardProjectsPipelineDrillIn(EnterpriseDashboardFilterForm)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.5 (Module Assembly - TC Cockpit Operations Dashboard Improvements 2)
 * <ul>
 *     <li>Update method {@link #getPMProjectData}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 6.6 (Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match)
 * <ul>
 *     <li>Add method {@link #getActiveProblemSet()}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.7 (Release Assembly - TC Cockpit New Enterprise Dashboard Release 2)
 * <ul>
 *     <li>Adds method {@link #getFilteredProjectsResult(int, int, long, long[], long, String)}</li>
 *     <li>Updates method {@link #getEnterpriseDashboardTotalSpendDrillIn(com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm, boolean)}</li>
 *     <li>Updates method {@link #getEnterpriseDashboardContestsPipelineDrillIn(com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm, boolean)}</li>
 *     <li>Updates method {@link #getEnterpriseDashboardProjectsPipelineDrillIn(com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm, boolean)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.8 (Module Assembly - TC Cockpit Platform Specialist Utilization Report and Graph)
 * <ul>
 *     <li>Add method {@link #getPlatformSpecialistReportData(long, String, String)}</li>
 *     <li>Add method {@link #getAllPlatformSpecialists()}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.9(Module Assembly - TopCoder Direct Member Payments Dashboard Backend Assembly v1.0) change notes:
 * <ol>
 *     <li>Added method {@link #getPullablePayments()}.</li>
 *     <li>Added method {@link #getPaymentsByStatus(Date, Date)}.</li>
 *     <li>Added method {@link #getPotentialMemberPayments(Date, Date)}.</li>
 *      <li>Added method {@link #getPaymentHistory(PaymentHistoryCriteria)}.</li>
 *      <li>Added method {@link #getPaymentsTrends(PaymentTrendSearchCriteria)}.</li>
 *      <li>Added method {@link #getTopMemberPayments(TopMemberPaymentCriteria)}.</li>
 * </ol>
 * <p>
 *
 * <p>
 * Version 6.10 (System Assembly - TopCoder Direct Member Payments Dashboard v1.0) change notes:
 * <ol>
 *     <li>Added method {@link #concatenate(int[], String)}.</li>
 *     <li>Updated method {@link #getPotentialMemberPayments(java.util.Date, java.util.Date, int[])}.</li>
 *     <li>Updated method {@link #getPaymentsTrends(PaymentTrendSearchCriteria)}</li>
 *     <li>Updated method {@link #getTopMemberPayments(TopMemberPaymentCriteria)}</li>
 * </ol>
 * <p>
 *
 * <p>
 * Version 6.11 (Release Assembly - TopCoder Copilot Feedback Updates)
 * <ul>
 *     <li>Updates {@link #getAllCopilotFeedback()} to fill added copilot feedback ratings data</li>
 * </ul>
 * </p>
 * Version 6.12 (Release Assembly - TopCoder Security Groups Release 7)
 * <ul>
 *     <li>Updates {@link #getDashboardBillingCostReport(java.util.List, com.topcoder.security.TCSubject, long, long[], long[], long[], long, long, long, String, java.util.Date, java.util.Date, java.util.Map)}
 *     to use the new commands. Each command has multiple splitted queries for the invoice report.</li>
 * </ul>
 * </p>
 * <p>
 * Version 6.13 (Release Assembly - TC Cockpit Project Total Cost Fixes)
 * <ul>
 *     <li>Updates {@link #getPMProjectData(com.topcoder.security.TCSubject)} to add project level cost</li>
 *     <li>Updates {@link #getDashboardProjectMetricsReport(com.topcoder.security.TCSubject, long, long, long, long[], java.util.Date, java.util.Date, java.util.List)}</li>
 *     <li>Updates {@link #getDashboardCostBreakDown(com.topcoder.security.TCSubject, long[], long[], java.util.Date, java.util.Date)}</li>
 *     <li>UPdates {@link #getDashboardCostReportDetails(com.topcoder.security.TCSubject, long, long[], long[], long, long, long[], java.util.Date, java.util.Date, java.util.Map)}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 6.14 (FAST! 72hr! Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress Part 1 v1.0)
 * <ul>
 *  <li>Add {@link #getMMSubmission} method. </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.15 (Release Assembly - TC Cockpit Enterprise Dashboard Projected Cost and Project Health Page)
 * <ul>
 *     <li>Updates method {@link #getEnterpriseDashboardTotalSpend(com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm)} to add
 *     projected cost data</li>
 *     <li>Updates method {@link #getEnterpriseDashboardTotalSpendDrillIn(com.topcoder.direct.services.view.form.enterpriseDashboard.EnterpriseDashboardFilterForm, boolean)} to add
 *     projected cost data</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.16 (Module Assembly - TopCoder Cockpit Project Planner)
 * <ul>
 *     <li>Adds {@link #getProjectCopilotPostingContests(long)} to get copilot postings of a specific cockpit project</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.17 (Release Assembly - TC Cockpit Bug Race Cost and Fees Part 1)
 * <ul>
 *     <li>Updated {@link #getInvoiceRecordRelatedData(List, List, List, List)} to support
 *     JIRA bug race contest fees.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.18 (Release Assembly - TC Cockpit Bug Race Cost and Fees Part 2)
 * <ul>
 *     <li>
 *         Updated method {@link #getDashboardCostReportDetails(com.topcoder.security.TCSubject, long, long[], long[], long, long, long[], java.util.Date, java.util.Date, java.util.Map)}
 *         to include project level bug race contest fee in the project level cost details.
 *     </li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.19 (Release Assembly - TC Cockpit JIRA Report Update)
 * <ul>
 *     <li>Updated {@link #getDashboardJiraIssuesReport(com.topcoder.security.TCSubject,
 *     long, long, long, long[], java.util.Date, java.util.Date)} to
 *     accept project jira issues without contest id and contest name.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.20 (Release Assembly - TopCoder Cockpit Operations Dashboard Improvements 4 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Updated {@link #getPMProjectData(TCSubject)} method to retrieve the list of copilots for the TC Direct 
 *     projects, maximum delay for the project's contests launching and late phases.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 6.21 Change notes:
 *   <ol>
 *     <li>Updated {@link #getContestFinalFixes(long)} method to set the submisison ID for final fix.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 6.22 (Module Assembly - TC Cockpit Contest Milestone Association Milestone Page Update)
 * <ul>
 *     <li>Added method {@link #getMilestoneContestAssociations(long, long, long)}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * Version 6.23 (BUGR - 9794) Change notes:
 * <ol>
 *     <li>Update method {@link #getMMSubmission(long, long)} to get the latest submission by default.</li>
 * </ol>
 * </p>
 *
 *
 * Version 6.25 (Release Assembly - TopCoder Cockpit Navigation Update)
 * <ul>
 *     <li>Added method {@link #getRecentItemsMaxNumber(long)}</li>
 *     <li>Added method {@link #getUserRecentDirectProjects(long)}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.26 (BUGR-10221 TopCoder Cockpit CMC Account ID for launching new contest)
 * <ul>
 *     <li>Added method {@link #getBillingAccountFromCMCAccountID(String)} to get billing account id
 *     by CMC Account ID</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.27 (Release Assembly - TC Cockpit Private Challenge Update)
 * <ul>
 *     <li>Added method {@link #getSecurityGroupsForBillingAccount(long)} to get the group id & name
 *     of a billing account</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.28 (Release Assembly - TC Cockpit New Challenge types Integration Bug Fixes)
 * <ul>
 *     <li>Updated {@link #getContestDashboardData(long, boolean, boolean)} to sort contest phases</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 6.3 (Release Assembly - Port Design Challenge Forum to use Dev Forum)
 * <ul>
 *     <li>Updated method {@link #getProjectContests(long, long)} to include the forum type data for each contest</li>
 *     <li>Updated method {@link #getActiveContests(long)} to include the forum type data for each contest</li>
 *     <li>Updated method {@link #getContestDashboardData(long, boolean, boolean)} to include the forum type data for the contest</li>
 *
 * </ul>
 * </p>
 *
 * @author isv, BeBetter, tangzx, xjtufreeman, Blues, flexme, Veve,
 * @author GreatKevin, duxiaoyang, minhu,
 * @author bugbuka, leo_lol, morehappiness, notpad, GreatKevin, zhu_tao, GreatKevin, 
 * @author Ghost_141, GreatKevin, Veve, GreatKevin
 * @version 6.3
 * @since 1.0
 */
public class DataProvider {

    /**
     * The suffiex for 'monthly'
     */
    private static final String MONTHLY_SUFFIX = "_monthly";

    
    /**
     * The name of paypal method.
     */
    private static final String PAYPAL_METHOD_NAME = "Paypal";

    /**
     * The name of payoneer method.
     */
    private static final String PAYONEER_METHOD_NAME = "Payoneer";

    /**
     * The name of western union method.
     */
    private static final String WESTERN_UNION_METHOD_NAME = "Western Union";

    /**
     * The name of not-set method.
     */
    private static final String NOT_SET_METHOD_NAME = "Not Set";


    private static final String INSTANT_SERACH_CONTEST = "select first ? pi.value as contest_name, p.project_id as contest_id, p.project_category_id as contest_type_id,\n" +
            "tdp.project_id as direct_project_id, tdp.name as direct_project_name\n" +
            "from project p, project_info pi, tc_direct_project tdp\n" +
            "where p.project_id = pi.project_id\n" +
            "and pi.project_info_type_id = 6\n" +
            "and p.project_category_id != 27\n" +
            "and exists (select resource_id from user_permission_grant upg\n" +
            "                    where upg.user_id=DECODE(?,0,upg.user_id,?) and upg.permission_type_id IN (0,1,2,3) and upg.resource_id = p.tc_direct_project_id)\n" +
            "and UPPER(pi.value) like ?\n" +
            "and p.tc_direct_project_id = tdp.project_id";


    private static final String INSTANT_SERACH_PROJECT = "select first ? distinct tdp.project_id, tdp.name, dpt.name as project_type, dpc.name as project_category, c.name as client_name\n" +
            "from tc_direct_project tdp\n" +
            "LEFT OUTER JOIN direct_project_type dpt on tdp.direct_project_type_id = dpt.direct_project_type_id\n" +
            "LEFT OUTER JOIN direct_project_category dpc on tdp.direct_project_category_id = dpc.direct_project_category_id\n" +
            "LEFT OUTER JOIN corporate_oltp:direct_project_account dpa  on tdp.project_id = dpa.project_id\n" +
            "LEFT OUTER JOIN time_oltp:client_project cp ON cp.project_id = dpa.billing_account_id\n" +
            "LEFT OUTER JOIN time_oltp:client c ON c.client_id = cp.client_id\n" +
            "WHERE exists (select resource_id from user_permission_grant upg\n" +
            "                          where upg.user_id=DECODE(?,0,upg.user_id,?) and upg.permission_type_id IN (0,1,2,3) and upg.resource_id = tdp.project_id)\n" +
            "      and UPPER(tdp.name) like ?";

    /**
     * <p>Constructs new <code>DataProvider</code> instance. This implementation does nothing.</p>
     */
    private DataProvider() {
    }

    /**
     * <p>Gets the current number of registered <code>TopCoder</code> members.</p>
     *
     * @return an <code>int</code> providing the current number of registered <code>TopCoder</code> members.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static int getMemberCount() throws Exception {
        CachedDataAccess countDai = new CachedDataAccess(MaxAge.QUARTER_HOUR, DBMS.DW_DATASOURCE_NAME);
        Request countReq = new Request();
        countReq.setContentHandle("member_count");
        return countDai.getData(countReq).get("member_count").getIntItem(0, "member_count");
    }

     /**
     * Gets the copilot user ids for the given direct project.
     *
     * @param directProjectId the direct project id.
     * @return an array of copilot user ids of the given direct project.
     * @throws Exception if an unexpected error occurs while communicating to persistence data store.
     * @since 2.2.0
     */
    public static List<ContestCopilotDTO> getCopilotsForDirectProject(long directProjectId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_project_copilots");
        request.setProperty("tcdirectid", String.valueOf(directProjectId));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_project_copilots");
        List<ContestCopilotDTO> result = new ArrayList<ContestCopilotDTO>();

        final int recordNum = resultContainer.size();

        for (int i = 0; i < recordNum; i++) {
            long copilotUserId = resultContainer.getLongItem(i, "copilot_user_id");
            long copilotProfileId = resultContainer.getLongItem(i, "copilot_profile_id");
            long projectId = resultContainer.getLongItem(i, "project_id");
            String projectName = resultContainer.getStringItem(i, "project_name");
            String handle = resultContainer.getStringItem(i, "handle");

            ContestCopilotDTO copilot = new ContestCopilotDTO();
            copilot.setUserId(copilotUserId);
            copilot.setHandle(handle);
            copilot.setCopilotProfileId(copilotProfileId);
            ProjectBriefDTO project = new ProjectBriefDTO();
            project.setId(projectId);
            project.setName(projectName);

            copilot.setCopilotProject(project);

            // add to result
            result.add(copilot);
        }

        return result;
    }

    /**
     * <p>Gets the statistics on available co-piloting projects and co-pilots.</p>
     *
     * @return a <code>CoPilotStatsDTO</code> providing the statistics on available co-piloting projects and available
     *         co-pilots.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static CoPilotStatsDTO getCopilotStats() throws Exception {
        CachedDataAccess countDai = new CachedDataAccess(MaxAge.QUARTER_HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request countReq = new Request();
        countReq.setContentHandle("copilot_stats");

        ResultSetContainer coPilotStatsResult = countDai.getData(countReq).get("copilot_stats");
        CoPilotStatsDTO result = new CoPilotStatsDTO();
        result.setAvailableCopilots(coPilotStatsResult.getIntItem(0, "available_copilots_count"));
        result.setAvailableCopilotProjects(coPilotStatsResult.getIntItem(0, "available_copilot_projects_count"));
        return result;
    }


    /**
     * Gets the copilot statistics of all the copilots of the given direct project.
     *
     * @param directProjectId the id of the direct project.
     * @return the list of <code>ProjectCopilotStatDTO</code>
     * @throws Exception if any error happends during execution.
     * @since 2.8.0
     */
    public static List<ProjectCopilotStatDTO> getDirectProjectCopilotStats(long directProjectId) throws Exception {
        // result list
        List<ProjectCopilotStatDTO> result = new ArrayList<ProjectCopilotStatDTO>();
        Set<Long> copilotsSet = new HashSet<Long>();

        // current user in session
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // get all the copilots belong to the direct project
        List<CopilotBriefDTO> copilots = DataProvider.getCopilotProject(currentUser.getUserId(), directProjectId).getCopilots();

        // initialize the result
        for (CopilotBriefDTO copilot : copilots) {

            ProjectCopilotStatDTO stat = new ProjectCopilotStatDTO();
            ProjectCopilotDTO pc = new ProjectCopilotDTO();

            // set the basic copilot information into ProjectCopilotDTO
            pc.setUserId(copilot.getUserId());
            pc.setHandle(copilot.getHandle());
            pc.setHandleLower(copilot.getHandle().toLowerCase());
            pc.setCopilotProfileId(copilot.getCopilotProfileId());
            pc.setCopilotProjectId(copilot.getCopilotProjectId());
            stat.setCopilotInfo(pc);

            if(!copilotsSet.contains(Long.valueOf(pc.getUserId()))) {
                result.add(stat);
                copilotsSet.add(pc.getUserId());
            }

        }


        // use cached access to store the basic copilot information because it does not change refrequently
        CachedDataAccess cachedAccess = new CachedDataAccess(MaxAge.THREE_HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request profileRequest = new Request();
        profileRequest.setContentHandle("copilot_profile_info");
        ResultSetContainer profileResult;
        Map<Long, ProjectCopilotStatDTO> helperMap = new HashMap<Long, ProjectCopilotStatDTO>();

        // add copilot general info - avatar image path and email
        for (ProjectCopilotStatDTO stat : result) {
            long copilotProfileId = stat.getCopilotInfo().getCopilotProfileId();

            profileRequest.setProperty("uid", String.valueOf(copilotProfileId));

            profileResult = cachedAccess.getData(profileRequest).get("copilot_profile_info");

            stat.getCopilotInfo().setAvatarPath(profileResult.getStringItem(0, "image_path"));

            stat.getCopilotInfo().setEmail(profileResult.getStringItem(0, "email"));

            // add the mapping of copilot user id to ProjectCopilotStatDTO
            helperMap.put(stat.getCopilotInfo().getUserId(), stat);
        }

        // add copilot statistics by counting all the contests with copilots in the project
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_project_copilot_contests");
        request.setProperty("uid", String.valueOf(currentUser.getUserId()));
        request.setProperty("tcdirectid", String.valueOf(directProjectId));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_project_copilot_contests");

        final int recordNum = resultContainer.size();

        for (int i = 0; i < recordNum; i++) {
            long copilotUserId = Long.valueOf(resultContainer.getStringItem(i, "copilot_user_id"));
            long statusId = resultContainer.getLongItem(i, "status_id");
            String status = resultContainer.getStringItem(i, "status");

            ProjectCopilotStatDTO currentStat = helperMap.get(copilotUserId);

            if (currentStat == null) {
                continue;
            }

            if (status.toLowerCase().equals("draft") || statusId == 2L) {
                // count the status id draft and status name draft
                currentStat.setDraftContestsNumber(currentStat.getDraftContestsNumber() + 1);
                continue;
            } else if (status.toLowerCase().equals("completed")) {
                // count the finished contests
                currentStat.setFinishedContestsNumber(currentStat.getFinishedContestsNumber() + 1);
                continue;
            } else if (statusId == 1L) {
                // count the active contests
                currentStat.setActiveContestsNumber(currentStat.getActiveContestsNumber() + 1);
                continue;
            } else if (statusId == 4L || statusId == 5L || statusId == 8L || statusId == 11L || statusId == 6L) {
                // failed contests
                currentStat.setFailuresContestsNumber(currentStat.getFailuresContestsNumber() + 1);
                continue;
            }
        }

        return result;
    }

    /**
     * Gets all the copilot feedback.
     *
     * @return a list of copilot feedback for admin to manage.
     * @throws Exception if there is any error
     * @since 4.7
     */
    public static List<CopilotFeedbackAdminDTO> getAllCopilotFeedback() throws Exception {
        List<CopilotFeedbackAdminDTO> result = new ArrayList<CopilotFeedbackAdminDTO>();

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("get_all_copilot_feedback");

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("get_all_copilot_feedback");

        final int recordNum = resultContainer.size();

        for (int i = 0; i < recordNum; i++) {
            CopilotFeedbackAdminDTO feedbackAdmin = new CopilotFeedbackAdminDTO();
            CopilotProjectFeedback feedback = new CopilotProjectFeedback();

            long copilotProjectid = resultContainer.getLongItem(i, "copilot_project_id");
            long feedackAuthorId = Long.parseLong(resultContainer.getStringItem(i, "create_user"));
            Date submitDate = resultContainer.getTimestampItem(i, "submit_date");
            String text = resultContainer.getStringItem(i, "text");
            String answer = resultContainer.getStringItem(i, "answer");
            String status = resultContainer.getStringItem(i, "status");
            long copilotUserId = resultContainer.getLongItem(i, "copilot_user_id");
            long directProjectId = resultContainer.getLongItem(i, "direct_project_id");
            String directProjectName = resultContainer.getStringItem(i, "direct_project_name");

            if(resultContainer.getItem(i, "time_rating").getResultData() != null) {
                feedback.setTimelineRating(Integer.parseInt(resultContainer.getStringItem(i, "time_rating")));
            }

            if(resultContainer.getItem(i, "quality_rating").getResultData() != null) {
                feedback.setQualityRating(Integer.parseInt(resultContainer.getStringItem(i, "quality_rating")));
            }
            if(resultContainer.getItem(i, "communication_rating").getResultData() != null) {
                feedback.setCommunicationRating(Integer.parseInt(resultContainer.getStringItem(i, "communication_rating")));
            }

            if(resultContainer.getItem(i, "management_rating").getResultData() != null) {
                feedback.setManagementRating(Integer.parseInt(resultContainer.getStringItem(i, "management_rating")));
            }

            feedback.setAuthorId(feedackAuthorId);
            feedback.setSubmitDate(submitDate);
            feedback.setText(text);
            feedback.setAnswer(answer.equalsIgnoreCase("yes"));
            feedback.setStatus(status);
            feedbackAdmin.setFeedback(feedback);
            feedbackAdmin.setCopilotProjectId(copilotProjectid);
            feedbackAdmin.setCopilotUserId(copilotUserId);
            feedbackAdmin.setDirectProjectId(directProjectId);
            feedbackAdmin.setDirectProjectName(directProjectName);

            result.add(feedbackAdmin);
        }

        return result;
    }

    /**
     * <p>Gets the details on <code>TopCoder Direct</code> facts.</p>
     *
     * @return a <code>TopCoderDirectFactsDTO</code> providing the details on <code>TopCoder Direct</code> facts.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static TopCoderDirectFactsDTO getTopCoderDirectFacts() throws Exception {
        CachedDataAccess countDai = new CachedDataAccess(MaxAge.QUARTER_HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request countReq = new Request();
        countReq.setContentHandle("tc_direct_facts");

        ResultSetContainer tcDirectFactsResult = countDai.getData(countReq).get("tc_direct_facts");
        TopCoderDirectFactsDTO result = new TopCoderDirectFactsDTO();
        result.setActiveContestsNumber(tcDirectFactsResult.getIntItem(0, "active_contests_count"));
        result.setActiveMembersNumber(tcDirectFactsResult.getIntItem(0, "active_members_count"));
        result.setActiveProjectsNumber(tcDirectFactsResult.getIntItem(0, "active_projects_count"));
        result.setCompletedProjectsNumber(tcDirectFactsResult.getIntItem(0, "completed_projects_count"));
        if (tcDirectFactsResult.getItem(0, "prize_purse").getResultData() != null) {
            result.setPrizePurse(tcDirectFactsResult.getDoubleItem(0, "prize_purse"));
        }

        CachedDataAccess dai = new CachedDataAccess(MaxAge.QUARTER_HOUR, DBMS.JIRA_DATASOURCE_NAME);
        Request dataRequest = new Request();
        dataRequest.setContentHandle("bug_race_active_contests_summary");

        result.setBugRacesNumber(0);
        result.setBugRacesPrizes(0);

        try
        {
            ResultSetContainer rsc = dai.getData(dataRequest).get("bug_race_active_contests_summary");
            if (!rsc.isEmpty()) {
                result.setBugRacesNumber(rsc.get(0).getIntItem("total_contests"));
                if (rsc.get(0).getItem("total_prizes").getResultData()!=null) {
                    result.setBugRacesPrizes(rsc.get(0).getFloatItem("total_prizes"));
                }
            }
        }
        catch (Exception e)
        {
            // ignore, if we dont have the query
        }


        return result;
    }

    /**
     * <p>Gets the details on latest activities on projects associated with specified user.</p>
     *
     * <p>Updates in version 2.5.0:
     * - Add a query request input 'tcdirectid' and set it to 0. It represents to query on all the direct projects the
     * user has access to or created.
     * - Refactoring the latest activity creation process into helper method fo reuse.
     * </p>
     *
     * @param userId a <code>long</code> providing the user to get the latest activities on associated projects for.
     * @param days an <code>int</code> providing the number of days from current time for selecting activities.
     * @return an <code>LatestActivitiesDTO</code> providing the details on latest activities on projects associated
     *         with the specified user.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static LatestActivitiesDTO getLatestActivitiesForUserProjects(long userId, int days) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_latest_activities_replatforming");
        // Setting to 0 means getting all the direct projects of the user
        request.setProperty("tcdirectid", "0");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("days", String.valueOf(days));

        final Map<ProjectBriefDTO, List<ActivityDTO>> activities = new HashMap<ProjectBriefDTO, List<ActivityDTO>>();
        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final Map<Long, TypedContestBriefDTO> contests = new HashMap<Long, TypedContestBriefDTO>();//here

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_latest_activities_replatforming");
        final int recordNum = resultContainer.size();

        for (int i = 0; i < recordNum; i++) {

            // create ActivityDTO from result row
            ActivityDTO activity = createLatestActivity(resultContainer, i, projects, contests);

            ProjectBriefDTO project = activity.getContest().getProject();
            List<ActivityDTO> projectActivities;

            if (!activities.containsKey(project)) {
                projectActivities = new ArrayList<ActivityDTO>();
                activities.put(project, projectActivities);
            } else {
                projectActivities = activities.get(project);
            }

            projectActivities.add(activity);
        }

        LatestActivitiesDTO result = new LatestActivitiesDTO();


        // sort the activities via activity date
        for (List<ActivityDTO> la : activities.values()) {
            Collections.sort(la, new Comparator<ActivityDTO>() {
                public int compare(ActivityDTO e1, ActivityDTO e2) {
                    return -e1.getDate().compareTo(e2.getDate());
                }
            });
        }

        // sort the map by project's latest activity date
        List<Map.Entry<ProjectBriefDTO, List<ActivityDTO>>> list =
                new LinkedList<Map.Entry<ProjectBriefDTO, List<ActivityDTO>>>(activities.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<ProjectBriefDTO, List<ActivityDTO>>>() {
            public int compare(Map.Entry<ProjectBriefDTO, List<ActivityDTO>> o1, Map.Entry<ProjectBriefDTO, List<ActivityDTO>> o2) {
                ActivityDTO a1 = o1.getValue().get(0);
                ActivityDTO a2 = o2.getValue().get(0);

                if (a1 == null || a2 == null) {
                    return 0;
                } else {
                    return -a1.getDate().compareTo(a2.getDate());
                }

            }
        });

        Map<ProjectBriefDTO, List<ActivityDTO>> sortedActivities = new LinkedHashMap<ProjectBriefDTO, List<ActivityDTO>>();

        for(Map.Entry<ProjectBriefDTO, List<ActivityDTO>> e : list) {
            sortedActivities.put(e.getKey(),e.getValue());
        }

        result.setActivities(sortedActivities);

        return result;
    }


    /**
     * Helper method to get data from result of query 'direct_latest_activities' and instantiate an ActivityDTO
     * instance to represent latest activity and return it as result.
     *
     *
     * @param result the ResultSetContainer returned by running the query
     * @param resultIndex the index to retrieve the result row
     * @param directProjectsMap the map to store direct projects
     * @param contestsMap the map to store the contests
     * @return the instantiated ActivityDTO instance
     * @since 2.5.0 - (Cockpit Performance Improvement Project Overview and Manage Copilot Posting Assembly)
     */
    private static ActivityDTO createLatestActivity(ResultSetContainer result, int resultIndex, Map<Long,
            ProjectBriefDTO> directProjectsMap, Map<Long, TypedContestBriefDTO> contestsMap) {

            String activityTypeText = result.getStringItem(resultIndex, "activity_type");
            long tcDirectProjectId = result.getLongItem(resultIndex, "tc_direct_project_id");
            String tcDirectProjectName = result.getStringItem(resultIndex, "tc_direct_project_name");
            long contestId = result.getLongItem(resultIndex, "contest_id");
            String contestName = result.getStringItem(resultIndex, "contest_name");
            long contestTypeId = result.getLongItem(resultIndex, "contest_type_id");
            Boolean isStudio = result.getBooleanItem(resultIndex, "is_studio");

            long originatorId = Long.parseLong(result.getStringItem(resultIndex, "user_id"));
            String originatorHandle = result.getStringItem(resultIndex, "user");
            Timestamp date = result.getTimestampItem(resultIndex, "activity_time");


            // Sets up the direct project
            final ProjectBriefDTO project;

            if (!directProjectsMap.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                directProjectsMap.put(tcDirectProjectId, project);
            } else {
                project = directProjectsMap.get(tcDirectProjectId);
            }

            final TypedContestBriefDTO contest;
            if (contestsMap.containsKey(contestId)) {
                contest = contestsMap.get(contestId);
            } else {
                contest = createTypedContest(contestId, contestName, project, ContestType.forIdAndFlag(contestTypeId, isStudio), null, !isStudio);//here
                contestsMap.put(contestId, contest);
            }

            ActivityType activityType = ActivityType.forName(activityTypeText);
            ActivityDTO activity = createActivity(contest, date, originatorHandle, originatorId, activityType);

            return activity;
    }

    /**
     * <p>Gets the details on upcoming activities on projects associated with specified user.</p>
     *
     * <p>Updates in version 2.5.0:
     * - Add a new query into for query direct_upcoming_activities, and set it to 0 to represents getting upcoming
     * activities of all the direct projects the user has access to or created.
     * - Refactoring the upcoming activity creation into the helper method for reuse.
     * </p>
     *
     * @param userId a <code>long</code> providing the user to get the upcoming activities on associated projects for.
     * @param days an <code>int</code> providing the number of days from current time for selecting activities.
     * @return an <code>UpcomingActivitiesDTO</code> providing the details on upcoming activities on projects associated
     *         with the specified user.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     */
    public static UpcomingActivitiesDTO getUpcomingActivitiesForUserProjects(long userId, int days) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_upcoming_activities_replatforming");
        request.setProperty("tcdirectid", "0");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("days", String.valueOf(days));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final Map<Long, TypedContestBriefDTO> contests = new HashMap<Long, TypedContestBriefDTO>();
        final List<ActivityDTO> activities = new ArrayList<ActivityDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_upcoming_activities_replatforming");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {

            ActivityDTO activity = createUpcomingActivity(resultContainer, i, projects, contests);

            activities.add(activity);
        }

        UpcomingActivitiesDTO result = new UpcomingActivitiesDTO();
        result.setActivities(activities);
        return result;
    }

    /**
     * <p>
     * Gets te details on upcoming activities of the specified direct project and specified user.
     * </p>
     *
     * @param userId userId a <code>long</code> providing the user to get the upcoming activities on associated projects for.
     * @param projectId a <code>long</code> providing the id of the direct project.
     * @param days  days an <code>int</code> providing the number of days from current time for selecting activities.
     * @return an <code>UpcomingActivitiesDTO</code> providing the details on upcoming activities on specified direct
     *  project.
     * @throws Exception if an unexpected error occurs while communicating to persistent data store.
     * @since 2.5.0 (Cockpit Performance Improvement Project Overview and Manage Copilot Posting)
     */
    public static UpcomingActivitiesDTO getUpcomingActivitiesForProject(long userId, long projectId, int days) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        // initialize the request
        Request request = new Request();
        request.setContentHandle("direct_upcoming_activities_replatforming");
        // set the query input value tcdirectid with the specified direct project id
        request.setProperty("tcdirectid", String.valueOf(projectId));
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("days", String.valueOf(days));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final Map<Long, TypedContestBriefDTO> contests = new HashMap<Long, TypedContestBriefDTO>();
        final List<ActivityDTO> activities = new ArrayList<ActivityDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_upcoming_activities_replatforming");

        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {

            ActivityDTO activity = createUpcomingActivity(resultContainer, i, projects, contests);

            activities.add(activity);
        }

        UpcomingActivitiesDTO result = new UpcomingActivitiesDTO();
        result.setActivities(activities);
        return result;
    }


    /**
     * Helper method to get data from result of query 'direct_upcoming_activities' and instantiate an ActivityDTO
     * instance to represent upcoming activity and return it as result.
     *
     *
     * @param result the ResultSetContainer returned by running the query
     * @param resultIndex the index to retrieve the result row
     * @param directProjectsMap the map to store direct projects
     * @param contestsMap the map to store the contests
     * @return the instantiated ActivityDTO instance
     * @since 2.5.0 - (Cockpit Performance Improvement Project Overview and Manage Copilot Posting Assembly)
     */
    private static ActivityDTO createUpcomingActivity(ResultSetContainer result, int resultIndex, Map<Long,
            ProjectBriefDTO> directProjectsMap, Map<Long, TypedContestBriefDTO> contestsMap) {

        String activityTypeText = result.getStringItem(resultIndex, "activity_type");
        long tcDirectProjectId = result.getLongItem(resultIndex, "tc_direct_project_id");
        String tcDirectProjectName = result.getStringItem(resultIndex, "tc_direct_project_name");
        long contestId = result.getLongItem(resultIndex, "contest_id");
        String contestName = result.getStringItem(resultIndex, "contest_name");
        Boolean isStudio = result.getBooleanItem(resultIndex, "is_studio");

        long originatorId = Long.parseLong(result.getStringItem(resultIndex, "user_id"));
        String originatorHandle = result.getStringItem(resultIndex, "user");
        Timestamp date = result.getTimestampItem(resultIndex, "activity_time");


        // Sets up the direct project
        final ProjectBriefDTO project;

        if (!directProjectsMap.containsKey(tcDirectProjectId)) {
            project = createProject(tcDirectProjectId, tcDirectProjectName);
            directProjectsMap.put(tcDirectProjectId, project);
        } else {
            project = directProjectsMap.get(tcDirectProjectId);
        }

        // set up the contest
        final TypedContestBriefDTO contest;
        if (contestsMap.containsKey(contestId)) {
            contest = contestsMap.get(contestId);
        } else {
            contest = createTypedContest(contestId, contestName, project, null, null, !isStudio);
            contestsMap.put(contestId, contest);
        }

        ActivityType activityType = ActivityType.forName(activityTypeText);
        ActivityDTO activity = createActivity(contest, date, originatorHandle, originatorId, activityType);

        return activity;
    }

    /**
     * <p>Creates new project with specified properties. The project will be associated with the specified user.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for creating projects. Current
     * implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @param projectName a <code>String</code> providing the project name.
     * @param projectDescription a <code>String</code> providing the project description.
     */
    public static void createProject(long userId, String projectName, String projectDescription) {
        MockData.createProject(userId, projectName, projectDescription);
    }

    /**
     * <p>Gets the list of project summary data associated with specified user.</p>
     *
     * @param tcSubject a <code>TCSubject</code> referencing the user.
     * @return a <code>List</code> listing the details for project summary data.
     * @throws Exception if an unexpected error occurs.
     */
    public static List<ProjectSummaryData> getProjectData(TCSubject tcSubject) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_my_projects_contests");

        if(DirectUtils.isCockpitAdmin(tcSubject)) {
            request.setProperty("uid", String.valueOf(0));
        } else {
            request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        }

        List<ProjectSummaryData> projectData = new ArrayList<ProjectSummaryData>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_my_projects_contests");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            ProjectSummaryData data = new ProjectSummaryData();
            data.setProjectId(resultContainer.getLongItem(i, "project_id"));
            data.setProjectName(resultContainer.getStringItem(i, "project_name"));
            data.setDirectProjectStatusId(resultContainer.getLongItem(i, "project_status_id"));
            data.setProjectCreationDate(resultContainer.getTimestampItem(i, "create_date"));
            data.setHasWritePermission(resultContainer.getBooleanItem(i, "has_write_permission"));
           
            if (resultContainer.getItem(i, "completion_date").getResultData() != null) {
                
                data.setProjectCompletionDate(resultContainer.getTimestampItem(i, "completion_date"));
            }
            if (resultContainer.getItem(i, "project_forum_id").getResultData() != null) {
                data.setProjectForumCategoryId(Long.parseLong(resultContainer.getStringItem(i, "project_forum_id")));
            }

            // draft
            data.getDraft().setTotalNumber(resultContainer.getIntItem(i, "num_draft"));

            // scheduled
            data.getScheduled().setTotalNumber(resultContainer.getIntItem(i, "num_scheduled"));

            // active
            data.getActive().setTotalNumber(resultContainer.getIntItem(i, "num_active"));

            // finished
            data.getFinished().setTotalNumber(resultContainer.getIntItem(i, "num_finished"));

            // cancelled
            data.getCancelled().setTotalNumber(resultContainer.getIntItem(i, "num_cancelled"));

            if(resultContainer.getItem(i, "customer_id").getResultData() != null) {
                data.setCustomerId(resultContainer.getLongItem(i, "customer_id"));
                data.setCustomerName(resultContainer.getStringItem(i, "customer_name"));
            } else {
                data.setCustomerId(-1);
                data.setCustomerName("none");
            }

            projectData.add(data);
        }

        return projectData;
    }

    /**
     * <p>Gets the details on projects associated with specified user and matching the specified criteria.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the matching
     * records. Current implementation uses mock data.</p>
     *
     * @param tcSubject the <code>TCSubject</code> entity
     * @param searchFor the value which will be searched against
     * @return a <code>List</code> providing the details on projects associated with the specified user.
     *
     * @throws Exception if any error occurs
     */
    public static List<DashboardProjectSearchResultDTO> searchUserProjects(TCSubject tcSubject, String searchFor)
        throws Exception {
        List<ProjectSummaryData> projects = getProjectData(tcSubject);
        return getFilteredProjects(searchFor, projects);
    }

    /**
     * Search the project by searchFor criteria, and set the customer id to the project dto.
     *
     * @param searchFor the search criteria
     * @param projects the project list to be searched
     * @return the search result
     * @throws Exception if any error occurs
     * @since 5.2
     */
    private static List<DashboardProjectSearchResultDTO> getFilteredProjects(String searchFor,
        List<ProjectSummaryData> projects) throws Exception {
        List<ProjectSummaryData> filteredProjects;

        if (StringUtils.isBlank(searchFor)) {
            filteredProjects = projects;
        } else {
            final String searchForLowerCase = searchFor.toLowerCase();
            filteredProjects = (List<ProjectSummaryData>) CollectionUtils.select(projects, new Predicate() {
                //@Override
                public boolean evaluate(Object data) {
                    ProjectSummaryData project = (ProjectSummaryData) data;
                    return project.getProjectName() != null
                        && project.getProjectName().toLowerCase().contains(searchForLowerCase);
                }
            });
        }

        // perform transfrom from ProjectSummaryData to DashboardProjectSearchResultDTO
        return (List<DashboardProjectSearchResultDTO>) CollectionUtils.collect(filteredProjects, new Transformer() {
            //@Override
            public Object transform(Object data) {
                ProjectSummaryData project = (ProjectSummaryData) data;
                DashboardProjectSearchResultDTO dto = new DashboardProjectSearchResultDTO();
                dto.setData(project);
                return dto;
            }
        });
    }

    /**
     * <p>
     * Gets the details on Platform Managers' projects associated with specified user and matching the specified
     * criteria.
     * </p>
     *
     * @param tcSubject the <code>TCSubject</code> entity
     * @param searchFor the value which will be searched against
     * @return a <code>List</code> providing the details on Platform Managers' projects associated with the specified user.
     *
     * @throws Exception if any error occurs
     * @since 5.2
     */
    public static List<DashboardProjectSearchResultDTO> searchPMUserProjects(TCSubject tcSubject, String searchFor)
        throws Exception {
        List<ProjectSummaryData> projects = getPMProjectData(tcSubject);
        return getFilteredProjects(searchFor, projects);
    }

    /**
     * <p>Gets the list of Platform Managers' project summary data associated with specified user.</p>
     * Change in version 5.8: retrieve last three posters from database.
     *
     * @param tcSubject a <code>TCSubject</code> referencing the user.
     * @return a <code>List</code> listing the details for project summary data.
     * @throws Exception if an unexpected error occurs.
     * @since 5.2
     */
    public static List<ProjectSummaryData> getPMProjectData(TCSubject tcSubject) throws Exception {
		CachedDataAccess dataAccessor = new CachedDataAccess(MaxAge.HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_my_pm_projects_contests_v3");

        if (DirectUtils.isCockpitAdmin(tcSubject)) {
            request.setProperty("uid", String.valueOf(0));
        } else {
            request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        }

        List<ProjectSummaryData> projectData = new ArrayList<ProjectSummaryData>();

        Map<String, ResultSetContainer> result = dataAccessor.getData(request);
        ResultSetContainer resultContainer = result.get("direct_my_pm_projects_contests_v3");
        Map<Long, ProjectSummaryData> projectDataLookup = new HashMap<Long, ProjectSummaryData>();
        
        for (ResultSetContainer.ResultSetRow row : resultContainer) {
            ProjectSummaryData data = new ProjectSummaryData();
            if (row.getItem("tc_direct_project_id").getResultData() != null) {
            	data.setProjectId(row.getLongItem("tc_direct_project_id"));
                projectDataLookup.put(data.getProjectId(), data);
            }
            if (row.getItem("tc_direct_project_name").getResultData() != null) {
            	data.setProjectName(row.getStringItem("tc_direct_project_name"));
            }
            if (row.getItem("customer_id").getResultData() != null) {
            	data.setCustomerId(row.getLongItem("customer_id"));
            }
            if (row.getItem("customer_name").getResultData() != null) {
            	data.setCustomerName(row.getStringItem("customer_name"));
            }
            if (row.getItem("project_status_id").getResultData() != null) {
            	data.setDirectProjectStatusId(row.getLongItem("project_status_id"));
            }
            if (row.getItem("start_date").getResultData() != null) {
            	data.setProjectCreationDate(getDate(row, "start_date"));
            }
            if (row.getItem("end_date").getResultData() != null) {
            	data.setProjectCompletionDate(getDate(row,"end_date"));
            }
            if (row.getItem("project_forum_id").getResultData() != null) {
                data.setProjectForumCategoryId(Long.parseLong(row.getStringItem("project_forum_id")));
                data.setLatestThreePosters(getLatestThreePosters(row.getStringItem("last_posters")));
            }
            if (row.getItem("project_fulfillment").getResultData() != null) {
            	data.setProjectFulfillment(row.getDoubleItem("project_fulfillment"));
            }
            if (row.getItem("total_budget").getResultData() != null) {
            	data.setTotalBudget(row.getStringItem("total_budget"));
            }
            if (row.getItem("actual_cost").getResultData() != null) {
            	data.setActualCost(row.getDoubleItem("actual_cost"));
            }
            if (row.getItem("cost_projected").getResultData() != null) {
            	data.setProjectedCost(row.getDoubleItem("cost_projected"));
            }
            if (row.getItem("planned_duration").getResultData() != null) {
            	data.setPlannedDuration(row.getStringItem("planned_duration"));
            }
            if (row.getItem("actual_duration").getResultData() != null) {
            	data.setActualDuration(row.getStringItem("actual_duration"));
            }
            if (row.getItem("projected_duration").getResultData() != null) {
            	data.setProjectedDuration(row.getStringItem("projected_duration"));
            }
            if (row.getItem("message_number").getResultData() != null) {
            	data.setMessageNumber(row.getIntItem("message_number"));
            }
            if (row.getItem("name_of_project_manager").getResultData() != null) {
            	data.setProjectManagerName(row.getStringItem("name_of_project_manager"));
            }

            if (row.getItem("stalled_contests_number").getResultData() != null) {
            	data.setHasStalledContests(row.getIntItem("stalled_contests_number") > 0);
            }
            if (row.getItem("has_late_contests").getResultData() != null) {
            	data.setHasLateContests(row.getBooleanItem("has_late_contests"));
            }
            if (row.getItem("phase_late_contests_number").getResultData() != null) {
            	data.setPhaseLateContestsNum(row.getIntItem("phase_late_contests_number"));
            }
            if (row.getItem("launch_late_contests_number").getResultData() != null) {
            	data.setLaunchLateContestsNum(row.getIntItem("launch_late_contests_number"));
            }
            if (row.getItem("milestone_late_contests_number").getResultData() != null) {
            	data.setCheckpointLateContestsNum(row.getIntItem("milestone_late_contests_number"));
            }
            if (row.getItem("stalled_contests_number").getResultData() != null) {
            	data.setStalledContestsNum(row.getIntItem("stalled_contests_number"));
            }
            if (row.getItem("ap_off_contests_number").getResultData() != null) {
            	data.setApOffContestsNum(row.getIntItem("ap_off_contests_number"));
            }
            if (row.getItem("historical_projected_cost").getResultData() != null) {
            	data.setHistoricalProjectedCost(row.getDoubleItem("historical_projected_cost"));
            }
            if (row.getItem("copilots").getResultData() != null) {
                data.setCopilotNames(row.getStringItem("copilots"));
            }
            if (row.getItem("launch_delayed_time").getResultData() != null) {
                Timestamp launchDelayedTime = row.getTimestampItem("launch_delayed_time");
                Date now = new Date();
                long diff = now.getTime() - launchDelayedTime.getTime();
                diff /= 1000;
                long hoursDelayed = diff / 3600;
                if (hoursDelayed > 0) {
                    data.setLaunchLateDelay(hoursDelayed); // There is at least 1 hour of delay
                } else if (diff % 3600 > 0) {
                    data.setLaunchLateDelay(0L);           // There is less than 1 hour of delay so 0 indicates that
                                                           // and null (set by default) will indicate that there is 
                                                           // no delay
                }
            }
            if (row.getItem("late_phase_end_time").getResultData() != null) {
                Timestamp latePhaseEndTime = row.getTimestampItem("late_phase_end_time");
                Date now = new Date();
                double diffInDays = (now.getTime() - latePhaseEndTime.getTime()) * 1D / 1000 / 3600 / 24;
                data.setLatePhaseDelay(diffInDays);
            }
            data.setActiveContestsCount(new HashMap<String, Integer>());

            projectData.add(data);
        }
        
        // Collect the data for the active contests
        resultContainer = result.get("direct_project_active_contest_stats");
        for (ResultSetContainer.ResultSetRow row : resultContainer) {
            long tcDirectProjectId = row.getLongItem("tc_direct_project_id");
            if (projectDataLookup.containsKey(tcDirectProjectId)) {
                String projectCategoryName = row.getStringItem("project_category_name");
                int activeContestsCount = row.getIntItem("active_contests_count");
                projectDataLookup.get(tcDirectProjectId).getActiveContestsCount().put(projectCategoryName, activeContestsCount);
            }
        }

        return projectData;
    }
    /**
     * Gets latest three posters.
     *
     * @param lastPosters the last posters
     * @return the latest three posters
     * @throws Exception if an unexpected error occurs.
     * @since 5.8
     */
    private static List<ForumPoster> getLatestThreePosters(String lastPosters) throws Exception {
        List<ForumPoster> posters = new ArrayList<ForumPoster>();

    if (lastPosters == null || lastPosters.equals("")) {
      return posters;
    }

        String[] postersStr = lastPosters.split(";");
        for(String p : postersStr) {
            String[] parts = p.split(","); // userid,handle,days
            ForumPoster data = new ForumPoster();
            data.setHandleId(parts[0]);
            data.setHandle(parts[1]);
            data.setDaysSincePost(parts[2]);
            posters.add(data);
        }
        return posters;
    }

    /**
     * <p>Gets the details on contests associated with specified user and matching the specified criteria.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the matching
     * records. Current implementation uses mock data.</p>
     *
     * @param tcSubject the <code>TCSubject</code> entity
     * @param searchFor the value which will be searched against
     * @param begin the begin date for contest start date
     * @param end the end date for contest start date
     * @return a <code>List</code> providing the details on contests associated with the specified user.
     *
     * @throws Exception if any error occurs
     */
    public static List<DashboardContestSearchResultDTO> searchUserContests(TCSubject tcSubject, String searchFor,
        final Date begin, final Date end) throws Exception {
        List<CommonProjectContestData> contests = DirectUtils.getContestServiceFacade().getCommonProjectContestData(
            tcSubject);
        List<CommonProjectContestData> filteredContests;
        if (StringUtils.isBlank(searchFor) && begin == null && end == null) {
            filteredContests = contests;
        } else {
            final String searchForLowerCase = searchFor.toLowerCase();
            filteredContests = (List<CommonProjectContestData>) CollectionUtils.select(contests, new Predicate() {
                //@Override
                public boolean evaluate(Object data) {
                    CommonProjectContestData contest = (CommonProjectContestData) data;
                    return isMatched(contest, searchForLowerCase, begin, end);
                }
            });
        }

        return (List<DashboardContestSearchResultDTO>) CollectionUtils.collect(filteredContests, new Transformer() {
            //@Override
            public Object transform(Object data) {
                CommonProjectContestData contest = (CommonProjectContestData) data;
                DashboardContestSearchResultDTO dto = new DashboardContestSearchResultDTO();

                ContestBriefDTO brief = new ContestBriefDTO();
                brief.setId(contest.getContestId());
                brief.setTitle(contest.getCname());
                brief.setSoftware(!"Studio".equals(contest.getType()));
                brief.setContestTypeName(contest.getType());
                dto.setContest(brief);

                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(contest.getProjectId());
                project.setName(contest.getPname());
                brief.setProject(project);

                dto.setContestType(contest.getType());
                dto.setStartTime(DirectUtils.getDate(contest.getStartDate()));
                dto.setEndTime(DirectUtils.getDate(contest.getEndDate()));
                dto.setRegistrantsNumber(contest.getNum_reg());
                dto.setSubmissionsNumber(contest.getNum_sub());
                dto.setForumPostsNumber(contest.getNum_for());
                if (contest.getForumId() != null)
                {
                    dto.setForumId(contest.getForumId());
                }
                else
                {
                    dto.setForumId(-1);
                }
                dto.setStatus(ContestStatus.forName(contest.getSname()));
                return dto;
            }
        });
    }

    /**
     * Tests to see if given contest is matched against all conditions.
     *
     * @param contest the contest object
     * @param searchFor the search for string
     * @param begin the beging date
     * @param end the end date
     * @return true if matched otherwise false
     */
    private static boolean isMatched(CommonProjectContestData contest, String searchFor, Date begin, Date end) {

        String searchForLower = searchFor.toLowerCase();

        if (!StringUtils.isBlank(searchFor) && contest.getCname() != null
            && !contest.getCname().toLowerCase().contains(searchForLower)) {
            return false;
        }

        Date contestStartDate = DirectUtils.getDateWithoutTime((DirectUtils.getDate(contest.getStartDate())));
        if (begin != null && contestStartDate != null && begin.compareTo(contestStartDate) > 0) {
            return false;
        }

        if (end != null && contestStartDate != null && end.compareTo(contestStartDate) < 0) {
            return false;
        }

        return true;
    }

    /**
     * <p>
     * Gets the details on users assigned to projects associated with specified user and matching the specified
     * criteria.
     * </p>
     *
     * <p>
     * Sub-sequent assemblies must implement this method to use the appropriate logic for getting the matching
     * records. Current implementation uses mock data.
     * </p>
     *
     * @param userId a <code>long</code> providing the user to get the list of matching users for.
     * @return a <code>List</code> providing the details on users assigned to projects associated with the specified
     *         user.
     */
    public static List<DashboardMemberSearchResultDTO> searchUserProjectMembers(long userId, String searchFor) {
        return MockData.searchUserProjectMembers(userId, searchFor);
    }

    /**
     * <p>Gets the list of projects associated with specified user.</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @return a <code>List</code> listing the details for user projects.
     * @throws Exception if an unexpected error occurs.
     */
    public static List<ProjectBriefDTO> getUserProjects(long userId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_my_projects_v2");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("directProjectStatusId", "1");

        List<ProjectBriefDTO> projects = new ArrayList<ProjectBriefDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_my_projects_v2");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            projects.add(createProject(tcDirectProjectId, tcDirectProjectName));
        }


        // get current user in session
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // if user id equals to current user id, get customers for it
        if (currentUser.getUserId() == userId) {
            // get all clients of the user
            Map<Long, String> allClients = DirectUtils.getAllClients(currentUser);

            for(Map.Entry<Long, String> client : allClients.entrySet()) {
                Map<Long, String> projectsForClient = DirectUtils.getProjectsForClient(currentUser, client.getKey());

                for(ProjectBriefDTO p : projects) {
                    if (projectsForClient.containsKey(p.getId())) {
                        p.setCustomerId(client.getKey());
                        p.setCustomerName(client.getValue());
                    }
                }

            }
        }

        return projects;
    }

    /**
     * <p>Gets the list of projects associated with specified user.</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @return a <code>List</code> listing the details for user projects.
     * @throws Exception if an unexpected error occurs.
     */
    public static List<ProjectBriefDTO> getUserProjectsList(long userId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_my_projects_v2");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("directProjectStatusId", "0");

        List<ProjectBriefDTO> projects = new ArrayList<ProjectBriefDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_my_projects_v2");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            projects.add(createProject(tcDirectProjectId, tcDirectProjectName));
        }

        return projects;
    }

    /**
     * <p>
     * Gets the stats on specified project.
     * </p>
     *
     * <p>Update notes in version 2.5.0:
     * Update to use a new simplified query 'direct_project_overview_statistics' to improvement the performance.
     * </p>
     *
     * @param projectId a <code>long</code> providing the ID for requested project.
     * @return a <code>ProjectStatsDTO</code> providing the stats for requested project.
     */
    public static ProjectStatsDTO getProjectStats(TCSubject tcSubject, long projectId) throws Exception {

        ProjectStatsDTO stats = new ProjectStatsDTO();
        ProjectBriefDTO project = new ProjectBriefDTO();

        double draftFee = 0, activeFee = 0, scheduledFee = 0, finishedFee = 0, cancelledFee = 0;
        int draftNum = 0, activeNum = 0, scheduledNum = 0, finishedNum = 0, cancelledNum = 0;

        String tcDirectProjectName = "";

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_project_overview_statistics");
        request.setProperty("uid", String.valueOf(tcSubject.getUserId()));
        request.setProperty("tcdirectid", String.valueOf(projectId));

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_project_overview_statistics");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
             String statusName = resultContainer.getStringItem(i, "status");

             double fee = 0;

             try {
                fee = resultContainer.getDoubleItem(i, "contest_payment");
             } catch(IllegalArgumentException ex) {
                 // ignore the IllegalArgumentException if query result does not have contest_payment column
             } catch(NullPointerException ex) {
                 // ignore the NullPointerException if query result does not have contest_payment column
             }

             tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");

             if(DirectUtils.ACTIVE_STATUS.contains(statusName)) {
                 activeNum++;
                 activeFee += fee;
             } else if (DirectUtils.DRAFT_STATUS.contains(statusName)) {
                 draftNum++;
                 draftFee += fee;
             } else if (DirectUtils.SCHEDULED_STATUS.contains(statusName)) {
                 scheduledNum++;
                 scheduledFee += fee;
             } else if (DirectUtils.FINISHED_STATUS.contains(statusName)) {
                 finishedNum++;
                 finishedFee += fee;
             } else if (DirectUtils.CANCELLED_STATUS.contains(statusName)) {
                 cancelledNum++;
                 cancelledFee += fee;
             }

        }

        // direct project info
        project.setId(projectId);
        project.setName(tcDirectProjectName);

        // contests numbers
        stats.setDraftContestsNumber(draftNum);
        stats.setRunningContestsNumber(activeNum);
        stats.setPipelineContestsNumber(scheduledNum);
        stats.setFinishedContestsNumber(finishedNum);
        stats.setCancelledContestsNumber(cancelledNum);

        // contests fees
        stats.setFeesFinalized(finishedFee);
        stats.setFeesRunning(activeFee);
        stats.setFeesDraft(draftFee);
        stats.setFeesScheduled(scheduledFee);
        stats.setFeesCancelled(cancelledFee);


        stats.setProject(project);

        return stats;
    }

    /**
     * Gets the project metrics report with the given paramters. The method will retrieve the project metrics
     * data into parameter <code>statses</code>
     *
     * @param currentUser        the current user.
     * @param projectId          the direct project id.
     * @param clientId           the client id.
     * @param billingAccountId   the billing accounts id.
     * @param projectStatus      the project status.
     * @param startDate          the start date.
     * @param endDate            the end date.
     * @param statses            the project metrics data.
     *
     * @throws Exception if any error occurs.
     * @since 3.9.1
     */
    public static void getDashboardProjectMetricsReport(TCSubject currentUser, long projectId,
            long clientId, long billingAccountId, long[] projectStatusIds,
            Date startDate, Date endDate, List<ProjectMetricsReportEntryDTO> statses) throws Exception {

            if (projectStatusIds == null || (projectStatusIds.length == 0)) {
                return;
            }

            // concatenate the filters
            String projectStatusesList = concatenate(projectStatusIds, ", ");

            // date format to prepare date for query input
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
            Request request = new Request();

            if (!setReportQueryParameters(request, currentUser, clientId, billingAccountId, projectId)) {
                return;
            }

            request.setProperty("uid", String.valueOf(currentUser.getUserId()));
            request.setProperty("sdt", dateFormatter.format(startDate));
            request.setProperty("edt", dateFormatter.format(endDate));
            request.setProperty("directProjectStatusIds", projectStatusesList);
            request.setContentHandle("dashboard_project_metrics_report_v2");
            final Map<String, ResultSetContainer> queryData = dataAccessor.getData(request);

            // Project metrics
            final ResultSetContainer resultContainer = queryData.get("dashboard_project_metrics_report_v2");
            for (ResultSetContainer.ResultSetRow row : resultContainer) {

                ProjectMetricsReportEntryDTO stats = new ProjectMetricsReportEntryDTO();
                stats.setProjectId(row.getLongItem("tc_direct_project_id"));
                stats.setProjectName(row.getStringItem("tc_direct_project_name"));
                stats.setProjectType(row.getStringItem("direct_project_type_name"));
                stats.setProjectStatus(row.getStringItem("project_status_name"));
                stats.setProjectFulfillment(row.getDoubleItem("fullfillment"));
                stats.setTotalBudget(row.getStringItem("total_budget"));
                stats.setActualCost(row.getDoubleItem("actual_cost"));
                stats.setStartDate(getDate(row,"create_date"));
                stats.setCompletionDate(getDate(row,"completion_date"));
                stats.setTotalContests(row.getIntItem("total_number"));

                // draft
                stats.setNumDraft(row.getIntItem("num_draft"));
                stats.setCostDraft(row.getDoubleItem("cost_draft"));

                // scheduled
                stats.setNumScheduled(row.getIntItem("num_scheduled"));
                stats.setCostScheduled(row.getDoubleItem("cost_scheduled"));

                // active
                stats.setNumActive(row.getIntItem("num_active"));
                stats.setCostActive(row.getDoubleItem("cost_active"));

                // finished
                stats.setNumFinished(row.getIntItem("num_finished"));
                stats.setCostFinished(row.getDoubleItem("cost_finished"));

                // cancelled
                stats.setNumCanceled(row.getIntItem("num_cancelled"));
                stats.setCostCanceled(row.getDoubleItem("cost_cancelled"));

                stats.setProjectedCost(row.getDoubleItem("cost_draft") + row.getDoubleItem("cost_scheduled") +
                		row.getDoubleItem("cost_active") + row.getDoubleItem("cost_finished") +
                		row.getDoubleItem("cost_cancelled") + row.getDoubleItem("project_level_cost"));

                stats.setPlannedCost(row.getDoubleItem("planned_cost"));
                statses.add(stats);
            }
    }

    /**
     * Gets the Jira Issue Report data.
     *
     * @param currentUser the current user to retrive the report.
     * @param projectId the direct project id.
     * @param clientId the client id.
     * @param billingAccountId the billing account id.
     * @param jiraIssuesStatusIds the ids of jira issue status.
     * @param startDate the start date
     * @param endDate the end date.
     * @return the jira issue report data.
     * @throws Exception if there is any error.
     * @since 4.8
     */
    public static List<JiraIssuesReportEntryDTO> getDashboardJiraIssuesReport(TCSubject currentUser, long projectId,
                                                        long clientId, long billingAccountId, long[] jiraIssuesStatusIds,
                                                        Date startDate, Date endDate) throws Exception {
        List<JiraIssuesReportEntryDTO> result = new ArrayList<JiraIssuesReportEntryDTO>();

        if (jiraIssuesStatusIds == null || (jiraIssuesStatusIds.length == 0)) {
            // return empty list
            return result;
        }

        // build the jira status filter
        List<String> jiraStatusName = new ArrayList<String>();

        for(long statusId : jiraIssuesStatusIds) {
            if(statusId == JiraIssueStatus.ACCEPTED.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.ACCEPTED.getStatusName());
            } else if(statusId == JiraIssueStatus.APPROVED.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.APPROVED.getStatusName());
            } else if(statusId == JiraIssueStatus.CLOSED.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.CLOSED.getStatusName());
            } else if(statusId == JiraIssueStatus.FORMAL_REVIEW.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.FORMAL_REVIEW.getStatusName());
            } else if(statusId == JiraIssueStatus.HOLD_FOR_3RD_PARTY.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.HOLD_FOR_3RD_PARTY.getStatusName());
            } else if(statusId == JiraIssueStatus.HOLD_FOR_CUSTOMER.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.HOLD_FOR_CUSTOMER.getStatusName());
            }else if(statusId == JiraIssueStatus.HOLD_FOR_IT.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.HOLD_FOR_IT.getStatusName());
            } else if(statusId == JiraIssueStatus.IN_PROGRESS.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.IN_PROGRESS.getStatusName());
            } else if(statusId == JiraIssueStatus.INFORMAL_REVIEW.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.INFORMAL_REVIEW.getStatusName());
            } else if(statusId == JiraIssueStatus.INFORMAL_REVIEW_PENDING.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.INFORMAL_REVIEW_PENDING.getStatusName());
            } else if(statusId == JiraIssueStatus.LIVE_DESIGN.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.LIVE_DESIGN.getStatusName());
            } else if(statusId == JiraIssueStatus.LIVE_DEVELOPMENT.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.LIVE_DEVELOPMENT.getStatusName());
            } else if(statusId == JiraIssueStatus.NEW_REQUEST.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.NEW_REQUEST.getStatusName());
            } else if(statusId == JiraIssueStatus.ON_HOLD.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.ON_HOLD.getStatusName());
            } else if(statusId == JiraIssueStatus.OPEN.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.OPEN.getStatusName());
            } else if(statusId == JiraIssueStatus.PREPPING.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.PREPPING.getStatusName());
            } else if(statusId == JiraIssueStatus.READY_TO_DEPLOY_TO_DEV.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.READY_TO_DEPLOY_TO_DEV.getStatusName());
            } else if(statusId == JiraIssueStatus.READY_TO_DEPLOY_TO_PROD.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.READY_TO_DEPLOY_TO_PROD.getStatusName());
            } else if(statusId == JiraIssueStatus.READY_TO_DEPLOY_TO_TEST.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.READY_TO_DEPLOY_TO_TEST.getStatusName());
            } else if(statusId == JiraIssueStatus.REOPENED.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.REOPENED.getStatusName());
            } else if(statusId == JiraIssueStatus.RESOLVED.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.RESOLVED.getStatusName());
            } else if(statusId == JiraIssueStatus.STUCK.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.STUCK.getStatusName());
            } else if(statusId == JiraIssueStatus.TESTING.getStatusId()) {
                jiraStatusName.add(JiraIssueStatus.TESTING.getStatusName());
            }
        }

        // concatenate the filters
        String jiraStatusesList = concatenate(jiraStatusName.toArray(new String[jiraStatusName.size()]), ", ");

        // date format to prepare date for query input
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();

        if (!setReportQueryParameters(request, currentUser, clientId, billingAccountId, projectId)) {
            return result;
        }


    if(DirectUtils.isTcStaff(currentUser)) {
            request.setProperty("uid", String.valueOf(0));
        } else {
            request.setProperty("uid", String.valueOf(currentUser.getUserId()));
        }
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("jirastatuses", jiraStatusesList);
        request.setContentHandle("dashboard_jira_issues_report");
        final Map<String, ResultSetContainer> queryData = dataAccessor.getData(request);

        final Map properties = request.getProperties();

        // get all jira issues for the report
        final ResultSetContainer resultContainer = queryData.get("dashboard_jira_issues_report");
        for (ResultSetContainer.ResultSetRow row : resultContainer) {

            JiraIssuesReportEntryDTO jiraIssue = new JiraIssuesReportEntryDTO();

            jiraIssue.setCustomer(row.getStringItem("customer"));
            jiraIssue.setBillingAccount(row.getStringItem("billingaccount"));

            if(row.getItem("contestname").getResultData() != null) {
                jiraIssue.setContestName(row.getStringItem("contestname"));
            }

            if(row.getItem("contestid").getResultData() != null) {
                jiraIssue.setContestId(row.getLongItem("contestid"));
            }

            jiraIssue.setTicketId(row.getStringItem("ticketid"));
            jiraIssue.setTicketTitle(row.getStringItem("tickettitle"));
            jiraIssue.setTicketDescription(row.getStringItem("ticketdescription"));
            jiraIssue.setPrize(row.getDoubleItem("prize"));
            jiraIssue.setStatus(row.getStringItem("status"));
            jiraIssue.setReporter(row.getStringItem("reporter"));
            jiraIssue.setAssignee(row.getStringItem("assignee"));
            jiraIssue.setTcoPoints(row.getIntItem("tcopoints"));
            jiraIssue.setLaunchDate(row.getTimestampItem("launchdate"));
            jiraIssue.setResolutionDate(row.getTimestampItem("resolutiondate"));
            jiraIssue.setVotesNumber(row.getIntItem("votesnumber"));
            jiraIssue.setWinner(row.getStringItem("winner"));
            jiraIssue.setProjectId(row.getLongItem("directprojectid"));
            jiraIssue.setProjectName(row.getStringItem("directprojectname"));

            result.add(jiraIssue);
        }

        return result;
    }

    /**
     * <p>Gets the details on latest activities on contests associated with specified project.</p>
     *
     * <p>
     * Updates in version 2.5.0: Remove the logic of calling of getLatestActivitiesForUserProjects and filter by
     * direct project id. This causes performance issue. In the new implementation, the direct project id is set as
     * input of the query to improve the performance.
     * .</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @param projectId a <code>long</code> providing the ID for project to get the latest activities on associated
     *        contests for.
     * @return an <code>LatestProjectActivitiesDTO</code> providing the details on latest activities on contests
     *         associated with the specified project.
     * @throws Exception if an unexpected error occurs.
     */
    public static LatestProjectActivitiesDTO getLatestActivitiesForProject(long userId, long projectId)
        throws Exception {

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_latest_activities_replatforming");

        // set the value of direct project id
        request.setProperty("tcdirectid", String.valueOf(projectId));
        request.setProperty("uid", String.valueOf(userId));

        // get the activities of last 15 days
        request.setProperty("days", "15");

        final Map<ContestBriefDTO, List<ActivityDTO>> activities = new HashMap<ContestBriefDTO, List<ActivityDTO>>();
        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final Map<Long, TypedContestBriefDTO> contests = new HashMap<Long, TypedContestBriefDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_latest_activities_replatforming");
        final int recordNum = resultContainer.size();

        for (int i = 0; i < recordNum; i++) {
            ActivityDTO activity = createLatestActivity(resultContainer, i, projects, contests);

            List<ActivityDTO> contestActivities;

            if (activities.containsKey(activity.getContest())) {
                contestActivities = activities.get(activity.getContest());
            } else {
                contestActivities = new ArrayList<ActivityDTO>();
                activities.put(activity.getContest(), contestActivities);
            }

            contestActivities.add(activity);
        }

        // sort the activities via activity date
        for (List<ActivityDTO> la : activities.values()) {
            Collections.sort(la, new Comparator<ActivityDTO>() {
                public int compare(ActivityDTO e1, ActivityDTO e2) {
                    return -e1.getDate().compareTo(e2.getDate());
                }
            });
        }

        // sort the map by contest's latest activity date
        List<Map.Entry<ContestBriefDTO, List<ActivityDTO>>> list =
                new LinkedList<Map.Entry<ContestBriefDTO, List<ActivityDTO>>>(activities.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<ContestBriefDTO, List<ActivityDTO>>>() {
            public int compare(Map.Entry<ContestBriefDTO, List<ActivityDTO>> o1, Map.Entry<ContestBriefDTO, List<ActivityDTO>> o2) {
                ActivityDTO a1 = o1.getValue().get(0);
                ActivityDTO a2 = o2.getValue().get(0);

                if (a1 == null || a2 == null) {
                    return 0;
                } else {
                    return -a1.getDate().compareTo(a2.getDate());
                }

            }
        });

        Map<ContestBriefDTO, List<ActivityDTO>> sortedActivities = new LinkedHashMap<ContestBriefDTO, List<ActivityDTO>>();

        for (Map.Entry<ContestBriefDTO, List<ActivityDTO>> e : list) {
            sortedActivities.put(e.getKey(), e.getValue());
        }


        LatestProjectActivitiesDTO dto = new LatestProjectActivitiesDTO();
        dto.setActivities(sortedActivities);

        return dto;
    }


    /**
     * <p>Gets the details on contests associated with the specified project.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * project. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user associated with project.
     * @param projectId a <code>long</code> providing the ID for project to get the details for associated contests for.
     * @return a <code>ProjectContestsListDTO</code> providing the details on contests associated with specified
     *         project.
     * @throws Exception if an unexpected error occurs.
     */
    public static ProjectContestsListDTO getProjectContests(long userId, long projectId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_my_contests_replatforming");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("tcdirectid", String.valueOf(projectId));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final List<ProjectContestDTO> contests = new ArrayList<ProjectContestDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_my_contests_replatforming");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            long contestId = resultContainer.getLongItem(i, "contest_id");
            String contestName = resultContainer.getStringItem(i, "contest_name");
            String statusName = resultContainer.getStringItem(i, "status");
            String typeName = resultContainer.getStringItem(i, "contest_type");
            String billingAccountStr = resultContainer.getStringItem(i, "billing_account_id");

            long billingAccountId = Long.parseLong(billingAccountStr);

            //TODO, this is to not affecting existing VMs, will change later
            long contestTypeId = 1;
            try
            {
                contestTypeId = resultContainer.getLongItem(i, "contest_type_id"); // BUGR-3913
            }
            catch (Exception e)
            {
            }

            Timestamp startDate = resultContainer.getTimestampItem(i, "start_date");
            Timestamp endDate = resultContainer.getTimestampItem(i, "end_date");
            int forumPostsCount = resultContainer.getIntItem(i, "number_of_forum");
            int registrantsCount = resultContainer.getIntItem(i, "number_of_registration");
            int submissionsCount = resultContainer.getIntItem(i, "number_of_submission");
            Boolean isStudio = resultContainer.getBooleanItem(i, "is_studio");
            int forumId = -1;
            try
                {
                    if (resultContainer.getStringItem(i, "forum_id") != null
                        && !resultContainer.getStringItem(i, "forum_id").equals("")) {
                        forumId = Integer.parseInt(resultContainer.getStringItem(i, "forum_id"));
                    }
            }
            catch (NumberFormatException ne)
            {
            // ignore
            }


            final ProjectBriefDTO project;
            if (!projects.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                projects.put(tcDirectProjectId, project);
            } else {
                project = projects.get(tcDirectProjectId);
            }

            ContestBriefDTO contestBrief = createContest(contestId, contestName, project, !isStudio, typeName);
            contestBrief.setBillingAccountId(billingAccountId);
            ContestType type = ContestType.forIdAndFlag(contestTypeId, isStudio);
            ContestStatus status = ContestStatus.forName(statusName);

            ProjectContestDTO contest = createProjectContest(contestBrief, type, status, startDate, endDate,
                                                             forumPostsCount, registrantsCount, submissionsCount, forumId, isStudio);

            if (resultContainer.getStringItem(i, "forum_type") != null
                    && !resultContainer.getStringItem(i, "forum_type").equals("")) {
                contest.setNewForum(true);
            } else {
                contest.setNewForum(false);
            }

            contests.add(contest);
        }

        ProjectContestsListDTO dto = new ProjectContestsListDTO();
        dto.setContests(contests);
        return dto;
    }

    /**
     * <p>Gets the details for active contests from the projects assigned to specified user.</p>
     *
     * @param userId a <code>long</code> providing the user ID.
     * @return a <code>ProjectContestsListDTO</code> providing the details for active contests.
     * @throws Exception if an unexpected error occurs.
     */
    public static ProjectContestsListDTO getActiveContests(long userId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_active_contests_replatforming");
        request.setProperty("uid", String.valueOf(userId));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final List<ProjectContestDTO> contests = new ArrayList<ProjectContestDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_active_contests_replatforming");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            long contestId = resultContainer.getLongItem(i, "contest_id");
            String contestName = resultContainer.getStringItem(i, "contest_name");

            String activePhaseName = null;
            if(resultContainer.getItem(i, "active_phase").getResultData() != null) {
                activePhaseName = resultContainer.getStringItem(i, "active_phase");
            }

            String actionPhaseName2 = null;
            if(resultContainer.getItem(i, "active_phase_2").getResultData() != null) {
                actionPhaseName2 = resultContainer.getStringItem(i, "active_phase_2");
            }

            String actionPhaseName3 = null;
            if(resultContainer.getItem(i, "active_phase_3").getResultData() != null) {
                actionPhaseName3 = resultContainer.getStringItem(i, "active_phase_3");
            }

            String nextScheduledPhaseName = null;
            if(resultContainer.getItem(i, "next_scheduled_phase").getResultData() != null) {
                nextScheduledPhaseName = resultContainer.getStringItem(i, "next_scheduled_phase");
            }

            String latePhase = null;
            if(resultContainer.getItem(i, "late_phase").getResultData() != null) {
                latePhase = resultContainer.getStringItem(i, "late_phase");
            }

            Set<String> statusNamesSet = new HashSet<String>();

            String statusName;

            if(activePhaseName == null) {
                // there is no active phase
                if(nextScheduledPhaseName == null) {
                    statusName = "Active";
                } else if(nextScheduledPhaseName.equalsIgnoreCase("Specification Submission")) {
                    // next scheduled is specification submission, we use status "Specification Review"
                    statusName =  "Specification Review";
                } else if (nextScheduledPhaseName.equalsIgnoreCase("Registration")) {
                    statusName = "Scheduled";
                } else {
                    statusName = "Stalled";
                }
            } else {
                statusName = activePhaseName;
            }

            statusNamesSet.add(statusName);

            String typeName = resultContainer.getStringItem(i, "contest_type");
            long contestTypeId = resultContainer.getLongItem(i, "contest_type_id"); // BUGR-3913
            Timestamp startDate = resultContainer.getTimestampItem(i, "start_date");
            Timestamp endDate = resultContainer.getTimestampItem(i, "end_date");
            int forumPostsCount = resultContainer.getIntItem(i, "number_of_forum");
            int registrantsCount = resultContainer.getIntItem(i, "number_of_registration");
            int submissionsCount = resultContainer.getIntItem(i, "number_of_submission");
            int checkpointSubmissionCount = resultContainer.getIntItem(i, "number_of_milestone_submission");
            int finalSubmissionCount = resultContainer.getIntItem(i, "number_of_final_submission");
            boolean isMultipleRound = resultContainer.getBooleanItem(i, "is_multiple_round");
            Boolean isStudio = resultContainer.getBooleanItem(i, "is_studio");
            int forumId = -1;
            long customerId = -1L;
            try {
                if (resultContainer.getStringItem(i, "forum_id") != null
                    && !resultContainer.getStringItem(i, "forum_id").equals("")) {
                    forumId = Integer.parseInt(resultContainer.getStringItem(i, "forum_id"));
                }
            } catch (NumberFormatException ne) {
                // ignore
            }

            try {
                if (resultContainer.getStringItem(i, "client_id") != null
                    && !resultContainer.getStringItem(i, "client_id").equals("")) {
                    customerId = Long.parseLong(resultContainer.getStringItem(i, "client_id"));
                }
            } catch (NumberFormatException ne) {
                // ignore
            }


            final ProjectBriefDTO project;
            if (!projects.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                projects.put(tcDirectProjectId, project);
            } else {
                project = projects.get(tcDirectProjectId);
            }

            ContestBriefDTO contestBrief = createContest(contestId, contestName, project, !isStudio, typeName);
            contestBrief.setCustomerId(customerId);
            ContestType type =  ContestType.forIdAndFlag(contestTypeId, isStudio);

            ContestStatus status = ContestStatus.forName(statusName);

            ProjectContestDTO contest = createProjectContest(contestBrief, type, status, startDate, endDate,
                                                             forumPostsCount, registrantsCount, submissionsCount, forumId, isStudio);


            if (resultContainer.getStringItem(i, "forum_type") != null
                    && !resultContainer.getStringItem(i, "forum_type").equals("")) {
                contest.setNewForum(true);
            } else {
                contest.setNewForum(false);
            }

            contest.setMultipleRound(isMultipleRound);
            contest.setCheckpointSubmissionNumber(checkpointSubmissionCount);
            contest.setFinalSubmissionNumber(finalSubmissionCount);

            // check late for the status#1
            if(latePhase != null && statusName.equalsIgnoreCase(latePhase)) {
                contest.setStatusLate(true);
            }

            if(actionPhaseName2 != null && !statusNamesSet.contains(actionPhaseName2)) {
                contest.setStatus2(ContestStatus.forName(actionPhaseName2));
                if(latePhase != null && actionPhaseName2.equalsIgnoreCase(latePhase)) {
                    contest.setStatus2Late(true);
                }
                statusNamesSet.add(actionPhaseName2);
            }

            if(actionPhaseName3 != null && !statusNamesSet.contains(actionPhaseName3)) {
                contest.setStatus3(ContestStatus.forName(actionPhaseName3));
                if(latePhase != null && actionPhaseName3.equalsIgnoreCase(latePhase)) {
                    contest.setStatus3Late(true);
                }
            }

            contests.add(contest);
        }

        ProjectContestsListDTO dto = new ProjectContestsListDTO();
        dto.setContests(contests);
        return dto;
    }

    /**
     * <p>Gets the details on contests associated with the specified project.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * project. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the ID for user associated with project.
     * @param projectId a <code>long</code> providing the ID for project to get the details for associated contests for.
     * @return a <code>ProjectContestsListDTO</code> providing the details on contests associated with specified
     *         project.
     * @throws Exception if an unexpected error occurs.
     */
    public static List<TypedContestBriefDTO> getProjectTypedContests(long userId, long projectId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_my_typed_contests_replatforming");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("tcdirectid", String.valueOf(projectId));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();
        final List<TypedContestBriefDTO> contests = new ArrayList<TypedContestBriefDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_my_typed_contests_replatforming");
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
            String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");
            long contestId = resultContainer.getLongItem(i, "contest_id");
            String contestName = resultContainer.getStringItem(i, "contest_name");
            String statusName = resultContainer.getStringItem(i, "status");

            //TODO, this is to not affecting existing VMs, will change later
            long contestTypeId = 1;
            try
            {
                contestTypeId = resultContainer.getLongItem(i, "contest_type_id"); // BUGR-3913
            }
            catch (Exception e)
            {
            }


            final ProjectBriefDTO project;
            if (!projects.containsKey(tcDirectProjectId)) {
                project = createProject(tcDirectProjectId, tcDirectProjectName);
                projects.put(tcDirectProjectId, project);
            } else {
                project = projects.get(tcDirectProjectId);
            }

            Boolean isStudio = resultContainer.getBooleanItem(i, "is_studio");
            ContestType type = ContestType.forIdAndFlag(contestTypeId, isStudio);
            ContestStatus status = ContestStatus.forName(statusName);

            TypedContestBriefDTO contest;
            contest = createTypedContest(contestId, contestName, project, type, status, !isStudio);
            contests.add(contest);
        }

        return contests;
    }

    /**
     * <p>Gets the statistics on specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return a <code>ContestStatsDTO</code> providing the statistics on contest.
     */
    public static ContestStatsDTO getContestStats(long contestId) {
        ContestStatsDTO dto = new ContestStatsDTO();
        dto.setEndTime(new Date(System.currentTimeMillis() + 24 * contestId * 3600 * 1000L));
        dto.setStartTime(new Date());
        dto.setSubmissionsNumber((int) contestId + 1);
        dto.setRegistrantsNumber((int) contestId + 7);
        dto.setForumPostsNumber((int) contestId + 17);
//        dto.setContest(MockData.getContest(contestId));
        dto.setContest(MockData.getContest(4));
        return dto;
    }

    /**
     * <p>Gets the details on specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return a <code>ContestStatsDTO</code> providing the details on contest.
     */
    public static ContestDTO getContest(long contestId) {
//        return MockData.getContest(contestId);
        return MockData.getContest(4);
    }

    /**
     * <p>Gets the list of registrants to specified contest.</p>
     *
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return a <code>List</code> providing the details for contest registrants.
     */
    public static List<ContestRegistrantDTO> getContestRegistrants(long contestId) {
//        return MockData.getContestRegistrants(contestId);
        return MockData.getContestRegistrants(4);
    }

    /**
     * Gets the checkpoint submission data for multiple-rounds software contest and sets the data into the
     * <code>SoftwareContestSubmissionDTO</code>.
     *
     * @param dto the <code>SoftwareContestSubmissionDTO</code> to set.
     * @throws Exception if an unexpected error occurs.
     * @since 3.6
     */
    public static void setSoftwareCheckpointSubmissionsData(SoftwareContestSubmissionsDTO dto) throws Exception {
        final String commandName = "direct_software_milestone_submissions_view";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(commandName);
        request.setProperty("pj", String.valueOf(dto.getProjectId()));

        // Get checkpoint reviews for contest checkpoint submissions and collect them to amp for faster lookup
        final ResultSetContainer reviewsContainer
                = dataAccessor.getData(request).get("direct_software_project_milestone_reviews");

        Map<Long, List<SoftwareSubmissionReviewDTO>> checkpointReviewsMap
                = new HashMap<Long, List<SoftwareSubmissionReviewDTO>>();

        for (ResultSetContainer.ResultSetRow reviewRow : reviewsContainer) {
            SoftwareSubmissionReviewDTO checkpointReview = new SoftwareSubmissionReviewDTO();
            checkpointReview.setSubmissionId(reviewRow.getLongItem("submission_id"));
            checkpointReview.setReviewId(reviewRow.getLongItem("review_id"));
            checkpointReview.setCommitted(reviewRow.getBooleanItem("is_committed"));
            checkpointReview.setFinalScore(reviewRow.getItem("final_score").getResultData() == null ? 0 :
                reviewRow.getFloatItem("final_score"));
            checkpointReview.setInitialScore(reviewRow.getItem("initial_score").getResultData() == null ? 0 :
                reviewRow.getFloatItem("initial_score"));
            checkpointReview.setCheckpointFeedback(reviewRow.getStringItem("feedback"));

            long submissionId = checkpointReview.getSubmissionId();

            if(checkpointReviewsMap.get(submissionId) == null) {
                List<SoftwareSubmissionReviewDTO> reviews = new ArrayList<SoftwareSubmissionReviewDTO>();
                reviews.add(checkpointReview);
                checkpointReviewsMap.put(checkpointReview.getSubmissionId(), reviews);
            } else {
                checkpointReviewsMap.get(submissionId).add(checkpointReview);
            }
        }

        // set contest checkpoint submissions
        final ResultSetContainer submissionsContainer
                = dataAccessor.getData(request).get("direct_software_project_milestone_submissions");

        // the number of checkpoint prize can be given for the contest
        int checkpointPrizeNumber = 0;

        List<SoftwareSubmissionDTO> submissions = new ArrayList<SoftwareSubmissionDTO>();

        List<SoftwareContestWinnerDTO> checkpointWinners = new ArrayList<SoftwareContestWinnerDTO>();

        for (ResultSetContainer.ResultSetRow submissionRow : submissionsContainer) {
            UserDTO submitter = new UserDTO();
            submitter.setId(Long.parseLong(submissionRow.getStringItem("submitter_id")));
            submitter.setHandle(submissionRow.getStringItem("submitter_handle"));

            SoftwareSubmissionDTO submission = new SoftwareSubmissionDTO();
            long submissionId = submissionRow.getLongItem("submission_id");
            submission.setSubmissionId(submissionId);
            submission.setSubmissionDate(submissionRow.getTimestampItem("create_date"));
            submission.setScreeningScore((Float) submissionRow.getItem("screening_score").getResultData());
            submission.setInitialScore((Float) submissionRow.getItem("initial_score").getResultData());
            submission.setFinalScore((Float) submissionRow.getItem("final_score").getResultData());
            submission.setPlacement((Integer) submissionRow.getItem("placement").getResultData());
            submission.setPassedScreening(!submissionRow.getBooleanItem("failed_milestone_screening"));
            submission.setPassedReview(!submissionRow.getBooleanItem("failed_milestone_review"));
            submission.setUploadId(submissionRow.getLongItem("upload_id"));
            submission.setSubmitter(submitter);

            if(checkpointPrizeNumber <= 0) {
                checkpointPrizeNumber = submissionRow.getIntItem("milestone_prize_number");
                dto.setCheckpointPrizeNumber(checkpointPrizeNumber);
                dto.setCheckpointPrizeAmount(submissionRow.getDoubleItem("milestone_prize_amount"));
            }

            submission.setReviews(checkpointReviewsMap.get(submission.getSubmissionId()));

            submissions.add(submission);

            Integer placement = submission.getPlacement();


            if (placement != null && placement <= checkpointPrizeNumber && submission.getPassedReview()) {
                SoftwareContestWinnerDTO winner = new SoftwareContestWinnerDTO();
                winner.setFinalScore(submission.getFinalScore());
                winner.setHandle(submitter.getHandle());
                winner.setId(submitter.getId());
                winner.setPlacement(placement);
                winner.setProjectId(dto.getProjectId());
                winner.setSubmissionId(submissionId);

                checkpointWinners.add(winner);
            }
        }

        dto.setSubmissions(submissions);
        dto.setCheckpointWinners(checkpointWinners);
    }

    /**
     * <p>Sets the specified DTO with data for requested project submissions.</p>
     *
     * <p>
     * Update in version 3.8 (Release Assembly - TC Direct Cockpit Release Four)
     * - do not set first place winner and second place winner if the submission does not passed review
     * </p>
     *
     * @param dto a <code>SoftwareContestSubmissionsDTO</code> to be set with data for project submissions.
     * @throws Exception if an unexpected error occurs.
     * @since 1.2.1
     */
    public static void setSoftwareSubmissionsData(SoftwareContestSubmissionsDTO dto) throws Exception {
        final String queryName = "direct_software_submissions_view";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("pj", String.valueOf(dto.getProjectId()));

        // Set reviewers for project and collect them to map for faster lookup on sub-sequent steps
        Map<Long, UserDTO> reviewersMap = new HashMap<Long, UserDTO>();
        final ResultSetContainer reviewersContainer
            = dataAccessor.getData(request).get("direct_software_project_reviewers");
        List<UserDTO> reviewers = new ArrayList<UserDTO>();
        for (ResultSetContainer.ResultSetRow reviewerRow : reviewersContainer) {
            UserDTO reviewer = new UserDTO();
            reviewer.setId(Long.parseLong(reviewerRow.getStringItem("reviewer_id")));
            reviewer.setHandle(reviewerRow.getStringItem("reviewer_handle"));
            long reviewerResourceId = reviewerRow.getLongItem("reviewer_resource_id");
            reviewersMap.put(reviewerResourceId, reviewer);
            reviewers.add(reviewer);
        }
        dto.setReviewers(reviewers);

        // Get the reviews for project submissions and collect them to map for faster lookup on sub-sequent steps
        final ResultSetContainer reviewsContainer
            = dataAccessor.getData(request).get("direct_software_project_reviews");
        Map<Long, List<SoftwareSubmissionReviewDTO>> reviewsMap
            = new HashMap<Long, List<SoftwareSubmissionReviewDTO>>();
        Map<Long, SoftwareSubmissionReviewDTO> screeningReviewsMap = new HashMap<Long, SoftwareSubmissionReviewDTO>();
        for (ResultSetContainer.ResultSetRow reviewRow : reviewsContainer) {
            SoftwareSubmissionReviewDTO review = new SoftwareSubmissionReviewDTO();
            review.setReviewer(reviewersMap.get(reviewRow.getLongItem("resource_id")));
            review.setSubmissionId(reviewRow.getLongItem("submission_id"));
            review.setFinalScore(reviewRow.getFloatItem("final_score"));
            review.setInitialScore(reviewRow.getFloatItem("initial_score"));
            review.setReviewId(reviewRow.getLongItem("review_id"));
            review.setCommitted(reviewRow.getBooleanItem("is_committed"));
            long reviewerRoleId = reviewRow.getLongItem("reviewer_role_id");

            long submissionId = review.getSubmissionId();
            if (reviewerRoleId != 2) { // Not screening review
                List<SoftwareSubmissionReviewDTO> submissionReviews;
                if (reviewsMap.containsKey(submissionId)) {
                    submissionReviews = reviewsMap.get(submissionId);
                } else {
                    submissionReviews = new ArrayList<SoftwareSubmissionReviewDTO>();
                    reviewsMap.put(submissionId, submissionReviews);
                }
                submissionReviews.add(review);
            } else {
                screeningReviewsMap.put(submissionId, review);
            }
        }

        // Set submissions
        final ResultSetContainer submissionsContainer
            = dataAccessor.getData(request).get("direct_software_project_submissions");
        List<SoftwareSubmissionDTO> submissions = new ArrayList<SoftwareSubmissionDTO>();
        for (ResultSetContainer.ResultSetRow submissionRow : submissionsContainer) {
            UserDTO submitter = new UserDTO();
            submitter.setId(Long.parseLong(submissionRow.getStringItem("submitter_id")));
            submitter.setHandle(submissionRow.getStringItem("submitter_handle"));

            SoftwareSubmissionDTO submission = new SoftwareSubmissionDTO();
            long submissionId = submissionRow.getLongItem("submission_id");
            submission.setSubmissionId(submissionId);
            submission.setSubmissionDate(submissionRow.getTimestampItem("create_date"));
            submission.setScreeningScore((Float) submissionRow.getItem("screening_score").getResultData());
            submission.setInitialScore((Float) submissionRow.getItem("initial_score").getResultData());
            submission.setFinalScore((Float) submissionRow.getItem("final_score").getResultData());
            submission.setPlacement((Integer) submissionRow.getItem("placement").getResultData());
            submission.setPassedScreening(!submissionRow.getBooleanItem("failed_screening"));
            submission.setPassedReview(!submissionRow.getBooleanItem("failed_review"));
            submission.setUploadId(submissionRow.getLongItem("upload_id"));
            submission.setSubmitter(submitter);

            submission.setReviews(reviewsMap.get(submission.getSubmissionId()));
            submission.setScreeningReview(screeningReviewsMap.get(submission.getSubmissionId()));

            submissions.add(submission);

            Integer placement = submission.getPlacement();
            if (placement != null && placement < 3 && submission.getPassedReview()) {
                SoftwareContestWinnerDTO winner = new SoftwareContestWinnerDTO();
                winner.setFinalScore(submission.getFinalScore());
                winner.setHandle(submitter.getHandle());
                winner.setId(submitter.getId());
                winner.setPlacement(placement);
                winner.setProjectId(dto.getProjectId());
                winner.setSubmissionId(submissionId);
                if (placement == 1) {
                    dto.setFirstPlaceWinner(winner);
                } else if (winner.getPlacement() == 2) {
                    dto.setSecondPlaceWinner(winner);
                }
            }

        }
        dto.setSubmissions(submissions);
    }


    /**
     *
     *
     * @param contestId
     * @return
     * @throws Exception
     */
    public static List<ContestFinalFixDTO> getContestFinalFixes(long contestId) throws Exception {
        final String queryName = "direct_contest_final_fixes";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("pj", String.valueOf(contestId));

        final ResultSetContainer results
            = dataAccessor.getData(request).get("direct_contest_final_fixes");

        List<ContestFinalFixDTO> finalFixes = new ArrayList<ContestFinalFixDTO>();

        int count = 0;

        for (ResultSetContainer.ResultSetRow finalFixRow : results) {
            ContestFinalFixDTO finalFix = new ContestFinalFixDTO();
            finalFix.setContestId(contestId);
            finalFix.setFinalFixDate(finalFixRow.getTimestampItem("final_fix_upload_time"));
            finalFix.setFinalFixerHandle(finalFixRow.getStringItem("final_fixer_handle"));
            finalFix.setFinalFixerUserId(Long.valueOf(finalFixRow.getStringItem("final_fixer_user_id")));
            finalFix.setUploadId(finalFixRow.getLongItem("upload_id"));
            if (finalFixRow.getItem("submission_id").getResultData() != null) {
                finalFix.setSubmissionId(finalFixRow.getLongItem("submission_id"));
            }
            count++;

            // set old ones reviewed to true, approved to false because they are rejected
            if (count > 1) {
                finalFix.setReviewed(true);
                finalFix.setApproved(false);
            }

            finalFix.setFinalFixSub(finalFixRow.getStringItem("upload_parameter"));

            finalFixes.add(finalFix);
        }

        int versionNumber = finalFixes.size();

        // insert version number
        for (ContestFinalFixDTO ff : finalFixes) {
            ff.setVersionNumber(versionNumber);
            versionNumber--;
        }

        return finalFixes;
    }

    /**
     * <p>Gets the enterprise level statistics for projects assigned to current user.</p>
     *
     * @param tcDirectProjects a <code>List</code> listing details for TC Direct projects accessible to user.
     * @return a <code>List</code> providing the details for all projects accessible to current user.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.2
     */
    public static List<EnterpriseDashboardProjectStatDTO> getEnterpriseProjectStats(List<ProjectData> tcDirectProjects)
        throws Exception {
        List<EnterpriseDashboardProjectStatDTO> data = new ArrayList<EnterpriseDashboardProjectStatDTO>();
        if ((tcDirectProjects == null) || (tcDirectProjects.isEmpty())) {
            return data;
        }

        String projectIds = getConcatenatedIdsString(tcDirectProjects);
        Set<Long> projectsWithStats = new HashSet<Long>();

        final String queryName = "direct_dashboard_enterprise_health";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("tdpis", projectIds);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            long projectId = row.getLongItem("tc_direct_project_id");

            ProjectBriefDTO project = new ProjectBriefDTO();
            project.setId(projectId);
            for (ProjectData tcDirectProject : tcDirectProjects) {
                if (tcDirectProject.getProjectId() == projectId) {
                    project.setName(tcDirectProject.getName());
                    projectsWithStats.add(tcDirectProject.getProjectId());
                    break;
                }
            }

            EnterpriseDashboardProjectStatDTO projectStatDTO = new EnterpriseDashboardProjectStatDTO();
            projectStatDTO.setProject(project);
            projectStatDTO.setAverageContestDuration(row.getDoubleItem("average_duration"));
            projectStatDTO.setAverageCostPerContest(row.getDoubleItem("average_cost_per_contest"));
            projectStatDTO.setAverageFulfillment(row.getDoubleItem("average_fulfillment"));
            projectStatDTO.setTotalProjectCost(row.getDoubleItem("total_cost"));

            data.add(projectStatDTO);
        }

        for (ProjectData tcDirectProject : tcDirectProjects) {
            if (!projectsWithStats.contains(tcDirectProject.getProjectId())) {
                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(tcDirectProject.getProjectId());
                project.setName(tcDirectProject.getName());

                EnterpriseDashboardProjectStatDTO projectStatDTO = new EnterpriseDashboardProjectStatDTO();
                projectStatDTO.setProject(project);
                projectStatDTO.setAverageContestDuration(0);
                projectStatDTO.setAverageCostPerContest(0);
                projectStatDTO.setAverageFulfillment(0);
                projectStatDTO.setTotalProjectCost(0);

                data.add(projectStatDTO);
            }
        }

        return data;
    }


    /**
     * <p>Gets the enterprise level statistics for projects assigned to current user.</p>
     *
     * @param tcDirectProjects a <code>List</code> listing details for TC Direct projects accessible to user.
     * @param userId a <code>long</code> providing the ID for the user to get data for.
     * @return a <code>List</code> providing the details for all projects accessible to current user.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.2
     */
    public static List<EnterpriseDashboardProjectStatDTO> getDirectProjectStats(List<ProjectData> tcDirectProjects,
                                                                                           long userId)
        throws Exception {
        List<EnterpriseDashboardProjectStatDTO> data = new ArrayList<EnterpriseDashboardProjectStatDTO>();
        if ((tcDirectProjects == null) || (tcDirectProjects.isEmpty())) {
            return data;
        }

        String projectIds = getConcatenatedIdsString(tcDirectProjects);
        Set<Long> projectsWithStats = new HashSet<Long>();

        final String queryName = "direct_project_stat_v2";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("tcdirectid", projectIds);
        request.setProperty("uid", String.valueOf(userId));

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            long projectId = row.getLongItem("tc_direct_project_id");

            ProjectBriefDTO project = new ProjectBriefDTO();
            project.setId(projectId);
            for (ProjectData tcDirectProject : tcDirectProjects) {
                if (tcDirectProject.getProjectId() == projectId) {
                    project.setName(tcDirectProject.getName());
                    projectsWithStats.add(tcDirectProject.getProjectId());
                    break;
                }
            }

            EnterpriseDashboardProjectStatDTO projectStatDTO = new EnterpriseDashboardProjectStatDTO();
            projectStatDTO.setProject(project);
            projectStatDTO.setCompletedNumber(row.getIntItem("completed_number"));
            projectStatDTO.setCancelledNumber(row.getIntItem("cancelled_number"));
            projectStatDTO.setTotalMemberCost(row.getDoubleItem("total_member_cost"));
            projectStatDTO.setAverageMemberCostPerContest(row.getDoubleItem("average_member_cost"));
            projectStatDTO.setTotalContestFee(row.getDoubleItem("total_contest_fee"));
            projectStatDTO.setAverageContestFeePerContest(row.getDoubleItem("average_contest_fee"));
            projectStatDTO.setAverageFulfillment(row.getDoubleItem("fullfillment"));
            projectStatDTO.setTotalProjectCost(row.getDoubleItem("total_cost"));
            projectStatDTO.setAverageContestDuration(row.getDoubleItem("average_duration"));

            data.add(projectStatDTO);
        }

        for (ProjectData tcDirectProject : tcDirectProjects) {
            if (!projectsWithStats.contains(tcDirectProject.getProjectId())) {
                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(tcDirectProject.getProjectId());
                project.setName(tcDirectProject.getName());

                EnterpriseDashboardProjectStatDTO projectStatDTO = new EnterpriseDashboardProjectStatDTO();
                projectStatDTO.setProject(project);
                projectStatDTO.setTotalMemberCost(0);
                projectStatDTO.setAverageCostPerContest(0);
                projectStatDTO.setTotalContestFee(0);
                projectStatDTO.setAverageContestFeePerContest(0);
                projectStatDTO.setAverageFulfillment(0);
                projectStatDTO.setAverageContestDuration(0);
                projectStatDTO.setTotalProjectCost(0);

                data.add(projectStatDTO);
            }
        }

        return data;
    }

    /**
     * <p>Gets the mapping to be used for looking up the project categories by IDs.</p>
     *
     * @return a <code>Map</code> mapping the project category IDs to category names.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.2
     */
    public static Map<Long, String> getAllProjectCategories() throws Exception {
        Map<Long, String> map = new LinkedHashMap<Long, String>();

        final String queryName = "project_categories_replatforming";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            map.put(row.getLongItem("project_category_id"), row.getStringItem("name"));
        }

        return map;
    }

    /**
     * Gets all the direct project status from the persistence. The key is the status id, the value
     * is the status name.
     *
     * @return the map of the direct project status.
     * @throws Exception if an error occurs.
     * @since 4.3
     */
    public static Map<Long, String> getAllDirectProjectStatus() throws Exception {
        Map<Long, String> map = new LinkedHashMap<Long, String>();

        final String queryName = "all_direct_project_status";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            map.put(row.getLongItem("direct_project_status_id"), row.getStringItem("direct_project_status_name"));
        }

        return map;
    }

    /**
     * Gets the project categories groupded by software and studio.
     *
     * @return the grouped project categories.
     * @throws Exception if any error occurs.
     * @since 4.0
     */
    public static Map<String, Map<Long, String>> getAllProjectCategoriesGrouped() throws Exception {
        Map<Long, String> softwareMap = new LinkedHashMap<Long, String>();
        Map<Long, String> studioMap = new LinkedHashMap<Long, String>();

        final String queryName = "project_categories_replatforming";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            long projectTypeId = row.getLongItem("project_type_id");
            if (projectTypeId == 1 || projectTypeId == 2) {
                softwareMap.put(row.getLongItem("project_category_id"), row.getStringItem("name"));
            }
            if (projectTypeId == 3) {
                studioMap.put(row.getLongItem("project_category_id"), row.getStringItem("name"));
            }

        }

        Map<String, Map<Long, String>> result = new HashMap<String, Map<Long, String>>();
        result.put("software", softwareMap);
        result.put("studio", studioMap);

        return result;
    }

    /**
     * <p>Gets the cost breakdown data for contests or markets.</p>
     *
     * @param currentUser the current user.
     * @param projectIds the project ids of the contests
     * @param projectCategoriesIds the project category ids of the market
     * @param startDate the start date when getting market cost breakdown data
     * @param endDate the end date when getting market cost breakdown data
     * @return If projectIds is not null or empty, the cost breakdown data for contests will be returned. If
     * projectCategoriesIds is not null or empty, the cost breakdown data for the market will be returned.
     * @throws Exception if an unexpected error occurs.
     * @since 2.6.1
     */
    public static List<DashboardCostBreakDownDTO> getDashboardCostBreakDown(TCSubject currentUser, long[] projectIds, long[] projectCategoriesIds, Date startDate, Date endDate) throws Exception {
        List <DashboardCostBreakDownDTO> data = new ArrayList<DashboardCostBreakDownDTO>();
        if ( (projectIds == null || projectIds.length == 0) && (projectCategoriesIds == null || projectCategoriesIds.length == 0) ) {
            return data;
        }

        DataAccess dataAccessor;
        Request request = new Request();
        String queryName;
        setUserPermissionQueryParameter(request, currentUser);
        if (projectCategoriesIds == null || projectCategoriesIds.length == 0) {
            queryName = "dashboard_contest_cost_breakdown";
            request.setProperty("pids", concatenate(projectIds, ", "));
            dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        } else {
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            queryName = "dashboard_market_cost_breakdown";
            request.setProperty("pcids", concatenate(projectCategoriesIds, ", "));
            request.setProperty("sdt", dateFormatter.format(startDate));
            request.setProperty("edt", dateFormatter.format(endDate));
            dataAccessor = new CachedDataAccess(MaxAge.THREE_HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);
        }
        request.setContentHandle(queryName);
        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            DashboardCostBreakDownDTO dto = new DashboardCostBreakDownDTO();
            dto.setId(row.getLongItem("id"));
            dto.setContestFee(row.getDoubleItem("contest_fee"));
            dto.setPrizes(row.getDoubleItem("prizes"));
            dto.setSpecReview(row.getDoubleItem("spec_review"));
            dto.setReview(row.getDoubleItem("review"));
            dto.setReliability(row.getDoubleItem("reliability"));
            dto.setDigitalRun(row.getDoubleItem("digital_run"));
            dto.setCopilot(row.getDoubleItem("copilot"));
            dto.setBuild(row.getDoubleItem("build"));
            dto.setBugs(row.getDoubleItem("bugs"));
            dto.setMisc(row.getDoubleItem("misc"));
            if (queryName.equals("dashboard_contest_cost_breakdown")) {
                dto.setFullfillment(row.getIntItem("fullfillment"));
            }
            if(dto.getId() > 0) {
                data.add(dto);
            }
        }

        return data;
    }

    /**
     * <p>Gets the enterprise-level statistics of contests posted within specified period of time.</p>
     *
     *
     * @param projectIds a <code>long</code> array providing the IDs for a project.
     * @param projectCategoryIDs a <code>long</code> array providing the IDs for project categories.
     * @param startDate a <code>Date</code> providing the beginning date for period.
     * @param endDate a <code>Date</code> providing the ending date for period.
     * @param clientIds a <code>long</code> array listing the IDs for clients.
     * @param billingAccountIds a <code>long</code> array listing the IDs for billing accounts.
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @since 2.2.0
     */
    public static List<EnterpriseDashboardContestStatDTO> getEnterpriseStatsForContests(long[] projectIds,
        long[] projectCategoryIDs, Date startDate, Date endDate, long[] clientIds, long[] billingAccountIds)
        throws Exception {
        List<EnterpriseDashboardContestStatDTO> data
            = new ArrayList<EnterpriseDashboardContestStatDTO>();
        if ((projectIds == null) || (projectIds.length == 0)) {
            return data;
        }
        if ((projectCategoryIDs == null) || (projectCategoryIDs.length == 0)) {
            return data;
        }
        if ((clientIds == null) || (clientIds.length == 0)) {
            return data;
        }
        if ((billingAccountIds == null) || (billingAccountIds.length == 0)) {
            return data;
        }

        String projectIDsList = concatenate(projectIds, ", ");
        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");
        String clientIdsList = concatenate(clientIds, ", ");
        String billingAccountIdsList = concatenate(billingAccountIds, ", ");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        // query for contest status
        String queryName;
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        if(projectIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_contest_stats_project";
            request.setProperty("tdpis", String.valueOf(projectIDsList));
        } else if (billingAccountIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_contest_stats_billing";
            request.setProperty("bpids", billingAccountIdsList);
        } else if (clientIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_contest_stats_client";
            request.setProperty("clids", clientIdsList);
        } else {
            return data;
        }
        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            EnterpriseDashboardContestStatDTO contestDTO = new EnterpriseDashboardContestStatDTO();
            contestDTO.setPostingDate(row.getTimestampItem("posting_date"));
            contestDTO.setDate(row.getTimestampItem("contest_date"));
            contestDTO.setCustomerName(row.getStringItem("customer_name"));
            contestDTO.setProjectName(row.getStringItem("project_name"));
            contestDTO.setContestName(row.getStringItem("contest_name"));
            contestDTO.setContestType(row.getStringItem("contest_type"));
            contestDTO.setProjectCategoryId(row.getIntItem("project_category_id"));
            contestDTO.setProjectId(row.getIntItem("project_id"));
            contestDTO.setDirectProjectId(row.getIntItem("direct_project_id"));
            contestDTO.setContestFullfilment(row.getDoubleItem("contest_fulfillment"));
            contestDTO.setContestCost(row.getDoubleItem("contest_cost"));
            contestDTO.setContestDuration(row.getDoubleItem("contest_duration"));
            data.add(contestDTO);
        }

        return data;
    }


    /**
     * <p>Gets the enterprise-level statistics of all contests posted within specified period of time.</p>
     *
     * @param projectCategoryIDs a <code>long</code> array providing the IDs for project categories.
     * @param startDate a <code>Date</code> providing the beginning date for period.
     * @param endDate a <code>Date</code> providing the ending date for period.
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @since 2.2.0
     */
    public static List<EnterpriseDashboardContestStatDTO> getEnterpriseStatsForAllContests(long[] projectCategoryIDs,
              Date startDate, Date endDate)
        throws Exception {
        List<EnterpriseDashboardContestStatDTO> data
            = new ArrayList<EnterpriseDashboardContestStatDTO>();

        if ((projectCategoryIDs == null) || (projectCategoryIDs.length == 0)) {
            return data;
        }

        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        // query for contest status
        final String queryName = "direct_dashboard_enterprise_contest_stats_overall";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);
        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            EnterpriseDashboardContestStatDTO contestDTO = new EnterpriseDashboardContestStatDTO();
            contestDTO.setPostingDate(row.getTimestampItem("posting_date"));
            contestDTO.setDate(row.getTimestampItem("contest_date"));
            contestDTO.setCustomerName(row.getStringItem("customer_name"));
            contestDTO.setProjectName(row.getStringItem("project_name"));
            contestDTO.setContestName(row.getStringItem("contest_name"));
            contestDTO.setContestType(row.getStringItem("contest_type"));
            contestDTO.setProjectCategoryId(row.getIntItem("project_category_id"));
            contestDTO.setProjectId(row.getIntItem("project_id"));
            contestDTO.setDirectProjectId(row.getIntItem("direct_project_id"));
            contestDTO.setContestFullfilment(row.getDoubleItem("contest_fulfillment"));
            contestDTO.setContestCost(row.getDoubleItem("contest_cost"));
            contestDTO.setContestDuration(row.getDoubleItem("contest_duration"));
            data.add(contestDTO);
        }

        return data;
    }


    /**
     * <p>Gets the enterprise-level avarage statistics of all contests posted within specified period of time.</p>
     *
     * @param projectCategoryIDs a <code>long</code> array providing the IDs for project categories.
     * @param startDate a <code>Date</code> providing the beginning date for period.
     * @param endDate a <code>Date</code> providing the ending date for period.
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @since 2.2.0
     */
     public static Map<Integer, List<Double>> getEnterpriseContestsAvgStatus(long[] projectCategoryIDs,
              Date startDate, Date endDate)
        throws Exception {

        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        // query for contest average status
        Map<Integer, List<Double>> contestTypeAvgMap = new HashMap<Integer, List<Double>>();
        final String contestAvgQuery = "direct_dashboard_enterprise_contest_avg";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(contestAvgQuery);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);

        final ResultSetContainer avgResultSetContainer = dataAccessor.getData(request).get(contestAvgQuery);
        for (ResultSetContainer.ResultSetRow row : avgResultSetContainer) {
            List<Double> avgDataList = new ArrayList<Double>();
            avgDataList.add(row.getDoubleItem("marketavg_fulfillment"));
            avgDataList.add(row.getDoubleItem("marketavg_cost"));
            avgDataList.add(row.getDoubleItem("marketavg_duration"));
            contestTypeAvgMap.put(row.getIntItem("project_category_id"), avgDataList);
        }

        return contestTypeAvgMap;
    }


    /**
     * <p>Gets the enterprise-level statistics for specified client project for contests of specified categories
     * completed within specified period of time.</p>
     *
     *
     * @param projectIds a <code>long</code> array providing the IDs for a project.
     * @param projectCategoryIDs a <code>long</code> array providing the IDs for project categories.
     * @param startDate a <code>Date</code> providing the beginning date for period.
     * @param endDate a <code>Date</code> providing the ending date for period.
     * @param clientIds a <code>long</code> array listing the IDs for clients.
     * @param billingAccountIds a <code>long</code> array listing the IDs for billing accounts.
     * @param monthly a<code>boolean</code> whether to get monthly or weekly data
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @throws IllegalArgumentException if any of specified arrays is <code>null</code> or empty.
     * @since 2.1.2
     */
    public static List<EnterpriseDashboardDetailedProjectStatDTO> getEnterpriseStatsForProject(long[] projectIds,
        long[] projectCategoryIDs, Date startDate, Date endDate, long[] clientIds, long[] billingAccountIds, boolean monthly)
        throws Exception {

        List<EnterpriseDashboardDetailedProjectStatDTO> data
            = new ArrayList<EnterpriseDashboardDetailedProjectStatDTO>();

        if ((projectIds == null) || (projectIds.length == 0)) {
            return data;
        }
        if ((projectCategoryIDs == null) || (projectCategoryIDs.length == 0)) {
            return data;
        }
        if ((clientIds == null) || (clientIds.length == 0)) {
            return data;
        }
        if ((billingAccountIds == null) || (billingAccountIds.length == 0)) {
            return data;
        }


        String projectIDsList = concatenate(projectIds, ", ");
        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");
        String clientIdsList = concatenate(clientIds, ", ");
        String billingAccountIdsList = concatenate(billingAccountIds, ", ");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();

        String queryName;
        if(projectIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_detailed_stats_project";
            request.setProperty("tdpis", String.valueOf(projectIDsList));
        } else if (billingAccountIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_detailed_stats_billing";
            request.setProperty("bpids", billingAccountIdsList);
        } else if (clientIds[0] != 0) {
            queryName = "direct_dashboard_enterprise_detailed_stats_client";
            request.setProperty("clids", clientIdsList);
        } else {
            return data;
        }

        if(monthly) {
            queryName += MONTHLY_SUFFIX;
        }

        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {

            EnterpriseDashboardDetailedProjectStatDTO costDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            costDTO.setDate(row.getTimestampItem("stat_date"));
            costDTO.setValue(row.getDoubleItem("cost"));
            costDTO.setContestsCount(row.getIntItem("total_completed_project"));
            costDTO.setStatsType(EnterpriseDashboardStatType.COST);

            EnterpriseDashboardDetailedProjectStatDTO durationDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            durationDTO.setDate(row.getTimestampItem("stat_date"));
            durationDTO.setValue(row.getDoubleItem("duration"));
            durationDTO.setContestsCount(row.getIntItem("total_completed_project"));
            durationDTO.setStatsType(EnterpriseDashboardStatType.DURATION);

            EnterpriseDashboardDetailedProjectStatDTO fulfillmentDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            fulfillmentDTO.setDate(row.getTimestampItem("stat_date"));
            fulfillmentDTO.setValue(row.getDoubleItem("fulfillment"));
            fulfillmentDTO.setContestsCount(row.getIntItem("total_project"));
            fulfillmentDTO.setStatsType(EnterpriseDashboardStatType.FULFILLMENT);

            data.add(costDTO);
            data.add(durationDTO);
            data.add(fulfillmentDTO);
        }

        return data;
    }


    /**
     * Gets the volume view data for enterprise dashboard volume view.
     *
     * @param projectId the tc direct project id.
     * @param billingProjectId the billing project id.
     * @param clientId the client id.
     * @param projectCategoryIds the ids of project categories.
     * @param startDate the start date of the view
     * @param endDate the end date of the view
     * @param month whether group by month, otherwise group by week
     * @return the volume view data.
     * @throws Exception if there is error.
     * @since 3.0
     */
    public static List<EnterpriseDashboardVolumeViewDTO> getEnterpriseDashboardVolumeView(long projectId,
                                                                                          long billingProjectId,
                                                                                          long clientId,
                                                                                          long[] projectCategoryIds,
                                                                                          Date startDate, Date endDate, boolean month)
            throws Exception {
        List<EnterpriseDashboardVolumeViewDTO> result = new ArrayList<EnterpriseDashboardVolumeViewDTO>();

        // argument checking
        if (projectId < 0 || billingProjectId < 0 || clientId < 0
                || projectCategoryIds == null || projectCategoryIds.length == 0) {
            return result;
        }

        String queryName = "direct_dashboard_enterprise_volume_view";

        if(!month) {
            queryName += "_week";
        }

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        DataAccess dataAccess = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        String projectCategoryIdsList = concatenate(projectCategoryIds, ", ");

        if (projectId != 0) {
            request.setProperty("tcdirectid", String.valueOf(projectId));
            request.setProperty("billingaccountid", "0");
            request.setProperty("clientid", "0");
        } else if (billingProjectId != 0) {
            request.setProperty("tcdirectid", "0");
            request.setProperty("billingaccountid", String.valueOf(billingProjectId));
            request.setProperty("clientid", "0");
        } else if (clientId != 0) {
            request.setProperty("tcdirectid", "0");
            request.setProperty("billingaccountid", "0");
            request.setProperty("clientid", String.valueOf(clientId));
        } else {
            // this will get all the projects' data
            request.setProperty("tcdirectid", "0");
            request.setProperty("billingaccountid", "0");
            request.setProperty("clientid", "0");
        }

        request.setProperty("pcids", projectCategoryIdsList);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setContentHandle(queryName);

        final ResultSetContainer resultSetContainer = dataAccess.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            EnterpriseDashboardVolumeViewDTO volumeDTO = new EnterpriseDashboardVolumeViewDTO();

            volumeDTO.setStatisticDate(row.getTimestampItem("stat_date"));
            volumeDTO.setCompletedContestsNumber(row.getIntItem("completed_contests_number"));
            volumeDTO.setFailedContestsNumber(row.getIntItem("failed_contests_number"));
            volumeDTO.setContestCategoryId(row.getLongItem("category_id"));

            result.add(volumeDTO);
        }

        return result;
    }

    /**
     *  Gets the cost range and duration range for the enterprise dashboard summary section. If any of the concrete client id, billing account id or project id is set,
     *  it retrieves the data for the customer. If client id, billing account id and project id are all set to 0, it retrieves the data for the market.
     *
     * @param projectIds the direct project ids
     * @param projectCategoryIDs  the project category ids
     * @param startDate the start date
     * @param endDate the end date
     * @param clientIds the client ids
     * @param billingAccountIds the billing account ids.
     * @return the range data
     * @throws Exception if an unexpected error occurs.
     * @since 2.6.7
     */
    public static Map<String, Double> getEnterpriseDashboardSummaryRange(long[] projectIds,
                                                                         long[] projectCategoryIDs, Date startDate, Date endDate, long[] clientIds, long[] billingAccountIds)
            throws Exception {

        Map<String, Double> data = new HashMap<String, Double>();

        if ((projectIds == null) || (projectIds.length == 0)) {
            return data;
        }
        if ((projectCategoryIDs == null) || (projectCategoryIDs.length == 0)) {
            return data;
        }
        if ((clientIds == null) || (clientIds.length == 0)) {
            return data;
        }
        if ((billingAccountIds == null) || (billingAccountIds.length == 0)) {
            return data;
        }


        String projectIDsList = concatenate(projectIds, ", ");
        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");
        String clientIdsList = concatenate(clientIds, ", ");
        String billingAccountIdsList = concatenate(billingAccountIds, ", ");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();

        String queryName = "direct_dashboard_enterprise_summary";

        if (projectIds[0] != 0) {
            request.setProperty("tdpis", String.valueOf(projectIDsList));
            request.setProperty("bpids", "0");
            request.setProperty("clids", "0");
        } else if (billingAccountIds[0] != 0) {
            request.setProperty("tdpis", "0");
            request.setProperty("bpids", billingAccountIdsList);
            request.setProperty("clids", "0");
        } else if (clientIds[0] != 0) {
            request.setProperty("tdpis", "0");
            request.setProperty("bpids", "0");
            request.setProperty("clids", clientIdsList);
        } else {
            // get market data
            request.setProperty("tdpis", "0");
            request.setProperty("bpids", "0");
            request.setProperty("clids", "0");
        }

        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            data.put("max_cost", row.getDoubleItem("max_cost"));
            data.put("min_cost", row.getDoubleItem("min_cost"));
            data.put("max_duration", row.getDoubleItem("max_duration_hours"));
            data.put("min_duration", row.getDoubleItem("min_duration_hours"));
        }

        return data;
    }

    /**
     * <p>Gets the enterprise-level statistics for all contests of specified categories completed within specified
     * period of time.</p>
     *
     * @param projectCategoryIDs a <code>long</code> array providing the IDs for project categories.
     * @param startDate a <code>Date</code> providing the beginning date for period.
     * @param endDate a <code>Date</code> providing the ending date for period.
     * @param monthly <code>boolean</code> whether to get monthly or weekly data
     * @return a <code>List</code> of statistical data.
     * @throws Exception if an unexpected error occurs.
     * @throws IllegalArgumentException if specified <code>projectCategoryIDs</code> array is <code>null</code> or
     *         empty.
     * @since 2.1.2
     */
    public static List<EnterpriseDashboardDetailedProjectStatDTO> getEnterpriseStatsForAllProjects(
        long[] projectCategoryIDs, Date startDate, Date endDate, boolean monthly) throws Exception {

        if ((projectCategoryIDs == null) || (projectCategoryIDs.length == 0)) {
            throw new IllegalArgumentException("Project category IDs are not specified");
        }

        String projectCategoryIDsList = concatenate(projectCategoryIDs, ", ");

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        List<EnterpriseDashboardDetailedProjectStatDTO> data
            = new ArrayList<EnterpriseDashboardDetailedProjectStatDTO>();
        String queryName = "direct_dashboard_enterprise_detailed_stats_overall";
        if(monthly) {
            queryName += MONTHLY_SUFFIX;
        }
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {

            EnterpriseDashboardDetailedProjectStatDTO costDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            costDTO.setDate(row.getTimestampItem("stat_date"));
            costDTO.setValue(row.getDoubleItem("cost"));
            costDTO.setContestsCount(row.getIntItem("total_completed_project"));
            costDTO.setStatsType(EnterpriseDashboardStatType.COST);

            EnterpriseDashboardDetailedProjectStatDTO durationDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            durationDTO.setDate(row.getTimestampItem("stat_date"));
            durationDTO.setValue(row.getDoubleItem("duration"));
            durationDTO.setContestsCount(row.getIntItem("total_completed_project"));
            durationDTO.setStatsType(EnterpriseDashboardStatType.DURATION);

            EnterpriseDashboardDetailedProjectStatDTO fulfillmentDTO = new EnterpriseDashboardDetailedProjectStatDTO();
            fulfillmentDTO.setDate(row.getTimestampItem("stat_date"));
            fulfillmentDTO.setValue(row.getDoubleItem("fulfillment"));
            fulfillmentDTO.setContestsCount(row.getIntItem("total_project"));
            fulfillmentDTO.setStatsType(EnterpriseDashboardStatType.FULFILLMENT);

            data.add(costDTO);
            data.add(durationDTO);
            data.add(fulfillmentDTO);
        }

        return data;
    }

    /**
     * <p>
     * Gets the dashboard data for specified contest.
     * </p>
     *
     * @param contestId
     *            a <code>long</code> providing the ID of a contest to get
     *            dashboard data for.
     * @param isStudio
     *            <code>true</code> if specified contest is <code>Studio</code>
     *            contest; <code>false</code> otherwise.
     * @return a <code>ContestDashboardDTO</code> providing the data for
     *         dashboard for specified contest.
     * @param cached
     *            whether should apply to cached model
     * @throws Exception
     *             if an unexpected error occurs.
     * @since 2.1.3
     */
    public static ContestDashboardDTO getContestDashboardData(long contestId,
            boolean isStudio, boolean cached) throws Exception {
     // Prepare request to database
        DataAccess dataAccessor;

        if (cached) {
            dataAccessor = new CachedDataAccess(MaxAge.QUARTER_HOUR,
                    DBMS.TCS_OLTP_DATASOURCE_NAME);
        } else {
            dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        }

        Request request = new Request();
        request.setContentHandle("direct_contest_dashboard_replatforming");
        request.setProperty("pj", String.valueOf(contestId));

        // Query database for contest dashboard data
        Map<String, ResultSetContainer> results = dataAccessor.getData(request);
        ContestDashboardDTO dto = new ContestDashboardDTO();

        // Analyze current and next phases
        final ResultSetContainer projectPhasesStats = results.get("project_phases_status_replatforming");

        dto.setCurrentPhase(new ProjectPhaseDTO());

        if (!projectPhasesStats.isEmpty()) {
            List<ProjectPhaseDTO> phases = new ArrayList<ProjectPhaseDTO>();
            boolean findCurrPhase = false;
            Date startTime = null;
            Date endTime = null;

            for (ResultSetRow row : projectPhasesStats) {
                ProjectPhaseDTO phase = new ProjectPhaseDTO();
                phase.setStartTime(row.getTimestampItem("start_time"));
                phase.setEndTime(row.getTimestampItem("end_time"));
                phase.setPhaseName(row.getStringItem("phase_name"));
                phase.setPhaseType(ProjectPhaseType.findProjectPhaseType(row.getIntItem("phase_type_id")));
                phase.setPhaseStatus(ProjectPhaseStatus.findProjectPhaseStatus(row.getIntItem("phase_status_id")));

                phases.add(phase);

                if (startTime == null || startTime.after(phase.getStartTime())) {
                    startTime = phase.getStartTime();
                }
                if (endTime == null || endTime.before(phase.getEndTime())) {
                    endTime = phase.getEndTime();
                }

                if (row.getIntItem("phase_status_id") == 2) {
                    // find current phase
                    findCurrPhase = true;
                    dto.setCurrentPhase(phase);

                    // set current phase status
                    Date now = new Date();
                    Date currentPhaseEndTime = phase.getEndTime();
                    if (now.compareTo(currentPhaseEndTime) > 0) {
                        dto.setCurrentPhaseStatus(RunningPhaseStatus.LATE);
                    } else {
                        long diff = currentPhaseEndTime.getTime() - now.getTime();
                        long hoursLeft = diff / (3600 * 1000);
                        if (hoursLeft < 2) {
                            dto.setCurrentPhaseStatus(RunningPhaseStatus.CLOSING);
                        } else {
                            dto.setCurrentPhaseStatus(RunningPhaseStatus.RUNNING);
                        }
                    }

                } else if (findCurrPhase) {
                    // find next phase
                    findCurrPhase = false;
                    dto.setNextPhase(phase);
                }
            }

            dto.setStartTime(startTime);
            dto.setEndTime(endTime);
            dto.setAllPhases(phases);
        }

        // Analyze registration status
        boolean isNewForum = false;

        final ResultSetContainer registrationStats = results.get("registration_status_replatforming");
        if (!registrationStats.isEmpty()) {
            ResultSetContainer.ResultSetRow row = registrationStats.getRow(0);
            dto.setNumberOfRegistrants(getInt(row, "registrants_count"));
            dto.setNumberOfSubmissions(getInt(row, "number_of_submissions"));
            dto.setPredictedNumberOfSubmissions(getInt(row, "predicted_number_of_submissions"));

            double reliabilityTotal = getDouble(row, "reliability_total");
            long registrationPhaseStatus = getLong(row, "registration_phase_status");
            long projectCategoryId = getLong(row, "project_category_id");


            if (row.getStringItem("forum_type") != null
                    && !row.getStringItem("forum_type").equals("")) {
                isNewForum = true;
            }

            if(isStudio || projectCategoryId == 29L) {
                dto.setRegistrationStatus(RegistrationStatus.HEALTHY);
            } else if (registrationPhaseStatus == 2) {
                if (reliabilityTotal >= 200) {
                    dto.setRegistrationStatus(RegistrationStatus.HEALTHY);
                } else {
                    dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_LESS_IDEAL_ACTIVE);
                }
            } else if (registrationPhaseStatus == 3) {
                if (reliabilityTotal >= 200) {
                    dto.setRegistrationStatus(RegistrationStatus.HEALTHY);
                } else if (reliabilityTotal >= 100) {
                    dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_LESS_IDEAL_CLOSED);
                } else {
                    dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_POOR);
                }
            }
            dto.setRegProgressPercent((int) Math.min(reliabilityTotal * 100 / 200, 100));
        }

        String latestHandle = "";
        long latestUserId = 0;
        long latestThreadId = 0;
        int totalForum = 0;
        long forumId = 0;
        int unansweredForumPostsNumber = 0;
        Date latestTime = null;


        DirectUtils.refreshCache("contest_forum_stats_replatforming");

        final ResultSetContainer forumStats = results.get("contest_forum_stats_replatforming");
        if (!forumStats.isEmpty()) {
            if (forumStats.getRow(0).getStringItem("latest_handle") != null)
            {
                latestHandle = forumStats.getRow(0).getStringItem("latest_handle");
            }
            latestUserId = getLong(forumStats.getRow(0), "latest_userid");
            latestThreadId = getLong(forumStats.getRow(0), "latest_threadid");
            if (forumStats.getRow(0).getStringItem("forum_id") != null)
            {
                String frm = forumStats.getRow(0).getStringItem("forum_id");
                forumId = new Long(frm);
            }
            totalForum = getInt(forumStats.getRow(0), "number_of_forum");
            unansweredForumPostsNumber = getInt(forumStats.getRow(0), "unanswered_threads");
            latestTime = getDate(forumStats.getRow(0), "latest_time");

            if (latestTime != null)
            {
                 //time from jive is UTC
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(latestTime);
                calendar.set(Calendar.ZONE_OFFSET, 0);
                latestTime = calendar.getTime();
            }
        }

        UserDTO latestForumPostAuthor = new UserDTO();
        latestForumPostAuthor.setHandle(latestHandle);
        latestForumPostAuthor.setId(latestUserId);

        ForumPostDTO latestForumPost = new ForumPostDTO();
        latestForumPost.setAuthor(latestForumPostAuthor);
        if (!isStudio || isNewForum) {
            latestForumPost.setUrl("https://apps.topcoder.com/forums/?module=Thread&threadID=" + latestThreadId);
        } else {
            latestForumPost.setUrl("https://studio.topcoder.com/forums?module=Thread&threadID=" + latestThreadId);
        }

        if (latestTime != null)
        {
            latestForumPost.setTimestamp(latestTime);
        }
        else
        {
            latestForumPost.setTimestamp(new Date());
        }

        if (latestUserId != 0)
        {
            dto.setLatestForumPost(latestForumPost);
        }

        if (!isStudio) {
            dto.setForumURL("https://apps.topcoder.com/forums/?module=Category&categoryID=" + forumId);
        } else {
            if(isNewForum) {
                dto.setForumURL("http://apps.topcoder.com/forums?module=ThreadList&forumID=" + forumId);
            } else {
                dto.setForumURL("http://studio.topcoder.com/forums?module=ThreadList&forumID=" + forumId);
            }
        }
        dto.setTotalForumPostsCount(totalForum);
        dto.setUnansweredForumPostsNumber(unansweredForumPostsNumber);

        // Analyze the status for reviewers signup and collect the list of reviewers
        final ResultSetContainer reviewSignupStats = results.get("review_signup_stats");
        if (reviewSignupStats.isEmpty()) {
            dto.setReviewersSignupStatus(ReviewersSignupStatus.ALL_REVIEW_POSITIONS_FILLED);
        } else {
            long hoursLeft = getLong(reviewSignupStats.getRow(0), "hours_left");
            int requiredReviewersCount = getInt(reviewSignupStats.getRow(0), "required_reviewers_count");
            List<UserDTO> reviewers = new ArrayList<UserDTO>();
            for (ResultSetContainer.ResultSetRow row : reviewSignupStats) {
                String reviewerHandle = row.getStringItem("reviewer_handle");
                if (reviewerHandle != null) {
                    UserDTO reviewer = new UserDTO();
                    reviewer.setId(getLong(row, "reviewer_id"));
                    reviewer.setHandle(reviewerHandle);
                    reviewers.add(reviewer);
                }
            }
            dto.setReviewers(reviewers);
            dto.setRequiredReviewersNumber(requiredReviewersCount);
            if (requiredReviewersCount > reviewers.size()) {
                if (hoursLeft < 24) {
                    dto.setReviewersSignupStatus(ReviewersSignupStatus.REVIEW_POSITIONS_NON_FILLED_DANGER);
                } else {
                    dto.setReviewersSignupStatus(ReviewersSignupStatus.REVIEW_POSITIONS_NON_FILLED_WARNING);
                }
            } else {
                dto.setReviewersSignupStatus(ReviewersSignupStatus.ALL_REVIEW_POSITIONS_FILLED);
            }
        }

        // Analyze the overall status of dependencies for project
        final ResultSetContainer projectDependenciesStatuses = results.get("project_dependencies_statuses");
        if (projectDependenciesStatuses.isEmpty()) {
            dto.setDependenciesStatus(DependenciesStatus.NO_DEPENDENCIES);
        } else {
            List<DependencyDTO> dependencies = new ArrayList<DependencyDTO>();
            boolean thereAreIncompleteDependencies = false;
            for (ResultSetContainer.ResultSetRow row : projectDependenciesStatuses) {
                long parentProjectStatusId = getLong(row, "project_status_id");
                if ((parentProjectStatusId == 1) || (parentProjectStatusId == 2)) {
                    thereAreIncompleteDependencies = true;
                }
                ProjectBriefDTO dependencyProject = new ProjectBriefDTO();
                dependencyProject.setId(getLong(row, "project_id"));
                dependencyProject.setName(row.getStringItem("project_name"));

                DependencyDTO dependency = new DependencyDTO();
                dependency.setDependencyProject(dependencyProject);
                dependency.setDependencyType(row.getStringItem("link_type_name"));

                dependencies.add(dependency);
            }
            dto.setDependencies(dependencies);

            if (thereAreIncompleteDependencies) {
                dto.setDependenciesStatus(DependenciesStatus.DEPENDENCIES_NON_SATISFIED);
            } else {
                dto.setDependenciesStatus(DependenciesStatus.DEPENDENCIES_SATISFIED);
            }
        }

        dto.setAllPhases(DirectUtils.sortContestPhases(dto.getAllPhases()));

        return dto;
    }

    /**
     * Gets the copilots information of the given direct project.
     *
     * @param userId the user id of the current user.
     * @param tcDirectProjectid the id the direct project.
     * @return the copilot projects
     * @throws Exception if any error happends during execution.
     * @since 2.8.0
     */
    public static CopilotProjectDTO getCopilotProject(long userId, long tcDirectProjectid) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("tc_direct_project_copilots");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("tcdirectid", String.valueOf(tcDirectProjectid));

        ResultSetContainer resultContainer = dataAccessor.getData(request).get(
                "tc_copilot_projects");

        CopilotProjectDTO result = new CopilotProjectDTO();
        result.setCopilots(new ArrayList<CopilotBriefDTO>());

        for (ResultSetContainer.ResultSetRow row : resultContainer) {

            long tcDirectProjectId = row.getLongItem("tc_direct_project_id");
            String tcDirectProjectName = row.getStringItem("tc_direct_project_name");

            if (row.getItem("handle").getResultData() == null) {
                continue;
            }

            String handle = row.getStringItem("handle");
            String copilotType = row.getStringItem("name");
            long copilotUserId = row.getLongItem("user_id");
            String copilotProjectId = row.getStringItem("copilot_project_id");
            String copilotProfileId = row.getStringItem("copilot_profile_id");

            if (result.getProject() == null) {
                // set ProjectBrieftDTO if not set
                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(tcDirectProjectId);
                project.setName(tcDirectProjectName);

                result.setProject(project);
            }

            // stores the copilot data
            CopilotBriefDTO copilot = new CopilotBriefDTO();
            copilot.setCopilotProfileId(Long.parseLong(copilotProfileId.trim()));
            copilot.setCopilotProjectId(Long.parseLong(copilotProjectId.trim()));
            copilot.setCopilotType(copilotType);
            copilot.setHandle(handle);
            copilot.setUserId(copilotUserId);

            // add to result
            result.getCopilots().add(copilot);
        }

        return result;
    }


    /**
     * Retrieve copilot project data.
     *
     * @param userId the user id
     * @return copilot projects
     * @throws Exception if any exception occurs
     */
    public static List<CopilotProjectDTO> getCopilotProjects(long userId)
            throws Exception {
        Map<Long, CopilotProjectDTO> copilotProjects = new HashMap<Long, CopilotProjectDTO>();

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("tc_copilot_projects");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("tcdirectid", "0");

        // set copilot project
        ResultSetContainer resultContainer = dataAccessor.getData(request).get(
            "tc_copilot_projects");
        for (ResultSetContainer.ResultSetRow row : resultContainer) {
            long tcDirectProjectId = row.getLongItem("tc_direct_project_id");
            String tcDirectProjectName = row
                    .getStringItem("tc_direct_project_name");
            String handle = row.getStringItem("handle");
            String copilotType = row.getStringItem("name");
            String copilotProjectId = row.getStringItem("copilot_project_id");
            String copilotProfileId = row.getStringItem("copilot_profile_id");

            if (!copilotProjects.containsKey(tcDirectProjectId)) {
                CopilotProjectDTO copilotProject = new CopilotProjectDTO();

                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(tcDirectProjectId);
                project.setName(tcDirectProjectName);
                copilotProject.setProject(project);

                copilotProject.setCopilots(new ArrayList<CopilotBriefDTO>());
                copilotProject.setContests(new ArrayList<CopilotContestDTO>());

                copilotProjects.put(tcDirectProjectId, copilotProject);
            }

            if (copilotType != null) {
                CopilotBriefDTO copilot = new CopilotBriefDTO();
                copilot.setCopilotProfileId(Long.parseLong(copilotProfileId.trim()));
                copilot.setCopilotProjectId(Long.parseLong(copilotProjectId.trim()));
                copilot.setCopilotType(copilotType);
                copilot.setHandle(handle);

                copilotProjects.get(tcDirectProjectId).getCopilots().add(
                        copilot);
            }
        }

        // set all contests
        Map<Long, CopilotContestDTO> contests = new HashMap<Long, CopilotContestDTO>();
        for (long projectId : copilotProjects.keySet()) {
            ProjectContestsListDTO contestsListDTO = getProjectContests(userId,
                    projectId);

            for (ProjectContestDTO projectContest : contestsListDTO
                    .getContests()) {
                CopilotContestDTO contest = new CopilotContestDTO();
                contest.setContest(projectContest.getContest());
                contest.setCopilots(new ArrayList<String>());

                contests.put(contest.getContest().getId(), contest);
            }

        }

        // set contest copilot
        resultContainer = dataAccessor.getData(request).get(
                "tc_copilot_contests");
        for (ResultSetContainer.ResultSetRow row : resultContainer) {
            long contestId = row.getLongItem("contest_id");
            String handle = row.getStringItem("copilot_handle");

            // System.out.println("copilot contest: contestId:" + contestId + " copilot:" + handle);

            if (handle != null) {
                contests.get(contestId).getCopilots().add(handle);
            }
        }

        // set contests to project
        for (Entry<Long, CopilotContestDTO> contest : contests.entrySet()) {
            copilotProjects.get(
                    contest.getValue().getContest().getProject().getId())
                    .getContests().add(contest.getValue());
        }

        return new ArrayList<CopilotProjectDTO>(copilotProjects.values());
    }

    /**
     * <p>Gets the statistics on drafts to launched contests ratio.</p>
     *
     * @param viewType a <code>PipelineScheduledContestsViewType</code> referencing the type of the pipeline report view
     *        to get data for.
     * @param userId a <code>long</code> providing the ID for the user to get data for.
     * @return a <code>List</code> listing the drafts ratio for specified report view type.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.5
     */
    public static List<PipelineDraftsRatioDTO> getPipelineDraftsRatioStats(PipelineScheduledContestsViewType viewType,
                                                                           long userId) throws Exception {
        if (viewType == null) {
            throw new IllegalArgumentException("The parameter [viewType] is NULL");
        }

        String queryName = "pipeline_drafts_ratio_" + viewType.toString().toLowerCase();

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("uid", String.valueOf(userId));

        Map<String, ResultSetContainer> results = dataAccessor.getData(request);
        ResultSetContainer statsResultContainer = results.get(queryName);

        Map<String, PipelineDraftsRatioDTO> result = new HashMap<String, PipelineDraftsRatioDTO>();
        for (ResultSetContainer.ResultSetRow row : statsResultContainer) {
            PipelineDraftsRatioDTO dto;
            String source = row.getStringItem("source");
            if (!result.containsKey(source)) {
                dto = new PipelineDraftsRatioDTO();
                dto.setSource(source);
                result.put(source, dto);
            } else {
                dto = result.get(source);
            }

            String status = row.getStringItem("status");
            if (status != null) {
                status = status.trim();
            }
            if ("Launched".equalsIgnoreCase(status)) {
                dto.setLaunchedContestsCount(row.getIntItem("contests_count"));
            } else {
                dto.setDraftContestsCount(row.getIntItem("contests_count"));
            }
        }
        return new ArrayList<PipelineDraftsRatioDTO>(result.values());
    }

    /**
     * <p>Gets the list of <code>Copilot Posting</code> </p>
     *
     * <p>Updates in version 2.5.0:
     * Use the new query 'direct_my_copilot_postings' to get the copilot postings of the user.
     * </p>
     *
     * <p>Updates in version 2.6.3:
     * Use the new query 'direct_my_copilot_postings' to get a list of ProjectContestDTO.
     * </p>
     *
     * @param user a <code>TCSubject</code> referencing the user.
     * @return a <code>List</code> of <code>Copilot Posting</code> contests accessible to specified user.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.7
     */
    public static List<ProjectContestDTO> getCopilotPostingContests(TCSubject user) throws Exception {
        List<ProjectContestDTO> result = new ArrayList<ProjectContestDTO>();
        Map<Long, ProjectBriefDTO> directProjects = new HashMap<Long, ProjectBriefDTO>();

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        Request request = new Request();
        request.setContentHandle("direct_my_copilot_postings");
        request.setProperty("uid", String.valueOf(user.getUserId()));

        Map<String, ResultSetContainer> results = dataAccessor.getData(request);

        final ResultSetContainer copilotPostings = results.get("direct_my_copilot_postings");

        for (ResultSetContainer.ResultSetRow cp : copilotPostings) {
            long contestId = cp.getLongItem("contest_id");
            String contestName = cp.getStringItem("contest_name");
            long directProjectId = cp.getLongItem("tc_direct_project_id");
            String directProjectName = cp.getStringItem("tc_direct_project_name");
            String contestStatus = cp.getStringItem("status");
            String currentPhaseName = cp.getStringItem("current_phase_name");
            Timestamp startDate = cp.getTimestampItem("start_date");
            Timestamp endDate = cp.getTimestampItem("end_date");
            int forumPostsCount = cp.getIntItem("number_of_forum");
            int registrantsCount = cp.getIntItem("number_of_registration");
            int submissionsCount = cp.getIntItem("number_of_submission");
            int forumId = -1;
            String forumIdStr = cp.getStringItem("forum_id");

            if (!StringUtils.isEmpty(forumIdStr)) {
                forumId = Integer.parseInt(forumIdStr);
            }

            final ProjectBriefDTO project;

            // check if we have the direct project initialized
            if (directProjects.containsKey(directProjectId)) {
                project = directProjects.get(directProjectId);
            } else {
                project = createProject(directProjectId, directProjectName);

                directProjects.put(directProjectId, project);
            }

            ContestBriefDTO contestBrief = createContest(contestId, contestName, project);
            ContestStatus status = ContestStatus.forName(contestStatus);
            if (status == ContestStatus.ACTIVE) {
                status = ContestStatus.forName(currentPhaseName);
            }

            ProjectContestDTO contest = createProjectContest(contestBrief,
                    ContestType.COPILOT_POSTING, status, startDate, endDate,
                    forumPostsCount, registrantsCount, submissionsCount, forumId,
                    false);

            result.add(contest);
        }

        return result;
    }

    /**
     * Gets the copilot posting contest of the specific direct project.
     *
     * @param directProjectId the id of the direct project.
     * @return the list of <code>ContestBriefDTO</code>
     * @throws Exception if any error
     * @since 6.16
     */
    public static List<ContestBriefDTO> getProjectCopilotPostingContests(long directProjectId) throws Exception {
        List<ContestBriefDTO> result = new ArrayList<ContestBriefDTO>();

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        Request request = new Request();
        request.setContentHandle("project_copilot_postings");
        request.setProperty("tcdirectid", String.valueOf(directProjectId));

        Map<String, ResultSetContainer> results = dataAccessor.getData(request);

        final ResultSetContainer copilotPostings = results.get("project_copilot_postings");

        for (ResultSetContainer.ResultSetRow cp : copilotPostings) {
            long contestId = cp.getLongItem("contest_id");
            long projectId = cp.getLongItem("tc_direct_project_id");
            String billingAccount = cp.getStringItem("billing_project_id");

            ContestBriefDTO contest = new ContestBriefDTO();
            contest.setId(contestId);
            contest.setBillingAccountId(Long.parseLong(billingAccount));

            result.add(contest);
        }

        return result;
    }

    /**
     * <p>Gets the currently open phases for specified project.</p>
     *
     * @param projectId a <code>long</code> providing the project ID.
     * @return a <code>List</code> of current phases for specified project.
     * @throws Exception if an unexpected error occurs.
     * @since 2.1.7
     */
    public static List<ProjectPhaseDTO> getCurrentPhases(long projectId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        Request request = new Request();
        request.setContentHandle("current_project_phases");
        request.setProperty("pids", String.valueOf(projectId));

        Map<String, ResultSetContainer> results = dataAccessor.getData(request);

        // Get current phases
        List<ProjectPhaseDTO> result = new ArrayList<ProjectPhaseDTO>();
        final ResultSetContainer projectPhases = results.get("current_project_phases");
        for (ResultSetContainer.ResultSetRow row : projectPhases) {
            String phaseTypeName = row.getStringItem("phase_type_name");
            ProjectPhaseDTO phase = new ProjectPhaseDTO();
            phase.setPhaseName(phaseTypeName);
            result.add(phase);
        }

        return result;
    }

    /**
     * Gets the customer Ids of the given project id.
     *
     * @param directProjectIds the array of project ids.
     * @return the map from direct project id to customer id.
     * @throws Exception if any error occurs.
     * @since 3.1
     */
    public static Map<Long, Long> getProjectsCustomers(long[] directProjectIds) throws Exception {
        Map<Long, Long> result = new HashMap<Long, Long>();

        if (directProjectIds == null || directProjectIds.length == 0) {
            return result;
        }

        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        Request request = new Request();

        ResultSetContainer resultContainer = null;

        request.setContentHandle("non_admin_client_billing_accounts_v2");
        request.setProperty("uid", String.valueOf(DirectUtils.getTCSubjectFromSession().getUserId()));
        resultContainer = dataAccess.getData(request).get(
                "non_admin_client_billing_accounts_v2");

        if (resultContainer != null) {

            for (ResultSetContainer.ResultSetRow row : resultContainer) {

                long clientId = row.getLongItem("client_id");
                long directProjectId = row.getLongItem("direct_project_id");

                result.put(directProjectId, clientId);
            }

        }

        return result;
    }

    /**
     * Gets all the projects of a client, returned as a map with project id as key and project name as value.
     *
     * @param clientName the name of the client
     * @return the all projects map.
     * @throws Exception if there is any error.
     * @since 3.9
     */
    public static Map<Long, String> getDirectProjectsForClient(String clientName) throws Exception {
        String handlerName = "client_direct_project_ids_v2";
        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        Request request = new Request();

        request.setContentHandle(handlerName);
        request.setProperty("clientname", clientName);
        ResultSetContainer resultContainer = dataAccess.getData(request).get(handlerName);

        Map<Long, String> directProjects = new HashMap<Long, String>();

        if (resultContainer != null) {

            for (ResultSetContainer.ResultSetRow row : resultContainer) {

                long directProjectId = row.getLongItem("direct_project_id");
                String directProjectName = row.getStringItem("direct_project_name");

                directProjects.put(directProjectId, directProjectName);

            }
        }

        return directProjects;
    }

    /**
     * Gets the clients and billing accounts for the admin user. All the clients and billing accounts
     * in the tcs_dw:client_project_dim will be returned. This method is used by EnterpriseDashboardAction
     * to populate the clients and customers dropdown for admin user. And it's a workaround for now and
     * should be replaced with a more formal approach in the following assembly.
     *
     * <p>Updates in 2.6.0:
     * <li>Remove the "ForAdmin" from the method name, and remove the argument projects. The projects list can be
     * got by calling method getUserProjects(long userId).</li>
     *
     * @param tcSubject the tcSubject
     * @return the list of billing projects.
     * @throws Exception if any error occurs.
     */
    public static Map<String, Object> getDashboardClientBillingProjectMappings(TCSubject tcSubject)
            throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        Map<Long, Map<Long, String>> clientBillingMap = new HashMap<Long, Map<Long, String>>();
        Map<Long, Map<Long, String>> clientProjectMap = new HashMap<Long, Map<Long, String>>();
        Map<Long, Map<Long, String>> billingProjectMap = new HashMap<Long, Map<Long, String>>();
        Map<Long, String> clientsMap = new HashMap<Long, String>();
        Map<Long, Long> projectClientMap = new HashMap<Long, Long>();

        DataAccess dataAccess = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);

        Request request = new Request();

        // set to null first
        ResultSetContainer resultContainer = null;

        if (DirectUtils.isTcOperations(tcSubject) || DirectUtils.isTcStaff(tcSubject)) {
            //System.out.println("query the cockpit admin...");
            dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
            request.setContentHandle("admin_client_billing_accounts_v2");
            resultContainer = dataAccess.getData(request).get(
                    "admin_client_billing_accounts_v2");
        } else {
            //System.out.println("query non admin...");
            List<ProjectBriefDTO> projects = getUserProjectsList(tcSubject.getUserId());
            long[] projectIds = new long[projects.size()];
            int index = 0;
            for(ProjectBriefDTO data : projects) {
                projectIds[index] = data.getId();
                index++;
            }
            if (projects.size() > 0) {
                request.setContentHandle("non_admin_client_billing_accounts");
                request.setProperty("tdpis", concatenate(projectIds, ", "));
                resultContainer = dataAccess.getData(request).get(
                        "non_admin_client_billing_accounts");
            }
        }


        if (resultContainer != null) {

            for (ResultSetContainer.ResultSetRow row : resultContainer) {

                long billingId = row.getLongItem("billing_account_id");
                String billingName = row.getStringItem("billing_account_name");
                long clientId = row.getLongItem("client_id");
                String clientName = row.getStringItem("client_name");
                long directProjectId = row.getLongItem("direct_project_id");
                String directProjectName = row.getStringItem("direct_project_name");
                // put into clients map
                clientsMap.put(clientId, clientName);

                // put into clientBillingMap
                Map<Long, String> billingsForClient = clientBillingMap.get(clientId);
                if (billingsForClient == null) {
                    billingsForClient = new HashMap<Long, String>();
                    clientBillingMap.put(clientId, billingsForClient);
                }

                billingsForClient.put(billingId, billingName);

                // put into clientProjectMap
                Map<Long, String> projectForClient = clientProjectMap.get(clientId);
                if (projectForClient == null) {
                    projectForClient = new HashMap<Long, String>();
                    clientProjectMap.put(clientId, projectForClient);
                }

                projectForClient.put(directProjectId, directProjectName);

                // put into billingProjectMap
                Map<Long, String> projectForBilling = billingProjectMap.get(billingId);
                if (projectForBilling == null) {
                    projectForBilling = new HashMap<Long, String>();
                    billingProjectMap.put(billingId, projectForBilling);
                }

                projectForBilling.put(directProjectId, directProjectName);

                projectClientMap.put(directProjectId, clientId);
            }

        }

        result.put("client.billing", clientBillingMap);
        result.put("client.project", clientProjectMap);
        result.put("billing.project", billingProjectMap);
        result.put("clients", clientsMap);
        result.put("project.client", projectClientMap);

        return result;
    }

    /**
     * Gets the clients and billing accounts for the admin user. This method is a new method to replace the
     * method getDashboardClientBillingProjectMappings. It gets the project and billing account relationship
     * directly from the direct_project_account table. The old method is kept in case it's needed by other
     * places.
     *
     * @param tcSubject the tcSubject
     * @return the list of billing projects.
     * @throws Exception if any error occurs.
     * @since 5.3
     */
    public static Map<String, Object> getDashboardClientBillingProjectMappingsV2(TCSubject tcSubject)
            throws Exception {

        Map<String, Object> globalResult = new HashMap<String, Object>();
        Map<Long, ClientBillingDirectProjectMappingDTO> globalRecordCache = new HashMap<Long, ClientBillingDirectProjectMappingDTO>();
        Map<Long, Map<Long, String>> globalClientBillingMap = new HashMap<Long, Map<Long, String>>();
        Map<Long, Map<Long, String>> globalClientProjectMap = new HashMap<Long, Map<Long, String>>();
        Map<Long, Map<Long, String>> globalBillingProjectMap = new HashMap<Long, Map<Long, String>>();
        Map<Long, String> globalClientsMap = new HashMap<Long, String>();
        Map<Long, Long> globalProjectClientMap = new HashMap<Long, Long>();

        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        Request request = new Request();

        ResultSetContainer resultContainer = null;

        final Map<String, Object> applicationContext = DirectUtils.getApplicationContext();

        if(applicationContext.get(DirectUtils.PROJECT_BILLING_MAPPING_RESULT_CACHE) != null
                && applicationContext.get(DirectUtils.PROJECT_BILLING_MAPPING_RECORD_CACHE) != null) {
            globalResult = (Map<String, Object>) applicationContext.get(DirectUtils.PROJECT_BILLING_MAPPING_RESULT_CACHE);
            globalRecordCache = (Map<Long, ClientBillingDirectProjectMappingDTO>) applicationContext.get(DirectUtils.PROJECT_BILLING_MAPPING_RECORD_CACHE);
        } else {
            // get from the database
            request.setContentHandle("admin_client_billing_accounts_v2");
            resultContainer = dataAccess.getData(request).get(
                    "admin_client_billing_accounts_v2");

        if (resultContainer != null) {
            for (ResultSetContainer.ResultSetRow row : resultContainer) {

                long billingId = row.getLongItem("billing_account_id");
                String billingName = row.getStringItem("billing_account_name");
                long clientId = row.getLongItem("client_id");
                String clientName = row.getStringItem("client_name");
                long directProjectId = row.getLongItem("direct_project_id");
                String directProjectName = row.getStringItem("direct_project_name");
                // put into clients map
                globalClientsMap.put(clientId, clientName);

                    // put into clientBillingMap
                    Map<Long, String> billingsForClient = globalClientBillingMap.get(clientId);
                    if (billingsForClient == null) {
                        billingsForClient = new HashMap<Long, String>();
                        globalClientBillingMap.put(clientId, billingsForClient);
                    }

                billingsForClient.put(billingId, billingName);

                // put into clientProjectMap
                Map<Long, String> projectForClient = globalClientProjectMap.get(clientId);
                if (projectForClient == null) {
                    projectForClient = new HashMap<Long, String>();
                    globalClientProjectMap.put(clientId, projectForClient);
                }

                projectForClient.put(directProjectId, directProjectName);

                // put into billingProjectMap
                Map<Long, String> projectForBilling = globalBillingProjectMap.get(billingId);
                if (projectForBilling == null) {
                    projectForBilling = new HashMap<Long, String>();
                    globalBillingProjectMap.put(billingId, projectForBilling);
                }

                projectForBilling.put(directProjectId, directProjectName);

                globalProjectClientMap.put(directProjectId, clientId);

                ClientBillingDirectProjectMappingDTO mappingDTO = new ClientBillingDirectProjectMappingDTO();
                mappingDTO.setBillingId(billingId);
                mappingDTO.setBillingName(billingName);
                mappingDTO.setClientId(clientId);
                mappingDTO.setClientName(clientName);
                mappingDTO.setProjectId(directProjectId);
                mappingDTO.setProjectName(directProjectName);
                globalRecordCache.put(directProjectId, mappingDTO);
        }
      }

            globalResult.put("client.billing", globalClientBillingMap);
            globalResult.put("client.project", globalClientProjectMap);
            globalResult.put("billing.project", globalBillingProjectMap);
            globalResult.put("clients", globalClientsMap);
            globalResult.put("project.client", globalProjectClientMap);
            DirectUtils.getApplicationContext().put(DirectUtils.PROJECT_BILLING_MAPPING_RESULT_CACHE, globalResult);
            DirectUtils.getApplicationContext().put(DirectUtils.PROJECT_BILLING_MAPPING_RECORD_CACHE, globalRecordCache);
        }


        if (DirectUtils.isTcOperations(tcSubject) || DirectUtils.isTcStaff(tcSubject)) {
            return globalResult;
        } else {
            Map<String, Object> result = new HashMap<String, Object>();
            Map<Long, Map<Long, String>> clientBillingMap = new HashMap<Long, Map<Long, String>>();
            Map<Long, Map<Long, String>> clientProjectMap = new HashMap<Long, Map<Long, String>>();
            Map<Long, Map<Long, String>> billingProjectMap = new HashMap<Long, Map<Long, String>>();
            Map<Long, String> clientsMap = new HashMap<Long, String>();
            Map<Long, Long> projectClientMap = new HashMap<Long, Long>();

            // get part of the result with the project ids the use has access to
            List<ProjectBriefDTO> projects = getUserProjectsList(tcSubject.getUserId());
            for(ProjectBriefDTO data : projects) {
                if (globalRecordCache.containsKey(data.getId())) {
                    ClientBillingDirectProjectMappingDTO dto = globalRecordCache.get(data.getId());
                    long billingId = dto.getBillingId();
                    String billingName = dto.getBillingName();
                    long clientId = dto.getClientId();
                    String clientName = dto.getClientName();
                    long directProjectId = dto.getProjectId();
                    String directProjectName = dto.getProjectName();
                    // put into clients map
                    clientsMap.put(clientId, clientName);

                    // put into clientBillingMap
                    Map<Long, String> billingsForClient = clientBillingMap.get(clientId);
                    if (billingsForClient == null) {
                        billingsForClient = new HashMap<Long, String>();
                        clientBillingMap.put(clientId, billingsForClient);
                    }

                    billingsForClient.put(billingId, billingName);

                    // put into clientProjectMap
                    Map<Long, String> projectForClient = clientProjectMap.get(clientId);
                    if (projectForClient == null) {
                        projectForClient = new HashMap<Long, String>();
                        clientProjectMap.put(clientId, projectForClient);
                    }

                    projectForClient.put(directProjectId, directProjectName);

                    // put into billingProjectMap
                    Map<Long, String> projectForBilling = billingProjectMap.get(billingId);
                    if (projectForBilling == null) {
                        projectForBilling = new HashMap<Long, String>();
                        billingProjectMap.put(billingId, projectForBilling);
                    }

                    projectForBilling.put(directProjectId, directProjectName);

                    projectClientMap.put(directProjectId, clientId);
                }
            }

            result.put("client.billing", clientBillingMap);
            result.put("client.project", clientProjectMap);
            result.put("billing.project", billingProjectMap);
            result.put("clients", clientsMap);
            result.put("project.client", projectClientMap);
            return result;
        }
    }


    /**
     * Gets the cost report details with the given paramters. The method returns a list of CostDetailsDTO. Each
     * CostDetailDTO represents cost details of one contest.
     *
     * @param currentUser the current user.
     * @param projectId the direct project id.
     * @param projectCategoryIds the software project category ides.
     * @param studioProjectCategoryIds the studio project category ids.
     * @param clientId the client id.
     * @param billingAccountId the billing accounts id.
     * @param projectStatusIds the project status ids.
     * @param startDate the start date.
     * @param endDate the end date.
     * @return the generated cost report.
     * @throws Exception if any error occurs.
     * @since 2.4.0
     */
    public static List<CostDetailsDTO> getDashboardCostReportDetails(TCSubject currentUser, long projectId, long[] projectCategoryIds, long[] studioProjectCategoryIds,
                long clientId, long billingAccountId, long[] projectStatusIds,  Date startDate, Date endDate, Map<String, Long> statusMapping) throws Exception {
        // create an empty list first to store the result data
        List<CostDetailsDTO> data
                = new ArrayList<CostDetailsDTO>();

        if ((projectCategoryIds == null && studioProjectCategoryIds == null)) {
            return data;
        }

        if((projectCategoryIds == null ? 0 : projectCategoryIds.length) + (studioProjectCategoryIds == null ? 0 : studioProjectCategoryIds.length) == 0) {
            return data;
        }

        if (projectStatusIds == null || (projectStatusIds.length == 0)) {
            return data;
        }

        // concatenate the filters
        String projectCategoryIDsList = "-1";
        if (projectCategoryIds != null && projectCategoryIds.length > 0) {
          projectCategoryIDsList = concatenate(projectCategoryIds, ", ");
        }
        String studioProjectCategoryIdsList = "-1";
        if (studioProjectCategoryIds != null && studioProjectCategoryIds.length > 0) {
           studioProjectCategoryIdsList = concatenate(studioProjectCategoryIds, ", ");
        }

        // date format to prepare date for query input
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();

        String queryName = "dashboard_cost_report_v2";

        if (!setReportQueryParameters(request, currentUser, clientId, billingAccountId, projectId)) {
            return data;
        }

        request.setContentHandle(queryName);
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);
        request.setProperty("scids", studioProjectCategoryIdsList);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);

        // prepare status filter
        Set<Long> statusFilter = new HashSet<Long>();
        for(long statusId : projectStatusIds) {
            statusFilter.add(statusId);
        }

        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {

            CostDetailsDTO costDTO = new CostDetailsDTO();

            // set status first
            if(row.getItem("contest_status").getResultData() != null) {
                costDTO.setStatus(row.getStringItem("contest_status").trim());
            }

            // filter by status
            if (costDTO.getStatus() != null && !statusFilter.contains(statusMapping.get(costDTO.getStatus().trim().toLowerCase()))) {
                continue;
            }

            IdNamePair client = new IdNamePair();
            IdNamePair billing = new IdNamePair();
            IdNamePair directProject = new IdNamePair();
            IdNamePair contest = new IdNamePair();
            IdNamePair contestCategory = new IdNamePair();

            if (row.getItem("client_id").getResultData() != null) {
                client.setId(row.getLongItem("client_id"));
            }
            if (row.getItem("client").getResultData() != null) {
                client.setName(row.getStringItem("client"));
            }
            if (row.getItem("billing_project_id").getResultData() != null) {
                billing.setId(row.getLongItem("billing_project_id"));
            }
            if (row.getItem("billing_project_name").getResultData() != null) {
                billing.setName(row.getStringItem("billing_project_name"));
            }
            if (row.getItem("direct_project_id").getResultData() != null) {
                directProject.setId(row.getLongItem("direct_project_id"));
            }
            if (row.getItem("direct_project_name").getResultData() != null) {
                directProject.setName(row.getStringItem("direct_project_name"));
            }
            if (row.getItem("project_id").getResultData() != null) {
                 contest.setId(row.getLongItem("project_id"));
            }
            if (row.getItem("contest_name").getResultData() != null) {
                contest.setName(row.getStringItem("contest_name"));
            }
            if (row.getItem("project_category_id").getResultData() != null) {
                contestCategory.setId(row.getLongItem("project_category_id"));
            }
            if (row.getItem("category").getResultData() != null) {
                contestCategory.setName(row.getStringItem("category"));
            }
            if (row.getItem("contest_fee").getResultData() != null) {
                costDTO.setContestFee(row.getDoubleItem("contest_fee"));
            }
            if (row.getItem("estimated_member_costs").getResultData() != null) {
                costDTO.setEstimatedCost(row.getDoubleItem("estimated_member_costs"));
            }
            if (row.getItem("actual_member_costs").getResultData() != null) {
                costDTO.setActualCost(row.getDoubleItem("actual_member_costs"));
            }
            if (row.getItem("launch_date").getResultData() != null) {
                costDTO.setLaunchDate(row.getTimestampItem("launch_date"));
            }
            if (row.getItem("completion_date").getResultData() != null) {
                costDTO.setCompletionDate(row.getTimestampItem("completion_date"));
            }
			
			long actualActive = 0;
			if (row.getItem("actual_active").getResultData() != null) {
                actualActive = row.getLongItem("actual_active");
            }

            costDTO.setStudio(row.getIntItem("is_studio") == 1);

            costDTO.setClient(client);
            costDTO.setBilling(billing);
            costDTO.setProject(directProject);
            costDTO.setContest(contest);
            costDTO.setContestType(contestCategory);

            if (costDTO.getStatus() != null) {
                if(costDTO.getStatus().equals("Completed") || costDTO.getStatus().equals("Failed") || costDTO.getStatus().equals("Cancelled")) 
				{
                    // for finished contest, total cost = actual cost + contest fee
                    costDTO.setTotal(costDTO.getActualCost() + costDTO.getContestFee());
                } 
				else if (actualActive == 1 && costDTO.getStatus().equals("Active")) 
				{
					costDTO.setTotal(costDTO.getActualCost() + costDTO.getContestFee());
				}
				 else 
				 {
                    // for unfinished contest, total cost = estimated cost + contest fee
                    costDTO.setTotal(costDTO.getEstimatedCost() + costDTO.getContestFee());
                }
            } else {
                costDTO.setTotal(costDTO.getActualCost() + costDTO.getContestFee());
            }


            data.add(costDTO);
        }

        return data;
    }

    /**
     * Gets the billing cost report entries with the given parameters. The method returns a map,
     * the key is the contest id, the value is a list of billing cost entries.
     *
     * @param invoiceTypes a <code>List</code> providing all the invoice types.
     * @param currentUser the current user.
     * @param projectId the direct project id.
     * @param projectCategoryIds the software project category ides.
     * @param studioProjectCategoryIds the studio project category ids.
     * @param paymentTypeIds the payment type ids.
     * @param clientId the client id.
     * @param billingAccountId the billing accounts id.
     * @param projectStatusIds the project status ids.
     * @param contestId the contest id
     * @param invoiceNumber the invoice number
     * @param startDate the start date.
     * @param endDate the end date.
     * @param statusMapping the mapping of all the contest status.
     * @param paymentTypesMapping the mapping of all the payment types.
     * @return the generated cost report.
     * @throws Exception if any error occurs.
     * @since 2.5.0
     */
    public static Map<Long, List<BillingCostReportEntryDTO>> getDashboardBillingCostReport(List<InvoiceType> invoiceTypes,
                                                                                           TCSubject currentUser, long projectId,
                                                                                           long[] paymentTypeIds,
                                                                                           long clientId, long billingAccountId,
                                                                                           long contestId, String invoiceNumber, Date startDate, Date endDate,
                                                                                           Map<String, Long> paymentTypesMapping) throws Exception {
        // create an empty map first to store the result data
        Map<Long, List<BillingCostReportEntryDTO>> data = new HashMap<Long, List<BillingCostReportEntryDTO>>();

        if (contestId <= 0) {
            if (paymentTypeIds == null || paymentTypeIds.length == 0) {
                return data;
            }
        }

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        endDate = calendar.getTime();

        // date format to prepare date for query input
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();

        String queryName;
        String commandName;
        boolean hasInvoice = invoiceNumber != null && invoiceNumber.trim().length() > 0;

        if (!hasInvoice) {
            commandName = "dashboard_billing_cost_invoice_report_aggregation";
        } else {
            commandName = "dashboard_billing_cost_invoice_report_invoice_number_aggregation";
        }

        if(contestId > 0) {
            request.setProperty("tcdirectid", "0");
            request.setProperty("billingaccountid", "0");
            request.setProperty("clientid", "0");
            request.setProperty("pj", String.valueOf(contestId));
            setUserPermissionQueryParameter(request, currentUser);
        } else {
            request.setProperty("pj", "0");
            if (!setReportQueryParameters(request, currentUser, clientId, billingAccountId, projectId)) {
                return data;
            }
        }
        if (hasInvoice) {
            request.setProperty("invoicenumber", invoiceNumber);
			request.setProperty("tcdirectid", "0");
            request.setProperty("billingaccountid", "0");
            request.setProperty("clientid", "0");
            request.setProperty("pj", "0");
        }

        request.setContentHandle(commandName);

        if (contestId > 0 || hasInvoice) {
            request.setProperty("sdt", dateFormatter.format(new GregorianCalendar(1900, 1, 1).getTime()));
            request.setProperty("edt", dateFormatter.format(new GregorianCalendar(9999, 1, 1).getTime()));
        } else {
            request.setProperty("sdt", dateFormatter.format(startDate));
            request.setProperty("edt", dateFormatter.format(endDate));
        }

        Map<String, ResultSetContainer> allResults = dataAccessor.getData(request);

        // prepare payment type filter
        Set<Long> paymentTypeFilter = new HashSet<Long>();
        for (long paymentTypeId : paymentTypeIds) {
            paymentTypeFilter.add(paymentTypeId);
        }

        for (Entry<String, ResultSetContainer> entry : allResults.entrySet()) {

            ResultSetContainer resultSetContainer = entry.getValue();

            for (ResultSetRow row : resultSetContainer) {

                BillingCostReportEntryDTO costDTO = new BillingCostReportEntryDTO();

                // set status first
                String status = row.getStringItem("contest_status");
                costDTO.setStatus(status == null ? null : status.trim());

                // get payment type
                String paymentType = row.getStringItem("line_item_category");

                IdNamePair client = new IdNamePair();
                IdNamePair billing = new IdNamePair();
                IdNamePair directProject = new IdNamePair();
                IdNamePair contest = new IdNamePair();
                IdNamePair contestCategory = new IdNamePair();

                if (row.getItem("contest_id").getResultData() != null) {
                    contest.setId(row.getLongItem("contest_id"));
                }
                if (row.getItem("contest_name").getResultData() != null) {
                    contest.setName(row.getStringItem("contest_name"));
                }
                if (row.getItem("payment_id").getResultData() != null) {
                    costDTO.setPaymentId(row.getLongItem("payment_id"));
                }

                if (row.getItem("client_id").getResultData() != null) {
                    client.setId(row.getLongItem("client_id"));
                }
                if (row.getItem("client").getResultData() != null) {
                    client.setName(row.getStringItem("client"));
                }
                if (row.getItem("billing_project_id").getResultData() != null) {
                    billing.setId(row.getLongItem("billing_project_id"));
                }
                if (row.getItem("billing_project_name").getResultData() != null) {
                    billing.setName(row.getStringItem("billing_project_name"));
                }
                if (row.getItem("direct_project_id").getResultData() != null) {
                    directProject.setId(row.getLongItem("direct_project_id"));
                }
                if (row.getItem("direct_project_name").getResultData() != null) {
                    directProject.setName(row.getStringItem("direct_project_name"));
                }

                if (row.getItem("project_category_id").getResultData() != null) {
                    contestCategory.setId(row.getLongItem("project_category_id"));
                }
                if (row.getItem("category").getResultData() != null) {
                    contestCategory.setName(row.getStringItem("category"));
                }
                if (row.getItem("actual_total_member_costs").getResultData() != null) {
                    costDTO.setActualTotalMemberCost(row.getDoubleItem("actual_total_member_costs"));
                }
                if (row.getItem("completion_date").getResultData() != null) {
                    costDTO.setCompletionDate(row.getTimestampItem("completion_date"));
                }
                if (row.getItem("launch_date").getResultData() != null) {
                    costDTO.setLaunchDate(row.getTimestampItem("launch_date"));
                }
                if (row.getItem("payment_date").getResultData() != null) {
                    costDTO.setPaymentDate(row.getTimestampItem("payment_date"));
                }
                if (row.getItem("line_item_amount").getResultData() != null) {
                    costDTO.setPaymentAmount(row.getDoubleItem("line_item_amount"));
                }
                if (row.getItem("reference_id").getResultData() != null) {
                    costDTO.setReferenceId(row.getStringItem("reference_id"));
                }

                if (row.getItem("invoice_amount").getResultData() != null) {
                    costDTO.setInvoiceAmount(row.getDoubleItem("invoice_amount"));
                }

                if (row.getItem("process_date").getResultData() != null) {
                    costDTO.setInvoiceDate(row.getTimestampItem("process_date"));
                }

                if (row.getItem("invoice_number").getResultData() != null) {
                    costDTO.setInvoiceNumber(row.getStringItem("invoice_number"));
                }

                if (row.getItem("invoice_id").getResultData() != null) {
                    costDTO.setInvoiceId(row.getLongItem("invoice_id"));
                }

                if (row.getItem("invoice_record_id").getResultData() != null) {
                    costDTO.setInvoiceRecordId(row.getLongItem("invoice_record_id"));
                }

                if (row.getItem("processed").getResultData() != null) {
                    costDTO.setProcessed(row.getBooleanItem("processed"));
                }

                if (row.getItem("payment_desc").getResultData() != null) {
                    costDTO.setPaymentDesc(row.getStringItem("payment_desc"));
                }

                costDTO.setClient(client);
                costDTO.setBilling(billing);
                costDTO.setProject(directProject);
                costDTO.setContest(contest);
                costDTO.setContestType(contestCategory);
                costDTO.setPaymentType(paymentType);

                List<BillingCostReportEntryDTO> entries;

                if (!data.containsKey(costDTO.getContest().getId())) {
                    entries = new ArrayList<BillingCostReportEntryDTO>();
                    data.put(costDTO.getContest().getId(), entries);
                } else {
                    entries = data.get(costDTO.getContest().getId());
                }

                // add the entry if payment type filter allows
                if (paymentTypeFilter.contains(paymentTypesMapping.get(paymentType.trim().toLowerCase()))) {
                    entries.add(costDTO);
                }
            }
        }

        return data;
    }

    /**
     * Gets a list of invoice numbers from the billing account id.
     *
     *
     * @param billingAccountId the id of the billing account.
     * @return a list of string representing the invoice numbers.
     * @throws Exception if any error.
     * @since 4.5
     */
    public static List<String> getInvoiceNumbersFromBilling(long billingAccountId) throws Exception {
        List<String> result = new ArrayList<String>();

        final String queryName = "get_invoice_number_from_billing";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("billingaccountid", String.valueOf(billingAccountId));

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            result.add(row.getStringItem("invoice_number"));
        }

        return result;
    }

    /**
     * Gets a list of projects from the invoice number and billing account id.
     *
     * @param invoiceNumber the invoice number.
     * @param billingAccountId the id of the billing account.
     * @return a list of projects, each project is stored in a <code>IdNamePair</code> instance.
     * @throws Exception if any error.
     * @since 4.5
     */
    public static List<IdNamePair> getProjectsForInvoiceNumberAndBilling(String invoiceNumber, long billingAccountId) throws Exception {
        List<IdNamePair> result = new ArrayList<IdNamePair>();

        final String queryName = "get_projects_from_billing_and_invoice_number";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("billingaccountid", String.valueOf(billingAccountId));
        request.setProperty("invoicenumber", invoiceNumber);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            IdNamePair item = new IdNamePair();
            item.setId(row.getLongItem("direct_project_id"));
            item.setName(row.getStringItem("direct_project_name"));
            result.add(item);
        }

        return result;
    }

    /**
     * Gets the contest receipt data for a contest.
     *
     * @param contestId the contest id.
     * @param isStudio true if the contest is a studio contest, false otherwise.
     * @return a <code>ContestReceiptDTO</code> instance providing the contest receipt data.
     * @throws Exception if any error occurs.
     */
    public static ContestReceiptDTO getContestReceipt(long contestId, boolean isStudio) throws Exception {
        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        Request request = new Request();
        request.setContentHandle("direct_contest_receipt_replatforming");
        request.setProperty("pj", String.valueOf(contestId));

        ResultSetContainer result = dataAccess.getData(request).get("direct_contest_receipt_replatforming_v2");
        if (result.size() == 0) {
            return null;
        }

        ContestReceiptDTO contestReceipt = new ContestReceiptDTO();
        List<ContestReceiptEntry> entries = new ArrayList<ContestReceiptEntry>();
        boolean intializeCommon = false;
        for (ResultSetContainer.ResultSetRow row : result) {
            if(!intializeCommon) {
                contestReceipt.setShowReceipt(row.getIntItem("show_receipt") == 1);
                contestReceipt.setFinished(contestReceipt.isShowReceipt());
                if(row.getItem("contest_launcher_id").getResultData() != null) {
                    contestReceipt.setContestLauncherId(Long.parseLong(row.getStringItem("contest_launcher_id")));
                } else {
                    contestReceipt.setContestLauncherId(0);
                }
            }

            ContestReceiptEntry entry = new ContestReceiptEntry();
            entry.setPaymentType(row.getStringItem("payment_type"));
            entry.setPaymentAmount(row.getDoubleItem("payment_amount"));

            entries.add(entry);
        }
        contestReceipt.setEntries(entries);

        return contestReceipt;
    }

    /**
     * <p>Constructs new <code>ProjectBriefDTO</code> instance based on specified properties.</p>
     *
     * @param id a <code>long</code> providing the project ID.
     * @param name a <code>String</code> providing the project name.
     * @return an <code>ProjectBriefDTO</code> providing the details for a single project.
     */
    private static ProjectBriefDTO createProject(long id, String name) {
        ProjectBriefDTO project = new ProjectBriefDTO();
        project.setId(id);
        project.setName(name);
        return project;
    }

    /**
     * <p>Constructs new <code>ContestBriefDTO</code> instance based on specified properties.</p>
     *
     * @param id a <code>long</code> providing the contest ID.
     * @param name a <code>String</code> providing the contest name.
     * @param project a <code>ProjectBriefDTO</code> providing the details for project contest belongs to.
     * @return an <code>ContestBriefDTO</code> providing the details for a single contest.
     */
    private static ContestBriefDTO createContest(long id, String name, ProjectBriefDTO project) {
        ContestBriefDTO contest = new ContestBriefDTO();
        contest.setId(id);
        contest.setTitle(name);
        contest.setProject(project);
        return contest;
    }

    /**
     * <p>Constructs new <code>ContestBriefDTO</code> instance based on specified properties.</p>
     *
     * @param id a <code>long</code> providing the contest ID.
     * @param name a <code>String</code> providing the contest name.
     * @param project a <code>ProjectBriefDTO</code> providing the details for project contest belongs to.
     * @return an <code>ContestBriefDTO</code> providing the details for a single contest.
     */
    private static ContestBriefDTO createContest(long id, String name, ProjectBriefDTO project, ContestType contestType) {
        TypedContestBriefDTO contest = new TypedContestBriefDTO();
        contest.setId(id);
        contest.setTitle(name);
        contest.setProject(project);

        contest.setContestType(contestType);

        return contest;
    }

    /**
     * <p>Constructs new <code>ContestBriefDTO</code> instance based on specified properties.</p>
     *
     * @param id a <code>long</code> providing the contest ID.
     * @param name a <code>String</code> providing the contest name.
     * @param project a <code>ProjectBriefDTO</code> providing the details for project contest belongs to.
     * @return an <code>ContestBriefDTO</code> providing the details for a single contest.
     */
    private static ContestBriefDTO createContest(long id, String name, ProjectBriefDTO project, Boolean isSoftware, String contestType) {
        ContestBriefDTO contest = new ContestBriefDTO();
        contest.setId(id);
        contest.setTitle(name);
        contest.setProject(project);
        contest.setSoftware(isSoftware);
        contest.setContestTypeName(contestType);
        return contest;
    }

    /**
     * <p>Constructs new <code>TypedContestBriefDTO</code> instance based on specified properties.</p>
     *
     * @param id a <code>long</code> providing the contest ID.
     * @param name a <code>String</code> providing the contest name.
     * @param project a <code>ProjectBriefDTO</code> providing the details for project contest belongs to.
     * @param type a <code>ContestType</code> providing the contest type.
     * @param status a <code>ContestStatus</code> providing the contest status.
     * @return an <code>TypedContestBriefDTO</code> providing the details for a single contest.
     */
    private static TypedContestBriefDTO createTypedContest(long id, String name, ProjectBriefDTO project,
                                                           ContestType type, ContestStatus status, Boolean isSoftware) {
        TypedContestBriefDTO contest = new TypedContestBriefDTO();
        contest.setId(id);
        contest.setTitle(name);
        contest.setProject(project);
        contest.setContestType(type);
        contest.setStatus(status);
        contest.setSoftware(isSoftware);
        return contest;
    }

    /**
     * <p>Constructs new <code>ActivityDTO</code> instance based on specified properties.</p>
     *
     * @param contest a <code>ContestBriefDTO</code> providing the details for the contest associated with activity.
     * @param date a <code>Date</code> providing the timestamp for the activity.
     * @param handle a <code>String</code> providing the handle for the user who is the originator of the activity.
     * @param userId a <code>long</code> providing the
     * @param type an <code>ActivityType</code> referencing the type of the activity.
     * @return an <code>ActivityDTO</code> providing the details for a single activity.
     */
    private static ActivityDTO createActivity(TypedContestBriefDTO contest,
                                              Date date, String handle, long userId,
                                              ActivityType type) {
        ActivityDTO activity = new ActivityDTO();
        activity.setContest(contest);
        activity.setDate(date);
        activity.setOriginatorHandle(handle);
        activity.setOriginatorId(userId);
        activity.setType(type);
        return activity;
    }

    /**
     * <p>
     * Constructs new <code>ProjectContestDTO</code> instance based on specified properties.
     * </p>
     *
     * @param contestBrief a <code>ContestBriefDTO</code> providing the details for the contest associated with activity.
     * @param type a <code>Date</code> providing the timestamp for the activity.
     * @param status a <code>String</code> providing the handle for the user who is the originator of the activity.
     * @param startTime a <code>long</code> providing the
     * @param endTime an <code>ActivityType</code> referencing the type of the activity.
     * @return an <code>ProjectContestDTO</code> providing the details for a single project contest.
     */
    private static ProjectContestDTO createProjectContest(ContestBriefDTO contestBrief, ContestType type,
                                                          ContestStatus status, Date startTime, Date endTime,
                                                          int forumPostsCount, int registrantsCount,
                                                          int submissionsCount, int forumId,
                                                          Boolean isStudio) {
        ProjectContestDTO dto = new ProjectContestDTO();
        dto.setContestType(type);
        dto.setContest(contestBrief);
        dto.setEndTime(endTime);
        dto.setForumPostsNumber(forumPostsCount);
        dto.setRegistrantsNumber(registrantsCount);
        dto.setStartTime(startTime);
        dto.setStatus(status);
        dto.setSubmissionsNumber(submissionsCount);
        dto.setForumId(forumId);
        dto.setIsStudio(isStudio);
        return dto;
    }

    /**
     * <p>Concatenates the IDs for specified projects into a single string value.</p>
     *
     * @param tcDirectProjects a <code>List</code> providing the details for projects.
     * @return a <code>String</code> providing the IDs for specified projects separated with commas.
     */
    private static String getConcatenatedIdsString(List<ProjectData> tcDirectProjects) {
        StringBuilder b = new StringBuilder();
        for (ProjectData project : tcDirectProjects) {
            if (b.length() > 0) {
                b.append(", ");
            }
            b.append(project.getProjectId());
        }
        return b.toString();
    }

    /**
     * <p>Gets the <code>int</code> value for specified column from specified resultset row.</p>
     *
     * @param row a <code>ResultSetRow</code> providing the data.
     * @param columnName a <code>String</code> providing the column name.
     * @return an <code>int</code> providing tge value for specified column or 0 if the value is <code>null</code>.
     */
    private static int getInt(ResultSetContainer.ResultSetRow row, String columnName) {
        TCResultItem resultItem = row.getItem(columnName);
        boolean isNull = resultItem.getResultData() == null;
        if (isNull) {
            return 0;
        } else {
            return row.getIntItem(columnName);
        }
    }

    /**
     * <p>Gets the <code>long</code> value for specified column from specified resultset row.</p>
     *
     * @param row a <code>ResultSetRow</code> providing the data.
     * @param columnName a <code>String</code> providing the column name.
     * @return a <code>long</code> providing tge value for specified column or 0 if the value is <code>null</code>.
     */
    private static long getLong(ResultSetContainer.ResultSetRow row, String columnName) {
        TCResultItem resultItem = row.getItem(columnName);
        boolean isNull = resultItem.getResultData() == null;
        if (isNull) {
            return 0;
        } else {
            return row.getLongItem(columnName);
        }
    }

    /**
     * <p>Gets the <code>double</code> value for specified column from specified resultset row.</p>
     *
     * @param row a <code>ResultSetRow</code> providing the data.
     * @param columnName a <code>String</code> providing the column name.
     * @return a <code>double</code> providing tge value for specified column or 0 if the value is <code>null</code>.
     */
    private static double getDouble(ResultSetContainer.ResultSetRow row, String columnName) {
        TCResultItem resultItem = row.getItem(columnName);
        boolean isNull = resultItem.getResultData() == null;
        if (isNull) {
            return 0;
        } else {
            return row.getDoubleItem(columnName);
        }
    }


    /**
     * <p>Gets the <code>Date</code> value for specified column from specified resultset row.</p>
     *
     * @param row a <code>ResultSetRow</code> providing the data.
     * @param columnName a <code>String</code> providing the column name.
     * @return a <code>Date</code> providing tge value for specified column or 0 if the value is <code>null</code>.
     */
    private static Date getDate(ResultSetContainer.ResultSetRow row, String columnName) throws  ParseException{
        SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        TCResultItem resultItem = row.getItem(columnName);
        boolean isNull = resultItem.getResultData() == null;
        if (isNull) {
            return null;
        } else {
            String dt = row.getStringItem(columnName);
            return myFmt.parse(dt);
        }
    }

    /**
     * <p>Build a string concatenating the specified values separated with specified delimiter.</p>
     *
     * @param items a <code>long</code> array providing the values to be concatenated.
     * @param delimiter a <code>String</code> providing the delimiter to be inserted between concatenated items.
     * @return a <code>String</code> providing the concatenated item values.
     * @since 2.1.9
     */
    private static String concatenate(long[] items, String delimiter) {
        StringBuilder b = new StringBuilder();
        for (Long id : items) {
            if (b.length() > 0) {
                b.append(delimiter);
            }
            b.append(id);
        }
        return b.toString();
    }

    /**
     * <p>Build a string concatenating the specified values separated with specified delimiter.</p>
     *
     * @param items a <code>int</code> array providing the values to be concatenated.
     * @param delimiter a <code>String</code> providing the delimiter to be inserted between concatenated items.
     * @return a <code>String</code> providing the concatenated item values.
     * @since 6.10
     */
    private static String concatenate(int[] items, String delimiter) {
        StringBuilder b = new StringBuilder();
        for (int id : items) {
            if (b.length() > 0) {
                b.append(delimiter);
            }
            b.append(id);
        }
        return b.toString();
    }

    /**
     * <p>Build a string concatenating the specified values separated with specified delimiter.</p>
     *
     * @param items a <code>long</code> Iterable providing the values to be concatenated.
     * @param delimiter a <code>String</code> providing the delimiter to be inserted between concatenated items.
     * @return a <code>String</code> providing the concatenated item values.
     * @since 2.9.1
     */
    private static String concatenate(Iterable<Long> items, String delimiter) {
        StringBuilder b = new StringBuilder();
        for (Long id : items) {
            if (b.length() > 0) {
                b.append(delimiter);
            }
            b.append(id);
        }
        return b.toString();
    }

    /**
     * <p>Gets the details on contests associated with the specified project.</p>
     *
     * <p>Sub-sequent assemblies must implement this method to use the appropriate logic for getting the details on
     * project. Current implementation uses mock data.</p>
     *
     * @param userId a <code>long</code> providing the ID of a user associated with project.
     * @param projectId a <code>long</code> providing the ID for project to get the details for associated contests for.
     * @param cached a flag indicates whether to cache the query result.
     * @return a <code>ProjectContestsListDTO</code> providing the details on contests associated with specified
     *         project.
     * @throws Exception if an unexpected error occurs.
     * @since 2.5.0
     */
    public static Map<ContestBriefDTO, ContestHealthDTO> getProjectContestsHealth(long userId, long projectId,
                                                                                  boolean cached)
        throws Exception {

        DataAccess dataAccessor;

        if (cached) {
            dataAccessor = new CachedDataAccess(MaxAge.QUARTER_HOUR, DBMS.TCS_OLTP_DATASOURCE_NAME);
        } else {
            dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        }

        DirectUtils.refreshCache("direct_project_overview_contests_health_replatforming");

        Request request = new Request();
        request.setContentHandle("direct_project_overview_contests_health_replatforming");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("tcdirectid", String.valueOf(projectId));

        final Map<Long, ProjectBriefDTO> projects = new HashMap<Long, ProjectBriefDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get("direct_project_overview_contests_health_replatforming");
        final int recordNum = resultContainer.size();

        Map<ContestBriefDTO, ContestHealthDTO> contests = new HashMap<ContestBriefDTO, ContestHealthDTO>();
        for (int i = 0; i < recordNum; i++) {

                ContestHealthDTO contestHealthDTO = new ContestHealthDTO();

                boolean isStudio = resultContainer.getBooleanItem(i, "is_studio");

                if (isStudio) {
                    contestHealthDTO.setRegistrationStatus(RegistrationStatus.HEALTHY);
                } else {
                    // Evaluate current registration status
                    double reliabilityTotal = getDouble(resultContainer.getRow(i), "reliability_total");
                    long registrationPhaseStatus = getLong(resultContainer.getRow(i), "registration_phase_status");
                    long projectCategoryId = getLong(resultContainer.getRow(i), "project_category_id");
                    setRegistrationPhaseStatus(contestHealthDTO, reliabilityTotal, registrationPhaseStatus);

                    if(projectCategoryId == 29L) {
                        // always set to health
                        contestHealthDTO.setRegistrationStatus(RegistrationStatus.HEALTHY);
                    }

                }

                // Evaluate current phase status
                Date currentPhaseEndTime = null;
                if (resultContainer.getItem(i, "current_phase_end_time").getResultData() != null) {
                    currentPhaseEndTime = resultContainer.getTimestampItem(i, "current_phase_end_time");
                }
                setCurrentPhaseStatus(contestHealthDTO, currentPhaseEndTime);

                // Evaluate forum activity status
                int unAnsweredThreadsCount = getInt(resultContainer.getRow(i), "unanswered_threads");
                contestHealthDTO.setUnansweredForumPostsNumber(unAnsweredThreadsCount);

                // Evaluate review sign-up status
                int requiredReviewersCount = getInt(resultContainer.getRow(i), "required_reviewers_count");
                int registeredReviewersCount = getInt(resultContainer.getRow(i), "registered_reviewers_count");
                long hoursLeft = getInt(resultContainer.getRow(i), "review_hours_left");
                setReviewSignupStatus(contestHealthDTO, hoursLeft, requiredReviewersCount,
                                      registeredReviewersCount);

                // Evaluate dependencies status
                Integer dependenciesCount
                    = resultContainer.getIntItem(i, "dependencies_count");
                Integer incompleteDependenciesCount
                    = resultContainer.getIntItem(i, "incomplete_dependencies_count");

                if (dependenciesCount == 0) {
                    contestHealthDTO.setDependenciesStatus(DependenciesStatus.NO_DEPENDENCIES);
                } else if (incompleteDependenciesCount > 0) {
                    contestHealthDTO.setDependenciesStatus(DependenciesStatus.DEPENDENCIES_NON_SATISFIED);
                } else {
                    contestHealthDTO.setDependenciesStatus(DependenciesStatus.DEPENDENCIES_SATISFIED);
                }

                // Set colors based on evaluated statuses
                DashboardHelper.setContestStatusColor(contestHealthDTO);

                // Get details for TC Direct Project
                long tcDirectProjectId = resultContainer.getLongItem(i, "tc_direct_project_id");
                String tcDirectProjectName = resultContainer.getStringItem(i, "tc_direct_project_name");

                final ProjectBriefDTO project;
                if (projects.containsKey(tcDirectProjectId)) {
                    project = projects.get(tcDirectProjectId);
                } else {
                    project = createProject(tcDirectProjectId, tcDirectProjectName);
                    projects.put(tcDirectProjectId, project);
                }

                // Get details for contest
                long contestId = resultContainer.getLongItem(i, "contest_id");
                String contestName = resultContainer.getStringItem(i, "contest_name");
                String contestType = resultContainer.getStringItem(i, "contest_type");

                ContestBriefDTO contestBrief = createContest(contestId, contestName, project, !isStudio, contestType);

                // Map contest to health status
                contests.put(contestBrief, contestHealthDTO);
        }

        return contests;
    }

     /**
     * Gets the documentUrl for the given document id.
     *
     * @param documentId the document Id to get url
     * @return the document Url
     * @throws Exception if can not find the document url
     */
    public static String getDocumentUrl(String documentId) throws Exception {
        final String queryName = "retrieve_document_url_replatforming";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("docid", documentId);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        if(resultSetContainer.getRowCount() != 1) {
            throw new DirectException("The document does not exist for " + documentId);
        }
        return resultSetContainer.getStringItem(0, "url");
    }


    /**
     * <p>Sets the specified contest health DTO with status of running phase (Late, Closing, Running).</p>
     *
     * @param dto a <code>ContestHealthDTO</code> providing the DTO to be set with current phase status.
     * @param currentPhaseEndTime a <code>Date</code> providing the end time for current phase or <code>null</code> if
     *        there is no active phase for contest.
     * @since 2.5.0
     */
    private static void setCurrentPhaseStatus(ContestHealthDTO dto, Date currentPhaseEndTime) {
        if (currentPhaseEndTime != null) {
            Date now = new Date();
            if (now.compareTo(currentPhaseEndTime) > 0) {
                dto.setCurrentPhaseStatus(RunningPhaseStatus.LATE);
            } else {
                long diff = currentPhaseEndTime.getTime() - now.getTime();
                long hoursLeft = diff / (3600 * 1000);
                if (hoursLeft < 2) {
                    dto.setCurrentPhaseStatus(RunningPhaseStatus.CLOSING);
                } else {
                    dto.setCurrentPhaseStatus(RunningPhaseStatus.RUNNING);
                }
            }
        }
    }

    /**
     * <p>Sets the specified contest health DTO with status of registration phase (Healthy, Poor, Less Than Ideal).</p>
     *
     * @param dto a <code>ContestHealthDTO</code> providing the DTO to be set with registration phase status.
     * @param reliabilityTotal a <code>double</code> providing the total reliability rating for registered users.
     * @param registrationPhaseStatus a <code>long</code> referencing the current status of registration phase.
     * @since 2.5.0
     */
    private static void setRegistrationPhaseStatus(ContestHealthDTO dto, double reliabilityTotal,
                                                   long registrationPhaseStatus) {
        dto.setRegistrationStatus(RegistrationStatus.HEALTHY);
        if (registrationPhaseStatus == 2) {
            if (reliabilityTotal >= 200) {
                dto.setRegistrationStatus(RegistrationStatus.HEALTHY);
            } else {
                dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_LESS_IDEAL_ACTIVE);
            }
        } else if (registrationPhaseStatus == 3) {
            if (reliabilityTotal >= 200) {
                dto.setRegistrationStatus(RegistrationStatus.HEALTHY);
            } else if (reliabilityTotal >= 100) {
                dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_LESS_IDEAL_CLOSED);
            } else {
                dto.setRegistrationStatus(RegistrationStatus.REGISTRATION_POOR);
            }
        }
    }

    /**
     * <p>Sets the specified contest health DTO with status of review sign-up (All Filled, Danger, Warning).</p>
     *
     * @param dto a <code>ContestHealthDTO</code> providing the DTO to be set with review sign-up status.
     * @param hoursLeft a <code>long</code> providing the hours left for review phase to complete.
     * @param requiredReviewersCount an <code>int</code> providing the required number of reviewers for contest.
     * @param registeredReviewersCount an <code>int</code> providing the actual number of registered reviewers for
     *        contest.
     * @since 2.5.0
     */
    private static void setReviewSignupStatus(ContestHealthDTO dto, long hoursLeft, int requiredReviewersCount,
                                              int registeredReviewersCount) {
        if (requiredReviewersCount > registeredReviewersCount) {
            if (hoursLeft < 24) {
                dto.setReviewersSignupStatus(ReviewersSignupStatus.REVIEW_POSITIONS_NON_FILLED_DANGER);
            } else {
                dto.setReviewersSignupStatus(ReviewersSignupStatus.REVIEW_POSITIONS_NON_FILLED_WARNING);
            }
        } else {
            dto.setReviewersSignupStatus(ReviewersSignupStatus.ALL_REVIEW_POSITIONS_FILLED);
        }
    }

    /**
     * <p>Gets the issues of the contest. The result is returned in a ContestIssuesTrackingDTO object.</p>
     *
     * @param contestId the id of the contest
     * @param isStudio the boolean to tell if the contest is a studio contest
     * @return the ContestIssuesTrackingDTO object
     * @throws Exception if an unexpected error occurs.
     * @since 2.6.2
     */
    public static ContestIssuesTrackingDTO getContestIssues(long contestId) throws Exception {

        // get issues and bug races from the Jira RPC soap service
        List<TcJiraIssue> results = JiraRpcServiceWrapper.getIssuesForContest(contestId);

        // use one list to store issues, another list to store bug races
        List<TcJiraIssue> issues = new ArrayList<TcJiraIssue>();
        List<TcJiraIssue> bugRaces = new ArrayList<TcJiraIssue>();

        // get the jira project name for bug race from the configuration. It will be used to tell which issue
        // is a bug race
        String bugRaceProjectName = ConfigUtils.getIssueTrackingConfig().getBugRaceProjectName().trim().toLowerCase();


        // filter out the jira issues and bug races
        for (TcJiraIssue item : results) {
            if(item.getProjectName().trim().toLowerCase().equals(bugRaceProjectName)) {
                bugRaces.add(item);
            } else {
                issues.add(item);
            }
        }

        // populate result
        ContestIssuesTrackingDTO result = new ContestIssuesTrackingDTO();
        result.setContestId(contestId);
        result.setIssues(issues);
        result.setBugRaces(bugRaces);

        return result;
    }


    /**
     * <p>Gets the issues of the direct project. The list of contests belong to the project will be passed in.</p>
     *
     * @param contests the list of the contests
     * @return map of contest to contest issues.
     * @throws Exception if an unexpected error occurs.
     * @since 2.6.2
     */
    public static Map<ContestBriefDTO, ContestIssuesTrackingDTO> getDirectProjectIssues(List<? extends ContestBriefDTO> contests) throws Exception {

        // Gets result from jira service
        List<TcJiraIssue> issues = JiraRpcServiceWrapper.getIssuesForDirectProject(contests);

        // Creates map to store result
        Map<ContestBriefDTO, ContestIssuesTrackingDTO> issuesMap = new HashMap<ContestBriefDTO, ContestIssuesTrackingDTO>();

        // Creates another assistant map
        Map<Long, ContestIssuesTrackingDTO> idsMap = new HashMap<Long, ContestIssuesTrackingDTO>();

        // Initializes the maps first
        for(ContestBriefDTO contest : contests) {
            ContestIssuesTrackingDTO contestIssues = new ContestIssuesTrackingDTO();
            contestIssues.setBugRaces(new ArrayList<TcJiraIssue>());
            contestIssues.setIssues(new ArrayList<TcJiraIssue>());
            contestIssues.setContestId(contest.getId());
            issuesMap.put(contest, contestIssues);
            idsMap.put(contest.getId(), contestIssues);
        }

        // Puts result into the map
        for(TcJiraIssue issue : issues) {
            Long projectId = issue.getProjectID();

            ContestIssuesTrackingDTO dto;

            if(issue.isBugRace()) {
                if(projectId != null) {
                    dto = idsMap.get(projectId);
                    if (dto != null) {
                        dto.getBugRaces().add(issue);
                    }
                }

            } else {
                 if(projectId != null) {
                    dto = idsMap.get(projectId);
                    if (dto != null) {
                        dto.getIssues().add(issue);
                    }
                }

            }


        }

        return issuesMap;
    }

    /**
     * <p>Gets the number of forum messages for the specified <code>TC Direct</code> project.</p>
     *
     * @param tcDirectProjectId a <code>long</code> providing the ID of TC Direct projects.
     * @return a <code>long</code> providing the number of forum messages for specified project.
     * @throws Exception if an unexpected error occurs.
     * @since 2.8.0
     */
    public static long getTopCoderDirectProjectForumThreadsCount(Long tcDirectProjectId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("tc_direct_project_forum_threads_count");
        request.setProperty("tcdirectid", String.valueOf(tcDirectProjectId));

        final ResultSetContainer resultContainer
            = dataAccessor.getData(request).get("tc_direct_project_forum_threads_count");

        return resultContainer.getLongItem(0, "forum_messages_count");
    }

    /**
     * <p>Gets the list of records with statuses for forums for specified <code>TC Direct</code> project.</p>
     *
     *
     * @param tcDirectProjectId a <code>long</code> providing the ID of the TC Direct project.
     * @param userId a <code>long</code> providing the ID of current user.
     * @return a <code>List</code> of records for forums statuses for project.
     * @throws Exception if an unexpected error occurs.
     * @since 2.8.0
     */
    public static List<ProjectForumStatusDTO> getProjectForumStatus(long tcDirectProjectId, long userId)
        throws Exception {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        List<ProjectForumStatusDTO> result = new ArrayList<ProjectForumStatusDTO>();
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("tc_direct_project_forum_status");
        request.setProperty("tcdirectid", String.valueOf(tcDirectProjectId));
        request.setProperty("uid", String.valueOf(userId));

        final ResultSetContainer resultContainer
            = dataAccessor.getData(request).get("tc_direct_project_forum_status");
        for (ResultSetContainer.ResultSetRow row : resultContainer) {
            ProjectForumStatusDTO entry = new ProjectForumStatusDTO();
            entry.setIsRead(row.getLongItem("unread_messages_count") == 0);
            entry.setThreadNumber(row.getLongItem("threads_number"));
            entry.setMessageNumber(row.getLongItem("messages_number"));
            if (row.getItem("latest_handle").getResultData() != null) {
                entry.setLastPostHandle(row.getStringItem("latest_handle"));
                long lastPostUserId = row.getLongItem("latest_user_id");
                entry.setLatestPostAuthorLink(HandleTag.getLink(lastPostUserId, "", "", null, null, new String[]{"coderTextOrange", "coderTextWhite", "coderTextGray", "coderTextGreen", "coderTextBlue", "coderTextYellow", "coderTextRed"}, new String[]{"coderTextOrange", "coderTextBlack", "coderTextGray", "coderTextGreen", "coderTextBlue", "coderTextYellow", "coderTextRed"}, false));
            }
            if (row.getItem("latest_time").getResultData() != null) {
                Timestamp timestamp = row.getTimestampItem("latest_time");
                entry.setLastPostTime(format.format(timestamp));
            }
            entry.setThreadID(row.getLongItem("forum_id"));
            entry.setThreadTitle(row.getStringItem("name"));
            entry.setSummary(row.getStringItem("description"));
            entry.setWatching(row.getBooleanItem("watching"));
            result.add(entry);
        }

        return result;
    }

    /**
     * <p>
     * Gets the mapping to be used for looking up the project copilot types by IDs.
     * </p>
     * @return a <code>Map</code> mapping the project copilot type ids to category names.
     * @throws Exception
     *             if an unexpected error occurs.
     * @since 2.9.1
     */
    public static Map<Long, String> getAllProjectCopilotTypes() throws Exception {
        Map<Long, String> map = new LinkedHashMap<Long, String>();

        final String queryName = "project_copilot_types";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            map.put(row.getLongItem("project_copilot_type_id"), row.getStringItem("name"));
        }

        return map;
    }

    /**
     * <p>Sets the user id parameter for report query to check the permission.</p>
     *
     * @param request the query request instance.
     * @param currentUser the current user.
     */
    private static void setUserPermissionQueryParameter(Request request, TCSubject currentUser) {
        long userId = currentUser.getUserId();
        if (DirectUtils.isTcOperations(currentUser) || DirectUtils.isTcStaff(currentUser)) {
            // TC Staff or TC Operations can access all direct projects
            userId = 0;
        }
        request.setProperty("uid", String.valueOf(userId));
    }

    /**
     * <p>Sets the common query parameters for the report queries.</p>
     *
     * @param request the query request instance.
     * @param currentUser the current user.
     * @param clientId the client id.
     * @param billingAccountId the billing account id.
     * @param projectId the project id.
     * @return true if there are some parameters set, false otherwise.
     * @since 2.9.1
     */
    private static boolean setReportQueryParameters(Request request, TCSubject currentUser, long clientId, long billingAccountId, long projectId) {
        setUserPermissionQueryParameter(request, currentUser);
    
    request.setProperty("tcdirectid", "0");
        request.setProperty("billingaccountid", "0");
        request.setProperty("clientid", "0");
    
    boolean anySet = false;
      
        if (projectId > 0) {
            request.setProperty("tcdirectid", String.valueOf(projectId));
      anySet = true;
        }
    if (billingAccountId > 0) {
            request.setProperty("billingaccountid", String.valueOf(billingAccountId));
      anySet = true;
        }
    if (clientId >= 0) {
            request.setProperty("clientid", String.valueOf(clientId));
      anySet = true;
        }
        return anySet;
    }

    /**
     * <p>Gets the <code>InvoiceRecordBriefDTO</code> data for multi payment data. In <code>invoice_record</code> table,
     * payment_id can unique determine contest_id, jira_issue_id, billing_account, invoice_type_id.
     * contest_id can unique determine billing_account. jira_issue_id can unique determine contest_id, billing_account.
     * So we should NOT get these data from request parameters because it may case data inconsistency
     * in <code>invoice_record</code> table if user construct URL manually.</p>
     *
     * <p>If payment_id is not 0, contest_id, jira_issue_id, billing_account_id, invoice_type will be returned from
     * database using payment_id.</p>
     * <p>If payment id is 0 and jira_issue_id is not empty, contest_id and billing_account_id will be returned from
     * database using jira_issue_id.</p>
     * <p>If payment_id is 0 and contest_id is not empty, billing_account_id will be returned from database
     * using contest_id.</p>
     *
     * @param jiraIssueIds the jira issue id of the payment data. Only used when corresponding payment id is zero.
     * @param contestIds the contest id of the payment data. Only used when corresponding payment id is zero and
     *                   jira issue id is empty.
     * @param paymentIds the payment id of the payment data.
     * @param invoiceTypeNames the invoice type names of the payment data.
     * @return a <code>List</code> providing the contest_id, jira_issue_id, billing_account_id,
     *         invoice_type data of the payment data.
     * @throws Exception if any error occurs.
     * @since 2.9.1
     */
    public static List<InvoiceRecordBriefDTO> getInvoiceRecordRelatedData(List<String> jiraIssueIds,
                                                                          List<Long> contestIds, List<Long> paymentIds,
                                                                          List<String> invoiceTypeNames)
            throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("tc_direct_contest_payment_invoice");
        List<Long> paymentIdsList = new ArrayList<Long>();
        paymentIdsList.add(0L);
        // get unique contest IDs
        Set<Long> contestIdsSet = new HashSet<Long>();
        contestIdsSet.add(0L);
        // get unique JIRA issue IDs
        Set<String> jiraIssueIdsSet = new HashSet<String>();
        jiraIssueIdsSet.add("0"); System.out.println("---------------jiraIssueIds---------------------"+jiraIssueIds);
        // prepare for the query parameters
        for (int i = 0; i < contestIds.size(); i++) {
            if (!PaymentType.PLATFORM_FEE.getDescription().equalsIgnoreCase(invoiceTypeNames.get(i))) {
                if (paymentIds.get(i) > 0) {
                    paymentIdsList.add(paymentIds.get(i));
                } else if (jiraIssueIds!= null && jiraIssueIds.get(i) != null && jiraIssueIds.get(i).length() > 0) {
                    jiraIssueIdsSet.add(jiraIssueIds.get(i));
                } else {
                    // use contest_id if payment_id is zero and jira_issue_id is empty
                    contestIdsSet.add(contestIds.get(i));
                }
            }
        }
        request.setProperty("pids", concatenate(contestIdsSet, ","));
        request.setProperty("payids", concatenate(paymentIdsList, ","));
        request.setProperty("jiraids", concatenate(jiraIssueIdsSet.toArray(new String[jiraIssueIdsSet.size()]), ","));
        final Map<String, ResultSetContainer> results = dataAccessor.getData(request);
        // query result by contestIds
        final ResultSetContainer contestResultSetContainer = results.get("tc_direct_contest_invoice");
        // query result by paymentIds
        final ResultSetContainer paymentResultSetContainer = results.get("tc_direct_payment_invoice_v2");
        // query result by jiraIssueIds
        final ResultSetContainer jiraResultSetContainer = results.get("tc_direct_jira_invoice");

        Map<Long, InvoiceRecordBriefDTO> contestInvoiceMap = new HashMap<Long, InvoiceRecordBriefDTO>();
        Map<Long, InvoiceRecordBriefDTO> paymentInvoiceMap = new HashMap<Long, InvoiceRecordBriefDTO>();
        Map<String, InvoiceRecordBriefDTO> jiraInvoiceMap = new HashMap<String, InvoiceRecordBriefDTO>();
        for (int i = 0; i < contestResultSetContainer.size(); i++) {
            InvoiceRecordBriefDTO record = new InvoiceRecordBriefDTO();
            record.setBillingAccountId(contestResultSetContainer.getLongItem(i, "billing_account_id"));
            if (contestResultSetContainer.getItem(i, "contest_id").getResultData() != null) {
                record.setContestId(contestResultSetContainer.getLongItem(i, "contest_id"));
                contestInvoiceMap.put(record.getContestId(), record);
            }
        }
        for (int i = 0; i < jiraResultSetContainer.size(); i++) {
            InvoiceRecordBriefDTO record = new InvoiceRecordBriefDTO();
            record.setBillingAccountId(jiraResultSetContainer.getLongItem(i, "billing_account_id"));
            String jiraIssueId = jiraResultSetContainer.getStringItem(i, "jira_issue_id");
            record.setJiraIssueId(jiraIssueId);
            jiraInvoiceMap.put(jiraIssueId, record);
        }
        for (int i = 0; i < paymentResultSetContainer.size(); i++) {
            InvoiceRecordBriefDTO record = new InvoiceRecordBriefDTO();
            record.setBillingAccountId(paymentResultSetContainer.getLongItem(i, "billing_account_id"));
            if (paymentResultSetContainer.getItem(i, "contest_id").getResultData() != null) {
                record.setContestId(paymentResultSetContainer.getLongItem(i, "contest_id"));
            }
            if (paymentResultSetContainer.getItem(i, "cockpit_project_id").getResultData() != null) {
                record.setCockpitProjectId(paymentResultSetContainer.getLongItem(i, "cockpit_project_id"));
            }
            record.setInvoiceType(paymentResultSetContainer.getStringItem(i, "invoice_type"));
            long paymentId = paymentResultSetContainer.getLongItem(i, "payment_id");
            paymentInvoiceMap.put(paymentId, record);
        }

        List<InvoiceRecordBriefDTO> result = new ArrayList<InvoiceRecordBriefDTO>();
        for (int i = 0; i < contestIds.size(); i++) {
            if (!PaymentType.PLATFORM_FEE.getDescription().equalsIgnoreCase(invoiceTypeNames.get(i))) {
                if (paymentIds.get(i) > 0) {
                    result.add(paymentInvoiceMap.get(paymentIds.get(i)));
                } else if (jiraIssueIds != null && jiraIssueIds.get(i) != null && jiraIssueIds.get(i).length() > 0) {
                    result.add(jiraInvoiceMap.get(jiraIssueIds.get(i)));
                } else {
                    result.add(contestInvoiceMap.get(contestIds.get(i)));
                }
            } else {
                InvoiceRecordBriefDTO record = new InvoiceRecordBriefDTO();
                record.setBillingAccountId(0);
                record.setContestId(contestIds.get(i));
                record.setInvoiceType(PaymentType.PLATFORM_FEE.getDescription());
                result.add(record);
            }
        }
        return result;
    }

    /**
     * Get member photos with specify user id array.
     *
     * @param userIds the user id array.
     * @return retrieved member photos.
     * @throws Exception if any exception occurs.
     * @since 3.2
     */
    public static Map<Long, MemberPhotoDTO> getMemberPhotos(long[] userIds) throws  Exception {
        Map<Long, MemberPhotoDTO> memberPhotos = new HashMap<Long, MemberPhotoDTO>();

        if (userIds.length == 0) {
            return memberPhotos;
        }

        final String queryName = "coder_image_data_list";
        DataAccess dataAccess = new DataAccess(DBMS.JTS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("uids", concatenate(userIds, ","));

        final ResultSetContainer container = dataAccess.getData(request).get(queryName);
        for (ResultSetRow row : container) {
            MemberPhotoDTO photo = new MemberPhotoDTO();

            photo.setPhotoPath(new StringBuilder().append(row.getStringItem("image_path")).
                    append(row.getStringItem("file_name")).toString());
            photo.setId(row.getLongItem("coder_id"));

            memberPhotos.put(photo.getId(), photo);
        }

        return memberPhotos;
    }

    /**
     * Get copilot statistics.
     *
     * @return copilot statistics
     * @throws Exception if any exception occurs
     * @since 3.2
     */
    public static Map<Long, CopilotPoolMember> getCopilotStatistics() throws Exception {
        final String commandName = "copilot_pool_statistics";
        final String queryName = "copilot_pool_statistics";
        DataAccess dataAccess = new CachedDataAccess(MaxAge.THREE_HOUR, DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(commandName);

        Map<Long, CopilotPoolMember> members = new HashMap<Long, CopilotPoolMember>();

        final ResultSetContainer container = dataAccess.getData(request).get(queryName);
        for (ResultSetRow row : container) {
            CopilotPoolMember copilotPoolMember = new CopilotPoolMember();

            copilotPoolMember.setTotalContests(row.getIntItem("total_contests_number"));
            copilotPoolMember.setTotalProjects(row.getIntItem("total_projects_number"));
            copilotPoolMember.setTotalFailedContests(row.getIntItem("failed_contests_number"));
            copilotPoolMember.setTotalRepostedContests(row.getIntItem("reposted_contests_number"));
            copilotPoolMember.setCurrentContests(row.getIntItem("current_contests_number"));
            copilotPoolMember.setCurrentProjects(row.getIntItem("current_projects_number"));

            members.put(row.getLongItem("user_id"), copilotPoolMember);
        }

        return members;
    }

    /**
     * Gets all the copilot skill rules from the tcs_dw:user_achievement_rule
     *
     * @return a list of <code>CopilotSkillDTO</code>
     * @since 6.2
     */
    public static List<CopilotSkillDTO> getCopilotSkillRules() throws Exception {

        List<CopilotSkillDTO> skillRules = new ArrayList<CopilotSkillDTO>();

        String commandName = "copilot_skills";
        DataAccess dataAccess = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(commandName);

        Map<String, ResultSetContainer> data = dataAccess.getData(request);
        ResultSetContainer container = data.get(commandName);

        for (ResultSetRow row : container) {
            CopilotSkillDTO skillDTO = new CopilotSkillDTO();
            skillDTO.setRuleId(row.getLongItem("rule_id"));
            skillDTO.setName(row.getStringItem("name"));
            skillDTO.setDescription(row.getStringItem("description"));
            skillRules.add(skillDTO);
        }

        return skillRules;
    }

    /**
     * Sets the analysis stats for the copilots.
     *
     * @param copilots the copilots.
     * @param projectTypeToAnalysis the project type of the copilot posting to analysis
     * @throws Exception if there is any error.
     * @since 5.6
     */
    public static void setCopilotFullStatistics(List<CopilotStatDTO> copilots, long projectTypeToAnalysis) throws Exception {
        Map<Long, CopilotStatDTO> copilotsMap = new HashMap<Long, CopilotStatDTO>();
        long[] userIds = new long[copilots.size()];
        int index = 0;
        for(CopilotStatDTO c : copilots) {
            copilotsMap.put(c.getUserId(), c);
            userIds[index++] = c.getUserId();
        }

        if (copilots.size() == 0) {
            return;
        }

        String requestInput = concatenate(userIds, ",");

        String commandName = "copilots_info";
        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(commandName);
        request.setProperty("uids", requestInput);

        Map<String, ResultSetContainer> data = dataAccess.getData(request);
        ResultSetContainer container = data.get("copilots_info");

        for (ResultSetRow row : container) {
            long userId = row.getLongItem("user_id");
            CopilotStatDTO copilotDTO = copilotsMap.get(userId);
            copilotDTO.setImagePath(row.getItem("image_path").getResultData() == null ? null : row.getStringItem("image_path"));
            copilotDTO.setStudioCopilot(row.getBooleanItem("is_studio_copilot"));
            copilotDTO.setSoftwareCopilot(row.getBooleanItem("is_software_copilot"));
            copilotDTO.setCountry(row.getStringItem("country_name"));
            copilotDTO.setTimeZone(row.getStringItem("timezone"));
            copilotDTO.setCopilotProfileId(row.getLongItem("copilot_profile_id"));
            copilotDTO.setPositiveFeedbackNumber(row.getIntItem("positive_feedback"));
            copilotDTO.setNegativeFeedbackNumber(row.getIntItem("negative_feedback"));
        }

        container = data.get("copilots_experience");

        Map<Long, Map<Long, CopilotStatDTO.Experience>> copilotsExperiencesMap = new HashMap<Long, Map<Long, CopilotStatDTO.Experience>>();

        for (ResultSetRow row : container) {
            long typeId = 0;
            long categoryId = 0;
            String typeName = null;
            String categoryName = null;
            if(row.getItem("direct_project_type_id").getResultData() != null) {
                typeId = row.getLongItem("direct_project_type_id");
                typeName = row.getStringItem("direct_project_type_name");
            }
            if(row.getItem("direct_project_category_id").getResultData() != null) {
                categoryId = row.getLongItem("direct_project_category_id");
                categoryName = row.getStringItem("direct_project_category_name");
            }


            long userId = row.getLongItem("user_id");
            Map<Long, CopilotStatDTO.Experience> copilotExperiencesMap;
            if(copilotsExperiencesMap.containsKey(userId)) {
                copilotExperiencesMap = copilotsExperiencesMap.get(userId);
            } else {
                copilotExperiencesMap = new HashMap<Long, CopilotStatDTO.Experience>();
                copilotsExperiencesMap.put(userId, copilotExperiencesMap);
            }

            long typeCategoryIndex = typeId * 1000 + categoryId;
            CopilotStatDTO.Experience exp;
            if(copilotExperiencesMap.containsKey(typeCategoryIndex)) {
                exp = copilotExperiencesMap.get(typeCategoryIndex);
            } else {
                exp = new CopilotStatDTO.Experience();
                exp.setProjectType(typeName == null ? "Other" : typeName);
                exp.setProjectCategory(categoryName);
                exp.setProjectTypeId(typeId);
                exp.setProjectCategoryId(categoryId);
                copilotExperiencesMap.put(typeCategoryIndex, exp);
            }

            long projectStatusId = row.getLongItem("project_status_id");

            if(projectStatusId == 1L) {
                exp.setActiveProjectNumber(exp.getActiveProjectNumber() + 1);
            } else if(projectStatusId == 2L || projectStatusId == 4L) {
                exp.setCompletedProjectNumber(exp.getCompletedProjectNumber() + 1);
            }
        }

        for(CopilotStatDTO item : copilots) {
            Map<Long, CopilotStatDTO.Experience> experiences = copilotsExperiencesMap.get(item.getUserId());
            List<CopilotStatDTO.Experience> other = new ArrayList<CopilotStatDTO.Experience>();
            List<CopilotStatDTO.Experience> matched = new ArrayList<CopilotStatDTO.Experience>();
            if (experiences != null) {
                for(Long indexId : experiences.keySet()) {
                    if((indexId / 1000) == projectTypeToAnalysis && projectTypeToAnalysis > 0) {
                        // matched
                        matched.add(experiences.get(indexId));
                    } else {
                        other.add(experiences.get(indexId));
                    }
                }
            }

            item.setMatchedExperience(matched);
            item.setOtherExperience(other);
        }

        commandName = "copilots_statistics";

        dataAccess = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        request = new Request();
        request.setContentHandle(commandName);
        request.setProperty("uids", requestInput);

        data = dataAccess.getData(request);

        container = data.get("copilots_statistics");

        for (ResultSetRow row : container) {
            long userId = row.getLongItem("user_id");
            CopilotStatDTO copilotDTO = copilotsMap.get(userId);
            copilotDTO.setCurrentContests(row.getIntItem("current_contests_number"));
            copilotDTO.setCurrentProjects(row.getIntItem("current_projects_number"));
            copilotDTO.setFulfillment(row.getItem("fulfillment").getResultData() == null ? -1 : row.getDoubleItem("fulfillment"));
        }

        container = data.get("copilots_skills");

        for (ResultSetRow row : container) {
            long userId = row.getLongItem("user_id");
            CopilotStatDTO copilotDTO = copilotsMap.get(userId);
            long copilotSkillId = row.getLongItem("copilot_skill_id");
            copilotDTO.addCopilotSkill(copilotSkillId);
        }

    }

    /**
     * <p>
     * Sets the project actual duration and project actual cost into <code>ProjectGeneralInfoDTO</code>
     * </p>
     *
     * @param projectGeneralInfo the <code>ProjectGeneralInfoDTO</code> instance to set values into.
     * @throws Exception if any exception happens
     * @since 3.4
     */
    public static void setProjectGeneralInfo(ProjectGeneralInfoDTO projectGeneralInfo) throws Exception {
        long projectId = projectGeneralInfo.getProject().getProjectId();

        final String commandName = "direct_project_general_info";
        final String projectStartDateQuery = "direct_project_general_info_start_date";
        final String projectedEndDateQuery = "direct_project_general_info_projected_end_date";
        final String projectedCostQuery = "direct_project_general_info_projected_cost";

        // final String projectCostQuery = "direct_project_general_info_cost";
        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(commandName);
        request.setProperty("tcdirectid", String.valueOf(projectId));

        // get result container map
        final Map<String, ResultSetContainer> map = dataAccess.getData(request);

        // get start date
        final ResultSetContainer startDateContainer = map.get(projectStartDateQuery);

        Timestamp startDate = null;

        if (startDateContainer.size() > 0) {
            startDate = startDateContainer.getTimestampItem(0, "start_date");

            if (startDate != null) {
                // project end date
                Date endTime = new Date();

                // use the project completion date as project end date if the project is completed
                if(projectGeneralInfo.getProject().getProjectStatusId() == 4L) {
                    endTime = projectGeneralInfo.getProject().getCompletionDate();
                }

                long duration = endTime.getTime() - startDate.getTime();

                long days = 0;

                if (duration > 0) {
                    days = DirectUtils.daysBetween(startDate, endTime);
                }


                projectGeneralInfo.setActualDuration((int) days);
            } else {
                projectGeneralInfo.setActualDuration(0);
            }

        } else {
            projectGeneralInfo.setActualDuration(0);
        }

        // get projected end date
        final ResultSetContainer projectedEndContainer = map.get(projectedEndDateQuery);

        if (projectedEndContainer.size() > 0) {
            final Timestamp projectedEndDate = projectedEndContainer.getTimestampItem(0, "projected_end_date");

            if (projectedEndDate != null && startDate != null) {
                long duration = projectedEndDate.getTime() - startDate.getTime();
                long days = 0;

                if (duration > 0) {
                    days = DirectUtils.daysBetween(startDate, projectedEndDate);
                }

                projectGeneralInfo.setProjectedDuration((int) days);

            } else {
                projectGeneralInfo.setProjectedDuration(0);
            }
        } else {
            projectGeneralInfo.setProjectedDuration(0);
        }


        // get projected cost
        final ResultSetContainer costContainer = map.get(projectedCostQuery);

        if(costContainer.size() > 0) {

            if (costContainer.getItem(0, "projected_cost").getResultData() != null) {
                final float cost = costContainer.getFloatItem(0, "projected_cost");

                projectGeneralInfo.setProjectedCost(Math.round(cost + projectGeneralInfo.getActualCost()));
            } else {
                projectGeneralInfo.setProjectedCost(projectGeneralInfo.getActualCost());
            }
        } else {
            projectGeneralInfo.setProjectedCost(projectGeneralInfo.getActualCost());
        }

    }

    /**
     * Gets the direct project ids filtered out by the enterprise dashboard filter.
     *
     * @param filterForm the filter form.
     * @return an array of filtered direct project ids.
     * @throws Exception if an error occurs.
     * @since 4.3
     */
    public static long[] getEnterpriseDashboardFilteredProjectIds(EnterpriseDashboardFilterForm filterForm)
            throws Exception {

        final Map<Long, String> projects = getEnterpriseDashboardFilteredProjects(filterForm);

        long[] result = new long[projects.size()];
        int count = 0;
        for(Long v : projects.keySet()) {
            result[count++] = v;
        }

        return result;
    }

    /**
     * Gets the direct projects filtered out by the enterprise dashboard filter. The direct projects are stored in a map.
     * The key is the direct project id, the value is the direct project name.
     *
     * @param filterForm the filter form
     * @return a map of direct projects
     * @throws Exception if any error occurs
     * @since 4.7
     */
    public static Map<Long, String> getEnterpriseDashboardFilteredProjects(EnterpriseDashboardFilterForm filterForm) throws Exception {

        long[] projectIds;

        if(filterForm.getDirectProjectId() != 0) {
            projectIds = new long[] {filterForm.getDirectProjectId()};
        } else {
            // get the set of the projects for the client
            Map<Long, String> projects = DirectUtils.getProjectsForClient(DirectUtils.getTCSubjectFromSession(), filterForm.getClientId());

            if (projects == null || projects.size() == 0) {
                return new HashMap<Long, String>();
            }

            projectIds = new long[projects.size()];
            int count = 0;
            for (Long id : projects.keySet()) {
                projectIds[count++] = id;
            }
        }

        return getFilteredProjectsResult(filterForm.getPageSize(), filterForm.getPageNumber(),
                filterForm.getProjectStatusId(), projectIds, filterForm.getProjectFilterId(), filterForm.getProjectFilterValue());
    }


    /**
     * Gets the filtered projects for the Enterprise Dashboard Filter panel
     *
     * @param pageSize the paging size
     * @param pageNumber the paging number
     * @param statusId the status id.
     * @param projectIds an array of project ids
     * @param projectFilterId  the id of the project filter
     * @param projectFilterValue  the value of project filter applied
     * @return the filtered projects storing in a map, the key is the project id, the value is the project name.
     * @throws Exception if there is any error
     * @since 6.6
     */
    public static Map<Long, String> getFilteredProjectsResult(int pageSize, int pageNumber, long statusId,
                                                               long[] projectIds, long projectFilterId,
                                                               String projectFilterValue) throws Exception {
        String tcDirectProjectIds = concatenate(projectIds, ", ");

        // page size should start from 0
        String pagination = "SKIP " + pageSize * pageNumber + " FIRST " + pageSize;

        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        String query = "enterprise_dashboard_filter_projects";
        Request request = new Request();
        request.setContentHandle(query);
        request.setProperty("pagination", pagination);
        request.setProperty("directProjectStatusId", String.valueOf(statusId));
        request.setProperty("tcdirectids", tcDirectProjectIds);
        request.setProperty("metakeyid", String.valueOf(projectFilterId));
        request.setProperty("metavalue", projectFilterValue);

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get(query);
        final int recordNum = resultContainer.size();
        Map<Long, String> result = new HashMap<Long, String>();
        for (int i = 0; i < recordNum; i++) {
            long tcDirectProjectId = resultContainer.getLongItem(i, "project_id");
            String tcDirectProjectname = resultContainer.getStringItem(i, "project_name");
            result.put(tcDirectProjectId, tcDirectProjectname);
        }

        return result;
    }

    /**
     * Gets the data for the enterprise dashboard projects widget.
     *
     * @param filterForm the filter form
     * @return a list of <code>EnterpriseDashboardProjectsWidgetDTO</code>
     * @throws Exception if there is any error.
     * @since 6.3
     */
    public static List<EnterpriseDashboardProjectsWidgetDTO>
            getEnterpriseDashboardProjectsWidgetData(EnterpriseDashboardFilterForm filterForm) throws Exception {
        long[] projectIds = getEnterpriseDashboardFilteredProjectIds(filterForm);

        List<EnterpriseDashboardProjectsWidgetDTO> result = new ArrayList<EnterpriseDashboardProjectsWidgetDTO>();

        if (projectIds == null || projectIds.length == 0) {
            return result;
        }

        String filteredProjectIds = concatenate(projectIds, ", ");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        String query = "enterprise_dashboard_projects";
        Request request = new Request();
        request.setContentHandle(query);
        request.setProperty("tcdirectids", filteredProjectIds);

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get(query);
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            EnterpriseDashboardProjectsWidgetDTO item = new EnterpriseDashboardProjectsWidgetDTO();
            item.setDirectProjectId(resultContainer.getLongItem(i, "project_id"));
            item.setDirectProjectName(resultContainer.getStringItem(i, "name"));
            item.setActiveContestsNumber(resultContainer.getIntItem(i, "active_contests_number"));
            if (resultContainer.getItem(i, "milestone_name").getResultData() != null) {
                item.setMilestoneDueDate(resultContainer.getTimestampItem(i, "min_milestone_due_date"));
                item.setMilestoneName(resultContainer.getStringItem(i, "milestone_name"));
            }

            result.add(item);
        }

        return result;
    }

    /**
     * Gets the enterprise dashboad total spend data.
     *
     * @param filterForm the <code>EnterpriseDashboardFilterForm</code> instance
     * @return a list of <code>EnterpriseDashboardTotalSpendDTO</code>
     * @throws Exception if any error occurs.
     * @since 4.3
     */
    public static List<EnterpriseDashboardTotalSpendDTO> getEnterpriseDashboardTotalSpend(
            EnterpriseDashboardFilterForm filterForm) throws Exception {
        long[] projectIds = getEnterpriseDashboardFilteredProjectIds(filterForm);

        if(projectIds == null || projectIds.length == 0) {
            return new ArrayList<EnterpriseDashboardTotalSpendDTO>();
        }

        String filteredProjectIds = concatenate(projectIds, ", ");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        String query = "enterprise_dashboard_total_spend_v3";
        Request request = new Request();
        request.setContentHandle(query);
        request.setProperty("tcdirectids", filteredProjectIds);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM");
        DateFormat sourceDateFormatter = new SimpleDateFormat("MMM''yy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDateFormatter.parse(filterForm.getStartMonth()));
        calendar.add(Calendar.MONTH, -2);
        request.setProperty("sdt", dateFormatter.format(calendar.getTime()));
        request.setProperty("edt", dateFormatter.format(sourceDateFormatter.parse(filterForm.getEndMonth())));

        long startMonthCount = calendar.get(Calendar.YEAR)  * 12 + calendar.get(Calendar.MONTH);

        calendar.setTime(sourceDateFormatter.parse(filterForm.getEndMonth()));

        long endMonthCount = calendar.get(Calendar.YEAR)  * 12 + calendar.get(Calendar.MONTH);

        Map<Long, EnterpriseDashboardTotalSpendDTO> resultMap = new LinkedHashMap<Long, EnterpriseDashboardTotalSpendDTO>();
        for(long m = startMonthCount; m <= endMonthCount; m++) {
            EnterpriseDashboardTotalSpendDTO item = new EnterpriseDashboardTotalSpendDTO();
            int year = (int) m / 12;
            int month = (int) m % 12;
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            item.setDate(calendar.getTime());
            resultMap.put(m, item);
        }

        ResultSetContainer resultContainer = dataAccessor.getData(request).get(query);
        int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long monthCount = resultContainer.getLongItem(i, "monthcount");
            double monthCost = resultContainer.getDoubleItem(i, "monthcost");
            double monthContestFee = resultContainer.getDoubleItem(i, "monthcontestfee");
            EnterpriseDashboardTotalSpendDTO item = resultMap.get((monthCount/100) * 12 + (monthCount % 100) -1);
            item.setMemberCostSum(item.getMemberCostSum() + monthCost);
            item.setContestFeeSum(item.getContestFeeSum() + monthContestFee);
        }

        query = "enterprise_dashboard_total_spend_projected";
        resultContainer = dataAccessor.getData(request).get(query);
        recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long monthCount = resultContainer.getLongItem(i, "monthcount");
            double projectedMemberCost = resultContainer.getDoubleItem(i, "projected_member_cost");
            double projectedContestFee = resultContainer.getDoubleItem(i, "projected_contest_fee");
            EnterpriseDashboardTotalSpendDTO item = resultMap.get((monthCount/100) * 12 + (monthCount % 100) -1);
            item.setProjectedMemberCostSum(item.getProjectedMemberCostSum() + projectedMemberCost);
            item.setProjectedContestFeeSum(item.getProjectedContestFeeSum() + projectedContestFee);
        }

        List<EnterpriseDashboardTotalSpendDTO> result = new ArrayList<EnterpriseDashboardTotalSpendDTO>(resultMap.values());

        for(int i = 2; i < result.size(); ++i) {
            long average = (result.get(i).getTotalSpend() + result.get(i - 1).getTotalSpend() + result.get(i - 2).getTotalSpend()) / 3;
            result.get(i).setAverageSpend(average);
        }

        return result.subList(2, result.size());
    }

    /**
     * Gets the data for the total spend drill-in for enterprise dashboard.
     *
     * @param filterForm the enterprise dashboard filter form
     * @param  singleMonth whether getting data for single month
     * @return a list of <code>TotalSpendDrillInDTO</code>
     * @throws Exception if there is any error
     * @since 6.4
     */
    public static List<TotalSpendDrillInDTO> getEnterpriseDashboardTotalSpendDrillIn(
            EnterpriseDashboardFilterForm filterForm, boolean singleMonth) throws Exception {
        long[] projectIds = getEnterpriseDashboardFilteredProjectIds(filterForm);

        if(projectIds == null || projectIds.length == 0) {
            return new ArrayList<TotalSpendDrillInDTO>();
        }

        if(singleMonth && !filterForm.getStartMonth().equals(filterForm.getEndMonth())) {
            throw new IllegalArgumentException("Drill-in can only check one month at a time");
        }

        String filteredProjectIds = concatenate(projectIds, ", ");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        String query = "enterprise_dashboard_total_spend_v3";
        Request request = new Request();
        request.setContentHandle(query);
        request.setProperty("tcdirectids", filteredProjectIds);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM");
        DateFormat sourceDateFormatter = new SimpleDateFormat("MMM''yy");
        request.setProperty("sdt", dateFormatter.format(sourceDateFormatter.parse(filterForm.getStartMonth())));
        request.setProperty("edt", dateFormatter.format(sourceDateFormatter.parse(filterForm.getEndMonth())));

        List<TotalSpendDrillInDTO> result = new ArrayList<TotalSpendDrillInDTO>();
        ResultSetContainer resultContainer = dataAccessor.getData(request).get(query);
        int recordNum = resultContainer.size();
        Calendar calendar = Calendar.getInstance();

        // the first level key is month count, the second key is direct project id
        Map<Long, Map<Long, TotalSpendDrillInDTO>> cache = new HashMap<Long, Map<Long, TotalSpendDrillInDTO>>();

        for (int i = 0; i < recordNum; i++) {
            TotalSpendDrillInDTO item = new TotalSpendDrillInDTO();
            long monthCount = resultContainer.getLongItem(i, "monthcount");

            Map<Long, TotalSpendDrillInDTO> projectsCache;

            if(cache.get(monthCount) == null) {
                cache.put(monthCount, new HashMap<Long, TotalSpendDrillInDTO>());
            }

            projectsCache = cache.get(monthCount);

            calendar.set(Calendar.YEAR, (int) monthCount/100);
            calendar.set(Calendar.MONTH, (int) (monthCount % 100) - 1);
            item.setYearMonthLabel(dateFormatter.format(calendar.getTime()));
            double monthCost = resultContainer.getDoubleItem(i, "monthcost");
            double monthContestFee = resultContainer.getDoubleItem(i, "monthcontestfee");
            long directProjectId = resultContainer.getLongItem(i, "direct_project_id");
            String directProjectName = resultContainer.getStringItem(i, "direct_project_name");
            item.setDirectProjectId(directProjectId);
            item.setDirectProjectName(directProjectName);
            item.setMemberCostSum(monthCost);
            item.setContestFeeSum(monthContestFee);
            result.add(item);

            projectsCache.put(directProjectId, item);
        }


        resultContainer = dataAccessor.getData(request).get("enterprise_dashboard_total_spend_projected");
        recordNum = resultContainer.size();

        for (int i = 0; i < recordNum; i++) {
            TotalSpendDrillInDTO item = null;
            long monthCount = resultContainer.getLongItem(i, "monthcount");
            long directProjectId = resultContainer.getLongItem(i, "direct_project_id");

            if (cache.get(monthCount) != null && cache.get(monthCount).containsKey(directProjectId)) {
                item = cache.get(monthCount).get(directProjectId);
            }

            if(item == null) {
                // no existing record, create new one
                item = new TotalSpendDrillInDTO();
                calendar.set(Calendar.YEAR, (int) monthCount/100);
                calendar.set(Calendar.MONTH, (int) (monthCount % 100) - 1);
                item.setYearMonthLabel(dateFormatter.format(calendar.getTime()));
                String directProjectName = resultContainer.getStringItem(i, "direct_project_name");
                item.setDirectProjectId(directProjectId);
                item.setDirectProjectName(directProjectName);
                result.add(item);
            }

            double projectedMonthCost = resultContainer.getDoubleItem(i, "projected_member_cost");
            double projectedMonthContestFee = resultContainer.getDoubleItem(i, "projected_contest_fee");

            item.setProjectedContestFeeSum(projectedMonthContestFee);
            item.setProjectedMemberCostSum(projectedMonthCost);
        }

        return result;
    }

    /**
     * Gets the contests pipeline data for new enterprise dashboard, each <code>EnterpriseDashboardMonthPipelineDTO</code> represents
     * the pipeline stats dat for one month in the filtered range.
     *
     * @param filterForm the <code>EnterpriseDashboardFilterForm</code> instance which contains all the filter data
     * @return a list of <code>EnterpriseDashboardMonthPipelineDTO</code>
     * @throws Exception if any error
     * @since 4.5
     */
    public static List<EnterpriseDashboardMonthPipelineDTO> getEnterpriseDashboardContestsPipeline(EnterpriseDashboardFilterForm filterForm) throws Exception {
        long[] projectIds = getEnterpriseDashboardFilteredProjectIds(filterForm);

        if(projectIds == null || projectIds.length == 0) {
            return new ArrayList<EnterpriseDashboardMonthPipelineDTO>();
        }

        String filteredProjectIds = concatenate(projectIds, ", ");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        String query = "enterprise_dashboard_contests_pipeline";
        Request request = new Request();
        request.setContentHandle(query);
        request.setProperty("tcdirectids", filteredProjectIds);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM");
        DateFormat sourceDataFormatter = new SimpleDateFormat("MMM''yy");
        Date startDate = sourceDataFormatter.parse(filterForm.getStartMonth());
        Date endDate = sourceDataFormatter.parse(filterForm.getEndMonth());
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long startMonthCount = calendar.get(Calendar.YEAR)  * 12 + calendar.get(Calendar.MONTH);

        calendar.setTime(endDate);

        long endMonthCount = calendar.get(Calendar.YEAR)  * 12 + calendar.get(Calendar.MONTH);

        Map<Long, EnterpriseDashboardMonthPipelineDTO> resultMap = new LinkedHashMap<Long, EnterpriseDashboardMonthPipelineDTO>();
        for(long m = startMonthCount; m <= endMonthCount; m++) {
            EnterpriseDashboardMonthPipelineDTO item = new EnterpriseDashboardMonthPipelineDTO();
            int year = (int) m / 12;
            int month = (int) m % 12;
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            item.setDate(calendar.getTime());
            resultMap.put(m, item);
        }

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get(query);
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            String currentPhase = resultContainer.getStringItem(i, "current_phase");
            String status = resultContainer.getStringItem(i, "sname");
            String newStatus = resultContainer.getStringItem(i, "newstatus");
            String phases = resultContainer.getStringItem(i, "phases");

            long monthCount = resultContainer.getLongItem(i, "monthcount");
            EnterpriseDashboardMonthPipelineDTO item = resultMap.get((monthCount/100) * 12 + (monthCount % 100) -1);

            if (currentPhase != null && ((String)status).equalsIgnoreCase(ProjectStatus.ACTIVE.getName())) {
                // active
                item.setTotalActiveContests(item.getTotalActiveContests() + 1);
            } else if (newStatus != null && ((String)status).equalsIgnoreCase(ProjectStatus.ACTIVE.getName())) {
                // all phases are done, then it is completed
                if (phases != null && ((String)phases).trim().equalsIgnoreCase("Completed"))
                {
                    item.setTotalCompletedContests(item.getTotalCompletedContests() + 1);
                }
                else if (phases != null && ((String)phases).trim().equalsIgnoreCase("Active"))
                {
                    item.setTotalActiveContests(item.getTotalActiveContests() + 1);
                }
                else
                {
                    if(newStatus.trim().equalsIgnoreCase("Draft")) {
                        item.setTotalDraftContests(item.getTotalDraftContests() + 1);
                    } else {
                        item.setTotalScheduledContests(item.getTotalScheduledContests() + 1);
                    }
                }
            } else if(!((String)status).equalsIgnoreCase(ProjectStatus.ACTIVE.getName())) {

                if (((String)status).equalsIgnoreCase(ProjectStatus.CANCELLED_CLIENT_REQUEST.getName())
                        || ((String)status).equalsIgnoreCase(ProjectStatus.CANCELLED_REQUIREMENTS_INFEASIBLE.getName()))
                {
                    item.setTotalFailedContests(item.getTotalFailedContests() + 1);
                }
                else if (((String)status).equalsIgnoreCase(ProjectStatus.DRAFT.getName()))
                {
                    item.setTotalDraftContests(item.getTotalDraftContests() + 1);
                }
                else
                {
                    item.setTotalCompletedContests(item.getTotalCompletedContests() + 1);
                }
            }


        }

        List<EnterpriseDashboardMonthPipelineDTO> result = new ArrayList<EnterpriseDashboardMonthPipelineDTO>(resultMap.values());

        return result;
    }


    /**
     * Gets the drill-in data for contests pipeline of enterprise dashboard.
     *
     * @param filterForm the enterprise dashboard filter form.
     * @param singleMonth whether getting data for single month
     * @return a list of <code>ContestPipelineDrillInDTO</code>
     * @throws Exception if there is any error.
     * @since 6.4
     */
    public static List<ContestPipelineDrillInDTO>
            getEnterpriseDashboardContestsPipelineDrillIn(EnterpriseDashboardFilterForm filterForm, boolean singleMonth) throws Exception {
        long[] projectIds = getEnterpriseDashboardFilteredProjectIds(filterForm);

        if(projectIds == null || projectIds.length == 0) {
            return new ArrayList<ContestPipelineDrillInDTO>();
        }

        if(singleMonth && !filterForm.getStartMonth().equals(filterForm.getEndMonth())) {
            throw new IllegalArgumentException("Drill-in can only check one month at a time");
        }

        String filteredProjectIds = concatenate(projectIds, ", ");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        String query = "enterprise_dashboard_contests_pipeline";
        Request request = new Request();
        request.setContentHandle(query);
        request.setProperty("tcdirectids", filteredProjectIds);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM");
        DateFormat sourceDataFormatter = new SimpleDateFormat("MMM''yy");
        Date startDate = sourceDataFormatter.parse(filterForm.getStartMonth());
        Date endDate = sourceDataFormatter.parse(filterForm.getEndMonth());
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));

        List<ContestPipelineDrillInDTO> result = new ArrayList<ContestPipelineDrillInDTO>();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get(query);
        final int recordNum = resultContainer.size();
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < recordNum; i++) {
            ContestPipelineDrillInDTO item = new ContestPipelineDrillInDTO();
            long monthCount = resultContainer.getLongItem(i, "monthcount");
            calendar.set(Calendar.YEAR, (int) monthCount/100);
            calendar.set(Calendar.MONTH, (int) (monthCount % 100) - 1);
            item.setYearMonthLabel(dateFormatter.format(calendar.getTime()));
            String currentPhase = resultContainer.getStringItem(i, "current_phase");
            String status = resultContainer.getStringItem(i, "sname");
            String newStatus = resultContainer.getStringItem(i, "newstatus");
            String phases = resultContainer.getStringItem(i, "phases");

            item.setContestId(resultContainer.getLongItem(i, "contest_id"));
            item.setContestName(resultContainer.getStringItem(i, "contest_name"));
            item.setDirectProjectId(resultContainer.getLongItem(i, "direct_project_id"));
            item.setDirectProjectName(resultContainer.getStringItem(i, "direct_project_name"));
            item.setStartDate(resultContainer.getTimestampItem(i, "start_date"));

            if(resultContainer.getItem(i, "copilot").getResultData() != null) {
                item.setCopilotHandle(resultContainer.getStringItem(i, "copilot"));
            }


            if (currentPhase != null && ((String)status).equalsIgnoreCase(ProjectStatus.ACTIVE.getName())) {
                // active
                item.setContestStatus("Active");
            } else if (newStatus != null && ((String)status).equalsIgnoreCase(ProjectStatus.ACTIVE.getName())) {
                // all phases are done, then it is completed
                if (phases != null && ((String)phases).trim().equalsIgnoreCase("Completed"))
                {
                    item.setContestStatus("Finsihed");
                }
                else if (phases != null && ((String)phases).trim().equalsIgnoreCase("Active"))
                {
                    item.setContestStatus("Active");
                }
                else
                {
                    if(newStatus.trim().equalsIgnoreCase("Draft")) {
                        item.setContestStatus("Draft");
                    } else {
                        item.setContestStatus("Scheduled");
                    }
                }
            } else if(!((String)status).equalsIgnoreCase(ProjectStatus.ACTIVE.getName())) {

                if (((String)status).equalsIgnoreCase(ProjectStatus.CANCELLED_CLIENT_REQUEST.getName())
                        || ((String)status).equalsIgnoreCase(ProjectStatus.CANCELLED_REQUIREMENTS_INFEASIBLE.getName()))
                {
                    item.setContestStatus("Failed");
                }
                else if (((String)status).equalsIgnoreCase(ProjectStatus.DRAFT.getName()))
                {
                    item.setContestStatus("Draft");
                }
                else
                {
                    item.setContestStatus("Finished");
                }
            }

            if (item.getContestStatus().equals("Finished")) {
                item.setEndDate(resultContainer.getTimestampItem(i, "end_date"));
            }

            result.add(item);
        }

        return result;
    }

    /**
     * Gets the projects pipeline data for new enterprise dashboard, each
     * <code>EnterpriseDashboardMonthProjectPipelineDTO</code> represents
     * the pipeline stats dat for one month in the filtered range.
     *
     * @param filterForm the <code>EnterpriseDashboardFilterForm</code> instance which contains all the filter data
     * @return a list of <code>EnterpriseDashboardMonthProjectPipelineDTO</code>
     * @throws Exception if any error
     * @since 6.1
     */
    public static List<EnterpriseDashboardMonthProjectPipelineDTO>
            getEnterpriseDashboardProjectsPipeline(EnterpriseDashboardFilterForm filterForm) throws Exception {
        long[] projectIds = getEnterpriseDashboardFilteredProjectIds(filterForm);

        if(projectIds == null || projectIds.length == 0) {
            return new ArrayList<EnterpriseDashboardMonthProjectPipelineDTO>();
        }

        String filteredProjectIds = concatenate(projectIds, ", ");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        String query = "enterprise_dashboard_projects_pipeline";
        Request request = new Request();
        request.setContentHandle(query);
        request.setProperty("tcdirectids", filteredProjectIds);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM");
        DateFormat sourceDataFormatter = new SimpleDateFormat("MMM''yy");
        Date startDate = sourceDataFormatter.parse(filterForm.getStartMonth());
        Date endDate = sourceDataFormatter.parse(filterForm.getEndMonth());
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long startMonthCount = calendar.get(Calendar.YEAR)  * 12 + calendar.get(Calendar.MONTH);

        calendar.setTime(endDate);

        long endMonthCount = calendar.get(Calendar.YEAR)  * 12 + calendar.get(Calendar.MONTH);

        Map<Long, EnterpriseDashboardMonthProjectPipelineDTO> resultMap =
                new LinkedHashMap<Long, EnterpriseDashboardMonthProjectPipelineDTO>();
        for(long m = startMonthCount; m <= endMonthCount; m++) {
            EnterpriseDashboardMonthProjectPipelineDTO item = new EnterpriseDashboardMonthProjectPipelineDTO();
            int year = (int) m / 12;
            int month = (int) m % 12;
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            item.setDate(calendar.getTime());
            resultMap.put(m, item);
        }

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get(query);
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            long projectStatusId = resultContainer.getLongItem(i, "project_status_id");
            long monthCount = resultContainer.getLongItem(i, "monthcount");

            EnterpriseDashboardMonthProjectPipelineDTO item =
                    resultMap.get((monthCount/100) * 12 + (monthCount % 100) -1);

            if (projectStatusId == 4L) {
                item.setTotalCompletedProjects(item.getTotalCompletedProjects() + 1);
            } else {
                item.setTotalStartedProjects(item.getTotalStartedProjects() + 1);
            }

        }

        List<EnterpriseDashboardMonthProjectPipelineDTO> result =
                new ArrayList<EnterpriseDashboardMonthProjectPipelineDTO>(resultMap.values());

        return result;
    }

    /**
     * Gets the drill-in data for projects pipeline of enterprise dashboard.
     *
     *
     * @param filterForm the enterprise dashboard filter form
     * @param  singleMonth whether getting data for single month
     * @return a list of <code>ProjectPipelineDrillInDTO</code>
     * @throws Exception if there is any error.
     * @since 6.4
     */
    public static List<ProjectPipelineDrillInDTO>
        getEnterpriseDashboardProjectsPipelineDrillIn(EnterpriseDashboardFilterForm filterForm, boolean singleMonth) throws Exception {
        long[] projectIds = getEnterpriseDashboardFilteredProjectIds(filterForm);

        if (projectIds == null || projectIds.length == 0) {
            return new ArrayList<ProjectPipelineDrillInDTO>();
        }

        if(singleMonth && !filterForm.getStartMonth().equals(filterForm.getEndMonth())) {
            throw new IllegalArgumentException("Drill-in can only check one month at a time");
        }

        String filteredProjectIds = concatenate(projectIds, ", ");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        String query = "enterprise_dashboard_projects_pipeline";
        Request request = new Request();
        request.setContentHandle(query);
        request.setProperty("tcdirectids", filteredProjectIds);
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM");
        DateFormat sourceDataFormatter = new SimpleDateFormat("MMM''yy");
        Date startDate = sourceDataFormatter.parse(filterForm.getStartMonth());
        Date endDate = sourceDataFormatter.parse(filterForm.getEndMonth());
        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));

        List<ProjectPipelineDrillInDTO> result = new ArrayList<ProjectPipelineDrillInDTO>();
        Calendar calendar = Calendar.getInstance();

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get(query);
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            ProjectPipelineDrillInDTO item = new ProjectPipelineDrillInDTO();
            long monthCount = resultContainer.getLongItem(i, "monthcount");
            calendar.set(Calendar.YEAR, (int) monthCount/100);
            calendar.set(Calendar.MONTH, (int) (monthCount % 100) - 1);
            item.setYearMonthLabel(dateFormatter.format(calendar.getTime()));
            item.setDirectProjectId(resultContainer.getLongItem(i, "project_id"));
            item.setDirectProjectName(resultContainer.getStringItem(i, "name"));
            item.setProjectStartDate(resultContainer.getTimestampItem(i, "create_date"));
            long projectStatusId = resultContainer.getLongItem(i, "project_status_id");
            item.setDirectProjectStatus(ProjectStatusType.getProjectStatusType(projectStatusId).getProjectStatusName());
            if (resultContainer.getItem(i, "completion_date").getResultData() != null) {
                item.setProjectCompletionDate(resultContainer.getTimestampItem(i, "completion_date"));
            }

            result.add(item);
        }

        return result;
    }

    /**
     * Gets the enterprise dashboard financial section data.
     *
     * @param form the <code>EnterpriseDashboardProjectFinancialDTO</code> instance
     * @return a list of <code>EnterpriseDashboardTotalSpendDTO</code>
     * @throws Exception if any error occurs
     * @since 4.3
     */
    public static List<EnterpriseDashboardProjectFinancialDTO> getEnterpriseDashboardProjectsFinancialInfo(
            EnterpriseDashboardFilterForm form) throws Exception {
        long[] projectIds = getEnterpriseDashboardFilteredProjectIds(form);
        List<EnterpriseDashboardProjectFinancialDTO> result = new ArrayList<EnterpriseDashboardProjectFinancialDTO>();

        if(projectIds == null || projectIds.length == 0) {
            // return empty result
            return result;
        }

        String filteredProjectIds = concatenate(projectIds, ", ");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        String query = "enterprise_dashboard_projects_financial";
        Request request = new Request();
        request.setContentHandle(query);
        request.setProperty("tcdirectids", filteredProjectIds);

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get(query);
        final int recordNum = resultContainer.size();
        for (int i = 0; i < recordNum; i++) {
            EnterpriseDashboardProjectFinancialDTO item = new EnterpriseDashboardProjectFinancialDTO();
            item.setProjectId(resultContainer.getLongItem(i, "tc_direct_project_id"));
            item.setProjectName(resultContainer.getStringItem(i, "tc_direct_project_name"));
            item.setBudget(resultContainer.getLongItem(i,"tc_direct_project_budget"));
            item.setActualCost(Math.round(resultContainer.getDoubleItem(i, "total_cost")));
            item.setPlannedCost(Math.round(resultContainer.getDoubleItem(i, "total_planned_cost")));
            result.add(item);
        }

        return result;
    }

    /**
     * Gets the contest participation metrics report with the given paramters. The method will retrieve the contest
     * copilots data into parameter <code>contestCopilots</code>, and retrieve the contest participation details data
     * into parameter contestDetails.
     *
     * @param currentUser            the current user.
     * @param projectId              the direct project id.
     * @param projectCategoryIds     the project category ids.
     * @param clientId               the client id.
     * @param billingAccountId       the billing accounts id.
     * @param projectStatus          the allowed project status.
     * @param startDate              the start date.
     * @param endDate                the end date.
     * @param basicMetrics           the basic metrics.
     * @param billingAggregation     the billing aggregation.
     * @param projectAggregation     the project aggregation.
     * @param contestTypeAggregation the contest type aggregation.
     * @param statusAggregation      the status aggregation.
     * @param contestAggregation     the contest aggregation.
     * @since 2.8.0
     */
    public static void getDashboardParticipationReport(TCSubject currentUser, long projectId, long[] projectCategoryIds,
                                                       long clientId, long billingAccountId, String[] projectStatus,
                                                       Date startDate, Date endDate,
                                                       ParticipationBasicReportDTO basicMetrics,
                                                       List<ParticipationAggregationReportDTO> billingAggregation,
                                                       List<ParticipationAggregationReportDTO> projectAggregation,
                                                       List<ParticipationAggregationReportDTO> contestTypeAggregation,
                                                       List<ParticipationAggregationReportDTO> statusAggregation,
                                                       List<ParticipationAggregationReportDTO> contestAggregation)
        throws Exception {

        if (projectCategoryIds == null || projectCategoryIds.length == 0) {
            return;
        }
        if (projectStatus == null || (projectStatus.length == 0)) {
            return;
        }

        // concatenate the filters
        String projectCategoryIDsList = concatenate(projectCategoryIds, ", ");
        String projectStatusesList = concatenate(projectStatus, ", ");

        // date format to prepare date for query input
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();

        if (!setReportQueryParameters(request, currentUser, clientId, billingAccountId, projectId)) {
            return;
        }

        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);
        request.setProperty("statuses", projectStatusesList);
        request.setContentHandle("dashboard_participation_metrics_report");
        final Map<String, ResultSetContainer> queryData = dataAccessor.getData(request);

        // Basic metrics
        final ResultSetContainer basicMetricsResult = queryData.get("dashboard_participation_basic_metrics");
        ResultSetRow basicMetricsResultRow = basicMetricsResult.get(0);
        basicMetrics.setTotalCopilots(basicMetricsResultRow.getIntItem("total_copilots_count"));
        basicMetrics.setTotalContests(basicMetricsResultRow.getIntItem("total_contests_count"));
        basicMetrics.setTotalProjects(basicMetricsResultRow.getIntItem("total_projects_count"));

        // Aggregation by billing account
        getParticipationReportAggregatedData(billingAggregation, queryData,
                                             "dashboard_participation_stats_aggregation_billing");

        // Aggregation by projects
        getParticipationReportAggregatedData(projectAggregation, queryData,
                                             "dashboard_participation_stats_aggregation_project");

        // Aggregation by contest types
        getParticipationReportAggregatedData(contestTypeAggregation, queryData,
                                             "dashboard_participation_stats_aggregation_type");

        // Aggregation by statuses
        getParticipationReportAggregatedData(statusAggregation, queryData,
                                             "dashboard_participation_stats_aggregation_status");

        // Aggregation by contest
        getParticipationReportAggregatedData(contestAggregation, queryData,
                                             "dashboard_participation_stats_aggregation_contest");
    }



    /**
     * Gets the contest participation metrics excel data with the given parameters.
     *
     * @param currentUser        the current user.
     * @param projectId          the direct project id.
     * @param projectCategoryIds project category ids.
     * @param clientId           the client id.
     * @param billingAccountId   the billing accounts id.
     * @param projectStatus      the allowed project status.
     * @param startDate          the start date.
     * @param endDate            the end date.
     * @param viewType           the view type
     * @return the excel data
     * @throws Exception if any error occurs.
     * @since 5.1
     */
    public static List<ParticipationAggregationReportDTO> getDashboardParticipationExcelData(
        TCSubject currentUser, long projectId, long[] projectCategoryIds, long clientId, long billingAccountId,
        String[] projectStatus, Date startDate, Date endDate,String viewType) throws Exception {

        if (viewType == null || viewType.length() == 0) {
            throw new IllegalArgumentException("The parameter [viewType] is NULL or empty");
        }

        if (projectCategoryIds == null || projectCategoryIds.length == 0) {
            throw new IllegalArgumentException("The parameter [projectCategoryIds] is NULL or empty");
        }
        if (projectStatus == null || (projectStatus.length == 0)) {
            throw new IllegalArgumentException("The parameter [projectStatus] is NULL or empty");
        }

        String queryName = "dashboard_participation_stats_aggregation_" + viewType.toString().toLowerCase();

        // concatenate the filters
        String projectCategoryIDsList = concatenate(projectCategoryIds, ", ");
        String projectStatusesList = concatenate(projectStatus, ", ");

        // date format to prepare date for query input
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();

        if (!setReportQueryParameters(request, currentUser, clientId, billingAccountId, projectId)) {
            throw new IllegalArgumentException("Query parameter missing");
        }

        request.setProperty("sdt", dateFormatter.format(startDate));
        request.setProperty("edt", dateFormatter.format(endDate));
        request.setProperty("pcids", projectCategoryIDsList);
        request.setProperty("statuses", projectStatusesList);
        request.setContentHandle("dashboard_participation_metrics_report");
        final Map<String, ResultSetContainer> queryData = dataAccessor.getData(request);

        List<ParticipationAggregationReportDTO> returnValue = new ArrayList<ParticipationAggregationReportDTO>();

        getParticipationReportAggregatedData(returnValue, queryData, queryName);

        return returnValue;
    }



    /**
     * <p>Reads the data for <code>Participation Metrics</code> report from the specified queries and puts it to
     * specified list.</p>
     *
     * @param aggregator a <code>List</code> collecting the aggregated data.
     * @param queryData a <code>Map</code> providing the query data.
     * @param queryNameBase a <code>String</code> providing the base name of the queries to get data from.
     * @since 3.8.1
     */
    private static void getParticipationReportAggregatedData(List<ParticipationAggregationReportDTO> aggregator,
                                                             Map<String, ResultSetContainer> queryData,
                                                             String queryNameBase) {
        ResultSetContainer result = queryData.get(queryNameBase);
        for (ResultSetRow row : result) {
            ParticipationAggregationReportDTO dto = new ParticipationAggregationReportDTO();

            dto.setGroupName(row.getStringItem("group_name"));
            dto.setTotalRegistrants(row.getIntItem("total_registrants_count"));
            dto.setUniqueRegistrants(row.getIntItem("unique_registrants_count"));
            dto.setTotalSubmissions(row.getIntItem("total_submissions_count"));
            dto.setCheckpointSubmissions(row.getIntItem("milestone_submissions_count"));
            dto.setFinalSubmissions(row.getIntItem("final_submissions_count"));
            dto.setCheckpointWinners(row.getIntItem("milestone_winners_count"));
            dto.setFinalWinners(row.getIntItem("final_winners_count"));
            dto.setTotalWinners(dto.getCheckpointWinners() + dto.getFinalWinners());
            dto.setRegistrantCountries(row.getIntItem("registrants_country_count"));
            dto.setSubmitterContries(row.getIntItem("submitters_country_count"));
            dto.setWinnerCountries(row.getIntItem("winners_country_count"));
            dto.setUniqueSubmitters(row.getIntItem("unique_submitters_count"));
            dto.setTotalUniqueWinners(row.getIntItem("unique_winners_count"));
            if (row.isValidColumn("total_contests_count")) {
                dto.setTotalContests(row.getIntItem("total_contests_count"));
            }

            aggregator.add(dto);
        }
    }

    /**
     * <p>
     * Gets the direct project forum templates defined in database.
     * </p>
     * @param projectType
     *            the direct project type id.
     * @return a list of <code>ProjectForumTemplateDTO</code> representing the project forum template.
     * @throws Exception
     *             if any error occurs.
     */
    public static List<ProjectForumTemplateDTO> getDirectProjectForumTemplates(long projectType) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_project_forum_template");
        request.setProperty("pt", "" + projectType);
        final Map<String, ResultSetContainer> queryData = dataAccessor.getData(request);

        final ResultSetContainer templateResult = queryData.get("direct_project_forum_template");
        List<ProjectForumTemplateDTO> templates = new ArrayList<ProjectForumTemplateDTO>();
        for (ResultSetContainer.ResultSetRow row : templateResult) {
            ProjectForumTemplateDTO template = new ProjectForumTemplateDTO();
            template.setForumName(row.getStringItem("name"));
            template.setForumDescription(row.getStringItem("description"));
            templates.add(template);
        }

        return templates;
    }

    /**
     * <p>Build a string concatenating the specified values separated with specified delimiter.</p>
     *
     * @param items     a <code>long</code> array providing the values to be concatenated.
     * @param delimiter a <code>String</code> providing the delimiter to be inserted between concatenated items.
     * @return a <code>String</code> providing the concatenated item values.
     * @since 2.1.9
     */
    private static String concatenate(String[] items, String delimiter) {
        StringBuilder b = new StringBuilder();
        for (String id : items) {
            if (b.length() > 0) {
                b.append(delimiter);
            }
            b.append('"').append(id).append('"');
        }
        return b.toString();
    }

    /**
     * <p>Generates  the <code>Excel</code> worksheet with results for contests for specified <code>TC Direct</code>
     * project.</p>
     *
     * @param tcDirectProjectId a <code>long</code> providing the ID of <code>TC Direct</code> project to generate
     *                          winner sheet for.
     * @return an <code>InputStream</code> with content of generated worksheet.
     * @throws Exception if an unexpected error occurs.
     * @since 3.11
     */
    public static InputStream generateWinnerSheet(long tcDirectProjectId) throws Exception {
        DateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy HH:mm z");
        DateFormat dateFormat2 = new SimpleDateFormat("MM.dd.yyyy");

        // Get project contest results from DB
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("project_contest_results");
        request.setProperty("tcdirectid", String.valueOf(tcDirectProjectId));
        ResultSetContainer projectContestResults = dataAccessor.getData(request).get("project_contest_results");

        // Convert results to Excel worksheet
        String sheetName = WorkbookUtil.createSafeSheetName("Contest Results for " + tcDirectProjectId);
        Workbook wb = new HSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet1 = wb.createSheet(sheetName);
        sheet1.setColumnWidth(0, 12 * 256);
        sheet1.setColumnWidth(1, 100 * 256);
        sheet1.setColumnWidth(2, 15 * 256);
        sheet1.setColumnWidth(3, 20 * 256);
        sheet1.setColumnWidth(4, 20 * 256);

        // Header
        Row row = sheet1.createRow(0);
        createExcelHeaderCell(0, "Contest ID", row);
        createExcelHeaderCell(1, "Contest Name", row);
        createExcelHeaderCell(2, "Completed", row);
        createExcelHeaderCell(3, "1", row);
        createExcelHeaderCell(4, "2", row);

        // Data
        int rowIndex = 1;
        for (ResultSetContainer.ResultSetRow data : projectContestResults) {
            row = sheet1.createRow(rowIndex++);
            row.createCell(0).setCellValue(data.getLongItem("contest_id"));
            row.createCell(1).setCellValue(data.getStringItem("contest_name"));
            String completionDateString = data.getStringItem("completion_date");
            row.createCell(2).setCellValue(dateFormat2.format(dateFormat.parse(completionDateString)));
            row.createCell(3).setCellValue(data.getStringItem("winner_handle"));
            TCResultItem runnerUpHandle = data.getItem("runner_up_handle");
            if (runnerUpHandle != null) {
                row.createCell(4).setCellValue((String) runnerUpHandle.getResultData());
            }
        }

        // Get the input stream with workbook content
        ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
        wb.write(saveTo);
        return new ByteArrayInputStream(saveTo.toByteArray());
    }

    /**
     * <p>Generates  the <code>Excel</code> worksheet with results for contests for specified <code>TC Direct</code>
     * project.</p>
     *
     * @param tcDirectProjectId a <code>long</code> providing the ID of <code>TC Direct</code> project to generate
     *                          winner sheet for.
     * @param startDate the start date
     * @param endDate the end date
     * @return an <code>InputStream</code> with content of generated worksheet.
     * @throws Exception if an unexpected error occurs.
     * @since 3.11
     */
    public static InputStream generateSubmissionSheet(long tcDirectProjectId, Date startDate, Date endDate, long userId) throws Exception {
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMinimumFractionDigits(2);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Get project contest results from DB
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("project_contest_submissions");
        request.setProperty("tcdirectid", String.valueOf(tcDirectProjectId));
        request.setProperty("sdt", dateFormat.format(startDate));
        request.setProperty("edt", dateFormat.format(endDate));
    request.setProperty("uid", String.valueOf(userId));

        ResultSetContainer projectContestResults = dataAccessor.getData(request).get("project_contest_submissions");

        // Convert results to Excel worksheet
        String sheetName = WorkbookUtil.createSafeSheetName("Contest Submissions for " + tcDirectProjectId);
        Workbook wb = new HSSFWorkbook();
        CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet1 = wb.createSheet(sheetName);
        sheet1.setColumnWidth(0, 12 * 256);
        sheet1.setColumnWidth(1, 100 * 256);
        sheet1.setColumnWidth(2, 30 * 256);
        sheet1.setColumnWidth(3, 20 * 256);
        sheet1.setColumnWidth(4, 20 * 256);

        // Header
        Row row = sheet1.createRow(0);
        createExcelHeaderCell(0, "Contest ID", row);
        createExcelHeaderCell(1, "Contest Name", row);
        createExcelHeaderCell(2, "Submission ID", row);
        createExcelHeaderCell(3, "Score", row);
        createExcelHeaderCell(4, "Rank", row);

        // Data
        int rowIndex = 1;
        for (ResultSetContainer.ResultSetRow data : projectContestResults) {
            row = sheet1.createRow(rowIndex++);
            row.createCell(0).setCellValue(data.getLongItem("contest_id"));
            row.createCell(1).setCellValue(data.getStringItem("contest_name"));
            row.createCell(2).setCellValue(data.getLongItem("submission_id"));
            row.createCell(3).setCellValue(format.format(data.getDoubleItem("final_score") / 100.0));
            row.createCell(4).setCellValue(data.getLongItem("placement"));
        }

        // Get the input stream with workbook content
        ByteArrayOutputStream saveTo = new ByteArrayOutputStream();
        wb.write(saveTo);
        return new ByteArrayInputStream(saveTo.toByteArray());
    }

    /**
     * <p>Generates the <code>PDF</code> document with composite review for specified submission.</p>
     *
     * @param loginUrl a <code>String</code> providing the URL for Login controller for Online Review application.
     * @param username a <code>String</code> providing username to be used to login to Online Review application.
     * @param password a <code>String</code> providing password to be used to login to Online Review application.
     * @param compositeReviewBaseURL a <code>String</code> providing the URL for ViewCompositeReview controller for
     *                               Online Review application.
     * @param baseURL a <code>String</code> providing the base URL for Online Review application.
     * @param submissionId a <code>long</code> providing the ID of submission to generate combined review scorecard for.
     * @since 3.11
     */
    public static InputStream generateCombinedReviewScorecard(String loginUrl, String username, String password,
                                                       String compositeReviewBaseURL, String baseURL,
                                                       long submissionId) throws Exception {
        HttpPost loginRequest = new HttpPost(loginUrl + "?method=login&userName=" + username + "&password=" + password);

        HttpClient http = new DefaultHttpClient();
        CookieStore cookieStore = new BasicCookieStore();
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        HttpResponse loginResponse = http.execute(loginRequest, localContext);
        if (loginResponse.getStatusLine().getStatusCode() == 302) {
            HttpEntity loginResponseEntity = loginResponse.getEntity();
            if (loginResponseEntity != null) {
                EntityUtils.consume(loginResponseEntity);
            }

            HttpGet compositeReviewRequest = new HttpGet(compositeReviewBaseURL + submissionId);
            HttpResponse compositeReviewResponse = http.execute(compositeReviewRequest, localContext);
            if (compositeReviewResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity compositeReviewPage = compositeReviewResponse.getEntity();
                if (compositeReviewPage != null) {
                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                    documentBuilderFactory.setValidating(false);
                    documentBuilderFactory.setFeature("http://xml.org/sax/features/namespaces", false);
                    documentBuilderFactory.setFeature("http://xml.org/sax/features/validation", false);
                    documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar",
                                                      false);
                    documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd",
                                                      false);

                    DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
                    InputStream content = compositeReviewPage.getContent();
                    Document doc = builder.parse(content);
                    NodeList head;
                    head = doc.getElementsByTagName("head");
		            if(head.getLength() == 0) {
                        head = doc.getElementsByTagName("HEAD");
                    }
                    Element style = doc.createElement("style");
                    style.setAttribute("type", "text/css");
                    style.appendChild(doc.createTextNode("<!--@page { size:960px 300mm; }-->"));
                    head.item(0).appendChild(style);



                    ITextRenderer renderer = new ITextRenderer();
                    renderer.setDocument(doc, baseURL);

                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    renderer.layout();
                    renderer.createPDF(os);
                    os.close();

                    return new ByteArrayInputStream(os.toByteArray());
                } else {
                    throw new DirectException("Failed to retrieve composite review for submission: " + submissionId
                                              + ". Content is empty");
                }
            } else {
                throw new DirectException("Failed to retrieve composite review for submission: " + submissionId
                                          + ". HTTP status code: "
                                          + compositeReviewResponse.getStatusLine().getStatusCode());
            }
        } else {
            throw new DirectException("Failed to login to Online Review application. HTTP status code: " +
                                      loginResponse.getStatusLine().getStatusCode());
        }
    }

    /**
     * <p>Creates the header cell with nold text aligned in center for specified row.</p>
     *
     * @param index an <code>int</code> providing the index of the cell.
     * @param title a <code>String</code> providing the text for the cell.
     * @param row   a <code>Row</code> providing the row to create cell for.
     * @since 3.11
     */
    private static void createExcelHeaderCell(int index, String title, Row row) {
        Workbook workbook = row.getSheet().getWorkbook();

        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(CellStyle.ALIGN_CENTER);

        Cell cell = row.createCell(index);
        cell.setCellValue(title);
        cell.setCellStyle(style);
    }

    /**
     *
     * <p>
     * Gets the contest ids to export for the given project.
     * </p>
     *
     * @param projectId the direct project id.
     * @param userId user id.
     * @param startDate start date to export.
     * @param endDate end date to export.
     * @return a map where the key is the contest id and the value is whether the contest is a studio contest (true if it is, otherwise false)
     * @throws Exception
     *             If there is any error.
     * @since 4.1
     */
    public static Map<Long, Boolean> getContestIdsToExport(long projectId, long userId, Date startDate, Date endDate)
            throws Exception {
        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);

        Request request = new Request();
        request.setContentHandle("contest_ids_to_export");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("tcdirectid", String.valueOf(projectId));
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        request.setProperty("sdt", formatter.format(startDate));
        request.setProperty("edt", formatter.format(endDate));

        final ResultSetContainer resultSetContainer = dataAccess.getData(request).get("contest_ids_to_export");
        Map<Long, Boolean> result = new HashMap<Long, Boolean>();
        for (ResultSetRow row : resultSetContainer) {
            result.put(row.getLongItem("contest_id"), row.getBooleanItem("is_studio"));
        }
        return result;
    }

    /**
     * <p>
     * Gets the submission ids to export.
     * </p>
     *
     * @param contestId the contest id
     * @param isStudio whether the contest is studio
     * @return a map, the key is the submission id, the value is the file location to export
     * @throws Exception
     *             If there is any error.
     * @since 4.1
     */
    public static List<Submission> getContesSubmissionIdsToExport(long contestId, boolean isStudio) throws Exception {
        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        if (isStudio) {
            request.setContentHandle("studio_submission_to_export");
        } else {
            request.setContentHandle("software_submission_to_export");
        }
        request.setProperty("pj", String.valueOf(contestId));
        ResultSetContainer resultSetContainer = null;
        if (isStudio) {
            resultSetContainer = dataAccess.getData(request).get("studio_submission_to_export");
        } else {
            resultSetContainer = dataAccess.getData(request).get("software_submission_to_export");
        }

        List<Submission> result = new ArrayList<Submission>();
        for (ResultSetRow row : resultSetContainer) {
            Submission s = new Submission();
            s.setId(row.getLongItem("submission_id"));
            s.setCreationUser(row.getStringItem("handle"));  // store the handle
            Upload u = new Upload();
            u.setOwner(row.getLongItem("user_id"));
            u.setParameter(row.getStringItem("parameter"));
            s.setUpload(u);
            result.add(s);
        }

        return result;
    }

    /**
     * <p>
     * Checks whether should show contests download panel.
     * </p>
     *
     * @param projectId the project id
     * @return true if should show, otherwise false.
     * @throws Exception If there is any error.
     * @since 4.1
     */
    public static boolean showContestsDownload(long projectId) throws Exception {
        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("show_contests_download");
        request.setProperty("tcdirectid", String.valueOf(projectId));

        final ResultSetContainer resultSetContainer = dataAccess.getData(request).get("show_contests_download");
        boolean showContestsDownload = false;

        if (resultSetContainer.size() > 0) {
            showContestsDownload = true;
        }
        return showContestsDownload;
    }

  /**
     * <p>
     * This method retrieves client user stats.
     * </p>
     * @return Collection of {@link ClientUserStatsDTO}.
     * @throws Exception  If there is any error.
     * @since 5.5
     */
    public static List<ClientUserStatsDTO> getClientUserStats() throws Exception {
        List<ClientUserStatsDTO> result = new ArrayList<ClientUserStatsDTO>();
        DataAccess dataAccess = new DataAccess(DBMS.TCS_DW_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("client_user_stats");
        final ResultSetContainer resultSetContainer = dataAccess.getData(request).get("client_user_stats");
        if(null == resultSetContainer) {
            return result;
        } else {
            ClientUserStatsDTO dto = null;
            for(ResultSetRow row : resultSetContainer) {
                dto = new ClientUserStatsDTO();
                dto.setClientId(row.getLongItem("client_id"));
                dto.setClientName(row.getStringItem("client_name"));
                dto.setYearMonth(row.getStringItem("year_month"));
                dto.setUserCount(row.getIntItem("user_count"));
                result.add(dto);
            }
            return result;
        }
    }

    /**
     * Gets the billing cost report entries for second installments with the given parameters. The method returns a map,
     * the key is the payment id for the first installment, the value is the billing cost entry for the second installment.
     *
     * @param paymentIds the payment ids.
     * @param invoiceTypeNames the invoice type names.
     * @return the generated cost report.
     * @throws Exception if any error occurs.
     * @since 5.9
     */
    public static Map<Long, BillingCostReportEntryDTO> getRelatedSecondInstallment(List<Long> paymentIds,
            List<String> invoiceTypeNames) throws Exception {
        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("tc_direct_payment_second_installment");
        List<Long> paymentIdsList = new ArrayList<Long>();
        paymentIdsList.add(0L);
        // prepare for the query parameters
        for (int i = 0; i < paymentIds.size(); i++) {
            if (!PaymentType.PLATFORM_FEE.getDescription().equalsIgnoreCase(invoiceTypeNames.get(i))) {
                if (paymentIds.get(i) > 0) {
                    paymentIdsList.add(paymentIds.get(i));
                }
            }
        }
        request.setProperty("payids", concatenate(paymentIdsList, ","));

        Map<Long, BillingCostReportEntryDTO> result = new HashMap<Long, BillingCostReportEntryDTO>();

        // query result by contestIds and paymentIds
        final ResultSetContainer resultSetContainer = dataAccess.getData(request).get("tc_direct_payment_second_installment");
        for (int i = 0; i < resultSetContainer.size(); i++) {
            long paymentId = resultSetContainer.getLongItem(i, "payment_id");
            long paymentIdSecond = resultSetContainer.getLongItem(i, "payment_id_second");
            BillingCostReportEntryDTO costDTO = new BillingCostReportEntryDTO();
            costDTO.setPaymentId(paymentIdSecond);
            costDTO.setInvoiceAmount(0);
            result.put(paymentId, costDTO);
        }
        return result;
    }

    /**
     * Gets the result for the instant search and search all.
     *
     * @param searchKey the search key.
     * @param searchNumber the search number.
     * @return the search result
     * @throws Exception if there is any error.
     * @since 6.2
     */
    public static Map<String, List> getInstantSearchResult(String searchKey, long searchNumber) throws Exception {
        if(searchKey == null || searchKey.length() < 3) {
            throw new IllegalArgumentException("The instant search query must contain at least 3 characters");
        }

        final TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("global_instant_search");

        if(DirectUtils.isTcStaff(currentUser)) {
            request.setProperty("uid", String.valueOf(0));
        } else {
            request.setProperty("uid", String.valueOf(currentUser.getUserId()));
        }

        request.setProperty("searchKey", searchKey.toUpperCase());
        request.setProperty("searchNumber", String.valueOf(searchNumber));

        ResultSetContainer resultSetContainer;

        // gets the contests search result
        resultSetContainer = dataAccess.getData(request).get("contests_instant_search");
        List<ContestSearchResult> contestResults = new ArrayList<ContestSearchResult>();

        for (ResultSetRow row : resultSetContainer) {
            ContestSearchResult contestResult = new ContestSearchResult();
            contestResult.setContestId(row.getLongItem("contest_id"));
            contestResult.setContestName(row.getStringItem("contest_name"));
            contestResult.setContestTypeId(row.getLongItem("contest_type_id"));
            // the last parameter of forIdAndFlag is not used
            contestResult.setContestTypeName(ContestType.forIdAndFlag(contestResult.getContestTypeId(), false).getName());
            contestResult.setProjectId(row.getLongItem("direct_project_id"));
            contestResult.setProjectName(row.getStringItem("direct_project_name"));
            contestResults.add(contestResult);
        }

        // gets the projects search result
        resultSetContainer = dataAccess.getData(request).get("projects_instant_search");
        List<ProjectSearchResult> projectResults = new ArrayList<ProjectSearchResult>();

        for (ResultSetRow row : resultSetContainer) {
            ProjectSearchResult projectResult = new ProjectSearchResult();
            projectResult.setProjectId(row.getLongItem("project_id"));
            projectResult.setProjectName(row.getStringItem("name"));
            if (row.getItem("project_type").getResultData() != null) {
                projectResult.setProjectTypeName(row.getStringItem("project_type"));
            }
            if (row.getItem("project_category").getResultData() != null) {
                projectResult.setProjectCategoryName(row.getStringItem("project_category"));
            }
            if (row.getItem("client_name").getResultData() != null) {
                projectResult.setClientName(row.getStringItem("client_name"));
            }
            projectResults.add(projectResult);
        }

        Map<String, List> result = new HashMap<String, List>();

        result.put("contests", contestResults);
        result.put("projects", projectResults);

        return result;
    }

    public static Map<String, List> getInstantSearchResultV2(String searchKey, long searchNumber) throws Exception {
        if (searchKey == null || searchKey.length() < 3) {
            throw new IllegalArgumentException("The instant search query must contain at least 3 characters");
        }

        final TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        long userId;

        if (DirectUtils.isTcStaff(currentUser)) {
            userId = 0;
        } else {
            userId = currentUser.getUserId();
        }

        Connection connection = null;
        PreparedStatement getContestsInstantSearch = null;
        PreparedStatement getProjectsInstantSearch = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtils.getDatabaseConnection(DBMS.TCS_OLTP_DATASOURCE_NAME);

            getContestsInstantSearch = connection.prepareStatement(INSTANT_SERACH_CONTEST);
            getProjectsInstantSearch = connection.prepareStatement(INSTANT_SERACH_PROJECT);

            getContestsInstantSearch.setLong(1, searchNumber);
            getContestsInstantSearch.setLong(2, userId);
            getContestsInstantSearch.setLong(3, userId);
            getContestsInstantSearch.setString(4, "%" + searchKey.toUpperCase() + "%");

            getProjectsInstantSearch.setLong(1, searchNumber);
            getProjectsInstantSearch.setLong(2, userId);
            getProjectsInstantSearch.setLong(3, userId);
            getProjectsInstantSearch.setString(4, "%" + searchKey.toUpperCase() + "%");

            resultSet = getContestsInstantSearch.executeQuery();
            List<ContestSearchResult> contestResults = new ArrayList<ContestSearchResult>();
            while (resultSet.next()) {
                ContestSearchResult contestResult = new ContestSearchResult();
                contestResult.setContestId(resultSet.getLong("contest_id"));
                contestResult.setContestName(resultSet.getString("contest_name"));
                contestResult.setContestTypeId(resultSet.getLong("contest_type_id"));
                // the last parameter of forIdAndFlag is not used
                contestResult.setContestTypeName(ContestType.forIdAndFlag(contestResult.getContestTypeId(), false).getName());
                contestResult.setProjectId(resultSet.getLong("direct_project_id"));
                contestResult.setProjectName(resultSet.getString("direct_project_name"));
                contestResults.add(contestResult);
            }


            resultSet = getProjectsInstantSearch.executeQuery();
            List<ProjectSearchResult> projectResults = new ArrayList<ProjectSearchResult>();
            while (resultSet.next()) {
                ProjectSearchResult projectResult = new ProjectSearchResult();
                projectResult.setProjectId(resultSet.getLong("project_id"));
                projectResult.setProjectName(resultSet.getString("name"));
                if (resultSet.getObject("project_type") != null) {
                    projectResult.setProjectTypeName(resultSet.getString("project_type"));
                }
                if (resultSet.getObject("project_category") != null) {
                    projectResult.setProjectCategoryName(resultSet.getString("project_category"));
                }
                if (resultSet.getObject("client_name") != null) {
                    projectResult.setClientName(resultSet.getString("client_name"));
                }
                projectResults.add(projectResult);
            }

            Map<String, List> result = new HashMap<String, List>();

            result.put("contests", contestResults);
            result.put("projects", projectResults);

            return result;


        } finally {
            DatabaseUtils.close(resultSet);
            DatabaseUtils.close(getContestsInstantSearch);
            DatabaseUtils.close(getProjectsInstantSearch);
            DatabaseUtils.close(connection);
        }

    }

    /**
     * get active problems
     * 
     * @return the active problems
     * @throws Exception if there is any error.
     * @since 6.6
     */
    public static List<ProblemDTO> getActiveProblemSet() throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("active_problems");
        final Map<String, ResultSetContainer> queryData = dataAccessor.getData(request);

        final ResultSetContainer templateResult = queryData.get("active_problems");
        List<ProblemDTO> problems = new ArrayList<ProblemDTO>();
        for (ResultSetContainer.ResultSetRow row : templateResult) {
            ProblemDTO problem = new ProblemDTO();
            problem.setId(row.getLongItem("problem_id"));
            problem.setName(row.getStringItem("problem_name"));
            problems.add(problem);
        }

        return problems;
        
    }

    /**
     * Gets the all topcoder platform specialists.
     *
     * @return all the topcoder platform specialists. The key is the user id, the value is the user handle.
     * @throws Exception if any error.
     * @since 6.8
     */
    public static Map<Long, String> getAllPlatformSpecialists() throws Exception{
        Map<Long, String> map = new LinkedHashMap<Long, String>();

        final String queryName = "get_all_platform_specialists";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            map.put(row.getLongItem("user_id"), row.getStringItem("handle"));
        }

        return map;
    }

    /**
     * Gets the report data for the platform specialist report.
     *
     * @param userIds the user ids of the platform specialists
     * @param startDate the start date of the report
     * @param endDate the end date of the report
     * @return a list of <code>PlatformSpecialistReportDTO</code>
     * @throws Exception if any error
     * @since 6.8
     */
    public static List<PlatformSpecialistReportDTO> getPlatformSpecialistReportData(String userIds, String startDate,
                                                                                    String endDate) throws Exception {
        final String queryName = "member_spend_by_platform_specialist";
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("uids", userIds);

        DateFormat queryDateFormat = new SimpleDateFormat("yyyy-MM");
        DateFormat timelineDateFormat = new SimpleDateFormat("MMM''yy");

        Date startTime = timelineDateFormat.parse(startDate);
        request.setProperty("sdt", queryDateFormat.format(startTime));
        Date endTime = timelineDateFormat.parse(endDate);
        request.setProperty("edt", queryDateFormat.format(endTime));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);

        long startMonthCount = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);
        calendar.setTime(endTime);
        long endMonthCount = calendar.get(Calendar.YEAR) * 12 + calendar.get(Calendar.MONTH);

        final ResultSetContainer resultSetContainer = dataAccessor.getData(request).get(queryName);
        Map<Long, PlatformSpecialistReportDTO> resultMap = new HashMap<Long, PlatformSpecialistReportDTO>();
        for (ResultSetContainer.ResultSetRow row : resultSetContainer) {
            long uid = row.getLongItem("user_id");
            String handle = row.getStringItem("handle");
            long monthCount = row.getLongItem("monthcount");
            double memberSpent = row.getDoubleItem("member_spent");
            long monthKey = (monthCount/100) * 12 + (monthCount % 100 -1);

            PlatformSpecialistReportDTO pmDTO = resultMap.get(uid);
            if (pmDTO == null) {
                pmDTO = new PlatformSpecialistReportDTO();
                pmDTO.setUserId(uid);
                pmDTO.setUserHandle(handle);
                // intialize month spent result
                for(long i = startMonthCount; i <= endMonthCount; ++i) {
                    pmDTO.getMonthsMemberSpend().put(i, 0.0);
                }
                resultMap.put(uid, pmDTO);
            }

            pmDTO.getMonthsMemberSpend().put(monthKey, pmDTO.getMonthsMemberSpend().get(monthKey) + memberSpent);
        }

        return new ArrayList<PlatformSpecialistReportDTO>(resultMap.values());
    }

    /**
     * Get member pullable payments.
     * 
     * @return
     *      an instance of {@link PullablePayments}.
     * @throws Exception
     *      if a generic error occurs
     */
    public static PullablePayments getPullablePayments() throws Exception {
      final String queryName = "get_pullable_payments";
      DataAccess dataAccess = new DataAccess(DBMS.JTS_OLTP_DATASOURCE_NAME);
      Request request = new Request();
      request.setContentHandle(queryName);
      
      final ResultSetContainer resultSet = dataAccess.getData(request).get(queryName);

      PullablePayments payments = new PullablePayments();
      for(ResultSetContainer.ResultSetRow row : resultSet) {
        int paymentMethodId = row.getIntItem("payment_method_id");
        double paymentAmount = row.getDoubleItem("net_amount");

        if(paymentMethodId == PaymentMethod.PAYPAL.getPaymentMethodId()) {
            payments.setPaypalPayments(paymentAmount);
        } else if(paymentMethodId == PaymentMethod.PAYONEER.getPaymentMethodId()) {
            payments.setPayoneerPayments(paymentAmount);
        } else if(paymentMethodId == PaymentMethod.WESTERN_UNION.getPaymentMethodId()) {
            payments.setWesternUnionPayments(paymentAmount);
        } else if (paymentMethodId == PaymentMethod.NOT_SET.getPaymentMethodId()
                || paymentMethodId == -1) {
            // we should treat it as NOT SET if there is no data in user_payment_method
            payments.setNotSetPayments(payments.getNotSetPayments() + paymentAmount);
        }
      }
      return payments;
    }
    
    /**
     * Get member payments by status for given days.
     * 
     * @param startDate
     *      start date
     * @param endDate
     *      end date
     * @return
     *      a list of {@link PaymentsByStatusResult} instances.
     * @throws Exception
     *      if a generic error occurs
     */
    public static List<PaymentsByStatusResult> getPaymentsByStatus(Date startDate, Date endDate) throws Exception {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Specified dates should not be null");
        }
        
      final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
      final String queryName = "get_payments_by_status";
      DataAccess dataAccess = new DataAccess(DBMS.JTS_OLTP_DATASOURCE_NAME);
      Request request = new Request();
      request.setContentHandle(queryName);
      request.setProperty("sda", dateFormatter.format(startDate));
      request.setProperty("ed", dateFormatter.format(endDate));

      List<PaymentsByStatus> payments = new ArrayList<PaymentsByStatus>(); 
      final ResultSetContainer resultSet = dataAccess.getData(request).get(queryName);
      for(int i=0;i<resultSet.size();i++) {
        double amount = resultSet.getDoubleItem(i, "net_amount");
        int statusId = resultSet.getIntItem(i, "payment_status_id");
        Date updateDate = resultSet.getTimestampItem(i, "date_modified");

        PaymentsByStatus paymentByStatus = new PaymentsByStatus();
        paymentByStatus.setStatusUpdateDate(updateDate);
        if(statusId == PaymentStatus.PAID.getPaymentStatusId()) {
          paymentByStatus.setPaidPayments(amount);
        } else if(statusId == PaymentStatus.ON_HOLD.getPaymentStatusId()) {
          paymentByStatus.setOnHoldPayments(amount);
        } else if(statusId == PaymentStatus.OWED.getPaymentStatusId() ||
            statusId == PaymentStatus.ACCRUING.getPaymentStatusId()) {
          paymentByStatus.setOwedOrAccruingPayments(amount);
        } else if(statusId == PaymentStatus.ENTERED_INTO_PAYMENT_SYSTEM.getPaymentStatusId()) {
          paymentByStatus.setPaymentsEnteredIntoPaymentSystem(amount);
        }
        payments.add(paymentByStatus);
      }
      DateRange dateRange = new DateRange();
      dateRange.setFromDate(startDate);
      dateRange.setToDate(endDate);

      PaymentsByStatusResult paymentByStatusResult = new PaymentsByStatusResult();
      paymentByStatusResult.setDateRange(dateRange);
      paymentByStatusResult.setPaymentsByStatus(payments);

      List<PaymentsByStatusResult> result = new ArrayList<PaymentsByStatusResult>();
      result.add(paymentByStatusResult);
      return result;
    }

    /**
     * Get member potential payments for given days.
     *
     * @param endDate
     *            end date
     * @param paymentMethodIds
     *            the payment method IDs
     * @return Map of {@link Integer} to {@link CashOutflowPotentialResult} instances.
     * @throws Exception
     *             if a generic error occurs
     */
    public static Map<Integer, CashOutflowPotentialResult> getPotentialMemberPayments(Date endDate,
                                                                              int[] paymentMethodIds)
        throws Exception {
        if (endDate == null) {
            throw new IllegalArgumentException("Specified dates should not be null");
        }
        if (paymentMethodIds == null || paymentMethodIds.length == 0) {
            throw new IllegalArgumentException("payment method ids should not be null/empty");
        }

        final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        final String queryName = "get_potential_member_payments";
        DataAccess dataAccess = new DataAccess(DBMS.JTS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle(queryName);
        request.setProperty("ed", dateFormatter.format(endDate));
        request.setProperty("paymentMethodIds", concatenate(paymentMethodIds, ","));

        final ResultSetContainer resultSet = dataAccess.getData(request).get(queryName);

        Map<Integer, CashOutflowPotentialResult> ret = new HashMap<Integer, CashOutflowPotentialResult>();
        for (int method : paymentMethodIds) {
            CashOutflowPotentialResult cashOutflowPotentialResult = new CashOutflowPotentialResult();
            cashOutflowPotentialResult.setItems(new ArrayList<CashOutflowPotential>());
            ret.put(method, cashOutflowPotentialResult);
        }

        for (int i = 0; i < resultSet.size(); i++) {
            double amount = resultSet.getDoubleItem(i, "net_amount");
            int paymentMethodId = resultSet.getIntItem(i, "payment_method_id");
            Date dueDate = resultSet.getTimestampItem(i, "date_due");

            CashOutflowPotential potential = new CashOutflowPotential();
            potential.setDueDate(dueDate);
            potential.setPayments(amount);

            if (!ret.containsKey(paymentMethodId)) {
                CashOutflowPotentialResult cashOutflowPotentialResult = new CashOutflowPotentialResult();
                cashOutflowPotentialResult.setItems(new ArrayList<CashOutflowPotential>());
                ret.put(paymentMethodId, cashOutflowPotentialResult);
            }
            ret.get(paymentMethodId).getItems().add(potential);
        }

        return ret;
    }

    /**
     * Get member payment history with given criteria.
     * 
     * @param criteria
     *      search criteria
     * @return
     *      a list of {@link PaymentHistoryResult} instances.
     * @throws Exception
     */
    public static List<PaymentHistoryResult> getPaymentHistory(PaymentHistoryCriteria criteria) throws Exception {
        Date startDate = criteria.getStartDate();
        Date endDate = criteria.getEndDate();
        
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Specified dates should not be null");
        }
        
        final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
      final String queryName = "get_payment_history";
      DataAccess dataAccess = new DataAccess(DBMS.JTS_OLTP_DATASOURCE_NAME);
      Request request = new Request();
      request.setContentHandle(queryName);
      request.setProperty("sda", dateFormatter.format(startDate));
      request.setProperty("ed", dateFormatter.format(endDate));

      List<PaymentHistory> paypalPaymentHistory = new ArrayList<PaymentHistory>();
      List<PaymentHistory> payoneerPaymentHistory = new ArrayList<PaymentHistory>();
      List<PaymentHistory> westernUnionPaymentHistory = new ArrayList<PaymentHistory>();
      List<PaymentHistory> notSetPaymentHistory = new ArrayList<PaymentHistory>();
      final ResultSetContainer resultSet = dataAccess.getData(request).get(queryName);
      for(int i=0;i<resultSet.size();i++) {
        double amount = resultSet.getDoubleItem(i, "net_amount");
        int paymentMethodId = resultSet.getIntItem(i, "payment_method_id");
        int monthDate = resultSet.getIntItem(i, "date_month");
        int yearDate = resultSet.getIntItem(i, "date_year");

        PaymentHistory history = new PaymentHistory();
        history.setAmount(amount);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, yearDate);
        calendar.set(Calendar.MONTH, monthDate - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        history.setMonthDate(calendar.getTime());
        if(paymentMethodId == PaymentMethod.PAYPAL.getPaymentMethodId()) {
          paypalPaymentHistory.add(history);
        } else if(paymentMethodId == PaymentMethod.PAYONEER.getPaymentMethodId()) {
          payoneerPaymentHistory.add(history);
        } else if(paymentMethodId == PaymentMethod.WESTERN_UNION.getPaymentMethodId()) {
          westernUnionPaymentHistory.add(history);
        } else if(paymentMethodId == PaymentMethod.NOT_SET.getPaymentMethodId()) {
          notSetPaymentHistory.add(history);
        }
      }
      List<PaymentHistoryResult> paymentHistoryResult = new ArrayList<PaymentHistoryResult>();

      PaymentHistoryResult paypalResult = new PaymentHistoryResult();
      paypalResult.setPaymentMethod(PaymentMethod.PAYPAL);
      paypalResult.setItems(paypalPaymentHistory);
      paymentHistoryResult.add(paypalResult);

      PaymentHistoryResult payoneerResult = new PaymentHistoryResult();
      payoneerResult.setPaymentMethod(PaymentMethod.PAYONEER);
      payoneerResult.setItems(payoneerPaymentHistory);
      paymentHistoryResult.add(payoneerResult);

      PaymentHistoryResult westernUnionResult = new PaymentHistoryResult();
      westernUnionResult.setPaymentMethod(PaymentMethod.WESTERN_UNION);
      westernUnionResult.setItems(westernUnionPaymentHistory);
      paymentHistoryResult.add(westernUnionResult);

      PaymentHistoryResult notSetResult = new PaymentHistoryResult();
      notSetResult.setPaymentMethod(PaymentMethod.NOT_SET);
      notSetResult.setItems(notSetPaymentHistory);
      paymentHistoryResult.add(notSetResult);
      return paymentHistoryResult;
    }

    /**
     * Get member payment trend by given criteria.
     * 
     * @param criteria
     *      search criteria
     * @return
     *      a list of {@link PaymentTrend} instances.
     * @throws Exception
     *      if a generic error occurs
     */
    public static List<PaymentTrend> getPaymentsTrends(PaymentTrendSearchCriteria criteria) throws Exception {
        Date startDate = criteria.getStartDate();
        Date endDate = criteria.getEndDate();
        
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Specified dates should not be null");
        }
      final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
      DataAccess dataAccess = new DataAccess(DBMS.JTS_OLTP_DATASOURCE_NAME);
      Request request = new Request();
      String queryName = "";
      if(criteria.getPaymentStatusId() == PaymentStatus.PAID.getPaymentStatusId()) {
        queryName = "get_paid_payments_trends";
        request.setProperty("psid", String.valueOf(PaymentStatus.PAID.getPaymentStatusId()));
      } else if(criteria.getPaymentStatusId() == PaymentStatus.OWED.getPaymentStatusId()) {
        queryName = "get_created_payments_trends";
      }
      
        request.setProperty("sda", dateFormatter.format(startDate));      
        request.setProperty("ed", dateFormatter.format(endDate));
      if(null != criteria.getSortingColumn()) {
        request.setProperty("sc", criteria.getSortingColumn());
      }
      if(null != criteria.getSortOrder()) {
        request.setProperty("sdir", criteria.getSortOrder());
      }
      ResultSetContainer resultSet = null;

      request.setContentHandle(queryName);
    resultSet = dataAccess.getData(request).get(queryName);

    List<PaymentTrend> paymentTrends = new ArrayList<PaymentTrend>();
    if(null != resultSet) {
      for(int i=0;i<resultSet.size();i++) {
        double amount = resultSet.getDoubleItem(i, "net_amount");
        Date datePaid = resultSet.getTimestampItem(i, "date_paid");
        int paymentMethodId = resultSet.getIntItem(i, "payment_method_id");

        PaymentTrend trend = new PaymentTrend();
        trend.setPayments(amount);
        trend.setCreatedOrPaidDate(datePaid);
        trend.setPaymentMethodId(paymentMethodId);
        paymentTrends.add(trend);
      }
    }
      return paymentTrends;
    }

    /**
     * Get top member payments via criteria.
     * 
     * @param criteria
     *      search criteria
     * @return
     *      a list of {@link TopMemberPayment} instances
     * @throws Exception
     *      if a generic error occurs
     */
    public static List<TopMemberPayment> getTopMemberPayments(TopMemberPaymentCriteria criteria) throws Exception {
      final String queryName = "top_10_payments_summary";
      DataAccess dataAccess = new DataAccess(DBMS.JTS_OLTP_DATASOURCE_NAME);
      Request request = new Request();
      request.setContentHandle(queryName);
      if(null != criteria.getSortColumn()) {
        request.setProperty("sc", criteria.getSortColumn());
      }
      if(null != criteria.getSortOrder()) {
        request.setProperty("sdir", criteria.getSortOrder());
      }
      if(criteria.getResultLimit() <= 0) {
        request.setProperty("rl", "10");
      } else {
        request.setProperty("rl", String.valueOf(criteria.getResultLimit()));
      }
      List<TopMemberPayment> topMemberPayments = new ArrayList<TopMemberPayment>();
      final ResultSetContainer resultSet = dataAccess.getData(request).get(queryName);
      for(int i=0;i<resultSet.size();i++) {
        double amount = resultSet.getDoubleItem(i, "net_amount");
        String handle = resultSet.getStringItem(i, "handle");
        long userId = resultSet.getLongItem(i, "user_id");
        int paymentMethodId = resultSet.getIntItem(i, "payment_method_id");

        TopMemberPayment payment = new TopMemberPayment();
        payment.setAmount(amount);
        payment.setHandle(handle);
        payment.setUserId(userId);
        payment.setMemberLink(HandleTag.getLink(userId, "", "", null, null,
                new String[]{"coderTextOrange", "coderTextWhite", "coderTextGray", "coderTextGreen",
                        "coderTextBlue", "coderTextYellow", "coderTextRed"},
                new String[]{"coderTextOrange", "coderTextBlack", "coderTextGray", "coderTextGreen",
                        "coderTextBlue", "coderTextYellow", "coderTextRed"},
                false));
        if(paymentMethodId == PaymentMethod.PAYPAL.getPaymentMethodId()) {
          payment.setPaymentMethod(PAYPAL_METHOD_NAME);
        } else if(paymentMethodId == PaymentMethod.PAYONEER.getPaymentMethodId()) {
          payment.setPaymentMethod(PAYONEER_METHOD_NAME);
        } else if(paymentMethodId == PaymentMethod.WESTERN_UNION.getPaymentMethodId()) {
          payment.setPaymentMethod(WESTERN_UNION_METHOD_NAME);
        }  else if(paymentMethodId == PaymentMethod.NOT_SET.getPaymentMethodId()) {
          payment.setPaymentMethod(NOT_SET_METHOD_NAME);
        }   

        topMemberPayments.add(payment);
      }
      return topMemberPayments;
    }
    
    /**
     * This method queries Marathon Match download submission.
     *
     * @param roundId Round ID.
     * @param coderId Coder ID.
     * @return instance of MMDownloadSumissionDTO.
     * @throws Exception
     *         If there is any error.
     * @since 6.14
     */
    public static MMDownloadSubmissionDTO getMMSubmission(long roundId, long coderId) throws Exception {
        Request r = new Request();
        r.setContentHandle("long_contest_latest_submission");
        r.setProperty("rd", String.valueOf(roundId));
        r.setProperty("cr", String.valueOf(coderId));
        r.setProperty("ex", "0");

        DataAccess dataAccess = new DataAccess(DBMS.OLTP_DATASOURCE_NAME);
        ResultSetContainer rsc = dataAccess.getData(r).get("long_contest_latest_submission");
        if(!rsc.isEmpty()) {
            ResultSetRow firstRow = rsc.get(0);
            MMDownloadSubmissionDTO dto = new MMDownloadSubmissionDTO();
            dto.setFileName(coderId + "_" + firstRow.getStringItem("submission_number") + ".txt");
            String submissionText = firstRow.getStringItem("submission_text");
            dto.setSubmission(new ByteArrayInputStream(submissionText.getBytes()));
            return dto;
        } else {
            throw new Exception("Not matching submission found");
        }

    }


    /**
     * Gets the contest milestone associations from the given direct project and milestone.
     *
     * @param directProjectId the direct project id.
     * @param milestoneId the milestone id.
     * @param userId the user id.
     * @return a list of <code>MilestoneContestDTO</code>
     * @throws Exception if any error occurs.
     * @since 6.22
     */
    public static List<MilestoneContestDTO> getMilestoneContestAssociations(long directProjectId, long milestoneId, long userId) throws Exception {
        DataAccess dataAccessor = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        Request request = new Request();
        request.setContentHandle("direct_project_milestones_contests_associations");
        request.setProperty("uid", String.valueOf(userId));
        request.setProperty("tcdirectid", String.valueOf(directProjectId));
        request.setProperty("mid", String.valueOf(milestoneId));

        final List<MilestoneContestDTO> result = new ArrayList<MilestoneContestDTO>();

        DateFormat contestDateFormater = new SimpleDateFormat("MM/dd/yyyy HH:mm");

        final ResultSetContainer resultContainer = dataAccessor.getData(request).get(
                "direct_project_milestones_contests_associations");
        for(ResultSetRow row : resultContainer) {
            MilestoneContestDTO item = new MilestoneContestDTO();
            item.setContestId(row.getLongItem("contest_id"));
            item.setContestName(row.getStringItem("contest_name"));
            String contestStatus = row.getStringItem("contest_status_name");
            if(contestStatus.toLowerCase().indexOf("cancelled") != -1) {
                item.setContestShortStatus("Cancelled");
            } else {
                item.setContestShortStatus(contestStatus);
            }
            item.setContestStatus(contestStatus);
            item.setContestType(row.getStringItem("contest_type"));
            item.setStartDate(contestDateFormater.format(row.getTimestampItem("start_date")));
            item.setEndDate(contestDateFormater.format(row.getTimestampItem("end_date")));
            item.setMilestoneId(row.getLongItem("project_milestone_id"));

            result.add(item);
        }

        return result;
    }

    /**
     * Gets the max records number to retrieve for user recent items.
     *
     * @param recentItemTypeId the user recent item type id.
     * @return the max records number
     * @throws Exception if any error.
     * @since 6.25
     */
    public static int getRecentItemsMaxNumber(long recentItemTypeId) throws Exception {
        Request r = new Request();
        r.setContentHandle("max_recent_items_number");
        r.setProperty("itemTypeId", String.valueOf(recentItemTypeId));

        DataAccess cachedDataAccess = new CachedDataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        ResultSetContainer resultSet = cachedDataAccess.getData(r).get("max_recent_items_number");

        if(!resultSet.isEmpty()) {
            ResultSetRow firstRow = resultSet.get(0);

            return firstRow.getIntItem("max_records_number");
        } else {
            throw new Exception("No record for recent item type:" + recentItemTypeId + " in user_recent_item_type");
        }
    }

    /**
     * Gets the recent direct projects the specified user has accessed.
     *
     * @param userId the user id
     * @return a list of <code>DirectProjectAccess</code>, each one represents an access of a unique project
     * @throws Exception if any error
     * @since 6.25
     */
    public static List<DirectProjectAccess> getUserRecentDirectProjects(long userId) throws Exception {
        int maxRecentProjectsNumber = getRecentItemsMaxNumber(1L);

        Request r = new Request();
        r.setContentHandle("user_recent_direct_projects");
        r.setProperty("ps", String.valueOf(maxRecentProjectsNumber));
        r.setProperty("uid", String.valueOf(userId));

        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        ResultSetContainer resultSet = dataAccess.getData(r).get("user_recent_direct_projects");

        List<DirectProjectAccess> result = new ArrayList<DirectProjectAccess>();

        for(ResultSetRow row : resultSet) {
            DirectProjectAccess access = new DirectProjectAccess();
            access.setAccessItemId(row.getLongItem("project_id"));
            access.setItemName(row.getStringItem("project_name"));
            access.setAccessTime(row.getTimestampItem("project_access_time"));
            access.setUserId(userId);
            result.add(access);
        }

        return result;
    }

    /**
     * Gets the billing account <code>IdNamePair</code> from the CMC Account ID.
     *
     * @param cmcAccountId the CMC Account ID.
     * @return the billing account.
     * @throws Exception if there is any error.
     * @since 1.9
     */
    public static IdNamePair getBillingAccountFromCMCAccountID(String cmcAccountId) throws Exception {
        ValidationUtility.checkNotEmpty(cmcAccountId, "cmcAccountId", IllegalArgumentException.class);

        Request r = new Request();
        r.setContentHandle("get_billing_with_cmc_account_id");
        r.setProperty("cmcaccountid", cmcAccountId);

        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        ResultSetContainer resultSet = dataAccess.getData(r).get("get_billing_with_cmc_account_id");

        if(!resultSet.isEmpty()) {
            IdNamePair billing = new IdNamePair();
            billing.setId(resultSet.get(0).getLongItem("project_id"));
            billing.setName(resultSet.get(0).getStringItem("name"));
            return  billing;
        }

        return null;
    }

    /**
     * Gets the security groups from the billing account.
     *
     * @param billingAccountId the id of the billing account.
     * @return a list of groups consisting of id and name.
     * @throws Exception if any error.
     * @since 6.27
     */
    public static List<IdNamePair> getSecurityGroupsForBillingAccount(long billingAccountId) throws Exception {
        ValidationUtility.checkPositive(billingAccountId, "billingAccountId", IllegalAccessException.class);

        Request r = new Request();
        r.setContentHandle("get_client_groups_from_billing");
        r.setProperty("billingaccountid", String.valueOf(billingAccountId));

        DataAccess dataAccess = new DataAccess(DBMS.TCS_OLTP_DATASOURCE_NAME);
        ResultSetContainer resultSet = dataAccess.getData(r).get("get_client_groups_from_billing");

        List<IdNamePair> result = new ArrayList<IdNamePair>();

        for(ResultSetRow row : resultSet) {
            IdNamePair group = new IdNamePair();
            group.setId(row.getLongItem("group_id"));
            group.setName(row.getStringItem("group_name"));
            result.add(group);
        }

        return result;
    }

}

