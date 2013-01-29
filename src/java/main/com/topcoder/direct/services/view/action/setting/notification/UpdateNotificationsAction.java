/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.setting.notification;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.notification.ContestNotification;
import com.topcoder.service.facade.contest.notification.ProjectNotification;
import com.topcoder.service.facade.project.notification.DirectProjectNotification;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Ajax action to update notifications.
 *
 * <p>
 *  Updated in version 1.1 (Release Assembly - TC Cockpit Project Forum Settings) changes:
 *  <ul>
 *      <li>Added method {@link #updateDirectProjectNotifications()} method</li>
 *  </ul>
 * </p>
 *
 * @author GreatKevin
 * @version 1.1
 */
public class UpdateNotificationsAction extends BaseDirectStrutsAction {

    /**
     * The response code to return in updateDirectProjectNotifications for unchanged.
     *
     * @since 1.1
     */
    private static final String UNCHANGED_RESPONSE_CODE = "unchanged";

    /**
     * The response code to return in  updateDirectProjectNotifications for updasted.
     *
     * @since 1.1
     */
    private static final String UPDATED_RESPONSE_CODE = "updated";

    /**
     * The directProjectNotifications to update.
     *
     * @since 1.1
     */
    private List<DirectProjectNotification> directProjectNotifications = new ArrayList<DirectProjectNotification>();

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

    /**
     * Ajax action handler for the update direct project notifications request.
     *
     * @return the result code
     * @since 1.1
     */
    public String updateDirectProjectNotifications() {
        Map<String, String> result = new HashMap<String, String>();

        try {
            if (getDirectProjectNotifications() == null || getDirectProjectNotifications().size() == 0) {
                result.put("response", UNCHANGED_RESPONSE_CODE);
            } else {

                TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

                if (getProjectServiceFacade() == null) {
                    throw new IllegalStateException("Project Service Facade is not initialized");
                }

                getProjectServiceFacade().updateProjectNotifications(currentUser, currentUser.getUserId(), getDirectProjectNotifications());

                result.put("response", UPDATED_RESPONSE_CODE);
            }

            setResult(result);

        } catch (Throwable e) {
            // set the error message into the ajax response
            if (getModel() != null) {
                setResult(e);
            }

            return ERROR;
        }

        return SUCCESS;
    }

    /**
     * Gets the direct project notifications.
     *
     * @return the direct project notifications.
     * @since 1.1
     */
    public List<DirectProjectNotification> getDirectProjectNotifications() {
        return directProjectNotifications;
    }

    /**
     * Sets the direct project notifications.
     *
     * @param directProjectNotifications the direct project notifications.
     *
     * @since 1.1
     */
    public void setDirectProjectNotifications(List<DirectProjectNotification> directProjectNotifications) {
        this.directProjectNotifications = directProjectNotifications;
    }
}
