/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.direct.services.configs.ServerConfiguration;
import com.topcoder.direct.services.view.util.DirectUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.Cookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * <p>
 * This abstract class provide supports for the action which wants to interact with jquery datatables (v1.9) via
 * ajax requests.
 * </p>
 *
 * @author GreatKevin
 * @version 1.0
 */
public abstract class ServiceBackendDataTablesAction extends AbstractAction {

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

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy HH:mm"));
    }

    /**
     * Build the service end point for API.
     *
     * @param parameters the additional parameters set in the URI.
     * @return the built URI.
     * @throws URISyntaxException if the syntax is error.
     */
    protected URI buildServiceEndPoint(Map<String, String> parameters) throws URISyntaxException {

        int pageSize = getIDisplayLength() == -1 ? MAX_PAGINATION_SIZE : getIDisplayLength();


        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost(ServerConfiguration.DIRECT_API_SERVICE_ENDPOINT).setPath(getServiceURL())
                .setParameter("offset", String.valueOf(getIDisplayStart()))
                .setParameter("limit", String.valueOf(pageSize));

        if (parameters != null) {
            for (String key : parameters.keySet()) {
                builder.setParameter(key, parameters.get(key));
            }
        }

        return builder.build();
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

        DefaultHttpClient httpClient = new DefaultHttpClient();
        JsonNode jsonNode = null;

        try {
            // specify the get request
            HttpGet getRequest = new HttpGet(apiEndPoint);

            Cookie jwtCookie = DirectUtils.getCookieFromRequest(ServletActionContext.getRequest(),
                    ServerConfiguration.JWT_COOOKIE_KEY);

            if (jwtCookie == null) {
                throw new Exception("The jwt cookie for the authorized user could not be loaded");
            }

            getRequest.setHeader(HttpHeaders.AUTHORIZATION,
                    "Bearer " + jwtCookie.getValue());

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
    public int getIDisplayStart() {
        return iDisplayStart;
    }

    /**
     * Sets the displayStart.
     *
     * @param iDisplayStart the display start.
     */
    public void setIDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    /**
     * Gets the displayLength.
     *
     * @return the displayLength.
     */
    public int getIDisplayLength() {
        return iDisplayLength;
    }

    /**
     * Sets the displayLength.
     *
     * @param iDisplayLength the displayLength.
     */
    public void setIDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    /**
     * Gets the sEcho.
     *
     * @return the sEcho.
     */
    public String getSEcho() {
        return sEcho;
    }

    /**
     * Sets the sEcho.
     *
     * @param sEcho the sEcho.
     */
    public void setSEcho(String sEcho) {
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
    public int getISortCol_0() {
        return iSortCol_0;
    }

    /**
     * Sets the sort column index.
     *
     * @param iSortCol_0 the sort column index.
     */
    public void setISortCol_0(int iSortCol_0) {
        this.iSortCol_0 = iSortCol_0;
    }

    /**
     * Gets the sort order.
     *
     * @return the sort order.
     */
    public String getSSortDir_0() {
        return sSortDir_0;
    }

    /**
     * Gets the sort order.
     *
     * @param sSortDir_0 the sort order.
     */
    public void setSSortDir_0(String sSortDir_0) {
        this.sSortDir_0 = sSortDir_0;
    }
}
