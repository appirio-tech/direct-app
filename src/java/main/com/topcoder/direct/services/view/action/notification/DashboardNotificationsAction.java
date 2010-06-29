/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.notification;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.notification.ProjectNotification;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Presents notification settings page in dashboard.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DashboardNotificationsAction extends BaseDirectStrutsAction {

    private CommonDTO viewData =  new CommonDTO();

    private  SessionData sessionData;
    
    /**
     * List of current notifications.
     */
    private List<ProjectNotification> notifications;

    /**
     * Executes action by fetching necessary data from the back-end.
     *
     * @throws Exception if any error occurs
     */
    protected void executeAction() throws Exception {
        TCSubject user = getUser();
        HttpServletRequest request = DirectUtils.getServletRequest();

        HttpSession session = request.getSession(false);
        
        if (session != null) {
            sessionData = new SessionData(session);
        }

        request.setAttribute("currentUserHandle", sessionData.getCurrentUserHandle());

        notifications = getContestServiceFacade().getNotificationsForUser(user, user.getUserId());

        List<ProjectBriefDTO> projects = DataProvider.getUserProjects(sessionData.getCurrentUserId());

        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);
        viewData.setUserProjects(userProjectsDTO);
    }

    /**
     * Get the user from session.
     *
     * @return the user.
     */
    public static TCSubject getUser() {
        HttpServletRequest request = DirectUtils.getServletRequest();
        if (request == null) {
            return null;
        }
        return new SessionData(request.getSession()).getCurrentUser();
    }

    /**
     * Returns list of current notifications
     *
     * @return list of current notifications
     */
    public List<ProjectNotification> getNotifications() {
        return notifications;
    }

    /**
     * Sets list of current notifications
     *
     * @param notifications list of current notifications
     */
    public void setNotifications(List<ProjectNotification> notifications) {
        this.notifications = notifications;
    }

    public CommonDTO getViewData() {
        return viewData;
    }

    public SessionData getSessionData() {
        return sessionData;
    }
}
