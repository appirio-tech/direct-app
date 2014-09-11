/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.link;

import com.topcoder.management.project.Project;
import com.topcoder.util.errorhandling.BaseRuntimeException;

import java.util.List;

/**
 * <p>An exception to be thrown in case there is a cycle detected in project links.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since Contest Dependency Automation assembly 
 */
public class ProjectLinkCycleException extends BaseRuntimeException {

    /**
     * <p>A <code>List</code> holding the projects comprising the cycle.</p>
     */
    private List<Project> cycle;

    /**
     * <p>Constructs new <code>ProjectLinkCycleException</code> instance with specified list of IDs in cycle.</p>
     *
     * @param cycle a <code>List</code> holding the projects comprising the cycle.
     */
    public ProjectLinkCycleException(List<Project> cycle) {
        super("There is a cyclic dependency in project links. The following projects are in cycle: "
              + buildCycleText(cycle));
        this.cycle = cycle;
    }

    /**
     * <p>Constructs new <code>ProjectLinkCycleException</code> instance with specified description of the error.</p>
     *
     * @param message a <code>String</code> providing the description of the encountered error.
     */
    public ProjectLinkCycleException(String message) {
        super(message);
    }

    /**
     * <p>Constructs new <code>ProjectLinkCycleException</code> instance with specified description and original cause
     * of the error.</p>
     *
     * @param message a <code>String</code> providing the description of the encountered error.
     * @param cause a <code>Throwable</code> providing the original cause of the error.
     */
    public ProjectLinkCycleException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * <p>Constructs new <code>ProjectLinkCycleException</code> instance with specified original cause of the error.</p>
     *
     * @param cause a <code>Throwable</code> providing the original cause of the error.
     */
    public ProjectLinkCycleException(Throwable cause) {
        super(cause);
    }

    /**
     * <p>Gets the projects in cycle.</p>
     *
     * @return a <code>List</code> listing the projects in cycle.
     */
    public List<Project> getCycle() {
        return this.cycle;
    }

    /**
     * <p>Builds the textual presentation of the specified cycle in project dependencies.</p>
     *
     * @param cycle a <code>List</code> holding the projects comprising the cycle.
     * @return a <code>String</code> providing the
     */
    private static String buildCycleText(List<Project> cycle) {
        StringBuilder b = new StringBuilder();
        for (Project project : cycle) {
            if (b.length() > 0) {
                b.append(", ");
            }
            b.append(project.getProperty("Project Name"));
            b.append(" ").append(project.getProperty("Project Version"));
            b.append(" (").append(project.getId()).append(")");
        }
        return b.toString();
    }
}
