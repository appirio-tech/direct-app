/*
 * Copyright (C) 2017 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project;

import java.io.Serializable;

/**
 * <p>
 * This is a MMProblem entity which represents the Marathon Match Problem entry.
 * </p>
 *
 * @author TCSCODER
 * @version 1.0
 */
public class MMProblem implements Serializable {
    /**
     * Problem Id
     */
    private long id = -1L;

    /**
     * Problem name
     */
    private String name;

    /**
     * Problem full text
     */
    private String problemText;

    /**
     * Problem name default format
     */
    public static final String DEFAULT_PROBLEM_NAME = "Match Problem: %s";

    /**
     * Problem text default format
     */
    public static final String DEFAULT_PROBLEM_TEXT = "Match Problem: <br/>%s <br/> Match Rule:<br/>%s";

    /**
     * Getter for problem Id
     *
     * @return problem Id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for problem Id
     *
     * @param id problem Id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for problem name
     *
     * @return problem name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for problem name
     *
     * @param name problem name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for problem text
     *
     * @return problem text
     */
    public String getProblemText() {
        return problemText;
    }

    /**
     * Setter for problem text
     *
     * @param problemText problem text
     */
    public void setProblemText(String problemText) {
        this.problemText = problemText;
    }
}
