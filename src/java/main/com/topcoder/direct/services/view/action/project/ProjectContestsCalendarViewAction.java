/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.project.ProjectContestDTO;
import com.topcoder.direct.services.view.dto.project.ProjectContestsListDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import org.apache.struts2.ServletActionContext;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  <p>
 *      The action processes the project contests calendar view request and return the result as json.
 *  </p>
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Project Contests Calendar View)
 */
public class ProjectContestsCalendarViewAction extends BaseDirectStrutsAction {

    /**
     * The url used for display the contest details page.
     */
    private static final String CONTEST_URL_PREFIX = "contest/detail.action?projectId=";

    /**
     * The date format for contest start date and end date used in JSON.
     */
    private static final SimpleDateFormat CONTEST_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Represents the draft status used in json.
     */
    private static final String DRAFT = "draft";

    /**
     * Represents the completed status used in json.
     */
    private static final String COMPLETED = "completed";

    /**
     * Represents the cancelled status used in json.
     */
    private static final String CANCELLED = "cancelled";

    /**
     * Represents the active status used in json.
     */
    private static final String ACTIVE = "active";

    /**
     * The direct project id.
     */
    private long directProjectId;

    /**
     * Gets the direct project id.
     *
     * @return the direct project id.
     */
    public long getDirectProjectId() {
        return directProjectId;
    }

    /**
     * Sets the direct project id.
     *
     * @param directProjectId the direct project id to set.
     */
    public void setDirectProjectId(long directProjectId) {
        this.directProjectId = directProjectId;
    }

    /**
     * Gets the contests of the project and transform the data into the JSON that the front end fullcalendar plugin can
     * consume. The JSON data will be returned via AJAX to the front end per request.
     *
     * @throws Exception if any error occurs.
     */
    @Override
    protected void executeAction() throws Exception {

        // get current user from the session
        TCSubject currentUser = getCurrentUser();

        // get the project contests data
        ProjectContestsListDTO projectContests = DataProvider.getProjectContests(currentUser.getUserId(), getDirectProjectId());

        // get contests
        List<ProjectContestDTO> contests = projectContests.getContests();

        // map to store the json result
        Map<String, Object> result = new HashMap<String, Object>();

        List<Map<String, String>> contestsJsonList = new ArrayList<Map<String, String>>();

        result.put("events", contestsJsonList);

        for (ProjectContestDTO c : contests) {

            // map to store data of one contest
            Map<String, String> contestJson = new HashMap<String, String>();

            contestJson.put("id", String.valueOf(c.getContest().getId()));
            contestJson.put("title", c.getContest().getTitle() + " (" + c.getContestType().getName() + ")");
            contestJson.put("url", CONTEST_URL_PREFIX + c.getContest().getId());
            contestJson.put("start", CONTEST_DATE_FORMAT.format(c.getStartTime()));
            contestJson.put("end", CONTEST_DATE_FORMAT.format(c.getEndTime()));
            contestJson.put("status", getJsonContestStatus(c.getStatus().getName()));

            contestsJsonList.add(contestJson);

        }

        // set the current date on TC server
        result.put("today", CONTEST_DATE_FORMAT.format(new Date()));

        setResult(result);

        SessionData sessionData = new SessionData(ServletActionContext.getRequest().getSession());
        sessionData.setCurrentSelectDirectProjectID(getDirectProjectId());
    }

    /**
     * Changes the contest status to the status used in returned json. The contest status like review, submission,
     * registration, appeals etc will be treated as active.
     *
     * @param status the contest status to transform
     * @return the status used in json.
     */
    private static String getJsonContestStatus(String status) {

        if(status.toLowerCase().contains("draft")) {
            return DRAFT;
        } else if (status.toLowerCase().contains("completed")) {
            return COMPLETED;
        } else if (status.toLowerCase().contains("cancelled")) {
            return CANCELLED;
        } else {
            return ACTIVE;
        }

    }

}

