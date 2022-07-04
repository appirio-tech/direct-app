package com.topcoder.project.service.failuretests;

import com.cronos.onlinereview.external.ExternalProject;
import com.cronos.onlinereview.external.ProjectRetrieval;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.impl.ExternalProjectImpl;

/**
 * <p>
 * Mock implemetation of ProjectRetrieval.
 * </p>
 * 
 * @author mittu
 * @version 1.0
 */
public class MockProjectRetrieval implements ProjectRetrieval {
    /**
     * <p>
     * Represents the state of the mock.
     * </p>
     */
    private byte state = 0x0;

    /**
     * @see com.cronos.onlinereview.external.ProjectRetrieval#retrieveProject(long)
     */
    public ExternalProject retrieveProject(long id) throws RetrievalException {
        if (this.state == 0x1) {
            throw new RetrievalException("Fail");
        }
        if (id == 1) {
            return new ExternalProjectImpl(id, 1, "1.0");
        } else {
            return null;
        }
    }

    /**
     * @see com.cronos.onlinereview.external.ProjectRetrieval#retrieveProject(java.lang.String,
     *      java.lang.String)
     */
    public ExternalProject[] retrieveProject(String name, String version) throws RetrievalException {
        return null;
    }

    /**
     * @see com.cronos.onlinereview.external.ProjectRetrieval#retrieveProjects(long[])
     */
    public ExternalProject[] retrieveProjects(long[] ids) throws RetrievalException {
        return null;
    }

    /**
     * @see com.cronos.onlinereview.external.ProjectRetrieval#retrieveProjects(java.lang.String[],
     *      java.lang.String[])
     */
    public ExternalProject[] retrieveProjects(String[] names, String[] versions) throws RetrievalException {
        return null;
    }

    /**
     * <p>
     * Sets the state of the mock.
     * </p>
     * 
     * @param state
     */
    public void setState(byte state) {
        this.state = state;
    }
}
