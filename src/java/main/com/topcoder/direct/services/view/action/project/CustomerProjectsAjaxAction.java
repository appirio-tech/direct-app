/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;


import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.UserProjectsDTO;
import com.topcoder.direct.services.view.dto.project.ProjectBriefDTO;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.security.TCSubject;

import java.util.List;

/**
 * Ajax action to get the user's projects data, the data is returned as json.
 *
 * @version 1.0
 * @author hohosky
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

        List<ProjectBriefDTO> projects
                = DataProvider.getUserProjects(currentUser.getUserId());
        UserProjectsDTO userProjectsDTO = new UserProjectsDTO();
        userProjectsDTO.setProjects(projects);

        // get json data from user projects dto
        setResult(userProjectsDTO.getJsonDataMap());

    }
}
