/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.contest.eligibility.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import javax.annotation.PostConstruct;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.topcoder.service.contest.eligibility.ContestEligibility;
import org.jboss.logging.Logger;

/**
 * <p>
 * It is a stateless session bean that uses JPA to manage the persistence of eligibility entities.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This class is thread safe because all operations are transactions in the EJB
 * environment.
 * </p>
 * <p>
 * <strong>Sample Usage:</strong>
 *
 * <pre>
 * //Acquire a ContestEligibilityManager
 * Context context = new InitialContext();
 * ContestEligibilityManager contestEligibilityManager =
 *     (ContestEligibilityManager) context
 *         .lookup(&quot;contest_eligibility_persistence/ContestEligibilityManagerBean/remote&quot;);
 *
 * // create a GroupContestEligibility instance named groupContestEligibility
 * ContestEligibility groupContestEligibility = new GroupContestEligibility();
 * groupContestEligibility.setContestId(16);
 * groupContestEligibility.setStudio(true);
 *
 * // insert groupContestEligibility into DB
 * groupContestEligibility = contestEligibilityManager.create(groupContestEligibility);
 *
 * // get a list of eligibilities for a contest
 * List&lt;ContestEligibility&gt; list = contestEligibilityManager.getContestEligibility(16, true);
 * System.out.println(list.size());
 *
 * // Save a list of eligibilities,you can add/update/delete entities.Here we just update it. You also
 * // can refer to ContestEligibilityManagerBeanTests for more tests to add/update/delete.
 * contestEligibilityManager.save(list);
 *
 * // Remove a contest eligibility
 * contestEligibilityManager.remove(groupContestEligibility);
 * </pre>
 *
 * </p>
 *
 * <p>
 * Version 1.0.1 ((TopCoder Online Review Switch To Local Calls Assembly)) Change notes:
 *   <ol>
 *     <li>Updated the class to use JBoss Logging for logging the events to make the component usable in local
 *     environment for Online Review application.</li>
 *     <li>Changed visibility for {@link #getEntityManager()} method to protected so it could be overridden for
 *     injection of the entity manager in local environment for Online Review application.</li>
 *   </ol>
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0.1
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ContestEligibilityManagerBean implements ContestEligibilityManagerLocal,
    ContestEligibilityManagerRemote {

    /**
     * <p>
     * Represents the sessionContext of the EJB.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;


    /**
     * <p>
     * This field represents the entity manager which is used to communicate with the persistence.
     * </p>
     * <p>
     * It's automatically injected by EJB container.
     * </p>
     */
    @Resource(name = "unitName")
    private String unitName;

    /**
     * The logger is used to log the methods.
     */
    private Logger logger;

    /**
     * Represents the log name.Default value is 'contest_eligibility_logger'.You also could change the default value
     * via deploy descriptor.
     */
    @Resource(name = "logName")
    private String logName = "contest_eligibility_logger";

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ContestEligibilityManagerBean() {
        // Does nothing
    }

    /**
     * Handle the post-construct event. It will initialize the logger.
     */
    @PostConstruct
    protected void initialize() {
        logger = Logger.getLogger(this.logName);
    }

    /**
     * Add a contest eligibility.
     *
     * @param contestEligibility
     *            contest eligibility
     * @return the added contest eligibility
     * @throws IllegalArgumentException
     *             if contestEligibility is null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when persisting the given contest eligibility
     */
    public ContestEligibility create(ContestEligibility contestEligibility)
        throws ContestEligibilityPersistenceException {
        logEntrance("ContestEligibilityManagerBean#create", new String[] {"contestEligibility"},
            new Object[] {contestEligibility});
        checkNull(contestEligibility, "contestEligibility");
        try {
            EntityManager entityManager = getEntityManager();
            entityManager.persist(contestEligibility);
        } catch (RuntimeException e) {
            throw logError(new ContestEligibilityPersistenceException(
                "Some error happens while persisting the entity.", e));
        }
        logExit("ContestEligibilityManagerBean#create");
        return contestEligibility;
    }

    /**
     * Remove a contest eligibility.
     *
     * @param contestEligibility
     *            the contest eligibility to remove
     * @throws IllegalArgumentException
     *             if contestEligibility is null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when removing the given contest eligibility
     */
    public void remove(ContestEligibility contestEligibility) throws ContestEligibilityPersistenceException {
        logEntrance("ContestEligibilityManagerBean#remove", new String[] {"contestEligibility"},
            new Object[] {contestEligibility});
        checkNull(contestEligibility, "contestEligibility");
        try {
            EntityManager entityManager = getEntityManager();
            entityManager.remove(entityManager.merge(contestEligibility));
        } catch (RuntimeException e) {
            throw logError(new ContestEligibilityPersistenceException(
                "Some error happens while removing the entity.", e));
        }
        logExit("ContestEligibilityManagerBean#remove");
    }

    /**
     * <p>
     * Save a list of eligibilities, if can be create/update/delete.
     * </p>
     * <ol>
     * You will get a list of eligibilities as input parameter, for each eligibility in the list, you do one
     * of these,
     * <li>create/insert, if id is 0 then insert.</li>
     * <li>update, if id is not 0, do an update.</li>
     * <li>delete, if 'delete' flag is true, you remove the eligibility.</li>
     * </ol>
     *
     * @param list
     *            a list of eligibilities,can be empty,then do nothing
     * @return the added/updated eligibilities
     * @throws IllegalArgumentException
     *             if list is null or it contains null
     * @throws ContestEligibilityPersistenceException
     *             if any errors occurred when saving eligibilities
     */
    public List<ContestEligibility> save(List<ContestEligibility> list)
        throws ContestEligibilityPersistenceException {
        logEntrance("ContestEligibilityManagerBean#save", new String[] {"list"}, new Object[] {list});
        checkNull(list, "list");
        for (ContestEligibility contestEligibility : list) {
            checkNull(contestEligibility, "contest eligibility in list");
        }
        try {
            EntityManager entityManager = getEntityManager();

            for (Iterator<ContestEligibility> iterator = list.iterator(); iterator.hasNext();) {
                ContestEligibility contestEligibility = iterator.next();
                if (contestEligibility.isDeleted()) {
                    entityManager.remove(entityManager.merge(contestEligibility));
                    iterator.remove();
                    continue;
                }

                if (contestEligibility.getId() != 0) {
                    entityManager.merge(contestEligibility);
                    continue;
                }
                entityManager.persist(contestEligibility);
            }
        } catch (RuntimeException e) {
            throw logError(new ContestEligibilityPersistenceException(
                "Some error happens while saving the list of contestEligibility.", e));
        }
        logExit("ContestEligibilityManagerBean#save");
        return list;
    }

    /**
     * Return a list of eligibilities for a contest.
     *
     * @param contestId
     *            the contest id
     * @param isStudio
     *            the flag used to indicate whether it is studio
     * @return a list of eligibilities
     * @throws IllegalArgumentException
     *             if contestId is not positive
     */
    @SuppressWarnings("unchecked")
    public List<ContestEligibility> getContestEligibility(long contestId, boolean isStudio) throws ContestEligibilityPersistenceException {
      
        logEntrance("ContestEligibilityManagerBean#getContestEligibility", new String[] {"contestId",
        "isStudio"}, new Object[] {contestId, isStudio});
        checkPositive(contestId, "contestId");
        EntityManager entityManager = getEntityManager();
        final Query query =
            entityManager
                .createQuery("from ContestEligibility c where c.contestId=:contestId and c.studio=:studio");
        query.setParameter("contestId", contestId);
        query.setParameter("studio", isStudio);
        List<ContestEligibility> results = query.getResultList();
        logExit("ContestEligibilityManagerBean#getContestEligibility");
        return results;
    
        
    }


    /**
     * Return a list of contest ids that has eligibility.
     *
     * @param contestids
     *            the contest id list
     * @param isStudio
     *            the flag used to indicate whether it is studio
     * @return a list of contst ids
     * @throws IllegalArgumentException
     *             if contestId is not positive
     */
    @SuppressWarnings("unchecked")
    public Set<Long> haveEligibility(Long[] contestids, boolean isStudio) throws ContestEligibilityPersistenceException {
        logEntrance("ContestEligibilityManagerBean#haveEligibility", new String[] {"contestIds[]",
            "isStudio"}, new Object[]{contestids, isStudio});

        Set<Long> result = new HashSet<Long>();

        if (contestids == null || contestids.length == 0)
        {
            return result;
        }
        
        String ids = "(" + contestids[0].longValue();
        if (contestids.length > 1)
        {
            for (int i = 1; i < contestids.length; i++)
            {
                ids += ", " + contestids[i].longValue();
            }
        }
        
        ids += ")";

        int studio = isStudio ? 1 : 0;
        String qeuryStr = "select unique contest_id from contest_eligibility where is_studio = "+ studio +" and  contest_id in " + ids;

        EntityManager entityManager = getEntityManager();
        Query query =
            entityManager
                .createNativeQuery(qeuryStr);

        List list = query.getResultList();

        if (list == null || list.size() == 0)
        {
            return result;
        }


        for (int i = 0; i < list.size(); i++) {

            Object row = list.get(i);

            result.add(new Long(row.toString()));
        }

        logExit("ContestEligibilityManagerBean#haveEligibility");
        return result;
    }

    /**
     * <p>
     * Logs the error.
     * </p>
     *
     * @param <T>
     *            the generic class type of error
     * @param error
     *            the error needs to be logged.
     * @return the error
     */
    private <T extends Exception> T logError(T error) {
        logger.error( "Error recognized: " +  error.getMessage(), error);
        return error;
    }

    /**
     * <p>
     * Log the entrance of a method and all the input arguments.
     * </p>
     *
     * @param methodName
     *            the name of the method
     * @param paramNames
     *            the name of the parameters
     * @param params
     *            the parameters
     */
    @SuppressWarnings("unchecked")
    private void logEntrance(String methodName, String[] paramNames, Object[] params) {
        logger.debug("Enter into Method: " + methodName + " At " + new Date());
        if (paramNames != null) {
            StringBuilder logInfo = new StringBuilder("Parameters:");
            for (int i = 0; i < paramNames.length; i++) {
                if (params[i] instanceof ContestEligibility) {
                    ContestEligibility contestEligibility = (ContestEligibility) params[i];
                    logInfo.append(" [ " + paramNames[i] + " = " + contestEligibility.getClass().getName()
                        + " with id=" + contestEligibility.getId() + " ]");
                    continue;
                }
                if (params[i] instanceof List && ((List<ContestEligibility>) params[i]).size() != 0) {
                    List<ContestEligibility> list = (List<ContestEligibility>) params[i];
                    StringBuilder paramLog = new StringBuilder();
                    for (ContestEligibility contestEligibility : list) {
                        if (contestEligibility == null) {
                            paramLog.append("  null");
                            continue;
                        }
                        paramLog.append(contestEligibility.getClass().getName() + " with id="
                            + contestEligibility.getId() + "  ");
                    }
                    logInfo.append(" [ " + paramNames[i] + " = {" + paramLog.toString() + "} ]");
                    continue;
                }
                logInfo.append(" [ " + paramNames[i] + " = " + params[i] + " ]");
            }
            logger.info(logInfo);
        }
    }

    /**
     * <p>
     * Log the exit of a method.
     * </p>
     *
     * @param methodName
     *            the name of the method
     */
    private void logExit(String methodName) {
        logger.debug("Exit out Method: " + methodName + " At " + new Date());
    }

    /**
     * <p>
     * Checks whether the given Object is null.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @throws IllegalArgumentException
     *             if the given Object is null
     */
    private void checkNull(Object arg, String name) {
        if (arg == null) {
            IllegalArgumentException e =
                new IllegalArgumentException("The argument " + name + " cannot be null.");
            throw logError(e);
        }
    }

    /**
     * <p>
     * Checks whether the given argument is positive.
     * </p>
     *
     * @param arg
     *            the argument to check
     * @param name
     *            the name of the argument to check
     * @throws IllegalArgumentException
     *             if the given argument is not positive
     */
    private void checkPositive(long arg, String name) {
        if (arg <= 0) {
            IllegalArgumentException e = new IllegalArgumentException(name + " should be positive.");
            throw logError(e);
        }
    }

    /**
     * <p>
     * Returns the <code>EntityManager</code> looked up from the session context.
     * </p>
     * 
     * @return the EntityManager looked up from the session context
     * @throws ContestEligibilityPersistenceException
     *             if fail to get the EntityManager from the sessionContext.
     */
    protected EntityManager getEntityManager() throws ContestEligibilityPersistenceException {
        try {
            Object obj = sessionContext.lookup(unitName);

            if (obj == null) {
                throw logError(new ContestEligibilityPersistenceException(
                "The object for jndi name '" + unitName + "' doesn't exist."));
            }

            return (EntityManager) obj;
        } catch (ClassCastException e) {
             throw new ContestEligibilityPersistenceException(
               "The jndi name for '" + unitName
                    + "' should be EntityManager instance.", e);
        }
    }
}