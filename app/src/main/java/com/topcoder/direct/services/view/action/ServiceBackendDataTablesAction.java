/*
 * Copyright (C) 2014 - 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.direct.services.view.dto.contest.ContestStatus;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This abstract class provide supports for the action which wants to interact with jquery datatables (v1.9) via
 * ajax requests.
 * </p>
 *
 * <p>
 * Version 1.1 (TopCoder Direct - Challenges Section Filters Panel)
 * <ul>
 *     <li>Added {@link #challengeTypes} and its getter</li>
 *     <li>Added {@link #challengeStatus} and its getter</li>
 *     <li>Added {@link #customers} and its getter</li>
 *     <li>Added {@link #projects} and its getter and setter</li>
 *     <li>Added {@link #customerFilter} and its getter and setter</li>
 *     <li>Added {@link #projectFilter} and its getter and setter</li>
 *     <li>Added {@link #challengeStatusFilter} and its getter and setter</li>
 *     <li>Added {@link #challengeTypeFilter} and its getter and setter</li>
 *     <li>Added method {@link #setupFilterPanel()}</li>
 *     <li>Added method {@link #getFiltersQuery()}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.2 (TopCoder Direct - Add date filters to Challenges section filter panel)
 * <ul>
 *     <li>Added {@link #startDateFrom} and its getter and setter</li>
 *     <li>Added {@link #startDateTo} and its getter and setter</li>
 *     <li>Added {@link #endDateFrom} and its getter and setter</li>
 *     <li>Added {@link #endDateTo} and its getter and setter</li>
 *     <li>Updated {@link #getFiltersQuery()} to include the date filters above</li>
 * </ul>
 * </p>
 * @author GreatKevin, Veve
 * @version 1.2
 */
