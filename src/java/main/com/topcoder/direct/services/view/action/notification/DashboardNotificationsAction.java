/*
 * Copyright (C) 2010 - 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.topcoder.service.facade.project.notification.DirectProjectNotification;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.RowNotFoundException;
import com.topcoder.web.ejb.user.UserPreference;

/**
 * Presents notification settings page in dashboard.
 *
 * <p>
 * Version 1.1 (Release Assembly - Project Contest Fee Management ) Change notes:
 *   <ol>
 *     <li>Added viewContestFeeOption flag to indicate the contest-fee option should be displayed or not</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 1.2 (Release Assembly - TC Cockpit Project Forum Settings) Change notes:
 *   <ol>
 *     <li>Added {@link #projectNotifications} and its getter and setter</li>
 *     <li>Updated {@link #executeAction()} to populate the projectNotifications for the view</li>
 *   </ol>
 * </p>
 *
 * @author GreatKevin
 * @version 1.2
 */
public class DashboardNotificationsAction extends BaseDirectStrutsAction {
    private static final List<UserPreferenceDTO> PREFERENCES;

    private CommonDTO viewData =  new CommonDTO();

    private  SessionData sessionData;

    /**
     * List of current notifications.
     */
    private List<ProjectNotification> notifications;

    /**
     * List of user preference.
     */
    private List<UserPreferenceDTO> preferences;

    /**
     * List of project forum notification settings.
     *
     * @since 1.2
     */
    private List<DirectProjectNotification> projectNotifications;

    /**
     * The user can view contest fee option or not.
     *
     * @since 1.1
     */
    private boolean viewContestFeeOption;

    /**
     * Represents whether the user can sync user with JIRA and wiki.
     */
    private boolean syncUser;
    
    static {
        PREFERENCES = new ArrayList<UserPreferenceDTO>();

        UserPreferenceDTO userPreferenceDTO = new UserPreferenceDTO();
        userPreferenceDTO.setPreferenceId(29);
        userPreferenceDTO.setDesc("Contest Timelines: Receive individual contest status notifications when each contest advances phases.");
        PREFERENCES.add(userPreferenceDTO);

        userPreferenceDTO = new UserPreferenceDTO();
        userPreferenceDTO.setPreferenceId(30);
        userPreferenceDTO.setDesc("Contest Forum Communication: Receive contest forum communication for questions posted in each contest in your project.");
        PREFERENCES.add(userPreferenceDTO);

        userPreferenceDTO = new UserPreferenceDTO();
        userPreferenceDTO.setPreferenceId(31);
        userPreferenceDTO.setDesc("Project Forum Communication: Receive project forum communication for questions posted in your project forum.");
        PREFERENCES.add(userPreferenceDTO);
}

    /**
     * Executes action by fetching necessary data from the back-end.
     *
     * <p>
     *     Updated in version 1.2 (Release Assembly - TC Cockpit Project Forum Settings)
     *     - Populate the projectNotifications to redener in the view.
     * </p>
     *
     * @throws Exception if any error occurs
     */
    protected void executeAction() throws Exception {

        if(getProjectServiceFacade() == null) {
            throw new IllegalStateException("Project Service Facade is not initialized.");
        }

        TCSubject user = getUser();
        HttpServletRequest request = DirectUtils.getServletRequest();

        HttpSession session = request.getSession(false);

        if (session != null) {
            sessionData = new SessionData(session);
        }

        request.setAttribute("currentUserHandle", sessionData.getCurrentUserHandle());

        viewContestFeeOption = DirectUtils.isSuperAdmin(user) || DirectUtils.isTCAccounting(user);
        syncUser = DirectUtils.isTcStaff(user);

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

        projectNotifications = getProjectServiceFacade().getProjectNotifications(user, user.getUserId());
        Collections.sort(projectNotifications, new ProjectNotificationComparator());
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

    /**
     * Getter for the view contest fee option.
     *
     * @return the view contest fee option.
     */
    public boolean isViewContestFeeOption() {
        return viewContestFeeOption;
    }

    /**
     * Setter for the view contest fee option.
     *
     * @param viewContestFeeOption the view contest fee option.
     */
    public void setViewContestFeeOption(boolean viewContestFeeOption) {
        this.viewContestFeeOption = viewContestFeeOption;
    }

    /**
     * Returns whether the user can sync user with JIRA and wiki.
     * 
     * @return the syncUser true if the user can sync user with JIRA and wiki, false otherwise.
     */
    public boolean isSyncUser() {
        return syncUser;
    }

    /**
     * Gets the project notifications.
     *
     * @return the project notifications.
     * @since 1.2
     */
    public List<DirectProjectNotification> getProjectNotifications() {
        return projectNotifications;
    }

    /**
     * Sets the project notifications.
     *
     * @param projectNotifications the project notifications.
     * @since 1.2
     */
    public void setProjectNotifications(List<DirectProjectNotification> projectNotifications) {
        this.projectNotifications = projectNotifications;
    }

    /**
     * The comparator used to sort project notifications by name
     * @since 1.2
     */
    private static class ProjectNotificationComparator implements Comparator<DirectProjectNotification> {
        public int compare(DirectProjectNotification o1, DirectProjectNotification o2) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    }
}
