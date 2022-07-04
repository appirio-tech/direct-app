/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.form.ProjectIdForm;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for setting the current project context for user's
 * session.</p>
 *
 * @author isv
 * @version 1.0
 */
public class SetCurrentProjectAction extends AbstractAction implements FormAction<ProjectIdForm> {

    /**
     * <p>A <code>ProjectIdForm</code> providing the input parameters for this action.</p>
     */
    private ProjectIdForm formData = new ProjectIdForm();

    /**
     * <p>Constructs new <code>SetCurrentProjectAction</code> instance. This implementation does nothing.</p>
     */
    public SetCurrentProjectAction() {
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>Object</code> providing the data for form submitted by user.
     */
    public ProjectIdForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Handles the incoming request.</p>
     *
     * @return a <code>String</code> referencing the next view or action to route request to. This implementation
     *         returns {@link #SUCCESS} always.
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            getSessionData().setCurrentProjectContext(null);
            return SUCCESS;
        } else {
            return result;
        }
    }
}
