/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.direct.services.view.dto.CommonDTO;

/**
 * <p>A simple action used for forwarding requests to other pages.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ForwardAction extends AbstractAction implements ViewAction<CommonDTO> {

    /**
     * <p>A <code>CommonDTO</code> providing the viewData for displaying by view.</p>
     */
    private CommonDTO viewData = new CommonDTO();

    /**
     * <p>Constructs new <code>ForwardAction</code> instance. This implementation does nothing.</p>
     */
    public ForwardAction() {
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
