/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import com.topcoder.direct.services.view.util.DirectUtils;
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
    private boolean studio;

    /**
     * <p>
     * Represents the contest id.
     * </p>
     */
    private long contestTypeId;

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
            DirectStrutsActionsHelper.getTCSubjectFromSession(), (int) contestTypeId, studio);

        // set as result
        setResult(getCapacityResult(contestTypeId, capacityDatas));
    }

    /**
     * <p>
     * Gets the capacity full dates for return.
     * </p>
     *
     * @param capacityDatas capacityDatas object
     * @return the map which will be returned
     */
    private Map<String, Object> getCapacityResult(long contestTypeId, List<CapacityData> capacityDatas) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<String> dates = new ArrayList<String>();

        if (capacityDatas == null) {
            capacityDatas = new ArrayList<CapacityData>();
        }

        for (CapacityData capacityData : capacityDatas) {
            //System.out.println("get date string: " + getDateString(capacityData.getDate()) + " for : "
            //    + contestTypeId);
            dates.add(getDateString(capacityData.getDate()));
        }

        result.put("contestTypeId", contestTypeId);
        result.put("fullDates", dates);
        result.put("studio", studio);

        return result;
    }

    /**
     * <p>
     * Gets the date string.
     * </p>
     *
     * @param date the xml date
     * @return the date string in the format of 'MM/dd/yyyy'
     * @see DirectUtils
     */
    private String getDateString(XMLGregorianCalendar date) {
        DateFormat formatter = new SimpleDateFormat(DirectUtils.DATE_FORMAT);
        return formatter.format(DirectUtils.getDate(date));
    }

    public boolean isStudio() {
        return studio;
    }

    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    public long getContestTypeId() {
        return contestTypeId;
    }

    public void setContestTypeId(long contestTypeId) {
        this.contestTypeId = contestTypeId;
    }
}
