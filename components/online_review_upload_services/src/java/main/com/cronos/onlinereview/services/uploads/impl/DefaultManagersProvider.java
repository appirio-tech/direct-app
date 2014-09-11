/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads.impl;

import com.cronos.onlinereview.autoscreening.management.ScreeningManager;
import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.cronos.onlinereview.services.uploads.ManagersProvider;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.project.ProjectManager;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.util.log.Level;

/**
 * <p>
 * This is the default implementation of <code>ManagersProvider</code>. It provides instances of entity managers
 * used in <code>UploadServices</code> class. The managers are loaded using <code>ObjectFactory</code> or set
 * through constructor. The log is performed for all methods.
 * </p>
 *
 * <p>
 * A sample configuration file that can be used is given below.
 *
 * <pre>
 * &lt;Config name=&quot;com.cronos.onlinereview.services.uploads.impl.DefaultManagersProvider&quot;&gt;
 *      &lt;Property name=&quot;objectFactoryNamespace&quot;&gt;
 *          &lt;Value&gt;myObjectFactory&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;resourceManagerIdentifier&quot;&gt;
 *          &lt;Value&gt;resourceManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;phaseManagerIdentifier&quot;&gt;
 *          &lt;Value&gt;phaseManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;projectManagerIdentifier&quot;&gt;
 *          &lt;Value&gt;projectManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;screeningManagerIdentifier&quot;&gt;
 *          &lt;Value&gt;screeningManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;uploadManagerIdentifier&quot;&gt;
 *          &lt;Value&gt;uploadManager&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Config&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * Thread safety: It's impossible to change the state of this class, hence the class is thread safe.
 * </p>
 *
 * @author fabrizyo, cyberjag
 * @version 1.0
 */
public class DefaultManagersProvider implements ManagersProvider {

    /**
     * <p>
     * Represents the default namespace for this class used to load the configuration with
     * <code>ConfigManager</code>.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = DefaultManagersProvider.class.getName();

    /**
     * <p>
     * Represents the logger to log all operations, exceptions, etc. It is initialized statically.
     * </p>
     */
    private static final com.topcoder.util.log.Log LOG = com.topcoder.util.log.LogManager
			.getLog(DefaultManagersProvider.class.getName());

    /**
     * <p>
     * Represents the upload manager identifier used in the configuration file.
     * </p>
     */
    private static final String UPLOAD_MANAGER_IDENTIFIER = "uploadManagerIdentifier";

    /**
     * <p>
     * Represents the screening manager identifier used in the configuration file.
     * </p>
     */
    private static final String SCREENING_MANAGER_IDENTIFIER = "screeningManagerIdentifier";

    /**
     * <p>
     * Represents the phase manager identifier used in the configuration file.
     * </p>
     */
    private static final String PHASE_MANAGER_IDENTIFIER = "phaseManagerIdentifier";

    /**
     * <p>
     * Represents the project manager identifier used in the configuration file.
     * </p>
     */
    private static final String PROJECT_MANAGER_IDENTIFIER = "projectManagerIdentifier";

    /**
     * <p>
     * Represents the resource manager identifier used in the configuration file.
     * </p>
     */
    private static final String RESOURCE_MANAGER_IDENTIFIER = "resourceManagerIdentifier";

    /**
     * <p>
     * Represents the manager to manage the resources. It is defined in constructor and can be accessed using
     * {@link #getResourceManager()}. It cannot be <code>null</code>.
     * </p>
     */
    private final ResourceManager resourceManager;

    /**
     * <p>
     * Represents the manager to manage the projects. It is defined in constructor and can be accessed using
     * {@link #getProjectManager()}. It cannot be <code>null</code>.
     * </p>
     */
    private final ProjectManager projectManager;

    /**
     * <p>
     * Represents the manager to manage the phases of a project. It is defined in constructor and can be accessed
     * using {@link #getPhaseManager()}. It cannot be <code>null</code>.
     * </p>
     */
    private final PhaseManager phaseManager;

    /**
     * <p>
     * Represents the manager to manage the auto screening tasks. It is defined in constructor and can be accessed
     * using {@link #getScreeningManager()}. It cannot be <code>null</code>.
     * </p>
     */
    private final ScreeningManager screeningManager;

    /**
     * <p>
     * Represents the manager to manage the deliverables: Submission and Uploads. It is defined in constructor and
     * can be accessed using {@link #getUploadManager()}. It cannot be <code>null</code>.
     * </p>
     */
    private final UploadManager uploadManager;

