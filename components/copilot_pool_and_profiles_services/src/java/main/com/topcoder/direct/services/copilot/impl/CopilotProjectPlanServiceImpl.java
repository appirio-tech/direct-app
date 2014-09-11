/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.impl;

import java.util.Date;

import javax.annotation.PostConstruct;

import com.topcoder.direct.services.copilot.CopilotProjectPlanService;
import com.topcoder.direct.services.copilot.CopilotServiceException;
import com.topcoder.direct.services.copilot.CopilotServiceInitializationException;
import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;

/**
 * <p>
 * This class is an implementation of CopilotProjectPlanService that simply delegates all calls to a pluggable
 * CopilotProjectPlanDAO instance. It extends GenericServiceImpl&lt;CopilotProjectPlan&gt; that implements most of
 * interface methods. This class uses Logging Wrapper logger to log errors and debug information.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {&quot;applicationContext.xml&quot;});
 * CopilotProjectPlanService service = (CopilotProjectPlanService)
 * context.getBean(&quot;copilotProjectPlanService&quot;);
 * // get copilot project plan
 * service.getCopilotProjectPlan(1l);
 * // create
 * service.create(new CopilotProjectPlan());
 * // update
 * CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
 * copilotProjectPlan.setId(1l);
 * service.update(copilotProjectPlan);
 * // delete
 * service.delete(1l);
 * // retrieve
 * service.retrieve(1l);
 * // retrieveAll
 * service.retrieveAll();
 *
 * </pre>
 *
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class has mutable attributes, thus it's not thread safe. But it's assumed that it will
 * be initialized via Spring IoC before calling any business method, this way it's always used in thread safe manner.
 * It uses thread safe CopilotProjectPlanDAO and Log instances.
 * </p>
 *
 * @author saarixx, TCSDEVELOPER
 * @version 1.0
 */
public class CopilotProjectPlanServiceImpl extends GenericServiceImpl<CopilotProjectPlan> implements
    CopilotProjectPlanService {
    /**
     * Creates an instance of CopilotProjectPlanServiceImpl.
     */
    public CopilotProjectPlanServiceImpl() {
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
        if (!(getGenericDAO() instanceof CopilotProjectPlanDAO)) {
            throw new CopilotServiceInitializationException(
                "genericDAO should be an instance of CopilotProjectPlanDAO.");
        }
    }

    /**
     * Retrieves the copilot project plan of copilot project with the given ID.
     *
     * @param copilotProjectId the ID of the copilot project
     * @return the retrieved copilot project plan or null if copilot project with the given ID doesn't exist
     * @throws IllegalArgumentException if copilotProjectId <= 0
     * @throws CopilotServiceException if some other error occurred
     */
    public CopilotProjectPlan getCopilotProjectPlan(long copilotProjectId) throws CopilotServiceException {
        final String method = "CopilotProjectPlanServiceImpl#getCopilotProjectPlan";
        final Date date = new Date();
        Helper.logMethodEntrance(method, getLog(), date);
        Helper.logInputArguments(getLog(), new String[] {"copilotProjectId"}, new Object[] {copilotProjectId});
        Helper.checkPositive(copilotProjectId, "copilotProjectId", method, getLog());
        try {
            CopilotProjectPlanDAO copilotProjectPlanDAO = (CopilotProjectPlanDAO) getGenericDAO();
            CopilotProjectPlan copilotProjectPlan = copilotProjectPlanDAO.getCopilotProjectPlan(copilotProjectId);
            Helper.logReturnValue(getLog(), copilotProjectPlan);
            Helper.logMethodExit(method, getLog(), date);
            return copilotProjectPlan;
        } catch (CopilotDAOException e) {
            throw Helper.logError(new CopilotServiceException(
                "Error occurred when executing copilotProjectPlanDAO#getCopilotProjectPlan.", e), method, getLog());
        }
    }
}
