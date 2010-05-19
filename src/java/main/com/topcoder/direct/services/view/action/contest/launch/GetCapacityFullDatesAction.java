/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.topcoder.service.pipeline.CapacityData;
import com.topcoder.service.pipeline.PipelineServiceFacade;

/**
 * <p>
 * This action will get the capacity full dates for the given contest type.
 * </p>
 * <p>
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread
 * safety is not required (instead in Struts 1 the thread safety is required because the action instances are reused).
 * This class is mutable and stateful: it's not thread safe.
 * </p>
 *
 * @author fabrizyo, FireIce
 * @version 1.0
 */
public class GetCapacityFullDatesAction extends BaseDirectStrutsAction {
    /**
     * <p>
     * Represents the unique serial version id.
     * </p>
     */
    private static final long serialVersionUID = -2831260343381044595L;

    /**
     * <p>
     * This parameter indicates if the client wants the capacity full dates of studio competitions.
     * </p>
     */
    private boolean isStudio;

    /**
     * <p>
     * Represents the contest id.
     * </p>
     */
    private long contestId;

    /**
     * <p>
     * Creates a <code>GetCapacityFullDatesAction</code> instance.
     * </p>
     */
    public GetCapacityFullDatesAction() {
    }

    /**
     * <p>
     * Executes the action.
     * </p>
     * <p>
     * Get the capacity full dates from the facade.
     * </p>
     *
     * @throws IllegalStateException if the contestTypeIdByContestType/pipelineServiceFacade is not set.
     * @throws Exception if any other error occurs
     * @see PipelineServiceFacade#getCapacityFullDates(com.topcoder.security.TCSubject, int, boolean)
     */
    protected void executeAction() throws Exception {
        if (null == getPipelineServiceFacade()) {
            throw new IllegalStateException("The pipelineServiceFacade is not initialized.");
        }

        // get capacity full dates.
        List<CapacityData> capacityDatas = getPipelineServiceFacade().getCapacityFullDates(
            DirectStrutsActionsHelper.getTCSubjectFromSession(), (int) contestId, isStudio);

        // set as result
        setResult(capacityDatas);

    }

    public long getContestId() {
        return contestId;
    }

    public void setContestId(long contestId) {
        this.contestId = contestId;
    }

    /**
     * <p>
     * Gets whether the client wants the capacity full dates of studio competitions.
     * </p>
     *
     * @return whether the client wants the capacity full dates of studio competitions.
     */
    public boolean isStudio() {
        return isStudio;
    }

    /**
     * <p>
     * Sets whether the client wants the capacity full dates of studio competitions.
     * </p>
     *
     * @param isStudio the flag whether the client wants the capacity full dates of studio competitions.
     */
    public void setStudio(boolean isStudio) {
        this.isStudio = isStudio;
    }
}
