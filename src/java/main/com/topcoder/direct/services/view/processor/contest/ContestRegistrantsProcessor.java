/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.contest;

import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.contest.ContestRegistrantDTO;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.util.logging.Logger;

import java.util.List;

/**
 * <p>A processor to be used for setting the data for the provided front-end action with details on registrants for
 * requested contest.</p>
 *
 * <p>This processor expects the actions of {@link ViewAction} type with view data of {@link ContestRegistrantDTO.Aware}
 * type to be passed to it.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ContestRegistrantsProcessor implements RequestProcessor<ViewAction> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(ContestRegistrantsProcessor.class);

    /**
     * <p>Constructs new <code>ContestRegistrantsProcessor</code> instance. This implementation does nothing.</p>
     */
    public ContestRegistrantsProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(ViewAction action) {
        ContestRegistrantDTO.Aware data = (ContestRegistrantDTO.Aware) action.getViewData();
        long contestId = data.getContestId();
        try {
            List<ContestRegistrantDTO> contestRegistrants = DataProvider.getContestRegistrants(contestId);
            data.setContestRegistrants(contestRegistrants);
        } catch (Exception e) {
            log.error("Failed to retrieve registrants for contest " + contestId, e);
            action.setResultCode(ViewAction.RC_UNEXPECTED_ERROR);
            action.setErrorMessage(e.getMessage());
            action.setError(e);
        }
    }

}
