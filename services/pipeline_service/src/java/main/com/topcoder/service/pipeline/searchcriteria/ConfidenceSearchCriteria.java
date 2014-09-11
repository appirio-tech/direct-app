/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.pipeline.searchcriteria;


/**
 * <p>
 * The search criteria instance for searching with 'confidence' field.
 * </p>
 */
public class ConfidenceSearchCriteria extends ContestsSearchCriteria {
    /** serial version UID. */
    private static final long serialVersionUID = 4954043949517547111L;

    /** The value of confidence. */
    private int confidence;

    /** Compares with greater of confidence value. */
    private boolean useGreater;

    /** Compares with equal of confidence value. */
    private boolean useEqual;

    /** Compares with lesser of confidence value. */
    private boolean useLesser;

    /**
     * Default ctor.
     */
    public ConfidenceSearchCriteria() {
    }

    /**
     * Constructs with confidence and the operator. The three boolean could not be all false or all true.
     *
     * @param confidence the value of confidence.
     * @param useGreater
     * @param useEqual
     * @param useLesser
     */
    public ConfidenceSearchCriteria(int confidence, boolean useGreater, boolean useEqual, boolean useLesser) {
        this.confidence = confidence;
        this.useEqual = useEqual;
        this.useGreater = useGreater;
        this.useLesser = useLesser;
    }

    /**
     * Constructs the where clause for the criteria.
     *
     * @return where clause, could be empty, not null
     */
    public String getWhereClause() {
        String oper = null;

        if (useEqual) {
            if (useGreater) {
                if (!useLesser) {
                    oper = ">=";
                }
            } else if (useLesser) {
                oper = "<=";
            } else {
                oper = "=";
            }
        } else {
            if (useGreater) {
                if (!useLesser) {
                    oper = ">";
                } else {
                    oper = "!=";
                }
            } else if (useLesser) {
                oper = "<";
            }
        }

        if (oper != null) {
            return new StringBuffer("pinfo.confidence").append(oper).append(this.confidence).toString();
        }

        return "";
    }

    /**
     * Overrides the toString to print the value.
     *
     * @return the string for this criteria
     */
    public String toString() {
        return "ConfidenceSearchCriteria[confidence=" + confidence + ", useGreater=" + useGreater + ", useLesser = " +
        useLesser + ",useEqual = " + useEqual + "]";
    }
}
