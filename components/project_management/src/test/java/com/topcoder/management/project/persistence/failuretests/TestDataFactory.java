/*
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence.failuretests;

import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.ProjectStatus;

/**
 * <p>A factory producing the sample data which could be used for testing purposes. This class provides a set of static
 * constants and a set of static methods producing the sample data. Note that the methods produce a new copy of sample
 * data on each method call.</p>
 *
 * @author  isv
 * @version 1.0
 */
public class TestDataFactory {

    /**
     * <p>A <code>String</code> providing the zero-length string.</p>
     */
    public static final String ZERO_LENGTH_STRING = "";

    /**
     * <p>A <code>String</code> providing the string consisting of whitespace characters only.</p>
     */
    public static final String WHITESPACE_ONLY_STRING = " \t \n \t ";

    /**
     * <p>A <code>String</code> providing the configuration namespace for failure tests.</p>
     */
    public static final String NAMESPACE = "com.topcoder.management.project.persistence.failuretests";

    /**
     * <p>A <code>String</code> providing the valid value for operator.</p>
     */
    public static final String OPERATOR = "FailureTest";

    /**
     * <p>A <code>String</code> providing the valid value for project update reason.</p>
     */
    public static final String REASON = "Project Update Reason";

    /**
     * <p>A <code>long</code> providing the valid value for existing project ID.</p>
     */
    public static final long EXISTING_PROJECT_ID = 1;

    /**
     * <p>A <code>long</code> array representing an empty array of IDs.</p>
     */
    public static final long[] EMPTY_ID_ARRAY = new long[0];

    /**
     * <p>A <code>long</code> array containing the negative ID among valid IDs.</p>
     */
    public static final long[] NEGATIVE_ID_ARRAY = new long[] {1,2,3,-10};

    /**
     * <p>A <code>long</code> array containing the zero ID among valid IDs.</p>
     */
    public static final long[] ZERO_ID_ARRAY = new long[] {1,2,3,0};

    /**
     * <p>A <code>long</code> array containing the valid IDs.</p>
     */
    public static final long[] VALID_ID_ARRAY = new long[] {1,2,3};


    /**
     * <p>Generates a new instance of <code>Project</code> type initialized with valid data.</p>
     *
     * @return a new <code>Project</code> instance.
     */
    public static Project getProject() {
        ProjectType type = new ProjectType(1, "FailureType");
        ProjectCategory category = new ProjectCategory(1, "FailureCategory", type);
        ProjectStatus status = new ProjectStatus(1, "FailureStatus");
        Project project = new Project(category, status);
        project.setId(1);
        return project;
    }

    /**
     * <p>Generates a new instance of <code>Project</code> type initialized with random data.</p>
     *
     * @return a new <code>Project</code> instance.
     */
    public static Project getProjectWithZeroId() {
        ProjectType type = new ProjectType(1, "FailureType");
        ProjectCategory category = new ProjectCategory(1, "FailureCategory", type);
        ProjectStatus status = new ProjectStatus(1, "FailureStatus");
        Project project = new Project(category, status);
        project.setId(0);
        return project;
    }

    /**
     * <p>Generates a new instance of <code>Project</code> type initialized with non-existing category.</p>
     *
     * @return a new <code>Project</code> instance.
     */
    public static Project getProjectWithInvalidCategory() {
        ProjectType type = new ProjectType(1, "FailureType");
        ProjectCategory category = new ProjectCategory(2, "FailureCategory", type);
        ProjectStatus status = new ProjectStatus(1, "FailureStatus");
        Project project = new Project(category, status);
        project.setId(1);
        return project;
    }

    /**
     * <p>Generates a new instance of <code>Project</code> type initialized with invalid status.</p>
     *
     * @return a new <code>Project</code> instance with invalid status.
     */
    public static Project getProjectWithInvalidStatus() {
        ProjectType type = new ProjectType(1, "FailureType");
        ProjectCategory category = new ProjectCategory(1, "FailureCategory", type);
        ProjectStatus status = new ProjectStatus(2, "FailureStatus");
        Project project = new Project(category, status);
        project.setId(1);
        return project;
    }
}
