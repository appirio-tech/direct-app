/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.direct.services.view.action.contest.launch.BaseDirectStrutsAction;
import com.topcoder.direct.services.view.dto.HomePageDTO;

/**
 * <p>A <code>Struts 2</code> action used for handling requests for viewing landing page for application.</p>
 *
 * <p>
 * Version 1.1 (Release Assembly - TopCoder Direct Cockpit Release Assembly Ten)
 * - Change to extend <code>BaseDirectStrutsAction</code>
 * </p>
 *
 * @author isv, GreatKevin
 * @version 1.1
 */
public class LandingPage extends BaseDirectStrutsAction implements ViewAction<HomePageDTO> {

    /**
     * <p>A <code>HomePageDTO</code> providing the viewData for displaying by <code>Landing Page</code> view.</p>
     */
    private HomePageDTO viewData = new HomePageDTO();

    /**
     * <p>Constructs new <code>LandingPage</code> instance. This implementation does nothing.</p>
     */
    public LandingPage() {
    }

    /**
     * <p>Gets the viewData to be displayed by <code>Landing Page</code> view.</p>
     *
     * @return a <code>HomePageDTO</code> providing the viewData for displaying by <code>Landing Page</code> view.
     */
    public HomePageDTO getViewData() {
        return this.viewData;
    }

    /**
     * Do nothing.
     *
     * @throws Exception - would never happen.
     * @since 1.1
     */
    @Override
    protected void executeAction() throws Exception {
    }
}
