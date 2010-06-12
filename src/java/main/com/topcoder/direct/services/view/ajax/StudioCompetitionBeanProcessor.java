/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.studio.ContestData;

/**
 * <p>
 * Bean processor for <code>StudioCompetition</code>.
 * </p>
 *
 * @author TCSDEVEDLOPER
 * @version 1.0
 * @since Direct - View/Edit/Activate Studio Contests Assembly
 */
public class StudioCompetitionBeanProcessor implements JsonBeanProcessor {
    /**
     * <p>
     * Processes the bean.
     * </p>
     *
     * @param bean the bean to be processed.
     * @param jsonConfig the configuration when processing. it will be ignored
     * @return json object for the bean
     * @throws IllegalArgumentException if the bean is not of type <code>StudioCompetition</code>
     */
    public JSONObject processBean(Object bean, JsonConfig jsonConfig) {
        if (!(bean instanceof StudioCompetition)) {
            throw new IllegalArgumentException("bean type should be StudioCompetition.");
        }

        return (JSONObject) JSONSerializer.toJSON(getMapResult((StudioCompetition) bean), jsonConfig);
    }

    /**
     * <p>
     * Gets the map result for the bean.
     * </p>
     *
     * @param bean the bean
     * @return the map result for the given bean
     */
    private Map<String, Object> getMapResult(StudioCompetition bean) {
        Map<String, Object> result = new HashMap<String, Object>();
        ContestData contestData = bean.getContestData();

        // dates
        result.put("startTime", bean.getStartTime());
        result.put("endTime", bean.getEndTime());

        // contest data
        result.put("contestId", contestData.getContestId());
        result.put("contestTypeId", contestData.getContestTypeId());
        result.put("name", contestData.getName());
        result.put("projectId", contestData.getProjectId());
        result.put("tcDirectProjectId", contestData.getTcDirectProjectId());
        result.put("tcDirectProjectName", contestData.getTcDirectProjectName());
        result.put("billingProject", contestData.getBillingProject());
        result.put("shortSummary", contestData.getShortSummary());
        result.put("contestDescriptionAndRequirements", contestData.getContestDescriptionAndRequirements());
        result.put("finalFileFormat", contestData.getFinalFileFormat().toUpperCase());
        result.put("otherFileFormats", (contestData.getOtherFileFormats() == null) ? "" : contestData
            .getOtherFileFormats().toUpperCase());
        result.put("statusId", contestData.getStatusId());
        result.put("detailedStatusId", contestData.getDetailedStatusId());
        result.put("contestAdministrationFee", contestData.getContestAdministrationFee());

        result.put("multiRound", contestData.isMultiRound());
        result.put("multiRoundData", contestData.getMultiRoundData());
        // milestone prizes
        result.put("milestonePrize", contestData.getMilestonePrizeData());

        // prizes
        result.put("prizes", contestData.getPrizes());

        // doc uploads
        result.put("docUploads", contestData.getDocumentationUploads());

        return result;
    }

}
