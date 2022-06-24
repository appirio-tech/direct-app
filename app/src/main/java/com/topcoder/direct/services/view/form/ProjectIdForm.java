/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.view.form;

import java.io.Serializable;

/**
 * <p>A form bean providing the ID for the project requested for some processing.</p>
 *
 * <p>
 * Version 1.1 (Direct Software Submission Viewer Assembly 1.0) Change notes:
 *   <ol>
 *     <li>Added <code>Aware</code> interface.</li>
 *   </ol>
 * </p>

 * @author isv
 * @version 1.1
 */
public class ProjectIdForm implements Serializable {

    /**
     * <p>An interface to be implemented by the parties interested in getting the values on parameters set to this form.
     * </p>
     */
    public static interface Aware {

        /**
         * <p>Sets the value of project ID parameter set to this form.</p>
         *
         * @param projectId a <code>long</code> providing the value of project ID parameter set to this form.
         */
        public void setProjectId(long projectId);
    }

    /**
     * <p>A <code>long</code> providing the ID of a requested project.</p>
     */
    private long projectId;

    /**
     * <p>An <code>Aware</code> referencing the object to be notified on parameters of this form.</p>
     */
    private Aware aware;

    /**
     * <p>Constructs new <code>ProjectIdForm</code> instance. This implementation does nothing.</p>
     */
    public ProjectIdForm() {
    }

    /**
     * <p>Constructs new <code>ProjectIdForm</code> instance to be used for notification of specified party on
     * parameters of this form.</p>
     *
     * @param aware an <code>Aware</code> referencing the object to be notified on parameters of this form.
     */
    public ProjectIdForm(Aware aware) {
        this.aware = aware;
    }

    /**
     * <p>Gets the ID for the requested project.</p>
     *
     * @return a <code>long</code> providing the ID of a requested project.
     */
    public long getProjectId() {
        return this.projectId;
    }

    /**
     * <p>Sets the ID for the requested project.</p>
     *
     * @param projectId a <code>long</code> providing the ID of a requested project.
     */
    public void setProjectId(long projectId) {
        this.projectId = projectId;
        if (this.aware != null) {
            this.aware.setProjectId(projectId);
        }
    }
}
