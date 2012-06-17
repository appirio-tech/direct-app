/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.TCSEmailMessage;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * This action handles the request of sending the feedback email.
 *
 * @author TCSASSEMBLER
 */
public class DashboardSendFeedbackAction extends BaseDirectStrutsAction {
    /**
     * Private constant specifying project final fix phase name.
     *
     */
    private static final String FEEDBACK_SUBJECT_NAME = "cockpit feedback";
    
    /**
     * Private constant specifying project final fix phase name.
     *
     */
    private static final String FEEDBACK_TO_ADDRESS = "support@topcoder.com";
    
    /**
     * <p>A <code>String</code> providing the feedback content.</p>
     */
    private String feedbackContent;
    
    /**
     * <p>Constructs new <code>DashboardSendFeedbackAction</code> instance. This implementation does nothing.</p>
     */
    public DashboardSendFeedbackAction() {
    }
    
    /**
     * <p>Gets the feedback content.</p>
     *
     * @return a <code>String</code> providing the reason for rejecting the final fixes.
     */
    public String getFeedbackContent() {
        return this.feedbackContent;
    }

    /**
     * <p>Sets the feedback content.</p>
     *
     * @param feedbackContent a <code>String</code> providing the feedback.
     */
    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    /**
     * The main logic of the action.
     *
     * @throws Exception if there is any error.
     */
    @Override
    protected void executeAction() throws Exception {
        String content = this.feedbackContent;
        long userId = getSessionData().getCurrentUser().getUserId();
        String fromAddr = getUserService().getEmailAddress(userId);
        // Create a TCSEmailMessage to be sent
        TCSEmailMessage email = new TCSEmailMessage();

        // Set subject message body.
        email.setSubject(FEEDBACK_SUBJECT_NAME);
        email.setFromAddress(fromAddr);
        email.setBody(content);
        email.addToAddress(FEEDBACK_TO_ADDRESS, TCSEmailMessage.TO);

        // Send email
        EmailEngine.send(email);
    }
}
