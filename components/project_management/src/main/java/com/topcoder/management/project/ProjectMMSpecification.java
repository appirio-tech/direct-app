/*
 * Copyright (C) 2010 - 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

/**
 * <p>
 * This is a ProjectMMSpecification entity which represents the Marathon Match Specification. It extends from
 * AuditableObject class.
 * </p>
 * <p>
 * <strong>Thread-Safety:</strong> This class is mutable and not thread safe. But it will be used as entity so it'll not
 * cause any thread safe problem.
 * </p>
 *
 * @author bugbuka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ProjectMMSpecification extends AuditableObject {

    /** The id. */
    private long id;

    /**
     * Represents the roundOneIntroduction of ProjectStudioSpecification. The default value is null. It's changeable. It
     * could not be null or empty. It's accessed in setter and getter.
     */
    private String matchDetails;

    /**
     * Represents the colors of ProjectStudioSpecification. The default value is null. It's changeable. It could not be
     * null or empty. It's accessed in setter and getter.
     */
    private String matchRules;
    
    /** The problemId. */
    private long problemId;
    
    /** The problemName. */
    private String problemName;
    
    /**
     * Empty constructor.
     */
    public ProjectMMSpecification() {
        // empty
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Gets the problem name.
     *
     * @return the problem name
     */
    public String getProblemName() {
        return problemName;
    }

    /**
     * Sets the problem name.
     *
     * @param problemName the new problem name
     */
    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    /**
     * Gets the match details.
     *
     * @return the match details
     */
    public String getMatchDetails() {
        return matchDetails;
    }

    /**
     * Sets the match details.
     *
     * @param matchDetails the new match details
     */
    public void setMatchDetails(String matchDetails) {
        this.matchDetails = matchDetails;
    }

    /**
     * Gets the problem id.
     *
     * @return the problem id
     */
    public long getProblemId() {
        return problemId;
    }
    


    /**
     * Sets the problem id.
     *
     * @param problemId the new problem id
     */
    public void setProblemId(long problemId) {
        this.problemId = problemId;
    }

    /**
     * Gets the match rules.
     *
     * @return the match rules
     */
    public String getMatchRules() {
        return matchRules;
    }

    /**
     * Sets the match rules.
     *
     * @param matchRules the new match rules
     */
    public void setMatchRules(String matchRules) {
        this.matchRules = matchRules;
    }
}
