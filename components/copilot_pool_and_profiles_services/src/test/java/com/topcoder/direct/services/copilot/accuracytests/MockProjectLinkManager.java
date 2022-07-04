/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.accuracytests;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.link.ProjectLink;
import com.topcoder.management.project.link.ProjectLinkManager;
import com.topcoder.management.project.link.ProjectLinkType;


/**
 * Mock for testing!
 *
 * @author onsky
 * @version 1.0
  */
public class MockProjectLinkManager implements ProjectLinkManager {
    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public void checkForCycle(long arg0) throws PersistenceException {
    }

    /**
     * Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public ProjectLinkType[] getAllProjectLinkTypes() throws PersistenceException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public ProjectLink[] getDestProjectLinks(long arg0)
        throws PersistenceException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     *
     * @return Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public ProjectLink[] getSourceProjectLinks(long arg0)
        throws PersistenceException {
        return null;
    }

    /**
     * Mock for testing!
     *
     * @param arg0 Mock for testing!
     * @param arg1 Mock for testing!
     * @param arg2 Mock for testing!
     *
     * @throws PersistenceException Mock for testing!
     */
    public void updateProjectLinks(long arg0, long[] arg1, long[] arg2)
        throws PersistenceException {
    }
}
