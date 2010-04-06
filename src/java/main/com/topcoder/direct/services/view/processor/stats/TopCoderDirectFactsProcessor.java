/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.processor.stats;

import com.topcoder.direct.services.view.action.ViewAction;
import com.topcoder.direct.services.view.dto.TopCoderDirectFactsDTO;
import com.topcoder.direct.services.view.processor.RequestProcessor;
import com.topcoder.direct.services.view.util.DataProvider;
import com.topcoder.shared.util.logging.Logger;

/**
 * <p>A processor to be used for setting the data for the provided front-end action with stats for <code>TopCoder Direct
 * </code> facts.</p>
 *
 * <p>This processor expects the actions of {@link ViewAction} type with view data of
 * {@link TopCoderDirectFactsDTO.Aware} type to be passed to it. If any of those requirements is not met then this
 * processor does nothing.</p>
 *
 * @author isv
 * @version 1.0
 */
public class TopCoderDirectFactsProcessor implements RequestProcessor<ViewAction> {

    /**
     * <p>A <code>Logger</code> to be used for logging the events encountered while processing the requests.</p>
     */
    private static final Logger log = Logger.getLogger(TopCoderDirectFactsProcessor.class);

    /**
     * <p>Constructs new <code>TopCoderDirectFactsProcessor</code> instance. This implementation does nothing.</p>
     */
    public TopCoderDirectFactsProcessor() {
    }

    /**
     * <p>Processes the incoming request which has been mapped to specified action. Analyzes if the action requires
     * statistics on <code>TopCoder Direct</code> facts and if that's the case then retrieves necessary stats from DB
     * and sets action's view data with retrieved values.</p>
     *
     * @param action an <code>Object</code> representing the current action mapped to incoming request.
     */
    public void processRequest(ViewAction action) {
        try {
            Object data = action.getViewData();
            if (data instanceof TopCoderDirectFactsDTO.Aware) {
                TopCoderDirectFactsDTO tcDirectFacts = DataProvider.getTopCoderDirectFacts();
                TopCoderDirectFactsDTO.Aware viewData = (TopCoderDirectFactsDTO.Aware) data;
                viewData.setTopcoderDirectFacts(tcDirectFacts);
            }
        } catch (Exception e) {
            log.debug("Failed to retrieve TopCoder Direct facts", e);
            action.setResultCode(ViewAction.RC_UNEXPECTED_ERROR);
            action.setErrorMessage(e.getMessage());
            action.setError(e);
        }
    }
}
