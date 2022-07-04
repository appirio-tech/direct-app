/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.LookupDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfileStatus;
import com.topcoder.direct.services.copilot.model.CopilotProjectStatus;
import com.topcoder.direct.services.copilot.model.CopilotType;

import java.text.MessageFormat;
import java.util.List;

/**
 * <p>This class is an implementation of LookupDAO that uses Hibernate session to access entities in persistence. This
 * class uses Logging Wrapper logger to log errors and debug information.</p>
 *
 * <p><strong>Configuration: </strong>This class will be used with Spring IoC and will be configured using Spring xml
 * file, sample bean definition:
 *
 * <pre>
 * &lt;bean id="lookupDAO"
 *        class="com.topcoder.direct.services.copilot.dao.impl.CopilotLookupDAOImpl"&gt;
 *      &lt;property name="loggerName" value="myLogger"/&gt;
 *      &lt;property name="sessionFactory" ref="tcsCatalogSessionFactory"/&gt;
 * &lt;/bean&gt;
 * </pre>
 * </p>
 *
 * <p><strong>Sample usage:</strong>
 * <pre>
 * // Retrieves LookupDAO from the Spring application context
 * ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
 * LookupDAO lookupDAO = (LookupDAO) context.getBean("lookupDAO");
 *
 * // Retrieves all CopilotProfilesStatus
 * List&lt;CopilotProfileStatus&gt; copilotProfileStatuses = lookupDAO.getAllCopilotProfileStatuses();
 *
 * // Retrieves all CopilotProjectStatus
 * List&lt;CopilotProjectStatus&gt; copilotProjectStatuses = lookupDAO.getAllCopilotProjectStatuses();
 *
 * // Retrieves all CopilotType
 * List&lt;CopilotType&gt; copilotTypes = lookupDAO.getAllCopilotTypes();
 * </pre>
 * </p>
 *
 * <p><strong>Thread safety:</strong> This class has mutable attributes, thus it's not thread safe. But it's assumed
 * that it will be initialized via Spring IoC before calling any business method, this way it's always used in thread
 * safe manner. It uses thread safe SessionFactory, Session and Log instances.</p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class LookupDAOImpl extends BaseDAO implements LookupDAO {

    /**
     * <p>Represents class name.</p>
     */
    private static final String CLASS_NAME = "LookupDAOImpl";

    /**
     * <p>Creates new instance of <code>{@link LookupDAOImpl}</code> class.</p>
     */
    public LookupDAOImpl() {
        // empty constructor
    }

    /**
     * <p>Retrieves all copilot profile statuses from persistence. Returns an empty list if none are found.</p>
     *
     * @return the retrieved copilot profile statuses (not null, doesn't contain null)
     *
     * @throws CopilotDAOException if any error occurred
     */
    public List<CopilotProfileStatus> getAllCopilotProfileStatuses() throws CopilotDAOException {
        final String methodName = "getAllCopilotProfileStatuses";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName);

        try {
            // retrieves all CopilotProfileStatus stored in persistence - simply delegates to base class method
            List<CopilotProfileStatus> result = getAllEntities(CopilotProfileStatus.class);

            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart, result);

            // returns the result
            return result;
        } catch (CopilotDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), e);

            throw e;
        }
    }

    /**
     * <p>Retrieves all copilot project statuses from persistence. Returns an empty list if none are found.</p>
     *
     * @return the retrieved copilot project statuses (not null, doesn't contain null)
     *
     * @throws CopilotDAOException if any error occurred
     */
    public List<CopilotProjectStatus> getAllCopilotProjectStatuses() throws CopilotDAOException {
        final String methodName = "getAllCopilotProjectStatuses";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName);

        try {
            // retrieves all CopilotProjectStatus stored in persistence - simply delegates to base class method
            List<CopilotProjectStatus> result = getAllEntities(CopilotProjectStatus.class);

            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart, result);

            // returns the result
            return result;
        } catch (CopilotDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), e);

            throw e;
        }
    }

    /**
     * <p>Retrieves all copilot types from persistence. Returns an empty list if none are found.</p>
     *
     * @return the retrieved copilot types (not null, doesn't contain null)
     *
     * @throws CopilotDAOException if any error occurred
     */
    public List<CopilotType> getAllCopilotTypes() throws CopilotDAOException {
        final String methodName = "getAllCopilotTypes";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName);

        try {
            // retrieves all CopilotTypes stored in persistence - simply delegates to base class method
            List<CopilotType> result = getAllEntities(CopilotType.class);

            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart, result);

            // returns the result
            return result;
        } catch (CopilotDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), e);

            throw e;
        }
    }
}
