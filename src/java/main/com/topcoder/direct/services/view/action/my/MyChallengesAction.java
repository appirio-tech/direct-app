/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.my;

import com.topcoder.direct.services.configs.ServerConfiguration;
import com.topcoder.direct.services.view.action.ServiceBackendDataTablesAction;
import com.topcoder.direct.services.view.dto.my.Challenge;
import com.topcoder.direct.services.view.dto.my.RestResult;
import com.topcoder.direct.services.view.util.DirectUtils;
import org.apache.struts2.ServletActionContext;
import org.codehaus.jackson.JsonNode;

import javax.servlet.http.Cookie;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <p>
 * The action to handle the my challenges request.
 * </p>
 *
 * <p>
 * Version 1.1 (TopCoder Direct - Challenges Section Filters Panel)
 * - Handle the filter panel parameters
 * </p>
 *
 * @author GreatKevin
 * @version 1.1
 */
public class MyChallengesAction extends ServiceBackendDataTablesAction {
    /**
     * The date format to format the date to display in challenge data.
     */
    private DateFormat challengeDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    /**
     * The mapping between display column index and column name to sort.
     */
    private static final Map<Integer, String> SORTING_MAP = new HashMap<Integer, String>();

    /**
     * Execute, forward to jsp page.
     *
     * @return the result code
     * @throws Exception if any error.
     */
    @Override
    public String execute() throws Exception {
        Cookie jwtCookie = DirectUtils.getCookieFromRequest(ServletActionContext.getRequest(),
                ServerConfiguration.JWT_COOOKIE_KEY);

        if (jwtCookie == null) {
            return ANONYMOUS;
        }
        // populate filter data
        this.setupFilterPanel();

        return SUCCESS;
    }

    static {
        SORTING_MAP.put(0, "clientname");
        SORTING_MAP.put(1, "billingname");
        SORTING_MAP.put(2, "directprojectname");
        SORTING_MAP.put(3, "challengename");
        SORTING_MAP.put(4, "challengetype");
        SORTING_MAP.put(5, "challengestatus");
        SORTING_MAP.put(6, "challengestartdate");
        SORTING_MAP.put(7, "challengeenddate");
        SORTING_MAP.put(8, "challengecreator");
    }


    /**
     * Handles the ajax request to get my challenges json data.
     *
     * @return the result code.
     */
    public String getMyChallenges() {
        try {
            Map<String, Object> result = new HashMap<String, Object>();

            Map<String, String> params = new HashMap<String, String>();
            params.put("metadata", "true");

            String sortColumn = "id";
            String sortOrder = " desc null last";
            if (getISortCol_0() >= 0 && getSSortDir_0() != null && getSSortDir_0().trim().length() > 0) {
                // there is sorting parameters
                sortColumn = SORTING_MAP.containsKey(getISortCol_0()) ? SORTING_MAP.get(getISortCol_0()) : "id";
                if (getSSortDir_0().trim().equalsIgnoreCase("asc")) {
                    sortOrder = " asc null first";
                }
            }

            params.put("orderBy", sortColumn + sortOrder);

            String filtersQuery = getFiltersQuery();

            if (filtersQuery.trim().length() > 0) {
                params.put("filter", filtersQuery);
            }

            JsonNode jsonNode = getJsonResultFromAPI(buildServiceEndPoint(params));

            RestResult<Challenge> restResult = objectMapper.readValue(jsonNode.get("result"),
                    objectMapper.getTypeFactory().constructParametricType(RestResult.class, Challenge.class));

            if (restResult != null) {
                result.put("iTotalRecords", restResult.getMetadata().getTotalCount());

                // no filtering - It should be the same as iTotalRecords
                result.put("iTotalDisplayRecords", restResult.getMetadata().getTotalCount());

                result.put("sEcho", getSEcho());

                List<List<String>> challengesData = new ArrayList<List<String>>();

                for (Challenge c : restResult.getContent()) {
                    List<String> challengeData = new ArrayList<String>();

//                    <th>Client Name</th>
                    challengeData.add(c.getClientName());

//                    <th>Billing Account Name</th>
                    challengeData.add(c.getBillingName());

//                    <th>Direct Project Name</th>
                    challengeData.add(
                            String.format(PROJECT_OVERVIEW_TD_HTML, c.getDirectProjectId(), c.getDirectProjectName()));
//                    <th>Challenge Name</th>
                    if (c.getChallengeType().equals("Copilot Posting")) {
                        challengeData.add(
                            String.format(COPILOT_POSTING_DETAILS_TD_HTML, c.getId(), c.getChallengeName()));
                    } else {
                    challengeData.add(
                            String.format(CHALLENGE_DETAILS_TD_HTML, c.getId(), c.getChallengeName()));
                    }
//                    <th>Challenge Type</th>
                    challengeData.add(c.getChallengeType());

                    // challenge status
                    challengeData.add(c.getChallengeStatus());

//                    <th>Challenge Start Date</th>
                    challengeData.add(challengeDateFormat.format(c.getChallengeStartDate()));

//                    <th>Challenge End Date</th>
                    challengeData.add(challengeDateFormat.format(c.getChallengeEndDate()));

                    challengeData.add(c.getChallengeCreator());

//                    <th>Total Prize</th>

                    challengeData.add(NumberFormat.getCurrencyInstance(Locale.US).format(c.getTotalPrize()));

                    challengesData.add(challengeData);
                }

                result.put("aaData", challengesData);
            }

            setResult(result);

        } catch (Throwable e) {
            if (getModel() != null) {
                setResult(e);
            }
        }
        return SUCCESS;
    }
}
