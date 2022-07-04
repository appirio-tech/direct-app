/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.contest;

import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.contest.ContestDTO;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>A processor to be used for setting the data for the provided front-end action with details for requested contest.
 * </p>
 *
 * <p>This processor expects the actions of {@link ViewAction} type with view data of {@link ContestDTO.Aware} type to
 * be passed to it.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ContestDetailsProcessor implements RequestProcessor<ViewAction> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(ContestDetailsProcessor.class);

    /**
     * <p>Constructs new <code>ContestDetailsProcessor</code> instance. This implementation does nothing.</p>
     */
    public ContestDetailsProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(ViewAction action) {
        ContestDTO.Aware data = (ContestDTO.Aware) action.getViewData();
        long contestId = data.getContestId();
        try {
            ContestDTO contest = DataProvider.getContest(contestId);
            data.setContest(contest);
        } catch (Exception e) {
            log.error("Failed to retrieve details for contest " + contestId, e);
            action.setResultCode(ViewAction.RC_UNEXPECTED_ERROR);
            action.setErrorMessage(e.getMessage());
            action.setError(e);
        }
    }
}
