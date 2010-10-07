/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.report;

import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.Project;
import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.dashboard.DashboardPipelineReportDTO;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineLaunchedContestsDTO;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineNumericalFilterType;
import com.topcoder.direct.services.view.dto.dashboard.pipeline.PipelineSummaryDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.form.DashboardPipelineReportForm;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.pipeline.CommonPipelineData;
import com.topcoder.service.pipeline.PipelineServiceFacade;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * <p>A <code>Struts</code> action to be used for handling the requests for viewing the Pipeline report.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0 (Direct Pipeline Integration Assembly)
 */
public class DashboardPipelineAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>SessionData</code> providing interface to current session.</p>
     */
    private SessionData sessionData;

    /**
     * <p>A <code>DashboardPipelineReportForm</code> providing the form parameters submitted by user.</p>
     */
    private DashboardPipelineReportForm formData;

    /**
     * <p>A <code>DashboardPipelineReportDTO</code> providing the view data for displaying by
     * <code>Pipeline Report</code> view. </p>
     */
    private DashboardPipelineReportDTO viewData;

    /**
     * <p>Constructs new <code>DashboardPipelineAction</code> instance. This implementation does nothing.</p>
     */
    public DashboardPipelineAction() {
        this.viewData = new DashboardPipelineReportDTO();
        this.formData = new DashboardPipelineReportForm();
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>DashboardPipelineReportForm</code> providing the data for form submitted by user..
     */
    public DashboardPipelineReportForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return an <code>DashboardPipelineReportDTO</code> providing the collector for data to be rendered by the view
     *         mapped to this action.
     */
    public DashboardPipelineReportDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Gets the current session associated with the incoming request from client.</p>
     *
     * @return a <code>SessionData</code> providing access to current session.
     */
    public SessionData getSessionData() {
        return this.sessionData;
    }

    /**
     * <p>Handles the incoming request. If action is executed successfully then changes the current project context to
     * project for contest requested for this action.</p>
     *
     * @return <code>SUCCESS</code> if request is to be forwarded to respective view or <code>download</code> if
     *         response is to be written to client directly.
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            if (getFormData().isExcel()) {
                return "download";
            }
        }
        return result;
    }

    /**
     * <p>Handles the incoming request. Retrieves data for Pipeline report and binds it to request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        // Get current session
        HttpServletRequest request = DirectUtils.getServletRequest();
        this.sessionData = new SessionData(request.getSession());
        TCSubject currentUser = getCurrentUser();

        // If necessary get and process report data
        if (!getViewData().getShowJustForm()) {
            // Analyze form parameters
            DashboardPipelineReportForm form = getFormData();
            Date startDate = DirectUtils.getDate(form.getStartDate());
            Date endDate = DirectUtils.getDate(form.getEndDate());

            // If any of dates is not set then use nearest preceding Sunday + 4 Weeks period
            if ((startDate == null) || (endDate == null)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(DirectUtils.DATE_FORMAT);
                Date nearestSunday = getSundayDate(new Date());
                startDate = nearestSunday;
                endDate = new Date(nearestSunday.getTime() + 4 * 7 * 24 * 3600 * 1000L);
                form.setStartDate(dateFormat.format(startDate));
                form.setEndDate(dateFormat.format(endDate));
            }

            // If numerical filter type was specified then add it to list of existing filters set so far
            PipelineNumericalFilterType numericalFilterType = form.getNumericalFilterType();
            if (numericalFilterType != null) {
                Double minValue = form.getNumericalFilterMinValue();
                Double maxValue = form.getNumericalFilterMaxValue();
                if ((minValue != null) || (maxValue != null)) {
                    if (minValue == null) {
                        minValue = 0D;
                    }
                    if (maxValue == null) {
                        maxValue = 1000000000D;
                    }
                    form.setNumericalFilter(numericalFilterType, minValue, maxValue);
                    form.setNumericalFilterType(PipelineNumericalFilterType.PRIZE);
                    form.setNumericalFilterMinValue(null);
                    form.setNumericalFilterMaxValue(null);
                }
            }

            // Define the collections to aggregate various data from pipeline service
            Set<String> clients = new TreeSet<String>();
            Map<Date, PipelineSummaryDTO> summaries = new TreeMap<Date, PipelineSummaryDTO>();
            List<CommonPipelineData> pipelineDetails = new ArrayList<CommonPipelineData>();
            Map<String, PipelineLaunchedContestsDTO> clientStats = new TreeMap<String, PipelineLaunchedContestsDTO>();
            Map<String, PipelineLaunchedContestsDTO> personStats = new TreeMap<String, PipelineLaunchedContestsDTO>();
            Map<String, PipelineLaunchedContestsDTO> copilotStats = new TreeMap<String, PipelineLaunchedContestsDTO>();
            Map<String, PipelineLaunchedContestsDTO> projectStats = new TreeMap<String, PipelineLaunchedContestsDTO>();
            Map<String, PipelineLaunchedContestsDTO> categoryStats = new TreeMap<String, PipelineLaunchedContestsDTO>();
            Map<String, PipelineLaunchedContestsDTO> billingStats = new TreeMap<String, PipelineLaunchedContestsDTO>();

            // Get pipeline data based on criteria provided by the user and evaluate various view data based on data
            // returned by service
            PipelineServiceFacade pipelineServiceFacade = getPipelineServiceFacade();
            List<CommonPipelineData> dataList = pipelineServiceFacade.getCommonPipelineData(currentUser, startDate,
                                                                                            endDate, false);
            for (CommonPipelineData data : dataList) {
                if (matchesFormParameters(data)) {
                    pipelineDetails.add(data);
                    clients.add(data.getClientName());

                    // Collect summary data
                    Date weekOf = getWeekOf(data.getStartDate());
                    PipelineSummaryDTO summary;
                    if (!summaries.containsKey(weekOf)) {
                        summary = new PipelineSummaryDTO();
                        summary.setWeekOf(weekOf);
                        summary.setIsTotal(false);
                        summaries.put(weekOf, summary);
                    } else {
                        summary = summaries.get(weekOf);
                    }

                    Double contestFee = data.getContestFee();
                    if (contestFee != null) {
                        summary.setContestsFee(summary.getContestsFee() + contestFee);
                    }

                    Double specReviewPayment = data.getSpecReviewPayment();
                    if (specReviewPayment != null) {
                        summary.setMemberCosts(summary.getMemberCosts() + specReviewPayment);
                    }

                    Double drPayment = data.getDr();
                    if (drPayment != null) {
                        summary.setMemberCosts(summary.getMemberCosts() + drPayment);
                    }

                    Double reviewPayment = data.getReviewPayment();
                    if (reviewPayment != null) {
                        summary.setMemberCosts(summary.getMemberCosts() + reviewPayment);
                    }

                    Double totalPrize = data.getTotalPrize();
                    if (totalPrize != null) {
                        summary.setMemberCosts(summary.getMemberCosts() + totalPrize);
                    }

                    summary.setAllContestsCount(summary.getAllContestsCount() + 1);

                    String contestStatus = data.getSname();
                    boolean isDraft = ContestStatus.DRAFT.getName().equals(contestStatus);
                    boolean isScheduled = ContestStatus.SCHEDULED.getName().equals(contestStatus);
                    boolean isCancelled = ContestStatus.CANCELLED.getName().equals(contestStatus);
                    boolean isLaunched = !(isDraft || isScheduled || isCancelled);

                    if (isLaunched) {
                        summary.setLaunchedContestsCount(summary.getLaunchedContestsCount() + 1);
                    }

                    // Collect scheduled contests
                    collectScheduledLaunchedContestStats(clientStats, isScheduled, isLaunched, data.getClientName());
                    collectScheduledLaunchedContestStats(categoryStats, isScheduled, isLaunched,
                                                         data.getContestCategory());
                    collectScheduledLaunchedContestStats(projectStats, isScheduled, isLaunched, data.getPname());
                    collectScheduledLaunchedContestStats(billingStats, isScheduled, isLaunched, data.getCpname());

                    String[] copilots = data.getCopilots();
                    if (copilots != null) {
                        for (String copilot : copilots) {
                            collectScheduledLaunchedContestStats(copilotStats, isScheduled, isLaunched, copilot);
                            collectScheduledLaunchedContestStats(personStats, isScheduled, isLaunched, copilot);
                        }
                    }
                    String manager = data.getManager();
                    if (manager != null && manager.trim().length() > 0) {
                        collectScheduledLaunchedContestStats(personStats, isScheduled, isLaunched, manager);
                    }
                }
            }

            // Calculate total summary
            List<PipelineSummaryDTO> summariesList = new ArrayList<PipelineSummaryDTO>(summaries.values());
            PipelineSummaryDTO totalSummary = new PipelineSummaryDTO();
            totalSummary.setIsTotal(true);
            for (PipelineSummaryDTO summary : summariesList) {
                totalSummary.setMemberCosts(summary.getMemberCosts() + totalSummary.getMemberCosts());
                totalSummary.setContestsFee(summary.getContestsFee() + totalSummary.getContestsFee());
                totalSummary.setLaunchedContestsCount(summary.getLaunchedContestsCount()
                                                      + totalSummary.getLaunchedContestsCount());
                totalSummary.setAllContestsCount(summary.getAllContestsCount() + totalSummary.getAllContestsCount());
            }
            summariesList.add(totalSummary);

            // Set view data with collected pipeline data
            getViewData().setClients(new ArrayList<String>(clients));
            getViewData().setSummaries(summariesList);
            getViewData().setContests(pipelineDetails);
            getViewData().setClientScheduledLaunchedContestStats(
                new ArrayList<PipelineLaunchedContestsDTO>(clientStats.values()));
            getViewData().setPersonScheduledLaunchedContestStats(
                new ArrayList<PipelineLaunchedContestsDTO>(personStats.values()));
            getViewData().setCopilotScheduledLaunchedContestStats(
                new ArrayList<PipelineLaunchedContestsDTO>(copilotStats.values()));
            getViewData().setProjectScheduledLaunchedContestStats(
                new ArrayList<PipelineLaunchedContestsDTO>(projectStats.values()));
            getViewData().setCategoriesScheduledLaunchedContestStats(
                new ArrayList<PipelineLaunchedContestsDTO>(categoryStats.values()));
            getViewData().setBillingprojectScheduledLaunchedContestStats(
                new ArrayList<PipelineLaunchedContestsDTO>(billingStats.values()));
        } else {
            // Get the clients for current user
            List<Project> clientBillingProjects = getProjectServiceFacade().getClientProjectsByUser(currentUser);
            Set<String> clients = new HashSet<String>();
            for (Project project : clientBillingProjects) {
                Client client = project.getClient();
                if (client != null) {
                    clients.add(client.getName());
                }
            }
            // for contest without client
            clients.add("One Off");
            getViewData().setClients(new ArrayList<String>(clients));
            getFormData().setClients(clients.toArray(new String[clients.size()]));
            SimpleDateFormat dateFormat = new SimpleDateFormat(DirectUtils.DATE_FORMAT);
            Date nearestSunday = getSundayDate(new Date());
            Date endDate = new Date(nearestSunday.getTime() + 4 * 7 * 24 * 3600 * 1000L);
            getFormData().setStartDate(dateFormat.format(nearestSunday));
            getFormData().setEndDate(dateFormat.format(endDate));
        }

        // For normal request flow prepare various data to be displayed to user
        if (!getFormData().isExcel()) {
            // Set projects data
            List<ProjectBriefDTO> projects = DataProvider.getUserProjects(currentUser.getUserId());
            UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
            userProjectsDTO.setProjects(projects);
            getViewData().setUserProjects(userProjectsDTO);

            // Set current project contests
            ProjectBriefDTO currentProject = this.sessionData.getCurrentProjectContext();
            if (currentProject != null) {
                List<TypedContestBriefDTO> contests
                    = DataProvider.getProjectTypedContests(currentUser.getUserId(), currentProject.getId());
                this.sessionData.setCurrentProjectContests(contests);
            }
        }
    }

    /**
     * <p>Collects the statistics for a single contest for specified source (client, person, copilot, etc).</p>
     *
     * @param stats a <code>Map</code> collecting the statistics on scheduled/launched contests.
     * @param scheduled <code>true</code> if contest is scheduled; <code>false</code> otherwise.
     * @param launched <code>true</code> if contest is launched; <code>false</code> otherwise.
     * @param source a <code>String</code> referencing the source for the statistics.
     */
    private void collectScheduledLaunchedContestStats(Map<String, PipelineLaunchedContestsDTO> stats,
                                                      boolean scheduled, boolean launched, String source) {
        if (scheduled || launched) {
            if (!stats.containsKey(source)) {
                PipelineLaunchedContestsDTO stat = new PipelineLaunchedContestsDTO();
                stat.setSource(source);
                stats.put(source, stat);
            }
            PipelineLaunchedContestsDTO stat = stats.get(source);
            if (scheduled) {
                stat.setScheduledContestsCount(stat.getScheduledContestsCount() + 1);
            } else {
                stat.setLaunchedContestsCount(stat.getLaunchedContestsCount() + 1);
            }
        }
    }

    /**
     * <p>Checks if specified contest matches the parameters of the form submitted by user.</p>
     *
     * @param contest a <code>CommonPipelineData</code> 
     * @return <code>true</code> if contest matches criteria; <code>false</code> otherwise.
     */
    private boolean matchesFormParameters(CommonPipelineData contest) {
        DashboardPipelineReportForm form = getFormData();
        boolean matches = true;

        // Check against contest type filter
        ContestType[] contestTypes = form.getContestTypes();
        if ((contestTypes != null) && (contestTypes.length > 0)) {
            boolean matchesContestType = false;
            for (ContestType type : contestTypes) {
                if ((contest.isStudio() && type == ContestType.STUDIO)  
                        || (!contest.isStudio() && !type.isStudio() && type.getId() == contest.getContestTypeId())) {
                    matchesContestType = true;
                    break;
                }
            }
            matches = matchesContestType;
        }

        // Check against contest status filter
        if (matches) {
            ContestStatus[] contestStatuses = form.getContestStatuses();
            if ((contestStatuses != null) && (contestStatuses.length > 0)) {
                boolean matchesContestStatus = false;
                for (ContestStatus status : contestStatuses) {
                    if (status.getName().equalsIgnoreCase(contest.getSname())) {
                        matchesContestStatus = true;
                        break;
                    }
                }
                matches = matchesContestStatus;
            }
        }

        // Check against client filter
        if (matches) {
            String[] clients = form.getClients();
            if ((clients != null) && (clients.length > 0)) {
                boolean matchesClient = false;
                for (String client : clients) {
                    if (client.equalsIgnoreCase(contest.getClientName())) {
                        matchesClient = true;
                        break;
                    }
                    else if ((contest.getClientName() == null || contest.getClientName().equals("")) 
                           && client.equals("One Off"))
                    {
                        matchesClient = true;
                        break;
                    }
                }
                matches = matchesClient;
            }
        }

        // Check against reposts filter
        if (matches) {
            Boolean wasReposted = contest.getWasReposted();
            if (!form.getShowReposts()) {
                matches = (wasReposted == null) || !wasReposted;
            }
        }

        // Check against numerical filters
        if (matches) {
            PipelineNumericalFilterType[] numericalFilterTypes = form.getNumericalFilterTypes();
            if (numericalFilterTypes != null) {
                Double[] numericalFilterMinValues = form.getNumericalFilterMinValues();
                Double[] numericalFilterMaxValues = form.getNumericalFilterMaxValues();
                for (int i = 0; (matches) && (i < numericalFilterTypes.length); i++) {
                    PipelineNumericalFilterType numericalFilterType = numericalFilterTypes[i];
                    if (numericalFilterType != null) {
                        Double minValue = numericalFilterMinValues[i];
                        Double maxValue = numericalFilterMaxValues[i];
                        if (numericalFilterType == PipelineNumericalFilterType.CONTEST_FEE) {
                            Double fee = contest.getContestFee();
                            matches = (fee != null) && (minValue.compareTo(fee) <= 0) && (maxValue.compareTo(fee) >= 0);
                        } else if (numericalFilterType == PipelineNumericalFilterType.DR_POINTS) {
                            Double dr = contest.getDr();
                            matches = (dr != null) && minValue.compareTo(dr) <= 0 && (maxValue.compareTo(dr) >= 0);
                        } else if (numericalFilterType == PipelineNumericalFilterType.DURATION) {
                            long hours = contest.getDurationInHrs();
                            matches = (minValue.compareTo(hours * 1D) <= 0) && (maxValue.compareTo(hours * 1D) >= 0);
                        } else if (numericalFilterType == PipelineNumericalFilterType.PRIZE) {
                            Double totalPrize = contest.getTotalPrize();
                            matches = (totalPrize != null) && (minValue.compareTo(totalPrize) <= 0)
                                      && (maxValue.compareTo(totalPrize) >= 0);
                        } else if (numericalFilterType == PipelineNumericalFilterType.REVIEW_COST) {
                            Double reviewPayment = contest.getReviewPayment();
                            matches = (reviewPayment != null) && (minValue.compareTo(reviewPayment) <= 0)
                                      && (maxValue.compareTo(reviewPayment) >= 0);
                        } else if (numericalFilterType == PipelineNumericalFilterType.SPEC_REVIEW_COST) {
                            Double specReviewPayment = contest.getSpecReviewPayment();
                            matches = (specReviewPayment != null) && (minValue.compareTo(specReviewPayment) <= 0)
                                      && (maxValue.compareTo(specReviewPayment) >= 0);
                        }
                    }
                }
            }
        }

        return matches;
    }

    /**
     * <p>Gets the date for first day of week which specified date corresponds to.</p>
     *
     * @param date an <code>XMLGregorianCalendar</code> providing the date. 
     * @return a <code>Date</code> providing the date for first dayof week.
     */
    private Date getWeekOf(XMLGregorianCalendar date) {
        Calendar weekOf = Calendar.getInstance();
        weekOf.setTime(date.toGregorianCalendar().getTime());
        weekOf.set(Calendar.HOUR, 0);
        weekOf.set(Calendar.MINUTE, 0);
        weekOf.set(Calendar.SECOND, 0);
        weekOf.set(Calendar.MILLISECOND, 0);
        weekOf.set(Calendar.HOUR_OF_DAY, 0);
        weekOf.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        return weekOf.getTime();
    }

    /**
     * <p>Gets the date for nearest preceding Sunday which specified date corresponds to.</p>
     *
     * @param date an <code>Date</code> providing the date.
     * @return a <code>Date</code> providing the date for nearest preceding Sunday.
     */
    private Date getSundayDate(Date date) {
        Calendar weekOf = Calendar.getInstance();
        weekOf.setTime(date);
        weekOf.set(Calendar.HOUR, 0);
        weekOf.set(Calendar.MINUTE, 0);
        weekOf.set(Calendar.SECOND, 0);
        weekOf.set(Calendar.MILLISECOND, 0);
        weekOf.set(Calendar.HOUR_OF_DAY, 0);
        weekOf.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        return weekOf.getTime();
    }
}
