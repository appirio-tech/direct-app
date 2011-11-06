/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;


import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.contest.TypedContestBriefDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.direct.services.view.util.SessionData;
import com.topcoder.security.TCSubject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ajax action to get the user's projects data, the data is returned as json.
 *
 * <p>
 *     Version 1.1 changes:
 *     - Add the current project id, current project name and the contests of current project data in the return.
 * </p>
 *
 * @version 1.1
 * @author hohosky, TCSASSEMBLER
 */
public class CustomerProjectsAjaxAction extends BaseDirectStrutsAction {

    /**
     * Gets user projects data via ajax.
     *
     * @throws Exception if there is error.
     */
    @Override
    protected void executeAction() throws Exception {
        TCSubject currentUser = DirectUtils.getTCSubjectFromSession();

        SessionData session = new SessionData(DirectUtils.getServletRequest().getSession());

        long currentProjectId = 0;
        String currentProjectName = "";

        // get current selected project
        ProjectBriefDTO currentProjectContext = session.getCurrentProjectContext();

        if (currentProjectContext != null) {
            currentProjectId = currentProjectContext.getId();
            currentProjectName = currentProjectContext.getName();
        }

        List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(
                currentUser.getUserId(), currentProjectId);

        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> contestsResult = new HashMap<String, Object>();


        for(TypedContestBriefDTO contest : contests) {
            Map<String, Object> cmap = new HashMap<String, Object>();
            cmap.put("title", contest.getTitle());
            cmap.put("id", contest.getId());
            cmap.put("status", contest.getStatus().getShortName());
            cmap.put("type", contest.getContestType().getLetter());

            contestsResult.put(String.valueOf(contest.getId()), cmap);
        }

        List<ProjectBriefDTO> projects
                = DataProvider.getUserProjects(currentUser.getUserId());
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);

        result.put("projects", userProjectsDTO.getJsonDataMap());
        result.put("contests", contestsResult);
        result.put("currentProjectId", currentProjectId);
        result.put("currentProjectName", currentProjectName);

        // get json data from user projects dto
        setResult(result);
    }
}