public abstract class ServiceBackendDataTablesAction extends AbstractAction {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ServiceBackendDataTablesAction.class);

    /**
     * The display start in the request.
     */
    private int iDisplayStart;

    /**
     * The display length in the request.
     */
    private int iDisplayLength;

    /**
     * The index of sorting column, start from 0.
     */
    private int iSortCol_0 = -1;

    /**
     * The order of the sorting. "asc" or "desc"
     */
    private String sSortDir_0;

    /**
     * The echo value used by datatables plugin.
     */
    private String sEcho;

    /**
     * The service part of the API.
     */
    private String serviceURL;

    /**
     * The challenge types options in filter panel
     *
     * @since 1.1
     */
    private Map<Long, String> challengeTypes;

    /**
     * The challenge status options in filter panel.
     *
     * @since 1.1
     */
    private Map<ContestStatus, String> challengeStatus;

    /**
     * The customer options in filter panel.
     * @since 1.1
     */
    private Map<Long, String> customers;

    /**
     * The project options in filter panel.
     *
     * @since 1.1
     */
    private List<ProjectBriefDTO> projects;

    /**
     * The customer filter.
     *
     * @since 1.1
     */
    private String customerFilter;

    /**
     * The project filter.
     *
     * @since 1.1
     */
    private String projectFilter;

    /**
     * The challenge status filter.
     *
     * @since 1.1
     */
    private String challengeStatusFilter;

    /**
     * The challenge type filter.
     *
     * @since 1.1
     */
    private String challengeTypeFilter;

    /**
     * The start date from filter.
     *
     * @since 1.2
     */
    private String startDateFrom;

    /**
     * The start date to filter.
     *
     * @since 1.2
     */
    private String startDateTo;

    /**
     * The end date from filter.
     *
     * @since 1.2
     */
    private String endDateFrom;

    /**
     * The end date to filter.
     *
     * @since 1.2
     */
    private String endDateTo;

    /**
     * The max pagination size.
     */
    private static final int MAX_PAGINATION_SIZE = Integer.MAX_VALUE;

    /**
     * The HTML template for the challenge details URL.
     */
    protected static final String CHALLENGE_DETAILS_TD_HTML = "<a class=\"longWordsBreak\" href=\"/direct/contest/detail.action?projectId=%d\">%s</a>";

    /**
     * The HTML template for the copilot posting details URL.
     */
    protected static final String COPILOT_POSTING_DETAILS_TD_HTML = "<a class=\"longWordsBreak\" href=\"/direct/copilot/copilotContestDetails.action?projectId=%d\">%s</a>";

    /**
     * The HTML template for the project overview details URL.
     */
    protected static final String PROJECT_OVERVIEW_TD_HTML = "<a href=\"../projectOverview?formData.projectId=%d\">%s</a>";

    /**
     * The error message throw when accessing the REST service API.
     */
    protected static final String ERROR_MESSAGE_FORMAT = "Service URL:%s, HTTP Status Code:%d, Error Message:%s";

    /**
     * The jackson object mapping which is used to deserialize json return from API to domain model.
     */
    protected static final ObjectMapper objectMapper;

    /**
     * <p>A static <code>Map</code> mapping the existing contest statuses to their textual presentations.</p>
     *
     * @since 1.1
     */
    private static final Map<ContestStatus, String> CONTEST_STATUSES;


    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy HH:mm"));

        CONTEST_STATUSES = new LinkedHashMap<ContestStatus, String>();
        CONTEST_STATUSES.put(ContestStatus.ACTIVE, ContestStatus.ACTIVE.getName());
        CONTEST_STATUSES.put(ContestStatus.DRAFT, ContestStatus.DRAFT.getName());
        CONTEST_STATUSES.put(ContestStatus.CANCELLED, ContestStatus.DRAFT.getName());
        CONTEST_STATUSES.put(ContestStatus.COMPLETED, ContestStatus.COMPLETED.getName());


    }

    /**
     * Setup the options to displayed in filter panel.
     *
     * @throws Exception if any error.
     * @since 1.1
     */
    protected void setupFilterPanel() throws Exception {
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        // Get the list of available project categories
        this.challengeTypes = DataProvider.getAllProjectCategories();

        // Get all the clients accessible by current user
        this.customers = DirectUtils.getAllClients(currentUser);

        this.challengeStatus = CONTEST_STATUSES;

        this.projects = DataProvider.getUserProjects(currentUser.getUserId());
    }

    /**
     * Build the service end point for API.
     *
     * @param parameters the additional parameters set in the URI.
     * @return the built URI.
     * @throws URISyntaxException if the syntax is error.
     */
    protected URI buildServiceEndPoint(Map<String, String> parameters) throws URISyntaxException, UnsupportedEncodingException {

        int pageSize = getiDisplayLength() == -1 ? MAX_PAGINATION_SIZE : getiDisplayLength();


        URIBuilder builder = new URIBuilder(getServiceURL());

        builder.setParameter("offset", String.valueOf(getiDisplayStart()))
                .setParameter("limit", String.valueOf(pageSize));

        String filters = "";
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                if ("filter".equals(key)) {
                    filters = URLEncoder.encode(parameters.get(key), "UTF-8");
                } else {
                    builder.setParameter(key, parameters.get(key));
                }
            }
        }

        return new URI(new StringBuilder(builder.build().toString()).append("&filter=").append(filters).toString());

    }

    /**
     * Gets the error message from the http status code.
     *
     * @param httpStatusCode the http status code.
     * @return the error message.
     */
    protected static String getErrorMessage(int httpStatusCode) {
        if (httpStatusCode == HttpStatus.SC_UNAUTHORIZED) {
            return "Unauthorized access to the service";
        } else if (httpStatusCode == HttpStatus.SC_BAD_REQUEST) {
            return "Bad Request to the service";
        } else if (httpStatusCode == HttpStatus.SC_NOT_FOUND) {
            return "The request service is not found";
        } else if (httpStatusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            return "Internal Server Error";
        }

        return "";
    }

    protected JsonNode getJsonResultFromAPI(URI apiEndPoint) throws Exception {
        logger.debug("Get JSON result from API: " + apiEndPoint);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JsonNode jsonNode = null;

        try {
            // specify the get request
            HttpGet getRequest = new HttpGet(apiEndPoint);

            String jwtToken = getSessionData().getToken();

            getRequest.setHeader(HttpHeaders.AUTHORIZATION,
                    "Bearer " + jwtToken);

            getRequest.addHeader(HttpHeaders.ACCEPT, "application/json");

            HttpResponse httpResponse = httpClient.execute(getRequest);
            HttpEntity entity = httpResponse.getEntity();

            if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception(String.format(ERROR_MESSAGE_FORMAT,
                        getRequest.getURI(),
                        httpResponse.getStatusLine().getStatusCode(),
                        getErrorMessage(httpResponse.getStatusLine().getStatusCode())));
            }

            jsonNode = objectMapper.readTree(entity.getContent());
        } finally {
            // release resources anyway for http client
            httpClient.getConnectionManager().shutdown();
        }

        return jsonNode;
    }

    /**
     * Gets the displayStart.
     *
     * @return the displayStart.
     */
    public int getiDisplayStart() {
        return iDisplayStart;
    }

    /**
     * Sets the displayStart.
     *
     * @param iDisplayStart the display start.
     */
    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    /**
     * Gets the displayLength.
     *
     * @return the displayLength.
     */
    public int getiDisplayLength() {
        return iDisplayLength;
    }

    /**
     * Sets the displayLength.
     *
     * @param iDisplayLength the displayLength.
     */
    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    /**
     * Gets the sEcho.
     *
     * @return the sEcho.
     */
    public String getsEcho() {
        return sEcho;
    }

    /**
     * Sets the sEcho.
     *
     * @param sEcho the sEcho.
     */
    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    /**
     * Gets the service URL.
     *
     * @return the service URL.
     */
    public String getServiceURL() {
        return serviceURL;
    }

    /**
     * Sets the service URL
     *
     * @param serviceURL the service URL.
     */
    public void setServiceURL(String serviceURL) {
        this.serviceURL = serviceURL;
    }

    /**
     * Gets the sort column index.
     *
     * @return the sort column index.
     */
    public int getiSortCol_0() {
        return iSortCol_0;
    }

    /**
     * Sets the sort column index.
     *
     * @param iSortCol_0 the sort column index.
     */
    public void setiSortCol_0(int iSortCol_0) {
        this.iSortCol_0 = iSortCol_0;
    }

    /**
     * Gets the sort order.
     *
     * @return the sort order.
     */
    public String getsSortDir_0() {
        return sSortDir_0;
    }

    /**
     * Gets the sort order.
     *
     * @param sSortDir_0 the sort order.
     */
    public void setsSortDir_0(String sSortDir_0) {
        this.sSortDir_0 = sSortDir_0;
    }

    /**
     * Gets the challenge types.
     *
     * @return the challenge types.
     * @since 1.1
     */
    public Map<Long, String> getChallengeTypes() {
        return challengeTypes;
    }

    /**
     * Gets the challenge status.
     *
     * @return the challenge status.
     * @since 1.1
     */
    public Map<ContestStatus, String> getChallengeStatus() {
        return challengeStatus;
    }

    /**
     * Gets the customers.
     *
     * @return the customers.
     * @since 1.1
     */
    public Map<Long, String> getCustomers() {
        return customers;
    }

    /**
     * Gets the projects.
     *
     * @return the projects.
     * @since 1.1
     */
    public List<ProjectBriefDTO> getProjects() {
        return projects;
    }

    /**
     * Gets the customer filter value.
     *
     * @return the customer filter value.
     * @since 1.1
     */
    public String getCustomerFilter() {
        return customerFilter;
    }

    /**
     * Sets the customer filter value.
     *
     * @param customerFilter the customer filter value.
     * @since 1.1
     */
    public void setCustomerFilter(String customerFilter) {
        this.customerFilter = customerFilter;
    }

    /**
     * Gets the project filter value.
     *
     * @return the project filter value.
     * @since 1.1
     */
    public String getProjectFilter() {
        return projectFilter;
    }

    /**
     * Sets the project filter value.
     *
     * @param projectFilter the project filter value.
     * @since 1.1
     */
    public void setProjectFilter(String projectFilter) {
        this.projectFilter = projectFilter;
    }

    /**
     * Gets the challenge status filter.
     *
     * @return the challenge status filter.
     * @since 1.1
     */
    public String getChallengeStatusFilter() {
        return challengeStatusFilter;
    }

    /**
     * Sets the challenge status filter value.
     *
     * @param challengeStatusFilter the challenge status filter.
     * @since 1.1
     */
    public void setChallengeStatusFilter(String challengeStatusFilter) {
        this.challengeStatusFilter = challengeStatusFilter;
    }

    /**
     * Gets the challenge type filter.
     *
     * @return the challenge type filter.
     * @since 1.1
     */
    public String getChallengeTypeFilter() {
        return challengeTypeFilter;
    }

    /**
     * Sets the challenge type filter.
     *
     * @param challengeTypeFilter the challenge type filter.
     * @since 1.1
     */
    public void setChallengeTypeFilter(String challengeTypeFilter) {
        this.challengeTypeFilter = challengeTypeFilter;
    }

    /**
     * Gets the filter query.
     *
     * @return the filter query.
     * @since 1.1
     */
    protected String getFiltersQuery() {
        StringBuilder str = new StringBuilder();
        List<String> filters = new ArrayList<String>();
        if (getCustomerFilter() != null && !getCustomerFilter().equalsIgnoreCase("all")) {
            filters.add("clientId=" + getCustomerFilter());
        }
        if (getProjectFilter() != null && !getProjectFilter().equalsIgnoreCase("all")) {
            filters.add("directProjectId=" + getProjectFilter());
        }
        if (getChallengeStatusFilter() != null && !getChallengeStatusFilter().equalsIgnoreCase("all")) {
            filters.add("challengeStatus=" + getChallengeStatusFilter());
        }
        if (getChallengeTypeFilter() != null && !getChallengeTypeFilter().equalsIgnoreCase("all")) {
            filters.add("challengeType=" + getChallengeTypeFilter());
        }
        if (getStartDateFrom() != null && getStartDateFrom().trim().length() > 0) {
            filters.add("startDateFrom=" + getStartDateFrom());
        }
        if (getStartDateTo() != null && getStartDateTo().trim().length() > 0) {
            filters.add("startDateTo=" + getStartDateTo());
        }
        if (getEndDateFrom() != null && getEndDateFrom().trim().length() > 0) {
            filters.add("endDateFrom=" + getEndDateFrom());
        }
        if (getEndDateTo() != null && getEndDateTo().trim().length() > 0) {
            filters.add("endDateTo=" + getEndDateTo());
        }

        for (int i = 0, len = filters.size(); i < len; ++i) {
            if (i > 0) {
                str.append('&');
            }
            str.append(filters.get(i));
        }

        return str.toString();
    }

    /**
     * Get the start date from.
     *
     * @return the start date from.
     *
     * @since 1.2
     */
    public String getStartDateFrom() {
        return startDateFrom;
    }

    /**
     * Sets the start date from.
     *
     * @param startDateFrom the start date from.
     *
     * @since 1.2
     */
    public void setStartDateFrom(String startDateFrom) {
        this.startDateFrom = startDateFrom;
    }

    /**
     * Gets the start date to.
     *
     * @return the start date to.
     * @since 1.2
     */
    public String getStartDateTo() {
        return startDateTo;
    }

    /**
     * Sets the start date to.
     *
     * @param startDateTo the start date to.
     * @since 1.2
     */
    public void setStartDateTo(String startDateTo) {
        this.startDateTo = startDateTo;
    }

    /**
     * Gets the end date from.
     *
     * @return the end date from.
     * @since 1.2
     */
    public String getEndDateFrom() {
        return endDateFrom;
    }

    /**
     * Sets the end date from.
     *
     * @param endDateFrom the end date from.
     * @since 1.2
     */
    public void setEndDateFrom(String endDateFrom) {
        this.endDateFrom = endDateFrom;
    }

    /**
     * Gets the end date to.
     *
     * @return the end date to.
     * @since 1.2
     */
    public String getEndDateTo() {
        return endDateTo;
    }

    /**
     * Sets the end date to.
     *
     * @param endDateTo the end date to.
     * @since 1.2
     */
    public void setEndDateTo(String endDateTo) {
        this.endDateTo = endDateTo;
    }
}
