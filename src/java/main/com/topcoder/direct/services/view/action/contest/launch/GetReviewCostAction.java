/*
 * Copyright (C) 2013 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.action.contest.launch;

import com.topcoder.direct.services.view.dto.contest.ContestType;
import com.topcoder.direct.services.view.dto.contest.ReviewType;
import com.topcoder.management.payment.calculator.impl.DefaultProjectPaymentCalculator;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectType;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;
import com.topcoder.project.phases.template.DefaultPhaseTemplate;
import com.topcoder.project.service.ProjectServices;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>An action for handling the requests for getting the review cost for desired contest.</p>
 * <p/>
 * <p>
 * Version 1.1 (Module Assembly - TC Cockpit Launch F2F contest)
 * <ul>
 * <li>Updated {@link #executeAction()} to calculate the iterative review payment</li>
 * </ul>
 * </p>
 * <p/>
 * <p>
 * Version 2.0 (TopCoder Direct - Review Cost Calculation Quick Updates) @author GreatKevin @challenge 30044580
 * <ul>
 * <li>Updated the review calculation logic, the screen, review, aggregation etc review cost are determined
 * by phase. The default number of submissions, reviewer number and review type are all taken into account</li>
 * </ul>
 * </p>
 *
 * @author notpad
 * @version 2.0
 */
public class GetReviewCostAction extends BaseDirectStrutsAction {

    /**
     * The map to store the mapping between the contest category id and the default submission count of this contest
     * category id.
     *
     * @since 2.0
     */
    private static Map<Long, Integer> DEFAULT_SUBMISSION_COUNTS_MAP = new HashMap<Long, Integer>();

    /**
     * The map to store the mapping between the phase type name and the resource role id which does the work for this
     * phase.
     *
     * @since 2.0
     */
    private static Map<String, Long> PHASE_RESOURCE_ROLE_MAP = new HashMap<String, Long>();

    static {

        /** setup the submission counts map, the key is project category id, the value is submission count **/

        //  Application Front-End Design 3
        DEFAULT_SUBMISSION_COUNTS_MAP.put(32L, 3);
        //  Architecture 2
        DEFAULT_SUBMISSION_COUNTS_MAP.put(7L, 2);
        //  Assembly Competition 2
        DEFAULT_SUBMISSION_COUNTS_MAP.put(14L, 2);
        //  Banners/Icons 5
        DEFAULT_SUBMISSION_COUNTS_MAP.put(16L, 5);
        //  Bug Hunt 4
        DEFAULT_SUBMISSION_COUNTS_MAP.put(9L, 4);
        //  Code 4
        DEFAULT_SUBMISSION_COUNTS_MAP.put(39L, 4);
        //  Conceptualization 2
        DEFAULT_SUBMISSION_COUNTS_MAP.put(23L, 2);
        //  Content Creation 5
        DEFAULT_SUBMISSION_COUNTS_MAP.put(35L, 5);
        //  Copilot Posting 3
        DEFAULT_SUBMISSION_COUNTS_MAP.put(29L, 3);
        //  Design First2Finish 5
        DEFAULT_SUBMISSION_COUNTS_MAP.put(40L, 5);
        //  First2Finish 3
        DEFAULT_SUBMISSION_COUNTS_MAP.put(38L, 3);
        //  Idea Generation 12
        DEFAULT_SUBMISSION_COUNTS_MAP.put(22L, 12);
        //  Logo Design 7
        DEFAULT_SUBMISSION_COUNTS_MAP.put(20L, 7);
        //  Print/Presentation 13
        DEFAULT_SUBMISSION_COUNTS_MAP.put(21L, 13);
        //  Specification 3
        DEFAULT_SUBMISSION_COUNTS_MAP.put(6L, 3);
        //  Test Scenarios 3
        DEFAULT_SUBMISSION_COUNTS_MAP.put(26L, 3);
        //  Test Suites 1
        DEFAULT_SUBMISSION_COUNTS_MAP.put(13L, 1);
        //  UI Prototype Competition 2
        DEFAULT_SUBMISSION_COUNTS_MAP.put(19L, 2);
        //  Web Design 4
        DEFAULT_SUBMISSION_COUNTS_MAP.put(17L, 4);
        //  Widget or Mobile Screen Design 6
        DEFAULT_SUBMISSION_COUNTS_MAP.put(30L, 6);
        //  Wireframes 7
        DEFAULT_SUBMISSION_COUNTS_MAP.put(18L, 7);

        //  Component Development / Design 2
        DEFAULT_SUBMISSION_COUNTS_MAP.put(1L, 2);
        DEFAULT_SUBMISSION_COUNTS_MAP.put(2L, 2);

        /** setup the phase name <-> resource role  id map **/

        PHASE_RESOURCE_ROLE_MAP.put(PhaseType.SPECIFICATION_REVIEW_PHASE.getName(),
                DefaultProjectPaymentCalculator.SPECIFICATION_REVIEWER_RESOURCE_ROLE_ID);

        PHASE_RESOURCE_ROLE_MAP.put(PhaseType.SCREENING_PHASE.getName(),
                DefaultProjectPaymentCalculator.PRIMARY_SCREENER_RESOURCE_ROLE_ID);

        PHASE_RESOURCE_ROLE_MAP.put(PhaseType.REVIEW_PHASE.getName(),
                DefaultProjectPaymentCalculator.REVIEWER_RESOURCE_ROLE_ID);

        PHASE_RESOURCE_ROLE_MAP.put(PhaseType.AGGREGATION.getName(),
                DefaultProjectPaymentCalculator.AGGREGATOR_RESOURCE_ROLE_ID);

        PHASE_RESOURCE_ROLE_MAP.put(PhaseType.FINAL_REVIEW_PHASE.getName(),
                DefaultProjectPaymentCalculator.FINAL_REVIEWER_RESOURCE_ROLE_ID);

        PHASE_RESOURCE_ROLE_MAP.put(PhaseType.ITERATIVE_REVIEW_PHASE.getName(),
                DefaultProjectPaymentCalculator.ITERATIVE_REVIEWER_RESOURCE_ROLE_ID);
    }


