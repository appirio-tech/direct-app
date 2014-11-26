/*
 * Copyright (C) 2011 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;


import com.topcoder.direct.services.view.action.BaseDirectStrutsAction;
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
 * <p>
 * Version 1.2 (TopCoder Direct - Change Right Sidebar to pure Ajax)
 * - Removes the statements to populate the right sidebar direct projects and project contests. It's changed to
 * load these data via ajax instead after the page finishes loading.
 * </p>
 *
 * @version 1.2
 * @author hohosky, Veve
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
        } else if (session.getCurrentSelectDirectProjectID() != null && session.getCurrentSelectDirectProjectID() > 0) {
            currentProjectId = session.getCurrentSelectDirectProjectID();
            currentProjectName = this.getProjectServiceFacade().getProject(currentUser, currentProjectId).getName();
        }

        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> contestsResult = new HashMap<String, Object>();

        if(currentProjectId > 0) {
            List<TypedContestBriefDTO> contests = DataProvider.getProjectTypedContests(
                    currentUser.getUserId(), currentProjectId);

            for(TypedContestBriefDTO contest : contests) {
                Map<String, Object> contestDataMap = new HashMap<String, Object>();
                contestDataMap.put("title", contest.getTitle());
                contestDataMap.put("id", contest.getId());
                contestDataMap.put("status", contest.getStatus().getShortName());
                contestDataMap.put("type", contest.getContestType().getLetter());

                contestsResult.put(String.valueOf(contest.getId()), contestDataMap);
            }
        }

        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(DataProvider.getUserProjects(currentUser.getUserId()));

        result.put("projects", userProjectsDTO.getJsonDataMap());
        result.put("contests", contestsResult);
        result.put("currentProjectId", currentProjectId);
        result.put("currentProjectName", currentProjectName);

        // get json data from user projects dto
        setResult(result);
    }
}
