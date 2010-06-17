/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.notification;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.notification.ContestNotification;
import com.topcoder.service.facade.contest.notification.ProjectNotification;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Ajax action to update notifications.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class UpdateNotificationsAction extends BaseDirectStrutsAction {
    /**
     * Executes action by saving data into back-end.
     *
     * @throws Exception if any error occurs
     */
    protected void executeAction() throws Exception {
        TCSubject user = DashboardNotificationsAction.getUser();
        HttpServletRequest request = DirectUtils.getServletRequest();

        // read parameters
        Set<Long> timeline = new HashSet<Long>();
        Set<Long> forum = new HashSet<Long>();
        if (request.getParameterValues("timeline") != null) {
            for (String s : request.getParameterValues("timeline")) {
                timeline.add(Long.parseLong(s));
            }
        }
        if (request.getParameterValues("forum") != null) {
            for (String s : request.getParameterValues("forum")) {
                forum.add(Long.parseLong(s));
            }
        }

        // update notifications
        List<ProjectNotification> notifications =
            getContestServiceFacade().getNotificationsForUser(user, user.getUserId());
        for (ProjectNotification pn : notifications) {
            for (ContestNotification cn : pn.getContestNotifications()) {
                cn.setForumNotification(forum.contains(cn.getContestId()));
                cn.setProjectNotification(timeline.contains(cn.getContestId()));
            }
        }
        getContestServiceFacade().updateNotificationsForUser(user, user.getUserId(), notifications);
    }
}
