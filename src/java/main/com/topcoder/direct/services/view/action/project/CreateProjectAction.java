/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.project;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.CommonDTO;
import com.topcoder.direct.services.view.form.ProjectForm;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for creating new projects.</p>
 *
 * @author isv
 * @version 1.0
 */
public class CreateProjectAction extends AbstractAction implements FormAction<ProjectForm>, ViewAction<CommonDTO> {

    /**
     * <p>A <code>ProjectForm</code> providing the input parameters submitted by user.</p>
     */
    private ProjectForm formData = new ProjectForm();

    /**
     * <p>A <code>CommonDTO</code> providing the viewData for displaying by view.</p>
     */
    private CommonDTO viewData = new CommonDTO();

    /**
     * <p>Constructs new <code>CreateProjectAction</code> instance. This implementation does nothing.</p>
     */
    public CreateProjectAction() {
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return a <code>ProjectForm</code> providing the data for form submitted by user..
     */
    public ProjectForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return an <code>Object</code> providing the collector for data to be rendered by the view mapped to this action.
     */
    public CommonDTO getViewData() {
        return this.viewData;
    }
}
