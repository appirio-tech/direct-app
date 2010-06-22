/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.cloudvm;

import com.topcoder.security.TCSubject;

/**
 * Action to retrieve VM instances from database.
 *
 * @author orange_cloud
 * @version 1.0
 */
public class ShowVMInstancesAction extends AbstractVMAction {
    /**
     * Retrieves active VM instances from database.
     *
     * @throws Exception from remote services
     */
    protected void executeAction() throws Exception {
        TCSubject user = getUser();
        authorize(user);
        setResult(getCloudVMService().getVMInstances(user));
    }
}
