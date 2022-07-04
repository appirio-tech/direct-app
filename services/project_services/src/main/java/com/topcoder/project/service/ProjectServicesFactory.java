/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.project.service;

import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.ObjectFactory;

/**
 * <p>
 * This is a factory of <code>ProjectServices</code> implementations. It is used by <b>EJB</b> to
 * get the <code>ProjectServicesBean</code>. It instantiates the ProjectServicesBean
 * implementation in lazy mode using <code>ObjectFactory</code>.
 * </p>
 *
 * <p>
 * Here is the sample configuration for this class
 *
 * <pre>
 *  &lt;CMConfig&gt;
 *  &lt;!-- Configuration for ObjectFactory --&gt;
 *  &lt;Config name=&quot;com.topcoder.util.objectfactory&quot;&gt;
 *  &lt;Property name=&quot;projectServicesKey&quot;&gt;
 *  &lt;Property name=&quot;type&quot;&gt;
 *  &lt;Value&gt;com.topcoder.project.service.impl.ProjectServicesImpl&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Property&gt;
 *  &lt;/Config&gt;
 *  &lt;!-- Configuration for ProjectServicesFactory --&gt;
 *  &lt;Config name=&quot;com.topcoder.project.service.ProjectServicesFactory&quot;&gt;
 *  &lt;Property name=&quot;specNamespace&quot;&gt;
 *  &lt;Value&gt;com.topcoder.util.objectfactory&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;projectServicesKey&quot;&gt;
 *  &lt;Value&gt;projectServicesKey&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;Property name=&quot;loggerName&quot;&gt;
 *  &lt;Value&gt;FactoryLog&lt;/Value&gt;
 *  &lt;/Property&gt;
 *  &lt;/Config&gt;
 *  &lt;/CMConfig&gt;
 * </pre>
 *
 * </p>
 *
 * <p>
 * <strong>Thread Safety:</strong> This class must be thread safe. To obtain this the developer
 * must synchronize the set of projectService instance using a synchronized block. This block ,
 * therefore, will be processed once, using lazy mode. The methods must not be synchronized for the
 * EJB rules.
 * </p>
 *
 * @author fabrizyo, znyyddf
 * @version 1.1
 * @since 1.1
 */
public class ProjectServicesFactory {
    /**
     * <p>
     * Represents the default namespace of this class.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = ProjectServicesFactory.class.getName();

    /**
     * <p>
     * Represents the namespace to use with the <code>ConfigManagerSpecificationFactory</code>.
     * It is required.
     * </p>
     */
    private static final String SPEC_NAMESPACE = "specNamespace";

    /**
     * <p>
     * Represents the key for the <code>ProjectServices</code> to pass to
     * <code>ObjectFactory</code>. It is required.
     * </p>
     */
    private static final String PROJECTSERVICES_KEY = "projectServicesKey";

    /**
     * <p>
     * Represents the name of the log to get from the LogManager. It is optional, and if it is not
     * given, logging is not performed.
     * </p>
     */
    private static final String LOGGER_NAME = "loggerName";

    /**
     * <p>
     * Represents the <code>ProjectServices</code> instance. It is instantiated in lazy mode.
     * Synchronize the set of projectService instance using a synchronized block. This block,
     * therefore, will be processed once, using lazy mode.
     * </p>
     *
     * <p>
     * This variable is initially null at beginning and then set at the first call of
     * <code>getProjectServices</code> methods. It is created in the getProjectServices(String
     * namespace) method in lazy mode, can be accessed in the getProjectServices methods.
     * </p>
     */
    private static ProjectServices projectServices;

    /**
     * <p>
     * Represents the <code>Log</code> instance to log the method calls. It is instantiated in
     * lazy mode in the same manner and in the same synchronized block of projectService.
     * </p>
     *
     * <p>
     * This variable is initially null at beginning and then set at the first call of
     * <code>getProjectServices</code> methods. It is optional and can be null, in this case the
     * logging is not performed.
     * </p>
     */
    private static Log logger;

    /**
     * <p>
     * This object is used to synchronize access to projectServices. It is immutable and never be
     * null.
     * </p>
     */
    private static Object syncProjectServices = new Object();

    /**
     * <p>
     * This is the private constructor to avoid the instantiation.
     * </p>
     */
    private ProjectServicesFactory() {
        // does nothing
    }

    /**
     * <p>
     * Return the ProjectServicesBean implementation created by ObjectFactory using the default
     * namespace.
     * </p>
     *
     * @return the ProjectServicesBean implementation
     * @throws ConfigurationException
     *             If any configuration error occurs: missing required values, or errors while
     *             constructing the implementation
     */
    public static ProjectServices getProjectServices() {
        Util.log(logger, Level.INFO, "Enters ProjectServicesFactory#getProjectServices() method.");
        ProjectServices services = getProjectServices(DEFAULT_NAMESPACE);
        Util.log(logger, Level.INFO, "Exits ProjectServicesFactory#getProjectServices() method.");
        return services;
    }

    /**
     * <p>
     * Return the <code>ProjectServices</code> implementation created by
     * <code>ObjectFactory</code>. Instantiate the ProjectServices instance in lazy mode.
     * </p>
     *
     * @return the ProjectServices implementation
     * @param namespace
     *            the namespace to load the configuration
     * @throws ConfigurationException
     *             If any configuration error occurs: missing required values, or errors while
     *             constructing the implementation
     * @throws IllegalArgumentException
     *             if namespace is null or trim to empty
     */
    public static ProjectServices getProjectServices(String namespace) {
        Util.log(logger, Level.INFO, "Enters ProjectServicesFactory#getProjectServices(String namespace) method.");
        ExceptionUtils.checkNullOrEmpty(namespace, null, null,
                "The parameter[namespace] should not be null or trim to empty.");

        // if the projectServices is not null, return it immediately
        if (projectServices != null) {
            Util.log(logger, Level.INFO, "Exits ProjectServicesFactory#getProjectServices(String namespace) method.");
            return projectServices;
        }

        try {
            // synchronize all this stuff in a block
            synchronized (syncProjectServices) {
                // if the projectServices is null then create the instance and set it
                if (projectServices == null) {
                    // gets SPEC_NAMESPACE for ConfigManagerSpecificationFactory
                    String specNamespace = Util.getStringPropertyValue(namespace, SPEC_NAMESPACE, true);
                    // creates an instance of ObjectFactory
                    ObjectFactory objectFactory = Util.getObjectFactory(specNamespace);

                    // Read the PROJECTSERVICES_KEY property
                    String projectServicesKey = Util.getStringPropertyValue(namespace, PROJECTSERVICES_KEY, true);
                    // Create the ProjectServices instance using the previous key
                    projectServices = (ProjectServices) Util.createObject(objectFactory, projectServicesKey,
                            ProjectServices.class);

                    // gets name of the log and gets the logger instance from LogManager if
                    // necessary
                    String logName = Util.getStringPropertyValue(namespace, LOGGER_NAME, false);
                    logger = (logName == null) ? null : LogManager.getLog(logName);
                }
            }

            return projectServices;
        } catch (ConfigurationException e) {
            Util.log(logger, Level.ERROR, "ConfigurationException occurred in"
                    + " ProjectServicesFactory#getProjectServices(String namespace) method : " + e.getMessage());
            throw e;
        } finally {
            Util.log(logger, Level.INFO, "Exits ProjectServicesFactory#getProjectServices(String namespace) method.");
        }
    }
}