    /**
     * Represents the project payment calculator.
     */
    private DefaultProjectPaymentCalculator projectPaymentCalculator;

    /**
     * The project services which gets the contest phases via contest id.
     *
     * @since 2.0
     */
    private ProjectServices projectServices;

    /**
     * The phase template manager to get the phases of a specified contest type.
     *
     * @since 2.0
     */
    private DefaultPhaseTemplate templateManager;

    /**
     * The contest id.
     *
     * @since 2.0
     */
    private long projectId;

    /**
     * Represents the project category id.
     */
    private long projectCategoryId;

    /**
     * The review type, can be 'Internal' or 'Community'.
     *
     * @since 2.0
     */
    private ReviewType reviewType;

    /**
     * Represents the first place prize value to be used to compute the payment.
     */
    private BigDecimal prize;


    /**
     * Represents the default number of submissions to be considered when computing the payment.
     */
    private static final int DEFAULT_SUBMISSION_COUNT = 3;

    /**
     * <p>Constructs new <code>GetReviewCostAction</code> instance. This implementation does nothing.</p>
     */
    public GetReviewCostAction() {
    }

    /**
     * <p>Handles the incoming request. Calculates the reivew cost for requested contest and binds
     * them to request.</p>
     *
     * @throws Exception if an unexpected error occurs.
     */
    protected void executeAction() throws Exception {
        ActionHelper.checkInjectedFieldNull(getProjectPaymentCalculator(), "projectPaymentCalculator");


        Map<String, Object> result = new HashMap<String, Object>();

        // get phases first
        Project phases = null;

        if (projectId > 0) {
            phases = projectServices.getPhases(projectId);
        } else {
            phases = templateManager.applyTemplate(getPhaseTemplateName(projectCategoryId));
        }

        if (phases == null) {
            throw new IllegalArgumentException(
                    "Could not find out the phases for contest type ID:" + projectCategoryId + " contest ID:" +
                            projectId);
        }


        BigDecimal total = BigDecimal.ZERO;

        // iterate all the phases to calculate the cost
        for (Phase phase : phases.getAllPhases()) {
            if (PHASE_RESOURCE_ROLE_MAP.containsKey(phase.getPhaseType().getName())) {
                BigDecimal phasePayment = getPhasePayment(projectId, phase, projectCategoryId, reviewType);
                if(phasePayment != null) {
                    total = total.add(phasePayment);
                }
                result.put(phase.getPhaseType().getName(), phasePayment == null ? 0 : phasePayment);
            }
        }

        result.put("total", total);

        setResult(result);
    }

    /**
     * Gets the project payment calculator.
     *
     * @return the project payment calculator.
     */
    public DefaultProjectPaymentCalculator getProjectPaymentCalculator() {
        return projectPaymentCalculator;
    }

    /**
     * Sets the project payment calculator.
     *
     * @param projectPaymentCalculator the project payment calculator.
     */
    public void setProjectPaymentCalculator(DefaultProjectPaymentCalculator projectPaymentCalculator) {
        this.projectPaymentCalculator = projectPaymentCalculator;
    }

    /**
     * Gets the contest id.
     *
     * @return the contest id.
     * @since 2.0
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Sets the contest id.
     *
     * @param projectId the contest id.
     * @since 2.0
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    /**
     * Gets the project category id.
     *
     * @return the project category id.
     */
    public long getProjectCategoryId() {
        return projectCategoryId;
    }

    /**
     * Sets the project category id.
     *
     * @param projectCategoryId the project category id.
     */
    public void setProjectCategoryId(long projectCategoryId) {
        this.projectCategoryId = projectCategoryId;
    }

    /**
     * @return the prize
     */
    public BigDecimal getPrize() {
        return prize;
    }

