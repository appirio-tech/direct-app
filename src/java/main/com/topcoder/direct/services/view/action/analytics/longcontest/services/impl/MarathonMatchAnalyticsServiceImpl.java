/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.analytics.longcontest.services.impl;

import com.topcoder.commons.utils.LoggingWrapperUtility;
import com.topcoder.commons.utils.ValidationUtility;
import com.topcoder.direct.services.view.action.analytics.longcontest.services.MarathonMatchAnalyticsService;
import com.topcoder.direct.services.view.action.analytics.longcontest.services.MarathonMatchAnalyticsServiceException;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.tc.rest.longcontest.resources.CompetitorResource;
import com.topcoder.web.tc.rest.longcontest.resources.MarathonMatchDetailsResource;
import com.topcoder.web.tc.rest.longcontest.resources.SearchResult;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.rs.security.oauth2.client.OAuthClientUtils;
import org.apache.cxf.rs.security.oauth2.common.ClientAccessToken;
import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of MarathonMatchAnalyticsService interface, it will used to call the remote rest service.
 *
 * <p>
 * <strong>Thread Safety: </strong> This class is mutable and thus is not thread
 * safe.
 * </p>
 *
 * @author Ghost_141
 * @since 1.0 (PoC Assembly - TopCoder Cockpit - Tracking Marathon Matches Progress)
 * @version 1.0
 */
public class MarathonMatchAnalyticsServiceImpl implements MarathonMatchAnalyticsService {

    /**
     * This field represents the qualified name of this class.
     */
    private static final String CLASS_NAME = MarathonMatchAnalyticsServiceImpl.class.getName();

    /**
     * The default value of page number.
     * It will be used in rest api.
     */
    private static final String PAGE_NUMBER = "1";

    /**
     * The default value of page size.
     * It will be used in rest api.
     */
    private static final String PAGE_SIZE = String.valueOf(Integer.MAX_VALUE);

    /**
     * The default value of sorting order.
     * It will be used in rest api.
     */
    private static final String SORTING_ORDER = "desc";

    /**
     * The default value of sorting field.
     * It will be used in rest api.
     */
    public static final String SORTING_FIELD = "8";

