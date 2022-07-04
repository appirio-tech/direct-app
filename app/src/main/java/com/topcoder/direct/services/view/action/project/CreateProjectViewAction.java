/*
 * Copyright (C) 2012 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import java.util.List;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.project.ProjectForumTemplateDTO;
import com.topcoder.direct.services.view.util.DataProvider;

/**
 * <p>
 * Renders the create project page. It retrieves the project forums templates and returns them as view data.
 * </p>
 * @author TCSASSEMBLY
 * @version 1.0
 */
public class CreateProjectViewAction extends AbstractAction implements ViewAction<List<ProjectForumTemplateDTO>> {

    /**
     * Represents the serial version uid of this class.
     */
    private static final long serialVersionUID = 839179926895781565L;

    /**
     * Represents the default project type id used to retrieve forum configuration.
     */
    private static final Long DEFAULT_PROJECT_TYPE_ID = 0L;

    /**
     * Represents the project forum template configuration.
     */
    private List<ProjectForumTemplateDTO> projectForumTemplates;

    /**
     * <p>
     * Creates an instance of this class. It does nothing.
     * </p>
     */
    public CreateProjectViewAction() {
    }

    /**
     * <p>
     * Executes the action. It retrieves a list of project forum templates from database.
     * </p>
     * @throws Exception
     *             if any error occurs.
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            projectForumTemplates = DataProvider.getDirectProjectForumTemplates(DEFAULT_PROJECT_TYPE_ID);
            return SUCCESS;
        } else {
            return result;
        }
    }

    /**
     * <p>
     * Gets the view data.
     * </p>
     * @return a list of <code>ProjectForumTemplateDTO</code> representing the project forum template configuration.
     */
    public List<ProjectForumTemplateDTO> getViewData() {
        return projectForumTemplates;
    }
}
