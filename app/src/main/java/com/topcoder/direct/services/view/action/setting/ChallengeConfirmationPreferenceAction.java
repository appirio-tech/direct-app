/*
 * Copyright (C) 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.setting;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.shared.util.DBMS;
import com.topcoder.web.common.RowNotFoundException;
import com.topcoder.web.ejb.user.UserPreference;

import java.util.HashMap;
import java.util.Map;

/**
 * This action gets and saves the save challenge confirmation preference of direct preferences.
 *
 * @author TCSASSEMBLER
 * @version 1.0 (TopCoder Direct - Draft Challenge Creation/Saving Prompt)
 */
public class ChallengeConfirmationPreferenceAction extends BaseDirectStrutsAction {

    /**
     * The preference value flag. True to show confirmation, false not show confirmation.
     */
    private Boolean flag;

    /**
     * The preference id of the save challenge confirmation preference.
     */
    public static final int CHALLENGE_CONFIRMATION_PREFERENCE_ID = 50;

    /**
     * Gets the save challenge confirmation preference value of the current user.
     *
     * @throws Exception if any error.
     */
    @Override
    protected void executeAction() throws Exception {
        UserPreference userPreference = this.getUserPreferenceHome().create();

        try {
            String value = userPreference.getValue(DirectUtils.getTCSubjectFromSession().getUserId(),
                    CHALLENGE_CONFIRMATION_PREFERENCE_ID, DBMS.COMMON_OLTP_DATASOURCE_NAME);
            Map<String, String> result = new HashMap<String, String>();
            result.put("value", value);
            setResult(result);
        } catch (RowNotFoundException rfe) {
            setResult(false);
        }

    }

    /**
     * Updates the user's save challenge confirmation preference value.
     *
     * @return the result code.
     */
    public String updateChallengeConfirmationPreference() {
        if (this.flag != null) {

            try {
                UserPreference userPreference = this.getUserPreferenceHome().create();

                // try get the user preference to see if it exists
                try {
                    userPreference.getValue(DirectUtils.getTCSubjectFromSession().getUserId(),
                            CHALLENGE_CONFIRMATION_PREFERENCE_ID, DBMS.COMMON_OLTP_DATASOURCE_NAME);
                } catch (RowNotFoundException rfe) {
                    // if the user preference does not exist, create first
                    userPreference.createUserPreference(DirectUtils.getTCSubjectFromSession().getUserId(),
                            CHALLENGE_CONFIRMATION_PREFERENCE_ID, DBMS.COMMON_OLTP_DATASOURCE_NAME);
                }

                userPreference.setValue(DirectUtils.getTCSubjectFromSession().getUserId(),
                        CHALLENGE_CONFIRMATION_PREFERENCE_ID, flag.toString(), DBMS.COMMON_OLTP_DATASOURCE_NAME);

                Map<String, String> result = new HashMap<String, String>();
                result.put("value", this.flag.toString());
                setResult(result);
            } catch (Throwable e) {
                if (getModel() != null) {
                    setResult(e);
                }
            }

        }

        return SUCCESS;
    }

    /**
     * Sets the preference value.
     *
     * @param flag the boolean flag to indicate the preference value.
     */
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
