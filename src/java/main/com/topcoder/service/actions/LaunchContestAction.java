/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

import java.util.List;

import com.topcoder.service.configs.ConfigUtils;
import com.topcoder.service.configs.StudioContestType;

/**
 * <p>
 * The launch contest action. It will trigger to load the launch contest page and provide getters for some data.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class LaunchContestAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Executes the action. Does nothing for now.
     * </p>
     */
    @Override
    protected void executeAction() throws Exception {
        // do nothing. it will trigger to load launch contest page
    }

    /**
     * <p>
     * Gets a list of studio contest types.
     * </p>
     *
     * @return the list of stuod contest types.
     */
    public List<StudioContestType> getStudioContestTypes() {
        return ConfigUtils.getStudioContestTypes();
    }
}
