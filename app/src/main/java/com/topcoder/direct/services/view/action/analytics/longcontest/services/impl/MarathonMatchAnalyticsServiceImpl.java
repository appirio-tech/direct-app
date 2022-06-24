/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.longcontest.services.impl;

import com.topcoder.direct.services.view.action.analytics.longcontest.services.MarathonMatchAnalyticsServiceException;
import com.topcoder.direct.services.view.action.analytics.longcontest.services.MarathonMatchAnalyticsService;
import com.topcoder.web.tc.rest.longcontest.resources.CompetitorResource;
import com.topcoder.web.tc.rest.longcontest.resources.MarathonMatchDetailsResource;
import com.topcoder.web.tc.rest.longcontest.resources.MarathonMatchItemResource;
import com.topcoder.web.tc.rest.longcontest.resources.MatchResultResource;
import com.topcoder.web.tc.rest.longcontest.resources.SearchResult;
import com.topcoder.web.tc.rest.longcontest.resources.SubmissionResource;
import com.topcoder.web.tc.rest.longcontest.resources.SystemTestResourceWrapper;

/**
 * The implementation of MarathonMatchAnalyticsService interface, it will used to call the remote rest service.
 * This class contains the method to get marathon match details, get registrants for a marathon match contest and
 * get competitor submission history.
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and thus is not thread
 * safe.
 * </p>
 *
 * <p>
 *     Version 1.1 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Competitors Tab
 *     <ol>
 *         <li>Add method {@link #getCompetitorSubmissionsHistory(long, String, int, int, String, String, String)}.</li>
 *         <li>Add static property {@link #HANDLE}</li>
 *     </ol>
 * </p>
 *
 * <p>
 *     Version 1.2 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress -
 *                   Dashboard and Submissions Tab
 *     <ol>
 *         <li>
 *             Add method {@link #getMarathonMatchListings(String, String, String, int, int, String, String, String)}
 *         </li>
 *         <li>
 *             Add method {@link #getMatchResults(long, int, int, String, String, String)}.
 *         </li>
 *         <li>
 *             Fix unhandled exception in {@link #getMarathonMatchDetails(long, String, String)} and
 *             {@link #getRegistrants(long, String)}
 *         </li>
 *     </ol>
 * </p>
 *
 * <p>
 *     Version 1.3 - Release Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress - Results Tab 2
 *     <ol>
 *         <li>Add method {@link #getSystemTests(long, Long, int, int, String, String, String)}.</li>
 *         <li>Add method {@link #getSystemTestsResultsEndPointUrl}.</li>
 *     </ol>
 * </p>
 *
 * @author Ghost_141
 * @since 1.0 (PoC Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress)
 * @version 1.3
 */
@Deprecated
public class MarathonMatchAnalyticsServiceImpl implements MarathonMatchAnalyticsService {

    /**
     * This field represents the qualified name of this class.
     */
    private static final String CLASS_NAME = MarathonMatchAnalyticsServiceImpl.class.getName();

    /**
     * The default value of page number.
     * It will be used in rest api.
     */
    private static final String DEFAULT_PAGE_NUMBER = "1";

    /**
     * The default value of page size.
     * It will be used in rest api.
     */
    private static final String DEFAULT_PAGE_SIZE = String.valueOf(Integer.MAX_VALUE);

    /**
     * The default value of sorting order.
     * It will be used in rest api.
     */
    private static final String DEFAULT_SORTING_ORDER = "desc";

    /**
     * The round id in rest path parameter.
     */
    private static final String ROUND_ID = "{round_id}";

    /**
     * The handle in rest path parameter.
     * @since 1.1
     */
    private static final String HANDLE = "{handle}";

    /**
     * Represent the base url of the remote rest service.
     */
    private String remoteServiceBaseUrl;

    /**
     * The Get mM basic info end point url.
     */
    private String getMMBasicInfoEndPointUrl;

    /**
     * The Get marathon match end point listing url.
     */
    private String getMarathonMatchListingsEndPointUrl;

    /**
     * The Get mM registrants end point url.
     */
    private String getMMRegistrantsEndPointUrl;

    /**
     * The Get all mM submissions end point url.
     */
    private String getAllMMSubmissionsEndPointUrl;

    /**
     * The Get match results end point url.
     */
    private String getMatchResultsEndPointUrl;

