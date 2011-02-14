/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.topcoder.direct.services.view.dto.contest.ContestReceiptDTO;
import com.topcoder.direct.services.view.util.DataProvider;

/**
 * <p>
 * This action extends <code>GetContestAction</code> and will get contest receipt information for a contest..
 * </p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread
 * safety is not required (instead in Struts 1 the thread safety is required because the action instances are reused).
 * This class is mutable and stateful: it's not thread safe.
 * </p>
 * 
 * @author flexme
 * @version 1.0
 */
public class GetContestReceiptAction extends GetContestAction {
    /**
     * Represents the serial version unique id.
     */
    private static final long serialVersionUID = -2565892310115449921L;

    /**
     * Represents the contest receipt.
     */
    private ContestReceiptDTO contestReceipt;

    /**
     * <p>
     * Creates a <code>GetContestReceiptAction</code> instance.
     * </p>
     */
    public GetContestReceiptAction() {
    }
    
    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * The returned software or studio contest will be available as result.
     * </p>
     *
     * @throws IllegalStateException if the contest service facade is not set.
     * @throws Exception if any other error occurs
     */
    protected void executeAction() throws Exception {
        super.executeAction();
        
        if (super.getContestId() > 0) {
            contestReceipt = DataProvider.getContestReceipt(super.getContestId(), true);
        } else {
            contestReceipt = DataProvider.getContestReceipt(super.getProjectId(), false);
        }
    }

    /**
     * Gets the contest receipt.
     * 
     * @return the contest receipt.
     */
    public ContestReceiptDTO getContestReceipt() {
        return contestReceipt;
    }

    /**
     * Sets the contest receipt.
     * 
     * @param contestReceipt the contest receipt.
     */
    public void setContestReceipt(ContestReceiptDTO contestReceipt) {
        this.contestReceipt = contestReceipt;
    }
}
