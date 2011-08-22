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
    SPECIFICATION_SUBMISSION(13, "specSub", "Spec Sub"),
    
    /**
     * The specification review type.
     */
    SPECIFICATION_REVIEW(14, "specReview", "Spec Review"),
    
    /**
     * The software registration type.
     */
    REGISTRATION(1, "registration", "Registration"),
    
    /**
     * The submission type.
     */
    SUBMISSION(2, "submission", "Submission"),
    
    /**
     * The screening type.
     */
    SCREENING(3, "screening", "Screening"),
    
    /**
     * The review type.
     */
    REVIEW(4, "review", "Review"),
    
    /**
     * The appeals type.
     */
    APPEALS(5, "appeals", "Appeals"),
    
    /**
     * The appeal response type.
     */
    APPEALS_RESPONSE(6, "appealsRes", "Appeals Res"),
    
    /**
     * The aggregation type.
     */
    AGGREGATION(7, "aggregation", "Aggr"),
    
    /**
     * The aggregation review type.
     */
    AGGREGATION_REVIEW(8, "aggregationReview", "Aggr Review"),
    
    /**
     * The final fix type.
     */
    FINAL_FIX(9, "finalFix", "Final Fix"),
    
    /**
     * The final review type.
     */
    FINAL_REVIEW(10, "finalReview", "Final Review"),
    
    /**
     * The approval type.
     */
    APPROVAL(11, "approval", "Approval"),
    
    /**
     * The post moterm type.
     */
    POST_MOTERM(12, "postMoterm", "Post Moterm"),
    
    /**
     * The milestone submission type.
     */
    MILESTONE_SUBMISSION(15, "milestoneSubmission", "Milestone Submission"),
    
    /**
     * The milestone submission type.
     */
    MILESTONE_SCREENING(16, "milestoneScreening", "Milestone Screening"),
    
    /**
     * The milestone submission type.
     */
    MILESTONE_REVIEW(17, "milestoneReview", "Milestone Review");
    
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
    
    /**
     * The constructor.
     * 
     * @param id the id
     * @param clazz the html class
     * @param sName the short name
     */
    private ProjectPhaseType(int id, String clazz, String sName) {
        phaseTypeId = id;
        htmlClass = clazz;
        shortName = sName;
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
}
