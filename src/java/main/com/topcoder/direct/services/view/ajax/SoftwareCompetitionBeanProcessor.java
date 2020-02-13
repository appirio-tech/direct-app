/*
 * Copyright (C) 2010 - 2019 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.ajax;

import java.util.*;

import com.topcoder.management.project.ProjectGroup;
import com.topcoder.management.project.ProjectPlatform;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceRole;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonBeanProcessor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import com.topcoder.catalog.entity.Category;
import com.topcoder.catalog.entity.CompDocumentation;
import com.topcoder.catalog.entity.Technology;
import com.topcoder.catalog.service.AssetDTO;
import com.topcoder.direct.services.configs.ConfigUtils;
import com.topcoder.direct.services.view.util.DirectUtils;
import com.topcoder.management.project.Project;
import com.topcoder.service.project.SoftwareCompetition;

/**
 * <p>
 * Bean processor for <code>SoftwareCompetition</code>.
 * </p>
 * <p>
 * Version 1.1 - Direct - Repost and New Version v1.0 Assembly Change Note
 * <li>
 * add project status in the response.</li>
 * <ul>
 * </ul>
 * <p>
 * Version 1.2 - TC Direct - Software Creation Update Assembly Change Note:
 * - Add copilots of the software contest into the json result.
 * </p>
 * </p>
 *
 * <p>
 * Version 1.3 - TC Direct Replatforming Release 1 Change Note
 * <ul>
 * <li>Add prizes, hasMulti, multiRoundEndDate, projectStudioSpecification, fileTypes to the response.</li>
 * </ul>
 * </p>

 * <p>
 * Version 1.4 - TC Direct Replatforming Release 4 Change Note
 * <ul>
 * <li>Add document url to the response.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.5 - Release Assembly - TopCoder Cockpit - Launch Contest Update for Marathon Match
 * <ul>
 * <li>Update {@link #getMapResult(SoftwareCompetition)} to support marathon match specification.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.6 (Module Assembly - TC Cockpit Contest Milestone Association 1)
 * <ul>
 *     <li>Updated {@link #getMapResult(com.topcoder.service.project.SoftwareCompetition)} to add milestone id and name</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.7 (Module Assembly - TC Cockpit Launch F2F contest)
 * <ul>
 *     <li>Updated {@link #getMapResult(com.topcoder.service.project.SoftwareCompetition)} to add platforms</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.8 (Release Assembly - TC Cockpit Private Challenge Update)
 * <ul>
 *     <li>Updated {@link #getMapResult(com.topcoder.service.project.SoftwareCompetition)} to include security group id</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 1.9 (Release Assembly - TC Cockpit New Challenge types Integration Bug Fixes)
 * <ul>
 *     <li>Updated {@link #getMapResult(com.topcoder.service.project.SoftwareCompetition)} to add isReviewPhaseClosed</li>
 * </ul>
 * </p>
 *
 *
 * <p>
 * Version 2.0 (First2Finish - TC Cockpit Auto Assign Reviewer Update)
 * <ul>
 *     <li>Updated {@link #getMapResult(com.topcoder.service.project.SoftwareCompetition)} to add reviewers data</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.1 (TOPCODER DIRECT - IMPROVEMENT FOR PRE-REGISTER MEMBERS WHEN LAUNCHING CHALLENGES)
 * <ul>
 *     <li> Add list of registrant in map result</li>
 * </ul>
 * </p>
 *
 * Version 2.2 (TOPCODER DIRECT - CLOSE PRIVATE CHALLENGE IMMEDIATELY)
 * <ul>
 *     <li>Update {@link #getMapResult(SoftwareCompetition)} include registrant id</li>
 * </ul>
 *
 * Version 2.3 (TOPCODER - SUPPORT GROUPS CONCEPT FOR CHALLENGES):
 * <ul>
 *     <li>Updated {@link #getMapResult(SoftwareCompetition)}include project groups of challenge</li>
 * </ul>
 *
 * <p>
 * Version 2.4 (Topcoder - Ability To Set End Date For Registration Phase and Submission Phase)
 * <ul>
 *     <li>Added regEndDate in response</li>
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.5 (TOPCODER - SUPPORT TYPEAHEAD FOR TASK ASSIGNEES IN DIRECT APP)
 * <ul>
 *     <li>Refactor registrants response</li>
 * </ul>
 * </p>
 *
 * @author BeBetter, TCSDEVELOPER, morehappiness, bugbuka, GreatKevin, TCSCODER
 * @version 2.5
 * @since Direct - View/Edit/Activate Software Contests Assembly
 * 
 * Version 2.6 (Topcoder - Integrate Direct with Groups V5)
 * <ul>
 *      <li>Refactor projectGroup to comply with v5</li>
 * </ul>
 * 
 * @version 2.6
 */
