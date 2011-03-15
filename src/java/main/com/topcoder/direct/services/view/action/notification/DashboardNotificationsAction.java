/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.notification;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.notification.UserPreferenceDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;
import com.topcoder.service.facade.contest.notification.ProjectNotification;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.RowNotFoundException;
import com.topcoder.web.ejb.user.UserPreference;
import com.topcoder.web.ejb.user.UserPreferenceHome;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Presents notification settings page in dashboard.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DashboardNotificationsAction extends BaseDirectStrutsAction {
    private static final List<UserPreferenceDTO> PREFERENCES;
    
    private CommonDTO viewData =  new CommonDTO();

    private  SessionData sessionData;
    
    /**
     * List of current notifications.
     */
    private List<ProjectNotification> notifications;
    
    private List<UserPreferenceDTO> preferences;
    
    static {
        PREFERENCES = new ArrayList<UserPreferenceDTO>();
        
        UserPreferenceDTO userPreferenceDTO = new UserPreferenceDTO();
        userPreferenceDTO.setPreferenceId(29);
        userPreferenceDTO.setDesc("Always assign notifications from OR when I am added to a role on the contest");
        PREFERENCES.add(userPreferenceDTO);
        
        userPreferenceDTO = new UserPreferenceDTO();
        userPreferenceDTO.setPreferenceId(30);
        userPreferenceDTO.setDesc("Always assign me a watch on forums related to my competitions");
        PREFERENCES.add(userPreferenceDTO);
    }

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

        UserPreference userPreference = getUserPreferenceHome().create();
        
        preferences = new ArrayList<UserPreferenceDTO>();
        for (UserPreferenceDTO preference : PREFERENCES) {
            String value;

            try {
                value = userPreference.getValue(user.getUserId(),
                        preference.getPreferenceId(),
                        DBMS.COMMON_OLTP_DATASOURCE_NAME);
            } catch (RowNotFoundException e) {
                //e.printStackTrace();
                
                // set default value to false if can't find the row
                value = "false";
                userPreference.createUserPreference(user.getUserId(),
                        preference.getPreferenceId(),
                        DBMS.COMMON_OLTP_DATASOURCE_NAME);
                userPreference.setValue(user.getUserId(),
                        preference.getPreferenceId(), value,
                        DBMS.COMMON_OLTP_DATASOURCE_NAME);
            }
            
            preference.setValue(value);
            preferences.add(preference);
        }
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

    /**
     * Get the preferences field.
     *
     * @return the preferences
     */
    public List<UserPreferenceDTO> getPreferences() {
        return preferences;
    }

    /**
     * Set the preferences field.
     *
     * @param preferences the preferences to set
     */
    public void setPreferences(List<UserPreferenceDTO> preferences) {
        this.preferences = preferences;
    }
}
