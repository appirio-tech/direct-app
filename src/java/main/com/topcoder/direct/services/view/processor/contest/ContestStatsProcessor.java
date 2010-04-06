/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.contest;

import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.contest.ContestStatsDTO;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>A processor to be used for setting the data for the provided front-end action with details on statistics for
 * requested contest.</p>
 *
 * <p>This processor expects the actions of {@link ViewAction} type with view data of {@link ContestStatsDTO.Aware} type
 * to be passed to it.</p>
 *
 * @author isv
 * @version 1.0
 */
public class ContestStatsProcessor implements RequestProcessor<ViewAction> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(ContestStatsProcessor.class);

    /**
     * <p>Constructs new <code>ContestStatsProcessor</code> instance. This implementation does nothing.</p>
     */
    public ContestStatsProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(ViewAction action) {
        ContestStatsDTO.Aware data = (ContestStatsDTO.Aware) action.getViewData();
        long contestId = data.getContestId();
        try {
            ContestStatsDTO contestStats = DataProvider.getContestStats(contestId);
            data.setContestStats(contestStats);
        } catch (Exception e) {
            log.error("Failed to retrieve statistics for contest " + contestId, e);
            action.setResultCode(ViewAction.RC_UNEXPECTED_ERROR);
            action.setErrorMessage(e.getMessage());
            action.setError(e);
        }
    }
}
