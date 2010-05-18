/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.actions;

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
 * <b>Thread Safety</b>: In <b>Struts 2</b> framework, the action is constructed for every request so the thread safety
 * is not required (instead in Struts 1 the thread safety is required because the action instances are reused). This
 * class is mutable and stateful: it's not thread safe.
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
     * It's the contest type used to perform the logic of the action.
     * </p>
     * <p>
     * It can't be null or empty.
     * </p>
     */
    private String contestType;

    /**
     * <p>
     * This parameter indicates if the client wants the capacity full dates of studio competitions.
     * </p>
     */
    private boolean isStudio;

    /**
     * <p>
     * It's used to retrieve the capacity full dates.
     * </p>
     * <p>
     * It will be not null because it will be injected. It can't be null.
     * </p>
     */
    private PipelineServiceFacade pipelineServiceFacade;

    /**
     * <p>
     * It represents the contest types ids by contest type String.
     * </p>
     * <p>
     * The keys and values can't be null. It's injected externally and it will be not null in the
     * <code>executeAction</code> method.
     * </p>
     */
    private Map<String, Long> contestTypeIdByContestType;

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
     * @throws IllegalStateException
     *             if the contestTypeIdByContestType/pipelineServiceFacade is not set.
     * @throws Exception
     *             if any other error occurs
     * @see PipelineServiceFacade#getCapacityFullDates(com.topcoder.security.TCSubject, int, boolean)
     */
    protected void executeAction() throws Exception {
        if (null == contestTypeIdByContestType) {
            throw new IllegalStateException("The contestTypeIdByContestType is not initialized.");
        }

        if (null == pipelineServiceFacade) {
            throw new IllegalStateException("The pipelineServiceFacade is not initialized.");
        }

        // the corresponding contest type id should be present
        if (contestTypeIdByContestType.containsKey(contestType)) {
            // get capacity full dates.
            List<CapacityData> capacityDatas = pipelineServiceFacade.getCapacityFullDates(DirectStrutsActionsHelper
                    .getTCSubjectFromSession(), contestTypeIdByContestType.get(contestType).intValue(), isStudio);

            // set as result
            setResult(capacityDatas);
        } else {
            addFieldError("contestType", "The contestType is invalid, no corresponding contest type id");
        }
    }

    /**
     * <p>
     * Gets the pipeline service facade.
     * </p>
     *
     * @return the pipeline service facade
     */
    public PipelineServiceFacade getPipelineServiceFacade() {
        return pipelineServiceFacade;
    }

    /**
     * <p>
     * Set the pipeline Service facade.
     * </p>
     *
     * @param pipelineServiceFacade
     *            the pipeline service facade to set
     * @throws IllegalArgumentException
     *             if <b>pipelineServiceFacade</b> is <code>null</code>
     */
    public void setPipelineServiceFacade(PipelineServiceFacade pipelineServiceFacade) {
        DirectStrutsActionsHelper.checkNull(pipelineServiceFacade, "pipelineServiceFacade");

        this.pipelineServiceFacade = pipelineServiceFacade;
    }

    /**
     * <p>
     * Gets the contest type.
     * </p>
     *
     * @return the contest type
     */
    public String getContestType() {
        return contestType;
    }

    /**
     * <p>
     * Sets the contest type.
     * </p>
     *
     * @param contestType
     *            the contest type to set
     */
    @RequiredStringValidator(message = "The contestType can not be null or empty",
            key = "i18n.GetCapacityFullDatesAction.contestTypeRequired")
    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    /**
     * <p>
     * Gets the mapping for contest type and contest type id.
     * </p>
     *
     * @return the mapping for contest type and contest type id.
     */
    public Map<String, Long> getContestTypeIdByContestType() {
        return contestTypeIdByContestType;
    }

    /**
     * <p>
     * Sets the mapping for contest type and contest type id.
     * </p>
     *
     * @param contestTypeIdByContestType
     *            the mapping for contest type and contest type id.
     * @throws IllegalArgumentException
     *             if <b>contestTypeIdByContestType</b> is <code>null</code>, or contains <code>null</code> key or
     *             values.
     */
    public void setContestTypeIdByContestType(Map<String, Long> contestTypeIdByContestType) {
        DirectStrutsActionsHelper.checkNull(contestTypeIdByContestType, "contestTypeIdByContestType");

        for (Entry<String, Long> entry : contestTypeIdByContestType.entrySet()) {
            if (null == entry.getKey()) {
                throw new IllegalArgumentException("The contestTypeIdByContestType map cann't have null key");
            }

            if (null == entry.getValue()) {
                throw new IllegalArgumentException("The contestTypeIdByContestType map cann't have null value");
            }
        }

        this.contestTypeIdByContestType = new HashMap<String, Long>(contestTypeIdByContestType);
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
     * @param isStudio
     *            the flag whether the client wants the capacity full dates of studio competitions.
     */
    @RequiredFieldValidator(message = "The isStudio field is required be set",
            key = "i18n.GetCapacityFullDatesAction.isStudioRequired")
    public void setStudio(boolean isStudio) {
        this.isStudio = isStudio;
    }
}
