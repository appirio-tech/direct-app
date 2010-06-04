/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.HashMap;
import java.util.Map;

import com.topcoder.direct.services.configs.ConfigUtils;

/**
 * <p>
 * Common action to handle some common requests.
 * </p>
 *
 * @author BeBetter
 * @version 1.0
 */
public class CommonAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Executes the action.
     * </p>
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing
    }

    /**
     * <p>
     * Gets the studio configurations.
     * </p>
     *
     * @return the success page
     */
    public String getStudioConfigs() {
        Map<String, Object> configs = new HashMap<String, Object>();
        configs.put("overview", ConfigUtils.getStudioOverviews());
        configs.put("fees", ConfigUtils.getStudioContestFees());
        configs.put("fileTypes", ConfigUtils.getFileTypes().getFileTypes());
        setResult(configs);
        return SUCCESS;
    }

}