    /**
     * @param prize the prize to set
     */
    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }

    /**
     * Sets the phase template manager.
     *
     * @param templateManager the phase template manager.
     * @since 2.0
     */
    public void setTemplateManager(DefaultPhaseTemplate templateManager) {
        this.templateManager = templateManager;
    }

    /**
     * Sets the project services.
     *
     * @param projectServices the projectServices to set
     * @since 2.0
     */
    public void setProjectServices(ProjectServices projectServices) {
        this.projectServices = projectServices;
    }

    /**
     * Sets the review type.
     *
     * @param reviewType the review type.
     * @since 2.0
     */
    public void setReviewType(ReviewType reviewType) {
        this.reviewType = reviewType;
    }


    /**
     * Gets the default submission number for the given contest category id.
     *
     * @param projectCategoryId the contest category id.
     * @return the default submission count number.
     * @since 2.0
     */
    private static int getDefaultSubmissionNumber(long projectCategoryId) {
        if (DEFAULT_SUBMISSION_COUNTS_MAP.containsKey(projectCategoryId)) {
            return DEFAULT_SUBMISSION_COUNTS_MAP.get(projectCategoryId);
        } else {
            return DEFAULT_SUBMISSION_COUNT;
        }
    }

    /**
     * Gets the total cost of the specified phase and contest category id.
     *
     * @param projectId         the contest id.
     * @param phase             the phase
     * @param projectCategoryId the contest category id.
     * @param reviewType        the review type.
     * @return total cost calculated.
     * @throws Exception if there is any error.
     * @since 2.0
     */
    private BigDecimal getPhasePayment(long projectId, Phase phase,
                                       long projectCategoryId, ReviewType reviewType) throws Exception {

        BigDecimal singleResourcePhasePayment = projectPaymentCalculator.getDefaultPayment(projectCategoryId,
                PHASE_RESOURCE_ROLE_MAP.get(phase.getPhaseType().getName()),
                getPrize(), getDefaultSubmissionNumber(projectCategoryId));

        System.out.println("singleResourcePhasePayment:" + singleResourcePhasePayment);

        int resourceNumber = getResourceNumberNeedToPayForPhase(projectId, phase, projectCategoryId, reviewType);

        return singleResourcePhasePayment == null ? null : singleResourcePhasePayment.multiply(
                new BigDecimal(resourceNumber));
    }


    /**
     * Gets the number of resources to work on a specified contest phase and get paid.
     *
     * @param projectId         the contest id.
     * @param phase             the phase
     * @param projectCategoryId the contest category id.
     * @param reviewType        the review type.
     * @return the number of resources.
     * @since 2.0
     */
    private static int getResourceNumberNeedToPayForPhase(long projectId, Phase phase, long projectCategoryId, ReviewType reviewType) {

        /** Review part (include iterative review) **/
        String phaseName = phase.getPhaseType().getName();

        if (ProjectCategory.getProjectCategoryById(projectCategoryId).getProjectType().getId()
                == ProjectType.STUDIO.getId()) {
            // default studio review type to INTERNAL
            reviewType = ReviewType.INTERNAL;
        }

        if (projectId <= 0) {

            // (A) project id <= 0, use assumed value

            if (phaseName.equals(PhaseType.ITERATIVE_REVIEW_PHASE.getName())) {
                // iterative review - only 1 iterative reviewer
                return 1;
            }

            if (phaseName.equals(PhaseType.REVIEW_PHASE.getName())) {
                if (ReviewType.INTERNAL == reviewType) {
                    // internal review, return 0
                    return 0;
                } else {
                    if (projectCategoryId == ContestType.CODE.getId()) {
                        // community review - code, 2 reviewers
                        return 2;
                    }

                    // others - 3 reviewers
                    return 3;
                }
            }
        } else {
            // (B) project id > 0, get review number from phase criteria

            if (phaseName.equals(PhaseType.ITERATIVE_REVIEW_PHASE.getName())
                    || phaseName.equals(PhaseType.REVIEW_PHASE.getName())
                    || phaseName.equals(PhaseType.SCREENING_PHASE.getName())) {
                if (phase.getAttribute("Reviewer Number") != null) {
                    return Integer.parseInt((String) phase.getAttribute("Reviewer Number"));
                } else {
                    return 1;
                }
            }
        }

        /** For other phase type, all default o 1**/
        return 1;
    }

    /**
     * Gets the phase template name through the project category id.
     *
     * @param projectCategoryId the project category id.
     * @return the phase template name.
     * @since 2.0
     */
    private String getPhaseTemplateName(long projectCategoryId) {

        ProjectCategory category = ProjectCategory.getProjectCategoryById(projectCategoryId);

        String[] templates = templateManager.getAllTemplateNames();
        for (String t : templates) {
            if (category.getName().equalsIgnoreCase(t)) {
                return t;
            }
        }
        for (String t : templates) {
            if (category.getProjectType().getName().equalsIgnoreCase(t)) {
                return t;
            }
        }

        return null;
    }


}
