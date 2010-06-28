/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.direct.services.view.dto.contest.ContestBriefDTO;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.management.project.SimpleProjectContestData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.CommonProjectContestData;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.contest.SimpleContestData;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.shared.common.TCContext;

/**
 * Direct common utility class.
 *
 * <p>Version 1.1 (Direct Registrants List assembly) change notes:
 *   <ul>
 *     <li>Added {@link #getContestStats(ContestServiceFacade, TCSubject, long)} method.</li>
 *     <li>Added {@link #getTCSubjectFromSession()} method.</li>
 *   </ul>
 * </p>
 *
 * @author BeBetter, isv
 * @version 1.1
 */
public final class DirectUtils {
    /**
     * Constant for date format.
     */
    public static final String DATE_FORMAT = "MM/dd/yyyy";

    /**
     * Gets date from date string.
     *
     * @param dateString the date string. see <code>DATE_FORMAT</code> for the format.
     * @return the <code>Date</code> object. it might be null.
     */
    public static Date getDate(String dateString) {
        if (dateString == null || dateString.trim().length() == 0) {
            return null;
        }

        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Gets date from the <code>XMLGregorianCalendar</code> object.
     *
     * @param calendarDate <code>XMLGregorianCalendar</code> object.
     * @return the <code>Date</code> object
     */
    public static Date getDate(XMLGregorianCalendar calendarDate) {
        if (calendarDate == null) {
            return null;
        }

        return calendarDate.toGregorianCalendar().getTime();
    }

    /**
     * Gets date without time portion.
     *
     * @param date the original date
     * @return the date without time information
     */
    public static Date getDateWithoutTime(Date date) {
        DateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        try {
            return formatter.parse(formatter.format(date));
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Gets <code>ContestServiceFacade</code> service bean.
     *
     * @return the <code>ContestServiceFacade</code> service bean
     * @throws NamingException if any naming exception occurs
     */
    public static ContestServiceFacade getContestServiceFacade() throws NamingException {
        Context context = TCContext.getContext(DirectProperties.CONTEST_SERVICE_FACADE_CONTEXT_FACTORY,
            DirectProperties.CONTEST_SERVICE_FACADE_PROVIDER_URL);
        return (ContestServiceFacade) context.lookup(DirectProperties.CONTEST_SERVICE_FACADE_JNDI_NAME);
    }

    public static HttpServletRequest getServletRequest() {
        return (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
    }

    /**
     * <p>Gets the statistics for the specified contest.</p>
     *
     * @param contestServiceFacade a <code>ContestServiceFacade</code> to be used for communicating to backend services.
     * @param currentUser a <code>TCSubject</code> representing the current user.
     * @param contestId a <code>long</code> providing the ID of a contest.
     * @return a <code>ContestStatsDTO</code> providing the statistics for specified contest.
     * @throws PersistenceException if an unexpected error occurs while accessing the persistent data store.
     * @since 1.1
     */
    public static ContestStatsDTO getContestStats(ContestServiceFacade contestServiceFacade, TCSubject currentUser,
                                                  long contestId) throws PersistenceException {

        List<CommonProjectContestData> userContests = contestServiceFacade.getCommonProjectContestData(currentUser);
        for (CommonProjectContestData contestData : userContests) {
            if (contestData.getContestId() == contestId) {
                ProjectBriefDTO project = new ProjectBriefDTO();
                project.setId(contestData.getProjectId());
                project.setName(contestData.getPname());

                ContestBriefDTO contest = new ContestBriefDTO();
                contest.setId(contestData.getContestId());
                contest.setTitle(contestData.getCname());
                contest.setProject(project);

                ContestStatsDTO dto = new ContestStatsDTO();
                dto.setEndTime(DirectUtils.getDate(contestData.getEndDate()));
                dto.setStartTime(DirectUtils.getDate(contestData.getStartDate()));
                dto.setSubmissionsNumber(contestData.getNum_sub());
                dto.setRegistrantsNumber(contestData.getNum_reg());
                dto.setForumPostsNumber(contestData.getNum_for());
                dto.setContest(contest);
                return dto;
            }
        }
        return null;
    }

    /**
     * <p>Gets the TCSubject instance from session.</p>
     *
     * @return the TCSubject instance from session.
     * @since 1.1
     */
    public static TCSubject getTCSubjectFromSession() {
        HttpServletRequest request = getServletRequest();
        if(request == null) {
            return null;
        }
        return new SessionData(request.getSession()).getCurrentUser();
    }
}
