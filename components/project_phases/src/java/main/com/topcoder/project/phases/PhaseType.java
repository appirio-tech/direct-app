/*
 * Copyright (C) 2006 - 2014 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.phases;

import java.io.Serializable;

/**
 * <p>
 * This class represents the phase type. A phase type consists of a numeric identifier and a name. This class is
 * serializable.
 * </p>
 * <p>
 * Version 2.1 - TC Direct Replatforming Release 1 change note:
 * <ul>
 * <li>Add static phases type for checkpoint phases.
 * </ul>
 * </p>
 *
 * <p>
 * Version 2.2 (Module Assembly - TC Cockpit - Studio - Final Fixes Integration Part One Assembly) Change notes:
 *   <ol>
 *     <li>Added {@link #FINAL_FIX_PHASE} constant.</li>
 *     <li>Added {@link #FINAL_REVIEW_PHASE} constant.</li>
 *   </ol>
 * </p>
 *
 * <p>
 * Version 2.3 (TopCoder Direct - Review Cost Calculation Quick Updates) @author GreatKevin @challenge 30044580
 * <ul>
 *     <li>Added {@link #AGGREGATION}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Thread Safety: This class is mutable. so it's not thread safe.
 * </p>
 *
 * @author oldbig, littlebull, isv
 * @version 2.3
 */
public class PhaseType implements Serializable {

    /**
     * represents Submission phase
     */
    public static final String SUBMISSION = "Submission";

    /**
     * represents Registration phase
     */
    public static final String REGISTRATION = "Registration";

    public static final PhaseType REGISTRATION_PHASE = new PhaseType(1, "Registration");

    public static final PhaseType SUBMISSION_PHASE = new PhaseType(2, "Submission");

    public static final PhaseType SPECIFICATION_SUBMISSION_PHASE = new PhaseType(13, "Specification Submission");

    public static final PhaseType SPECIFICATION_REVIEW_PHASE = new PhaseType(14, "Specification Review");

    public static final PhaseType APPROVAL_PHASE = new PhaseType(11, "Approval");

    /**
     * <p>A <code>PhaseType</code> referencing the <code>Final Review</code> phase type.</p>
     * 
     * @since 2.2
     */
    public static final PhaseType FINAL_REVIEW_PHASE = new PhaseType(10, "Final Review");

    /**
     * <p>A <code>PhaseType</code> referencing the <code>Final Fix</code> phase type.</p>
     *
     * @since 2.2
     */
    public static final PhaseType FINAL_FIX_PHASE = new PhaseType(9, "Final Fix");

    public static final PhaseType SCREENING_PHASE = new PhaseType(3, "Screening");
    /**
     * Represents the checkpoint submission phase.
     * 
     * @since 2.1
     */
    public static final PhaseType CHECKPOINT_SUBMISSION_PHASE = new PhaseType(15, "Checkpoint Submission");
    
    /**
     * Represents the checkpoint screening phase.
     * 
     * @since 2.1
     */
    public static final PhaseType CHECKPOINT_SCREENING_PHASE = new PhaseType(16, "Checkpoint Screening");
    
    /**
     * Represents the checkpoint review phase.
     * 
     * @since 2.1
     */
    public static final PhaseType CHECKPOINT_REVIEW_PHASE = new PhaseType(17, "Checkpoint Review");

    public static final PhaseType REVIEW_PHASE = new PhaseType(4, "Review");
	
    public static final PhaseType ITERATIVE_REVIEW_PHASE = new PhaseType(18, "Iterative Review");

    public static final PhaseType AGGREGATION = new PhaseType(7, "Aggregation");

    /**
     * Represents the phase type id. Initialized in the constructor and could be accessed via getter and setter method.
     * The value could not be negative .
     */
    private long id;

    /**
     * Represents the phase type name. Initialized in the constructor and could be accessed via getter and setter
     * method. The value could not be null .
     */
    private String name;


    /**
     * The constructor with the phase type id and name.
     *
     * @param id
     *            the phase type id
     * @param name
     *            the phase type name
     * @throws IllegalArgumentException
     *             if <code>id</code> is negative or <code>name</code> is null
     */
    public PhaseType(long id, String name) {
        setId(id);
        setName(name);
    }

	public PhaseType() {

    }

    /**
     * Gets the phase type id.
     *
     * @return the phase type id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the phase type id.
     *
     * @param id
     *            the phase type id
     * @throws IllegalArgumentException
     *             if <code>id</code> is negative
     */
    public void setId(long id) {
        ProjectPhaseHelper.checkLongNotNegative(id, "id");

        this.id = id;
    }

    /**
     * Gets the phase type name.
     *
     * @return the phase type name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the phase type name.
     *
     * @param name
     *            the phase type name.
     * @throws IllegalArgumentException
     *             if <code>name</code> is null
     */
    public void setName(String name) {
        ProjectPhaseHelper.checkObjectNotNull(name, "name");

        this.name = name;
    }
}
