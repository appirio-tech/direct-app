/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.scorecard.accuracytests;

import com.topcoder.management.scorecard.data.Scorecard;


/**
 * The dummy class used to validate the parameters.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class DummyParameters {
    /** The Scorecard instance used for test. */
    private Scorecard scorecard = null;

    /** The name of operator used for test. */
    private String operator = null;

    /** The value of id used for test. */
    private long id;

    /** The values of id array used for test. */
    private long[] ids = null;

    /** The flag of complete used for test. */
    private boolean complete;

    /** The name of the called method. */
    private String methodName = null;

    /**
     * Creates a new DummyParameters object.
     */
    public DummyParameters() {
        // do nothing
    }

    /**
     * Returns the scorecard.
     *
     * @return the scorecard.
     */
    public Scorecard getScorecard() {
        return scorecard;
    }

    /**
     * Assigns the scorecard.
     *
     * @param card the scorecard to assign.
     */
    public void setScorecard(Scorecard card) {
        this.scorecard = card;
    }

    /**
     * Returns the flag of complete.
     *
     * @return the flag of complete.
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Assigns the scorecard.
     *
     * @param complete the flag of complete to assign.
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * Returns the id.
     *
     * @return the id.
     */
    public long getId() {
        return id;
    }

    /**
     * Assigns the id.
     *
     * @param id the id to assign.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the id array.
     *
     * @return the id array.
     */
    public long[] getIds() {
        return ids;
    }

    /**
     * Assigns the id array.
     *
     * @param ids the id array to assign.
     */
    public void setIds(long[] ids) {
        this.ids = ids;
    }

    /**
     * Returns the operator.
     *
     * @return the operator.
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Assigns the operator.
     *
     * @param operator the operator to assign.
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Returns the name of the called method.
     *
     * @return the name of the called method.
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Assigns the name of the called method.
     *
     * @param methodName the the name of the called method to assign.
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
