/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.setting.notification;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.notification.UserPreferenceDTO;
import com.topcoder.security.TCSubject;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.ejb.user.UserPreference;

import java.util.List;

/**
 * <p>
 * A <code>Struts</code> action used for handling udpate preference
 * request.
 * </p>
 * 
 * @author tangzx
 * @version 1.0
 * @since BUGR-4597
 */
public class UpdateUserPreferencesAction extends BaseDirectStrutsAction {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -3127179206775870166L;
    
    /**
     * The preferences.
     */
    private List<UserPreferenceDTO> userPreferences;
    
    /**
     * Get the userPreferences field.
     *
     * @return the userPreferences
     */
    public List<UserPreferenceDTO> getUserPreferences() {
        return userPreferences;
    }

    /**
     * Set the userPreferences field.
     *
     * @param userPreferences the userPreferences to set
     */
    public void setUserPreferences(List<UserPreferenceDTO> userPreferences) {
        this.userPreferences = userPreferences;
    }

    /**
     * <p>
     * Handles the incoming request. Update user preferences.
     * </p>
     * 
     * @throws Exception
     *             if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        TCSubject user = getCurrentUser();
        UserPreference userPreference = getUserPreferenceHome().create();
        
        for (UserPreferenceDTO preference : userPreferences) {
            userPreference.setValue(user.getUserId(),
                    preference.getPreferenceId(), preference.getValue(),
                    DBMS.COMMON_OLTP_DATASOURCE_NAME);    
        }
    }
}
