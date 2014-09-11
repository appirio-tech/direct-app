/*
 * Copyright (C) 2013 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.project.task.impl;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.topcoder.direct.services.project.task.NotificationService;
import com.topcoder.direct.services.project.task.TaskManagementConfigurationException;
import com.topcoder.service.user.UserService;
import com.topcoder.util.log.Log;

/**
 * <p>
 * Base class for the JPA based services of this component.
 * </p>
 * <p>
 * It holds common configuration parameters (provides variables, getters, setters and checkInitialization
 * method).
 * </p>
 * <p>
 * Sample Configuration: (the log is not required)
 * <pre>
    &lt;property name=&quot;userService&quot; ref=&quot;mockUserService&quot;/&gt;
    &lt;property name=&quot;notificationService&quot; ref=&quot;notificationService&quot;/&gt;
 * </pre>
 * </p>
 * <p>
 * <b>Thread Safety:</b>This class is mutable, but can be used thread safely under
 * following conditions: setters should not be
 * called after initialization and method arguments will not be used concurrently.
 * </p>
 *
 * @author Mozgastik, TCSDEVELOPER
 * @version 1.0
 */
public abstract class BaseJPAService {

    /**
     * <p>
     * Represents the logger for performing logging. If null, logging will not be performed.
     * </p>
     * <p>
     * It is fully mutable, has protected getter and public setter.
     * </p>
     * <p>
     * It can be any value.
     * </p>
     */
    private Log log;


    /**
     * <p>
     * Represents the service for managing users.
     * </p>
     * <p>
     * It is fully mutable, has protected getter and public setter.
     * </p>
     * <p>
     * Technically can be any value, but will be validated in checkInitialization method to be not null.
     * </p>
     */
    private UserService userService;

    /**
     * <p>
     * Represents the JPA entity manager for accessing persistence.
     * </p>
     * <p>
     * It is fully mutable, has protected getter and public setter.
     * </p>
     * <p>
     * Technically can be any value, but will be validated in checkInitialization method to be not null.
     * </p>
     */
    @PersistenceContext(unitName = "taskPersistenceUnit")
    private EntityManager entityManager;

    /**
     * <p>
     * Represents the service for sending notifications.
     * </p>
     * <p>
     * It is fully mutable, has protected getter and public setter.
     * </p>
     * <p>
     * Technically can be any value, but will be validated in checkInitialization method to be not null.
     * </p>
     */
    private NotificationService notificationService;



    /**
     * <p>
     * Creates the instance of BaseJPAService (since this class is an abstract class, it is actually called by
     * its concrete subclass).
     * </p>
     * <p>
     * This is the default constructor of BaseJPAService.
     * </p>
     */
    protected BaseJPAService() {
        // does nothing
    }

    /**
     * <p>
     * Validates configuration parameters.
     * </p>
     * <p>
     * NOTE, in this class the required fields are:
     * userService, entityManager, notificationService.
     * The log is optional.
     * </p>
     *
     * @throws TaskManagementConfigurationException if any configuration parameter has invalid value.
     */
    @PostConstruct
    public void checkInitialization() {
        ServiceHelper.checkState(userService == null,
            "The userService is not properly injected.");
        ServiceHelper.checkState(entityManager == null,
            "The entityManager is not properly injected.");
        ServiceHelper.checkState(notificationService == null,
            "The notificationService is not properly injected.");

    }

    /**
     * <p>
     * Gets the logger for performing logging.
     * </p>
     * @return the logger for performing logging. It may be null.
     */
    protected Log getLog() {
        return log;
    }

    /**
     * <p>
     * Sets the logger for performing logging.
     * </p>
     * @param log the logger for performing logging. If null, logging will not be performed.
     */
    public void setLog(Log log) {
        this.log = log;
    }


    /**
     * <p>
     * Gets the service for managing users.
     * </p>
     * @return the service for managing users.
     */
    protected UserService getUserService() {
        return userService;
    }

    /**
     * <p>
     * Sets the service for managing users.
     * </p>
     * @param userService the service for managing users.
     */
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * <p>
     * Gets the JPA entity manager for accessing persistence.
     * </p>
     * @return the JPA entity manager for accessing persistence.
     */
    protected EntityManager getEntityManager() {
/*        Query query = entityManager.createNativeQuery("set lock mode to wait");
        query.executeUpdate();*/
        return entityManager;
    }

    /**
     * <p>
     * Sets the JPA entity manager for accessing persistence.
     * </p>
     * @param entityManager the JPA entity manager for accessing persistence.
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * <p>
     * Gets the service for sending notifications.
     * </p>
     * @return the service for sending notifications.
     */
    protected NotificationService getNotificationService() {
        return notificationService;
    }

    /**
     * <p>
     * Sets the service for sending notifications.
     * </p>
     * @param notificationService the service for sending notifications.
     */
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

}
