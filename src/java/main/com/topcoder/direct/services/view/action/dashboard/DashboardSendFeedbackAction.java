/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.TCSEmailMessage;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;


/**
 * This action handles the request of sending the feedback email.
 *
 * @author TCSASSEMBLER
 */
public class DashboardSendFeedbackAction extends BaseDirectStrutsAction {
    /**
     * Private constant specifying name for email subject.
     *
     */
    private static final String FEEDBACK_SUBJECT_NAME = "cockpit feedback";

    /**
     * Private constant specifying the target email address to send the feedback.
     *
     */
    private static final String FEEDBACK_TO_ADDRESS = "support@topcoder.com";

    /**
     * Private constant specifying the 'from' email address to send the feedback.
     *
     */
    private static final String FEEDBACK_FROM_ADDRESS = "support@topcoder.com";

    /**
     * <p>A <code>String</code> providing the feedback content.</p>
     */
    private String feedbackContent;

    /**
     * <p>A <code>String</code> providing the URL where the feedback dialog at when user sends the support.</p>
     */
    private String feedbackURL;

    /**
     * <p>Constructs new <code>DashboardSendFeedbackAction</code> instance. This implementation does nothing.</p>
     */
    public DashboardSendFeedbackAction() {
    }

    /**
     * <p>Gets the feedback content.</p>
     *
     * @return a <code>String</code> providing the feedback content.
     */
    public String getFeedbackContent() {
        return this.feedbackContent;
    }

    /**
     * <p>Sets the feedback content.</p>
     *
     * @param feedbackContent a <code>String</code> providing the feedback content.
     */
    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    /**
     * <p>Gets the feedback URL.</p>
     *
     * @return a <code>String</code> providing the URL where the feedback dialog at when user sends the support.
     */
    public String getFeedbackURL() {
        return this.feedbackURL;
    }

    /**
     * <p>Sets the feedback content.</p>
     *
     * @param feedbackURL a <code>String</code> providing the URL where the feedback dialog at when user sends the support.
     */
    public void setFeedbackURL(String feedbackURL) {
        this.feedbackURL = feedbackURL;
    }

    /**
     * The main logic of the action.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        long userId = getSessionData().getCurrentUser().getUserId();
        String reporterEmail = getUserService().getEmailAddress(userId);

        // Get current time.
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, MMM d, yyyy hh:mm a", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("EST5EDT"));
        String timeLine = dateFormat.format(new Date()).toString() + " EDT\n";

        // Get reporter information.
        String handle = getUserService().getUserHandle(userId);
        String reporterLine = "Reporter Handle: " + handle + "\n";
        reporterLine += "Reporter Email: " + reporterEmail + "\n";

        // Get feedback information.
        String urlLine = "Source URL: " + this.feedbackURL + "\n";
        String header = timeLine + reporterLine + urlLine;
        String splitLine = "------------------------------------------------------------------------------------------------------------------------\n\n";
        String content = this.feedbackContent;

        // Create a TCSEmailMessage to be sent
        TCSEmailMessage email = new TCSEmailMessage();

        // Set subject message body.
        email.setSubject(FEEDBACK_SUBJECT_NAME);
        email.setFromAddress(FEEDBACK_FROM_ADDRESS);
        email.setBody(header + splitLine + content);
        email.addToAddress(FEEDBACK_TO_ADDRESS, TCSEmailMessage.TO);

        // Send email
        EmailEngine.send(email);
    }
}
