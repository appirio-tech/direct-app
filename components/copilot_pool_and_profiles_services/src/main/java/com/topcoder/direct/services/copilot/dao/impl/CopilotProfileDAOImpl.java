/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.direct.services.copilot.dao.impl;

import com.topcoder.direct.services.copilot.dao.CopilotDAOException;
import com.topcoder.direct.services.copilot.dao.CopilotProfileDAO;
import com.topcoder.direct.services.copilot.model.CopilotProfile;
import com.topcoder.direct.services.copilot.model.CopilotProfileInfo;
import com.topcoder.direct.services.copilot.model.IdentifiableEntity;
import org.hibernate.HibernateException;

import java.text.MessageFormat;

/**
 * <p>This class is an implementation of CopilotProfileDAO that uses Hibernate session to access entities in
 * persistence. It extends GenericDAOImpl&lt;CopilotProfile&gt; that provides common functionality for
 * CopilotProfileDAO, CopilotProjectDAO and CopilotProjectPlanDAO implementations provided in this component. This class
 * uses Logging Wrapper logger to log errors and debug information.</p>
 *
 * <p><strong>Configuration: </strong>This class will be used with Spring IoC and will be configured using Spring xml
 * file, sample bean definition:
 *
 * <pre>
 * &lt;bean id="copilotProfileDAO"
 *        class="com.topcoder.direct.services.copilot.dao.impl.CopilotProfileDAOImpl"&gt;
 *      &lt;property name="loggerName" value="myLogger"/&gt;
 *      &lt;property name="sessionFactory" ref="tcsCatalogSessionFactory"/&gt;
 * &lt;/bean&gt;
 * </pre>
 * </p>
 *
 * <p><strong>Sample usage:</strong>
 * <pre>
 * // Retrieves CopilotProfileDAO from the Spring application context
 * ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
 * CopilotProfileDAO copilotProfileDAO =
 * (CopilotProfileDAO) context.getBean("copilotProfileDAO");
 *
 * // Creates CopilotProfileProfile instance and fill it with data
 * CopilotProfile copilotProfile = new CopilotProfile();
 *
 * // Saves the CopilotProfile in persistence
 * int id = copilotProfileDAO.create(copilotProfile);
 *
 * // Updates the CopilotProfile
 * copilotProfileDAO.update(copilotProfile);
 *
 * // Deletes the CopilotProfile by id
 * copilotProfileDAO.delete(1);
 *
 * // Retrieves CopilotProfile by id
 * CopilotProfile copilotProfile = copilotProfileDAO.retrieve(1);
 *
 * // Retrieves all CopilotProfiles
 * List&lt;CopilotProfile&gt; copilotProfiles = copilotProfileDAO.retrieveAll();
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
public class CopilotProfileDAOImpl extends GenericDAOImpl<CopilotProfile> implements CopilotProfileDAO {

    /**
     * <p>Represents class name.</p>
     */
    private static final String CLASS_NAME = "CopilotProfileDAOImpl";

    /**
     * <p>Represents a hql query used for retrieving {@link CopilotProfile} by userId.</p>
     */
    private static final String COPILOT_PROFILE_QUERY = "from CopilotProfile cp where cp.userId = :userId";

    /**
     * <p>Creates new instance of <code>{@link CopilotProfile}</code> class.</p>
     */
    public CopilotProfileDAOImpl() {
        // empty constructor
    }

    /**
     * <p>Retrieves the copilot profile of the user with the given ID.</p>
     *
     * @param userId the user ID of the copilot
     *
     * @return the retrieved copilot profile or null if copilot user with the given ID doesn't exist
     *
     * @throws IllegalArgumentException if userId <= 0
     * @throws CopilotDAOException      if some other error occurred
     */
    public CopilotProfile getCopilotProfile(long userId) throws CopilotDAOException {
        final String methodName = "getCopilotProfile";
        final long executionStart = System.currentTimeMillis();
        Helper.logMethodEntered(getLog(), CLASS_NAME, methodName, new String[]{"userId"}, new Object[]{userId});

        Helper.checkIsPositive(getLog(), userId, "userId", CLASS_NAME, methodName);

        try {
            // executes query and retrieves single result
            CopilotProfile result = (CopilotProfile) getSession()
                    .createQuery(COPILOT_PROFILE_QUERY)
                    .setParameter("userId", userId)
                    .uniqueResult();

            // log method exit
            Helper.logMethodExited(getLog(), CLASS_NAME, methodName, executionStart, result);

            // return result
            return result;
        } catch (HibernateException e) {
            CopilotDAOException exc = new CopilotDAOException(MessageFormat.format(
                    "Exception occurred when retrieving CopilotProfile with userId {0}.", userId), e);

            Helper.logError(getLog(),
                    MessageFormat.format(Helper.METHOD_ERROR, CLASS_NAME, methodName), e);

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

        // delegates to base class method
        // propagates IllegalArgumentException
        super.updateAuditTimestamp(entity, create);

        if (entity instanceof CopilotProfile) {
            CopilotProfile copilotProfile = (CopilotProfile) entity;

            // iterate over all ProfileInfo and update them
            if (copilotProfile.getProfileInfos() != null) {
                for (CopilotProfileInfo copilotProfileInfo : copilotProfile.getProfileInfos()) {

                    updateAuditTimestamp(copilotProfileInfo, copilotProfileInfo.getId() == 0);
                }
            }
        }
    }
}

