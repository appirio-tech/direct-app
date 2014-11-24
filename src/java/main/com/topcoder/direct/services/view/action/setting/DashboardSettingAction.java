/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.setting;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

/**
 * This action handles the loading request of settings pages.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (Release Assembly - TopCoder Cockpit Settings Related Pages Refactoring)
 */
public class DashboardSettingAction extends BaseDirectStrutsAction {

    /**
     * The setting type.
     */
    private String settingType;

    /**
     * Sets the setting type.
     *
     * @param settingType the setting type.
     */
    public void setSettingType(String settingType) {
        this.settingType = settingType;
    }

    /**
     * Gets the setting type.
     *
     * @return the setting type.
     */
    public String getSettingType() {
        return settingType;
    }

    /**
     * Action execution - check permission for contest fee and sync user setting page.
     *
     * @throws Exception if any error
     */
    @Override
    protected void executeAction() throws Exception {
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();
        if (settingType.toLowerCase().equals("contestfee") && !(DirectUtils.isTCAccounting(currentUser) || DirectUtils.isSuperAdmin(currentUser))) {
            throw new Exception("You don't have permission to access this page");
        }

        if (settingType.toLowerCase().equals("syncuser") && !(DirectUtils.isTcStaff(currentUser))) {
            throw new Exception("You don't have permission to access this page");
        }
    }

    /**
     * Gets if the contest fee setting page accessible.
     *
     * @return if the contest fee setting page accessible.
     */
    public boolean isContestFeeSettingAccessible() {
        TCSubject user = DirectUtils.getTCSubjectFromSession();
        return DirectUtils.isSuperAdmin(user) || DirectUtils.isTCAccounting(user);
    }

    /**
     * Gets if the sync user setting page accessible.
     *
     * @return if the sync user setting page accessible.
     */
    public boolean isJiraSynAccessible() {
        TCSubject user = DirectUtils.getTCSubjectFromSession();
        return DirectUtils.isTcStaff(user);
    }
}
