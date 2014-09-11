/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service.accuracytests;

import com.cronos.onlinereview.external.ExternalProject;
import com.cronos.onlinereview.external.ProjectRetrieval;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.impl.ExternalProjectImpl;

/**
 * <p>
 * Mock implementation of ProjectRetrieval used for accuracy test cases.
 * </p>
 *
 * @author kshatriyan
 * @version 1.0
 */
public class MockProjectRetrieval implements ProjectRetrieval {

    /**
     * Returns a sample ExternalProject.
     *
     * @param arg0
     *            The project id.
     *
     * @return a sample ExternalProject.
     *
     * @throws RetrievalException
     *             Not throwm.
     */
    public ExternalProject retrieveProject(long arg0) throws RetrievalException {
        ExternalProjectImpl externalProject = new ExternalProjectImpl(1, 1, "1.0");
        externalProject.addTechnology("Java");
        externalProject.addTechnology("C#");
        return externalProject;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            String parameter.
     * @param arg1
     *            String parameter.
     *
     * @return null always.
     *
     * @throws RetrievalException
     *             Not thrown.
     */
    public ExternalProject[] retrieveProject(String arg0, String arg1) throws RetrievalException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            Array of project ids.
     * @return null always.
     *
     * @throws RetrievalException
     *             Not thrown.
     */
    public ExternalProject[] retrieveProjects(long[] arg0) throws RetrievalException {
        return null;
    }

    /**
     * Not implemented.
     *
     * @param arg0
     *            String parameter.
     * @param arg1
     *            String parameter.
     *
     * @return null always.
     *
     * @throws RetrievalException
     *             Not thrown.
     */
    public ExternalProject[] retrieveProjects(String[] arg0, String[] arg1) throws RetrievalException {
        return null;
    }

}