public class SoftwareCompetitionBeanProcessor implements JsonBeanProcessor {
    /**
     * <p>
     * The constant for design category.
     * </p>
     */
    private static final long CATEGORY_DESIGN = 1;

    /**
     * <p>
     * The constant for dev category.
     * </p>
     */
    private static final long CATEGORY_DEV = 2;

    /**
     * <p>
     * The constant for concept category.
     * </p>
     */
    private static final long CATEGORY_CONCEPT = 23;

    /**
     * <p>
     * The constant for spec category.
     * </p>
     */
    private static final long CATEGORY_SPEC = 6;

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
        if (!(bean instanceof SoftwareCompetition)) {
            throw new IllegalArgumentException("bean type should be StudioCompetition.");
        }

        return (JSONObject) JSONSerializer.toJSON(getMapResult((SoftwareCompetition) bean), jsonConfig);
    }

    /**
     * <p>
     * Gets the map result for the bean.
     * </p>
     *
     * @param bean the bean
     * @return the map result for the given bean
     */
    private Object getMapResult(SoftwareCompetition bean) {
        Map<String, Object> result = new HashMap<String, Object>();

        Project project = bean.getProjectHeader();
        AssetDTO assetDTO = bean.getAssetDTO();

        result.put("contestId", project.getId());
        result.put("projectStatus", project.getProjectStatus());
        result.put("projectCategory", project.getProjectCategory());
        result.put("contestName", assetDTO.getName());
        result.put("startDate", DirectUtils.getDateString(DirectUtils.getRegistrationStartDate(bean)));
        result.put("tcDirectProjectId", project.getTcDirectProjectId());
        result.put("tcDirectProjectName", project.getTcDirectProjectName());
        result.put("billingProjectId", project.getProperties().get("Billing Project"));
        result.put("adminFee", project.getProperties().get("Admin Fee"));
        result.put("directProjectMilestoneId", bean.getDirectProjectMilestoneId());
        result.put("directProjectMilestoneName", bean.getDirectProjectMilestoneName());
        result.put("securityGroupId", project.getSecurityGroupId());
        result.put("challengeCreator", project.getCreator());

        // retrieve review scorecard id.
        for(com.topcoder.project.phases.Phase phase : bean.getProjectPhases().getAllPhases()){
            if(phase.getPhaseType().getName().equals(com.topcoder.project.phases.PhaseType.REVIEW_PHASE.getName()) && phase.getAttributes().get("Scorecard ID") != null){
                result.put("reviewScorecardId", phase.getAttributes().get("Scorecard ID").toString());
            }

            if(phase.getPhaseType().getName().equals(com.topcoder.project.phases.PhaseType.ITERATIVE_REVIEW_PHASE.getName()) && phase.getAttributes().get("Scorecard ID") != null){
                result.put("iterativeReviewScorecardId", phase.getAttributes().get("Scorecard ID").toString());
            }
        }

        // get resources of project
        Resource[] resources = bean.getResources();

        // uses a map to store the copilots, key is the user id, value is the handle
        Map<String, String> copilots = new HashMap<String, String>();

        // use a map to store the reviewers, key is the user id, value is the handle
        Map<String, String> reviewers = new HashMap<String, String>();

        double totalCopilots = 0;

        List<Map> registrant = new ArrayList<Map>();

        // Gets copilots and reviewers from the resources of the contest
        for (Resource r : resources) {
            // get copilots
            if(r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_COPILOT_ID) {
                // resource is of role Copilot, add the resource into the copilot map
                copilots.put(String.valueOf(r.getProperty("External Reference ID")), r.getProperty("Handle"));

                String copilotPaymentStr = r.getProperty("Payment");
                double copilotFee = 0;

                try {
                    copilotFee = Double.valueOf(copilotPaymentStr);
                } catch(Exception ex) {
                    // ignore
                }

                totalCopilots += copilotFee;
            }

            if(r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_SUBMITTER) {
                Map<String, String> user = new HashMap<String, String>();
                user.put("id", String.valueOf(r.getUserId()));
                user.put("name", r.getProperty("Handle"));
                registrant.add(user);
            }
            // get reviewers
            if (r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_ITERATIVE_REVIEWER_ID ||
                    r.getResourceRole().getId() == ResourceRole.RESOURCE_ROLE_REVIEWER_ID) {
                reviewers.put(String.valueOf(r.getProperty("External Reference ID")), r.getProperty("Handle"));
            }
        }

        result.put("registrant", registrant);
        // put the copilots into the result
        result.put("copilots", copilots);

        // put the reviewers into the result
        result.put("reviewers", reviewers);

        result.put("copilotsFee", String.valueOf(totalCopilots));

        // project info properties map
        result.put("properties", project.getProperties());

        // spec
        if (!DirectUtils.isStudio(bean)) {
        	result.put("detailedRequirements", project.getProjectSpec().getDetailedRequirements());
            result.put("softwareGuidelines", project.getProjectSpec().getFinalSubmissionGuidelines());
            result.put("privateDescription", project.getProjectSpec().getPrivateDescription());
        }

        // technologies/categories for development/design
        if (isDevOrDesign(bean)) {
            result.put("rootCategoryId", assetDTO.getRootCategory().getId());
            result.put("categoryIds", CollectionUtils.collect(assetDTO.getCategories(), new Transformer() {
                public Object transform(Object object) {
                    return ((Category) object).getId() + "";
                }
            }));
        }

        if (isTechnologyContest(bean)) {
            result.put("technologyIds", CollectionUtils.collect(assetDTO.getTechnologies(), new Transformer() {
                public Object transform(Object object) {
                    return ((Technology) object).getId();
                }
            }));
        }

        if(isPlatformContest(bean)) {
            result.put("platformIds", CollectionUtils.collect(bean.getProjectHeader().getPlatforms(), new Transformer() {
                public Object transform(Object object) {
                    return ((ProjectPlatform) object).getId();
                }
            }));
        }

        // group api v5 use ProjectGroup.newId as id
        result.put("groupIds", CollectionUtils.collect(bean.getProjectHeader().getGroups(), new Transformer() {
            public Object transform(Object object) {
                return ((ProjectGroup) object).getNewId();
            }
        }));

        result.put("groupNames", CollectionUtils.collect(bean.getProjectHeader().getGroups(), new Transformer() {
            public Object transform(Object object) {
                return ((ProjectGroup) object).getName();
            }
        }));

        // documentation
        result.put("documentation", CollectionUtils.collect(assetDTO.getDocumentation(), new Transformer() {
            public Object transform(Object object) {
                CompDocumentation doc = (CompDocumentation) object;
                Map<String, Object> docMap = new HashMap<String, Object>();
                docMap.put("documentId", doc.getId());
                docMap.put("fileName", doc.getUrl().substring(doc.getUrl().lastIndexOf("/") + 1));
                docMap.put("description", doc.getDocumentName());
                docMap.put("documentTypeId", doc.getDocumentTypeId());
                docMap.put("url", doc.getUrl());
                return docMap;
            }
        }));

        // populate software contest fee so we could show prizes data on time
        result.put("softwareContestFees", ConfigUtils.getSoftwareContestFees());

        // end date
        result.put("regEndDate", DirectUtils.getDateString(DirectUtils.getRegistrationEndDate(bean)));
        result.put("subEndDate", DirectUtils.getDateString(DirectUtils.getSubmissionEndDate(bean)));
        result.put("endDate", DirectUtils.getDateString(DirectUtils.getEndDate(bean)));
        result.put("paidFee", DirectUtils.getPaidFee(bean));

        result.put("phaseOpen", DirectUtils.isPhaseOpen(bean));
        result.put("hasSpecReview", DirectUtils.hasSpecReview(bean));
        result.put("isSpecReviewStarted", DirectUtils.isSpecReviewStarted(bean));
        result.put("isReviewPhaseClosed", DirectUtils.isReviewPhaseClosed(bean));
        result.put("prizes", bean.getProjectHeader().getPrizes());

        boolean hasMulti = DirectUtils.isMultiRound(bean);
        result.put("hasMulti", hasMulti);
        if (hasMulti) {
            result.put("multiRoundEndDate", DirectUtils.getDateString(DirectUtils.getMultiRoundEndDate(bean)));
        }

        if(DirectUtils.isMM(bean)){
            result.put("projectMMSpecification", bean.getProjectHeader().getProjectMMSpecification());
        } else if (DirectUtils.isStudio(bean)) {
            result.put("projectStudioSpecification", bean.getProjectHeader().getProjectStudioSpecification());
            result.put("fileTypes", bean.getProjectHeader().getProjectFileTypes());
        }


        return result;
    }

    /**
     * <p>
     * Determines if it is development or design competition.
     * </p>
     *
     * @param bean the software competition
     * @return true if it is development or design competition
     */
    private boolean isDevOrDesign(SoftwareCompetition bean) {
        long categoryId = bean.getProjectHeader().getProjectCategory().getId();
        return (CATEGORY_DESIGN == categoryId || CATEGORY_DEV == categoryId);
    }

    /**
     * <p>
     * Determines if it needs technology.
     * </p>
     *
     * @param bean the software competition
     * @return true if it needs technology
     */
    private boolean isTechnologyContest(SoftwareCompetition bean) {
        long categoryId = bean.getProjectHeader().getProjectCategory().getId();
        return !(CATEGORY_CONCEPT == categoryId || CATEGORY_SPEC == categoryId);
    }

    /**
     * <p>
     * Determines if it needs platform.
     * </p>
     *
     * @param bean the software competition
     * @return true if it needs platform.
     */
    private boolean isPlatformContest(SoftwareCompetition bean) {
        return isTechnologyContest(bean);
    }
}
