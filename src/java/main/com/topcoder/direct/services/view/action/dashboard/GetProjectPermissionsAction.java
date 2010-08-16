/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.dashboard;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.action.contest.launch.DirectStrutsActionsHelper;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.permission.ProjectPermission;
import com.topcoder.security.TCSubject;

import java.util.List;

/**
 * <p>A <code>Struts</code> action for handling requests for getting the project level permissions for all projects
 * which current user is granted <code>Full Access</code> permission for.</p>
 *
 * @author isv
 * @version 1.0 (Direct Permissions Setting Back-end and Integration Assembly 1.0)
 */
public class GetProjectPermissionsAction extends BaseDirectStrutsAction {

    /**
     * <p>Constructs new <code>GetProjectPermissionsAction</code> instance. This implementation does nothing.</p>
     */
    public GetProjectPermissionsAction() {
    }

    /**
     * <p>Handles the incoming request. Gets the list of project level permissions for all projects which current user
     * is granted <code>Full Access</code> permission for and binds them to request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        ContestServiceFacade contestServiceFacade = getContestServiceFacade();
        TCSubject tcSubject = DirectStrutsActionsHelper.getTCSubjectFromSession();
        List<ProjectPermission> permissions = contestServiceFacade.getProjectPermissions(tcSubject);
        setResult(permissions);
    }
}
