/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action;

import com.topcoder.direct.services.view.dto.HomePageDTO;

/**
 * <p>A <code>Struts 2</code> action used for handling requests for viewing landing page for application.</p>
 *
 * @author isv
 * @version 1.0
 */
public class LandingPage extends AbstractAction implements ViewAction<HomePageDTO> {

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
}
