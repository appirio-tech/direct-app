package com.topcoder.direct.services.copilot.stresstests;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.link.ProjectLink;
import com.topcoder.management.project.link.ProjectLinkManager;
import com.topcoder.management.project.link.ProjectLinkType;

public class MockProjectLinkManager implements ProjectLinkManager {

    public void checkForCycle(long sourceProjectId) throws PersistenceException {

    }

    public ProjectLinkType[] getAllProjectLinkTypes() throws PersistenceException {

        return null;
    }

    public ProjectLink[] getDestProjectLinks(long sourceProjectId) throws PersistenceException {

        return null;
    }

    public ProjectLink[] getSourceProjectLinks(long destProjectId) throws PersistenceException {

        return null;
    }

    public void updateProjectLinks(long sourceProjectId, long[] destProjectIds, long[] linkTypeIds)
        throws PersistenceException {

    }

}
