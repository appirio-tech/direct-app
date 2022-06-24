/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.LookupService;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.LookupDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;

/**
 * <p>
 * This class is an implementation of LookupService that simply uses a pluggable LookupDAO instance to perform its
 * functionality. It's assumed that this class will be initialized using Spring IoC. This class uses Logging Wrapper
 * logger to log errors and debug information.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 *
 * ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {&quot;applicationContext.xml&quot;});
 * LookupService service = (LookupService) context.getBean(&quot;lookupService&quot;);
 * // get all copilot profile statuses
 * service.getAllCopilotProfileStatuses();
 * // get all copilot project statuses
 * service.getAllCopilotProjectStatuses();
 * // get all copilot types
 * service.getAllCopilotTypes();
 *
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class has mutable attributes, thus it's not thread safe. But it's assumed that it will
 * be initialized via Spring IoC before calling any business method, this way it's always used in thread safe manner.
 * It uses thread safe LookupDAO and Log instances.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class LookupServiceImpl extends LoggingEnabledService implements LookupService {
    /**
     * The lookup DAO to be used by this class. Cannot be null after initialization (validation is performed in
     * checkInit() method). Initialized by Spring setter injection. Has protected getter and public setter. Is used
     * in checkInit(), getAllCopilotProfileStatuses(), getAllCopilotProjectStatuses() and getAllCopilotTypes().
     */
    private LookupDAO lookupDAO;

    /**
     * Creates an instance of LookupServiceImpl.
     */
    public LookupServiceImpl() {
        // Do nothing
    }

    /**
     * Checks whether this class was initialized by Spring properly.
     *
     * @throws CopilotServiceInitializationException if the class was not initialized properly (e.g. when required
     *             properly is not specified or property value has invalid format)
     */
    @PostConstruct
    protected void checkInit() {
        super.checkInit();
        Helper.checkNullForInjectedValue(lookupDAO, "lookupDAO");
    }

    /**
     * Retrieves all copilot profile statuses from persistence. Returns an empty list if none are found.
     *
     * @return the retrieved copilot profile statuses (not null, doesn't contain null)
     * @throws CopilotServiceException if any error occurred
     */
    public List<CopilotProfileStatus> getAllCopilotProfileStatuses() throws CopilotServiceException {
        final String method = "LookupServiceImpl#getAllCopilotProfileStatuses";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        try {
            List<CopilotProfileStatus> list = lookupDAO.getAllCopilotProfileStatuses();
            Helper.logReturnValue(getLog(), list);
            Helper.logMethodExit(method, getLog(), date);
            return list;
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException(
                "Error occurred when executing getAllCopilotProfileStatuses.", e), method, getLog());
        }
    }

    /**
     * Retrieves all copilot project statuses from persistence. Returns an empty list if none are found.
     *
     * @return the retrieved copilot project statuses (not null, doesn't contain null)
     * @throws CopilotServiceException if any error occurred
     */
    public List<CopilotProjectStatus> getAllCopilotProjectStatuses() throws CopilotServiceException {
        final String method = "LookupServiceImpl#getAllCopilotProjectStatuses";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        try {
            List<CopilotProjectStatus> list = lookupDAO.getAllCopilotProjectStatuses();
            Helper.logReturnValue(getLog(), list);
            Helper.logMethodExit(method, getLog(), date);
            return list;
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException(
                "Error occurred when executing getAllCopilotProjectStatuses.", e), method, getLog());
        }
    }

    /**
     * Retrieves all copilot types from persistence. Returns an empty list if none are found.
     *
     * @return the retrieved copilot types (not null, doesn't contain null)
     * @throws CopilotServiceException if any error occurred
     */
    public List<CopilotType> getAllCopilotTypes() throws CopilotServiceException {
        final String method = "LookupServiceImpl#getAllCopilotTypes";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        try {
            List<CopilotType> list = lookupDAO.getAllCopilotTypes();
            Helper.logReturnValue(getLog(), list);
            Helper.logMethodExit(method, getLog(), date);
            return list;
        } catch (CopilotDAOException e) {
            throw Helper.logError(
                new CopilotServiceException("Error occurred when executing getAllCopilotTypes.", e), method,
                getLog());
        }
    }

    /**
     * Retrieves the lookup DAO to be used by this class.
     *
     * @return the lookup DAO to be used by this class
     */
    protected LookupDAO getLookupDAO() {
        return lookupDAO;
    }

    /**
     * Sets the lookup DAO to be used by this class.
     *
     * @param lookupDAO the lookup DAO to be used by this class
     */
    public void setLookupDAO(LookupDAO lookupDAO) {
        this.lookupDAO = lookupDAO;
    }
}
