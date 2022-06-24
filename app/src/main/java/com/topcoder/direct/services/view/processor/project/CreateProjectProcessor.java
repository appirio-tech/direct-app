/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.project;

import com.topcoder.direct.services.view.action.project.CreateProjectAction;
import com.topcoder.direct.services.view.form.ProjectForm;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>A processor to be used for setting the data for the provided front-end action with details on latest activities
 * for projects associated with current user.</p>
 *
 * <p>This processor expects the actions of {@link CreateProjectAction} type to be passed to it.</p>
 *
 * <p>Sub-sequent assemblies may update this class as necessary in order to apply appropriate business logic for
 * creating projects.</p>
 *
 * @author isv
 * @version 1.0
 */
public class CreateProjectProcessor implements RequestProcessor<CreateProjectAction> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(CreateProjectProcessor.class);

    /**
     * <p>Constructs new <code>CreateProjectProcessor</code> instance. This implementation does nothing.</p>
     */
    public CreateProjectProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(CreateProjectAction action) {
        try {
            ProjectForm data = action.getFormData();
            String projectName = data.getName();
            String projectDescription = data.getDescription();
            DataProvider.createProject(action.getSessionData().getCurrentUserId(), projectName, projectDescription);
        } catch (Exception e) {
            log.debug("Failed to create project due to unexpected error", e);
            action.setResultCode(CreateProjectAction.RC_UNEXPECTED_ERROR);
            action.setErrorMessage(e.getMessage());
            action.setError(e);
        }
    }
}