    /**
     * The Get competitor details end point url.
     */
    private String getCompetitorDetailsEndPointUrl;

    /**
     * The url for getSystemTests api.
     * @since 1.3
     */
    private String getSystemTestsResultsEndPointUrl;

    /**
     * Represent the client id.
     */
    private String clientId;

    /**
     * Represent the client secret.
     */
    private String clientSecret;

    /**
     * Represent the api key of marathon match rest api.
     */
    private String apiKey;

    /**
     * Check the initialization of bean instance.
     */
    public void checkInit() {
    }

    /**
     * Get the marathon match details by given round id and group type.
     *
     * @param groupType the group type.
     * @param accessToken the access token.
     * @param roundId the round id.
     * @return the instance of MarathonMatchDetailsResource.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     */
    public MarathonMatchDetailsResource getMarathonMatchDetails(long roundId, String groupType, String accessToken)
            throws MarathonMatchAnalyticsServiceException{
       throw new MarathonMatchAnalyticsServiceException("not implemented");
    }

    /**
     * Get the registrants data by calling rest service.
     *
     * @param roundId the round id.
     * @param accessToken the access token.
     * @return the registrants data.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     */
    public SearchResult<CompetitorResource> getRegistrants(long roundId,
            String accessToken) throws MarathonMatchAnalyticsServiceException {
        throw new MarathonMatchAnalyticsServiceException("not implemented");
    }

    /**
     * Get the submission history of one competitor with given round id, handle name of competitor, page size, page
     * number, sorting order, sorting field.
     *
     * @param sortingOrder the sorting order.
     * @param handle the handle name.
     * @param accessToken the access token.
     * @param sortingField the sorting field.
     * @param pageSize the page size.
     * @param pageNumber the page number.
     * @param roundId the round id.
     * @return the search result.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     * @since 1.1
     */
    public SearchResult<SubmissionResource> getCompetitorSubmissionsHistory(long roundId, String handle, int pageSize,
            int pageNumber, String sortingOrder, String sortingField, String accessToken)
            throws MarathonMatchAnalyticsServiceException {
       throw new MarathonMatchAnalyticsServiceException("not implemented"); 
    }

    /**
     * Get the marathon match lists from the TC platform api.
     *
     * @param type the contest type. It can only be 'active', 'past'. Can't be null.
     * @param date the date of matches. Can be null.
     * @param projectId the project that marathon match belong to. Can be null.
     * @param pageSize the page size. Can be null.
     * @param pageNumber the page number. Can be null.
     * @param sortingOrder the sort order of result. It can only be 'desc' and 'asc'. Can be null.
     * @param sortingField the sort field of result. It can only be roundId,fullName,shortName,startDate,endDate,
     *                     winnerHandle,winnerScore,currentlyTopRankingSubmissionHandle,currentlyTopProvisionalScore.
     *                     Can be null.
     * @param accessToken the access token used in rest call.
     * @return the result of marathon match list.
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     * @since 1.2
     */
    public SearchResult<MarathonMatchItemResource> getMarathonMatchListings(String type, String date, String projectId,
            int pageSize, int pageNumber, String sortingOrder, String sortingField, String accessToken)
            throws MarathonMatchAnalyticsServiceException {
        throw new MarathonMatchAnalyticsServiceException("not implemented");
    }

    /**
     * Get the marathon match result from platform api.
     *
     * @param roundId the round id of this marathon match contest.
     * @param pageSize the page size of result.
     * @param pageNumber the page number of result.
     * @param sortingOrder the sorting order of result. Can be null. It can only be 'desc' and 'asc'.
     * @param sortingField the sorting field of result. Can be null. It can only be 'rank', 'handleName', 'coderId',
     *                     'coderRating', 'finalScore', 'provisionalScore'.
     * @param accessToken the access token.
     * @return the result
     * @throws MarathonMatchAnalyticsServiceException if any error occurred.
     * @since 1.2
     */
    public SearchResult<MatchResultResource> getMatchResults(long roundId, int pageSize, int pageNumber,
            String sortingOrder, String sortingField, String accessToken)
            throws MarathonMatchAnalyticsServiceException {
        throw new MarathonMatchAnalyticsServiceException("not implemented");
    }

