/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.dto.contest;

/**
 * <p>A <code>Enum</code> providing all project phase types.</p>
 * 
 * @author TCSASSEMBLER
 * @version 1.0
 * @since TC Direct Contest Dashboard Update Assembly
 */
public enum ProjectPhaseType {
    /**
     * The specification submission type.
     */
    SPECIFICATION_SUBMISSION(13, "specSub", "Spec Sub", 1),
    
    /**
     * The specification review type.
     */
    SPECIFICATION_REVIEW(14, "specReview", "Spec Review", 2),
    
    /**
     * The software registration type.
     */
    REGISTRATION(1, "registration", "Registration", 3),
    
    /**
     * The submission type.
     */
    SUBMISSION(2, "submission", "Submission", 7),
    
    /**
     * The screening type.
     */
    SCREENING(3, "screening", "Screening", 8),
    
    /**
     * The review type.
     */
    REVIEW(4, "review", "Review", 9),
    
    /**
     * The appeals type.
     */
    APPEALS(5, "appeals", "Appeals", 10),
    
    /**
     * The appeal response type.
     */
    APPEALS_RESPONSE(6, "appealsRes", "Appeals Res", 16),
    
    /**
     * The aggregation type.
     */
    AGGREGATION(7, "aggregation", "Aggr", 17),
    
    /**
     * The aggregation review type.
     */
    AGGREGATION_REVIEW(8, "aggregationReview", "Aggr Review", 13),
    
    /**
     * The final fix type.
     */
    FINAL_FIX(9, "finalFix", "Final Fix", 18),
    
    /**
     * The final review type.
     */
    FINAL_REVIEW(10, "finalReview", "Final Review", 19),
    
    /**
     * The approval type.
     */
    APPROVAL(11, "approval", "Approval", 20),
    
    /**
     * The post moterm type.
     */
    POST_MOTERM(12, "postMoterm", "Post Moterm", 21),
    
    /**
     * The milestone submission type.
     */
    MILESTONE_SUBMISSION(15, "milestoneSubmission", "Milestone Submission", 4),
    
    /**
     * The milestone submission type.
     */
    MILESTONE_SCREENING(16, "milestoneScreening", "Milestone Screening", 5),
    
    /**
     * The milestone submission type.
     */
    MILESTONE_REVIEW(17, "milestoneReview", "Milestone Review", 6),

    /**
     * The secondary Reviewer Review phase.
     */
    SECONDARY_REVIEW(18, "secondaryReview", "Secondary Review", 12),

    /**
     * The primary review evaluation phase.
     */
    PRIMARY_REVIEW_EVALUATION(19, "primaryReviewEvaluation", "Primary Review Evaluation", 13),

    /**
     * The new appeals phase.
     */
    NEW_APPEALS(20, "newAppeals", "New Appeals", 14),


    /**
     * The new appeals response phase.
     */
    PRIMARY_APPEALS_RESPONSE(21, "primaryAppealsResponse", "Primary Appeals Response", 15);
    
    /**
     * The phase type id.
     */
    private int phaseTypeId;
    
    /**
     * The html class.
     */
    private String htmlClass;
    
    /**
     * The short name.
     */
    private String shortName;
    
    private int order;
    
    /**
     * The constructor.
     * 
     * @param id the id
     * @param clazz the html class
     * @param sName the short name
     */
    private ProjectPhaseType(int id, String clazz, String sName, int order) {
        phaseTypeId = id;
        htmlClass = clazz;
        shortName = sName;
        this.order = order;
    }
    
    /**
     * Find project phase type by specified id.
     * 
     * @param id the id
     * @return the phase type
     */
    public static ProjectPhaseType findProjectPhaseType(int id) {
        for (ProjectPhaseType phase : ProjectPhaseType.values()) {
            if (phase.phaseTypeId == id) {
                return phase;
            }
        }
        
        return SUBMISSION;
    }

    /**
     * Gets the phaseTypeId field.
     * 
     * @return the phaseTypeId
     */
    public int getPhaseTypeId() {
        return phaseTypeId;
    }

    /**
     * Gets the htmlClass field.
     * 
     * @return the htmlClass
     */
    public String getHtmlClass() {
        return htmlClass;
    }

    /**
     * Gets the shortName field.
     * 
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }
    
    public int getOrder() {
        return order;
    }
}
