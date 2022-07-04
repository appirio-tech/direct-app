/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads;

import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.resource.ResourceManager;

/**
 * <p>
 * <code>ManagerProvider</code> provides instances of entity managers used in <code>UploadServices</code>
 * interface. It contains all managers kept from the external components.
 * </p>
 *
 * <p>
 * Thread safety: the implementations are not required to be thread safe but stateless.
 * </p>
 *
 * @author fabrizyo, cyberjag
 * @version 1.0
 */
public interface ManagersProvider {
    /**
     * <p>
     * Returns a <code>ResourceManager</code> instance. This is used in <code>UploadServices</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>ResourceManager</code> instance
     */
    public ResourceManager getResourceManager();

    /**
     * <p>
     * Returns a <code>ProjectManager</code> instance. This is used in <code>UploadServices</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>ProjectManager</code> instance
     */
    public ProjectManager getProjectManager();

    /**
     * <p>
     * Returns a <code>PhaseManager</code> instance. This is used in <code>UploadServices</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>PhaseManager</code> instance
     */
    public PhaseManager getPhaseManager();

    /**
     * <p>
     * Returns a <code>ScreeningManager</code> instance. This is used in <code>UploadServices</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>ScreeningManager</code> instance
     */
    public ScreeningManager getScreeningManager();

    /**
     * <p>
     * Returns a <code>UploadManager</code> instance. This is used in <code>UploadServices</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>UploadManager</code> instance
     */
    public UploadManager getUploadManager();
}
