/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.project.ProjectForumStatusDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>An action for handling the requests for getting the current status of the forums for desired
 * <code>TC Direct</code> project.</p>
 * 
 * @author TCSDEVELOPER
 * @version 1.0 (opCoder Cockpit Project Overview R2 Project Forum Backend Assembly)
 */
public class GetProjectForumsStatusAction extends BaseDirectStrutsAction {

    /**
     * <p>A <code>long</code> providing the ID of TC Direct project to get the forum status for.</p>
     */
    private long tcDirectProjectId;

    /**
     * <p>Constructs new <code>GetProjectForumsStatusAction</code> instance. This implementation does nothing.</p>
     */
    public GetProjectForumsStatusAction() {
    }

    /**
     * <p>Handles the incoming request. Retrieves the list of forum statuses for requested TC Direct project and binds
     * them to request.</p>
     * 
     * @throws Exception if an unexpected error occurs.
     */
    @Override
    protected void executeAction() throws Exception {
        Map<String, List<ProjectForumStatusDTO>> result = new HashMap<String, List<ProjectForumStatusDTO>>();
        setResult(result);
        
        if (DirectUtils.hasProjectReadPermission(this, getCurrentUser(), getTcDirectProjectId())) {
            List<ProjectForumStatusDTO> entries = DataProvider.getProjectForumStatus(getTcDirectProjectId(), 
                                                                                     getCurrentUser().getUserId());
            result.put("projectForumThreads", entries);
        } else {
            result.put("projectForumThreads", new ArrayList<ProjectForumStatusDTO>());
        }
    }

    /**
     * <p>Gets the ID of TC Direct project to get the forum status for.</p>
     *
     * @return a <code>long</code> providing the ID of TC Direct project to get the forum status for.
     */
    public long getTcDirectProjectId() {
        return this.tcDirectProjectId;
    }

    /**
     * <p>Sets the ID of TC Direct project to get the forum status for.</p>
     *
     * @param tcDirectProjectId a <code>long</code> providing the ID of TC Direct project to get the forum status for.
     */
    public void setTcDirectProjectId(long tcDirectProjectId) {
        this.tcDirectProjectId = tcDirectProjectId;
    }
}
