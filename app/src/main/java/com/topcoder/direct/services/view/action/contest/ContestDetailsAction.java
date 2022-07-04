/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest;

import com.topcoder.direct.services.view.action.AbstractAction;
import com.topcoder.direct.services.view.action.FormAction;
import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.contest.ContestDetailsDTO;
import com.topcoder.direct.services.view.form.ContestIdForm;

/**
 * <p>A <code>Struts</code> action to be used for handling requests for viewing the <code>Contest Details</code> page
 * for requested contest.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ContestDetailsAction extends AbstractAction implements FormAction<ContestIdForm>,
                                                                    ViewAction<ContestDetailsDTO> {

    /**
     * <p>A <code>ContestIdForm</code> providing the ID of a requested contest.</p>
     */
    private ContestIdForm formData;

    /**
     * <p>A <code>ContestDetailsDTO</code> providing the view data for displaying by <code>Contest Details</code> view.
     * </p>
     */
    private ContestDetailsDTO viewData;

    /**
     * <p>Constructs new <code>ContestDetailsAction</code> instance. This implementation does nothing.</p>
     */
    public ContestDetailsAction() {
        this.viewData = new ContestDetailsDTO();
        this.formData = new ContestIdForm(this.viewData);
    }

    /**
     * <p>Gets the form data.</p>
     *
     * @return an <code>Object</code> providing the data for form submitted by user..
     */
    public ContestIdForm getFormData() {
        return this.formData;
    }

    /**
     * <p>Gets the data to be displayed by view mapped to this action.</p>
     *
     * @return an <code>Object</code> providing the collector for data to be rendered by the view mapped to this action.
     */
    public ContestDetailsDTO getViewData() {
        return this.viewData;
    }

    /**
     * <p>Handles the incoming request. If action is executed successfully then changes the current project context to
     * project for contest requested for this action.</p>
     *
     * @return a <code>String</code> referencing the next view or action to route request to. This implementation
     *         returns {@link #SUCCESS} always.
     * @throws Exception if an unexpected error occurs while processing the request.
     */
    @Override
    public String execute() throws Exception {
        String result = super.execute();
        if (SUCCESS.equals(result)) {
            getSessionData().setCurrentProjectContext(getViewData().getContestStats().getContest().getProject());
            return SUCCESS;
        } else {
            return result;
        }
    }
}