    /**
     * <p>
     * Creates <code>DefaultManagersProvider</code> using the configuration with default namespace.
     * </p>
     *
     * @throws ConfigurationException
     *             If any error occurs during accessing configuration. If bad configuration is detected.
     */
    public DefaultManagersProvider() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Creates <code>DefaultManagersProvider</code> using the configuration with specified namespace.
     * </p>
     *
     * @param namespace
     *            the namespace to load configuration
     * @throws ConfigurationException
     *             If any error occurs during accessing configuration. If bad configuration is detected.
     * @throws IllegalArgumentException
     *             if namespace is <code>null</code> or trim to empty
     */
    public DefaultManagersProvider(String namespace) throws ConfigurationException {
        Helper.checkString(namespace, "namespace", LOG);
        this.resourceManager = (ResourceManager) Helper.createObject(namespace, RESOURCE_MANAGER_IDENTIFIER, null,
                LOG, ResourceManager.class);
        LOG.log(Level.INFO, "ResourceManager created using ObjectFactory");
        this.projectManager = (ProjectManager) Helper.createObject(namespace, PROJECT_MANAGER_IDENTIFIER, null,
                LOG, ProjectManager.class);
        LOG.log(Level.INFO, "ProjectManager created using ObjectFactory");
        this.phaseManager = (PhaseManager) Helper.createObject(namespace, PHASE_MANAGER_IDENTIFIER, null, LOG,
                PhaseManager.class);
        LOG.log(Level.INFO, "PhaseManager created using ObjectFactory");
        this.screeningManager = (ScreeningManager) Helper.createObject(namespace, SCREENING_MANAGER_IDENTIFIER,
                null, LOG, ScreeningManager.class);
        LOG.log(Level.INFO, "ScreeningManager created using ObjectFactory");
        this.uploadManager = (UploadManager) Helper.createObject(namespace, UPLOAD_MANAGER_IDENTIFIER, null, LOG,
                UploadManager.class);
        LOG.log(Level.INFO, "UploadManager created using ObjectFactory");
    }

    /**
     * <p>
     * Creates <code>DefaultManagersProvider</code> with the specified managers.
     * </p>
     *
     * @param resourceManager
     *            the manager to manage the resources
     * @param projectManager
     *            the manager to manage the projects.
     * @param phaseManager
     *            the manager to manage the phases of a project.
     * @param screeningManager
     *            the manager to manage the auto screening tasks.
     * @param uploadManager
     *            the manager to manage the deliverables: Submission and Uploads.
     * @throws IllegalArgumentException
     *             if any argument is <code>null</code>
     */
    public DefaultManagersProvider(ResourceManager resourceManager, ProjectManager projectManager,
            PhaseManager phaseManager, ScreeningManager screeningManager, UploadManager uploadManager) {
        Helper.checkNull(resourceManager, "resourceManager", LOG);
        Helper.checkNull(projectManager, "projectManager", LOG);
        Helper.checkNull(phaseManager, "phaseManager", LOG);
        Helper.checkNull(screeningManager, "screeningManager", LOG);
        Helper.checkNull(uploadManager, "uploadManager", LOG);

        this.resourceManager = resourceManager;
        this.projectManager = projectManager;
        this.phaseManager = phaseManager;
        this.screeningManager = screeningManager;
        this.uploadManager = uploadManager;
    }

    /**
     * <p>
     * Returns the <code>ResourceManager</code> instance. This is used in <code>DefaultUploadService</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>ResourceManager</code> instance
     */
    public ResourceManager getResourceManager() {
        LOG.log(Level.DEBUG, "Entered DefaultManagersProvider#getResourceManager()");
        try {
            return resourceManager;
        } finally {
            LOG.log(Level.DEBUG, "Exited DefaultManagersProvider#getResourceManager()");
        }
    }

    /**
     * <p>
     * Returns the <code>ProjectManager</code> instance. This is used in <code>DefaultUploadService</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>ProjectManager</code> instance
     */
    public ProjectManager getProjectManager() {
        LOG.log(Level.DEBUG, "Entered DefaultManagersProvider#getProjectManager()");
        try {
            return projectManager;
        } finally {
            LOG.log(Level.DEBUG, "Exited DefaultManagersProvider#getProjectManager()");
        }
    }

    /**
     * <p>
     * Returns the <code>PhaseManager</code> instance. This is used in <code>DefaultUploadService</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>PhaseManager</code> instance
     */
    public PhaseManager getPhaseManager() {
        LOG.log(Level.DEBUG, "Entered DefaultManagersProvider#getPhaseManager()");
        try {
            return phaseManager;
        } finally {
            LOG.log(Level.DEBUG, "Exited DefaultManagersProvider#getPhaseManager()");
        }
    }

    /**
     * <p>
     * Returns the <code>ScreeningManager</code> instance. This is used in <code>DefaultUploadService</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>ScreeningManager</code> instance
     */
    public ScreeningManager getScreeningManager() {
        LOG.log(Level.DEBUG, "Entered DefaultManagersProvider#getScreeningManager()");
        try {
            return screeningManager;
        } finally {
            LOG.log(Level.DEBUG, "Exited DefaultManagersProvider#getScreeningManager()");
        }
    }

    /**
     * <p>
     * Returns the <code>UploadManager</code> instance. This is used in <code>DefaultUploadService</code> to
     * retrieve this manager and perform all its operations.
     * </p>
     *
     * @return a <code>UploadManager</code> instance
     */
    public UploadManager getUploadManager() {
        LOG.log(Level.DEBUG, "Entered DefaultManagersProvider#getUploadManager()");
        try {
            return uploadManager;
        } finally {
            LOG.log(Level.DEBUG, "Exited DefaultManagersProvider#getUploadManager()");
        }
    }
}