    /**
     * Log instance.
     */
    private Log logger = LogManager.getLog(CLASS_NAME);

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
     * The data format used to format date string from rest response.
     */
    private DateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy HH:mm z");

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
        final String signature = CLASS_NAME + "#checkInit()";
        try {
            getMMBasicInfoEndPointUrl = fixUrl("getMMBasicInfoEndPointUrl", getMMBasicInfoEndPointUrl);
            getAllMMSubmissionsEndPointUrl = fixUrl("getAllMMSubmissionsEndPointUrl", getAllMMSubmissionsEndPointUrl);
            getMMRegistrantsEndPointUrl = fixUrl("getMMRegistrantsEndPointUrl", getMMRegistrantsEndPointUrl);
            getMatchResultsEndPointUrl = fixUrl("getMatchResultsEndPointUrl", getMatchResultsEndPointUrl);
            getCompetitorDetailsEndPointUrl =
                    fixUrl("getCompetitorDetailsEndPointUrl", getCompetitorDetailsEndPointUrl);
            remoteServiceBaseUrl = fixUrl("remoteServiceBaseUrl", remoteServiceBaseUrl);

            ValidationUtility.checkNotNullNorEmptyAfterTrimming(clientId, "clientId", IllegalArgumentException.class);
            ValidationUtility.checkNotNullNorEmptyAfterTrimming(clientSecret,
                    "clientSecret", IllegalArgumentException.class);
        } catch (IllegalArgumentException iae) {
            LoggingWrapperUtility.logException(logger, signature, iae);
            throw iae;
        }
    }

    /**
     * Check the url whether start with '/' or end with '/'. If it's not fix it.
     * If the url is fine, just return them back.
     *
     * @param urlName the name of the url.
     * @param value the url value.
     * @return the fixed value.
     */
    private String fixUrl(String urlName, String value) {
        if(value == null || value.trim().length() == 0) {
            throw new IllegalArgumentException("The " + urlName + " should not be null nor empty");
        }
        value = value.trim();
        if(!value.startsWith("/") && !value.startsWith("http")) {
            value = "/" + value.trim();
        }
        if(value.endsWith("/") && value.length() != 1) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
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
        final String signature = CLASS_NAME + "#getMarathonMatchDetails(roundId, groupType, accessToken)";
        try {
            LoggingWrapperUtility.logEntrance(logger, signature, new String[] {"roundId", "groupType", "accessToken"},
                    new Object[] {roundId, groupType, accessToken});

            ValidationUtility.checkPositive(roundId, "roundId", IllegalArgumentException.class);
            ValidationUtility.checkNotNullNorEmptyAfterTrimming(groupType, "groupType", IllegalArgumentException.class);
            ValidationUtility.checkNotNullNorEmptyAfterTrimming(accessToken, "accessToken",
                    IllegalArgumentException.class);

            String jsonResult = callRestService(getMMBasicInfoEndPointUrl,
                    createParameterMap(new String[] {"groupType"}, new String[] {groupType}),
                    createParameterMap(new String[] {"{round_id}"}, new String[] {String.valueOf(roundId)}),
                    accessToken);
            ObjectMapper mapper = createObjectMapper();

            MarathonMatchDetailsResource result =
                    mapper.readValue(jsonResult.getBytes(), new TypeReference<MarathonMatchDetailsResource>() {});

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {result});

            return result;
        } catch (MarathonMatchAnalyticsServiceException e) {
            LoggingWrapperUtility.logException(logger, signature, e);
            throw e;
        } catch (IOException ioe) {
            LoggingWrapperUtility.logException(logger, signature, ioe);
            throw new MarathonMatchAnalyticsServiceException("Error occurred when process json string.", ioe);
        }
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
        final String signature = CLASS_NAME
                + "#getRegistrants(roundId, acessToken)";
        try {
            LoggingWrapperUtility.logEntrance(logger, signature,
                    new String[] {"roundId", "accessToken"},
                    new Object[] {roundId, accessToken});

            ValidationUtility.checkPositive(roundId, "roundId", IllegalArgumentException.class);
            ValidationUtility.checkNotNullNorEmptyAfterTrimming(accessToken, "accessToken",
                    IllegalArgumentException.class);

            String jsonResult = callRestService(getMMRegistrantsEndPointUrl,
                    createParameterMap(new String[] {"pageSize", "pageNumber", "sortingOrder", "sortingField"},
                            new String[] {PAGE_SIZE, PAGE_NUMBER, SORTING_ORDER, SORTING_FIELD}),
                    createParameterMap(new String[] {"{round_id}"}, new String[] {String.valueOf(roundId)}),
                    accessToken);
            ObjectMapper mapper = createObjectMapper();

            SearchResult<CompetitorResource> result =
                    mapper.readValue(jsonResult.getBytes(), new TypeReference<SearchResult<CompetitorResource>>() {});

            LoggingWrapperUtility.logExit(logger, signature, new Object[] {result});

            return result;
        } catch (MarathonMatchAnalyticsServiceException e) {
            LoggingWrapperUtility.logException(logger, signature, e);
            throw e;
        } catch (IOException e) {
            LoggingWrapperUtility.logException(logger, signature, e);
            throw new MarathonMatchAnalyticsServiceException("Error occurred when process json string.", e);
        }
    }

    /**
     * Build a json node based on an existing response.
     *
     * @param url the url.
     * @param queryParams the map of query parameters.
     * @param pathParams the map of path parameters.
     * @param accessToken the access token.
     * @return the json node of the response.
     * @throws IOException if any error occurs when reading data.
     * @throws IOException if any error occurs when reading data.
     */
    private String callRestService(String url, Map<String, String> queryParams, Map<String, String> pathParams,
            String accessToken) throws IOException, MarathonMatchAnalyticsServiceException {
        WebClient client = getWebClient();

        for(String key : queryParams.keySet()) {
            client.query(key, queryParams.get(key));
        }

        // Set the api key for the rest call.
        client.query("user_key", apiKey);

        for(String key : pathParams.keySet()) {
            url = url.replace(key, pathParams.get(key));
        }

        OAuthClientUtils.Consumer consumer = new OAuthClientUtils.Consumer(clientId, clientSecret);
        ClientAccessToken clientAccessToken = new ClientAccessToken(OAuthConstants.BEARER_TOKEN_TYPE, accessToken);
        String authHeader = OAuthClientUtils.createAuthorizationHeader(consumer, clientAccessToken);
        client.replaceHeader("Authorization", authHeader);

        Response response = client.path(url).get();

        if (response.getStatus() != 200) {
            throw new MarathonMatchAnalyticsServiceException("The rest call is failed.");
        } else {
            return IOUtils.toString((InputStream) response.getEntity(), "UTF-8");
        }
    }

    /**
     * Create the map by given input keys and values.
     *
     * @param key the keys
     * @param value the values.
     * @return the map.
     */
    private Map<String, String> createParameterMap(String[] key, String[] value) {
        Map<String, String> map = new HashMap<String, String>();
        for(int i = 0; i < key.length; i++) {
            map.put(key[i], value[i]);
        }
        return map;
    }

    /**
     * Gets the <code>WebClient</code> instance to make call to remote service.
     *
     * @return the <code>WebClient</code> instance
     */
    private WebClient getWebClient() {
        return WebClient.create(getRemoteServiceBaseUrl());
    }

    /**
     * Create the object mapper.
     *
     * @return the object mapper instance.
     */
    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(dateFormat);
        return mapper;
    }

    /**
     * Gets remote service base url.
     *
     * @return the remote service base url
     */
    public String getRemoteServiceBaseUrl() {
        return remoteServiceBaseUrl;
    }

    /**
     * Sets remote service base url.
     *
     * @param remoteServiceBaseUrl the remote service base url
     */
    public void setRemoteServiceBaseUrl(String remoteServiceBaseUrl) {
        this.remoteServiceBaseUrl = remoteServiceBaseUrl;
    }

    /**
     * Gets get mM basic info end point url.
     *
     * @return the get mM basic info end point url
     */
    public String getGetMMBasicInfoEndPointUrl() {
        return getMMBasicInfoEndPointUrl;
    }

    /**
     * Sets get mM basic info end point url.
     *
     * @param getMMBasicInfoEndPointUrl the get mM basic info end point url
     */
    public void setGetMMBasicInfoEndPointUrl(String getMMBasicInfoEndPointUrl) {
        this.getMMBasicInfoEndPointUrl = getMMBasicInfoEndPointUrl;
    }

    /**
     * Gets get mM registrants end point url.
     *
     * @return the get mM registrants end point url
     */
    public String getGetMMRegistrantsEndPointUrl() {
        return getMMRegistrantsEndPointUrl;
    }

    /**
     * Sets get mM registrants end point url.
     *
     * @param getMMRegistrantsEndPointUrl the get mM registrants end point url
     */
    public void setGetMMRegistrantsEndPointUrl(String getMMRegistrantsEndPointUrl) {
        this.getMMRegistrantsEndPointUrl = getMMRegistrantsEndPointUrl;
    }

    /**
     * Gets get all mM submissions end point url.
     *
     * @return the get all mM submissions end point url
     */
    public String getGetAllMMSubmissionsEndPointUrl() {
        return getAllMMSubmissionsEndPointUrl;
    }

    /**
     * Sets get all mM submissions end point url.
     *
     * @param getAllMMSubmissionsEndPointUrl the get all mM submissions end point url
     */
    public void setGetAllMMSubmissionsEndPointUrl(String getAllMMSubmissionsEndPointUrl) {
        this.getAllMMSubmissionsEndPointUrl = getAllMMSubmissionsEndPointUrl;
    }

    /**
     * Gets get match results end point url.
     *
     * @return the get match results end point url
     */
    public String getGetMatchResultsEndPointUrl() {
        return getMatchResultsEndPointUrl;
    }

    /**
     * Sets get match results end point url.
     *
     * @param getMatchResultsEndPointUrl the get match results end point url
     */
    public void setGetMatchResultsEndPointUrl(String getMatchResultsEndPointUrl) {
        this.getMatchResultsEndPointUrl = getMatchResultsEndPointUrl;
    }

    /**
     * Gets get competitor details end point url.
     *
     * @return the get competitor details end point url
     */
    public String getGetCompetitorDetailsEndPointUrl() {
        return getCompetitorDetailsEndPointUrl;
    }

    /**
     * Sets get competitor details end point url.
     *
     * @param getCompetitorDetailsEndPointUrl the get competitor details end point url
     */
    public void setGetCompetitorDetailsEndPointUrl(String getCompetitorDetailsEndPointUrl) {
        this.getCompetitorDetailsEndPointUrl = getCompetitorDetailsEndPointUrl;
    }

    /**
     * Gets get marathon match listings end point url.
     *
     * @return the get marathon match listings end point url
     */
    public String getGetMarathonMatchListingsEndPointUrl() {
        return getMarathonMatchListingsEndPointUrl;
    }

    /**
     * Sets get marathon match listings end point url.
     *
     * @param getMarathonMatchListingsEndPointUrl the get marathon match listings end point url
     */
    public void setGetMarathonMatchListingsEndPointUrl(String getMarathonMatchListingsEndPointUrl) {
        this.getMarathonMatchListingsEndPointUrl = getMarathonMatchListingsEndPointUrl;
    }

    /**
     * Sets client id.
     *
     * @param clientId the client id
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Sets client secret.
     *
     * @param clientSecret the client secret
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * Sets api key.
     *
     * @param apiKey the api key
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