    /**
     * Get system tests results of specified marathon match.
     *
     * @param roundId the round id of this marathon match contest.
     * @param coderId the coder id of a specified coder.
     * @param coderStartNumber the coder start number.
     * @param testCaseStartNumber the test case start number.
     * @param sortingOrder the sort order. 'asc', 'desc' or null only.
     * @param sortingField the sort field. could be null.
     * @param accessToken the access token.
     * @return the search result of system test resource wrapper.
     * @since 1.3
     */
    public SystemTestResourceWrapper getSystemTests(long roundId, Long coderId, int coderStartNumber,
            int testCaseStartNumber, String sortingField, String sortingOrder, String accessToken)
            throws MarathonMatchAnalyticsServiceException {
        throw new MarathonMatchAnalyticsServiceException("not implemented");
    }

    /**
     * Gets remote service base url.
     *
     * @return the remote service base url
     */
    public String getRemoteServiceBaseUrl() {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Sets remote service base url.
     *
     * @param remoteServiceBaseUrl the remote service base url
     */
    public void setRemoteServiceBaseUrl(String remoteServiceBaseUrl) {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Gets get mM basic info end point url.
     *
     * @return the get mM basic info end point url
     */
    public String getGetMMBasicInfoEndPointUrl() {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Sets get mM basic info end point url.
     *
     * @param getMMBasicInfoEndPointUrl the get mM basic info end point url
     */
    public void setGetMMBasicInfoEndPointUrl(String getMMBasicInfoEndPointUrl) {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Gets get mM registrants end point url.
     *
     * @return the get mM registrants end point url
     */
    public String getGetMMRegistrantsEndPointUrl() {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Sets get mM registrants end point url.
     *
     * @param getMMRegistrantsEndPointUrl the get mM registrants end point url
     */
    public void setGetMMRegistrantsEndPointUrl(String getMMRegistrantsEndPointUrl) {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Gets get all mM submissions end point url.
     *
     * @return the get all mM submissions end point url
     */
    public String getGetAllMMSubmissionsEndPointUrl() {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Sets get all mM submissions end point url.
     *
     * @param getAllMMSubmissionsEndPointUrl the get all mM submissions end point url
     */
    public void setGetAllMMSubmissionsEndPointUrl(String getAllMMSubmissionsEndPointUrl) {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Gets get match results end point url.
     *
     * @return the get match results end point url
     */
    public String getGetMatchResultsEndPointUrl() {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Sets get match results end point url.
     *
     * @param getMatchResultsEndPointUrl the get match results end point url
     */
    public void setGetMatchResultsEndPointUrl(String getMatchResultsEndPointUrl) {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Gets get competitor details end point url.
     *
     * @return the get competitor details end point url
     */
    public String getGetCompetitorDetailsEndPointUrl() {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Sets get competitor details end point url.
     *
     * @param getCompetitorDetailsEndPointUrl the get competitor details end point url
     */
    public void setGetCompetitorDetailsEndPointUrl(String getCompetitorDetailsEndPointUrl) {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Gets get marathon match listings end point url.
     *
     * @return the get marathon match listings end point url
     */
    public String getGetMarathonMatchListingsEndPointUrl() {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Sets get marathon match listings end point url.
     *
     * @param getMarathonMatchListingsEndPointUrl the get marathon match listings end point url
     */
    public void setGetMarathonMatchListingsEndPointUrl(String getMarathonMatchListingsEndPointUrl) {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Sets client id.
     *
     * @param clientId the client id
     */
    public void setClientId(String clientId) {
        throw new IllegalStateException("not implemented");
    }

    /**
     * Sets client secret.
     *
     * @param clientSecret the client secret
     */
    public void setClientSecret(String clientSecret) {
        
    }

    /**
     * Sets api key.
     *
     * @param apiKey the api key
     */
    public void setApiKey(String apiKey) {
        
    }

    /**
     * Gets get system tests results end point url.
     *
     * @return the get system tests results end point url
     * @since 1.3
     */
    public String getGetSystemTestsResultsEndPointUrl() {
        throw new IllegalStateException("not implemented");  
    }

    /**
     * Sets get system tests results end point url.
     *
     * @param getSystemTestsResultsEndPointUrl the get system tests results end point url
     * @since 1.3
     */
    public void setGetSystemTestsResultsEndPointUrl(String getSystemTestsResultsEndPointUrl) {
      
    }
}

