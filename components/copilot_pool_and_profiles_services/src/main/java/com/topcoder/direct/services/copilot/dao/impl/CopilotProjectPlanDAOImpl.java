/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProjectPlanDAO;
import com.topcoder.direct.services.copilot.model.CopilotProjectPlan;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import com.topcoder.direct.services.copilot.model.PlannedContest;
import org.hibernate.HibernateException;

import java.text.MessageFormat;

/**
 * <p>This class is an implementation of CopilotProjectPlanDAO that uses Hibernate session to access entities in
 * persistence. It extends GenericDAOImpl&lt;CopilotProjectPlan&gt; that provides common functionality for
 * CopilotProfileDAO, CopilotProjectDAO and CopilotProjectPlanDAO implementations provided in this component. This class
 * uses Logging Wrapper logger to log errors and debug information.
 *
 * <p><strong>Configuration: </strong>This class will be used with Spring IoC and will be configured using Spring xml
 * file, sample bean definition:
 *
 * <pre>
 * &lt;bean id="copilotProjectPlanDAO"
 *        class="com.topcoder.direct.services.copilot.dao.impl.CopilotProjectPlanDAOImpl"&gt;
 *      &lt;property name="loggerName" value="myLogger"/&gt;
 *      &lt;property name="sessionFactory" ref="tcsCatalogSessionFactory"/&gt;
 * &lt;/bean&gt;
 * </pre>
 * </p>
 *
 * <p><strong>Sample usage:</strong>
 * <pre>
 * // Retrieves CopilotProjectPlanDAO from the Spring application context
 * ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
 * CopilotProjectPlanDAO copilotProjectPlanDAO =
 * (CopilotProjectPlanDAO) context.getBean("copilotProjectPlanDAO");
 *
 * // Creates CopilotProjectPlanProjectPlan instance and fill it with data
 * CopilotProjectPlan copilotProjectPlan = new CopilotProjectPlan();
 *
 * // Saves the CopilotProjectPlan in persistence
 * int id = copilotProjectPlanDAO.create(copilotProjectPlan);
 *
 * // Updates the CopilotProjectPlan
 * copilotProjectPlanDAO.update(copilotProjectPlan);
 *
 * // Deletes the CopilotProjectPlan by id
 * copilotProjectPlanDAO.delete(1);
 *
 * // Retrieves CopilotProjectPlan by id
 * CopilotProjectPlan copilotProjectPlan = copilotProjectPlanDAO.retrieve(1);
 *
 * // Retrieves all CopilotProjectPlans
 * List&lt;CopilotProjectPlan&gt; copilotProjectPlans = copilotProjectPlanDAO.retrieveAll();
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
public class CopilotProjectPlanDAOImpl extends GenericDAOImpl<CopilotProjectPlan> implements CopilotProjectPlanDAO {

    /**
     * <p>Represents class name.</p>
     */
    private static final String CLASS_NAME = "CopilotProjectPlanDAOImpl";

    /**
     * <p>Represents a hql query used for retrieving {@link CopilotProjectPlan} by copilotProjectId.</p>
     */
    private static final String COPILOT_PROJECT_PLAN_QUERY =
            "from CopilotProjectPlan p where p.copilotProjectId = :copilotProjectId";

    /**
     * <p>Creates new instance of <code>{@link CopilotProjectPlanDAOImpl}</code> class.</p>
     */
    public CopilotProjectPlanDAOImpl() {
        // empty constructor
    }

    /**
     * <p>Retrieves the copilot project plan of copilot project with the given ID.</p>
     *
     * @param copilotProjectId the ID of the copilot project
     *
     * @return the retrieved copilot project plan (or null if copilot project with the given ID doesn't exist or there
     *         is no associated plan)
     *
     * @throws IllegalArgumentException if copilotProjectId <= 0
     * @throws CopilotDAOException      if some other error occurred
     */
    public CopilotProjectPlan getCopilotProjectPlan(long copilotProjectId) throws CopilotDAOException {
        final String methodName = "getCopilotProjectPlan";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName, new String[]{"copilotProjectId"},
                new Object[]{copilotProjectId});

        Helper.checkIsPositive(getLog(), copilotProjectId, "copilotProjectId", CLASS_NAME, methodName);

        try {
            // executes query and retrieves single result
            CopilotProjectPlan result = (CopilotProjectPlan) getSession()
                    .createQuery(COPILOT_PROJECT_PLAN_QUERY)
                    .setParameter("copilotProjectId", copilotProjectId)
                    .uniqueResult();

            // logs method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart, result);

            // returns result
            return result;
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException(MessageFormat.format(
                    "Exception occurred when retrieving CopilotProjectPlan with copilotProjectId {0}.",
                    copilotProjectId), e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), exc);

            throw exc;
        } catch (CopilotDAOException e) {
            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), e);

            throw e;
        }
    }

    /**
     * <p>Updates the creation/modification timestamp property of the given entity with the current date/time.</p>
     *
     * @param entity the entity to be updated
     * @param create true if entity will be created in persistence, false - if entity will be updated
     *
     * @throws IllegalArgumentException if entity is null
     */
    protected void updateAuditTimestamp(IdentifiableEntity entity, boolean create) {

        // delegate to base class method
        // propagates IllegalArgumentException
        super.updateAuditTimestamp(entity, create);

        if (entity instanceof CopilotProjectPlan) {
            CopilotProjectPlan copilotProjectPlan = (CopilotProjectPlan) entity;

            // iterate over all planned contests
            if (copilotProjectPlan.getPlannedContests() != null) {
                for (PlannedContest plannedContest : copilotProjectPlan.getPlannedContests()) {
                    updateAuditTimestamp(plannedContest, plannedContest.getId() == 0);
                }
            }
        }
    }
}
