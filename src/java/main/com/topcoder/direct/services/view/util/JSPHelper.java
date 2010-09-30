/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.util;

import com.topcoder.direct.services.view.dto.contest.ContestRoundType;
import com.topcoder.direct.services.view.dto.contest.SoftwareSubmissionDTO;
import com.topcoder.direct.services.view.dto.contest.SoftwareSubmissionReviewDTO;
import com.topcoder.service.studio.SubmissionData;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <p>This class provides the set of static functions for use by JSP views.</p>
 *
 *
 * <p>
 * Version 1.1 (Submission Viewer Release 1 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getSubmissionPreviewImageURL(long, String, int, javax.servlet.http.HttpServletRequest)}
 *     method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.1.1 (Direct Software Submission Viewer Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #getReview(SoftwareSubmissionDTO, long)} method.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.1.2 (Direct Software Submission Viewer 4 Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added {@link #isCheckedOut(ContestRoundType, SubmissionData)} method.</li>
 *   </ol>
 * </p>
 *
 * @author isv, TCSDEVELOPER
 * @version 1.1.2
 */
public class JSPHelper {

    /**
     * <p>Constructs new <code>JSPHelper</code> instance. This implementation does nothing.</p>
     */
    private JSPHelper() {
    }

    /**
     * <p>Gets the day from the specified date.</p>
     *
     * @param date a <code>Date</code> providing the date.
     * @return an <code>int</code> providing the day from the specified value.
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * <p>Gets the month from the specified date.</p>
     *
     * @param date a <code>Date</code> providing the date.
     * @return an <code>int</code> providing the month from the specified value (0-based).
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * <p>Gets the year from the specified date.</p>
     *
     * @param date a <code>Date</code> providing the date.
     * @return an <code>int</code> providing the year from the specified value.
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * <p>Gets the textual presentation of specified date.</p>
     *
     * @param date a <code>Date</code> providing the date to be verified.
     * @param pattern a <code>String</code> providing the template for date formatting.
     * @return a <code>String</code> providing the date text.
     */
    public static String getDateText(Date date, String pattern) {
        Date now = new Date();

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);

        if (isSameDay(now, date)) {
            return "Today";
        } else if (isSameDay(date, yesterday.getTime())) {
            return "Yesterday";
        } else if (isSameDay(date, tomorrow.getTime())) {
            return "Tomorrow";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            return formatter.format(date);
        }

    }

    /**
     * <p>Gets the textual description of the period in which the specified date (contest completion) will be reached.
     * </p>
     *
     * @param date a <code>Date</code> providing the date of contest completion.
     * @return a <code>String</code> providing the textual description of the period in which the specified date will
     *         be reached.
     */
    public static String getEndText(Date date) {
        if (date == null) {
            return "";
        } else {
            Date now = new Date();
            if (now.compareTo(date) >= 0) {
                return "Finished";
            } else {
                long diff = date.getTime() - now.getTime();
                long seconds = diff / 1000L;
                long minutes = seconds / 60L;
                long hours = minutes / 60L;
                long days = hours / 24L;
                if (days > 0) {
                    return "Ends in " + days + " days";
                } else if (hours > 0) {
                    return "Ends in " + hours + " hours";
                } else if (minutes > 0) {
                    return "Ends in " + minutes + " minutes";
                } else {
                    return "Ends in " + seconds + " seconds";
                }
            }
        }
    }

    /**
     * <p>Gets the XHTML URL-encoded string for URL for preview image of specified type for specified submission.</p>
     *
     * @param submissionId a <code>long</code> providing the submission ID.
     * @param previewType a <code>String</code> providing the preview image type. The value for this parameter is
     *        expected to be conformant to XHTML standard specification for HTML attribute values. 
     * @param artifactNum an <code>int</code> providing the artifact number.
     * @param request an <code>HttpServletRequest</code> representing incoming request.
     * @return a <code>String</code> providing the XHTML URL-encoded string for URL for retrieving the preview image of
     *         specified type for specified submission.
     * @since 1.1
     */
    public static String getSubmissionPreviewImageURL(long submissionId, String previewType, int artifactNum,
                                                      HttpServletRequest request) {
        String protocol = request.isSecure() ? "https" : "http";
        if (artifactNum <= 0) {
            return protocol + "://www.topcoder.com/direct/cockpit/impersonation/cockpitStudio.do?&amp;sbmid="
                   + submissionId + "&amp;sbt=" + previewType;
        } else {
            return protocol + "://www.topcoder.com/direct/cockpit/impersonation/cockpitStudio.do?&amp;sbmid="
                   + submissionId + "&amp;sbt=" + previewType + "&amp;sfi=" + artifactNum;
        }
    }

    /**
     * <p>Gets the review from specified reviewer for specified submission.</p>
     *
     * @param submission a <code>SoftwareSubmissionDTO</code> providing the details for submission.
     * @param reviewerId a <code>long</code> providing the reviewer ID.
     * @return a <code>SoftwareSubmissionReviewDTO</code> providing the review or <code>null</code> if there is no such
     *         review.
     * @since 1.1.1 
     */
    public static SoftwareSubmissionReviewDTO getReview(SoftwareSubmissionDTO submission, long reviewerId) {
        List<SoftwareSubmissionReviewDTO> reviews = submission.getReviews();
        if (reviews != null) {
            for (SoftwareSubmissionReviewDTO review : reviews) {
                if (review.getReviewer().getId() == reviewerId) {
                    return review;
                }
            }
        }
        return null;
    }

    /**
     * <p>Checks if specified submission is checked out.</p>
     *
     * @param roundType a <code>ContestRoundType</code> referencing the round type.
     * @param submission a <code>SubmissionData</code> providing details for submission.
     * @return <code>true</code> if submission is checked out; <code>false</code> otherwise.
     * @since 1.1.2
     */
    public static boolean isCheckedOut(ContestRoundType roundType, SubmissionData submission) {
        boolean submissionCheckedOut = false;
        if (roundType == ContestRoundType.MILESTONE) {
            if (submission.isAwardMilestonePrize() != null && submission.isAwardMilestonePrize()
                || submission.isPaidFor()) {
                submissionCheckedOut = true;
            }
        } else {
            if (submission.getUserRank() > 0) {
                submissionCheckedOut = true;
            }
        }
        return submissionCheckedOut;
    }

    /**
     * <p>Checks if specified dates represent same day.</p>
     *
     * @param d1 a first date.
     * @param d2 a second date.
     * @return <code>true</code> if dates represent same day; <code>false</code> otherwise.
     */
    private static boolean isSameDay(Date d1, Date d2) {
        return (getDay(d1) == getDay(d2)) & (getMonth(d1) == getMonth(d2)) && (getYear(d1) == getYear(d2));
    }
}
