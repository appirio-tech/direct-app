/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.my;

import com.topcoder.direct.services.configs.ServerConfiguration;
import com.topcoder.direct.services.view.action.ServiceBackendDataTablesAction;
import com.topcoder.direct.services.view.dto.my.Challenge;
import com.topcoder.direct.services.view.dto.my.RestResult;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.service.user.UserService;
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
 * The action to handle the my created challenges request.
 * </p>
 *
 * @author GreatKevin
 * @version 1.0
 */
public class MyCreatedChallengesAction extends ServiceBackendDataTablesAction {

    /**
     * The date format to format the date to display in challenge data.
     */
    private DateFormat challengeDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm 'ET (GMT -400)'");

    /**
     * The user service.
     */
    private UserService userService;

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

        // do nothing, forward to the jsp page directly
        return SUCCESS;
    }


    /**
     * Handles the ajax request to get my challenges json data.
     *
     * @return the result code.
     */
    public String getMyCreatedChallenges() {
        try {
            Map<String, Object> result = new HashMap<String, Object>();

            Map<String, String> params = new HashMap<String, String>();
            params.put("filter",
                    "creator=" + getUserService().getUserHandle(DirectUtils.getTCSubjectFromSession().getUserId()));
            params.put("metadata", "true");
            params.put("orderBy", "id desc null last");

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
                    challengeData.add(
                            String.format(CHALLENGE_DETAILS_TD_HTML, c.getId(), c.getChallengeName()));
//                    <th>Challenge Type</th>
                    challengeData.add(c.getChallengeType());

                    // challenge status
                    challengeData.add(c.getChallengeStatus());

//                    <th>Challenge Start Date</th>
                    challengeData.add(challengeDateFormat.format(c.getChallengeStartDate()));

//                    <th>Challenge End Date</th>
                    challengeData.add(challengeDateFormat.format(c.getChallengeEndDate()));

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


    /**
     * Gets the user service.
     *
     * @return the user service.
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Sets the user service.
     *
     * @param userService the user service.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
